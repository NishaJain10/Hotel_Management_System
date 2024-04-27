package com.example.hotel1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HotellistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotellist);
        CardView cardView1 = findViewById(R.id.cardView1);
        CardView cardView2 = findViewById(R.id.cardView2);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openHotelDetailsActivity("Double Tree");
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHotelDetailsActivity1("The Florida Hotel");
            }
        });
    }
    private void openHotelDetailsActivity(String hotelName) {
        Intent intent = new Intent(HotellistActivity.this, HotelsActivity.class);
        intent.putExtra("HOTEL_NAME", hotelName);
        startActivity(intent);
    }
    private void openHotelDetailsActivity1(String hotelName) {
        Intent intent = new Intent(HotellistActivity.this, HotelsActivity1.class);
        intent.putExtra("HOTEL_NAME", hotelName);
        startActivity(intent);
    }
}