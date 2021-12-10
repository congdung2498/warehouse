import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import {BookcarService} from '../bookcar.service';
import {Drive} from '../Entity/Drive';
import {DriveSquad} from '../Entity/DriveSquad';
import {Employee} from '../../Entity/Employee';
import {Car} from '../Entity/Cars';
import {MatchingDC} from '../Entity/MatchingDC';
import {ConfirmationService, MessageService} from 'primeng/components/common/api';
import {Table} from 'primeng/table';
import {Router, NavigationEnd} from '@angular/router';
import {Title} from '@angular/platform-browser';
import {Constants} from '../../shared/containts';
import {AutoComplete} from 'primeng/primeng';
import {AppComponent} from '../../app.component';


@Component({
    selector: 'app-category-driver',
    templateUrl: './category-driver.component.html',
    styleUrls: ['./category-driver.component.css']
})


export class CategoryDriverComponent implements OnInit {
    @ViewChild('inputSquad') private inputSquad: AutoComplete;
    @ViewChild('inputDriver') private inputDriver: AutoComplete;
    @ViewChild('inputListSelectedLicensePlate') private inputListSelectedLicensePlate: AutoComplete;
    @ViewChild('myTable')
    myTable: Table;
    @ViewChild('inputGroup') private inputGroup: ElementRef;
    listCar: Car[];
    ListDrive: Drive[];  // danh muc lai xe
    ListDriver: Employee[];   // danh sach tai xe
    driver: any;  // tai xe
    squad: any;

    drive: Drive;    // danh muc lai xe
    listteamcar: DriveSquad[]; // danh sach doi xe
    list: any;

    tempcar: any;  // doi xe

    //count: number;
    teamCar: DriveSquad;
    status: number;
    processStatus: number;
    message: string;
    edit: number;
    phoneNumber: string;
    filteredCountriesSingleSquad: DriveSquad[];   // list P-Complete Squad
    filteredCountriesSingleCar: any[];   // list P-Complete Car
    filteredCountriesSingleDriver: Employee[]; // list P-Complete Drive
    countUp: number[] = [];
    temp: string;
    listtemp: string[];
    listMatching: any[];
    listSuggest: any[];
    count: number;
    carDrive: MatchingDC;  // doi tuong gan xe
    listCarDrive: MatchingDC[] = [];  // danh muc gan xe
    carInstead: Car;
    msgs: any;
    checkInsert: number;
    pageNumber = 0;

    pageSize = 10;

    total_record: number;

    from: number;

    to: number;

    listCarMatched: any[];

    isEdit: boolean;

    isGroupBy: number;

    listMatching1: Car[];

    squadMatching: DriveSquad;

    driverMatching: Employee;

    navigationSubscription;

    phoneNumberDetail: any;

    filterLicensePlate: any[];
    selectedLicensePlate: any[];
    listReceiverRemove: any[];
    listSelectedLicensePlate: any[];
    cols = [
        {field: '', header: 'STT', width: '5%'},
        {field: '', header: 'Thao Tác', width: '15%'},
        {field: 'teamName', header: 'Tên Đội Xe', width: '20%'},
        {field: 'full_name', header: 'Tên Lái Xe', width: '20%'},
        {field: 'full_name', header: 'Số Điện Thoại', width: '20%'},
        {field: 'selectedLicensePlate', header: 'Xe Đã Gán ', width: '20%'},
        {field: 'status', header: 'Trạng Thái', width: '20%'}
    ];


    display = false;


    constructor(private bookcarService: BookcarService, private confirmationService: ConfirmationService, private title: Title, private messageService: MessageService, private router: Router, private app: AppComponent) {
        this.drive = new Drive();
        this.navigationSubscription = this.router.events.subscribe(event => {
            if (!(event instanceof NavigationEnd)) {
                return;
            }
            // Do what you need to do here, for instance :
            this.status = 1;
            this.handleSearch();
            //this.getListDrive();
        });
        this.getTeamCarvt030003();
        this.getDrivers();
        // this.getListDrive();
        this.getListCar();
        this.handleSearch();
    }


    ngOnInit() {
        this.carInstead = new Car('', '', '', '', '', '', 0, 3, '');
        this.tempcar = new DriveSquad('', '', 0, '', '', '', 0, null);
        this.filteredCountriesSingleSquad = [];
        this.filteredCountriesSingleCar = [];
        this.filteredCountriesSingleDriver = [];
        this.listCarMatched = [];
        this.carDrive = new MatchingDC('', '', '', '', '', 0);
        this.status = 1;
        this.processStatus = 0;
        this.phoneNumber = '';
        this.phoneNumberDetail = '';
        this.edit = 0;
        this.count = 0;
        this.temp = '';
        this.listMatching = [''];
        this.listSuggest = [''];
        this.message = '';
        this.listReceiverRemove = [null];
        this.title.setTitle('Thêm mới danh mục lái xe - PMQTVP');
        //this.squad = new DriveSquad(null, null, 0, null, null, null, 0);
        //this.driver = new Employee(null, null, null, null, null, null, 0, null, null, null, null, 0, null, 0);
        this.checkInsert = 0;
        this.total_record = 0;
        this.isEdit = false;
        this.isGroupBy = 0;
        this.driverMatching = new Employee(null, null, null, null, null, null, 0, null, null, null, null, 0, null, 0);
        this.squadMatching = new DriveSquad(null, null, 0, null, null, null, 0, null);
        this.filterLicensePlate = [];
        this.selectedLicensePlate = [];
        this.listSelectedLicensePlate = [null];
    }

    //  when selecting receiver
    selectLicensePlate(index, event) {
        let count = 0;
        this.listSelectedLicensePlate.forEach(element => {
            if (element.carId === event.carId) {
                count++;
            }
        });
        if (count > 1) {
            this.app.showWarn('Gán cho lái xe không được chọn trùng');
            this.listSelectedLicensePlate.splice(index, 1);
            this.inputListSelectedLicensePlate.domHandler.findSingle(this.inputListSelectedLicensePlate.el.nativeElement, 'input').focus();
        }
    }

    getTeamCarvt030003() {
        this.bookcarService.getTeamCarvt030003().subscribe(y => {
            this.listteamcar = y.data;
        });
    }


    getDrivers() {
        this.bookcarService.getDrivers().subscribe(y => {
            this.ListDriver = y.data;
        });
    }

    paginate(event) {
        this.drive.startRow = event.first;
        this.bookcarService.searchListDrive(this.drive).subscribe(y => {
            this.ListDrive = y.data.listDrive;
            this.pageNumber = event.first;
            this.pageSize = y.data.listDrive.length;
            this.total_record = y.data.totalDrive;
        });
    }

    squadSelect(event) {
        this.listSelectedLicensePlate = [null];
        this.squad = new DriveSquad(event.squadId, this.getNameObject(event.squadName), 0, null, null, null, 0, null);
    }

    driverSelect(event) {
        this.driver =
          new Employee(event.employeeId, event.userName, event.employeeCode,
            this.getNameObject(event.fullName), event.employeePhone, event.employeeEmail, event.unitId, event.unitName, event.title, event.levelEmployee, event.role, 0, null, event.status);
        this.phoneNumber = event.employeePhone;
    }


    // set driver when click dropdown
    setDriver() {
        if (this.driver == null || this.driver == undefined) {
            this.driver = new Employee(null, null, null, null, null, null, 0, null, null, null, null, 0, null, 0);
            this.phoneNumber = '';
        }
    }

    // set driver value when click dropdown
    setTeamCar() {
        if (this.squad == null || this.squad === undefined) {
            this.squad = new DriveSquad(null, null, 0, null, null, null, 0, null);
        }
    }

    // validate empty field
    validate(squad: DriveSquad, driver: Employee): boolean {
        if (!(squad != null && squad.squadName !== undefined)) {
            this.inputSquad.domHandler.findSingle(this.inputSquad.el.nativeElement, 'input').focus();
            this.app.showWarn('Tên đội xe không được để trống.');

            return false;
        }
        if (!(driver != null && driver.fullName !== undefined)) {
            this.app.showWarn('Tên lái xe không được để trống.');
            this.inputDriver.domHandler.findSingle(this.inputDriver.el.nativeElement, 'input').focus();
            return false;
        }
        let count = 0;
        this.listSelectedLicensePlate.forEach(element => {
            if (element !== null) {
                count++;
                return;
            }
        });
        if (count === 0) {
            this.app.showWarn('Gán cho lái xe không được để trống.');
            this.inputListSelectedLicensePlate.domHandler.findSingle(this.inputListSelectedLicensePlate.el.nativeElement, 'input').focus();
            return false;
        } else {
            this.message = '';
            return true;
        }
    }

    // add new drive or update drive when click button "Them"
    handleAdd() {   // insert
        if (this.edit === 0) {
            if (this.validate(this.squad, this.driver)) {
                this.settingDrive();
                this.confirmationService.confirm({
                    message: 'Đồng chí có chắc chắn lưu bản ghi này không?',
                    header: 'Xác nhận thêm mới',
                    icon: 'pi pi-exclamation-triangle',
                    acceptLabel: 'Có',
                    rejectLabel: 'Không',
                    accept: () => {
                        console.log(this.drive);
                        this.bookcarService.insertDrive(this.drive).subscribe(y => {
                            if (y.status == 0) {
                                this.app.errorValidateDate('Người lái xe này đã tồn tại.');
                                this.inputSquad.domHandler.findSingle(this.inputSquad.el.nativeElement, 'input').focus();
                            } else {
                                this.resetAll();
                                this.app.showSuccess('Đã thêm lái xe mới.');
                            }
                        });
                    },
                    reject: () => {
                    }
                });
            }

        } else if ( this.edit !== 0) {
            if (!this.validate(this.squad, this.driver)){
                return;
            }
            this.settingDrive();
            this.confirmationService.confirm({
                message: 'Đồng chí có chắc chắn muốn sửa bản ghi này không?',
                header: 'Xác nhận sửa',
                icon: 'pi pi-exclamation-triangle',
                acceptLabel: 'Có',
                rejectLabel: 'Không',
                accept: () => {
                    this.bookcarService.updateDrive(this.drive).subscribe(
                      y => {
                          if (y.status === 5) {
                              this.app.showWarn('Hiện xe đang làm nhiệm vụ.');
                          } else if (y.status === Constants.DELETE_UPDATE_ERROR) {
                              this.app.showWarn('Cập nhật không thành công. Người lái xe này đã bị xóa bởi một người khác.');
                          } else if (y['status'] === 2) {
                              this.app.showWarn('Người lái xe này đã tồn tại.');
                          } else if (y['status'] === 0) {
                              this.app.showWarn('Cập nhật không thành công.');
                          } else {
                              this.resetAll();
                              this.app.showSuccess('Cập nhập thành công');
                          }
                      }
                    );
                },
            });
        }
        this.edit = 0;
        this.isEdit = false;
    }

    // edit
    doEdit(drive: Drive) {
        this.edit = 1;
        const check = 0, checkFinal = 0;
        this.carDrive.squadId = drive.squadId;
        this.carDrive.userName = drive.userName;
        this.carDrive.carId = '';
        const object = {
            'userName': drive.userName,
            'squadId': drive.squadId

        };
        this.bookcarService.findDrive(object).subscribe(y => {
            const deiveSquad = {
                'squadId': y.data.squadId,
                'squadName': y.data.squadName
            };
            this.squad = deiveSquad;
            const driver = {
                'employeeId': y.data.employeeId,
                'fullName': y.data.fullName,
                'userName': y.data.userName
            };
            this.driver = driver;
            this.drive = y.data;
            this.status = y.data.status;
            if (y.data.selectedLicensePlate !== null) {
                this.listSelectedLicensePlate = y.data.selectedLicensePlate;
                this.selectedLicensePlate = y.data.selectedLicensePlate;
                // this.listReceiverRemove = y.data.selectedLicensePlate;
            } else {
                this.selectedLicensePlate = [];
                this.listSelectedLicensePlate = [null];
            }

        });
    }


    settingDrive() {
        this.settingSquad();
        this.drive.employeeId = this.driver.employeeId;
        this.drive.squadId = this.squad.squadId;

        this.drive.squadName = this.getNameObject(this.squad.squadName);
        this.drive.status = this.driver.status;
        if (this.isEdit = false) {
            this.drive.processStatus = this.processStatus;
        }
        this.drive.fullName = this.getNameObject(this.driver.fullName);
        const index_sq = this.listteamcar.findIndex(x => x.squadName === this.drive.squadName);
        //this.drive.squadId = this.listteamcar[index_sq].squadId;
        //const index_drv = this.ListDriver.find(x => x.employeeId === this.drive.employeeId);
        this.drive.userName = this.driver.userName;
        console.log(this.driver);
        //this.drive.employeePhone = index_drv.employeePhone;
        this.drive.status = this.status;
        this.drive.selectedLicensePlate = JSON.parse(JSON.stringify( this.listSelectedLicensePlate));
    }


    settingSearch() {
        //alert(this.getNameObject(this.driver.fullName));
        if (this.squad != null && this.squad.squadName !== undefined) {
            //this.drive.squadName = this.getNameObject(this.squad.squadName);
            this.drive.squadId = (this.squad.squadId);
            this.drive.squadName = null;
        } else {
            this.drive.squadId = null;
            this.drive.squadName = this.squad === '' ? null : this.squad;
        }


        if (this.driver != null && this.driver.fullName !== undefined) {
            //this.drive.fullName = this.getNameObject(this.driver.fullName);
            this.drive.userName = (this.driver.userName);
            this.drive.fullName = null;
        } else {
            this.drive.userName = null;
            this.drive.fullName = this.driver === '' ? null : this.driver;
        }

        this.drive.selectedLicensePlate = this.listSelectedLicensePlate;
        this.drive.employeePhone = this.phoneNumber;
        this.drive.status = this.status;
        this.drive.startRow = 0;
        this.drive.rowSize = 10;
    }

    resetCar() {
        this.getListCar();
    }

    doDelete(drive: Drive) {
        this.bookcarService.deleteDrive(drive).subscribe(
          y => {
              if (y.status === 0) {
                  this.messageService.add({
                      severity: 'error',
                      summary: 'Thông báo',
                      detail: 'Xóa không thành công.'
                  });
              } else if (y.status === Constants.DELETE_UPDATE_ERROR) {
                  this.messageService.add({
                      severity: 'error',
                      summary: 'Thông báo',
                      detail: 'Xóa không thành công. Người lái xe này đã bị xóa bởi một người khác.'
                  });
              } else {
                  this.resetAll();
                  this.messageService.add({
                      severity: 'success',
                      summary: 'Thông báo',
                      detail: 'Đã xóa lái xe.'
                  });
              }
          });

    }


    settingSquad(): any {
        const splitSquad = this.squad.squadName.split(' - ');
        return splitSquad[0];
    }


    // search data
    handleSearch() {
        if (this.myTable !== undefined) {
            this.myTable.reset();
        }
        this.settingSearch();
        this.bookcarService.searchListDrive(this.drive).subscribe(y => {
            this.ListDrive = y.data.listDrive;
            this.pageNumber = 0;
            this.total_record = y.data.totalDrive;
            // if (this.ListDrive.length == 0) this.messageService.add({ severity: 'success', summary: 'Thông báo', detail: 'Chưa tìm thấy dữ liệu phù hợp.' });
        });
    }


    // when click Matching button
    showDialog(drive: Drive) {
        // carDrive
        this.carDrive.squadId = drive.squadId;
        this.carDrive.userName = drive.userName;
        this.carDrive.carId = '';
        this.phoneNumberDetail = drive.employeePhone;
        //this.carDrive.processStatus=drive.processStatus;
        this.settingListSuggest(drive.squadId);  // set list car suggest
        this.status = drive.status;
        const index_sq = <DriveSquad>this.listteamcar.find(x => x.squadId.localeCompare(drive.squadId) == 0);
        if (index_sq == undefined) {
            this.squadMatching = new DriveSquad('', '', 0, null, null, null, drive.status, null);
            this.messageService.add({
                severity: 'error',
                summary: 'Thông báo',
                detail: 'Lái xe này chưa có đội xe nào,nên không gán xe được.'
            });
            return;
        } else {
            this.squadMatching = new DriveSquad(index_sq.squadId, index_sq.squadName, 0, null, null, null, drive.status, null);
        }
        const index_drv = this.ListDriver.find(x => x.employeeId == drive.employeeId);
        this.driverMatching = index_drv;
        //this.phoneNumber = drive.employeePhone;
        this.getCarMatched();
        this.display = true;
    }


    focusOutDriver() {
        if (this.driver != null && this.driver != undefined) {
            if (this.driver.fullName == null || this.driver.fullName == undefined) {
                this.driver = null;
                this.phoneNumber = '';
            } else {
                this.phoneNumber = this.driver.employeePhone;
            }
        } else {
            this.phoneNumber = '';
        }
        //;
    }

    focusOutCar(x: number) {
        if (this.listMatching[x] != null && this.listMatching[x] != undefined) {
            if (this.listMatching[x].licensePlate == null || this.listMatching[x].licensePlate == undefined) {
                this.listMatching[x] = null;
            }
        }
    }


    checkEmpty(input: string) {
        let i = 0;
        let output: string;
        output = '';
        for (i = 0; i < input.length; i++) {
            if (input.charAt(i) !== '') {
                output += input.charAt(i);
            }
        }
        return output;
    }


    // suggest
    filterCountrySingle(event): any {

        let temp: any;
        if (this.squad == null) {
            temp = new DriveSquad(null, null, 0, null, null, null, null, null);
        } else {
            if (this.checkEmpty(this.squad) == '') {
                temp = new DriveSquad(null, null, 0, null, null, null, null, null);
            } else {
                temp = new DriveSquad(null, this.squad.trim(), 0, null, null, null, null, null);
            }
        }
        this.bookcarService.findSquad(temp).subscribe(y => {
            this.filteredCountriesSingleSquad = y.data;
            if (y.status === 1) {
                this.filteredCountriesSingleSquad.forEach(x => {
                    if (x.placeName != null) {
                        x.squadName = x.squadName + ' - ' + x.placeName;
                    }
                });
            }
        });
    }

    filterCountrySingleDriver(event): any {
        let temp: any;
        if (this.driver == null) {
            temp = new Employee(null, null, null, null, null, null, 0, null, null, null, null, 0, null, 0);
        } else {
            if (this.checkEmpty(this.driver) === '') {
                temp = new Employee(null, null, null, null, null, null, 0, null, null, null, null, 0, null, 0);
            } else {
                temp = new Employee(null, null, null, this.driver.trim(), null, null, 0, null, null, null, null, 0, null, 0);
            }
        }

        this.bookcarService.findDriver(temp).subscribe(y => {
            this.filteredCountriesSingleDriver = y.data;
        });
    }

    // close pop-up
    close() {
        this.resetPopup();
    }

    resetPopup() {
        this.listSuggest = [''];
        this.countUp = [];
        this.listMatching = [''];
        this.listCarDrive = [];
        this.count = 0;
        this.display = false;
    }


    countup() {
        this.resetCountUp(this.countUp);
        this.count = this.countUp.length - 1;
        this.count++;
        this.countUp.push(this.count);
    }


    remove(k) {
        this.bookcarService.getListcar().subscribe(y => {
            this.listCar = y.data;

            const removeCar = this.listMatching[k];
            let check = 0;
            if (removeCar !== undefined) {
                this.listCar.forEach(element => {
                    if (element.licensePlate === removeCar.licensePlate) {
                        if (element.processStatus !== 7 && element.processStatus !== 0) {
                            check = 1;
                            return;
                        }
                    }
                });

                if (check == 1) {
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Thông báo',
                        detail: 'Xe này đang hoạt động, không thể xóa được.'
                    });
                    return;
                }
            }

            let index: any;
            if (k === 0) {
                index = 0;
            } else {
                index = this.countUp.findIndex(x => x === k);
            }

            if (this.countUp.length > 1) {
                this.count--;
                this.countUp.splice(index, 1);

                this.listMatching.splice(index, 1);
                this.resetCountUp(this.countUp);
            }
        });
    }

    resetCountUp(countUp: any) {
        let index = 0;
        const array: any[] = [];
        countUp.forEach(element => {

            element = index;
            array.push(element);
            index++;
        });
        this.countUp = array;
    }


    // add drive_car
    add() {
        let count = 0;
        let check = 0;
        // this.listMatching.forEach(element => {
        //   if (element == "") check = 1;
        // });
        this.getListCar();
        this.groupBy(this.listMatching);
        if (this.isGroupBy === 1) {
            this.messageService.add({
                severity: 'error',
                summary: 'Thông báo',
                detail: 'Vui lòng không nhập xe trùng nhau.'
            });
            this.isGroupBy = 0;
            this.close();
            return;
        }

        if (this.listMatching.length == 0) {
            check = 1;
        } else {
            this.listMatching.forEach(element => {
                let array: any[];
                if (element != null) {
                    array = (element + '').split(' - ');

                    //let index = <Car> this.listCar.find(x => x.licensePlate == array[0]);
                    this.listCar.forEach(element1 => {

                        if (element1.licensePlate.localeCompare(element.licensePlate) == 0) {
                            this.carDrive.carId = element1.carId;
                            this.carDrive.processStatus = element1.processStatus;
                            count++;
                        }

                    });

                    const tempObj = new MatchingDC(this.carDrive.carId, this.carDrive.squadId, '', this.carDrive.userName, '', this.carDrive.processStatus);

                    this.listCarDrive.push(tempObj);
                }
            });

            if (count !== this.listMatching.length) {
                check = 1;
            }
        }

        if (check === 1) {
            this.messageService.add({
                severity: 'error',
                summary: 'Thông báo',
                detail: 'Chưa có xe nào được gán, vui lòng nhập lại !'
            });
            this.listCarDrive = [];
            if (this.listMatching.length === 0) {
                if (this.inputGroup.nativeElement.querySelectorAll('input')[0] != null
                  && this.inputGroup.nativeElement.querySelectorAll('input')[0] !== undefined) {
                    this.inputGroup.nativeElement.querySelectorAll('input')[0].focus();
                }
            } else {
                for (let index = 0; index < this.listMatching.length; index++) {
                    if (this.listMatching[index] === undefined || this.listMatching[index] === null
                      || this.listMatching[index] === '' || this.listMatching[index].licensePlate === '') {
                        this.inputGroup.nativeElement.querySelectorAll('input')[index].focus();
                        return;
                    }
                }
            }
            return;
        }
        this.bookcarService.insertDriveCar(this.listCarDrive).subscribe(
          y => {
              if (y.status === 0) {
                  this.messageService.add({severity: 'error', summary: 'Thông báo', detail: 'Không Gán xe được'});
              } else {
                  this.messageService.add({
                      severity: 'success',
                      summary: 'Thông báo',
                      detail: 'Đã giao xe cho lái xe quản lý'
                  });
                  this.handleSearch();
              }
          });
        this.resetPopup();
    }


    groupBy(list: any[]) {
        const newArr: any[] = [];

        const newList = list.filter(element => {
            return element != null && element !== '';
        });
        newList.forEach(element => {
            if (newArr.indexOf(element.licensePlate) === -1) {
                newArr.push(element.licensePlate);
            }
        });
        if (newArr.length < newList.length) {
            this.isGroupBy = 1;
        }
    }

    getListCar() {
        this.bookcarService.getListcar().subscribe(y => {
            this.listCar = y.data;
        });
    }

    settingListSuggest(squadId: string) {
        this.listSuggest.splice(0, 1);
        this.listCar.forEach(element => {
            if (element.squadId === squadId) {
                this.listSuggest.push(element);
            }
        });

        this.listSuggest.forEach(element => {
            element.displayOption = element.licensePlate + ' - ' + element.type + ' - ' + element.seat;
        });
    }

    filterCountrySingleCar(event) {
        this.filteredCountriesSingleCar = [];
        const query = event.query;
        this.filteredCountriesSingleCar = this.filterCar(query.trim(), this.listSuggest);
        //this.filteredCountriesSingleCar  = this.listSuggest;
    }


    filterCar(query, list: any[]): any {
        const filtered: any[] = [];
        let item: string;
        for (let i = 0; i < list.length; i++) {
            item = list[i].licensePlate + ' - ' + list[i].type + ' - ' + list[i].seat;
            if (item.toLowerCase().includes(query.toLowerCase())) {
                filtered.push(list[i]);
            }
        }
        return filtered;
    }


    getNameObject(suggest: string): any {
        if (suggest == null) {
            return '';
        }
        const split = suggest.split(' - ');
        return split[0];
    }


    // GET LIST CAR MATCHED

    getCarMatched() {
        this.bookcarService.findMatchedCar(this.carDrive).subscribe(y => {
            this.listCarMatched = y.data;
            this.listCarMatched.forEach(element => {
                const index = this.listCar.find(x => x.carId == element.carId);
                element.type = index.type;
                element.seat = index.seat;
                element.status = index.status;
                element.licensePlate = index.licensePlate;
                element.processStatus = index.processStatus;
            });

            this.showCarMatched();
        });
    }


    showCarMatched() {
        let count = 0;
        this.listCarMatched.forEach(element => {
            this.listMatching[count] = element;
            this.listMatching[count].displayOption = element.licensePlate + ' - ' + element.type + ' - ' + element.seat;
            this.countUp.push(count++);
        });
        this.count = count - 1;
    }

    resetAll() {
        if (this.myTable !== undefined) {
            this.myTable.reset();
        }
        this.isEdit = false;
        this.edit = 0;
        this.squad = null;
        this.driver = null;
        this.phoneNumber = '';
        this.status = 1;
        this.selectedLicensePlate = [];
        this.listSelectedLicensePlate = [null];
        this.listReceiverRemove = [];
        this.handleSearch();
    }

    resetButton() {
        this.isEdit = false;
        this.edit = 0;
        this.squad = null;
        this.driver = null;
        this.phoneNumber = '';
        this.status = 1;
        this.selectedLicensePlate = [];
        this.listSelectedLicensePlate = [null];
        this.listReceiverRemove = [];
        this.handleSearch();
        //this.getListDrive();
    }


    confirmAdd() {
        if (this.validate(this.squad, this.driver)) {
            this.confirmationService.confirm({
                message: 'Đồng chí có chắc chắn lưu bản ghi này không?',
                header: 'Confirmation',
                icon: 'pi pi-exclamation-triangle',
                acceptLabel: 'Có',
                rejectLabel: 'Không',
                accept: () => {
                    this.handleAdd();

                },
                reject: () => {
                    //this.resetAll();
                }
            });
        }
    }

    confirmDelete(drive: Drive) {
        if (drive.processStatus === 4 || drive.processStatus === 5) {
            this.app.showWarn('Hiện lái xe đang làm nhiệm vụ');
        } else if (drive.processStatus === 0 || drive.processStatus === 7) {
            this.confirmationService.confirm({
                message: 'Đồng chí có chắc chắn xóa bản ghi này không?',
                header: 'Xác nhận xóa',
                icon: 'pi pi-exclamation-triangle',
                accept: () => {
                    this.doDelete(drive);
                    this.msgs = [{severity: 'info', summary: 'Confirmed', detail: 'This record has deleted'}];
                },
                reject: () => {
                    this.msgs = [{severity: 'info', summary: 'Rejected', detail: 'This record has rejected'}];
                }
            });
        }

    }


    confirmMatching() {
        this.confirmationService.confirm({
            message: 'Are you sure that you want to matching this driver with these car ?',
            header: 'Confirmation',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                //this.add();
                this.msgs = [{severity: 'info', summary: 'Confirmed', detail: 'This record has inserted'}];
            },
            reject: () => {
                this.msgs = [{severity: 'info', summary: 'Rejected', detail: 'This record has rejected'}];
            }
        });
    }


    loadLicensePlate(event) {
        if (this.squad && this.squad.length > 0 || !this.squad) {
            this.filterLicensePlate = [];
            this.inputSquad.domHandler.findSingle(this.inputSquad.el.nativeElement, 'input').focus();
            this.app.showWarn('Chưa nhập tên đội xe.');
            return;
        }
        const object = {
            'driveSquadId': this.squad.squadId,
            'searchDTO': event.query,
        };
        this.bookcarService.searchSuggestionLicensePlate(object).subscribe(item => {
            this.filterLicensePlate = item['data'];
        });
    }

    //  change receiver
    changeLicensePlate(index: number) {
        this.listSelectedLicensePlate[index] = null;
    }


    //  when add textbox receiver
    addTextbox() {
        this.listSelectedLicensePlate.push(null);
        //this.listReceiverRemove.push(null);
    }

    //  when remove textbox receiver
    removeTextbox(index: number) {
        this.listSelectedLicensePlate.splice(index, 1);
        ///this.selectedLicensePlate.splice(index, 1);
        //this.listReceiverRemove.splice(index, 1);
    }

    alertMessage(severity: string, summary: string, detail: string) {
        this.messageService.add({
            severity: severity,
            summary: summary,
            detail: detail
        });
    }

}
