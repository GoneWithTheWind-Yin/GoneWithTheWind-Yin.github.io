package com.example.weatherapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.WeekWeatherAdapter;
import com.example.weatherapp.api.GetImage;
import com.example.weatherapp.data.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class Favorites extends Fragment {
    private GetImage getImage;
    private Context context;

    public Favorites() {
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
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_favorites, container, false);
        context = getActivity();

        Bundle args = getArguments();
        assert args != null;
        String data = args.getString("WeatherBundle");
        String city = args.getString("CityBundle");
        try {
            // TODO 要加入一个对于城市的处理
            TextView location = rootView.findViewById(R.id.location);
            location.setText(city);

            JSONObject weatherData = new JSONObject(data);
            JSONObject tempData = weatherData.getJSONObject("data");
            JSONArray timelines = tempData.getJSONArray("timelines");
            JSONArray intervals = timelines.getJSONObject(0).getJSONArray("intervals");
            JSONObject currentDayData = intervals.getJSONObject(0).getJSONObject("values");

            ImageView weatherIcon = rootView.findViewById(R.id.weatherIcon);
            weatherIcon.setImageResource(getImage.getIncon(currentDayData.getInt("weatherCode")));
            TextView temperature = rootView.findViewById(R.id.temperature);
            temperature.setText(currentDayData.getDouble("temperature") + "°F");
            TextView weatherDes = rootView.findViewById(R.id.weather_des);
            weatherDes.setText(getImage.getDes(currentDayData.getInt("weatherCode")));

            TextView humidity = rootView.findViewById(R.id.humidity_text);
            humidity.setText(currentDayData.getDouble("humidity") + "%");
            TextView windSpeed = rootView.findViewById(R.id.wind_text);
            windSpeed.setText(currentDayData.getDouble("windSpeed") + "mph");
            TextView visibility = rootView.findViewById(R.id.visibility_text);
            visibility.setText(currentDayData.getDouble("visibility") + "mi");
            TextView pressure = rootView.findViewById(R.id.preesure_text);
            pressure.setText(currentDayData.getDouble("pressureSeaLevel") + "inHg");

            LinkedList<JSONObject> list = new LinkedList<>();
            for (int i = 0; i < 7; ++i) {
                list.add(intervals.getJSONObject(i));
            }
            WeekWeatherAdapter weekWeatherAdapter = new WeekWeatherAdapter(list, context);
            ListView weekList = rootView.findViewById(R.id.week_list);
            weekList.setAdapter(weekWeatherAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }
}