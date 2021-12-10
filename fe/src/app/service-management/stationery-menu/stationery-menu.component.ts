import { Component, OnInit, OnDestroy, AfterViewInit, ViewChild, Renderer2, ElementRef } from '@angular/core';
import { Table } from 'primeng/table';
import { Title } from '@angular/platform-browser';
import { Router, NavigationEnd } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/components/common/api';
import { ServiceDataService } from '../service-data.service';
import { AutoComplete } from 'primeng/autocomplete';
import { Stationery } from '../Entity/ListStationery';
import { Dropdown } from 'primeng/primeng';
import {TokenStorage} from '../../shared/token.storage';
import { saveAs } from 'file-saver';
import {UserInfo} from '../../shared/UserInfo';

@Component({
  selector: 'app-stationery-menu',
  templateUrl: './stationery-menu.component.html',
  styleUrls: ['./stationery-menu.component.css']
})
export class StationeryMenuComponent implements OnInit, OnDestroy, AfterViewInit {

  @ViewChild('myTable') private myTable: Table;
  @ViewChild('inputStationeryName') private inputName: AutoComplete;
  @ViewChild('inputPrice') private inputPrice: ElementRef;
  @ViewChild('inputStationeryType') private inputStationeryType: Dropdown;
  @ViewChild('inputUnitCalculation') private inputUnitCalculation: Dropdown;

  navigationSubscription;
  condition: Stationery;
  isClick: boolean;

  listStationery: Stationery[];
  stationeryId: any;
  stationeryTypes: any[];
  selectedType: any;
  stationeryInfo: any;
  filterStationery: any[];
  stationeryPrice: any;
  unitCalculation: any[];
  selectedUnitCal: any;
  statusStationery: string;

  loading: boolean;
  loading2: boolean;
  isAdd: boolean;
  totalRecord: number;
  startRow: number;
  rowSize: number;
  uf: UserInfo;
  showImportForm: boolean;
  uploadedFiles: any[] = [];

  notiImportFile : any;
  file: Blob;
  // tslint:disable-next-line:max-line-length
  onlyChar: RegExp = /^[0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;
  // tslint:disable-next-line:max-line-length
  notChar: RegExp = /[^0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;

  constructor(private _ServiceDataService: ServiceDataService, private confirmationService: ConfirmationService, private tokenStorage: TokenStorage,
    private messageService: MessageService, private title: Title, private renderer: Renderer2, private router: Router) {
    this.navigationSubscription = this.router.events.subscribe(event => {
      if (!(event instanceof NavigationEnd)) { return; }
      // Do what you need to do here, for instance :
      this.resetButton();
      if (this.isClick) {
        this.ngOnInit();
        this.ngAfterViewInit();
      }
    });
  }

  ngOnInit() {
    this.isClick = false;
    this.loading = true;
    this.loading2 = false;
    this.isAdd = true;
    this.statusStationery = '1';
    this.startRow = -1;
    this.rowSize = 1;
    this.totalRecord = 0;
    this.title.setTitle('Danh mục vật tư thiết bị - PMQTVP');
    this.getStationeryType();
    this.getStationeryUnitCal();
    this.uf = this.tokenStorage.getUserInfo();
    this.notiImportFile = null;
  }

  //  when edit data in stationery field name
  changeStationery() {
    if (this.notChar.test(this.inputName.el.nativeElement.children[0].children[0].value)) {
      this.inputName.el.nativeElement.children[0].children[0].value = this.inputName.el.nativeElement.
        children[0].children[0].value.replace(this.notChar, '');
    }
  }

  //  when edit data in stationery price, format price
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
      //  set max length if typing
      this.renderer.setAttribute(this.inputPrice.nativeElement, 'maxlength', '14');
    }
    //  set limit value for price
    if (+this.inputPrice.nativeElement.value > 2000000000) {
      this.inputPrice.nativeElement.value = '2000000000';
    }
    //  add comma to separate number
    this.inputPrice.nativeElement.value = this.inputPrice.nativeElement.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    this.stationeryPrice = this.inputPrice.nativeElement.value;
    //  remove max length (only need for user paste value)
    this.renderer.removeAttribute(this.inputPrice.nativeElement, 'maxlength');
    return this.inputPrice.nativeElement.value;
  }

  //  when focus stationery name
  focusStationeryName() {
    if (!this.isAdd) {
      this.renderer.setAttribute(this.inputName.el.nativeElement.children[0].children[0], 'disabled', 'true');
      this.renderer.setAttribute(this.inputName.el.nativeElement.children[0].children[1], 'disabled', 'true');
    }
    this.renderer.setAttribute(this.inputName.el.nativeElement.children[0].children[0], 'maxlength', '50');
  }

  //  when click 'x' icon of stationery name field
  clearStationery(event) {
    if (this.stationeryInfo) {
      if (event != null) {
        event.stopPropagation();
      }
      this.stationeryInfo = null;
      this.loading2 = false;
    }
  }

  //  get list of stationery type
  getStationeryType() {
    this._ServiceDataService.getCodeStationery('S008').subscribe(item => {
      this.stationeryTypes = item['data'];
    });
  }

  //  get list of stationery unit calculation
  getStationeryUnitCal() {
    this._ServiceDataService.getCodeStationery('S009').subscribe(item => {
      this.unitCalculation = item['data'];
    });
  }

  //  load stationery name and id
  loadStationery(event) {
    const object = {
      'stationeryName': event.query ? event.query.replace(this.notChar, '') : ''
    };
    this.loading2 = false;
    this._ServiceDataService.getStationeryNameId(object).subscribe(item => {
      this.filterStationery = item['data'];
      this.loading2 = true;
    });
  }

  //  edit data
  doEdit(item: Stationery) {
    this.isAdd = false;
    this.stationeryId = item.stationeryId;
    this.statusStationery = item.status;
    this.stationeryPrice = this.changePrice(item.stationeryPrice + '');

    this.stationeryInfo = {
      'stationeryId': item.stationeryId,
      'stationeryName': item.stationeryName
    };

    //  stationery type
    for (let i = 0; i < this.stationeryTypes.length; i++) {
      if (this.stationeryTypes[i].codeValue === item.codeTypeValue) {
        this.selectedType = this.stationeryTypes[i];
        break;
      }
    }

    //  stationery unit calculation
    for (let i = 0; i < this.unitCalculation.length; i++) {
      if (this.unitCalculation[i].codeValue === item.codeUnitValue) {
        this.selectedUnitCal = this.unitCalculation[i];
        break;
      }
    }
  }

  //  handle condition
  getConditions() {
    let stationeryid = null;
    let stationeryname = null;
    let stationeryprice = -1;

    if (this.stationeryInfo && this.stationeryInfo.length > 0) {
      stationeryname = this.stationeryInfo.replace(this.notChar, '').trim();
    } else if (this.stationeryInfo && this.stationeryInfo['stationeryId']) {
      stationeryid = this.stationeryInfo['stationeryId'].trim();
    }

    if (this.stationeryPrice !== undefined && this.stationeryPrice != null && this.stationeryPrice !== '') {
      stationeryprice = this.stationeryPrice.toString().replace(/[^0-9]/g, '');
    }
    this.condition = null;
    this.condition = {
      stationeryId: stationeryid,
      stationeryName: stationeryname,
      stationeryType: this.selectedType ? this.selectedType['codeValue'] : null,
      stationeryUnitCal: this.selectedUnitCal ? this.selectedUnitCal['codeValue'] : null,
      stationeryPrice: stationeryprice,
      codeTypeValue: null,
      codeUnitValue: null,
      masterClassType: 'S008',
      masterClassUnit: 'S009',
      status: this.statusStationery,
      startRow: 0,
      rowSize: 10,
      securityUsername: this._ServiceDataService.getAccount().securityUsername,
      securityPassword: this._ServiceDataService.getAccount().securityPassword
    };
  }

  //  get list search menu service
  getListStationery() {
    this.loading = true;
    if (this.myTable !== undefined) {
      this.myTable.reset();
    }

    this._ServiceDataService.getReportStationery(this.condition).subscribe(item => {
      this.listStationery = item['data'];
      this.startRow = 0;
      if (this.listStationery && this.listStationery.length >= 10) {
        this.rowSize = 10;
      } else {
        this.rowSize = this.listStationery.length;
      }

      if (this.listStationery === null || this.listStationery.length === 0) {
        // if (this.isClick) {
        //   this.alertMessage('info', 'Thông báo', 'Không tìm thấy bản ghi tương ứng');
        // }

        this.totalRecord = 0;
      }

      this.isClick = true;
      this.loading = false;
    });
  }

  //  when click new page
  paginate(event) {
    this.loading = true;
    this.startRow = event.first;
    this.condition.startRow = event.first;
    this.condition.rowSize = 10;

    this._ServiceDataService.getReportStationery(this.condition).subscribe(item => {
      this.listStationery = item['data'];
      this.rowSize = this.listStationery.length;
      this.loading = false;
    });
  }

  //  when click search button
  searchData() {
    this.getConditions();
    this._ServiceDataService.countTotalRecordStationery(this.condition).subscribe(item => {
      this.totalRecord = item['data'];
      this.getListStationery();
    });
  }

  exportStatineryReportExcel() {
    this.getConditions();
    this.condition.startRow = -1;
    this._ServiceDataService.countTotalRecordStationery(this.condition).subscribe(item => {
      const numOfRow = item['data'];
      if (numOfRow <= 50000) {
        this._ServiceDataService.exportExcelstationeryReport(this.condition).subscribe(
            (next: ArrayBuffer) => {
              const fileName = 'DSVTTB_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
                  + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
                  + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
              const file: Blob = new Blob([next], {type: 'application/xlsx'});
              saveAs(file, fileName);
            }
        );
      } else {
        this.messageService.add({
          severity: 'warn',
          summary: 'Không thể xuất file',
          detail: 'Số bản ghi tìm thấy vượt quá 50000'
        });
      }
    });
  }

  //  when click add button
  addData() {
    if (this.checkBeforeDoAction()) {
      this.confirmationService.confirm({
        message: 'Đồng chí có muốn THÊM vật tư thiết bị này?',
        header: 'Thêm vật tư thiết bị',
        icon: 'pi pi-question-circle',
        acceptLabel: 'Thêm',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          let stationeryname = null;
          if (this.stationeryInfo && this.stationeryInfo.length > 0) {
            stationeryname = this.stationeryInfo.replace(this.notChar, '').trim();
          } else if (this.stationeryInfo && this.stationeryInfo['stationeryId']) {
            stationeryname = this.stationeryInfo['stationeryName'].trim();
          }

          const object = {
            'action': 1,
            'data': {
              'stationeryName': stationeryname,
              'stationeryType': this.selectedType ? this.selectedType['codeValue'] : null,
              'status': this.statusStationery,
              'stationeryPrice': + this.stationeryPrice.toString().replace(/[^0-9]/g, ''),
              'stationeryUnitCal': this.selectedUnitCal ? this.selectedUnitCal['codeValue'] : null,
              'securityUsername': this._ServiceDataService.getAccount().securityUsername,
              'securityPassword': this._ServiceDataService.getAccount().securityPassword
            }
          };
          this._ServiceDataService.handleSetUpStationery(object).subscribe(item => {
            if (item['status'] === 1) {
              this.resetButton();
             // this.searchData();
              this.alertMessage('success', 'Thông báo', 'Thêm vật tư thiết bị thành công');
            } else if (item['status'] === 0) {
              this.alertMessage('error', 'Cảnh báo', 'Thêm vật tư thiết bị thất bại');
            } else if (item['status'] === 2) {
              this.alertMessage('error', 'Cảnh báo', 'Vật tư thiết bị đã tồn tại');
              this.inputName.domHandler.findSingle(this.inputName.el.nativeElement, 'input').focus();
            } else {
              this.alertMessage('error', 'Cảnh báo', 'Có lỗi không xác định');
            }
          });
        }
      });
    }
  }

  //  when click add button
  updateData() {
    if (this.checkBeforeDoAction()) {
      this.confirmationService.confirm({
        message: 'Đồng chí có muốn CẬP NHẬT vật tư thiết bị này?',
        header: 'Cập nhật vật tư thiết bị',
        icon: 'pi pi-question-circle',
        acceptLabel: 'Cập nhật',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          let stationeryname = null;
          if (this.stationeryInfo && this.stationeryInfo.length > 0) {
            stationeryname = this.stationeryInfo.replace(this.notChar, '').trim();
          } else if (this.stationeryInfo && this.stationeryInfo['stationeryId']) {
            stationeryname = this.stationeryInfo['stationeryName'].trim();
          }

          const object = {
            'action': 2,
            'data': {
              'stationeryId': this.stationeryId,
              'stationeryName': stationeryname,
              'stationeryType': this.selectedType ? this.selectedType['codeValue'] : null,
              'status': this.statusStationery,
              'stationeryPrice': +this.stationeryPrice.toString().replace(/[^0-9]/g, ''),
              'stationeryUnitCal': this.selectedUnitCal ? this.selectedUnitCal['codeValue'] : null,
              'securityUsername': this._ServiceDataService.getAccount().securityUsername,
              'securityPassword': this._ServiceDataService.getAccount().securityPassword
            }
          };

          this._ServiceDataService.handleSetUpStationery(object).subscribe(item => {
            if (item['status'] === 1) {
              this.resetButton();
              //this.searchData();
              this.alertMessage('success', 'Thông báo', 'Cập nhật vật tư thiết bị thành công');
            } else if (item['status'] === 0) {
              this.alertMessage('error', 'Cảnh báo', 'Cập nhật vật tư thiết bị thất bại');
            } else if (item['status'] === 2) {
              this.alertMessage('error', 'Cảnh báo', 'Vật tư thiết bị đã tồn tại');
              this.inputName.domHandler.findSingle(this.inputName.el.nativeElement, 'input').focus();
            } else if (item['status'] === 3) {
              this.alertMessage('error', 'Cảnh báo', 'Vật tư thiết bị đang được sử dụng');
            } else if (item['status'] === 4) {
              this.alertMessage('error', 'Cảnh báo', 'Vật tư thiết bị không tồn tại');
            } else if (item['status'] === 15) {
              this.searchData();
              this.alertMessage('error', 'Cảnh báo', 'Vật tư thiết bị đã bị xóa bởi người khác');
            } else {
              this.alertMessage('error', 'Cảnh báo', 'Có lỗi không xác định');
            }
          });
        }
      });
    }
  }

  //  delete stationery
  deleteData(item: Stationery) {
    this.confirmationService.confirm({
      message: 'Đồng chí có muốn XÓA vật tư thiết bị này?',
      header: 'Xóa vật tư thiết bị',
      icon: 'pi pi-question-circle',
      acceptLabel: 'Xóa',
      rejectLabel: 'Hủy bỏ',
      accept: () => {
        const object = {
          'action': 3,
          'data': {
            'stationeryId': item.stationeryId,
            'securityUsername': this._ServiceDataService.getAccount().securityUsername,
            'securityPassword': this._ServiceDataService.getAccount().securityPassword
          }
        };

        this._ServiceDataService.handleSetUpStationery(object).subscribe(itemResp => {
          if (itemResp['status'] === 1) {
            if (this.stationeryId === item.stationeryId) {
              this.resetButton();
              //this.searchData();
            } else {
              this.condition = null;
              this.condition = {
                stationeryId: null,
                stationeryName: null,
                stationeryType: null,
                stationeryUnitCal: null,
                stationeryPrice: -1,
                codeTypeValue: null,
                codeUnitValue: null,
                masterClassType: 'S008',
                masterClassUnit: 'S009',
                status: this.statusStationery,
                startRow: 0,
                rowSize: 10,
                securityUsername: this._ServiceDataService.getAccount().securityUsername,
                securityPassword: this._ServiceDataService.getAccount().securityPassword
              };
              console.log(this.condition.status);

              this._ServiceDataService.countTotalRecordStationery(this.condition).subscribe(itemResp1 => {
                this.totalRecord = itemResp1['data'];
                this.getListStationery();
              });
            }
            this.alertMessage('success', 'Thông báo', 'Xóa vật tư thiết bị thành công');
          } else if (itemResp['status'] === 0) {
            this.alertMessage('error', 'Cảnh báo', 'Xóa vật tư thiết bị thất bại');
          } else if (itemResp['status'] === 4) {
            this.searchData();
            this.alertMessage('error', 'Cảnh báo', 'Vật tư thiết bị xóa bởi người khác');
          } else if (itemResp['status'] === 3) {
            this.alertMessage('error', 'Cảnh báo', 'Vật tư thiết bị đang được sử dụng');
          } else {
            this.alertMessage('error', 'Cảnh báo', 'Có lỗi không xác định');
          }
        });
      }
    });
  }

  //  check before add
  checkBeforeDoAction(): boolean {
    let isOk = true;
    const valName = this.inputName.el.nativeElement.children[0].children[0].value;

    if (!this.stationeryInfo || (valName && valName.trim().length === 0)) {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Tên vật tư thiết bị không được để trống');
      this.inputName.domHandler.findSingle(this.inputName.el.nativeElement, 'input').focus();
    } else if (!this.selectedType) {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Chủng loại không được để trống');
      this.inputStationeryType.focus();
    } else if (!this.stationeryPrice || this.stationeryPrice.length === 0 || this.stationeryPrice === 0 || this.stationeryPrice === '0') {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Đơn giá không được để trống hoặc phải lớn hơn 0');
      this.inputPrice.nativeElement.focus();
    } else if (!this.selectedUnitCal) {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Đơn vị tính không được để trống');
      this.inputUnitCalculation.focus();
    } else if (this.stationeryPrice.toString().replace(/[^0-9]/g, '') === 0) {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Đơn giá không hợp lệ');
      this.inputPrice.nativeElement.focus();
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

  //  when click reset button
  resetButton() {
    this.isAdd = true;
    this.stationeryId = null;
    this.stationeryInfo = null;
    this.selectedType = null;
    this.stationeryPrice = null;
    this.selectedUnitCal = null;
    this.statusStationery = '1';
    this.searchData();
  }

  ngAfterViewInit() {
    this.searchData();
  }

  ngOnDestroy() {
    // avoid memory leaks here by cleaning up after ourselves. If we
    // don't then we will continue to run our initialiseInvites()
    // method on every navigationEnd event.
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

  openImportExcel() {
    this.showImportForm = true;
  }
  exportTemplateExcel() {
    this._ServiceDataService.templateImportExcelStationeryReport({}).subscribe(
        (res: Response) => {
          var fileName = 'Import_stationery_template.xlsx';
          const file: Blob = new Blob([res.body], {type: 'application/xlsx'});
          saveAs(file, fileName);
         // this.loading = false;
        }
    );
  }

  uploadExcel(event: any) {
    this.notiImportFile = null;
    var formData = new FormData();
    var file = event.files[0];
    formData.append('file', file);

    this._ServiceDataService.importExcelStationeryReport(formData).subscribe((res: Response) => {
      var mess = res.headers.get('messageCode');
      if (mess === 'failFormat') {
        this.notiImportFile = 'Format file import không chính xác. Vui lòng kiểm tra lại.';
      }else if(mess === 'empty'){
        this.notiImportFile = 'File chưa có dữ liệu import.';
      }else if (mess === 'fail') {
        this.notiImportFile = 'Import không thành công. Tải về file kết quả để kiểm tra lại.';
        this.file = new Blob([res.body], {type: 'application/xlsx'});
        this.searchData();
      }else if (mess === 'success') {
         this.notiImportFile = 'Import thành công.';
        this.file = new Blob([res.body], {type: 'application/xlsx'});
        this.searchData();
      } else {
        this.alertMessage('error', 'Cảnh báo', 'Import thất bại');
      }
      this.loading = false;
    });
  }

  exportImportResult(){
    var fileName = 'Import_stationery_result.xlsx';
    saveAs(this.file, fileName);
  }

  clearExcel(){
    this.notiImportFile = null;
  }
  clearImportExcel(){
    this.showImportForm= false;
    this.uploadedFiles = [];
    this.notiImportFile = null;

  }

}
