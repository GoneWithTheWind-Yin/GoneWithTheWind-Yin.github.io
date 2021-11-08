import {Component, OnInit} from '@angular/core';
import {ServiceService} from "../service/service.service";
import * as Highcharts from 'highcharts';
import HC_more from 'highcharts/highcharts-more';
HC_more(Highcharts);

@Component({
    selector: 'app-temp-chart',
    templateUrl: './temp-chart.component.html',
    styleUrls: ['./temp-chart.component.css']
})
export class TempChartComponent implements OnInit {

    constructor(public service: ServiceService) {
    }

    showChart() {
        var jsonData = [];

        for (let i = 0; i < 15; ++i) {
            let time = new Date(this.service.dailyWeatherData["data"]["timelines"][0]["intervals"][i]["startTime"]);
            jsonData[i] = [time.getTime(), this.service.dailyWeatherData["data"]["timelines"][0]["intervals"][i]["values"]["temperatureMin"], this.service.dailyWeatherData["data"]["timelines"][0]["intervals"][i]["values"]["temperatureMax"]];
        }

        return {
            chart: {
                type: 'arearange',
                zoomType: 'x',
                scrollablePlotArea: {
                    maxWidth: 1000,
                    scrollPositionX: 1
                },
            },

            title: {
                text: 'Temperature Ranges (Min, Max)'
            },

            xAxis: {
                // 可能格式需要修改一下
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
                    linearGradient: [0, 100, 0, 400],
                    stops: [
                        // @ts-ignore
                        [0, Highcharts.getOptions().colors[3]],
                        // @ts-ignore
                        [1, Highcharts.color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                }
            }]
        };
    }

    ngOnInit(): void {
        // @ts-ignore
        Highcharts.chart('temp-container', this.showChart());
    }

}
