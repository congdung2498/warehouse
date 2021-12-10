import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KitchenManagementRoutingModule } from './kitchen-management-routing.module';
import { ByKitchenComponent } from './by-kitchen/by-kitchen.component';
import { ByEmployeeComponent } from './by-employee/by-employee.component';
import { DayoffconfigComponent } from './dayoffconfig/dayoffconfig.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { TableModule } from 'primeng/table';
import { KitchenManagementService } from './kitchen-management.service';
import { KitchenConfigComponent } from './kitchen-config/kitchen-config.component';
import { DialogModule } from 'primeng/dialog';
import { FieldsetModule } from 'primeng/fieldset';
import { DropdownModule } from 'primeng/dropdown';
import { CalendarModule } from 'primeng/calendar';
import { TabViewModule } from 'primeng/tabview';
import { TreeModule } from 'primeng/tree';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ButtonModule } from 'primeng/button';
import { AccordionModule } from 'primeng/accordion';
import { KeyFilterModule } from 'primeng/keyfilter';
import { TooltipModule } from 'primeng/tooltip';
import { MultiSelectModule } from 'primeng/multiselect';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { DishConfigComponent } from './dish-config/dish-config.component';
import { MenuConfigComponent } from './menu-config/menu-config.component';
import { PaginatorModule } from 'primeng/paginator';
import { AbbreviationsComponent } from './abbreviations/abbreviations.component';
import { NewAbbreviationsComponent } from './new-abbreviations/new-abbreviations.component';
import {RadioButtonModule} from "primeng/primeng";
import { LunchComponentComponent } from './lunch-component/lunch-component.component';
import { FileUploadModule } from 'primeng/components/fileupload/fileupload';
@NgModule({
  imports: [
    ReactiveFormsModule,
    CommonModule,
    KitchenManagementRoutingModule,
    FormsModule,
    FieldsetModule,
    DropdownModule,
    CalendarModule,
    TabViewModule,
    TreeModule,
    TableModule,
    AutoCompleteModule,
    ScrollPanelModule,
    CheckboxModule,
    InputTextModule,
    InputTextareaModule,
    ButtonModule,
    AccordionModule,
    KeyFilterModule,
    TooltipModule,
    MultiSelectModule,
    ToggleButtonModule,
    DialogModule,
    PaginatorModule,
    RadioButtonModule,
    FileUploadModule

  ],
  declarations: [ByKitchenComponent,
    ByEmployeeComponent,
    DayoffconfigComponent,
    KitchenConfigComponent,
    DishConfigComponent,
    MenuConfigComponent,
    AbbreviationsComponent,
    NewAbbreviationsComponent,
    LunchComponentComponent
  ],
  providers: [KitchenManagementService]
})
export class KitchenManagementModule { }
