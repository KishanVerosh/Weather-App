package com.example.weatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    MyListAdapter adapter;
    List<String> dates = new ArrayList<>();
    List<String> times = new ArrayList<>();
    List<String> temperatures = new ArrayList<>();
    List<String> humidities = new ArrayList<>();
    List<Integer> imageIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Weather Forecast");

        listView = findViewById(R.id.list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Create an Intent to start the DetailActivity
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("date", dates.get(position));
                intent.putExtra("time", times.get(position));
                intent.putExtra("temperature", temperatures.get(position));
                intent.putExtra("humidity", humidities.get(position));
                intent.putExtra("image", imageIds.get(position));
                startActivity(intent);
            }
        });

        // Load weather data initially
        loadWeatherData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload weather data when returning from Settings activity
        loadWeatherData();
    }

    private void loadWeatherData() {
        // Clear existing data
        dates.clear();
        times.clear();
        temperatures.clear();
        humidities.clear();
        imageIds.clear();

        // Fetch new data
        new FetchWeatherDataTask().execute();
    }

    private class FetchWeatherDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            SharedPreferences prefs = getSharedPreferences("WeatherAppPrefs", MODE_PRIVATE);
            String location = prefs.getString("location", "Colombo, LK"); // default to Colombo
            String tempUnit = prefs.getString("temp_unit", "metric"); // default to metric
            String unitParam = tempUnit.equalsIgnoreCase("F") ? "imperial" : "metric";

            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=fbfb6af52eacb73f2bf9f6daf897bd3e&units=" + unitParam);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                SharedPreferences prefs = getSharedPreferences("WeatherAppPrefs", MODE_PRIVATE);
                String tempUnit = prefs.getString("temp_unit", "metric"); // default to metric

                JSONObject jsonObject = new JSONObject(result);
                JSONArray listArray = jsonObject.getJSONArray("list");

                for (int i = 0; i < listArray.length(); i++) {
                    JSONObject listItem = listArray.getJSONObject(i);
                    String dateTime = listItem.getString("dt_txt");
                    String[] dateTimeParts = dateTime.split(" ");
                    String date = dateTimeParts[0];
                    String time = dateTimeParts[1];

                    String temp = listItem.getJSONObject("main").getString("temp") + (tempUnit.equalsIgnoreCase("F") ? " °F" : " °C");
                    String humidity = listItem.getJSONObject("main").getString("humidity") + " %";
                    String icon = listItem.getJSONArray("weather").getJSONObject(0).getString("icon");

                    dates.add(date);
                    times.add(time);
                    temperatures.add(temp);
                    humidities.add(humidity);
                    imageIds.add(getIconResourceId(icon));
                }

                adapter = new MyListAdapter(MainActivity.this, dates, times, temperatures, imageIds);
                listView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private int getIconResourceId(String iconName) {
        switch (iconName) {
            case "01d":
                return R.drawable.icon_01d;
            case "01n":
                return R.drawable.icon_01n;
            case "02d":
                return R.drawable.icon_02d;
            case "02n":
                return R.drawable.icon_02n;
            case "03d":
                return R.drawable.icon_03d;
            case "03n":
                return R.drawable.icon_03n;
            case "04d":
                return R.drawable.icon_04d;
            case "04n":
                return R.drawable.icon_04n;
            case "09d":
                return R.drawable.icon_09d;
            case "09n":
                return R.drawable.icon_09n;
            case "10d":
                return R.drawable.icon_10d;
            case "10n":
                return R.drawable.icon_10n;
            case "11d":
                return R.drawable.icon_11d;
            case "11n":
                return R.drawable.icon_11n;
            case "13d":
                return R.drawable.icon_13d;
            case "13n":
                return R.drawable.icon_13n;
            default:
                return R.drawable.icon_default;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
