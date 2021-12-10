import {Component, OnInit, Renderer2, ViewChild} from '@angular/core';
import {DateTimeUtil} from "../../../common/datetime";
import {CommonUtils} from "../../../common/commonUtils";
import {ConfirmationService, MessageService, TreeNode} from "primeng/api";
import {Condition} from "../Entity/ConditionSearch";
import {ReportserviceService} from "../reportservice.service";
import {KitchenManagementService} from "../../kitchen-management/kitchen-management.service";
import {UnitTreeModel} from "../../../common/unittree";
import {TokenStorage} from "../../shared/token.storage";
import {Table} from "primeng/table";
import {Employee} from "../../Entity/Employee";
import {InOutEmployeeService} from "../inOutEmployeeService.service";
import {Checking, SystemCode, UpdatingChecking} from "../Entity/Checking";
import {UserInfo} from "../../shared/UserInfo";
import {AppComponent} from "../../app.component";
import {saveAs} from "file-saver";
import {AutoComplete, Calendar, Dropdown, MultiSelect} from "primeng/primeng";
import { trigger, state, style, transition, animate } from '@angular/animations';
import {Title} from "@angular/platform-browser";

declare const require: any;
const pixelWidth = require('string-pixel-width');

@Component({
  selector: 'app-checking',
  templateUrl: './checking.component.html',
  styleUrls: ['./checking.component.css'],
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
export class CheckingComponent implements OnInit {


  @ViewChild('myDrop') myDrop: MultiSelect;
  @ViewChild('myTable') myTable: Table;
  @ViewChild('createWithEmployeeFld') private createWithEmployeeFld: AutoComplete;
  @ViewChild('withEmployeeFld') withEmployeeFld: AutoComplete;
  @ViewChild('createPlaceFld') createPlaceFld: Dropdown;
  @ViewChild('createReasonFld') createReasonFld: Dropdown;
  @ViewChild('createApproverFld') createApproverFld: AutoComplete;
  @ViewChild('employeeFld') employeeFld: AutoComplete;

  @ViewChild('updatePlaceFld') updatePlaceFld: Dropdown;
  @ViewChild('updateReasonFld') updateReasonFld: Dropdown;
  @ViewChild('updateApproverFld') updateApproverFld: AutoComplete;

  @ViewChild('createStartTimeFld') createStartTimeFld: Calendar;
  @ViewChild('createEndTimeFld') createEndTimeFld: Calendar;
  @ViewChild('updateStartTimeFld') updateStartTimeFld: Calendar;
  @ViewChild('updateEndTimeFld') updateEndTimeFld: Calendar;
  @ViewChild('barcodeInput') barcodeInput;

  vn = DateTimeUtil.vn;
  status = CommonUtils.STATUS;
  statusLabels = CommonUtils.STATUS_LABELS;

  fromDate: Date;
  toDate: Date;
  minDate: Date = new Date();
  fieldSetTitle: string;

  selectedStatus: string[];
  employees: Employee[];
  selectedEmployee: Employee;
  employeeInfo: UserInfo;
  employeeUnit: string;

  checking: Checking;
  places: SystemCode[];
  selectedPlace: SystemCode;
  reasons: SystemCode[];
  selectedReason: SystemCode;
  selectedWithEmployees: Employee[];
  selectedApprove: Employee;

  unitSelectors: TreeNode[];
  selectedUnits: TreeNode[];

  checkings: Checking[];
  searchingModel: Condition;
  imageData: any = '';

  totalRecords: number = 0;
  startRow: number = 0;
  rowSize: number = 0;
  approveTotal: number = 0;

  isShowApproveBtn: boolean;
  isShowApproveChk: boolean;
  isShowApproveRow: boolean;
  isDisableEmployeeFld: boolean;
  isShowDialog: boolean;
  isShowUpdateDialog: boolean;
  isShowReasonOfGuard: boolean;
  isCheckedLate: boolean;
  isEdit: boolean;
  isMoreTime: boolean;
  isCheckAll: boolean;
  isShowUpdateButton: boolean = true;
  isShowAddMoreTimeButton: boolean = true;
  isDisableReasonOfGuard: boolean = true;
  isDisableStartTime: boolean;

  isApprove: boolean;
  isGetIn: boolean;
  isGetOut: boolean;
  isCopy: boolean;
  isLateOut: boolean;
  isEarlyOut: boolean;
  isEarlyDay: boolean;

  isManager: boolean;
  isEmployee: boolean;
  isAdmin: boolean;
  isSecurity: boolean;

  selectedCheckingIds: string[];
  selectedChecking: Checking;

  editReasonOfApproverFld: string;
  editReasonDetailFld: string;
  editStartTimeByPlan: Date;
  editEndTimeByPlan: Date;
  statusLabel: string;

  textShow: string;
  isShowCheckAllStatus: boolean = false;
  len: number;
  maxLenStatus: number;
  barcode: string;
  isCheckingEarlyOrLate: any;
  isCheckingEarlyOrLateInput: any;


  constructor(private checkingService: InOutEmployeeService,
              private reportService: ReportserviceService,
              private kitchenService: KitchenManagementService,
              private unitTree: UnitTreeModel, private tokenService: TokenStorage, private title: Title,
              private renderer: Renderer2, private app: AppComponent, private confirmationService: ConfirmationService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.title.setTitle('Quản lý ra vào _ PMQTVP');
    this.fromDate = new Date();
    this.fromDate.setHours(0, 0, 0, 0);
    this.toDate = new Date();
    this.toDate.setHours(23, 59, 59, 59);
    this.employeeInfo = this.tokenService.getUserInfo();
    this.checking = {};
    this.selectedChecking = {};
    this.textShow = 'Chọn tất cả';
    this.maxLenStatus = 431;

    if (this.tokenService.checkRole('PMQT_ADMIN') || this.tokenService.checkRole('PMQT_CVP')) this.initAdmin();
    else if (this.tokenService.checkRole('PMQT_QL')) this.initManager();
    else if (this.tokenService.checkRole('PMQT_Canhve')) this.initSecurity();
    else this.initEmployee();

    this.getSelectingModel();
    this.isCheckingEarlyOrLate = {} ;
    this.isCheckingEarlyOrLateInput = {'opacity': '0.8','width':'100%'};
  }

  initAdmin() {
    this.isShowApproveBtn = true;
    this.isShowApproveChk = true;
    this.isShowApproveRow = false;
    this.isAdmin = true;
    this.searchingModel = {startDate: this.fromDate, endDate: this.toDate, startRow: 0, rowSize: 10};
    this.getCheckingList({loadUnit: true, unitId: null, config: this.searchingModel});
  }

  initManager() {
    this.isShowApproveBtn = true;
    this.isShowApproveChk = true;
    this.isShowApproveRow = false;
    this.isManager = true;
    this.searchingModel = {startDate: this.fromDate, endDate: this.toDate, startRow: 0, rowSize: 10, listUnit: []};

    this.checkingService.getParentUnitId(this.employeeInfo.unitId.toString()).subscribe(resp => {
      if(resp) {
        this.searchingModel.listUnit.push(resp.data);
        this.getCheckingList({loadUnit: true, unitId: resp.data, config: this.searchingModel});
      }
    });
  }

  initEmployee() {
    this.isShowApproveBtn = false;
    this.isShowApproveChk = false;
    this.isShowApproveRow = false;
    this.isDisableEmployeeFld = true;
    this.isEmployee = true;
    this.selectedEmployee = {
      userName: this.employeeInfo.userName.toString(),
      fullName: this.employeeInfo.fullName.toString()
    };
    this.searchingModel = {startDate: this.fromDate, endDate: this.toDate, startRow: 0, rowSize: 10, personInfo: this.selectedEmployee.userName};
    this.employeeUnit = this.employeeInfo.unitName.toString();
    this.getCheckingList({loadUnit: true, unitId: null, config: this.searchingModel});
  }

  initSecurity() {
    this.isShowApproveBtn = false;
    this.isShowApproveChk = false;
    this.isShowApproveRow = true;
    this.isSecurity = true;
    this.searchingModel = {startDate: this.fromDate, endDate: this.toDate, startRow: 0, rowSize: 10};
    this.getCheckingList({loadUnit: true, unitId: null, config: this.searchingModel});
  }

  ngAfterViewInit() {
    if(this.barcodeInput) this.barcodeInput.nativeElement.focus();
  }

  onHideStatusPanel() {
    this.isShowCheckAllStatus = false;
  }

  onShowStatusPanel() {
    const child = this.myDrop.el.nativeElement.children[0].children[3];
    this.renderer.setStyle(child, 'max-width', 'inherit');
    this.renderer.setStyle(child, 'width', 'inherit');
    this.isShowCheckAllStatus = true;
  }

  onChangeStatus() {
    if (this.selectedStatus != null && this.selectedStatus.length === this.status.length) {
      this.textShow = 'Bỏ chọn tất cả';
    } else {
      this.textShow = 'Chọn tất cả';
    }

    const str: string[] = [];
    const child = this.myDrop.el.nativeElement.children[0].children[1];
    this.len = 50 + pixelWidth(',...', { size: 16 });
    if (this.selectedStatus != null && this.selectedStatus !== undefined && this.selectedStatus.length > 0) {
      for (let i = 0; i < this.selectedStatus.length; i++) {
        this.len += pixelWidth(this.status[this.selectedStatus[i]].label, { size: 16 });
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
    } else {
      child.children[0].innerText = '-- Chọn trạng thái --';
    }
  }

  onShowDetailForCopy(checking: Checking) {
    this.fieldSetTitle = 'Chi tiết đăng ký ra vào';
    this.isCopy = true;
    this.editChecking(checking);
    this.isShowUpdateDialog = true;
    this.isShowReasonOfGuard = true;
    this.isDisableReasonOfGuard = true;
    this.isDisableStartTime = true;
  }

  onHideEditDialog() {
    this.isCopy = false;
    this.isLateOut = false;
    this.isEarlyOut = false;
    this.isApprove =false;
    this.isGetIn = false;
    this.isGetOut = false;
    this.isEdit = false;
    this.isShowUpdateButton = false;
    this.isShowAddMoreTimeButton = false;
    this.isEarlyDay = false;
    this.isShowReasonOfGuard = false;
    this.isDisableReasonOfGuard = false;
    this.isDisableStartTime = false;

    this.editReasonDetailFld = null;
    this.editReasonOfApproverFld = null;
    this.editStartTimeByPlan = null;
    this.editEndTimeByPlan = null;
    this.statusLabel = null;
    if(this.barcodeInput) this.barcodeInput.nativeElement.focus();
    this.isCheckingEarlyOrLate = {};
    this.imageData = '';
  }

  onCopy() {
    this.isShowUpdateDialog = false;
    this.resetCreateChecking();
    this.checking = new Checking();
    this.checking.isLate = this.selectedChecking.isLate;
    this.checking.destination = this.selectedChecking.destination;
    this.checking.reasonRegistion = this.selectedChecking.reasonRegistion;
    this.checking.reasonDetail = this.selectedChecking.reasonDetail;
    this.checking.approverUserName = this.selectedChecking.approverUserName;
    this.checking.approverFullName = this.selectedChecking.approverFullName;
    this.checking.inOutEmployeeList = this.selectedChecking.inOutEmployeeList;

    for(let i = 0; i < this.places.length; i++) {
      if(this.selectedChecking.destination === this.places[i].codeName) this.selectedPlace = this.places[i];
    }

    for(let i = 0; i < this.reasons.length; i++) {
      if(this.selectedChecking.reasonRegistion === this.reasons[i].codeName) this.selectedReason = this.reasons[i];
    }

    if(this.selectedChecking.isLate === 1) this.isCheckedLate = true;
    this.selectedApprove = {userName: this.selectedChecking.approverUserName, fullName: this.selectedChecking.approverFullName};

    if(this.checking.inOutEmployeeList) {
      let userNames = this.checking.inOutEmployeeList.split(',');
      this.checkingService.getWithEmployee(userNames).subscribe(response => {
        this.selectedWithEmployees = response.data;
      });
    }
    this.isShowDialog = true;

    this.getImageToShow(this.selectedChecking.empCode);
  }

  onEditChecking(checking: Checking) {
    this.fieldSetTitle = 'Sửa đăng ký ra vào';
    this.isEdit = true;
    this.isShowUpdateButton = true;
    if(new Date(checking.endTimeByPlan).getTime() < new Date().getTime()) this.isShowUpdateButton = false;
    this.isMoreTime = true;
    this.isShowUpdateDialog = true;
    this.isShowReasonOfGuard = false;
    this.editChecking(checking);
    this.getImageToShow(checking.empCode);
  }

  onAddMoreTimeChecking(checking: Checking) {
    this.fieldSetTitle = 'Gia hạn đăng ký vào ra';
    this.isEdit = false;
    this.isMoreTime = true;
    this.isShowAddMoreTimeButton = true;
    if(new Date(checking.endTimeByPlan).getTime() < new Date().getTime()) this.isShowAddMoreTimeButton = false;
    this.isShowUpdateDialog = true;
    this.isShowReasonOfGuard = false;
    if(checking.logToRollbackStatus === 3 || checking.status === 3 || (checking.status === 1 && checking.isLate === 1)) {
      this.isDisableStartTime = true;
    }
    this.editChecking(checking);
  }

  editChecking(checking: Checking) {
    if(this.withEmployeeFld) this.withEmployeeFld.multiInputEL.nativeElement.value = '';
    this.resetCreateChecking();
    this.isApprove = this.isGetIn = this.isGetOut = false;

    for(let i = 0; i < this.places.length; i++) {
      if(checking.destination === this.places[i].codeName) this.selectedPlace = this.places[i];
    }

    for(let i = 0; i < this.reasons.length; i++) {
      if(checking.reasonRegistion === this.reasons[i].codeName) this.selectedReason = this.reasons[i];
    }

    if(checking.isLate == 1) this.isCheckedLate = true;
    this.selectedApprove = {userName: checking.approverUserName, fullName: checking.approverFullName};
    this.selectedChecking = checking;
    this.editStartTimeByPlan = new Date(this.selectedChecking.startTimeByPlan);
    this.editEndTimeByPlan = new Date(this.selectedChecking.endTimeByPlan);
    this.editReasonDetailFld = this.selectedChecking.reasonDetail;

    if(this.selectedChecking.inOutEmployeeList) {
      let userNames = this.selectedChecking.inOutEmployeeList.split(',');
      this.checkingService.getWithEmployee(userNames).subscribe(response => {
        this.selectedWithEmployees = response.data;
      });
    }

    this.setStatusLabel(checking);
  }

  onResetSearching() {
    this.selectedStatus = null;
    this.selectedUnits = null;
    if(!this.isEmployee) this.selectedEmployee = null;
    this.fromDate = new Date();
    this.fromDate.setHours(0, 0, 0, 0);
    this.toDate = new Date();
    this.toDate.setHours(23, 59, 59, 59);
  }

  onShowCheckingForInOut(checking: Checking) {
    this.isShowReasonOfGuard = true;
    this.isDisableReasonOfGuard = false;
    this.resetCreateChecking();
    this.fieldSetTitle = 'Phê duyệt đăng ký vào ra';
    this.selectedChecking = checking;
    this.isShowUpdateDialog = true;

    this.selectedChecking.endTimeByPlan = new Date(this.selectedChecking.endTimeByPlan);

    if(this.selectedChecking.status === 3 || (this.selectedChecking.status === 1 && this.selectedChecking.isLate === 1)) {
      if(this.selectedChecking.endTimeByPlan.getDate() > new Date().getDate()) {
        this.isEarlyDay = true;
      } else {
        this.isGetOut = false;
        this.isGetIn = true;
      }
    } else if(this.selectedChecking.status === 1) {
      if(this.selectedChecking.endTimeByPlan.getDate() > new Date().getDate()) {
        this.isEarlyOut = true;
      } else if(this.selectedChecking.endTimeByPlan < new Date()) {
        this.isLateOut = true;
      } else {
        this.isGetOut = true;
        this.isGetIn = false;
      }
    }

    let message = null;
    // change color of dialog when checking early or late
    if (this.selectedChecking.endTimeByPlan < new Date() && this.selectedChecking.status === 1 && this.selectedChecking.isLate === 0) {
      this.isCheckingEarlyOrLate = {'background-color': '#ff0c0c', 'color': '#ffffff'}
      this.isCheckingEarlyOrLateInput = {'opacity': '0.8','width':'100%'};
      message = 'Đã quá giờ ra';
    } else if (this.selectedChecking.endTimeByPlan < new Date() && 
		((this.selectedChecking.status === 3 && this.selectedChecking.isLate === 0) || (this.selectedChecking.status === 1 && this.selectedChecking.isLate === 1))) {
      this.isCheckingEarlyOrLate = {'background-color': '#ff0c0c', 'color': '#ffffff'}
      this.isCheckingEarlyOrLateInput = {'opacity': '0.8','width':'100%'};
      message = 'Đã quá giờ vào';
    }
    if (message) {
      this.app.showWarn(message);
    }

    this.isApprove = this.isEdit = this.isMoreTime = false;

    for(let i = 0; i < this.places.length; i++) {
      if(checking.destination === this.places[i].codeName) this.selectedPlace = this.places[i];
    }

    for(let i = 0; i < this.reasons.length; i++) {
      if(checking.reasonRegistion === this.reasons[i].codeName) this.selectedReason = this.reasons[i];
    }

    if(checking.isLate == 1) this.isCheckedLate = true;
    this.selectedApprove = {userName: checking.approverUserName, fullName: checking.approverFullName};
    this.selectedChecking = checking;
    this.editStartTimeByPlan = new Date(this.selectedChecking.startTimeByPlan);
    this.editEndTimeByPlan = new Date(this.selectedChecking.endTimeByPlan);
    this.editReasonOfApproverFld = this.selectedChecking.reasonOfGuard;
    this.editReasonDetailFld = this.selectedChecking.reasonDetail;
    this.isDisableStartTime = true;

    this.setStatusLabel(checking);
    this.getImageToShow(this.selectedChecking.empCode);
  }

  onUpdateChecking() {
    this.selectedChecking.approverUserName = null;
    if (this.selectedPlace) this.selectedChecking.destination = this.selectedPlace.codeName;
    if (this.selectedReason) this.selectedChecking.reasonRegistion = this.selectedReason.codeName;
    if (this.selectedApprove) this.selectedChecking.approverUserName = this.selectedApprove.userName;
    this.selectedChecking.isLate = this.isCheckedLate ? 1 : 0;
    this.selectedChecking.reasonDetail = this.editReasonDetailFld;

    this.selectedChecking.inOutEmployeeList = null;
    if (this.selectedWithEmployees && this.selectedWithEmployees.length > 0) {
      let withEmployees: string = this.selectedWithEmployees[0].userName;
      for (let i = 1; i < this.selectedWithEmployees.length; i++) {
        if (i < this.selectedWithEmployees.length) withEmployees += ',';
        withEmployees += this.selectedWithEmployees[i].userName;
      }
      this.selectedChecking.inOutEmployeeList = withEmployees;
    }

    this.selectedChecking.startTimeByPlan = this.editStartTimeByPlan;
    this.selectedChecking.endTimeByPlan = this.editEndTimeByPlan;

    if(!this.validateEmptyFields(this.selectedChecking, this.updatePlaceFld, this.updateReasonFld, this.updateApproverFld)) return;
    if(!this.validateTime(this.selectedChecking, this.updateStartTimeFld, this.updateEndTimeFld)) return;

    this.checkingService.updateChecking(this.selectedChecking).subscribe(response => {
      if (response.status == 1) {
        this.app.showSuccess('Cập nhật ra vào thành công');
        this.isShowUpdateDialog = false;
      } else if (response.status == 7) {
        this.app.showWarn('Cập nhật ra vào không thành công - trùng với thời gian đăng ký trước đó');
      } else {
        this.app.showWarn('Cập nhật ra vào không thành công');
      }
      this.onSearchChecking();
    });
  }

  onUpdateAddMoreTime() {
    this.selectedChecking.startTimeByPlan = this.editStartTimeByPlan;
    this.selectedChecking.endTimeByPlan = this.editEndTimeByPlan;

    if(!this.selectedChecking.startTimeByPlan || !this.selectedChecking.endTimeByPlan) {
      this.app.showWarn('Thời gian ra và vào không được để trống');
      return false;
    }
    if (this.selectedChecking.startTimeByPlan >= this.selectedChecking.endTimeByPlan) {
      this.app.showWarn('Thời gian ra phải nhỏ hơn thời gian vào');
      return false;
    }
    if(this.selectedChecking.status === 1 && this.selectedChecking.isLate === 0) {
      if(this.selectedChecking.startTimeByPlan <= new Date()) {
        this.app.showWarn('Thời gian ra phải lớn hơn thời gian hiện tại');
        return false;
      }
    }
    if(this.selectedChecking.endTimeByPlan.getHours() >= 18 || (this.selectedChecking.endTimeByPlan.getHours() === 17 && this.selectedChecking.endTimeByPlan.getMinutes() > 30)) {
      this.app.showWarn('Thời gian ra - Quá thời gian làm việc 17h30');
      return false;
    }
    if(this.selectedChecking.startTimeByPlan.getDay() != this.selectedChecking.endTimeByPlan.getDay()) {
      this.app.showWarn('Chỉ được ra vào trong ngày');
      return false;
    }

    this.checkingService.updateMoreTimeChecking(this.selectedChecking).subscribe(response => {
      if (response.status == 1) {
        this.app.showSuccess('Gia hạn ra vào thành công');
        this.isShowUpdateDialog = false;
        this.onSearchChecking();
      } else if (response.status == 7) {
        this.app.showWarn('Gia hạn ra vào không thành công - trùng với thời gian đăng ký trước đó');
      } else {
        this.app.showWarn('Gia hạn ra vào không thành công');
      }
    });
  }

  onCreateChecking() {
    this.checking.approverUserName = null;
    if (this.selectedPlace) this.checking.destination = this.selectedPlace.codeName;
    if (this.selectedReason) this.checking.reasonRegistion = this.selectedReason.codeName;
    if (this.selectedApprove) this.checking.approverUserName = this.selectedApprove.userName;
    this.checking.isLate = this.isCheckedLate ? 1 : 0;

    this.checking.inOutEmployeeList = null;
    if (this.selectedWithEmployees && this.selectedWithEmployees.length > 0) {
      let withEmployees: string = this.selectedWithEmployees[0].userName;
      for (let i = 1; i < this.selectedWithEmployees.length; i++) {
        if (i < this.selectedWithEmployees.length) withEmployees += ',';
        withEmployees += this.selectedWithEmployees[i].userName;
      }
      this.checking.inOutEmployeeList = withEmployees;
    }

    if(!this.validateEmptyFields(this.checking, this.createPlaceFld, this.createReasonFld, this.createApproverFld)) return;
    if(!this.validateTime(this.checking, this.createStartTimeFld, this.createEndTimeFld)) return;

    this.checkingService.createChecking(this.checking).subscribe(response => {
      if (response.status === 1) {
        this.app.showSuccess('Đăng ký ra vào thành công');
        this.isShowDialog = false;
        this.onSearchChecking();
      } else if (response.status === 7) {
        this.app.showWarn('Đăng ký ra vào không thành công - Trùng thời gian đăng ký với bản ghi trước');
      } else {
        this.app.showWarn('Đăng ký ra vào không thành công');
      }
    });
  }

  onSelectWithEmployee(employee: Employee) {
    if (this.selectedWithEmployees && this.selectedWithEmployees.length > 0) {
      for (let i = 0; i < this.selectedWithEmployees.length; i++) {
        if (this.selectedWithEmployees[i].userName === this.employeeInfo.userName.toString()) {
          this.selectedWithEmployees.splice(i, 1);
          break;
        }
      }

      for (let i = 0; i < this.selectedWithEmployees.length; i++) {
        if (employee.userName === this.selectedWithEmployees[i].userName) return;
      }
    }
  }

  onShowConfirmApprove() {
    if(!this.selectedCheckingIds || this.selectedCheckingIds.length == 0) {
      this.app.showWarn('Chưa chọn đăng ký');
      return;
    }
    this.resetCreateChecking();
    if(this.selectedCheckingIds.length > 1) {
      this.confirmationService.confirm({
        message: 'Đ/c đang phê duyệt nhiều đăng ký,  Đ/c chắc chắn muốn phê duyệt không?',
        header: 'Nhắc nhở phê duyệt',
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: 'Đồng ý',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          this.approveByManager();
        }
      });
    } else {
      this.fieldSetTitle = 'Phê duyệt đăng ký vào ra';
      this.isShowUpdateDialog = true;
      this.isShowReasonOfGuard = true;
      this.isDisableReasonOfGuard = false;
      this.isDisableStartTime = true;
      if(this.selectedCheckingIds.length === 1) {
        for(let i = 0; i < this.checkings.length; i++) {
          if(this.selectedCheckingIds[0] === this.checkings[i].inOutRegisterId) {
            this.selectedChecking = this.checkings[i];
          }
        }
      }
      this.editChecking(this.selectedChecking);
      if(this.selectedChecking.status === 0 || this.selectedChecking.status === 5) {
        this.isApprove = true;
        this.isGetIn = false;
        this.isGetOut = false;
        this.editReasonOfApproverFld = this.selectedChecking.reasonOfApprover;
      } else if(this.selectedChecking.status === 1) {
        this.isApprove = false;
        this.isGetIn = false;
        this.isGetOut = true;
        this.editReasonOfApproverFld = this.selectedChecking.reasonOfGuard;
      } else if(this.selectedChecking.status === 3) {
        this.isApprove = false;
        this.isGetIn = true;
        this.isGetOut = false;
        this.editReasonOfApproverFld = this.selectedChecking.reasonOfGuard;
      }
      this.isMoreTime = this.isEdit = false;

      this.getImageToShow(this.selectedChecking.empCode);
    }
  }

  onCheckChecking(checking: Checking, isChecked: any) {
    if(isChecked) {
      this.selectedChecking = checking;
      this.selectedChecking.startTimeByPlan = new Date(this.selectedChecking.startTimeByPlan);
      this.selectedChecking.endTimeByPlan = new Date(this.selectedChecking.endTimeByPlan);
      if(this.selectedCheckingIds.length === this.approveTotal) this.isCheckAll = true;
    } else {
      this.selectedChecking = new Checking();
      this.isCheckAll = false;
    }
    if(this.selectedCheckingIds.length === 1) {
      for(let i = 0; i < this.checkings.length; i++) {
        if(this.selectedCheckingIds[0] === this.checkings[i].inOutRegisterId) {
          this.selectedChecking = this.checkings[i];
          this.selectedChecking.startTimeByPlan = new Date(this.selectedChecking.startTimeByPlan);
          this.selectedChecking.endTimeByPlan = new Date(this.selectedChecking.endTimeByPlan);
        }
      }
    }
  }

  onGuardApprove() {
    let checkingConfig: UpdatingChecking = new UpdatingChecking();
    if(!checkingConfig.inOutRegisterId) checkingConfig.inOutRegisterId = [];
    checkingConfig.inOutRegisterId.push(this.selectedChecking.inOutRegisterId);
    checkingConfig.reasonOfApprover = this.editReasonOfApproverFld;

    if(this.selectedChecking.status === 0 || this.selectedChecking.status === 5) {
      checkingConfig.status = 2;
    } else if(this.selectedChecking.status === 1) {
      if(this.selectedChecking.isLate === 0) checkingConfig.status = 6;
      else if(this.selectedChecking.isLate === 1) checkingConfig.status = 7;
      checkingConfig.guardOutUserName = this.employeeInfo.userName.toString();
    } else if(this.selectedChecking.status === 3) {
      checkingConfig.status = 7;
      checkingConfig.guardInUserName = this.employeeInfo.userName.toString();
    }

    checkingConfig.logToRollBackStatus = this.selectedChecking.status;
    checkingConfig.reasonOfGuard = this.editReasonOfApproverFld;

    this.reportService.guardAction(checkingConfig).subscribe(response => {
      if (response.status === 1) {
        this.app.showSuccess("Từ chối thành công");
        this.onSearchChecking();
      } else {
        this.app.showWarn("Từ chối không thành công");
      }
      this.isShowUpdateDialog = false;
      this.isCheckAll = false;
      this.selectedCheckingIds = [];
    });
  }

  onUpdateApprove() {
    if((!this.selectedCheckingIds || this.selectedCheckingIds.length == 0) && !this.selectedChecking) return;

    let checkingConfig: UpdatingChecking = new UpdatingChecking();
    checkingConfig.inOutRegisterId = this.selectedCheckingIds;
    if(!checkingConfig.inOutRegisterId || checkingConfig.inOutRegisterId.length === 0 ) {
      checkingConfig.inOutRegisterId = [];
      checkingConfig.inOutRegisterId.push(this.selectedChecking.inOutRegisterId);
    }
    checkingConfig.reasonOfApprover = this.editReasonOfApproverFld;;

    if(this.selectedChecking.status === 0 ) {
      checkingConfig.status = 1;
    } else if(this.selectedChecking.status === 5) {
      if(this.selectedChecking.logToRollbackStatus === 1) checkingConfig.status = 1;
      else if(this.selectedChecking.logToRollbackStatus === 3) {
        checkingConfig.status = 3;
        checkingConfig.isMoreTimeGetInApprove = 1;
      }
    } else if(this.selectedChecking.status === 1) {
      if(this.selectedChecking.isLate === 1) checkingConfig.status = 4;
      else checkingConfig.status = 3;
      checkingConfig.guardOutUserName = this.employeeInfo.userName.toString();
    } else if(this.selectedChecking.status === 3) {
      checkingConfig.status = 4;
      checkingConfig.guardInUserName = this.employeeInfo.userName.toString();
    }

    checkingConfig.reasonOfGuard = this.editReasonOfApproverFld;

    this.checkingService.updateStatusApproveChecking(checkingConfig).subscribe(response => {
      if (response.status === 1) {
        this.app.showSuccess("Phê duyệt thành công");
        if(response.data) {
          this.app.showWarn("Chú ý - Các đồng chí " + response.data + " bị trùng thời gian đăng ký trước đó");
        }
      } else {
        this.app.showWarn("Phê duyệt không thành công");
      }
      this.onSearchChecking();
      this.selectedCheckingIds = [];
      this.isCheckAll = false;
      this.isShowUpdateDialog = false;
    });
  }

  approveByManager() {
    let checkingConfig: UpdatingChecking = new UpdatingChecking();
    checkingConfig.inOutRegisterId = this.selectedCheckingIds;

    this.checkingService.approveByManager(checkingConfig).subscribe(response => {
      if (response.status === 1) {
        this.app.showSuccess("Phê duyệt thành công");
        if(response.data) {
          this.app.showWarn("Chú ý - Các đồng chí " + response.data + " phê duyệt không thành công");
        }
      } else {
        this.app.showWarn("Phê duyệt không thành công");
      }
      this.onSearchChecking();
      this.selectedCheckingIds = [];
      this.isCheckAll = false;
      this.isShowUpdateDialog = false;
    });
  }

  onShowDialog() {
    this.createWithEmployeeFld.multiInputEL.nativeElement.value = '';
    this.isShowDialog = true;
    this.checking = new Checking();
    this.checking.status = 0;
    this.resetCreateChecking();
    this.getImageToShow(this.employeeInfo.code.toString());
  }

  getSelectingModel() {
    this.checkingService.getSelectingModel().subscribe(response => {
      this.places = response.data.places;
      this.reasons = response.data.reasons;
    });
  }

  checkAll(isChecked: boolean) {
    if (!this.checkings || this.checkings.length == 0) return;
    this.selectedCheckingIds = [];
    if (isChecked) {
      for (let i = 0; i < this.checkings.length; i++) {
        let checking = this.checkings[i];
        if (this.checkApprove(checking)) this.selectedCheckingIds.push(checking.inOutRegisterId);
      }
    }
  }

  onSearchChecking() {
    if(this.fromDate && this.toDate) {
      if (this.fromDate > this.toDate) {
        this.app.showWarn('Thời gian bắt dầu phải nhỏ hơn thời gian kết thúc');
        return;
      }
    }

    if (this.selectedEmployee && this.selectedEmployee.userName) {
      this.searchingModel.userName = this.selectedEmployee.userName;
    } else {
      this.searchingModel.userName = null;
      if(!this.isEmployee) {
        let personInfo: string = this.employeeFld.inputEL.nativeElement.value;
        this.searchingModel.personInfo = personInfo;
      }
    }

    if(this.toDate) this.toDate.setHours(23, 59, 59, 59);
    this.searchingModel.listStatus = this.selectedStatus;
    this.searchingModel.startDate = this.fromDate;
    this.searchingModel.endDate = this.toDate;
    this.searchingModel.startRow = 0;
    this.searchingModel.rowSize = 10;

    this.searchingModel.listUnit = [];
    if(this.isManager) {
      this.checkingService.getParentUnitId(this.employeeInfo.unitId.toString()).subscribe(resp => {
        if(resp) {
          this.searchingModel.listUnit.push(resp.data);
        }
        this.getCheckingList({loadUnit: false, unitId: null, config: this.searchingModel});
      });
    } else {
      let selectedUnits: string[] = [];
      if (this.selectedUnits && this.selectedUnits.length > 0) {
        for (let i = 0; i < this.selectedUnits.length; i++) {
          selectedUnits.push(this.selectedUnits[i].data);
        }
        this.searchingModel.listUnit = selectedUnits;
      }
      this.getCheckingList({loadUnit: false, unitId: null, config: this.searchingModel});
    }
  }

  onKeyUpEmployee(event) {
    let personInfo: string = this.employeeFld.inputEL.nativeElement.value;
    if(personInfo.length > 0) {
      let str = personInfo.trim();
      if(event.code === 'Space') {
        this.employeeFld.inputEL.nativeElement.value = str;
      }
    }
  }

  onPaginate(event) {
    this.startRow = event.first + 1;
    this.searchingModel.startRow = event.first;
    this.rowSize = this.startRow + 9;
    if (this.rowSize > this.totalRecords) this.rowSize = this.totalRecords;

    let searchingModel = {loadUnit: false, unitId: null, config: this.searchingModel};
    this.reportService.getCheckingModel(searchingModel).subscribe(response => {
      this.checkings = response.data.checkings;
      this.setIsApprove(this.checkings);
    });
  }

  clearFromDate() {
    if (this.fromDate) this.fromDate = null;
  }

  setStatusLabel(checking: Checking) {
    if(checking.status === 0 && checking.endTimeByPlan < new Date()) {
      this.statusLabel = 'Quá thời gian ra';
    } else if(checking.status === 1 && checking.endTimeByPlan < new Date()) {
      if(checking.isLate === 0)this.statusLabel = 'Quá thời gian ra';
      else this.statusLabel = 'Quá thời gian vào';
    } else if(checking.status === 3 && checking.endTimeByPlan < new Date()) {
      this.statusLabel = 'Quá thời gian vào';
    } else if(checking.status === 5 && checking.endTimeByPlan < new Date()) {
      if(checking.logToRollbackStatus === 1) this.statusLabel = 'Quá thời gian ra';
      else this.statusLabel = 'Quá thời gian vào';
    } else {
      this.statusLabel = this.statusLabels[checking.status];
    }
  }

  validateEmptyFields(checking: Checking, placeFld: Dropdown, reasonFld: Dropdown, createApprover: AutoComplete) {
    if(!checking.destination) {
      placeFld.focus();
      this.app.showWarn('Nơi đến không được để trống');
      return false;
    }
    if(!checking.reasonRegistion) {
      reasonFld.focus();
      this.app.showWarn('Lý do không được để trống');
      return false;
    }
    if(!checking.approverUserName) {
      createApprover.domHandler.findSingle(createApprover.el.nativeElement, 'input').focus();
      this.app.showWarn('Người phê duyệt không được để trống');
      return false;
    }
    return true;
  }

  validateTime(checking: Checking, startTimeByPlan: Calendar, endTimeByPlan: Calendar) {
    if(!checking.startTimeByPlan) {
      startTimeByPlan.domHandler.findSingle(startTimeByPlan.el.nativeElement, 'input').focus();
      this.app.showWarn('Thời gian ra không được để trống');
      return false;
    }

    if(!checking.endTimeByPlan) {
      endTimeByPlan.domHandler.findSingle(endTimeByPlan.el.nativeElement, 'input').focus();
      this.app.showWarn('Thời gian vào không được để trống');
      return false;
    }

    if (checking.startTimeByPlan >= checking.endTimeByPlan) {
      startTimeByPlan.domHandler.findSingle(startTimeByPlan.el.nativeElement, 'input').focus();
      this.app.showWarn('Thời gian ra phải nhỏ hơn thời gian vào');
      return false;
    }
    if(checking.startTimeByPlan <= new Date()) {
      startTimeByPlan.domHandler.findSingle(startTimeByPlan.el.nativeElement, 'input').focus();
      this.app.showWarn('Thời gian ra phải lớn hơn thời gian hiện tại');
      return false;
    }
    if(checking.endTimeByPlan.getHours() >= 18 || (checking.endTimeByPlan.getHours() === 17 && checking.endTimeByPlan.getMinutes() > 30)) {
      startTimeByPlan.domHandler.findSingle(startTimeByPlan.el.nativeElement, 'input').focus();
      this.app.showWarn('Thời gian ra - Quá thời gian làm việc 17h30');
      return false;
    }
    if(checking.startTimeByPlan.getDay() != checking.endTimeByPlan.getDay()) {
      endTimeByPlan.domHandler.findSingle(endTimeByPlan.el.nativeElement, 'input').focus();
      this.app.showWarn('Chỉ được ra vào trong ngày');
      return false;
    }

    return true;
  }

  onKeypressCreateTrimming(event) {
    if(event.code === 'Space') {
      if(this.checking.reasonDetail) this.checking.reasonDetail = this.checking.reasonDetail.trim();
    }
  }

  onChangeCreateTrimming() {
    if(this.checking.reasonDetail) this.checking.reasonDetail = this.checking.reasonDetail.trim();
  }

  onKeypressUpdateTrimming(event) {
    if(event.code === 'Space') {
      if(this.selectedChecking.reasonOfGuard) this.selectedChecking.reasonOfGuard = this.selectedChecking.reasonOfGuard.trim();
    }
  }

  onChangeUpdateTrimming() {
    if(this.selectedChecking.reasonOfGuard) this.selectedChecking.reasonOfGuard = this.selectedChecking.reasonOfGuard.trim();
  }

  clearToDate() {
    if (this.toDate) this.toDate = null;
  }

  onSearchManagerEmployee(event) {
    this.checkingService.managerEmployeeAutocomplete(event.query).subscribe(response => {
      this.employees = response.data;
    });
  }

  onSearchEmployee(event) {
    this.checkingService.employeeAutocomplete(event.query).subscribe(response => {
      this.employees = response.data;
    });
  }

  exportToExcel() {
    const fileName = 'DSDKRV_' + this.employeeInfo.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
      + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
      + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';

    this.searchingModel.startRow = -1;


    //TODO
    if(this.fromDate && this.toDate) {
      if (this.fromDate > this.toDate) {
        this.app.showWarn('Thời gian bắt dầu phải nhỏ hơn thời gian kết thúc');
        return;
      }
    }

    if (this.selectedEmployee && this.selectedEmployee.userName) {
      this.searchingModel.userName = this.selectedEmployee.userName;
    } else {
      this.searchingModel.userName = null;
      if(!this.isEmployee) {
        let personInfo: string = this.employeeFld.inputEL.nativeElement.value;
        this.searchingModel.personInfo = personInfo;
      }
    }

    if(this.toDate) this.toDate.setHours(23, 59, 59, 59);
    this.searchingModel.listStatus = this.selectedStatus;
    this.searchingModel.startDate = this.fromDate;
    this.searchingModel.endDate = this.toDate;
    this.searchingModel.listUnit = [];

    if(this.isManager) {
      this.checkingService.getParentUnitId(this.employeeInfo.unitId.toString()).subscribe(resp => {
        if(resp) {
          this.searchingModel.listUnit = [];
          this.searchingModel.listUnit.push(resp.data);
        }
        this.reportService.countTotalRecord(this.searchingModel).subscribe(item => {
          const numOfRow = item['data'];
          if (numOfRow <= 50000) {
            this.reportService.getExcel(this.searchingModel).subscribe(
              (next: ArrayBuffer) => {
                const file: Blob = new Blob([next], { type: 'application/xlsx' });
                saveAs(file, fileName);
              }
            );
          } else {
            this.app.showWarn('Không thể xuất file - Số bản ghi tìm thấy vượt quá 50000');
          }
        });
      });
    } else {
      let selectedUnits: string[] = [];
      if (this.selectedUnits && this.selectedUnits.length > 0) {
        for (let i = 0; i < this.selectedUnits.length; i++) {
          selectedUnits.push(this.selectedUnits[i].data);
        }
        this.searchingModel.listUnit = selectedUnits;
      }
      this.reportService.countTotalRecord(this.searchingModel).subscribe(item => {
        const numOfRow = item['data'];
        if (numOfRow <= 50000) {
          this.reportService.getExcel(this.searchingModel).subscribe(
            (next: ArrayBuffer) => {
              const file: Blob = new Blob([next], { type: 'application/xlsx' });
              saveAs(file, fileName);
            }
          );
        } else {
          this.app.showWarn('Không thể xuất file - Số bản ghi tìm thấy vượt quá 50000');
        }
      });
    }
  }

  getImageToShow(code: string) {
    this.reportService.getImage(code).subscribe(item => {
      this.imageData = item['data'];
    });
  }

  getCheckingList(searchingModel: any) {
    this.reportService.getCheckingModel(searchingModel).subscribe(response => {
      if (response.data && response.data.units && (this.isAdmin || this.isSecurity || this.isManager)) {
        this.unitSelectors = this.unitTree.convertToTree(response.data.units);
      }
      this.startRow = 0;
      this.rowSize = 0;
      this.totalRecords = 0;
      if(response.data && response.data.checkings) this.checkings = response.data.checkings;
      if(response.data && response.data.totalRecords) this.totalRecords = response.data.totalRecords;
      this.rowSize = 10;
      if (this.checkings && this.checkings.length > 0) this.startRow = 1;
      if (this.rowSize > this.totalRecords) this.rowSize = this.totalRecords;

      if (this.checkings && this.checkings.length > 0) this.setIsApprove(this.checkings);
      if (this.myTable) this.myTable.reset();
    });
  }

  setIsApprove(checkings: Checking[]) {
    this.approveTotal = 0;
    for (let i = 0; i < checkings.length; i++) {
      let checking: Checking = checkings[i];
      if(this.checkApprove(checking)) {
        checking.isApprove = true;
        this.approveTotal++;
      }
      if(this.checkEditable(checking)) checking.isEdit = checking.isMoreTime = true;
    }
  }

  checkApprove(checking: Checking) {
    if (new Date(checking.endTimeByPlan).getTime() > new Date().getTime() && (checking.status === 0 || checking.status === 5)) return true;
    return false;
  }

  checkEditable(checking: Checking) {
    if(checking.employeeUserName != this.employeeInfo.userName) return false;

    if(checking.status === 0) checking.isEdit = true;
    if((checking.status === 1 || checking.status === 3 || checking.status === 5)) checking.isMoreTime = true;
    if(checking.status === 2 || checking.status === 4 || checking.status === 6 ||
      checking.status === 7 || checking.status === 8 || checking.status === 9) checking.isCopy = true;
  }

  nodeExpand(event) {
    if(this.isEmployee) return;
    if (event.node && event.node.children !== undefined) return;
    this.kitchenService.getSectors(event.node.data).subscribe(data => {
      event.node.children = data;
    });
  }

  resetCreateChecking() {
    this.isCheckedLate = false;
    this.selectedReason = null;
    this.selectedPlace = null;
    this.selectedWithEmployees = [];
    this.selectedApprove = null;
  }

  timeout: any = null;

  onFindByCode() {
    let temp = this.barcode;
    let checking: Checking = null;
    clearTimeout(this.timeout);
    this.timeout = setTimeout(() => {
      if (this.barcode) {
        this.checkingService.getCheckingByBarcode(this.barcode).subscribe(
          res => {
            checking = res.data;
            if (checking) {
                this.onShowCheckingForInOut(checking);
            }
            this.barcode = null;
            this.barcodeInput.nativeElement.focus();
          }
        )
      }
    }, 500);

  }

  clearBarcode() {
    this.barcode = null
  }
}
