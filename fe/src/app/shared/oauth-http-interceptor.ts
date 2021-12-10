import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import { Constants } from './containts';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import 'rxjs/add/observable/throw';
import { MessageService } from 'primeng/components/common/api';
import { AuthService } from './auth.service';

@Injectable()
export class OauthHttpInterceptor implements HttpInterceptor {

    constructor(private message: MessageService, private auth: AuthService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        console.log('intercepted request ... ' + req.url);

        try {
            if (!req.url.includes('com/viettel/vtnet360/vt00/vt000000/03') && !req.url.includes('com/viettel/vtnet360/vt00/vt000000/08')) {
                // Clone the request to add the new header.
                const authReq = req.clone({ headers: req.headers.set('Authorization', 'bearer ' + this.getToken()) });
                console.log('Sending request with new header now ...');
                return next.handle(authReq)
                    .catch((error, caught) => {
                        if (error instanceof HttpErrorResponse) {
                            console.log((<HttpErrorResponse>error).status);
                            switch ((<HttpErrorResponse>error).status) {
                                case 0:
                                    this.message.add({
                                        severity: 'info',
                                        summary: 'Thông báo:',
                                        detail: 'Hệ thống không có phản hồi hoặc mất kết nối mạng!'
                                    });
                                    break;
                                case 400:
                                    this.message.add({
                                        severity: 'error',
                                        summary: 'Lỗi:',
                                        detail: 'Yêu cầu gửi lên có lỗi cú pháp!'
                                    });
                                    break;
                                case 401:
                                    this.message.add({
                                        severity: 'info',
                                        summary: 'Thông báo:',
                                        detail: 'Phiên làm việc đã hết hạn hoặc Đồng chí không có quyền thực hiện yêu cầu này!'
                                    });
                                    this.signOut();
                                    return Observable.throw(error);
                                case 403:
                                    this.message.add({
                                        severity: 'error',
                                        summary: 'Lỗi:',
                                        detail: 'Yêu cầu bị chặn bởi hệ thống!'
                                    });
                                    break;
                                case 404:
                                    this.message.add({
                                        severity: 'error',
                                        summary: 'Lỗi:',
                                        detail: 'Không thể xác định được kết nối tới hệ thống!'
                                    });
                                    break;
                                case 500:
                                    this.message.add({
                                        severity: 'error',
                                        summary: 'Lỗi:',
                                        detail: 'Hệ thống server gặp sự cố, vui lòng báo lại cho nhân viên bảo trì!'
                                    });
                                    break;
                            }
                        }
                        return Observable.throw(error);
                    }) as any;
            }
        } catch (error) {
            // this.signOut();
            return Observable.throw(error);
        }

        return next.handle(req) as any;
    }

    addAuthenticationToken(request) {
        // Get access token from Local Storage
        const accessToken = this.getToken();

        // If access token is null this means that user is not logged in
        // And we return the original request
        if (!accessToken) {
            return request;
        }

        // We clone the request, because the original request is immutable
        return request.clone({
            setHeaders: {
                Authorization: this.getToken()
            }
        });
    }

    signOut() {
        sessionStorage.removeItem(Constants.TOKEN_KEY);
        sessionStorage.removeItem(Constants.REFRESH_TOKEN_KEY);
        sessionStorage.removeItem(Constants.USER_INFO);
        sessionStorage.clear();
        localStorage.setItem('clearSessionStorage', 'PMQT');
        localStorage.removeItem('clearSessionStorage');
        window.location.href = Constants.LOGOUT_URL;
    }

    public saveToken(token: string, refreshtoken: string) {
        sessionStorage.removeItem(Constants.TOKEN_KEY);
        sessionStorage.setItem(Constants.TOKEN_KEY, token);
        sessionStorage.removeItem(Constants.REFRESH_TOKEN_KEY);
        sessionStorage.setItem(Constants.REFRESH_TOKEN_KEY, refreshtoken);
    }

    public getToken(): string {
        return sessionStorage.getItem(Constants.TOKEN_KEY);
    }

    public getRefreshToken(): string {
        return sessionStorage.getItem(Constants.REFRESH_TOKEN_KEY);
    }

    public isTokenVaild(): boolean {
        return sessionStorage.getItem(Constants.TOKEN_KEY).length < 1 ? false : true;
    }
}

@Injectable()
export class HTTPStatus {
  private requestInFlight$: BehaviorSubject<boolean>;
  constructor() {
    this.requestInFlight$ = new BehaviorSubject(false);
  }

  setHttpStatus(inFlight: boolean) {
    this.requestInFlight$.next(inFlight);
  }

  getHttpStatus(): Observable<boolean> {
    return this.requestInFlight$.asObservable();
  }
}

@Injectable()
export class HTTPListener implements HttpInterceptor {

    private requests: HttpRequest<any>[] = [];


  constructor(private status: HTTPStatus) {}

  removeRequest(req: HttpRequest<any>) {
    const i = this.requests.indexOf(req);
    if (i >= 0) {
      this.requests.splice(i, 1);
    }
    this.status.setHttpStatus(this.requests.length > 0);
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.requests.push(req);
    this.status.setHttpStatus(true);
    // We create a new observable which we return instead of the original
    return Observable.create(observer => {
      // And subscribe to the original observable to ensure the HttpRequest is made
      const subscription = next.handle(req)
        .subscribe(
        event => {
          if (event instanceof HttpResponse) {
            this.removeRequest(req);
            observer.next(event);
          }
        },
        err => { this.removeRequest(req); observer.error(err); },
        () => { this.removeRequest(req); observer.complete(); });
      // return teardown logic in case of cancelled requests
      return () => {
        this.removeRequest(req);
        subscription.unsubscribe();
      };
    });
  }
}
