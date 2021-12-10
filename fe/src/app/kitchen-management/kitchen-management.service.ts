import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/delay';
import { TreeNode } from 'primeng/components/common/api';
import { map } from 'rxjs/operators/map';
import { Constants } from '../shared/containts';
import {ConfigLunch, LunchModel, SearchingLunch, SearchingLunchModel, UpdatingAllLunch} from "./Entity/LunchConfig";
import {TokenStorage} from "../shared/token.storage";
import {MenuSettingToInsert} from "./Entity/MenuSetting";
import {MenuSettingConfig} from "./Entity/MenuSettingConfig";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class KitchenManagementService {

  constructor(private http: HttpClient, private tokenService: TokenStorage) { }

  /**
   *
   * ---- Dayoff Config Component ----
   *
   * */

  getParentUnitId(unitId: string): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/lunch/get-parent-unit';
    let account = this.tokenService.getAccount();
    let object = {search: unitId, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
    return this.http.post(url, object, httpOptions);
  }

  getExistedLunchMessage(settingLunch: ConfigLunch): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/lunch/get-existed-lunch-msg';
    let account = this.tokenService.getAccount();
    settingLunch.securityUsername = account.securityUsername;
    settingLunch.securityPassword = account.securityPassword;
    return this.http.post(url, settingLunch, httpOptions);
  }

  getAllKitchenFullName(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/get-all-kitchen-full';
    let account = this.tokenService.getAccount();
    return this.http.post(url,account, httpOptions);
  }

  getKitchenByChefFullName(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/get-kitchen-by-chef-full';
    let account = this.tokenService.getAccount();
    return this.http.post(url, account, httpOptions);
  }

  //TODO
  dishAutocomplete(search: string): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/dish-autocomplete/' + search;
    return this.http.get(url, httpOptions);
  }

  getLunchModelByDay(searchingConfig: SearchingLunchModel): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/lunch/get-lunch-model-by-day';
    let account = this.tokenService.getAccount();
    searchingConfig.securityUsername = account.securityUsername;
    searchingConfig.securityPassword = account.securityPassword;
    return this.http.post(url, searchingConfig, httpOptions);
  }

  getLunchModelByPeriod(searchingConfig: SearchingLunchModel): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/lunch/get-lunch-model-by-period';
    let account = this.tokenService.getAccount();
    searchingConfig.securityUsername = account.securityUsername;
    searchingConfig.securityPassword = account.securityPassword;
    return this.http.post(url, searchingConfig, httpOptions);
  }

  createLunchByPeriod(config: ConfigLunch) : Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/lunch/create-lunch-by-period';
    let account = this.tokenService.getAccount();
    config.securityUsername = account.securityUsername;
    config.securityPassword = account.securityPassword;
    return this.http.post(url, config, httpOptions);
  }

  deleteLunchByPeriod(config: ConfigLunch) : Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/lunch/delete-lunch-by-period';
    let account = this.tokenService.getAccount();
    config.securityUsername = account.securityUsername;
    config.securityPassword = account.securityPassword;
    return this.http.post(url, config, httpOptions);
  }

  updateVoting(lunch: LunchModel): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/lunch/update-voting';
    let account = this.tokenService.getAccount();
    lunch.securityUsername = account.securityUsername;
    lunch.securityPassword = account.securityPassword;
    return this.http.post(url, lunch, httpOptions);
  }

  updateAllLunch(config: UpdatingAllLunch): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/lunch/update-all-lunch';
    let account = this.tokenService.getAccount();
    config.securityUsername = account.securityUsername;
    config.securityPassword = account.securityPassword;
    return this.http.post(url, config, httpOptions);
  }

  getMenuSettingByDate(searching: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/lunch/get-menu-by-date';
    let account = this.tokenService.getAccount();
    searching.securityUsername = account.securityUsername;
    searching.securityPassword = account.securityPassword;
    return this.http.post(url, searching, httpOptions);
  }

  createLunchByDay(settingLunch: Object): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/lunch/create-lunch-by-day';
    return this.http.post(url, settingLunch, httpOptions);
  }

  settingLunch(settingLunch: any): Observable<any> {
    let account = this.tokenService.getAccount();
    settingLunch.securityUsername = account.securityUsername;
    settingLunch.securityPassword = account.securityPassword;
    console.log(settingLunch);
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020006/04';
    return this.http.post(url, settingLunch, httpOptions);
  }

  getLunchModel(settingLunch: SearchingLunch): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020006/01';
    let account = this.tokenService.getAccount();
    settingLunch.securityUsername = account.securityUsername;
    settingLunch.securityPassword = account.securityPassword;
    return this.http.post(url, settingLunch, httpOptions);
  }

  getTotalLunchModel(settingLunch: SearchingLunch): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020006/getTotalLunchDate';
    let account = this.tokenService.getAccount();
    settingLunch.securityUsername = account.securityUsername;
    settingLunch.securityPassword = account.securityPassword;
    return this.http.post(url, settingLunch, httpOptions);
  }

  managerEmployeeAutocomplete(search: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/checking/manager-employee-autocomplete';
    let account = this.tokenService.getAccount();
    let object = {search: search, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
    return this.http.post(url, search, httpOptions);
  }

  employeeAutocomplete(search: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/checking/employee-autocomplete';
    let account = this.tokenService.getAccount();
    let object = {search: search, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
    return this.http.post(url, search, httpOptions);
  }

  insertMenu(object: MenuSettingToInsert): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020004/04';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  getListDishByKitchen(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020004/05';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  getListMenu(object: MenuSettingConfig): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/find-menu';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

    countTotalMenu(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/countTotalMenu';
    let account = this.tokenService.getAccount();
      object.securityUsername = account.securityUsername;
      object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  getListDish(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/dishconfig';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  getAllKitchen(object: any) : Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020002/05';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  getKitchenByChef(object: any) : Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/find-kitchen-by-chef';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  updateDishConfig(object: any) {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020002/03';
    return this.http.post(url, object);
  }

  //  get list of day off now
  getListDayOff(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020011/01';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  insert day off
  insertDayOff(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020011/02';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  update day off
  updateDayOff(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020011/03';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  count total day off
  countTotalDayOff(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020011/04';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  copy day off
  copyDayOff(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020011/05';
    let account = this.tokenService.getAccount();
    let param = {listDayOff: object, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
    return this.http.post(url, param, httpOptions);
  }

  //  find all years
  findAllYears(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020011/06';
    let account = this.tokenService.getAccount();
    return this.http.post(url, account, httpOptions);
  }

  // getSectors() {
  //   return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020000/02', httpOptions);
  // }

  getSectors(query: string) {
    if (query === null || query === undefined) {
      query = '';
    }
    let account = this.tokenService.getAccount();
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020000/03',
      {
        'query': query,
        'securityUsername': account.securityUsername,
        'securityPassword': account.securityPassword
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
    let account = this.tokenService.getAccount();
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020000/04',
      {
        'query': query,
        'securityUsername': account.securityUsername,
        'securityPassword': account.securityPassword
      },
      httpOptions).pipe(
        map(res => {
          return this.dataToTree([res['data']]);
        })
      );
  }

  private dataToTree(data: any[]): TreeNode[] {
    const nodes = [];
    for (const cont of data) {
      nodes.push(this.dataToTreeNode(cont));
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

  // data of kitchenConfig component
  getReport(kitchenIds: string[], month: Date, unitForSearch: Array<String>) {
    let account = this.tokenService.getAccount();
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020012/01', {
      'kitchenIds': kitchenIds,
      'month': month,
      'listUnitForSearch': unitForSearch,
      'securityUsername': account.securityUsername,
      'securityPassword': account.securityPassword
    }, httpOptions);
  }

  getReportByEmployee(kitchenIds: string[], month: Date, unitForSearch: Array<String>, userName: String, query: String) {
    let account = this.tokenService.getAccount();
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020013/01', {
      'kitchenIds': kitchenIds,
      'month': month,
      'listUnitForSearch': unitForSearch,
      'userName': userName,
      'query': query,
      'securityUsername': account.securityUsername,
      'securityPassword': account.securityPassword
    }, httpOptions);
  }

  getReportExcel(kitchenIds: string[], month: Date, unitForSearch: Array<String>) {
    const limitHttpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      responseType: 'blob' as 'blob'
    };
    let account = this.tokenService.getAccount();
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020012/02', {
      'kitchenIds': kitchenIds,
      'month': month,
      'listUnitForSearch': unitForSearch,
      'securityUsername': account.securityUsername,
      'securityPassword': account.securityPassword
    }, limitHttpOptions);
  }

  getReportExcelByEmployee(kitchenIds: string[], month: Date, unitForSearch: Array<String>, userName: String, query: String) {
    const limitHttpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      responseType: 'blob' as 'blob'
    };
    let account = this.tokenService.getAccount();
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020013/02', {
      'kitchenIds': kitchenIds,
      'month': month,
      'listUnitForSearch': unitForSearch,
      'userName': userName,
      'query': query,
      'securityUsername': account.securityUsername,
      'securityPassword': account.securityPassword
    }, limitHttpOptions);
  }

  getResultsAPI(query) {
    let account = this.tokenService.getAccount();
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020000/01',
      {
        'query': query,
        'securityUsername': account.securityUsername,
        'securityPassword': account.securityPassword
      }, httpOptions);
  }

  //  get list of employe
  getEmployee(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010011/01';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  /**
   *
   * --- Kitchen Config Component ---
   *
   */
  //  get kitchen place
  getKitchenPlace(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/01';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  get chefl
  getChef(object: any) {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020003/vt020003FindListChef2';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  get list kitchen
  getListKitchen(object: any) {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020003/02';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  count total record kitchen
  countTotalKitchen(object: any) {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020003/03';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  //  set up kitchen
  handleSetUpKitchen(object: any) {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020003/04';
    return this.http.post(url, object, httpOptions);
  }

  getReportExcelKitchenInfo() {
    const limitHttpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      responseType: 'blob' as 'blob'
    };
    let account = this.tokenService.getAccount();
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020003/05', account, limitHttpOptions);
  }

  //TODO
  findAllKitchenName(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/findKitchenName';
    let account = this.tokenService.getAccount();
    return this.http.post(url, account, httpOptions);
  }

  //TODO
  getShortName(text: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/findUnitShortName/'+text;
    return this.http.get(url, httpOptions);
  }

//  findAbbreviations
  findAbbreviations(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/findAbbreviations';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  countTotalMenuService(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/countTotalMenuService';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }


  importReportResponse(kitchenIds: string[], month: Date, unitForSearch: Array<String>, userName: String, query: String): Observable<any> {
    let account = this.tokenService.getAccount();
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020013/exportReportResponse', {
      'kitchenIds': kitchenIds,
      'month': month,
      'listUnitForSearch': unitForSearch,
      'userName': userName,
      'query': query,
      'securityUsername': account.securityUsername,
      'securityPassword':  account.securityPassword
    }, {observe: 'response', responseType: 'blob'});
  }

  //TODO
  findUnitShortNameDropdown(text: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/findUnitShortNameDropdown/'+text;
    return this.http.get(url, httpOptions);
  }

  abbreviationsInsert(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/abbreviationsInsert';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  updateAbbreviations(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/updateAbbreviations';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  buildCompleteTree(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/buildCompleteTree';
    let account = this.tokenService.getAccount();
    if(object) {
      object.securityUsername = account.securityUsername;
      object.securityPassword = account.securityPassword;
    } else {
      object = {securityUsername: account.securityUsername, securityPassword: account.securityPassword};
    }
    return this.http.post(url, object, httpOptions);
  }

  //TODO
  findKitchenNameUpdate(text: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/findKitchenNameUpdate/'+text;
    return this.http.get(url, httpOptions);
  }

  //  findAbbreviations
  updateMenu(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/updateMenu';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  getDishImage(object: any) {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/getDishImage';
    return this.http.post(url, object, {responseType: 'blob' as 'blob'});
  }

  getImage(object: any) {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/get-dish-image';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, {responseType: 'blob' as 'blob'});
  }

  findKitchenNameOld(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/findKitchenNameOld';
    let account = this.tokenService.getAccount();
    return this.http.post(url, account, httpOptions);
  }

  checkMenuExistResult(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/checkMenuExistResult';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  deleteAbbreviations(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/deleteAbbreviations';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  findUnitShortNameDropdownAll(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/findUnitShortNameDropdownAll';
    let account = this.tokenService.getAccount();
    return this.http.post(url, account, httpOptions);
  }

  checkMenuExist(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020004/03';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object , httpOptions);
  }

  //TODO
  findKitchenNameUpdateCheck(text: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/findKitchenNameUpdateCheck/'+text;
    return this.http.get(url, httpOptions);
  }

  //TODO
  findKitchenNameByUser(text: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/findKitchenNameByUser/'+text;
    return this.http.get(url, httpOptions);
  }

  countTotalMenuKichen(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/countTotalMenuKichen';
    let account = this.tokenService.getAccount();
    object.securityUsername = account.securityUsername;
    object.securityPassword = account.securityPassword;
    return this.http.post(url, object, httpOptions);
  }

  findKitchenNameAll(): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/findKitchenNameAll';
    let account = this.tokenService.getAccount();
    return this.http.post(url, account, httpOptions);
  }


}
