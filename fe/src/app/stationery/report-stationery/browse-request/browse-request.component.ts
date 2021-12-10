import {Component, EventEmitter, OnInit, Output, Renderer2, ViewChild} from '@angular/core';
import {ParamToInsertEmployee, StationeryEmployee} from "../../Entity/ReportStationery";
import {StationeryService} from "../../stationery.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {KitchenManagementService} from "../../../kitchen-management/kitchen-management.service";
import {TokenStorage} from "../../../shared/token.storage";
import {Title} from "@angular/platform-browser";
import {AppComponent} from "../../../app.component";
import {EntityRqFindEmp} from "../../../bookcar/Entity/CarDetails";
import {BookcarService} from "../../../bookcar/bookcar.service";
import {ListTripAll} from "../../../bookcar/Entity/ListTrip";
import {AutoComplete} from "primeng/primeng";

@Component({
  selector: 'app-browse-request',
  templateUrl: './browse-request.component.html',
  styleUrls: ['./browse-request.component.css']
})
export class BrowseRequestComponent implements OnInit {

  @ViewChild('managerFld') private managerFld: AutoComplete;
  @Output() result: EventEmitter<any> = new EventEmitter<any>();

  entityRqFindEmp: EntityRqFindEmp = {pattern: '', fromIndex: 1, getSize: 20};
  fullNameDetails : number;
  note : string;
  listSearchDetails : StationeryEmployee[] = [];
  paramToInsertEmployee: ParamToInsertEmployee = { message : null, employeeUserName : null, listRequestID : [], approveUserName : null } ;
  qlttNameList: ListTripAll[];
  qlttName: ListTripAll;

  isDisabledButton: boolean;

  constructor(private stationeryService: StationeryService, private kitchenService: KitchenManagementService, private confirmationService: ConfirmationService,
              private messageService: MessageService, private renderer: Renderer2, private tokenStorage: TokenStorage, private title: Title, private app: AppComponent) { }

  ngOnInit() {

  }

  onModelChange(listChooseEmployee : StationeryEmployee[], callback: any) {
    var total = 0;
    let stationeryIds: string[] = [];
    this.listSearchDetails = listChooseEmployee;
    for (let i = 0; i < this.listSearchDetails.length; i++) {
      total = total + this.listSearchDetails[i].unitPrice * this.listSearchDetails[i].totalRequest;
    }

    this.fullNameDetails = total;
    callback();
  }

  doCancel(rowData: StationeryEmployee) {
    if(this.listSearchDetails) {
      for(let i = 0; i < this.listSearchDetails.length; i++) {
        if(rowData.issuesStationeryItemId === this.listSearchDetails[i].issuesStationeryItemId) {
          this.fullNameDetails = this.fullNameDetails - this.listSearchDetails[i].totalMoneyRequest;
          this.listSearchDetails.splice(i, 1);
          break;
        }
      }
    }
    if(this.listSearchDetails.length > 0) {
      let total = 0;
      for (let i = 0; i < this.listSearchDetails.length; i++) {
        total = total + this.listSearchDetails[i].unitPrice * this.listSearchDetails[i].totalRequest;
      }
      this.fullNameDetails = total;
    }
  }

  handleUpdateRequest() {
    let component = this;

    if(!this.qlttName || !this.qlttName.userName) {
      this.app.showWarn('Đ/c chưa chọn lãnh đạo ký duyệt');
      return;
    }

    if(!this.listSearchDetails || this.listSearchDetails.length < 1) {
      this.app.showWarn('Không có yêu cầu nào để trình ký');
      return;
    }

    if(!this.note || this.note.length < 1) {
      this.app.showWarn('Nội dung không được để trống');
      return;
    }

    this.paramToInsertEmployee.listRequestID =[];
    for ( let i =0  ; i< this.listSearchDetails.length ; i++){
      this.paramToInsertEmployee.listRequestID.push(this.listSearchDetails[i].issuesStationeryItemId);
    }
    this.paramToInsertEmployee.message = this.note;
    this.paramToInsertEmployee.approveUserName = this.qlttName.userName;

    this.isDisabledButton = true;
    this.stationeryService.updateIssuesStationeryItems(this.paramToInsertEmployee).subscribe(res => {
      if(res.status === 1 ) {
        this.app.showSuccess('Đã trình ký các yêu cầu được lựa chọn');
        this.paramToInsertEmployee.listRequestID = [];
        this.result.emit(1);
      } else if(res.status === 3) {
        this.app.showWarn('Yêu cầu đã được trình ký trước đó, thực hiện không thành công');
        this.result.emit(1);
      } else if(res.status === 17) {
        this.app.showWarn('Đã quá thời hạn đăng ký văn phòng phẩm của tháng này');
      } else  if(res.status === 18) {
        this.app.showWarn('Đ/c cần xác nhận yêu cầu (' + res.data + ') đã hoàn thành trước khi tạo yêu cầu mới');
      } else if(res.status === 19) {
        this.app.showWarn('Trình ký có nhiều hơn một đơn vị hoặc nhiều hơn một vị trí');
      } else if(res.status === 20) {
        this.app.showWarn('Trình ký có nhiều hơn một đơn vị hoặc nhiều hơn một vị trí');
      } else if(res.status === 15) {
        this.app.showWarn('Vượt quá hạn mức');
      } else {
        this.app.showWarn('Trình ký thất bại');
      }
      this.isDisabledButton = false;
    });
  }

  handleCancel() {
    this.paramToInsertEmployee.listRequestID = [];
    this.qlttName = null;
    this.note = null;
    this.result.emit(1);
    this.fullNameDetails = 0;
    this.qlttNameList = null;
    this.isDisabledButton = false;
  }

  onDropdownClick(event) {
    event.originalEvent.preventDefault();
    //event.originalEvent.stopPropagation();
    this.managerFld.show();
    this.getManager(event);
  }

  getManager(event) {
    this.entityRqFindEmp.pattern = event.query;
    this.entityRqFindEmp.role = ['PMQT_QL'];
    this.stationeryService.getListManager(this.entityRqFindEmp).subscribe(res => {
      this.qlttNameList = res.data;
    });
  }
}
