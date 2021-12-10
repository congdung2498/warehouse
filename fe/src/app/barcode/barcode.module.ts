import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AngularFontAwesomeModule} from 'angular-font-awesome';

/* import module from primeNG */
import {CalendarModule} from 'primeng/calendar';
import {DropdownModule} from 'primeng/dropdown';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {FormsModule, ReactiveFormsModule} from '@angular/forms'; // <-- NgModel lives here
import {TableModule} from 'primeng/table';

import {RadioButtonModule} from 'primeng/radiobutton';


import {InputTextModule} from 'primeng/inputtext';
import {AutoCompleteModule} from 'primeng/autocomplete';
import {TooltipModule} from 'primeng/tooltip';
import {KeyFilterModule} from 'primeng/keyfilter';
import {FieldsetModule} from 'primeng/fieldset';

import {ScrollPanelModule} from 'primeng/scrollpanel';
import {DialogModule} from 'primeng/dialog';
import {CheckboxModule, MultiSelectModule} from "primeng/primeng";

import {RatingModule} from "primeng/rating";
import { BarcodeRoutingModule } from './barcode-routing.module';
import { BarcodeComponent } from './barcode/barcode.component';
import { BarcodeService } from './barcode.service';
import { NgxBarcodeModule } from 'ngx-barcode';
import { NgxQRCodeModule } from 'ngx-qrcode2';
import { ExportAsModule } from 'ngx-export-as';
import { QRCodeModule } from 'angularx-qrcode';
import { TinboxComponent } from './tinbox/tinbox.component';
import { RackprintComponent } from './rackprint/rackprint.component';

@NgModule({
  imports: [
    CommonModule,
    BarcodeRoutingModule,
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
    FieldsetModule,
    ScrollPanelModule,
    CheckboxModule,
    DialogModule,
    MultiSelectModule,
    RatingModule,
    InputTextareaModule,
    NgxBarcodeModule,
    NgxQRCodeModule,
    ExportAsModule,
    QRCodeModule 
  ],
  providers:[
    BarcodeService
  ],
  declarations: [BarcodeComponent, TinboxComponent, RackprintComponent
  ]
})
export class BarcodeModule { }
