package com.example.weatherapp.api;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.weatherapp.adapters.MainTabsAdapter;
import com.example.weatherapp.data.WeatherData;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GetWeatherData {
    protected final ViewPager viewPager;
    protected final RequestQueue requestQueue;
    protected final FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;
    protected ArrayList<WeatherData> favoriteList = new ArrayList<>();
    protected final GetUrl getUrl;
    protected int listSize = 1;
    protected WeatherData currentLocData;
    protected AtomicInteger num = new AtomicInteger();
    protected TabLayout tabLayout;

    public GetWeatherData(ViewPager viewPager, RequestQueue requestQueue, FragmentManager fragmentManager, SharedPreferences sharedPreferences, TabLayout tabLayout) {
        this.viewPager = viewPager;
        this.requestQueue = requestQueue;
        this.fragmentManager = fragmentManager;
        this.sharedPreferences = sharedPreferences;
        this.tabLayout = tabLayout;
        this.getUrl = new GetUrl();
    }

    public void getAllWeatherData() {
        Map<String, ?> map = sharedPreferences.getAll();
        listSize = map.size() + 1;
        num = new AtomicInteger();
        Log.d("Debug", "current list size: " + listSize);
        getWeatherDataByIP(fragmentManager);
        for (String key: map.keySet()) {
            Log.d("Debug", "current city name: " + key);
            getWeatherDataByCity(key);
        }
    }

    public void getWeatherDataByIP(FragmentManager fragmentManager) {
        // 后续可以改为直接获取地理位置
        String url = getUrl.getIpUrl();
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        String city = response.getString("city") + ", " + response.getString("region");
                        requestWeatherData(response.getString("loc"), city, true);
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
                        String formattedAddress = results.getJSONObject(0).getString("formatted_address");
                        JSONObject location = geometry.getJSONObject("location");
                        requestWeatherData(location.getString("lat") + "," + location.getString("lng"), formattedAddress, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });
        requestQueue.add(request);
    }

    public void requestWeatherData(String loc, String city, boolean isFrontPage) {
        String url = getUrl.getWeatherUrl() + "&location=" + loc;
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        if (isFrontPage) {
                            currentLocData = new WeatherData(city, response);
                            num.addAndGet(1);
                        } else {
                            // TODO 后续可能要对于list的顺序做一个处理
                            favoriteList.add(new WeatherData(city, response));
                            num.addAndGet(1);
                        }
                        Log.d("Debug", "current finished request number: " + num.get());
                        if (num.get() == listSize) {
                            setMainPage();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });
        requestQueue.add(request);
    }

    public void setMainPage() {
        favoriteList = new ArrayList<>();
        Map<String, ?> map = sharedPreferences.getAll();
        for (String key: map.keySet()) {
            String data = sharedPreferences.getString(key, "");
            try {
                favoriteList.add(new WeatherData(key, new JSONObject(data)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        MainTabsAdapter adapter = new MainTabsAdapter(fragmentManager, currentLocData, favoriteList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
