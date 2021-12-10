import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {DateTimeUtil} from '../../../common/datetime';
import {KitchenConfig} from '../Entity/KitchenConfig';
import {Kitchen} from '../Entity/Kitchen';
import {KitchenManagementService} from '../kitchen-management.service';
import {MenuSettingConfig} from '../Entity/MenuSettingConfig';
import {MenuSetting, MenuItem, MenuSettingToInsert} from '../Entity/MenuSetting';
import {AppComponent} from '../../app.component';
import {DatePipe} from '@angular/common';
import {UserInfo} from '../../shared/UserInfo';
import {TokenStorage} from '../../shared/token.storage';
import {Table} from 'primeng/table';
import { Calendar } from 'primeng/primeng';
import {ConfirmationService, MessageService} from 'primeng/api';
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-menu-config',
  templateUrl: './menu-config.component.html',
  styleUrls: ['./menu-config.component.css']
})
export class MenuConfigComponent implements OnInit {
  @ViewChild('dt')
  @ViewChild('inputSettingDate') private inputSettingDate: ElementRef;
  @ViewChild('transferDateFromF') transferDateFromF: Calendar;
  @ViewChild('dt') dataTableComponent: Table;
  vn = DateTimeUtil.vn;
  searchingKitchen: KitchenConfig;
  searchingMenuConfig: MenuSettingConfig;
  menuSettings: MenuSetting[];
  menuSettingsCheck: any[];
  kitchens: Kitchen[];
  kitchen : Kitchen;
  selectedKitchen: Kitchen;
  chefNameFld: String;
  dateFrom: Date;
  dateTo: Date;
  isManager : boolean;
  isDisableBtn: boolean;
  isEditNew: boolean;
  isEdit: boolean;
  isCopy: boolean;
  isNew: boolean;
  isEditOld: boolean;
  showDlgImage: boolean;
  isCopyOld: boolean;
  menuItems: MenuItem[];
  selectedMenuItems: MenuItem[];
  selectedEditMenuItem: MenuSetting;
  selectedMenuItem: MenuItem;
  settingDate: Date;
  isShow : boolean;
  isShowDropManager : boolean;
  startRow: number;
  rowSize = 0;
  minDate: Date = new Date();
  tomorrow: Date ;
  total_record = 0;
  isEmployee: boolean;
  userInfo: UserInfo;
  dateOfMenuOld: any;
  imageToShow: any;
  imageData: any;

  isAdmin: boolean;
  isNhanVien: boolean;
  editedDate: Date;
  showImageLabel: string;

  constructor(private kitchenManagementService: KitchenManagementService, private app: AppComponent,
              private tokenStorage: TokenStorage , private messageService: MessageService,
              private confirmationService: ConfirmationService, private title: Title) {

  }

  ngOnInit() {
    this.title.setTitle('Danh sách menu _ PMQTVP');
    this.settingDate = new Date();
    this.dateFrom = new Date();
    let dd = this.dateFrom.getDate();
    let mm = this.dateFrom.getMonth() + 2; //January is 0!
    let yyyy = this.dateFrom.getFullYear();
    this.tomorrow = new Date(this.minDate.getTime() + 1000 * 60 * 60 * 24);
    if (dd < 10) {
     var dd2 =  '0' + dd;
    } else {
      var dd2 =  '' + dd;
    }
    if (mm < 10) {
     var mm2 = '0' +  mm;
    } else {
      var mm2 = '' +  mm;
    }
    var today = mm2 + '/' +  dd2 + '/' + yyyy;

    this.dateTo = new  Date(today);
    this.selectedKitchen = {
      kitchenID: '',
    kitchenName: '',
    placeID: null,
    placeName: '',
    chefName: '',
    note: '',
    price: null,
    chefUserName: '',
    chefPhone: '',
    isEmployee: '',
    status: null,
    listPhoneNumberReceiveSms : null,
      check : null
    };

    this.searchingMenuConfig = {
      chefID: null,
      pageNumber: 1,
      pageSize: 7,
      kitchenID: null,
      dateFrom: null,
      dateTo: null
    };
    this.isDisableBtn = false;
    this.isNew = false;
    this.userInfo = this.tokenStorage.getUserInfo();
    this.isManager = false;
    this.startRow = 0;
    this.rowSize = 0;
    this.total_record = 0;

    this.selectedMenuItems = [];
    this.selectedEditMenuItem = new MenuSetting();
    // check Role
    if (this.tokenStorage.checkRole('PMQT_ADMIN')) {
      this.isEmployee = true;
      this.isShow = true;
      this.isShowDropManager = false;
      this.initAdmin();
    } else  if( this.tokenStorage.checkRole('PMQT_Bep_truong')) {
      this.isEmployee = true;
       this.isShowDropManager = true;
       this.initAdmin();
    } else {
      this.isEmployee = false;
      this.isShowDropManager = false;
      this.isShow = true;
      this.initEmployee();
    }

    this.minDate = new Date();

    let component = this;
    let callback = () : void => {
      component.getAllKitchen();
    }
    this.tokenStorage.getSecurityAccount(callback);
  }

  initAdmin() {
    this.isAdmin = true;
  }

  initEmployee() {
    this.isNhanVien = true;
  }

  clearDateOfMenu(event) {
    if (this.selectedEditMenuItem.dateOfMenu) {
      event.stopPropagation();
      this.selectedEditMenuItem.dateOfMenu = null;
    }
  }

  onInsertMenu() {
    if (!this.settingDate) {
      this.app.showWarn('Đ/c chưa chọn ngày');
      return;
    }
    let checkDish =0 ;
    const date = new DatePipe('en-US').transform(this.settingDate, 'yyyy-MM-dd');
    const menuSetting: MenuSettingToInsert = {
      dateOfMenuOld: null, dateOfMenu: new Date(this.settingDate), listDish: this.selectedMenuItems,
      chefID: this.selectedKitchen.chefUserName, kitchenID: this.selectedKitchen.kitchenID, copyFlag: 0
    };

    if (menuSetting.listDish.length == 0  ) {
      this.app.showWarn('Đ/c chưa chon món');
      return;
    }

    for(let i =0 ; i < menuSetting.listDish.length ; i++) {
      if(menuSetting.listDish[i].dishID == null){
      checkDish++;
      }
    }
    if(checkDish == menuSetting.listDish.length){
      this.app.showWarn('Đ/c chưa chon món');
      return;
    }else {
      for (let i = menuSetting.listDish.length - 1; i >= 0; i--) {
        if (menuSetting.listDish[i].dishID == null) {
          menuSetting.listDish.splice(i, 1);
        }
      }
    }

    for (let i = 0; i < menuSetting.listDish.length; i++) {
      for(let j =0 ; j < menuSetting.listDish.length; j++){
        if(menuSetting.listDish[i].dishID === menuSetting.listDish[j].dishID && i!=j){
          this.app.showWarn('Món đã bị trùng');
          return;
        }
      }
    }

    if (menuSetting.listDish.length == 0  ) {
      this.app.showWarn('Đ/c chưa chon món');
      return;
    }

    this.kitchenManagementService.checkMenuExistResult(menuSetting).subscribe(response => {
      this.menuSettingsCheck = response.data;
      var date = new DatePipe('en-US').transform(this.settingDate, 'dd-MM-yyyy');
      if (this.menuSettingsCheck.length != 0) this.app.showWarn('Đã tồn tại menu của ngày :' +  date);
      else this.insertHandle(menuSetting);
    });
  }

  insertHandle(menuSetting) {
    var date = new DatePipe('en-US').transform(this.settingDate, 'dd-MM-yyyy');
    this.kitchenManagementService.insertMenu(menuSetting).subscribe(response => {

      if (response.status == 1) {

        this.isNew = false;
        this.app.showSuccess('Đã thiết lập menu của  ngày ' + date);
        this.selectedMenuItems = null;
        this.settingDate = null;
      }else if (response.status == 6) {

        this.isNew = false;
        this.app.showSuccess('Đã cập nhật menu của  ngày ' + date);
        this.selectedMenuItems = null;
        this.settingDate = null;
      }
      else {
        this.app.showWarn('Thiết lập menu của ngày ' + date + ' không thành công');
      }
      this.onSearchListMenu();
    });
  }

  onChangeKitchen(event) {
    this.chefNameFld = event.value.chefName;
  }

  onSelectMenu(menuItem: MenuItem, index: number) {
    for (let i = 0; i < this.selectedMenuItems.length; i++) {
      if (menuItem.dishID === this.selectedMenuItems[i].dishID) {
        this.app.showWarn('Món đã chọn bị trùng');
        this.selectedMenuItems[index].dishID = menuItem.dishID;
        return;
      }
    }
    this.selectedMenuItems[index].dishID = menuItem.dishID;
    this.selectedMenuItems[index].dishName = menuItem.dishName;
    this.selectedMenuItems[index].image = menuItem.image;
}

  onRemoveMenu(index: number) {
    this.selectedMenuItems.splice(index, 1);
  }

  clone(obj) {
    if (null == obj || "object" != typeof obj) return obj;
    var copy = obj.constructor();
    for (var attr in obj) {
      if (obj.hasOwnProperty(attr)) copy[attr] = obj[attr];
    }
    return copy;
  }

  onHideEditDialog() {
    this.editedDate = null;
  }

  onEditMenu(menu: MenuSetting) {
    this.editedDate = JSON.parse(JSON.stringify(menu.dateOfMenu));
    const searchingConfig = { chefID: null, status: 1, deleteFG: 0, dishName: null, kitchenID: this.selectedKitchen.kitchenID };

    this.dateOfMenuOld = menu.dateOfMenu;
    this.selectedEditMenuItem = this.clone(menu);
    this.selectedEditMenuItem.listDish = this.clone(menu.listDish);
    this.selectedEditMenuItem.dateOfMenu = new Date(this.selectedEditMenuItem.dateOfMenu);

    let today = new Date();
    today.setHours(0, 0, 0, 0);
    today.setTime(today.getTime() + 1000 * 24 * 60 * 60);
    menu.dateOfMenu = new Date(menu.dateOfMenu);
    menu.dateOfMenu.setHours(0, 0, 0, 0);

    if (menu.dateOfMenu >= today && this.isEmployee ) {
      this.isEdit = true;
      this.isCopy = false;

      this.kitchenManagementService.getListDishByKitchen(searchingConfig).subscribe(response => {
        this.menuItems = response.data;

        if(this.menuItems && this.menuItems.length > 0) {
          let message: string = null;
          for(let i = 0; i < this.selectedEditMenuItem.listDish.length; i++) {
            let isDeleted: boolean = true;
            for(let j = 0; j < this.menuItems.length; j++) {
              if(this.selectedEditMenuItem.listDish[i].dishID === this.menuItems[j].dishID) isDeleted = false;
            }
            if(isDeleted) {
              if(!message) message = 'Các món không còn hoạt động: ' + this.selectedEditMenuItem.listDish[i].dishName;
              else message = message + ', ' + this.selectedEditMenuItem.listDish[i].dishName;
              this.selectedEditMenuItem.listDish.splice(i, 1);
            }
          }
          if(message)this.app.showWarn(message);
        } else if(this.selectedEditMenuItem.listDish && this.selectedEditMenuItem.listDish.length > 0){
          let message: string = null;
          for(let i = 0; i < this.selectedEditMenuItem.listDish.length; i++) {
            if(!message) message = 'Các món không còn hoạt động: ' + this.selectedEditMenuItem.listDish[i].dishName;
            else message = message + ', ' + this.selectedEditMenuItem.listDish[i].dishName;
          }
          if(message)this.app.showWarn(message);
        }
      });

    } else if(menu.dateOfMenu < today && this.isEmployee ){
      this.menuItems = menu.listDish;
      this.isCopyOld = true;
      this.isEdit = false;
    } else {
      this.menuItems = menu.listDish;
      this.isCopyOld = false;
      this.isEdit = false;
    }
  }

  onAddMenu() {
    if (!this.selectedMenuItems) { this.selectedMenuItems = []; }
    const item: MenuItem = {dishID: null, dishName: null};
    this.selectedMenuItems.push(item);
  }

  onRemoveMenuUpdate(index: number) {
    this.selectedEditMenuItem.listDish.splice(index, 1);
  }

  onAddMenuUpdate() {
    if (!this.selectedEditMenuItem.listDish) { this.selectedEditMenuItem.listDish = []; }
    const item: MenuItem = {dishID: null, dishName: null};
    this.selectedEditMenuItem.listDish.push(item);
  }

  onDisplayForm() {
    this.settingParams();
    const searchingConfig = {
      chefID: null,
      status: 1,
      deleteFG: 0,
      dishName: null,
      kitchenID: this.selectedKitchen.kitchenID
    };
    let currentTime = new Date();
    let ddcheck =  currentTime.getDate()+1;

    let dd = currentTime.getDate() + 1;
    let mm = currentTime.getMonth() + 1; //January is 0!
    let yyyy = currentTime.getFullYear();
    if (dd < 10) {
      var dd2 =  '0' + dd ;
    } else {
      var dd2 = '' + dd;
    }
    if (mm < 10) {
      var mm2 = '0' +  mm;
    } else {
      var mm2 = '' + mm;
    }if(ddcheck < 10){
      var dd3 =  '0' + ddcheck ;
    }else {
      var dd3 = '' + ddcheck;
    }
    var oldDay = mm2 + '/' +  dd3 + '/' + yyyy;
    var today = mm2 + '/' +  dd2 + '/' + yyyy;
    this.settingDate = new  Date(today);
    this.minDate = new Date(oldDay);
    if(this.tokenStorage.checkRole('PMQT_Bep_truong')){
      this.selectedKitchen.kitchenID = this.searchingMenuConfig.kitchenID;
    }
    if (this.selectedKitchen.kitchenID == null || this.selectedKitchen.kitchenID == '' ) {
      this.app.showWarn('Đ/c chưa chọn bếp');
      return;
    }
    this.selectedMenuItems = [];
    this.getListDishByKitchen(searchingConfig);
  }

  getListDishByKitchen(searchingConfig: object) {
    this.kitchenManagementService.getListDishByKitchen(searchingConfig).subscribe(response => {
      this.isNew = true;
      this.menuItems = response.data;
    });
  }

  getAllKitchen() {
    if (this.tokenStorage.checkRole('PMQT_Bep_truong') && !this.tokenStorage.checkRole('PMQT_ADMIN')) {
      this.chefNameFld = this.userInfo.fullName;
      this.searchingKitchen = {kitchenName: null, status: 1, deleteFG: 0, chefUserName: this.userInfo.userName};
      this.kitchenManagementService.getKitchenByChef(this.searchingKitchen).subscribe(response => {
        this.kitchens = response.data;
        if(this.kitchens) {
          if(this.kitchens.length == 1) {
            this.searchingMenuConfig.kitchenID = this.kitchens[0].kitchenID;
            this.isShowDropManager = true;
            this.isNew = false;
          } else {
            this.isShowDropManager = false;
            this.isShow = true;
            this.searchingMenuConfig.kitchenID = this.selectedKitchen.kitchenID;
          }
        }
      });
    } else {
      this.searchingKitchen = {kitchenName: null, status: 1, deleteFG: 0, chefUserName: null};
      this.kitchenManagementService.getAllKitchen(this.searchingKitchen).subscribe(response => {
        this.kitchens = response.data;
      });
    }
  }

  clearFromDate() {
    if (this.dateFrom) { this.dateFrom = null; }
  }

  clearToDate() {
    if (this.dateTo) { this.dateTo = null; }
  }

  clearToDateAdd() {
    if (this.settingDate) { this.settingDate = null; }
  }

  settingParams() {
    this.searchingMenuConfig.kitchenID = this.selectedKitchen.kitchenID;
    this.isShowDropManager = false;
    this.isShow = true;
  }

  onSearchListMenu() {
    this.settingParams();
    if (this.searchingMenuConfig.kitchenID == null || this.searchingMenuConfig.kitchenID == '' ) {
      this.app.showWarn('Đ/c chưa chọn bếp');
      return;
    }
    this.dataTableComponent.reset();

  }

  public onLazyLoad(event) {
    if(this.dateFrom) this.dateFrom.setHours(0, 0, 0, 0);
    if(this.dateTo) this.dateTo.setHours(23, 59, 59, 0);
    if ( this.dateFrom && this.dateTo && this.dateFrom > this.dateTo ) {
      var from = new DatePipe('en-US').transform(this.dateFrom, 'dd-MM-yyyy');
      var to = new DatePipe('en-US').transform(this.dateTo, 'dd-MM-yyyy');
      this.app.showWarn('Giá trị Từ ngày : '+ from + ' phải nhỏ hơn hoặc bằng giá trị Đến ngày : ' + to);
      return;
    } else {
      this.searchingMenuConfig.dateFrom = this.dateFrom;
      this.searchingMenuConfig.dateTo = this.dateTo;
    }
    this.startRow = event.first;
    this.searchingMenuConfig.pageNumber = event.first;
    this.searchingMenuConfig.pageSize = event.rows;

    let component = this;
    let callback = () : void => {
      component.kitchenManagementService.countTotalMenu(component.searchingMenuConfig).subscribe(res => {
        if(res.data != null){
          component.total_record = res.data;
        } else {
          component.total_record =0;
        }
      });

      component.kitchenManagementService.getListMenu(component.searchingMenuConfig).subscribe(res => {
        component.menuSettings = res.data;
        if (component.menuSettings) {
          component.rowSize = component.menuSettings.length;
        }
      });
    }
    this.tokenStorage.getSecurityAccount(callback);
  }

  updateMenu() {
    if (!this.selectedEditMenuItem.dateOfMenu) {
      this.app.showWarn('Đ/c chưa chọn ngày');
      this.transferDateFromF.inputfieldViewChild.nativeElement.focus();
      return;
    }
    let count = 0;
    for (let i = 0; i < this.selectedEditMenuItem.listDish.length; i++) {
      if (this.selectedEditMenuItem.listDish[i].dishID != null) {
        count ++;
      }
      for (let j = 0; j < this.selectedEditMenuItem.listDish.length; j++) {
        if (i != j && this.selectedEditMenuItem.listDish[i].dishID === this.selectedEditMenuItem.listDish[j].dishID && this.selectedEditMenuItem.listDish[i].dishID != null) {
          this.app.showWarn('Không được chọn trùng món ăn ');
          return;
        }
      }
    }
    if (count == 0) {
      this.app.showWarn('Đ/c chưa chọn món');
      return;
    }
      let dateMenuItemFormat;
        if (this.selectedEditMenuItem.dateOfMenu !== undefined ) {
            dateMenuItemFormat  = new DatePipe('en-US').transform(this.selectedEditMenuItem.dateOfMenu, 'yyyy-MM-dd');
        }
      let dateMenuItemFormatShow;
        if (this.selectedEditMenuItem.dateOfMenu !== undefined ) {
          dateMenuItemFormatShow  = new DatePipe('en-US').transform(this.selectedEditMenuItem.dateOfMenu, 'dd-MM-yyyy');
        }

       const dateOfMenuOldFormat = new DatePipe('en-US').transform(this.dateOfMenuOld, 'yyyy-MM-dd');
       for ( let i = 0 ; i < this.selectedEditMenuItem.listDish.length; i++) {
         if ( this.selectedEditMenuItem.listDish [i].dishID == null) {
           this.selectedEditMenuItem.listDish.splice(i, 1);
           i--;
         }
       }
    const menuSetting: MenuSettingToInsert = {
      dateOfMenuOld: dateOfMenuOldFormat,
      dateOfMenu: dateMenuItemFormat, listDish: this.selectedEditMenuItem.listDish,
      chefID: this.selectedEditMenuItem.chefID, kitchenID: this.selectedEditMenuItem.kitchenID, copyFlag: 0
    };

    this.editedDate = new Date(this.editedDate);
    this.editedDate.setHours(0, 0, 0, 0);
    let dateOfMenu: Date = new Date(menuSetting.dateOfMenu);
    dateOfMenu.setHours(0, 0, 0, 0);

    if(this.editedDate.getTime() != dateOfMenu.getTime()) {
      this.kitchenManagementService.checkMenuExist(menuSetting).subscribe(response => {
        if (response.status === 2) {
          this.app.showWarn('Đã tồn tại menu ngày ' + new DatePipe('en-US').transform( menuSetting.dateOfMenu, 'dd-MM-yyyy'));
        } else if (response.status === 1) {
          this.kitchenManagementService.updateMenu(menuSetting).subscribe(response2 => {
            this.isNew = false;
            if (response2.status == 1) {
              this.app.showSuccess('Đã cập nhật menu ngày ' + dateMenuItemFormatShow);
              this.selectedMenuItems = null;
              this.settingDate = null;
              this.isEdit = false;
              this.settingParams();
              if (this.dataTableComponent !== undefined) {
                this.dataTableComponent.reset();
              } else {
                const event = {
                  first: 0,
                  rows: 10
                };
                this.onLazyLoad(event);
              }
            } else {
              this.app.showWarn('Cập nhật menu ngày '+dateMenuItemFormatShow+ ' không thành công');
            }
          });
        }
      });
    } else {
      this.kitchenManagementService.updateMenu(menuSetting).subscribe(response2 => {
        this.isNew = false;
        if (response2.status == 1) {
          this.app.showSuccess('Đã cập nhật menu ngày ' + dateMenuItemFormatShow);
          this.selectedMenuItems = null;
          this.settingDate = null;
          this.isEdit = false;
          this.settingParams();
          if (this.dataTableComponent !== undefined) {
            this.dataTableComponent.reset();
          } else {
            const event = {
              first: 0,
              rows: 10
            };
            this.onLazyLoad(event);
          }
        } else {
          this.app.showWarn('Cập nhật menu ngày '+dateMenuItemFormatShow+ ' không thành công');
        }
      });
    }
  }

  copyMenu(menu: MenuSetting) {

    for (let i = menu.listDish.length - 1; i >= 0; i--) {
      if (menu.listDish[i].dishID == null) {
        menu.listDish.splice(i, 1);
      }
    }
    if (menu.dateOfMenu !== undefined) {
      menu.dateOfMenu = this.tomorrow;
    }

    const searchingConfig = { chefID: null, status: 1, deleteFG: 0, dishName: null, kitchenID: this.selectedKitchen.kitchenID };
    this.kitchenManagementService.getListDishByKitchen(searchingConfig).subscribe(response => {
      this.menuItems = response.data;

      if(this.menuItems && this.menuItems.length > 0) {
        let message: string = null;
        for(let i = 0; i < this.selectedEditMenuItem.listDish.length; i++) {
          let isDeleted: boolean = true;
          for(let j = 0; j < this.menuItems.length; j++) {
            if(this.selectedEditMenuItem.listDish[i].dishID === this.menuItems[j].dishID) isDeleted = false;
          }
          if(isDeleted) {
            if(!message) message = 'Các món không còn hoạt động: ' + this.selectedEditMenuItem.listDish[i].dishName;
            else message = message + ', ' + this.selectedEditMenuItem.listDish[i].dishName;
            this.selectedEditMenuItem.listDish.splice(i, 1);
          }
        }
        if(message)this.app.showWarn(message);
      } else if(this.selectedEditMenuItem.listDish && this.selectedEditMenuItem.listDish.length > 0){
        let message: string = null;
        for(let i = 0; i < this.selectedEditMenuItem.listDish.length; i++) {
          if(!message) message = 'Các món không còn hoạt động: ' + this.selectedEditMenuItem.listDish[i].dishName;
          else message = message + ', ' + this.selectedEditMenuItem.listDish[i].dishName;
        }
        if(message)this.app.showWarn(message);
      }
    });

   // menu.dateOfMenu = null;
    this.isEdit = false;
    this.isEditNew = true;
  }

  copyEditMenu(menu: MenuSetting) {
    this.isEditNew = true;
    this.isCopy = false;

  }

  copyEditMenuDisable(menu: MenuSetting) {
    this.copyMenu(menu);
  }

  onRemoveMenuCopy(index: number) {
    this.selectedEditMenuItem.listDish.splice(index, 1);
  }

  onAddMenuCopy() {
    if (!this.selectedEditMenuItem.listDish) { this.selectedEditMenuItem.listDish = []; }
    const item: MenuItem = {dishID: null, dishName: null};
    this.selectedEditMenuItem.listDish.push(item);

  }

  validateKitchenDate(): boolean {
    if (this.settingDate === null || this.settingDate === undefined) {
      this.inputSettingDate.nativeElement.focus();
      this.messageService.add({
        severity: 'error',
        summary: 'Thông báo',
        detail: 'Tên món ăn không được để trống'
      });
      return false;
    }
    return true;
  }

  onInsertCopyMenu() {
    if (!this.selectedEditMenuItem.dateOfMenu) {
      this.app.showWarn('Đ/c chưa chọn ngày');
      return;
    }
    let count = 0 ;
    for (let i = 0; i < this.selectedEditMenuItem.listDish.length; i++) {
      if (this.selectedEditMenuItem.listDish[i].dishID != null) {
        count ++;
      }
      for (let j = 0; j < this.selectedEditMenuItem.listDish.length; j++) {
        if (i != j && this.selectedEditMenuItem.listDish[i].dishID === this.selectedEditMenuItem.listDish[j].dishID && this.selectedEditMenuItem.listDish[i].dishID != null) {
          this.app.showWarn('Không được chọn trùng món ăn ');
          return;
        }
      }
    }
    if (count == 0) {
      this.app.showWarn('Đ/c chưa chọn món');
      return;
    }

    const date = new DatePipe('en-US').transform(this.selectedEditMenuItem.dateOfMenu, 'yyyy-MM-dd');
    const dateShow = new DatePipe('en-US').transform(this.selectedEditMenuItem.dateOfMenu, 'dd-MM-yyyy');
    for ( let i = 0 ; i < this.selectedEditMenuItem.listDish.length; i++) {
      if ( this.selectedEditMenuItem.listDish [i].dishID == null) {
        this.selectedEditMenuItem.listDish.splice(i, 1);
        i--;
      }
    }
    const menuSetting: MenuSettingToInsert = {
      dateOfMenuOld: null, dateOfMenu: this.selectedEditMenuItem.dateOfMenu, listDish: this.selectedEditMenuItem.listDish,
      chefID: this.selectedEditMenuItem.chefID, kitchenID: this.selectedEditMenuItem.kitchenID, copyFlag: 0
    };
    this.kitchenManagementService.checkMenuExist(menuSetting).subscribe(response => {
      if (response.status === 2) {
        this.app.showWarn('Đã tồn tại menu của ngày ' + dateShow);
      } else if (response.status === 1) {
        this.kitchenManagementService.insertMenu(menuSetting).subscribe(response2 => {
          this.isEditNew = false;
          this.isEditOld = false;
          if (response2.status == 1) {
            this.app.showSuccess('Đã thiết lập menu của ngày ' + dateShow);
            this.settingParams();
            if (this.dataTableComponent !== undefined) {
              this.dataTableComponent.reset();
            } else {
              const event = {
                first: 0,
                rows: 10
              };
              this.onLazyLoad(event);
            }
          } else {
            this.app.showWarn('Thiết lập menu của ngày'+dateShow+ 'không thành công');
          }
        });
      }
    });
  }

  onHideImagePopup() {
    this.imageData = null;
  }

  openDlgImage(menu: MenuItem) {
    if(!menu.dishID) {
      this.app.showWarn('Đ/c chưa chọn món ăn');
      return;
    }

    this.showDlgImage = true;
    this.selectedMenuItem = menu;
    this.getDishImage(menu.dishID);
  }

  closeDlgImage() {
    this.showDlgImage = false;
    this.showImageLabel = null;
  }

  getDishImage(dishId: any) {
    this.showImageLabel = 'loading.....';
    this.kitchenManagementService.getDishImage(dishId).subscribe(data => {
      this.imageData = null;
      if(data.size === 0) this.showImageLabel = 'Lỗi hiển thị ảnh';
      else this.showImageLabel = null;
      this.createImageFromBlob(data);
    });
  }

  createImageFromBlob(image: any) {
    let reader = new FileReader();
    reader.addEventListener('load', () => {
      this.imageToShow = reader.result;
      if (this.imageToShow) {
        this.imageData = this.imageToShow.toString().split(',')[1];
      }
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

}
