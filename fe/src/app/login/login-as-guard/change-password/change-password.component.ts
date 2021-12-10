import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../shared/auth.service';
import { TokenStorage } from '../../../shared/token.storage';
import { Title } from '@angular/platform-browser';
import { MessageService, ConfirmationService } from 'primeng/components/common/api';
import { LoginService } from '../../login.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePassWordComponent implements OnInit {

  returnUrl: String;
  oldPassword: String;
  newPassword: String;
  newPasswordValidate: String;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private login: LoginService,
    private tokenStorage: TokenStorage,
    private title: Title,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
  ) {
    this.returnUrl = null;
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
    this.confirmationService.confirm({
      message: 'Đồng chí có muốn đổi mật khẩu của tài khoản cảnh vệ?',
      header: 'Đổi mật khẩu',
      icon: 'pi pi-question-circle',
      acceptLabel: 'Đổi',
      rejectLabel: 'Hủy bỏ',
      accept: () => {
        if (!this.oldPassword) {
          this.messageService.add(
            {
              severity: 'error',
              summary: 'Lỗi',
              detail: 'Mật khẩu cũ không thể để trống!'
            });
            return;
        }
        if (!this.newPassword || this.newPassword.length < 6) {
          this.messageService.add(
            {
              severity: 'error',
              summary: 'Lỗi',
              detail: 'Mật khẩu mới không thể để trống!'
            });
            return;
        }
        if (this.newPasswordValidate !== this.newPassword) {
          this.messageService.add(
            {
              severity: 'error',
              summary: 'Lỗi',
              detail: 'Mật khẩu mới và nhập lại mật khẩu mới không giống nhau!'
            });
            return;
        }
        if (this.oldPassword === this.newPassword) {
          this.messageService.add(
            {
              severity: 'error',
              summary: 'Lỗi',
              detail: 'Mật khẩu cũ và mật khẩu mới không thể trùng nhau!'
            });
            return;
        }
        this.login.changeGuardPassword(this.oldPassword, this.newPassword).subscribe(
          result => {
           if (result['status'] === 0) {
            this.messageService.add(
              {
                severity: 'error',
                summary: 'Lỗi',
                detail: 'Mật khẩu cũ không chính xác!'
              });
          } else if (result['status'] === 1) {
            this.messageService.add(
              {
                severity: 'info',
                summary: 'Thông báo',
                detail: 'Đổi mật khẩu thành công!'
              });
             this.router.navigateByUrl('/');
           } else {
            this.messageService.add(
              {
                severity: 'error',
                summary: 'Lỗi',
                detail: 'Xảy ra lỗi không xác định!'
              });
           }
          },
          error => {
            this.messageService.add(
              {
                severity: 'error',
                summary: 'Lỗi',
                detail: 'Xảy ra lỗi không xác định hoặc mất kết nối internet, vui lòng thử lại!'
              });
          }
        );
      }
    });
  }
}
