package com.example.weatherapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.DetailActivity;
import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.adapters.MainTabsAdapter;
import com.example.weatherapp.adapters.WeekWeatherAdapter;
import com.example.weatherapp.api.GetImage;
import com.example.weatherapp.data.WeatherData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Map;

public class Favorites extends Fragment {
    private GetImage getImage;
    private TabLayout tabLayout;
    private Context context;
    private ViewGroup rootView;

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
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_favorites, container, false);
        context = getActivity();

        Bundle args = getArguments();
        assert args != null;
        String data = args.getString("WeatherBundle");
        String city = args.getString("CityBundle");
        int color = args.getInt("Color");
        try {
            rootView.setBackgroundResource(color);

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

            final FloatingActionButton floatingActionButton = rootView.findViewById(R.id.add_favorite);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("cities", Context.MODE_PRIVATE);
            Map<String, ?> map = sharedPreferences.getAll();
            if (map.containsKey(city)) {
                floatingActionButton.setImageResource(R.mipmap.map_marker_minus);
            }
            boolean isFrontPage = args.getBoolean("IsFrontPage");
            if (isFrontPage) {
                floatingActionButton.setVisibility(View.GONE);
            }

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("cities", Context.MODE_PRIVATE);
                    Map<String, ?> map = sharedPreferences.getAll();
                    if (map.containsKey(city)) {
                        deleteFromFavorite();
                    } else {
                        addToFavorite();
                    }
                }
            });

            CardView overallInfo = rootView.findViewById(R.id.overall_info);
            overallInfo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("CityBundle", city);
                    intent.putExtra("WeatherBundle", data);
                    startActivity(intent);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public void addToFavorite() {
        Bundle args = getArguments();
        assert args != null;
        String data = args.getString("WeatherBundle");
        String city = args.getString("CityBundle");

        final FloatingActionButton floatingActionButton = rootView.findViewById(R.id.add_favorite);
        floatingActionButton.setImageResource(R.mipmap.map_marker_minus);
        Toast.makeText(getActivity(), city +" was added to favorites", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("cities", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(city, data);
        editor.apply();
        // TODO 要做删除后的view处理
    }

    public void deleteFromFavorite() {
        Bundle args = getArguments();
        assert args != null;
        String city = args.getString("CityBundle");

        final FloatingActionButton floatingActionButton = rootView.findViewById(R.id.add_favorite);
        floatingActionButton.setImageResource(R.mipmap.map_marker_plus);
        Toast.makeText(getActivity(), city +" was removed to favorites", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("cities", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(city);
        editor.apply();

        // TODO 要判断是不是mainPage，只有mainPage才刷新
        ViewPager viewPager = getActivity().findViewById(R.id.pager);
        MainTabsAdapter mainTabsAdapter = (MainTabsAdapter) viewPager.getAdapter();
        assert mainTabsAdapter != null;
        int pos = mainTabsAdapter.getCity(city);
        Log.d("Debug", "delete page is " + pos);
        MainActivity.deleteCity(pos + 1);
        mainTabsAdapter.deleteCity(pos);
        mainTabsAdapter.notifyDataSetChanged();
    }
}