import {Component, OnInit, OnDestroy, AfterViewInit, ViewChild, Renderer2, ElementRef} from '@angular/core';
import {trigger, state, style, transition, animate} from '@angular/animations';
import {Table} from 'primeng/table';
import {Title} from '@angular/platform-browser';
import {Router, NavigationEnd} from '@angular/router';
import {ServiceDataService} from '../service-data.service';
import {ConfirmationService, MessageService} from 'primeng/components/common/api';
import {Condition} from '../Entity/ConditionMenuService';
import {MenuService} from '../Entity/ListMenuService';
import {AutoComplete} from 'primeng/autocomplete';
import { TokenStorage } from '../../shared/token.storage';
import { UserInfo } from '../../shared/UserInfo';
import { saveAs } from 'file-saver';
import { FileUploadModule, FileUpload } from 'primeng/components/fileupload/fileupload';
import {Constants} from '../../shared/containts';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Component({
    selector: 'app-service-menu',
    templateUrl: './service-menu.component.html',
    styleUrls: ['./service-menu.component.css'],
    animations: [
        trigger('animation', [
            state('true', style({
                opacity: 1
            })),
            state('false', style({
                opacity: 0,
                display: 'none'
            })),
            transition('0 => 1', animate('190ms')),
            transition('1 => 0', animate('1000ms'))
        ])
    ]
})

export class ServiceMenuComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild('myTable') private myTable: Table;
    @ViewChild('rpTimeInput') private resTimeInput: ElementRef;
    @ViewChild('inputPlace') private inputPlace: AutoComplete;
    @ViewChild('inputUnit') private inputUnit: AutoComplete;
    @ViewChild('inputService') private inputService: AutoComplete;
    @ViewChild('inputGroup') private inputGroup: ElementRef;

    navigationSubscription;
    condition: Condition;
    conditionSetUp: Condition;
    isClick: boolean;
    listReceiverRemove: any[];

    listSearchMenuService: MenuService[];
    serviceId: string;
    filterPlace: any[];
    filterService: any[];
    filterUnitReceive: any[];

    placeInfo: any;
    serviceInfo: any;
    unitReceiveInfo: any;

    //kienadd
    unitReceiveInfoForloadReceive: any;
    //kienadd END

    responseTime: string;
    statusService: string;
    totalRecord: number;
    startRow: number;
    rowSize: number;

    //  handle list receiver
    filterReceiver: any[];
    selectedReceiver: any[];
    listSelectedReceiver: any[];

    isEmpty: boolean;
    loading: boolean;
    loading2: boolean;
    isAdd: boolean;
    displayPopup: boolean;
    messageAlert: string;
    uf: UserInfo;
    urlUpload: any = Constants.HOME_URL + 'com/viettel/vtnet360/vt04/vt040001/uploadService';
    uploadedFiles: any[] = [];
    showImportForm: boolean;
    requireSign: boolean;
    notiImportFile: any;
    file: Blob;
    // tslint:disable-nex
    // t-line:max-line-length
    onlyChar: RegExp = /^[0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;
    // tslint:disable-next-line:max-line-length
    notChar: RegExp = /[^0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;

    constructor(private _ServiceDataService: ServiceDataService, private confirmationService: ConfirmationService, private tokenStorage: TokenStorage,
                private messageService: MessageService, private title: Title, private renderer: Renderer2, private router: Router) {
        this.navigationSubscription = this.router.events.subscribe(event => {
            if (!(event instanceof NavigationEnd)) {
                return;
            }
            // Do what you need to do here, for instance :
            this.serviceId = null;
            this.placeInfo = null;
            this.serviceInfo = null;
            this.unitReceiveInfo = null;
            this.responseTime = '';
            this.statusService = '1';
            this.listReceiverRemove = [null];
            this.filterReceiver = [];
            this.selectedReceiver = [];
            this.listSelectedReceiver = [null];
            this.isAdd = true;
            this.requireSign = true;
            if (this.isClick) {
                this.ngOnInit();
                this.ngAfterViewInit();
            }
        });
    }

    ngOnInit() {
        this.notiImportFile = null;
        this.isClick = false;
        this.loading = true;
        this.loading2 = false;
        this.isAdd = true;
        this.displayPopup = false;
        this.startRow = -1;
        this.rowSize = 1;
        this.totalRecord = 0;
        this.statusService = '1';
        this.messageAlert = 'Không được nhập quá 50 ký tự';
        this.listReceiverRemove = [null];
        this.filterReceiver = [];
        this.selectedReceiver = [];
        this.listSelectedReceiver = [null];
        this.title.setTitle('Danh mục dịch vụ - PMQTVP');
        this.uf = this.tokenStorage.getUserInfo();
        this.requireSign = true;
    }

    //  when edit data in place
    changePlace() {
        this.clearService(null);
        if (this.notChar.test(this.inputPlace.el.nativeElement.children[0].children[0].value)) {
            this.inputPlace.el.nativeElement.children[0].children[0].value = this.inputPlace.el.nativeElement.children[0].children[0].value.replace(this.notChar, '');
        }
    }

    //  when edit data in service
    changeService(baseService: string) {
        if (baseService) {
            this.inputService.el.nativeElement.children[0].children[0].value = baseService;
        }
        if (this.notChar.test(this.inputService.el.nativeElement.children[0].children[0].value)) {
            this.inputService.el.nativeElement.children[0].children[0].value = this.inputService.el.nativeElement.children[0].children[0].value.replace(this.notChar, '');
        }
        return this.inputService.el.nativeElement.children[0].children[0].value;
    }

    //  when edit data in unit receivered
    changeUnitReceiver() {
        this.validateCompleteInput();
    }


   /* unitPressTyping() {

        // this.validateCompleteInput();
        // //kienadd
        this.listReceiverRemove = [null];
        this.selectedReceiver = [];
        this.listSelectedReceiver = [null];
        this.unitReceiveInfoForloadReceive = null;
    }*/

    validateCompleteInput() {

        if (this.notChar.test(this.inputUnit.el.nativeElement.children[0].children[0].value)) {
            this.inputUnit.el.nativeElement.children[0].children[0].value = this.inputUnit.el.nativeElement.children[0].children[0].value.replace(this.notChar, '');
        }

    }

    //  check first character of response time
    changeResponseTime() {
        const valResponseTime = this.resTimeInput.nativeElement.value.toString();
        //  remove all character is not digit
        if (/[^0-9]/g.test(valResponseTime)) {
            this.resTimeInput.nativeElement.value = this.resTimeInput.nativeElement.value.replace(/[^0-9]/g, '');
            //  remove all first number is 0
            if (/^0*/g.test(valResponseTime)) {
                this.resTimeInput.nativeElement.value = this.resTimeInput.nativeElement.value.replace(/^0*/g, '');
            }
        }
        if (+this.resTimeInput.nativeElement.value > 2000000000) {
            this.resTimeInput.nativeElement.value = '2000000000';
        }
        this.responseTime = this.resTimeInput.nativeElement.value;
        //  set maxlength to format
        this.renderer.setAttribute(this.resTimeInput.nativeElement, 'maxlength', '10');
        //  remove maxlength to reset
        this.renderer.removeAttribute(this.resTimeInput.nativeElement, 'maxlength');
    }

    //  focus service name
    focusServiceName() {
        this.renderer.setAttribute(this.inputService.el.nativeElement.children[0].children[0], 'maxlength', '50');
    }

    //  when click radio button active
    clickStatusActive() {
        if (!this.isAdd) {
            this.statusService = '1';
        }
    }

    //  when click radio button deactive
    clickStatusDeactive() {
        if (!this.isAdd) {
            this.statusService = '0';
        }
    }

    //  when click 'x' of service
    clearService(event) {

        if (this.serviceInfo) {
            if (event != null) {
                event.stopPropagation();
            }
            this.serviceInfo = null;
            this.loading2 = false;
        }

    }

    //  show suggestion when type in place
    loadPlace(event) {

        this.changePlace();
        const object = {
            'placeName': event.query ? event.query.replace(this.notChar, '') : ''
        };
        this._ServiceDataService.getPlaces(object).subscribe(item => {
            this.filterPlace = item['data'];
        });
    }

    //  show suggestion when type in service
    loadService(event) {
        let object = null;
        if (this.placeInfo && this.placeInfo['placeId']) {
            const valPlace = this.inputPlace.el.nativeElement.children[0].children[0].value;
            object = {
                'serviceName': event.query ? event.query.replace(this.notChar, '') : '',
                'placeId': (valPlace && valPlace.trim().length > 0) ? this.placeInfo['placeId'] : null
            };
        } else {
            object = {
                'serviceName': event.query ? event.query.replace(this.notChar, '') : ''
            };
        }
        this.loading2 = false;
        this._ServiceDataService.getServices(object).subscribe(item => {
            this.filterService = item['data'];
            this.loading2 = true;
        });
    }

    //  show suggestion when type in unit received kien
    loadUnitReceive(event) {
        this.listReceiverRemove = [null];
        // this.selectedReceiver = [];
        // this.listSelectedReceiver = [null];
        this.unitReceiveInfoForloadReceive = null;

        this.validateCompleteInput();

        const object = {
            'placeName': event.query ? event.query.replace(this.notChar, '') : ''
        };

        this._ServiceDataService.getUnitReceived(object).subscribe(item => {
            this.filterUnitReceive = item['data'];
        });

        // //kien add sua de load ra toan bo
        // this.unitReceiveInfo = null;

        // // //kienadd
        // this.listReceiverRemove = [null];
        // this.listSelectedReceiver = [null];

    }

    //  show suggestion when type in receiver kien
    loadReceiver(event,index) {
        this.listReceiverRemove[index] = null;
        if (this.unitReceiveInfoForloadReceive == null) {

            this.unitReceiveInfoForloadReceive = {
                placeId: '',
                placeName: '',
                threeLevelUnit: ''
            };

            // this.filterReceiver = [];
            // return;
        }

        const tempListReceiver = [];

        for (let i = 0; i < this.listReceiverRemove.length; i++) {
            if (this.listReceiverRemove[i]) {
                tempListReceiver.push(this.listReceiverRemove[i]);
            }
        }

        const object = {
            'receiverName': event.query,
            'listUserNameReceiver': tempListReceiver,
            'unitId': this.unitReceiveInfoForloadReceive.placeId
        };

        this._ServiceDataService.getReceivers(object).subscribe(item => {
            this.filterReceiver = item['data'];
            console.log(this.filterReceiver);
        });


    }


    //  when remove textbox receiver
    removeTextbox(index: number) {
        this.listSelectedReceiver.splice(index, 1);
        this.selectedReceiver.splice(index, 1);
        this.listReceiverRemove.splice(index, 1);
    }

    //  when add textbox receiver
    addTextbox() {
        this.listSelectedReceiver.push(null);
        this.listReceiverRemove.push(null);
    }

    //  when selecting receiver
    selectReceiver(index: number) {
        this.listReceiverRemove[index] = this.selectedReceiver[index].receiverUserName;
    }

    setSelectUnit(event) {
        // khi ma chon don vi: kien
       // this.unitReceiveInfo = null;
        this.unitReceiveInfo = {
            placeId: event.placeId,
            placeName: event.placeName,
            threeLevelUnit: event.placeName
        };

        // 123
        this.unitReceiveInfoForloadReceive = this.unitReceiveInfo;

        this.listReceiverRemove = [null];
        // this.selectedReceiver = [];
        // this.listSelectedReceiver = [null];

    }

    //  when click edit button in row of table
    doEdit(item: MenuService) {
        this.isAdd = false;
        this.serviceId = item.serviceId;
        this.responseTime = +item.responseTime > 2000000000 ? '2000000000' : item.responseTime;
        this.statusService = item.statusService;

        this.placeInfo = {
            placeId: item.placeId,
            placeName: item.placeName
        };

        this.serviceInfo = {
            serviceId: item.serviceId,
            serviceName: item.serviceName
        };

        this.unitReceiveInfo = {
            placeId: item.unitId,

            placeName: item.unitName,

            threeLevelUnit: item.unitName
        };
        this.requireSign = !!+item.requireSign;
        this.unitReceiveInfoForloadReceive = this.unitReceiveInfo;

        const object = {
            'serviceId': item.serviceId
        };

        this.filterReceiver = [];
        this.listReceiverRemove = [null];
        this.selectedReceiver = [];
        this.listSelectedReceiver = [null];

        this._ServiceDataService.getReceiverOfService(object).subscribe(item1 => {

            this.filterReceiver = item1['data'];

            for (let i = 0; i < this.filterReceiver.length; i++) {
                if (i > 0) {
                    this.listSelectedReceiver.push(null);
                }
                this.selectedReceiver[i] = this.filterReceiver[i];
                this.listReceiverRemove[i] = this.selectedReceiver[i].receiverUserName;
            }

        });

    }

    //  handle fileds which have [forceSelection] property
    handleBeforeGetCondition() {
        const valPlace = this.inputPlace.el.nativeElement.children[0].children[0].value;
        const valUnit = this.inputUnit.el.nativeElement.children[0].children[0].value;

        if (!valPlace || valPlace.trim().length === 0) {
            this.placeInfo = null;
        }

        if (!valUnit || valUnit.trim().length === 0) {
            this.unitReceiveInfo = null;
        }
    }

    //  handle conditions to search
    getConditionSearch() {
        let placeid = null;
        let placename = null;
        let serviceid = null;
        let servicename = null;
        let unitid = null;
        let unitname = null;
        //  handle place
        if (this.placeInfo && this.placeInfo.length > 0) {
            placename = this.placeInfo;
            placeid = null;
        } else if (this.placeInfo && this.placeInfo['placeId']) {
            placename = null;
            placeid = this.placeInfo['placeId'];
        }

        //  handle service
        if (this.serviceInfo && this.serviceInfo.length > 0) {
            servicename = this.serviceInfo;
            serviceid = null;
        } else if (this.serviceInfo && this.serviceInfo['serviceId']) {
            servicename = null;
            serviceid = this.serviceInfo['serviceId'];
        }

        //  handle unit received
        if (this.unitReceiveInfo && this.unitReceiveInfo.length > 0) {
            unitname = this.unitReceiveInfo;
            unitid = null;
        } else if (this.unitReceiveInfo && this.unitReceiveInfo['placeId']) {
            unitname = null;
            unitid = this.unitReceiveInfo['placeId'];
        }

        // get list Receiver User name
        let listreceiverusername: any[] = [];
        const inputList: NodeListOf<HTMLInputElement> = this.inputGroup.nativeElement.querySelectorAll('input');
        console.log(inputList.length);
        for (let index = 0; index < inputList.length; index++) {
            const element = inputList[index];
            if (this.selectedReceiver[index] && element && element.value != null && element.value !== '') {
                listreceiverusername.push(this.selectedReceiver[index].receiverUserName);
            }
            console.log(listreceiverusername);
        }

        this.condition = null;
        this.condition = {
            placeId: placeid,
            placeName: placename,
            serviceId: serviceid,
            serviceName: servicename,
            listReceiverUserName: listreceiverusername,
            unitId: unitid,
            unitName: unitname,
            responseTime: this.responseTime,
            statusService: this.statusService,
            requireSign: this.requireSign ? 1 : 0,
            startRow: 0,
            rowSize: 10,
            securityUsername: this._ServiceDataService.getAccount().securityUsername,
            securityPassword: this._ServiceDataService.getAccount().securityPassword
        };
    }

    //  get condition to insert, update
    getConditionSetUp() {
        let placeid = null;
        let placename = null;
        let serviceid = null;
        let servicename = null;
        let unitid = null;
        let unitname = null;

        this.handleBeforeGetCondition();

        //  handle place
        if (this.placeInfo && this.placeInfo.length > 0) {
            placename = this.placeInfo;
        } else if (this.placeInfo && this.placeInfo['placeId']) {
            placeid = this.placeInfo['placeId'];
        }

        //  handle service
        if (this.serviceInfo && this.serviceInfo.length > 0) {
            servicename = this.changeService(this.serviceInfo);
            servicename = servicename.trim();
        } else if (this.serviceInfo && this.serviceInfo['serviceId']) {
            serviceid = this.serviceInfo['serviceId'];
        }

        //  handle unit received
        if (this.unitReceiveInfo && this.unitReceiveInfo.length > 0) {
            unitname = this.unitReceiveInfo;
        } else if (this.unitReceiveInfo && this.unitReceiveInfo['placeId']) {
            unitid = this.unitReceiveInfo['placeId'];
        }
        this.conditionSetUp = null;
        this.conditionSetUp = {
            placeId: placeid,
            placeName: placename,
            serviceId: serviceid,
            serviceName: servicename,
            listReceiverUserName: null,
            unitId: unitid,
            unitName: unitname,
            responseTime: this.responseTime,
            statusService: this.statusService,
            requireSign: this.requireSign ? 1 : 0,
            startRow: -1,
            rowSize: -1,
            securityUsername: this._ServiceDataService.getAccount().securityUsername,
            securityPassword: this._ServiceDataService.getAccount().securityPassword
        };
    }

    //  get list receiver user name
    getListReceiverUserName() {
        this.conditionSetUp.listReceiverUserName = [];

        if (this.selectedReceiver && this.selectedReceiver.length > 0) {
            for (let i = 0; i < this.selectedReceiver.length; i++) {
                if (this.selectedReceiver[i] && this.selectedReceiver[i].receiverUserName.length > 0) {
                    this.conditionSetUp.listReceiverUserName.push(this.selectedReceiver[i].receiverUserName);
                }
            }
        }
    }

    //  get list search menu service
    getListSearchMenuService() {
        this.loading = true;
        if (this.myTable !== undefined) {
            this.myTable.reset();
        }

        this._ServiceDataService.getListSearchMenuServices(this.condition).subscribe(item => {
            this.listSearchMenuService = item['data'];
            this.startRow = 0;
            if (this.listSearchMenuService && this.listSearchMenuService.length >= 10) {
                this.rowSize = 10;
            } else {
                this.rowSize = this.listSearchMenuService.length;
            }

            if (this.listSearchMenuService === null || this.listSearchMenuService.length === 0) {
                this.isEmpty = true;

                // if (this.isClick) {
                //   this.alertMessage('info', 'Thông báo', 'Không tìm thấy bản ghi tương ứng');
                // }
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

            this._ServiceDataService.getListSearchMenuServices(this.condition).subscribe(item => {
                this.listSearchMenuService = item['data'];
                this.rowSize = this.listSearchMenuService.length;
                this.loading = false;
            });
        }, 500);
    }

    //  when click search button
    searchData() {
        this.getConditionSearch();
            this._ServiceDataService.countTotalMenuService(this.condition).subscribe(item => {
            this.totalRecord = item['data'];
            this.getListSearchMenuService();
        });
    }

    exportServiceExcel() {
        this.getConditionSearch();
        this._ServiceDataService.countTotalMenuService(this.condition).subscribe(item => {
            this.totalRecord = item['data'];
            const numOfRow = item['data'];
            if (numOfRow <= 50000) {
                this._ServiceDataService.exportServiceExcel(this.condition).subscribe(
                    (next: ArrayBuffer) => {
                        var fileName = 'DSSERVICE_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
                            + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
                            + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
                        const file: Blob = new Blob([next], {type: 'application/xlsx'});
                        saveAs(file, fileName);
                    }
                );
            } else {
                this.messageService.add({
                    severity: 'warn',
                    summary: 'Không thể xuất file',
                    detail: 'Số bản ghi tìm thấy vượt quá 50000'
                });
            }
        });
    }

    //  when click add button
    addData() {
        if (this.checkBeforeAdd()) {
            this.confirmationService.confirm({
                message: 'Đ/c có muốn THÊM dịch vụ này?',
                header: 'Thêm dịch vụ',
                icon: 'pi pi-question-circle',
                acceptLabel: 'Thêm',
                rejectLabel: 'Hủy bỏ',
                accept: () => {
                    this.getConditionSetUp();
                    this.getListReceiverUserName();

                    const object = {
                        'action': 1,
                        'data': this.conditionSetUp
                    };

                    this._ServiceDataService.handleSetUpService(object).subscribe(item => {
                        if (item['status'] === 1) {
                            this.resetButton();
                            this.searchData();
                            this.alertMessage('success', 'Thông báo', 'Đã thêm danh mục dịch vụ');
                        } else if (item['status'] === 0) {
                            this.alertMessage('error', 'Cảnh báo', 'Thêm dịch vụ thất bại');
                        } else if (item['status'] === 2) {
                            this.alertMessage('error', 'Cảnh báo', 'Danh mục dịch vụ đã tồn tại');
                            this.inputService.domHandler.findSingle(this.inputService.el.nativeElement, 'input').focus();
                        } else {
                            this.alertMessage('error', 'Cảnh báo', 'Có lỗi không xác định');
                        }
                    });
                }
            });
        }
    }

    //  when click update button
    updateData() {
        if (this.checkBeforeAdd()) {
            this.confirmationService.confirm({
                message: 'Đ/c có muốn CẬP NHẬT dịch vụ này?',
                header: 'Cập nhật dịch vụ',
                icon: 'pi pi-question-circle',
                acceptLabel: 'Cập nhật',
                rejectLabel: 'Hủy bỏ',
                accept: () => {
                    this.getConditionSetUp();
                    this.getListReceiverUserName();
                    this.conditionSetUp.serviceId = this.serviceId;

                    if (this.serviceInfo && this.serviceInfo.length > 0) {
                        this.conditionSetUp.serviceName = this.serviceInfo.trim();
                    } else if (this.serviceInfo && this.serviceInfo['serviceName']) {
                        this.conditionSetUp.serviceName = this.serviceInfo['serviceName'].trim();
                    }
                    if (this.statusService === '0') {
                        this.conditionSetUp.startRow = 1;
                    }

                    const object = {
                        'action': 2,
                        'data': this.conditionSetUp
                    };

                    this._ServiceDataService.handleSetUpService(object).subscribe(item => {

                        if (item['status'] === 1) {
                            this.resetButton();
                            this.searchData();
                            this.alertMessage('success', 'Thông báo', 'Đã cập nhật danh mục dịch vụ.');
                        } else if (item['status'] === 0) {
                            this.alertMessage('error', 'Cảnh báo', 'Cập nhật dịch vụ thất bại');
                        } else if (item['status'] === 2) {
                            this.alertMessage('error', 'Cảnh báo', 'Dịch vụ đã tồn tại');
                            this.inputService.domHandler.findSingle(this.inputService.el.nativeElement, 'input').focus();
                        } else if (item['status'] === 3) {
                            this.alertMessage('error', 'Cảnh báo', 'Dịch vụ đang được sử dụng');
                        } else if (item['status'] === 14) {
                            this.searchData();
                            this.alertMessage('error', 'Cảnh báo', 'Dịch vụ đã bị xóa bởi người khác');
                        } else {
                            this.alertMessage('error', 'Cảnh báo', 'Có lỗi không xác định');
                        }
                    });
                }
            });
        }
    }

    //  when click delete button in row of table
    doDelete(item: MenuService) {
        this.confirmationService.confirm({
            message: 'Đ/c có muốn XÓA dịch vụ này?',
            header: 'Xóa dịch vụ',
            icon: 'fa fa-warning',
            acceptLabel: 'Xóa',
            rejectLabel: 'Hủy bỏ',
            accept: () => {
                this.conditionSetUp = new Condition();
                this.conditionSetUp.serviceId = item.serviceId;
                this.conditionSetUp.placeName = item.placeName;
                this.conditionSetUp.serviceName = item.serviceName;
                this.conditionSetUp.securityUsername = this._ServiceDataService.getAccount().securityUsername;
                this.conditionSetUp.securityPassword = this._ServiceDataService.getAccount().securityPassword;
                const object = {
                    'action': 3,
                    'data': this.conditionSetUp
                };

                this._ServiceDataService.handleSetUpService(object).subscribe(item1 => {
                    if (item1['status'] === 1) {
                        if (this.serviceId === item.serviceId) {
                            this.resetButton();
                            this.searchData();
                        } else {
                            this.condition = {
                                placeId: null,
                                placeName: null,
                                serviceId: null,
                                serviceName: null,
                                listReceiverUserName: null,
                                unitId: null,
                                unitName: null,
                                responseTime: null,
                                statusService: null,
                                requireSign: null,
                                startRow: 0,
                                rowSize: 10,
                                securityUsername: this._ServiceDataService.getAccount().securityUsername,
                                securityPassword: this._ServiceDataService.getAccount().securityPassword
                            };
                            this._ServiceDataService.countTotalMenuService(this.condition).subscribe(itemResp => {
                                this.totalRecord = itemResp['data'];
                                this.getListSearchMenuService();
                            });
                        }
                        this.alertMessage('success', 'Thông báo', 'Xóa dịch vụ thành công');
                    } else if (item1['status'] === 0) {
                        this.alertMessage('error', 'Cảnh báo', 'Xóa dịch vụ thất bại');
                    } else if (item1['status'] === 4) {
                        this.searchData();
                        this.alertMessage('error', 'Cảnh báo', 'Dịch vụ đã bị xóa bởi người khác');
                    } else if (item1['status'] === 3) {
                        this.alertMessage('error', 'Cảnh báo', 'Hiện tại dịch vụ đang được yêu cầu sử dụng. ');
                    } else {
                        this.alertMessage('error', 'Cảnh báo', 'Có lỗi không xác định');
                    }
                });
            }
        });
    }

    //  check before add
    checkBeforeAdd(): boolean {

        this.handleBeforeGetCondition();
        let isOk = true;
        const valService = this.inputService.el.nativeElement.children[0].children[0].value;
        if (!this.placeInfo || (this.placeInfo.placeId == null && this.placeInfo.placeName == null )) {
            isOk = false;
            this.alertMessage('error', 'Cảnh báo', 'Vị trí không được để trống');
            this.inputPlace.domHandler.findSingle(this.inputPlace.el.nativeElement, 'input').focus();
        } else if (!this.serviceInfo || (valService && valService.trim().length === 0)) {
            isOk = false;
            this.alertMessage('error', 'Cảnh báo', 'Tên dịch vụ không được để trống');
            this.inputService.domHandler.findSingle(this.inputService.el.nativeElement, 'input').focus();
        } else if (!this.unitReceiveInfo || this.unitReceiveInfo.placeName == null) {
            isOk = false;
            this.alertMessage('error', 'Cảnh báo', 'Đơn vị đáp ứng không được để trống');
            this.inputUnit.domHandler.findSingle(this.inputUnit.el.nativeElement, 'input').focus();
        } else if (!this.responseTime || this.responseTime.length === 0) {
            isOk = false;
            this.alertMessage('error', 'Cảnh báo', 'Thời gian đáp ứng không được để trống');
            this.resTimeInput.nativeElement.focus();
        } else if (this.listReceiverRemove.includes(null)) {
            isOk = false;
            this.alertMessage('error', 'Cảnh báo', 'Người tiếp nhận không được để trống');
            for (let index = 0; index < this.listReceiverRemove.length; index++) {
                if (this.listReceiverRemove[index] === null) {
                    this.inputGroup.nativeElement.querySelectorAll('input')[index].focus();
                    return;
                }
            }
        } else if (this.responseTime === '0') {
            isOk = false;
            this.alertMessage('error', 'Cảnh báo', 'Thời gian đáp ứng không hợp lệ');
            this.resTimeInput.nativeElement.focus();
        }

        return isOk;
    }

    //  alert message
    alertMessage(severity: string, summary: string, detail: string) {
        this.messageService.add({
            severity: severity,
            summary: summary,
            detail: detail
        });
    }

    //  when click reset button
    resetButton() {
        this.serviceId = null;
        this.placeInfo = null;
        this.serviceInfo = null;
        this.unitReceiveInfo = null;
        this.responseTime = '';
        this.statusService = '1';
        this.listReceiverRemove = [null];
        this.filterReceiver = [];
        this.selectedReceiver = [];
        this.listSelectedReceiver = [null];
        this.listReceiverRemove =[null];
        this.isAdd = true;
        this.requireSign = true;
        this.searchData();
    }

    ngAfterViewInit() {
        this.searchData();
    }

    ngOnDestroy() {
        // avoid memory leaks here by cleaning up after ourselves. If we
        // don't then we will continue to run our initialiseInvites()
        // method on every navigationEnd event.
        if (this.navigationSubscription) {
            this.navigationSubscription.unsubscribe();
        }
    }

    closeDialog() {
        this.showImportForm = false;
        this.uploadedFiles =[];
        this.notiImportFile= null;
    }

    openImportExcel() {
        this.showImportForm = true;
    }

    exportTemplateExcel() {
        this.loading = true;
        this._ServiceDataService.exportTemplate({}).subscribe(
            (res: Response) => {
                var fileName = 'Import_service_template.xlsx';
                const file: Blob = new Blob([res.body], {type: 'application/xlsx'});
                saveAs(file, fileName);
                this.loading = false;
            }
        );
    }

    uploadExcel(event: any) {
        this.notiImportFile = null;
        var formData = new FormData();
        var file = event.files[0];
        formData.append('file', file);

        this._ServiceDataService.importService(formData).subscribe((res: Response) => {
            console.log(res.headers);
            var mess = res.headers.get('messageCode');
            if (mess === 'failFormat') {
                this.notiImportFile = 'Format file import không chính xác. Vui lòng kiểm tra lại.';
            }else if(mess === 'empty'){
                this.notiImportFile = 'File chưa có dữ liệu import.';
            }else if (mess === 'fail') {
                this.notiImportFile = 'Import không thành công. Tải về file kết quả để kiểm tra lại.';
                this.file = new Blob([res.body], {type: 'application/xlsx'});
                this.searchData();
            }else if (mess === 'success') {
                this.notiImportFile = 'Import thành công.';
                 this.file = new Blob([res.body], {type: 'application/xlsx'});
                this.searchData();
            } else {
                this.alertMessage('error', 'Cảnh báo', 'Import thất bại');
            }
            this.loading = false;
        });
    }

    exportImportResult(){
        var fileName = 'Import_service_template_result.xlsx';
        saveAs(this.file, fileName);
    }

    clearExcel(){
        this.notiImportFile = null;
    }

}
