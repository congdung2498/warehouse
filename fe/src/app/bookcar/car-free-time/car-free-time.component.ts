import {Component, OnInit, ViewChild} from '@angular/core';
import {Table} from 'primeng/table';
import {FormBuilder} from '@angular/forms';
import {BookcarService} from '../bookcar.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {AppComponent} from '../../app.component';
import {CarFreeTimes} from '../Entity/CarFreeTimes';
import {DriverSquadSearch} from '../Entity/DriverSquadSearch';
import {UserInfo} from '../../shared/UserInfo';
import {TokenStorage} from '../../shared/token.storage';
import {saveAs} from 'file-saver';
import {DriverStatus} from "../Entity/Status";
import {SelectItem} from "primeng/api";


@Component({
    selector: 'app-car-free-time',
    templateUrl: './car-free-time.component.html',
    styleUrls: ['./car-free-time.component.css']
})
export class CarFreeTimeComponent implements OnInit {
    onlyChar: RegExp = /^[0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;
    notChar: RegExp = /[^0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;

    @ViewChild('dt') dataTableComponent: Table;

    driverStatusShow: string;
    carStatusShow: string;
    resultList: any = [];
    getSquadList: any = [];
    getDriveList: any = [];
    statusDrive: SelectItem[];
    statusCar: SelectItem[];
    total_record: number;
    carStatusList = DriverStatus.CAR_FREE_STATUS;
    driveStatusList: SelectItem[] = DriverStatus.DRIVER_FREE_STATUS;
    driverStatusLabels: string[] = DriverStatus.DRIVER_STATUS_LABELS;
    carStatusLabels: string[] = DriverStatus.CAR_STATUS_LABELS;
    driverSquadName: any;
    driver: any;
    typeList: DriverSquadSearch[];
    licensePlateList: DriverSquadSearch[];
    carType: DriverSquadSearch;
    seat: DriverSquadSearch;
    seatList: DriverSquadSearch[];
    licensePlate: DriverSquadSearch;
    userInfo: UserInfo;
    pageNumber = 0;
    pageSize = 15;
    carFreeTimes: CarFreeTimes;
    squadList: DriverSquadSearch[];
    driveSquad: DriverSquadSearch;

    isManager: boolean;
    isCarLeader: boolean;
    isDisbaleLeaderCar: boolean;


    constructor(private formBuilder: FormBuilder,
                private bookcarService: BookcarService,
                private confirmationService: ConfirmationService,
                private messageService: MessageService,
                private tokenStorage: TokenStorage,
                private app: AppComponent) {
    }

    ngOnInit() {
        this.userInfo = this.tokenStorage.getUserInfo();
        this.carFreeTimes = new CarFreeTimes();
        this.driveSquad = new DriverSquadSearch();

        this.checkRole();
        let component = this;
        let callback = () : void => {
            if(component.isManager) {
                component.bookcarService.findAllSquad(component.driveSquad).subscribe(res => {
                    component.squadList = res.data;
                });
            }

            component.bookcarService.findAllTypeCars().subscribe(res => {
                component.typeList = res.data;
            });

            this.bookcarService.findAllSeatCars().subscribe(res => {
                component.seatList = res.data;
            });
        }
        component.tokenStorage.getSecurityAccount(callback);
    }

    checkRole() {
        if (this.userInfo.role.includes('PMQT_ADMIN') || this.userInfo.role.includes('PMQT_CVP') || this.userInfo.role.includes('PMQT_QL')) {
            this.isManager = true;
        }
        if (this.userInfo.role.includes('PMQT_QL_Doi_xe')) {
            this.isCarLeader = true;
            this.isDisbaleLeaderCar = true;
        }
    }

    loadDriveSquad(event) {
        const object = { 'searchDTO': event.query.trim() ? event.query.trim().replace(this.notChar, '') : '' };
        this.bookcarService.searchDriveSquadName(object).subscribe(item => {
            this.squadList = item['data'];
        });
    }
    loadLicensePlate(event) {
        const object = { 'searchDTO': event.query.trim() ? event.query.trim().replace(this.notChar, '') : '' };
        this.bookcarService.searchLicensePlate(object).subscribe(item => {
            this.licensePlateList = item['data'];
        });
    }

    selectlicensePlate(item) {
        this.carFreeTimes.licensePlate = item;
    }

    getlicensePlate(ev) {
        const object = { 'search': ev.query.trim() ? ev.query.trim().replace(this.notChar, '') : '' };
        this.bookcarService.searchLicensePlate(object).subscribe(res => {
            this.licensePlateList = res.data;
        });
    }

// lai xe
    getDrive(ev) {
        const query =  ev.query === '' ? '' : ev.query ;
        this.bookcarService.searchDriveName(query).subscribe(res => {
            this.getDriveList = res.data;
        });
    }

    settingParams() {
        this.carFreeTimes.freeTime = true;
        if (this.seat) {
            this.carFreeTimes.seat = this.seat.seatId;
        } else {
            this.carFreeTimes.seat = null;
        }
        if (this.carType) {
            this.carFreeTimes.carType = this.carType.typeId;
        } else {
            this.carFreeTimes.carType = null;
        }
        if (this.driverSquadName && this.driverSquadName.squadId) {
            this.carFreeTimes.driverSquadId = this.driverSquadName.squadId;
            this.carFreeTimes.driverSquadName = null;
        } else {
            this.carFreeTimes.driverSquadName = this.driverSquadName === '' ? null : this.driverSquadName;
            this.carFreeTimes.driverSquadId = null;
        }
        if (this.driver && this.driver.userName !== undefined && this.driver.userName !== null) {
            this.carFreeTimes.driverUserName = this.driver.userName;
            this.carFreeTimes.driverFullName = null;
        } else {
            this.carFreeTimes.driverUserName = null;
            this.carFreeTimes.driverFullName = this.driver === '' ? null : this.driver ;
        }
        if (this.statusCar) {
            this.carFreeTimes.carStatus = [];
            for(let i = 0; i < this.statusCar.length; i++) {
                if(this.statusCar[i].value === '0') {
                    this.carFreeTimes.carStatus.push('0');
                    this.carFreeTimes.carStatus.push('7');
                } else {
                    this.carFreeTimes.carStatus.push(this.statusCar[i].value);
                }
            }
        }

        if (this.licensePlate) {
            if(this.licensePlate.licensePlate) this.carFreeTimes.licensePlate = this.licensePlate.licensePlate;
            else this.carFreeTimes.licensePlate = this.licensePlate.toString();
        } else {
            this.carFreeTimes.licensePlate = null;
        }

        if(this.statusDrive) {
            this.carFreeTimes.driverStatus = [];
            for(let i = 0; i < this.statusDrive.length; i++) {
                if(this.statusDrive[i].value === '0') {
                    this.carFreeTimes.driverStatus.push('0');
                    this.carFreeTimes.driverStatus.push('7');
                } else {
                    this.carFreeTimes.driverStatus.push(this.statusDrive[i].value);
                }
            }
        }
    }

    processSearch() {
        this.settingParams();
        this.dataTableComponent.reset();
    }

    driverSelect(e){}

    public onLazyLoad(event) {
        this.carFreeTimes.pageNumber = event.first;
        this.carFreeTimes.pageSize = event.rows;
        if(this.isCarLeader) {
            let condition: DriverSquadSearch = new DriverSquadSearch();
            condition.userName =  this.userInfo.userName.toString();
            this.bookcarService.findAllSquad(condition).subscribe(res => {
                if(res.data) {
                    this.squadList = res.data;
                    if(this.squadList && this.squadList.length > 0) {
                        this.driverSquadName = this.squadList[0];
                        this.carFreeTimes.driverSquadId = this.driverSquadName.squadId;
                        this.bookcarService.getCarFreeTimes(this.carFreeTimes).subscribe(res => {
                            if(res){
                                this.pageNumber = event.first;
                                this.pageSize = res.data.listFreeCar.length;
                                this.resultList = res.data.listFreeCar;
                                this.total_record = res.data.total;
                            }
                        });
                    }
                }
            });
        } else {
            this.bookcarService.getCarFreeTimes(this.carFreeTimes).subscribe(res => {
                if(res){
                    this.pageNumber = event.first;
                    this.pageSize = res.data.listFreeCar.length;
                    this.resultList = res.data.listFreeCar;
                    this.total_record = res.data.total;
                }
            });
        }
    }

    exportToExcel() {
        this.settingParams();
        const fileName = 'TKXR_' + this.userInfo.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
          + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
          + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
        this.bookcarService.exportExcelFreeCar(this.carFreeTimes).subscribe(
          (next: ArrayBuffer) => {
              const file: Blob = new Blob([next], { type: 'application/xlsx' });
              saveAs(file, fileName);
          });
    }
}
