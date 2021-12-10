import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AngularFontAwesomeModule} from 'angular-font-awesome';

/* import module from primeNG */
import {CalendarModule} from 'primeng/calendar';
import {DropdownModule} from 'primeng/dropdown';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {FormsModule, ReactiveFormsModule} from '@angular/forms'; // <-- NgModel lives here
import {TableModule} from 'primeng/table';
import {BookcarRoutingModule} from './bookcar-routing.module';
import {AddTeamCarComponent} from './teamcar/add-team-car.component';
import {RadioButtonModule} from 'primeng/radiobutton';
import {ReportBookcarComponent} from './report-bookcar/report-bookcar.component';
import {BookcarService} from './bookcar.service';
import {InputTextModule} from 'primeng/inputtext';
import {AutoCompleteModule} from 'primeng/autocomplete';
import {TooltipModule} from 'primeng/tooltip';
import {KeyFilterModule} from 'primeng/keyfilter';
import {FieldsetModule} from 'primeng/fieldset';
import {CategoryCarComponent} from './category-car/category-car.component';
import {CategoryDriverComponent} from './category-driver/category-driver.component';
import {ScrollPanelModule} from 'primeng/scrollpanel';
import {DialogModule} from 'primeng/dialog';
import {CheckboxModule, MultiSelectModule} from "primeng/primeng";
import {RequestCarComponent} from './request-car/request-car.component';
import {CarFreeTimeComponent} from './car-free-time/car-free-time.component';
import {RatingModule} from "primeng/rating";
import {CreatingComponent} from './report-bookcar/creating/creating.component';
import {RenewedComponent} from "./report-bookcar/renewed/renewed.component";
import {RefuseComponent} from "./report-bookcar/refuse/refuse.component";
import { DetailsCarBookingComponent } from './report-bookcar/details-car-booking/details-car-booking.component';
import {OrderingComponent} from "./report-bookcar/ordering/ordering.component";

@NgModule({
    imports: [
        ReactiveFormsModule,
        CheckboxModule,
        CommonModule,
        CalendarModule,
        FormsModule,
        DropdownModule,
        AngularFontAwesomeModule,
        TableModule,
        AutoCompleteModule,
        RadioButtonModule,
        InputTextModule,
        TooltipModule,
        KeyFilterModule,
        BookcarRoutingModule,
        FieldsetModule,
        ScrollPanelModule,
        CheckboxModule,
        DialogModule,
        MultiSelectModule,
        RatingModule,
        InputTextareaModule
    ],
    declarations: [
        AddTeamCarComponent,
        ReportBookcarComponent,
        CategoryCarComponent,
        CategoryDriverComponent,
        RequestCarComponent,
        CarFreeTimeComponent,
        CreatingComponent,
        RenewedComponent,
        RefuseComponent,
 DetailsCarBookingComponent,
        OrderingComponent
    ],
    providers: [BookcarService]
})
export class BookcarModule {
}