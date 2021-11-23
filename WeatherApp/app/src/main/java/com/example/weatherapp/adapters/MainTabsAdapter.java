package com.example.weatherapp.adapters;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.weatherapp.fragment.Favorites;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainTabsAdapter extends FragmentPagerAdapter {
    // TODO 后续改为一个list方便进行add和remove的操作
    private ArrayList<JSONObject> favoriteCities;
    private JSONObject currentLocData;
    static int NUM_PAGES = 1;

    public MainTabsAdapter(FragmentManager manager, JSONObject currentLocData, ArrayList<JSONObject> favoriteCities) {
        // TODO 支持传入参数
        super(manager);
        this.currentLocData = currentLocData;
        this.favoriteCities = favoriteCities;
        NUM_PAGES = favoriteCities.size() + 1;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return newInstance(position);
    }

    public Fragment newInstance(int position) {
        if (position == 0) {
            // 0的时候处理当前位置的数据
            Favorites favorite = new Favorites();
            Bundle args = new Bundle();
            args.putString("WeatherBundle", currentLocData.toString());
            favorite.setArguments(args);
            return favorite;
        } else {
            // 处理list里面的数据
            Favorites favorite = new Favorites();
            Bundle args = new Bundle();
            args.putString("WeatherBundle", favoriteCities.get(position - 1).toString());
            favorite.setArguments(args);
            return favorite;
        }
    }



    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
