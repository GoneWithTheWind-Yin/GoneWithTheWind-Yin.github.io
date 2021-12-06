package com.example.weatherapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.api.GetImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TodayDetail extends Fragment {
    private JSONObject weatherData;
    private GetImage getImage;

    public TodayDetail() {
        getImage = new GetImage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Bundle args = getArguments();
        assert args != null;
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_today_detail, container, false);
        try {
            String weatherString = args.getString("WeatherData");
            weatherData = new JSONObject(weatherString);
            JSONObject data = weatherData.getJSONObject("data");
            JSONArray timelines = data.getJSONArray("timelines");
            JSONArray intervals = timelines.getJSONObject(0).getJSONArray("intervals");
            JSONObject currentDayData = intervals.getJSONObject(0).getJSONObject("values");

            TextView windSpeed = rootView.findViewById(R.id.wind_detail);
            windSpeed.setText(currentDayData.getString("windSpeed") + "mph");
            TextView pressure = rootView.findViewById(R.id.pressure_detail);
            pressure.setText(currentDayData.getString("pressureSeaLevel") + "inHg");
            TextView Precipitation = rootView.findViewById(R.id.precipitation_detail);
            Precipitation.setText(currentDayData.getString("precipitationProbability") + "%");
            TextView temperature = rootView.findViewById(R.id.temperature_detail);
            temperature.setText(currentDayData.getInt("temperature") + "°F");
            TextView humidity = rootView.findViewById(R.id.humidity_detail);
            humidity.setText(currentDayData.getString("humidity") + "%");
            TextView visibility = rootView.findViewById(R.id.visibility_detail);
            visibility.setText(currentDayData.getString("visibility") + "mi");
            TextView cloudCover = rootView.findViewById(R.id.cloud_detail);
            cloudCover.setText(currentDayData.getString("cloudCover") + "%");
            TextView ozone = rootView.findViewById(R.id.ozone_detail);
            ozone.setText(currentDayData.getString("uvIndex"));

            ImageView weatherIcon = rootView.findViewById(R.id.weather_view);
            weatherIcon.setImageResource(getImage.getIncon(currentDayData.getInt("weatherCode")));
            TextView weather = rootView.findViewById(R.id.weather_detail);
            weather.setText(getImage.getDes(currentDayData.getInt("weatherCode")));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }
}