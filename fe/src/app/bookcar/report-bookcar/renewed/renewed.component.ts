import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {DateTimeUtil} from "../../../../common/datetime";
import {BookcarService} from "../../bookcar.service";
import {DriverSquadSearch} from "../../Entity/DriverSquadSearch";
import {ListTripAll} from "../../Entity/ListTrip";
import {TokenStorage} from "../../../shared/token.storage";
import {EntityRqFindEmp} from "../../Entity/CarDetails";
import {AutoComplete, Calendar, ConfirmationService, Dropdown, MessageService} from "primeng/primeng";
import {ValidateUtils} from "../../../../common/validate";
import {AppComponent} from "../../../app.component";
import {RequestCar} from "../../Entity/RequestCar";


@Component({
    selector: 'booking-car-renewed',
    templateUrl: './renewed.component.html',
    styleUrls: ['./renewed.component.css'],
    providers: [TokenStorage]
})
export class RenewedComponent implements OnInit {

    @Input() userInfo: any;
    @Input() routeWayList: ListTripAll[];
    @Input() seatList: ListTripAll[];
    @Input() typeList: ListTripAll[];
    @Input() squadList: ListTripAll[];
    @Input() placeStartList: ListTripAll[];
    @Output() result: EventEmitter<any> = new EventEmitter<any>();

    //qlttName

    @ViewChild('createStartTimeFld') createStartTimeFld: Calendar;
    @ViewChild('createEndTimeFld') createEndTimeFld: Calendar;


    convertStatus = ['Chờ phê duyệt', 'Đã duyệt', 'Từ chối', 'Hủy đặt xe', 'Đã xếp xe', 'Bắt đầu đi',
        'Đã đến nơi', 'Hoàn thành', 'Quá hạn', 'Từ chối xếp xe', 'Chờ gia hạn'];
    STATUS_LABELS = ['Chờ phê duyệt', 'Đã duyệt', 'Từ chối', ''];

    vn = DateTimeUtil.vn;
    copyInfo: any;
    //flag if  is renew popup
    isRefuse: boolean = false;

    startDate: Date;
    endDate: Date;

    teamCarName: ListTripAll;
    journey: ListTripAll;
    carType: ListTripAll;
    seat: ListTripAll;
    startPlace: ListTripAll;
    tagetPlace: string;
    route: string;

    unitName: string;
    fullName: string;
    phoneNumber: string;

    numOfPassenger: number;
    listOfPassenger: string;
    reason: string;

    appoverQlttByUserList: ListTripAll[];
    appoverLddvByUserList: ListTripAll[];
    appoverCvpByUserList: ListTripAll[];

    appoverQltt: ListTripAll;
    appoverCvp: ListTripAll;
    appoverLddv: ListTripAll;


    flagCvp: number;
    flagLddv: number;
    flagQltt: number;


    driveSquad: DriverSquadSearch = {
        typeId: null, seatId: null, squadId: null, squadName: null, placeId: null, placeName: null, userName: null,
        userCreate: null, fullName: null, status: null, unitName: null, emplPhone: null, displayOption: null,
        licensePlate: null, pageNumber: null, pageSize: null, totalRecords: null
    };


    constructor(private _BookcarService: BookcarService, private tokenStorage: TokenStorage, private app: AppComponent, private confirmationService: ConfirmationService,) {
    }

    ngOnInit() {
    }

    doCopy() {
        if (this.copyInfo) {
            this.unitName = this.copyInfo.detailUnit;
            this.fullName = this.copyInfo.empName;
            this.phoneNumber = this.copyInfo.empPhone;

            this.flagCvp = this.copyInfo.flagCvp;
            this.flagLddv = this.copyInfo.flagLddv;
            this.flagQltt = this.copyInfo.flagQltt;


            this.startDate = new Date(this.copyInfo.dateStart);
            this.endDate = new Date(this.copyInfo.dateEnd);


            this.route = this.copyInfo.route;
            this.numOfPassenger = this.copyInfo.numOfPassenger;
            this.listOfPassenger = this.copyInfo.listOfPassenger;
            this.reason = this.copyInfo.reason;
            this.tagetPlace = this.copyInfo.tagetPlace;

            // loai hanh trinh
            if (this.copyInfo.routeType) {

                this.routeWayList.forEach(el => {
                    if (el.routeType === this.copyInfo.routeType) {
                        this.journey = el;
                    }
                })
            }
            // doi xe
            if (this.copyInfo.squadId) {
                this.squadList.forEach(el => {
                    if (el.squadId === this.copyInfo.squadId) {
                        this.teamCarName = el;
                    }
                })
            }
            // loai xe
            if (this.copyInfo.carType) {
                this.typeList.forEach(el => {
                    if (el.typeId === this.copyInfo.typeId) {
                        this.carType = el;
                    }
                })
            }
            // so cho
            if (this.copyInfo.seat) {

                this.seatList.forEach(el => {
                    if (el.seatId == this.copyInfo.seat) {
                        this.seat = el;
                    }
                })
            }
            if (this.copyInfo.startPlace) {
                let searchDataById = {searchDTO: '', searchPlaceName: ''}
                searchDataById.searchDTO = this.copyInfo.startPlace;
                this._BookcarService.searchPlaceName(searchDataById).subscribe(res => {
                    if (res) {
                        this.placeStartList  = res.data;
                        for (let i = 0; i <  this.placeStartList.length; i++) {
                            if ( this.placeStartList[i].startPlace === this.copyInfo.startPlace) {
                                this.startPlace =  this.placeStartList[i];
                                break;
                            }
                        }
                    }
                });
            }
//==========================================================================================================



            if (this.copyInfo.appoverQltt != null && this.copyInfo.appoverQltt != "") {
                let searchDataById = {searchDTO: '', searchPlaceName: ''}
                searchDataById.searchDTO = this.copyInfo.appoverQltt;
                this._BookcarService.getQlttByUserName(searchDataById).subscribe(res => {
                    this.appoverQlttByUserList = res.data;
                });
            } else {
                this.appoverQltt = null;
                this.appoverQlttByUserList = null;
            }
            if (this.copyInfo.appoverCvp != null && this.copyInfo.appoverCvp != "") {
                let searchDataById = {searchDTO: '', searchPlaceName: ''}
                searchDataById.searchDTO = this.copyInfo.appoverCvp;
                this._BookcarService.getQlcvpByUserName(searchDataById).subscribe(res => {
                    this.appoverCvpByUserList = res.data;
                });
            } else {
                this.appoverCvp = null;
                this.appoverCvpByUserList = null;
            }

            if (this.copyInfo.appoverLddv != null && this.copyInfo.appoverLddv != "") {
                let searchDataById = {searchDTO: '', searchPlaceName: ''};
                searchDataById.searchDTO = this.copyInfo.appoverLddv;
                this._BookcarService.getQldvByUserName(searchDataById).subscribe(res => {
                    this.appoverLddvByUserList = res.data;
                });
            } else {
                this.appoverLddv = null;
                this.appoverLddvByUserList = null;
            }
        }
    }

    validate(): boolean {
        if (!this.startDate) {
            ValidateUtils.validateTime(this.createStartTimeFld, 'Thời gian bắt đầu không được bỏ trống', this.app);
            return false;
        }
        if (!this.endDate) {
            ValidateUtils.validateTime(this.createEndTimeFld, 'Thời gian kết thúc không được bỏ trống', this.app);
            return false;
        }
        if (this.startDate < new Date()) {
            ValidateUtils.validateTime(this.createStartTimeFld, 'Thời gian bắt đầu không thể nhỏ hơn thời gian hiện tại', this.app);
            return false
        }

        this.startDate.setHours(this.startDate.getHours(), this.startDate.getMinutes(), 0, 0);
        this.endDate.setHours(this.endDate.getHours(), this.endDate.getMinutes(), 0, 0);
        if (this.startDate.getTime() >= this.endDate.getTime()) {
            ValidateUtils.validateTime(this.createStartTimeFld, 'Thời gian bắt đầu phải nhỏ hơn thời gian kết thúc', this.app);
            return false
        }
        return true;
    }


    // đăng kí lại
    renewal() {
        if (!this.validate()) return;

        this.copyInfo.dateStart = this.startDate;
        this.copyInfo.dateEnd = this.endDate;
        this.confirmationService.confirm({
            message: 'Đ/c chắc chắn muốn gia hạn chuyến xe này không?',
            header: 'Gia hạn chuyến xe',
            icon: 'pi pi-exclamation-triangle',
            acceptLabel: 'Đồng ý',
            rejectLabel: 'Hủy bỏ',
            accept: () => {
                this._BookcarService.updateCarDetails(this.copyInfo).subscribe(res => {
                    if (res.status == 1) {
                        this.result.emit(res.status);
                        this.app.showSuccess('Cập nhật thành công');
                    } else {
                        this.app.showWarn('Cập nhật thất bại');
                    }
                });
            }
        });
    }


}
