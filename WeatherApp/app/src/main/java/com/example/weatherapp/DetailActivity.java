package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;

import com.example.weatherapp.adapters.DetailTabsAdapter;
import com.example.weatherapp.data.WeatherData;
import com.example.weatherapp.fragment.WeeklyDetail;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        try {
            String data = intent.getStringExtra("WeatherBundle");
            JSONObject dataObject = new JSONObject(data);
            String city = intent.getStringExtra("CityBundle");
            Objects.requireNonNull(getSupportActionBar()).setTitle(city);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            ViewPager viewPager = findViewById(R.id.detail_pager);
            PagerAdapter adapter = new DetailTabsAdapter(getSupportFragmentManager(), new WeatherData(city, dataObject));
            viewPager.setAdapter(adapter);

            TabLayout tabLayout = findViewById(R.id.detail_tab);
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){});
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.twitter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}