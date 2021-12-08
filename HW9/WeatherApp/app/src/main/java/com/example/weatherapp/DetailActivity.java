package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.weatherapp.adapters.DetailTabsAdapter;
import com.example.weatherapp.data.WeatherData;
import com.example.weatherapp.fragment.WeeklyDetail;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    private String city = "";
    private String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        try {
            data = intent.getStringExtra("WeatherBundle");
            JSONObject dataObject = new JSONObject(data);
            city = intent.getStringExtra("CityBundle");
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Log.d("Debug", "press menu button");
       switch (menuItem.getItemId()){
           case R.id.twitter_button:
               try {
                   Log.d("Debug", "press twitter button");
                   JSONObject dataObject = new JSONObject(data).getJSONObject("data");
                   JSONArray timelines = dataObject.getJSONArray("timelines");
                   JSONArray intervals = timelines.getJSONObject(0).getJSONArray("intervals");
                   JSONObject currentDayData = intervals.getJSONObject(0).getJSONObject("values");
                   String url = "https://twitter.com/intent/tweet?text=";
                   url +="Check Out "+ city +"'s Weather! It is " + currentDayData.getDouble("temperature") + "°F! &hashtags=CSCI571WeatherSearch";
                   Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                   startActivity(intent);
               } catch (JSONException e) {
                   Log.d("Debug", "error");
                   e.printStackTrace();
               }
               return super.onOptionsItemSelected(menuItem);
           case android.R.id.home:
               finish();
               return super.onOptionsItemSelected(menuItem);
           default:
               return super.onOptionsItemSelected(menuItem);
        }
    }
}