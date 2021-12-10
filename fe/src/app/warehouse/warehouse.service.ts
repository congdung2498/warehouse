import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from '../shared/containts';
import { TokenStorage } from '../shared/token.storage';
import { HttpHeaders, HttpClient } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class WarehouseService {

  constructor(private http: HttpClient, private tokenService: TokenStorage) {
  }

  // L?y danh s�ch Kho
  getListWarehouse(object: any): Observable<any> {
    if (object.warehouse_name != null) {
      object.warehouse_name = object.warehouse_name.trim()
    }
    let account = this.tokenService.getAccount();
    const obj: any = {
      warehouseId: object.warehouse_id,
      name: object.warehouse_name,
      type: object.type,
      acreage: object.acreage,
      address: object.address,
      status: object.status,
      rowNum: object.row_num,
      heightNum: object.height_num,
      columnNum: object.column_num,
      pageSize: object.pageSize,
      pageNumber: object.pageNumber,
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/10';
    return this.http.post(url, obj, httpOptions);
  }

  insertUpdate(warehouse_id: number,
    warehouse_name: string,
    typeOfWarehouse: number,
    status: number,
    acreage: number,
    address: string,
    row_num: number,
    column_num: number,
    height_num: number): Observable<any> {
    let account = this.tokenService.getAccount();
    const obj: any = {
      warehouseId: warehouse_id,
      name: warehouse_name,
      type: typeOfWarehouse,
      acreage: acreage,
      address: address,
      status: status,
      rowNum: row_num,
      heightNum: height_num,
      columnNum: column_num,
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    return this.http.post(
      Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/04', obj,
      httpOptions
    );
  }

  deleteWarehouse(warehouse_id: number): Observable<any> {
    let account = this.tokenService.getAccount();
    const obj: any = {
      warehouseId: warehouse_id,
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    return this.http.post(
      Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/05', obj,
      httpOptions
    );
  }

  loadListRackSlot(warehouse_id: number): Observable<any> {
    let account = this.tokenService.getAccount();
    const obj: any = {
      warehouseId: warehouse_id,
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    return this.http.post(
      Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/06', obj,
      httpOptions
    );
  }

  // L?y danh s�ch Kho
  getListWarehouseForDropdown(): Observable<any> {
    let account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/07';
    return this.http.post(url, obj, httpOptions);
  }

  // L?y danh s�ch slot trong kho
  getListSlotOfWarehouse(object: any): Observable<any> {
    if (object.warehouse_name != null) {
      object.warehouse_name = object.warehouse_name.trim()
    }
    let account = this.tokenService.getAccount();
    const obj: any = {
      warehouseId: object.warehouse_id,
      name: object.warehouse_name,
      pageSize: object.pageSize,
      pageNumber: object.pageNumber,
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/08';
    return this.http.post(url, obj, httpOptions);
  }

  //L?y file excel t? template
  getExcel(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/09';
    let account = this.tokenService.getAccount();
    const obj: any = {
      warehouseId: object.warehouse_id,
      name: object.warehouse_name,
      pageSize: object.pageSize,
      pageNumber: object.pageNumber,
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    return this.http.post<any>(url, obj, {
      responseType: 'arraybuffer' as 'json'
    });
  }

  getDocumentReport(object: any): Observable<any> {
    if (object.warehouse_name != null) {
      object.warehouse_name = object.warehouse_name.trim()
    }
    let account = this.tokenService.getAccount();
    const obj: any = {
      warehouseId: object.warehouse_id,
      name: object.warehouse_name,
      pageSize: object.pageSize,
      pageNumber: object.pageNumber,
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt07/vt070002/08';
    return this.http.post(url, obj, httpOptions);
  }

  getDocumentReportV2(object: any): Observable<any> {
    if (object.warehouse_name != null) {
      object.warehouse_name = object.warehouse_name.trim()
    }
    let account = this.tokenService.getAccount();
    const obj: any = {
      warehouseId: object.warehouse_id,
      name: object.warehouse_name,
      pageSize: object.pageSize,
      pageNumber: object.pageNumber,
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt07/vt070002/11';
    return this.http.post(url, obj, httpOptions);
  }

  getExcelForDocument(object: any): Observable<any> {
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070002/12';
    let account = this.tokenService.getAccount();
    const obj: any = {
      warehouseId: object.warehouse_id,
      name: object.warehouse_name,
      pageSize: object.pageSize,
      pageNumber: object.pageNumber,
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    return this.http.post<any>(url, obj, {
      responseType: 'arraybuffer' as 'json'
    });
  }

  importProjectData(data: any): Observable<any> {
    let account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      data: data
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/99/2';
    return this.http.post(url, obj, httpOptions);
  }

  importOfficialDispatchData(data: any): Observable<any> {
    let account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      data: data
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070003/01';
    return this.http.post(url, obj, httpOptions);
  }

  importFolderProjectData(data: any): Observable<any> {
    let account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      data: data
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/99/1';
    return this.http.post(url, obj, httpOptions);
  }

  importFolderOfficialDispatchData(data: any): Observable<any> {
    let account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      data: data
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070003/04';
    return this.http.post(url, obj, httpOptions);
  }

  importFolderVoucherData(data: any): Observable<any> {
    let account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      data: data
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070005/01';
    return this.http.post(url, obj, httpOptions);
  }

  importFolderRetailProjectData(data: any): Observable<any> {
    let account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      data: data
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070005/04';
    return this.http.post(url, obj, httpOptions);
  }

}
