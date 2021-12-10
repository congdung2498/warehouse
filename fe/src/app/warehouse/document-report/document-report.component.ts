import { Component, OnInit, ViewChild, Renderer2 } from '@angular/core';
import { AutoComplete, MessageService, ConfirmationService } from 'primeng/primeng';
import { WarehouseService } from '../warehouse.service';
import { Title } from '@angular/platform-browser';
import { Router, NavigationEnd } from '@angular/router';
import { Table } from 'primeng/table';
import { ConditionSearch } from '../Entity/ConditionSearch';
import {saveAs} from 'file-saver';

@Component({
  selector: 'app-document-report',
  templateUrl: './document-report.component.html',
  styleUrls: ['./document-report.component.css']
})
export class DocumentReportComponent implements OnInit {

  ngOnInit() {
  }

  warehouseNameChoose: string;
  listOfWarehouses: any[];
  filterWarehouse: any[] = [];
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
  totalDocument: number = 0;
  totalRecords: number = 0;
  totalTinbox: number = 0;
  totalFolder: number = 0;
  loading: boolean;

  pageNumber: number = 1;

  pageSize: number = 10;

  listDocument: any[];

  conditionSearch: ConditionSearch;

  // // tslint:disable-next-line:max-line-length
  // onlyChar: RegExp = /^[0-9a-zA-ZÝÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÝÌỈĨỊÓÒỎÕỌÔờỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĝ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếờểễệíìỉĩịóòờõờôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;

  // // tslint:disable-next-line:max-line-length
  // notChar: RegExp = /[^0-9a-zA-ZÝÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÝÌỈĨỊÓÒỎÕỌÔờỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĝ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếờểễệíìỉĩịóòờõờôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;

  cols = [
    { field: "", header: "STT", width: '50px'},
    { field: "projectName", header: "Dự Án"},
    { field: "packageName", header: "Gói thầu"},
    { field: "contractName", header: "Hợp đồng"},
    { field: "constructionName", header: "Công trình"},
    { field: "document", header: "Tài Liệu"},
    { field: "tinBoxQRCOde", header: "Mã Thùng"},
    { field: "tinBoxName", header: "Tên Thùng"},
    { field: "folderQRCode", header: "Mã Hồ Sơ"},
    { field: "folderName", header: "Tên Hồ Sơ"},
    { field: "warehouse", header: "Kho"},
    { field: "position", header: "Vị trí"},
    { field: "positionQRCode", header: "Mã Vị Trí"}
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
      title.setTitle("Báo cáo Chi tiết Tài Liệu");
      this.warehouse_name = null;
      this.conditionSearch = new ConditionSearch();
    });
    this.warehouseService.getListWarehouseForDropdown().subscribe(
      data => {
        this.listOfWarehouses = data.data;
        console.log(this.listOfWarehouses);
      }
    );
    this.loading = false;
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
    if(this.warehouseNameChoose == undefined || this.warehouseNameChoose == null || this.warehouseNameChoose == '') {
      this.messageService.add({
        severity: "error",
        summary: "Cảnh báo lỗi:",
        detail: 'Bạn cần chọn Kho.'
      });
      this.inputWarehouse.domHandler.findSingle(this.inputWarehouse.el.nativeElement,'input').focus();
      return;
    }

    if(this.warehouseNameChoose != this.warehouse_name) this.pageNumber = 1;

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
      this.getDocumentReport();
    }
  }

  getDocumentReport(){
    //console.log(this.conditionSearch);
    this.warehouseService.getDocumentReportV2(this.conditionSearch)
    .subscribe(resp=>{
      //console.log(resp);
      if(resp.data){
        this.totalFolder = resp.data.totalFolder;
        this.totalTinbox = resp.data.totalTinbox;
        this.totalDocument = resp.data.totalDocument;
        this.listDocument = resp.data.returnRecords;
      }
    })
  }

  findSearchingResult() {
    let status1: number;
    //console.log(this.conditionSearch)
    this.warehouseService
      .getDocumentReport(this.conditionSearch)
      .subscribe(data => {
        this.listDocument = data["data"];        
        if (this.listDocument.length == 0) {
          this.totalRecords = 0;
          this.totalDocument = 0;
        } else {
          this.totalRecords = this.listDocument[0].totalRecords;
          this.totalDocument = this.totalRecords;
          this.totalFolder = this.listDocument[0].totalFolder;
          this.totalTinbox = this.listDocument[0].totalTinbox;
        }        
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
    console.log(event)
    this.loading = true;
    this.pageNumber = event.first/this.pageSize + 1
    console.log(this.pageNumber)
    this.conditionSearch.pageNumber = this.pageNumber;
    this.getDocumentReport();
    this.loading = false;

  }

  setWarehouse(event) {
    console.log(event.warehouseId);
    this.warehouse_id = event.warehouseId;
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
    let value2 =
      this.warehouse_id === undefined || this.warehouse_id === null ? 0 : this.warehouse_id;
      this.conditionSearch.warehouse_id = value2;
      const fileName = 'BCLTCTTL_' + ('0' + new Date().getDate()).slice(-2) + '_'
      + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
      + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + '.xlsx';
      this.warehouseService.getExcelForDocument(this.conditionSearch).subscribe(
        (next: ArrayBuffer) => {
          const file: Blob = new Blob([next], { type: 'application/xlsx' });
          saveAs(file, fileName);
        });
      this.handleSearch();
  }

  calculateEndOfPage(){
    if(this.totalDocument == 0) return 0;
    if(this.pageSize*this.pageNumber >= this.totalDocument) return this.totalDocument;
    return this.pageSize*this.pageNumber;
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
