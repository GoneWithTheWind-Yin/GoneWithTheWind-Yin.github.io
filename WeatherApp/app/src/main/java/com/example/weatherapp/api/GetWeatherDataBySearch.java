package com.example.weatherapp.api;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
    private final AppCompatActivity appCompatActivity;

    public GetWeatherDataBySearch(ViewPager viewPager, RequestQueue requestQueue, FragmentManager fragmentManager,
                                  SharedPreferences sharedPreferences,
                                  ActionBar actionBar, AppCompatActivity appCompatActivity) {
        super(viewPager, requestQueue, fragmentManager, sharedPreferences, null);
        this.actionBar = actionBar;
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public void requestWeatherData(String loc, String city, boolean isFrontPage) {
        String url = getUrl.getWeatherUrl() + "&location=" + loc;
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
                        args.putInt("Color", R.color.black);
                        favorite.setArguments(args);
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.search_result, favorite);
                        fragmentTransaction.commit();

                        TextView search = appCompatActivity.findViewById(R.id.search_result_des);
                        search.setText("Search Result");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });
        requestQueue.add(request);
    }
}
