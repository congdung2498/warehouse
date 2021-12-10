import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddCardIdComponent } from './add-card-id/add-card-id.component';
import {CheckingComponent} from "./checking/checking.component";

const routes: Routes = [
  {
    path: 'addCarId',
    component: AddCardIdComponent
  },
  {
    path: 'checking',
    component: CheckingComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CheckInOutRoutingModule { }
