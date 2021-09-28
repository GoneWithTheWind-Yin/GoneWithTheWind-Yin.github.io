var weatherDict = {
    1000: "clear_day",
    1100: "mostly_clear_day",
    1101: "partly_cloudy_day",
    1102: "mostly_cloudy",
    1001: "cloudy",
    2000: "fog",
    2001: "fog_light",
    8000: "tstorm",
    5001: "flurries",
    5100: "snow_light",
    5000: "snow",
    5101: "snow_heavy",
    7102: "ice_pellets_light",
    7000: "ice_pellets",
    7101: "ice_pellets_heavy",
    4000: "drizzle",
    6000: "freezing_drizzle",
    6200: "freezing_rain_light",
    6001: "freezing_rain",
    6201: "freezing_rain_heavy",
    4200: "rain_light",
    4001: "rain",
    4201: "rain_heavy"
}
var weatherDict2 = {
    1000: "clear",
    1100: "mostly clear",
    1101: "partly cloudy",
    1102: "mostly cloudy",
    1001: "cloudy",
    2000: "fog",
    2001: "light fog",
    8000: "thunderstorm",
    5001: "flurries",
    5100: "light snow",
    5000: "snow",
    5101: "heavy snow",
    7102: "light ice pellets",
    7000: "ice pellets",
    7101: "heavy ice pellets",
    4000: "drizzle",
    6000: "freezing drizzle",
    6200: "light freezing rain",
    6001: "freezing rain",
    6201: "heavy freezing rain",
    4200: "light rain",
    4001: "rain",
    4201: "heavy rain"
}

var monthDict = {
    "01": "Jan",
    "02": "Feb",
    "03": "Mar",
    "04": "Apr",
    "05": "May",
    "06": "Jun",
    "07": "Jul",
    "08": "Aug",
    "09": "Sep",
    "10": "Oct",
    "11": "Nov",
    "12": "Dec"
}

var weekDict = {0: "Sunday", 1: "Monday", 2: "Tuesday", 3: "Wednesday", 4: "Thursday", 5: "Friday", 6: "Saturday"}

var precipitationDict = {0: "N/A", 1: "Rain", 2: "Snow", 3: "Freezing Rain", 4: "Ice Pellets"}

var globalAddress = ""
var globalLocation = ""
var weatherDayData = ""

function submitForm(event) {
    event.preventDefault();
    clearResultArea()
    var street = document.getElementById("street").value;
    var city = document.getElementById("city").value;
    var state = document.getElementById("state").value;
    var address = "";
    var location = "";

    if (document.getElementById("checkLocation").checked) {
        flag = true;
        var ipInfoUrl = "https://ipinfo.io/?token=0b676f0b07b1a9"
        fetch(ipInfoUrl).then(res => res.json())
            .then(function (data) {
                console.log(data);
                var json = data;
                location = json.loc
                address = json.city + "," + json.region + "," + json.postal
                globalLocation = location
                globalAddress = address
                var geocodingUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="
                geocodingUrl += address + "&key=AIzaSyAd9Qbqgx8fyM2WufIIkdlRcBt8mDrtdoM"
                fetch(geocodingUrl).then(response => response.json())
                    .then(function (geoData) {
                        console.log(geoData)
                        address = geoData["results"][0]["formatted_address"]
                        var weather_data = requestBackendForCard(address, location, "", "");
                        console.log(weather_data);
                    }).catch(function (error) {
                    console.log(error);
                })
            }).catch(function (e) {
            console.log(e);
        })
    } else {
        var geocodingUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="
        geocodingUrl += street + "," + city + "," + state + "" + "&key=AIzaSyAd9Qbqgx8fyM2WufIIkdlRcBt8mDrtdoM"
        fetch(geocodingUrl).then(res => res.json())
            .then(function (data) {
                var lat = data["results"][0]["geometry"]["location"]["lat"]
                var lng = data["results"][0]["geometry"]["location"]["lng"]
                location = lat + "," + lng
                address = data["results"][0]["formatted_address"]
                globalLocation = location
                globalAddress = address
                console.log(data);
                var weather_data = requestBackendForCard(address, location, "", "");
            }).catch(function (e) {
            console.log(e);
        })
    }

}

function generateCard(address, weather) {
    if (weather === "") {
        var no_record = document.getElementById("noRecordsDisplay");
        no_record.innerHTML = "<div id='noRecord' align='center' style='background-color: white; width: 600px; top: 550px; position: relative; height: 30px; font-size:24px'>No Records have been found.</div>";
    } else {
        var card = document.getElementById("card");
        var weatherCode = weather["data"]["timelines"][0]["intervals"][0]["values"]["weatherCode"];
        var weatherName = weatherDict[weatherCode];
        var weatherTem = weather["data"]["timelines"][0]["intervals"][0]["values"]["temperature"];
        var weatherDes = weatherDict2[weatherCode];
        var humidity = weather["data"]["timelines"][0]["intervals"][0]["values"]["humidity"];
        var pressure = weather["data"]["timelines"][0]["intervals"][0]["values"]["pressureSeaLevel"];
        var windSpeed = weather["data"]["timelines"][0]["intervals"][0]["values"]["windSpeed"];
        var visibility = weather["data"]["timelines"][0]["intervals"][0]["values"]["visibility"];
        var cloudCover = weather["data"]["timelines"][0]["intervals"][0]["values"]["cloudCover"];
        var UV = weather["data"]["timelines"][0]["intervals"][0]["values"]["uvIndex"];
        card.innerHTML = "<p style=\"font-size: 26px; margin-left: 30px\">" + address + "</p>"
            + "<img src=\"../static/images/" + weatherName + ".svg\" width=\"150px\" height=\"150px\" style=\"margin-left: 30px;\" alt=\"weather image\">"
            + "<p style=\"font-size: 140px; font-weight: 100; float: right; margin: 0 60px 0 0;\">" + weatherTem + "°</p>"
            + "<div style='font-size: 22px; margin-left: 30px; width:150px; text-align: center'>" + weatherDes + "</div>"
            + "<div id=\"humidity\" style=\"margin-left: 20px; text-align: center; float: left; width: 100px\">\n<p>Humidity</p>\n"
            + "<img src=\"https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-16-512.png\" alt=\"Humidity\" width=\"30px\" height=\"30px\">\n<p>" + humidity + "%</p></div>"
            + "<div id=\"pressure\" style=\"margin-left: 20px; text-align: center; float: left; width: 100px\">\n<p>Pressure</p>\n"
            + "<img src=\"https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-25-512.png\" alt=\"Pressure\" width=\"30px\" height=\"30px\">\n<p>" + pressure + "inHg</p></div>"
            + "<div id=\"windSpeed\" style=\"margin-left: 20px; text-align: center; float: left; width: 100px\">\n<p>Wind Speed</p>\n"
            + "<img src=\"https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png\" alt=\"Wind Speed\" width=\"30px\" height=\"30px\">\n<p>" + windSpeed + "mph</p></div>"
            + "<div id=\"visibility\" style=\"margin-left: 20px; text-align: center; float: left; width: 100px\">\n<p>Visibility</p>\n"
            + "<img src=\"https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-30-512.png\" alt=\"Visibility\" width=\"30px\" height=\"30px\">\n<p>" + visibility + "mi</p></div>"
            + "<div id=\"cloudCover\" style=\"margin-left: 20px; text-align: center; float: left; width: 100px\">\n<p>Cloud Cover</p>\n"
            + "<img src=\"https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png\" alt=\"Cloud Cover\" width=\"30px\" height=\"30px\">\n<p>" + cloudCover + "%</p></div>"
            + "<div id=\"uvLevel\" style=\"margin-left: 20px; text-align: center; float: left; width: 100px\">\n<p>UV Level</p>\n"
            + "<img src=\"https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-24-512.png\" alt=\"UV Level\" width=\"30px\" height=\"30px\">\n<p>" + UV + "</p></div>";

        var forcast = document.getElementById("forecast")
        forcast.innerHTML = "<div id=\"head\" class=\"head\" style=\"background-color: #516fa8; height: 45px; text-align: center; font-size: 18px\"></div>\n" +
            "    <div id=\"body\" class=\"body\"></div>"

        var head = document.getElementById("head")
        head.innerHTML = "<div style=\"width: 330px; float: left; margin-top: 13px\">Date</div>\n" +
            "        <div style=\"width: 230px; float: left; margin-top: 13px\">Status</div>\n" +
            "        <div style=\"width: 180px; float: left; margin-top: 13px\">Temp High</div>\n" +
            "        <div style=\"width: 180px; float: left; margin-top: 13px\">Temp Low</div>\n" +
            "        <div style=\"width: 180px; float: left; margin-top: 13px\">Wind speed</div>"

        for (let i = 0; i < 15; ++i) {
            let time = weather["data"]["timelines"][0]["intervals"][i]["startTime"]
            time = parseDate(time)
            let code = weather["data"]["timelines"][0]["intervals"][i]["values"]["weatherCode"];
            let tempHigh = weather["data"]["timelines"][0]["intervals"][i]["values"]["temperatureMax"];
            let tempLow = weather["data"]["timelines"][0]["intervals"][i]["values"]["temperatureMin"];
            let speed = weather["data"]["timelines"][0]["intervals"][i]["values"]["windSpeed"];
            let dayWeather = weather["data"]["timelines"][0]["intervals"][i];
            let body = document.getElementById("body");
            console.log(dayWeather);
            body.innerHTML += "<div style=\"margin-top: 5px; height: 60px; text-align: center; background-color: white;\" onclick='generateDetail(" + JSON.stringify(dayWeather) + ")' >"
                + "<div style=\"width: 330px; float: left; margin-top: 25px\">" + time + "</div>\n<div style=\"width: 230px; float: left;\">"
                + "<img src=\"../static/images/" + weatherDict[code] + ".svg\" height=\"60px\" width=\"60px\" style=\" vertical-align:middle\">\n"
                + "<span style=\"margin-left: 15px;\">" + weatherDict2[code] + "</span></div>"
                + "<div style=\"width: 180px; float: left; margin-top: 25px\">" + tempHigh + "</div>"
                + "<div style=\"width: 180px; float: left; margin-top: 25px\">" + tempLow + "</div>"
                + "<div style=\"width: 180px; float: left; margin-top: 25px\">" + speed + "</div></div>";
        }
        // for (let i = 0; i < 15; ++i) {
        //     let dayWeather = weather["data"]["timelines"][0]["intervals"][i];
        //     document.getElementById(i.toString()).onclick = function () {
        //         generateDetail(dayWeather)
        //     }
        // }
    }
}

function parseDate(time) {
    var year = time.substring(0, 4);
    var month = time.substring(5, 7);
    var day = time.substring(8, 10);
    var date = new Date(parseInt(year), parseInt(month) - 1, parseInt(day));
    month = monthDict[month];
    var week = weekDict[date.getDay()];
    return week + ", " + day + " " + month + " " + year;
}


function requestBackendForCard(address, location, timeSteps, units) {
    // 后续改为在线的url
    var backend_url = "http://127.0.0.1:8080/search?"
    backend_url += "location=" + location
    if (timeSteps !== "") {
        backend_url += "&timesteps=" + timeSteps
    }
    if (units !== "") {
        backend_url += "&units=" + units
    }

    var data = {};

    var res = fetch(backend_url)
    res.then(res => res.json())
        .then(function (weatherData) {
            data = weatherData;
            weatherDayData = data;
            generateCard(address, data);
        })
    return data
}

function checkboxOnClick(checkbox) {
    if (checkbox.checked) {
        document.getElementById("street").innerText = ''
        document.getElementById("city").innerText = ''
        document.getElementById("street").disabled = true
        document.getElementById("city").disabled = true
        document.getElementById("state").disabled = true
    } else {
        document.getElementById("street").disabled = false
        document.getElementById("city").disabled = false
        document.getElementById("state").disabled = false
    }
}

function clearForm() {
    document.getElementById("addressForm").reset();
    document.getElementById("street").disabled = false
    document.getElementById("city").disabled = false
    document.getElementById("state").disabled = false
    clearResultArea();
}

function clearResultArea() {
    var noRecordsDisplay = document.getElementById("noRecordsDisplay");
    noRecordsDisplay.innerHTML = ''
    var card = document.getElementById("card");
    card.innerHTML = ''
    var forecast = document.getElementById("forecast");
    forecast.innerHTML = ''
    var weatherDetail = document.getElementById("weatherDetail");
    weatherDetail.innerHTML = ''
    var weatherChart = document.getElementById("weatherChart");
    weatherChart.innerHTML = ''
}

function generateDetail(data) {
    var card = document.getElementById("card");
    card.innerHTML = '';
    var forecast = document.getElementById("forecast");
    forecast.innerHTML = '';
    console.log(data);
    var detail = document.getElementById("weatherDetail");
    detail.innerHTML = "<div id=\"detailTitle\" class=\"detailTitle\" style=\"text-align: center;\">\n"
        + "<p style=\"font-size: 46px; font-weight: 300; margin-top: 3px; margin-bottom: 0\">Daily Weather Detail</p>\n"
        + "<hr style=\"width: 450px; color: white; margin-top: 30px\"></div>"
    var time = data["startTime"];
    var weatherCode = data["values"]["weatherCode"];
    var temperatureMin = data["values"]["temperatureMin"];
    var temperatureMax = data["values"]["temperatureMax"];
    var precipitation = data["values"]["precipitationType"];
    precipitation = precipitationDict[precipitation];
    var precipitationProb = data["values"]["precipitationProbability"];
    var windSpeed = data["values"]["windSpeed"];
    var humidity = data["values"]["humidity"];
    var visibility = data["values"]["visibility"];
    var sunRise = data["values"]["sunriseTime"];
    var sunSet = data["values"]["sunsetTime"];
    detail.innerHTML += "<div id=\"detailCard\" class=\"detailCard\" style=\"margin-top: 30px; background-image: linear-gradient(white, #3a4f75); width: 600px; position: absolute; \">\n"
        + "<img src=\"../static/images/" + weatherDict[weatherCode] + ".svg\" height=\"200px\" width=\"200px\" style=\"float: right; margin-right: 20px; margin-top: 0\">"
        + "<p style=\"color: #3b4f76; font-size: 30px; margin: 25px 0 0 20px\">" + parseDate(time) + "</p>"
        + "<p style=\"color: #3b4f76; font-size: 30px; margin: 20px 0 0 20px\">" + weatherDict2[weatherCode] + "</p>"
        + "<p style=\"color: #3b4f76; font-size: 36px; margin: 20px 0 0 20px\">" + temperatureMax + "°F/" + temperatureMin + "°F</p>"
        + " <div style=\"display: flex; width: 600px\"><div style=\"font-size: 22px; color: white; width: 60%; text-align: right\">\n"
        + "<p style=\"margin: 30px 0 0 0\">Precipitation:&nbsp</p><p style=\"margin: 10px 0 0 0;\">Chance of Rain:&nbsp</p>\n"
        + "<p style=\"margin: 10px 0 0 0;\">Wind Speed:&nbsp</p><p style=\"margin: 10px 0 0 0;\">Humidity:&nbsp</p>\n"
        + "<p style=\"margin: 10px 0 0 0;\">Visibility:&nbsp</p><p style=\"margin: 10px 0 25px 0;\">Sunrise/Sunset:&nbsp</p></div>\n"
        + "<div style=\"font-size: 22px; color: white;\"><p style=\"margin: 30px 0 0 0;\">" + precipitation + "</p>"
        + "<p style=\"margin: 10px 0 0 0;\">" + precipitationProb + "%</p>"
        + "<p style=\"margin: 10px 0 0 0;\">" + windSpeed + "mph</p>"
        + "<p style=\"margin: 10px 0 0 0;\">" + humidity + "%</p>"
        + "<p style=\"margin: 10px 0 0 0;\">" + visibility + "mi</p>"
        + "<p style=\"margin: 10px 0 25px 0;\">" + parseTime(sunRise) + "/" + parseTime(sunSet) + "</p>";
    generateChart(data)
}

function generateChart(data) {
    var weatherChart = document.getElementById("weatherChart")
    weatherChart.innerHTML = "<div id=\"chartTitle\" class=\"chartTitle\" style=\"text-align: center;\">\n"
        + "<p style=\"font-size: 46px; font-weight: 300; margin-top: 3px; margin-bottom: 0\">Weather Charts</p>\n"
        + "<hr style=\"width: 450px; color: white; margin-top: 15px\">\n" +
        "<img src=\"../static/images/point-down-512.png\" width=\"50px\" height=\"50px\" onclick='generateCharts()'></div>"
        + "<figure class=\"temperature-figure\"><div id=\"tempContainer\"></div></figure>";
}

function parseTime(time) {
    var hour = time.substring(11, 13);
    var min = time.substring(14, 16);
    var hourInt = parseInt(hour)
    if (hourInt > 12) {
        hourInt -= 12
        return hourInt + ":" + min + "PM";
    }
    return hourInt + ":" + min + "AM";
}

function getSecond(time) {
    var year = time.substring(0, 4);
    var month = time.substring(5, 7);
    var day = time.substring(8, 10);
    var date = new Date(parseInt(year), parseInt(month) - 1, parseInt(day));
    console.log(date)
    return date.getTime();
}

function generateCharts() {
    temperatureRange();
    // var backend_url = "http://127.0.0.1:8080/search?"
    // backend_url += "location=" + globalLocation
    // backend_url += "&timesteps=" + "1h"
    // var res = fetch(backend_url)
    // res.then(res => res.json())
    //     .then(function (weatherData) {
    //         var temp = new Meteogram(weatherData, "container");
    //         temp.createChart();
    //     })
}

function temperatureRange() {
    console.log(globalAddress);
    console.log(globalLocation);

    var jsonData = [];

    console.log(weatherDayData)

    for (let i = 0; i < 15; ++i) {
        let time = weatherDayData["data"]["timelines"][0]["intervals"][i]["startTime"];
        jsonData[i] = [getSecond(time), weatherDayData["data"]["timelines"][0]["intervals"][i]["values"]["temperatureMin"], weatherDayData["data"]["timelines"][0]["intervals"][i]["values"]["temperatureMax"]];
    }

    console.log(jsonData)

    Highcharts.chart('tempContainer', {

        chart: {
            type: 'arearange',
            zoomType: 'x',
            scrollablePlotArea: {
                minWidth: 600,
                scrollPositionX: 1
            }
        },

        title: {
            text: 'Temperature Ranges (Min, Max)'
        },

        xAxis: {
            type: 'datetime',
            tickInterval: 86400000,
        },

        yAxis: {
            title: {
                text: null
            }
        },

        tooltip: {
            crosshairs: true,
            shared: true,
            valueSuffix: '°F',
            xDateFormat: '%A, %b %e'
        },

        legend: {
            enabled: false
        },

        series: [{
            name: 'Temperatures',
            data: jsonData,
            lineColor: '#f19e27',
            lineWidth: 2,
            fillColor: {
                linearGradient: [0, 100, 0, 300],
                stops: [
                    [0, Highcharts.getOptions().colors[3]],
                    [1, Highcharts.color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                ]
            }
        }]

    });
}
