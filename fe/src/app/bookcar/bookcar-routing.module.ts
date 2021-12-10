import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AddTeamCarComponent } from "./teamcar/add-team-car.component";
import { ReportBookcarComponent } from "./report-bookcar/report-bookcar.component";
import { CategoryCarComponent } from "./category-car/category-car.component";
import { CategoryDriverComponent } from "./category-driver/category-driver.component";
import {RequestCarComponent} from "./request-car/request-car.component";
import {CarFreeTimeComponent} from "./car-free-time/car-free-time.component";


const routes: Routes = [
  {
    path: "reportBookCar",
    component: ReportBookcarComponent
  },
  {
    path: "addTeamCar",
    component: AddTeamCarComponent
  },
  {
    path: "addCategoryCar",
    component: CategoryCarComponent
  },
  {
    path: "addCategoryDriver",
    component: CategoryDriverComponent
  },
  {
    path: "addRequestCar",
    component: RequestCarComponent
  },
  {
    path: "carFreeTimes",
    component: CarFreeTimeComponent
  }
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookcarRoutingModule { }
