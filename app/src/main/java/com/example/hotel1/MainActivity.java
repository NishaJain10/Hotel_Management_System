package com.example.hotel1;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1=findViewById(R.id.button7);
        Button registerButton = findViewById(R.id.registerbutton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText uEditText1=findViewById(R.id.editText);
                EditText pEditText2=findViewById(R.id.editText4);
                String uname=uEditText1.getText().toString();
                String pass=pEditText2.getText().toString();
                String admin="adminhotel1";
                if(uname.equals(admin) && pass.equals(admin))
                {
                    Intent i=new Intent(MainActivity.this,AdminActivity.class);
                    startActivity(i);
                }
                else{
                    login(v);
                }

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
    public void login(View view)
    {
        MyDb myDb=new MyDb(this);
        SQLiteDatabase db=myDb.getReadableDatabase();
        String[] columns={"username","pass"};
        EditText uEditText=findViewById(R.id.editText);
        EditText pEditText=findViewById(R.id.editText4);
        String[] cValues={
                uEditText.getText().toString(),pEditText.getText().toString()
        };
        Cursor cursor=db.query("Customer",columns,"username = ? AND pass = ?",cValues,null,null,null);
        if(cursor!=null)
        {
            if(cursor.moveToFirst()){
                Intent intent=new Intent(this,HotellistActivity.class);
                intent.putExtra("Username",uEditText.getText().toString());
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this,"Wrong Login Details",Toast.LENGTH_LONG).show();
            }
        }

    }
}