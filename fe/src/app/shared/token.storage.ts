import { Injectable } from '@angular/core';
import { UserInfo } from './userInfo';
import { SecurityAccount } from './SecurityAccount';
import { Constants } from './containts';
import { HttpClient, HttpHeaders } from '@angular/common/http';


import * as account from '../../assets/account.json';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class TokenStorage {


  private userInfo: UserInfo = null;
  private securityAccount: SecurityAccount = null;

  constructor(private http: HttpClient) {
    this.getSecurityAccount();
  }

  signOut() {
    this.clearStorage();
    localStorage.setItem('clearSessionStorage', 'PMQT');
    localStorage.removeItem('clearSessionStorage');
    window.location.href = Constants.LOGOUT_URL;
  }

  clearStorage() {
    this.userInfo = null;
    sessionStorage.removeItem(Constants.TOKEN_KEY);
    sessionStorage.removeItem(Constants.REFRESH_TOKEN_KEY);
    sessionStorage.removeItem(Constants.USER_INFO);
    sessionStorage.clear();
  }

  public saveToken(token: string, refreshtoken: string, userInfo: UserInfo) {
    this.clearStorage();
    let usernameEncode = btoa(userInfo.userName.toString().toUpperCase());
    let roles: String[] = [];
    userInfo.role.forEach((role) => {
      role = atob(role.toString());
      role = role.substring(0, role.length - 15) + role.substring(role.length - 3, role.length);
      role = atob(role.toString());
      role = role.substring(0, role.length - 8) + role.substring(role.length - 3, role.length);
      role = atob(role.toString());
      role = role.substring(0, role.length - 5) + role.substring(role.length - 3, role.length);
      let roleFront = role.substring(0, role.length - 14);
      let roleBack = role.substring(role.length - 2, role.length);
      role = roleFront + roleBack;
      role = role.substring(12, role.length);
      role = role.substring(0, 5) + role.substring(17, role.length) ;
      role = atob(role.toString());
      role = role.substring(32, role.length);
      roles.push(role);
    });
    userInfo.role = roles;
    this.userInfo = userInfo;
    this.userInfo.avatar = null;
    sessionStorage.setItem(Constants.TOKEN_KEY,  token);
    sessionStorage.setItem(Constants.REFRESH_TOKEN_KEY,  refreshtoken);
    sessionStorage.setItem(Constants.USER_INFO,  JSON.stringify(userInfo));
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

  public getUserInfo(): UserInfo {
    if (this.isLogged()) {
      if (this.userInfo == null) {

        this.userInfo = JSON.parse(sessionStorage.getItem(Constants.USER_INFO));
        // this.http.post<any>(Constants.HOME_URL + "/com/viettel/vtnet360/vt00/vt000000/16", null, httpOptions).subscribe( res => {
        //    this.userInfo = res.data as UserInfo;
        // });
      }
      return this.userInfo;
    } else {
      this.userInfo = null;
      return this.userInfo;
    }
  }

  public isLogged(): boolean {
    if (sessionStorage.getItem(Constants.TOKEN_KEY) != null) {
      return true;
    } else {
      this.userInfo = null;
      return false;
    }
  }
  checkPermission(url: string) {
    const listRole = url.split('/');
    listRole.shift();
    for (let index = 0; index < listRole.length; index++) {
      const element = listRole[index];
      if (Constants[element] !== undefined && !this.getUserInfo().role.includes(Constants[element])) { return false; }
    }
    return true;
  }

  isPermission(url: string): boolean {
    if (this.getUserInfo().role.includes('PMQT_ADMIN')) return true;

    const listRole = url.split('/');
    listRole.shift();
    const element = listRole[listRole.length - 1];
    let menuRoles: string[] = Constants[element];
    if(menuRoles && menuRoles.length > 0) {
      for(let i = 0; i < menuRoles.length; i++) {
        for(let j = 0; j < this.getUserInfo().role.length; j++) {
          if(menuRoles[i] === this.getUserInfo().role[j].toString().trim()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public checkRole(role: string) {
    let roles = this.getUserInfo().role;
    if(roles) {
      for (let index = 0; index < roles.length; index++) {
        if(roles.includes(role)) return true;
      }
    }
    return false;
  }

  public getAccount(): SecurityAccount {
    return this.securityAccount;
  }

  public getSecurityAccount(callback?: Function) {
    this.securityAccount = new SecurityAccount();
    this.securityAccount.securityUsername = account['username'];
    this.securityAccount.securityPassword = account['password'];
    if(callback) callback();
  }
}
