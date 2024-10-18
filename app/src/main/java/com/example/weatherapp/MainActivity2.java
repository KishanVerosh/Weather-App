package com.example.weatherapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    TextView dateTextView, timeTextView, temperatureTextView, humidityTextView;
    ImageView weatherIcon; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dateTextView = findViewById(R.id.date_text_view);
        timeTextView = findViewById(R.id.time_text_view);
        temperatureTextView = findViewById(R.id.temperature_text_view);
        humidityTextView = findViewById(R.id.humidity_text_view);
        weatherIcon = findViewById(R.id.weather_icon); // Add this line

        // Get the data from the Intent
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String temperature = getIntent().getStringExtra("temperature");
        String humidity = getIntent().getStringExtra("humidity");
        int imageResource = getIntent().getIntExtra("image", R.drawable.icon_default);

        // Set the data to the TextViews
        dateTextView.setText(date);
        timeTextView.setText(time);
        temperatureTextView.setText(temperature);
        humidityTextView.setText(humidity);
        weatherIcon.setImageResource(imageResource); // Add this line
    }
}
