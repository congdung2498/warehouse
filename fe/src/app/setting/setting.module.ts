import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SettingRoutingModule } from './setting-routing.module';
import { PlaceConfigComponent } from './place-config/place-config.component';
import { FormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
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
import { SettingService } from './setting.service';
import { VersionConfigComponent } from './version-config/version-config.component';
import { ParamConfigComponent } from './param-config/param-config.component';
import { ParamUnitConfigComponent } from './param-unit-config/param-unit-config.component';
import { ParamProcessConfigComponent } from './param-process-config/param-process-config.component';
import { ParamWebConfigComponent } from './param-web-config/param-web-config.component';
import { ParamTypeConfigComponent } from './param-type-config/param-type-config.component';
import {RadioButtonModule} from "primeng/primeng";

@NgModule({
  imports: [
    CommonModule,
    SettingRoutingModule,
    FormsModule,
    FieldsetModule,
    DropdownModule,
    CalendarModule,
    TabViewModule,
    TreeModule,
    TableModule,
    AutoCompleteModule,
    ScrollPanelModule,
    RadioButtonModule,
    CheckboxModule,
    InputTextModule,
    InputTextareaModule,
    ButtonModule,
    AccordionModule,
    KeyFilterModule,
    TooltipModule,
    MultiSelectModule,
    ToggleButtonModule,
    DialogModule
  ],
  declarations: [
    PlaceConfigComponent,
    VersionConfigComponent,
    ParamConfigComponent,
    ParamUnitConfigComponent,
    ParamProcessConfigComponent,
    ParamWebConfigComponent,
    ParamTypeConfigComponent
  ],
  providers: [SettingService]
})
export class SettingModule { }
