import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/delay';
import {of} from 'rxjs/observable/of';
// import { LEADERS } from './mock-data';
import {Employee} from '../Entity/Employee';
import {SearchingCondition} from './Entity/searchingCodition';
import {SearchingTinboxCondition} from './Entity/searchingTinboxCodition';
import {Constants} from '../shared/containts';
import {TokenStorage} from "../shared/token.storage";
import { SearchingRackCondition } from './Entity/searchingRackCodition';
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};
@Injectable()
export class BarcodeService {

  constructor(private http: HttpClient, private tokenService: TokenStorage) { }

  findTypeBarcode(name: string): Observable<any> {
   
    const obj: any = {
      name: name,
        pageSize: Constants.MAX_RECORD_DISPLAY,
        pageNumber: 0
    };
    let account = this.tokenService.getAccount();
    obj.securityUsername = account.securityUsername;
    obj.securityPassword = account.securityPassword;
    return this.http.post<any>(
        Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070001/02', obj,
        httpOptions
    );
  }

  insertUpdate(description: string,
    printed: boolean,
    barCodePrefixId: number,
    prefix:string,
    quantity: number): Observable<any> {
      let account = this.tokenService.getAccount();
      const obj: any = {
      description: description,
      printed: false,
      barCodePrefixId: barCodePrefixId,
      quantity: quantity,
      prefix:prefix,
      
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
      };
      return this.http.post(
      Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070001/03', obj,
      httpOptions
      );
  }

  findSearchingResult(obj: SearchingCondition): Observable<any> {
    
    let account = this.tokenService.getAccount();
    obj.securityUsername = account.securityUsername;
    obj.securityPassword = account.securityPassword;
    return this.http.post<any>( Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070001/04', obj,httpOptions);
}
  findBarcodeDetail(barCodeRangeId: number): Observable<any> {
    let account = this.tokenService.getAccount();
    const obj: any = {
      barCodeRangeId:barCodeRangeId,
    securityUsername: account.securityUsername,
    securityPassword: account.securityPassword
    
    };
    return this.http.post(
    Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070001/05', obj,
    httpOptions
    );
  }

  changeBarcodeRangeDetail(barCodeRangeId: number): Observable<any> {
    let account = this.tokenService.getAccount();
    const obj: any = {
      barCodeRangeId:barCodeRangeId,
    securityUsername: account.securityUsername,
    securityPassword: account.securityPassword
    
    };
    return this.http.post(
    Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070001/06', obj,
    httpOptions
    );
  }
  checkChangeBarcodeRange(barCodeRangeId: number): Observable<any> {
    let account = this.tokenService.getAccount();
    const obj: any = {
      barCodeRangeId:barCodeRangeId,
    securityUsername: account.securityUsername,
    securityPassword: account.securityPassword
    
    };
    return this.http.post(
    Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070001/07', obj,
    httpOptions
    );
  }
  findSearchingTinboxResult(obj: SearchingTinboxCondition): Observable<any> {
    
    let account = this.tokenService.getAccount();
    obj.securityUsername = account.securityUsername;
    obj.securityPassword = account.securityPassword;
    return this.http.post<any>( Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070002/10', obj,httpOptions);
  }

  //get tin boxes in only "kho lưu"
  getTinBoxInType1Warehouse(obj: SearchingTinboxCondition): Observable<any> {
    
    let account = this.tokenService.getAccount();
    obj.securityUsername = account.securityUsername;
    obj.securityPassword = account.securityPassword;
    return this.http.post<any>( Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070002/10/1', obj,httpOptions);
  }

  findTypeWarehouseDetail(name: string): Observable<any> {
    
    const obj: any = {
      name: name,
        pageSize: Constants.MAX_RECORD_DISPLAY,
        pageNumber: 0
    };
    return this.http.post<any>(
        Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070002/06', obj,
        httpOptions
    );
  }

  //get only "kho lưu" not "kho tạm"
  findType1Warehouses(name: string): Observable<any> {  
    
    const obj: any = {
      name: name,
      pageSize: Constants.MAX_RECORD_DISPLAY,
      pageNumber: 0
    };
    let account = this.tokenService.getAccount();
    obj.securityUsername = account.securityUsername;
    obj.securityPassword = account.securityPassword;
    return this.http.post<any>(
        Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070002/06/01', obj,
        httpOptions
    );
  }

  updateTinbox(tinBoxId: number,
    name: string,
    index: number,
    mngUser:string,
    type: string,warehouseId:number): Observable<any> {
      let account = this.tokenService.getAccount();      
      const obj: any = {
        tinBoxId: tinBoxId,
        name: name,
        index: index,
        mngUser: mngUser,
        type:type,
        warehouseId:warehouseId
      };
      obj.securityUsername = account.securityUsername;
      obj.securityPassword = account.securityPassword;
      return this.http.post(
      Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070002/07', obj,
      httpOptions
      );
  }


  findSearchingRackResult(obj: SearchingRackCondition): Observable<any> {
    
    let account = this.tokenService.getAccount();
    obj.securityUsername = account.securityUsername;
    obj.securityPassword = account.securityPassword;
    return this.http.post<any>( Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/14', obj,httpOptions);
  }
  updatePrintTimeRack(listActivityId :any): Observable<any> {
      // let account = this.tokenService.getAccount();
      const obj: any = {
        warehouseName: listActivityId
      };
      let account = this.tokenService.getAccount();
      obj.securityUsername = account.securityUsername;
      obj.securityPassword = account.securityPassword;
      return this.http.post(
      Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/15', obj,
      httpOptions
      );
  }

  //get all project names in tinbox
  getProjectsInTinBox(tinBoxId: number): Observable<any> {  
    
    const obj: any = {
      tinBoxId: tinBoxId
    };
    let account = this.tokenService.getAccount();
    obj.securityUsername = account.securityUsername;
    obj.securityPassword = account.securityPassword;
    return this.http.post<any>(
        Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070002/88/4', obj,
        httpOptions
    );
  }
}
