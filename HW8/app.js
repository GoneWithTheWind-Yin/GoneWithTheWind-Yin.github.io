// Copyright 2017 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

'use strict';

// [START gae_node_request_example]
const express = require('express');
const url = require("url");
const https = require('https');
const path = require("path");
const app = express();
const PORT = process.env.PORT || 8080;

app.use(express.static(path.join(__dirname, "FrontEnd/dist/FrontEnd")));
app.get('/', function(req, res) {
    res.sendFile(path.join(__dirname, "FrontEnd/dist/FrontEnd/index.html"));
});



app.get('/search', function (req, res) {
    var params = url.parse(req.url, true).query;
    var searchURL = "https://api.tomorrow.io/v4/timelines?location=" + params.location;
    if (params.timesteps === '1d') {
        searchURL += "&fields=temperature,temperatureApparent,temperatureMin,temperatureMax,windSpeed,windDirection,humidity,pressureSeaLevel,uvIndex,weatherCode,precipitationProbability,precipitationType,sunriseTime,sunsetTime,visibility,moonPhase,cloudCover";
        searchURL += "&timesteps=1d";
    } else {
        searchURL += "&fields=temperature,temperatureApparent,temperatureMin,temperatureMax,windSpeed,windDirection,humidity,pressureSeaLevel,uvIndex";
        searchURL += "&timesteps=1h";
    }
    searchURL += "&units=imperial&timezone=America/Los_Angeles";
    searchURL += "&apikey=XPIAROop3O9FnGayZBJxA5xxmb7BS2ix";
    res.setHeader("Content-Type", "text/plain");
    res.setHeader("Access-Control-Allow-Origin", "*");
    https.get(searchURL, function (request) {
        var text = "";
        request.on('data', function (data) {
            text += data;
        });
        request.on('end', function () {
            return res.send(text);
        });
    });
});

app.get('/autocomplete', function (req, res) {
    var params = url.parse(req.url, true).query;
    var completeURL = 'https://maps.googleapis.com/maps/api/place/autocomplete/json?input=' + params.city + '&types=(cities)&language=en&key=AIzaSyDInz2_kjtD__RQh6w_RxTmi7-8jG0S6ww';
    res.setHeader("Content-Type", "text/plain");
    res.setHeader("Access-Control-Allow-Origin", "*");
    https.get(completeURL, function (request) {
        var text = "";
        request.on('data', function (data) {
            text += data;
        });
        request.on('end', function () {
            return res.send(text);
        });

    });
})


app.listen(PORT, () => {
    console.log(`App listening on port ${PORT}`);
    console.log('Press Ctrl+C to quit.');
});
// [END gae_node_request_example]

module.exports = app;
