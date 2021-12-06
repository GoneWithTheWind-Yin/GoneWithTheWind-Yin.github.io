package com.example.weatherapp.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.weatherapp.data.WeatherData;
import com.example.weatherapp.fragment.SummaryDetail;
import com.example.weatherapp.fragment.TodayDetail;
import com.example.weatherapp.fragment.WeeklyDetail;

import org.json.JSONObject;

public class DetailTabsAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 3;
    private WeatherData weatherData;

    public DetailTabsAdapter(@NonNull FragmentManager fragmentManager, WeatherData weatherData) {
        super(fragmentManager);
        this.weatherData = weatherData;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return newTodayInstance();
        } else if (position == 1) {
            return newWeeklyInstance();
        } else {
            return newSummaryInstance();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    public Fragment newTodayInstance() {
        TodayDetail todayDetail = new TodayDetail();
        Bundle args = new Bundle();
        args.putString("WeatherData", weatherData.getData().toString());
        todayDetail.setArguments(args);
        return todayDetail;
    }

    public Fragment newWeeklyInstance() {
        WeeklyDetail weeklyDetail = new WeeklyDetail();
        Bundle args = new Bundle();
        args.putString("WeatherData", weatherData.getData().toString());
        weeklyDetail.setArguments(args);
        return weeklyDetail;
    }

    public Fragment newSummaryInstance() {
        SummaryDetail summaryDetail = new SummaryDetail();
        Bundle args = new Bundle();
        args.putString("WeatherData", weatherData.getData().toString());
        summaryDetail.setArguments(args);
        return summaryDetail;
    }
}
