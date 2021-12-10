import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { LoginRoutingModule } from "./login-routing.module";
import { LoginComponent } from "./login/login.component";
import { InputTextModule } from "primeng/inputtext";
import { MessageService } from "primeng/components/common/api";
import { ButtonModule } from "primeng/button";
import { FormsModule } from "@angular/forms";
import { AuthService } from "../shared/auth.service";
import { LoginService } from "./login.service";
import { LoginAsGuardComponent } from './login-as-guard/login-as-guard.component';
import { ChangePassWordComponent } from './login-as-guard/change-password/change-password.component';
import { FieldsetModule } from 'primeng/fieldset';
import { DialogModule } from 'primeng/dialog';

@NgModule({
  imports: [
    CommonModule,
    LoginRoutingModule,
    InputTextModule,
    FormsModule,
    ButtonModule,
    FieldsetModule,
    DialogModule
  ],
  declarations: [LoginComponent, LoginAsGuardComponent, ChangePassWordComponent],

  providers: [LoginService, AuthService],

  exports: [LoginComponent]
})
export class LoginModule {}
