import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {DateTimeUtil} from "../../../../common/datetime";
import {BookcarService} from "../../bookcar.service";
import {DriverSquadSearch} from "../../Entity/DriverSquadSearch";
import {ListTripAll} from "../../Entity/ListTrip";
import {TokenStorage} from "../../../shared/token.storage";
import {DriveCarInfo, EntityRqFindEmp, RequestMatchCar, ResponseCars} from "../../Entity/CarDetails";
import {AutoComplete, Calendar, ConfirmationService, Dropdown, MessageService} from "primeng/primeng";
import {ValidateUtils} from "../../../../common/validate";
import {AppComponent} from "../../../app.component";
import {RequestCar} from "../../Entity/RequestCar";
import {DatePipe} from "@angular/common";


@Component({
    selector: 'booking-car-ordering',
    templateUrl: './ordering.component.html',
    styleUrls: ['./ordering.component.css'],
    providers: [TokenStorage]
})
export class OrderingComponent implements OnInit {

    @Input() userInfo: any;
    @Output() result: EventEmitter<any> = new EventEmitter<any>();
    @Output() refuse: EventEmitter<any> = new EventEmitter<any>();
    @Input() routeWayList: ListTripAll[];
    @Input() seatList: ListTripAll[];
    @Input() typeList: ListTripAll[];
    @Input() squadList: ListTripAll[];
    @Input() placeStartList: ListTripAll[];

    STATUS_LABELS = ['Chờ phê duyệt', 'Đã duyệt', 'Từ chối', ''];

    vn = DateTimeUtil.vn;
    copyInfo: any;
    //flag if  is renew popup
    isRefuse: boolean = false;

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
    statusLabel: string;

    appoverQlttByUserList: ListTripAll[];
    appoverLddvByUserList: ListTripAll[];
    appoverCvpByUserList: ListTripAll[];

    appoverQltt: ListTripAll;
    appoverCvp: ListTripAll;
    appoverLddv: ListTripAll;

    isStatusRefuseOrder: boolean;
    isStatusCancelOrder: boolean;

    driveCarNameFree: RequestMatchCar = {userName: ''};
    teamCarNameFree: ResponseCars = {carId: ''};

    driveCarNameFreeList: RequestMatchCar[];
    teamCarNameFreeList: ResponseCars[];

    dateStart:string;
    dateEnd:string;

    driveSquad: DriverSquadSearch = {
        typeId: null, seatId: null, squadId: null, squadName: null, placeId: null, placeName: null, userName: null,
        userCreate: null, fullName: null, status: null, unitName: null, emplPhone: null, displayOption: null,
        licensePlate: null, pageNumber: null, pageSize: null, totalRecords: null
    };

    requestMatchCar: RequestMatchCar = {
        bookCarId: '', userRequest: '', userAssigner: '', userName: '', carId: '',
        fullName: '', searchBy: '', role: '', pageNumber: 0, pageSize: 10000
    };

    responseCars: ResponseCars = {
        bookCarId: '', carId: '', type: '', seat: '', licensePlate: '', userName: '',
        squadId: '', userAssigner: '', pageNumber: 0, pageSize: 10000
    }

    driveCarInfo: DriveCarInfo = {
        bookCarId: '', reasonRefuse: '', userName: '', squadId: '', carId: '',
        userAssigner: '', userRequest: '', processStatus: null
    };

    constructor(private _BookcarService: BookcarService, private tokenStorage: TokenStorage, private app: AppComponent, private confirmationService: ConfirmationService,) {
    }

    ngOnInit() {
        this.teamCarName= null;
        this.journey= null;
        this.carType= null;
        this.seat= null;
        this.startPlace= null;
    }

    doCopy() {
        if (this.copyInfo) {
            console.log(this.copyInfo);
            this.unitName = this.copyInfo.detailUnit;
            this.fullName = this.copyInfo.empName;
            this.phoneNumber = this.copyInfo.empPhone;

            this.statusLabel = this.copyInfo.statusLabel;

            if (this.copyInfo.statusTrips == 1) {
                this.isStatusRefuseOrder = true;
                this.isStatusCancelOrder = false;
            }
            if (this.copyInfo.statusTrips == 4) {
                this.isStatusRefuseOrder = false;
                this.isStatusCancelOrder = true;
            }
            this.startDate = new Date(this.copyInfo.dateStart);
            this.endDate = new Date(this.copyInfo.dateEnd);
            this.dateStart = new DatePipe("en-US").transform(this.startDate, 'dd-MM-yyyy HH:mm');
            this.dateEnd = new DatePipe("en-US").transform(this.endDate, 'dd-MM-yyyy HH:mm');
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
                let searchDataById = {searchDTO: '', searchPlaceName: ''};
                searchDataById.searchDTO = this.copyInfo.startPlace;
                for (let i = 0; i < this.placeStartList.length; i++) {
                    if (this.placeStartList[i].startPlace === this.copyInfo.startPlace) {
                        this.startPlace = this.placeStartList[i];
                        break;
                    }
                }
            }

//==========================================================================================================
            this.requestMatchCar.bookCarId = this.copyInfo.carBookingId;
            this.requestMatchCar.squadId = this.copyInfo.squadId;
            this._BookcarService.searchDriveNameIsFree(this.requestMatchCar).subscribe(res => {
                    if (res) {
                        this.driveCarNameFreeList = res.data;
                    }
                }, err => {
                },() =>{
                if(this.copyInfo.driverUser){
                    this.driveCarNameFreeList.forEach( el =>{
                        if(el.userName == this.copyInfo.driverUser){
                            this.driveCarNameFree = el;
                            this.getCarFree(1);
                        }
                    })
                }
            });
        }

    }

    getCarFree(event){
        this.teamCarNameFree = null;
        this.responseCars.bookCarId = this.copyInfo.carBookingId;
        this.responseCars.userName = this.driveCarNameFree.userName;
        this._BookcarService.searchCarsIsFree(this.responseCars).subscribe(res => {
            if (res) {
                this.teamCarNameFreeList = res.data;
                if(this.copyInfo.carId && event == 1){
                    this.teamCarNameFreeList.forEach(el =>{
                        if(el.carId == this.copyInfo.carId ){
                            this.teamCarNameFree = el;
                        }
                    })
                }
            }
        });

    }

    doRefuse() {
        this.refuse.emit(true);
    }

    isOrder() {
      if(!this.driveCarNameFree || !this.driveCarNameFree.userName) {
        this.app.showWarn('Lái xe không được để trống');
        return;
      }

      if(!this.teamCarNameFree || !this.teamCarNameFree.carId) {
        this.app.showWarn('Xe không được để trống');
        return;
      }

        this.driveCarInfo.userName = this.driveCarNameFree.userName;
        this.driveCarInfo.bookCarId = this.copyInfo.carBookingId;
        this.driveCarInfo.processStatus = 4;
        this.driveCarInfo.carId = this.teamCarNameFree.carId;

        this._BookcarService.updateCarOrder(this.driveCarInfo).subscribe(res => {
            console.log(res);
            if (res.status == 1) {
                this.app.showSuccess('Đã xếp xe');
                this.result.emit(1)
            } else {
                this.app.showWarn('Xếp xe không thành công');
            }
        });
    }
}
