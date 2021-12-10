import {Component, OnInit, OnDestroy, ViewChild, Renderer2, ElementRef} from '@angular/core';
import {trigger, state, style, transition, animate} from '@angular/animations';
import {ServiceDataService} from '../service-data.service';
import {MessageService, TreeNode, SelectItem} from 'primeng/components/common/api';
import {Condition} from '../Entity/ConditionReportService';
import {RegistrationService} from '../Entity/ListReportService';
import {saveAs} from 'file-saver';
import {MultiSelect} from 'primeng/multiselect';
import {AutoComplete} from 'primeng/autocomplete';
import {TokenStorage} from '../../shared/token.storage';
import {Title} from '@angular/platform-browser';
import {Router, NavigationEnd} from '@angular/router';
import {Table} from 'primeng/table';
import {UserInfo} from '../../shared/userInfo';
import {KitchenManagementService} from '../../kitchen-management/kitchen-management.service';
import {ConfirmationService} from 'primeng/api';
import {AppComponent} from "../../app.component";
import {DateTimeUtil} from "../../../common/datetime";
import {StatusIssuesService, StatusSignVoffice} from "../Entity/Status";
import {DatePipe} from "@angular/common";
import {noUndefined} from "@angular/compiler/src/util";
declare const require: any;
const pixelWidth = require('string-pixel-width');

@Component({
    selector: 'app-service-statistics',
    templateUrl: './service-statistics.component.html',
    styleUrls: ['./service-statistics.component.css'],
    providers: [TokenStorage],
    animations: [
        trigger('animation', [
            state('true', style({
                transform: 'translateY(0px)'
            })),
            state('false', style({
                opacity: 0,
                display: 'none',
                transform: 'translateY(10px)'
            })),
            transition('0 => 1', animate('190ms')),
            transition('1 => 0', animate('190ms'))
        ])
    ]
})
export class ServiceStatisticsComponent implements OnInit, OnDestroy {
    // tslint:disable-next-line:max-line-length
    onlyChar: RegExp = /^[0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;
    // tslint:disable-next-line:max-line-length
    notChar: RegExp = /[^0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;

    @ViewChild('myTable') private myTable: Table;
    @ViewChild('inputPlace') private inputPlace: AutoComplete;
    @ViewChild('inputPlaceSave') private inputPlaceSave: AutoComplete;
    @ViewChild('inputService') private inputService: AutoComplete;
    @ViewChild('inputServiceSave') private inputServiceSave: AutoComplete;
    @ViewChild('noteIssuesServiceF') private noteIssuesServiceF: ElementRef;
    @ViewChild('reasonPostponeF') private reasonPostponeF: ElementRef;
    @ViewChild('noteContentF') private noteContentF: ElementRef;
    @ViewChild('resonCantExecutiveF') private resonCantExecutiveF: ElementRef;
    @ViewChild('inputEmployeeManager') private inputEmployeeManager: AutoComplete;
    @ViewChild('inputEmployeeUnit') private inputEmployeeUnit: AutoComplete;
    @ViewChild('inputEmployeeOffice') private inputEmployeeOffice: AutoComplete;
    @ViewChild('myDrop') private myDrop: MultiSelect;
    @ViewChild('myDropDetail') private myDropDetail: MultiSelect;
    @ViewChild('myDropSysthetic') private myDropSysthetic: MultiSelect;

    navigationSubscription;
    isClick: boolean;
    uf: UserInfo;
    condition: Condition;
    listParentUnit: string[];
    listSelectedUnitId: any[] = [];
    checkedAll: boolean;
    isChecked: boolean;
    checkedAllApprove: boolean;
    checkedAllDetail: boolean;
    checkedAllSynthetic: boolean;
    isCheckedApprove: boolean;
    isCheckedDetail: boolean;
    isCheckedSynthetic: boolean;
    isReport: number;
    minDate: Date = new Date();

    listActivity: any;
    listIdIssuesServiceApprove: any = [];
    listIdIssuesServiceDetail: any = [];
    listIdIssuesServiceSynthetic: any = [];
    listIdIssuesServiceReport: any = [];
    count = 0;
    countApprove = 0;
    countItemApprove = 0;
    countDetail = 0;
    countItemDetail = 0;
    countSynthetic = 0;
    countItemSynthetic = 0;

    fromDate: Date;
    toDate: Date;
    vn = DateTimeUtil.vn;

    status: SelectItem[] = StatusIssuesService.IssuesService_STATUS;
    statusSysthetic: SelectItem[] = StatusSignVoffice.SIGNVOFFICE_STATUS;
    statusDetail: SelectItem[] = StatusSignVoffice.SIGNVOFFICE_STATUS;
    selectedStatus: any[] = [];
    selectedStatusDetail: any[] = [];
    selectedStatusSysthetic: any[] = [];
    len: number;
    maxLenStatus: number;
    sectors: TreeNode[];
    selectedSector: TreeNode[];
    filterPlace: any[];
    filterPlaceSave: any[];
    placeInfo: any;
    placeInfoSave: any;
    filterService: any[];
    filterServiceSave: any[];
    serviceInfo: any;
    serviceInfoSave: any;
    filterReceiver: any[];
    receiverInfo: any;
    employeeManagerInfo: any;
    employeeOfficeInfo: any;
    employeeUnitInfo: any;

    listRegisService: RegistrationService[];
    listStationery: any[];
    itemStationery: any;
    totalRecord: number;
    startRow: number;
    rowSize: number;
    averageRating: string;
    totalRecordStationery: number;
    startRowSta: number;
    rowSizeSta: number;

    isEmpty: boolean;
    loading: boolean;
    loading2: boolean;
    loading3: boolean;
    loadingTree = false;
    loadingReport = false;
    convertStatus: string[];
    convertStatusVoffice: string[];
    isManager: boolean;
    isEmployee: boolean;
    displayCreateExcel: boolean;
    //  for stationery
    displayStationery: boolean;
    displayAddReport: boolean;
    headerStationery: string;
    textShow: string;
    isDisplay: boolean;
    displayAddIssuesService: boolean;
    displayStartIssuesService: boolean;
    displayReasonRefuse: boolean;
    fullFillTime = null;

    filterEmployeeManager: any[];
    filterEmployeeUnit: any[];
    filterEmployeeOffice: any[];
    issuesServiceSave: any;
    noteIssuesService: any;
    noteContentReport: any;
    resultSave: any;

    statusShow = -1;
    statusRating = -1;
    userShowDetail: any;
    titleDetailIssuesService: any;
    issueServiceId: any;

    listSelectedStationery: any[];
    selectedReceiver: any[];

    reasonPostpone: any;
    noteReasonRefuseHandoverIssuesService: any;
    postponeDate: Date;
    resonCantExecutive: any;
    filterStationery: any[];
    quantity: any;

    displayHistoryIssuesService: any;
    listHistoryIssuesService: any[];

    ratting: any;
    feedback: any;
    isAdmin = false;

    isHcvp = false;
    isQl = false;
    isCvp = false;
    isHcdv = false;

    rattingRequest: any;
    feedBackRequest: any;
    rattingPerform: any;
    feedBackPerform: any;
    fromDateReport: any;
    toDateReport: any;
    listUnitIdReport: string[];
    isStationary: boolean;
    thisTimeNow: any;
  //  issueServiceId: any;


    constructor(private _ServiceDataService: ServiceDataService, private _KitchenManagementService: KitchenManagementService,
                private messageService: MessageService, private tokenStorage: TokenStorage, private confirmationService: ConfirmationService,
                private title: Title, private renderer: Renderer2, private router: Router, private app: AppComponent) {
        this.userShowDetail = this.uf = this.tokenStorage.getUserInfo();
        let array = this.uf.unitName.split("/");
        if(array.length > 0){
            this.userShowDetail.nameUnit=array[array.length-1];
        }
        this.navigationSubscription = this.router.events.subscribe(event => {
            if (!(event instanceof NavigationEnd)) {
                return;
            }
            // Do what you need to do here, for instance :
            this.resetButton();
            if (this.isClick) {
                this.ngOnInit();
                // this.ngAfterViewInit();
            }
        });
    }

    ngOnInit() {
        this.minDate = new Date();
        this.isClick = false;
        this.displayStationery = false;
        this.loading = true;
        this.loading2 = false;
        this.loading3 = true;
        this.len = 50;
        this.maxLenStatus = 431;
        this.startRow = -1;
        this.rowSize = 1;
        this.totalRecord = 0;
        this.isDisplay = false;
        this.displayAddReport = false;
        this.title.setTitle('Thống kê yêu cầu dịch vụ - PMQTVP');

        if (this.uf.role.includes('PMQT_ADMIN')) {
            this.isAdmin = true;
        }
        this.textShow = 'Chọn tất cả';
        this.setStatus();
        ///this.checkRole();
        this.listActivity = [];
        this.displayAddIssuesService = false;
        this.displayStartIssuesService = false;
        this.displayReasonRefuse = false;
        this.listSelectedStationery = [null];
        this.selectedReceiver = [];
        this.displayHistoryIssuesService = false;
        this.listHistoryIssuesService = [];
        this.ratting = 5;
        this.isReport=0;
        this.setRole();
    }

    setFromToDate(){
        if( new Date().getMonth()!=1){
            let d = new Date(new Date().getFullYear(), new Date().getMonth()-1, 0);
            if(new Date().getDate()<= d.getDate()){
                this.fromDate = new Date(new Date().setMonth(new Date().getMonth()-1));
            }else{
                this.fromDate = d;
            }
        }else{

            let d = new Date(new Date().getFullYear()-1, 12, 0);
            if(new Date().getDate()<= d.getDate()){
                this.fromDate = new Date(d.setDate(new Date().getDate()));
            }else{
                this.fromDate = d;
            }
        }


        this.toDate =  new Date(new Date().getTime()+(5*1000*60*60*24))
    }
        setRole(){
            if (this.uf.role.includes('PMQT_ADMIN')) {
                this.isAdmin = true;
            }
            if (this.uf.role.includes('PMQT_HCVP')) {
                this.isHcvp = true;
            }
            if (this.uf.role.includes('PMQT_QL')) {
                this.isQl = true;
            }
            if (this.uf.role.includes('PMQT_CVP')) {
                this.isCvp = true;
            }
            if (this.uf.role.includes('PMQT_HC_DV')) {
                this.isHcdv = true;
            }
        }
    //  check role user
    checkRole() {
        this.isEmployee = false;
        this.isManager = false;

        if (this.uf.role.includes('PMQT_ADMIN') || this.uf.role.includes('PMQT_HCVP')) {
            this.getSector(0);
        } else if (this.uf.role.includes('PMQT_QL') || this.uf.role.includes('PMQT_CVP') || this.uf.role.includes('PMQT_HC_DV')) {

            this.isManager = true;
            this.getSector(1);
        } else if (this.uf.role.includes('PMQT_HC_DV')) {
            this.isManager = true;
            this.getSector(2);
        } else {
            this.isEmployee = true;
            this.getSector(2);
        }
    }

    //  get list unit
    getSector(type: number) {
        this.listParentUnit = [];
        if (type === 0) {
            this._KitchenManagementService.getSectors(null).subscribe(data => {
                this.sectors = data;
                const parentId = [];
                this.sectors.forEach(value => {
                    if (value.data) {
                        parentId.push(value.data);
                    }
                });
                this.listParentUnit = parentId;
                this.searchData();
            });
        } else if (type === 1) {
            this._ServiceDataService.getListUnit(this.uf.unitId as string).subscribe(data => {
                this.sectors = [{
                    label: this.uf.unitName as string,
                    data: this.uf.unitId,
                    selectable: true,
                    children: data
                }];
                this.listParentUnit.push(this.uf.unitId as string);
                this.searchData();
            });
        } else if (type === 2) {
            this.sectors = [{
                label: this.uf.unitName as string,
                data: this.uf.unitId,
                selectable: false
            }];
            this.selectedSector = [this.sectors[0]];
            this.searchData();
        }
    }

    //  expand tree
    nodeExpand(event) {
        this.loadingTree = true;
        if (event.node && event.node.children !== undefined) {
            this.loadingTree = false;
            return;
        }
        if (event.node && event.node.children === undefined) {
            this._KitchenManagementService
                .getSectors(event.node.data).subscribe(
                data => {
                    event.node.children = data;
                    this.loadingTree = false;
                });
        }
    }

    //  when edit data in place
    changePlace() {
        this.clearService(null);
        const valPlace = this.inputPlace.el.nativeElement.children[0].children[0].value;
        if (this.notChar.test(valPlace)) {
            this.inputPlace.el.nativeElement.children[0].children[0].value = valPlace.replace(this.notChar, '');
        }
    }
    changePlaceSave() {
        this.clearServiceSave(null);
        const valPlace = this.inputPlaceSave.el.nativeElement.children[0].children[0].value;
        if (this.notChar.test(valPlace)) {
            this.inputPlaceSave.el.nativeElement.children[0].children[0].value = valPlace.replace(this.notChar, '');
        }
    }

    //  when edit data in service
    changeService() {
        this.fullFillTime = null;
        const valService = this.inputService.el.nativeElement.children[0].children[0].value;
        this.renderer.setAttribute(this.inputService.el.nativeElement.children[0].children[0], 'maxlength', '50');
        if (this.notChar.test(valService)) {
            this.inputService.el.nativeElement.children[0].children[0].value = valService.replace(this.notChar, '');
        }
    }
    changeServiceSave() {
        this.fullFillTime = null;
        const valService = this.inputServiceSave.el.nativeElement.children[0].children[0].value;
        this.renderer.setAttribute(this.inputServiceSave.el.nativeElement.children[0].children[0], 'maxlength', '50');
        if (this.notChar.test(valService)) {
            this.inputServiceSave.el.nativeElement.children[0].children[0].value = valService.replace(this.notChar, '');
        }
    }

    selectSeviceSave(event) {
        this.fullFillTime = event.fullFillTime;
    }

    //   when select fromDate field
    selectFromDate() {
        if (this.toDate && this.toDate < this.fromDate) {
            this.toDate = this.fromDate;
        }

        if(this.postponeDate) {
            if(this.postponeDate.getTime() < new Date().getTime()) this.postponeDate = new Date();
        }
    }

    //   when select toDate field
    selectToDate() {
        if (this.fromDate && this.toDate && this.fromDate > this.toDate) {
            this.fromDate = this.toDate;
        }
    }

    //  when click icon 'x' of from date
    clearFromDate(event) {
        if (this.fromDate) {
            event.stopPropagation();
            this.fromDate = null;
        }
    }

    //  when click icon 'x' of to date
    clearToDate(event) {
        if (this.toDate) {
            event.stopPropagation();
            this.toDate = null;
        }
    }

    //  when click 'x' of service
    clearService(event) {
        this.fullFillTime = null;
        if (this.serviceInfo) {
            if (event != null) {
                event.stopPropagation();
            }
            this.serviceInfo = null;
            this.loading2 = false;
        }
    }
    clearServiceSave(event) {
        this.fullFillTime = null;
        if (this.serviceInfoSave) {
            if (event != null) {
                event.stopPropagation();
            }
            this.serviceInfoSave = null;
            this.loading2 = false;
        }
    }

    clearPlace() {
        if (this.placeInfo) {
            this.placeInfo = null;
        }
    }
    clearPlaceSave() {
        if (this.placeInfoSave) {
            this.placeInfoSave = null;
        }
    }

    //  show suggestion when type in place
    loadPlace(event) {
        const object = {
            'placeName': event.query
        };
        this._ServiceDataService.getPlaces(object).subscribe(item => {
            this.filterPlace = item['data'];
        });
    }
    loadPlaceSave(event) {
        const object = {
            'placeName': event.query
        };
        this._ServiceDataService.getPlaces(object).subscribe(item => {
            this.filterPlaceSave = item['data'];
        });
    }

    //  show suggestion when type in service
    loadService(event) {
        let object = null;
        if (this.placeInfo && this.placeInfo.length > 0) {
            object = {
                'serviceName': event.query,
                'placeName': this.placeInfo
            };
        } else if (this.placeInfo && this.placeInfo['placeName']) {
            object = {
                'serviceName': event.query,
                'placeId': this.placeInfo['placeId']
            };
        } else {
            object = {
                'serviceName': event.query
            };
        }
        this.loading2 = false;
        this._ServiceDataService.getServices(object).subscribe(item => {
            this.filterService = item['data'];
            this.loading2 = true;
        });

    }
    loadServiceSave(event) {
        let object = null;
        if (this.placeInfoSave && this.placeInfoSave.length > 0) {
            object = {
                'serviceName': event.query,
                'placeName': this.placeInfoSave
            };
        } else if (this.placeInfoSave && this.placeInfoSave['placeName']) {
            object = {
                'serviceName': event.query,
                'placeId': this.placeInfoSave['placeId']
            };
        } else {
            object = {
                'serviceName': event.query
            };
        }
        this.loading2 = false;
        this._ServiceDataService.getServices(object).subscribe(item => {
            this.filterServiceSave = item['data'];
            this.loading2 = true;
        });

    }

    //  show suggestion when type in receiver
    loadReceiver(event) {
        const object = {
            'receiverName': event.query
        };

        this._ServiceDataService.getReceiversForSussgest(object).subscribe(item => {
            this.filterReceiver = item['data'];
        });
    }

    //  when change status
    onChange() {
        const str: string[] = [];
        const child = this.myDrop.el.nativeElement.children[0].children[1];
        this.len = 50 + pixelWidth(',...', {size: 16});
        if (this.selectedStatus != null
            && this.selectedStatus !== undefined
            && this.selectedStatus.length > 0) {
            for (let i = 0; i < this.selectedStatus.length; i++) {
                this.len += pixelWidth(this.status[this.selectedStatus[i]].label, {size: 16});
                if (this.len < this.maxLenStatus) {
                    if (i === 0) {
                        str.push(this.status[this.selectedStatus[i]].label);
                    } else {
                        str.push(' ' + this.status[this.selectedStatus[i]].label);
                    }
                }
            }

            if (this.len > this.maxLenStatus) {
                child.children[0].innerText = str.toString() + ',...';
            } else {
                child.children[0].innerText = str.toString();
            }
        }else {
                child.children[0].innerText = '-- Chọn trạng thái --';
        }
    }

    onChangeDetail() {
        const str: string[] = [];
        const child = this.myDropDetail.el.nativeElement.children[0].children[1];
        this.len = 50 + pixelWidth(',...', {size: 16});
        if (this.selectedStatusDetail != null
            && this.selectedStatusDetail !== undefined
            && this.selectedStatusDetail.length > 0) {
            for (let i = 0; i < this.selectedStatusDetail.length; i++) {
                this.len += pixelWidth(this.statusDetail[this.selectedStatusDetail[i]].label, {size: 16});
                if (this.len < this.maxLenStatus) {
                    if (i === 0) {
                        str.push(this.statusDetail[this.selectedStatusDetail[i]].label);
                    } else {
                        str.push(' ' + this.statusDetail[this.selectedStatusDetail[i]].label);
                    }
                }
            }

            if (this.len > this.maxLenStatus) {
                child.children[0].innerText = str.toString() + ',...';
            } else {
                child.children[0].innerText = str.toString();
            }
        }else {
            child.children[0].innerText = '-- Chọn trạng thái --';
        }
    }

    onChangeSysthetic() {
        const str: string[] = [];
        const child = this.myDropSysthetic.el.nativeElement.children[0].children[1];
        this.len = 50 + pixelWidth(',...', {size: 16});
        if (this.selectedStatusSysthetic != null
            && this.selectedStatusSysthetic !== undefined
            && this.selectedStatusSysthetic.length > 0) {
            for (let i = 0; i < this.selectedStatusSysthetic.length; i++) {
                this.len += pixelWidth(this.statusSysthetic[this.selectedStatusSysthetic[i]].label, {size: 16});
                if (this.len < this.maxLenStatus) {
                    if (i === 0) {
                        str.push(this.statusSysthetic[this.selectedStatusSysthetic[i]].label);
                    } else {
                        str.push(' ' + this.statusSysthetic[this.selectedStatusSysthetic[i]].label);
                    }
                }
            }

            if (this.len > this.maxLenStatus) {
                child.children[0].innerText = str.toString() + ',...';
            } else {
                child.children[0].innerText = str.toString();
            }
        }else {
            child.children[0].innerText = '-- Chọn trạng thái --';
        }
    }

    loadEmployeeManager(event) {
        let listRole=[];
        listRole.push('PMQT_QL');

        const object = {
            'role': listRole,
            'pattern': event.query,
            'pageNumber':0,
            'pageSize':20
        };
        this._ServiceDataService.getListEmployeeManager(object).subscribe(item => {
            this.filterEmployeeManager = item.data;
        });
    }

    changeEmployeeManager() {
        if (this.notChar.test(this.inputEmployeeManager.el.nativeElement.children[0].children[0].value)) {
            this.inputEmployeeManager.el.nativeElement.children[0].children[0].value = this.inputEmployeeManager.el.nativeElement.children[0].children[0].value.replace(this.notChar, '');
        }
    }

    clearEmployeeManager() {
        if (this.employeeManagerInfo) {
            this.employeeManagerInfo = null;
        }
    }

    loadEmployeeUnit(event) {
        let listRole=[];
        listRole.push('PMQT_QL');

        const object = {
            'role': listRole,
            'pattern': event.query,
            'pageNumber':0,
            'pageSize':20
        };
        this._ServiceDataService.getListEmployeeManager(object).subscribe(item => {
            this.filterEmployeeUnit = item.data;
        });
    }

    changeEmployeeUnit() {
        if (this.notChar.test(this.inputEmployeeUnit.el.nativeElement.children[0].children[0].value)) {
            this.inputEmployeeUnit.el.nativeElement.children[0].children[0].value = this.inputEmployeeUnit.el.nativeElement.children[0].children[0].value.replace(this.notChar, '');
        }
    }

    clearEmployeeUnit() {
        if (this.employeeUnitInfo) {
            this.employeeUnitInfo = null;
        }
    }


    loadEmployeeOffice(event) {
        let listRole=[];
        listRole.push('PMQT_CVP');

        const object = {
            'role': listRole,
            'pattern': event.query,
            'pageNumber':0,
            'pageSize':20
        };
        this._ServiceDataService.getListEmployeeManager(object).subscribe(item => {
            this.filterEmployeeOffice = item.data;
        });
    }

    changeEmployeeOffice() {
        if (this.notChar.test(this.inputEmployeeOffice.el.nativeElement.children[0].children[0].value)) {
            this.inputEmployeeOffice.el.nativeElement.children[0].children[0].value = this.inputEmployeeOffice.el.nativeElement.children[0].children[0].value.replace(this.notChar, '');
        }
    }

    clearEmployeeOffice() {
        if (this.employeeOfficeInfo) {
            this.employeeOfficeInfo = null;
        }
    }

    //  when click new page stationery
    paginateStationery(event) {
        const object = {
            'serviceId': this.itemStationery.issueServiceId,
            'startRow': event.first,
            'rowSize': 5
        };
        this.startRowSta = event.first;

        this.loading3 = true;
        this._ServiceDataService.getListStationery(object).subscribe(item => {
            this.listStationery = item['data'];
            this.rowSizeSta = this.listStationery.length;
            this.loading3 = false;
        });
    }

    // show stationery related issue service
    showStationery(itemIssueService: RegistrationService) {
        this.itemStationery = null;
        this.itemStationery = itemIssueService;
        this.headerStationery = 'VTTB cần sử dụng của yêu cầu ' + itemIssueService.issueServiceId;
        const object = {
            'serviceId': itemIssueService.issueServiceId,
            'startRow': 0,
            'rowSize': 5
        };
        this.displayStationery = false;
        this._ServiceDataService.countTotalRecordStationreyVTTB(object).subscribe(item1 => {
            this.totalRecordStationery = item1['data'];
            this._ServiceDataService.getListStationery(object).subscribe(item => {
                this.listStationery = item['data'];
                this.startRowSta = 0;
                if (this.listStationery && this.listStationery.length >= 5) {
                    this.rowSizeSta = 5;
                } else {
                    this.rowSizeSta = this.listStationery.length;
                }
                this.loading3 = false;
                this.displayStationery = true;
            });
        });
    }

    //  get data of node selected to export excel
    getNoteDataExportExcel(): any[] {
        this.listSelectedUnitId = [];
        if ((this.isManager || !this.isEmployee) && (this.selectedSector === null || this.selectedSector.length === 0)) {
            return this.listParentUnit;
        } else if (this.selectedSector != null) {
            for (const file of this.selectedSector) {
                this.listSelectedUnitId.push(file.data);
            }
        }
        return this.listSelectedUnitId.length > 0 ? this.listSelectedUnitId : null;
    }

    //  get data of node selected
    getSelectedNodeData(): any[] {
        this.listSelectedUnitId = [];
        if ((this.isManager || !this.isEmployee) && (this.selectedSector === null || this.selectedSector.length === 0)) {
            return this.listParentUnit;
        } else if (this.isEmployee) {
            this.listSelectedUnitId.push(this.uf.unitId);
        } else if (this.selectedSector != null) {
            for (const file of this.selectedSector) {
                this.listSelectedUnitId.push(file.data);
            }
        }
        return this.listSelectedUnitId.length > 0 ? this.listSelectedUnitId : null;
    }

    //  get condition to search
    getConditionSearch() {
        let placeid = null;
        let placename = null;
        let serviceid = null;
        let servicename = null;
        let receiverusername = null;
        let receivername = null;

        //  handle place
        if (this.placeInfo && this.placeInfo.length > 0) {
            placename = this.placeInfo;
        } else if (this.placeInfo && this.placeInfo['placeId']) {
            placeid = this.placeInfo['placeId'];
        }

        //  handle service
        if (this.serviceInfo && this.serviceInfo.length > 0) {
            servicename = this.serviceInfo;
        } else if (this.serviceInfo && this.serviceInfo['serviceId']) {
            serviceid = this.serviceInfo['serviceId'];
        }
        //  handle receiver
        if (this.receiverInfo && this.receiverInfo.length > 0) {
            receivername = this.receiverInfo;
        } else if (this.receiverInfo && this.receiverInfo['receiverUserName']) {
            receiverusername = this.receiverInfo['receiverUserName'];
        }
        this.condition = null;
        this.condition = {
            placeId: placeid,
            placeName: placename,
            listUnitId: this.getSelectedNodeData(),
            listStatus: this.selectedStatus,
            serviceId: serviceid,
            serviceName: servicename,
            receiverUserName: receiverusername,
            receiverName: receivername,
            startDate: this.fromDate,
            endDate: this.toDate,
            isStationary: this.isStationary ,
            startRow: 0,
            rowSize: 10,
            manager: (this.isManager || !this.isEmployee) && (this.selectedSector === null || this.selectedSector.length === 0) ? 1 : -1,
            listStatusDetail: this.selectedStatusDetail,
            listStatusSynthetic: this.selectedStatusSysthetic

        };

        this.fromDateReport = this.fromDate;
        this.toDateReport= this.toDate;
        this.listUnitIdReport = this.getSelectedNodeData();

    }

    //  get list registration service
    getListReport() {
        this.checkedAll = false;
        this.checkedAllApprove = false;
        this.listIdIssuesServiceApprove = [];
        this.countApprove=0;
        this.loading = true;
        /*    if (this.myTable !== undefined) {
         this.myTable.reset();
         }
         */
         console.log(this.condition);
        this._ServiceDataService.getReportRegisService(this.condition).subscribe(item => {
            if(this.statusRating == 1){
                this.setUpUserShowDetail(this.issueServiceId);
                this.displayStartIssuesService = true;
            }
            item['data'].listIssuesService.forEach((obj, index) => {
                obj.isRecei = 0;
                obj.isApprove = 0;
                if ((this.uf.userName === obj.userNameCVP && obj.flagCVP === 0 && obj.flagLDVV === 1 && obj.flagQLTT === 1) ||
                        (this.uf.userName === obj.userNameLDDV &&  obj.flagLDVV ===  0 && obj.flagQLTT === 1) ||
                            (this.uf.userName === obj.userNameQLTT && obj.flagQLTT === 0)) {
                                        obj.isApprove = 1;
                }
                let string = obj.userNameRecei;
                if (string !== null) {
                    string = string.split(',');
                    for (let i = 0; i < string.length; i++) {
                        if (this.uf.userName === string[i]) {
                            obj.isRecei = 1;
                        }
                    }
                }
            });
            this.totalRecord = item['data'].totalIssuesService;
            this.listRegisService = item['data'].listIssuesService;
            this.countItemApproveF();
            if (this.listRegisService && this.listRegisService.length >= 10) {
                this.rowSize = 10;
            } else {
                this.rowSize = this.listRegisService.length;
            }

            if (this.listRegisService === null || this.listRegisService.length === 0) {
                this.isEmpty = true;

                // if (this.isClick) {
                //   this.alertMessage('info', 'Thông báo', 'Không tìm thấy bản ghi tương ứng');
                // }
            } else {
                this.isEmpty = false;
            }

            for (let item of this.listRegisService) {
                if(this.listIdIssuesServiceSynthetic.includes(item.issueServiceId)) {
                    item.isCheckedSynthetic = true;
                }
            }

            for (let item of this.listRegisService) {
                if(this.listIdIssuesServiceDetail.includes(item.issueServiceId)) {
                    item.isCheckedDetail = true;
                }
            }

            this.isClick = true;
            const __this = this;
            setTimeout(function () {
                __this.loading = false;
                __this.checkedAllDetail = __this.checkSelection(14);
                __this.checkedAllSynthetic = __this.checkSelection(15);

            }, 100);
        });

    }

    //  when click new page
    paginate(event) {
        // this.loading = true;
        this.startRow = event.first;
        this.condition.startRow = event.first;
        this.getListReport();

        /*        this._ServiceDataService.getReportRegisService(this.condition).subscribe(item => {
         this.listRegisService = item['data'].listIssuesService;
         this.rowSize = this.listRegisService.length;
         this.loading = false;
         });*/
    }

    //  when click 'Tim kiem' button
    searchData() {
        this.listIdIssuesServiceSynthetic = [];
        this.listIdIssuesServiceDetail = [];
        this.getConditionSearch();
        if (this.myTable !== undefined) {
            this.myTable.reset();
        }
        this.startRow = 0;
        /*     this._ServiceDataService.countTotalRecordReportService(this.condition).subscribe(item => {
         this.totalRecord = item['data'].startRow;
         if (item['data'].placeId != null) {
         this.averageRating = item['data'].placeId;
         } else {
         this.averageRating = null;
         }

         });*/
        this.getListReport();
    }

    //  export excel report service
    exportToExcel() {
        const fileName = 'TKYCDV_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
            + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
            + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
        this.getConditionSearch();
        this.condition.startRow = -1;
        this.condition.rowSize = -1;
        this._ServiceDataService.countTotalRecordReportService(this.condition).subscribe(item => {
            if (item['data'].startRow <= 50000) {
                this._ServiceDataService.exportExcel(this.condition).subscribe(
                    (next: ArrayBuffer) => {
                        const file: Blob = new Blob([next], {type: 'application/xlsx'});
                        saveAs(file, fileName);
                    });
            } else {
                this.alertMessage('warn', 'Không thể xuất file', 'Số bản ghi tìm thấy vượt quá 50000');
            }
        });
    }

    //  export excel VTTB
    exportExcelVTTB() {
        const fileName = 'VTTB_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
            + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
            + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
        const object = {
            'listUnitId': this.getNoteDataExportExcel(),
            'startDate': this.fromDate,
            'endDate': this.toDate,
            'status': this.selectedStatus
        };
        this._ServiceDataService.exportExcelVTTB(object).subscribe(
            (next: ArrayBuffer) => {
                const file: Blob = new Blob([next], {type: 'application/xlsx'});
                saveAs(file, fileName);
            });
    }


    //  when click 'Lam moi' button
    resetButton() {
        this.placeInfo = null;
        this.placeInfoSave = null;
        this.setFromToDate();
        //this.fromDate = new Date();
        //this.toDate = new Date();
        this.selectedStatus = null;
        this.selectedStatusSysthetic = null;
        this.selectedStatusDetail = null;
        this.onChange();
        this.onChangeDetail();
        this.onChangeSysthetic();
        this.serviceInfo = null;
        this.serviceInfoSave = null;
        this.receiverInfo = null;
       // this.sectors=null;
        this.checkRole();
        this.selectedSector = null;
        this.searchData();
    }

    //  set up status
    setStatus() {
        this.convertStatus = StatusIssuesService.STATUS;
        this.convertStatusVoffice = StatusSignVoffice.STATUS;
    }

    //  alert message
    alertMessage(severity: string, summary: string, detail: string) {
        this.messageService.add({
            severity: severity,
            summary: summary,
            detail: detail
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

    public checkedAllItem(event) {

        if (this.checkedAll) {
            this.count = 0;
            event.forEach(element => {
                element.isChecked = false;
                const index = this.listActivity.indexOf(element);
                this.listActivity.splice(index, 1);
            });
        } else {
            this.count = this.listRegisService.length;
            const listTemp = Object.assign([], this.listActivity);
            event.forEach(element => {
                element.isChecked = true;
                let isExists = false;
                for (let i = 0; i < listTemp.length; i++) {
                    if (listTemp[i].issueServiceId === element.issueServiceId) {
                        isExists = true;
                    }
                }
                if (!isExists) {
                    this.listActivity.push(element);
                }
            });

        }
    }

    public checkedItem(item) {


        if (item.isChecked) {
            this.count--;
            const index = this.listActivity.indexOf(item);
            this.listActivity.splice(index, 1);
            this.checkedAll = false;
        } else {
            this.count++;
            if (this.count == this.listRegisService.length) {
                this.checkedAll = true;
            }
            this.listActivity.push(item);
        }
    }

    public countItemApproveF() {
        this.countItemApprove = 0;
        this.listRegisService.forEach(element => {
            if (element.status == 0) {
                this.countItemApprove++;
            }

        });

        this.countItemDetail = 0;
        this.listRegisService.forEach(element => {
            if (element.statusDetail == 0) {
                this.countItemDetail++;
            }

        });
        this.countItemSynthetic = 0;
        this.listRegisService.forEach(element => {
            if (element.statusSynthetic == 0) {
                this.countItemSynthetic++;
            }

        });
    }

    public checkedAllItemApprove(event) {
        if (!this.checkedAllApprove) {
            this.countApprove = 0;
            event.forEach(element => {
                if (element.status == 0) {
                    element.isCheckedApprove = false;
                    const index = this.listIdIssuesServiceApprove.indexOf(element);
                    this.listIdIssuesServiceApprove.splice(index, 1);
                }

            });
        } else {
            this.countApprove = this.countItemApprove;
            const listTemp = Object.assign([], this.listIdIssuesServiceApprove);
            event.forEach(element => {
                if (element.status == 0) {
                    element.isCheckedApprove = true;
                    let isExists = false;
                    for (let i = 0; i < listTemp.length; i++) {
                        if (listTemp[i] === element.issueServiceId) {
                            isExists = true;
                        }
                    }
                    if (!isExists) {
                        this.listIdIssuesServiceApprove.push(element.issueServiceId);
                    }
                }
            });

        }
    }

    public checkedItemApprove(item) {

        if (!item.isCheckedApprove) {
            this.countApprove--;
            const index = this.listIdIssuesServiceApprove.indexOf(item.issueServiceId);
            this.listIdIssuesServiceApprove.splice(index, 1);
            this.checkedAllApprove = false;
        } else {
            this.countApprove++;
            if (this.countApprove == this.countItemApprove) {
                this.checkedAllApprove = true;
            }
            this.listIdIssuesServiceApprove.push(item.issueServiceId);
        }
    }

    public checkedAllItemDetail(event) {
        if (!this.checkedAllDetail) {
            this.countDetail = 0;
            event.forEach(element => {
                if ((element.statusDetail == 0 || element.statusDetail == 5 || element.statusDetail == 4)&& (element.status ==6 || element.status ==8 || element.status ==9)) {
                    element.isCheckedDetail = false;
                    const index = this.listIdIssuesServiceDetail.indexOf(element);
                    this.listIdIssuesServiceDetail.splice(index, 1);
                }

            });
        } else {
            this.countDetail = this.countItemDetail;
            const listTemp = Object.assign([], this.listIdIssuesServiceDetail);
            event.forEach(element => {
                if ((element.statusDetail == 0 || element.statusDetail == 5 || element.statusDetail == 4)&& (element.status ==6 || element.status ==8 || element.status ==9)) {
                    element.isCheckedDetail = true;
                    let isExists = false;
                    for (let i = 0; i < listTemp.length; i++) {
                        if (listTemp[i] === element.issueServiceId) {
                            isExists = true;
                        }
                    }
                    if (!isExists) {
                        this.listIdIssuesServiceDetail.push(element.issueServiceId);
                    }
                }
            });

        }
    }

    public checkedItemDetail(item) {
        if (!item.isCheckedDetail) {
            this.countDetail--;
            const index = this.listIdIssuesServiceDetail.indexOf(item.issueServiceId);
            this.listIdIssuesServiceDetail.splice(index, 1);
            // this.checkedAllDetail = false;
        } else {
            this.countDetail++;
            // if (this.countDetail == this.countItemDetail) {
            //     this.checkedAllDetail = true;
            // }
            this.listIdIssuesServiceDetail.push(item.issueServiceId);
        }
        const __this = this;
        setTimeout(function () {
            __this.checkedAllDetail = __this.checkSelection(14);
            // __this.checkedAllSynthetic = __this.checkSelection(15);
        }, 100);
    }
    public checkedAllItemSynthetic(event) {
        if (!this.checkedAllSynthetic) {
            this.countSynthetic = 0;
            event.forEach(element => {
                if ((element.statusSynthetic ==0 || element.statusSynthetic ==5|| element.statusSynthetic ==4)&& (element.status ==6 || element.status ==8 || element.status ==9)) {
                    element.isCheckedSynthetic = false;
                    const index = this.listIdIssuesServiceSynthetic.indexOf(element);
                    this.listIdIssuesServiceSynthetic.splice(index, 1);
                }

            });
        } else {
            this.countSynthetic = this.countItemDetail;
            const listTemp = Object.assign([], this.listIdIssuesServiceSynthetic);
            event.forEach(element => {
                if ((element.statusSynthetic ==0 || element.statusSynthetic ==5 || element.statusSynthetic ==4)&& (element.status ==6 || element.status ==8 || element.status ==9)) {
                    element.isCheckedSynthetic = true;
                    let isExists = false;
                    for (let i = 0; i < listTemp.length; i++) {
                        if (listTemp[i] === element.issueServiceId) {
                            isExists = true;
                        }
                    }
                    if (!isExists) {
                        this.listIdIssuesServiceSynthetic.push(element.issueServiceId);
                    }
                }
            });

        }
    }

    public checkedItemSynthetic(item) {
        if (!item.isCheckedSynthetic) {
            this.countSynthetic--;
            const index = this.listIdIssuesServiceSynthetic.indexOf(item.issueServiceId);
            this.listIdIssuesServiceSynthetic.splice(index, 1);
            // this.checkedAllSynthetic = false;
        } else {
            this.countSynthetic++;
            // if (this.countSynthetic  == this.countItemDetail) {
            //     this.checkedAllSynthetic = true;
            // }
            this.listIdIssuesServiceSynthetic.push(item.issueServiceId);
        }
        const __this = this;
        setTimeout(function () {
            // __this.checkedAllDetail = __this.checkSelection(14);
            __this.checkedAllSynthetic = __this.checkSelection(15);
        }, 100);
    }

    setUpUserShowDetail(issuedServiceId, callback?: any) {
        if (this.statusShow == 0) {
            this.userShowDetail = this.uf;
            this.displayAddIssuesService = true;
        } else {
            /* else if (this.statusShow == 1 || this.statusShow == 7 || this.statusShow == 3 || this.statusShow == 8 || this.statusShow == 10 || this.statusShow == 11 || this.statusShow == 12) {*/
            const object = {
                'issuedServiceId': issuedServiceId
            };

            this._ServiceDataService.finDatailIssueseService(object).subscribe(response => {
                this.statusRating =-1;
                this.userShowDetail = response.data;
                this.placeInfoSave = {
                    placeName: this.userShowDetail.namePlace,
                    // placeId: this.userShowDetail.issueseLocation
                };
                this.serviceInfoSave = {
                    serviceName: this.userShowDetail.nameService,
                    serviceId: this.userShowDetail.idService,
                    requireSign: this.userShowDetail.requireSign
                };
                this.employeeManagerInfo = {
                    fullNameEmployee: (this.userShowDetail.nameQLTT == null) ? ' ' : this.userShowDetail.nameQLTT
                };
                this.employeeUnitInfo = {
                    fullNameEmployee: (this.userShowDetail.nameLDDV == null) ? ' ' : this.userShowDetail.nameLDDV
                };
                this.employeeOfficeInfo = {
                    fullNameEmployee: (this.userShowDetail.nameCVP == null) ? ' ' : this.userShowDetail.nameCVP
                };
                //this.listSelectedStationery = response.data.statonery;
               // this.fullFillTime = this.userShowDetail.fullFillTime;
                this.noteIssuesService = this.userShowDetail.note;
                this.rattingRequest = this.userShowDetail.rating;
                this.feedBackRequest = this.userShowDetail.feedback;
                this.rattingPerform = this.userShowDetail.rattingToUser;
                this.feedBackPerform = this.userShowDetail.feedBackToUser;
                if(this.userShowDetail.status == 4){
                    if(this.userShowDetail.postponeReason){
                        this.reasonPostpone = this.userShowDetail.postponeReason;
                    }
                    if(this.userShowDetail.postponeToTime){
                        this.postponeDate = new Date(this.userShowDetail.postponeToTime);
                    }
                }

                if ( this.statusShow === 10 || this.statusShow === 11 ) {
                    this.listSelectedStationery = this.userShowDetail.statonery;
                }
                this.thisTimeNow = this.userShowDetail.realTimeTotal;
               /* if(this.userShowDetail.status == 1){
                    this.thisTimeNow =((new Date().getTime() - this.userShowDetail.timeStartPlan)/(60*60*1000)).toFixed(1);
                }else if(this.userShowDetail.status == 0||this.userShowDetail.status == 2){
                    this.thisTimeNow =0;
                }else if(this.userShowDetail.status == 3 ){
                    this.thisTimeNow =(((new Date().getTime() - this.userShowDetail.timeResume))/(60*60*1000)+ (this.userShowDetail.totalHourActual/1000)).toFixed(1);
                }else{
                    this.thisTimeNow =(this.userShowDetail.totalHourActual/1000).toFixed(1);
                }*/
                this.fullFillTime = this.thisTimeNow.toFixed(2)+'/' + this.userShowDetail.fullFillTime;
                if(this.statusShow === 10 || this.statusShow === 11){
                    this.displayStartIssuesService = true;
                }

                if(callback) callback();

            });
        }
    }

    showAddForm() {
        this.statusShow = 0;
        this.titleDetailIssuesService = 'THÊM MỚI YÊU CẦU DỊCH VỤ';
        this.clearPlaceSave();
        this.clearServiceSave(null);
        this.setUpUserShowDetail(null);
        this.userShowDetail.status = 10;
        this.displayAddIssuesService = true;
    }

    cancelShowAddForm() {
        this.clearPlaceSave();
        this.clearServiceSave(null);
        this.noteIssuesService = null;
        this.clearEmployeeManager();
        this.clearEmployeeUnit();
        this.clearEmployeeOffice();
        this.reasonPostpone = null;
        this.postponeDate = null;
    }

    getIssuesServiceSave(): boolean {
        let placeid = null;
        let serviceid = null;
        let note = null;
        let appoverQltt = null;
        let appoverLddv = null;
        let appoverCvp = null;

        if ((this.placeInfoSave && this.placeInfoSave.length > 0) || !this.placeInfoSave) {
            this.inputPlaceSave.domHandler.findSingle(this.inputPlaceSave.el.nativeElement, 'input').focus();
            this.app.showWarn('Vị trí không được để trống');
            return false;
        } else if (this.placeInfoSave && this.placeInfoSave['placeId']) {
            placeid = this.placeInfoSave['placeId'];
        }

        //  handle service
        if ((this.serviceInfoSave && this.serviceInfoSave.length > 0) || !this.serviceInfoSave) {
            this.inputServiceSave.domHandler.findSingle(this.inputServiceSave.el.nativeElement, 'input').focus();
            this.app.showWarn('Dịch vụ không được để trống');
            return false;
        } else if (this.serviceInfoSave && this.serviceInfoSave['serviceId']) {
            serviceid = this.serviceInfoSave['serviceId'];
 }

        if (this.noteIssuesService !== null && this.noteIssuesService !== undefined && this.noteIssuesService.trim() !== '') {
            note = this.noteIssuesService;
        } else {
            this.noteIssuesServiceF.nativeElement.focus();
            this.app.showWarn('Tình trạng không được để trống');
            return false;
        }
        if (((this.employeeManagerInfo && this.employeeManagerInfo.length > 0) || !this.employeeManagerInfo) && (this.serviceInfoSave !== null && this.serviceInfoSave.requireSign === 1)) {
            this.inputEmployeeManager.domHandler.findSingle(this.inputEmployeeManager.el.nativeElement, 'input').focus();
            this.app.showWarn('Quản lý trực tiếp không được để trống');
            return false;
        } else if (this.employeeManagerInfo && this.employeeManagerInfo['userName']) {
            appoverQltt = this.employeeManagerInfo['userName'];
        }

        if (this.employeeUnitInfo && this.employeeUnitInfo.length > 0) {
        } else if (this.employeeUnitInfo && this.employeeUnitInfo['userName']) {
            appoverLddv = this.employeeUnitInfo['userName'];
        }

        if(appoverQltt==appoverLddv && appoverQltt!=null){
            this.inputEmployeeManager.domHandler.findSingle(this.inputEmployeeManager.el.nativeElement, 'input').focus();
            this.app.showWarn('Không được chọn trùng quản lý');
            return false;
        }

        if (this.employeeOfficeInfo && this.employeeOfficeInfo.length > 0) {
        } else if (this.employeeOfficeInfo && this.employeeOfficeInfo['userName']) {
            appoverCvp = this.employeeOfficeInfo['userName'];
        }

        if(appoverQltt==appoverCvp && appoverQltt!=null){
            this.inputEmployeeManager.domHandler.findSingle(this.inputEmployeeManager.el.nativeElement, 'input').focus();
            this.app.showWarn('Không được chọn trùng quản lý');
            return false;
        }

        this.issuesServiceSave = null;
        this.issuesServiceSave = {
            issueseLocation: placeid,
            serviceId: serviceid,
            note: note,
            fullFillTime: this.fullFillTime,
            appoverQltt: appoverQltt,
            appoverLddv: appoverLddv,
            appoverCvp: appoverCvp
        };
        return true;
    }

    saveIssuesService() {
        if (this.getIssuesServiceSave() === false) {
            return;
        }
        this._ServiceDataService.insertIssuesService(this.issuesServiceSave).subscribe(item => {
            this.resultSave = item.status;
            if(item.status == 8){
                this.displayAddIssuesService = false;
                this.app.showWarn("Nhân viên cần hoàn thành tất cả các yêu cầu trước đó trước khi tạo yêu cầu mới")
                this.resetButton();
            } else if (item.status == 1) {
                this.displayAddIssuesService = false;
                this.app.showSuccess('Đã gửi yêu cầu sử dụng dịch vụ');
                this.resetButton();
            }
        });
    }

    approveIssuedService() {
        if(this.listIdIssuesServiceApprove.length == 0){
            this.app.showWarn('Chưa chọn bản ghi nào');
        }else if (this.listIdIssuesServiceApprove.length == 1) {
            this.statusShow = 1;
            this.titleDetailIssuesService = 'PHÊ DUYỆT YÊU CẦU DỊCH VỤ';
            // let index = this.listRegisService.find(x => x.issueServiceId == this.listIdIssuesServiceApprove[0]);
            this.setUpUserShowDetail(this.listIdIssuesServiceApprove[0]);
            this.displayAddIssuesService = true;

        } else if (this.listIdIssuesServiceApprove.length > 1) {
            this.confirmationService.confirm({
                message: 'Đ/c có chắc chắn muốn thực hiện?',
                header: 'Xác nhận phê duyệt',
                icon: 'pi pi-info-circle',
                acceptLabel: 'Đồng ý',
                rejectLabel: 'Hủy bỏ',
                accept: () => {
                    const object = {
                        'flagAppove': 1,
                        'issuedServiceId': this.listIdIssuesServiceApprove
                    };
                    this._ServiceDataService.getListIdIssuesService(object).subscribe(item => {
                        if(item.status==1){
                            this.app.showSuccess('Đã phê duyệt các yêu cầu được lựa chọn');
                            this.resetButton();
                        }
                    });
                },
                reject: () => {
                }
            });


        }
    }

    approveDetailIssuesService() {
        let listIdIssuesServiceApprove=[];
        listIdIssuesServiceApprove.push(this.userShowDetail.issuedServiceId);
        const object = {
            'flagAppove': 1,
            'issuedServiceId': listIdIssuesServiceApprove
        };
        this._ServiceDataService.getListIdIssuesService(object).subscribe(item => {
            if(item.status == 9 ) {
                this.displayAddIssuesService = false;
                this.app.errorValidateDate('Yêu cầu này không phê duyệt được');
            } else if( item.status == 1 ) {
                this.displayAddIssuesService = false;
                this.app.showSuccess('Đã phê duyệt yêu cầu');
                this.resetButton();
            } else {
                //this.displayAddIssuesService = false;
                this.app.errorValidateDate('Phê duyệt yêu cầu thất bại');
            }
        });
    }

    showRefuseIssuesService(item) {
        this.statusShow = 1;
        this.titleDetailIssuesService = 'Phê duyệt yêu cầu dịch vụ';
        // let index = this.listRegisService.find(x => x.issueServiceId == this.listIdIssuesServiceApprove[0]);
        this.setUpUserShowDetail(item.issueServiceId);
        //this.listIdIssuesServiceApprove.push(this.userShowDetail.issuedServiceId);
        this.displayAddIssuesService = true;
    }

    refuseDetailIssuesService() {
        this.displayReasonRefuse= true;

    }

    showCanncelIssuesService(item) {
        this.statusShow = 7;
        this.titleDetailIssuesService = 'CHI TIẾT YÊU CẦU DỊCH VỤ';

        this.setUpUserShowDetail(item.issueServiceId);
        this.issueServiceId = item.issueServiceId;
        this.displayAddIssuesService = true;
    }

    cancelIssuesService() {
        const object = {
            'flag': 7,
            'issuedServiceId': this.issueServiceId
        };
        this._ServiceDataService.updateHandoverIssuesService(object).subscribe(item => {
            if(item.status == 9 ){
                this.displayAddIssuesService = false;
                this.app.errorValidateDate('Yêu cầu này không thể hủy');
            }else if( item.status == 1 ){
                this.displayAddIssuesService = false;
                this.app.showSuccess('Đã hủy yêu cầu dịch vụ');
                this.resetButton();
            }
        });

    }

    showStartIssuesService(item) {
        this.titleDetailIssuesService ='XỬ LÝ YÊU CẦU DỊCH VỤ';
        this.statusShow = 3;

        this.issueServiceId = item.issueServiceId;
        this.listSelectedStationery = [null];

        let component = this;
        let callback = () => {
          this.displayStartIssuesService = true;
        }
        this.setUpUserShowDetail(item.issueServiceId, callback);
    }

    startIssuesService() {
            let status =0;
            if((this.userShowDetail.status == 1)){
                status =1;
            }
        const object = {
            'flagExecutive': 3,
            'issuedServiceId': this.issueServiceId,
            'statusStart': status
        };
        this._ServiceDataService.updateIssuesService(object).subscribe(item => {
            if(item.status == 9 ){
               // this.displayStartIssuesService = false;
                this.app.errorValidateDate('Tiếp tục yêu cầu thất bại');
            }else if( item.status == 1 ){
                //this.displayStartIssuesService = false;
                this.setUpUserShowDetail(this.issueServiceId);
                this.app.showSuccess('Đã bắt đầu xử lý yêu cầu');
                this.postponeDate = null;
                this.reasonPostpone = null;
                this.resetButton();
            }

        });
    }

    postponeIssuesService() {
        if(this.reasonPostpone == null || this.reasonPostpone.trim()=='' || this.reasonPostpone == undefined){
            this.app.showWarn('Lý do hoãn thực hiện không được để trống');
            this.reasonPostponeF.nativeElement.focus();
            return;
        }

        if(!this.postponeDate){
            this.app.showWarn('Thời gian hẹn sau không được để trống');
            return;
        }

        const object = {
            'flagExecutive': 4,
            'issuedServiceId': this.issueServiceId,
            'resonPostponeExecutive': this.reasonPostpone,
            'datePostpone': this.postponeDate
        };
        this._ServiceDataService.updateIssuesService(object).subscribe(item => {
            if(item.status == 9 ){
                this.displayStartIssuesService = false;
                this.app.errorValidateDate('Yêu cầu này không thể hoãn thực hiện');
            }else if( item.status == 1 ){
                this.displayStartIssuesService = false;
                this.app.showSuccess('Hoãn thực hiện yêu cầu thành công');
                this.resetButton();
            }
        });
        this.reasonPostpone = null;
        this.postponeDate = null;

    }

    cantExecutiveIssuesService() {
        if(this.resonCantExecutive == null || this.resonCantExecutive.trim() =='' || this.resonCantExecutive == undefined){
            this.app.showWarn('Lý do không thực hiện được không được để trống');
            this.resonCantExecutiveF.nativeElement.focus();
            return;
        }
        const object = {
            'flagExecutive': 5,
            'issuedServiceId': this.issueServiceId,
            'resonCantExecutive': this.resonCantExecutive,
        };
        this._ServiceDataService.updateIssuesService(object).subscribe(item => {
            if(item.status == 9 ){
                this.displayStartIssuesService = false;
                this.app.errorValidateDate('Yêu cầu này không thể từ chối thực hiện');
            }else if( item.status == 1 ){
                this.displayStartIssuesService = false;
                this.app.showSuccess('Đã từ chối đáp ứng yêu cầu');
                this.resetButton();

                this.titleDetailIssuesService ="ĐÁNH GIÁ NGƯỜI YÊU CẦU"
                this.statusShow = 11;
                this.resetButton();
                //this.userShowDetail = item;
                // this.issueServiceId = this.issueServiceId;
                this.setUpUserShowDetail(this.issueServiceId);
                this.displayStartIssuesService = true;
            }
        });
        this.resonCantExecutive = null;
    }

    removeTextbox(index: number) {
        this.listSelectedStationery.splice(index, 1);
        // this.selectedReceiver.splice(index, 1);
        //  this.listReceiverRemove.splice(index, 1);
    }

    //  when add textbox receiver
    addTextbox() {
        this.listSelectedStationery.push(null);
        //this.listReceiverRemove.push(null);
    }

    loadStationery(event) {
        const object = {
            'pattern': event.query,
            'masterCode': 'S009'
        };
        this._ServiceDataService.getStationerysForSussgest(object).subscribe(item => {
            this.filterStationery = item['data'];
        });
    }

    selectStationery(index: number, item) {
        let count: number = 0;
        if (this.listSelectedStationery && this.listSelectedStationery.length > 0) {
            for (let i = 0; i < this.listSelectedStationery.length; i++) {
                if (item['stationeryId'] === this.listSelectedStationery[i].stationeryId) {
                    count++;
                }
            }
        }

        if(count == 2) {
            this.listSelectedStationery.splice(index, 1);
            this.app.showWarn('Không được chọn trùng vật tư thiết bị');
        } else {
            item.stationeryQuantity = 1;
            this.listSelectedStationery[index] = item;
        }
    }

    changeStationery(index: number) {
        this.listSelectedStationery[index] = null;
    }

    succesIssuesService() {
        let index = -2;
        while (index !== -1) {
            index = this.listSelectedStationery.indexOf(null);
            if (index !== -1) {
                this.listSelectedStationery.splice(index, 1);
            }
        }
        if(this.listSelectedStationery.length >0 ){
            let count=0;
            this.listSelectedStationery.forEach(element => {
                if(+element.stationeryQuantity>9999999 || +element.stationeryQuantity<0){
                    this.app.errorValidateDate('Số lượng vượt quá giá trị cho phép hoạc nhỏ hơn 0');
                    count++;
                    return;
                }
            });
            if(count > 0){
                return;
            }
        }
        const object = {
            'flagExecutive': 6,
            'issuedServiceId': this.issueServiceId,
            'stationery': this.listSelectedStationery
        };
        this._ServiceDataService.updateIssuesService(object).subscribe(item => {
            if(item.status == 9 ){
                this.displayStartIssuesService = false;
                this.app.errorValidateDate('Yêu cầu này không thể hoàn thành');
            }else if( item.status == 1 ){
                this.displayStartIssuesService = false;
                this.app.showSuccess('Đã hoàn thành xử lý yêu cầu');

                this.titleDetailIssuesService ="ĐÁNH GIÁ NGƯỜI YÊU CẦU"
                this.statusShow = 11;
                this.statusRating = 1;
                this.resetButton();
                //this.userShowDetail = item;
               // this.issueServiceId = this.issueServiceId;
/*                this.setUpUserShowDetail(this.issueServiceId);
                this.displayStartIssuesService = true;*/
               //
            }
        });
    }

    cancelStartIssuesService() {
        this.reasonPostpone = null;
        this.postponeDate = null;
        this.resonCantExecutive = null;
        this.ratting = 5;
        this.feedback= null;
        this.employeeManagerInfo = null;
        this.employeeUnitInfo = null;
        this.employeeOfficeInfo = null;
        this.noteIssuesService = null;
        //this.clearPlace();
        //this.clearService(null);
    }

    showApproveHandoverIssuesService(item) {
        this.titleDetailIssuesService ="CHI TIẾT YÊU CẦU DỊCH VỤ";
        this.statusShow = 8;
        this.setUpUserShowDetail(item.issueServiceId);
        this.displayAddIssuesService = true;
        this.issueServiceId = item.issueServiceId;
    }

    changeQuantity() {
        if (+this.quantity < 1) {
            this.quantity = 1;
        } else if (+this.quantity > 9999999) {
            this.quantity = 9999999;
        }
    }

    closeDetailIssuesService() {
        this.displayAddIssuesService = false;
    }

    refuseHandoverIssuesService() {
        this.displayReasonRefuse = true;
    }

    approveHandoverIssuesService() {
        const object = {
            'flag': 8,
            'issuedServiceId': this.issueServiceId
        };
        this._ServiceDataService.updateHandoverIssuesService(object).subscribe(item => {
            if(item.status == 9 ){
                this.displayAddIssuesService = false;
                this.app.errorValidateDate('Yêu cầu này không thể Tiếp nhận bàn giao');
            }else if( item.status == 1 ){
                this.displayAddIssuesService = false;
                this.app.showSuccess('Đã hoàn thành xử lý yêu cầu');

                this.statusShow = 10;
                this.setUpUserShowDetail(this.issueServiceId);
                this.titleDetailIssuesService = 'ĐÁNH GIÁ DỊCH VỤ ĐÃ HOÀN THÀNH';


                this.resetButton();

            }
        });
    }

    canncelRefuseHandoverIssuesService() {
        this.displayReasonRefuse = false;
    }

    confirmRefuseHandoverIssuesService() {
        this.displayAddIssuesService = false;
        this.displayReasonRefuse = false;
        if(this.statusShow==8){
            const object = {
                'flag': 9,
                'issuedServiceId': this.issueServiceId,
                'userReason': this.noteReasonRefuseHandoverIssuesService,
            };
            this._ServiceDataService.updateHandoverIssuesService(object).subscribe(item => {
                if(item.status == 1 ){
                    this.app.showSuccess("Đã từ chối phê duyệt yêu cầu")
                    this.resetButton();
                    this.resetButton();
                    this.statusShow = 10;
                    this.setUpUserShowDetail(this.issueServiceId);
                    this.displayStartIssuesService = true;
                    this.titleDetailIssuesService = 'ĐÁNH GIÁ DỊCH VỤ';
                }
            });
            this.noteReasonRefuseHandoverIssuesService=null;
        }else if(this.statusShow==1){
            let listIdIssuesServiceApprove=[];
            listIdIssuesServiceApprove.push(this.userShowDetail.issuedServiceId);
            const object = {
                'flagAppove': 2,
                'issuedServiceId': listIdIssuesServiceApprove,
                'resonRefuse': this.noteReasonRefuseHandoverIssuesService,
            };
            this._ServiceDataService.getListIdIssuesService(object).subscribe(item => {
                if(item.status == 9 ){
                    this.displayAddIssuesService = false;
                    this.app.errorValidateDate('Yêu cầu này không thể từ chối');
                }else if( item.status == 1 ){
                    this.displayAddIssuesService = false;
                    this.app.showSuccess('Đã từ chối phê duyệt yêu cầu');
                    this.resetButton();
                }
            });
            this.noteReasonRefuseHandoverIssuesService=null;
        }

    }

    cancelShowReasonRefuse(){
        this.noteReasonRefuseHandoverIssuesService=null;
    }

    showHistoryIssueseSerive() {
        const object = {
            'serviceId': this.userShowDetail.idService,
            'issuesUsername': this.userShowDetail.issuesUserName,
        };
        this._ServiceDataService.historyIssuesService(object).subscribe(item => {
            this.listHistoryIssuesService = item['data'];
        });
        this.displayHistoryIssuesService = true;
    }

    showDetailIssuesService(item) {
        this.titleDetailIssuesService="CHI TIẾT YÊU CẦU DỊCH VỤ";
        this.statusShow = 12;
        this.userShowDetail = item;
        this.issueServiceId = item.issueServiceId;
        this.setUpUserShowDetail(item.issueServiceId);
        this.displayAddIssuesService = true;
    }

    showRattingPerform(item) {
        this.titleDetailIssuesService ="ĐÁNH GIÁ DỊCH VỤ";
        this.statusShow = 10;
        this.userShowDetail = item;
        this.issueServiceId = item.issueServiceId;
        this.setUpUserShowDetail(item.issueServiceId);
        this.displayStartIssuesService = true;

    }

    showRattingRequest(item) {
        this.titleDetailIssuesService ="ĐÁNH GIÁ NGƯỜI YÊU CẦU";
        this.statusShow = 11;
        this.userShowDetail = item;
        this.issueServiceId = item.issueServiceId;
        this.setUpUserShowDetail(item.issueServiceId);

    }

    evaluateIssuesService() {
        this.displayAddIssuesService = false;
        this.displayReasonRefuse = false;

        if (this.statusShow == 10) {
            const object = {
                'issuedServiceId': this.issueServiceId,
                'flag': 10,
                'ratting': this.ratting,
                'feedBack': this.feedback,
            };
            this._ServiceDataService.updateHandoverIssuesService(object).subscribe(item => {
                if(item.status==1){
                    this.app.showSuccess('Cảm ơn đồng chí đã dành thời gian phản hồi');
                    this.displayStartIssuesService = false;
                    this.ratting = 5;
                    this.feedback=null;
                    this.resetButton();
                }

            });
        } else if (this.statusShow == 11) {
            const object = {
                'issuedServiceId': this.issueServiceId,
                'flag': 11,
                'rattingToUser': this.ratting,
                'feedBackToUser': this.feedback,
            };
            this._ServiceDataService.updateHandoverIssuesService(object).subscribe(item => {
                if(item.status==1){
                    this.app.showSuccess('Cảm ơn đồng chí đã dành thời gian phản hồi');
                    this.displayStartIssuesService = false;
                    this.ratting = 5;
                    this.feedback=null;
                    this.resetButton();
                }
            });
        }
    }

    createReportDetail(){
        if(this.listIdIssuesServiceDetail.length == 0){
            this.app.showWarn('Chưa chọn bản ghi nào');
            return;
        }
        if(this.listIdIssuesServiceDetail.length != 0){
            this.displayAddReport= true;
            this.isReport=1;
        }

    }

    createReportSynthetic(){
        if(this.listIdIssuesServiceSynthetic.length == 0){
            this.app.showWarn('Chưa chọn bản ghi nào');
            return;
        }
        if(this.listIdIssuesServiceSynthetic.length != 0){
            this.displayAddReport= true;
            this.isReport=2;
        }
    }

    cancelAddReport(){
        this.displayAddReport= false;
        this.noteContentReport= null;
    }

    confirmAddReport(){
        this.loadingReport= true;
        if(this.noteContentReport == null || this.noteContentReport.trim() =='' || this.noteContentReport==undefined ){
            this.app.errorValidateDate('Nội dung báo cáo không được để trống');
            this.noteContentF.nativeElement.focus();
            return;
        }
        if(this.noteContentReport.length >500 ){
            this.app.errorValidateDate('Nội dung báo cáo không được lớn hơn 500 ký tự');
            this.noteContentF.nativeElement.focus();
            return;
        }
        this.listIdIssuesServiceReport = [];
        if(this.isReport==1){
            this.listIdIssuesServiceReport = this.listIdIssuesServiceDetail;
        }else if(this.isReport == 2){
            this.listIdIssuesServiceReport = this.listIdIssuesServiceSynthetic;
        }
        this.displayAddReport = false;
        const object = {
            'content': this.noteContentReport,
            'type': this.isReport,
            'issuedServiceId':this.listIdIssuesServiceReport,
            'fromDate':this.fromDateReport,
            'toDate':this.toDateReport,
            'listUnitId':this.listUnitIdReport
        };
        this._ServiceDataService.insertSignVoffice(object).subscribe(item => {
            if(item.status == 1){
                this.app.showSuccess('Đã tạo báo cáo thành công');
                this.router.navigate(['/service-management/SignVoffice'])
            }
        });
    }

    checkSelection(index: any) {
        let totalCheckbox = 0;
        let totalSelected = 0;
        const tr = this.myTable.el.nativeElement.getElementsByTagName('tr');
        for (let i = 1; i < tr.length; i++) {
            if (tr[i].children[index] === undefined) {
                continue;
            }
            let td = tr[i].children[index].children[0];
            if (td != undefined && td.tagName === 'P-CHECKBOX') {
                ++totalCheckbox;
                const span = td.querySelector('.ui-clickable');
                if (span.className != undefined && span.className.trim().includes('pi pi-check')) {
                    ++totalSelected;
                }
            }
        }
        if (totalCheckbox !== 0 && totalCheckbox === totalSelected) {
            return true;
        } else {
            return false;
        }
    }
}
