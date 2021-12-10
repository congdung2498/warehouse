import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from '../shared/containts';
import { TokenStorage } from '../shared/token.storage';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class DocumentBoxSearchService {

  constructor(private http: HttpClient, private tokenService: TokenStorage) {
  }



  getAllActiveWarehouse(): Observable<any> {
    let account = this.tokenService.getAccount();
    const param = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt07/vt070000/11';
    return this.http.post(url, param, httpOptions);
  }

  searchDocumentBox(searchCondition: any): Observable<any> {
    let account = this.tokenService.getAccount();

    const accountParam = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
    };

    const param = Object.assign({}, accountParam, searchCondition);

    // const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt07/vt070000/12';
    // const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt07/vt070000/16';
    const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt07/vt070000/99/7';
    return this.http.post(url, param, httpOptions);
  }

  getTinBoxContent(searchCondition: any): Observable<any> {
    let account = this.tokenService.getAccount();

    const accountParam = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
    };

    const param = Object.assign({}, accountParam, searchCondition);

    const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt07/vt070000/13';
    return this.http.post(url, param, httpOptions);
  }

  synchrony(synchrony: any): Observable<any> {
    let account = this.tokenService.getAccount();
    const accountParam = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
    };

    const param = Object.assign({}, accountParam, synchrony);

    const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt07/vt070000/17';
    return this.http.post(url, param, httpOptions);
  }

  getProjectDetail(searchCondition: any): Observable<any> {
    const account = this.tokenService.getAccount();

    const accountParam = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
    };

    const param = Object.assign({}, accountParam, searchCondition);

    const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt07/vt070002/05';
    return this.http.post(url, param, httpOptions);
  }

  getDocumentListInParent(id, type, folderId): Observable<any> {
    const headers = new HttpHeaders();
    const params = new HttpParams().append('id', id).append('type', type).append('folderId', folderId);
    const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt07/vt070000/99/8';
    return this.http.get(url, { headers, params });
  }

  uploadDocument(file, id, type): Observable<any> {

    const formData = new FormData();
    formData.append('file', file);
    formData.append('documentId', id);
    formData.append('documentType', type);

    const params = new HttpParams();
    const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt07/vt070003/uploadFile';
    const options = {
      params: params,
      reportProgress: true,
    };

    return this.http.post(url, formData, options);
  }

  viewDocument(searchCondition: any): Observable<any> {
    const account = this.tokenService.getAccount();

    const accountParam = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
    };

    const param = Object.assign({}, accountParam, searchCondition);

    const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt07/vt070003/viewFile';
    return this.http.post(url, param);
  }

  exportSearchResult(data: any): Observable<any> {
    const account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      data: data
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/99/9';
    return this.http.post(url, obj, { observe: 'response', responseType: 'blob' });
  }
}
