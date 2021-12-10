import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { LoginService } from '../login.service';
import { TokenStorage } from '../../shared/token.storage';
import { UserInfo } from '../../shared/userInfo';
import { MessageService } from 'primeng/components/common/api';
import { Message } from 'primeng/components/common/api';

import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../shared/auth.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-login-as-guard',
  templateUrl: './login-as-guard.component.html',
  styleUrls: ['./login-as-guard.component.css']
})
export class LoginAsGuardComponent implements OnInit {
  returnUrl: string;
  ticket: string;
  loading = false;
  username: string;
  password: string;
  isLoginError = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private login: AuthService,
    private tokenStorage: TokenStorage,
    private title: Title,
    private messageService: MessageService
  ) {
    this.username = '';
    this.password = '';
    this.title.setTitle('Đăng Nhập - VTNet');
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.returnUrl = params['returnUrl'];
    });
    if (this.returnUrl === null || this.returnUrl === undefined) {
      this.returnUrl = this.route.snapshot.queryParamMap.get('returnUrl');
    }
  }

  OnSubmit() {
    let checkValidate = true;
    if (
      this.username === undefined ||
      this.username === null ||
      this.username.trim().length === 0
    ) {
      checkValidate = false;
      this.messageService.add({
        severity: 'error',
        summary: 'Lỗi:',
        detail: 'Tên đăng nhập không được để trống'
      });
      return;
    }
    if (
      this.password === undefined ||
      this.password === null ||
      this.password.trim().length === 0
    ) {
      checkValidate = false;
      this.messageService.add({
        severity: 'error',
        summary: 'Lỗi:',
        detail: 'Mật khẩu không được để trống'
      });
    }
    if (checkValidate) {
      this.loading = true;
      this.login.attemptAuth(this.username, this.password).subscribe(
        result => {
          // Handle result
          this.tokenStorage.saveToken(
            result['access_token'],
            result['refresh_token'],
            result['userInfor']
          );
        },
        error => {
          this.messageService.add({
            severity: 'error',
            summary: 'Lỗi:',
            detail: 'Tên đăng nhập hoặc mật khẩu không đúng'
          });
          this.loading = false;
        },
        () => {
          console.log(this.returnUrl);
          // 'onCompleted' callback.
          // No errors, route to new page here
          if (this.returnUrl === null || this.returnUrl === undefined) {
            this.router.navigateByUrl('/');
            return;
          }
          this.router.navigateByUrl(this.returnUrl);
        }
      );
    }
  }
}
