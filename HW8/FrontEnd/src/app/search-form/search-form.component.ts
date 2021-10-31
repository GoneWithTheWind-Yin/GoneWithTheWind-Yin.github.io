import {Component, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
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

    public searchForm: any = {
        street: "",
        city: "",
        state: "Select Your State",
        currentLocation: false,
    }

    // @ts-ignore
    checkForm() {
        if (this.searchForm.street == null || this.searchForm.street == "" || this.searchForm.street.trim() == "") {
            return true;
        }
        if (this.searchForm.city == null || this.searchForm.city == "" || this.searchForm.city.trim() == "") {
            return true;
        }
        if (this.searchForm.state == "Select Your State") {
            return true;
        }
        return false;
    }

    submitForm() {
        console.log(this.searchForm);
        this.service.loadStatus = true;
        if (this.searchForm.currentLocation) {
            this.service.getWeatherDataByIP("1d");
            this.service.getWeatherDataByIP("1h");
        } else {
            this.service.getWeatherDataByLoc(this.searchForm.street, this.searchForm.city, this.searchForm.state, "1d");
            this.service.getWeatherDataByLoc(this.searchForm.street, this.searchForm.city, this.searchForm.state, "1h");
            // console.log(this.service.dailyWeatherData);
        }
    }

    clearForm() {
        this.searchForm.street = "";
        this.searchForm.city = "";
        this.searchForm.state = "Select Your State";
        this.searchForm.currentLocation = false;
        this.service.dailyWeatherData = "";
        this.service.hourlyWeatherData = "";
        this.service.loadStatus = false;
    }


    constructor(private http: HttpClient, public service: ServiceService) {
    }


    ngOnInit(): void {

    }

}
