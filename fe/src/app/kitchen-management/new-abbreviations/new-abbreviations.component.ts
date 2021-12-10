import {Component, ElementRef, OnDestroy, OnInit, Renderer2, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {NavigationEnd, Router} from '@angular/router';
import {TokenStorage} from '../../shared/token.storage';
import {ConfirmationService, MessageService, TreeNode} from 'primeng/api';
import {ReportserviceService} from '../../check-in-out/reportservice.service';
import {KitchenManagementService} from '../kitchen-management.service';
import {AppComponent} from '../../app.component';
import {Title} from '@angular/platform-browser';
import {UserInfo} from '../../shared/UserInfo';
import {Condition} from '../../check-in-out/Entity/ConditionSearch';
import {CommonUtils} from '../../../common/commonUtils';
import {DateTimeUtil} from '../../../common/datetime';
import {AbbreviationsInsert} from "../Entity/AbbreviationsInsert";
import {SelectingKitchenUnit} from "../Entity/Kitchen";
import {Dropdown, InputText} from "primeng/primeng";

@Component({
  selector: 'app-new-abbreviations',
  templateUrl: './new-abbreviations.component.html',
  styleUrls: ['./new-abbreviations.component.css']
})
export class NewAbbreviationsComponent implements OnInit , OnDestroy {
  @ViewChild('inputTree') private inputTree: ElementRef;
  @ViewChild('inputKitchenName') private inputKitchenName: ElementRef;
  @ViewChild('inputAbbreviations') private inputAbbreviations: ElementRef;
  @ViewChild('inputAbbreviationsText') private inputAbbreviationsText: ElementRef;
  @ViewChild('kitchenFld') kitchenFld: Dropdown;
  navigationSubscription;
  condition: Condition;
  isClick: boolean;
  userInfo: UserInfo;
  len: number;
  maxLenStatus: number;
  listParentUnit: string[];
  listSelectedUnitId: any[] = [];
  listUnitId: any[] = [];
  fromDate: Date;
  toDate: Date;
  vn = DateTimeUtil.vn;
  status = CommonUtils.STATUS;
  unitId: number;
  unitShorteningList = new Array();
  kitchenNameList = new Array();
  filterEmployee: any[];
  sectors: TreeNode[];
  selectedStatus: any[] = [];
  selectedSector: TreeNode[];
  empInfor: any;
  startRow: number;
  rowSize: number;
  totalRecord: number;
  isChecked: boolean;
  imageToShow: any;
  isEmpty: boolean;
  loading: boolean;
  loading2: boolean;
  loadingTree = false;
  convertStatus: string[];
  isShow: boolean;
  isShowManager: boolean;
  isManager: boolean;
  isEmployee: boolean;
  isShowImg: boolean;
  isDisplay: boolean;
  textShow: string;
  shortName: SelectingKitchenUnit;
  shortNameText : string;
  checked : number;

  formSearch: FormGroup;
  abbreviationsInsert: AbbreviationsInsert = {
    kitchenId: '', note: '', unitId: null, shortName: '', createUser: '',
    createDate: '', updateUser: '', updateDate: '',
  };

  resultList: any = [];
  isDialogAddNew: boolean;
  isShowDropdown: boolean;
  isShowTextBox: boolean;
  isCheck : boolean;

  onlyCharEmail: RegExp = /^[0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ]+$/;
  onlyNum: RegExp = /^[0-9]+$/;

  constructor( private formBuilder: FormBuilder , private _KitchenManagementService: KitchenManagementService,
               private confirmationService: ConfirmationService , private messageService: MessageService, private  app: AppComponent ,
               private tokenStorage: TokenStorage, private title: Title, private renderer: Renderer2, private router: Router) {
    this.navigationSubscription = this.router.events.subscribe(event => {
      if (!(event instanceof NavigationEnd)) { return; }
      // Do what you need to do here, for instance :
      this.resetButton();
      if (this.isClick) {
        this.ngOnInit();
      }
    });
  }

  ngOnInit() {
    this.isShowDropdown = true;
    this.isShowTextBox = false;
    this.buildForm();
    this.userInfo = this.tokenStorage.getUserInfo();
    this.len = 50;
    this.isDisplay = false;
    this.startRow = -1;
    this.rowSize = 1;
    this.totalRecord = 0;
    this.textShow = 'Chọn tất cả';
    this.imageToShow = '';
    this.maxLenStatus = 431;
    this.checkRole();
    this.isCheck = true;
    this.isShowTextBox = true;
    this.isShowDropdown = false;
  }

  private   buildForm(): void {
    this.formSearch = this.formBuilder.group({
      kitchenId : [''],
      note : [''],
      unitId   : [null],
      shortName : [''],
      createUser : [''],
      createDate : [''],
      updateUser : [''],
      updateDate : [''],
      shortNameText : ['']
    });
  }

//  check role user
  checkRole() {
    this.isShow = false;
    this.isShowManager = false;
    this.isShowImg = false;
    this.isEmployee = false;
    this.isManager = false;

    if (this.userInfo.role.includes('PMQT_Canhve')) {
      this.isShow = true;
      this.isShowManager = true;
      this.isShowImg = true;
      this.selectedStatus = ['1', '3'];

      this.getSector(0);
    } else if (this.userInfo.role.includes('PMQT_ADMIN')|| this.userInfo.role.includes('PMQT_Bep_truong')) {
      this.isShow = true;
      this.isShowManager = true;
      this.getSector(0);
    } else if (this.userInfo.role.includes('PMQT_QL') || this.userInfo.role.includes('PMQT_CVP')) {
      this.isShow = true;
      this.isShowManager = true;
      this.isManager = true;
      this.getSector(1);
    } else {
      this.empInfor = {
        result: this.userInfo.fullName,
        userName: this.userInfo.userName
      };
      this.isEmployee = true;
      this.getSector(2);
    }
    if(this.userInfo.role.includes('PMQT_Bep_truong') && !this.userInfo.role.includes('PMQT_ADMIN')) {
      this._KitchenManagementService.findKitchenNameByUser(this.userInfo.userName).subscribe(res => {
        this.kitchenNameList = res.data;
      });
    } else if(this.userInfo.role.includes('PMQT_ADMIN')){
      this._KitchenManagementService.findAllKitchenName().subscribe(res => {
        this.kitchenNameList = res.data;
      });
    }
  }
  changeType(isChecked: boolean) {
    if (isChecked) {
      this.isShowTextBox = true;
      this.isShowDropdown = false;
    } else {
      this.isShowDropdown = true;
      this.isShowTextBox = false;
    }
  }


  //  get list unit
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

  //  expand tree
  nodeExpand(event) {
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

  nodeSelect(event) {
    this.selectedSector = [];
    this.selectedSector.push(event.node);
    this._KitchenManagementService.findKitchenNameUpdate(event.node.data).subscribe(res => {
      this.unitShorteningList = res.data;
    });
  }

  //  get data of node selected
  getSelectedNodeData(): any[] {
    this.listSelectedUnitId = [];
    if ((this.isManager || !this.isEmployee) && (this.selectedSector === null || this.selectedSector.length === 0)) {
      return this.listParentUnit;
    } else if (this.isEmployee) {
      this.listSelectedUnitId.push(this.userInfo.unitId);
    } else if (this.selectedSector != null) {
      for (const file of this.selectedSector) {
        this.listSelectedUnitId.push(file.data);
      }
    }
    return this.listSelectedUnitId.length > 0 ? this.listSelectedUnitId : null;
  }
//  when click 'Lam moi' button
  resetButton() {
    this.selectedStatus = null;
    this.fromDate = new Date();
    this.toDate = new Date();
    this.selectedSector = null;
    this.empInfor = '';
  }

  validateForm(formSearch: FormGroup): boolean {
    if (this.formSearch.value.kitchenId == '') {
      this.app.errorValidateDate('Bếp không được để trống');
      this.kitchenFld.focus();
      // this.inputKitchenName.nativeElement.focus();
      return false;
    }

    if(this.isCheck){
      if (this.formSearch.value.shortNameText == '' ) {
        this.app.errorValidateDate('Tên viết tắt không được để trống');
        this.inputAbbreviationsText.nativeElement.focus();
        return false;
      }
    } else {
      if (this.formSearch.value.shortName == '' ) {
        this.app.errorValidateDate('Tên viết tắt không được để trống');
        // this.inputAbbreviationsText.nativeElement.focus();
        return false;
      }
    }

    return true;
  }

  addNew(){
    this.settingInsert();
    if(this.abbreviationsInsert.unitId == 234841) {
      this.app.errorValidateDate('Tên đơn vị phải được chọn');
      // this.inputTree.nativeElement.focus();
      return ;
    }
    this.isDialogAddNew = true;
    if(!this.validateForm(this.formSearch)){
      return;
    }

    this.confirmationService.confirm({
      message: 'Đ/c chắc chắn muốn thêm không?',
      header: 'Thêm tên viết tắt',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Đồng ý',
      rejectLabel: 'Hủy bỏ',
      accept: () => {
        this.addNewHandle();
      }
    });
  }

  addNewHandle() {
    this._KitchenManagementService.abbreviationsInsert(this.abbreviationsInsert).subscribe(res => {
      if(res.status === 1 ) {
        this.router.navigate(['/kitchenManager/abbreviations-config']);
        this.isDialogAddNew = false;
        this.app.showSuccess('Thêm tên viết tắt đơn vị thành công');
      } else if(res.status === 5) {
        this.app.showWarn('Một đơn vị ứng với một bếp chỉ có một tên viết tắt');
      } else if(res.status === 8){
        this.app.showWarn('Bếp hiện tại đã không hoạt động');
      } else if(res.status === 2) {
        this.app.showWarn('Tên viết tắt này đã tồn tại');
      } else {
        this.app.showWarn('Gửi thất bại');
      }
    });
  }
  getLastItem(): any{
    this.listUnitId = [];
    this.listUnitId = this.getSelectedNodeData();
    return this.listUnitId.slice(-1)[0];
  }

  settingInsert() {
    if(this.isCheck){
      this.abbreviationsInsert.shortName = this.formSearch.value.shortNameText;
    } else {
      this.abbreviationsInsert.shortName = this.formSearch.value.shortName.shortName;
    }
    this.abbreviationsInsert.kitchenId = this.formSearch.value.kitchenId.kitchenID;
    this.abbreviationsInsert.note = this.formSearch.value.note;
    this.abbreviationsInsert.createUser = this.userInfo.userName;
    this.abbreviationsInsert.unitId = this.getLastItem();
  }
  return(){
    this.router.navigate(['/kitchenManager/abbreviations-config']);
  }


  ngOnDestroy() {
    // avoid memory leaks here by cleaning up after ourselves. If we
    // don't then we will continue to run our initialiseInvites()
    // method on every navigationEnd event.
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }
}
