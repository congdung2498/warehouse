import {ChangeDetectorRef, Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {SettingService} from "../setting.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {Title} from "@angular/platform-browser";
import {Router} from "@angular/router";
import {AppComponent} from "../../app.component";
import {ResultConfigName} from "../Entity/ListPlace";
import {Table} from "primeng/table";

@Component({
  selector: 'app-param-process-config',
  templateUrl: './param-process-config.component.html',
  styleUrls: ['./param-process-config.component.css']
})
export class ParamProcessConfigComponent implements OnInit {

  @ViewChild('myTable') private myTable: Table;
  @ViewChild('msClassNoteEle') private msClassNoteEle: ElementRef;
  @ViewChild('msClassConfigEle') private msClassConfigEle: ElementRef;
  msClassConfigList : ResultConfigName[];
  msClassConfig : ResultConfigName ={
    masterName : null,
    masterClass : null,
    value : null,
    name : null,
    masterCode : null,
    nameConfig : null,
    valueConfig : null,
    noteConfig : null,
    status : null,
    pageSize : null,
    pageNumber : null,
    masterClassConfig : null
  };
  isDisabled : boolean;
  searchClassConfigList : ResultConfigName[];
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
    status : null,
    pageSize : null,
    pageNumber : null,
    masterClassConfig : null
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
    masterClassConfig : null
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
    masterClassConfig : null
  } ;
  paramName: string;
  paramNote: string;
  paramConfig: string;
  isAdd: boolean;
  isEdit: boolean;
  totalRecords: number;
  pageNumber : number;
  pageSize : number;
  isShow : boolean;
  isSearch : boolean;
  constructor(private settingService: SettingService, private confirmationService: ConfirmationService,private cdref: ChangeDetectorRef,private app: AppComponent,
              private messageService: MessageService, private title: Title, private renderer: Renderer2, private router: Router) { }

  ngOnInit() {
    this.settingService.getAllProcessConfig().subscribe(res => {
      this.msClassConfigList = res.data;
    });
    this.isSearch = true;
  }

  onLazyLoad(event){
    this.paramSearch.pageNumber = event.first;
    this.paramSearch.pageSize = event.rows;
    this.pageNumber = event.first;
    this.pageSize = event.rows;
    this.settingService.getCountParamProcessConfigByCode(this.paramSearch).subscribe(res => {
      this.searchCountClassConfigList = res.data;
      this.totalRecords = this.searchCountClassConfigList.length;
    });
    this.settingService.getParamProcessConfigByCode(this.paramSearch).subscribe(res => {
      this.searchClassConfigList = res.data;
    });
  }
  settingSearch(){
    if(this.msClassConfig != null) {
      this.paramSearch.masterClassConfig = this.msClassConfig.masterClassConfig;
    }else {
      this.paramSearch.masterClassConfig = null;
    }
    if(this.paramName != null){
   this.paramSearch.nameConfig = this.paramName.trim();
    } if(this.paramConfig != null){
   this.paramSearch.valueConfig = this.paramConfig.trim();
    }if(this.paramNote != null){
   this.paramSearch.noteConfig = this.paramNote.trim();
    }
  }
  handleSearch(){
    this.isSearch = true;
    this.isAdd = false;
    this.isEdit = false;
    this.isShow = false;
    this.settingSearch();
    this.myTable.reset();
  }
  doSearch(){
    this.settingSearch();
    this.myTable.reset();
  }
  doEdit(event :ResultConfigName){
    this.isSearch = false;
    this.msClassConfig = event;
    this.paramName = event.nameConfig;
    this.paramNote = event.noteConfig;
    this.paramConfig = event.valueConfig;
    this.isEdit = true;
    this.isShow = true;
    this.isDisabled = true;
  }
  validateForm(updateMasterParam : ResultConfigName): boolean {

    if (updateMasterParam.valueConfig == '') {
      this.app.showWarn('Giá trị tham số không được để trống');
      this.msClassConfigEle.nativeElement.value ='';
      this.msClassConfigEle.nativeElement.focus();
      return false;
    }
    return true;
  }
  handleEdit(){
    this.settingUpdate();
  }
  settingUpdate(){
    this.updateMasterParam.masterClassConfig = this.msClassConfig.masterClassConfig.trim();
    this.updateMasterParam.nameConfig = this.msClassConfig.nameConfig.trim();
    this.updateMasterParam.valueConfig = this.paramConfig.trim();
    this.updateMasterParam.noteConfig = this.paramNote.trim();
    if(!this.validateForm(this.updateMasterParam)){
      return;
    }
    this.settingService.editProcessParam(this.updateMasterParam).subscribe(res => {
      if(res.status =1){
        this.app.showSuccess('Cập nhật tham số thành công');
        this.isSearch = true;
        this.isShow = false;
        this.msClassConfig = null;
        this.paramName = '';
        this.paramNote = '';
        this.paramConfig = '';
        this.isEdit = false;
        this.isDisabled = false;
        this.handleSearch();
      }
    });
  }
  handleReset(){
    this.msClassConfig = null;
    this.paramName = null;
    this.paramNote = null;
    this.paramConfig = null;
    this.isDisabled = false;
    this.isShow = false;
    this.isSearch = true;
    this.isEdit = false;
    this.handleSearchReset();
    this.myTable.reset();
  }
  handleSearchReset(){
      this.paramSearch.masterClassConfig = null;
      this.paramSearch.nameConfig = null;
      this.paramSearch.valueConfig = null;
      this.paramSearch.noteConfig = null;
  }

}
