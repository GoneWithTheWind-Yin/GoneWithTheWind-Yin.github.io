package com.example.weatherapp.api;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.weatherapp.R;
import com.example.weatherapp.fragment.Favorites;

public class GetWeatherDataBySearch extends GetWeatherData {
    private final ActionBar actionBar;

    public GetWeatherDataBySearch(ViewPager viewPager, RequestQueue requestQueue, FragmentManager fragmentManager, ActionBar actionBar) {
        super(viewPager, requestQueue, fragmentManager);
        this.actionBar = actionBar;
    }

    @Override
    public void requestWeatherData(String loc, String city) {
        String url = "https://api.tomorrow.io/v4/timelines?fields=temperature,temperatureApparent," +
                "temperatureMin,temperatureMax,windSpeed,windDirection,humidity,pressureSeaLevel," +
                "uvIndex,weatherCode,precipitationProbability,precipitationType,sunriseTime,sunsetTime," +
                "visibility,moonPhase,cloudCover&timesteps=1d&units=imperial&timezone=America/Los_Angeles" +
                "&location=" + loc + "&apikey=XPIAROop3O9FnGayZBJxA5xxmb7BS2ix";
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        actionBar.setTitle(city);
                        actionBar.setHomeButtonEnabled(true);
                        actionBar.setDisplayHomeAsUpEnabled(true);

                        Favorites favorite = new Favorites();
                        Bundle args = new Bundle();
                        args.putString("WeatherBundle", response.toString());
                        args.putString("CityBundle", city);
                        favorite.setArguments(args);
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.search_result, favorite);
                        fragmentTransaction.commit();

//                        TextView search = viewPager.findViewById(R.id.search_result_des);
//                        search.setText("Search Result");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });
        requestQueue.add(request);
    }
}
