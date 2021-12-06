package com.example.weatherapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.adapters.AutoCompleteAdapter;
import com.example.weatherapp.adapters.MainTabsAdapter;
import com.example.weatherapp.api.GetWeatherData;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO 设计有多个Tab的加载策略
public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private ViewPager mainTabs;
    private AutoCompleteAdapter autoCompleteAdapter;
    private FragmentManager mainFragmentManager;
    private GetWeatherData getWeatherData;
    private static TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getWeatherData.getAllWeatherData();
    }

    @Override
    protected void onRestart() {
        // TODO 添加一个刷新列表的操作 restart和start有区别 restart的时候只添加不重新查询
        getWeatherData.setMainPage();
        super.onRestart();
    }

    private void init() {
        mainFragmentManager = getSupportFragmentManager();

        requestQueue = Volley.newRequestQueue(this);

        mainTabs = findViewById(R.id.pager);

        SharedPreferences sharedPreferences = getSharedPreferences("cities", Context.MODE_PRIVATE);
        tabLayout = findViewById(R.id.tab_layout);
        getWeatherData = new GetWeatherData(mainTabs, requestQueue, mainFragmentManager, sharedPreferences, tabLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        final SearchView.SearchAutoComplete autoCompleteTextView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        autoCompleteTextView.setAdapter(autoCompleteAdapter);
        autoCompleteTextView.setDropDownBackgroundResource(R.color.colorWhite);
        autoCompleteTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        autoCompleteTextView.setText(autoCompleteAdapter.getObject(position));
                    }
                });
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener(){
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (newText.length() >= 1)
                            getAutoComplete(newText, autoCompleteTextView);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        if (query.length() > 0) {
                            Intent intent = new Intent(MainActivity.this, SearchableActivity.class);
                            intent.putExtra("CityBundle", query);
                            startActivity(intent);
                        }
                        return false;
                    }
                });

        return true;
    }

    public void getAutoComplete(String input, SearchView.SearchAutoComplete s) {
        String url = "https://weathersearch-1998.wl.r.appspot.com/autocomplete?city=" + input;
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    List<String> cityList = new ArrayList<>();
                    try {
                        JSONArray array = response.getJSONArray("predictions");
                        for (int i = 0; i < array.length(); ++i) {
                            JSONObject city = array.getJSONObject(i);
                            cityList.add(city.getString("description"));
                            autoCompleteAdapter = new AutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
                            autoCompleteAdapter.setData(cityList);
                            s.setAdapter(autoCompleteAdapter);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                });
        requestQueue.add(request);
    }

    public static void deleteCity(int pos) {
        int num = tabLayout.getTabCount();
        Log.d("Debug", "current tab size: " + num);
        for (int i = 0; i < num; ++i) {
            Log.d("Debug", "delete tab's info " + tabLayout.getTabAt(i).toString());
        }
        tabLayout.removeTabAt(pos);
    }
}
