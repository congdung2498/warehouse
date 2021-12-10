 import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {StationeryRoutingModule} from './stationery-routing.module';
import {StationeryManagementComponent} from './stationery-management/stationery-management.component';
import {StationeryService} from './stationery.service';

import {AngularFontAwesomeModule} from "angular-font-awesome";

import {CalendarModule} from "primeng/calendar";
import {DropdownModule} from "primeng/dropdown";
import {FormsModule, ReactiveFormsModule} from "@angular/forms"; // <-- NgModel lives here
import {TableModule} from "primeng/table";
import {RadioButtonModule} from "primeng/radiobutton";
import {InputTextModule} from "primeng/inputtext";
import {AutoCompleteModule} from "primeng/autocomplete";
import {TooltipModule} from "primeng/tooltip";
import {KeyFilterModule} from "primeng/keyfilter";
import {FieldsetModule} from "primeng/fieldset";
import {ScrollPanelModule} from "primeng/scrollpanel";
import {MultiSelectModule} from "primeng/multiselect";
import {ReceiverComponent} from './receiver/receiver.component';
import {ReportStationeryComponent} from './report-stationery/report-stationery.component';
import {TreeModule} from 'primeng/tree';
import {DialogModule} from 'primeng/dialog';
import {RequestStationeryComponent} from './request-stationery/request-stationery.component';
import {CheckboxModule, FileUploadModule, InputTextareaModule, SpinnerModule, TabViewModule} from "primeng/primeng";
import {RequestOfficeComponent} from './request-stationery/request-office/request-office.component';
import {ListRequestComponent} from './request-stationery/list-request/list-request.component';
import {ProcessUnitComponent} from './request-stationery/process-unit/process-unit.component';
import {ProcessRequestComponent} from './request-stationery/process-request/process-request.component';
import {ConfirmRequestComponent} from './request-stationery/confirm-request/confirm-request.component';
import {EditListRequestComponent} from './request-stationery/list-request/edit-list-request/detail-edit-list-request/edit-list.component';
import {RattingListRequestComponent} from './request-stationery/list-request/ratting-list-request/ratting-list-request.component';
import {RatingModule} from "primeng/rating";
import {AddStationeryComponent} from './add-stationery/add-stationery.component';
import { ReportStationeryEmployeeComponent } from './report-stationery-employee/report-stationery-employee.component';
import { ProcessRequestStationeryComponent } from './process-request-stationery/process-request-stationery.component';
import { BrowseRequestComponent } from './report-stationery/browse-request/browse-request.component';
import { DetailsStationeryUnitComponent } from './report-stationery/details-stationery-unit/details-stationery-unit.component';
import { VoteHcdvComponent } from './report-stationery/vote-hcdv/vote-hcdv.component';
import { VoteVptctComponent } from './report-stationery/vote-vptct/vote-vptct.component';
 import {ConfirmPendingComponent} from "./report-stationery/confirm-pending/confirm-pending.component";


@NgModule({
    imports: [
        CommonModule,
        StationeryRoutingModule,
        FormsModule,
        TableModule,
        RadioButtonModule,
        FieldsetModule,
        ScrollPanelModule,
        KeyFilterModule,
        InputTextModule,
        TooltipModule,
        DropdownModule,
        AngularFontAwesomeModule,
        AutoCompleteModule,
        CalendarModule,
        TreeModule,
        DialogModule,
        MultiSelectModule,
        TabViewModule,
        CheckboxModule,
        ReactiveFormsModule,
        RatingModule,
        FileUploadModule,
        InputTextareaModule,
        SpinnerModule
    ],
    declarations: [
        StationeryManagementComponent, ReceiverComponent, ReportStationeryComponent, RequestStationeryComponent,
        RequestOfficeComponent, ListRequestComponent, ProcessUnitComponent, ProcessRequestComponent, ConfirmPendingComponent,
        ConfirmRequestComponent,
        EditListRequestComponent,
        RattingListRequestComponent,
        AddStationeryComponent,
        ReportStationeryEmployeeComponent,
        ProcessRequestStationeryComponent,
        BrowseRequestComponent,
        DetailsStationeryUnitComponent,
        VoteHcdvComponent,
        VoteVptctComponent,
    ],
    providers: [StationeryService]

})

export class StationeryModule {
}
