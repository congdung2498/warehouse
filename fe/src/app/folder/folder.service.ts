import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from '../shared/containts';
import { TokenStorage } from '../shared/token.storage';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Folder } from './Entity/Folder';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class FolderService {

  constructor(private http: HttpClient, private tokenService: TokenStorage) {
  }

  searchFolderByNameOrQrCode(qrCode:string, pageNumber: number, pageSize: number): Observable<any> {
    let account = this.tokenService.getAccount();
    const postData: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      qrCode: qrCode,
      pageNumber: pageNumber,
      pageSize: pageSize
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070002/88/1';
    return this.http.post(url, postData, httpOptions);
  }

  getProjectsInFolder(folderId: number): Observable<any> {
    let account = this.tokenService.getAccount();
    const postData: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      folderId: folderId,
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070002/88/2';
    return this.http.post(url, postData, httpOptions);
  }

  getProjectsNotInFolder(folderId: number): Observable<any> {
    let account = this.tokenService.getAccount();
    const postData: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      folderId: folderId,
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070002/88/3';
    return this.http.post(url, postData, httpOptions);
  }

  addDocsInProjectToFolder(postData: any): Observable<any> {
    let account = this.tokenService.getAccount();
    postData.securityUsername = account.securityUsername;
    postData.securityPassword = account.securityPassword;    
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070002/03';
    return this.http.post(url, postData, httpOptions);
  }

  getProjectDetails(folderId: number, projectId: number): Observable<any> {
    let account = this.tokenService.getAccount();
    const postData: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      folderId: folderId,
      projectId: projectId,
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070002/15';
    return this.http.post(url, postData, httpOptions);
  }

}
