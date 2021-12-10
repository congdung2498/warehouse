import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { BarcodeComponent } from "./barcode/barcode.component";
import { TinboxComponent } from "./tinbox/tinbox.component";
import { RackprintComponent } from "./rackprint/rackprint.component";
const routes: Routes = [
  {
    path: "barcode",
    component: BarcodeComponent
  },
  {
    path: "tinbox",
    component: TinboxComponent
  },
  {
    path: "rackprint",
    component: RackprintComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BarcodeRoutingModule {}
