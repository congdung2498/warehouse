import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/delay';
import { Constants } from '../shared/containts';
import {ResultConfigName} from "./Entity/ListPlace";
import {Service} from "../shared/service";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class SettingService extends Service {

  constructor(http: HttpClient) {
    super(http);
  }

  /**
   *
   *  ---- Place Config Component ----
   *
   * */
  //  get list place
  getListPlace(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020001/01';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  handle action
  handleAction(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020001/03';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  count total place
  countTotalPlace(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020001/02';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }


  getListVersion(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt06/vt060001/01';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  addNewVersionControl(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt06/vt060001/03';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  updateVersionControl(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt06/vt060001/02';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  deleteVersionControl(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt06/vt060001/04';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  deleteAllSession(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt06/vt060001/deleteAllSession';
    return this.http.post(url, httpOptions);
  }

  getMasterClass(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getMasterClass';
    return this.http.post(url,  this.getAccount(), httpOptions);
  }
  getMasterClassWeb(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getMasterClassWeb';
    return this.http.post(url,  this.getAccount(), httpOptions);
  }
  getAllProcessConfig(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getAllProcessConfig';
    return this.http.post(url, this.getAccount(), httpOptions);
  }

  getMasterClassByCode(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getMasterClassByCode';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object,  httpOptions);
  }

  editTypeParam(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/editTypeParam';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  getParamWebConfigByCode(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getParamWebConfigByCode';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object,  httpOptions);
  }

  insertMasterCodeWeb(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/insertMasterCodeWeb';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  editWebParam(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/editWebParam';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  getCodeValueByMasterClass(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getCodeValueByMasterClass';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  getParamProcessConfigByCode(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getParamProcessConfigByCode';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object,  httpOptions);
  }

  getCountParamProcessConfigByCode(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getCountParamProcessConfigByCode';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object,  httpOptions);
  }

  getCountMasterClassByCode(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getCountMasterClassByCode';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object,  httpOptions);
  }

  getCountParamWebConfigByCode(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getCountParamWebConfigByCode';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object,  httpOptions);
  }

  editProcessParam(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/editProcessParam';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }
}
