import { Component, OnInit } from "@angular/core";
import { HttpErrorResponse } from "@angular/common/http";
import { LoginService } from "../login.service";
import { TokenStorage } from "../../shared/token.storage";
import { UserInfo } from "../../shared/userInfo";
import { MessageService } from "primeng/components/common/api";
import { Message } from "primeng/components/common/api";

import { Router, ActivatedRoute } from "@angular/router";
import { AuthService } from "../../shared/auth.service";
import { Title } from "@angular/platform-browser";
import { Constants } from "../../shared/containts";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
  providers: [TokenStorage]
})
export class LoginComponent implements OnInit {
  returnUrl: string;
  ticket: string;
  loading = false;
  msgs: Message[] = [];
  username: string;
  password: string;
  isLoginError = false;
  display: Boolean = false;
  message: String;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private login: AuthService,
    private tokenStorage: TokenStorage,
    private title: Title,
    private messageService: MessageService
  ) {
    this.username = "";
    this.password = "";
    this.title.setTitle("Đăng Nhập - VTNet");
  }

  clear() {
    this.msgs = [];
  }
  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.returnUrl = params["returnUrl"];
      this.ticket = params["ticket"];
      // if ((this.ticket !== undefined) && !this.tokenStorage.isLogged()) {
      //   this.LoginByTicket();
      // } else
      console.log(this.ticket);
      if (this.ticket !== undefined || this.ticket !== null || this.ticket.length > 0) {
        this.tokenStorage.clearStorage();
        this.LoginByTicket();
      } else {
        this.router.navigateByUrl("/");
      }
    });
    if (this.returnUrl === null || this.returnUrl === undefined) {
      this.returnUrl = this.route.snapshot.queryParamMap.get("returnUrl");
    }
  }

  LoginByTicket() {
    this.tokenStorage.clearStorage();
    this.login.getUserInfoByTicket(this.ticket).subscribe(
      result => {
        if (result["access_token"] === null  && result["refresh_token"] === null && result["userInfor"] === null) {
          console.log("validate error");
          window.location.href = Constants.LOGOUT_URL;
        }
        // Validate that userInfo is exist
        if (!result["userInfor"]) {
          this.message = 'ML01: Tài khoản không tồn tại trong hệ thống PMQTVP';
          this.showDialog();
          return;
        }

        if (!result["access_token"]) {
          this.message = 'ML02: Xảy ra lỗi không xác định với hệ thống PMQTVP';
          this.showDialog();
          return;
        }

        // Handle result
        this.tokenStorage.saveToken(
          result["access_token"],
          result["refresh_token"],
          result["userInfor"]
        );
        console.log(this.returnUrl);
        // 'onCompleted' callback.
        // No errors, route to new page here
        if (this.returnUrl === null || this.returnUrl === undefined) {
          this.router.navigateByUrl("/");
          return;
        }
        this.router.navigateByUrl(this.returnUrl);
      },
      error => {
        console.log("validate error");
        window.location.href = Constants.LOGOUT_URL;
      }
    );
  }

  showDialog() {
      this.display = true;
  }

  logOut() {
    this.tokenStorage.signOut();
  }
}
