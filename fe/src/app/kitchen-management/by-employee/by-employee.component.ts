import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { TreeNode, MessageService } from 'primeng/components/common/api';
import { KitchenManagementService } from '../kitchen-management.service';
import { ResponseAPI } from '../Entity/Response';
import { Title } from '@angular/platform-browser';
import { TokenStorage } from '../../shared/token.storage';
import { saveAs } from 'file-saver';
import { Subject } from 'rxjs/Subject';
import { Table } from 'primeng/table';
import { takeUntil } from 'rxjs/operators/takeUntil';
import { map } from 'rxjs/operators/map';
import {DateTimeUtil, Month} from "../../../common/datetime";
import {ReportKitchen} from "../Entity/Kitchen";


interface Kitchen {
  kitchenId: String;
  label: String;
}

@Component({
  selector: 'app-by-employee',
  templateUrl: './by-employee.component.html',
  styleUrls: ['./by-employee.component.css']
})
export class ByEmployeeComponent implements OnInit, OnDestroy {
  // table
  @ViewChild('dt')
  private dataTableComponent: Table;

  // mark that this component destroyed
  private destroy$: Subject<boolean> = new Subject<boolean>();
  // table is loading or not
  loading = true;

  // cols in table
  cols: any[];

  // date field
  dateValue = new Date();
  maxDateValue = new Date();
  blankDateValue = new Date().toLocaleDateString('en-US', { month: '2-digit', year: 'numeric' });

  // search date
  searchDateValue = new Date();
  empInfor: any = {
    result: '',
    userName: ''
  };
  filterEmployee: any[];
  onlyCharEmail: RegExp = /^[0-9a-zA-Z@ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂ ưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ()_\-\.]+$/;

  vn = DateTimeUtil.vn;
  searchBtn = false;
  months = Array(31).fill(1).map((x, i) => i + 1);
  selectedKitchen: ReportKitchen;
  sectors: TreeNode[];
  selectedSector: TreeNode[];
  report: any[];
  results: ReportKitchen[];
  loadingTree = false;
  totalRecords: number;
  startRow: number;
  rowSize: number;

  isAdmin: boolean;
  isManager: boolean;
  isChef: boolean;
  isEmployee: boolean;

  constructor(private kitchenService: KitchenManagementService,
    private messageService: MessageService, private tokenStorage: TokenStorage, private title: Title) {
    this.title.setTitle('Thống Kê Đặt Cơm Theo Nhân Viên - VTNet');
    /*let component = this;
    let callback = () : void => {
      component.getSectors();
    }
    this.tokenStorage.getSecurityAccount(callback);*/
  }

  ngOnInit() {
    let component = this;
    let callback = () : void => {
      if (component.tokenStorage.getUserInfo().role.indexOf('PMQT_ADMIN') >= 0) {
        component.kitchenService.getSectors(null).pipe(takeUntil(component.destroy$)).subscribe(data => {
          component.sectors = data; this.doAfter();
        });
        component.initAdmin();
      } else if (component.tokenStorage.getUserInfo().role.indexOf('PMQT_HCVP') >= 0 ||
        component.tokenStorage.getUserInfo().role.indexOf('PMQT_HC_DV') >= 0 ||
        component.tokenStorage.getUserInfo().role.indexOf('PMQT_QL') >= 0 ||
        component.tokenStorage.getUserInfo().role.indexOf('PMQT_CVP') >= 0
      ) {
        component.kitchenService
          .getSectorGreatFather(this.tokenStorage.getUserInfo().unitId + '').pipe(takeUntil(component.destroy$)).subscribe(data => {
          component.sectors = data; this.doAfter();
        });
        component.initManager();
      } else if (component.tokenStorage.getUserInfo().role.indexOf('PMQT_Bep_truong') >= 0) {
        component.kitchenService.getSectors(null).pipe(takeUntil(component.destroy$)).subscribe(data => {
          component.sectors = data; component.doAfter();
        });
        component.initChef();
      } else if (component.tokenStorage.getUserInfo().role.indexOf('PMQT_NV') >= 0) {
        component.sectors = [{
          label: component.tokenStorage.getUserInfo().unitName as string,
          data: component.tokenStorage.getUserInfo().unitId,
          selectable: false
        }];
        component.selectedSector = [component.sectors[0]];
        component.doAfter();
        component.initEmployee();
      }
    }
    this.tokenStorage.getSecurityAccount(callback);
  }

  initAdmin() {
    this.isAdmin = true;
    this.kitchenService.getAllKitchenFullName().subscribe(data => {
      this.results = data['data'];
    });
  }

  initChef() {
    this.isChef = true;
    this.kitchenService.getKitchenByChefFullName().subscribe(data => {
      this.results = data['data'];
      let kitchenIds: string[] = [];
      if(this.results) {
        for(let i = 0; i < this.results.length; i++) {
          if(this.results[i].kitchenID) kitchenIds.push(this.results[i].kitchenID);
        }
      }

      this.kitchenService
        .getReportByEmployee(kitchenIds, new Date(this.dateValue.getTime() - (this.dateValue.getTimezoneOffset() * 60000)),
          null, null, null).pipe(
        takeUntil(this.destroy$)
      )
        .subscribe(response => {
          this.searchDateValue = this.dateValue;
          this.report = this.processReportData(response['data']);
          this.startRow = 0;
          if (this.report.length > 10) {
            this.rowSize = 10;
          } else {
            this.rowSize = this.report.length;
          }
          this.totalRecords = this.report.length;
        });
    });
  }

  initManager() {
    this.isManager = true;
    this.kitchenService.getAllKitchenFullName().subscribe(data => {
      this.results = data['data'];
    });
  }

  initEmployee() {
    this.isEmployee = true;
    this.kitchenService.getAllKitchenFullName().subscribe(data => {
      this.results = data['data'];
    });
  }


  doAfter() {
    if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_HCVP') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_HC_DV') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_QL') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_CVP') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_ADMIN') >= 0) {
      this.searchData();
    } else if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_Bep_truong') >= 0) {
      this.kitchenService.getResultsAPI('').subscribe(data => {
        this.searchData();
      });
    } else if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_NV') >= 0) {
      this.empInfor = {
        result: this.tokenStorage.getUserInfo().fullName as string,
        userName: this.tokenStorage.getUserInfo().userName as string
      };
      this.kitchenService.getResultsAPI('').subscribe(data => {
        this.searchData();
      });
    }
  }

  getSectors(): void {
    if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_ADMIN') >= 0) {
      this.kitchenService
      .getSectors(null).pipe(
        takeUntil(this.destroy$)
      )
      .subscribe(data => this.sectors = data);
    } else if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_HCVP') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_HC_DV') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_QL') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_CVP') >= 0
    ) {
      this.kitchenService
      .getSectorGreatFather(this.tokenStorage.getUserInfo().unitId + '').pipe(
        takeUntil(this.destroy$)
      )
      .subscribe(data => this.sectors = data);
    } else if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_Bep_truong') >= 0) {

      this.kitchenService
      .getSectors(null).pipe(
        takeUntil(this.destroy$)
      )
      .subscribe(data => this.sectors = data);

    } else if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_NV') >= 0) {
      this.sectors = [{
        label: this.tokenStorage.getUserInfo().unitName as string,
        data: this.tokenStorage.getUserInfo().unitId,
        selectable: false
      }];
      this.selectedSector = [this.sectors[0]];
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
          if (this.selectedSector !== null && this.selectedSector !== undefined && event.node !== null &&
            event.node !== undefined && this.selectedSector.includes(event.node)) {
            data.forEach(element => {
              if (element !== null && element !== undefined && element.data !== null && element.data !== undefined) {
                this.selectedSector.push(element);
              }
            });
          }
          this.loadingTree = false;
        });
    }
  }

  clear() {
    this.messageService.clear();
  }

  searchData() {
    // enable loading animation
    this.loading = true;

    // kitchenID -- null will search for all
    let kitchenIds: string[] = [];

    // dateValue will be current if user didn't pick one
    this.dateValue = this.dateValue || new Date();

    if (this.dateValue.getMonth() === new Date().getMonth() &&
      this.dateValue.getFullYear() === new Date().getFullYear()) {
      this.dateValue = new Date();
    }
    // List Unit
    let listUnit = new Array();

    // Name for search
    let userName = null;
    let persionInfo = null;
    //  handle employee
    if (this.empInfor != null && this.empInfor.length > 0) {
      persionInfo = this.empInfor;
    } else if (this.empInfor != null
      && this.empInfor['result'] !== undefined) {
      userName = this.empInfor['userName'];
    }

    if(!this.selectedKitchen || !this.selectedKitchen.kitchenID) {
      if(this.isChef) {
        if(this.results) {
          for(let i = 0; i < this.results.length; i++) {
            if(this.results[i].kitchenID) kitchenIds.push(this.results[i].kitchenID);
          }
        }
      }
    } else {
      kitchenIds.push(this.selectedKitchen.kitchenID);
    }

    if (this.selectedSector === null || this.selectedSector === undefined) {
      listUnit = null;
    } else {
      this.selectedSector.forEach(element => {
        if (element !== null && element !== undefined && element.data !== null && element.data !== undefined) {
          listUnit.push(element.data);
        }
      });
    }

    if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_ADMIN') < 0 &&
      (this.tokenStorage.getUserInfo().role.indexOf('PMQT_HCVP') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_HC_DV') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_QL') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_CVP') >= 0)) {
      if (listUnit === null || listUnit === undefined || listUnit === [] || listUnit.length === 0) {
        listUnit = [this.sectors[0].data];
      }
    }

    this.kitchenService
      .getReportByEmployee(kitchenIds, new Date(this.dateValue.getTime() - (this.dateValue.getTimezoneOffset() * 60000)),
        listUnit, userName, persionInfo).pipe(
          takeUntil(this.destroy$)
        )
      .subscribe(response => {
        this.searchDateValue = this.dateValue;
        this.report = this.processReportData(response['data']);
        this.startRow = 0;
        if (this.report.length > 10) {
          this.rowSize = 10;
        } else {
          this.rowSize = this.report.length;
        }
        this.totalRecords = this.report.length;
      });
  }

  paginate(event) {
    this.startRow = event.first;
    this.totalRecords = this.report.length;
  }

  getPermission(): number {
    if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_HCVP') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_HC_DV') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_QL') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_CVP') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_ADMIN') >= 0) {
      return 1;
    } else if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_Bep_truong') >= 0) {
      return 0;
    } else {
      return 3;
    }
  }

  processReportData(group: any[]): any {
    // reset Table
    if (this.dataTableComponent !== undefined) { this.dataTableComponent.reset();}

    if (group === null || group === undefined) { return undefined; }
    const output = group.reduce(function (groups, item) {
      const val = {
        code: item['code'], fullName: item['fullName'], priceEach: item['priceEach'], unitName: item['unitName'],
        detailUnit: item['detailUnit'], kitchenId: item['kitchenId'], kitchenName: item['kitchenName'], months: new Array(31)
      };
      const find = groups.find(function (element) {
        return element['priceEach'] === item['priceEach'] && element['unitName'] === item['unitName'] && element['code'] === item['code']
          && element['fullName'] === item['fullName'] && element['kitchenName'] === item['kitchenName']
          && element['kitchenId'] === item['kitchenId'];
      });
      if (find !== undefined) {
        if (find['months'][item['resultDay'] - 1] === undefined) {
          find['months'][item['resultDay'] - 1] = item['totalMeal'];
        } else {
          find['months'][item['resultDay'] - 1] += item['totalMeal'];
        }
      } else {
        val['months'][item['resultDay'] - 1] = item['totalMeal'];
        groups.push(val);
      }
      return groups;
    }, []);
    output['total'] = 0;
    output['money'] = 0;
    output.forEach(function (element) {
      element['total'] = element['months'].reduce(function (s, v) {
        return s + (v || 0);
      }, 0);
      output['total'] += (element['total'] || 0);
      output['money'] += (element['total'] || 0) * element['priceEach'];
      if(!element['unitName']) element['unitName'] = 'Khác (chưa có đơn vị)';
    });
    // disable loading animation
    this.loading = false;
    return output;
  }

  exportExcel(): void {
    // enable loading animation
    this.loading = true;

    // kitchenID -- null will search for all
    let kitchenIds: string[] = [];

    // dateValue will be current if user didn't pick one
    this.dateValue = this.dateValue || new Date();

    if (this.dateValue.getMonth() === new Date().getMonth() &&
      this.dateValue.getFullYear() === new Date().getFullYear()) {
      this.dateValue = new Date();
    }
    // List Unit
    let listUnit = new Array();

    // Name for search
    let userName = null;
    let persionInfo = null;
    //  handle employee
    if (this.empInfor != null && this.empInfor.length > 0) {
      persionInfo = this.empInfor;
    } else if (this.empInfor != null
      && this.empInfor['result'] !== undefined) {
      userName = this.empInfor['userName'];
    }

    if(!this.selectedKitchen || !this.selectedKitchen.kitchenID) {
      if(this.isChef) {
        if(this.results) {
          for(let i = 0; i < this.results.length; i++) {
            if(this.results[i].kitchenID) kitchenIds.push(this.results[i].kitchenID);
          }
        }
      }
    } else {
      kitchenIds.push(this.selectedKitchen.kitchenID);
    }

    if (this.selectedSector === null || this.selectedSector === undefined) {
      listUnit = null;
    } else {
      this.selectedSector.forEach(element => {
        if (element !== null && element !== undefined && element.data !== null && element.data !== undefined) {
          listUnit.push(element.data);
        }
      });
    }

    if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_ADMIN') < 0 &&
      (this.tokenStorage.getUserInfo().role.indexOf('PMQT_HCVP') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_HC_DV') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_QL') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_CVP') >= 0)) {
      if (listUnit === null || listUnit === undefined || listUnit === [] || listUnit.length === 0) {
        listUnit = [this.sectors[0].data];
      }
    }

    this.kitchenService
      .getReportExcelByEmployee(kitchenIds, new Date(this.dateValue.getTime() - (this.dateValue.getTimezoneOffset() * 60000)),
        listUnit, userName, persionInfo).pipe(
        map(res => res),
        takeUntil(this.destroy$)
      )
      .subscribe(res => {
        const fileName = 'THSATNV_' + this.tokenStorage.getUserInfo().userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
          + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours() +
          'h' + new Date().getMinutes() + 'm' + new Date().getSeconds()
          + 's' + new Date().getMilliseconds() + 'ms.xlsx';
        const file: Blob = new Blob([res], { type: 'application/xlsx' });
        saveAs(file, fileName);
      }, err => {
        // show the error
      });
    // enable loading animation
    this.loading = false;
  }

  //  show suggestions when type in 'Nhan vien' field
  loadEmployee(event) {
    if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_ADMIN') < 0 ) {
      if( this.tokenStorage.getUserInfo().role.indexOf('PMQT_HCVP') >= 0 ||
        this.tokenStorage.getUserInfo().role.indexOf('PMQT_HC_DV') >= 0 ||
        this.tokenStorage.getUserInfo().role.indexOf('PMQT_CVP') >= 0) {
        let object = null;
        object = {
          'result': event.query.trim().toLowerCase(),
          'listUnit': [this.tokenStorage.getUserInfo().unitId]
        };
        this.getEmployee(object);
      } else if(this.tokenStorage.getUserInfo().role.indexOf('PMQT_QL') >= 0) {
        this.kitchenService.getParentUnitId(this.tokenStorage.getUserInfo().unitId.toString()).subscribe(resp => {
          if(resp) {
            let unitId: string = resp.data;
            let object = {
              'result': event.query.trim().toLowerCase(),
              'listUnit': [unitId]
            };
            this.getEmployee(object);
          }
        });
      }
    } else {
      let object = null;
      object = {
        'result': event.query.trim().toLowerCase(),
        'listUnit': ['']
      };
      this.getEmployee(object);
    }
  }

  //  get employee from database
  getEmployee(object) {
    this.kitchenService.getEmployee(object).subscribe(item => {
      this.filterEmployee = item['data'];
    });
  }

  ngOnDestroy() {
    this.destroy$.next(true);
    // Now let's also unsubscribe from the subject itself:
    this.destroy$.unsubscribe();
  }

  exportReportResponse(): void {
    this.loading = true;

    let kitchenIds: string[] = [];

    // dateValue will be current if user didn't pick one
    this.dateValue = this.dateValue || new Date();

    if (this.dateValue.getMonth() === new Date().getMonth() &&
      this.dateValue.getFullYear() === new Date().getFullYear()) {
      this.dateValue = new Date();
    }
    // List Unit
    let listUnit = new Array();

    // Name for search
    let userName = null;
    let persionInfo = null;
    //  handle employee
    if (this.empInfor != null && this.empInfor.length > 0) {
      persionInfo = this.empInfor;
    } else if (this.empInfor != null
      && this.empInfor['result'] !== undefined) {
      userName = this.empInfor['userName'];
    }

    if(!this.selectedKitchen || !this.selectedKitchen.kitchenID) {
      if(this.isChef) {
        if(this.results) {
          for(let i = 0; i < this.results.length; i++) {
            if(this.results[i].kitchenID) kitchenIds.push(this.results[i].kitchenID);
          }
        }
      }
    } else {
      kitchenIds.push(this.selectedKitchen.kitchenID);
    }

    if (this.selectedSector === null || this.selectedSector === undefined) {
      listUnit = null;
    } else {
      this.selectedSector.forEach(element => {
        if (element !== null && element !== undefined && element.data !== null && element.data !== undefined) {
          listUnit.push(element.data);
        }
      });
    }

    if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_ADMIN') < 0 &&
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_HCVP') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_HC_DV') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_QL') >= 0 ||
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_CVP') >= 0) {
      if (listUnit === null || listUnit === undefined || listUnit === [] || listUnit.length === 0) {
        listUnit = [this.sectors[0].data];
      }
    }

    this.kitchenService
      .importReportResponse(kitchenIds, new Date(this.dateValue.getTime() - (this.dateValue.getTimezoneOffset() * 60000)),
        listUnit, userName, persionInfo)
      .subscribe((res: Response) => {
        const fileName = 'Export_Report_Response.xlsx';
        const file: Blob = new Blob([res.body], {type: 'application/xlsx'});
        saveAs(file, fileName);
        this.loading = false;
      });

    this.loading = false;
  }
}
