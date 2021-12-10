import {Component, OnInit, ViewChild, Renderer2} from '@angular/core';
import {TreeNode, ConfirmationService, MessageService, SelectItem} from 'primeng/components/common/api';
import {StationeryService} from '../stationery.service'
import {Place} from '../Entity/Place';
import {Employee} from '../Entity/Employee';
import {
    DataResponse, DataResponseDetails, DataResponseRatting, DataResponseUnit, InfoToSearchIssuesStationeryItems,
    IssuesStationeryItems,
    IssuesStationeryItemsToInsert,
    PlaceStationery, ReportStationery,
    RequestParam, RequestParamUnit, SearchDataStationery, StationeryEmployee,
    StationeryInfo,
    StationeryParam, StationeryReportRequest, UpdateIssuesStationery
} from '../Entity/ReportStationery';
import {TokenStorage} from '../../shared/token.storage';
import {UserInfo} from '../../shared/userInfo';
import {trigger, state, style, transition, animate} from '@angular/animations';

import {saveAs} from 'file-saver';
import {Table} from 'primeng/table';
import {Title} from '@angular/platform-browser';
import {KitchenManagementService} from '../../kitchen-management/kitchen-management.service';
import {MultiSelect} from 'primeng/primeng';
import {DatePipe} from "@angular/common";
import {Router} from "@angular/router";
import {AppComponent} from "../../app.component";
import {Stationery, UpdateAllList} from "../Entity/Stationery";
import {ProcessRequestStationeryComponent} from "../process-request-stationery/process-request-stationery.component";
import {DetailsStationeryUnitComponent} from "./details-stationery-unit/details-stationery-unit.component";
import {ConfirmPendingComponent} from "./confirm-pending/confirm-pending.component";
import {VoteHcdvComponent} from "./vote-hcdv/vote-hcdv.component";
import {VoteVptctComponent} from "./vote-vptct/vote-vptct.component";
import {DateTimeUtil} from "../../../common/datetime";


declare const require: any;
const pixelWidth = require('string-pixel-width');

@Component({
    selector: 'app-report-stationery',
    templateUrl: './report-stationery.component.html',
    styleUrls: ['./report-stationery.component.css'],
    animations: [
        trigger('animation', [
            state('true', style({
                opacity: 1,
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
export class ReportStationeryComponent implements OnInit {
    cols = [
        // { field: "rpStationeryId", header: "Mã yêu cầu", width: "12%" },
        {field: "placeName", header: "Vị Trí", width: "20%"},
        {field: "threeLevelUnit", header: "Đơn Vị", width: "20%"},
        {field: "FullName", header: "Người thực hiện", width: "15%"},
        {field: "employeePhone", header: "Số Điện Thoại", width: "10%"},
        {field: "totalRequest", header: "S/L Yêu Cầu", width: "6%"},
        {field: "totalFulFill", header: "S/L Đáp Ứng", width: "6%"},
        // {field: "status", header: "Trạng Thái", width: "8%"},
        // {field: "message", header: "Nội Dung", width: "13%"},


    ];

    colsEmployee = [
        // { field: "rpStationeryId", header: "Mã yêu cầu", width: "12%" },
        {field: "startPlace", header: "Tên vị Trí", width: "15%"},
        {field: "threeLevelUnit", header: "Tên đơn Vị", width: "17%"},
        {field: "issuesStationeryId", header: "Mã YC của nhân viên", width: "15%"},
        {field: "requestDate", header: "Ngày đăng ký", width: "10%"},
        // {field: "", header: "Ngày trình ký", width: "6%"},
        {field: "fullName", header: "Tên nhân viên", width: "6%"},
        {field: "statusUnit", header: "Trạng thái VPP Nhân viên", width: "10%"},
        {field: "totalRequest", header: "Trạng thái VPP đơn vị", width: "6%"},
    ];

    colsBeta = [
        {field: "", header: "STT", width: "10%"},
        {field: "teamName", header: "Tên VPP", width: "35%"},
        {field: "full_name", header: "Số Lượng Yêu Cầu", width: "20%"},
        {field: "full_name", header: "Số Lượng Bàn Giao", width: "20%"},
        {field: "full_name", header: "Đơn Vị Tính", width: "15%"}

    ];

    colsUnit = [
        {field: "", header: "STT", width: "10%"},
        {field: "fullName", header: "Người yêu cầu", width: "35%"},
        {field: "requestDate", header: "Ngày yêu cầu", width: "20%"},
        {field: "stationeryName", header: "Tên VPP", width: "20%"},
        {field: "totalFulFill", header: "Số lượng", width: "15%"},
        {field: "price", header: "Đơn giá", width: "15%"},
        {field: "totalMoney", header: "Số tiền", width: "15%"},

    ];

    status: SelectItem[] = [
        {label: 'Chờ LĐ phê duyệt', value: '0'},
        {label: 'Lãnh đạo phê duyệt', value: '1'},
        {label: 'Lãnh đạo từ chối', value: '2'},
        {label: 'VP TCT tiếp nhận', value: '3'},
        {label: 'VP TCT hoãn thực hiện', value: '4'},
        {label: 'VP TCT Không thực hiện được', value: '5'},
        {label: 'VP TCT cấp phát cho HCĐV', value: '6'},
        {label: 'HCĐV xác nhận VPP', value: '7'},
        {label: 'Từ chối xác nhận', value: '8'}
    ];

    ListSelectedStatus: any[] = [];
    @ViewChild('myTable') private myTable: Table;
    @ViewChild('myTableConfirm') private myTableConfirm: Table;
    @ViewChild('myEmployee') private myEmployee: Table;
    @ViewChild('myDrop') private myDrop: MultiSelect;
    @ViewChild('dtUnit') private dtUnit: Table;
    @ViewChild('processRequestComponent') processRequestComponent: ProcessRequestStationeryComponent;
    @ViewChild('detailsStationeryUnit') detailsStationeryUnit: DetailsStationeryUnitComponent;
    @ViewChild('confirmPendingComponent') confirmPendingComponent: ConfirmPendingComponent;
    @ViewChild('voteHcdvComponent') voteHcdvComponent: VoteHcdvComponent;
    @ViewChild('voteVptctComponent') voteVptctComponent: VoteVptctComponent;
    minDateValue: Date;
    maxDateValue: Date;
    vn = DateTimeUtil.vn;
    searchData : SearchDataStationery ={
        issuesStationeryApprovedId : ''
    } ;
    updateIssuesStationery : UpdateIssuesStationery ={
        issuesStationeryIdOld : null,
        issuesStationeryApprovedId : null,
        userName : null,
        issuesStationeryItemId : null,
        ldReasonReject : null,
        issueStationeryId : null

    };
    rowSize: number = 0;
    startRow: number = 0;

    updateAllIssuesStationery: string[];
    updateAllList:  UpdateAllList = {
        userName : '',
        updateAllIssuesStationery : ['']
    };
    dataResponseUnit :DataResponseUnit ={
        fullName : null,
        message : null,
        dateRequest : null,
        status : null,
        totalRequest : null,
        totalFulfill : null,
        sumTotalMoney : null,
        feedBackToHcdv: null,
        feedBackToVptct: null,
        ratingToUser: null,
        ratingToVptct: null,
        totalFulfillHcdv : null,
        unitName : null,
        sumTotalMoneyHcdv : null,
        phoneNumber : null,
        hcdvReasonReject : null,
        ldReasonReject :null,
        vptctReasonReject : null,
        listData : []
    };
    dataResponseUnitOneRequest :DataResponseUnit ={
        fullName : null,
        message : null,
        dateRequest : null,
        status : null,
        totalRequest : null,
        totalFulfill : null,
        sumTotalMoney : null,
        feedBackToHcdv: null,
        feedBackToVptct: null,
        ratingToUser: null,
        ratingToVptct: null,
        totalFulfillHcdv : null,
        unitName : null,
        sumTotalMoneyHcdv : null,
        phoneNumber : null,
        hcdvReasonReject : null,
        ldReasonReject :null,
        vptctReasonReject : null,
        listData : []
    };
    listSearchDetails : DataResponseDetails[];
    requestParamUnit : RequestParamUnit ={
        requestID : null
    };
    dataResponseUnitOld: DataResponseRatting;
    fullNameDetails : string;
    messageDetails : string;
    requestDateDetails : string;
    totalMoneyDetails : number;
    isDialogProcessRequest : boolean;
    isDialogConfirmPending : boolean;
    dialogConfirmRequestOneSelect : boolean;
    dialogRefuseRequestOneSelect : boolean;
    reasonRefuse : string;
    reasonRefuseLDReject : string;
    rpRatingVPTCT : number;
    rpFeedbackToVPTCT : string;
    rpRatingHCDV : number;
    rpFeedbackToHCDV : string;
    limitDate : number;
    convertStatus : string[];
    dialogImport : boolean;
    priceUnit : number;
    dialogHandlingRequest : boolean;
    dialogDetailsRequest : boolean;
    dialogRefuseRequest : boolean;
    rpItemsName: string;
    rpItemsQuantity: number;
    rpItemsDateRequest: string;
    rpItemsMessage: string;
    rpItemsQuantityRequest: number;
    rpItemsTotalMoney: number;
    rpItemsReason: string;
    total_record_unit: number;
    dialogDetailsUnit: boolean = false;
    isDisplay: boolean = false;
    rpItemStatus: number;
    textShow: any;
    empUnit: String;
    empName: String;
    empPhone: String
    empEmail: String;
    quantity: number;
    quota: number;
    unitId: String;
    statusConstant: string = 'Đang xử lý';
    note: String;
    stationeryNameList: StationeryInfo[];
    selectedStationeryName: StationeryInfo;
    listActivity: string[];
    listActivityEmployee: string[];
    lstChefInfor: StationeryReportRequest[] = [];
    averageRatting: number;
    reportStationery: ReportStationery;
    placeName : string;
    infoToSearchIssuesStationeryItems: InfoToSearchIssuesStationeryItems = {
        employeeUserName: '',
        managerUserName: '',
        requestDate: null,
        pageNumber: null,
        pageSize: null,
        status: null,
        roleAdmin: true
    };
    listInfoToSearchIssuesStationeryItems: DataResponse;
    issuesStationeryItemsToInsert: IssuesStationeryItemsToInsert = {
        listStationery: [''],
        issuesStationeryItemID: '',
        issuesStationeryID: '',
        stationeryID: '',
        issuesStationeryApprovedID: '',
        issuesStationeryRegistryID: '',
        status: null,
        totalRequest: null,
        totalFulFill: null,

    };
    len: number;
    maxLenStatus: any;
    employee: any;
    place: any;
    placeStationery : PlaceStationery;
    listFilterEmployee: any[];
    listFilterPlace: any[];
    listStationeryReport: ReportStationery[];
    listStationeryReportEmployee : StationeryEmployee[];
    listCountStationeryReport: ReportStationery[];
    total_record_employee : number;
    message: ReportStationery;
    listStationeryFullfill: any[];
    listStationeryFullUnit: ReportStationery[];
    countStationeryFullUnit: any[];
    display: boolean = false;
    listHandlingStationery : IssuesStationeryItems[];
    uf: UserInfo;
    listUnitManager: string[];
    sectors: TreeNode[];
    selectedSector: TreeNode[];
    requestParam : RequestParam = {
        placeID : null,
        listStationery : [],
        note : ''
    }
    isManager: boolean;
    isEmployee: boolean;
    listSelectedUnitId: any[] = [];
    listParentUnit: string[];
    fromDate: Date;
    toDate: Date;
    loadingTree = false;
    total_record: number;
    pageNumber: number = 0;
    pageSize: number = 10;
    isCheckAll: boolean;
    isCheckAllEmployee : boolean;
    dialogDetailsEmployee: boolean;
    dialogAddStationery: boolean;
    dialogConfirmRequest: boolean;
    selectedItem: ReportStationery[] = []
    dialogVoteHCDVStationery: boolean;
    dialogVoteVPTCTStationery : boolean;
    selectedStationeryItem: ReportStationery;
    dialogAcceptRequest:boolean
    listFilterEmployeeAdmin : any[];
    isAdmin: boolean;
    isHCDV: boolean;
    isHCVP_VPP: boolean;
    isQL: boolean;
    isNV: boolean;
    isVPTCTShow : boolean;
    isVPTCT: boolean;
    isCheckedAll: boolean;
    selectedItemIds: string[];
    isDisabledHcdvFld: boolean;
    selectedAppproving: ReportStationery;
    selectedAppprovingVote: ReportStationery;
    hcdvFullName : String;

    constructor(private stationeryService: StationeryService, private kitchenService: KitchenManagementService, private confirmationService: ConfirmationService,
                private messageService: MessageService, private renderer: Renderer2, private tokenStorage: TokenStorage, private title: Title, private app: AppComponent) {
    }

    ngOnInit() {
        this.reportStationery = new ReportStationery(null,null, 0, null, null, null, null, null, 0, null, 0, null, null, [], [], null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null,null, null,null,null,null,null,null,null);
        this.uf = this.tokenStorage.getUserInfo();
        this.listUnitManager = [];
        this.fromDate = new Date();
        this.toDate = new Date();
        this.averageRatting = 0;
        this.len = 50;
        this.total_record = 0;
        this.maxLenStatus = 431;
        this.textShow = 'Chọn tất cả';
        this.title.setTitle('Thống kê yêu cầu văn phòng phẩm - PMQTVP');
        this.setRole();
        this.checkRole();
        this.setStatus();
        this.lstChefInfor = [];
        this.lstChefInfor.push(new StationeryReportRequest(null, null, null, null,null, null));
        this.selectedStationeryItem = new ReportStationery(null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null)
        this.stationeryService.findStationeryByName().subscribe(
            data => {
                this.stationeryNameList = data.data;
            }
        );
        this.empUnit = this.uf.unitName;
        this.empName = this.uf.fullName;
        this.empPhone = this.uf.phoneNumber;
        this.empEmail = this.uf.email;

    }

    setStatus(){
        this.convertStatus = ['Chờ LĐ phê duyệt', 'Lãnh đạo phê duyệt', 'Lãnh đạo từ chối', 'VP TCT tiếp nhận', 'VP TCT hoãn thực hiện', ' VP TCT Không thực hiện được',
            'VP TCT cấp phát cho HCĐV', 'HCĐV xác nhận VPP ','Từ chối xác nhận'];
    }

    setRole() {
        console.log(this.uf.role);
        if (this.uf.role.includes('PMQT_ADMIN')) {
            this.isVPTCT = true;
            this.isAdmin = true;
            this.isVPTCTShow = true;
        } else if (this.uf.role.includes('PMQT_HCVP_VPP')) {
            this.isVPTCT = true;
            this.isHCVP_VPP = true;
            this.isVPTCTShow = false;
        } else if (this.uf.role.includes('PMQT_HC_DV')) {
            this.hcdvFullName = this.uf.fullName;
            this.isVPTCT = true;
            this.isHCDV = true;
            this.isDisabledHcdvFld = true;
            this.isVPTCTShow = true;
            this.employee = {userName: this.uf.userName.toString(), fullName: this.uf.fullName.toString()};
        } else if (this.uf.role.includes('PMQT_QL')) {
            this.isVPTCT = true;
            this.isQL = true;
            this.isAdmin = true;
            this.isVPTCTShow = true;
        } else if (this.uf.role.includes('PMQT_NV')) {
            this.isVPTCT = true;
            this.isNV = true;
            this.isVPTCTShow = false;
        }
    }

    checkAllItems(isChecked: boolean) {
        if(isChecked) {
            this.selectedItemIds = [];
            if(this.listStationeryReport && this.listStationeryReport.length > 0) {
                for(let i = 0; i < this.listStationeryReport.length; i++) {
                    let stationery = this.listStationeryReport[i];
                    if(stationery.status === 0) this.selectedItemIds.push(this.listStationeryReport[i].rpStationeryId);
                }
            }
        } else {
            this.selectedItemIds = null;
        }
    }

    onCheckItem(isChecked: boolean) {
        if(isChecked) {
            let count: number = 0;
            for(let i = 0; i < this.listStationeryReport.length; i++) {
                let stationery = this.listStationeryReport[i];
                if(stationery.status === 0) count = count + 1;
            }
            if(count === this.selectedItemIds.length) this.isCheckedAll = true;
        } else {
            this.isCheckedAll = false;
        }
    }

    closeDetailsUnit(){}

    refuseRequest() {
        console.log(this.selectedItemIds);
        this.requestParamUnit.requestID = this.selectedItemIds[0];
        this.stationeryService.findDataToApprove(this.requestParamUnit).subscribe(res => {
                if(res.status === 1) {
                    this.dataResponseUnit = res.data;
                    this.fullNameDetails = this.dataResponseUnit.fullName;
                    this.messageDetails = this.dataResponseUnit.message;
                    this.totalMoneyDetails = this.dataResponseUnit.sumTotalMoney;
                    this.listSearchDetails = this.dataResponseUnit.listData;
                    this.requestDateDetails = new DatePipe("en-US").transform(this.dataResponseUnit.dateRequest,'yyyy-MM-dd');
                    this.dialogConfirmRequestOneSelect = true;
                }
            }
        );
    }

    onShowRefuseRequest(rowData: ReportStationery) {
        this.selectedStationeryItem = rowData;
        this.requestParamUnit.requestID = rowData.rpStationeryId;
        this.stationeryService.findDataToApprove(this.requestParamUnit).subscribe(res => {
              if(res.status === 1) {
                  this.dataResponseUnit = res.data;
                  this.fullNameDetails = this.dataResponseUnit.fullName;
                  this.messageDetails = this.dataResponseUnit.message;
                  this.totalMoneyDetails = this.dataResponseUnit.sumTotalMoney;
                  this.listSearchDetails = this.dataResponseUnit.listData;
                  this.requestDateDetails = new DatePipe("en-US").transform(this.dataResponseUnit.dateRequest,'yyyy-MM-dd');
                  this.dialogConfirmRequest = true;
              }
          }
        );
    }

    selectRow(rowData: ReportStationery) {
        this.requestParamUnit.requestID = rowData.rpStationeryId;
        console.log(this.requestParamUnit);
        this.stationeryService.findDataToApprove(this.requestParamUnit).subscribe(res => {
            if (res.status === 1) {
                this.detailsStationeryUnit.onModelChange(res.data, rowData);
                this.dialogDetailsUnit = true;
            }
        });

    }

    refuseAccept() {
        this.dialogRefuseRequest = true;
    }
    // tu choi  cap phat
    refuse(){}

    // xac nhan  cap phat
    approveHandle(){
    }

    approveChoose() {
        this.updateIssuesStationery.issuesStationeryApprovedId = this.selectedStationeryItem.rpStationeryId;
        this.updateIssuesStationery.issuesStationeryItemId = this.selectedStationeryItem.issuesStationeryItemId;
        this.updateIssuesStationery.issueStationeryId = this.selectedStationeryItem.issuesStationeryId;
        this.updateIssuesStationery.ldReasonReject = this.reasonRefuse;

        this.stationeryService.updateRefuseIssuesStationery(this.updateIssuesStationery).subscribe(
            data => {
               if(data.status ==1){
                   this.app.showSuccess('Cập nhật thành công');
               }
                this.dialogConfirmRequest = false;
            }
        );

    }

    cancelRefuse(){
        this.dialogRefuseRequest = false;
    }
    cancelOneRefuse(){
        this.dialogRefuseRequestOneSelect = false;
    }
    onHideReject() {
        this.reasonRefuseLDReject = null;
    }

    approveHandleAccept() {
        if(!this.selectedStationeryItem) {
            if(this.selectedItemIds && this.selectedItemIds.length > 0) {
                for(let i = 0; i < this.listStationeryReport.length; i++) {
                    if(this.listStationeryReport[i].rpStationeryId === this.selectedItemIds[0]) {
                        this.selectedStationeryItem = this.listStationeryReport[i];
                    }
                }
            }
        }
        this.updateIssuesStationery.issuesStationeryIdOld = this.selectedStationeryItem.rpStationeryId;
        this.updateIssuesStationery.issuesStationeryApprovedId = this.selectedStationeryItem.rpStationeryId;
        this.updateIssuesStationery.issuesStationeryItemId = this.selectedStationeryItem.issuesStationeryItemId;
        this.updateIssuesStationery.issueStationeryId = this.selectedStationeryItem.issuesStationeryId;
        this.updateIssuesStationery.ldReasonReject = this.reasonRefuseLDReject;
        console.log(this.updateIssuesStationery);
        this.stationeryService.updateRefuseIssuesStationery(this.updateIssuesStationery).subscribe(
            data => {
                if(data.status ==1){
                    this.app.showSuccess('Đã từ chối duyệt yêu cầu văn phòng phẩm');
                    this.dialogRefuseRequest = false;
                    this.dialogConfirmRequest = false;
                    this.dialogConfirmRequestOneSelect = false;
                    this.myTable.reset();
                    this.handleSearch();
                }  else if(data.status === 4) {
                    this.app.showWarn('Yêu cầu đã được từ chối trước đó, thực hiện không thành công');
                    this.dialogRefuseRequest = false;
                    this.dialogConfirmRequest = false;
                    this.dialogConfirmRequestOneSelect = false;
                    this.myTable.reset();
                    this.handleSearch();
                }else if(data.status ==10){
                    this.app.showWarn('Đã quá thời hạn đăng ký văn phòng phẩm của tháng này');
                } else {
                    this.app.showWarn('Từ chối thất bại - có lỗi không xác định');
                }
            }
        );
    }

    approveHandleAcceptOneSelect() {
        this.updateIssuesStationery.issuesStationeryApprovedId = this.selectedItemIds[0];
        this.updateIssuesStationery.ldReasonReject = this.reasonRefuseLDReject;
        this.stationeryService.updateRefuseIssuesStationery(this.updateIssuesStationery).subscribe(
            data => {
                 if(data.status ==1){
                    this.app.showSuccess('Đã từ chối duyệt yêu cầu văn phòng phẩm');
                    this.dialogRefuseRequest = false;
                    this.dialogConfirmRequest = false;
                    this.dialogConfirmRequestOneSelect = false;
                    this.dialogRefuseRequestOneSelect = false;
                    this.myTable.reset();
                    this.handleSearch();
                }else if(data.status ==4){
                     this.app.showWarn('Bản ghi đã được phê duyệt trước đó, thực hiện không thành công');
                    this.dialogRefuseRequest = false;
                    this.dialogConfirmRequest = false;
                    this.dialogConfirmRequestOneSelect = false;
                    this.dialogRefuseRequestOneSelect = false;
                    this.myTable.reset();
                    this.handleSearch();
                }
                else if(data.status ==10){
                    this.app.showWarn('Đã quá thời hạn đăng ký văn phòng phẩm của tháng này');
                } else {
                    this.app.showWarn('Từ chối thất bại - có lỗi không xác định');
                }
            }
        );
    }


    accept(){
            this.updateIssuesStationery.issuesStationeryApprovedId = this.selectedStationeryItem.rpStationeryId;
            this.updateIssuesStationery.issuesStationeryIdOld = this.selectedStationeryItem.rpStationeryId;
        this.stationeryService.updateApproveIssuesStationery(this.updateIssuesStationery).subscribe(data => {
            if(data.status ==1){
                this.app.showSuccess('Đã duyệt yêu cầu văn phòng phẩm');
                this.dialogConfirmRequest = false;
                this.myTable.reset();
                this.handleSearch();
            }else if(data.status ==4){
                this.app.showSuccess('Bản ghi đã được phê duyệt trước đó, thực hiện không thành công');
                this.dialogConfirmRequest = false;
                this.myTable.reset();
                this.handleSearch();
            } else if(data.status === 17) {
                this.app.showWarn('Đã quá thời hạn đăng ký văn phòng phẩm của tháng này');
            }else {
                this.app.showWarn('Phê duyệt thất bại - lỗi không xác định');
            }
          }
        );
    }
    refuseAcceptRequest(){
            this.dialogRefuseRequestOneSelect = true;
    }
    acceptRequestOneSelect(){
        this.updateIssuesStationery.issuesStationeryApprovedId = this.selectedItemIds[0];
        this.stationeryService.updateApproveIssuesStationery(this.updateIssuesStationery).subscribe(data => {
                if(data.status ==1){
                    this.app.showSuccess('Đã duyệt yêu cầu văn phòng phẩm');
                    this.dialogConfirmRequestOneSelect = false;
                    this.myTable.reset();
                    this.handleSearch();
                }else if(data.status ==4){
                this.app.showSuccess('Đã duyệt yêu cầu văn phòng phẩm');
                this.dialogConfirmRequestOneSelect = false;
                this.myTable.reset();
                this.handleSearch();
            }
                else if(data.status === 17) {
                    this.app.showWarn('Đã quá thời hạn đăng ký văn phòng phẩm của tháng này');
                } else {
                    this.app.showWarn('Phê duyệt thất bại - lỗi không xác định');
                }
            }
        );
    }


    onBasicUpload(){}
    //  set min date when click 'Tu' field
    selectFromDate() {
        if (this.toDate && this.toDate < this.fromDate) {
            this.toDate = this.fromDate;
        }
    }

    //  set max date when click 'Den' field
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

    checkRole() {
        this.isEmployee = false;
        this.isManager = false;

        if (this.uf.role.includes('PMQT_ADMIN')) {
            this.isManager = false;
            this.getSector(0);
        } else if (this.uf.role.includes('PMQT_HC_DV')) {
            this.unitId = this.uf.unitId;
                        this.isManager = false;
                        this.isHCDV = true;
                        this.stationeryService.getUnitHCDVNoStaff().subscribe(data => {
                            this.sectors = data;
                });
        }else if (this.uf.role.includes('PMQT_HCVP_VPP')) {

            this.stationeryService.checkVptctInStaff().subscribe(res => {
                console.log(res.data);
                console.log(res.length);
                    if(res.data != null  ) {
                        this.getSector(0);
                        this.unitId = this.uf.unitId;
                        this.isManager = false;
                        this.isNV = false;
                    } else {
                        this.getSector(2);
                        this.unitId = this.uf.unitId;
                        this.isManager = false;
                        this.isNV = true;
                    }
                console.log(this.isNV);
                }

            );
        }else if (this.uf.role.includes('PMQT_QL')) {
            this.getSector(0);
            this.unitId = this.uf.unitId;
            this.isManager = false;
            this.isAdmin =false;
            this.isHCDV =false;
            this.isHCVP_VPP =false;
            this.isQL = true;
        } else {
            this.isManager = true;
            this.isEmployee = true;
            this.getSector(0);
        }
    }

    getSector(type: number) {
        if(this.isQL) {
            this.kitchenService.getSectorGreatFather(this.uf.unitId.toString()).subscribe(data => {
                this.sectors = data;
            });
        }
        else if(this.isHCDV) {

                    this.stationeryService.getUnitHCDV().subscribe(data => {
                        this.sectors = data;
                    });

            }


    else if(this.isHCVP_VPP) {
                this.stationeryService.getUnitVPTCT().subscribe(data => {
                    if(data.length > 0 ){
                        this.sectors = data;
                    } else {
                    this.kitchenService.getSectors(null).subscribe(res => {
                        this.sectors = res;
                        });
                    }
                });
        }
        else {
            if (type === 0) {
                this.kitchenService.getSectors(null).subscribe(data => {
                    this.sectors = data;
                });
            } else if (type === 1) {
                this.kitchenService.getSectors(this.uf.unitId as string).subscribe(data => {
                    this.sectors = data;
                });
            } else if (type === 2) {
                console.log(this.sectors);
                this.sectors = [{
                    label: this.uf.unitName as string,
                    data: this.uf.unitId,
                    selectable: false
                }];
                this.selectedSector = [this.sectors[0]];
            }
        }
        console.log(this.selectedSector);
    }

    nodeExpand(event) {
        this.loadingTree = true;
        if (event.node && event.node.children !== undefined) {
            this.loadingTree = false;
            return;
        }
        if (event.node && event.node.children === undefined) {
            this.kitchenService
                .getSectors(event.node.data).subscribe(
                data => {
                    event.node.children = data;
                    this.loadingTree = false;
                });
        }
    }

    getReportStationery() {
        this.stationeryService.getReportStationery().subscribe(
            data => {
                this.listStationeryReport = data.data;
                data.data.forEach(element => {
                    this.averageRatting += element.ratting;
                });
                this.averageRatting = Number((this.averageRatting / this.listStationeryReport.length).toFixed(2));
                this.total_record = this.listStationeryReport.length;

            }
        );
    }

    filterPlace(event) {
        let temp: any;

        if (this.place == null) {
            temp = new Place(null, null);
        } else {
            if (this.checkEmpty(this.place) == '') {
                temp = new Place(null, null);
            }
            else {
                temp = new Place(null, this.place.trim());
            }
        }

        if(this.isHCDV) {
            temp.username = this.uf.userName.toString();

                    this.stationeryService.findPlace(temp).subscribe(data => {
                        console.log(" else   i'm here ");
                            this.listFilterPlace = data.data;
                        }
                    );

        }else if(this.isHCVP_VPP) {
            temp.username = this.uf.userName.toString();
            this.stationeryService.findPlaceByVPTCT(temp).subscribe(data => {
                    this.listFilterPlace = data.data;
                }
            );
        }  else {
            this.stationeryService.findPlace(temp).subscribe(data => {
                  this.listFilterPlace = data.data;
              }
            );
        }
    }

    filterEmployeeAdmin(event): any {
        let temp: any;

        if (this.employee == null) {
            temp = new Employee(null, null, null, null, null, null, null);
        } else {
            if (this.checkEmpty(this.employee) == '') {
                temp = new Employee(null, null, null, null, null, null, null);
            }
            else {
                temp = new Employee(null, this.employee.trim(), this.employee.trim(), null, null, null, null);
            }
        }

        this.stationeryService.getEmployeeTCT(temp).subscribe(
            data => {
                this.listFilterEmployeeAdmin = data.data;
                console.log(this.listFilterEmployeeAdmin);
                if (data.status == 1) {
                    this.listFilterEmployeeAdmin.forEach(x => {
                        x.fullName = x.fullName + " - " + x.employeePhone + " - " + x.unit;
                    });
                }
            }
        );
    }


    filterEmployee(event): any {
        let temp: any;

        if (this.employee == null) {
            temp = new Employee(null, null, null, null, null, null, null);
        } else {
            if (this.checkEmpty(this.employee) == '') {
                temp = new Employee(null, null, null, null, null, null, null);
            }
            else {
                temp = new Employee(null, this.employee.trim(), this.employee.trim(), null, null, null, null);
            }
        }

        this.stationeryService.getEmployeeTCT(temp).subscribe(
            data => {
                this.listFilterEmployee = data.data;
                console.log(data.data);
                if (data.status == 1) {
                    this.listFilterEmployee.forEach(x => {
                        x.fullName = x.fullName + " - " + x.employeePhone + " - " + x.unit;
                    });
                }
            });
    }

    employeeSelect(event) {
        console.log(event);
        this.employee = new Employee(event.employeeId, this.getNameObject(event.fullName), event.selectUserName, event.employeePhone, event.role, event.place, event.unit, event.selectUserName);
        console.log(this.employee);
    }

    getNameObject(suggest: string): any {
        if (suggest == null) return "";
        let split = suggest.split(" - ");
        return split[0];
    }


    checkEmpty(input: string) {
        let i = 0;
        let output: string;
        output = '';
        for (i = 0; i < input.length; i++) {
            if (input.charAt(i) != '') output += input.charAt(i)
        }
        return output;
    }

    focusOutPlace() {
        if (this.place != null && this.place != undefined) {
            if (this.place.placeId == null || this.place.placeId == undefined) {
                this.place = null
            }
        }
    }

    focusOutEmployee() {
        if (this.employee != null && this.employee != undefined) {
            if (this.employee.employeeId == null || this.employee.employeeId == undefined) {
                this.employee = null
            }
        }
    }

    showDialog(rowData: ReportStationery) {
        this.reportStationery.rpStationeryId = rowData.rpStationeryId;

        this.stationeryService.getFullfillStationery(this.reportStationery).subscribe(
            y => {
                this.listStationeryFullfill = y.data;
                let item = this.listStationeryFullfill[0];
                this.reportStationery.fullName = item.fullName;
                this.reportStationery.status = rowData.status - 1;
                this.reportStationery.lastUser = item.lastUser;
                this.stationeryService.getLastUser(this.reportStationery).subscribe(
                    data => { 
                        this.reportStationery.lastUser = data.data;
                    }
                );

                this.stationeryService.getFullnameHCDV(this.reportStationery).subscribe(
                  data => {
                      this.reportStationery.fullName = data.data;
                  }
                );
            }
        )
        this.display = true;
    }

    close() {
        this.display = false;
    }


    settingSearch() {
        console.log(this.employee);
        if (this.employee != null ) {
            this.reportStationery.userName = this.employee.selectUserName;
        } else {
            if (this.employee === '') this.employee = null;
            this.reportStationery.userName = this.employee;
        }

        if (this.place != null && this.place.placeName != undefined) {
            this.reportStationery.placeName = this.place.placeName;
            this.reportStationery.placeId = this.place.placeId;

        } else {
            if (this.place === '') this.place = null;
            this.reportStationery.placeName = this.place;
            this.reportStationery.placeId = this.place;
        }


        this.listSelectedUnitId = [];
        if (this.selectedSector == null || this.selectedSector.length === 0) {
            if(this.isQL) {
                this.listSelectedUnitId.push(this.sectors[0].data);
            }
        } else
            this.selectedSector.forEach(element => {
                this.listSelectedUnitId.push(element.data);
            });
        this.reportStationery.listUnitId = this.listSelectedUnitId;
        this.reportStationery.listStatus = this.ListSelectedStatus;
        this.reportStationery.startDate = this.fromDate;
        this.reportStationery.endDate = this.toDate;
        console.log(this.reportStationery);
    }

    handleSearch() {
        this.reportStationery.pageSize = 15;
        this.startRow = 1;
        this.rowSize = 15;
        this.reportStationery.pageNumber = 0;
        this.settingSearch();
        this.getListReport();
    }

    handleSearchReset() {
        this.reportStationery.pageSize = 0;
        this.startRow = 0;
        this.rowSize = 0;
        this.reportStationery.pageNumber = 0;
        this.settingSearch();
        this.getListReportReset();
    }

    handleSearchEmployee() {
        this.reportStationery.pageSize = 15;
        this.reportStationery.pageNumber = 0;
        this.settingSearch();
        this. getListReport();
    }

  getListReport() {
    this.selectedItemIds = null;
    this.isCheckedAll = false;
    this.listActivity = [];
      console.log(this.reportStationery);
    this.stationeryService.countReportStationery(this.reportStationery).subscribe(res => {
        if (res) {
          this.total_record = res.data;
          this.startRow = 0;
          if(this.total_record > 0) this.startRow = 1;
          if(this.total_record > 15) this.rowSize = 15;
          else this.rowSize = this.total_record;
            this.stationeryService.searchReportStationery(this.reportStationery).subscribe(
                data => {
                    console.log(data);
                    if(data.data != null){
                    this.listStationeryReport = data.data;
                    if(this.listStationeryReport.length > 0){
                        for(let j =0 ; j< this.listStationeryReport.length ; j++){
                            this.setApprove(this.listStationeryReport[j]);
                            }
                        }
                    }
                    if(data.status ==14){
                        this.app.showWarn('Đồng chí chưa được cấu hình trong danh mục người tiếp nhận');
                    }

                }
            );
        }
      }
    );
  }

  setApprove(stationery: ReportStationery) {
      if(stationery.status === 0) {
          if(this.isAdmin || (this.isQL && this.uf.userName.toString() === stationery.ldUsername)) {
              stationery.isApprove = true;
          }
      }
  }

    getListReportReset() {
        this.selectedItemIds = null;
        this.isCheckedAll = false;
        this.listActivity = [];
        this.stationeryService.searchReportStationery(this.reportStationery).subscribe(
            data => {
                this.listStationeryReport = null;
            }
        );
       this.total_record = 0;
    }

    onPaginate(event) {
      this.isCheckedAll = false;
      this.selectedItemIds = [];
      this.selectedItemIds = null;
      this.startRow = event.first + 1;
      this.reportStationery.pageNumber = event.first;
      this.rowSize = this.startRow +  14;
      if (this.rowSize > this.total_record) this.rowSize = this.total_record;

      this.stationeryService.searchReportStationery(this.reportStationery).subscribe(
        data => {
          this.listStationeryReport = data.data;
            if(this.listStationeryReport.length > 0){
                for(let j =0 ; j< this.listStationeryReport.length ; j++){
                    this.setApprove(this.listStationeryReport[j]);
                }
            }
        }
      );
    }

    onLazyLoad(event) {
      this.checkAllItems(true);
      this.selectedItemIds = [];
      this.selectedItemIds = null;
      this.reportStationery.pageNumber = event.first;
      this.pageNumber = event.first;
      this.reportStationery.pageSize = event.rows;
      this.pageSize = event.rows;
      this.getListReport();
    }

    exportExcelVPP() {
        this.settingSearch();
        const fileName = 'TKDUVPP_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
            + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
            + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
        if (this.reportStationery.listUnitId == null || this.reportStationery.listUnitId.length == 0) {
            this.reportStationery.listUnitId = this.sectors.map(sector => sector.data);
        }
        this.stationeryService.exportExcelVPP(this.reportStationery).subscribe(
            (next: ArrayBuffer) => {
                const file: Blob = new Blob([next], {type: 'application/xlsx'});
                saveAs(file, fileName);
            });
    }

    exportExcel() {
        this.settingSearch();
        const fileName = 'TKYCVPP_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
            + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
            + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
        this.stationeryService.exportExcel(this.reportStationery).subscribe(
            (next: ArrayBuffer) => {
                const file: Blob = new Blob([next], {type: 'application/xlsx'});
                saveAs(file, fileName);
            });
    }

    selectedAll(event) {
        event.stopPropagation();
        if (this.ListSelectedStatus != null && this.ListSelectedStatus.length === this.status.length) {
            this.ListSelectedStatus = [];
            this.textShow = 'Chọn tất cả';
        } else {
            this.ListSelectedStatus = [];
            for (let i = 0; i < this.status.length; i++) {
                this.ListSelectedStatus.push(this.status[i].value);
                this.textShow = 'Bỏ chọn tất cả';
            }
        }
        this.onChange();
    }

    onChange() {
        if (this.ListSelectedStatus != null && this.ListSelectedStatus.length === this.status.length) {
            this.textShow = 'Bỏ chọn tất cả';
        } else {
            this.textShow = 'Chọn tất cả';
        }
        if(this.ListSelectedStatus == null){
            this.textShow = '';
        }
        const str: string[] = [];
        const child = this.myDrop.el.nativeElement.children[0].children[1];
        this.len = 50 + pixelWidth(',...', {size: 16});
        if (this.ListSelectedStatus != null
            && this.ListSelectedStatus !== undefined
            && this.ListSelectedStatus.length > 0) {
            for (let i = 0; i < this.ListSelectedStatus.length; i++) {
                this.len += pixelWidth(this.status[this.ListSelectedStatus[i]].label, {size: 16});
                if (this.len < this.maxLenStatus) {
                    if (i === 0) {
                        str.push(this.status[this.ListSelectedStatus[i]].label);
                    } else {
                        str.push(' ' + this.status[this.ListSelectedStatus[i]].label);
                    }
                }
            }

            if (this.len > this.maxLenStatus) {
                child.children[0].innerText = str.toString() + ',...';
            } else {
                child.children[0].innerText = str.toString();
            }
        } else {
            child.children[0].innerText = '-- Chọn trạng thái --';
        }
    }

    onShow() {
        const child = this.myDrop.el.nativeElement.children[0].children[3];
        this.renderer.setStyle(child, 'max-width', 'inherit');
        this.renderer.setStyle(child, 'width', 'inherit');
        this.isDisplay = true;
    }

    onHide() {
        this.isDisplay = false;
    }

    checkAll(isChecked: boolean) {
        if (!this.listStationeryReport || this.listStationeryReport.length == 0) {
            return;
        }

        this.listActivity = [];
        if (isChecked) {
            for (let i = 0; i < this.listStationeryReport.length; i++) {
                const booking = this.listStationeryReport[i];
                this.listActivity.push(booking.rpStationeryId);
            }
        }
    }

    checkAllEmployee(isChecked: boolean) {
        if (!this.listStationeryReportEmployee || this.listStationeryReportEmployee.length == 0) {
            return;
        }

        this.listActivityEmployee = [];
        if (isChecked) {
            for (let i = 0; i < this.listStationeryReportEmployee.length; i++) {
                const booking = this.listStationeryReportEmployee[i];
                this.listActivityEmployee.push(booking.issuesStationeryItemId);
            }
        }
    }

    checkItemEmployee(isChecked: boolean) {
        if (isChecked) {
            if (this.listActivityEmployee.length === this.listActivityEmployee.length) this.isCheckAllEmployee = true;
        } else {
            this.isCheckAllEmployee = false;
        }
    }

    checkItem(isChecked: boolean) {
        if (isChecked) {
            if (this.listActivity.length === this.listStationeryReport.length) this.isCheckAll = true;
        } else {
            this.isCheckAll = false;
        }
    }

    settingInfoItem(event) {
        this.infoToSearchIssuesStationeryItems.status = event.status;
        this.infoToSearchIssuesStationeryItems.requestDate = event.requestDate;
    }

    addRequest() {
        let isValid = this.onValidate()
        if (isValid) {
            this.requestParam.listStationery = this.lstChefInfor;
            this.requestParam.placeID = this.placeStationery.placeId;
            this.requestParam.note= this.note;
            this.stationeryService.insertIssuesStationery(this.requestParam).subscribe(
                y => {
                   if(!y){
                       this.app.showSuccess('Thêm mới thành công');
                   }
                }
            );
            this.dialogAddStationery = false;
        }
        this.myTable.reset();
        this.handleSearch();

    }

    resetButton() {
        this.fromDate = new Date();
        this.toDate = new Date();
        this.ListSelectedStatus = [];
        this.onChange();
        this.selectedSector = [];
        this.place = null;
        this.employee = null;
        this.handleSearchReset();
        this.myTable.reset();
    }

    indexTracker(index: number, value: any) {
        return index;
    }

    selectedStationery(){
        let count =0;
        for (let i = 0; i < this.stationeryNameList.length; i++) {
            for(let j =0 ; j < this.lstChefInfor.length ; j++){

                if(this.stationeryNameList[i].stationeryId == this.lstChefInfor[j].stationeryId){
                    this.stationeryNameList.splice(i, 1);
                    count ++;
                }
            }

        }
    }

    addRequestStationery() {
        this.dialogAddStationery = true;

    }

    removeChef(index: number) {
        this.lstChefInfor.splice(index, 1);
        for (let i = 0; i < this.stationeryNameList.length; i++) {
            for(let j =0 ; j < this.lstChefInfor.length ; j++){
                if(this.stationeryNameList[i].stationeryId == this.lstChefInfor[j].stationeryId){
                    this.stationeryNameList.push(this.stationeryNameList[i]);

                }
            }

        }
    }

    addChef() {
        this.lstChefInfor.push(new StationeryReportRequest(null, null, null, null,null, null));
    }

    acceptRequest(rowData?: ReportStationery) {
        this.selectedAppprovingVote = rowData;
        this.processRequestComponent.reportId(rowData);
        const requestParamUnit: RequestParamUnit = new RequestParamUnit();
        requestParamUnit.requestID = rowData.rpStationeryId;
        this.stationeryService.findDataToApproveProcess(requestParamUnit).subscribe(res => {
            this.stationeryService.findDataToApproveProcess(requestParamUnit).subscribe(item => {
                if (item.status === 1) {
                     this.dataResponseUnitOld = item.data;
                }
                if (res.status === 1) {
                    this.processRequestComponent.onModelChange(res.data, this.dataResponseUnitOld);
                    this.isDialogProcessRequest = true;
                }
            });
        });

    }

    acceptAllRequest(){
        console.log(this.selectedItemIds);
        this.stationeryService.getLimitDateEnd().subscribe(res => {
            if (res) {
                let limitDate = res.data;
                if(limitDate < new Date().getDay()) {
                    this.app.showWarn('Đã quá thời hạn đăng ký văn phòng phẩm của tháng này');
                } else {
                    if(!this.selectedItemIds ||this.selectedItemIds.length == 0) {
                        this.app.showWarn('Không có đăng ký cần phê duyệt');
                    } else if(this.selectedItemIds.length == 1){
                        this.refuseRequest();
                    } else {
                        this.confirmationService.confirm({
                            message: 'Đ/c đang phê duyệt nhiều đăng ký,  Đ/c chắc chắn muốn phê duyệt không?',
                            header: 'Nhắc nhở phê duyệt',
                            icon: 'pi pi-exclamation-triangle',
                            acceptLabel: 'Đồng ý',
                            rejectLabel: 'Hủy bỏ',
                            accept: () => {
                                this.updateAllList.updateAllIssuesStationery = this.selectedItemIds;
                                if(this.selectedItemIds.length > 1) {
                                    this.stationeryService.updateAllIssuesStationery(this.updateAllList).subscribe(resp => {
                                          if(resp.status === 1) {
                                              this.app.showSuccess('Đã phê duyệt yêu cầu văn phòng phẩm');
                                              this.myTable.reset();
                                              this.handleSearch();
                                          } else if(resp.status === 17) {
                                              this.app.showWarn('Đã quá thời hạn đăng ký văn phòng phẩm của tháng này');
                                          } else {
                                              this.app.showWarn('Phê duyệt thất bại - lỗi không xác định');
                                          }
                                      }
                                    );
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    onCloseResult(event){
        if(event == 1) {
            this.isDialogProcessRequest = false;
            this.voteHcdvComponent.onModelChange(this.selectedAppprovingVote);
            this.dialogVoteHCDVStationery = true;
            this.myTable.reset();
            this.handleSearch();
        }
        if(event == 2){
            this.isDialogProcessRequest = false;
            this.myTable.reset();
            this.handleSearch();
        }
    }

    onCloseResultDetailsUnit(event){
        if(event == 1){
            this.dialogDetailsUnit = false;
        }
    }

    onCloseResultConfirmPending(event){
        if(event == 1) {
            this.isDialogConfirmPending = false;
            this.voteVptctComponent.onModelChange(this.selectedAppproving);
            this.dialogVoteVPTCTStationery = true;
            this.myTable.reset();
            this.handleSearch();
        }
    }

    onValidate() {
        let count = 0
        this.lstChefInfor.forEach(item => {
            if (item.stationeryId) {
                count++
                if (item.quantity) {
                    this.app.showWarn('Số lượng không được để trống');
                    return false;
                }
            }
        })
        if (count == 0) {
            this.app.showWarn('Văn phòng phẩm không được để trống')
            return false;
        }
        return true;
    }

    handleRequest(item){
        this.dialogDetailsRequest = true;
    }

    confirmRequest(rowData? :ReportStationery ) {
      this.selectedAppproving = rowData;
        this.confirmPendingComponent.onStationeryApproveID(rowData);
        var requestParamUnit: RequestParamUnit = new RequestParamUnit();
        requestParamUnit.requestID = rowData.rpStationeryId;
        this.stationeryService.findDataToApprove(requestParamUnit).subscribe(res => {
            if (res.status === 1) {
                this.confirmPendingComponent.onModelChange(rowData,res.data);
            }
            this.isDialogConfirmPending = true;
        });

    }

    doVoteHCDV(rowData:ReportStationery) {
        this.voteHcdvComponent.onModelChange( rowData);
        this.dialogVoteHCDVStationery = true;
    }

    doVoteVPTCT(rowData? : ReportStationery) {
        this.voteVptctComponent.onModelChange(rowData);
        this.dialogVoteVPTCTStationery = true;

    }

    convertDate(date) {
        return new DatePipe("en-US").transform(date, 'dd-MM-yyyy');
    }

    onCloseVoteHCDVdialog() {
        this.voteHcdvComponent.onHideVoteHcdvPopup();
        this.dialogVoteHCDVStationery = false;
        this.selectedStationeryItem = new ReportStationery(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null);
    }
    onCloseVoteVPTCTdialog(){
        this.voteVptctComponent.onHideVotetctPopup();
        this.dialogVoteVPTCTStationery = false;
        this.selectedStationeryItem = new ReportStationery(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null,null,null,null,null,null,null,null,null,null);
    }
    onCloseProcessDialog(){

    }

    onCloseResultHcdv(event){
        if(event == 1){
            this.dialogVoteHCDVStationery = false;
            this.selectedAppproving = null;
            this.handleSearch();
            this.myTable.reset();
        }
    }

    onCloseResultVptct(event){
        if(event == 1){
            this.dialogVoteVPTCTStationery = false;
            this.selectedAppproving = null;
            this.handleSearch();
            this.myTable.reset();
        }
    }

    voteHCDV() {

    }

    onCloseVerifyRequest(){
        this.dialogAcceptRequest = false;
        this.handleSearch();
        this.myTable.reset();
    }

    importExcel(){
        this.dialogImport = true;

    }

    onHideProcessPopup() {
        this.processRequestComponent.onHideProcessPopup();
    }
    onHideDetailsPopup() {
        this.detailsStationeryUnit.onHideDetailsPopup();
    }
    onHideComfirmPendingPopup() {
        this.confirmPendingComponent.onHideProcessPopup();
    }
}
