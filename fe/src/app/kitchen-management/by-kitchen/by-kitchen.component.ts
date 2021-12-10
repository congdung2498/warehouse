import { Component, OnInit, OnDestroy, ViewChild, ViewChildren } from '@angular/core';
import { TreeNode, MessageService } from 'primeng/components/common/api';
import { KitchenManagementService } from '../kitchen-management.service';
import { ResponseAPI } from '../Entity/Response';
import { Router } from '@angular/router';
import { Subject } from 'rxjs/Subject';
import { saveAs } from 'file-saver';
import { takeUntil } from 'rxjs/operators/takeUntil';
import { map } from 'rxjs/operators/map';
import { TokenStorage } from '../../shared/token.storage';
import { Table } from 'primeng/table';
import { Title } from '@angular/platform-browser';
import {DateTimeUtil} from "../../../common/datetime";
import {ReportKitchen} from "../Entity/Kitchen";

interface Kitchen {
  kitchenId: String;
  label: String;
}

@Component({
  selector: 'app-by-kitchen',
  templateUrl: './by-kitchen.component.html',
  styleUrls: ['./by-kitchen.component.css']
})


export class ByKitchenComponent implements OnInit, OnDestroy {
  // table
  @ViewChild('dt')
  private dataTableComponent: Table;

  // mark that this component destroyed
  private destroy$: Subject<boolean> = new Subject<boolean>();

  // table is loading or not
  loading = true;

  loadingTree = false;
  // cols in table
  cols: any[];

  // date field
  dateValue = new Date();
  maxDateValue = new Date();
  blankDateValue = new Date().toLocaleDateString('en-US', { month: '2-digit', year: 'numeric' });

  // search date
  searchDateValue = new Date();

  vn = DateTimeUtil.vn;
  searchBtn = false;

  months = Array(31).fill(1).map((x, i) => i + 1);

  selectedKitchen: ReportKitchen;

  sectors: TreeNode[];

  selectedSector: TreeNode[];

  report: any[];

  results: ReportKitchen[];

  totalRecords: number;
  startRow: number;
  rowSize: number;

  isAdmin: boolean;
  isChef: boolean;

  constructor(private kitchenService: KitchenManagementService, private router: Router,
    private messageService: MessageService, private tokenStorage: TokenStorage, private title: Title) {
    this.title.setTitle('Thống Kê Đặt Cơm Theo Theo Đơn Vị - VTNet');
  }

  ngOnInit() {
    let component = this;
    let callback = () : void => {
      if (component.tokenStorage.getUserInfo().role.indexOf('PMQT_ADMIN') >= 0) {
        component.kitchenService.getSectors(null).pipe(takeUntil(component.destroy$)).subscribe(data => {
          component.sectors = data;
          component.searchData();
        });
        component.initAdmin();
      } else if (this.tokenStorage.getUserInfo().role.indexOf('PMQT_HCVP') >= 0 ||
        component.tokenStorage.getUserInfo().role.indexOf('PMQT_HC_DV') >= 0 ||
        component.tokenStorage.getUserInfo().role.indexOf('PMQT_QL') >= 0 ||
        component.tokenStorage.getUserInfo().role.indexOf('PMQT_CVP') >= 0) {
        component.kitchenService.getSectorGreatFather(component.tokenStorage.getUserInfo().unitId + '').pipe(takeUntil(component.destroy$)).subscribe(data => {
          component.sectors = data;
          component.searchData();
        });
        component.initAdmin();
      } else if (component.tokenStorage.getUserInfo().role.indexOf('PMQT_Bep_truong') >= 0) {
        component.kitchenService.getSectors(null).pipe(takeUntil(component.destroy$)).subscribe(data => {
          component.sectors = data;
        });
        component.initChef();
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

      this.kitchenService.getReport(kitchenIds, this.dateValue, null).pipe(takeUntil(this.destroy$)).subscribe(response => {
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
      this.tokenStorage.getUserInfo().role.indexOf('PMQT_CVP') >= 0) {
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

  searchData() {
    this.loading = true;

    // kitchenID -- null will search for all
    let kitchenIds: string[] = [];

    // dateValue will be current if user didn't pick one
    this.dateValue = this.dateValue || new Date();

    // List Unit
    let listUnit = new Array();

    if (this.dateValue.getMonth() === new Date().getMonth() &&
      this.dateValue.getFullYear() === new Date().getFullYear()) {
      this.dateValue = new Date();
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
        listUnit.push(element.data);
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

    this.kitchenService.getReport(kitchenIds, this.dateValue, listUnit).pipe(takeUntil(this.destroy$)).subscribe(response => {
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

  processReportData(group: any[]): any {
    // reset Table
    if (this.dataTableComponent !== undefined) {
      this.dataTableComponent.reset();
    }
    if (group === null || group === undefined) { return undefined; }
    const output = group.reduce(function (groups, item) {
      const val = { priceEach: item['priceEach'], unitId: item['unitId'], unitName: item['unitName'],
      detailUnit: item['detailUnit'], kitchenId: item['kitchenId'], kitchenName: item['kitchenName'], months: new Array(31) };
      const find = groups.find(function (element) {
        return element['priceEach'] === item['priceEach'] && element['unitId'] === item['unitId']
        && element['kitchenName'] === item['kitchenName'] && element['kitchenId'] === item['kitchenId'];
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
      if(!element['unitId']) {
        element['unitName'] = 'Khác (chưa có đơn vị)';
      }
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
        listUnit.push(element.data);
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
      .getReportExcel(kitchenIds, this.dateValue, listUnit).pipe(
        map(res => res),
        takeUntil(this.destroy$)
      )
      .subscribe(res => {
        const fileName = 'THSATDV_' + this.tokenStorage.getUserInfo().userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
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

  onUnitSelect() {
    this.selectedSector = null;
  }

  clear() {
    this.messageService.clear();
  }

  ngOnDestroy() {
    this.destroy$.next(true);
    // Now let's also unsubscribe from the subject itself:
    this.destroy$.unsubscribe();
  }
}

