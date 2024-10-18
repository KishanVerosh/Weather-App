package com.example.weatherapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    EditText locationInput, tempUnitInput;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        locationInput = findViewById(R.id.locationInput);
        tempUnitInput = findViewById(R.id.tempUnitInput);
        saveButton = findViewById(R.id.saveButton);

        // Load saved preferences
        SharedPreferences prefs = getSharedPreferences("WeatherAppPrefs", MODE_PRIVATE);
        locationInput.setText(prefs.getString("location", ""));
        tempUnitInput.setText(prefs.getString("temp_unit", "C"));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = locationInput.getText().toString();
                String tempUnit = tempUnitInput.getText().toString();

                // Save to SharedPreferences
                SharedPreferences.Editor editor = getSharedPreferences("WeatherAppPrefs", MODE_PRIVATE).edit();
                editor.putString("location", location);
                editor.putString("temp_unit", tempUnit);
                editor.apply();

                // Finish the activity and return to MainActivity
                finish();
            }
        });
    }
}
