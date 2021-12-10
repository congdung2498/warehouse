import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/delay';
import {of} from 'rxjs/observable/of';
// import { LEADERS } from './mock-data';
import {Employee} from '../Entity/Employee';
import {Car} from './Entity/Cars';
import {Drive} from './Entity/Drive';
import {MatchingDC} from './Entity/MatchingDC';
import {Condition} from './Entity/ConditionSearch';
import {ListTrip} from './Entity/ListTrip';
import {SearchingCondition} from './Entity/searchingCodition';
import {DriveSquad} from './Entity/DriveSquad';
import {Constants} from '../shared/containts';
import {TokenStorage} from "../shared/token.storage";

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class BookcarService {
    constructor(private http: HttpClient, private tokenService: TokenStorage) {
    }


    getCarBookingModel(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/driving/get-car-booking-model';
        let account = this.tokenService.getAccount();
        return this.http.post(url, account, httpOptions);
    }

    //  get team car
    getDriveSquad(object: any): Observable<any> {
        if (object.squadName != null) {
            object.squadName = object.squadName.trim()
        }
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030000/03';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  get list of employee
    getEmployees(object: any): Observable<any> {
        if (object.result != null) {
            object.result = object.result.trim()
        }
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030014/01';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post<any>(url, object, httpOptions);
    }

    //  get list of employee's phone number
    getEmployeesPhone(object: any): Observable<any> {
        if (object.result != null) {
            object.result = object.result.trim()
        }
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030014/02';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post<any>(url, object, httpOptions);
    }

    //  get list of driver
    getListDriver(object: any): Observable<any> {
        if (object.result != null) {
            object.result = object.result.trim()
        }
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030014/03';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post<any>(url, object, httpOptions);
    }

    //  get car type
    getCarsType(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt00/vt000000/01';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post<any>(url, object, httpOptions);
    }

    //  get list of trips infor
    getListTrip(object: Condition): Observable<ListTrip[]> {
        if (object.driveSquadName != null) {
            object.driveSquadName = object.driveSquadName.trim();
        }
        if (object.driverInfo != null) {
            object.driverInfo = object.driverInfo.trim();
        }
        if (object.empInfo != null) {
            object.empInfo = object.empInfo.trim();
        }
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030014/04';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post<any[]>(url, object, httpOptions);
    }

    getAllListTrip(object: Condition): Observable<ListTrip[]> {
        if (object.squadName != null) {
            object.squadName = object.squadName.trim();
        }
        if (object.driverInfo != null) {
            object.driverInfo = object.driverInfo.trim();
        }
        if (object.empInfo != null) {
            object.empInfo = object.empInfo.trim();
        }
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/findAllListTrip';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post<any[]>(url, object, httpOptions);
    }
    //  get report file excel
    getExcel(object: Condition): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030014/05';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post<any>(url, object, {
            responseType: 'arraybuffer' as 'json'
        });
    }

    //  count total record
    countTotalRecord(object: Condition) {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030014/06';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    // ********************** START CODE MOI ***************************
    //TODO : review and rewrite
    findPlace(placeName: string): Observable<any> {
        if (placeName != null) {
            placeName = placeName.trim()
        }
        const obj: any = {
            placeName: placeName,
            pageSize: Constants.MAX_RECORD_DISPLAY,
            pageNumber: 0
        };
        return this.http.post<any>(
            Constants.HOME_URL + 'com/viettel/vtnet360/vt00/vt000000/04', obj,
            httpOptions
        );
    }

    findLeader(placeId: string, fullName: string): Observable<any> {
        if (fullName != null) {
            fullName = fullName.trim()
        }
        let account = this.tokenService.getAccount();
        const obj: any = {
            placeId: placeId,
            fullName: fullName,
            pageSize: Constants.MAX_RECORD_DISPLAY,
            pageNumber: 0,
            securityUsername: account.securityUsername,
            securityPassword: account.securityPassword
        };
        return this.http.post<any>(
            Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030001/02', obj,
            httpOptions
        );
    }

    findSearchingResult(obj: SearchingCondition): Observable<any> {
        if (obj.squadName != null) {
            obj.squadName = obj.squadName.trim()
        }
        let account = this.tokenService.getAccount();
        obj.securityUsername = account.securityUsername;
        obj.securityPassword = account.securityPassword;
        return this.http.post<any>( Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030001/03', obj,httpOptions);
    }

    insertUpdate(bookCarId: string,
                 squadName: string,
                 placeId: string,
                 userName: string,
                 status: string,
                 phone: string): Observable<any> {
        let account = this.tokenService.getAccount();
        const obj: any = {
            squadId: bookCarId,
            squadName: squadName,
            placeId: placeId,
            userName: userName,
            status: status,
            emplPhone: phone,
            securityUsername: account.securityUsername,
            securityPassword: account.securityPassword
        };
        return this.http.post(
            Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030001/04', obj,
            httpOptions
        );
    }

    deleteSquad(squadId: string): Observable<any> {
        let account = this.tokenService.getAccount();
        const obj: any = {
            squadId: squadId,
            securityUsername: account.securityUsername,
            securityPassword: account.securityPassword
        };
        return this.http.post(
            Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030001/05', obj,
            httpOptions
        );
    }

    // ********************** END CODE MOI ***************************

    // Toàn bộ danh sách danh mục xe
    //TODO
    getListcar(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030002/01';
        let account = this.tokenService.getAccount();
        return this.http.post(url, account, httpOptions);
    }

    // get seat
    //TODO
    getCarSeat(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030002/02';
        let account = this.tokenService.getAccount();
        return this.http.post(url, account, httpOptions);
    }

    // get type
    //TODO
    getCarType(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030002/03';
        let account = this.tokenService.getAccount();
        return this.http.post(url, account, httpOptions);

    }

    // get doi xe
    getTeamCar(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030002/04';
        let account = this.tokenService.getAccount();
        return this.http.post(url, account, httpOptions);
    }

    // search Cars
    searchListcar(object: Car): Observable<any> {
        if (object.squadName != null) {
            object.squadName = object.squadName.trim()
        }
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030002/05';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    // insert
    insertListcar(object: Car): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030002/06';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    // updateListCar
    updateListcar(object: Car): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030002/07';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);

    }

    // delete
    deleteCar(obj: Car): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030002/08';
        let account = this.tokenService.getAccount();
        obj.securityUsername = account.securityUsername;
        obj.securityPassword = account.securityPassword;
        return this.http.post(url, obj, httpOptions);
    }

    // get all Driver employee
    getDrivers(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/01';
        let account = this.tokenService.getAccount();
        return this.http.post(url, account, httpOptions);
    }


    getListDrive(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/02';
        let account = this.tokenService.getAccount();
        return this.http.post(url, account, httpOptions);
    }

    searchListDrive(object: Drive): Observable<any> {
        if (object.squadName != null) {
            object.squadName = object.squadName.trim()
        }
        if (object.fullName != null) {
            object.fullName = object.fullName.trim()
        }
        if (object.employeePhone != null) {
            object.employeePhone = object.employeePhone.trim()
        }
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/04';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getTeamCarvt030003(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/03';
        let account = this.tokenService.getAccount();
        return this.http.post(url, account, httpOptions);
    }

    insertDrive(object: Drive): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/05';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    updateDrive(object: Drive): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/06';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    deleteDrive(object: Drive): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/07';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    insertDriveCar(object: MatchingDC[]): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030004/01';
        let account = this.tokenService.getAccount();
        let param = {matches: object, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
        return this.http.post(url, param, httpOptions);
    }

    findSquad(object: DriveSquad): Observable<any> {
        if (object.squadName != null) {
            object.squadName = object.squadName.trim()
        }
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/08';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    findDriver(object: Employee): Observable<any> {
        if (object.fullName != null) {
            object.fullName = object.fullName.trim()
        }
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/09';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    findMatchedCar(object: MatchingDC): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030004/02';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    deleteMatchedCar(object: MatchingDC): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030004/03';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }
    // search All car free times
    getCarFreeTimes(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030013/free-car';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }


    //  update employee
    getListCarRoute(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030009/03';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    //  insert request car
    insertRequesrCars(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030005/03';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    findAllSquad(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030000/03';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    findAllTypeCars(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030002/03';
        let account = this.tokenService.getAccount();
        return this.http.post(url, account, httpOptions);
    }

    findAllSeatCars(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030002/02';
        let account = this.tokenService.getAccount();
        return this.http.post(url, account, httpOptions);
    }

    getJourney(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030010/02';
        return this.http.post(url, this.tokenService.getAccount(), httpOptions);
    }

    findPlaceStart(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030000/03';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getListManagerDirect(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt00/vt000000/02';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getListManagerUnit(text: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchQldv';
        let account = this.tokenService.getAccount();
        let condition = {search: text, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
        return this.http.post(url, condition, httpOptions);
    }

    getListManagerChief(text: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchQlcvp';
        let account = this.tokenService.getAccount();
        let condition = {search: text, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
        return this.http.post(url, condition, httpOptions);
    }

    getListPlaceStart(text: any): Observable<any> {
        let account = this.tokenService.getAccount();
        let condition = {searchDTO: text, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchPlaceStart';
        return this.http.post(url, condition, httpOptions);
    }

    getUnitId(text: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchUnit';
        let account = this.tokenService.getAccount();
        let condition = {search: text, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
        return this.http.post(url, condition, httpOptions);
    }

    //TODO
    getFullName(text: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchFullName';
        let account = this.tokenService.getAccount();
        let condition = {search: text, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
        return this.http.post(url, condition, httpOptions);
    }

    //TODO
    getPhoneNumber(text: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchPhoneNumber';
        let account = this.tokenService.getAccount();
        let condition = {search: text, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
        return this.http.post(url, condition, httpOptions);
    }

    //TODO
    searchDriveName(text: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchDriveName' ;
        let account = this.tokenService.getAccount();
        let condition = {search: text, securityUsername: account.securityUsername, securityPassword: account.securityPassword};
        return this.http.post(url, condition, httpOptions);
    }

    // updateCarRoute
    updateCarRoute(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030009/01';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    // updateListCar
    updateCarDetails(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/updateCarDetails';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    // updateListCar
    updateRejectCarDetails(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/updateRejectCarDetails';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);

    }

    cancelRequest(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030011/01';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);

    }
    //  insert request car
    insertRequesrCarsDetails(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/insertCarDetails';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

// updateListCar
    updateNewCarDetails(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/updateNewCarDetails';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);

    }

    exportExcel(object: any): Observable<any> {
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030013/06', object, { responseType: 'arraybuffer' as 'json' });

    }
    exportExcelFreeCar(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030013/export-file-free-car';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post<any>(url, object, {responseType: 'arraybuffer' as 'json'});
    }

    // updateListCar
    updateCompleteCarDetails(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/updateCompleteCarDetails';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);

    }

    searchPlaceName(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchPlaceName';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    searchDriveNameAll(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchDriveNameAll';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    searchAllCars(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchAllCars';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    searchDriveSquadName(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchDriveSquadName';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    searchLicensePlate(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchLicensePlate';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    searchSuggestionLicensePlate(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchSuggestionLicensePlate';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    getPlaceByAll(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030002/02';
        return this.http.post(url, this.tokenService.getAccount(), httpOptions);
    }

    getQlttByUserName(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/getQlttByUserName';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    getQldvByUserName(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/getQldvByUserName';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    getQlcvpByUserName(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/getQlcvpByUserName';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    findAllListTripById(object: Condition): Observable<ListTrip[]> {
        if (object.driveSquadName != null) {
            object.driveSquadName = object.driveSquadName.trim();
        }
        if (object.driverInfo != null) {
            object.driverInfo = object.driverInfo.trim();
        }
        if (object.empInfo != null) {
            object.empInfo = object.empInfo.trim();
        }
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;

        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/findAllListTripById';
        return this.http.post<any[]>(url, object, httpOptions);
    }

    updateListCarApprove(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/updateListCarApprove';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    //TODO
    searchPlaceStartAll(): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchPlaceStartAll';
        let account = this.tokenService.getAccount();
        return this.http.post(url, account, httpOptions);
    }

    getJourneyById(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030010/getJourneyById';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    findAllSquadById(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030000/findAllSquadById';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    getCarTypeById(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030002/getCarTypeById';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    getCarSeatById(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030002/getCarSeatById';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    findDrive(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/find-drive';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    searchDriveNameIsFree(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030008/01';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    searchCarsIsFree(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030008/02';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object , httpOptions);
    }

    updateCarRefuseOrder(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030008/04';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    updateRattingUser(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030012/02';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);

    }

    updateCarOrder(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030008/03';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    updateCarRefuse(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030006/01';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    updateCarApprove(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030006/01';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);

    }
    findCarRoute(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030009/02';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }

    getFreeTimeCount(object: any): Observable<any> {
        const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030013/07';
        let account = this.tokenService.getAccount();
        object.securityUsername = account.securityUsername;
        object.securityPassword = account.securityPassword;
        return this.http.post(url, object, httpOptions);
    }
}