import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AngularFontAwesomeModule} from 'angular-font-awesome';
import {CalendarModule} from 'primeng/calendar';
import {DropdownModule} from 'primeng/dropdown';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
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
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {FolderRoutingModule} from './folder-routing.module';
import {FolderSearchComponent} from './folder-search-by-qr-code/folder-search.component';
import {FolderService} from './folder.service';
import { FolderDetailsComponent } from './folder-details/folder-details.component';

@NgModule({
  imports: [
    CommonModule,
    FolderRoutingModule,
    ReactiveFormsModule,
    CheckboxModule,
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
    DialogModule,
    MultiSelectModule,
    RatingModule,
    InputTextareaModule,
    ConfirmDialogModule
  ],
  declarations: [
    FolderSearchComponent, FolderDetailsComponent
  ],
  providers:[
    FolderService,
  ]
})
export class FolderModule { }
