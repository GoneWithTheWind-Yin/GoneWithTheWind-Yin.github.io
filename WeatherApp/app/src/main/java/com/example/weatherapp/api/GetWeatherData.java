package com.example.weatherapp.api;

import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.weatherapp.R;
import com.example.weatherapp.adapters.MainTabsAdapter;
import com.example.weatherapp.data.WeatherData;
import com.example.weatherapp.fragment.Favorites;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetWeatherData {
    protected final ViewPager viewPager;
    protected final RequestQueue requestQueue;
    protected final FragmentManager fragmentManager;
    protected ArrayList<WeatherData> favoriteList = new ArrayList<>();
    protected final GetUrl getUrl;

    public GetWeatherData(ViewPager viewPager, RequestQueue requestQueue, FragmentManager fragmentManager) {
        this.viewPager = viewPager;
        this.requestQueue = requestQueue;
        this.fragmentManager = fragmentManager;
        this.getUrl = new GetUrl();

    }

    public void getWeatherDataByIP(FragmentManager fragmentManager) {
        // 后续可以改为直接获取地理位置
        String url = getUrl.getIpUrl();
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        String city = response.getString("city") + response.getString("region");
                        requestWeatherData(response.getString("loc"), city);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });
        requestQueue.add(request);
    }

    public void getWeatherDataByCity(String city) {
        String url = getUrl.getGeoUrl() + "&address=" + city;
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        JSONObject geometry = results.getJSONObject(0).getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");
                        requestWeatherData(location.getString("lat") + "," + location.getString("lng"), city);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });
        requestQueue.add(request);
    }

    public void requestWeatherData(String loc, String city) {
        String url = getUrl.getWeatherUrl() + "&location=" + loc;
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        // TODO 要处理favoriteList
                        WeatherData currentLocData = new WeatherData(city, response);
                        MainTabsAdapter adapter = new MainTabsAdapter(fragmentManager, currentLocData, favoriteList);
                        viewPager.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });
        requestQueue.add(request);
    }
}
