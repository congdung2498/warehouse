import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Employee} from "../Entity/Employee";
import {Observable} from "rxjs/Observable";
import {Constants} from "../shared/containts";
import {SystemCode} from "./Entity/SystemCode";
import {InOutRegister} from "./Entity/InOutRegister";
import {Checking, UpdatingChecking} from "./Entity/Checking";
import {TokenStorage} from "../shared/token.storage";

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class InOutEmployeeService {

    constructor(private http: HttpClient, private tokenService: TokenStorage) { }


    getParentUnitId(unitId: string): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/kitchen/lunch/get-parent-unit';
        let account = this.tokenService.getAccount();
        let object = {search: unitId, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
        return this.http.post(url, object, httpOptions);
    }

    managerEmployeeAutocomplete(search: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/checking/manager-employee-autocomplete';
        let account = this.tokenService.getAccount();
        let object = {search: search, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
        return this.http.post(url, object, httpOptions);
    }

    employeeAutocomplete(search: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/checking/employee-autocomplete';
        let account = this.tokenService.getAccount();
        let object = {search: search, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
        return this.http.post(url, object, httpOptions);
    }

    getWithEmployee(userNames: string[]): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/checking/get-with-employee';
        let account = this.tokenService.getAccount();
        let object = {userNames: userNames, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
        return this.http.post(url, object, httpOptions);
    }

    updateChecking(checking: Checking): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/checking/update';
        let account = this.tokenService.getAccount();
        checking.securityUsername = account.securityUsername;
        checking.securityPassword = account.securityPassword;
        return this.http.post(url, checking, httpOptions);
    }

    approveByManager(object: UpdatingChecking) : Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/checking/approve-by-manager';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    updateStatusApproveChecking(object: UpdatingChecking) : Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/checking/update-status-approve';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    updateMoreTimeChecking(checking: Checking): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/checking/update-more-time';
        let account = this.tokenService.getAccount();
        checking.securityUsername = account.securityUsername;
        checking.securityPassword = account.securityPassword;
        return this.http.post(url, checking, httpOptions);
    }

    createChecking(checking: Checking): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/checking/create';
        let account = this.tokenService.getAccount();
        checking.securityUsername = account.securityUsername;
        checking.securityPassword = account.securityPassword;
        return this.http.post(url, checking, httpOptions);
    }

    getSelectingModel(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/checking/get-selecting-model';
        let account = this.tokenService.getAccount();
        return this.http.post(url, account, httpOptions);
    }

    add(item: InOutRegister) {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010001/01';
        let account = this.tokenService.getAccount();
        item.securityUsername = account.securityUsername;
        item.securityPassword = account.securityPassword;
        return this.http.post(url, item, httpOptions);
    }

    update(item: InOutRegister) {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010002/01';
        let account = this.tokenService.getAccount();
        item.securityUsername = account.securityUsername;
        item.securityPassword = account.securityPassword;
        return this.http.post(url, item, httpOptions);
    }

    findEmployee(employee: Employee): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010001/02';
        let account = this.tokenService.getAccount();
        employee.securityUsername = account.securityUsername;
        employee.securityPassword = account.securityPassword;
        return this.http.post(url, employee, httpOptions);
    }

    findEmployeeByUserName(employee: Employee): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010001/04';
        let account = this.tokenService.getAccount();
        employee.securityUsername = account.securityUsername;
        employee.securityPassword = account.securityPassword;
        return this.http.post(url, employee, httpOptions);
    }

    //  count total record
    countTotalRecord(object: any) {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010011/04';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getListInOut(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt01/vt010011/02';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getCheckingByBarcode(barcode: string): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/checking/get-checking-model-by-barcode';
        let account = this.tokenService.getAccount();
        let object = {barcode: barcode, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
        return this.http.post(url, object, httpOptions);
    }
}