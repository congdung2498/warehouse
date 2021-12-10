import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {DateTimeUtil} from "../../../../common/datetime";
import {BookcarService} from "../../bookcar.service";
import {DriverSquadSearch} from "../../Entity/DriverSquadSearch";
import {ListTripAll} from "../../Entity/ListTrip";
import {TokenStorage} from "../../../shared/token.storage";
import {EntityRqFindEmp, UpdateBookCar} from "../../Entity/CarDetails";
import {AutoComplete, Calendar, ConfirmationService, Dropdown, MessageService} from "primeng/primeng";
import {ValidateUtils} from "../../../../common/validate";
import {AppComponent} from "../../../app.component";
import {RequestCar} from "../../Entity/RequestCar";


@Component({
    selector: 'booking-car-refuse',
    templateUrl: './refuse.component.html',
    styleUrls: ['./refuse.component.css'],
    providers: [TokenStorage]
})
export class RefuseComponent implements OnInit {

    @Input() userInfo: any;
    @Input() routeWayList: ListTripAll[];
    @Input() seatList: ListTripAll[];
    @Input() typeList: ListTripAll[];
    @Input() squadList: ListTripAll[];
    @Input() placeStartList: ListTripAll[];
    @Output() result: EventEmitter<any> = new EventEmitter<any>();
    @Output() refuse: EventEmitter<any> = new EventEmitter<any>();


    @ViewChild('createStartTimeFld') createStartTimeFld: Calendar;
    @ViewChild('createEndTimeFld') createEndTimeFld: Calendar;

    STATUS_LABELS = ['Chờ phê duyệt', 'Đã duyệt', 'Từ chối', ''];
    convertStatus = ['Chờ phê duyệt', 'Đã duyệt', 'Từ chối', 'Hủy đặt xe', 'Đã xếp xe', 'Bắt đầu đi',
        'Đã đến nơi', 'Hoàn thành', 'Quá hạn', 'Từ chối xếp xe', 'Chờ gia hạn'];

    vn = DateTimeUtil.vn;
    copyInfo: any;
    isShowDescription: boolean;
    reasonRefuse: string;

    startDate: Date;
    endDate: Date;

    unitName: string;
    fullName: string;
    phoneNumber: string;

    teamCarName: ListTripAll;
    journey: ListTripAll;
    carType: ListTripAll;
    seat: ListTripAll;
    startPlace: ListTripAll;
    tagetPlace: string;
    route: string;

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

    IsAQltt: boolean;
    IsAQldv: boolean;
    IsACvp: boolean;

    isQltt: boolean;
    isQdv: boolean;
    isCvp: boolean;
    check: number;


    driveSquad: DriverSquadSearch = {
        typeId: null, seatId: null, squadId: null, squadName: null, placeId: null, placeName: null, userName: null,
        userCreate: null, fullName: null, status: null, unitName: null, emplPhone: null, displayOption: null,
        licensePlate: null, pageNumber: null, pageSize: null, totalRecords: null
    };

    updateBookCar: UpdateBookCar = {
        status: null, bookCarId: '', listId: [''], reasonRefuse: '', userName: '', qltt: '',
        flagQltt: null, lddv: '', flagLddv: null, cvp: '', role: '', userRequest: ''
    };

    isShowApproveBtn: boolean;
    isShowRejectBtn: boolean;

    constructor(private _BookcarService: BookcarService, private tokenStorage: TokenStorage, private app: AppComponent, private confirmationService: ConfirmationService,) {
    }

    ngOnInit() {}

    doCopy() {
        if (this.copyInfo) {
            this.unitName = this.copyInfo.detailUnit;
            this.fullName = this.copyInfo.empName;
            this.phoneNumber = this.copyInfo.empPhone;

            let startTime: Date = new Date(this.copyInfo.dateStart);
            if(this.copyInfo.isApprove && (startTime.getTime() > new Date().getTime())) {
              this.isShowApproveBtn = true;
            }

            if(this.copyInfo.isReject) {
              this.isShowRejectBtn = true;
            }

            for (let i = 0; i < this.userInfo.role.length; i++) {
                if (this.userInfo.role[i] == 'PMQT_QL') {
                    this.check = 1;
                    this.IsAQltt = true;
                    break;
                } else if (this.userInfo.role[i] == 'PMQT_HC_DV') {
                    this.check = 2;
                    this.IsAQldv = true;
                    break;
                } else if (this.userInfo.role[i] == 'PMQT_CVP') {
                    this.check = 3;
                    this.IsACvp = true;
                    break;
                } else if (this.userInfo.role[i] == 'PMQT_ADMIN') {
                    this.check = 4;
                    this.IsAQldv = true;
                    this.IsAQltt = true;
                    this.IsACvp = true;
                    break;
                }
                else {
                    this.check = 5;
                    this.IsAQldv = false;
                    this.IsAQltt = false;
                    this.IsACvp = false;
                    break;
                }
            }
            if (this.check == 4) {
                this.isQltt = false;
                this.isQdv = false;
                this.isCvp = false;
            }
            else if (this.check == 1 && this.copyInfo.flagQltt == 0) {
                this.isQltt = false;
                this.isQdv = true;
                this.isCvp = true;
            } else if (this.check == 1 && this.copyInfo.flagQltt != 0) {
                this.isQltt = true;
                this.isQdv = true;
                this.isCvp = true;
            } else if (this.check == 2 && this.copyInfo.flagLddv == 0) {
                this.isQltt = true;
                this.isQdv = false;
                this.isCvp = true;
            } else if (this.check == 2 && this.copyInfo.flagLddv != 0) {
                this.isQltt = true;
                this.isQdv = true;
                this.isCvp = true;
            } else if (this.check == 3 && this.copyInfo.flagCvp == 0) {
                this.isQltt = true;
                this.isQdv = true;
                this.isCvp = false;
            } else if (this.check == 3 && this.copyInfo.flagCvp != 0) {
                this.isQltt = true;
                this.isQdv = true;
                this.isCvp = true;
            } else {
                this.isQltt = true;
                this.isQdv = true;
                this.isCvp = true;
            }

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
                        this.placeStartList = res.data;
                        for (let i = 0; i < this.placeStartList.length; i++) {
                            if (this.placeStartList[i].startPlace === this.copyInfo.startPlace) {
                                this.startPlace = this.placeStartList[i];
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
            }
            if (this.copyInfo.appoverCvp != null && this.copyInfo.appoverCvp != "") {
                let searchDataById = {searchDTO: '', searchPlaceName: ''}
                searchDataById.searchDTO = this.copyInfo.appoverCvp;
                this._BookcarService.getQlcvpByUserName(searchDataById).subscribe(res => {
                    this.appoverCvpByUserList = res.data;
                });
            } else {
                this.appoverCvp = null;
            }

            if (this.copyInfo.appoverLddv != null && this.copyInfo.appoverLddv != "") {
                let searchDataById = {searchDTO: '', searchPlaceName: ''};
                searchDataById.searchDTO = this.copyInfo.appoverLddv;
                this._BookcarService.getQldvByUserName(searchDataById).subscribe(res => {
                    this.appoverLddvByUserList = res.data;
                });
            } else {
                this.appoverLddv = null;
            }
        }

    }

    getPlaceStart(ev) {
        this._BookcarService.getListPlaceStart(ev.query).subscribe(res => {
            this.placeStartList = res.data;
        });
    }

    doRefuse() {
        this.refuse.emit(true);
    }

    approveHandle() {
        this.settingParamsApprove();
        this.confirmationService.confirm({
            message: 'Đồng chí chắc chắn muốn phê duyệt không?',
            header: 'Nhắc nhở phê duyệt',
            icon: 'pi pi-exclamation-triangle',
            acceptLabel: 'Đồng ý',
            rejectLabel: 'Hủy bỏ',
            accept: () => {
                this._BookcarService.updateCarApprove(this.updateBookCar).subscribe(res => {
                        if (res.status == 1) {
                            this.app.showSuccess('Đã phê duyệt yêu cầu sử dụng xe');
                            this.result.emit(1)
                        } else {
                            this.app.showWarn('Phê duyệt thất bại');
                        }
                    });
            }
        });
    }

    settingParamsApprove() {
        var listBookId = [];
        listBookId.push(this.copyInfo.carBookingId);
        this.updateBookCar.listId = listBookId;
        this.updateBookCar.bookCarId = this.copyInfo.carBookingId;
        if (this.appoverQltt) {
            this.updateBookCar.qltt = this.appoverQltt.userNameBooker;
        }
        if (this.appoverLddv) {
            this.updateBookCar.lddv = this.appoverLddv.userNameBooker;
        }
        if (this.appoverCvp) {
            this.updateBookCar.cvp = this.appoverCvp.userNameBooker;
        }
        this.updateBookCar.status = 1;
    }

    hideComponent() {
        this.appoverCvpByUserList = null;
        this.appoverQlttByUserList = null;
        this.appoverLddvByUserList = null;
        this.copyInfo = null;
        this.appoverCvp = null;
        this.appoverQltt = null;
        this.appoverLddv = null;
    }


}

