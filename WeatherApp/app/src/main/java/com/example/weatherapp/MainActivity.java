package com.example.weatherapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.adapters.MainTabsAdapter;
import com.example.weatherapp.fragment.Favorites;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// TODO 设计有多个Tab的加载策略
public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private ViewPager mainTabs;
    private ArrayList<JSONObject> favorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getWeatherDataByIP();
    }

    public void init() {
        requestQueue = Volley.newRequestQueue(this);
        mainTabs = findViewById(R.id.pager);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mainTabs);
    }

    public void getWeatherDataByIP() {
        // 后续可以改为直接获取地理位置
        String url = "https://ipinfo.io/?token=0b676f0b07b1a9";
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        requestWeatherData(response.getString("loc"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });
        requestQueue.add(request);
    }

    public void getWeatherDataByCity(String city, String state) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?" +
                "&key=AIzaSyAd9Qbqgx8fyM2WufIIkdlRcBt8mDrtdoM&language=en_US&address=" + city + ", " + state;
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        JSONObject geometry = results.getJSONObject(0).getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");
                        requestWeatherData(location.getString("lat") + "," + location.getString("lng"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });
        requestQueue.add(request);
    }

    public void requestWeatherData(String loc) {
        String url = "https://api.tomorrow.io/v4/timelines?fields=temperature,temperatureApparent," +
                "temperatureMin,temperatureMax,windSpeed,windDirection,humidity,pressureSeaLevel," +
                "uvIndex,weatherCode,precipitationProbability,precipitationType,sunriseTime,sunsetTime," +
                "visibility,moonPhase,cloudCover&timesteps=1d&units=imperial&timezone=America/Los_Angeles" +
                "&location=" + loc + "&apikey=XPIAROop3O9FnGayZBJxA5xxmb7BS2ix";
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        MainTabsAdapter adapter = new MainTabsAdapter(getSupportFragmentManager(), response, favorList);
                        mainTabs.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });
        requestQueue.add(request);
    }
}
