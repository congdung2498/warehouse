import {SecurityAccount} from "./SecurityAccount";
import {HttpClient, HttpHeaders} from "@angular/common/http";

import * as account from '../../assets/account.json';


export class Service {
  public httpOptions = { headers: new HttpHeaders({ "Content-Type": "application/json" }) };

  protected securityAccount: SecurityAccount = null;

  constructor(protected http: HttpClient) {
    this.securityAccount = new SecurityAccount();
    this.securityAccount.securityUsername = account['username'];
    this.securityAccount.securityPassword = account['password'];
  }

  public getAccount(): SecurityAccount {
    return this.securityAccount;
  }
}

