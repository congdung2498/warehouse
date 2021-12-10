import {Component, OnInit, Renderer2, ViewChild} from '@angular/core';
import {Employee} from "../Entity/Employee";
import {Table} from "primeng/table";
import {AutoComplete, FileUpload, MultiSelect} from "primeng/primeng";
import {Place} from '../Entity/Place';
import {
    CanceIssuesStationey,
    DataResponse, DataResponseQuota, DetailsEmployeeInfo, EditIssuesStationey, InfoEmployee,
    InfoToSearchIssuesStationeryItems, IssuesStationeryItems, IssuesStationeryItemsToInsert, PlaceEmployee,
    PlaceEmployeeParam,
    PlaceStationery,
    ReportStationery, RequestParam, RequestParamUnit, SearchDataById,
    SearchDataStationery, StationeryEmployee,
    StationeryInfo, StationeryItem,
    StationeryReportRequest, StationeryRequest
} from "../Entity/ReportStationery";
import {StationeryService} from "../stationery.service";
import {KitchenManagementService} from "../../kitchen-management/kitchen-management.service";
import {ConfirmationService, MessageService, SelectItem, TreeNode} from "primeng/api";
import {AppComponent} from "../../app.component";
import {Title} from "@angular/platform-browser";
import {TokenStorage} from "../../shared/token.storage";
import {UserInfo} from "../../shared/UserInfo";
import {DatePipe} from "@angular/common";
import {saveAs} from "file-saver";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {ValidateUtils} from "../../../common/validate";
import {BrowseRequestComponent} from "../report-stationery/browse-request/browse-request.component";
import {StationeryStatus} from "../Entity/Stationery";
import {DateTimeUtil} from "../../../common/datetime";

declare const require: any;
const pixelWidth = require('string-pixel-width');

@Component({
    selector: 'app-report-stationery-employee',
    templateUrl: './report-stationery-employee.component.html',
    styleUrls: ['./report-stationery-employee.component.css'],
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

export class ReportStationeryEmployeeComponent implements OnInit {

    status: SelectItem[] =
      [
          {label: 'Đang xử lý', value: '0'},
          {label: 'Hoàn thành một phần', value: '1'},
          {label: 'Hoàn thành', value: '2'},
          {label: 'Từ chối', value: '3'},
          {label: 'Hủy yêu cầu', value: '4'},
          {label: 'Từ chối xác nhận', value: '5'},
          {label: 'Không thực hiện được', value: '6'}
      ];

    ListSelectedStatus: any[] = [];
    @ViewChild('myTable') private myTable: Table;
    @ViewChild('fileUpload') private fileUpload: FileUpload;
    @ViewChild('myEmployee') private myEmployee: Table;
    @ViewChild('myDrop') private myDrop: MultiSelect;
    @ViewChild('dtUnit') private dtUnit: Table;
    @ViewChild('placeAddFld') private placeAddFld: AutoComplete;
    @ViewChild('placeCopyFld') private placeCopyFld: AutoComplete;
    @ViewChild('browseRequestComponent') browseRequestComponent: BrowseRequestComponent;
    minDateValue: Date;
    maxDateValue: Date;
    vn = DateTimeUtil.vn;
    listChooseEmployee: StationeryEmployee[] = [];
    searchData: SearchDataStationery = {
        issuesStationeryApprovedId: ''
    };
    editObjectEmployee : ReportStationery;
    loading: boolean;
    notiImportFile: any;
    file: Blob;
    showImportForm: boolean;
    listDataResponseQuota: DataResponseQuota;
    limitStationeryQuota: number;
    limitStationeryById: number;
    limitStationeryByUsername: number;
    limitQuota: number;
    limitQuotaStr: string;
    // fixNumber: number;
    numberStr: string;
    uploadedFiles: any[] = [];

    numberStrByUser: string;
    stationeryRequestStr: string;
    issuesStationeryItemId: string;
    stationeryQuantityStr: number;
    dialogDetailsRequestEmployee: boolean;
    isDialogBrowseRequest: boolean;
    dialogEditRequestEmployee: boolean;
    dialogDeleteRequestEmployee: boolean;
    isHCVP: boolean;
    isNotHCVP: boolean;
    stationeryNameListAdd: StationeryInfo[];
    fixNumberStr: number;
    rpRatingVPTCT: number;
    rpFeedbackToVPTCT: string;
    rpRatingHCDV: number;
    rpFeedbackToHCDV: string;
    limitDate: number;
    convertStatus: string[];
    convertStatusUnit: string[];
    dialogImport: boolean;
    dialogHandlingRequest: boolean;
    dialogDetailsRequest: boolean;
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
    massage: string;
    quantity: number;
    quota: number;
    unitId: String;
    fixNumberQuota: string;
    fixNumberQuotaHCVP: string;
    fixNumberQuotaNotHCVP: string;
    fixNumberByUser: number;
    statusConstant: string = 'Đang xử lý';
    note: String;
    stationeryNameList: StationeryInfo[];
    stationeryNameListById: StationeryReportRequest[];
    selectedStationeryName: StationeryInfo;
    listActivity: string[];
    listActivityEmployee: string[];
    lstChefInfor: StationeryReportRequest[] = [];
    lstChefInforById: StationeryEmployee;
    listChefInforById: StationeryEmployee[] = [];
    averageRatting: number;
    reportStationery: ReportStationery;
    canceIssuesStationey: CanceIssuesStationey = {
        userName: null,
        issuesStationeryId: null,
        listIssueStationeryItemId: []
    }
    editIssuesStationey: EditIssuesStationey = {
        placeID: null,
        listStationery: [],
        issuesStationeryIdOld: '',
        note: ''
    }
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
    unitEmployee: string;
    nameEmployee: string;
    phoneEmployee: string;
    emailEmployee: string;
    quotaEmployee: string;
    placeEmployee: string;

    len: number;
    maxLenStatus: any;
    employee: any;
    place: any;
    placeStationery: PlaceStationery = {
        placeId: null,
        placeName: '',
        lstPlaceId: ''
    };
    listFilterEmployee: any[];
    listFilterPlace: any[];
    listFilterPlaceAdd : any[];
    listStationeryReport: ReportStationery[];
    listStationeryReportEmployee: StationeryEmployee[];
    listCountStationeryReport: ReportStationery[];
    total_record_employee: number = 0;
    searchDataById: SearchDataById = {
        issuesStationeryApprovedId: ''
    };
    message: ReportStationery;
    DetailsrpStationeryId: string;
    PhonerpStationeryId: string;
    FullnamerpStationeryId: string;
    LimitrpStationeryId: string;
    PlacerpStationeryId: string;
    listStationeryFullfill: any[];
    listStationeryFullUnit: DataResponse[];
    countStationeryFullUnit: any[];
    display: boolean = false;
    listHandlingStationery: IssuesStationeryItems[];
    uf: UserInfo;
    listUnitManager: string[];
    sectors: TreeNode[];
    selectedSector: TreeNode[];
    requestParam: RequestParam = {
        placeID: null,
        listStationery: [],
        note: ''
    }
    stationeryRequest: StationeryRequest = {
        stationeryId: null,
        quantity: null
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
    isCheckAllEmployee: boolean;
    dialogDetailsEmployee: boolean;
    dialogAddStationery: boolean;
    dialogConfirmRequest: boolean;
    selectedItem: any[] = []
    dialogVoteHCDVStationery: boolean;
    selectedStationeryItem: ReportStationery;
    dialogAcceptRequest: boolean

    isCancel: boolean = false;
    isView: boolean = false;

    rowSize: number = 0;
    startRow: number = 0;

    isAdmin: boolean;
    isHCDV: boolean;
    isHCVP_VPP: boolean;
    isQL: boolean;
    isNV: boolean;
    isDisableEmployeeFld: boolean;
    statusEmployee : number;

    isCheckedAll: boolean;
    selectedItemIds: string[];
    isShowAddingVPP: boolean;
    isDisabledButton: boolean;
    isDisableAddBtn: boolean;
    isDisableRemoveBtn: boolean;
    stationeryItems: StationeryItem[];
    isDisableDownBtn: boolean;
    isShowCancelConfirmDlg: boolean;


    constructor(private stationeryService: StationeryService, private kitchenService: KitchenManagementService, private confirmationService: ConfirmationService,
                private messageService: MessageService, private renderer: Renderer2, private tokenStorage: TokenStorage, private title: Title, private app: AppComponent) {

    }

    ngOnInit() {
        this.reportStationery = new ReportStationery(null,null, 0, null, null, null, null, null, 0, null, 0, null, null, [], [],
          null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
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
        this.lstChefInfor.push(new StationeryReportRequest(null, 1, null, null, null, null));
        this.selectedStationeryItem = new ReportStationery(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
          null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)
        this.stationeryService.findStationeryByName().subscribe(data => {
              this.stationeryNameList = data.data;
          }
        );
        this.empUnit = this.uf.unitName;
        this.empName = this.uf.fullName;
        this.empPhone = this.uf.phoneNumber;
        this.empEmail = this.uf.email;
    }

    setRole() {
        if (this.uf.role.includes('PMQT_ADMIN')) {
            this.isAdmin = true;
        } else if (this.uf.role.includes('PMQT_HCVP_VPP')) {
            this.isNV = true;
            this.isDisableEmployeeFld = true;
            this.employee = {userName: this.uf.userName, fullName: this.uf.fullName};
        } else if (this.uf.role.includes('PMQT_HC_DV')) {
            this.isNV = true;
            this.stationeryService.getStationeryStaffByUser().subscribe(data => {
               if(data.data) {
                   this.isHCDV = true;
                   this.stationeryService.getUnitHCDV().subscribe(data => {
                       this.sectors = data;
                   });
               }  else {
                   this.isDisableEmployeeFld = true;
                   this.employee = {userName: this.uf.userName, fullName: this.uf.fullName};
               }
            });
        } else if (this.uf.role.includes('PMQT_QL')) {
            this.isQL = true;
        } else if (this.uf.role.includes('PMQT_NV')) {
            this.isNV = true;
            this.isDisableEmployeeFld = true;
            this.employee = {userName: this.uf.userName, fullName: this.uf.fullName};
        }
    }

    setStatus() {
        this.convertStatusUnit = ['', 'Đang xử lý', 'Hoàn thành một phần', 'Hoàn thành', 'Từ chối', 'Hủy yêu cầu', 'Từ chối xác nhận', 'Không thực hiện được'];
        this.convertStatus = ['Chờ LĐ phê duyệt', 'Lãnh đạo phê duyệt', 'Lãnh đạo từ chối', 'VP TCT tiếp nhận (xác nhận tiếp nhận)', 'VP TCT Hoãn thực hiện', 'VP TCT Không thực hiện được', 'VP TCT cấp phát cho HCĐV', 'HCĐV xác nhận  VPP', 'Từ chối xác nhận'];

    }

    checkAllItems(isChecked: boolean) {
        if(isChecked) {
            this.selectedItemIds = [];
            if(this.listStationeryReportEmployee && this.listStationeryReportEmployee.length > 0) {
                for(let i = 0; i < this.listStationeryReportEmployee.length; i++) {
                    let stationery = this.listStationeryReportEmployee[i];
                    if(stationery.isApprove) {
                        this.selectedItemIds.push(this.listStationeryReportEmployee[i].issuesStationeryItemId);
                    }
                }
            }
        } else {
            this.selectedItemIds = null;
        }
    }

    onCheckItem(isChecked: boolean) {
        if(isChecked) {
            let count: number = 0;
            for(let i = 0; i < this.listStationeryReportEmployee.length; i++) {
                let stationery = this.listStationeryReportEmployee[i];
                if(stationery.isApprove) count = count + 1;
            }
            if(count === this.selectedItemIds.length) this.isCheckedAll = true;
        } else {
            this.isCheckedAll = false;
        }
    }

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
        if (this.uf.role.includes('PMQT_ADMIN')) {
            this.getSector(0);
        } else {
            this.getSector(2);
        }
    }

    getSector(type: number) {
        if(this.isQL) {
            this.kitchenService.getSectorGreatFather(this.uf.unitId.toString()).subscribe(data => {
                this.sectors = data;
            });
        } else if(this.isHCDV) {

        } else {
            if (type === 0) {
                this.kitchenService.getSectors(null).subscribe(data => {
                    this.sectors = data;
                });
            } else if (type === 1) {
                this.kitchenService.getSectors(this.uf.unitId as string).subscribe(data => {
                    this.sectors = data;
                });
            } else if (type === 2) {
                this.sectors = [{
                    label: this.uf.unitName as string,
                    data: this.uf.unitId,
                    selectable: false
                }];
            }
        }
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
        let temp:Place = new Place(null, event.query);
        this.stationeryService.findPlace(temp).subscribe(data => {
              this.listFilterPlaceAdd = data.data;
          }
        );
        if(this.isHCDV) {
            this.stationeryService. findPlaceByHCDV(temp).subscribe(data => {
                  if(data.data != null && data.data.length > 0 ) {
                      this.listFilterPlace = data.data;
                  } else {
                      this.stationeryService.findPlace(temp).subscribe(data => {
                            this.listFilterPlace = data.data;
                        }
                      );
                  }
              }
            );
        } else {
            this.stationeryService.findPlace(temp).subscribe(data => {
                  this.listFilterPlace = data.data;
              }
            );
        }
    }

    filterPlaceEdit(event) {
        let temp:Place = new Place(null, event.query);
        this.stationeryService.findPlace(temp).subscribe(data => {
            this.listFilterPlace = data.data;
            this.listFilterPlaceAdd = data.data;
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

        this.stationeryService.getEmployees(temp).subscribe(data => {
            this.listFilterEmployee = data.data;
          }
        );
    }

    employeeSelect(event) {
        this.employee = new Employee(event.employeeId, this.getNameObject(event.fullName), event.selectUserName, event.employeePhone, event.role, event.place, event.unit);
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
              this.reportStationery.fullName = rowData.fullName;
              this.reportStationery.status = rowData.status - 1;
              this.reportStationery.lastUser = item.lastUser;
              //this.reportStationery.lastUser = item.lastUser;
              this.stationeryService.getLastUser(this.reportStationery).subscribe(
                data => {
                    this.reportStationery.lastUser = data.data;
                }
              );
          }
        )
        this.display = true;
    }

    close() {
        this.display = false;
    }

    selectRow(rowData: StationeryEmployee) {
        this.unitEmployee = rowData.unitName;
        this.nameEmployee = rowData.fullName;
        this.phoneEmployee = rowData.phoneNumber;
        this.emailEmployee = rowData.email;
        this.placeEmployee = rowData.startPlace;
        this.stationeryRequestStr = rowData.stationeryName;
        this.stationeryQuantityStr = rowData.totalRequest;
        this.statusEmployee = rowData.status;

        this.massage = rowData.note;
        this.placeStationery = {placeId: rowData.placeId, placeName: rowData.startPlace, lstPlaceId: ''};
        if (rowData.issuesStationeryItemId) {
            this.searchDataById.issuesStationeryApprovedId = rowData.issuesStationeryId;
            this.stationeryService.getIssuesStationeryItemsById(this.searchDataById).subscribe(res => {
                if (res) {
                    this.stationeryNameListById = res.data;
                    this.stationeryService.findStationeryByName().subscribe(data => {
                          this.stationeryNameList = data.data;
                          for(let i = 0; this.stationeryNameListById.length; i++) {
                              let stationery = this.stationeryNameListById[i];
                              if(!this.checkExistStationery(stationery.stationeryId)) {
                                  if(!this.stationeryNameList) this.stationeryNameList = [];
                                  this.stationeryNameList.push({stationeryId: stationery.stationeryId, stationeryName: stationery.stationeryName,
                                      calculationUnit: stationery.calUnit, unitPrice: stationery.unitPrice});
                              }
                          }
                      }
                    );

                    this.lstChefInfor = [];
                    this.stationeryNameListById.forEach(el => {
                        this.lstChefInfor.push(new StationeryReportRequest(el.stationeryId, el.quantity, el.calUnit, el.unitPrice, el.stationeryName, el.issuesStationeryItemId));
                    });
                }
                this.stationeryService.findSpentQuotaByUser(rowData.employeeUsername).subscribe(res => {
                    if (res) {
                        this.quotaEmployee = res.data;
                    }
                    this.dialogEditRequestEmployee = true;
                    this.isCancel = true;
                    this.isView = true;
                });
            });
        }
    }

    settingSearch() {
        if (this.employee != null && this.employee.userName != undefined) {
            this.reportStationery.userName = this.employee.userName
        } else {
            this.reportStationery.userName = null;
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
            if(this.sectors && this.sectors[0]) this.listSelectedUnitId = [this.sectors[0].data];
        } else {
            this.selectedSector.forEach(element => {
                this.listSelectedUnitId.push(element.data);
            });
        }
        this.reportStationery.listUnitId = this.listSelectedUnitId;
        this.reportStationery.listStatus = this.ListSelectedStatus;
        this.reportStationery.startDate = this.fromDate;
        this.reportStationery.endDate = this.toDate;
    }

    handleSearchEmployee() {
        this.reportStationery.pageSize = 15;
        this.reportStationery.pageNumber = 0;
        this.settingSearch();
        this.getListReport();
    }

    onPaginate(event) {
        this.isCheckedAll = false;
        this.selectedItemIds = null;
        this.startRow = event.first + 1;
        this.reportStationery.pageNumber = event.first;
        this.rowSize = this.startRow + 14;
        if (this.rowSize > this.total_record_employee) this.rowSize = this.total_record_employee;

        this.stationeryService.searchStationeryByEmployee(this.reportStationery).subscribe(data => {
            this.listStationeryReportEmployee = data.data;
            this.setEditable(this.listStationeryReportEmployee);
        });
    }

    getListReport() {
        this.stationeryService.countStationeryByEmployee(this.reportStationery).subscribe(data => {
              this.total_record_employee = data.data;
              this.rowSize = 15;
              if (this.rowSize > this.total_record_employee) this.rowSize = this.total_record_employee;

              if (this.total_record_employee > 0) {
                  this.startRow = 1;
                  this.stationeryService.searchStationeryByEmployee(this.reportStationery).subscribe(data => {
                      this.listStationeryReportEmployee = data.data;
                      this.setEditable(this.listStationeryReportEmployee);
                      if (this.myEmployee) this.myEmployee.reset();
                      this.selectedItemIds = null;
                      this.isCheckedAll = false;
                  });
              } else {
                  this.startRow = 0;
                  this.listStationeryReportEmployee = null;
                  if (this.myEmployee) this.myEmployee.reset();
              }
          }
        );
    }

    exportExcelVPP() {
        this.isDisabledButton = true;
        this.settingSearch();
        const fileName = 'TKDUVPP_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
          + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
          + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
        if (this.reportStationery.listUnitId == null || this.reportStationery.listUnitId.length == 0) {
            this.reportStationery.listUnitId = this.sectors.map(sector => sector.data);
        }
        this.stationeryService.exportExcelVPP(this.reportStationery).subscribe((next: ArrayBuffer) => {
            const file: Blob = new Blob([next], {type: 'application/xlsx'});
            saveAs(file, fileName);
            this.isDisabledButton = false;
          });
    }

    exportExcel() {
        this.settingSearch();
        const fileName = 'TKYCVPP_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
          + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
          + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
        this.stationeryService.exportExcelEmployee(this.reportStationery).subscribe(
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
        if (this.ListSelectedStatus == null) {
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
        this.isDisableAddBtn = true;
        let isValid = this.onValidate(this.placeAddFld);
        if(!isValid) {
            this.isDisableAddBtn = false;
            return;
        }

        this.requestParam.listStationery = this.lstChefInfor;
        this.requestParam.placeID = this.placeStationery.placeId;
        this.requestParam.note = this.note ? this.note : '';
        this.stationeryService.insertIssuesStationery(this.requestParam).subscribe(y => {
              if (y.status == 0) {
                  this.app.showWarn('Có lỗi không xác định');
              } else if (y.status == 1) {
                  this.app.showSuccess('Đã gửi yêu cầu VPP');
                  this.dialogAddStationery = false;
                  this.handleSearchEmployee();
              } else if (y.status == 2) {
                  this.app.showWarn('Đã yêu cầu quá hạn mức tháng');
              }else if (y.status == 21) {
                  this.app.showWarn('Đơn vị chưa được gán định mức. Vui lòng liên hệ văn phòng để gán định mức cho đơn vị');
              }
            this.isDisableAddBtn = false;
          }
        );
    }

    resetButton() {
        this.fromDate = new Date();
        this.toDate = new Date();
        this.ListSelectedStatus = [];
        this.onChange();
        this.selectedSector = [];
        this.place = null;
        this.employee = null;
        this.handleSearchEmployee();
    }

    indexTracker(index: number, value: any) {
        return index;
    }

    selectedAddStationery(i, event) {
        let count = 0;
        this.lstChefInfor[i].quantity = 1;
        if (this.lstChefInfor[i] != null) {
            this.lstChefInfor.forEach(element => {
                if (element.stationeryId === event.value.stationeryId) {
                    count++;
                }
            });
            if (count > 1) {
                this.app.showWarn('Văn phòng phẩm không được chọn trùng');
                this.lstChefInfor.splice(i, 1);
            }
        }
    }

    addRequestStationery() {
        this.dialogAddStationery = true;
        this.stationeryService.findSpendingLimitQuota().subscribe(resp => {
            if(resp) {
                if(resp.status === 21) {
                    this.limitQuotaStr = 'Đơn vị chưa được gán định mức. Vui lòng liên hệ văn phòng để gán định mức cho đơn vị';
                } else {
                    this.limitQuota = resp.data['spendingLimit'];
                }
            }
        });
    }

    removeChef(index: number) {
        this.lstChefInfor.splice(index, 1);
        if(this.lstChefInfor.length === 1) this.isDisableRemoveBtn = true;
    }

    addChef() {
        this.lstChefInfor.push(new StationeryReportRequest(null, 1, null, null, null, null));
        this.isDisableRemoveBtn = false;
    }

    acceptRequest(rowData?: ReportStationery) {
        if (rowData) {
            this.dialogConfirmRequest = true;
            this.infoToSearchIssuesStationeryItems.requestDate = rowData.requestDate;
            this.infoToSearchIssuesStationeryItems.managerUserName = this.uf.userName;
            this.infoToSearchIssuesStationeryItems.status = rowData.status;
            this.selectedStationeryItem = rowData;
            this.stationeryService.findIssuesStationeryItems(this.infoToSearchIssuesStationeryItems).subscribe(
              y => {
                  this.listStationeryFullUnit = y.data;
              }
            );
        } else {
            if (this.selectedItem.length == 1) {
                this.dialogConfirmRequest = true;
                this.infoToSearchIssuesStationeryItems.requestDate = this.selectedItem[0].requestDate;
                this.infoToSearchIssuesStationeryItems.managerUserName = this.uf.userName;
                this.infoToSearchIssuesStationeryItems.status = this.selectedItem[0].status;
                this.selectedStationeryItem = this.selectedItem[0];
                this.stationeryService.findIssuesStationeryItems(this.infoToSearchIssuesStationeryItems).subscribe(
                  y => {
                      this.listStationeryFullUnit = y.data;
                  }
                );
            } else {
                // this.app.confirmMessage()
            }
        }
    }

    onValidate(place: AutoComplete): boolean {
        if (!this.placeStationery || !this.placeStationery.placeId) {
            ValidateUtils.validateAutoComplete(place, 'Vị trí không được để trống', this.app);
            return false;
        }
        if(!this.lstChefInfor || this.lstChefInfor.length == 0) {
            this.app.showWarn('Đồng chí chưa chọn văn phòng phẩm');
            return;
        }

        for(let i = 0; i < this.lstChefInfor.length; i++) {
            let lstChefInfor: StationeryReportRequest = this.lstChefInfor[i];
            if(!lstChefInfor.stationeryId) {
                this.app.showWarn('Văn phòng phẩm không được để trống');
                return false;
            }
            if(!lstChefInfor.quantity || lstChefInfor.quantity === 0) {
                this.app.showWarn('Số lượng không được để trống');
                return false;
            }
        }
        return true;
    }

    handleRequest(item) {
        this.dialogDetailsRequest = true;
    }

    confirmRequest(rowData) {
        this.infoToSearchIssuesStationeryItems.requestDate = rowData.requestDate;
        this.infoToSearchIssuesStationeryItems.managerUserName = this.uf.userName;
        this.infoToSearchIssuesStationeryItems.status = rowData.status;
        this.selectedStationeryItem = rowData;
        this.stationeryService.findIssuesStationeryItems(this.infoToSearchIssuesStationeryItems).subscribe(y => {
              this.listStationeryFullUnit = y.data;
          }
        );
        this.dialogAcceptRequest = true;
    }

    doVoteHCDV(rowData) {
        this.infoToSearchIssuesStationeryItems.requestDate = rowData.requestDate;
        this.infoToSearchIssuesStationeryItems.managerUserName = this.uf.userName;
        this.infoToSearchIssuesStationeryItems.status = rowData.status;
        this.selectedStationeryItem = rowData;
        this.stationeryService.findIssuesStationeryItems(this.infoToSearchIssuesStationeryItems).subscribe(
          y => {
              this.listStationeryFullUnit = y.data;
          }
        );
        this.dialogVoteHCDVStationery = true
    }

    doEdit(event) {
        this.editObjectEmployee = event;
        this.isCancel = false;
        this.isShowAddingVPP = true;
        this.unitEmployee = event.unitName;
        this.nameEmployee = event.fullName;
        this.phoneEmployee = event.phoneNumber;
        this.emailEmployee = event.email;
        this.placeEmployee = event.startPlace;
        this.massage = event.note;
        this.statusEmployee = event.status;
        this.placeStationery = {placeId: event.placeId, placeName: event.startPlace, lstPlaceId: ''};

        if (event.issuesStationeryItemId) {
            this.searchDataById.issuesStationeryApprovedId = event.issuesStationeryId;
            this.stationeryService.getIssuesStationeryItemsById(this.searchDataById).subscribe(res => {
                if (res) {
                    this.stationeryNameListById = res.data;
                    this.stationeryService.findStationeryByName().subscribe(data => {
                        this.stationeryNameList = data.data;
                        for(let i = 0; this.stationeryNameListById.length; i++) {
                            let stationery = this.stationeryNameListById[i];
                            if(!this.checkExistStationery(stationery.stationeryId)) {
                                if(!this.stationeryNameList) this.stationeryNameList = [];
                                this.stationeryNameList.push({stationeryId: stationery.stationeryId, stationeryName: stationery.stationeryName,
                                    calculationUnit: stationery.calUnit, unitPrice: stationery.unitPrice});
                            }
                        }
                      }
                    );

                    this.lstChefInfor = [];
                    let approved = false;
                    if(event.approvedItem > 0) approved = true;
                    this.stationeryNameListById.forEach(el => {
                        let disable = false;
                        if(el.issuesStationeryApproveId) {
                            disable = true;
                            this.isShowAddingVPP = false;
                        }
                        this.lstChefInfor.push(new StationeryReportRequest(el.stationeryId, el.quantity, el.calUnit, el.unitPrice,
                          el.stationeryName, el.issuesStationeryItemId, el.issuesStationeryApproveId, disable, approved));
                    });

                    if(this.lstChefInfor.length === 1) this.isDisableRemoveBtn = true;
                    this.editIssuesStationey.issuesStationeryIdOld = event.issuesStationeryId;
                    this.editIssuesStationey.placeID = event.placeId;
                    this.stationeryService.findSpentQuotaByUser(event.employeeUsername).subscribe(res => {
                        if (res) {
                            this.quotaEmployee = res.data;
                        }
                        this.dialogEditRequestEmployee = true;
                    });
                }
            });
        }
    }

    editEmployee() {
        this.isDisableAddBtn = true;
        for(let i = 0; i < this.lstChefInfor.length; i++) {
            let lstChefInfor: StationeryReportRequest = this.lstChefInfor[i];
            if(!lstChefInfor.stationeryId) {
                this.app.showWarn('Văn phòng phẩm không được để trống');
                this.isDisableAddBtn = false;
                return false;
            }
            if(!lstChefInfor.quantity || lstChefInfor.quantity === 0) {
                this.app.showWarn('Số lượng không được để trống');
                this.isDisableAddBtn = false;
                return false;
            }
        }

        this.editIssuesStationey.placeID = this.placeStationery.placeId.toString();
        this.editIssuesStationey.note = this.massage;

        this.editIssuesStationey.listStationery = this.lstChefInfor;
        this.stationeryService.editIssuesStationery(this.editIssuesStationey).subscribe(y => {
              if (y.status == 1) {
                  this.app.showSuccess('Đã cập nhật yêu cầu văn phòng phẩm');
                  this.onClosedialog();
                  this.handleSearchEmployee();
              } else if (y.status == 2) {
                  this.app.showWarn('Vượt quá hạn mức trong tháng');
              } else if(y.status === 3 ) {
                  this.app.showWarn('Vượt quá hạn mức tháng');
                  this.handleSearchEmployee();
              } else if(y.status === 4) {
                  this.app.showWarn('Yêu cầu đã được hủy trước đó, thực hiện không thành công');
                  this.onClosedialog();
                  this.handleSearchEmployee();
              } else {
                  this.app.showWarn('Cập nhật thất bại');
              }
            this.isDisableAddBtn = false;
          }
        );
    }

    doCancel(item) {
        this.isCancel = true;
        this.unitEmployee = item.unitName;
        this.nameEmployee = item.fullName;
        this.phoneEmployee = item.phoneNumber;
        this.emailEmployee = item.email;
        this.placeEmployee = item.startPlace;
        this.massage = item.note;
        this.statusEmployee = item.status;
        this.placeStationery = {placeId: item.placeId, placeName: item.startPlace, lstPlaceId: ''};
        if (item.issuesStationeryItemId) {
            this.searchDataById.issuesStationeryApprovedId = item.issuesStationeryId;
            this.stationeryService.getIssuesStationeryItemsById(this.searchDataById).subscribe(res => {
                if (res) {
                    this.stationeryNameListById = res.data;
                    this.stationeryService.findStationeryByName().subscribe(data => {
                        this.stationeryNameList = data.data;
                          for(let i = 0; this.stationeryNameListById.length; i++) {
                              let stationery = this.stationeryNameListById[i];
                              if(!this.checkExistStationery(stationery.stationeryId)) {
                                  if(!this.stationeryNameList) this.stationeryNameList = [];
                                  this.stationeryNameList.push({stationeryId: stationery.stationeryId, stationeryName: stationery.stationeryName,
                                      calculationUnit: stationery.calUnit, unitPrice: stationery.unitPrice});
                              }
                          }
                      }
                    );

                    this.lstChefInfor = [];
                    this.stationeryNameListById.forEach(el => {
                        this.lstChefInfor.push(new StationeryReportRequest(el.stationeryId, el.quantity, el.calUnit, el.unitPrice, el.stationeryName, el.issuesStationeryItemId));
                        this.canceIssuesStationey.listIssueStationeryItemId = this.lstChefInfor;

                    });
                }
                this.stationeryService.findSpentQuotaByUser(item.employeeUsername).subscribe(res => {
                    if (res) { this.quotaEmployee = res.data; }
                    this.dialogEditRequestEmployee = true;
                });
            });
        }
        this.canceIssuesStationey.issuesStationeryId = item.issuesStationeryId;
    }

    cancelEmployee() {
        this.isDisableAddBtn = true;
        this.onHideCancelConfirmDlg();
        this.stationeryService.updateCancelIssuesStationery(this.canceIssuesStationey).subscribe(y => {
              if (y.status == 1) {
                  this.app.showSuccess('Đã hủy yêu cầu văn phòng phẩm');
                  this.onClosedialog();
                  this.handleSearchEmployee();
              } else if(y.status === 3) {
                  this.app.showWarn('Yêu cầu đã được hủy trước đó, thực hiện không thành công');
                  this.onClosedialog();
                  this.handleSearchEmployee();
              } else {
                  this.app.showWarn('Hủy yêu cầu văn phòng phẩm thất bại');
              }
            this.isDisableAddBtn = false;
          }
        );
    }

    convertDate(date) {
        return new DatePipe("en-US").transform(date, 'dd-MM-yyyy');
    }

    importExcel() {
        this.dialogImport = true;
    }

    execute() {
        if (!this.selectedItemIds || this.selectedItemIds.length === 0) {
            this.app.showWarn('Đồng chí chưa chọn yêu cầu nào');
            return;
        }
        let component = this;
        let callback = () => {
            component.isDialogBrowseRequest = true;
        }

        //TODO: review and rewrite
        this.listChooseEmployee = [];
        for(let i = 0; i < this.listStationeryReportEmployee.length; i++) {
            for(let j = 0; j < this.selectedItemIds.length; j++) {
                if(this.listStationeryReportEmployee[i].issuesStationeryItemId === this.selectedItemIds[j]) {
                    this.listChooseEmployee.push(this.listStationeryReportEmployee[i]);
                }
            }
        }
        this.browseRequestComponent.onModelChange(this.listChooseEmployee, callback);
    }

    onHideExcute() {
        this.browseRequestComponent.handleCancel();
        this.listChooseEmployee = null;
    }

    onCloseResultBrowseRequest(event) {
        if (event == 1) {
            this.isDialogBrowseRequest = false;
            this.handleSearchEmployee();
        }
    }

    openImportExcel() {
        this.showImportForm = true;
    }

    uploadExcel(event: any) {
        this.isDisableAddBtn = true;
        var formData = new FormData();
        var file = event.files[0];
        formData.append('file', file);

        this.stationeryService.importExcelStationeryReport(formData).subscribe((res: Response) => {
            var mess = res.headers.get('messageCode');
            if (mess === 'failFormat') {
                this.notiImportFile = 'Format file import không chính xác. Vui lòng kiểm tra lại.';
                this.isDisableDownBtn = false;
            } else if (mess === 'valid') {
                this.notiImportFile = 'Format file import không chính xác. Vui lòng kiểm tra lại.';
                this.isDisableDownBtn = false;
            }  else if (mess === 'fail') {
                this.notiImportFile = 'Import không thành công. Tải về file kết quả để kiểm tra lại.';
                this.isDisableDownBtn = true;
                this.file = new Blob([res.body], {type: 'application/xlsx'});
            } else if (mess === 'note') {
                this.notiImportFile = 'Ghi chú vượt quá độ dài cho phép (255). Vui lòng kiểm tra lại.';
                this.file = new Blob([res.body], {type: 'application/xlsx'});
                this.isDisableDownBtn = true;
            } else if (mess === 'success') {
                this.notiImportFile = 'Import thành công.';
                this.file = new Blob([res.body], {type: 'application/xlsx'});
                this.isDisableDownBtn = true;
                this.handleSearchEmployee();
            } else if (mess === 'limited') {
                this.notiImportFile = 'Vượt quá hạn mức.';
                this.isDisableDownBtn = false;
            } else if (mess === 'norow') {
                this.notiImportFile = 'Không có bản ghi nào thỏa mãn điều kiện.';
                this.file = new Blob([res.body], {type: 'application/xlsx'});
                this.isDisableDownBtn = true;
            } else {
                this.alertMessage('error', 'Cảnh báo', 'Import thất bại');
            }
            this.isDisableAddBtn = false;
        });
    }

    onHideImportDialog() {
        this.notiImportFile = null;
        this.file = null;
        this.fileUpload.clear();
    }

    alertMessage(severity: string, summary: string, detail: string) {
        this.messageService.add({
            severity: severity,
            summary: summary,
            detail: detail
        });
    }

    clearExcel() {
        this.notiImportFile = null;
    }

    onCancelExcel() {
        this.showImportForm = false;
    }

    exportTemplateExcel() {
        this.stationeryService.templateImportExcelStationeryReport({}).subscribe(
          (res: Response) => {
              var fileName = 'Import_stationery_template.xlsx';
              const file: Blob = new Blob([res.body], {type: 'application/xlsx'});
              saveAs(file, fileName);
          }
        );
    }

    exportImportResult() {
        var fileName = 'Import_stationery_result.xlsx';
        saveAs(this.file, fileName);
    }

    onClosedialog() {
        this.isDisableRemoveBtn = false;
        this.isView = false;
        this.isShowAddingVPP = false;
        this.dialogDetailsRequestEmployee = false;
        this.dialogAddStationery = false;
        this.dialogEditRequestEmployee = false;
        this.placeStationery = null;
        this.massage = null;
        this.listChooseEmployee = [];
        this.lstChefInfor = [];
        this.quotaEmployee = null;
        this.note = null;
        this.lstChefInfor.push(new StationeryReportRequest(null, 1, null, null, null, null))
        this.isDisableAddBtn = false;
        this.stationeryService.findStationeryByName().subscribe(data => {
              this.stationeryNameList = data.data;
          }
        );
    }

    setEditable(items: StationeryEmployee[]) {
        if(!items || items.length === 0) return;
        for(let i = 0; i < items.length; i++) {
            let item = items[i];
            if(this.uf.userName.toString() === item.employeeUsername) {
                if(item.status === 1 && item.statusItem === 0) item.isEdit = true;
                if(item.status === 1 && item.approvedItem === 0) item.isCancel = true;
            }

            if(this.isAdmin || this.isHCDV) {
                if(item.status === 1 && item.statusItem === 0) item.isApprove = true;
            }
        }
    }

    checkExistStationery(stationeryItemId: string) {
        if(!this.stationeryNameList || this.stationeryNameList.length === 0) return false;
        for(let i = 0; i < this.stationeryNameList.length; i++) {
            let item = this.stationeryNameList[i];
            if(stationeryItemId === item.stationeryId) return true;
        }
        return false;
    }

    onKeypressCreateTrimming(event) {
        if(event.code === 'Space') {
            if(this.note) this.note = this.note.trim();
        }
    }

    onChangeCreateTrimming() {
        if(this.note) this.note= this.note.trim();
    }

    onKeypressEditTrimming(event) {
        if(event.code === 'Space') {
            if(this.massage) this.massage = this.massage.trim();
        }
    }

    onChangeEditTrimming() {
        if(this.massage) this.massage= this.massage.trim();
    }

    onHideCancelConfirmDlg() {
        this.isShowCancelConfirmDlg = false;
    }

    onShowCancelConfigmDlg() {
        this.isShowCancelConfirmDlg = true;
    }
}
