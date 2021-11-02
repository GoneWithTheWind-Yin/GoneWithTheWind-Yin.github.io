import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ServiceService} from "../service/service.service";
import {animate, style, transition, trigger} from "@angular/animations";

@Component({
    selector: 'app-search-result',
    templateUrl: './search-result.component.html',
    styleUrls: ['./search-result.component.css'],
})

export class SearchResultComponent implements OnInit {

    @Output() slide = new EventEmitter<string>();

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
