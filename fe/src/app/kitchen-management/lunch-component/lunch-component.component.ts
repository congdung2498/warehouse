import {Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {ConfigLunch, LunchModel, SearchingLunch, SearchingLunchModel, UpdatingAllLunch} from "../Entity/LunchConfig";
import {UserInfo} from "../../shared/UserInfo";
import {SelectingKitchen} from "../Entity/Kitchen";
import {DateTimeUtil, Month} from "../../../common/datetime";
import {KitchenManagementService} from "../kitchen-management.service";
import {AppComponent} from "../../app.component";
import {TokenStorage} from "../../shared/token.storage";
import {MenuItem} from "../Entity/MenuSetting";
import {Dropdown, MultiSelect} from "primeng/primeng";
import {Table} from "primeng/table";
import {ConfirmationService} from "primeng/api";
import { trigger, state, style, transition, animate } from '@angular/animations';
import {Dish} from "../Entity/DishConfig";
/*import {Input} from "../../../../../../../SourceCode/Angular 5/node_modules/@angular/compiler/src/core";*/


@Component({
  selector: 'app-lunch-component',
  templateUrl: './lunch-component.component.html',
  styleUrls: ['./lunch-component.component.css'],
  animations: [
    trigger('animation', [
      state('true', style({
        opacity: 1,
        transform: 'translateY(0px)'
      })),
      state('false', style({
        opacity: 0,
        display: 'none',
        transform: 'translateY(10px)'
      })),
      transition('0 => 1', animate('190ms')),
      transition('1 => 0', animate('190ms'))
    ])
  ]
})
export class LunchComponentComponent implements OnInit {

  @ViewChild('periodMonthFld') periodMonthFld: MultiSelect;
  @ViewChild('cutPeriodMonthFld') cutPeriodMonthFld: MultiSelect;
  @ViewChild('kitchenFld') kitchenFld: Dropdown;
  @ViewChild("yearEle") yearEl: ElementRef;
  @ViewChild("cutYearEle") cutYearEle: ElementRef;
  @ViewChild('myTable') myTable: Table;
  @ViewChild('quantityFld') quantityFld: ElementRef;

  vn = DateTimeUtil.vn;

  statusLabels: string[] = ['Không ăn', 'Đặt ăn'];
  loginUser: UserInfo;
  lunches: LunchModel[];
  kitchens: SelectingKitchen[];
  selectedKitchen: SelectingKitchen;
  lunchMethodFld: string = 'isPeriod';

  isCutPeriod: boolean;
  isPeriod: boolean;
  isByDay: boolean;

  loading2 = false;

  isCheckedAll: boolean;
  isChecked: boolean;
  configLunch: ConfigLunch;
  searchingLunch: SearchingLunch;

  periodDays: string[];
  cutPeriodDays: string[];
  configMonths: Month[] = DateTimeUtil.months;
  selectedConfigMonths: Month[];
  selectedCutConfigMonths: Month[];
  yearFld: number;
  cutYearFld: number;

  startDate: Date;
  endDate: Date;

  isShowUpdateOneLunch: boolean;
  isShowUpdateAllLunch: boolean;

  selectedLunchIds: string[];
  selectedLunch: LunchModel;
  selectedLunchKitchen: SelectingKitchen;
  selectedLunchMenus: MenuItem[];
  isSelectedLunchBooking: boolean;
  selectedLunchQuantity: number;

  updateAllLunchQuantity: number = 1;
  isUpdateAllLunchHasBooking: boolean = true;
  startRow: number;
  rowSize: number;
  totalRecord: number;

  isShowVoting: boolean;
  isShowVotingBtn: boolean;
  starColors: string[] = ['silver', 'silver', 'silver', 'silver', 'silver'];
  selectedRating: number = 0;
  lunchCommentFld: string;
  votingSelectedDate: Date;
  updatableTottal: number = 0;

  isShowCloseBtn: boolean;
  updateTitle: string;
  isDisableKitchen: boolean;
  isFirstLoad: boolean;
  textPeriodShow: string;
  textCutPeriodShow: string;
  isShowCheckAllPeriod: boolean = false;
  isShowCheckAllCutPeriod: boolean = false;

  searchingLunchModel: SearchingLunchModel;
  isShowImage: boolean;
  imageData: any;
  lunchQuantity: number = 1;



  constructor(private kitchenService: KitchenManagementService, private app: AppComponent, private tokenStorage: TokenStorage,
              private confirmationService: ConfirmationService, private renderer: Renderer2) { }

  ngOnInit() {
    this.onInitPeriod();
    this.selectedLunch = new  LunchModel();
    this.textPeriodShow = 'Chọn tất cả';
    this.textCutPeriodShow = 'Chọn tất cả';

    let today = new Date();
    this.yearFld = today.getFullYear();
    this.cutYearFld = today.getFullYear();
    if(!this.selectedConfigMonths) this.selectedConfigMonths = [];
    this.selectedConfigMonths.push(DateTimeUtil.getMonthByName(today.getMonth() + 1));
    this.loginUser = this.tokenStorage.getUserInfo();

    let component = this;
    let callback = () : void => {
      component.getAllKitchen();
      component.getLunchModel();
    }
    this.tokenStorage.getSecurityAccount(callback);
  }

  onInitByDay() {
    this.isByDay  = true;
    this.isCutPeriod = this.isPeriod = false;
  }

  onInitCutPeriod() {
    this.isCutPeriod = true;
    this.isPeriod = this.isByDay = false;
    this.isDisableKitchen = true;
  }

  onInitPeriod() {
    this.isPeriod = true;
    this.isCutPeriod = this.isByDay = false;
  }

  onChangeCutPeriodMonth() {
    if (this.selectedCutConfigMonths != null && this.selectedCutConfigMonths.length === 12) {
      this.textCutPeriodShow = 'Bỏ chọn tất cả';
    } else {
      this.textCutPeriodShow = 'Chọn tất cả';
    }
  }

  onHideCutPeriodMonthPanel() {
    this.isShowCheckAllCutPeriod = false;
  }

  onShowCutPeriodMonthPanel() {
    const child = this.cutPeriodMonthFld.el.nativeElement.children[0].children[3];
    this.renderer.setStyle(child, 'max-width', 'inherit');
    this.renderer.setStyle(child, 'width', 'inherit');
    this.isShowCheckAllCutPeriod = true;
  }

  onChangePeriodMonth() {
    if (this.selectedConfigMonths != null && this.selectedConfigMonths.length === 12) {
      this.textPeriodShow = 'Bỏ chọn tất cả';
    } else {
      this.textPeriodShow = 'Chọn tất cả';
    }
  }

  onHidePeriodMonthPanel() {
    this.isShowCheckAllPeriod = false;
  }

  onShowPeriodMonthPanel() {
    const child = this.periodMonthFld.el.nativeElement.children[0].children[3];
    this.renderer.setStyle(child, 'max-width', 'inherit');
    this.renderer.setStyle(child, 'width', 'inherit');
    this.isShowCheckAllPeriod = true;
  }

  onUpdateVoting() {
    if(this.selectedRating === 0 && !this.lunchCommentFld) {
      this.app.showWarn('Đ/c chưa có đánh giá nào (điểm số và góp ý)');
      return;
    }

    this.selectedLunch.comment = this.lunchCommentFld;
    this.selectedLunch.rating = this.selectedRating;
    this.kitchenService.updateVoting(this.selectedLunch).subscribe(response => {
      if(response.status === 1) {
        this.app.showSuccess('Cảm ơn Đ/c đã đánh giá');
      } else {
        this.app.showWarn('Nhận xét bữa ăn không thành công');
      }
      this.isShowVoting = false;
      this.getLunchModel();
    });
  }

  onShowVoting(lunch: LunchModel) {
    this.selectedLunch = lunch;
    this.selectedLunch.lunchDate = new Date(this.selectedLunch.lunchDate);

    let today = new Date();
    if((today.getHours() > 12 || (today.getHours() === 12 && today.getMinutes() >= 30)) && today.getHours() <= 14 && lunch.lunchDate.getDate() === today.getDate() &&
        lunch.lunchDate.getMonth() === today.getMonth() && lunch.lunchDate.getFullYear() === today.getFullYear() &&
        !lunch.comment && lunch.rating === 0) {
      this.isShowVotingBtn = true;
    }

    this.isShowVoting = true;
    this.lunchCommentFld = lunch.comment;
    this.votingSelectedDate = new Date(lunch.lunchDate);

    this.starColors = ['silver', 'silver', 'silver', 'silver', 'silver'];
    for(let i = 0; i < lunch.rating; i++) {
      this.starColors[i] = 'orange';
    }

    this.getMenuSettingByDate();
  }

  onHideVoting() {
    this.isShowVoting = false;
    this.selectedRating = 0;
    this.selectedLunch = new LunchModel();
    this.selectedLunchMenus = null;
    this.isShowVotingBtn = false;
  }

  onCheckRating(rate: number) {
    this.selectedRating = rate;
    this.starColors = ['silver', 'silver', 'silver', 'silver', 'silver'];
    for(let i = 0; i < rate; i++) {
      this.starColors[i] = 'orange';
    }
  }

  onShowLunchDetail(lunch: LunchModel) {
    this.updateTitle = 'Đặt lịch ăn';
    this.isShowUpdateOneLunch = true;
    this.isShowCloseBtn = true;
    this.selectedLunch = lunch;
    this.selectedLunch.lunchDate = new Date(this.selectedLunch.lunchDate);

    for (let i = 0; i < this.kitchens.length; i++) {
      if (this.selectedLunch.kitchenId === this.kitchens[i].kitchenID) {
        this.selectedLunchKitchen = this.kitchens[i];
      }
    }
    if (this.selectedLunch.hasBooking === 1) this.isSelectedLunchBooking = true;
    this.selectedLunchQuantity = this.selectedLunch.quantity;
    this.getMenuSettingByDate();
  }

  onShowUpdateOneLunch() {
    this.updateTitle = 'Cập nhật đăng ký ăn trưa';
    this.isShowUpdateOneLunch = true;
    for (let i = 0; i < this.lunches.length; i++) {
      if (this.lunches[i].lunchId === this.selectedLunchIds[0]) {
        this.selectedLunch = this.lunches[i];
        this.selectedLunch.lunchDate = new Date(this.selectedLunch.lunchDate);
        break;
      }
    }
    for (let i = 0; i < this.kitchens.length; i++) {
      if (this.selectedLunch.kitchenId === this.kitchens[i].kitchenID) {
        this.selectedLunchKitchen = this.kitchens[i];
      }
    }
    if (this.selectedLunch.hasBooking === 1) this.isSelectedLunchBooking = true;
    this.selectedLunchQuantity = this.selectedLunch.quantity;
    this.getMenuSettingByDate();
  }

  onHideEditOneLunch() {
    this.isSelectedLunchBooking = false;
    this.selectedLunchKitchen = null;
    this.selectedLunchQuantity = 0;
    this.selectedLunchIds = null;
    this.isShowUpdateOneLunch = false;
    this.selectedLunchMenus = null;
    this.isShowCloseBtn = false;
  }

  onHideEditAllLunch() {
    this.updateAllLunchQuantity = 1;
    this.isUpdateAllLunchHasBooking = true;
    this.isShowUpdateAllLunch = false;
  }

  onShowUpdateAllLunch() {
    this.isShowUpdateAllLunch = true;
  }

  onShowUpdateLunch() {
    if(!this.selectedLunchIds || this.selectedLunchIds.length === 0) {
      this.app.showWarn('Đ/c chưa chọn ngày nào!');
      return;
    }

    if(this.selectedLunchIds.length === 1) {
      this.onShowUpdateOneLunch();
    } else if(this.selectedLunchIds.length > 1){
      this.onShowUpdateAllLunch();
    }
  }

  onUpdateOneLunch() {
    if(this.selectedLunchQuantity > 5 && this.isSelectedLunchBooking) {
      this.confirmationService.confirm({
        message: 'Đ/c đang đăng ký nhiều suất ăn, Đ/c chắc chắn muốn đăng ký không?',
        header: 'Nhắc nhở cập nhật',
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: 'Đồng ý',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          this.updateOneLunch();
        }
      });
    } else {
      this.updateOneLunch();
    }
  }

  updateOneLunch() {
    let configLunch: ConfigLunch = new ConfigLunch();
    let account = this.tokenStorage.getAccount();
    configLunch.securityUsername = account.securityUsername;
    configLunch.securityPassword = account.securityPassword;
    configLunch.listPeriodic = this.cutPeriodDays;
    configLunch.kitchenID = this.selectedLunchKitchen.kitchenID;
    configLunch.userName = this.loginUser.userName.toString();
    configLunch.quantity = this.selectedLunchQuantity;
    configLunch.lunchDate = this.selectedLunch.lunchDate;
    configLunch.unitId = this.loginUser.unitId.toString();
    if(this.isSelectedLunchBooking) configLunch.hasBooking = 1;
    else configLunch.hasBooking = 0;

    configLunch.isPeriodic = this.selectedLunch.period;

    this.kitchenService.settingLunch({action: 2, data: configLunch}).subscribe(response => {
      if(response.status === 1) {
        this.app.showSuccess('Cập nhật đặt ăn trưa thành công');
        this.selectedLunchIds = [];
      } else if(response.status === 2) {
        this.app.showWarn('Cập nhật đặt ăn trưa không thành công - vượt quá số suất cho phép');
        this.selectedLunchIds = [];
      } else {
        this.app.showWarn('Cập nhật đặt ăn trưa không thành công');
      }
      this.isShowUpdateOneLunch = false;
      this.onSearchLunch();
    });
  }

  onUpdateAllLunch() {
    if(this.updateAllLunchQuantity > 5 && this.isUpdateAllLunchHasBooking) {
      this.confirmationService.confirm({
        message: 'Đ/c đang đăng ký nhiều suất ăn, Đ/c chắc chắn muốn đăng ký không?',
        header: 'Nhắc nhở cập nhật',
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: 'Đồng ý',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          this.updateAllLunch();
        }
      });
    } else {
      this.updateAllLunch();
    }
  }

  updateAllLunch() {
    let config: UpdatingAllLunch = new UpdatingAllLunch();
    config.quantity = this.updateAllLunchQuantity;
    if(this.isUpdateAllLunchHasBooking) config.hasBooking = 1;
    else config.hasBooking = 0;
    config.lunchIds = this.selectedLunchIds;
    config.loginUsername = this.loginUser.userName.toString();

    this.kitchenService.updateAllLunch(config).subscribe(response => {
      if(response.status === 1) {
        this.app.showSuccess('Cập nhật đặt ăn trưa thành công');
        this.selectedLunchIds = [];
      } else {
        this.app.showWarn('Cập nhật đặt ăn trưa không thành công');
      }
      this.isShowUpdateAllLunch = false;
      this.onSearchLunch();
    });
  }

  onChangeMethod() {
    this.isDisableKitchen = false;
    if(this.lunchMethodFld === 'isPeriod') {
      this.onInitPeriod();
    } else if(this.lunchMethodFld === 'isCutPeriod') {
      this.onInitCutPeriod();
    } else if(this.lunchMethodFld === 'isByDay') {
      this.onInitByDay();
    }
  }

  onCreateLunch() {
    if(this.isPeriod) this.createLunchByPeriodic();
    else if(this.isCutPeriod) this.deleteLunchByCutPeriodic();
    else this.createLunchByDay();
  }

  getMenuSettingByDate() {
    this.kitchenService.getMenuSettingByDate({kitchenId: this.selectedLunch.kitchenId, date: this.selectedLunch.lunchDate}).subscribe(response => {
      this.selectedLunchMenus = response.data;
    });
  }

  getLunchModel() {
    if (!this.searchingLunch) this.searchingLunch = new SearchingLunch();
    this.searchingLunch.userName = this.loginUser.userName.toString();
    this.searchingLunch.month = new Date().getMonth() + 1;
    this.searchingLunch.year = new Date().getFullYear();
    this.searchingLunch.isPeriodic = 1;
    this.searchingLunch.hasBooking = 1;
    this.searchingLunch.startRow = 0;
    this.searchingLunch.rowSize = 10;
    this.startRow = 0;

    this.kitchenService.getTotalLunchModel(this.searchingLunch).subscribe(item => {
      this.totalRecord = item['data'];

      if (this.totalRecord > 0) {
        this.rowSize = 10;
        if (this.totalRecord > 0) this.startRow = 1;
        if (this.rowSize > this.totalRecord) this.rowSize = this.totalRecord;
        this.getLunchModelData(this.searchingLunch);
      }
    });
  }

  onSearchLunch() {
    this.isCheckedAll = false;
    this.selectedLunchIds = [];
    this.startRow = 0;
    if (this.myTable) this.myTable.reset();
    if(this.isPeriod) {
      this.searchByPeriod();
    } else if(this.isCutPeriod) {
      this.lunches = null;
      this.totalRecord = 0;
      this.rowSize = 0;
    } else {
      this.searchByDay();
    }
  }

  searchByPeriod() {
    this.isFirstLoad = false;
    if(!this.searchingLunchModel) this.searchingLunchModel = new SearchingLunchModel();
    this.searchingLunchModel.userName  = this.loginUser.userName.toString();
    this.searchingLunchModel.startRow = 0;
    this.searchingLunchModel.rowSize = 10;
    this.searchingLunchModel.year = this.yearFld;
    this.searchingLunchModel.listPeriodic = this.periodDays;
    if(this.selectedKitchen) this.searchingLunchModel.kitchenId = this.selectedKitchen.kitchenID;
    else this.searchingLunchModel.kitchenId = null;

    let months: number[] = null;
    if(this.selectedConfigMonths && this.selectedConfigMonths.length > 0) {
      months= [];
      for(let i = 0; i < this.selectedConfigMonths.length; i++) {
        months.push(this.selectedConfigMonths[i].name);
      }
    }
    this.searchingLunchModel.months = months;

    this.kitchenService.getLunchModelByPeriod(this.searchingLunchModel).subscribe(response => {
      if (response.data) {
        this.lunches = response.data.lunches;
        this.totalRecord = response.data.totalRecords;
        this.setUpdatable(this.lunches);
        this.rowSize = 10;
        if (this.lunches && this.lunches.length > 0) this.startRow = 1;
        if (this.rowSize > this.totalRecord) this.rowSize = this.totalRecord;
      }
    });
  }

  searchByCutPeriod() {
    this.isFirstLoad = false;
    if(!this.searchingLunchModel) this.searchingLunchModel = new SearchingLunchModel();
    this.searchingLunchModel.userName  = this.loginUser.userName.toString();
    this.searchingLunchModel.startRow = 0;
    this.searchingLunchModel.rowSize = 10;
    this.searchingLunchModel.year = this.cutYearFld;
    this.searchingLunchModel.listPeriodic = this.cutPeriodDays;
    if(this.selectedKitchen) this.searchingLunchModel.kitchenId = this.selectedKitchen.kitchenID;
    else this.searchingLunchModel.kitchenId = null;

    let months: number[] = null;
    if(this.selectedCutConfigMonths && this.selectedCutConfigMonths.length > 0) {
      months= [];
      for(let i = 0; i < this.selectedCutConfigMonths.length; i++) {
        months.push(this.selectedCutConfigMonths[i].name);
      }
    }
    this.searchingLunchModel.months = months;

    this.kitchenService.getLunchModelByPeriod(this.searchingLunchModel).subscribe(response => {
      if (response.data) {
        this.lunches = response.data.lunches;
        this.totalRecord = response.data.totalRecords;
        this.setUpdatable(this.lunches);
        this.rowSize = 10;
        if (this.lunches && this.lunches.length > 0) this.startRow = 1;
        if (this.rowSize > this.totalRecord) this.rowSize = this.totalRecord;
      }
    });
  }

  searchByDay() {
    this.isFirstLoad = false;
    if(this.startDate) this.startDate.setHours(0, 0, 0, 0);
    if(this.endDate) this.endDate.setHours(23, 59, 59, 59);

    if(this.startDate && this.endDate) {
      if (this.startDate > this.endDate) {
        this.app.showWarn('Thời gian bắt đầu phải nhỏ hơn thời gian kết thúc');
        return ;
      }
    }

    if(!this.searchingLunchModel) this.searchingLunchModel = new SearchingLunchModel();
    this.searchingLunchModel.userName  = this.loginUser.userName.toString();
    this.searchingLunchModel.startTime = this.startDate;
    this.searchingLunchModel.endTime = this.endDate;
    this.searchingLunchModel.quantity = this.lunchQuantity;
    this.searchingLunchModel.startRow = 0;
    this.searchingLunchModel.rowSize = 10;
    if(this.selectedKitchen) this.searchingLunchModel.kitchenId = this.selectedKitchen.kitchenID;
    else this.searchingLunchModel.kitchenId = null;

    let quantityStr = this.quantityFld.nativeElement.value;
    if(quantityStr != '') this.searchingLunchModel.searchQuantity = true;
    else this.searchingLunchModel.searchQuantity = false;

      this.kitchenService.getLunchModelByDay(this.searchingLunchModel).subscribe(response => {
      if (response.data) {
        this.lunches = response.data.lunches;
        this.totalRecord = response.data.totalRecords;
        this.setUpdatable(this.lunches);
        this.rowSize = 10;
        if (this.lunches && this.lunches.length > 0) this.startRow = 1;
        if (this.rowSize > this.totalRecord) this.rowSize = this.totalRecord;
      }
    });
  }

  getLunchModelData(param: SearchingLunch) {
    this.selectedLunchIds = [];
    this.isCheckedAll = false;
    this.isFirstLoad = true;
    this.kitchenService.getLunchModel(param).subscribe(response => {
      if (response.data) {
        if (response.data.periodic) {
          this.periodDays = [];
          for (let i = 0; i < response.data.periodic.length; i++) {
            let dayOfWeek = this.periodDays.push(response.data.periodic[i].toString());
          }
        }
        if (response.data.kitchenID) {
          this.selectedKitchen = {kitchenID: response.data.kitchenID, kitchenName: response.data.kitchenName, chefName: null, status: 0};
        }
        this.lunches = response.data.listLunchDate;
        this.setUpdatable(this.lunches);
      }
    });
  }

  paginate(event) {
    if(this.isFirstLoad) {
      this.onPaginateFirstLoad(event);
    } else {
      if(this.isPeriod) this.onPaginateByPeriod(event);
      else if(this.isCutPeriod) this.onPaginateByCutPeriod(event);
      else this.onPaginateByDay(event);
    }
  }

  onPaginateFirstLoad(event) {
    this.startRow = event.first + 1;
    this.rowSize = this.startRow + 9;
    if (this.rowSize > this.totalRecord) this.rowSize = this.totalRecord;

    this.searchingLunch.startRow = event.first;
    this.getLunchModelData(this.searchingLunch);
  }

  onPaginateByPeriod(event) {
    if(!this.searchingLunchModel) {
      this.searchingLunchModel = new SearchingLunchModel();
      this.searchingLunchModel.userName  = this.loginUser.userName.toString();
      this.searchingLunchModel.startRow = 0;
      this.searchingLunchModel.rowSize = 10;
      this.searchingLunchModel.year = this.yearFld;
      this.searchingLunchModel.listPeriodic = this.periodDays;
      if(this.selectedKitchen) this.searchingLunchModel.kitchenId = this.selectedKitchen.kitchenID;
      else this.searchingLunchModel.kitchenId = null;

      let months: number[] = null;
      if(this.selectedConfigMonths && this.selectedConfigMonths.length > 0) {
        months= [];
        for(let i = 0; i < this.selectedConfigMonths.length; i++) {
          months.push(this.selectedConfigMonths[i].name);
        }
      }
      this.searchingLunchModel.months = months;
    }

    this.startRow = event.first + 1;
    this.searchingLunchModel.startRow = event.first;
    this.rowSize = this.startRow + 9;
    if (this.rowSize > this.totalRecord) this.rowSize = this.totalRecord;

    this.kitchenService.getLunchModelByPeriod(this.searchingLunchModel).subscribe(response => {
      if (response.data) {
        this.lunches = response.data.lunches;
        this.setUpdatable(this.lunches);
      }
    });
  }

  onPaginateByCutPeriod(event) {
    if(!this.searchingLunchModel) {
      this.searchingLunchModel = new SearchingLunchModel();
      this.searchingLunchModel.userName  = this.loginUser.userName.toString();
      this.searchingLunchModel.startRow = 0;
      this.searchingLunchModel.rowSize = 10;
      this.searchingLunchModel.year = this.cutYearFld;
      this.searchingLunchModel.listPeriodic = this.cutPeriodDays;
      if(this.selectedKitchen) this.searchingLunchModel.kitchenId = this.selectedKitchen.kitchenID;
      else this.searchingLunchModel.kitchenId = null;

      let months: number[] = null;
      if(this.selectedCutConfigMonths && this.selectedCutConfigMonths.length > 0) {
        months= [];
        for(let i = 0; i < this.selectedCutConfigMonths.length; i++) {
          months.push(this.selectedCutConfigMonths[i].name);
        }
      }
      this.searchingLunchModel.months = months;
    }

    this.startRow = event.first + 1;
    this.searchingLunchModel.startRow = event.first;
    this.rowSize = this.startRow + 9;
    if (this.rowSize > this.totalRecord) this.rowSize = this.totalRecord;

    this.kitchenService.getLunchModelByPeriod(this.searchingLunchModel).subscribe(response => {
      if (response.data) {
        this.lunches = response.data.lunches;
        this.setUpdatable(this.lunches);
      }
    });
  }

  onPaginateByDay(event) {
    this.startRow = event.first + 1;
    this.searchingLunchModel.startRow = event.first;
    this.rowSize = this.startRow + 9;
    if (this.rowSize > this.totalRecord) this.rowSize = this.totalRecord;

    this.kitchenService.getLunchModelByDay(this.searchingLunchModel).subscribe(response => {
      if (response.data) {
        this.lunches = response.data.lunches;
        this.setUpdatable(this.lunches);
      }
    });
  }

  onHideImageDialog() {
    this.imageData = null;
  }

  onShowImage(dish: any) {
    this.isShowImage = true;
    this.kitchenService.getImage({dishId: dish.dishID}).subscribe(data => {
      this.createImageFromBlob(data);
    });
  }

  createImageFromBlob(image: any) {
    let reader = new FileReader();
    reader.addEventListener('load', () => {
      let imageToShow = reader.result;
      if (imageToShow) {
        this.imageData = imageToShow.toString().split(',')[1];
      }
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  deleteLunchByCutPeriodic() {
    if(!this.selectedKitchen || !this.selectedKitchen.kitchenID) {
      this.app.showWarn('Đ/c chưa chọn bếp');
      this.kitchenFld.focus();
      return;
    }

    if(!this.cutPeriodDays|| this.cutPeriodDays.length === 0) {
      this.app.showWarn('Đ/c chưa chọn ngày trong tuần');
      return;
    }

    if(!this.selectedCutConfigMonths || this.selectedCutConfigMonths.length === 0) {
      this.app.showWarn('Đ/c chưa chọn tháng');
      return;
    }

    let months: number[] = [];
    for(let i = 0; i < this.selectedCutConfigMonths.length; i++) {
      if(this.yearFld === new Date().getFullYear()) {
        if(this.selectedCutConfigMonths[i].name > (new Date().getMonth())) {
          months.push(this.selectedCutConfigMonths[i].name);
        }
      } else {
        months.push(this.selectedCutConfigMonths[i].name);
      }
    }
    if(months.length === 0) {
      this.app.showWarn('Không có tháng nào thõa mãn điều kiện');
      return;
    }

    if(!this.cutYearFld) {
      this.cutYearEle.nativeElement.focus();
      this.app.showWarn('Năm không được bỏ trống');
      return;
    }

    if(this.cutYearFld < new Date().getFullYear()) {
      this.cutYearEle.nativeElement.focus();
      this.app.showWarn('Năm không được nhỏ hơn năm hiện tại');
      return;
    }

    let configLunch: ConfigLunch = new ConfigLunch();
    configLunch.userName = this.loginUser.userName.toString();
    configLunch.listPeriodic = this.cutPeriodDays;
    configLunch.kitchenID = this.selectedKitchen.kitchenID;
    configLunch.unitId = this.loginUser.unitId.toString();
    configLunch.months = months;
    configLunch.year = this.cutYearFld;

    this.kitchenService.deleteLunchByPeriod(configLunch).subscribe(response => {
      if(response.status === 1) {
        this.app.showSuccess('Cắt đặt lịch ăn trưa thành công');
        this.startRow = 0;
        this.onSearchLunch();
      } else {
        this.app.showWarn('Cắt dặt lịch ăn trưa không thành công');
      }
    });
  }

  createLunchByPeriodic() {
    if(!this.selectedKitchen || !this.selectedKitchen.kitchenID) {
      this.app.showWarn('Đ/c chưa chọn bếp');
      this.kitchenFld.focus();
      return;
    }

    if(!this.periodDays || this.periodDays.length === 0) {
      this.app.showWarn('Đ/c chưa chọn ngày trong tuần');
      return;
    }

    if(!this.selectedConfigMonths || this.selectedConfigMonths.length === 0) {
      this.app.showWarn('Đ/c chưa chọn tháng');
      return;
    }

    let months: number[] = [];
    for(let i = 0; i < this.selectedConfigMonths.length; i++) {
      if(this.yearFld === new Date().getFullYear()) {
        if(this.selectedConfigMonths[i].name > (new Date().getMonth())) {
          months.push(this.selectedConfigMonths[i].name);
        }
      } else {
        months.push(this.selectedConfigMonths[i].name);
      }
    }
    if(months.length === 0) {
      this.app.showWarn('Không có tháng nào thõa mãn điều kiện');
      return;
    }

    if(!this.yearFld) {
      this.yearEl.nativeElement.focus();
      this.app.showWarn('Năm không được bỏ trống');
      return;
    }

    if(this.yearFld < new Date().getFullYear()) {
      this.yearEl.nativeElement.focus();
      this.app.showWarn('Năm không được nhỏ hơn năm hiện tại');
      return;
    }

    if(!this.configLunch) this.configLunch = new ConfigLunch();
    this.configLunch.userName = this.loginUser.userName.toString();
    this.configLunch.listPeriodic = this.periodDays;
    this.configLunch.kitchenID = this.selectedKitchen.kitchenID;
    this.configLunch.unitId = this.loginUser.unitId.toString();
    this.configLunch.months = months;
    this.configLunch.year = this.yearFld;

    this.kitchenService.createLunchByPeriod(this.configLunch).subscribe(response => {
      if(response.status === 1) {
        this.app.showSuccess('Đặt lịch ăn trưa thành công');
        this.startRow = 0;
        this.searchByPeriod();
      } else {
        this.app.showWarn('Đặt lịch ăn trưa không thành công');
      }
    });
  }

  createLunchByDay() {
	if(!this.selectedKitchen || !this.selectedKitchen.kitchenID) {
      this.app.showWarn('Đ/c chưa chọn bếp');
      this.kitchenFld.focus();
      return;
    }
	
    if(!this.validateTime(this.startDate, this.endDate)) {
      return;
    }

    if(this.lunchQuantity < 1) {
      this.app.showWarn('Số suất không được nhỏ hơn 1');
      return;
    }


    let configLunch: ConfigLunch = new ConfigLunch();
    configLunch.startTime = this.startDate;
    configLunch.endTime = this.endDate;
    configLunch.kitchenID = this.selectedKitchen.kitchenID;
    configLunch.userName = this.loginUser.userName.toString();
    configLunch.unitId = this.loginUser.unitId.toString();
    configLunch.quantity = this.lunchQuantity;

    this.kitchenService.getExistedLunchMessage(configLunch).subscribe(response => {
      if(response.status === 1 && response.data) {
        this.confirmationService.confirm({
          message: 'Đồng chí đã đặt ăn trước đó: ' + response.data,
          header: 'Nhắc nhở đặt ăn bị trùng',
          icon: 'pi pi-exclamation-triangle',
          acceptLabel: 'Đồng ý',
          rejectLabel: 'Hủy bỏ',
          accept: () => {
            this.createLunchByDayService(configLunch);
          }
        });
      } else {
        this.createLunchByDayService(configLunch);
      }
    });
  }

  createLunchByDayService(configLunch: ConfigLunch) {
    let account = this.tokenStorage.getAccount();
    configLunch.securityUsername = account.securityUsername;
    configLunch.securityPassword = account.securityPassword;

    this.kitchenService.createLunchByDay({action: 2, data: configLunch}).subscribe(response => {
      if(response.status === 1) {
        this.app.showSuccess('Đặt lịch ăn trưa theo ngày thành công');
        this.startRow = 0;
        this.searchByDay();
      } else if(response.status === 3 ) {
        this.app.showWarn('Ngày đặt ăn không hợp lệ');
      } else if(response.status === 8 ) {
        this.app.showWarn('Ngày đặt ăn vượt quá số suất quy định');
      } else {
        this.app.showWarn('Đặt lịch ăn trưa theo ngày không thành công');
      }
    });
  }

  validateTime(startTime: Date, endTime: Date) {
    if(!startTime || !endTime) {
      this.app.showWarn('Thời gian bắt đầu và kết thúc không được bỏ trống');
      return false;
    }

    startTime.setHours(23, 59, 59, 59);
    endTime.setHours(23, 59, 59, 59);

    if (startTime > endTime) {
      this.app.showWarn('Thời gian bắt đầu phải nhỏ hơn thời gian kết thúc');
      return false;
    }
    if(startTime < new Date()) {
      this.app.showWarn('Thời gian bắt đầu phải lớn hơn thời gian hiện tại');
      return false;
    }

    return true;
  }

  getAllKitchen() {
    this.kitchenService.findAllKitchenName().subscribe(response => {
      this.kitchens = response.data;
    });
  }

  checkAll(isChecked: boolean) {
    this.selectedLunchIds = [];
    if(isChecked) {
      let today = new Date();
      today.setHours(23, 59, 59, 59);
      for(let i = 0; i < this.lunches.length; i++) {
        let lunch: LunchModel = this.lunches[i];
        if(lunch.lunchDate > today)  this.selectedLunchIds.push(lunch.lunchId);
      }
    }
  }

  onCheckLunch(isChecked: boolean) {
    if(isChecked) {
      if(this.selectedLunchIds.length === this.updatableTottal) this.isCheckedAll = true;
    } else {
      this.isCheckedAll = false;
    }
  }

  setUpdatable(lunches: LunchModel[]) {
    if(!lunches || lunches.length === 0) return;
    this.updatableTottal = 0;
    let today = new Date();
    if(today.getHours() > 8 || (today.getHours() === 8 && today.getMinutes() > 39)) {
      today.setHours(23, 59, 59, 59);
    }
    for(let i = 0; i < lunches.length; i++) {
      let lunch: LunchModel = lunches[i];
      let lunchDate = new Date(lunch.lunchDate);
      lunchDate.setHours(8, 40, 0, 0);
      if(lunchDate > today)  {
        lunch.isUpdatable = true;
        this.updatableTottal++;
      }
    }
  }
}
