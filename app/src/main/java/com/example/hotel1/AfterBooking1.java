package com.example.hotel1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AfterBooking1 extends AppCompatActivity {
    private NotificationManager mManager;
    private static final String CHANNEL_ID = "ABCD";
    private static final String CHANNEL_NAME = "DemoNotification";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_booking1);

        MyDb myDb = new MyDb(this);
        SQLiteDatabase db = myDb.getReadableDatabase();
        Bundle bundle = getIntent().getExtras();
        int j = 0;
        final String usn = bundle.getString("Username");
        final String Room_no = bundle.getString("Room_no");
        //int numberofdays=getIntent().getExtras().getInt("Number_of_days");
        String nom = getIntent().getExtras().getString("Number_of_days");
        int nn = Integer.parseInt(nom);
        switch (Room_no) {
            case "Standard":
                j = 1000;
                break;
            case "Deluxe":
                j = 2000;
                break;
            case "Suite":
                j = 3000;
                break;
            case "Executive":
                j = 4000;
                break;
            case "Family":
                j = 5000;
                break;
            case "Presidential":
                j = 6000;
                break;
            case "Adjoining":
                j = 7000;
                break;
            case "Art Suite":
                j = 8000;
                break;
            case "Penthouse":
                j = 9000;
                break;
            case "Pet-Friendly":
                j = 10000;
                break;
            default:
                j = 0;
        }
        int num = nn * j;
        TextView text = findViewById(R.id.textView18);
        text.setText("Your total cost will be \n" + String.valueOf(num));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            if (mManager == null) {
                mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }
            mManager.createNotificationChannel(channel);
            NotificationCompat.Builder builder = getChannelNotification(
                    "My Notification", "This notification was created for DEMO");
            Intent resultIntent = new Intent(this, AfterBooking.class);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_IMMUTABLE);
            builder.setContentIntent(resultPendingIntent);
            mManager.notify(1, builder.build());
        }

        TextView tt=findViewById(R.id.textView13);
        tt.setText("Thank You!");

        Button button=findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(j);
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