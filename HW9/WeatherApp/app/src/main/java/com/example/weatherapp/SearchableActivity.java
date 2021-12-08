package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.api.GetWeatherDataBySearch;
import com.example.weatherapp.fragment.Favorites;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class SearchableActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private GetWeatherDataBySearch getWeatherDataBySearch;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ActionBarTheme);
        setContentView(R.layout.activity_searchable);
        init();
        Intent intent = getIntent();
        String city = intent.getStringExtra("CityBundle");
        SharedPreferences sharedPreferences = getSharedPreferences("cities", Context.MODE_PRIVATE);
        getWeatherDataBySearch = new GetWeatherDataBySearch(null, requestQueue,
                getSupportFragmentManager(), sharedPreferences, getSupportActionBar(), this);
        getWeatherDataBySearch.getWeatherDataByCity(city);
    }

    private void init() {
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // 解决如何返回的时候不显示progress bar
        finish();
        return super.onSupportNavigateUp();
    }
}