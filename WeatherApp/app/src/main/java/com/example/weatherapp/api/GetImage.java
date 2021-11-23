package com.example.weatherapp.api;

import com.example.weatherapp.R;

import java.util.HashMap;

public class GetImage {
    private HashMap<Integer, String> weatherDict = new HashMap<Integer, String>() {
        {
            put(1000, "Clear");
            put(1100, "Mostly Clear");
            put(1101, "Partly Cloudy");
            put(1102, "Mostly Cloudy");
            put(1001, "Cloudy");
            put(2000, "Fog");
            put(2100, "Light Fog");
            put(8000, "Thunderstorm");
            put(5001, "Flurries");
            put(5100, "Light Snow");
            put(5000, "Snow");
            put(5101, "Heavy Snow");
            put(7102, "Light Ice Pellets");
            put(7000, "Ice Pellets");
            put(7101, "Heavy Ice Pellets");
            put(4000, "Drizzle");
            put(6000, "Freezing Drizzle");
            put(6200, "Light Freezing Rain");
            put(6001, "Freezing Rain");
            put(6201, "Heavy Freezing Rain");
            put(4200, "Light Rain");
            put(4001, "Rain");
            put(4201, "Heavy Rain");
            put(3000, "Light Wind");
            put(3001, "Wind");
            put(3002, "Strong Wind");
        }
    };

    private HashMap<Integer, Integer> weatherIconDict = new HashMap<Integer, Integer>() {
        {
            put(1000, R.drawable.ic_clear_day);
            put(1100, R.drawable.ic_mostly_clear_day);
            put(1101, R.drawable.ic_partly_cloudy_day);
            put(1102, R.drawable.ic_mostly_cloudy);
            put(1001, R.drawable.ic_cloudy);
            put(2000, R.drawable.ic_fog);
            put(2100, R.drawable.ic_fog_light);
            put(8000, R.drawable.ic_tstorm);
            put(5001, R.drawable.ic_flurries);
            put(5100, R.drawable.ic_snow_light);
            put(5000, R.drawable.ic_snow);
            put(5101, R.drawable.ic_snow_heavy);
            put(7102, R.drawable.ic_ice_pellets_light);
            put(7000, R.drawable.ic_ice_pellets);
            put(7101, R.drawable.ic_ice_pellets_heavy);
            put(4000, R.drawable.ic_drizzle);
            put(6000, R.drawable.ic_freezing_drizzle);
            put(6200, R.drawable.ic_freezing_rain_light);
            put(6001, R.drawable.ic_freezing_rain);
            put(6201, R.drawable.ic_freezing_rain_heavy);
            put(4200, R.drawable.ic_freezing_rain_light);
            put(4001, R.drawable.ic_rain);
            put(4201, R.drawable.ic_rain_heavy);
        }
    };

    public int getIncon(int code) {
        return weatherIconDict.get(code);
    }

    public String getDes(int code) {
        return weatherDict.get(code);
    }

}
