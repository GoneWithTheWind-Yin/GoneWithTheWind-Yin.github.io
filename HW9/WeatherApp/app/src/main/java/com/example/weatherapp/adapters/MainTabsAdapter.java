package com.example.weatherapp.adapters;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.data.WeatherData;
import com.example.weatherapp.fragment.Favorites;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class MainTabsAdapter extends FragmentStatePagerAdapter {
    // TODO 后续改为一个list方便进行add和remove的操作
    private ArrayList<WeatherData> favoriteCities;
    private WeatherData currentLocData;
//    private ArrayList<Favorites> fragmentList;

    public MainTabsAdapter(FragmentManager manager, WeatherData currentLocData, ArrayList<WeatherData> favoriteCities) {
        // TODO 支持传入参数
        super(manager);
        this.currentLocData = currentLocData;
        this.favoriteCities = favoriteCities;
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
            args.putString("WeatherBundle", currentLocData.getData().toString());
            args.putString("CityBundle", currentLocData.getCity());
            args.putInt("Color", R.color.gray);
            args.putBoolean("IsFrontPage", true);
            args.putBoolean("IsMain", true);
            favorite.setArguments(args);
            return favorite;
        } else {
            // 处理list里面的数据
            Log.d("Debug", "current tab position: " + position + ", current tab name: " + favoriteCities.get(position - 1).getCity());
            Favorites favorite = new Favorites();
            Bundle args = new Bundle();
            args.putString("WeatherBundle", favoriteCities.get(position - 1).getData().toString());
            args.putString("CityBundle", favoriteCities.get(position - 1).getCity());
            args.putInt("Color", R.color.gray);
            args.putBoolean("IsFrontPage", false);
            favorite.setArguments(args);
            args.putBoolean("IsMain", true);
            return favorite;
        }
    }

    @Override
    public int getCount() {
        return favoriteCities.size() + 1;
    }

    public void deleteCity(ViewGroup context, int pos) {
        if (!favoriteCities.isEmpty() && favoriteCities.size() >= pos) {
            favoriteCities.remove(pos);
        }
    }

    public int getCity(String city) {
        for (int i = 0; i < favoriteCities.size(); ++i) {
            if (favoriteCities.get(i).getCity().equals(city)) {
                return i;
            }
        }
        return 0;
    }
}
