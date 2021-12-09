package com.example.weatherapp.api;

public class GetUrl {
    private final String ipUrl = "https://ipinfo.io/?token=0b676f0b07b1a9";
    private final String geoUrl = "https://weathersearch-1998.wl.r.appspot.com/get_location?city=";
    private final String weatherUrl = "https://weathersearch-1998.wl.r.appspot.com/search?timesteps=1d";
    private final String autoCompleteUrl = "https://weathersearch-1998.wl.r.appspot.com/autocomplete?city=";

//    mMSmnGIVw3Fr1WsrBUpy8k7wcIMqzbNg
//    XPIAROop3O9FnGayZBJxA5xxmb7BS2ix

    public String getIpUrl() {
        return ipUrl;
    }

    public String getGeoUrl() {
        return geoUrl;
    }

    public String getWeatherUrl() {
        return weatherUrl;
    }

    public String getAutoCompleteUrl() {
        return autoCompleteUrl;
    }
}
