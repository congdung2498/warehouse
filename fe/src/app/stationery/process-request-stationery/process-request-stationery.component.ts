import {Component, ElementRef, EventEmitter, Input, OnInit, Output, Renderer2, ViewChild} from '@angular/core';
import {
  CompleteStationey,
  DataResponseDetails, DataResponseRatting, DataResponseUnit, ItemModel, ProcessIssuesStationery, ReportStationery,
  RequestParamUnit
} from "../Entity/ReportStationery";
import {DateTimeUtil} from "../../../common/datetime";
import {DatePipe} from "@angular/common";
import {StationeryService} from "../stationery.service";
import {KitchenManagementService} from "../../kitchen-management/kitchen-management.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {TokenStorage} from "../../shared/token.storage";
import {Title} from "@angular/platform-browser";
import {AppComponent} from "../../app.component";
import {Calendar} from "primeng/primeng";

@Component({
  selector: 'app-process-request-stationery',
  templateUrl: './process-request-stationery.component.html',
  styleUrls: ['./process-request-stationery.component.css']
})
export class ProcessRequestStationeryComponent implements OnInit {

  @Input() rowData : ReportStationery;
  @Output() result: EventEmitter<any> = new EventEmitter<any>();
  @ViewChild('inputVptctReason') private inputVptctReason: ElementRef;
  @ViewChild('inputVptctReasonReject') private inputVptctReasonReject: ElementRef;
  @ViewChild('createStartTimeFld') private createStartTimeFld: Calendar;



  notChar: RegExp = /[^0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;
  vn = DateTimeUtil.vn;
  fullName : string;
  unitName : string;
  phoneNumber : string;
  detailStationery : string;
  requestParamUnit : RequestParamUnit ={
    requestID : null
  };

  completeStationey : CompleteStationey ={
    stationeryId : null,
    issuesStationeryApprovedId : null,
    quantity : null
  }
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
  reportStationery : ReportStationery ;
  approveId  : string;
  vptctReason : string;
  vptctPostponeToTime : Date;
  vptctReasonReject : string;
  processIssuesStationery : ProcessIssuesStationery ={
    issuesStationeryIdOld : null,
    ldReasonReject : null,
    userName: null,
    issuesStationeryApprovedId: null,
    listIssuesStationeryItemId: [],
    listIssueStationeryId: [],
    vptctReason : null,
    vptctPostponeToTime: null,
    vptctReasonReject : null,
    hcdvReasonReject: null,
  } ;
  fromDate : Date;
  minDate: Date = new Date();
  listSearchDetails : ItemModel[];
  listSearchProcessDetails : ItemModel[];
  dataResponseUnitUpdate :DataResponseRatting ={
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
  isShow : boolean;

  constructor(private stationeryService: StationeryService, private kitchenService: KitchenManagementService, private confirmationService: ConfirmationService,
              private messageService: MessageService, private renderer: Renderer2, private tokenStorage: TokenStorage, private title: Title, private app: AppComponent) { }

  ngOnInit() {
    this.minDate = new Date();
  }

  onModelChange(dataResponseUnit: DataResponseRatting ,dataResponseUnitOld) {
    console.log(dataResponseUnit);
    if(dataResponseUnit.status == 1){
      this.isShow = true;
    } else {
      this.isShow = false;
    }
    this.dataResponseUnit = dataResponseUnit;
    console.log(dataResponseUnit);
    this.fullName = this.dataResponseUnit.fullName;
    this.unitName = this.dataResponseUnit.unitName;
    this.phoneNumber = this.dataResponseUnit.phoneNumber;
    this.listSearchProcessDetails = dataResponseUnitOld.listData;
    console.log(this.listSearchProcessDetails);
    this.listSearchDetails = dataResponseUnit.listData;
    if(this.dataResponseUnit.vptctReason != null) {
      this.vptctReason = this.dataResponseUnit.vptctReason;
    }if(this.dataResponseUnit.vptctPostponeToTime != null){
      this.vptctPostponeToTime = new Date(this.dataResponseUnit.vptctPostponeToTime);
    }
  }
  reportId(rowData: ReportStationery) {
    this.approveId = rowData.rpStationeryId;
    this.requestParamUnit.requestID =rowData.rpStationeryId;
    this.stationeryService.findInfoRequestDetailProcessDetails(this.requestParamUnit).subscribe(item => {
        this.dataResponseUnitUpdate = item.data;

    });
  }

  doConfirm(){
    this.processIssuesStationery.issuesStationeryApprovedId = this.approveId;
    this.processIssuesStationery.listIssuesStationeryItemId = this.dataResponseUnitUpdate.listData;
    this.processIssuesStationery.issuesStationeryIdOld = this.approveId;
      this.stationeryService.confirmReceiptIssuesStationery(this.processIssuesStationery).subscribe(
          res => {
            if(res.status == 1){
              this.app.showSuccess('Xác nhận tiếp nhận thành công');
              this.result.emit(2);
            }else if(res.status == 4){
              this.app.showWarn('Yêu cầu đã được thực hiện trước đó, thực hiện không thành công');
              this.result.emit(2);
            } else {
              this.app.showWarn('Xác nhận tiếp nhận thất bại - lỗi không xác định');
            }
          }
      );
  }
  validateConfirm(itemOld : ItemModel[],itemNew : ItemModel[]){
    for(let i = 0 ; i < itemOld.length ; i++) {
      for(let j =0 ; j< itemNew.length; j++){
        if(itemNew[j].quantity > itemOld[i].quantity){
          this.app.showWarn('Số lượng đáp ứng không thể lớn hơn số lượng yêu cầu');
          return false;
        }
      }
    }
    return true;
  }

  postponedImplementation() {
    this.processIssuesStationery.issuesStationeryApprovedId = this.approveId;
    this.processIssuesStationery.listIssuesStationeryItemId = this.dataResponseUnitUpdate.listData;
    this.processIssuesStationery.issuesStationeryIdOld = this.approveId;
    if(this.vptctReason) {
      let s : string = this.vptctReason.replace(/  +/g, ' ');
      this.processIssuesStationery.vptctReason = s;
    }
    this.processIssuesStationery.vptctPostponeToTime = this.vptctPostponeToTime;
    let isvalid = this.validatePostponedImplementation(this.processIssuesStationery, this.createStartTimeFld );
    if(!isvalid) {
      return;
    }

    this.stationeryService.postponedImplementationIssuesItems(this.processIssuesStationery).subscribe(
      res => {
        if(res.status ==1){
          this.app.showSuccess('Đã hoãn bàn giao văn phòng phẩm');
          this.result.emit(2);
        }else if(res.status == 4) {
          this.app.showWarn('Yêu cầu đã được thực hiện trước đó, thực hiện không thành công');
          this.result.emit(2);
        }else {
          this.app.showWarn('Hoãn thực hiện thất bại - có lỗi không xác định');
        }
      }
    );
  }
  validatePostponedImplementation(processIssuesStationery : ProcessIssuesStationery , startTimeByPlan: Calendar ){
    if(processIssuesStationery.vptctReason == null || processIssuesStationery.vptctReason.trim().length == 0 ){
      this.app.showWarn('Lý do hoãn thực hiện không được để trống');
      this.inputVptctReason.nativeElement.value = '';
      this.inputVptctReason.nativeElement.focus();
      return false;
    }
    if(!processIssuesStationery.vptctPostponeToTime){
      startTimeByPlan.domHandler.findSingle(startTimeByPlan.el.nativeElement, 'input').focus();
      this.app.showWarn('Thời gian hoãn không được để trống');
      return false;
    }
    if(processIssuesStationery.vptctPostponeToTime <= new Date()) {
      startTimeByPlan.domHandler.findSingle(startTimeByPlan.el.nativeElement, 'input').focus();
      this.app.showWarn('Thời gian ra phải lớn hơn thời gian hiện tại');
      return false;
    }

    return true;
  }

  notPossible(){
    this.processIssuesStationery.issuesStationeryApprovedId = this.approveId;
    this.processIssuesStationery.listIssuesStationeryItemId = this.dataResponseUnitUpdate.listData;
    this.processIssuesStationery.issuesStationeryIdOld = this.approveId;
    if(this.vptctReasonReject != null){
    let s : string = this.vptctReasonReject.replace(/ +/g, ' ');
    this.processIssuesStationery.vptctReasonReject = s;
    }
    let isvalid = this.validateNotPossibleApprove(this.processIssuesStationery);
    if(!isvalid){
      return;
    }
    this.stationeryService.notPossibleApprove(this.processIssuesStationery).subscribe(res => {
        if(res.status ==1) {
          this.app.showSuccess('Đã từ chối bàn giao VPP');
          this.result.emit(1);
        }else if(res.status == 4) {
        this.app.showWarn('Yêu cầu đã được thực hiện trước đó, thực hiện không thành công');
        this.result.emit(2);
      } else {
          this.app.showWarn('Cập nhật thất bại - có lỗi không xác định');
        }
      }
    );

  }
  validateNotPossibleApprove(processIssuesStationery : ProcessIssuesStationery){
    if(processIssuesStationery.vptctReasonReject == null || processIssuesStationery.vptctReasonReject.trim() ==''){
      this.app.showWarn('Lý do không thực hiện được không được đẻ trống');
      this.inputVptctReasonReject.nativeElement.value = '';
      this.inputVptctReasonReject.nativeElement.focus();
      return false;
    }
    return true;
  }
  selectFromDate(){

  }

  complete(){
    this.processIssuesStationery.issuesStationeryApprovedId = this.approveId;
    this.processIssuesStationery.listIssuesStationeryItemId = this.dataResponseUnit.listData;
    this.processIssuesStationery.issuesStationeryIdOld = this.approveId;
    this.processIssuesStationery.vptctReasonReject = this.vptctReasonReject;
    let isValid  = this.validateIsCompleteIssusStationery(this.processIssuesStationery);
    if(!isValid){
      return;
    }
    this.stationeryService.isCompleteIssusStationery(this.processIssuesStationery).subscribe(
      res => {
        if(res.status == 5){
          this.app.showWarn('Số lượng đáp ứng không thể lớn hơn số lượng yêu cầu');
        }
        else if(res.status ==1){
          this.app.showSuccess('Đã bàn giao yêu cầu VPP');
          this.result.emit(1);
        }else if(res.status == 4) {
          this.app.showWarn('Yêu cầu đã được thực hiện trước đó, thực hiện không thành công');
          this.result.emit(2);
        } else {
          this.app.showWarn('Bàn giao thất bại - Có lỗi không xác định');
        }
      }
    );
  }
  validateIsCompleteIssusStationery(processIssuesStationery : ProcessIssuesStationery) {
    if(processIssuesStationery.listIssuesStationeryItemId && processIssuesStationery.listIssuesStationeryItemId.length > 0)
      for(let i = 0; i < processIssuesStationery.listIssuesStationeryItemId.length; i++) {
        if(!processIssuesStationery.listIssuesStationeryItemId[i].quantity ||
                      processIssuesStationery.listIssuesStationeryItemId[i].quantity === 0) {
          this.app.showWarn('Số lượng không được để trống');
          return false;
        }
      }
    return true;
  }

  onHideProcessPopup() {
    this.vptctReason = null;
    this.vptctPostponeToTime = null;
    this.vptctReasonReject = null;
  }
  clearFromDate(event) {
    if (this.vptctPostponeToTime) {
      event.stopPropagation();
      this.vptctPostponeToTime = null;
    }
  }


}
