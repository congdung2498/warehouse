import {Component, OnInit, ViewChild} from '@angular/core';
import {DateTimeUtil} from "../../../../common/datetime";
import {StationeryService} from "../../stationery.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {RequestUnit} from "../../Entity/RequestUnit";
import {DatePipe} from "@angular/common";
import {AppComponent} from "../../../app.component";
import {Table} from "primeng/table";
import {Router} from "@angular/router";
import {BookcarService} from "../../../bookcar/bookcar.service";
import {IssuesStationeryApproved} from "../../Entity/IssuesStationeryApproved";

@Component({
  selector: 'app-process-unit',
  templateUrl: './process-unit.component.html',
  styleUrls: ['./process-unit.component.css']
})
export class ProcessUnitComponent implements OnInit {

  @ViewChild(Table) dataTableComponent: Table;
  checkedAll: boolean;
  count = 0;
  isChecked: boolean;
  totalMoney: number;
  message: string;
  approveUserName :any= [];
  resultList: any = [];
  listActivity: any;
  userName : string;
  issuesStationeryApproved: IssuesStationeryApproved = {
    employeeUserName: '',
    message: '',
    approveUserName: '',
    listRequestID: []

  }


  requestUnit: RequestUnit = {
    requestDate: '',
    status: null,
    pageNumber: null,
    pageSize: null,
    employeeUserName: '',
    roleAdmin: false,
    managerUserName: ''


  };
  totalRcord: number;
  documentList = new Array();
  userEmployeeName: string;
  dateFrom: Date;
  formSearch: FormGroup;
  fullNameList = new Array();
  vn = DateTimeUtil.vn;
  employeeUserName : string;
  constructor(private stationeryService: StationeryService,
              private formBuilder: FormBuilder,
              private app: AppComponent,
              private router: Router) {

  }

  ngOnInit() {
    this.buildForm();
    this.listActivity = [];
  }

  get f() {
    return this.formSearch.controls;
  }

  private buildForm(): void {
    this.formSearch = this.formBuilder.group({
      fullName: [''],
      hcdvUserName: ['']

    });
  }

  getManager(ev) {
    this.stationeryService.getListManagerDirect(ev.query)
      .subscribe(res => {
        this.documentList = res.data;

      });
  }

  searchFullNameInRole(ev) {

      this.stationeryService.searchFullNameInRole(ev.query)
          .subscribe(res => {
            this.fullNameList = res.data;
            console.log(res);
          });

  }

  settingParams() {
    this.requestUnit.pageSize = 10;
    this.requestUnit.pageNumber = 1;
    this.requestUnit.requestDate = new DatePipe('en-US').transform(this.dateFrom, 'yyyy-MM-dd');
    this.requestUnit.employeeUserName = this.employeeUserName;

  }

  searchData() {

    this.settingParams();

    this.dataTableComponent.reset();
  }

  public onLazyLoad(event) {
    this.checkedAll = false;
    this.requestUnit.pageNumber = event.first;
    this.requestUnit.pageSize = event.rows;


    this.stationeryService.searchProcessUnit(this.requestUnit).subscribe(res => {
      this.resultList = res.data;
      this.totalRcord = res.data.length;
      console.log(this.resultList);
      console.log(this.totalRcord);
      this.router.navigate(['/stationery/requestStationery']);
      this.app.showSuccess('Gửi thành công')
    });


  }

  public checkedAllItem(event) {


    if (this.checkedAll) {
      this.count = 0;
      event.forEach(element => {
        element.isChecked = false;
        const index = this.listActivity.indexOf(element);

        this.listActivity.splice(index, 1);
      });
    } else {
      this.count = this.resultList.length;
      const listTemp = Object.assign([], this.listActivity);
      event.forEach(element => {
        element.isChecked = true;
        var isExists = false;
        var money;
        for (let i = 0; i < listTemp.length; i++) {
          if (listTemp[i].requestID === element.requestID) {
            isExists = true;
          }
          money = element[i].totalMoney;
          this.totalMoney = money * (this.count);
          console.log(money);
          console.log(this.totalMoney);
        }
        if (!isExists) {
          this.listActivity.push(element);
        }
      });

    }
  }

  public checkedItem(item) {
    var moneyRecord = 0
    let money = item.totalMoney;
    if (item.isChecked) {
      this.count--;
      const index = this.listActivity.indexOf(item);
      this.listActivity.splice(index, 1);
      this.checkedAll = false;
      this.totalMoney -= money;
    } else {
      this.count++;
      if (this.count == this.resultList.length) {
        this.checkedAll = true;
      }
      if (!this.totalMoney) this.totalMoney = money;
      else this.totalMoney += money;

      this.listActivity.push(item);
      console.log(this.listActivity);
    }
  }

  setValue() {
    this.issuesStationeryApproved.approveUserName = this.approveUserName.userName;
    this.issuesStationeryApproved.message = this.message;
    const listTemp = Object.assign([], this.listActivity);

    for (let i = 0; i < listTemp.length; i++) {
      this.issuesStationeryApproved.employeeUserName = listTemp[i].employeeUserName;
      this.issuesStationeryApproved.listRequestID.push(listTemp[i].requestID);


    }


  }

  approve() {
    this.setValue();
    this.app.confirmMessage(null, () => {// on accepted

      this.stationeryService.approveIssuesStationeryItems(this.issuesStationeryApproved).subscribe(res => {
        //this.router.navigate(['/stationery/requestStationery']);
        if (res.status == 1) {
          this.app.showSuccess('Gửi thành công');
          this.onRefresh();
        }
      });

    }, () => {
      this.app.showWarn('Gửi thất bại');
    });


  }

  onRefresh() {
    this.totalMoney = 0;
    this.message = null;
    this.approveUserName = null;
    this.listActivity = [];
  }
}
