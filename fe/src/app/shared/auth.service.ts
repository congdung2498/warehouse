import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Constants } from './containts';

const httpOptions = {
  headers: new HttpHeaders(
    {
      'Authorization': 'Basic YnJvd3Nlci1jbGllbnQ6c2VjcmV0',
      'Content-Type': 'application/x-www-form-urlencoded'
    })
};

@Injectable()
export class AuthService {

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

  attemptAuthRefresh(refrestToken: string): Observable<any> {
    const params = new URLSearchParams();
    params.append('refresh_token', refrestToken);
    params.append('grant_type', 'refresh_token');
    return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt00/vt000000/03', params.toString(), httpOptions);
  }

  getUserInfoByTicket(ticket: string): Observable<any> {
    console.log('attempAuth with ticket ::' + ticket);
    return this.http.get(Constants.HOME_URL + 'com/viettel/vtnet360/vt00/vt000000/08?ticket=' + ticket, httpOptions);
  }
}
