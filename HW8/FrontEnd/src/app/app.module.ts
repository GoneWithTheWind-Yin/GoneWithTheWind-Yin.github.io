import {NgModule} from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from "@angular/forms";
import {MatIconModule} from '@angular/material/icon';
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SearchFormComponent} from './search-form/search-form.component';
import {SearchResultComponent} from './search-result/search-result.component';
import {ServiceService} from "./service/service.service";
import {WeatherChartComponent} from './weather-chart/weather-chart.component';
import {TempChartComponent} from './temp-chart/temp-chart.component';
import {MeteogramChartComponent} from './meteogram-chart/meteogram-chart.component';
import {HighchartsChartModule} from 'highcharts-angular';
import {ChartModule} from 'angular-highcharts';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {NowhitespaceDirective} from './directives/nowhitespace.directive';
import {MatInputModule} from "@angular/material/input";
import {NoopAnimationsModule, BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';

@NgModule({
    declarations: [
        AppComponent,
        SearchFormComponent,
        SearchResultComponent,
        WeatherChartComponent,
        TempChartComponent,
        MeteogramChartComponent,
        NowhitespaceDirective,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        NgbModule,
        MatIconModule,
        HttpClientModule,
        HighchartsChartModule,
        ChartModule,
        MatAutocompleteModule,
        MatInputModule,
        NoopAnimationsModule,
        BrowserAnimationsModule,
        FontAwesomeModule,
    ],
    providers: [ServiceService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
