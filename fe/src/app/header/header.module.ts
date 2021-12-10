import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HeaderRoutingModule } from './header-routing.module';
import { HeaderComponent } from './header/header.component';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { TokenStorage } from '../shared/token.storage';
@NgModule({
  imports: [
    CommonModule,
    HeaderRoutingModule,
    AngularFontAwesomeModule
  ],
  declarations: [HeaderComponent],
  providers: [TokenStorage],
  exports: [HeaderComponent]
})
export class HeaderModule { }
