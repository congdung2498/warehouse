import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { StationeryManagementComponent } from './stationery-management/stationery-management.component';
import { ReceiverComponent } from './receiver/receiver.component';
import { ReportStationeryComponent } from './report-stationery/report-stationery.component';
import {RequestStationeryComponent} from "./request-stationery/request-stationery.component";

import {RattingListRequestComponent} from "./request-stationery/list-request/ratting-list-request/ratting-list-request.component";
import {EditListRequestComponent} from "./request-stationery/list-request/edit-list-request/detail-edit-list-request/edit-list.component";
import {AddStationeryComponent} from "./add-stationery/add-stationery.component";





const routes: Routes = [
  {
    path: "stationeryManagement",
    component: StationeryManagementComponent
  },
  {
    path: "receiver",
    component: ReceiverComponent
  },
  {
    path: "reportStationery",
    component: ReportStationeryComponent
  },
  {
    path: "requestStationery",
    component: RequestStationeryComponent
  },
  {
    path: "editListRequest",
    component: EditListRequestComponent
  },
  {
    path: "rattingListRequest",
    component: RattingListRequestComponent
  },
  {
    path: "addStationery",
    component: AddStationeryComponent
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StationeryRoutingModule { }
