
import {Component, OnInit, OnDestroy, ViewChild, ElementRef, Renderer2} from "@angular/core";
import {BarcodeService} from "../barcode.service";
import {
  MessageService,
  ConfirmationService
} from "primeng/api";
import { ExportAsService, ExportAsConfig } from 'ngx-export-as';
import {Router, NavigationEnd} from "@angular/router";
import {Table} from "primeng/table";
import {Title} from "@angular/platform-browser";
import {SearchingRackCondition} from "../Entity/searchingRackCodition";
import {Constants} from "../../shared/containts";
import {AutoComplete} from "primeng/primeng";
import { WarehouseDetail } from "../Entity/WarehouseDetail";
import * as jsPDF from 'jspdf';
import { RackDetail } from "../Entity/RackDetail";
@Component({
  selector: 'app-rackprint',
  templateUrl: './rackprint.component.html',
  styleUrls: ['./rackprint.component.css']
})
export class RackprintComponent implements OnInit {
  @ViewChild('inputSquad') private inputSquad: ElementRef;
  @ViewChild('inputTypeWarehouseDetail') private inputTypeWarehouseDetail: AutoComplete;
  @ViewChild('inputTypeTinbox') private inputTypeTinbox: AutoComplete;
  @ViewChild('inputMngUser') private inputMngUser: ElementRef;
  @ViewChild('inputName') private inputName: ElementRef;
  @ViewChild('inputcolumn') private inputcolumn: ElementRef;
  @ViewChild("myTable")
  myTable: Table;
  loading: boolean;
  navigationSubscription;
  pageNumber: number = 0;
  totalRecords: number = 0;
  pageSize: number = 10;
  status: string = "1";
  conditionSearch: SearchingRackCondition;
  name:string;
  typeWarehouseDetail:any;
  listTypeWarehouseDetail: any[];
  listActivity:Array<String> = []; 
  listActivityId:String; 
  listNameToPrint:Array<RackDetail> = []; 
  isChecked: boolean= false ;
  row: number;
  column: number;
  isShowUpdateDialog: boolean;
  isEdit:boolean=true;
  typeRack:any;
  typeRackId:number=-1;
  listTypeRack: any[];
  isCheckAll: boolean;
  notChar: RegExp = /[%&_*\\'<>]/g;
  cols = [
    {field: "", header: "STT", width: "5%"},
    {field: "", header: "Chọn", width: "5%"},
    {field: "", header: "Thao tác", width: "5%"},
    {field: "warehouseName", header: "Kho", width: "20%"},
    {field: "row", header: "Hàng", width: "10%"},
    {field: "column", header: "Cột", width: "10%"},
    {field: "name", header: "Tên", width: "15%"},
    {field: "type", header: "Kiểu vị trí", width: "20%"},
    {field: "printTime", header: "Số lần đã in", width: "10%"}
];
constructor(
  private barcodeService: BarcodeService,
  private messageService: MessageService,
  private confirmationService: ConfirmationService,
  private router: Router,
  private title: Title,
  private renderer: Renderer2
  ) {
      this.navigationSubscription = this.router.events.subscribe(event => {
          if (!(event instanceof NavigationEnd)) {
              return;
          }
          // this.handleSearch();
      });
      this.loading = false;
      this.conditionSearch = new SearchingRackCondition();
   }
   resetForm() {
       
        if (this.myTable !== undefined) {
            this.myTable.reset();
        }
        this.typeWarehouseDetail = null;
        this.row = null;
        this.typeRack = null;
        this.column = null;
        this.handleSearch();
    }
    removeBlank() {
        if (this.typeWarehouseDetail != null)
            this.typeWarehouseDetail = this.typeWarehouseDetail.trim()
    }
    
   
    showMessage(severity: string, summary: string, detail: string) {
        this.messageService.add({
            severity: severity,
            summary: summary,
            detail: detail
        });
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
  
    isSelected() {
        
      if (this.typeWarehouseDetail != undefined && this.typeWarehouseDetail != null) {
        //   console.log("a");s
          if (this.typeWarehouseDetail.warehouseId == null || this.typeWarehouseDetail.warehouseId == undefined) {
              this.typeWarehouseDetail = null;
          }
      }

      if (this.typeRack != undefined && this.typeRack != null) {
        if ( this.listTypeRack.indexOf(this.typeRack)==-1) {
            this.typeRack = null;
        }
    }
    }
    prefix: string = null;
    getDriverTinbox(event) {
      this.typeRack = event;
  }
    filterTypeTinbox(event){
         let items = ["Chưa cấu hình","Lưu trữ","Đường đi","Gần cửa","Không sử dụng"];
          this.listTypeRack = Object.assign([], items).filter(
            typeRack => typeRack.trim().toLowerCase().indexOf(event.query.trim().toLowerCase()) > -1
          )
    }
    getDriver(event) {
      this.typeWarehouseDetail.warehouseId = event.warehouseId;
    }
    filterTypeWarehouseDetail(event) {
      let status: number;
      this.barcodeService.findType1Warehouses(event.query.trim()).subscribe(x => {
          this.listTypeWarehouseDetail = x["data"];
          status = x["status"];
          if (status == 1) {
              if (this.listTypeWarehouseDetail.length == 0) {
                this.showMessage("info", "Thông báo: Không có dữ liệu", "");
              }
          } else {
              this.showMessage("error", "Thông báo: Có lỗi xảy ra", "");
          }
      });
  }
    
    validate(name: string, mess: string, isShow: boolean) {
        if (name === undefined || name === null || name.trim().length == 0) {
            if (isShow) {
                this.messageService.add({
                    severity: "error",
                    summary: "Cảnh báo lỗi:",
                    detail: mess
                });

            }
            return false;
        }
        return true;
    }
 
  
    handleSearch() {
      if(this.typeRack=="Vị trí"){
        this.typeRackId = 2;
      }else if(this.typeRack=="Đường đi"){
        this.typeRackId = 1;
      }else if(this.typeRack=="Vị trí gần cửa"){
        this.typeRackId = 0;
      }else if(this.typeRack=="Không sử dụng"){
        this.typeRackId = 3;
      }else{
        this.typeRackId = -1;
      }
      if (this.myTable !== undefined) {
        this.myTable.reset();
        let value1 =
            this.typeRack === undefined ||
            this.typeRack === null 
                ? -2
                : this.typeRackId;
          let value2 =
              this.typeWarehouseDetail === undefined ||
              this.typeWarehouseDetail === null
                  ? null
                  : this.typeWarehouseDetail.warehouseId;
        let value3 =
             this.row === undefined || this.row === null || this.row === 0  ? null : this.row;
             if( this.row > 1000000000){
              value3 = 1000000000;
             }
        let value4 =
             this.column === undefined || this.column === null || this.column === 0 ? null : this.column;
             if( this.column > 1000000000){
              value4 = 1000000000;
             }
        this.conditionSearch.type = value1;
        this.conditionSearch.warehouseId = value2;
        this.conditionSearch.row = value3;
        this.conditionSearch.column = value4;
        this.isEdit = true;
        this.pageNumber = 0;
        if (value2 ==null) {
          this.messageService.add({
            severity: "error",
            summary: "Cảnh báo lỗi:",
            detail: "Kho không được để trống"
          });
          this.inputTypeWarehouseDetail.domHandler.findSingle(this.inputTypeWarehouseDetail.el.nativeElement, 'input').focus();
          return;
        }else{
          this.findSearchingResult();
        }
      }
    }

    paginate(event) {
        this.pageNumber = event.first;        
    }

    fieldSetTitle: string;
    listBarcodeDetail: any[];
    barcoderangeId:number;
    docx:jsPDF;
    doEdit(rowData: any) {
      let rackDetail = new RackDetail();
      rackDetail.rackId = rowData.rackId;
      rackDetail.name = rowData.name;
      rackDetail.printTime = rowData.printTime;
      this.listNameToPrint.push(rackDetail);

      this.listActivityId = rackDetail.rackId+"";
      this.isShowUpdateDialog =true;
      this.fieldSetTitle = 'Chi tiết vị trí';
      
    }
    printAll(){
      if(this.isEdit){
        return;
      }
      this.listActivityId="";
      this.listActivity.forEach(element => {
          let check:string[]= element.split(",");
          let rackDetail = new RackDetail();

          rackDetail.rackId = parseInt(check[0]);
          rackDetail.name = check[1]; 
          this.listActivityId+=rackDetail.rackId+",";
          this.listNameToPrint.push(rackDetail);
      });
      let number = this.listActivityId.length-1;
      this.listActivityId = this.listActivityId.substr(0,number);
      this.isShowUpdateDialog =true;
    }
    checkItem(isChecked: boolean) {
      if(this.listActivity.length == 0) {
          this.isEdit = true;
      } else {
          this.isEdit = false;
      }
      if(isChecked) {
        if(this.listActivity.length === this.listRack.length) this.isCheckAll = true;
      } else {
          this.isCheckAll = false;
      }
   }
   checkAll(isChecked: boolean) {
    if (!this.listRack || this.listRack.length == 0) {
        return;
    }

    this.listActivity = [];
    if (isChecked) {
        this.isEdit = false;
        for (let i = 0; i < this.listRack.length; i++) {
            const rackDetail = this.listRack[i];
            let a = rackDetail.rackId +","+rackDetail.name;
            this.listActivity.push(a);
        }
    }else{
      this.isEdit = true;
    }
}
    updatePrintTimeRack(listActivityId :any){
      let status1:number; 
      this.barcodeService.updatePrintTimeRack(listActivityId).subscribe(x => {
        status1 = x["status"];
        if (status1 == 1) {
          if (this.myTable !== undefined) {
            this.myTable.reset();
          }
          this.handleSearch();
        } else {
            this.showMessage("error", "Thông báo: Có lỗi xảy ra khi cập nhật số lần in", "");
        }
    });
    }
    doPrint(){
        let status1: number;
        

       let doc =   new jsPDF({ 
            // orientation: 'landscape',
            orientation: 'portrait',
              unit: 'pt',
              format:'a4'
            });
        let specialElementHandlers = {
            '#editor': function(element, renderer){
                return true;
            }
            };
        doc.setFontSize(100);  
        doc.setFontType("bold");
        let y= 125;
        this.listNameToPrint.forEach((x, index, array) => {
          doc.text(x["name"],44,y);
          y= y+150;
          if (index === (array.length -1)) {
            
         }else{
          if(y>750){
            doc.addPage();
            y=125;
          }
         }
           
        });
      
        setTimeout(function () {
            doc.autoPrint(); 
            doc.output('dataurlnewwindow'); 
            // doc.save("name");
        }, 600);  
                 
        this.updatePrintTimeRack(this.listActivityId);
        
        
        this.fieldSetTitle = 'Chi tiết vị trí';
     
        this.isShowUpdateDialog =false;
        // this.myTable.reset();
        // this.listRack.length=0;
       
    }
    doPrintBig(){
      let status1: number;
      

     let doc =   new jsPDF({ 
          orientation: 'landscape',
          // orientation: 'portrait',
            unit: 'pt',
            format:'a4'
          });
      let specialElementHandlers = {
          '#editor': function(element, renderer){
              return true;
          }
          };
      doc.setFontSize(200);  
      doc.setFontType("bold");
      let y= 260;
      this.listNameToPrint.forEach((x, index, array) => {
        let check:string[]= x["name"].split("/");
        doc.text(check[0],170,y);
        doc.text(check[1],170,y+200);
        y= y+250;
        if (index === (array.length -1)) {
       }else{
        if(y>250){
          doc.addPage();
          y=260;
        }
       }
         
      });
    
      setTimeout(function () {
          doc.autoPrint(); 
          doc.output('dataurlnewwindow'); 
          // doc.save("name");
      }, 600);  
               
         
      
      this.updatePrintTimeRack(this.listActivityId);
      this.fieldSetTitle = 'Chi tiết vị trí';
   
      this.isShowUpdateDialog =false;
      // this.myTable.reset();
      // this.listRack.length=0;
      
  }
    close(){
        this.isShowUpdateDialog = false;
        // this.resetForm();
        this.listNameToPrint.length =0 ;
        this.listActivityId = "" ;
        // this.myTable.reset();
    }
    listRack: any[];
    /* Searching function */
    findSearchingResult() {
        this.listActivity.length=0 ;
        this.isCheckAll =false;
        let status1: number;
        this.barcodeService
            .findSearchingRackResult(this.conditionSearch)
            .subscribe(x => {
                this.listRack = x["data"];
                if (this.listRack.length > 0) {
                   this.totalRecords = this.listRack.length;
                   
                }

                status1 = x["status"];

                if (status1 == 1) {
                    if (this.listRack.length == 0) {
                        this.pageNumber = 0;
                        this.totalRecords = 0;
                        //this.showMessage("info", "Không tìm thấy dữ liệu phù hợp", "");
                    } else {
                        this.listRack.forEach(x => {
                         
                            // x.displayOption = x.fullName;
                        });
                    }
                } else {
                    this.showMessage(
                        "error",
                        "Có lỗi không xác định, vui lòng thử lại sau!",
                        ""
                    );
                }
            });
    }
   
      
    limitValue(input, min, max){
      if(input.value == null || input.value == "") input.value = null;
      else if(input.value < min) input.value = min;
      else if(input.value > max) input.value = max;
    }

    setRow(input){
      this.row = input.value !== 0 ? input.value : null;
    }
  
    setCol(input){
      this.column = input.value !== 0 ? input.value : null;
    }

ngOnInit() {
    // this.handleSearch();
    this.title.setTitle("In nhãn vị trí");
}

ngOnDestroy() {
    if (this.navigationSubscription) {
        this.navigationSubscription.unsubscribe();
    }
}

}
