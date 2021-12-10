/**
 *
 * --- ThangBT ---
 *
 */
import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core';
import { KitchenManagementService } from '../kitchen-management.service';
import { ConfirmationService, MessageService, SelectItem } from 'primeng/components/common/api';
import { DayOff } from '../Entity/ListDayOff';
import { Table } from 'primeng/table';
import { Title } from '@angular/platform-browser';
import { Router, NavigationEnd } from '@angular/router';
import { MultiSelect } from 'primeng/multiselect';
import { Calendar } from 'primeng/primeng';
import {TokenStorage} from "../../shared/token.storage";

@Component({
  selector: 'app-dayoffconfig',
  templateUrl: './dayoffconfig.component.html',
  styleUrls: ['./dayoffconfig.component.css']
})
export class DayoffconfigComponent implements OnInit, OnDestroy {

  @ViewChild('myTable')
  private myTable: Table;
  @ViewChild('multiSelect')
  private multiSelect: MultiSelect;
  @ViewChild('inputDate') inputDate;
  @ViewChild('inputDescription') private inputDescription: ElementRef;

  navigationSubscription;

  passDayOff: boolean;
  canDayOff: boolean;
  dayOff: Date;
  minDate: Date;
  dayOffDescription: string;
  vn = {
    firstDayOfWeek: 1,
    dayNames: ['Thứ hai', 'Thứ 3', 'Thứ 4', 'Thứ 5', 'Thứ 6', 'Thứ 7', 'Chủ Nhật'],
    dayNamesShort: ['CN', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7'],
    dayNamesMin: ['CN', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7'],
    monthNames: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
      'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
    monthNamesShort: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
      'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
    today: 'Hôm nay',
    clear: 'Xóa'
  };

  listDayOff: DayOff[];
  selectedDay: DayOff;
  years: SelectItem[] = [];
  selectedYears: string[] = [];

  isEmpty: boolean;
  loading: boolean;
  isSave: boolean;
  totalRecord: number;
  startRow: number;
  rowSize: number;

  constructor(private _KitchenManagementService: KitchenManagementService, private confirmationService: ConfirmationService,
    private messageService: MessageService, private title: Title, private router: Router, private tokenStorage: TokenStorage) {
    this.navigationSubscription = this.router.events.subscribe(event => {
      if (!(event instanceof NavigationEnd)) { return; }
      // Do what you need to do here, for instance :
      this.resetButton();
      this.ngOnInit();
    });
  }

  ngOnInit() {
    this.loading = true;
    this.isSave = false;
    this.passDayOff = false;
    this.dayOff = null;
    this.canDayOff = true;
    this.minDate = new Date();
    this.dayOffDescription = null;
    this.startRow = -1;
    this.rowSize = 1;
    this.totalRecord = 0;
    this.title.setTitle('Cấu hình ngày nghỉ - PMQTVP');

    let component = this;
    let callback = () : void => {
      component.getListDayOff();
    }
    this.tokenStorage.getSecurityAccount(callback);
  }

  //  when select row
  onRowSelect() {
    const tempDate = new Date(new Date(this.selectedDay.dayOff).getFullYear() + '/' + (new Date(this.selectedDay.dayOff).getMonth() + 1)
      + '/' + new Date(this.selectedDay.dayOff).getDate() + ' 23:59:59');
    this.passDayOff = false;
    if (tempDate < new Date()) {
      this.passDayOff = true;
    }
    this.isSave = true;
    this.dayOff = tempDate;
    this.canDayOff = this.selectedDay.status === 1;
    this.dayOffDescription = this.selectedDay.description;
  }

  //  when selecting year to filter
  filterYear() {

    if (!this.isSelectedYearsHasValue()) {
      return;
    }

    const object = {
      'startRow': 0,
      'rowSize': 10,
      'listYears': this.selectedYears
    };
    this._KitchenManagementService.countTotalDayOff(object).subscribe(item => {
      this.totalRecord = item['data'];

      this.loading = true;
      if (this.myTable !== undefined) {
        this.myTable.reset();
      }
      this._KitchenManagementService.getListDayOff(object).subscribe(item2 => {
        this.listDayOff = item2['data'];

        this.startRow = 0;
        if (this.listDayOff != null && this.listDayOff.length >= 10) {
          this.rowSize = 10;
        } else {
          this.rowSize = this.listDayOff.length;
        }

        if (this.listDayOff == null || this.listDayOff.length === 0) {
          this.isEmpty = true;
        } else {
          this.isEmpty = false;
        }

        this.loading = false;
      });
    });
  }

  onInputYear() {
    const tmp: HTMLInputElement = this.myTable.el.nativeElement.querySelector('input.ui-inputtext');
    const tmp1: HTMLSpanElement = this.myTable.el.nativeElement.querySelector('span');
    tmp.setAttribute('maxlength', '4');
    tmp.value = '';
    this.multiSelect.onFilter({target: {value: ''}});
    tmp.oninput = () => {
      if (/[^0-9]/g.test(tmp.value.toString())) {
        tmp.value = tmp.value.replace(/[^0-9]/g, '');
      }
    };
  }

  //  add dayoff
  addData() {
    if (this.checkBeforeAdd()) {
      this.confirmationService.confirm({
        message: 'Đồng chí có muốn THÊM ngày nghỉ này?',
        header: 'Thêm ngày nghỉ',
        icon: 'pi pi-question-circle',
        acceptLabel: 'Thêm',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          const object = {
            'dayOff': this.dayOff,
            'description': this.dayOffDescription.trim(),
            'status': this.canDayOff ? 1 : 0
          };
          this._KitchenManagementService.insertDayOff(object).subscribe(item => {
            if (item['status'] === 1) {
              this.getListDayOff();
              this.resetButton();
              this.alertMessage('success', 'Thông báo', 'Đã thêm mới ngày nghỉ');
            } else if (item['status'] === 0) {
              this.alertMessage('error', 'Cảnh báo', 'Thêm ngày nghỉ thất bại');
            } else if (item['status'] === 2) {
              this.alertMessage('error', 'Cảnh báo', 'Đã tồn tại ngày nghỉ ' + new Date(this.dayOff).getDate() + '/'
                + (new Date(this.dayOff).getMonth() + 1) + '/' + new Date(this.dayOff).getFullYear());
              this.inputDate.showOverlay(this.inputDate.nativeElement);
            } else {
              this.alertMessage('error', 'Cảnh báo', 'Có lỗi không xác định');
            }
          });
        }
      });
    }
  }

  //  update dayoff
  saveData() {
    if (this.selectedDay) {
      if (this.checkBeforeAdd()) {
        this.confirmationService.confirm({
          message: 'Đồng chí có muốn CẬP NHẬT ngày nghỉ này?',
          header: 'Cập nhật ngày nghỉ',
          icon: 'pi pi-question-circle',
          acceptLabel: 'Cập nhật',
          rejectLabel: 'Hủy bỏ',
          accept: () => {
            const object = {
              'dayOffId': this.selectedDay.dayOffId,
              'dayOff': this.dayOff,
              'description': this.dayOffDescription.trim(),
              'status': this.canDayOff ? 1 : 0
            };
            this._KitchenManagementService.updateDayOff(object).subscribe(item => {
              if (item['status'] === 1) {
                this.getListDayOff();
                this.resetButton();
                this.alertMessage('success', 'Thông báo', 'Đã cập nhật ngày nghỉ');
              } else if (item['status'] === 0) {
                this.alertMessage('error', 'Cảnh báo', 'Cập nhật ngày nghỉ thất bại');
              } else if (item['status'] === 2) {
                this.alertMessage('error', 'Cảnh báo', 'Đã tồn tại ngày nghỉ ' + new Date(this.dayOff).getDate() + '/'
                  + (new Date(this.dayOff).getMonth() + 1) + '/' + new Date(this.dayOff).getFullYear());
                this.inputDate.showOverlay(this.inputDate.nativeElement);
              } else {
                this.alertMessage('error', 'Cảnh báo', 'Có lỗi không xác định');
              }
            });
          }
        });
      }
    } else {
      this.alertMessage('error', 'Cảnh báo', 'Chưa chọn bản ghi');
    }
  }

  //  copy dayoff
  copyData() {
    const object = {
      'startRow': -1,
      'rowSize': -1
    };
    this._KitchenManagementService.getListDayOff(object).subscribe(item => {
      if (item['data'] && item['data'].length > 0) {
        this.confirmationService.confirm({
          message: 'Đồng chí có muốn SAO CHÉP các ngày nghỉ ?',
          header: 'Sao chép ngày nghỉ',
          icon: 'pi pi-question-circle',
          acceptLabel: 'Sao chép',
          rejectLabel: 'Hủy bỏ',
          accept: () => {
            const listdayoff: DayOff[] = [];
            for (let i = 0; i < item['data'].length; i++) {
              listdayoff.push(item['data'][i]);
              listdayoff[i].dayOff = new Date((new Date(listdayoff[i].dayOff).getFullYear() + 1) + '-'
                + (new Date(listdayoff[i].dayOff).getMonth() + 1) + '-' + new Date(listdayoff[i].dayOff).getDate() + ' 23:59:59');
            }

            this._KitchenManagementService.copyDayOff(listdayoff).subscribe(item1 => {
              if (item1['status'] === 1) {
                this._KitchenManagementService.findAllYears().subscribe(item2 => {
                  const data: string[] = item2['data'];
                  this.years = [];
                  for (let i = 0; i < data.length; i++) {
                    this.years.push({
                      label: data[i],
                      value: data[i]
                    });
                  }
                });
                this.alertMessage('success', 'Thông báo', 'Đã sao chép ngày nghỉ');
              } else if (item1['status'] === 0) {
                this.alertMessage('error', 'Cảnh báo', 'Sao chép ngày nghỉ thất bại');
              } else {
                this.alertMessage('error', 'Cảnh báo', 'Có lỗi không xác định');
              }
            });
          }
        });
      } else {
        this.alertMessage('warn', 'Thông báo', 'Không có dữ liệu để sao chép');
      }
    });
  }

  //  check before add
  checkBeforeAdd(): boolean {
    let isOk = true;
    if (!this.dayOff) {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Ngày không được để trống');
      setTimeout(() => {
        this.inputDate.showOverlay(this.inputDate.nativeElement);
      }, 0);
    } else if (!this.dayOffDescription || this.dayOffDescription.trim().length === 0) {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Mô tả không được để trống');
      this.inputDescription.nativeElement.focus();
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
    this.isSave = false;
    this.selectedDay = null;
    this.dayOff = null;
    this.canDayOff = true;
    this.dayOffDescription = null;
    this.passDayOff = false;
  }

  //  when click new page
  paginate(event) {
    if (!this.isSelectedYearsHasValue()) {
      return;
    }
    this.loading = true;
    setTimeout(() => {
      this.startRow = event.first;
      const object = {
        'startRow': event.first,
        'rowSize': 10,
        'listYears': this.selectedYears
      };

      this._KitchenManagementService.getListDayOff(object).subscribe(item => {
        this.listDayOff = item['data'];
        this.rowSize = this.listDayOff.length;
        this.loading = false;
      });
    }, 500);
  }

  getListDayOff() {

      this.loading = true;
      //  get all year to select
      this._KitchenManagementService.findAllYears().subscribe(item1 => {
        const data: string[] = item1['data'];
        this.years = [];
        for (let i = 0; i < data.length; i++) {
          this.years.push({
            label: data[i],
            value: data[i]
          });
        }
        console.log(this.selectedYears);
        // set select default
        if (!this.selectedYears || this.selectedYears.length === 0) {
          for (let x = 0; x < this.years.length; x++) {
            const element = this.years[x];
            if (element.value === '' + new Date().getFullYear()) {
              this.selectedYears = [new Date().getFullYear() + ''];
            }
          }
          // this.selectedYears = [this.years[0].value];
        }

        if (!this.isSelectedYearsHasValue()) {
          return;
        }

        const object = {
          'startRow': 0,
          'rowSize': 10,
          'listYears': this.selectedYears
        };

        this.loading = true;
        this._KitchenManagementService.countTotalDayOff(object).subscribe(item => {
        this.totalRecord = item['data'];

        this.loading = true;
        if (this.myTable !== undefined) {
          this.myTable.reset();
        }
        this._KitchenManagementService.getListDayOff(object).subscribe(item2 => {
          this.listDayOff = item2['data'];

          this.startRow = 0;
          if (this.listDayOff != null && this.listDayOff.length >= 10) {
            this.rowSize = 10;
          } else {
            this.rowSize = this.listDayOff.length;
          }

          if (this.listDayOff == null || this.listDayOff.length === 0) {
            this.isEmpty = true;
          } else {
            this.isEmpty = false;
          }

          this.loading = false;
        });
      });
    });
  }

  isSelectedYearsHasValue() {
    if (!this.selectedYears || this.selectedYears.length < 1) {
      this.startRow = -1;
      this.rowSize = 1;
      this.totalRecord = 0;
      this.listDayOff = [];
      this.loading = false;
      return false;
    }
    return true;
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
