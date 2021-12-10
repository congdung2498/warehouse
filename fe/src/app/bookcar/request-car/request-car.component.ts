import {Component, OnInit, OnDestroy, ViewChild} from '@angular/core';
import {BookcarService} from '../bookcar.service';
import {MessageService} from 'primeng/components/common/messageservice';

import {TokenStorage} from '../../shared/token.storage';

import {Router, NavigationEnd} from '@angular/router';
import {FormBuilder, FormGroup} from "@angular/forms";
import {RequestCar} from "../Entity/RequestCar";

import {ConfirmationService, SelectItem} from "primeng/api";
import {AppComponent} from "../../app.component";
import {AutoComplete, Calendar} from "primeng/primeng";
import {DatePipe} from "@angular/common";

import {DriverSquadSearch} from "../Entity/DriverSquadSearch";


import {DateTimeUtil} from "../../../common/datetime";
import {Journey, Seat, Type} from "../Entity/CarDetails";
import {ListTripAll} from "../Entity/ListTrip";

@Component({
    selector: 'app-request-car',
    templateUrl: './request-car.component.html',
    styleUrls: ['./request-car.component.css']
})
export class RequestCarComponent implements OnInit {

    @ViewChild('documentF') documentF: AutoComplete;
    @ViewChild('transferDateFromF') transferDateFromF: Calendar;
    @ViewChild('transferDateToF') transferDateToF: Calendar;
    resultList: any = [];

    fullNameUnit: String;
    unitId: String;
    phoneNumber: String;
    documentList: ListTripAll[];
    lddvNameList: ListTripAll[];
    placeStartList: ListTripAll[];
    driverSquadName: DriverSquadSearch;
    journey: Journey;
    requestCar: RequestCar = {
        carBookingId: '',
        squadId: '',
        routeWay: '',
        type: '',
        seat: '',
        startPlace: null,
        targetPlace: '',
        route: '',
        totalPartner: null,
        listPartner: '',
        reason: '',
        createUser: '',
        approverDir: '',
        approverLead: '',
        approverPre: '',
        dateStart: null,
        dateEnd: null
    };
    driveSquad: DriverSquadSearch = {
        typeId: null,
        seatId: null,
        squadId: null,
        squadName: null,
        placeId: null,
        placeName: null,
        userName: null,
        userCreate: null,
        fullName: null,
        status: null,
        unitName: null,
        emplPhone: null,
        displayOption: null,
        licensePlate: null,
        pageNumber: null,
        pageSize: null,
        totalRecords: null
    };


    startRow: number;
    rowSize: number;
    formSearch: FormGroup;
    dateStart: Date;
    dateEnd: Date;
    unit: string;
    userName: string;
    placeName: string;
    squadList: DriverSquadSearch[];
    typeList: Type[];
    seatList: Seat[];
    routeWayList: Journey[];
    cvpNameList: DriverSquadSearch[];


    // startPlace: string;
    //
    // placeId: number;


    vn = DateTimeUtil.vn;


    constructor(private formBuilder: FormBuilder,
                private    bookcarService: BookcarService,
                private confirmationService: ConfirmationService,
                private messageService: MessageService,
                private router: Router,
                private app: AppComponent,
                private tokenService: TokenStorage) {

        let userInfo = this.tokenService.getUserInfo();
        this.unitId = userInfo.unitName;
        this.fullNameUnit = userInfo.fullName;
        this.phoneNumber = userInfo.phoneNumber;

// doi xe
        this.bookcarService.findAllSquad(this.driveSquad).subscribe(res => {
            console.log('constructor FindAllQquad');
            console.log(res);
            this.squadList = res.data;
        });


// loai xe
        this.bookcarService.findAllTypeCars().subscribe(res => {
            console.log(res);
            this.typeList = res.data;

        });


        // so cho
        this.bookcarService.findAllSeatCars().subscribe(res => {
            console.log(res);
            this.seatList = res.data;
        });

// hanh trinbh
        this.bookcarService.getJourney().subscribe(res => {
            console.log(res);
            this.routeWayList = res.data;
        });


    }


    ngOnInit() {

        this.buildForm();
        // this.processSearch();
    }

    get f() {
        return this.formSearch.controls;
    }

    private buildForm(): void {
        this.formSearch = this.formBuilder.group({
            driverSquadName: [], //
            type: [], //
            dateStart: [], //
            dateEnd: [], //
            route: [],
            routeWay: [],
            reason: [],
            listPartner: [],
            startPlace: [], //
            targetPlace: [], //
            seat: [],
            journey: [],
            totalPartner: [], //
            qlttName: [],
            lddvName: [], //
            cvpName: [],

        });
    }

// validateForm
    validateForm(formSearch: FormGroup): boolean {
        if (formSearch.value.driverSquadName == '') {
            this.app.errorValidateDate('Đội xe không được để trống');
            return false;
        }
        if (formSearch.value.type == '') {
            this.app.errorValidateDate('Loại xe không được để trống');
            return false;
        }
        if (formSearch.value.dateStart == '') {
            this.app.errorValidateDate('Ngày bắt đầu không được để trống');
            return false;
        }
        if (formSearch.value.dateEnd == '') {
            this.app.errorValidateDate('Ngày kết thúc không được để trống');
            return false;
        }
        if (formSearch.value.startPlace == '') {
            this.app.errorValidateDate('Điểm đi không được để trống');
            return false;
        }
        if (formSearch.value.totalPartner == null) {
            this.app.errorValidateDate('Số người tham gia không được để trống');
            return false;
        }
        if (formSearch.value.lddvName == '') {
            this.app.errorValidateDate('Lãnh đạo đơn vị không được để trống');
            return false;
        }

        return true;
    }


    validateDate(): boolean {


        if (this.dateStart != null && this.dateStart !== undefined) {
            this.requestCar.dateStart = new Date(this.dateStart);

        } else {
            this.requestCar.dateStart = null;
        }
        if (this.dateEnd != null && this.dateEnd !== undefined) {
            this.requestCar.dateEnd = new Date(this.dateEnd);
        } else {
            this.requestCar.dateEnd = null;
        }
        // tslint:disable-next-line:max-line-length
        if (this.requestCar.dateStart != null && this.requestCar.dateEnd != null && this.requestCar.dateEnd < this.requestCar.dateStart) {
            this.app.errorValidateDate('Ngày bắt đầu phải nhỏ hơn ngày kết thúc');

            return false;
        }
        // tslint:disable-next-line:max-line-length
        if (this.requestCar.dateStart != null && this.requestCar.dateStart > new Date(Date.now())) {
            this.app.errorValidateDateNow('Ngày kết thúc phải lớn hơn ngày hiện tại');

            return false;
        }
        // tslint:disable-next-line:max-line-length
        if (this.requestCar.dateEnd != null && this.requestCar.dateEnd > new Date(Date.now())) {

            this.app.errorValidateDateNow('Ngày bắt đầu phải lớn hơn ngày hiện tại');
            return false;
        }
        return true;
    }


    settingParams() {
        this.requestCar.routeWay = this.formSearch.value.routeWay;
        this.requestCar.approverPre = this.formSearch.value.cvpName.userName;
        this.requestCar.squadId = this.formSearch.value.driverSquadName.squadId;
        this.requestCar.approverLead = this.formSearch.value.lddvName.userName;
        this.requestCar.approverDir = this.formSearch.value.qlttName.userName;
        this.requestCar.totalPartner = this.formSearch.value.totalPartner;
        this.requestCar.route = this.formSearch.value.journey.journeyId;
        this.requestCar.targetPlace = this.formSearch.value.targetPlace;
        this.requestCar.startPlace = this.formSearch.value.startPlace.placeId;
        this.requestCar.listPartner = this.formSearch.value.listPartner;
        this.requestCar.reason = this.formSearch.value.reason;
        this.requestCar.seat = this.formSearch.value.seat.seatId;
        this.requestCar.type = this.formSearch.value.type.typeId;
        this.requestCar.createUser = this.fullNameUnit;
    }

    save() {

        if (this.validateDate() === false) {
            return;
        }
        if (!this.validateForm(this.formSearch)) {
            return;
        }
        console.log(this.requestCar);
        this.settingParams();
        this.app.confirmMessage(null, () => {// on accepted

            this.bookcarService.insertRequesrCars(this.requestCar).subscribe(res => {
                this.router.navigate(['/book-car/reportBookCar']);
                this.app.showSuccess('Gửi thành công');
            });

        }, () => {
            this.app.showWarn('Gửi thất bại');
        });

    }


    getManager(ev) {

        this.bookcarService.getListManagerDirect(ev.query)
            .subscribe(res => {
                console.log(res);
                this.documentList = res.data;

            });
    }

    selectManager(item) {
        this.formSearch.controls['qlttName'].setValue(item.fullName);


    }

    clearSearch() {
        this.formSearch.controls['qlttName'].setValue(null);

    }

    getManagerUnit(ev) {
        this.bookcarService.getListManagerUnit(ev.query)
            .subscribe(res => {
                console.log(res);
                this.lddvNameList = res.data;

            });
    }

    getManagerChief(ev) {

        this.bookcarService.getListManagerChief(ev.query)
            .subscribe(res => {
                console.log(res);
                this.cvpNameList = res.data;

            });
    }

    getPlaceStart(ev) {


        this.bookcarService.getListPlaceStart(ev.query)
            .subscribe(res => {
                console.log(res);
                this.placeStartList = res.data;

            });
    }

    return() {
        this.router.navigate(['/book-car/reportBookCar']);
    }
}
