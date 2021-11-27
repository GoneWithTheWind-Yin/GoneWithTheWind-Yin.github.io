package com.example.weatherapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.api.GetImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class WeekWeatherAdapter extends BaseAdapter {
    private LinkedList<JSONObject> weeklyData;
    private Context context;
    private final int SIZE_LIST = 7;
    private GetImage getImage = new GetImage();

    public WeekWeatherAdapter(LinkedList<JSONObject> weeklyData, Context context) {
        this.weeklyData = weeklyData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return SIZE_LIST;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        try {
            JSONObject value = weeklyData.get(position).getJSONObject("values");
            view = LayoutInflater.from(context).inflate(R.layout.week_list, parent, false);

            TextView date = view.findViewById(R.id.date);
            date.setText(weeklyData.get(position).getString("startTime").substring(0, 10));

            ImageView forecastIcon = view.findViewById(R.id.forecast_icon);
            forecastIcon.setImageResource(getImage.getIncon(value.getInt("weatherCode")));
            TextView lowTemp = view.findViewById(R.id.low_temperature);
            lowTemp.setText("" + value.getInt("temperatureMin"));
            TextView highTemp = view.findViewById(R.id.high_temperature);
            highTemp.setText("" + value.getInt("temperatureMax"));
            return view;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
