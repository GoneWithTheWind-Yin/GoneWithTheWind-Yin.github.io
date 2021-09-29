import json

import requests
from flask import Flask, render_template, request, jsonify

app = Flask(__name__)


@app.route('/')
def index():
    return app.send_static_file('WeatherSearch.html')


@app.route('/search', methods=["GET"])
def do_search():
    args = request.args
    data = args.to_dict()
    location = data["location"]
    time_steps = "1d"
    if "timesteps" in data:
        time_steps = data["timesteps"]
    units = "imperial"
    if "units" in data:
        units = data["units"]
    field = "temperature,temperatureApparent,temperatureMin,temperatureMax,windSpeed,windDirection,humidity,pressureSeaLevel,uvIndex,weatherCode,precipitationProbability,precipitationType,sunriseTime,sunsetTime,visibility,moonPhase,cloudCover"
    if "field" in data:
        field = data["field"]

    tomorrow_url = "https://api.tomorrow.io/v4/timelines?location=" + location
    tomorrow_url += "&fields=" + field
    tomorrow_url += "&timesteps=" + time_steps
    tomorrow_url += "&units=" + units
    tomorrow_url += "&timezone=America/Los_Angeles"
    tomorrow_url += "&apikey=XPIAROop3O9FnGayZBJxA5xxmb7BS2ix"

    temperature_response = requests.get(tomorrow_url).json()
    print(json.dumps(temperature_response))
    temperature_response = jsonify(temperature_response)

    # weather_data = str(temperature_response.data, "utf-8")

    return temperature_response


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=8080, debug=True)
