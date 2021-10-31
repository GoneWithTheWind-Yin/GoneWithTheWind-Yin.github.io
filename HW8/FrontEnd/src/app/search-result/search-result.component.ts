import {Component, OnInit} from '@angular/core';
import {ServiceService} from "../service/service.service";
// @ts-ignore
import {} from '@types/googlemaps';

declare var google: any;

@Component({
    selector: 'app-search-result',
    templateUrl: './search-result.component.html',
    styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {

    constructor(public service: ServiceService) {
    }

    public activeID = "nav-day-tab";

    resultClass = "btn btn-primary";
    favoriteClass = "btn btn-default";
    isResult = true;
    isFavorite = false;
    public map: any;
    public marker: any;

    setActive(ID: any) {
        this.activeID = ID
    }

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

    addFavorite() {
        this.isFavorite = !this.isFavorite;
    }

    ngOnInit(): void {
        let uluru = {lat: 30, lng: 110};
        this.map = new google.maps.Map(
            document.getElementById("map") as HTMLElement,
            {
                zoom: 4,
                center: uluru,
            }
        );
        this.marker = new google.maps.Marker({
            position: uluru,
            map: this.map,
        });
        this.marker.setMap(this.map);
    }

}
