import {ChangeDetectorRef, EventEmitter, Injectable, Output} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {animate, state, style, transition, trigger} from "@angular/animations";

declare var google: any;

@Injectable({
    providedIn: 'root',
})

export class ServiceService {

    public loadStatus: any = false;
    public address: any = "Los Angles, California";
    public dailyWeatherData: any = "";
    public hourlyWeatherData: any = "";
    public isError: any = false;
    public detailID: any = 0;
    public isDetail: any = false;
    public active: any = 0;
    public lat: any = 30;
    public lng: any = 110;
    public map: any;
    public marker: any;
    public city: any;
    public state: any;

    @Output() slide = new EventEmitter<string>();

    public getWeatherDataByIP(period: any) {
        var ipInfoUrl = "https://ipinfo.io/?token=0b676f0b07b1a9"
        this.http.get(ipInfoUrl).subscribe(data => {
            // @ts-ignore
            console.log(data["loc"]);
            // @ts-ignore
            this.address = data["city"] + ", " + data["region"];
            // @ts-ignore
            this.city = data["city"];
            // @ts-ignore
            this.state = data["region"]
            // @ts-ignore
            var arr = data["loc"].split(',');
            this.lat = arr[0];
            this.lng = arr[1];
            // @ts-ignore
            this.getWeatherData(data["loc"], period);
        });
    }

    public getWeatherDataByLoc(street: any, city: any, state: any, period: any) {
        var geocodingUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="
        geocodingUrl += street + "," + city + "," + state + "" + "&key=AIzaSyAd9Qbqgx8fyM2WufIIkdlRcBt8mDrtdoM&language=en_US"
        this.http.get(geocodingUrl).subscribe(data => {
            // @ts-ignore
            if (data["results"][0] === undefined) {
                // 做无数据展示处理
            } else {
                // @ts-ignore
                this.lat = data["results"][0]["geometry"]["location"]["lat"];
                // @ts-ignore
                this.lng = data["results"][0]["geometry"]["location"]["lng"];
                var location = this.lat + "," + this.lng;
                this.address = city + ", " + state;
                this.city = city;
                this.state = state;
                this.getWeatherData(location, period);
            }
        });
    }

    public getWeatherData(loc: any, period: any) {
        var URL = "http://weathersearch-1998.wl.r.appspot.com/search?" + "location=" + loc + "&timesteps=" + period;
        this.http.get(URL).subscribe(data => {
            if (period == "1d") {
                this.dailyWeatherData = data;
            } else {
                this.hourlyWeatherData = data;
            }
        });
    }

    checkStatus() {
        if (this.dailyWeatherData === "" || this.hourlyWeatherData === "") {
            return false;
        }
        if (this.dailyWeatherData["data"] == undefined || this.hourlyWeatherData["data"] == undefined) {
            this.loadStatus = false;
            this.isError = true;
            return false;
        }
        this.loadStatus = false;
        return true;
    }

    showDetail(i: any) {
        this.active++;
        // console.log(this.active);
        this.detailID = i;
        this.isDetail = true;
        setTimeout(() => {
            this.initMap();
        }, 500)
    }

    returnList() {
        this.active--;
        // console.log(this.active);
        this.detailID = 0;
        this.isDetail = false;
    }

    initMap(): void {
        // @ts-ignore
        let uluru = {lat: parseFloat(this.lat), lng: parseFloat(this.lng)};
        this.map = new google.maps.Map(
            document.getElementById("map") as HTMLElement,
            {
                zoom: 16,
                center: uluru,
            }
        );
        this.marker = new google.maps.Marker({
            position: uluru,
            map: this.map,
        });
        // this.marker.setMap(this.map);
    }

    weatherDict: any = {
        1000: "clear_day.svg",
        1100: "mostly_clear_day.svg",
        1101: "partly_cloudy_day.svg",
        1102: "mostly_cloudy.svg",
        1001: "cloudy.svg",
        2000: "fog.svg",
        2001: "fog_light.svg",
        8000: "tstorm.svg",
        5001: "flurries.svg",
        5100: "snow_light.svg",
        5000: "snow.svg",
        5101: "snow_heavy.svg",
        7102: "ice_pellets_light.svg",
        7000: "ice_pellets.svg",
        7101: "ice_pellets_heavy.svg",
        4000: "drizzle.svg",
        6000: "freezing_drizzle.svg",
        6200: "freezing_rain_light.svg",
        6001: "freezing_rain.svg",
        6201: "freezing_rain_heavy.svg",
        4200: "rain_light.svg",
        4001: "rain.svg",
        4201: "rain_heavy.svg",
        3000: "wind_light.svg",
        3001: "wind.svg",
        3002: "wind_strong.svg"
    }
    weatherDict2: any = {
        1000: "Clear",
        1100: "Mostly Clear",
        1101: "Partly Cloudy",
        1102: "Mostly Cloudy",
        1001: "Cloudy",
        2000: "Fog",
        2001: "Light Fog",
        8000: "Thunderstorm",
        5001: "Flurries",
        5100: "Light Snow",
        5000: "Snow",
        5101: "Heavy Snow",
        7102: "Light Ice Pellets",
        7000: "Ice Pellets",
        7101: "Heavy Ice Pellets",
        4000: "Drizzle",
        6000: "Freezing Drizzle",
        6200: "Light Freezing Rain",
        6001: "Freezing Rain",
        6201: "Heavy Freezing Rain",
        4200: "Light Rain",
        4001: "Rain",
        4201: "Heavy Rain",
        3000: "Light Wind",
        3001: "Wind",
        3002: "Strong Wind",
    }

    stateDict: any = {
        "AL": "Alabama",
        "AK": "Alaska",
        "AZ": "Arizona",
        "AR": "Arkansas",
        "CA": "California",
        "CO": "Colorado",
        "CT": "Connecticut",
        "DE": "Delaware",
        "DC": "District Of Columbia",
        "FL": "Florida",
        "GA": "Georgia",
        "HI": "Hawaii",
        "ID": "Idaho",
        "IL": "Illinois",
        "IN": "Indiana",
        "IA": "Iowa",
        "KS": "Kansas",
        "KY": "Kentucky",
        "LA": "Louisiana",
        "ME": "Maine",
        "MD": "Maryland",
        "MA": "Massachusetts",
        "MI": "Michigan",
        "MN": "Minnesota",
        "MS": "Mississippi",
        "MO": "Missouri",
        "MT": "Montana",
        "NE": "Nebraska",
        "NV": "Nevada",
        "NH": "New Hampshire",
        "NJ": "New Jersey",
        "NM": "New Mexico",
        "NY": "New York",
        "NC": "North Carolina",
        "ND": "North Dakota",
        "OH": "Ohio",
        "OK": "Oklahoma",
        "OR": "Oregon",
        "PA": "Pennsylvania",
        "RI": "Rhode Island",
        "SC": "South Carolina",
        "SD": "South Dakota",
        "TN": "Tennessee",
        "TX": "Texas",
        "UT": "Utah",
        "VT": "Vermont",
        "VA": "Virginia",
        "WA": "Washington",
        "WV": "West Virginia",
        "WI": "Wisconsin",
        "WY": "Wyoming"
    }

    weekDict: any = {
        "Sun": "Sunday",
        "Mon": "Monday",
        "Tue": "Tuesday",
        "Wed": "Wednesday",
        "Thu": "Thursday",
        "Fri": "Friday",
        "Sat": "Saturday"
    }

    getDate(i: any) {
        var date = new Date(this.dailyWeatherData['data']['timelines'][0]['intervals'][i]["startTime"]).toDateString();
        return this.weekDict[date.substr(0, 3)] + "," + date.substr(3);
    }

    getStatus(i: any) {
        return this.weatherDict2[this.dailyWeatherData['data']['timelines'][0]['intervals'][i]["values"]["weatherCode"]];
    }

    getImage(i: any) {
        return "../../assets/images/" + this.weatherDict[this.dailyWeatherData['data']['timelines'][0]['intervals'][i]["values"]["weatherCode"]];
    }

    getTempHigh(i: any) {
        return this.dailyWeatherData['data']['timelines'][0]['intervals'][i]["values"]["temperatureMax"];
    }

    getTempLow(i: any) {
        return this.dailyWeatherData['data']['timelines'][0]['intervals'][i]["values"]["temperatureMin"];
    }

    getTemp(i: any) {
        return this.dailyWeatherData['data']['timelines'][0]['intervals'][i]["values"]["temperatureApparent"];
    }

    getSunriseTime(i: any) {
        var date = new Date(this.dailyWeatherData['data']['timelines'][0]['intervals'][i]["values"]["sunriseTime"]);
        return date.toTimeString().substr(0, 8)
        // return date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    }

    getSunsetTime(i: any) {
        var date = new Date(this.dailyWeatherData['data']['timelines'][0]['intervals'][i]["values"]["sunsetTime"]);
        return date.toTimeString().substr(0, 8)
        // return date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    }

    getHumidity(i: any) {
        return this.dailyWeatherData['data']['timelines'][0]['intervals'][i]["values"]["humidity"];
    }

    getVisibility(i: any) {
        return this.dailyWeatherData['data']['timelines'][0]['intervals'][i]["values"]["visibility"];
    }

    getCloudCover(i: any) {
        return this.dailyWeatherData['data']['timelines'][0]['intervals'][i]["values"]["cloudCover"];
    }

    getWindSpeed(i: any) {
        return this.dailyWeatherData['data']['timelines'][0]['intervals'][i]["values"]["windSpeed"];
    }

    // favoriteList: any = [];
    favoriteMap = new Map();

    checkFavorite() {
        return this.favoriteMap.get(this.address) != undefined;
        // return this.favoriteList.count({"city": this.city, "state": this.state})
    }

    appendFavorite() {
        // this.favoriteList.push({"city": this.city, "state": this.state});
        if (this.favoriteMap.get(this.address) != undefined) {
            this.removeFavorite(this.address);
            return;
        }
        this.favoriteMap.set(this.address, {"city": this.city, "state": this.state});
    }

    removeFavorite(key: any) {
        this.favoriteMap.delete(key);
    }

    searchWeather(key: any) {
        this.clearForm();
        this.searchForm.city = this.favoriteMap.get(key).city;
        this.searchForm.state = this.favoriteMap.get(key).state;
        this.getResult();
        this.submitForm();
    }

    public searchForm: any = {
        street: "",
        city: "",
        state: "Select Your State",
        currentLocation: false,
    }

    checkForm() {
        if (this.searchForm.street == null || this.searchForm.street == "" || this.searchForm.street.trim() == "") {
            return true;
        }
        if (this.searchForm.city == null || this.searchForm.city == "" || this.searchForm.city.trim() == "") {
            return true;
        }
        if (this.searchForm.state == "Select Your State") {
            return true;
        }
        return false;
    }

    submitForm() {
        console.log(this.searchForm);
        this.loadStatus = true;
        if (this.searchForm.currentLocation) {
            this.getWeatherDataByIP("1d");
            this.getWeatherDataByIP("1h");
        } else {
            this.getWeatherDataByLoc(this.searchForm.street, this.searchForm.city, this.searchForm.state, "1d");
            this.getWeatherDataByLoc(this.searchForm.street, this.searchForm.city, this.searchForm.state, "1h");
            // console.log(this.service.dailyWeatherData);
        }
    }

    clearForm() {
        this.searchForm.street = "";
        this.searchForm.city = "";
        this.searchForm.state = "Select Your State";
        this.searchForm.currentLocation = false;
        this.dailyWeatherData = "";
        this.hourlyWeatherData = "";
        this.loadStatus = false;
        this.getResult();
    }

    resultClass = "btn btn-primary";
    favoriteClass = "btn btn-default";
    isResult = true;
    isFavorite = false;

    getResult() {
        this.resultClass = "btn btn-primary";
        this.favoriteClass = "btn btn-default";
        this.isResult = true;
        this.isFavorite = false;
    }

    getFavorite() {
        this.resultClass = "btn btn-default";
        this.favoriteClass = "btn btn-primary";
        this.isResult = false;
        this.isFavorite = true;
    }

    postTwitter() {
        var text = "The temperature in " + this.city + ", " + this.state + " on " + this.getDate(this.detailID) + " is "
            + this.getTemp(this.detailID) + "°F. The weather conditions are " + this.getStatus(this.detailID) + "&hashtags=CSCI571WeatherSearch";
        var url = "https://twitter.com/intent/tweet?text=" + text;
        console.log(url);
        var newWin = window.open(url, "_blank");
    }

    updateForm(str: any) {
        setTimeout(() => {
            this.searchForm.state = this.stateDict[str];
        });
    }

    constructor(private http: HttpClient) {
    }
}
