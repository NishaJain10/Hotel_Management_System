package com.example.hotel1;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        final Spinner dropdown=findViewById(R.id.spinner);
        String[] items=new String[]{"Standard","Deluxe","Suite","Executive","Family","Presidential","Adjoining","Art Suite","Penthouse","Pet-Friendly"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);
        dropdown.setAdapter(adapter);
        MyDb myDb=new MyDb(this);
        final Button all=findViewById(R.id.button8);
        final SQLiteDatabase db=myDb.getWritableDatabase();
        final SQLiteDatabase dbr=myDb.getReadableDatabase();
        final Button b1=findViewById(R.id.button5);
        Button b2=findViewById(R.id.button6);
        Button logoutButton = findViewById(R.id.button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c=dbr.rawQuery("SELECT * FROM rooms",null);
                StringBuffer buffer=new StringBuffer();
                while (c.moveToNext()){
                    buffer.append("Room number: "+c.getString(0)+"\n");
                    //buffer.append("Name: "+c.getString(1)+"\n");
                    buffer.append("StartDate: "+c.getString(2)+"\n");
                    buffer.append("Days: "+c.getString(3)+"\n");
                }
                showMessage("Booked Rooms",buffer.toString());
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Room=dropdown.getSelectedItem().toString();
                String table="rooms";
                String where="room_no=?";
                db.delete(table,where,new String[]{Room});
                Toast.makeText(getApplicationContext(),"DELETED ROOM INFO OF "+Room,Toast.LENGTH_SHORT).show();
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cc=dbr.rawQuery("SELECT * FROM Customer",null);
                StringBuffer buffer=new StringBuffer();
                while (cc.moveToNext())
                {
                    buffer.append("Name: "+cc.getString(0)+"\n");
                    buffer.append("Username: "+cc.getString(1)+"\n");
                    buffer.append("Password: "+cc.getString(2)+"\n");
                    buffer.append("Gender: "+cc.getString(3)+"\n");
                }
                showMessage("Users Registered",buffer.toString());
                Toast.makeText(getApplicationContext(),"Displaying Users Registered",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}