import {DateTimeUtil} from "../../../common/datetime";
import {Component, OnInit, OnDestroy, ViewChild, ElementRef, Renderer2} from "@angular/core";
import {BarcodeService} from "../barcode.service";
import { MessageService, ConfirmationService} from "primeng/api";
import { ExportAsService, ExportAsConfig } from 'ngx-export-as';
import {Router, NavigationEnd} from "@angular/router";
import {Table} from "primeng/table";
import {Title} from "@angular/platform-browser";
import {SearchingCondition} from "../Entity/searchingCodition";
import {Constants} from "../../shared/containts";
import {AutoComplete} from "primeng/primeng";
import { typeBarcode } from "../Entity/typeBarcode";
import { BarCodeRange } from "../Entity/BarCodeRange";
import * as jsPDF from 'jspdf';
import * as html2pdf from 'html2pdf.js';
// import * as html2canvas  from  'html2canvas';
@Component({
  selector: 'app-barcode',
  templateUrl: './barcode.component.html',
  styleUrls: ['./barcode.component.css']
})
export class BarcodeComponent implements OnInit {
    
    @ViewChild('exportPDF') private exportPDF: ElementRef;
    @ViewChild('inputSquad') private inputSquad: ElementRef;
    @ViewChild('inputTypeBarcode') private inputTypeBarcode: AutoComplete;
    @ViewChild('inputDescription') private inputDescription: ElementRef;
    @ViewChild('inputNumOfBarcode') private inputNumOfBarcode: ElementRef;
    @ViewChild("myTable")
    myTable: Table;
    loading: boolean;
    navigationSubscription;
    pageNumber: number = 0;
    totalRecords: number = 0;
    pageSize: number = 10;
    status: string = "1";
    conditionSearch: SearchingCondition;
    dateCreate:Date;
    description: string;
    descriptionPrint:string;
    typeBarcode:any;
    listTypeBarcode: any[];
    isChecked: boolean= false ;
    numOfBarcode: number  ;
    isShowUpdateDialog: boolean;
    isEdit:boolean=false;
  // tslint:disable-next-line:max-line-length
//   onlyChar: RegExp = /^[0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;

  // tslint:disable-next-line:max-line-length
//   notChar: RegExp = /[^0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;
  notChar: RegExp = /[%&_*\\'<>]/g;
  cols = [
    {field: "", header: "STT", width: "5%"},
    {field: "", header: "Thao tác", width: "8%"},
    {field: "prefix", header: "Loại mã vạch", width: "17%"},
    {field: "quantity", header: "Số lượng mã", width: "10%"},
    {field: "description", header: "Mục đích", width: "40%"},
    {field: "printed", header: "Trạng Thái", width: "10%"},
    {field: "createDate", header: "Ngày tạo", width: "10%"}
];
    
  constructor(
    private barcodeService: BarcodeService,
    private exportAsService: ExportAsService,
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
            this.handleSearch();
        });
        this.loading = false;
        this.conditionSearch = new SearchingCondition();
     }

    resetButton() {
       
      this.description = null;
      this.numOfBarcode = null;
      this.isChecked = false;
      this.typeBarcode = null;
      this.handleSearch();
  }

    resetForm() {
       
        if (this.myTable !== undefined) {
            this.myTable.reset();
        }
        this.typeBarcode = null;
        this.description = null;
        this.isChecked = false;
        this.numOfBarcode = null;
        this.handleSearch();
    }
    removeBlank() {
        if (this.typeBarcode != null)
            this.typeBarcode = this.typeBarcode.trim()
    }
    changeDescription() {
        if (this.notChar.test(this.inputDescription.nativeElement.value)) {
          this.inputDescription.nativeElement.value = this.inputDescription.nativeElement.value.replace(this.notChar, '');
        }
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
        if (this.typeBarcode != undefined && this.typeBarcode != null) {            
            if (this.typeBarcode.prefix == null || this.typeBarcode.prefix == undefined) {
                this.typeBarcode = null;
            }
        }
    }
    prefix: string = null;
    getDriver(event) {
        this.prefix = event.prefix;
    }
    filterTypeBarcode(event) {
        let status: number;
        // let query: any;
        // if (this.typeBarcode == null) {
        //     query = null;
        // } else {
        //     if (this.checkEmpty(this.typeBarcode) == '') query = null;
        //     else query = this.typeBarcode;
        // }
        this.barcodeService.findTypeBarcode(event.query.trim()).subscribe(x => {
            this.listTypeBarcode = x["data"];
            status = x["status"];
            if (status == 1) {
                // if (this.listPlace.length == 0) {
                //   this.showMessage("info", "Thông báo: Không có dữ liệu", "");
                // }
            } else {
                this.showMessage("error", "Thông báo: Có lỗi xảy ra", "");
            }
        });
    }
    handleAdd() {
       
        if(this.description != null && this.description != undefined ){
            this.description =this.description.trim();
            for (let index = 0; index < this.description.length; index++) {
                this.description = this.description.replace(this.notChar, "");
            }
        }
        this.conditionSearch = new SearchingCondition();
        this.conditionSearch.pageNumber = this.pageNumber;
        this.conditionSearch.pageSize = this.pageSize;
        this.conditionSearch.quantity = this.numOfBarcode;
        let status1: number;
        let data:any;
        let check = this.checkDataBeforeAdd();
        if (check) {
            this.confirmationService.confirm({
                message: "Đồng chí có muốn tạo dải mã vạch này không?",
                rejectVisible: true,
                header: "Xác nhận tạo",
                icon: "pi pi-info-circle",
                acceptLabel: 'Đồng ý',
                rejectLabel: 'Hủy bỏ',
                accept: () => {


                    this.barcodeService
                        .insertUpdate(
                            this.description,
                            this.isChecked,
                            this.typeBarcode.barCodePrefixId,
                            this.prefix,
                            this.numOfBarcode
                        )
                        .subscribe(x => {
                            status1 = x["status"];
                            if (status1 == Constants.RESPONSE_SUCESS) {
                                this.showMessage("success", "Đã thêm mới dải mã vạch", "");
                                data= x["data"];
                                if(this.isChecked==true){
                                    this.descriptionPrint = this.description;
                                    this.doEdit(data);
                                }
                                this.resetForm();
                               
                            }  else if (status1 == Constants.RESPONSE_ERROR) {
                                this.showMessage("error", "Có lỗi không xác định, vui lòng thử lại sau!", "");
                                this.description = null;
                                this.isChecked = false;
                                this.numOfBarcode = null;
                            } else if (status1 == 10) {
                                this.showMessage(
                                    "error",
                                    "Có lỗi không xác định, vui lòng thử lại sau!",
                                    ""
                                );
                                this.description = null;
                                this.isChecked = false;
                                this.numOfBarcode = null;
                            } 
                        });

                },
                reject: () => {
                    this.resetForm();
                }
            });
            
        }

    }


    checkDataBeforeAdd() {
        let check = true;
       
        var value2 =
            this.typeBarcode === undefined || this.typeBarcode === null
                ? null
                : this.typeBarcode.name;
        var value3 = 
            this.numOfBarcode === undefined || this.numOfBarcode === null || this.numOfBarcode === 0 || this.numOfBarcode > 100
                ? null
                : this.numOfBarcode.toString();
        if (!this.validate(value2, "Loại mã vạch không được để trống", true)) {
            check = false;
            this.inputTypeBarcode.domHandler.findSingle(this.inputTypeBarcode.el.nativeElement, 'input').focus();
            return check;
        }
       
        if (!this.validate(this.description, "Mục đích không được để trống", true)) {
            check = false;
            this.inputDescription.nativeElement.focus();
            return check;
        }
        if (!this.validate(value3, "Số lượng không được để trống", true)) {
            check = false;
            this.inputNumOfBarcode.nativeElement.focus();
            return check;
        }


        return check;
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
        
        
        if (this.myTable !== undefined) {
            this.myTable.reset();
            let value1 =
                this.description === undefined ||
                this.description === null ||
                this.description === ""
                    ? null
                    : this.description.trim();
            let value2 =
                this.typeBarcode === undefined || this.typeBarcode === null
                    ? null
                    : "" + this.typeBarcode.prefix;
            let value3 =
                this.isChecked === undefined || this.isChecked === null
                    ? false
                    : this.isChecked;
            let value4 =
                this.numOfBarcode === undefined || this.numOfBarcode === null ? null : this.numOfBarcode;
            this.conditionSearch.description = value1;
            this.conditionSearch.prefix = value2;
            this.conditionSearch.printed = value3;
            this.conditionSearch.quantity = value4;
            this.conditionSearch.pageNumber = this.pageNumber;
            this.conditionSearch.pageSize = this.pageSize;
            if(this.isChecked ==true){
                this.isEdit =true;
            }else{
                this.isEdit =false;
            }
            this.findSearchingResult();
        }
    }
    paginate(event) {
        this.pageNumber = event.first;
        
    }
    fieldSetTitle: string;
    selectedBarcodeRange:BarCodeRange;
    listBarcodeDetail: any[];
    barcoderangeId:number;
    docx:jsPDF;
    doEdit(barcoderange: BarCodeRange) {
       
        
        let status1: number;
        if(barcoderange.printed==false ){
            this.barcoderangeId = barcoderange.barCodeRangeId;
            this.descriptionPrint = barcoderange.description;
           
            this.barcodeService
                .findBarcodeDetail(barcoderange.barCodeRangeId)
                .subscribe(x => {
                    this.listBarcodeDetail = x["data"];
                    if (this.listBarcodeDetail.length > 0) {
                        
                    }
    
                    status1 = x["status"];
    
                    if (status1 == 1) {
                        if (this.listBarcodeDetail.length == 0) {
                            //thoong bao ji day
                        } else {
                         
                           
                        }
                    } else {
                        this.showMessage(
                            "error",
                            "Có lỗi không xác định, vui lòng thử lại sau!",
                            ""
                        );
                    }
                });
           
            this.fieldSetTitle = 'Chi tiết mã vạch';
            this.isShowUpdateDialog =true;
        }
        
    }

    renderQRCode = function(doc, total, index, j, k, l, m, callback){
        let that = this;
        let container = document.getElementById('exportPDF' + index);
        if(container != null){
            doc.fromHTML(
                container.innerHTML, 
                52 + j, 
                50 + k, 
                {pagesplit: false}, 
                function() {
    
                    l+=110;

                    if(l>470){
                        l=0;
                        m+=110;
                    }   

                    if(m>700){
                        doc.addPage();
                        m=0;
                    }
        
                    index++;
                    j+=110;

                    if(j>470){
                        j=0;
                        k+=110;
                    }
                    
                    if(k>700){
                        k=0;
                    }
                    
                    if(index < total){
                        that.renderQRCode(doc, total, index, j, k, l, m, callback);
                    }
                    else{
                        callback();
                    }
                }
            );
        }
        else{
            index++;
            if(index < total){
                that.renderQRCode(doc, total, index, j, k, l, m, callback);
            }
            else{
                callback();
            }
        }        
    }
   
    doPrint(){
        document.body.style.cursor='wait';
        let status1: number;
        let status2: number;
        this.barcodeService
        .checkChangeBarcodeRange(this.barcoderangeId)
        .subscribe(x => {
            // this.listBarcodeDetail = x["data"];
            status2 = x["status"];
            if (status2 != 1) {
               
        
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
                }

                let element;
                var i =0;
                var j =0;
                var k =0;
                var l = 0;
                var m = 0;
                let name = "Dải mã vạch "+this.descriptionPrint+".pdf";
                
                let total = this.listBarcodeDetail.length;
                let that = this;
                
                this.renderQRCode(doc, total, i, j, k, l, m, function(){
                    doc.autoPrint();
                    doc.save(name);
                    //window.open(doc.output('bloburl'));
                    that.barcodeService
                    .changeBarcodeRangeDetail(that.barcoderangeId)
                    .subscribe(x => {
                        // this.listBarcodeDetail = x["data"];
                        status1 = x["status"];
                        if (status1 == 1) {
                        //    this.showMessage("success", "Đã in dải mã vạch", "");
                            
                        } else {
                            this.showMessage(
                                "error",
                                "Có lỗi không xác định, vui lòng thử lại sau!",
                                ""
                            );
                        }
                    })
                    .add(()=>{
                        that.resetForm();
                        document.body.style.cursor='default';
                    });

                });

                // this.listBarcodeDetail.forEach(x => {
                    
                //     element = document.getElementById('exportPDF' +i).innerHTML;
                //     // doc.internal.set
                //     doc.fromHTML(element,52+j,50+k,{
                //         //  'width':20,
                        
                //         pagesplit: false,
                //             'elementHandlers':specialElementHandlers
                //         },function() {
                          
                //             l+=110;
                //             if(l>470){
                //                  l=0;
                //                  m+=110;
                //              }   
                //             if(m>700){
                             
                //                 doc.addPage();
                //                 m=0;
                //             }
                            
                //             // doc.addPage();
                //     });
                //    i++;
                   
                //    j+=110;
                //    if(j>470){
                //         j=0;
                //         k+=110;
                //     }   
                //     if(k>700){
                             
                //         // doc.addPage();
                //         k=0;
                //     }
                // });
              
                // setTimeout(function () {
                //     // doc.autoPrint(); 
                //     // doc.output('dataurlnewwindow'); 
                //     doc.save(name);
                // }, 600);

                // this.export();
                
                 
            } else {
                this.showMessage(
                    "error",
                    "Dải mã vạch này đã từng in!",
                    ""
                );
                document.body.style.cursor='default';
            }
        });
               
        this.fieldSetTitle = 'Chi tiết mã vạch';     
        
        
    }
    close(){
        this.isShowUpdateDialog = false;
    }
    listBarcodeRange: any[];
    /* Searching function */
    findSearchingResult() {
        let status1: number;
        this.barcodeService
            .findSearchingResult(this.conditionSearch)
            .subscribe(x => {
                this.listBarcodeRange = x["data"];
                if (this.listBarcodeRange.length > 0) {
                    
                    if (
                        (this.conditionSearch.description == null ||
                            this.conditionSearch.description == "") &&
                        (this.conditionSearch.securityUsername == "" ||
                            this.conditionSearch.securityUsername == null) &&
                             (this.conditionSearch.prefix == null ||
                            this.conditionSearch.prefix == "")  
                    ) {
                        this.totalRecords = this.listBarcodeRange[0].totalRecords;
                    } else {
                        if (this.myTable !== undefined) {
                            this.myTable.reset();
                            this.totalRecords = this.listBarcodeRange[0].totalRecords;
                        }
                    }
                }

                status1 = x["status"];

                if (status1 == 1) {
                    if (this.listBarcodeRange.length == 0) {
                        this.pageNumber = 0;
                        this.totalRecords = 0;
                        //this.showMessage("info", "Không tìm thấy dữ liệu phù hợp", "");
                    } else {
                        this.listBarcodeRange.forEach(x => {
                         
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
    // exportAsConfig: ExportAsConfig = {
    //     type: 'pdf', // the type you want to download
    //     elementId: 'exportPDF', // the id of html/table element
    //     options: { // html-docx-js document options
    //         orientation: 'landscape',
    //         margins: {
    //           top: '0'
    //         },
    //         display: 'flex'
    //     },
    // }
    

    ngOnInit() {
        this.handleSearch();
        this.title.setTitle("Thêm mới mã vạch ");
    }

    ngOnDestroy() {
        if (this.navigationSubscription) {
            this.navigationSubscription.unsubscribe();
        }
    }

}
