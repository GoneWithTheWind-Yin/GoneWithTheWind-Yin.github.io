import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-search-result',
    templateUrl: './search-result.component.html',
    styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {

    constructor() {
    }

    resultClass = "btn btn-primary";
    favoriteClass = "btn btn-default";

    getResult() {
        this.resultClass = "btn btn-primary";
        this.favoriteClass = "btn btn-default";
    }

    getFavorite() {
        this.resultClass = "btn btn-default";
        this.favoriteClass = "btn btn-primary";
    }

    ngOnInit(): void {
    }

}
