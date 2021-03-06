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
      this.app.showWarn('??/c ch??a ch???n l??nh ?????o k?? duy???t');
      return;
    }

    if(!this.listSearchDetails || this.listSearchDetails.length < 1) {
      this.app.showWarn('Kh??ng c?? y??u c???u n??o ????? tr??nh k??');
      return;
    }

    if(!this.note || this.note.length < 1) {
      this.app.showWarn('N???i dung kh??ng ???????c ????? tr???ng');
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
        this.app.showSuccess('???? tr??nh k?? c??c y??u c???u ???????c l???a ch???n');
        this.paramToInsertEmployee.listRequestID = [];
        this.result.emit(1);
      } else if(res.status === 3) {
        this.app.showWarn('Y??u c???u ???? ???????c tr??nh k?? tr?????c ????, th???c hi???n kh??ng th??nh c??ng');
        this.result.emit(1);
      } else if(res.status === 17) {
        this.app.showWarn('???? qu?? th???i h???n ????ng k?? v??n ph??ng ph???m c???a th??ng n??y');
      } else  if(res.status === 18) {
        this.app.showWarn('??/c c???n x??c nh???n y??u c???u (' + res.data + ') ???? ho??n th??nh tr?????c khi t???o y??u c???u m???i');
      } else if(res.status === 19) {
        this.app.showWarn('Tr??nh k?? c?? nhi???u h??n m???t ????n v??? ho???c nhi???u h??n m???t v??? tr??');
      } else if(res.status === 20) {
        this.app.showWarn('Tr??nh k?? c?? nhi???u h??n m???t ????n v??? ho???c nhi???u h??n m???t v??? tr??');
      } else if(res.status === 15) {
        this.app.showWarn('V?????t qu?? h???n m???c');
      } else {
        this.app.showWarn('Tr??nh k?? th???t b???i');
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
