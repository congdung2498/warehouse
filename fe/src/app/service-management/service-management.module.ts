import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ServiceManagementRoutingModule } from './service-management-routing.module';
import { ServiceMenuComponent } from './service-menu/service-menu.component';
import { ServiceStatisticsComponent } from './service-statistics/service-statistics.component';
import { FieldsetModule } from 'primeng/fieldset';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { CalendarModule } from 'primeng/calendar';
import { TableModule } from 'primeng/table';
import { ServiceDataService } from './service-data.service';
import { InputTextModule } from 'primeng/inputtext';
import { RadioButtonModule } from 'primeng/radiobutton';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { TooltipModule } from 'primeng/tooltip';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { KeyFilterModule } from 'primeng/keyfilter';
import { TreeModule } from 'primeng/tree';
import { CheckboxModule } from 'primeng/checkbox';
import { DialogModule } from 'primeng/dialog';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { StationeryMenuComponent } from './stationery-menu/stationery-menu.component';
import { MultiSelectModule } from 'primeng/multiselect';
import {RatingModule} from 'primeng/rating';
import { FileUploadModule } from 'primeng/components/fileupload/fileupload';
import {SignVofficeComponent} from "./SignVoffice/sign-voffice.component";
@NgModule({
  imports: [
    CommonModule,
    ServiceManagementRoutingModule,
    FormsModule,
    FieldsetModule,
    DropdownModule,
    CalendarModule,
    TableModule,
    RadioButtonModule,
    AutoCompleteModule,
    InputTextModule,
    TooltipModule,
    ScrollPanelModule,
    AutoCompleteModule,
    KeyFilterModule,
    TreeModule,
    CheckboxModule,
    DialogModule,
    OverlayPanelModule,
    MultiSelectModule,
    FileUploadModule,
    RatingModule
  ],
  declarations: [ServiceMenuComponent, ServiceStatisticsComponent, StationeryMenuComponent, SignVofficeComponent],
  providers: [ServiceDataService]
})
export class ServiceManagementModule { }
