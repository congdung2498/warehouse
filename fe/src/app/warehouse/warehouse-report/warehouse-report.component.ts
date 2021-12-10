import { Component, OnInit, ViewChild, Renderer2 } from '@angular/core';
import { AutoComplete, MessageService, ConfirmationService } from 'primeng/primeng';
import { WarehouseService } from '../warehouse.service';
import { Title } from '@angular/platform-browser';
import { Router, NavigationEnd } from '@angular/router';
import { Table } from 'primeng/table';
import { ConditionSearch } from '../Entity/ConditionSearch';
import {saveAs} from 'file-saver';

@Component({
  selector: 'app-warehouse-report',
  templateUrl: './warehouse-report.component.html',
  styleUrls: ['./warehouse-report.component.css']
})
export class WarehouseReportComponent implements OnInit {

  warehouseNameChoose: string;
  listOfWarehouses: any[];
  filterWarehouse: any[];
  @ViewChild("myTable")
  myTable: Table;

  // @ViewChild('inputSquad') private inputSquad: ElementRef;
  @ViewChild('inputWarehouse') private inputWarehouse: AutoComplete;

  // @ViewChild('inputEmployee') private inputEmployee: AutoComplete;

  navigationSubscription;
  // driveSquad: any;
  warehouse_id: number;
  warehouse_name: string;
  position: number;
  total_position: number;
  used_position: number;
  unused_position: number;
  totalRecords: number = 0;
  countActive: number = 0;
  totalRemain: number = 0;
  loading: boolean;

  pageNumber: number = 0;

  pageSize: number = 0;

  listWarehouseSlot: any[];

  conditionSearch: ConditionSearch;

  // // tslint:disable-next-line:max-line-length
  // onlyChar: RegExp = /^[0-9a-zA-ZÝÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÝÌỈĨỊÓÒỎÕỌÔờỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĝ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếờểễệíìỉĩịóòờõờôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;

  // // tslint:disable-next-line:max-line-length
  // notChar: RegExp = /[^0-9a-zA-ZÝÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÝÌỈĨỊÓÒỎÕỌÔờỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĝ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếờểễệíìỉĩịóòờõờôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;

  cols = [
    { field: "", header: "STT", width: "7%" },
    { field: "warehouseName", header: "Kho", width: "23%" },
    { field: "row", header: "Hàng", width: "15%" },
    { field: "column", header: "Cột", width: "10%" },
    { field: "height", header: "Vị trí", width: "10%" },
    { field: "", header: "QR Code", width: "20%" },
    { field: "status", header: "Hiện trạng", width: "15%" }
  ];

  listCol: any[];

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private warehouseService: WarehouseService,
    private router: Router,
    private title: Title,
    private renderer: Renderer2
  ) {
    this.navigationSubscription = this.router.events.subscribe(event => {
      if (!(event instanceof NavigationEnd)) {
        return;
      }
      // Do what you need to do here, for instance :
      title.setTitle("Báo cáo Hiện trạng Kho");
      this.warehouse_name = null;
      this.conditionSearch = new ConditionSearch();
      
    });
    this.warehouseService.getListWarehouseForDropdown().subscribe(
      data => {
        this.listOfWarehouses = data.data;
      }
    );
    this.loading = false;
  }

  ngOnInit() {
    this.countActive = 0;
    this.totalRemain = 0;
  }

  // // ******************************** START CODE MOI *********************************** //

  filterWarehouseName(event) {
    this.filterWarehouse = [];
    this.listOfWarehouses.forEach(element => {
      let warehouse = element;
      if(warehouse.name.toLowerCase().indexOf(event.query.trim().toLowerCase())>-1) {
        this.filterWarehouse.push(warehouse);
      }
    });
  }

  handleSearch() {
    this.pageSize = 10;
    if(this.warehouseNameChoose == undefined || this.warehouseNameChoose == null || this.warehouseNameChoose == '') {
      this.messageService.add({
        severity: "error",
        summary: "Cảnh báo lỗi:",
        detail: 'Bạn cần chọn Kho.'
      });
      this.inputWarehouse.domHandler.findSingle(this.inputWarehouse.el.nativeElement,'input').focus();
      return;
    }
    this.pageNumber = 0;
    this.countActive = 0;
    if (this.myTable !== undefined) {
      this.myTable.reset();
      let value1 =
        this.warehouse_name === undefined ||
          this.warehouse_name === null ||
          this.warehouse_name === ""
          ? null
          : this.warehouse_name;
      let value2 =
        this.warehouse_id === undefined ||
          this.warehouse_id === null
          ? 0 : this.warehouse_id;
      this.conditionSearch.warehouse_id = value2;
      this.conditionSearch.warehouse_name = value1;
      this.conditionSearch.pageNumber = this.pageNumber;
      this.conditionSearch.pageSize = this.pageSize;
      this.findSearchingResult();
    }
  }

  findSearchingResult() {
    let status1: number;
    this.warehouseService
      .getListSlotOfWarehouse(this.conditionSearch)
      .subscribe(data => {
        this.listWarehouseSlot = data.data;
        if (this.listWarehouseSlot.length == 0) {
          this.totalRecords = 0;
        } else {
          this.totalRecords = this.listWarehouseSlot[0].totalRecords;
        }
        this.countActive = this.listWarehouseSlot[0].totalActive;
        this.totalRemain = this.totalRecords - this.countActive;
      });
  }

  showMessage(severity: string, summary: string, detail: string) {
    this.messageService.add({
      severity: severity,
      summary: summary,
      detail: detail
    });
  }

  paginate(event) {
    this.loading = true;
    let pagenumber = event.first;
    this.conditionSearch.pageNumber = pagenumber;
    this.pageNumber = pagenumber;
    this.findSearchingResult();
    this.loading = false;

  }

  setWarehouse(event) {
    this.warehouse_id = event.warehouseId;
    this.warehouseNameChoose = event;
  }
  isSelected() {
        
    if (this.warehouseNameChoose != undefined && this.warehouseNameChoose != null) {
        
        if ( this.filterWarehouse.indexOf(this.warehouseNameChoose)==-1) {
          this.warehouseNameChoose = null;
        }
        if (this.warehouse_id == 0 ) {            
            this.warehouse_id = null;
        }
    }
}
  export() {
    if(this.warehouseNameChoose == undefined || this.warehouseNameChoose == null || this.warehouseNameChoose == '') {
      this.messageService.add({
        severity: "error",
        summary: "Cảnh báo lỗi:",
        detail: 'Bạn cần chọn Kho.'
      });
      this.inputWarehouse.domHandler.findSingle(this.inputWarehouse.el.nativeElement,'input').focus();
      return;
    }
    let value2 =
        this.warehouse_id === undefined ||
          this.warehouse_id === null
          ? 0 : this.warehouse_id;
      this.conditionSearch.warehouse_id = value2;
    const fileName = 'BCHTK_' + ('0' + new Date().getDate()).slice(-2) + '_'
      + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
      + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
    this.warehouseService.getExcel(this.conditionSearch).subscribe(
      (next: ArrayBuffer) => {
        const file: Blob = new Blob([next], { type: 'application/xlsx' });
        saveAs(file, fileName);
      });
      this.handleSearch();
  }

  // // ******************************** END CODE MOI *********************************** //


  ngOnDestroy() {
    // avoid memory leaks here by cleaning up after ourselves. If we
    // don't then we will continue to run our initialiseInvites()
    // method on every navigationEnd event.
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

}
