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
  convertStatus = ['Ch??? L?? ph?? duy???t', 'L??nh ?????o ph?? duy???t', 'L??nh ?????o t??? ch???i', 'VP TCT ti???p nh???n', 'VP TCT ho??n th???c hi???n', ' VP TCT Kh??ng th???c hi???n ???????c',
    'VP TCT c???p ph??t cho HC??V ', 'HC??V x??c nh???n VPP','T??? ch???i x??c nh???n'];

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
        this.app.showSuccess('C???m ??n ??/c ???? d??nh th???i gian ????nh gi??');
        this.result.emit(1);
        this.feedback = null;
        this.ratting = 5;
      } else if(res.status == 4 ) {
        this.app.showWarn('B???n ghi ???? ???????c ????nh gi?? tr?????c ????, th???c hi???n kh??ng th??nh c??ng');
        this.result.emit(1);
      } else {
        this.app.showWarn('????nh gi?? th???t b???i');
      }
    });

  }
  onHideVoteHcdvPopup() {
    this.feedback = null;
    this.ratting =5;
  }


}
