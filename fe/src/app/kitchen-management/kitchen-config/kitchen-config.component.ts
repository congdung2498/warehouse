/**
 *
 * --- ThangBT ---
 *
 */
import { Component, OnInit, OnDestroy, ViewChild, Renderer2, ElementRef, ViewChildren, QueryList } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { KitchenManagementService } from '../kitchen-management.service';
import { MessageService, ConfirmationService } from 'primeng/api';
import { Kitchen } from '../Entity/Kitchen';
import { Chef } from '../Entity/Chef';
import { AutoComplete } from 'primeng/autocomplete';
import { Title } from '@angular/platform-browser';
import { KitchenCondition } from '../Entity/Condition';
import { UserInfo } from '../../shared/userInfo';
import { Table } from 'primeng/table';
import { TokenStorage } from '../../shared/token.storage';
import { saveAs } from 'file-saver';

const TemplateID = 'VT_KC_';

@Component({
  selector: 'app-kitchen-config',
  templateUrl: './kitchen-config.component.html',
  styleUrls: ['./kitchen-config.component.css']
})
export class KitchenConfigComponent implements OnInit, OnDestroy {

  @ViewChild('myTable') private myTable: Table;
  @ViewChild('inputKitchenName') private inputKitchenName: ElementRef;
  @ViewChild('inputPlace') private inputKitchenPlace: AutoComplete;
  @ViewChild('inputChef') private inputChef: AutoComplete;
  @ViewChild('inputChefUserName') private inputChefUserName: ElementRef;
  @ViewChild('inputChefPhone') private inputChefPhone: ElementRef;
  @ViewChild('inputPrice') private inputPrice: ElementRef;
  @ViewChild('listPhone') private listPhone: ElementRef;
  @ViewChildren('inputChefList') inputChefList: QueryList<AutoComplete>;
  @ViewChildren('inputChefUserNameLst') inputChefUserNameLst: QueryList<ElementRef>;
  @ViewChildren('inputChefPhoneLst') inputChefPhoneLst: QueryList<ElementRef>;


  navigationSubscription;
  condition: KitchenCondition;
  uf: UserInfo;
  kitchenId: any;
  isClick: boolean;

  kitchenInfo: any;
  kitchenPrice: any;
  placeInfo: any;
  filterPlace: any[];
  kitchenStatus: boolean;
  chefInfo: any;
  filterChef: any[];
  chefUserName: string;
  chefPhone: string;
  kitchenNote: any;

  listKitchen: Kitchen[];

  isAdd: boolean;
  loading: boolean;
  totalRecord: number;
  startRow: number;
  rowSize: number;

  //add phone 
  display: boolean = false;
  count: number;
  countUp: number[] = [];
  listMatching: any;
  listPhoneNumber: any[] = [];

  displayChef: boolean = false;
  lstChefInfor: Chef[] = [];
  lstChefInforBackUp: Chef[] = [];
  getChefName : string;

  // tslint:disable-next-line:max-line-length
  onlyChar: RegExp = /^[0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;
  // tslint:disable-next-line:max-line-length
  notChar: RegExp = /[^0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;

  constructor(private _KitchenManagementService: KitchenManagementService, private messageService: MessageService,
    private confirmationService: ConfirmationService, private tokenStorage: TokenStorage,
    private title: Title, private renderer: Renderer2, private router: Router) {
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
    this.isAdd = true;
    this.loading = true;
    this.isClick = false;
    this.startRow = -1;
    this.rowSize = 1;
    this.totalRecord = 0;
    this.count = 0;
    this.listMatching = [""];
    this.title.setTitle('Cấu hình bếp - PMQTVP');

    this.lstChefInfor = [];
    this.lstChefInfor.push(new Chef(null, null, null, null));
    this.lstChefInforBackUp = [];
    this.lstChefInforBackUp.push(new Chef(null, null, null, null));

    this.getListKitchen();
  }

  //  edit kitchen name
  changeKitchenName() {
    if (this.notChar.test(this.inputKitchenName.nativeElement.value)) {
      this.inputKitchenName.nativeElement.value = this.inputKitchenName.nativeElement.value.replace(this.notChar, '');
    }
  }

  //  handle when focusing kitchen name field
  focusKitchenName() {
    this.renderer.setAttribute(this.inputKitchenName.nativeElement, 'maxlength', '255');
  }

  //  when edit price, format it
  changePrice(basePrice: string) {
    if (basePrice) {
      this.inputPrice.nativeElement.value = +basePrice;
    }
    //  remove all character is not a number and convert price to number
    if (/[^0-9]/g.test(this.inputPrice.nativeElement.value)) {
      this.inputPrice.nativeElement.value = +this.inputPrice.nativeElement.value.toString().replace(/[^0-9]/g, '');
    }
    //  remove all first 0 if number digit > 1
    if (!/^(?!(0))*[1-9][0-9]{0,}$/g.test(this.inputPrice.nativeElement.value.toString())) {
      this.inputPrice.nativeElement.value = this.inputPrice.nativeElement.value !== '' ?
      +this.inputPrice.nativeElement.value.toString().replace(/^0*/g, '') : '';
    }
    //  set max lenght if pasting
    if (this.inputPrice.nativeElement.value.length >= 11) {
      this.renderer.setAttribute(this.inputPrice.nativeElement, 'maxlength', '11');
    } else {
      //  set max lenght if typing
      this.renderer.setAttribute(this.inputPrice.nativeElement, 'maxlength', '14');
    }
    //  set limit value for price
    if (+this.inputPrice.nativeElement.value > 2000000000) {
      this.inputPrice.nativeElement.value = '2000000000';
    }
    //  add comma to separate number
    this.inputPrice.nativeElement.value = this.inputPrice.nativeElement.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    this.kitchenPrice = this.inputPrice.nativeElement.value;
    //  remove max length (only need for user paste value)
    this.renderer.removeAttribute(this.inputPrice.nativeElement, 'maxlength');
    return this.inputPrice.nativeElement.value;
  }

  //  when editing place field
  changePlace() {
    if (this.notChar.test(this.inputKitchenPlace.el.nativeElement.children[0].children[0].value)) {
      this.inputKitchenPlace.el.nativeElement.children[0].children[0].value = this.inputKitchenPlace.el.nativeElement
        .children[0].children[0].value.replace(this.notChar, '');
    }
  }

  //  show suggestion when typing in place
  loadPlace(event) {
    const object = {
      'placeName': event.query ? event.query.replace(this.notChar, '') : ''
    };
    this._KitchenManagementService.getKitchenPlace(object).subscribe(item => {
        this.filterPlace = item['data'];
    });
  }

  //  handle when focusing in chef field
  focusChef() {
    this.renderer.setAttribute(this.inputChef.el.nativeElement.children[0].children[0], 'maxlength', '100');
  }

  //  show suggestion when typing in chef field
  loadChef(event) {
    const object = {
      'query': event.query ? event.query.replace(this.notChar, '').trim() : '',
      'kitchenId': this.kitchenId
    };
    this._KitchenManagementService.getChef(object).subscribe(item => {
      this.filterChef = item['data'];
    });
  }

  //  handle when editing chef username
  changeChefUserName() {
    if (this.notChar.test(this.inputChefUserName.nativeElement.value)) {
      this.inputChefUserName.nativeElement.value = this.inputChefUserName.nativeElement.value.replace(this.notChar, '');
    }
  }

  //  when focusing chef username field
  focusChefUserName() {
    this.renderer.setAttribute(this.inputChefUserName.nativeElement, 'maxlength', '50');
  }

  //  handle when editing list phone to get sms
  changeChefPhoneGetSms(x: number) {
    const tmp: HTMLElement = this.listPhone.nativeElement;
    if (/[^0-9]/g.test(tmp.querySelectorAll('input')[x].value.toString())) {
      tmp.querySelectorAll('input')[x].value = tmp.querySelectorAll('input')[x].value.replace(/[^0-9]/g, '');
    }
    //  remove all character is not digit
    if (/[^0-9]/g.test(this.listPhoneNumber[x].toString())) {
      this.listPhoneNumber[x] = this.listPhoneNumber[x].replace(/[^0-9]/g, '');
    }
  }

  //  when click reset button
  resetButton() {
    this.isAdd = true;
    this.kitchenId = null;
    this.kitchenInfo = null;
    this.kitchenPrice = null;
    this.placeInfo = null;
    this.kitchenStatus = false;
    this.chefInfo = null;
    this.chefUserName = null;
    this.chefPhone = null;
    this.kitchenNote = null;
    this.lstChefInforBackUp = [];
    this.lstChefInforBackUp.push(new Chef(null, null, null, null));
    this.getChefName = '';
  }

  //  when click new page
  paginate(event) {
    this.loading = true;
    this.startRow = event.first;
    const object = {
      'startRow': event.first,
      'rowSize': 10
    };

    this._KitchenManagementService.getListKitchen(object).subscribe(item => {
      this.listKitchen = item['data'];
      this.rowSize = this.listKitchen.length;
      this.loading = false;
    });
  }

  //  get list kitchen
  getListKitchen() {
    this.loading = true;
    let component = this;

    let callback = () : void => {
      let account = component.tokenStorage.getAccount();
      const object = {
        'startRow': 0,
        'rowSize': 10,
        securityUsername: account.securityUsername,
        securityPassword: account.securityPassword
      };
      component._KitchenManagementService.countTotalKitchen(object).subscribe(item => {
        component.totalRecord = item['data'];

        if (component.myTable !== undefined) {
          component.myTable.reset();
        }
        component._KitchenManagementService.getListKitchen(object).subscribe(item2 => {
          component.listKitchen = item2['data'];

          component.startRow = 0;
          if (component.listKitchen != null && component.listKitchen.length >= 10) {
            component.rowSize = 10;
          } else {
            component.rowSize = component.listKitchen.length;
          }
          component.loading = false;
          component.isClick = true;
        });
      });
    }

    this.tokenStorage.getSecurityAccount(callback);
  }

  //  edit action
  doEdit(item: Kitchen) {
    this.isAdd = false;
    this.kitchenId = item.kitchenID;
    this.kitchenInfo = item.kitchenName;
    this.kitchenNote = item.note;
    this.kitchenStatus = item.status === 1;
    this.chefUserName = item.chefUserName;
    this.chefPhone = item.chefPhone;
    this.kitchenPrice = this.changePrice(item.price.toString());
    this.listPhoneNumber = item.listPhoneNumberReceiveSms;
    this.placeInfo = {
      'placeId': item.placeID,
      'placeName': item.placeName
    };
    this.chefInfo = {
      'chefName': item.chefName,
      'chefUserName': item.chefUserName,
      'chefPhone': item.chefPhone
    };

    if (item.chefName != null) {
      const names =  item.chefName.split(',');
      const codes =  item.chefUserName.split(',');
      const phones =  item.chefPhone.split(',');
      const isEmps =  item.isEmployee.split(',');

      this.lstChefInforBackUp = [];
      for (let i = 0; i < names.length; i++) {
        const chef = new Chef(null, null, null, null);
        chef.chefName = names[i];
        chef.chefUserName = codes[i];
        chef.chefPhone = phones[i];
        chef.isEmployee = Number(isEmps[i]);
        this.lstChefInforBackUp.push(chef);
      }
      this.setListChefName();
    }
  }

  //  get condition
  getCondition() {
    let chefname = null;

    if (this.chefInfo && this.chefInfo.length > 0) {
      chefname = this.chefInfo.replace(this.notChar, '').trim();
    } else if (this.chefInfo && this.chefInfo['chefName']) {
      chefname = this.chefInfo['chefName'];
    }

    const listPhoneNumberReceive: any[] = [];
    let count = 0;
    this.listPhoneNumber.forEach(element => {
      if (element.trim() !== '') {
        listPhoneNumberReceive[count++] = element;
      }
    });

    let account = this.tokenStorage.getAccount();
    this.condition = null;
    this.condition = {
      kitchenID: null,
      kitchenName: this.kitchenInfo ? this.kitchenInfo.replace(this.notChar, '').trim() : null,
      placeID: (this.placeInfo && this.placeInfo['placeId']) ? +this.placeInfo['placeId'] : null,
      chefName: chefname,
      chefUserName: this.chefUserName ? this.chefUserName.replace(this.notChar, '').trim() : null,
      chefPhone: this.chefPhone ? this.chefPhone.toString().replace(/[^0-9]/g, '') : null,
      note: this.kitchenNote ? this.kitchenNote.trim() : this.kitchenNote,
      price: this.kitchenPrice ? this.kitchenPrice.toString().replace(/[^0-9]/g, '') : null,
      status: this.kitchenStatus ? 1 : 0,
      listPhoneNumberReceiveSms: listPhoneNumberReceive,
      lstChef: this.lstChefInforBackUp,
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };

  }

  //  add kitchen
  addData() {
    if (this.checkBeforeAction()) {
      this.confirmationService.confirm({
        message: 'Đồng chí có muốn THÊM bếp này?',
        header: 'Thêm bếp',
        icon: 'pi pi-question-circle',
        acceptLabel: 'Thêm',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          this.getCondition();
          let account = this.tokenStorage.getAccount();
          const object = {
            action: '1',
            data: this.condition
          };
          this._KitchenManagementService.handleSetUpKitchen(object).subscribe(item => {
            if (item['status'] === 1) {
              this.resetButton();
              this.getListKitchen();
              this.alertMessage('success', 'Thông báo', 'Thêm bếp thành công');
            } else if (item['status'] === 0) {
              this.alertMessage('error', 'Cảnh báo', 'Thêm bếp thất bại');
            } else if (item['status'] === 2) {
              this.alertMessage('error', 'Cảnh báo', 'Bếp bị trùng');
              this.inputKitchenName.nativeElement.focus();
            } else if (item['status'] === 3) {
              this.alertMessage('error', 'Cảnh báo', 'Mã của bếp trưởng bị trùng');
              this.inputChefUserName.nativeElement.focus();
            } else if (item['status'] === 4) {
              this.alertMessage('error', 'Cảnh báo', 'Không được nhập ký tự đặc biệt');
            } else if (item['status'] === 5) {
              this.alertMessage('error', 'Cảnh báo', 'Vị trí bếp không tồn tại');
              this.inputKitchenPlace.domHandler.findSingle(this.inputKitchenPlace.el.nativeElement, 'input').focus();
            } else {
              this.alertMessage('error', 'Cảnh báo', 'Lỗi không xác định');
            }
          });
        }
      });
    }
  }

  //  update kitchen
  updateData() {
    if (this.checkBeforeAction()) {
      this.confirmationService.confirm({
        message: 'Đồng chí có muốn CẬP NHẬT bếp này?',
        header: 'Cập nhật bếp',
        icon: 'pi pi-question-circle',
        acceptLabel: 'Cập nhật',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          this.getCondition();
          this.condition.kitchenID = this.kitchenId;
          let account = this.tokenStorage.getAccount();

          const object = {
            action: '2',
            data: this.condition
          };
          this._KitchenManagementService.handleSetUpKitchen(object).subscribe(item => {
            if (item['status'] === 1) {
              this.resetButton();
              this.getListKitchen();
              this.alertMessage('success', 'Thông báo', 'Cập nhật bếp thành công');
            } else if (item['status'] === 0) {
              this.alertMessage('error', 'Cảnh báo', 'Cập nhật bếp thất bại');
            } else if (item['status'] === 2) {
              this.alertMessage('error', 'Cảnh báo', 'Bếp bị trùng');
              this.inputKitchenName.nativeElement.focus();
            } else if (item['status'] === 3) {
              this.alertMessage('error', 'Cảnh báo', 'Mã của bếp trưởng bị trùng');
              this.inputChefUserName.nativeElement.focus();
            } else if (item['status'] === 4) {
              this.alertMessage('error', 'Cảnh báo', 'Không được nhập ký tự đặc biệt');
            } else if (item['status'] === 5) {
              this.alertMessage('error', 'Cảnh báo', 'Vị trí bếp không tồn tại');
              this.inputKitchenPlace.domHandler.findSingle(this.inputKitchenPlace.el.nativeElement, 'input').focus();
            } else if (item['status'] === 9) {
              this.alertMessage('error', 'Cảnh báo', 'Cập nhật không thành công. Bếp này đã bị xóa');
            } else {
              this.alertMessage('error', 'Cảnh báo', 'Lỗi không xác định');
            }
          });
        }
      });
    }
  }

  //  delete kitchen
  doDelete(item: Kitchen) {
    this.confirmationService.confirm({
      message: 'Đồng chí có muốn XÓA bếp này?',
      header: 'Xóa bếp',
      icon: 'pi pi-question-circle',
      acceptLabel: 'Xóa',
      rejectLabel: 'Hủy bỏ',
      accept: () => {
        this.getCondition();
        this.condition.kitchenID = item.kitchenID;
        const object = {
          action: '3',
          data: this.condition
        };
        this._KitchenManagementService.handleSetUpKitchen(object).subscribe(itemResp => {
          if (itemResp['status'] === 1) {
            this.resetButton();
            this.getListKitchen();
            this.alertMessage('success', 'Thông báo', 'Xóa bếp thành công');
          } else if (itemResp['status'] === 0) {
            this.alertMessage('error', 'Cảnh báo', 'Xóa bếp thất bại');
          } else if (itemResp['status'] === 9) {
            this.alertMessage('error', 'Cảnh báo', 'Bếp này đã được xóa bởi người khác');
          } else {
            this.alertMessage('error', 'Cảnh báo', 'Lỗi không xác định');
          }
        });
      }
    });
  }

  //  export excel file
  exportExcel() {
    this._KitchenManagementService
      .getReportExcelKitchenInfo()
      .subscribe(res => {
        const fileName = 'BCTNB_' + this.tokenStorage.getUserInfo().userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
          + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours() +
          'h' + new Date().getMinutes() + 'm' + new Date().getSeconds()
          + 's' + new Date().getMilliseconds() + 'ms.xlsx';
        const file: Blob = new Blob([res], { type: 'application/xlsx' });
        saveAs(file, fileName);
      }, err => {
        // show the error
      });
  }

  //  check before action
  checkBeforeAction(): boolean {
    let isOk = true;

    if (!this.kitchenInfo) {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Tên bếp không được để trống');
      this.inputKitchenName.nativeElement.focus();
    } else if (!this.placeInfo) {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Vị trí bếp không được để trống');
      this.inputKitchenPlace.domHandler.findSingle(this.inputKitchenPlace.el.nativeElement, 'input').focus();
    } else if (!this.kitchenPrice) {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Đơn giá không được để trống');
      this.inputPrice.nativeElement.focus();
    } else {
      for (let i = 0; i < this.lstChefInforBackUp.length; i++) {
        if (this.lstChefInforBackUp[i].chefName == null) {
          isOk = false;
          this.alertMessage('error', 'Cảnh báo', 'Thông tin bếp trưởng không được để trống');
          break;
        }
      }
    }

    return isOk;
  }

  //  alert message
  alertMessage(severity: string, summary: string, detail: string) {
    this.messageService.add({
      severity: severity,
      summary: summary,
      detail: detail
    });
  }

  ngOnDestroy() {
    // avoid memory leaks here by cleaning up after ourselves. If we
    // don't then we will continue to run our initialiseInvites()
    // method on every navigationEnd event.
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

  //
  showDialog() {
    this.display = true;
    this.showPhoneNumberMatched();
  }

  showPhoneNumberMatched() {
    this.countUp = [];
    let count = 0;

    this.listPhoneNumber.forEach(element => {
      this.listPhoneNumber[count] = element;

      this.countUp.push(count++);
    });
    this.count = count - 1;

  }

  countup() {
    this.count = this.countUp.length - 1;
    this.count++;
    this.countUp.push(this.count);
  }

  resetCountUp(countUp: any) {
    let index = 0;
    const array = [];
    countUp.forEach(element => {

      element = index;
      array.push(element);
      index++;
    });
    this.countUp = array;
  }

  remove(k) {
    let index: any;
    if (k === 0) {
      index = 0;
    } else {
      index = this.countUp.findIndex(x => x === k);
    }

    if (this.countUp.length > 0) {
      this.count--;
      this.countUp.splice(index, 1);

      this.listPhoneNumber.splice(index, 1);
      this.resetCountUp(this.countUp);
    }

  }

  close() {
    this.display = false;
  }

  showDialogChef() {
    this.displayChef = true;
    this.lstChefInfor = JSON.parse(JSON.stringify(this.lstChefInforBackUp));
  }

  closeDlgChef() {
    this.displayChef = false;
  }

  indexTracker(index: number, value: any) {
    return index;
  }

  selectChef(index: number) {
    this.lstChefInfor[index].isEmployee  = 1;
  }

  changeChef(index: number) {
    const arrInputChef = this.inputChefList.toArray();
    const arrChefUserName = this.inputChefUserNameLst.toArray();
    const arrChefPhone = this.inputChefPhoneLst.toArray();

    if (this.notChar.test(arrInputChef[index].el.nativeElement.children[0].children[0].value)) {
      arrInputChef[index].el.nativeElement.children[0].children[0].value = arrInputChef[index].el.nativeElement
        .children[0].children[0].value.replace(this.notChar, '');
    }
    if (arrInputChef[index].el.nativeElement.children[0].children[0].value != null) {
      arrChefUserName[index].nativeElement.value = null;
      arrChefPhone[index].nativeElement.value = null;
    }

    this.lstChefInfor[index].isEmployee  = 0;
  }

  addChef() {
    this.lstChefInfor.push(new Chef(null, null, null, null));
  }

  setUpChefNotInVtNet() {
    const arrInputChef = this.inputChefList.toArray();
    const arrChefUserName = this.inputChefUserNameLst.toArray();
    const arrChefPhone = this.inputChefPhoneLst.toArray();

    for (let i = 0; i < this.lstChefInfor.length; i++) {
      if (this.lstChefInfor[i].chefUserName == null) {
        const chef = new Chef(null, null, null, null);
        chef.chefName = arrInputChef[i].el.nativeElement.children[0].children[0].value.trim();
        chef.chefUserName = arrChefUserName[i].nativeElement.value;
        chef.chefPhone = arrChefPhone[i].nativeElement.value;
        chef.isEmployee = 0;
        this.lstChefInfor[i] = chef;
      }
    }
  }

  removeChef(index: number) {
    this.lstChefInfor.splice(index, 1);
  }

  changeChefName(index: number) {
    const arrChefUserName = this.inputChefUserNameLst.toArray();
    if (this.notChar.test(arrChefUserName[index].nativeElement.value)) {
      arrChefUserName[index].nativeElement.value = arrChefUserName[index].nativeElement.value.replace(this.notChar, '');
    }
  }

  changeChefPhone(index: number) {
    const arrChefPhone = this.inputChefPhoneLst.toArray();
    if (/[^0-9]/g.test(arrChefPhone[index].nativeElement.value.toString())) {
      arrChefPhone[index].nativeElement.value = arrChefPhone[index].nativeElement.value.replace(/[^0-9]/g, '');
    }
    //  set maxlength to format
    this.renderer.setAttribute( arrChefPhone[index].nativeElement, 'maxlength', '10');
  }

  validateChef(showMessage: boolean) {
    const arrInputChef = this.inputChefList.toArray();
    const arrChefUserName = this.inputChefUserNameLst.toArray();
    const arrChefPhone = this.inputChefPhoneLst.toArray();

    for (let i = 0; i < arrInputChef.length; i++) {
      if (arrInputChef[i].el.nativeElement.children[0].children[0].value == null || arrInputChef[i].el.nativeElement.children[0].children[0].value === '') {
        if (showMessage) {
          this.alertMessage('error', 'Cảnh báo', 'Bếp trưởng không được để trống');
        }
        arrInputChef[i].domHandler.findSingle(arrInputChef[i].el.nativeElement, 'input').focus();
        return false;
      }

      if (arrChefUserName[i].nativeElement.value == null || arrChefUserName[i].nativeElement.value === '') {
        if (showMessage) {
          this.alertMessage('error', 'Cảnh báo', 'Mã nhân viên không được để trống');
        }
        arrChefUserName[i].nativeElement.focus();
        return false;
      }

      if (arrChefPhone[i].nativeElement.value == null || arrChefPhone[i].nativeElement.value === '') {
        if (showMessage) {
          this.alertMessage('error', 'Cảnh báo', 'Số điện thoại không được để trống');
        }
        arrChefPhone[i].nativeElement.focus();
        return false;
      }
    }

    for (let i = 0; i < arrInputChef.length; i++) {
      for (let j = 0; j < arrInputChef.length && j !== i; j++) {
        if (arrChefUserName[i].nativeElement.value === arrChefUserName[j].nativeElement.value) {
          if (showMessage) {
            this.alertMessage('error', 'Cảnh báo', 'Mã bếp trưởng bị trùng');
          }
          arrChefUserName[i].nativeElement.focus();
          return false;
        }
      }
    }

    return true;
  }

  validateAddChef() {
    if (this.validateChef(true)) {
      this.setUpChefNotInVtNet();
      this.lstChefInforBackUp = JSON.parse(JSON.stringify(this.lstChefInfor));
      this.displayChef = false;
      this.setListChefName();
    }
  }

  setListChefName() {
    if (this.lstChefInforBackUp.length > 0) {
      this.getChefName = '';
      for (let i = 0; i < this.lstChefInforBackUp.length; i++) {
        this.getChefName += this.lstChefInforBackUp[i].chefName + ', ';
      }

      if (this.getChefName.length > 0) {
        this.getChefName = this.getChefName.substr(0, this.getChefName.length - 2);
      }
    }
  }

}
