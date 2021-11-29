package com.example.weatherapp.api;

public class GetUrl {
    private final String ipUrl = "https://ipinfo.io/?token=0b676f0b07b1a9";
    private final String geoUrl = "https://maps.googleapis.com/maps/api/geocode/json?&key=AIzaSyAd9Qbqgx8fyM2WufIIkdlRcBt8mDrtdoM&language=en_US";
    private final String weatherUrl = "https://api.tomorrow.io/v4/timelines?fields=temperature,temperatureApparent," +
            "temperatureMin,temperatureMax,windSpeed,windDirection,humidity,pressureSeaLevel,uvIndex,weatherCode," +
            "precipitationProbability,precipitationType,sunriseTime,sunsetTime,visibility,moonPhase," +
            "cloudCover&timesteps=1d&units=imperial&timezone=America/Los_Angeles&apikey=mMSmnGIVw3Fr1WsrBUpy8k7wcIMqzbNg";

    public String getIpUrl() {
        return ipUrl;
    }

    public String getGeoUrl() {
        return geoUrl;
    }

    public String getWeatherUrl() {
        return weatherUrl;
    }
}
