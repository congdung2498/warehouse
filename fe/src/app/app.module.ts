import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderModule } from './header/header.module';
import { LoginModule } from './login/login.module';
import { HttpClientModule } from '@angular/common/http';
import { KitchenManagementModule } from './kitchen-management/kitchen-management.module';
import { AuthService } from './shared/auth.service';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AuthGuard } from './shared/auth.guard';
import { Title } from '@angular/platform-browser';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';
import { ToastModule } from 'primeng/toast';
import { HTTPStatus, HTTPListener, OauthHttpInterceptor } from './shared/oauth-http-interceptor';

/* "Barrel" of Http Interceptors */
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';
import { ConfirmationService, MessageService } from 'primeng/api';
import {SharedModule} from "primeng/shared";
import {PanelModule} from "primeng/panel";
import {TabViewModule} from "primeng/primeng";


const RxJS_Services = [HTTPListener, HTTPStatus];

@NgModule({
  declarations: [AppComponent, PageNotFoundComponent, WelcomePageComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HeaderModule,
    LoginModule,
    KitchenManagementModule,
    ToastModule,
    ConfirmDialogModule,
    ProgressSpinnerModule,
    MessageModule,
    MessagesModule,
    SharedModule,
    TabViewModule

  ],
  providers: [
    RxJS_Services,
    { provide: HTTP_INTERCEPTORS, useClass: OauthHttpInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: HTTPListener, multi: true },
    AuthService,
    AuthGuard,
    Title,
    ConfirmationService,
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
