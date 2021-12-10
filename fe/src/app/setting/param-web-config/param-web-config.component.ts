import {ChangeDetectorRef, Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {SettingService} from "../setting.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {Router} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {MasterClass, ResultConfigName} from "../Entity/ListPlace";
import {Table} from "primeng/table";
import {AppComponent} from "../../app.component";
import {Dropdown, InputText} from "primeng/primeng";

@Component({
  selector: 'app-param-web-config',
  templateUrl: './param-web-config.component.html',
  styleUrls: ['./param-web-config.component.css']
})
export class ParamWebConfigComponent implements OnInit {

  @ViewChild('myTable') private myTable: Table;
  @ViewChild('msClassFld') private msClassFld: Dropdown;
  @ViewChild('msClassText') private msClassText: ElementRef;
  isAdd: boolean;
  isEdit: boolean;
  isAddParamWebConfig: boolean;
  searchClassConfigList : ResultConfigName[];
  msClassConfigList : MasterClass[];
  resultInsert : ResultConfigName[];
  msClassInsertConfigList : ResultConfigName[];
  convertStatus : string[];
  msClassConfig : MasterClass;
  msClassConfigUpdate : ResultConfigName;
  searchCountClassConfigList : ResultConfigName[];
  paramSearch: ResultConfigName = {
    masterName : null,
    masterClass : null,
    value : null,
    name : null,
    masterCode : null,
    nameConfig : null,
    valueConfig : null,
    noteConfig : null,
    pageSize : null,
    pageNumber : null,
    status : null
  }
  paramInsert: ResultConfigName = {
    masterName : null,
    masterClass : null,
    value : null,
    name : null,
    masterCode : null,
    nameConfig : null,
    valueConfig : null,
    noteConfig : null,
    pageSize : null,
    pageNumber : null,
    status : null
  }
  masterParam : ResultConfigName = {
    masterName : null,
    masterClass : null,
    value : null,
    name : null,
    masterCode : null,
    nameConfig : null,
    valueConfig : null,
    noteConfig : null,
    pageSize : null,
    pageNumber : null,
    status : null
  } ;
  updateMasterParam : ResultConfigName = {
    masterName : null,
    masterClass : null,
    value : null,
    name : null,
    masterCode : null,
    nameConfig : null,
    valueConfig : null,
    noteConfig : null,
    pageSize : null,
    pageNumber : null,
    status : null
  } ;
  insertMasterParam : ResultConfigName = {
    masterName : null,
    masterClass : null,
    value : null,
    name : null,
    masterCode : null,
    status : null,
    updateDate : null,
    nameConfig : null,
    valueConfig : null,
    noteConfig : null,
    updateUser : null
  } ;
  paramName : string;
  paramNameInsert : string;
  status : string;
  statusInsert : string;
  isShow: boolean;
  pageNumber : number;
  pageSize : number;
  totalRecords : number;
  isDisabled : boolean;
  isSearch : boolean;
  constructor(private settingService: SettingService, private confirmationService: ConfirmationService,private cdref: ChangeDetectorRef,private app: AppComponent,
              private messageService: MessageService, private title: Title, private renderer: Renderer2, private router: Router) {
    this.settingService.getMasterClassWeb().subscribe(res => {
      this.msClassConfigList = res.data;
    });
    this.status = "1";
    this.settingService.getMasterClass().subscribe(res => {
      this.msClassInsertConfigList = res.data;
    });
  }

  ngOnInit() {
    this.isAdd = true;
    this.isEdit = false;
    this.isShow = false;
    this.isSearch = true;
    this.setStatus();
    this.paramName = '';
  }
  setStatus(){
    this.convertStatus = ['Không hoạt động', 'Hoạt động'];
  }
  handleAdd(){
    this.isAddParamWebConfig = true;
  }
  handleEdit(){
  this.settingUpdate();
  }
  settingUpdate(){
    this.updateMasterParam.masterClass = this.msClassConfig.masterCode;
    this.updateMasterParam.name = this.paramName.trim();
    if(this.updateMasterParam.name == ''){
      this.app.showWarn('Giá trị tham số không được để trống');
      this.msClassText.nativeElement.value = '';
      this.msClassText.nativeElement.focus();
      return;
    }
    this.updateMasterParam.status = this.status;
    this.updateMasterParam.value = this.msClassConfigUpdate.value;
    this.settingService.editWebParam(this.updateMasterParam).subscribe(res => {
      if(res.status == 1){
        this.app.showSuccess("Chỉnh sửa tham số thành công");
        this.msClassConfig = null;
        this.paramName = null;
        this.isShow = false;
        this.handleReset();
      }else if (res.status == 5) {
        this.app.showWarn("Đã tồn tại cấu hình tham số này, đồng chí vui lòng chọn lại");
      }
    });
  }
  onValidate(insertMasterParam :ResultConfigName) {
    if (insertMasterParam.masterClass == '' ||  insertMasterParam.masterClass == null) {
      this.msClassFld.focus();
      this.app.showWarn('Loại tham số phải được chọn');
      return false;
    } if (insertMasterParam.name == '' ||  insertMasterParam.name == null ) {
      this.msClassText.nativeElement.value ='';
      this.msClassText.nativeElement.focus();
      this.app.showWarn('Giá trị không được để trống');
      return false;
    }
    return true;
  }
  addNew(){
    this.settingInsert();
  }
settingInsert() {
    if(this.msClassConfig != null) {
      this.paramInsert.masterClass = this.msClassConfig.masterCode;
      this.insertMasterParam.masterClass = this.msClassConfig.masterCode;
    }if(this.status != null){
  this.insertMasterParam.status = this.status;
  }if(this.paramName != null && this.paramName.trim() != '' ){
  this.insertMasterParam.name = this.paramName.trim();
  }
  if(this.paramInsert.masterClass == null){
    this.app.showWarn("Loại tham số phải được chọn");
    this.msClassFld.focus();
    return;
  }
  if(this.paramInsert.masterClass != null){
  this.settingService.getCodeValueByMasterClass(this.paramInsert).subscribe(res => {
    this.resultInsert = res.data;
    if(this.resultInsert != null){
      let last: ResultConfigName =  this.resultInsert[this.resultInsert.length - 1];
      let valueNumber = Number(last.value);
      let valueNew = valueNumber + 1;
      let valueInsert = '';
      if(valueNew < 10){
        valueInsert = '0' + valueNew;
      }else {
        valueInsert = ''+valueNew;
      }
      this.insertMasterParam.value = valueInsert;
      let isValid = this.onValidate(this.insertMasterParam)
      if (isValid) {
        this.settingService.insertMasterCodeWeb(this.insertMasterParam).subscribe(res => {
          if (res.status == 1) {
            this.app.showSuccess("Đã thêm cấu hình tham số  thành công");
            this.paramName ='';
            this.msClassConfig = null;
            this.status = '1';
            this.handleReset();
          } if (res.status == 5) {
            this.app.showWarn("Đã tồn tại cấu hình tham số này, đồng chí vui lòng chọn lại");
          }
        });
      }
    }
  });
  }
}
  onLazyLoad(event){
    this.paramSearch.pageNumber = event.first;
    this.paramSearch.pageSize = event.rows;
    this.pageNumber = event.first;
    this.pageSize = event.rows;
    this.settingService.getCountParamWebConfigByCode(this.paramSearch).subscribe(res => {
      this.searchCountClassConfigList = res.data;
      this.totalRecords = this.searchCountClassConfigList.length;
    });
    this.settingService.getParamWebConfigByCode(this.paramSearch).subscribe(res => {
      this.searchClassConfigList = res.data;
    });
  }

  handleSearch(){
    this.isAdd = true;
    this.isEdit = false;
    this.isSearch = true;
    this.settingSearch();
    this.myTable.reset();
  }
  handleSearchReset(){
    this.isSearch = true;
    this.isAdd = true;
    this.isEdit = false;
    this.isDisabled = false;
    this.settingSearchReset();
    this.myTable.reset();

  }
  settingSearchReset(){
    this.paramSearch.name = this.paramName.trim();;
    this.paramSearch.status =  '1';
    if(this.msClassConfig != null){
      this.paramSearch.masterName = this.msClassConfig.masterCode;
    } else {
      this.paramSearch.masterName = null;
    }
  }
  settingSearch(){
    this.paramSearch.status = this.status;
    if(this.paramName != null){
    this.paramSearch.name = this.paramName.trim();
    }
    if(this.msClassConfig != null){
    this.paramSearch.masterName = this.msClassConfig.masterCode;
    } else {
      this.paramSearch.masterName = null;
    }
  }

  doEdit(event :ResultConfigName) {
    console.log(event);
    if(!event.masterName) this.msClassConfig = { masterCode: event.masterCode, masterName: event.masterCode };
    else this.msClassConfig = {masterCode: event.masterCode, masterName: event.masterCode + ' - ' + event.masterName };
    if(event.status ==='0' ){
      this.status ='0';
    }else if(event.status === '1'){
      this.status = '1';
    }
    this.isSearch = false;
    this.msClassConfigUpdate = event;

    this.paramName = event.name;
    this.isEdit = true;
    this.isAdd = false;
    this.isShow = true;
    this.isDisabled = true;
  }
  handleReset(){
    this.insertMasterParam.masterClass = null;
    this.insertMasterParam.masterCode = null;
    this.insertMasterParam.masterName = null;
    this.insertMasterParam.name = null;
    this.insertMasterParam.nameConfig = null;
    this.insertMasterParam.noteConfig = null;
    this.insertMasterParam.status = null;
    this.insertMasterParam.updateDate = null;
    this.insertMasterParam.updateUser = null;
    this.insertMasterParam.value = null;
    this.insertMasterParam.valueConfig = null;
    this.isShow = false;
    this.status = "1";
    this.isDisabled = false;
    this.msClassConfig = null;
    this.paramName = '';
    this.handleSearchReset();
  }
}
