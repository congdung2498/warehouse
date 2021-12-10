import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/delay';
import { Condition } from './Entity/ConditionSearch';
import { Constants } from '../shared/containts';
import {UpdatingChecking} from "./Entity/Checking";
import {TokenStorage} from "../shared/token.storage";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ReportserviceService {

  constructor(private http: HttpClient, private tokenService: TokenStorage) { }


  getCheckingModel(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/checking/get-checking-model-web';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  /**
   *
   * ---- report check-in-out ----
   *
   * */
  //  get list of employe
  getEmployee(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010011/01';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  get list of infor go out
  getListInOut(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010011/02';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  get report file excel
  getExcel(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010011/03';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post<any>(url, object, { responseType: 'arraybuffer' as 'json' });
  }

  //  count total record
  countTotalRecord(object: any) {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010011/04';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  guard action accept or reject
  guardAction(object: UpdatingChecking) : Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010000/03';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  update over time go out
  updateOverTime(object: any) {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010000/04';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  get image
  getImage(object: any) {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt00/vt000000/07?EmployeeCode=' + object;
    return this.http.get(url);
  }

  /**
   *
   *  ---- add card employee ----
   *
   * */
  //  get list of employee
  getEmployee2(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010012/01';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  get list card id
  getCardId(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010012/02';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  get list add card
  getListAddCard(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010012/03';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  count total record of list card
  countTotalRecord2(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010012/04';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  update employee
  updateCardEmployee(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010012/05';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }
}
