import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CheckInOutRoutingModule } from './check-in-out-routing.module';
import { AddCardIdComponent } from './add-card-id/add-card-id.component';

/* import fontawesome */
import { AngularFontAwesomeModule } from 'angular-font-awesome';

/* import module from primeNG */
// import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import {FormsModule, ReactiveFormsModule} from '@angular/forms'; // <-- NgModel lives here
import { TableModule } from 'primeng/table';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { CheckboxModule } from 'primeng/checkbox';
import { TooltipModule } from 'primeng/tooltip';
import { ReportserviceService } from './reportservice.service';
import { KeyFilterModule } from 'primeng/keyfilter';
import { FieldsetModule } from 'primeng/fieldset';
import { DataTableModule } from 'primeng/datatable';
import { PaginatorModule } from 'primeng/paginator';
import { MultiSelectModule } from 'primeng/multiselect';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { DialogModule } from 'primeng/dialog';
import { TreeModule } from 'primeng/tree';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { KitchenManagementService } from '../kitchen-management/kitchen-management.service';
import {InOutEmployeeService} from "./inOutEmployeeService.service";
import { CheckingComponent } from './checking/checking.component';
import {UnitTreeModel} from "../../common/unittree";
import {ConfirmDialogModule, InputTextModule} from "primeng/primeng";

@NgModule({
  imports: [
    CommonModule,
    CheckInOutRoutingModule,
    CalendarModule,
    FormsModule,
    DropdownModule,
    AngularFontAwesomeModule,
    TableModule,
    AutoCompleteModule,
    CheckboxModule,
    TooltipModule,
    KeyFilterModule,
    FieldsetModule,
    DataTableModule,
    PaginatorModule,
    OverlayPanelModule,
    DialogModule,
    TreeModule,
    ScrollPanelModule,
    MultiSelectModule,
    ConfirmDialogModule,
    InputTextModule
  ],
  declarations: [AddCardIdComponent, CheckingComponent],
  providers: [ReportserviceService, KitchenManagementService, InOutEmployeeService, UnitTreeModel]
})
export class CheckInOutModule { }
