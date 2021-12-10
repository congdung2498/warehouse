/**
 *
 * --- ThangBT ---
 *
 */
import { Component, OnInit, OnDestroy, AfterViewInit, ViewChild, Renderer2 } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { Table } from 'primeng/table';
import { ConfirmationService, MessageService } from 'primeng/components/common/api';
import { Place } from '../Entity/ListPlace';
import { Title } from '@angular/platform-browser';
import { Router, NavigationEnd } from '@angular/router';
import { AutoComplete } from 'primeng/autocomplete';
import { SettingService } from '../setting.service';

@Component({
  selector: 'app-place-config',
  templateUrl: './place-config.component.html',
  styleUrls: ['./place-config.component.css'],
  animations: [
    trigger('animation', [
      state('true', style({
        opacity: 1
      })),
      state('false', style({
        opacity: 0,
        display: 'none'
      })),
      transition('0 => 1', animate('190ms')),
      transition('1 => 0', animate('1000ms'))
    ])
  ]
})
export class PlaceConfigComponent implements OnInit, OnDestroy, AfterViewInit {

  @ViewChild('myTable') private myTable: Table;
  @ViewChild('inputCode') private inputCode: AutoComplete;
  @ViewChild('inputName') private inputName: AutoComplete;

  navigationSubscription;
  condition: Place;
  conditionSetUp: Place;
  isClick: boolean;
  displayPopup: boolean;  //  popup show error message when place code reach max length, now is not using
  messageAlert: string;   //  message of popup, now is not using

  listPlace: Place[];
  filterPlace: Place[];
  placeCodeInfo: any;
  canActive: boolean;
  placeNameInfo: any;
  placeDescription: any;
  startRow: number;
  rowSize: number;
  totalRecord: number;

  isEmpty: boolean;
  loading: boolean;
  disabledCode: boolean;
  disabledSave: boolean;

  // tslint:disable-next-line:max-line-length
  onlyChar: RegExp = /^[0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;
  // tslint:disable-next-line:max-line-length
  notChar: RegExp = /[^0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;

  constructor(private settingService: SettingService, private confirmationService: ConfirmationService,
    private messageService: MessageService, private title: Title, private renderer: Renderer2, private router: Router) {
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
    this.isClick = false;
    this.loading = true;
    this.displayPopup = false;
    this.disabledCode = false;
    this.disabledSave = true;
    this.canActive = true;
    this.startRow = -1;
    this.rowSize = 1;
    this.totalRecord = 0;
    this.title.setTitle('Cấu hình vị trí - PMQTVP');
    this.searchData();
  }

  //  suggestion of place code and name
  loadPlace(event, type: number) {
    // let object = null;
    // if (type === 1) {
    //   object = {
    //     'placeCode': event.query,
    //     'status': -1,
    //     'startRow': 0,
    //     'rowSize': 10
    //   };
    // } else if (type === 2) {
    //   object = {
    //     'placeName': event.query,
    //     'status': -1,
    //     'startRow': 0,
    //     'rowSize': 10
    //   };
    // }

    // this.settingService.getListPlace(object).subscribe(item => {
    //   this.filterPlace = item['data'];
    // });
    this.filterPlace = [];
  }

  //  check character of place code when paste
  replaceCode() {
    const code: string = this.inputCode.el.nativeElement.children[0].children[0].value;
    this.renderer.setAttribute(this.inputCode.el.nativeElement.children[0].children[0], 'maxlength', '50');
    if (this.notChar.test(code)) {
      this.inputCode.el.nativeElement.children[0].children[0].value = code.replace(this.notChar, '');
    }
  }

  //  check character of place name when paste
  replaceName() {
    const name: string = this.inputName.el.nativeElement.children[0].children[0].value;
    this.renderer.setAttribute(this.inputName.el.nativeElement.children[0].children[0], 'maxlength', '200');
    if (this.notChar.test(name)) {
      this.inputName.el.nativeElement.children[0].children[0].value = name.replace(this.notChar, '');
    }
  }

  //  get condition to insert, update
  getConditionSetUp() {
    let placecode = null;
    let placename = null;

    if (this.placeCodeInfo && this.placeCodeInfo.length > 0) {
      placecode = this.placeCodeInfo.trim();
    } else if (this.placeCodeInfo && this.placeCodeInfo['placeCode']) {
      placecode = this.placeCodeInfo['placeCode'].trim();
    }

    if (this.placeNameInfo && this.placeNameInfo.length > 0) {
      placename = this.placeNameInfo.trim();
    } else if (this.placeNameInfo && this.placeNameInfo['placeName']) {
      placename = this.placeNameInfo['placeName'].trim();
    }

    this.conditionSetUp = null;
    this.conditionSetUp = {
      placeCode: placecode,
      placeName: placename,
      placeDescription: this.placeDescription ? this.placeDescription.trim() : null,
      status: this.canActive ? 1 : 0,
      startRow: -1,
      rowSize: -1
    };
  }

  //  get condition to search
  getListCondition() {
    let placecode = null;
    let placename = null;

    if (this.placeCodeInfo && this.placeCodeInfo.length > 0) {
      placecode = this.placeCodeInfo.trim();
    } else if (this.placeCodeInfo && this.placeCodeInfo['placeCode']) {
      placecode = this.placeCodeInfo['placeCode'].trim();
    }

    if (this.placeNameInfo && this.placeNameInfo.length > 0) {
      placename = this.placeNameInfo.trim();
    } else if (this.placeNameInfo && this.placeNameInfo['placeName']) {
      placename = this.placeNameInfo['placeName'].trim();
    }

    this.condition = null;
    this.condition = {
      placeCode: placecode,
      placeName: placename,
      placeDescription: this.placeDescription ? this.placeDescription.trim() : null,
      status: this.canActive ? 1 : 0,
      startRow: 0,
      rowSize: 10
    };
  }

  //  get list data
  getListPlace() {
    this.loading = true;
    if (this.myTable !== undefined) {
      this.myTable.reset();
    }

    this.settingService.getListPlace(this.condition).subscribe(item => {
      if (item && item['data']) {
        this.listPlace = item['data'];
        this.startRow = 0;

        if (this.listPlace && this.listPlace.length > 0) {
          this.isEmpty = false;
          this.rowSize = (this.listPlace.length >= 10) ? 10 : this.listPlace.length;
        } else {
          this.isEmpty = true;

          // if (this.isClick) {
          //   this.alertMessage('info', 'Thông báo', 'Không tìm thấy bản ghi tương ứng');
          // }
        }

        this.isClick = true;
        this.loading = false;
      }
    });
  }

  //  when click new page
  paginate(event) {
    this.loading = true;
    this.startRow = event.first;
    this.condition.startRow = event.first;

    this.settingService.getListPlace(this.condition).subscribe(item => {
      this.listPlace = item['data'];
      this.rowSize = this.listPlace.length;
      this.loading = false;
    });
  }

  //  search list places
  searchData() {
    this.getListCondition();
    this.settingService.countTotalPlace(this.condition).subscribe(item => {
      this.totalRecord = item['data'];
      this.getListPlace();
    });
  }

  //  add place
  addData() {
    if (this.checkCondition()) {
      this.confirmationService.confirm({
        message: 'Đồng chí có muốn THÊM vị trí này?',
        header: 'Thêm vị trí',
        icon: 'pi pi-question-circle',
        acceptLabel: 'Thêm',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          this.getConditionSetUp();
          const object = {
            'action': 1,
            'data': this.conditionSetUp
          };
          this.settingService.handleAction(object).subscribe(item => {
            if (item) {
              if (item['status'] === 0) {
                this.alertMessage('error', 'Cảnh báo', 'Thêm vị trí thất bại');
              } else if (item['status'] === 1) {
                this.resetButton();
                this.searchData();
                this.alertMessage('success', 'Thông báo', 'Thêm vị trí thành công');
              } else if (item['status'] === 2) {
                this.alertMessage('error', 'Cảnh báo', 'Mã vị trí hoặc tên vị trí đã tồn tại');
                this.inputCode.domHandler.findSingle(this.inputCode.el.nativeElement, 'input').focus();
              } else {
                this.alertMessage('error', 'Cảnh báo', 'Có lỗi không xác định');
              }
            }
          });
        }
      });
    }
  }

  // update data
  saveData() {
    if (this.checkCondition()) {
      this.confirmationService.confirm({
        message: 'Đồng chí có muốn CẬP NHẬT vị trí này?',
        header: 'Cập nhật vị trí',
        icon: 'pi pi-question-circle',
        acceptLabel: 'Cập nhật',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          this.getConditionSetUp();
          const object = {
            'action': 2,
            'data': this.conditionSetUp
          };
          this.settingService.handleAction(object).subscribe(item => {
            if (item) {
              if (item['status'] === 0) {
                this.alertMessage('error', 'Cảnh báo', 'Cập nhật vị trí thất bại');
              } else if (item['status'] === 1) {
                this.resetButton();
                this.searchData();
                this.alertMessage('success', 'Thông báo', 'Cập nhật vị trí thành công');
              } else if (item['status'] === 2) {
                this.alertMessage('error', 'Cảnh báo', 'Tên vị trí đã tồn tại');
                this.inputName.domHandler.findSingle(this.inputName.el.nativeElement, 'input').focus();
              } else if (item['status'] === 3) {
                this.alertMessage('error', 'Cảnh báo', 'Cập nhật không thành công.Bản ghi này đã bị xóa!');
                this.resetButton();
                this.searchData();
              } else {
                this.alertMessage('error', 'Cảnh báo', 'Có lỗi không xác định');
              }
            }
          });
        }
      });
    }
  }

  //  delete place
  deleteData(item: Place) {
    this.confirmationService.confirm({
      message: 'Đồng chí có muốn XÓA vị trí này?',
      header: 'Xóa vị trí',
      icon: 'pi pi-question-circle',
      acceptLabel: 'Xóa',
      rejectLabel: 'Hủy bỏ',
      accept: () => {
        const object = {
          'action': 3,
          'data': {
            'placeCode': item.placeCode
          }
        };
        this.settingService.handleAction(object).subscribe(itemResp => {
          if (itemResp) {
            if (itemResp['status'] === 0) {
              this.alertMessage('error', 'Cảnh báo', 'Xóa vị trí thất bại');
            } else if (itemResp['status'] === 1) {
              if (this.placeCodeInfo && this.placeCodeInfo['placeCode']) {
                if (this.placeCodeInfo['placeCode'] === item.placeCode) {
                  this.resetButton();
                }
              }
              this.getListCondition();
              this.condition.placeCode = null;
              this.condition.placeName = null;
              this.condition.status = 1;
              this.settingService.countTotalPlace(this.condition).subscribe(itemResp2 => {
                this.totalRecord = itemResp2['data'];
                this.getListPlace();
              });
              this.alertMessage('success', 'Thông báo', 'Xóa vị trí thành công');
            } else if (itemResp['status'] === 3) {
              this.alertMessage('error', 'Cảnh báo', 'Bản ghi này đã được xóa bởi người khác');
              this.resetButton();
              this.searchData();
            } else {
              this.alertMessage('error', 'Cảnh báo', 'Có lỗi không xác định');
            }
          }
        });
      }
    });
  }

  //  fill data to input
  doEdit(item: Place) {
    this.disabledCode = true;
    this.disabledSave = false;
    this.placeCodeInfo = {
      placeCode: item.placeCode.trim()
    };
    this.placeNameInfo = {
      placeName: item.placeName.trim()
    };
    this.placeDescription = item.placeDescription;
    this.canActive = item.status === 1;
  }

  //  reset data
  resetButton() {
    this.placeCodeInfo = null;
    this.placeNameInfo = null;
    this.placeDescription = null;
    this.canActive = true;
    this.disabledCode = false;
    this.disabledSave = true;
  }

  //  check condition
  checkCondition(): boolean {
    let isOk = true;

    if (!this.placeCodeInfo) {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Mã vị trí không được để trống');
      this.inputCode.domHandler.findSingle(this.inputCode.el.nativeElement, 'input').focus();
    } else if (this.placeCodeInfo) {
      if ((this.placeCodeInfo['placeCode'] && this.placeCodeInfo['placeCode'].trim().length === 0)
        || (this.disabledSave && this.placeCodeInfo.trim().length === 0)) {
        isOk = false;
        this.alertMessage('error', 'Cảnh báo', 'Mã vị trí không được để trống');
        this.inputCode.domHandler.findSingle(this.inputCode.el.nativeElement, 'input').focus();
      } else if (!this.placeNameInfo) {
        isOk = false;
        this.alertMessage('error', 'Cảnh báo', 'Tên vị trí không được để trống');
        this.inputName.domHandler.findSingle(this.inputName.el.nativeElement, 'input').focus();
      } else if (this.placeNameInfo) {
        if ((this.disabledSave && this.placeNameInfo.trim().length === 0)
          || (this.placeNameInfo['placeName'] && this.placeNameInfo['placeName'].trim().length === 0)) {
          isOk = false;
          this.alertMessage('error', 'Cảnh báo', 'Tên vị trí không được để trống');
          this.inputName.domHandler.findSingle(this.inputName.el.nativeElement, 'input').focus();
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

  ngAfterViewInit() {
    this.renderer.setAttribute(this.inputCode.el.nativeElement.children[0].children[0], 'tabindex', '1');
    this.renderer.setAttribute(this.inputName.el.nativeElement.children[0].children[0], 'tabindex', '3');
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
