import {ChangeDetectorRef, Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {ResultConfigName} from "../Entity/ListPlace";
import {SettingService} from "../setting.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {Title} from "@angular/platform-browser";
import {Router} from "@angular/router";
import {Table} from "primeng/table";
import {AppComponent} from "../../app.component";

@Component({
  selector: 'app-param-type-config',
  templateUrl: './param-type-config.component.html',
  styleUrls: ['./param-type-config.component.css']
})
export class ParamTypeConfigComponent implements OnInit {

  @ViewChild('msClassDrop') private msClassDrop: ElementRef;
  @ViewChild('msClassText') private msClassText: ElementRef;
  msClassConfigList :ResultConfigName[];
  searchClassConfigList :ResultConfigName[];
  msClassInsertConfigList: ResultConfigName[];
  searchCountClassConfigList : ResultConfigName[];
  masterParam : ResultConfigName = {
    masterName : null,
    masterClass : null,
    value : null,
    name : null,
    masterCode : null,
    nameConfig : null,
    valueConfig : null,
    noteConfig : null,
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
  } ;
  paramSearch: ResultConfigName = {
    masterName : null,
    masterClass : null,
    value : null,
    name : null,
    nameConfig : null,
    valueConfig : null,
    noteConfig : null,
    masterCode : null
  }
  isDisabled : boolean;
  isEdit: boolean;
  paramName : string;
  isShow : boolean;
  convertStatus = [''];
  pageNumber : number;
  pageSize : number;
  totalRecords : number;
  isSearch : boolean;
  @ViewChild('myTable') private myTable: Table;
  constructor(private settingService: SettingService, private confirmationService: ConfirmationService,private cdref: ChangeDetectorRef,private app: AppComponent,
              private messageService: MessageService, private title: Title, private renderer: Renderer2, private router: Router) {
    this.isShow = false;
    this.isEdit = false;
  }

  ngOnInit() {
    this.isSearch = true;
    this.settingService.getMasterClass().subscribe(res => {
      this.msClassConfigList = res.data;
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


  handleSearch(){
    this.isSearch = true;
    this.settingSearch();
    this.myTable.reset();
  }
  settingSearch(){
    if(this.paramName != null){
      this.paramSearch.masterName = this.paramName.trim();
    }
    if(this.masterParam != null){
      this.paramSearch.masterCode = this.masterParam.masterCode;
    }else {
      this.paramSearch.masterCode = null;
    }
  }
  handleSave() {
    this.settingUpdate();
  }
  settingUpdate(){
    this.updateMasterParam.masterCode = this.masterParam.masterCode;
    this.updateMasterParam.masterName = this.paramName.trim();
    let isValid = this.onValidate(this.updateMasterParam);
    if(isValid){
      this.settingService.editTypeParam(this.updateMasterParam).subscribe(res => {
        if(res.status ==1){
          this.app.showSuccess('Cập nhật tham số thành công');
          this.handleSearchReset();
        }
      });
    }
  }
  onValidate(updateMasterParam : ResultConfigName){
    if(updateMasterParam.masterCode == null){
      this.app.showWarn('Đ/c phải chọn loại tham số');
      this.msClassDrop.nativeElement.focus();
      return false;
    } if(updateMasterParam.masterName == null || updateMasterParam.masterName.trim() == '' ){
      this.app.showWarn('Tên loại tham số không được để trống');
      this.msClassText.nativeElement.value ='';
      this.msClassText.nativeElement.focus();
      return false;
    }
    return true;
  }

  onLazyLoad(event) {
    this.paramSearch.pageNumber = event.first;
    this.pageNumber = event.first;
    this.pageSize = event.rows;
    this.paramSearch.pageSize = event.rows;
    this.settingService.getCountMasterClassByCode(this.paramSearch).subscribe(res => {
      this.searchCountClassConfigList = res.data;
      this.totalRecords = this.searchCountClassConfigList.length;
    });
    this.settingService.getMasterClassByCode(this.paramSearch).subscribe(res => {
      this.searchClassConfigList = res.data;
    });
  }

  doEdit(event :ResultConfigName){
    this.isSearch = false;
    this.masterParam = JSON.parse(JSON.stringify(event));
    this.paramName = event.masterName;
    if(!this.masterParam.masterName) this.masterParam.masterName = this.masterParam.masterCode;
    this.isShow = true;
    this.isDisabled = true;
  }

  handleReset(){
    this.isShow = false;
    this.isDisabled = false;
    this.handleSearchReset();
  }

  handleSearchReset(){
    this.masterParam = null;
    this.paramName = '';
    this.isShow = false;
    this.isSearch = true;
    this.isEdit = false;
    this.isDisabled = false;
    this.settingSearchReset();
    this.myTable.reset();
  }

  settingSearchReset(){
    this.settingService.getMasterClass().subscribe(res => {
      this.msClassConfigList = res.data;
    });
    this.paramSearch.name = null;
    this.paramSearch.masterCode = null;
    this.paramSearch.masterName = null;
  }
}
