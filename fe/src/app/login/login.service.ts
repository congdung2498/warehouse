import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/delay';
import { Constants } from '../shared/containts';

const httpOptions = {
  headers: new HttpHeaders(
    {
      'Authorization': 'Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0',
      'Content-Type': 'application/x-www-form-urlencoded'
    })
};

@Injectable()
export class LoginService {

  private _headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) {}

  attemptAuth(ussername: string, password: string): Observable<any> {
    const params = new URLSearchParams();
    params.append('username', ussername);
    params.append('password', password);
    params.append('grant_type', 'password');
    // const credentials = {username: ussername, password: password, grant_type: 'password'};
    console.log('attempAuth ::' + ussername);
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt00/vt000000/03', params.toString(), httpOptions);
  }

  changeGuardPassword(oldPassword: String, newPassword: String) {
    const params = {
      'oldPassword' : oldPassword,
      'newPassword' : newPassword
    };
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt00/vt000000/09', params);
  }
}
