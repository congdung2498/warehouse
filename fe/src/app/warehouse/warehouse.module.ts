import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { WarehouseRoutingModule } from './warehouse-routing.module';
import { WarehouseManagementComponent } from './warehouse-management/warehouse-management.component';
import { FilebucketComponent } from './filebucket/filebucket.component';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { WarehouseService } from './warehouse.service';
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
import {CheckboxModule, MultiSelectModule, TreeModule} from "primeng/primeng";
import {RatingModule} from "primeng/rating";
import { WarehouseReportComponent } from './warehouse-report/warehouse-report.component';
import { DocumentReportComponent } from './document-report/document-report.component';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DocumentBoxSearchComponent } from './document-box-search/document-box-search.component';
import { DocumentBoxSearchService } from './document-box-search.service';
import { HighlightSearch } from './document-box-search/highlight.pipe.component';
import { ExcelHelperService } from './excel.helper.service';
import { OfficialDispatchUploadComponent } from './official-dispatch-upload/official-dispatch-upload.component';
import { FolderVoucherUploadComponent } from './folder-voucher-upload/folder-voucher-upload.component';
import { FolderOfficialDispatchUploadComponent } from './folder-official-dispatch-upload/folder-official-dispatch-upload.component';
import { FolderRetailProjectComponent } from './folder-retail-project-upload/folder-retail-project-upload.component';
import { FolderProjectUploadComponent } from './folder-project-upload/folder-project-upload.component';
import { ProjectUploadComponent } from './project-upload/project-upload.component';
import { DocumentBoxSearchDetailDialogComponent } from './document-box-search-detail-dialog/document-box-search-detail-dialog.component';
@NgModule({
  imports: [
    CommonModule,
    WarehouseRoutingModule,
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
        ConfirmDialogModule,
        TreeModule
  ],
  declarations: [
    WarehouseManagementComponent,
    FilebucketComponent,
    WarehouseReportComponent,
    DocumentReportComponent,
    DocumentBoxSearchComponent,
    HighlightSearch,
    ProjectUploadComponent,
    FolderProjectUploadComponent,
    OfficialDispatchUploadComponent,
    FolderOfficialDispatchUploadComponent,
    FolderVoucherUploadComponent,
    FolderRetailProjectComponent,
    DocumentBoxSearchDetailDialogComponent
  ],
  providers: [
    WarehouseService,
    DocumentBoxSearchService,
    ExcelHelperService
  ]
})
export class WarehouseModule { }
