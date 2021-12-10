import { NgModule } from '@angular/core';
import { Routes, RouterModule, PreloadAllModules } from '@angular/router';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AuthGuard } from './shared/auth.guard';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';

const routes: Routes = [
  {
    path: 'login',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    loadChildren: './login/login.module#LoginModule'
  },
  {
    path: 'book-car',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    runGuardsAndResolvers: 'always',
    loadChildren: './bookcar/bookcar.module#BookcarModule'
  },
  {
    path: 'check-in-out',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    runGuardsAndResolvers: 'always',
    loadChildren: './check-in-out/check-in-out.module#CheckInOutModule'
  },
  {
    path: 'service-management',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    runGuardsAndResolvers: 'always',
    loadChildren:
      './service-management/service-management.module#ServiceManagementModule'
  },
  {
    path: 'kitchenManager',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    runGuardsAndResolvers: 'always',
    loadChildren:
      './kitchen-management/kitchen-management.module#KitchenManagementModule'
  },
  {
    path: 'stationery',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    runGuardsAndResolvers: 'always',
    loadChildren:
      './stationery/stationery.module#StationeryModule'
  },
  {
    path: 'barcode',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    runGuardsAndResolvers: 'always',
    loadChildren: './barcode/barcode.module#BarcodeModule'
  },
  {
    path: 'setting',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    runGuardsAndResolvers: 'always',
    loadChildren:
      './setting/setting.module#SettingModule'
  },
  {
    path: 'warehouse',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    runGuardsAndResolvers: 'always',
    loadChildren: './warehouse/warehouse.module#WarehouseModule'
  },
  {
    path: 'folder',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    runGuardsAndResolvers: 'always',
    loadChildren: './folder/folder.module#FolderModule'
  },
  {
    path: '',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    runGuardsAndResolvers: 'always',
    component: WelcomePageComponent
  },
  { path: '**', canActivate: [AuthGuard], canActivateChild: [AuthGuard], component: PageNotFoundComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule {}
