import {Component, EventEmitter, Input, OnInit, Output, Renderer2} from '@angular/core';
import {
  DataResponseDetails, DataResponseRatting, DataResponseUnit, ItemModel, ProcessIssuesStationery, ReportStationery,
  RequestParamUnit
} from "../../Entity/ReportStationery";
import {StationeryService} from "../../stationery.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {KitchenManagementService} from "../../../kitchen-management/kitchen-management.service";
import {TokenStorage} from "../../../shared/token.storage";
import {Title} from "@angular/platform-browser";
import {AppComponent} from "../../../app.component";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-confirm-pending',
  templateUrl: './confirm-pending.component.html',
  styleUrls: ['./confirm-pending.component.css']
})
export class ConfirmPendingComponent implements OnInit {
  @Input() rowData : ReportStationery;
  @Output() result: EventEmitter<any> = new EventEmitter<any>();
  rpItemsName : string;
  rpItemsQuantity : number;
  rpItemsDateRequest : Date;
  rpItemsMessage : string;
  rpItemsQuantityRequest : number;
  rpItemsTotalMoney : number;
  rpRatingVPTCT : number;
  rpFeedbackToVPTCT : string;
  rpRatingHCDV : number;
  rpFeedbackToHCDV : string;
  rpVPTCTTotal: number;
  dialogRefuseRequest : boolean;
  hcdvReasonReject : string;
  dataResponseUnit :DataResponseRatting ={
    fullNameHcdv : null,
    fullNameTct : null,
    dateRequest : null,
    message : null,
    status : null,
    feedBackToVptct : null,
    feedBackToHcdv : null,
    ratingToVptct : null,
    ratingToUser: null,
    totalRequest: null,
    totalFulfill: null,
    sumTotalMoney: null,
    totalRequestHcdv : null,
    sumTotalMoneyHcdv : null,
    hcdvReasonReject : null,
    ldReasonReject : null,
    vptctReasonReject : null,
    vptctPostponeToTime : null,
    unitName : null,
    phoneNumber : null,
    listData : []
  }
  processIssuesStationery : ProcessIssuesStationery ={
      ldReasonReject: null,
    userName: null,
    issuesStationeryApprovedId: null,
    listIssuesStationeryItemId: [],
    listIssueStationeryId: [],
    vptctReason : null,
    vptctPostponeToTime: null,
    vptctReasonReject : null,
    hcdvReasonReject: null,
    totalFulfill : null
  } ;
  listSearchDetails : ItemModel[];
  requestParamUnit : RequestParamUnit ={
    requestID : null
  };
  checkStatus : number;
  ldReasonReject : string;
  reportStationery : ReportStationery;
  constructor(private stationeryService: StationeryService, private kitchenService: KitchenManagementService, private confirmationService: ConfirmationService,
              private messageService: MessageService, private renderer: Renderer2, private tokenStorage: TokenStorage, private title: Title, private app: AppComponent) {

  }

  ngOnInit() {
  }

  onStationeryApproveID(rowData){
    this.reportStationery = rowData;
  }

  onModelChange(rowData :ReportStationery, dataResponseUnit: DataResponseRatting) {
    console.log(dataResponseUnit);
    this.dataResponseUnit = dataResponseUnit;
    this.checkStatus = this.dataResponseUnit.status;
    if(this.checkStatus == 2){
      this.ldReasonReject = this.dataResponseUnit.ldReasonReject;
    }if(this.checkStatus == 5){
      this.ldReasonReject = this.dataResponseUnit.vptctReasonReject;
    }if(this.checkStatus == 8){
      this.ldReasonReject = this.dataResponseUnit.hcdvReasonReject;
    }
    this.rpItemsName = this.dataResponseUnit.fullName;
    this.rpItemsQuantity = this.dataResponseUnit.totalRequest;
    this.rpItemsDateRequest = this.dataResponseUnit.dateRequest;
    this.rpItemsMessage = this.dataResponseUnit.message;
    this.rpItemsQuantityRequest = this.dataResponseUnit.totalFulfill;
    this.rpItemsTotalMoney   = this.dataResponseUnit.sumTotalMoney;
    this.listSearchDetails = this.dataResponseUnit.listData;
    this.reportStationery = rowData;
    this.rpVPTCTTotal =  this.dataResponseUnit.sumTotalMoneyHcdv;
  }

  refuseAccept() {
    this.dialogRefuseRequest = true;
  }

  approveHandleAccept(){
    this.processIssuesStationery.issuesStationeryApprovedId = this.reportStationery.rpStationeryId;
    this.processIssuesStationery.listIssuesStationeryItemId = this.listSearchDetails;
    this.processIssuesStationery.hcdvReasonReject = this.hcdvReasonReject;
    this.stationeryService.refuseConfirmationPendingApprove(this.processIssuesStationery).subscribe(res => {
      if(res.status ==1 ){
        this.app.showSuccess('T??? ch???i x??c nh???n th??nh c??ng');
        this.result.emit(1);
      }else if(res.status ==4 ){
        this.app.showWarn('B???n ghi ???? ???????c ph?? duy???t tr?????c ????, th???c hi???n kh??ng th??nh c??ng');
        this.result.emit(1);
      } else {
        this.app.showWarn('Duy???t th???t b???i - C?? l???i kh??ng x??c ?????nh');
      }
    });
  }
  accept(){
    this.processIssuesStationery.issuesStationeryApprovedId = this.reportStationery.rpStationeryId;
    this.processIssuesStationery.listIssuesStationeryItemId = this.listSearchDetails;
    this.stationeryService.confirmationPendingApprove(this.processIssuesStationery).subscribe(res => {
      if(res.status ==1 ) {
        this.app.showSuccess('X??c nh???n th??nh c??ng');
        this.result.emit(1);
      }else if(res.status ==4 ) {
        this.app.showWarn('b???n ghi ???? ???????c ph?? duy???t tr?????c ????, th???c hi???n kh??ng th??nh c??ng');
        this.result.emit(1);
      } else {
        this.app.showWarn('Duy???t th???t b???i - C?? l???i kh??ng x??c ?????nh');
      }
    });
  }

  cancelRefuse(){
    this.dialogRefuseRequest = false;
  }
  onHideProcessPopup() {

  }
  onHideRefuseRequestPopup(){
    this.hcdvReasonReject = null;
  }
}
