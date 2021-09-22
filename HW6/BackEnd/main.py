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
    if data["location"] != "":
        time_steps = data["timeSteps"]
    units = "imperial"
    if data["units"] != "":
        time_steps = data["units"]

    tomorrow_url = "https://api.tomorrow.io/v4/timelines?location=" + location + "&fields=temperature,temperatureApparent,temperatureMin,temperatureMax,windSpeed,windDirection,humidity,pressureSeaLevel,uvIndex,weatherCode,precipitationProbability,precipitationType,sunriseTime,sunsetTime,visibility,moonPhase,cloudCover"
    tomorrow_url += "&timesteps=" + time_steps
    tomorrow_url += "units=" + units
    tomorrow_url += "&apikey=mMSmnGIVw3Fr1WsrBUpy8k7wcIMqzbNg"

    temperature_response = requests.get(tomorrow_url).json()
    temperature_response = jsonify(temperature_response)
    print(temperature_response)

    return temperature_response


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=8080, debug=True)
