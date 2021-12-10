import {Component, EventEmitter, Input, OnInit, Output, Renderer2} from '@angular/core';
import {
  DataResponseDetails, DataResponseRatting, DataResponseUnit, ItemModel, ReportStationery, RequestParamUnit2,
  VoteHCDV
} from "../../Entity/ReportStationery";
import {StationeryService} from "../../stationery.service";
import {KitchenManagementService} from "../../../kitchen-management/kitchen-management.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {AppComponent} from "../../../app.component";
import {Title} from "@angular/platform-browser";
import {TokenStorage} from "../../../shared/token.storage";

@Component({
  selector: 'app-vote-hcdv',
  templateUrl: './vote-hcdv.component.html',
  styleUrls: ['./vote-hcdv.component.css']
})
export class VoteHcdvComponent implements OnInit {
  @Input() rowData : ReportStationery;
  @Output() result: EventEmitter<any> = new EventEmitter<any>();
  rpTotalMoneyFullfill : number;
  dataResponseUnit :DataResponseRatting ={
    fullName : null,
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
  voteHCDV : VoteHCDV ={
    ratingToVptct : null,
    issuesStationeryApprovedId : null,
    feedbackToHcdv : null,
    feedbackToVptct : null,
    ratingToHcdv : null
  }
  requestParamUnit : RequestParamUnit2 = {
    requestID : null,
    pageSize : null,
    pageNumber : null
  };
  data :ReportStationery;
  rpItemsName : string;
  rpItemsQuantity : number;
  rpItemsDateRequest : Date;
  rpItemsMessage : string;
  rpItemsQuantityRequest : number;
  rpItemsTotalMoney : number;
  rpItemsStatus : number;
  listSearchDetails : ItemModel[];
  feedback : string;
  total_record : number;
  checkStatus : number;
  ldReasonReject : string;
  vptctReasonReject : string;
  hcdvReasonReject : string;
  ratting : number;
  convertStatus = ['Chờ LĐ phê duyệt', 'Lãnh đạo phê duyệt', 'Lãnh đạo từ chối', 'VP TCT tiếp nhận', 'VP TCT hoãn thực hiện', ' VP TCT Không thực hiện được',
    'VP TCT cấp phát cho HCĐV ', 'HCĐV xác nhận VPP','Từ chối xác nhận'];

  constructor(private stationeryService: StationeryService, private kitchenService: KitchenManagementService, private confirmationService: ConfirmationService,
              private messageService: MessageService, private renderer: Renderer2, private tokenStorage: TokenStorage, private title: Title, private app: AppComponent) { }

  ngOnInit() {
    this.ratting =5;
  }



  onModelChange(dataResponseUnit: ReportStationery){
    console.log(dataResponseUnit);
    this.requestParamUnit.requestID = dataResponseUnit.rpStationeryId;
    this.stationeryService.findDataToApproveProcess(this.requestParamUnit).subscribe(res => {
      this.dataResponseUnit = res.data;
      this.checkStatus = this.dataResponseUnit.status;
      if (this.checkStatus == 2) {
        this.ldReasonReject = this.dataResponseUnit.ldReasonReject;
      }
      if (this.checkStatus == 5) {
        this.ldReasonReject = this.dataResponseUnit.vptctReasonReject;
      }
      if (this.checkStatus == 8) {
        this.ldReasonReject = this.dataResponseUnit.hcdvReasonReject;
      }
      this.rpItemsName = this.dataResponseUnit.fullName;
      this.rpItemsQuantity = this.dataResponseUnit.totalRequest;
      this.rpItemsDateRequest = this.dataResponseUnit.dateRequest;
      this.rpItemsMessage = this.dataResponseUnit.message;
      this.rpItemsQuantityRequest = this.dataResponseUnit.totalFulfill;
      this.rpItemsTotalMoney = this.dataResponseUnit.sumTotalMoney;
      this.rpTotalMoneyFullfill = this.dataResponseUnit.sumTotalMoneyHcdv;
      this.rpItemsStatus = this.dataResponseUnit.status;
      this.listSearchDetails = this.dataResponseUnit.listData;
      console.log(this.listSearchDetails);
    });
  }
  voteHandleHCDV(){
     this.voteHCDV.issuesStationeryApprovedId = this.requestParamUnit.requestID;
    this.voteHCDV.ratingToVptct  = this.ratting;
    this.voteHCDV.feedbackToVptct = this.feedback;
    this.stationeryService.voteHcdv(this.voteHCDV).subscribe(res => {
      if(res.status ==1) {
        this.app.showSuccess('Cảm ơn Đ/c đã dành thời gian đánh giá');
        this.result.emit(1);
        this.feedback = null;
        this.ratting = 5;
      } else if(res.status == 4 ) {
        this.app.showWarn('Bản ghi đã được đánh giá trước đó, thực hiện không thành công');
        this.result.emit(1);
      } else {
        this.app.showWarn('Đánh giá thất bại');
      }
    });

  }
  onHideVoteHcdvPopup() {
    this.feedback = null;
    this.ratting =5;
  }


}
