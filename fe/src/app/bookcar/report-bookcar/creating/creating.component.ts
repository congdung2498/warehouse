import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {DateTimeUtil} from "../../../../common/datetime";
import {BookcarService} from "../../bookcar.service";
import {DriverSquadSearch} from "../../Entity/DriverSquadSearch";
import {ListTripAll} from "../../Entity/ListTrip";
import {TokenStorage} from "../../../shared/token.storage";
import {EntityRqFindEmp} from "../../Entity/CarDetails";
import {AutoComplete, Calendar, ConfirmationService, Dropdown, InputTextarea, MessageService} from "primeng/primeng";
import {ValidateUtils} from "../../../../common/validate";
import {AppComponent} from "../../../app.component";
import {RequestCar} from "../../Entity/RequestCar";
import {CommonUtils} from "../../../../common/commonUtils";


@Component({
    selector: 'booking-car-creating',
    templateUrl: './creating.component.html',
    styleUrls: ['./creating.component.css'],
    providers: [TokenStorage]
})
export class CreatingComponent implements OnInit {

    @Input() userInfo: any;
    @Input() routeWayList: ListTripAll[];
    @Input() seatList: ListTripAll[];
    @Input() typeList: ListTripAll[];
    @Input() squadList: ListTripAll[];
    @Output() result: EventEmitter<any> = new EventEmitter<any>();

    //qlttName
    @ViewChild('createCarTeamFld') createCarTeamFld: Dropdown;
    @ViewChild('createCarCategoryFld') createCarCategoryFld: Dropdown;
    @ViewChild('createRouteWayFld') createRouteWayFld: Dropdown;
    @ViewChild('createStartTimeFld') createStartTimeFld: Calendar;
    @ViewChild('createEndTimeFld') createEndTimeFld: Calendar;
    @ViewChild('createStartPlaceFld') createStartPlaceFld: AutoComplete;
    @ViewChild("createTargetPlaceFld") createTargetPlaceFld: ElementRef;
    @ViewChild("numOfPassengerFld") numOfPassengerFld: ElementRef;
    @ViewChild('LDDVFld') LDDVFld: AutoComplete;
    @ViewChild('CVPFld') CVPFld: AutoComplete;
    @ViewChild('reasonFld') reasonFld: ElementRef;
    @ViewChild('createSeatFld') createSeatFld: Dropdown;

    convertStatus = ['Chờ phê duyệt', 'Đã duyệt', 'Từ chối', 'Hủy đặt xe', 'Đã xếp xe', 'Bắt đầu đi',
        'Đã đến nơi', 'Hoàn thành', 'Quá hạn', 'Từ chối xếp xe', 'Chờ gia hạn'];
    vn = DateTimeUtil.vn;
    copyInfo: any;
    //flag if  is renew popup
    isCancel:boolean = false;

    startDate: Date;
    endDate: Date;
    minDate: Date = new Date();

    unitName: string;
    fullName: string;
    phoneNumber: string;

    placeStartList: ListTripAll[];
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

    qlttNameList: ListTripAll[];
    lddvNameList: ListTripAll[];
    cvpNameAllList: ListTripAll[];

    qlttName: ListTripAll;
    lddvName: ListTripAll;
    cvpName: ListTripAll;

    isShowRejectReasonDialog: boolean;
    rejectReason: string;

    entityRqFindEmp: EntityRqFindEmp = {pattern: '', fromIndex: 1, getSize: 20};
    driveSquad: DriverSquadSearch = {
        typeId: null, seatId: null, squadId: null, squadName: null, placeId: null, placeName: null, userName: null,
        userCreate: null, fullName: null, status: null, unitName: null, emplPhone: null, displayOption: null,
        licensePlate: null, pageNumber: null, pageSize: null, totalRecords: null
    };

    requestCar: RequestCar = {
        carBookingId: null, squadId: null, routeWay: null, type: null, seat: null, startPlace: null, targetPlace: null, route: null,
        listPartner: null, totalPartner: null, reason: null, createUser: null, approverDir: null, approverLead: null,
        approverPre: null, dateStart: null, dateEnd: null,
    };

    constructor(private _BookcarService: BookcarService, private tokenStorage: TokenStorage, private app: AppComponent, private confirmationService: ConfirmationService,) {
    }

    ngOnInit() {
        this.setEmployeeInfo();
    }

    doCopy() {
        if (!this.copyInfo) return;
        if(this.isCancel){
            this.startDate = new Date(this.copyInfo.dateStart);
            this.endDate = new Date(this.copyInfo.dateEnd);
            this.unitName = this.copyInfo.detailUnit;
            this.fullName = this.copyInfo.empName;
            this.phoneNumber = this.copyInfo.empPhone;
        } else {
            this.startDate = null;
            this.endDate = null;
            this.setEmployeeInfo();
        }

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
        // diem khoi hanh
        if (this.copyInfo.startPlace) {
            let searchDataById = {searchDTO: '', searchPlaceName: ''}
            searchDataById.searchDTO = this.copyInfo.startPlace;
            this._BookcarService.searchPlaceName(searchDataById).subscribe(res => {
                if (res) {
                    let placeNameListById = res.data;
                    for (let i = 0; i < placeNameListById.length; i++) {
                        if (placeNameListById[i].startPlace === this.copyInfo.startPlace) {
                            this.startPlace = placeNameListById[i];
                            this.startPlace.placeId = placeNameListById[i]['startPlace'];
                            break;
                        }
                    }
                }
            });
        }


        if (this.copyInfo.appoverQltt != null && this.copyInfo.appoverQltt != "") {
            let searchDataById = {searchDTO: '', searchPlaceName: ''}
            searchDataById.searchDTO = this.copyInfo.appoverQltt;
            this._BookcarService.getQlttByUserName(searchDataById).subscribe(res => {
                let appoverQlttByUserList = res.data;
                if (appoverQlttByUserList) {
                    for (let i = 0; i < appoverQlttByUserList.length; i++) {
                        if (appoverQlttByUserList[i].userNameBooker == this.copyInfo.appoverQltt) {
                            this.qlttName = appoverQlttByUserList[i];
                            this.qlttName.userName = appoverQlttByUserList[i].userNameBooker;
                            break;
                        }
                    }
                }
            });
        } else {
            this.qlttName = null;
        }
        if (this.copyInfo.appoverCvp != null && this.copyInfo.appoverCvp != "") {
            let searchDataById = {searchDTO: '', searchPlaceName: ''}
            searchDataById.searchDTO = this.copyInfo.appoverCvp;
            this._BookcarService.getQlcvpByUserName(searchDataById).subscribe(res => {
                let appoverCvpByUserList = res.data;
                if (appoverCvpByUserList) {
                    for (let i = 0; i < appoverCvpByUserList.length; i++) {
                        if (appoverCvpByUserList[i].userNameBooker == this.copyInfo.appoverCvp) {
                            this.cvpName = appoverCvpByUserList[i];
                            this.cvpName.userName = appoverCvpByUserList[i].userNameBooker;
                            break;
                        }
                    }
                }
            });
        } else {
            this.cvpName = null;
        }

        if (this.copyInfo.appoverLddv != null && this.copyInfo.appoverLddv != "") {
            let searchDataById = {searchDTO: '', searchPlaceName: ''}
            searchDataById.searchDTO = this.copyInfo.appoverLddv;
            this._BookcarService.getQldvByUserName(searchDataById).subscribe(res => {
                let appoverLddvByUserList = res.data;
                if (appoverLddvByUserList) {
                    for (let i = 0; i < appoverLddvByUserList.length; i++) {
                        if (appoverLddvByUserList[i].userNameBooker == this.copyInfo.appoverLddv) {
                            this.lddvName = appoverLddvByUserList[i];
                            this.lddvName.userName = appoverLddvByUserList[i].userNameBooker;
                            break;
                        }
                    }
                }
            });
        } else {
            this.lddvName = null;
        }
    }

    onSelectManager() {
        if(!this.lddvName || !this.lddvName.userName) return;
        if(this.lddvName.userName === this.qlttName.userName) {
            this.app.showWarn("Quản lý trực tiếp không được trùng với quản lý đơn vị");
            this.qlttName = null;
            return;
        }
    }

    onSelectManagerLDDV() {
        if(!this.qlttName || !this.qlttName.userName) return;
        if(this.lddvName.userName === this.qlttName.userName) {
            this.app.showWarn("Quản lý trực tiếp không được trùng với quản lý đơn vị");
            this.lddvName = null;
            return;
        }
    }

    getPlaceStart(ev) {
        this._BookcarService.getListPlaceStart(ev.query).subscribe(res => {
            this.placeStartList = res.data;
        });
    }

    getManager(ev) {
        this.entityRqFindEmp.pattern = ev.query;
        this.entityRqFindEmp.role = ['PMQT_QL'];
        this._BookcarService.getListManagerDirect(this.entityRqFindEmp).subscribe(res => {
            this.qlttNameList = res.data;
        });
    }

    getManagerUnit(ev) {
        let searchLeader: EntityRqFindEmp = new EntityRqFindEmp();
        searchLeader.pattern = ev.query;
        searchLeader.role = ['PMQT_QL', 'PMQT_CVP'];
        this._BookcarService.getListManagerDirect(searchLeader).subscribe(res => {
            this.lddvNameList = res.data;
        });
    }

    getManagerChief(ev) {
        let searchLeader: EntityRqFindEmp = new EntityRqFindEmp();
        searchLeader.pattern = ev.query;
        searchLeader.role = ['PMQT_CVP'];
        this._BookcarService.getListManagerDirect(searchLeader).subscribe(res => {
            this.cvpNameAllList = res.data;
        });
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
        if (!this.teamCarName) {
            ValidateUtils.validate(this.createCarTeamFld, 'Đội xe không được để trống', this.app);
            return false;
        } else if (!this.journey) {
            ValidateUtils.validate(this.createRouteWayFld, 'Loại hành trình không được để trống', this.app);
            return false;
        } else if (!this.carType) {
            ValidateUtils.validate(this.createCarCategoryFld, 'Loại xe không được để trống', this.app);
            return false;
        } else if (!this.seat || !this.seat.seatId) {
            ValidateUtils.validate(this.createSeatFld, 'Số chỗ không được để trống', this.app);
            return false;
        } else if (!this.startPlace || !this.startPlace.placeId) {
            ValidateUtils.validateAutoComplete(this.createStartPlaceFld, 'Điểm khởi hành không được để trống', this.app);
            return false;
        } else if (!this.reason) {
            ValidateUtils.validateTextInput(this.reasonFld, 'Lý do không được để trống', this.app);
            return false;
        } else if (!this.tagetPlace || this.tagetPlace.trim().length === 0) {
            ValidateUtils.validateTextInput(this.createTargetPlaceFld, 'Điểm đến không được để trống', this.app);
            return false;
        } else if (!this.numOfPassenger) {
            ValidateUtils.validateTextInput(this.numOfPassengerFld, 'Số người đi không được để trống', this.app);
            return false;
        }
        else if (!this.lddvName || !this.lddvName.userName || this.lddvName.userName.length === 0) {
            ValidateUtils.validateAutoComplete(this.LDDVFld, 'Lãnh đạo đơn vị không được để trống', this.app);
            return false;
        }
        return true;
    }

    setEmployeeInfo() {
        this.unitName = this.userInfo.unitName;
        this.fullName = this.userInfo.fullName;
        this.phoneNumber = this.userInfo.phoneNumber;
    }

    save() {
        if (!this.validate()) return;
        this.settingParamsRegisterDetails();
        this.confirmationService.confirm({
            message: 'Đ/c chắc chắn muốn thêm không?',
            header: 'Đăng ký chuyến xe',
            icon: 'pi pi-exclamation-triangle',
            acceptLabel: 'Đồng ý',
            rejectLabel: 'Hủy bỏ',
            accept: () => {
                this.addNewHandle(this.requestCar);
            }
        });
    }

    addNewHandle(entityBookCar: any) {
        this._BookcarService.insertRequesrCars(entityBookCar).subscribe(res => {
            if (res.status == 1) {
                this.result.emit(res.status);
                this.app.showSuccess('Đã đăng ký yêu cầu đặt xe');
            } else if (res.status == 5) {
                this.app.showWarn('Đăng ký thất bại- Trùng thời gian đăng ký trước đó');
            } else {
                this.result.emit(res.status);
                this.app.showWarn("Gửi thất bại");
            }
            this.setEmployeeInfo();
        });
    }

    reject() {
        let cancelRequest ={bookCarId:''}
        cancelRequest.bookCarId = this.copyInfo.carBookingId;
        if(this.requestCar.createUser === this.userInfo.userName.toString()) {
            this.confirmationService.confirm({
                message: 'Đ/c chắc chắn muốn hủy yêu cầu không?',
                header: 'Hủy yêu cầu đặt xe',
                icon: 'pi pi-exclamation-triangle',
                acceptLabel: 'Đồng ý',
                rejectLabel: 'Hủy bỏ',
                accept: () => {
                    this._BookcarService.cancelRequest(cancelRequest).subscribe(res => {
                        this.app.showSuccess('Đã hủy yêu cầu sử dụng xe');
                        this.result.emit(res.status);
                        this.setEmployeeInfo();
                    });
                }
            });
        } else {
            this.isShowRejectReasonDialog = true;
        }
    }

    onReject() {
        let cancelRequest ={bookCarId: this.copyInfo.carBookingId, reason: this.rejectReason};
        this._BookcarService.cancelRequest(cancelRequest).subscribe(res => {
            this.app.showSuccess('Đã hủy yêu cầu sử dụng xe');
            this.result.emit(res.status);
            this.isShowRejectReasonDialog = false;
            this.setEmployeeInfo();
        });
    }

    onCloseRejectDialog() {
        this.rejectReason = null;
        this.isShowRejectReasonDialog = false;
        this.setEmployeeInfo();
    }

    settingParamsRegisterDetails() {
        this.requestCar.dateEnd = this.endDate;
        this.requestCar.dateStart = this.startDate;
        this.requestCar.routeWay = this.route;
        this.requestCar.route = this.journey.routeType;
        this.requestCar.reason = this.reason;
        this.requestCar.seat = this.seat.seatId;
        this.requestCar.type = this.carType.typeId;
        this.requestCar.squadId = this.teamCarName.driveSquadId ? this.teamCarName.driveSquadId : this.teamCarName.squadId
        this.requestCar.targetPlace = this.tagetPlace;
        this.requestCar.startPlace = this.startPlace.startPlace ? this.startPlace.startPlace : this.startPlace.placeId;
        this.requestCar.totalPartner = this.numOfPassenger;
        this.requestCar.listPartner = this.listOfPassenger;
        this.requestCar.driverSquadId = this.requestCar.squadId;
        if (this.qlttName != null && this.qlttName.userName != '') {
            this.requestCar.approverDir = this.qlttName.userName;
        } else {
            this.requestCar.approverDir = null;
        }
        if (this.lddvName != null && this.lddvName.userName != '') {
            this.requestCar.approverLead = this.lddvName.userName;
        } else {
            this.requestCar.approverLead = null;
        }
        if (this.cvpName != null && this.cvpName.userName != '') {
            this.requestCar.approverPre = this.cvpName.userName;
        } else {
            this.requestCar.approverPre = null;
        }

        console.log(this.requestCar);
    }

    onResetPopup() {
        this.isCancel = false;
        this.startDate = null;
        this.endDate = null;
        this.route = null;
        this.numOfPassenger = null;
        this.listOfPassenger = null;
        this.reason = null;
        this.tagetPlace = null
        this.teamCarName = null;
        this.teamCarName = null;
        this.carType = null;
        this.seat = null;
        this.startPlace = null;
        this.tagetPlace = null;
        this.route = null;
        this.numOfPassenger = null;
        this.listOfPassenger = null;
        this.reason = null;
        this.qlttName = null;
        this.lddvName = null;
        this.cvpName = null
        this.journey = null;
        this.setEmployeeInfo();
    }
}
