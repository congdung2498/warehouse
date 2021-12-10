import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ByEmployeeComponent } from '../kitchen-management/by-employee/by-employee.component';
import { ByKitchenComponent } from '../kitchen-management/by-kitchen/by-kitchen.component';
import { DayoffconfigComponent } from '../kitchen-management/dayoffconfig/dayoffconfig.component';
import { KitchenConfigComponent } from './kitchen-config/kitchen-config.component';
import { DishConfigComponent } from './dish-config/dish-config.component';
import {MenuConfigComponent} from "./menu-config/menu-config.component";
import {AbbreviationsComponent} from "./abbreviations/abbreviations.component";
import {NewAbbreviationsComponent} from "./new-abbreviations/new-abbreviations.component";

const routes: Routes = [
  {
    path: 'report-by-employee',
    component: ByEmployeeComponent
  },
  {
    path: 'report-by-kitchen',
    component: ByKitchenComponent
  },
  {
    path: 'dayoff-config',
    component: DayoffconfigComponent
  },
  {
    path: 'kitchen-config',
    component: KitchenConfigComponent
  },
  {
    path: 'dish-config',
    component: DishConfigComponent
  },
  {
    path: 'menu-config',
    component: MenuConfigComponent
  },
  {
    path: 'abbreviations-config',
    component: AbbreviationsComponent
  },
  {
    path: 'add-new-abbreviations',
    component: NewAbbreviationsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KitchenManagementRoutingModule { }
