import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { LoginAsGuardComponent } from './login-as-guard/login-as-guard.component';
import { ChangePassWordComponent } from './login-as-guard/change-password/change-password.component';

const routes: Routes = [
  {
    path: 'vebinh',
    component: LoginAsGuardComponent
  },
  {
    path: 'doimatkhau',
    component: ChangePassWordComponent
  },
  {
    path: '',
    component: LoginComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoginRoutingModule {}
