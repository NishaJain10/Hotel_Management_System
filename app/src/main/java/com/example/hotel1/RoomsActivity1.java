package com.example.hotel1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RoomsActivity1 extends AppCompatActivity {
    Button btnNotification;
    private NotificationManager mManager;
    private static final String CHANNEL_ID = "ABCD";
    private static final String CHANNEL_NAME = "DemoNotification";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms1);

        final Spinner no_of_days = findViewById(R.id.spinner2);
        String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        no_of_days.setAdapter(adapter);

        //Bundle bundle = getIntent().getExtras();
        final TextView v1 = findViewById(R.id.textView7);
        //final String usn = bundle.getString("Username");
        Button btn = findViewById(R.id.button9);

        final MyDb myDb = new MyDb(this);
        final SQLiteDatabase db = myDb.getWritableDatabase();
        final SQLiteDatabase dbr = myDb.getReadableDatabase();
        TextView startdate = findViewById(R.id.textView17);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                startdate.setText(date);
            }
        };

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                final int year = cal.get(Calendar.YEAR);
                final int month = cal.get(Calendar.MONTH);
                final int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        RoomsActivity1.this, android.R.style.Theme,mDateSetListener, year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String days = no_of_days.getSelectedItem().toString();
                ContentValues values = new ContentValues();
                RadioGroup help = findViewById(R.id.radio);
                int recheck = help.getCheckedRadioButtonId();

                if (recheck == -1) {
                    Toast.makeText(getApplicationContext(), "Please select a room", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedRoom = findViewById(recheck);
                    String roomNo = selectedRoom.getText().toString().trim();
                    String startDate = startdate.getText().toString();

                    //values.put("name", usn);
                    values.put("room_no", roomNo);
                    values.put("startDate", startDate);
                    values.put("no_of_days", days);

                    if (startDate.equals("Datepick")) {
                        Toast.makeText(getApplicationContext(), "Select the date", Toast.LENGTH_SHORT).show();
                    } else {
                        Cursor c = dbr.rawQuery("SELECT * FROM rooms WHERE room_no = ?", new String[]{roomNo});
                        if (c.moveToFirst())
                        {
                            Toast.makeText(getApplicationContext(), "This room is already booked", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            db.insert("rooms", null, values);
                            Toast.makeText(getApplicationContext(), "Room booked successfully", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(getApplicationContext(), AfterBooking.class);
                            //i.putExtra("Username", usn);
                            i.putExtra("Room_no", roomNo);
                            i.putExtra("Number_of_days", days);
                            startActivity(i);
                        }
                    }
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                    NotificationChannel channel = new NotificationChannel(
                            CHANNEL_ID, CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_DEFAULT);
                    if (mManager == null) {
                        mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    }
                    mManager.createNotificationChannel(channel);
                    NotificationCompat.Builder builder = getChannelNotification(
                            "Voyagable", "You Booked a room successfully");
                    Intent resultIntent = new Intent(getApplicationContext(), AfterBooking.class);
                    PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_IMMUTABLE);
                    builder.setContentIntent(resultPendingIntent);
                    mManager.notify(1, builder.build());
                }
            }
        });

        final int REQUEST_PHONE_CALL = 1;
        final Button callbutton = findViewById(R.id.button10);
        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+917021036469")); // Use the correct phone number
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(RoomsActivity1.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    } else {
                        startActivity(callIntent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public NotificationCompat.Builder getChannelNotification(String title, String message) {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_hotel)
                .setAutoCancel(true);
    }
}