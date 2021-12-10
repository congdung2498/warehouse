import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import { CallUnit } from './Entity/CalUnit';
import { Stationery } from './Entity/Stationery';
import { LimitSpend } from './Entity/LimitSpend';
import { Place } from './Entity/Place';
import { Unit } from './Entity/Unit';
import { Employee } from './Entity/Employee';
import { Receiver } from './Entity/Receiver';
import {InfoToSearchIssuesStationeryItems, ReportStationery} from './Entity/ReportStationery';
import { map } from 'rxjs/operators/map';
import { TreeNode } from 'primeng/components/common/api';
import { Constants } from '../shared/containts';
import {Service} from "../shared/service";


const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" })
};
@Injectable()
export class StationeryService extends  Service {

  constructor(http: HttpClient) {
    super(http);
  }



  getStationeryStaffByUser() : Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationery/get-stationery-staff';
    return this.http.post(url, this.getAccount(), httpOptions);
  }

  getUnitHCDV() : Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationery/get-unit-hcdv';
    return this.http.post(url, this.getAccount(), httpOptions).pipe(map(res => {
        return this.dataToTree(res['data']);
      })
    );;
  }

  getUnitHCDVNoStaff() : Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationery/get-unit-hcdv-noStaff';
    return this.http.post(url, this.getAccount(), httpOptions).pipe(map(res => {
          return this.dataToTree(res['data']);
        })
    );;
  }

  getUnitVPTCT() : Observable<any> {
      const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationery/get-unit-vptct';
    return this.http.post(url, this.getAccount(), httpOptions).pipe(map(res => {
          return this.dataToTree(res['data']);
        })
    );;
  }

  getItemsByStationeryIds(stationeryIds: string[]): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationery/get-items-by-stationerys';
    let object = {stationeryIds: stationeryIds, securityUsername: this.getAccount().securityUsername, securityPassword: this.securityAccount.securityPassword};
    return this.http.post(url, object, httpOptions);
  }

  getStationeryItemImage(stationeryItemId: string) {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/get-stationery-item-image';
    let param = {search: stationeryItemId, securityUsername: this.getAccount().securityUsername, securityPassword: this.getAccount().securityPassword};
    return this.http.post(url, param, {responseType: 'blob' as 'blob'});
  }

  getStationery(): Observable<any> {
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/01', this.getAccount(), httpOptions);
  }

  getCalcultionUnit(): Observable<any> {
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/02', this.getAccount(), httpOptions);
  }

  findCalcultionUnit(object: CallUnit): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/03', object, httpOptions);
  }

  searchStationery(object: Stationery): Observable<any> {
    if(object.stationeryName != null){
      object.stationeryName = object.stationeryName.trim();
    }
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/04', object, httpOptions);
  }

  searchCountStationery(object: Stationery): Observable<any> {
    if(object.stationeryName != null){
      object.stationeryName = object.stationeryName.trim();
    }
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/searchCountStationery', object, httpOptions);
  }


  insertStationery(object: any): Observable<any> {
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/05', object, httpOptions);
  }

  updateStationery(object: any): Observable<any> {
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/06', object, httpOptions);
  }

  deleteStationery(object: Stationery): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/07', object, httpOptions);
  }

  deleteSelectStationery(object: any): Observable<any> {
    let param = {securityUsername: this.getAccount().securityUsername, securityPassword: this.getAccount().securityPassword, stationerys: object};
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/deleteSelectStationery', param, httpOptions);
  }

  getLimit(): Observable<any> {
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/08', this.getAccount(), httpOptions);
  }

  updateLimit(object: LimitSpend): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/09', object, httpOptions);
  }

  // DANH MUC NGUOI TIEP NHAN

  getReceiver(): Observable<any> {
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/01', this.getAccount(), httpOptions);
  }

  getRole(): Observable<any> {
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/02', this.getAccount(), httpOptions);
  }

  findEmployees(object: Employee): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/03', object, httpOptions);
  }

  findUnit(object: Unit): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/04', object, httpOptions);
  }

  //TODO
  findPlaceByHCDV(object: Place): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/find-place-by-hcdv', object, httpOptions);
  }

  //TODO
  findPlaceByVPTCT(object: Place): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/find-place-by-vptct', object, httpOptions);
  }

  //TODO
  findPlace(object: Place): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/05', object, httpOptions);
  }

  searchReceiver(object: Receiver): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/06', object, httpOptions);
  }

  insertReceiver(object: Receiver): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/07', object, httpOptions);
  }

  updateReceiver(object: Receiver): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/08', object, httpOptions);
  }

  deleteReceiver(object: Receiver): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/09', object, httpOptions);
  }


  getEmployees(object: Employee): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/01', object, httpOptions);
  }

  getEmployeeTCT(object: Employee): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/getEmployeeTCT', object, httpOptions);
  }

  getHcdv(object: Employee): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/10', object, httpOptions);
  }

  getReportStationery(): Observable<any> {
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/02', this.getAccount(), httpOptions);
  }

  getFullfillStationery(object: ReportStationery): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/03', object, httpOptions);
  }

  getLastUser(object: ReportStationery): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/04', object, httpOptions);
  }

  // getSectors(object: any) {
  //   return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020000/03', object, httpOptions);
  // }



  searchReportStationery(object: ReportStationery): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/05', object, httpOptions);
  }

  // countReportStationery(object: ReportStationery : Observable<any>) {
  //   const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/08';
  //   return this.http.post(url, object, httpOptions);
  // }
  countReportStationery(object: ReportStationery): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/09';
    return this.http.post(url, object, httpOptions);
  }

  exportExcel(object: ReportStationery): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/06', object, { responseType: 'arraybuffer' as 'json' });
  }

  exportExcelEmployee(object: ReportStationery): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/exportExcelEmployee', object, { responseType: 'arraybuffer' as 'json' });
  }

  exportExcelVPP(object: ReportStationery): Observable<any> {
    object.securityPassword = this.getAccount().securityPassword;
    object.securityUsername = this.getAccount().securityUsername;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/07', object, { responseType: 'arraybuffer' as 'json' });
  }

  getLoginInfo() {
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/08', this.getAccount() ,httpOptions).pipe(map(res => {
        return {
          treeUnit: this.dataToTree(res['data']['unit']),
          receiver: res['data']['receiver']
        };
      })
    );
  }

  getSectors(query: string) {
    if (query === null || query === undefined) {
      query = '';
    }
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020000/03',
      {
        'query': query,
        'securityUsername': this.getAccount().securityUsername,
        'securityPassword': this.getAccount().securityPassword
      },
      httpOptions).pipe(
        map(res => {
          return this.dataToTree(res['data']);
        })
      );
  }

  getSectorGreatFather(query: string) {
    if (query === null || query === undefined) {
      query = '';
    }
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020000/04',
      {
        'query': query,
        'securityUsername': this.getAccount().securityUsername,
        'securityPassword': this.getAccount().securityPassword
      },
      httpOptions).pipe(
        map(res => {
          return this.dataToTree([res['data']]);
        })
      );
  }

  private dataToTree(data: any[]): TreeNode[] {
    const nodes = [];
    if(!data || data.length === 0) {
     /* nodes.push({ label: 'Đơn vị không tồn tại'});*/
    } else {
      for (const cont of data) {
        nodes.push(this.dataToTreeNode(cont));
      }
    }
    return nodes;
  }

  private dataToTreeNode(cont: any): TreeNode {
    return {
      label: cont.unitName,
      data: cont.unitId,
      leaf: cont.isLeaf === null ? true : false
    };
  }
  insertStationeryForm(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050003/02', object, httpOptions);
  }

  findData(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/01', object, httpOptions);
  }
  findDataRequest(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/request-model', object, httpOptions);
  }

  findToGiveOut(object: any): Observable<any> {
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/01', object, httpOptions);
  }

  searchPlaceIsRole(text: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/searchPlaceIsRole';
    let object = {search: text, securityUsername: this.getAccount().securityUsername, securityPassword: this.getAccount().securityPassword};
    return this.http.post(url, object, httpOptions);
  }

  searchFullNameInRole(text: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/searchFullNameInRole';
    let object = {search: text, securityUsername: this.getAccount().securityUsername, securityPassword: this.getAccount().securityPassword};
    return this.http.post(url, object, httpOptions);
  }

  searchUnitName(text: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/searchUnitName';
    let object = {search: text, securityUsername: this.getAccount().securityUsername, securityPassword: this.getAccount().securityPassword};
    return this.http.post(url, object, httpOptions);
  }

  searchProcessUnit(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050004/01';
    return this.http.post(url, object, httpOptions);
  }

  //TODO
  getListManagerDirect(text: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchQltt/'+text;
    return this.http.get(url, httpOptions);
  }


  approveIssuesStationeryItems(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050004/02';
    return this.http.post(url, object, httpOptions);
  }

  findInfoRequest(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050010/01', object, httpOptions);
  }

  findIssuesStationeryItems(object: InfoToSearchIssuesStationeryItems): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050004/01';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }
  countIssuesStationeryItems(object: InfoToSearchIssuesStationeryItems): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050004/03';
    return this.http.post(url, object, httpOptions);
  }

  findStationeryByName(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/01';
    return this.http.post(url, this.getAccount(), httpOptions);
  }

    findAllPlace(): Observable<any> {
        const obj: any = {
            placeName: null,
            pageSize: Constants.MAX_RECORD_DISPLAY,
            pageNumber: 0,
            securityUsername: this.getAccount().securityUsername,
            securityPassword: this.getAccount().securityPassword
        };
        return this.http.post<any>(
            Constants.HOME_URL + 'com/viettel/vtnet360/vt00/vt000000/04', obj,
            httpOptions
        );
    }

  findStationeryQuota(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/02';
    return this.http.post(url, object, httpOptions);
  }

  countStationeryQuota(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/03';
    return this.http.post(url, object, httpOptions);
  }

  getLimitDateEnd(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/04';
    return this.http.post(url, this.getAccount(), httpOptions);
  }

  updateLimitDate(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/05';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  insertQuota(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/06';
    return this.http.post(url, object, httpOptions);
  }

  updateQuota(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/07';
    return this.http.post(url, object, httpOptions);
  }

  deleteQuota(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/08';
    return this.http.post(url, object, httpOptions);
  }

  insertIssuesStationery(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/insertIssuesStationery';
    return this.http.post(url, object, httpOptions);
  }

  updateAllIssuesStationery(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/updateAllIssuesStationery', object, httpOptions);
  }

  searchStationeryByEmployee(object: ReportStationery): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/searchStationeryByEmployee', object, httpOptions);
  }

  getDetailsIssuesStationeryItems(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getDetailsIssuesStationeryItems', object, httpOptions);
  }
  countStationeryByEmployee(object: ReportStationery): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/countStationeryByEmployee', object, httpOptions);
  }
  checkLimitStationeryByEmployee(object: ReportStationery): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/checkLimitStationeryByEmployee', object, httpOptions);
  }


  findSpentQuotaByUser(employeeUsername: string): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/find-spent-quota-by-user';
    let object = {userName: employeeUsername, securityUsername: this.getAccount().securityUsername, securityPassword: this.getAccount().securityPassword};
    return this.http.post(url, object, httpOptions);
  }

  getIssuesStationeryItemsById(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getIssuesStationeryItemsById';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }
  updateCancelIssuesStationery(object: any): Observable<any> {
      const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/updateCancelIssuesStationery';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //TODO: review and rewrite
  importExcelStationeryReport(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationery/uploadStationeryEmployee';
    return this.http.post(url, object, {observe: 'response', responseType: 'blob'});
  }

  templateImportExcelStationeryReport(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/downloadTemplateImport';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object,  { observe: 'response', responseType: 'blob'});
  }

  downloadTemplateImportStationery(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/downloadTemplateImportStationery';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object,  { observe: 'response', responseType: 'blob'});
  }


  getDishImage(object: any) {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationery/get-stationery-image';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, {responseType: 'blob' as 'blob'});
  }

  editIssuesStationery(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/editIssuesStationery';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  updateRefuseIssuesStationery(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/updateRefuseIssuesStationery';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  updateApproveIssuesStationery(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/updateApproveIssuesStationery';
    return this.http.post(url, object, httpOptions);
  }

  // xac nhan tiep nhan
  confirmReceiptIssuesStationery(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/confirmReceiptIssuesStationery';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  findDataToApprove(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050006/01';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  checkHcdvInStaff(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/checkHcdvInStaff';
    return this.http.post(url, this.getAccount(), httpOptions);
  }

  checkVptctInStaff(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/checkVptctInStaff';
    return this.http.post(url,this.getAccount(), httpOptions);
  }


  findDataToApproveProcess(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050006/findDataToApproveProcess';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  findInfoRequestDetailProcessDetails(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050006/findInfoRequestDetailProcessDetails';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  // hoan thanh thuc hien
  postponedImplementationIssuesItems(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/postponedImplementationIssuesItems';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }
// khong thuc hien duoc
  notPossibleApprove(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/notPossibleApprove';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }
  // hoan thanh
  isCompleteIssusStationery(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/isCompleteIssusStationery';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }
  // xac nhan cap nhat
  confirmationPendingApprove(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/confirmationPendingApprove';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }
  // tu choi cap nhat
  refuseConfirmationPendingApprove(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/refuseConfirmationPendingApprove';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }
  // get items employee bu id
  findInfoRequestEmployee(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/findInfoRequestEmployee';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }
  searchPlaceEmployeeById(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/searchPlaceEmployeeById';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }
  findInfoRequestHcdv(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/findDataToRatting';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  findDataToRattingDetails(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/findDataToRattingDetails';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  findProcessInfoRequestHcdv(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/findDataProcessToRatting';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  countInfoRequestHcdvDetails(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/countInfoRequestHcdvDetails';
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  requestByUserName(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/requestByUserName', object, httpOptions);
  }

  voteHcdv(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/voteHcdv';
    return this.http.post(url, object, httpOptions);
  }

  voteVptct(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/voteVptct';
    return this.http.post(url, object, httpOptions);
  }

  getQuotaLimit(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getQuotaLimit', object, httpOptions);
  }

  requestByUnitId(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/requestByUnitId', object, httpOptions);
  }


  updateIssuesStationeryItems(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050004/02';
    return this.http.post(url, object, httpOptions);
  }

  //TODO
  getListManager(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt00/vt000000/02';
    return this.http.post(url, object, httpOptions);
  }

  findSpendingLimitQuota(): Observable<any> {
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/findSpendingLimitQuota', this.getAccount(), httpOptions);
  }

  importService(object: any): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/uploadStationeryManagement';
    return this.http.post(url, object, {observe: 'response', responseType: 'blob'});
  }

  getFullnameHCDV(object: ReportStationery): Observable<any> {
    object.securityUsername = this.getAccount().securityUsername;
    object.securityPassword = this.getAccount().securityPassword;
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/getFullnameHCDV', object, httpOptions);
  }
}
