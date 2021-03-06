import {Component, OnInit, OnDestroy, AfterViewInit, ViewChild, Renderer2, ElementRef} from '@angular/core';
import {trigger, state, style, transition, animate} from '@angular/animations';
import {Table} from 'primeng/table';
import {AutoComplete} from 'primeng/autocomplete';
import {ServiceDataService} from '../service-data.service';
import {SelectItem} from "primeng/api";

import {DateTimeUtil} from "../../../common/datetime";
import {SignVoffice, SignVofficeSerch} from "../Entity/SignVoffice";
import {StatusSignVoffice} from "../Entity/Status";
import {saveAs} from "file-saver";
import {UserInfo} from "../../shared/UserInfo";
import {TokenStorage} from "../../shared/token.storage";
import {ConfirmationService} from 'primeng/api';
import {AppComponent} from "../../app.component";
import {debug} from "util";
import {elementStart} from "@angular/core/src/render3/instructions";


@Component({
    selector: 'app-sgin-voffice',
    templateUrl: './sign-voffice.component.html',
    styleUrls: ['./sign-voffice.component.css']
})
export class SignVofficeComponent implements OnInit {

    @ViewChild('myTable') private myTable: Table;
    @ViewChild('noteContentReportF') private noteContentReportF: ElementRef;
    @ViewChild('inputUseNameLogin') private inputUseNameLogin: ElementRef;
    @ViewChild('inputEmployeeUnit') private inputEmployeeUnit: AutoComplete;
    @ViewChild('inputEmployeeManager') private inputEmployeeManager: AutoComplete;
    listSignVoffice: any;
    statusList: SelectItem[] = StatusSignVoffice.SIGNVOFFICE_STATUS_REPORT;
    typeList: SelectItem[] = StatusSignVoffice.SIGNVOFFICE_TYPE;
    vn = DateTimeUtil.vn;
    fromDate: Date;
    toDate: Date;
    signVofficeSearch: SignVofficeSerch;
    documentcode: any;
    signUserName: any;
    selectedType: any;
    selectStatus: any;
    totalRecord: any;
    displayAddReport: any;
    noteContentReport: any;
    idSignVoffice: any;
    uf: UserInfo;
    convertStatus: string[];
    convertType: string[];
    startRow: number;
    rowSize: number;
    displaySign: any;
    filterEmployeeManager: any[];
    filterSignUserName: any[];
    filterEmployeeUnit: any[];
    employeeManagerInfo: any;
    employeeUnitInfo: any;
    unit: any;
    managerUnit: any;
    useNameLogin: any;
    passwordLogin: any;
    displayConfimSign: any;
    email: any;
    notiSign: any;
    signError = StatusSignVoffice.SIGNVOFFICE_ERROR;
    isconfirmSign : any;
    signId :any;
    typeSign: any;
    isManager: any;
    isQl: any;
    fileName: any
    disabledSign: any
    filterDocumentcode: any[];
    // tslint:disable-next-line:max-line-length
    onlyChar: RegExp = /^[0-9a-zA-Z??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????()_\-\.]+$/;
    // tslint:disable-next-line:max-line-length
    notChar: RegExp = /[^0-9a-zA-Z??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????()_\-\.]/g;

    constructor(private serviceDataService: ServiceDataService , private tokenStorage: TokenStorage, private confirmationService: ConfirmationService,
                private app: AppComponent) {
    }

    ngOnInit() {
        this.isManager = false;
        this.isQl = false;
        this.fromDate= null;
        this.toDate= null;
        this.documentcode= null;
        this.signUserName= null;
        this.selectedType= null;
        this.selectStatus= null;
        this.uf = this.tokenStorage.getUserInfo();
        this.startRow =-1;
        this.searchData();
        this.displayAddReport= false;
        this.noteContentReport= null;
        this.displaySign = false;
        this.displayConfimSign= false;
        this.setStatus();
        this.setRole();
        this.disabledSign = false;

        /*this.serviceDataService.getUserInfo().subscribe(y => {
            this.userInfo = y.data;
            console.log(this.userInfo);
        });*/
    }
    setRole(){
        if (this.uf.role.includes('PMQT_ADMIN') ||this.uf.role.includes('PMQT_HCVP') || this.uf.role.includes('PMQT_HC_DV')) {
            this.isManager = true;
        }else if(this.uf.role.includes('PMQT_QL')||this.uf.role.includes('PMQT_CVP')){
            this.isQl = true;
        }

    }
    setStatus() {

        this.convertStatus = StatusSignVoffice.STATUS;
        this.convertType = StatusSignVoffice.TYPE;
    }
    resetButon(){
        if (this.myTable !== undefined) {
            this.myTable.reset();
        }
        this.searchData();
    }

    loadSignUserName(event){
        let listRole=[];
        listRole.push('PMQT_QL');

        const object = {
            'role': listRole,
            'pattern': event.query,
            'pageNumber':0,
            'pageSize':20
        };
        this.serviceDataService.getListEmployeeManager(object).subscribe(item => {
            this.filterSignUserName = item.data;
        });
    }

    searchData() {
        this.myTable.reset();
        this.startRow = 0;
        if (this.fromDate) this.fromDate.setHours(0, 0, 0, 0);
        this.signVofficeSearch = {
            fromDate: this.fromDate,
            toDate: this.toDate,
            documentCode: (this.documentcode != null &&this.documentcode.documentCode != undefined && this.documentcode.documentCode != null )? this.documentcode.documentCode: this.documentcode ,
            signUserName: (this.signUserName  != null &&this.signUserName.email != undefined && this.signUserName.email != null )? this.signUserName.email: this.signUserName ,
            type: this.selectedType != null ? this.selectedType.value : null,
            status: this.selectStatus != null ? this.selectStatus.value : null,
            startRow: this.startRow,
            rowSize: 10
        }

        this.serviceDataService.findSignVoffice(this.signVofficeSearch).subscribe(item => {
            this.listSignVoffice = item['data'].listSignVoffice;
            this.totalRecord = item['data'].totalSiginVoffice;
            this.rowSize = this.listSignVoffice.length;
            console.log(this.listSignVoffice);
        });
    }

    loaddocumentCode(event){

    }
    selectFromDate() {
        if (this.toDate && this.toDate < this.fromDate) {
            this.toDate = this.fromDate;
        }
    }

    //   when select toDate field
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
    exportSignVofficeExcel(){
        this.signVofficeSearch = {
            fromDate: this.fromDate,
            toDate: this.toDate,
            documentCode: (this.documentcode != null &&this.documentcode.documentCode != undefined && this.documentcode.documentCode != null )? this.documentcode.documentCode: this.documentcode ,
            signUserName: (this.signUserName  != null &&this.signUserName.email != undefined && this.signUserName.email != null )? this.signUserName.email: this.signUserName ,
            type: this.selectedType != null ? this.selectedType.value : null,
            status: this.selectStatus != null ? this.selectStatus.value : null,
            startRow: 0,
            rowSize: 10
        }
        this.serviceDataService.exportSignVoffice(this.signVofficeSearch).subscribe(
            (next: ArrayBuffer) => {
                var fileName = 'DSSERVICE_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
                    + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
                    + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
                const file: Blob = new Blob([next], {type: 'application/xlsx'});
                saveAs(file, fileName);
            }
        );
    }
    loadDocumentcode(event) {

        let object ={
            documentCode: event.query,
            startRow: 0,
            rowSize: 10
        }

        this.serviceDataService.findSignVoffice(object).subscribe(item => {
            this.filterDocumentcode = item['data'].listSignVoffice;

        });

    }

    changeDocumentcode(){

    }

    doEdit(item){
        this.displayAddReport = true;
        this.noteContentReport = item.content;
        this.idSignVoffice = item.signVofficeId;
    }
    confirmAddReport(){
        if(this.noteContentReport == null || this.noteContentReport == undefined || this.noteContentReport.trim() == ''){
            this.noteContentReportF.nativeElement.focus();
            this.app.errorValidateDate('N???i dung v??n b???n kh??ng ???????c ????? tr???ng');
            return;
        }
        if(this.noteContentReport.length >500 ){
            this.app.errorValidateDate('N???i dung b??o c??o kh??ng ???????c l???n h??n 500 k?? t???');
            this.noteContentReportF.nativeElement.focus();
            return;
        }
        let object={
            'signVofficeId' : this.idSignVoffice,
            'content' : this.noteContentReport
        }
        this.serviceDataService.updateSignVoffice(object).subscribe(item => {
                if(item.status == 1 ){
                    this.app.showSuccess('S???a b??o c??o th??nh c??ng');
                    this.displayAddReport = false;
                    this.resetButon();
                }

        });
    }

    deleteData(item){
        this.confirmationService.confirm({
            message: '??/c c?? ch???c ch???n mu???n th???c hi???n ?',
            header: 'H???y b??o c??o',
            icon: 'pi pi-info-circle',
            acceptLabel: '?????ng ??',
            rejectLabel: 'H???y b???',
            accept: () => {
                let object={
                    'signVofficeId' : item.signVofficeId,
                    'status' : 5
                }
                this.serviceDataService.updateSignVoffice(object).subscribe(item => {
                    if(item.status == 1 ){
                        this.app.showSuccess('H???y b??o c??o th??nh c??ng');
                        this.resetButon();
                    }

                });
            },
            reject: () => {
            }
        });
    }
    cancelAddReport(){
        this.displayAddReport = false;
        this.noteContentReport = null;
    }
    sign(item){
        this.signId = item.signVofficeId;
        this.typeSign = item.type;
        this.displaySign= true;
        this.fileName = this.change_alias(item.content);
    }

    loadEmployeeManager(event) {
        let listRole=[];
        listRole.push('PMQT_QL');

        const object = {
            'role': listRole,
            'pattern': event.query,
            'pageNumber':0,
            'pageSize':20
        };
        this.serviceDataService.getListEmployeeManager(object).subscribe(item => {
            this.filterEmployeeManager = item.data;
        });
    }

    changeEmployeeManager() {
        this.unit = null;
        if (this.notChar.test(this.inputEmployeeManager.el.nativeElement.children[0].children[0].value)) {
            this.inputEmployeeManager.el.nativeElement.children[0].children[0].value = this.inputEmployeeManager.el.nativeElement.children[0].children[0].value.replace(this.notChar, '');
        }
    }

    clearEmployeeManager() {
        if (this.employeeManagerInfo) {
            this.employeeManagerInfo = null;
        }
        this.unit= null;
    }


    loadEmployeeUnit(event) {
        let listRole=[];
        listRole.push('PMQT_QL');

        const object = {
            'role': listRole,
            'pattern': event.query,
            'pageNumber':0,
            'pageSize':20
        };
        this.serviceDataService.getListEmployeeManager(object).subscribe(item => {
            this.filterEmployeeUnit = item.data;
        });
    }

    changeEmployeeUnit() {
        this.managerUnit= null;
        if (this.notChar.test(this.inputEmployeeUnit.el.nativeElement.children[0].children[0].value)) {
            this.inputEmployeeUnit.el.nativeElement.children[0].children[0].value = this.inputEmployeeUnit.el.nativeElement.children[0].children[0].value.replace(this.notChar, '');
        }
    }

    clearEmployeeUnit() {
        if (this.employeeUnitInfo) {
            this.employeeUnitInfo = null;
        }
        this.managerUnit = null;
    }

    selectEmployeeManagerInfo(){
        if(this.employeeManagerInfo != null && this.employeeManagerInfo.unitName != null)
        this.unit= this.employeeManagerInfo.unitName;
    }

    selectEmployeeUnitInfo(){

        if(this.employeeUnitInfo != null && this.employeeUnitInfo.unitName != null)
            this.managerUnit= this.employeeUnitInfo.unitName;
    }
    cancelSign(){
        this.displaySign= false;
        this.clearEmployeeUnit();
        this.clearEmployeeManager()
        this.useNameLogin = null;
        this.passwordLogin = null;

    }

    signStart(){
         this.email=null;
        if(this.employeeManagerInfo != null && this.employeeManagerInfo !=undefined && this.employeeManagerInfo.email != null ){
                this.email = this.employeeManagerInfo.email;

        }
        if(this.useNameLogin == null || this.useNameLogin== undefined ||this.useNameLogin.trim()==''|| this.passwordLogin== null || this.passwordLogin== undefined||this.passwordLogin.trim()==''){
            this.app.errorValidateDate('T??n ????ng nh???p ho???c password kh??ng ???????c ????? tr???ng');
            this.inputUseNameLogin.nativeElement.focus();
            return;
        }
        if(this.employeeUnitInfo == null || this.employeeUnitInfo ==undefined || this.employeeUnitInfo.email == null ){
            this.app.errorValidateDate('Qu???n l?? ????n v??? kh??ng ???????c ????? tr???ng');
            this.inputEmployeeUnit.domHandler.findSingle(this.inputEmployeeUnit.el.nativeElement, 'input').focus();
            return;
        }else{
            if(this.email!= null){
                if(this.email == this.employeeUnitInfo.email){
                    this.app.errorValidateDate('Kh??ng ???????c ch???n tr??ng qu???n l??');
                    this.inputEmployeeUnit.domHandler.findSingle(this.inputEmployeeUnit.el.nativeElement, 'input').focus();
                    return;
                }
                this.email  = this.email +';'+ this.employeeUnitInfo.email;
            }else {
                this.email = this.employeeUnitInfo.email;
            }

        }
        this.displayConfimSign= true;
        this.notiSign = '??/c c?? ch???c ch???n mu???n th???c hi???n';
        this.isconfirmSign= true;
        this.disabledSign = false;
    }

    confirmSign(){
        this.disabledSign = true;
        let object={
            "username" : this.useNameLogin,
            "password" : this.passwordLogin,
            "emails" : this.email,
           "jobTitles" : "Nh??n vi??n ph??t tri???n ph???n m???m",
            "signImageIndexs" : "1",
            "isPublicTexts" : "YES",
            'signVofficeId' : this.signId,
            "typeSign" : this.typeSign,
            "fileName" : this.fileName,
            "isSign":1

        }
        this.serviceDataService.signVoffice(object).subscribe(item => {
            this.isconfirmSign= false;
            if (item.status == 1) {
                this.app.showSuccess('Tr??nh k?? th??nh c??ng');
                this.displayConfimSign = false;
                this.displaySign = false;
                this.resetButon();
                //this.notiSign = 'Tr??nh k?? th??nh c??ng';
            } else if(item.status == 0) {
                if(item.result== null) {
                    this.notiSign = 'Qu???n l?? kh??ng t???n t???i';
                    return;
                }else if(this.signError[item.result] != undefined){
                    this.notiSign = this.signError[item.result];
                    return;
                } else if(this.signError[item.result] == undefined) {
                    this.notiSign = 'Tr??nh k?? kh??ng th??nh c??ng';
                    return;
                }
            }

        })
    }

    btnCancelConfirmSign(){
        this.displayConfimSign = false;
    }
    btnCancelConfirmSendSign(){
        this.displayConfimSign = false;
        this.displaySign = false;
        this.resetButon();
    }

    exportSigVoficeSynthetic(item){
        if(item.type==2){
            let object= {
                'signVofficeId': item.signVofficeId
            }
            this.serviceDataService.exportSigVofice(object).subscribe(
                (next: ArrayBuffer) => {
                    if(next.byteLength==0){
                        this.app.errorValidateDate('File kh??ng t???n t???i');
                    }else {
                        var fileName = 'BCTT_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
                            + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
                            + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.pdf';
                        const file: Blob = new Blob([next], {type: 'application/pdf'});
                        saveAs(file, fileName);
                    }

                }
            );
        }else if(item.type==1){
            let object= {
                'signVofficeId': item.signVofficeId
            }
            this.serviceDataService.exportSigVofice(object).subscribe(
                (next: ArrayBuffer) => {
                    if(next.byteLength==0){
                        this.app.errorValidateDate('File kh??ng t???n t???i');
                    }else{
                        var fileName = 'BCCT_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
                            + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
                            + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.pdf';
                        const file: Blob = new Blob([next], {type: 'application/pdf'});
                        saveAs(file, fileName);
                    }

                }
            );
        }

    }

    paginate(event){
        this.startRow =event.first;
        if (this.fromDate) this.fromDate.setHours(0, 0, 0, 0);
        this.signVofficeSearch = {
            fromDate: this.fromDate,
            toDate: this.toDate,
            documentCode: (this.documentcode != null &&this.documentcode.documentCode != undefined && this.documentcode.documentCode != null )? this.documentcode.documentCode: this.documentcode ,
            signUserName: (this.signUserName  != null &&this.signUserName.email != undefined && this.signUserName.email != null )? this.signUserName.email: this.signUserName ,
            type: this.selectedType != null ? this.selectedType.value : null,
            status: this.selectStatus != null ? this.selectStatus.value : null,
            startRow: event.first,
            rowSize: 10
        }

        this.serviceDataService.findSignVoffice(this.signVofficeSearch).subscribe(item => {

            this.listSignVoffice = item['data'].listSignVoffice;
            this.totalRecord = item['data'].totalSiginVoffice;
            this.rowSize = this.listSignVoffice.length;
            //console.log(this.change_alias("xin ch??o m???i ng?????i t??i t??n l?? ho??ng v??n quang"));


        });
    }

    change_alias(alias) {
        var str = alias;
        if(str!= null || str != undefined ){
            str = str.toLowerCase();
            str = str.replace(/??|??|???|???|??|??|???|???|???|???|???|??|???|???|???|???|???/g,"a");
            str = str.replace(/??|??|???|???|???|??|???|???|???|???|???/g,"e");
            str = str.replace(/??|??|???|???|??/g,"i");
            str = str.replace(/??|??|???|???|??|??|???|???|???|???|???|??|???|???|???|???|???/g,"o");
            str = str.replace(/??|??|???|???|??|??|???|???|???|???|???/g,"u");
            str = str.replace(/???|??|???|???|???/g,"y");
            str = str.replace(/??/g,"d");
            str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g," ");
            str = str.replace(/ + /g," ");
            str = str.trim();
        }

        return str;
    }
    change_aliasStatus(alias,status) {
        var str = alias;
        if((this.isManager|| this.isQl) && status==4){
            return '';
        }
        if(str!= null || str != undefined ){
            str = str.toLowerCase();
            str = str.replace(/??|??|???|???|??|??|???|???|???|???|???|??|???|???|???|???|???/g,"a");
            str = str.replace(/??|??|???|???|???|??|???|???|???|???|???/g,"e");
            str = str.replace(/??|??|???|???|??/g,"i");
            str = str.replace(/??|??|???|???|??|??|???|???|???|???|???|??|???|???|???|???|???/g,"o");
            str = str.replace(/??|??|???|???|??|??|???|???|???|???|???/g,"u");
            str = str.replace(/???|??|???|???|???/g,"y");
            str = str.replace(/??/g,"d");
            str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g," ");
            str = str.replace(/ + /g," ");
            str = str.trim();
        }

        return str+'.pdf';
    }

    onClickDetailFile(item: any) {
        console.log(item);
        if(!item) return;
        if(item.type === 1) {
            this.showReportSignPdf(item);
        } else if(item.type === 2) {
            this.downloadDetailFile(item);
        }
    }

    downloadDetailFile(item: any) {
        this.serviceDataService.downloadDetailFile(item).subscribe(
          (next: ArrayBuffer) => {
              if(next.byteLength==0){
                  this.app.errorValidateDate('File kh??ng t???n t???i');
              } else {
                  var fileName = 'BCCT_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
                    + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
                    + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
                  const file: Blob = new Blob([next], { type: 'application/xlsx' });
                  saveAs(file, fileName);
              }
          }
        );
    }

    showReportSignPdf(item){
        if(item.type==2 && (this.isManager|| this.isQl) ){
            let object= {
                'signVofficeId': item.signVofficeId

            }
            this.serviceDataService.exportSigVofice(object).subscribe(

                (next: ArrayBuffer) => {
                    if(next.byteLength==0){
                        this.app.errorValidateDate('File kh??ng t???n t???i');
                    }else {
                        var fileName = 'BCTT_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
                            + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
                            + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.pdf';
                        const file: Blob = new Blob([next], {type: 'application/pdf'});
                        var fileURL = URL.createObjectURL(file);
                        window.open(fileURL);
                    }

                    // saveAs(file, fileName);
                }
            );
        }else if(item.type==1 && (this.isManager|| this.isQl) && item.status!=4 ){
            let object= {
                'signVofficeId': item.signVofficeId
            }
            this.serviceDataService.exportSigVofice(object).subscribe(
                (next: ArrayBuffer) => {
                    if(next.byteLength==0){
                        this.app.errorValidateDate('File kh??ng t???n t???i');
                    }else {
                        var fileName = 'BCCT_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
                            + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
                            + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.pdf';
                        const file: Blob = new Blob([next], {type: 'application/pdf'});
                        var fileURL = URL.createObjectURL(file);
                        window.open(fileURL);
                    }

                    //saveAs(file, fileName);
                }
            );
        }
    }
}
