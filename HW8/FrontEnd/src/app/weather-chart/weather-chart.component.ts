import {Component, OnInit} from '@angular/core';
import {ServiceService} from "../service/service.service";

@Component({
    selector: 'app-weather-chart',
    templateUrl: './weather-chart.component.html',
    styleUrls: ['./weather-chart.component.css']
})
export class WeatherChartComponent implements OnInit {

    constructor(public service: ServiceService) {
    }

    ngOnInit(): void {
    }

}
