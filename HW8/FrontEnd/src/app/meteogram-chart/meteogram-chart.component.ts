import {Component, OnInit} from '@angular/core';
import {ServiceService} from "../service/service.service";
import * as Highcharts from 'highcharts';
import windbarb from 'highcharts/modules/windbarb.src';
import HC_more from 'highcharts/highcharts-more';

HC_more(Highcharts);
// @ts-ignore
windbarb(Highcharts);

@Component({
    selector: 'app-meteogram-chart',
    templateUrl: './meteogram-chart.component.html',
    styleUrls: ['./meteogram-chart.component.css']
})
export class MeteogramChartComponent implements OnInit {

    constructor(public service: ServiceService) {
    }

    temperatures: any = [];
    humidity: any = [];
    winds: any = [];
    pressures: any = [];

    getUpdate() {
        // @ts-ignore
        this.service.hourlyWeatherData["data"]["timelines"][0]["intervals"].forEach((node, i) => {

            const x = Date.parse(node.startTime),
                to = x + 36e5;


            this.temperatures.push({
                x,
                y: node.values.temperature,
                // custom options used in the tooltip formatter
                to,
            });

            this.humidity.push({
                x,
                y: node.values.humidity,
            });

            if (i % 2 === 0) {
                this.winds.push({
                    x,
                    value: node.values.windSpeed,
                    direction: node.values.windDirection,
                });
            }

            this.pressures.push({
                x,
                y: node.values.pressureSeaLevel
            });
        });
    }

    getOption() {
        this.getUpdate();
        return {
            chart: {
                renderTo: "meteogram-container",
                marginBottom: 70,
                marginRight: 40,
                marginTop: 50,
                plotBorderWidth: 1,
                height: 400,
                alignTicks: false,
                scrollablePlotArea: {
                    minWidth: 720
                }
            },

            title: {
                text: 'Hourly Weather (For next 5 Days)',
                align: 'center',
                style: {
                    whiteSpace: 'nowrap',
                    textOverflow: 'ellipsis',
                }
            },

            credits: {
                text: 'Forecast',
                position: {
                    x: -40
                }
            },

            tooltip: {
                shared: true,
                useHTML: true,
                headerFormat:
                    '<small>{point.x:%A, %b %e, %H:%M} - {point.point.to:%H:%M}</small><br>'

            },

            xAxis: [{ // Bottom X axis
                type: 'datetime',
                tickInterval: 2 * 36e5, // two hours
                minorTickInterval: 36e5, // one hour
                tickLength: 0,
                gridLineWidth: 1,
                gridLineColor: 'rgba(128, 128, 128, 0.1)',
                startOnTick: false,
                endOnTick: false,
                minPadding: 0,
                maxPadding: 0,
                offset: 30,
                showLastLabel: true,
                labels: {
                    format: '{value:%H}'
                },
                crosshair: true
            }, { // Top X axis
                linkedTo: 0,
                type: 'datetime',
                tickInterval: 24 * 3600 * 1000,
                labels: {
                    format: '{value:<span style="font-size: 12px; font-weight: bold">%a</span> %b %e}',
                    align: 'left',
                    x: 3,
                    y: -5
                },
                opposite: true,
                tickLength: 20,
                gridLineWidth: 1
            }],

            yAxis: [{ // temperature axis
                title: {
                    text: null
                },
                labels: {
                    format: '{value}°',
                    style: {
                        fontSize: '10px'
                    },
                    x: -3
                },
                plotLines: [{ // zero plane
                    value: 0,
                    color: '#BBBBBB',
                    width: 1,
                    zIndex: 2
                }],
                maxPadding: 0.3,
                minRange: 8,
                tickInterval: 1,
                gridLineColor: 'rgba(128, 128, 128, 0.1)'

            }, { // precipitation axis
                title: {
                    text: null
                },
                labels: {
                    enabled: false
                },
                gridLineWidth: 0,
                tickLength: 0,
                minRange: 10,
                min: 0

            }, { // Air pressure
                allowDecimals: false,
                title: { // Title on top of axis
                    text: 'hPa',
                    offset: 0,
                    align: 'high',
                    rotation: 0,
                    style: {
                        fontSize: '10px',
                        // @ts-ignore
                        color: Highcharts.getOptions().colors[3]
                    },
                    textAlign: 'left',
                    x: 3
                },
                labels: {
                    style: {
                        fontSize: '8px',
                        // @ts-ignore
                        color: Highcharts.getOptions().colors[3]
                    },
                    y: 2,
                    x: 3
                },
                gridLineWidth: 0,
                opposite: true,
                showLastLabel: false
            }],

            legend: {
                enabled: false
            },

            plotOptions: {
                series: {
                    pointPlacement: 'between'
                }
            },


            series: [{
                name: 'Temperature',
                data: this.temperatures,
                type: 'spline',
                marker: {
                    enabled: false,
                    states: {
                        hover: {
                            enabled: true
                        }
                    }
                },
                tooltip: {
                    valueSuffix: ' °F'
                },
                zIndex: 1,
                color: '#FF3333',
                negativeColor: '#48AFE8'
            }, {
                name: 'Humidity',
                data: this.humidity,
                type: 'column',
                color: '#68CFE8',
                yAxis: 1,
                groupPadding: 0,
                pointPadding: 0,
                grouping: false,
                dataLabels: {
                    enabled: false,
                    filter: {
                        operator: '>',
                        property: 'y',
                        value: 0
                    },
                    style: {
                        fontSize: '8px',
                        color: 'gray'
                    }
                },
                tooltip: {
                    valueSuffix: ' %'
                }
            }, {
                name: 'Air pressure',
                // @ts-ignore
                color: Highcharts.getOptions().colors[3],
                data: this.pressures,
                marker: {
                    enabled: false
                },
                shadow: false,
                tooltip: {
                    valueSuffix: ' inHg'
                },
                dashStyle: 'shortdot',
                yAxis: 2
            }, {
                name: 'Wind',
                type: 'windbarb',
                id: 'windbarbs',
                // @ts-ignore
                color: Highcharts.getOptions().colors[8],
                lineWidth: 2,
                data: this.winds,
                vectorLength: 12,
                yOffset: -15,
                tooltip: {
                    valueSuffix: ' m/s'
                }
            }]
        };
    }

    drawBlocksForWindArrows = function (chart: any) {
        const xAxis = chart.xAxis[0];

        // Center items in block
        chart.get('windbarbs').markerGroup.attr({
            translateX: chart.get('windbarbs').markerGroup.translateX + 5
        });

    };

    ngOnInit(): void {
        // @ts-ignore
        Highcharts.chart(this.getOption(), chart => {
            this.drawBlocksForWindArrows(chart);
        });
    }

}
