package com.example.weatherapp.data;

import org.json.JSONObject;

public class WeatherData {
    private String city;
    private JSONObject data;

    public WeatherData(String city, JSONObject data) {
        this.city = city;
        this.data = data;
    }

    public String  getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
