package com.example.hotel1;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.os.Bundle;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button b1=findViewById(R.id.registerbutton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });
    }
    public void register(View view) {

        final EditText name = findViewById(R.id.editText1);
        final EditText username = findViewById(R.id.editText2);
        final EditText passEdit = findViewById(R.id.editText3);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        String name1 = name.getText().toString();
        String user = username.getText().toString();
        String pass = passEdit.getText().toString();
        int rCheckId = radioGroup.getCheckedRadioButtonId();
        if (rCheckId == -1)
        {
            Toast.makeText(getApplicationContext(), "Select the gender", Toast.LENGTH_SHORT).show();
        }
        else
        {
            RadioButton radioButon = findViewById(rCheckId);
            String gender = radioButon.getText().toString();

            MyDb myDb = new MyDb(this);
            SQLiteDatabase dbr = myDb.getReadableDatabase();
            Cursor c = dbr.rawQuery("SELECT * FROM Customer WHERE username = ?", new String[]{user});
            /*
            If the cursor successfully moves to the first row, it means that a record with the specified username already exists in the database. The moveToFirst() method returns true if the cursor is not empty, and in this case, it means that there is a match
             */
            if (c.moveToFirst())
            {
                Toast.makeText(getApplicationContext(), "This username already exists", Toast.LENGTH_SHORT).show();
            }
            else
            {
                SQLiteDatabase db = myDb.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", name1);
                values.put("username", user);
                values.put("pass", pass);
                values.put("gender", gender);
                if (name1.trim().length() == 0 || user.trim().length() == 0 || pass.trim().length() == 0 || gender.matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Enter the details", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.insert("Customer", null, values);
                    Toast.makeText(this, "You registered successfully!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                }
            }
        }
    }
}