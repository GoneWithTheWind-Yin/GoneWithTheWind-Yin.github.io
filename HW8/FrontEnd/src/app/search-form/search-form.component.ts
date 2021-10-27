import {Component, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
    selector: 'app-search-form',
    templateUrl: './search-form.component.html',
    styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {

    public states:any = [
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
        if (this.searchForm.street == "" || this.searchForm.street.trim() == "") {
            return true;
        }
        if (this.searchForm.city == "" || this.searchForm.city.trim() == "") {
            return true;
        }
        if (this.searchForm.state == "Select Your State") {
            return true;
        }
        return false;
    }

    submitForm() {
        console.log(this.searchForm);
        if (this.searchForm.currentLocation) {

        } else {

        }
    }

    clearForm() {
        this.searchForm.street = "";
        this.searchForm.city = "";
        this.searchForm.state = "Select Your State";
        this.searchForm.currentLocation = false;
    }


    constructor() {
    }

    ngOnInit(): void {

    }

}
