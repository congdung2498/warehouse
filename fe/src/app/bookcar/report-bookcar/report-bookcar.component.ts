import {ConfirmationService, SelectItem} from "primeng/api";
/**
 *
 * --- ThangBT ---
 *
 */
import {Component, OnInit, OnDestroy, ViewChild, ElementRef} from '@angular/core';
import {BookcarService} from '../bookcar.service';
import {MessageService} from 'primeng/components/common/messageservice';
import {ListCarBooking, ListTrip, ListTripAll, Ratting} from '../Entity/ListTrip';
import {Condition, ConditionById} from '../Entity/ConditionSearch';
import {UserInfo} from '../../shared/UserInfo';
import {saveAs} from 'file-saver';
import {TokenStorage} from '../../shared/token.storage';
import {Table} from 'primeng/table';
import {Title} from '@angular/platform-browser';
import {Router, NavigationEnd} from '@angular/router';
import {
    CancelRequest, CarDetails, DriveCarInfo, EntityBookCar, EntityRqFindEmp, Journey, RequestMatchCar, ResponseCarRoute,
    ResponseCars,
    SearchData,
    UpdateBookCar, UpdateProcessCarRoute
} from '../Entity/CarDetails';
import {DatePipe} from '@angular/common';
import {AppComponent} from '../../app.component';
import {DriverSquadSearch} from '../Entity/DriverSquadSearch';
import {DateTimeUtil} from '../../../common/datetime';
import {ManagementResult} from "../Entity/ManagementResult";
import {Place} from "../../setting/Entity/ListPlace";
import {RequestCar} from "../Entity/RequestCar";
import {CommonUtils} from "../../../common/commonUtils";
import {AutoComplete, Calendar, Dropdown} from "primeng/primeng";
import {CreatingComponent} from "./creating/creating.component";
import {RenewedComponent} from "./renewed/renewed.component";
import {RefuseComponent} from "./refuse/refuse.component";
import {OrderingComponent} from "./ordering/ordering.component";
import {DetailsCarBookingComponent} from "./details-car-booking/details-car-booking.component";

@Component({
    selector: 'app-report-bookcar',
    templateUrl: './report-bookcar.component.html',
    styleUrls: ['./report-bookcar.component.css'],
    providers: [TokenStorage]
})

export class ReportBookcarComponent implements OnInit, OnDestroy {

    @ViewChild('myTable') private myTable: Table;
    @ViewChild('createStartTimeFld') createStartTimeFld: Calendar;
    @ViewChild('createEndTimeFld') createEndTimeFld: Calendar;
    @ViewChild('creatingCpn') creatingCpn: CreatingComponent;
    @ViewChild('renewedCpn') renewedCpn: RenewedComponent;
    @ViewChild('refuseCpn') refuseCpn: RefuseComponent;
    @ViewChild('orderingCpn') orderingCpn: OrderingComponent;
    @ViewChild('detailComponent') detailComponent: DetailsCarBookingComponent;

    carStatusList = CommonUtils.CAR_BOOKING_STATUS;
    placeStartList: ListTripAll[];
    rattingDrive: Ratting = {note: '', userName: '', bookCarId: '', updateDate: '', numberOfStar: null};
    driveCarInfo: DriveCarInfo = {
        bookCarId: '', reasonRefuse: '', userName: '', squadId: '', carId: '',
        userAssigner: '', userRequest: '', processStatus: null
    };
    updateBookCar: UpdateBookCar = {
        status: null, bookCarId: '', listId: [''], reasonRefuse: '', userName: '', qltt: '',
        flagQltt: null, lddv: '', flagLddv: null, cvp: '', role: '', userRequest: ''
    };
    exchangeList: ResponseCarRoute;
    responseCarRoute: ResponseCarRoute = {
        bookCarId: '', userName: '', fullName: '', fullNameRequester: '', emplPhone: '', carId: '', type: '',
        seat: '', route: '', licensePlate: '', dateStart: null, dateEnd: null, totalPartner: null, startPlace: '',
        targetPlace: '', routeWay: '', status: null, listStatus: [null], processByRole: '', pageNumber: null,
        pageSize: null, detailUnit: '', phoneNumber: '', fullNameUser: '', squadName: ''
    };
    updateProcessCarRoute: UpdateProcessCarRoute = {
        userName: '', userDriver: '', bookCarId: '', status: null, timeReadyStart: null,
        timeAtTarget: null, timeReadyFinish: null, carId: ''
    }

    fullNameUnit: String;
    unitId: String;
    phoneNumber: String;
    emDriveName: string;
    empCartype: string;
    empDateStart: string;
    empSeat: string;
    empDateEnd: string;
    empListenPlate: string;
    emRouteType: string;
    emRouteWay: string;
    empSquadName: string;
    empStartPlace: string;
    empUse: string;
    empTagetPlace: string;
    empPhone: string;
    empUnit: string;
    empLisPassenger: number;
    driveNameComplete: string;
    driveVoteUser: string;
    squadNameVoteUser: string;
    feedbackUser: string;
    rattingUser: number;
    approve: string;
    cancel: string;
    copy: string;
    rejected: string;
    squadNameComplete: string;
    jouney: string;
    car: string;
    userVote: string;
    adminVote: string;
    reasonRefuse: string;
    isShowDescription: boolean;
    isDialogDriverVote: boolean;
    isDialogNew: boolean;
    isDialogDetails : boolean;
    isEdit: boolean;
    dateStart: string;
    dateEnd: string;
    startReadyTime: string;
    endReadyTime: string;
    searchDataById: SearchData = {
        squadId: '',
        searchDTO: ''
    };
    //TODO:

    listCarBooking: ListCarBooking = {
        appoverQltt: '', appoverLddv: '', appoverCvp: '', listCar: [], isAQltt: false, isAQldv: false, isACvp: false
    };

    check = 0;

    isShowDescriptionRefuse: boolean;
    fullName: String;
    navigationSubscription;
    condition: Condition;
    conditionById: ConditionById = {
        carBookingId: '', driveSquadId: '', unitIdManager: '', squadId: '', driveSquadName: '', userNameDriver: '',
        driverInfo: '', carType: '', userNameBooker: '', empInfo: '', selectedCarStatus: [], startDate: null,
        endDate: null, dateStart: null, dateEnd: null, numOfRows: null, startRow: null, rowSize: null
    };

    averageRating: string;
    isClick: boolean;
    uf: UserInfo;

    startRow: number;
    rowSize: number;
    checkedAll: boolean;
    isChecked: boolean;
    fromDate: Date;
    toDate: Date;

    listActivity: string[];
    count = 0;
    isDialogStillApprove: boolean;
    isDialogComplete: boolean;
    tagetPlace: string;
    isDialogRefuse: boolean;
    isDialogOrderBy: boolean;
    feedback: string;
    reason: string;
    detailUnit: string;
    empName: string;


    placeNameList: ListTripAll[];
    typeList: ListTripAll[];
    seatList: ListTripAll[];
    squadList: ListTripAll[];
    routeWayList: ListTripAll[];
    squadListById: ListTripAll[];
    placeNameListById: ListTripAll[];

    teamCarName: ListTripAll;
    routeType: ManagementResult;
    startPlaceDriveVote: string;
    targetPlace: string;
    carType: ListTripAll;
    startPlace: ListTripAll;
    numOfPassenger: number;
    listOfPassenger: string;
    timeReadyStart: Date;
    timeStart: string;
    timeEnd: string;
    timeReadyEnd: Date;
    reasonUse: string;
    qlttName: ListTripAll;
    lddvName: ListTripAll;
    cvpName: ListTripAll;
    route: string;
    driverName: string;
    reasonAssigner: string;
    ratting: number = 5;
    startDate: Date;
    endDate: Date;
    seat: ListTripAll;
    carBookingId: string;
    appoverCvp: ListTripAll;
    appoverLddv: ListTripAll;
    appoverQltt: ListTripAll;

    flagCvp: number;
    flagLddv: number;
    flagQltt: number;
    IsAQltt: boolean;
    IsAQldv: boolean;
    IsACvp: boolean;
    selectedCarBooking: ListTripAll;
    journey: ListTripAll;
    vn = DateTimeUtil.vn;
    selectedTrip: ListTripAll;
    listSearchTrip: ListTripAll[];
    listSearchTripById: ListTripAll[];
    listTripRefuse: ListTripAll;
    cars: any[];
    filterTeamCar: any[];
    filterEmployee: any[];
    filterPhoneNumber: any[];
    filterDriver: any[];
    selectedCar: any;
    selectedCarStatus: any[];
    teamCarInfor: any;
    empInfor: any;
    empPhoneNum: any;
    driverInfor: any;
    totalRecord: number;

    isEmpty: boolean;
    loading: boolean;
    loading2: boolean;
    convertStatus: string[];
    isNotEmployee: boolean;
    isLeaderCar: boolean;
    disableTeamCar: boolean;
    isManager: boolean;
    isAdmin: boolean;
    isDrive: boolean;
    isDialogJourney: boolean;
    isStart: boolean;
    isEnd: boolean;
    isQltt: boolean;
    isQdv: boolean;
    isCvp: boolean;
    userInfo: UserInfo;
    bookCarDetail: ListTripAll

    isEmployee: boolean;
    isDisableCarTeam: boolean;

    selectedListTrip: any[] = [];
    statusLabel: string;
    // tslint:disable-next-line:max-line-length
    onlyChar: RegExp = /^[0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;
    onlyNum: RegExp = /^[0-9]+$/;

    constructor(private _BookcarService: BookcarService, private messageService: MessageService, private confirmationService: ConfirmationService,
                private tokenStorage: TokenStorage, private title: Title, private router: Router, private tokenService: TokenStorage,
                private app: AppComponent) {

        this.navigationSubscription = this.router.events.subscribe(event => {
            if (!(event instanceof NavigationEnd)) {
                return;
            }
            // Do what you need to do here, for instance :
            this.resetButton();
            if (this.isClick) {
                this.ngOnInit();
            }
        });

        let userInfo = this.tokenService.getUserInfo();
        this.userInfo = userInfo;
        this.unitId = userInfo.unitName;
        this.fullNameUnit = userInfo.fullName;
        this.phoneNumber = userInfo.phoneNumber;
        this.approve = 'Gia hạn';
        this.rejected = 'Từ chối';
        this.copy = 'Sao chép';
        this.car = 'Sắp xếp xe';
        this.userVote = 'Người yêu cầu đánh giá';
        this.adminVote = 'Lái xe đánh giá';
        this.jouney = 'Hành trình xe';
        this.cancel = 'Hủy';
    }

    ngOnInit() {
        this.rattingUser = 5;
        this.ratting = 5;
        this.isClick = false;
        this.loading2 = false;
        this.fromDate = new Date();
        this.toDate = new Date(this.fromDate.getTime() + 30 * 24 * 60 * 60 * 1000);
        this.loading = true;
        this.startRow = -1;
        this.rowSize = 1;
        this.totalRecord = 0;
        this.title.setTitle('Thống kê đặt xe - PMQTVP');
        this.getCarType();
        this.setStatus();
        this.checkRole();
        this.listActivity = [];
        this.appoverLddv = new ListTripAll();
        this.appoverCvp = new ListTripAll();
        this.appoverQltt = new ListTripAll();
        this.uf = this.tokenStorage.getUserInfo();
        if (this.uf.role.includes('PMQT_QL')) {
            this.appoverQltt.appoverQltt = this.uf.userName;
        }
        if (this.uf.role.includes('PMQT_QL') && this.uf.role.includes('PMQT_CVP')) {
            this.appoverLddv.appoverLddv = this.uf.userName;
        }
        if (this.uf.role.includes('PMQT_CVP')) {
            this.appoverCvp.appoverCvp = this.uf.userName;
        }

        for (let i = 0; i < this.uf.role.length; i++) {
            if (this.uf.role[i] == 'PMQT_QL') {
                this.check = 1;
                this.IsAQltt = true;
                break;
            } else if (this.uf.role[i] == 'PMQT_CVP' && this.uf.role[i] == 'PMQT_QL') {
                this.check = 2;
                this.IsAQldv = true;
                break;
            } else if (this.uf.role[i] == 'PMQT_CVP') {
                this.check = 3;
                this.IsACvp = true;
                break;
            } else if (this.uf.role[i] == 'PMQT_ADMIN') {
                this.check = 4;
                this.IsAQldv = true;
                this.IsAQltt = true;
                this.IsACvp = true;
                break;
            } else {
                this.check = 5;
                this.IsAQldv = false;
                this.IsAQltt = false;
                this.IsACvp = false;
                break;
            }
        }

        if (!this.isManager) {
            if (this.isLeaderCar) {
                const object = {
                    'userName': this.uf.userName
                };
                this._BookcarService.getDriveSquad(object).subscribe(item => {
                    this.filterTeamCar = item['data'];
                    this.teamCarInfor = this.filterTeamCar[0];
                    this.searchData();
                });
            } else {
                this.searchData();
            }
        } else {
            this.searchData();
        }
        this.squadListById = []


        this._BookcarService.getCarBookingModel().subscribe(res => {
            if(res.status === 1) {
                this.squadList = res.data['squads'];
                this.typeList = res.data['carTypes'];
                this.seatList = res.data['seats'];
                this.routeWayList = res.data['journeyTypes'];
                this.placeNameList = res.data['places'];
            }
        });
    }

    //  when click 'x' of phone number
    clearPhoneNum(event) {
        if (this.empPhoneNum != null) {
            event.stopPropagation();
            this.empPhoneNum = null;
            this.loading2 = false;
        }
    }

    checkRole() {
        this.uf = this.tokenStorage.getUserInfo();
        if (this.uf.role.includes('PMQT_ADMIN')) {
            this.isAdmin = true;
        } else if (this.uf.role.includes('PMQT_CVP') || this.uf.role.includes('PMQT_QL')) {
            this.isManager = true;
        } else if (this.uf.role.includes('PMQT_QL_Doi_xe')) {
            this.isLeaderCar = true;
            this.isDisableCarTeam = true;
        } else if (this.uf.role.includes('PMQT_Doi_xe')) {
            this.isDrive = true;
            this.isDisableCarTeam = true;
            this.driverInfor = {result: this.uf.fullName.toString(), userNameDriver: this.uf.userName.toString()};
        } else if (this.uf.role.includes('PMQT_NV')) {
            this.isEmployee = true;
            this.empInfor = {result: this.uf.fullName.toString(), userNameBooker: this.uf.userName.toString()};
        }
    }

    //  set up status
    setStatus() {
        this.convertStatus = ['Chờ phê duyệt', 'Đã duyệt', 'Từ chối', 'Hủy đặt xe', 'Đã xếp xe', 'Bắt đầu đi',
            'Đã đến nơi', 'Hoàn thành', 'Quá hạn', 'Từ chối xếp xe', 'Chờ gia hạn', 'Đã ghép xe'];
    }

    //  get car type
    getCarType() {
        const object = {
            'masterClass': 'S001'
        };
        this._BookcarService.getCarsType(object).subscribe(item => {
            this.cars = item['data'];
        });
    }

    //  when select fromDate field
    selectFromDate() {
        if (this.toDate != null && this.toDate < this.fromDate) {
            this.toDate = this.fromDate;
        }
    }

    //  when select toDate field
    selectToDate() {
        if (this.fromDate != null && this.toDate != null && this.fromDate > this.toDate) {
            this.fromDate = this.toDate;
        }
    }

    //  when change data in team car
    onChange() {
        this.driverInfor = null;
    }

    //  when click icon 'x' of from date
    clearFromDate(event) {
        if (this.fromDate != null) {
            event.stopPropagation();
            this.fromDate = null;
        }
    }

    //  when click icon 'x' of to date
    clearToDate(event) {
        if (this.toDate != null) {
            event.stopPropagation();
            this.toDate = null;
        }
    }

    //  when click 'x' of drive squad
    clearTeamCar(event) {
        if (this.teamCarInfor != null) {
            event.stopPropagation();
            this.teamCarInfor = null;
            this.loading2 = false;
        }
    }

    //  when click 'x' of driver
    clearDriver(event) {
        if (this.driverInfor != null) {
            event.stopPropagation();
            this.driverInfor = null;
            this.loading2 = false;
        }
    }

    //  when click 'x' of booker
    clearBooker(event) {
        if (this.empInfor != null) {
            event.stopPropagation();
            this.empInfor = null;
            this.loading2 = false;
        }
    }

    //  show suggestions when type in team car field
    loadTeamCar(event) {
        const object = {
            'squadName': event.query
        };
        this.loading2 = false;
        this._BookcarService.getDriveSquad(object).subscribe(item => {
            this.filterTeamCar = item['data'];
            if (!this.disableTeamCar) {
                this.loading2 = true;
            }
            this.filterTeamCar.forEach(element => {
            });
        });
    }

    //  show suggestions when type in booker field
    loadEmployee(event) {
        let object = null;
        if (this.isManager) {
            object = {
                'result': event.query,
                'unitIdManager': this.isManager ? this.uf.unitId as string : null
            };
        } else {
            object = {
                'result': event.query
            };
        }
        this.loading2 = false;
        this._BookcarService.getEmployees(object).subscribe(item => {
            this.filterEmployee = item['data'];
            this.loading2 = true;
        });
    }

    //  show suggestions when type in phone number field
    loadPhoneNumber(event) {
        let object = null;
        if (this.isManager) {
            object = {
                'result': event.query,
                'unitIdManager': this.isManager ? this.uf.unitId as string : null
            };
        } else {
            object = {
                'result': event.query
            };
        }
        this.loading2 = false;
        this._BookcarService.getEmployeesPhone(object).subscribe(item => {
            this.filterPhoneNumber = item['data'];
            this.loading2 = true;
        });
    }

    //  show suggestions when type in driver field, driver follow team car
    loadDriver(event) {
        let object = null;
        if (this.teamCarInfor != null && this.teamCarInfor.length > 0) {
            object = {
                'result': event.query,
                'empPhone': this.teamCarInfor
            };
        } else if (this.teamCarInfor != null && this.teamCarInfor['squadName'] !== undefined) {
            object = {
                'result': event.query,
                'squadId': this.teamCarInfor['squadId']
            };
        } else {
            object = {
                'result': event.query
            };
        }
        this.loading2 = false;
        this._BookcarService.getListDriver(object).subscribe(item => {
            this.filterDriver = item['data'];
            this.loading2 = true;
        });
    }

    //  handle condtions to search
    getConditionTrip() {
        let teamcarid = null;
        let teamcarname = null;
        let usernamebooker = null;
        let empinfo = null;
        let selectedCarStatus = [];
        let usernamedriver = null;
        let driverinfo = null;
        selectedCarStatus = this.selectedCarStatus;
        //  handle drive squad
        if (this.teamCarInfor != null && this.teamCarInfor.length > 0) {
            teamcarname = this.teamCarInfor;
        } else if (this.teamCarInfor != null && this.teamCarInfor['squadId'] !== undefined) {
            teamcarid = this.teamCarInfor['squadId'];
        }
        //  handle car-booker
        if (this.empInfor != null && this.empInfor.length > 0) {
            empinfo = this.empInfor;
        } else if (this.empInfor != null && this.empInfor['userNameBooker'] !== undefined) {
            usernamebooker = this.empInfor['userNameBooker'];
        }
        /*else if (this.selectedCarStatus != null)*/

        //  handle driver
        if (this.driverInfor != null && this.driverInfor.length > 0) {
            driverinfo = this.driverInfor;
        } else if (this.driverInfor != null && this.driverInfor['result'] !== undefined) {
            usernamedriver = this.driverInfor['userNameDriver'];
        }

        if (this.fromDate) this.fromDate.setHours(0, 0, 0, 0);
        if (this.toDate) this.toDate.setHours(23, 59, 59, 59);

        this.condition = null;
        this.condition = {
            unitIdManager: this.isManager ? this.uf.unitId as string : null,
            squadId: teamcarid,
            squadName: teamcarname,
            userNameBooker: usernamebooker,
            userNameDriver: usernamedriver,
            driverInfo: driverinfo,
            carType: this.selectedCar != null ? this.selectedCar['codeValue'] : null,
            empInfo: empinfo,
            selectedCarStatus: selectedCarStatus,
            startDate: this.fromDate,
            endDate: this.toDate,
            dateStart: this.fromDate,
            dateEnd: this.toDate,
            numOfRows: -1,
            startRow: -1,
            rowSize: -1
        };
    }

    //  get list search trip from database
    getListSearchTrip() {
        this.checkedAll = false;
        if (this.myTable !== undefined) {
            this.myTable.reset();
        }
        this.loading = true;
        this._BookcarService.getAllListTrip(this.condition).subscribe(item => {
            if (item['data'] != null) {
                this.listSearchTrip = item['data'];
                for (let i = 0; i < this.listSearchTrip.length; i++) {
                    let listCar = this.listSearchTrip[i];
                    this.setEnableBookCar(listCar);
                    this.setStatusLabelBookCar(listCar);
                }
            }
            this.startRow = 0;
            if (this.listSearchTrip != null && this.listSearchTrip.length >= 10) {
                this.rowSize = 10;
            } else {
                this.rowSize = this.listSearchTrip.length;
            }
            if (this.listSearchTrip == null || this.listSearchTrip.length === 0) {
                this.isEmpty = true;
            } else {
                this.isEmpty = false;
            }
            this.isClick = true;
            this.loading = false;
        });


    }

    //  when click new page
    paginate(event) {
        this.loading = true;
        setTimeout(() => {
            this.startRow = event.first;
            this.condition.startRow = event.first;
            this.condition.rowSize = 10;
            this._BookcarService.getAllListTrip(this.condition).subscribe(item => {
                this.listSearchTrip = item['data'];
                this.rowSize = this.listSearchTrip.length;
                this.loading = false;
                for (let i = 0; i < this.listSearchTrip.length; i++) {
                    let listCar = this.listSearchTrip[i];
                    this.setEnableBookCar(listCar);
                    this.setStatusLabelBookCar(listCar);
                }
            });
        }, 500);
    }

    //  when click search button
    searchData() {
        this.checkedAll = false;
        this.getConditionTrip();
        this.condition.startRow = 0;
        this.condition.rowSize = 10;
        this._BookcarService.countTotalRecord(this.condition).subscribe(item => {
            this.totalRecord = item['data'].numOfRows;
            if (item['data'].averageRating != null) {
                this.averageRating = item['data'].averageRating;
            } else {
                this.averageRating = null;
            }
            this.getListSearchTrip();
        });
    }

    //  when click export file button
    exportToExcel() {
        const fileName = 'TKDX_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
          + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
          + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
        this.getConditionTrip();
        this._BookcarService.countTotalRecord(this.condition).subscribe(item => {
            const totalrecord = item['data'].numOfRows;
            if (totalrecord <= 50000) {
                this.condition.numOfRows = totalrecord;
                this._BookcarService.getExcel(this.condition).subscribe(
                  (next: ArrayBuffer) => {
                      const file: Blob = new Blob([next], {type: 'application/xlsx'});
                      saveAs(file, fileName);
                  });
            } else {
                this.messageService.add({
                    severity: 'warn',
                    summary: 'Không thể xuất file',
                    detail: 'Số bản ghi tìm thấy vượt quá 50000'
                });
            }
        });
    }

    //  when click reset button
    resetButton() {
        if(!this.isLeaderCar) this.teamCarInfor = null;
        this.selectedCar = null;
        if(!this.isEmployee) this.empInfor = null;
        this.empPhoneNum = null;
        if(!this.isDrive) this.driverInfor = null;
        this.selectedCarStatus = null;
        this.toDate = new Date();
        this.fromDate = new Date();

        this.checkedAll = false;
        this.getConditionTrip();
        this.condition.startRow = 0;
        this.condition.rowSize = 10;
        this._BookcarService.countTotalRecord(this.condition).subscribe(item => {
            this.totalRecord = item['data'].numOfRows;
            if (item['data'].averageRating != null) {
                this.averageRating = item['data'].averageRating;
            } else {
                this.averageRating = null;
            }
            this.getListSearchTrip();
        });
    }

    ngOnDestroy() {
        // avoid memory leaks here by cleaning up after ourselves. If we
        // don't then we will continue to run our initialiseInvites()
        // method on every navigationEnd event.
        if (this.navigationSubscription) {
            this.navigationSubscription.unsubscribe();
        }
    }

    addNew() {
        this.isDialogNew = true;
    }

    checkApprove(checking: ListTripAll) {
        if (checking.statusTrips === 0) {
            return true;
        }
        return false;
    }

    //TODO: rename to onShowDetail
    doDetails(event) {
        this.selectedTrip = event;
        this.ratting = event.ratting;
        this.flagCvp = event.flagCvp;
        this.flagLddv = event.flagLddv;
        this.flagQltt = event.flagQltt;
        this.detailUnit = event.detailUnit;
        this.empName = event.empName;
        this.empPhone = event.empPhone;
        this.startDate = new Date(event.startDate);
        this.endDate = new Date(event.endDate);
        this.teamCarName = event.squadName;
        this.routeType = event.routeType;
        // this.carType = event.carType;
        this.startPlace = event.startPlace;
        this.tagetPlace = event.tagetPlace;
        this.route = event.route;
        this.numOfPassenger = event.numOfPassenger;
        this.listOfPassenger = event.listOfPassenger;
        if (event.statusTrips == 0) {
            this.isDialogStillApprove = true;
            this.isDialogComplete = false;
            this.isDialogRefuse = false;
            this.isDialogOrderBy = false;
        } else if (event.statusTrips == 7) {
            this.isDialogStillApprove = false;
            this.isDialogOrderBy = false;
            this.isDialogComplete = true;
            this.isDialogRefuse = false;
            this.ratting = 5;
        } else if (event.statusTrips == 2 || event.statusTrips == 3) {
            this.isDialogStillApprove = false;
            this.isDialogComplete = false;
            this.isDialogOrderBy = false;
            this.isDialogRefuse = true;
        } else if (event.statusTrips == 4) {
            this.isDialogStillApprove = false;
            this.isDialogComplete = false;
            this.isDialogOrderBy = true;
            this.isDialogRefuse = false;
        }
    }

    doCancel(event) {
        this.creatingCpn.copyInfo = event;
        this.creatingCpn.isCancel = true;
        this.creatingCpn.doCopy();
        this.isDialogNew = true;
    }

    doOrder(event) {
        this.orderingCpn.copyInfo = event;
        this.orderingCpn.doCopy();
        this.isDialogOrderBy = true;
    }

    doRenewed(event) {
        this.renewedCpn.copyInfo = event;
        this.isDialogStillApprove = true;
        this.renewedCpn.doCopy();
    }

    doComplete(event) {
        this.selectedTrip = event;
        this.flagCvp = event.flagCvp;
        this.flagLddv = event.flagLddv;
        this.flagQltt = event.flagQltt;
        this.detailUnit = event.detailUnit;
        this.empName = event.empName;
        this.empPhone = event.empPhone;
        this.startDate = new Date(event.dateStart);
        this.dateStart = new DatePipe("en-US").transform(this.startDate, 'HH:mm:ss dd-MM-yyyy');
        this.endDate = new Date(event.dateEnd);
        this.dateEnd = new DatePipe("en-US").transform(this.endDate, 'HH:mm:ss dd-MM-yyyy');
        this.routeType = event.routeType;
        this.startPlace = event.startPlace;
        if (event.startPlace) {
            this.searchDataById.searchDTO = event.startPlace;
            this._BookcarService.searchPlaceName(this.searchDataById).subscribe(res => {
                if (res) {
                    this.placeNameListById = res.data;
                    this.startPlaceDriveVote = this.placeNameListById[0].placeName;
                }
            });
        } else {
            this.startPlaceDriveVote = '';
        }
        this.driveNameComplete = event.driverName;
        this.squadNameComplete = event.squadName;
        this.tagetPlace = event.tagetPlace;
        this.route = event.route;
        this.numOfPassenger = event.numOfPassenger;
        this.listOfPassenger = event.listOfPassenger;
        this.reasonUse = event.reason;
        this.timeReadyStart = new Date(event.timeReadyStart);
        this.startReadyTime = new DatePipe("en-US").transform(this.timeReadyStart, 'HH:mm:ss dd-MM-yyyy');
        this.timeReadyEnd = new Date(event.timeReadyEnd);
        this.endReadyTime = new DatePipe("en-US").transform(this.timeReadyEnd, 'HH:mm:ss dd-MM-yyyy');
        this.isDialogComplete = true;
        this.ratting = 5;
    }

// đánh giá người sử dụng xe
    settingParamsComplete() {
        this.selectedTrip.updateUser = this.uf.userName;
        this.selectedTrip.rating = this.ratting;
        this.selectedTrip.feedback = this.feedback;
    }

    onHideDialogUserVote() {
        this.ratting = 5;
        this.feedback = null;
        this.isDialogComplete = false;
    }

    rattingFromUser() {
        this.settingParamsComplete();
        this.confirmationService.confirm({
            message: 'Đ/c có muốn đánh giá chuyến xe này không?',
            header: 'Đánh giá chuyến xe',
            icon: 'pi pi-exclamation-triangle',
            acceptLabel: 'Đồng ý',
            rejectLabel: 'Hủy bỏ',
            accept: () => {
                this._BookcarService.updateCompleteCarDetails(this.selectedTrip).subscribe(res => {
                    if (res.status == 1) {
                        this.app.showSuccess('Đánh giá thành công');
                        this.searchData();
                        this.onHideDialogUserVote()
                    } else {
                      this.app.showWarn('Đánh giá thất bại');
                    }
                });
            }
        });
    }

    startJourney() {
        this.updateProcessCarRoute.bookCarId = this.exchangeList.bookCarId;
        this.updateProcessCarRoute.status = 5;
        this.updateProcessCarRoute.carId = this.exchangeList.carId;
        this.updateProcessCarRoute.userDriver = this.exchangeList.userName;
        this.updateProcessCarRoute.timeReadyStart = new Date()
        this._BookcarService.updateCarRoute(this.updateProcessCarRoute).subscribe(res => {
            if (res.status == 1) {
                this.app.showSuccess('Đã bắt đầu hành trình');
                this.searchData();
                this.onHideDialogJourney();
            } else {
              this.app.showWarn('Cập nhật xe bắt đầu đi thất bại');
            }
        });
    }

    endJourney() {
        this.isDialogJourney = false;
        this.updateProcessCarRoute.bookCarId = this.exchangeList.bookCarId;
        this.updateProcessCarRoute.status = 7;
        this.updateProcessCarRoute.carId = this.exchangeList.carId;
        this.updateProcessCarRoute.userDriver = this.exchangeList.userName;
        this.updateProcessCarRoute.timeReadyFinish = new Date();
        this._BookcarService.updateCarRoute(this.updateProcessCarRoute).subscribe(res => {
            if (res.status == 1) {
                this.app.showSuccess('Xe đã về vị trí');
                this.onHideDialogJourney();
                this.searchData();
                this.isDialogDriverVote = true;
                this.rattingUser = 5;
                this.driveVoteUser = this.selectedCarBooking.driverName;
                this.startPlaceDriveVote = this.selectedCarBooking.placeName;
                this.squadNameVoteUser = this.selectedCarBooking.squadName;
                this.tagetPlace = this.selectedCarBooking.tagetPlace;
                this.empName = this.selectedCarBooking.empName;
                this.dateStart = new DatePipe("en-US").transform(this.selectedCarBooking.dateStart, 'HH:mm dd-MM-yyyy');
                this.dateEnd = new DatePipe("en-US").transform(this.selectedCarBooking.dateEnd, 'HH:mm dd-MM-yyyy');
                this.empPhone = this.selectedCarBooking.empPhone;
                this.timeStart = new DatePipe("en-US").transform(this.selectedCarBooking.timeReadyStart, 'HH:mm dd-MM-yyyy');
                this.timeEnd = new DatePipe("en-US").transform(this.updateProcessCarRoute.timeReadyFinish, 'HH:mm dd-MM-yyyy');
                this.detailUnit = this.selectedCarBooking.detailUnit;

            } else {
                this.app.showWarn("Cập nhật xe về vị trí thất bại")
            }
        });
    }

    doCopy(event) {
        this.creatingCpn.copyInfo = event;
        this.creatingCpn.doCopy();
        this.isDialogNew = true;
    }

    doExchange(event) {
      this.selectedCarBooking = event;
        this.statusLabel = event.statusLabel;
        this.responseCarRoute.bookCarId = event.carBookingId;
        this._BookcarService.findCarRoute(this.responseCarRoute).subscribe(res => {
            if (res) {
                this.exchangeList = res.data;
                this.emDriveName = this.exchangeList.fullName;
                this.empCartype = this.exchangeList.type;
                this.empDateStart = new DatePipe("en-US").transform(this.exchangeList.dateStart, 'dd-MM-yyyy HH:mm');
                this.empSeat = this.exchangeList.seat;
                this.empDateEnd = new DatePipe("en-US").transform(this.exchangeList.dateEnd, 'dd-MM-yyyy HH:mm');
                this.empListenPlate = this.exchangeList.licensePlate;
                this.emRouteType = this.exchangeList.route;
                this.emRouteWay = this.exchangeList.routeWay;
                this.empSquadName = this.exchangeList.squadName;
                this.empStartPlace = this.exchangeList.startPlace;
                this.empUse = this.exchangeList.fullNameUser;
                this.empTagetPlace = this.exchangeList.targetPlace;
                this.empPhone = this.exchangeList.phoneNumber;
                this.empUnit = this.exchangeList.detailUnit;
                this.empLisPassenger = this.exchangeList.totalPartner;

                if (this.exchangeList.status === 4) {
                    this.isStart = true;
                } else if (this.exchangeList.status === 5) {
                  this.isEnd = true;
                }
                this.isDialogJourney = true;
            }
        });
    }

    onHideOderPopup() {
        this.orderingCpn.driveCarNameFree = null;
        this.orderingCpn.teamCarNameFree = null;
        this.isDialogOrderBy = false;
        this.isStart = false;
        this.isEnd = false;
    }

    onHideDialogJourney() {
      this.isStart = false;
      this.isEnd = false;
      this.isDialogJourney = false;
      this.exchangeList = null;
    }

    doRefuse(event) {
        this.listTripRefuse = event;
        for (let i = 0; i < this.uf.role.length; i++) {
            if (this.uf.role[i] == 'PMQT_QL') {
                this.check = 1;
                this.IsAQltt = true;
                break;
            } else if (this.uf.role[i] == 'PMQT_HC_DV') {
                this.check = 2;
                this.IsAQldv = true;
                break;
            } else if (this.uf.role[i] == 'PMQT_CVP') {
                this.check = 3;
                this.IsACvp = true;
                break;
            } else if (this.uf.role[i] == 'PMQT_ADMIN') {
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
        else if (this.check == 1 && event.flagQltt == 0) {
            this.isQltt = false;
            this.isQdv = true;
            this.isCvp = true;
        } else if (this.check == 1 && event.flagQltt != 0) {
            this.isQltt = true;
            this.isQdv = true;
            this.isCvp = true;
        } else if (this.check == 2 && event.flagLddv == 0) {
            this.isQltt = true;
            this.isQdv = false;
            this.isCvp = true;
        } else if (this.check == 2 && event.flagLddv != 0) {
            this.isQltt = true;
            this.isQdv = true;
            this.isCvp = true;
        } else if (this.check == 3 && event.flagCvp == 0) {
            this.isQltt = true;
            this.isQdv = true;
            this.isCvp = false;
        } else if (this.check == 3 && event.flagCvp != 0) {
            this.isQltt = true;
            this.isQdv = true;
            this.isCvp = true;
        } else {
            this.isQltt = true;
            this.isQdv = true;
            this.isCvp = true;
        }
        this.isDialogRefuse = true;
        this.refuseCpn.copyInfo = event;
        this.refuseCpn.doCopy();

    }

    onHideCarRefuse() {
        if(this.refuseCpn) this.refuseCpn.hideComponent();
    }

    cancelRefuse() {
        this.isShowDescription = false;
        this.isShowDescriptionRefuse = false;
    }

    onHideRefuseDialog() {
        this.reasonRefuse = null;
        this.reasonAssigner = null;
    }

    chooseRefuse() {
        var listBookId = [];
        listBookId.push(this.listTripRefuse.carBookingId);
        this.updateBookCar.listId = listBookId;
        this.updateBookCar.status = 2;
        if (this.appoverQltt) {
            this.updateBookCar.qltt = this.appoverQltt.userNameBooker;
        } else {
            this.updateBookCar.qltt = '';
        }
        if (this.appoverLddv) {
            this.updateBookCar.lddv = this.appoverLddv.userNameBooker;
        } else {
            this.updateBookCar.lddv = '';
        }
        if (this.appoverCvp) {
            this.updateBookCar.cvp = this.appoverCvp.userNameBooker;
        } else {
            this.updateBookCar.cvp = '';
        }
        this.updateBookCar.reasonRefuse = this.reasonRefuse;
        this._BookcarService.updateCarRefuse(this.updateBookCar)
          .subscribe(res => {
                if (res.status == 1) {
                    this.app.showSuccess('Đã từ chối duyệt yêu cầu sử dụng xe');
                    this.searchData();
                }
                else {
                    this.app.showWarn('Từ chối duyệt yêu cầu thất bại');
                }
            }, er => {
            },
            () => {
                this.isShowDescription = false;
                this.isDialogRefuse = false;
            });
    }

    cancelOrdering() {
      if(!this.reasonAssigner || this.reasonAssigner.length === 0) {
        this.app.showWarn('Lý do không được để trống');
        return;
      }

      if (this.orderingCpn.driveCarNameFree && this.orderingCpn.driveCarNameFree.userName != '') {
          this.driveCarInfo.userName = this.orderingCpn.driveCarNameFree.userName;
      } else {
          this.driveCarInfo.userName = '';
      }
      this.driveCarInfo.bookCarId = this.orderingCpn.copyInfo.carBookingId;
      this.driveCarInfo.processStatus = 9;
      this.driveCarInfo.reasonRefuse = this.reasonAssigner;
      if (this.orderingCpn.teamCarNameFree && this.orderingCpn.teamCarNameFree.carId != '' && this.orderingCpn.teamCarNameFree.carId != null) {
          this.driveCarInfo.carId = this.orderingCpn.teamCarNameFree.carId;
      } else {
          this.driveCarInfo.carId = '';
      }
      this._BookcarService.updateCarRefuseOrder(this.driveCarInfo)
        .subscribe(res => {
            if (res.status == 1) {
                this.app.showSuccess('Đã hủy xếp xe');
                this.searchData();
            }
            else {
                this.app.showWarn('Hủy xếp xe thất bại');
            }
        });
      this.isShowDescriptionRefuse = false;
      this.isDialogOrderBy = false;
    }

    doDriverVote(event) {
        this.rattingUser = 5;
        this.selectedTrip = event;
        this.feedbackUser = event.feedBack;
        this.flagCvp = event.flagCvp;
        this.flagLddv = event.flagLddv;
        this.flagQltt = event.flagQltt;
        this.detailUnit = event.detailUnit;
        this.empName = event.empName;
        this.empPhone = event.empPhone;
        this.startDate = new Date(event.dateStart);
        this.dateStart = new DatePipe("en-US").transform(this.startDate, 'HH:mm:ss dd-MM-yyyy');
        this.endDate = new Date(event.dateEnd);
        this.dateEnd = new DatePipe("en-US").transform(this.endDate, 'HH:mm:ss dd-MM-yyyy')
        this.routeType = event.routeType;
        if (event.startPlace) {
            this.searchDataById.searchDTO = event.startPlace;
            this._BookcarService.searchPlaceName(this.searchDataById).subscribe(res => {
                if (res) {
                    this.placeNameListById = res.data;
                    this.startPlaceDriveVote = this.placeNameListById[0].placeName;
                }
            });

        } else {
            this.startPlaceDriveVote = '';
        }
        this.tagetPlace = event.tagetPlace;
        this.route = event.route;
        this.numOfPassenger = event.numOfPassenger;
        this.listOfPassenger = event.listOfPassenger;
        this.reasonUse = event.reason;
        this.timeReadyStart = new Date(event.timeReadyStart);
        this.timeStart = new DatePipe("en-US").transform(this.timeReadyStart, 'HH:mm:ss dd-MM-yyyy');
        this.timeReadyEnd = new Date(event.timeReadyEnd);
        this.timeEnd = new DatePipe("en-US").transform(this.timeReadyEnd, 'HH:mm:ss dd-MM-yyyy ')
        this.isDialogDriverVote = true;
        this.driveVoteUser = event.driverName;
        this.squadNameVoteUser = event.squadName;
    }

    //phe duyet yeu cau
    approveList() {
        if (!this.selectedListTrip || this.selectedListTrip.length === 0) {
            this.app.showWarn('Không có đăng ký cần phê duyệt')
        } else if (this.selectedListTrip.length === 1) {
            this.conditionById.carBookingId = this.selectedListTrip[0].carBookingId;
            this._BookcarService.findAllListTripById(this.conditionById).subscribe(item => {
                this.listSearchTripById = item['data'];
                this.listTripRefuse = this.listSearchTripById[0];
                this.setEnableBookCar(this.listTripRefuse);
                this.setStatusLabelBookCar(this.listTripRefuse);
                this.doRefuse(this.listTripRefuse);
                this.selectedListTrip = null;
            });
        } else {
            this.confirmationService.confirm({
                message: 'Đ/c đang phê duyệt nhiều đăng ký,  Đ/c chắc chắn muốn phê duyệt không?',
                header: 'Nhắc nhở phê duyệt',
                icon: 'pi pi-exclamation-triangle',
                acceptLabel: 'Đồng ý',
                rejectLabel: 'Hủy bỏ',
                accept: () => {
                    let listIdCar: any[] = []
                    this.selectedListTrip.forEach(el => {
                        listIdCar.push(el.carBookingId);
                    });
                    this.listCarBooking.listCar = listIdCar;
                    this.listCarBooking.isACvp = this.IsACvp;
                    this.listCarBooking.isAQldv = this.IsAQldv;
                    this.listCarBooking.isAQltt = this.IsAQltt;
                    this._BookcarService.updateListCarApprove(this.listCarBooking).subscribe(res => {
                        if (res.status == 1) {
                            this.app.showSuccess('Phê duyệt thành công');
                            this.searchData();
                        } else {
                            this.app.showWarn('Phê duyệt thất bại');
                        }
                    });
                    this.selectedListTrip = null;
                    this.searchData();
                }
            });
        }
    }

    chooseRefuseOrder() {
      if(!this.reasonAssigner || this.reasonAssigner.length === 0) {
        this.app.showWarn('Lý do không được để trống');
        return;
      }
        if (this.orderingCpn.driveCarNameFree && this.orderingCpn.driveCarNameFree.userName != '') {
            this.driveCarInfo.userName = this.orderingCpn.driveCarNameFree.userName;
        } else {
            this.driveCarInfo.userName = '';
        }
        this.driveCarInfo.bookCarId = this.orderingCpn.copyInfo.carBookingId;
        this.driveCarInfo.processStatus = 9;
        this.driveCarInfo.reasonRefuse = this.reasonAssigner;
        if (this.orderingCpn.teamCarNameFree && this.orderingCpn.teamCarNameFree.carId != '' && this.orderingCpn.teamCarNameFree.carId != null) {
            this.driveCarInfo.carId = this.orderingCpn.teamCarNameFree.carId;
        } else {
            this.driveCarInfo.carId = '';
        }
        this._BookcarService.updateCarRefuseOrder(this.driveCarInfo)
          .subscribe(res => {
              if (res.status == 1) {
                  this.app.showSuccess('Đã từ chối xếp xe');
                  this.searchData();
              }
              else {
                  this.app.showWarn('Từ chối xếp xe thất bại');
              }
          });
        this.isShowDescriptionRefuse = false;
        this.isDialogOrderBy = false;
    }

    settingParamsDrive() {
        this.rattingDrive.numberOfStar = this.rattingUser;
        this.rattingDrive.note = this.feedbackUser;
        if(this.selectedTrip)this.rattingDrive.bookCarId = this.selectedTrip.carBookingId;
        else this.rattingDrive.bookCarId = this.selectedCarBooking.carBookingId;
    }

    onHideDialogDriverVote() {
        this.rattingUser = 0;
        this.feedbackUser = null;
        this.isDialogDriverVote = false;
        this.selectedCarBooking = null;
    }

    rattingFromDrive() {
        this.settingParamsDrive();
        this.confirmationService.confirm({
            message: 'Đ/c có muốn đánh giá chuyến xe này không?',
            header: 'Đánh giá chuyến xe',
            icon: 'pi pi-exclamation-triangle',
            acceptLabel: 'Đồng ý',
            rejectLabel: 'Hủy bỏ',
            accept: () => {
                this._BookcarService.updateRattingUser(this.rattingDrive).subscribe(res => {
                    if (res.status == 1) {
                        this.app.showSuccess('Đánh giá thành công');
                        this.onHideDialogDriverVote()
                        this.searchData();
                    } else {
                      this.app.showSuccess('Đánh giá thất bại');
                    }
                });
            }
        });
    }

    onCloseDialog() {
        this.isDialogRefuse = false;
        this.isDialogStillApprove = false;
        this.isDialogNew = false;
        this.isDialogOrderBy = false;
        this.creatingCpn.onResetPopup();
    }

    onCloseDetailsDialog(){
        this.isDialogDetails = false;
    }

    onSaveResult(event) {
        if (event == 1) {
            this.onCloseDialog();
            this.searchData()
        }
    }

    onCloseResult(event){
        if(event == 1){
            this.onCloseDetailsDialog();
        }
    }

    openRefuseReason(event) {
        this.isShowDescription = event;
    }

    openRefuseOrderReason(event){
        this.isShowDescriptionRefuse = event;
    }

    onShowDetail(bookCar: ListTripAll) {
        this.isDialogDetails = true;
        this.bookCarDetail = bookCar;
        this.detailComponent.onModelChange(bookCar);
    }

    setEnableBookCar(bookCar: ListTripAll) {
      let isAdmin: boolean;
      let isLeaderCar: boolean;
      let isManager: boolean;
      let isDrive: boolean;
      let isEmployee: boolean;

      if (this.uf.role.includes('PMQT_ADMIN')) {
          isAdmin = true;
      }
      if (this.uf.role.includes('PMQT_QL_Doi_xe')) {
          isLeaderCar = true;
      }
      if (this.uf.role.includes('PMQT_CVP') || this.uf.role.includes('PMQT_QL')) {
          isManager = true;
      }
      if (this.uf.role.includes('PMQT_Doi_xe')) {
          isDrive = true;
      }
      if (this.uf.role.includes('PMQT_NV')) {
          isEmployee = true;
      }
        //set cancel, add more time, copy, user vote
        if (isAdmin || bookCar.createUser === this.userInfo.userName.toString()) {
            bookCar.isCopy = true;
            if (bookCar.statusTrips === 1 || bookCar.statusTrips === 4) {
                bookCar.isCancel = true;
                bookCar.isAddMoreTime = true;
            }

            if (bookCar.statusTrips === 0 || bookCar.statusTrips === 10) {
                let startTime: Date = new Date(bookCar.dateStart);
                if((!bookCar.appoverCvp && !bookCar.appoverQltt) || startTime.getTime() < new Date().getTime()) bookCar.isAddMoreTime = true;
                bookCar.isCancel = true;
            }

            if (bookCar.statusTrips === 7 && bookCar.rating === 0) {
                bookCar.isUserVote = true;
            }
        }

        if (isLeaderCar) {
            if (bookCar.statusTrips === 4) {
                bookCar.isCancel = true;
            }
        }

        // set reject
        if (this.isAdmin || this.isManager) {
            if (bookCar.statusTrips === 0 || bookCar.statusTrips === 10) {
              let dateStart: Date = new Date(bookCar.dateStart);
              if(isManager) {
                if(bookCar.appoverQltt === this.userInfo.userName.toString() &&  (!bookCar.flagQltt || bookCar.flagQltt === 0)) {
                  if(dateStart.getTime() > new Date().getTime()) bookCar.isApprove = true;
                  bookCar.isReject = true;
                } else if((!bookCar.appoverQltt || bookCar.flagQltt === 1) && (bookCar.appoverLddv === this.userInfo.userName.toString() &&  (!bookCar.flagLddv || bookCar.flagLddv === 0))) {
                  if(dateStart.getTime() > new Date().getTime()) bookCar.isApprove = true;
                  bookCar.isReject = true;
                } else if(bookCar.flagLddv === 1 && (bookCar.appoverCvp === this.userInfo.userName.toString() &&  (!bookCar.flagCvp || bookCar.flagCvp === 0))) {
                  if(dateStart.getTime() > new Date().getTime()) bookCar.isApprove = true;
                  bookCar.isReject = true;
                }
              } else {
                bookCar.isReject = true;
                if(dateStart.getTime() > new Date().getTime()) bookCar.isApprove = true;
              }
            }
        }

        // set setupable, driver vote, isJourney
        if (isAdmin || isLeaderCar) {
            if (bookCar.statusTrips === 1 || bookCar.statusTrips === 4) {
                bookCar.isSetup = true;
            }
        }

        if (isAdmin || isLeaderCar || isDrive) {
            if (bookCar.statusTrips === 5 || bookCar.statusTrips === 4) {
                bookCar.isJourney = true;
            }

            if (bookCar.statusTrips === 7  && (bookCar.driverRating === 0 || !bookCar.driverFeedback) ) {
                bookCar.isDriverVote = true;
            }
        }

        if (this.uf.userName === bookCar.createUser) {
            bookCar.isShowEdit = true;
        }
    }

    setStatusLabelBookCar(bookCar: ListTripAll) {
        let status: number = bookCar.statusTrips;
        if(status === 2 ||  status === 3 || status === 7 || status === 9 || status === 6 || status === 8) {
            bookCar.statusLabel = this.convertStatus[status];
        } else if (status === 11) {
            let licensePlate: string = bookCar.licensePlate;
            bookCar.statusLabel = this.convertStatus[status] + "_" + licensePlate;
        } else {
            let startTime: Date = new Date(bookCar.dateStart);
            if(startTime.getTime() < new Date().getTime()) {
                bookCar.isLate = true;
                bookCar.statusLabel = this.convertStatus[status] + '(Đã quá hạn)';
            } else {
                bookCar.statusLabel = this.convertStatus[status];
            }
        }
        //set flag if null
        if(bookCar.appoverQltt && bookCar.appoverQltt.length > 0) {
          if(!bookCar.flagQltt || bookCar.flagQltt === 0) bookCar.flagQltt = 0;
        }  else {
          bookCar.flagQltt = 3;
        }

        if(bookCar.appoverLddv && bookCar.appoverLddv.length > 0) {
          if(!bookCar.flagLddv || bookCar.flagLddv === 0) bookCar.flagLddv = 0;
        }  else {
          bookCar.flagLddv = 3;
        }

        if(bookCar.appoverCvp && bookCar.appoverCvp.length > 0) {
          if(!bookCar.flagCvp || bookCar.flagCvp === 0) bookCar.flagCvp = 0;
        }  else {
          bookCar.flagCvp = 3;
        }
    }
}
