import {Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {KitchenManagementService} from '../../kitchen-management/kitchen-management.service';
import {ConfirmationService, MessageService, TreeNode} from 'primeng/api';
import {AppComponent} from '../../app.component';
import {Router} from '@angular/router';
import {Title} from '@angular/platform-browser';
import {TokenStorage} from '../../shared/token.storage';
import {UserInfo} from '../../shared/UserInfo';
import {
  DataResponseEdit, DataResponseQuota, InsertQuota, RequestParamQuota,
  UpdateLimitDateDTO
} from '../Entity/Stationery';
import {StationeryService} from '../stationery.service';
import {Table} from 'primeng/table';
import {ValidateUtils} from "../../../common/validate";

@Component({
  selector: 'app-add-stationery',
  templateUrl: './add-stationery.component.html',
  styleUrls: ['./add-stationery.component.css']
})
export class AddStationeryComponent implements OnInit {
  @ViewChild('inputQuantity') inputQuantity: ElementRef;
  @ViewChild('inputQuantityAdd') inputQuantityAdd: ElementRef;
  @ViewChild('inputQuota') private inputQuota: ElementRef;
  @ViewChild('inputQuotaAdd') private inputQuotaAdd: ElementRef;
  @ViewChild('inputquotaEdit') private inputquotaEdit: ElementRef;
  @ViewChild('inputQuantityFldEdit') inputQuantityFldEdit: ElementRef;

  @ViewChild('dt') dataTableComponent: Table;
  listSelectedUnitId: any[] = [];
  listSelectedUnitDetailsId: any[] = [];
  dataResponseQuota: DataResponseQuota[] ;
  updateLimitDateDTO: UpdateLimitDateDTO = { codeName : 0};
  updateQuota: DataResponseEdit ={
    id : '' ,
    unitName : '' ,
    unitId  : null,
    quota : null ,
    quantity : null,
    total : null,
    path : '',
  };
  onlyNum: RegExp = /^[0-9,.]+$/;
  listUnitId: any[] = [];
  insertQuota: InsertQuota = {
    unitId : null,
    quota : null,
    quantity : null
  };
  requestParamQuota: RequestParamQuota = {
    quantity : null,
    dateLimit : null,
    quota : null,
    listStatus : [],
    pageNumber : null,
    pageSize : null,

  };
  price : any;
  numberStr : string;
  fixNumberQuotaHCVP : string;
  totalEdit : any;
  dialogEdit : boolean;
  total : any;
  money : number;
  quotaUpdate : any;
  quotaUpdateString : string;
  quantityUpdate : any;
  limitDateUpdate : number;
  dialogUpdate: boolean;
  dialogChangeDate: boolean;
  quota: any = null;
  quantity: any = null;
  userInfo: UserInfo;
  listParentUnit: string[];
  listParentUnitDetails: string[];
  selectedSector: TreeNode[];
  sectorsUpdate: TreeNode[];
  editSectors: TreeNode[];
  selectedSectorDetails: TreeNode[];
  sectorUpdates: TreeNode[];
  selectedNode: TreeNode;
  sectors: TreeNode[];
  sectorsDetails: TreeNode[];
  loadingTreeUpdate: boolean;
  loadingTree = false;
  tempNode: TreeNode;
  editSelectedSector: TreeNode;
  selectedSectorUpdate: TreeNode[];
  listReceiver: any[];
  total_record: number = 0;
  limitDate: number ;
  getLimitDate: string;

  startRow: number = 0;
  rowSize: number = 0;

  constructor( private _KitchenManagementService: KitchenManagementService,
               private confirmationService: ConfirmationService, private messageService: MessageService, private  app: AppComponent ,
               private tokenStorage: TokenStorage, private title: Title, private renderer: Renderer2, private router: Router, private stationeryService: StationeryService) { }

  ngOnInit() {
    this.userInfo = this.tokenStorage.getUserInfo();
    this.checkRole();
    this.selectedSectorUpdate = [];
    this.stationeryService.getLimitDateEnd().subscribe(res => {
      if (res) {
        this.limitDate = res.data;
      }
    });
  }

  findNode(sectorUpdates: TreeNode, unitId: any) {
    if (sectorUpdates.data == unitId) {
      this.selectedNode = sectorUpdates;
    }
    for (let i = 0; i < sectorUpdates.children.length; i++) {
      this.findNode(sectorUpdates.children[i], unitId);
    }
  }

  findNode2(sectorUpdates: TreeNode, unitId: any) {
    if (sectorUpdates.data == unitId) {
      this.tempNode = sectorUpdates;
      sectorUpdates.expanded = true;
    }
    for (let i = 0; i < sectorUpdates.children.length; i++) {
      this.findNode2(sectorUpdates.children[i], unitId);
    }
  }

  setExpand(sectorUpdates: TreeNode, node: any) {
    if (node == null) {
      return;
    }
    this.findNode2(sectorUpdates, node);
    this.setExpand(sectorUpdates, this.tempNode.parent);
  }

  //  check role user
  checkRole() {
    if (this.userInfo.role.includes('PMQT_Canhve')) {
      this.getSector(0);
      this.getSectorsDetails(0);
    } else if (this.userInfo.role.includes('PMQT_ADMIN') || this.userInfo.role.includes('PMQT_Bep_truong')|| this.userInfo.role.includes('PMQT_HCVP_VPP')) {
      this.getSector(0);
      this.getSectorsDetails(0);
    } else if (this.userInfo.role.includes('PMQT_QL') || this.userInfo.role.includes('PMQT_CVP')) {
      this.getSector(1);
      this.getSectorsDetails(1);
    } else {
      this.getSector(2);
      this.getSectorsDetails(2);
    }
  }

  getSectorsDetails(type : number){
    if (type === 0) {
      this._KitchenManagementService.getSectors(null).subscribe(data => {
        this.sectorsDetails = data;
        const parentId = [];
        this.sectorsDetails.forEach(value => {
          if (value.data) {
            parentId.push(value.data);
          }
        });
        this.listParentUnitDetails = parentId;
      });
    } else if (type === 1) {
      this._KitchenManagementService.getSectors(this.userInfo.unitId as string).subscribe(data => {
        this.sectorsDetails = [{
          label: this.userInfo.unitName as string,
          data: this.userInfo.unitId,
          selectable: true,
          children: data
        }];
        this.listParentUnitDetails = [];
        this.listParentUnitDetails.push(this.userInfo.unitId as string);
      });
    } else if (type === 2) {
      this.sectorsDetails = [{
        label: this.userInfo.unitName as string,
        data: this.userInfo.unitId,
        selectable: false
      }];
      this.selectedSectorDetails = [this.sectorsDetails[0]];
    }
  }

  onChangePrice(){
    if(+this.inputQuota.nativeElement.value <1){
      this.inputQuota.nativeElement.value = '';
    }
    if (/[^0-9.]/g.test(this.inputQuota.nativeElement.value)) {
      this.inputQuota.nativeElement.value = this.inputQuota.nativeElement.value.toString().replace(/[^0-9.]/g, '');
    }

    if (/^[0]+$/g.test(this.inputQuota.nativeElement.value)) {
      this.inputQuota.nativeElement.value = '0';
    }

    if (/[.]+.0[0]+$/g.test(this.inputQuota.nativeElement.value)) {
      this.inputQuota.nativeElement.value = this.inputQuota.nativeElement.value.toString().replace(/[.]+.0[0]+$/g, '');
    }

    if(this.inputQuota.nativeElement.value.indexOf('.')!==this.inputQuota.nativeElement.value.lastIndexOf('.')&&this.inputQuota.nativeElement.value.indexOf('.')!==-1){
      this.inputQuota.nativeElement.value =  this.inputQuota.nativeElement.value.substring(0,  this.inputQuota.nativeElement.value.length - 1);
    }

    if (!/^(?!(0))*[1-9][0-9]{0,}$/g.test(this.inputQuota.nativeElement.value)) {
      this.inputQuota.nativeElement.value = this.inputQuota.nativeElement.value !== '0' ?
          this.inputQuota.nativeElement.value.toString().replace(/^0*{0.}/g, '') : '0';
    }
    if ((this.inputQuota.nativeElement.value.length > 1
        && this.inputQuota.nativeElement.value.charAt(this.inputQuota.nativeElement.value.length - 1) !== '.') &&
        (this.inputQuota.nativeElement.value
            .substring(this.inputQuota.nativeElement.value.length - 2, this.inputQuota.nativeElement.value.length) !== '.0')) {
      this.inputQuota.nativeElement.value =
          parseFloat(this.inputQuota.nativeElement.value ).toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});
    } else {
      this.inputQuota.nativeElement.value = this.inputQuota.nativeElement.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
    this.quota =this.inputQuota.nativeElement.value;
  }

  onChangeQuantity(){
    if(+this.inputQuantity.nativeElement.value <1){
      this.inputQuantity.nativeElement.value = '';
    }
    if (/[^0-9]/g.test(this.inputQuantity.nativeElement.value)) {
      this.inputQuantity.nativeElement.value = this.inputQuantity.nativeElement.value.toString().replace(/[^0-9]/g, '');
    }
    if ((this.inputQuantity.nativeElement.value.length > 1
        && this.inputQuantity.nativeElement.value.charAt(this.inputQuantity.nativeElement.value.length - 1) !== '.') &&
        (this.inputQuantity.nativeElement.value
            .substring(this.inputQuantity.nativeElement.value.length - 2, this.inputQuantity.nativeElement.value.length) !== '.0')) {
      this.inputQuantity.nativeElement.value =
          parseFloat(this.inputQuantity.nativeElement.value).toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});
    } else {
      this.inputQuantity.nativeElement.value = this.inputQuantity.nativeElement.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
  }

  getSector(type: number) {
    this.listParentUnit = [];

    if (type === 0) {
      this._KitchenManagementService.getSectors(null).subscribe(data => {
        this.sectors = data;
        const parentId = [];
        this.sectors.forEach(value => {
          if (value.data) {
            parentId.push(value.data);
          }
        });
        this.listParentUnit = parentId;
        this.sectors[0].selectable = false;
      });

    } else if (type === 1) {
      this._KitchenManagementService.getSectors(this.userInfo.unitId as string).subscribe(data => {
        this.sectors = [{
          label: this.userInfo.unitName as string,
          data: this.userInfo.unitId,
          selectable: true,
          children: data
        }];
        this.listParentUnit.push(this.userInfo.unitId as string);
      });

    } else if (type === 2) {
      this.sectors = [{
        label: this.userInfo.unitName as string,
        data: this.userInfo.unitId,
        selectable: false
      }];
      this.selectedSector = [this.sectors[0]];
    }
  }

  nodeExpandDetails(event) {
    this.loadingTree = true;
    if (event.node && event.node.children !== undefined) {
      this.loadingTree = false;
      return;
    }
    if (event.node && event.node.children === undefined) {
      this._KitchenManagementService.getSectors(event.node.data).subscribe(data => {
        event.node.children = data;
        this.loadingTree = false;
      });
    }
  }

  nodeExpand(event) {
    this.loadingTree = true;
    if (event.node && event.node.children !== undefined) {
      this.loadingTree = false;
      return;
    }
    if (event.node && event.node.children === undefined) {
      this._KitchenManagementService
        .getSectors(event.node.data).subscribe(
        data => {
          event.node.children = data;
          this.loadingTree = false;
        });
    }
  }

  nodeSelect(event){
    this.selectedSector = [];
    this.selectedSector.push(event.node);
  }

  public onLazyLoad(event) {
    this.requestParamQuota.pageNumber = event.first;
    this.requestParamQuota.pageSize = event.rows;

    this.startRow = event.first + 1;
    this.rowSize = this.startRow + 9;

    this.stationeryService.countStationeryQuota(this.requestParamQuota).subscribe(res => {
      if (res) {
        this.total_record = res.data;
        if (this.rowSize > this.total_record) this.rowSize = this.total_record;
        if(this.total_record === 0) this.startRow = 0;
      }
    });
    this.stationeryService.findStationeryQuota(this.requestParamQuota).subscribe(res => {
      if (res) {
        this.dataResponseQuota = res.data;
      }
    });
  }

  settingParams() {
    if (this.quota !== null ) {
      this.requestParamQuota.quota = this.quota.replace(/[^0-9.]/g, '');
    } else {
      this.requestParamQuota.quota = null;
    }
    if (this.quantity !== null) {
      this.requestParamQuota.quantity = this.quantity.replace(/[^0-9.]/g, '');
    } else {
      this.requestParamQuota.quantity = null;
    }
    if (this.limitDate !== null) {
      this.requestParamQuota.dateLimit = this.limitDate;
    }

    this.listSelectedUnitDetailsId = [];
    if (this.selectedSectorDetails == null || this.selectedSectorDetails.length === 0) {
      this.listSelectedUnitDetailsId = [this.sectorsDetails[0].data];
    } else {
      this.selectedSectorDetails.forEach(element => {
        this.listSelectedUnitDetailsId.push(element.data);
      });
    }
    this.requestParamQuota.listStatus = this.listSelectedUnitDetailsId;
  }

  handleSearch() {
    this.settingParams();
    this.dataTableComponent.reset();
  }

  handleAdd() {
    this.limitDateUpdate = this.limitDate;
    this.resetButton();
    this.dialogUpdate = true;
    //this.settingInsert();
  }

  getLastItem(): any{
    this.listUnitId = [];
    this.listUnitId = this.getSelectedNodeData();
    if(this.listUnitId !== null && this.listUnitId.length != 0 ){
      return this.listUnitId.slice(-1)[0];
    }
  }

  getSelectedNodeData(): any[] {
    this.listSelectedUnitId = [];
    if (this.selectedSector === null ) {
      return this.listParentUnit;
    } else if (this.selectedSector != null) {
      for (const file of this.selectedSector) {
        this.listSelectedUnitId.push(file.data);
      }
    }
    return this.listSelectedUnitId.length > 0 ? this.listSelectedUnitId : null;
  }

  settingInsert() {
    if(this.selectedSector && this.selectedSector[0]) this.insertQuota.unitId = this.selectedSector[0].data;
    this.insertQuota.quota = parseFloat(this.quotaUpdate.replace(/[^0-9.]/g, ''));
    this.insertQuota.quantity = this.quantityUpdate.replace(/[^0-9.]/g, '');
  }

  eTotal(){
    this.total = parseFloat(this.quotaUpdate) * this.quantityUpdate;
  }

  addNew(){
    if(!this.selectedSector ||this.selectedSector == null||this.selectedSector.length==0  ) {
      this.app.showWarn('Chưa có đơn vị cần cấu hình');
      return;
    }
    this.quotaUpdate=this.inputQuotaAdd.nativeElement.value;
    this.quantityUpdate=this.inputQuantityAdd.nativeElement.value
    if(this.quotaUpdate === null || this.quotaUpdate === undefined ||this.quotaUpdate === '' ){
      this.app.showWarn('Định mức/người không được bỏ trống');
      this.inputQuotaAdd.nativeElement.focus();
      return;
    }
/*    if(!parseFloat(this.quotaUpdate) || parseFloat(this.quotaUpdate) === 0) {
      ValidateUtils.validateTextInput(this.createQuotaUpdateFld, 'Định mức phải lớn hơn 0', this.app);
      return;
    }*/
    if(this.quantityUpdate === null || this.quantityUpdate === undefined ||this.quantityUpdate === ''){
      this.app.showWarn('Quân số không được bỏ trống');
      this.inputQuantityAdd.nativeElement.focus();
      return;
    }
    if(!this.quantityUpdate || this.quantityUpdate === 0) {
      ValidateUtils.validateTextInput(this.inputQuantityAdd, 'Quân số phải lớn hơn 0', this.app);
      return;
    }

    this.settingInsert();
    this.stationeryService.insertQuota(this.insertQuota).subscribe(res => {
      if (res.status === 1) {
        this.app.showSuccess('Thêm định mức thành công');
        this.dialogUpdate = false;
        this.handleSearch();
      } else if (res.status === 2) {
        this.app.showWarn('Đơn vị này đã được cấu hình định mức');
      } else {
        this.app.showWarn('Thêm định mức thất bại');
      }
    });
  }

  doLimitDate() {
    this.stationeryService.getLimitDateEnd().subscribe(res => {
      if (res) {
        this.limitDate = res.data;
      }
    });
    this.dialogChangeDate = true;
  }

  closeLimitDate() {
    this.stationeryService.getLimitDateEnd().subscribe(res => {
      if (res) {
        this.limitDate = res.data;
      }
    });
    this.dialogChangeDate = false;
  }

  saveLimitDate() {
    if(this.limitDate > 31) this.limitDate = 31;
    this.updateLimitDateDTO.codeName = this.limitDate;
    this.stationeryService.updateLimitDate(this.updateLimitDateDTO).subscribe(res => {
      if (res) {
        this.app.showSuccess('Đã cập nhật hạn đăng ký VPP');
        this.dialogChangeDate = false;
        this.handleSearch();
      }
    });
  }

  cancel(){
    this.dialogUpdate = false;
  }

  doDelete(event){
    this.updateQuota.id = event.id;
    this.updateQuota.unitId = event.unitId;
    this.confirmationService.confirm({
      message: 'Đ/c có muốn xóa định mức VPP này không ?',
      header: 'Xóa định mức VPP',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Đồng ý',
      rejectLabel: 'Hủy bỏ',
      accept: () => {
        this.stationeryService.deleteQuota(this.updateQuota).subscribe(res => {
          if (res) {
            if(res.status === 1) {
              this.app.showSuccess('Xóa định mức VPP thành công');
            } else if(res.status === 4) {
              this.app.showWarn('Xóa định mức thất bại - Định mức không tồn tại');
            } else {
              this.app.showWarn('Xóa định mức thất bại');
            }
            this.handleSearch();
          }
        });
      }
    });
  }

  onChangeQuotaAdd() {
    if(+this.inputQuotaAdd.nativeElement.value <1){
      this.inputQuotaAdd.nativeElement.value = '';
    }
    if (/[^0-9.]/g.test(this.inputQuotaAdd.nativeElement.value)) {
      this.inputQuotaAdd.nativeElement.value = this.inputQuotaAdd.nativeElement.value.toString().replace(/[^0-9.]/g, '');
    }

    if (/^[0]+$/g.test(this.inputQuotaAdd.nativeElement.value)) {
      this.inputQuotaAdd.nativeElement.value = '';
    }

    if (/[.]+.0[0]+$/g.test(this.inputQuotaAdd.nativeElement.value)) {
      this.inputQuotaAdd.nativeElement.value = this.inputQuotaAdd.nativeElement.value.toString().replace(/[.]+.0[0]+$/g, '');
    }

    if(this.inputQuotaAdd.nativeElement.value.indexOf('.')!==this.inputQuotaAdd.nativeElement.value.lastIndexOf('.')&&this.inputQuotaAdd.nativeElement.value.indexOf('.')!==-1){
      this.inputQuotaAdd.nativeElement.value =  this.inputQuotaAdd.nativeElement.value.substring(0,  this.inputQuotaAdd.nativeElement.value.length - 1);
    }
    if (!/^(?!(0))*[1-9][0-9]{0,}$/g.test(this.inputQuotaAdd.nativeElement.value)) {
      this.inputQuotaAdd.nativeElement.value = this.inputQuotaAdd.nativeElement.value !== '0' ?
          this.inputQuotaAdd.nativeElement.value.toString().replace(/^0*{0.}/g, '') : '0';
    }
    if ((this.inputQuotaAdd.nativeElement.value.length > 1
        && this.inputQuotaAdd.nativeElement.value.charAt(this.inputQuotaAdd.nativeElement.value.length - 1) !== '.') &&
        (this.inputQuotaAdd.nativeElement.value
            .substring(this.inputQuotaAdd.nativeElement.value.length - 2, this.inputQuotaAdd.nativeElement.value.length) !== '.0')) {
      this.inputQuotaAdd.nativeElement.value =
          parseFloat(this.inputQuotaAdd.nativeElement.value)
              .toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});
    } else {
      this.inputQuotaAdd.nativeElement.value = this.inputQuotaAdd.nativeElement.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
    //const updatingQuota: number = parseFloat(this.quotaUpdate);
    this.inputQuota.nativeElement.value = parseFloat(Math.floor(this.inputQuota.nativeElement.value * 100) / 100 + '')
        .toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});

    this.quotaUpdate = this.inputQuotaAdd.nativeElement.value;

    if(this.quotaUpdate== null ||this.quotaUpdate== undefined ||this.quotaUpdate== '' ||this.quantityUpdate== null ||this.quantityUpdate== undefined ||this.quantityUpdate== ''  ){
      this.total = 0;
    }else{
      this.total = (parseFloat(this.quotaUpdate.replace(/[^0-9.]/g, '')) * this.quantityUpdate.replace(/[^0-9.]/g, '')).toFixed( 2 );
    }


    if ((this.total.length > 1 && this.total.charAt(this.total.length - 1) !== '.') &&
        (this.total
            .substring(this.total.length - 2, this.total.length) !== '.0')) {
      this.total = parseFloat(Math.floor(this.total * 100) / 100 + '')
          .toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});
    } else {
      this.total = this.total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
  }

  onChangeQuantityAdd(){
    if(+this.inputQuantityAdd.nativeElement.value <1){
      this.inputQuantityAdd.nativeElement.value = '';
    }
    if (/[^0-9]/g.test(this.inputQuantityAdd.nativeElement.value)) {
      this.inputQuantityAdd.nativeElement.value = this.inputQuantityAdd.nativeElement.value.toString().replace(/[^0-9]/g, '');
    }
    if ((this.inputQuantityAdd.nativeElement.value.length > 1
        && this.inputQuantityAdd.nativeElement.value.charAt(this.inputQuantityAdd.nativeElement.value.length - 1) !== '.') &&
        (this.inputQuantityAdd.nativeElement.value
            .substring(this.inputQuantityAdd.nativeElement.value.length - 2, this.inputQuantityAdd.nativeElement.value.length) !== '.0')) {
      this.inputQuantityAdd.nativeElement.value =
          parseFloat(this.inputQuantityAdd.nativeElement.value).toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});
    } else {
      this.inputQuantityAdd.nativeElement.value = this.inputQuantityAdd.nativeElement.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
    this.quantityUpdate = this.inputQuantityAdd.nativeElement.value;

    if(this.quotaUpdate== null ||this.quotaUpdate== undefined ||this.quotaUpdate== '' ||this.quantityUpdate== null ||this.quantityUpdate== undefined ||this.quantityUpdate== ''  ){
      this.total = 0;
    }else{
      this.total = (parseFloat(this.quotaUpdate.replace(/[^0-9.]/g, '')) * this.quantityUpdate.replace(/[^0-9.]/g, '')).toFixed( 2 );
    }
    if ((this.total.length > 1 && this.total.charAt(this.total.length - 1) !== '.') &&
        (this.total
            .substring(this.total.length - 2, this.total.length) !== '.0')) {
      this.total = parseFloat(Math.floor(this.total * 100) / 100 + '')
          .toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});
    } else {
      this.total = this.total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
  }

  onHideCreateQuota() {
    this.total = 0;
  }

  onChangeQuotaEdit() {
    if(+this.inputquotaEdit.nativeElement.value <1){
      this.inputquotaEdit.nativeElement.value = '';
    }
    if (/[^0-9.]/g.test(this.inputquotaEdit.nativeElement.value)) {
      this.inputquotaEdit.nativeElement.value = this.inputquotaEdit.nativeElement.value.toString().replace(/[^0-9.]/g, '');
    }

    if (/^[0]+$/g.test(this.inputquotaEdit.nativeElement.value)) {
      this.inputquotaEdit.nativeElement.value = '0';
    }

    if (/[.]+.0[0]+$/g.test(this.inputquotaEdit.nativeElement.value)) {
      this.inputquotaEdit.nativeElement.value = this.inputquotaEdit.nativeElement.value.toString().replace(/[.]+.0[0]+$/g, '');
    }
    if(this.inputQuotaAdd.nativeElement.value.indexOf('.')!==this.inputQuotaAdd.nativeElement.value.lastIndexOf('.')&&this.inputQuotaAdd.nativeElement.value.indexOf('.')!==-1){
      this.inputQuotaAdd.nativeElement.value =  this.inputQuotaAdd.nativeElement.value.substring(0,  this.inputQuotaAdd.nativeElement.value.length - 1);
    }
    // remove dot of double number if duplicate
   /* if (this.inputquota.nativeElement.value.replace(/[^.]/g, '').length > 1) {
      this.inputquota.nativeElement.value =
          this.removeDuplicateRegexMatchesExceptFirst(this.inputquota.nativeElement.value.toString(), /[.]/g);
    }*/

    //  remove all first 0 if number digit > 1
    if (!/^(?!(0))*[1-9][0-9]{0,}$/g.test(this.inputquotaEdit.nativeElement.value)) {
      this.inputquotaEdit.nativeElement.value = this.inputquotaEdit.nativeElement.value !== '0' ?
          this.inputquotaEdit.nativeElement.value.toString().replace(/^0*{0.}/g, '') : '0';
    }
    if ((this.inputquotaEdit.nativeElement.value.length > 1
        && this.inputquotaEdit.nativeElement.value.charAt(this.inputquotaEdit.nativeElement.value.length - 1) !== '.') &&
        (this.inputquotaEdit.nativeElement.value
            .substring(this.inputquotaEdit.nativeElement.value.length - 2, this.inputquotaEdit.nativeElement.value.length) !== '.0')) {
      this.inputquotaEdit.nativeElement.value =
          parseFloat(Math.floor(this.inputquotaEdit.nativeElement.value * 100) / 100 + '')
              .toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});
    } else {
      this.inputquotaEdit.nativeElement.value = this.inputquotaEdit.nativeElement.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
    const updatingQuota: number = parseFloat(this.quotaUpdate);
    this.inputQuota.nativeElement.value = parseFloat(Math.floor(this.inputQuota.nativeElement.value * 100) / 100 + '')
         .toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});

    this.quotaUpdate = this.inputquotaEdit.nativeElement.value;

    if(this.quotaUpdate== null ||this.quotaUpdate== undefined ||this.quotaUpdate== '' ||this.quantityUpdate== null ||this.quantityUpdate== undefined ||this.quantityUpdate== ''  ){
      this.totalEdit = 0;
    }else{
      this.totalEdit = (parseFloat(this.quotaUpdate.replace(/[^0-9.]/g, '')) * this.quantityUpdate.replace(/[^0-9.]/g, '')).toFixed(2);
    }

    if ((this.totalEdit.length > 1 && this.totalEdit.charAt(this.totalEdit.length - 1) !== '.') &&
        (this.totalEdit
            .substring(this.totalEdit.length - 2, this.totalEdit.length) !== '.0')) {
      this.totalEdit =
          parseFloat(Math.floor(this.totalEdit * 100) / 100 + '')
              .toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});
    } else {
      this.totalEdit = this.totalEdit.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }

  }

  onChangeQuantityEdit(){
    if(+this.inputQuantityFldEdit.nativeElement.value <1){
      this.inputQuantityFldEdit.nativeElement.value = '';
    }
    if (/[^0-9]/g.test(this.inputQuantityFldEdit.nativeElement.value)) {
      this.inputQuantityFldEdit.nativeElement.value = this.inputQuantityFldEdit.nativeElement.value.toString().replace(/[^0-9]/g, '');
    }
    if ((this.inputQuantityFldEdit.nativeElement.value.length > 1
        && this.inputQuantityFldEdit.nativeElement.value.charAt(this.inputQuantityFldEdit.nativeElement.value.length - 1) !== '.') &&
        (this.inputQuantityFldEdit.nativeElement.value
            .substring(this.inputQuantityFldEdit.nativeElement.value.length - 2, this.inputQuantityFldEdit.nativeElement.value.length) !== '.0')) {
      this.inputQuantityFldEdit.nativeElement.value =
          parseFloat(Math.floor(this.inputQuantityFldEdit.nativeElement.value * 100) / 100 + '')
              .toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});
    } else {
      this.inputQuantityFldEdit.nativeElement.value = this.inputQuantityFldEdit.nativeElement.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
    this.quantityUpdate = this.inputQuantityFldEdit.nativeElement.value;

    if(this.quotaUpdate== null ||this.quotaUpdate== undefined ||this.quotaUpdate== '' ||this.quantityUpdate== null ||this.quantityUpdate== undefined ||this.quantityUpdate== ''  ){
      this.totalEdit = 0;
    }else{
      this.totalEdit = (parseFloat(this.quotaUpdate.replace(/[^0-9.]/g, '')) * this.quantityUpdate.replace(/[^0-9.]/g, '')).toFixed(2);
    }

    if ((this.totalEdit.length > 1 && this.totalEdit.charAt(this.totalEdit.length - 1) !== '.') &&
        (this.totalEdit
            .substring(this.totalEdit.length - 2, this.totalEdit.length) !== '.0')) {
      this.totalEdit =
          parseFloat(Math.floor(this.totalEdit * 100) / 100 + '')
              .toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});
    } else {
      this.totalEdit = this.totalEdit.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
  }

  doEdit(event) {
    this.dialogEdit = true;
    this.onGetTreeToCheck(event.path);
    this._KitchenManagementService.buildCompleteTree(null)
      .subscribe(res => {
        this.sectorUpdates = res.data;
        this.findNode(this.sectorUpdates[0], event.unitId);
        this.setExpand(this.sectorUpdates[0], this.selectedNode.parent);
        this.selectedSectorUpdate = [this.selectedNode];
      });
    this.totalEdit = event.total;
    this.quotaUpdate = this.changePrice(event.quota);
    this.quantityUpdate = event.quantity.toString();
    this.updateQuota.id = event.id;
    this.limitDateUpdate = event.limitDate;
    this.updateQuota.unitId = event.unitId;
  }

  addEdit(){
    if(this.quotaUpdate === null || this.quotaUpdate === undefined ||this.quotaUpdate==='' ){
      this.app.showWarn('Định mức/người không được bỏ trống');
      this.inputquotaEdit.nativeElement.focus();
      return;
    }
    if(!parseFloat(this.quotaUpdate) || parseFloat(this.quotaUpdate) === 0) {
      ValidateUtils.validateTextInput(this.inputQuota, 'Định mức phải lớn hơn 0', this.app);
      return;
    }
    if(this.quantityUpdate === null || this.quantityUpdate === undefined ||this.quantityUpdate==='' ){
      this.app.showWarn('Quân số không được bỏ trống');
      this.inputQuantityFldEdit.nativeElement.focus();
      return;
    }

    this.updateQuota.quota = parseFloat(this.quotaUpdate.replace(/[^0-9.]/g, ''));
    this.updateQuota.quantity = this.quantityUpdate.replace(/[^0-9.]/g, '');

    this.stationeryService.updateQuota(this.updateQuota).subscribe(res => {
      if(res.status ===4 ){
        this.app.showWarn('Sửa định mức thất bại - Định mức không tồn tại');
        this.dialogEdit = false;
      }else if (res) {
        this.app.showSuccess('Đã cập nhật định mức VPP');
        this.dialogEdit = false;
      } else {
        this.app.showWarn('Cập nhật hạn đăng kí VPP thất bại');
      }
      this.handleSearch();
    });
  }

  editCancel(){
    this.dialogEdit = false;
  }

  onGetTreeToCheck(path: string) {
    let vtnetUnit: string = path.split("234841")[1];
    let unitIds = vtnetUnit.split("/");
    this._KitchenManagementService.getSectors(null).subscribe(data => {
      this.editSectors = data;

      this._KitchenManagementService.getSectors(unitIds[1]).subscribe(data => {
        this.editSectors[0].children = data;
        this.editSectors[0].leaf = true;
      });
    });
  }

  resetButton() {
    this.selectedSector = null;
    this.quotaUpdate = null;
    this.quantityUpdate = null;
  }
  changePrice(basePrice: number) {
    this.numberStr = basePrice.toFixed(2);
    this.fixNumberQuotaHCVP = this.numberStr.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return this.fixNumberQuotaHCVP;
  }
}
