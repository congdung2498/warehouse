import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/delay';
import {Observable} from 'rxjs/Observable';
import {Constants} from '../shared/containts';
import { map } from 'rxjs/operators/map';
import { TreeNode } from 'primeng/components/common/api';
import {Service} from "../shared/service";
const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class ServiceDataService extends Service {

    constructor(http: HttpClient) {
        super(http);
    }

    private dataToTreeNode(cont: any): TreeNode {
        return {
            label: cont.unitName,
            data: cont.unitId,
            leaf: cont.isLeaf === null ? true : false
        };
    }

    private dataToTree(data: any[]): TreeNode[] {
        const nodes = [];
        for (const cont of data) {
            nodes.push(this.dataToTreeNode(cont));
        }
        return nodes;
    }
    /**
     *
     *  ---- Service Menu Component ----
     *
     **/
    //  get list of places
    getPlaces(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/01';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  get list of services
    getServices(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/02';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  get list of unit received
    getUnitReceived(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/03';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  get list receiver
    getReceivers(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/04';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  count total receiver
    getReceiverOfService(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/05';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  get list services search
    getListSearchMenuServices(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/06';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  count total menu services
    countTotalMenuService(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/07';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  set up service
    handleSetUpService(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/08';
        return this.http.post(url, object, httpOptions);
    }

    //get list receiver for sussgest execute
    getReceiversForSussgest(object: any): Observable<any> {
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/09';
        return this.http.post(url, object, httpOptions);
    }

    /**
     *
     *  ---- Stationery Menu Component ----
     *
     **/
    //  get list name and id of stationery
    getStationeryNameId(object: any) {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040002/01';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  get list type and unit calculation of stationery
    getCodeStationery(object: any) {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040002/02';
        let param = {search: object, securityUsername: this.getAccount().securityUsername, securityPassword: this.getAccount().securityPassword};
        return this.http.post(url, param, httpOptions);
    }

    //  get list report stationery
    getReportStationery(object: any) {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040002/03';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  count total record of stationery
    countTotalRecordStationery(object: any) {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040002/04';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  set up stationery
    handleSetUpStationery(object: any) {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040002/05';
        return this.http.post(url, object, httpOptions);
    }

    /**
     *
     * ---- Report Service Component ----
     *
     */
    //  get list registration service
    getReportRegisService(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/issuesService/search';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  count total record
    countTotalRecordReportService(object: any) {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040010/02';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  get list stationery
    getListStationery(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040010/03';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  count total record of stationery
    countTotalRecordStationreyVTTB(object: any) {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040010/04';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  get file excel
    exportExcel(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040010/05';
        return this.http.post<any>(url, object, {responseType: 'arraybuffer' as 'json'});
    }

    //  get file excel VTTB
    exportExcelVTTB(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040010/06';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post<any>(url, object, {responseType: 'arraybuffer' as 'json'});
    }

/*    getUserInfo(): Observable<any> {
        const url =
            Constants.HOME_URL + '/com/viettel/vtnet360/issuesService/userInfo';
        return this.http.post(url, httpOptions);
    }*/

    getListEmployeeManager(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt00/vt000000/02';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    insertIssuesService(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/issuesService/insert';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getListEmployeeSeachMulti(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/issuesService/employeeSeachMulti';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getListPlace(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/issuesService/place';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getListUnit(query: string) {
        if (query === null || query === undefined) {
            query = '';
        }
        return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/get-unit-tree',
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

    getListService(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/issuesService/service';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getListIssuesBrowserService(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt04/vt040004/01';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }
    getTotalIssuesBrowserService(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/issuesService/totalIssuesServiceForApprove';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getListIdIssuesService(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt04/vt040000/04';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }


    exportServiceExcel(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/10';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post<any>(url, object, {responseType: 'arraybuffer' as 'json'});
    }

    exportTemplate(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/downloadTemplate';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, {observe: 'response', responseType: 'blob'});
    }

    //TODO : review and rewrite
    importService(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/uploadService';
        return this.http.post(url, object, {observe: 'response', responseType: 'blob'});
    }

    getIssuesService(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/issuesService/detail';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getListIssuesServiceRequestApproved(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040000/02';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getTotalIssuesServiceRequestApproved(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/totalIssuesService';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    updateIssuesService(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/issuesService/executive-Service ';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getStationerysForSussgest(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt04/vt040007/02';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    exportExcelstationeryReport(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/exportStationery';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post<any>(url, object,  { responseType: 'arraybuffer' as 'json' });
    }

    templateImportExcelStationeryReport(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/downloadTemplateStationery';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object,  { observe: 'response', responseType: 'blob'});
    }

    importExcelStationeryReport(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/uploadStationery';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, {observe: 'response', responseType: 'blob'});
    }

    finDatailIssueseService(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt04/vt040000/03';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    updateHandoverIssuesService(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt04/vt040000/05';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    historyIssuesService(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/issuesService/history';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    findSignVoffice(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/signVoffice/find-sign-voffice';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    exportSignVoffice(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/signVoffice/export-signVoffice';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post<any>(url, object, {responseType: 'arraybuffer' as 'json'});
    }

    insertSignVoffice(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/signVoffice/insert-sign-voffice';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    updateSignVoffice(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/signVoffice/update-sign-voffice';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    signVoffice(object: any): Observable<any> {
        const url = Constants.HOME_URL + '/com/viettel/vtnet360/vt00/vt000000/15';
        return this.http.post(url, object, httpOptions);
    }

    exportSigVoficeSynthetic(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/signVoffice/export-stationery-Synthetic';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post<any>(url, object, {responseType: 'arraybuffer' as 'json'});
    }

    exportSigVoficeDetail(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/signVoffice/export-stationery-detail';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post<any>(url, object, {responseType: 'arraybuffer' as 'json'});
    }
    exportSigVofice(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/signVoffice/get-file-sign';
        object.securityUsername = this.getAccount().securityUsername;
        object.securityPassword = this.getAccount().securityPassword;
        return this.http.post<any>(url, object, {responseType: 'arraybuffer' as 'json'});
    }

    //TODO: keep doing
    downloadDetailFile(item: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/signVoffice/download-detail-file';
        return this.http.post<any>(url, item, { responseType: 'arraybuffer' as 'json' });
    }

    getSectors(query: string) {
        if (query === null || query === undefined) {
            query = '';
        }
        return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/get-unit-tree',
            {
                'query': query,
                'securityUsername': this.getAccount().securityUsername,
                'securityPassword': this.getAccount().securityPassword,
            },
            httpOptions).pipe(
            map(res => {
                return this.dataToTree(res['data']);
            })
        );
    }
}
