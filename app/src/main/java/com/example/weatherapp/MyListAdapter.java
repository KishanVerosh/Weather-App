package com.example.weatherapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> dates;
    private final List<String> times;
    private final List<String> temperatures;
    private final List<Integer> imageIds;

    public MyListAdapter(Activity context, List<String> dates, List<String> times, List<String> temperatures, List<Integer> imageIds) {
        super(context, R.layout.activity_my_list_adapter, dates);
        this.context = context;
        this.dates = dates;
        this.times = times;
        this.temperatures = temperatures;
        this.imageIds = imageIds;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_my_list_adapter, null, true);

        ImageView imageView = rowView.findViewById(R.id.icon);
        TextView dateText = rowView.findViewById(R.id.txt_date);
        TextView timeText = rowView.findViewById(R.id.time);
        TextView tempText = rowView.findViewById(R.id.txt_temp);

        dateText.setText(dates.get(position));
        timeText.setText(times.get(position));
        tempText.setText(temperatures.get(position));
        imageView.setImageResource(imageIds.get(position));

        return rowView;
    }
}
