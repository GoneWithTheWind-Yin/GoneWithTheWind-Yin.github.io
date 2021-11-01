import {Component, OnInit} from '@angular/core';
import {ServiceService} from "../service/service.service";

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

    setActive(ID: any) {
        this.activeID = ID
    }

    addFavorite() {
        this.service.appendFavorite();
    }

    ngOnInit(): void {
    }
}
