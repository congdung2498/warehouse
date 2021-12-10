import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ServiceMenuComponent } from './service-menu/service-menu.component';
import { ServiceStatisticsComponent } from './service-statistics/service-statistics.component';
import { StationeryMenuComponent } from './stationery-menu/stationery-menu.component';
import {SignVofficeComponent} from "./SignVoffice/sign-voffice.component";


const routes: Routes = [
  {
    path: 'ServiceMenu',
    component: ServiceMenuComponent
  },
  {
    path: 'ServiceStatistics',
    component: ServiceStatisticsComponent
  },
  {
    path: 'StationeryMenu',
    component: StationeryMenuComponent
  },
  {
    path: 'SignVoffice',
    component: SignVofficeComponent
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ServiceManagementRoutingModule { }
