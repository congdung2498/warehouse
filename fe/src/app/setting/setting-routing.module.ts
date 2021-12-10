import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PlaceConfigComponent } from './place-config/place-config.component';
import { VersionConfigComponent } from './version-config/version-config.component';
import {ParamConfigComponent} from "./param-config/param-config.component";

const routes: Routes = [
  {
    path: 'place-config',
    component: PlaceConfigComponent
  },
  {
    path: 'version-config',
    component: VersionConfigComponent
  },
  {
    path: 'param-config',
    component: ParamConfigComponent
  }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SettingRoutingModule { }
