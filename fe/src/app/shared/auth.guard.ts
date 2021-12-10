import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, CanActivateChild } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { TokenStorage } from './token.storage';
import { Constants } from './containts';
import { MessageService } from 'primeng/components/common/api';

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(private tokenStorage: TokenStorage, private router: Router, private message: MessageService) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.checkLogin(state.url);
  }

  canActivateChild(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
      return this.checkLogin(state.url);
  }

  checkLogin(url: string): boolean {
    console.log(url);
    if (url.includes('login') && !this.tokenStorage.isLogged()) {
      return true;
    }
    if (this.tokenStorage.isLogged() && (url === '/login' || url === '/login/vebinh')) {
      this.message.add({
        severity: 'info',
        summary: 'Thông báo:',
        detail: 'Phiên làm việc trước vẫn còn tồn tại!'
      });
      this.router.navigate(['/'], {});
      return false;
    }
    if (this.tokenStorage.isLogged()) {
      if (this.tokenStorage.checkPermission(url)) {
        return true;
      } else if (this.tokenStorage.isPermission(url)) {
        return true;
      } else {
        /** homepage */
        this.message.add({
          severity: 'error',
          summary: 'Lỗi:',
          detail: 'Không có quyền truy cập!'
        });
        this.router.navigate(['/'], {});
        return false;
      }
    }

    // Navigate to the login page with extras
    this.tokenStorage.clearStorage();

    /** old login */
    // this.router.navigate(['/login'], { queryParams: { 'returnUrl': url }});

    /** passport login */
    window.location.href = Constants.LOGIN_URL;

    return false;
  }
}
