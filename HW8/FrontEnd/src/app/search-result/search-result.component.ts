import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ServiceService} from "../service/service.service";
import {animate, group, query, style, transition, trigger} from "@angular/animations";
import {faTrashAlt} from '@fortawesome/free-solid-svg-icons';

const left = [
    query(':enter, :leave', style({ position: 'relative', width: '100%' }), { optional: true }),
    group([
        query(':enter', [style({ transform: 'translateX(-100%)' }), animate('.3s ease-out', style({ transform: 'translateX(0%)' }))], {
            optional: true,
        }),
        query(':leave', [style({ transform: 'translateX(0%)' }), animate('.3s ease-out', style({ transform: 'translateX(100%)' }))], {
            optional: true,
        }),
    ]),
];

const right = [
    query(':enter, :leave', style({ position: 'relative', width: '100%' }), { optional: true }),
    group([
        query(':enter', [style({ transform: 'translateX(100%)' }), animate('.3s ease-out', style({ transform: 'translateX(0%)' }))], {
            optional: true,
        }),
        query(':leave', [style({ transform: 'translateX(0%)' }), animate('.3s ease-out', style({ transform: 'translateX(-100%)' }))], {
            optional: true,
        }),
    ]),
];

@Component({
    selector: 'app-search-result',
    templateUrl: './search-result.component.html',
    styleUrls: ['./search-result.component.css'],
    animations: [
        trigger("animSlider", [
            transition(':increment', right),
            transition(':decrement', left),
        ])
    ]
})

export class SearchResultComponent implements OnInit {

    // @Output() slide = new EventEmitter<string>();
    trash = faTrashAlt;

    constructor(public service: ServiceService) {
    }

    addFavorite() {
        this.service.appendFavorite();
    }

    ngOnInit(): void {
    }
}
