package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
        setContentView(R.layout.activity_searchable);
        init();
        Intent intent = getIntent();
        String city = intent.getStringExtra("CityBundle");
        getWeatherDataBySearch = new GetWeatherDataBySearch(findViewById(R.layout.activity_searchable), requestQueue, getSupportFragmentManager(), getSupportActionBar());
        getWeatherDataBySearch.getWeatherDataByCity(city);
    }

    private void init() {
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}