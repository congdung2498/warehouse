import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FilebucketComponent } from './filebucket/filebucket.component';
import { WarehouseManagementComponent } from './warehouse-management/warehouse-management.component';
import { WarehouseReportComponent } from './warehouse-report/warehouse-report.component';
import { DocumentReportComponent } from './document-report/document-report.component';
import { DocumentBoxSearchComponent } from './document-box-search/document-box-search.component';
import { OfficialDispatchUploadComponent } from './official-dispatch-upload/official-dispatch-upload.component';
import { FolderOfficialDispatchUploadComponent } from './folder-official-dispatch-upload/folder-official-dispatch-upload.component';
import { FolderVoucherUploadComponent } from './folder-voucher-upload/folder-voucher-upload.component';
import { FolderRetailProjectComponent } from './folder-retail-project-upload/folder-retail-project-upload.component';
import { FolderProjectUploadComponent } from './folder-project-upload/folder-project-upload.component';
import { ProjectUploadComponent } from './project-upload/project-upload.component';

const routes: Routes = [
  {
    path: 'fileBucket',
    component: FilebucketComponent
  },
  {
    path: 'warehouse-management',
    component: WarehouseManagementComponent
  },
  {
    path: 'warehouse-report',
    component: WarehouseReportComponent
  },
  {
    path: 'document-report',
    component: DocumentReportComponent
  },
  {
    path: 'document-box-search',
    component: DocumentBoxSearchComponent
  },
  {
    path: 'project-upload',
    component: ProjectUploadComponent
  },
  {
    path: 'folder-project-upload',
    component: FolderProjectUploadComponent
  },
  {
    path: 'official-dispatch',
    component: OfficialDispatchUploadComponent
  },
  {
    path: 'folder-official-dispatch-upload',
    component: FolderOfficialDispatchUploadComponent
  },
  {
    path: 'folder-voucher-upload',
    component: FolderVoucherUploadComponent
  },
  {
    path: 'folder-retail-project-upload',
    component: FolderRetailProjectComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WarehouseRoutingModule { }
