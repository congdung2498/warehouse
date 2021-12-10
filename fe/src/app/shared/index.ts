/* "Barrel" of Http Interceptors */
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { OauthHttpInterceptor, HTTPListener } from './oauth-http-interceptor';

/** Http interceptor providers in outside-in order */
export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: OauthHttpInterceptor, multi: true },
  { provide: HTTP_INTERCEPTORS, useClass: HTTPListener, multi: true }
];
