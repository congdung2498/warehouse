import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BookcarService} from "../../bookcar.service";
import {TokenStorage} from "../../../shared/token.storage";
import {AppComponent} from "../../../app.component";
import {ConfirmationService} from "primeng/api";
import {ListTripAll} from "../../Entity/ListTrip";
import {DateTimeUtil} from "../../../../common/datetime";
import {DriverSquadSearch} from "../../Entity/DriverSquadSearch";
import {DatePipe} from "@angular/common";
import {SearchDataBookId} from "../../Entity/ReportStationery";

@Component({
    selector: 'app-details-car-booking',
    templateUrl: './details-car-booking.component.html',
    styleUrls: ['./details-car-booking.component.css']
})
export class DetailsCarBookingComponent implements OnInit {
    vn = DateTimeUtil.vn;
    @Input() userInfo: any;
    @Input() bookCar: ListTripAll;
    @Input() routeWayList: ListTripAll[];
    @Input() seatList: ListTripAll[];
    @Input() typeList: ListTripAll[];
    @Input() squadList: ListTripAll[];

    @Output() result: EventEmitter<any> = new EventEmitter<any>();

    STATUS_LABELS = ['Chờ phê duyệt', 'Đã duyệt', 'Từ chối', ''];

    flagCvp: number;
    flagLddv: number;
    flagQltt: number;
    convertStatus = ['Chờ phê duyệt', 'Đã duyệt', 'Từ chối', 'Hủy đặt xe', 'Đã xếp xe', 'Bắt đầu đi',
        'Đã đến nơi', 'Hoàn thành', 'Quá hạn', 'Từ chối xếp xe', 'Chờ gia hạn'];

    unitName: string;
    fullName: string;
    phoneNumber: string;
    ratting: number;
    feedback: string;
    driverRating: number;
    driverFeedback: string;
    statusStrip: number;
    startDate: Date;
    endDate: Date;
    teamCarName: ListTripAll;
    journey: ListTripAll;
    carType: ListTripAll;
    seat: ListTripAll;
    searchDataById: SearchDataBookId = {
        searchDTO: '',
        searchPlaceName: ''
    };
    startPlace: string;
    tagetPlace: string;
    route: string;
    numOfPassenger: number;
    listOfPassenger: string;
    reason: string;
    qlttName: ListTripAll;
    lddvName: ListTripAll;
    cvpName: ListTripAll;
    driveSquad: DriverSquadSearch = {
        typeId: null, seatId: null, squadId: null, squadName: null, placeId: null, placeName: null, userName: null,
        userCreate: null, fullName: null, status: null, unitName: null, emplPhone: null, displayOption: null,
        licensePlate: null, pageNumber: null, pageSize: null, totalRecords: null
    };

    constructor(private _BookcarService: BookcarService, private tokenStorage: TokenStorage, private app: AppComponent,
                private confirmationService: ConfirmationService) {
    }

    ngOnInit() {
    }

    onModelChange(bookCar: ListTripAll) {
        this.bookCar = bookCar;
        this.unitName = bookCar.detailUnit;
        this.fullName = bookCar.empName;
        this.phoneNumber = bookCar.empPhone;
        this.startDate = new Date(this.bookCar.dateStart);
        this.endDate = new Date(this.bookCar.dateEnd);
        this.statusStrip = bookCar.statusTrips;
        if (this.bookCar.squadId) {
            this.squadList.forEach(el => {
                if (el.squadId === this.bookCar.squadId) {
                    this.teamCarName = el;
                }
            });
        }

        // loai hanh trinh
        if (this.bookCar.routeType) {

            this.routeWayList.forEach(el => {
                if (el.routeType === this.bookCar.routeType) {
                    this.journey = el;
                }
            });
        }
        // loai xe
        if (this.bookCar.carType) {
            this.typeList.forEach(el => {
                if (el.typeId === this.bookCar.typeId) {
                    this.carType = el;
                }
            });
            // so cho
            if (this.bookCar.seat) {
                this.seatList.forEach(el => {
                    if (el.seatId == this.bookCar.seat) {
                        this.seat = el;
                    }
                });
            }

            if (this.bookCar.appoverQltt != null && this.bookCar.appoverQltt != "") {
                this.searchDataById.searchDTO = this.bookCar.appoverQltt;
                this._BookcarService.getQlttByUserName(this.searchDataById).subscribe(res => {
                    let appoverQlttByUserList = res.data;
                    if (appoverQlttByUserList) {
                        for (let i = 0; i < appoverQlttByUserList.length; i++) {
                            if (appoverQlttByUserList[i].userNameBooker == this.bookCar.appoverQltt) {
                                this.qlttName = appoverQlttByUserList[i];
                                break;
                            }
                        }
                    }
                });
            } else {
                this.qlttName = null;
            }
            if (this.bookCar.appoverCvp != null && this.bookCar.appoverCvp != "") {
                this.searchDataById.searchDTO = this.bookCar.appoverCvp;
                this._BookcarService.getQlcvpByUserName(this.searchDataById).subscribe(res => {
                    let appoverCvpByUserList = res.data;
                    if (appoverCvpByUserList) {
                        for (let i = 0; i < appoverCvpByUserList.length; i++) {
                            if (appoverCvpByUserList[i].userNameBooker == this.bookCar.appoverCvp) {
                                this.cvpName = appoverCvpByUserList[i];
                                break;
                            }
                        }
                    }
                });
            } else {
                this.cvpName = null;
            }

            if (this.bookCar.appoverLddv != null && this.bookCar.appoverLddv != "") {
                this.searchDataById.searchDTO = this.bookCar.appoverLddv;
                this._BookcarService.getQldvByUserName(this.searchDataById).subscribe(res => {
                    let appoverLddvByUserList = res.data;
                    if (appoverLddvByUserList) {
                        for (let i = 0; i < appoverLddvByUserList.length; i++) {
                            if (appoverLddvByUserList[i].userNameBooker == this.bookCar.appoverLddv) {
                                this.lddvName = appoverLddvByUserList[i];
                                break;
                            }
                        }
                    }
                });
            } else {
                this.lddvName = null;
            }
        }

        // diem khoi hanh
        if (this.bookCar.startPlace) {
            let searchDataById = {searchDTO: null, searchPlaceName: ''}
            searchDataById.searchDTO = this.bookCar.startPlace;
            this._BookcarService.searchPlaceName(searchDataById).subscribe(res => {
                if (res) {
                    let placeNameListById = res.data;
                    for (let i = 0; i < placeNameListById.length; i++) {
                        if (placeNameListById[i].startPlace === this.bookCar.startPlace) {
                            this.startPlace = placeNameListById[i].placeName;
                            break;
                        }
                    }
                }
            });
        }

        this.tagetPlace = bookCar.tagetPlace;
        this.numOfPassenger = bookCar.numOfPassenger;
        this.listOfPassenger = bookCar.listOfPassenger;
        this.reason = bookCar.reason;
        this.route = bookCar.route;
        this.ratting = bookCar.rating;
        this.feedback = bookCar.feedback;
        this.driverRating = bookCar.driverRating;
        this.driverFeedback = bookCar.driverFeedback;
        this.flagQltt = bookCar.flagQltt;
        this.flagCvp = bookCar.flagCvp;
        this.flagLddv = bookCar.flagLddv;
    }

    close() {
        this.result.emit(1);
    }

}

