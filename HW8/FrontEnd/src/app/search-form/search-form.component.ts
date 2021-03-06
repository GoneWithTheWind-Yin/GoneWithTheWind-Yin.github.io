import {Component, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ServiceService} from "../service/service.service";

@Component({
    selector: 'app-search-form',
    templateUrl: './search-form.component.html',
    styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {


    public states: any = [
        "Select Your State",
        "Alabama",
        "Alaska",
        "Arizona",
        "Arkansas",
        "California",
        "Colorado",
        "Connecticut",
        "Delaware",
        "District Of Columbia",
        "Florida",
        "Georgia",
        "Hawaii",
        "Idaho",
        "Illinois",
        "Indiana",
        "Iowa",
        "Kansas",
        "Kentucky",
        "Louisiana",
        "Maine",
        "Maryland",
        "Massachusetts",
        "Michigan",
        "Minnesota",
        "Mississippi",
        "Missouri",
        "Montana",
        "Nebraska",
        "Nevada",
        "New Hampshire",
        "New Jersey",
        "New Mexico",
        "New York",
        "North Carolina",
        "North Dakota",
        "Ohio",
        "Oklahoma",
        "Oregon",
        "Pennsylvania",
        "Rhode Island",
        "South Carolina",
        "South Dakota",
        "Tennessee",
        "Texas",
        "Utah",
        "Vermont",
        "Virginia",
        "Washington",
        "West Virginia",
        "Wisconsin",
        "Wyoming"
    ];


    constructor(private http: HttpClient, public service: ServiceService) {
    }

    public options: any;
    public optionsStates: any;

    public httpOptions = {
        headers: new HttpHeaders({
            'Access-Control-Allow-Origin': '*',
        })
    };

    getAutoComplete(event: any) {
        setTimeout( () => {
            this.options = [];
            this.optionsStates = [];
            var city = this.service.searchForm.city.trim();
            var url = "https://weathersearch-1998.wl.r.appspot.com/autocomplete?city=" + this.service.searchForm.city;
            if (this.service.searchForm.city != "" && city.length != 0) {// check all blank
                this.http.get(url).subscribe(data => {
                    console.log(data);
                    // @ts-ignore
                    var arr = data["predictions"];
                    for (let i = 0; i < arr.length; ++i) {
                        let ac = arr[i]["structured_formatting"]["main_text"];
                        let state = arr[i]["terms"][1]["value"];
                        this.options.push(ac);
                        this.optionsStates.push(state);
                    }
                });
            }
        });
    }


    ngOnInit(): void {

    }

}
