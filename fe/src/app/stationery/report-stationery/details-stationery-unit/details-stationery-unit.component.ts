import {Component, EventEmitter, Input, OnInit, Output, Renderer2} from '@angular/core';
import {
  DataResponseDetails, DataResponseRatting, DataResponseUnit, ItemModel, ReportStationery,
  RequestParamUnit
} from "../../Entity/ReportStationery";
import {DatePipe} from "@angular/common";
import {StationeryService} from "../../stationery.service";
import {KitchenManagementService} from "../../../kitchen-management/kitchen-management.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {Title} from "@angular/platform-browser";
import {AppComponent} from "../../../app.component";
import {TokenStorage} from "../../../shared/token.storage";

@Component({
  selector: 'app-details-stationery-unit',
  templateUrl: './details-stationery-unit.component.html',
  styleUrls: ['./details-stationery-unit.component.css']
})
export class DetailsStationeryUnitComponent implements OnInit {
  @Input() rowData : ReportStationery;
  @Output() result: EventEmitter<any> = new EventEmitter<any>();
  colsBeta = [
    {field: "", header: "STT", width: "10%"},
    {field: "fullName", header: "Người yêu cầu", width: "35%"},
    {field: "dateRequest", header: "Ngày Yêu Cầu", width: "20%"},
    {field: "stationeryName", header: "Tên VPP", width: "35%"},
    {field: "quantity", header: "Số Lượng ", width: "20%"},
    {field: "price", header: "Đơn giá", width: "20%"},
    {field: "totalMoney", header: "Số Tiền", width: "20%"},

  ];
  rpItemsName : string;
  rpItemsQuantity : number;
  rpItemsDateRequest : string;
  rpItemsMessage : string;
  rpItemsQuantityRequest : number;
  rpItemsTotalMoney : number;
  rpRatingVPTCT : number;
  rpFeedbackToVPTCT : string;
  rpRatingHCDV : number;
  rpFeedbackToHCDV : string;
  dataResponseUnit :DataResponseRatting ={
    fullNameHcdv : null,
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
    hcdvReasonReject : null,
    ldReasonReject : null,
    vptctReasonReject : null,
    vptctPostponeToTime : null,
    unitName : null,
    phoneNumber : null,
    listData : []
  }
  ldReasonReject : string;
  statusDtails: number;
  total_record: number;
  listSearchDetails: ItemModel[];
  requestParamUnit: RequestParamUnit ={
    requestID : null
  };
  convertStatus = ['Chờ LĐ phê duyệt', 'Lãnh đạo phê duyệt', 'Lãnh đạo từ chối', 'VP TCT tiếp nhận', 'VP TCT hoãn thực hiện', ' VP TCT Không thực hiện được',
    'VP TCT cấp phát cho HCĐV', 'HCĐV xác nhận VPP','Từ chối xác nhận'];
  constructor(private stationeryService: StationeryService, private kitchenService: KitchenManagementService, private confirmationService: ConfirmationService,
              private messageService: MessageService, private renderer: Renderer2, private tokenStorage: TokenStorage, private title: Title, private app: AppComponent) { }

  ngOnInit() {
  }

  onModelChange(dataResponseUnit: DataResponseRatting, rowData : ReportStationery){
    console.log(dataResponseUnit);
    this.dataResponseUnit = dataResponseUnit;
    this.statusDtails = rowData.status;
    if(this.statusDtails == 1){
      this.ldReasonReject  = dataResponseUnit.message;
    }
    if(this.statusDtails == 2){
      if(dataResponseUnit.ldReasonReject != null){
        this.ldReasonReject  = dataResponseUnit.ldReasonReject;
      }
    }if(this.statusDtails == 4){
      if(dataResponseUnit.vptctReason != null){
        this.ldReasonReject  = dataResponseUnit.vptctReason;
      }
    }if(this.statusDtails == 5){
      if(dataResponseUnit.vptctReasonReject != null){
        this.ldReasonReject  = dataResponseUnit.vptctReasonReject;
      }
    }if(this.statusDtails == 8){
      if(dataResponseUnit.hcdvReasonReject != null){
        this.ldReasonReject  = dataResponseUnit.hcdvReasonReject;
      }
    }

    this.rpItemsName = this.dataResponseUnit.fullName;
    this.rpItemsQuantity = this.dataResponseUnit.totalRequest;
    this.rpItemsDateRequest = new DatePipe("en-US").transform(this.dataResponseUnit.dateRequest, 'dd/MM/yyyy');
    this.rpItemsMessage = this.dataResponseUnit.message;
    this.rpItemsQuantityRequest = this.dataResponseUnit.totalFulfill;
    this.rpItemsTotalMoney = this.dataResponseUnit.sumTotalMoney;
    this.listSearchDetails = this.dataResponseUnit.listData;
    console.log(this.listSearchDetails);
    this.rpRatingVPTCT = this.dataResponseUnit.ratingToVptct;
    this.rpFeedbackToVPTCT = this.dataResponseUnit.feedBackToVptct;
    this.rpRatingHCDV = this.dataResponseUnit.ratingToUser;
    this.rpFeedbackToHCDV = this.dataResponseUnit.feedBackToHcdv;

  }
  closeDetailsUnit(){
    this.result.emit(1);
  }
  onHideDetailsPopup(){
    this.ldReasonReject = null;
    this.rpItemsName = null;
    this.rpItemsQuantity = null;
    this.rpItemsDateRequest = null;
    this.rpItemsQuantityRequest = null;
    this.rpItemsMessage = null;
    this.rpItemsTotalMoney = null;
    this.listSearchDetails = null;
    this.rpFeedbackToVPTCT = null;
    this.rpRatingVPTCT = null;
    this.rpRatingHCDV = null;
    this.rpFeedbackToHCDV = null;
  }
}
