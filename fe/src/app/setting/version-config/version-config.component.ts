import { Component, OnInit, ViewChild, Renderer2, OnDestroy } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { AutoComplete, ConfirmationService, MessageService, Dropdown } from 'primeng/primeng';
import { Table } from 'primeng/table';
import { SettingService } from '../setting.service';
import { Router, NavigationEnd } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-version-config',
  templateUrl: './version-config.component.html',
  styleUrls: ['./version-config.component.css'],
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
export class VersionConfigComponent implements OnInit, OnDestroy {

  @ViewChild('myTable') private myTable: Table;
  @ViewChild('inputType') private inputType: Dropdown;
  @ViewChild('inputVersion') private inputVersion: AutoComplete;
  @ViewChild('inputLink') private inputLink: AutoComplete;

  navigationSubscription;
  isClick: boolean;
  displayPopup: boolean;  //  popup show error message when place code reach max length, now is not using
  messageAlert: string;   //  message of popup, now is not using

  listVersionDetail: any[] = [];
  filterPlace: any;

  replaceCode: any;
  replaceName: any;
  versionLink: any;
  versionType: any;
  canActive: boolean;
  version: any;
  versionDesc: any;
  startRow: number;
  rowSize: number;
  totalRecord: number;

  suggest: any[];
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
      this.suggest =  [
        {
          label: 'android',
          value: 'android',
        },
        {
          label: 'apple',
          value: 'apple',
        }];
      this.versionType = this.suggest[0];
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
    this.title.setTitle('Cấu hình phiên bản mobile - PMQTVP');
    this.searchData();
  }

  //  search list places
  searchData() {
    this.loading = true;
    const condition = {
      version: this.version ? this.version : null,
      versionLink: this.versionLink ? this.versionLink : null,
      versionType: this.versionType ? this.versionType.value : null,
      versionDesc: this.versionDesc ? this.versionDesc.trim() : null
    };
    this.settingService.getListVersion(condition).subscribe(item => {
      this.listVersionDetail = item['data'];
      this.loading = false;
      if (this.listVersionDetail === null || this.listVersionDetail.length === 0){
        this.isEmpty = true;
      }
    });
  }

  loadPlace(event, id) {
    this.filterPlace = [];
    if (id === 1) {
      this.filterPlace = ['android', 'apple'];
    }
  }

  //  add place
  addData() {
    if (this.checkCondition()) {
      this.confirmationService.confirm({
        message: 'Đồng chí có muốn THÊM phiên bản này?',
        header: 'Thêm phiên bản',
        icon: 'pi pi-question-circle',
        acceptLabel: 'Thêm',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          const condition = {
            version: this.version,
            versionLink: this.versionLink ? this.versionLink.trim() : '',
            versionType: this.versionType  ? this.versionType.value.trim() : '',
            versionDesc: this.versionDesc ? this.versionDesc.trim() : ''
          };
          this.settingService.addNewVersionControl(condition).subscribe(result => {
            if (result.status === 1) {

              this.alertMessage('info', 'Thông báo', 'Thêm thành công!');
              this.resetButton();
              this.searchData();
              this.settingService.deleteAllSession().subscribe(result => {

              });
            } else if (result.status === 2) {

              this.alertMessage('info', 'Thông báo', 'Thêm thất bại, kiểu thiết bị đã tồn tại!');
              this.resetButton();
              this.searchData();

            } else {
                this.alertMessage('error', 'Cảnh báo', 'Thêm thất bại!');
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
        message: 'Đồng chí có muốn CẬP NHẬT phiên bản này?',
        header: 'Cập nhật phiên bản',
        icon: 'pi pi-question-circle',
        acceptLabel: 'Cập nhật',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          const condition = {
            version: this.version,
            versionLink: this.versionLink ? this.versionLink.trim() : '',
            versionType: this.versionType  ? this.versionType.value.trim() : '',
            versionDesc: this.versionDesc ? this.versionDesc.trim() : '',
            status: this.canActive ? 1 : 0,
          };
          this.settingService.updateVersionControl(condition).subscribe(result => {
            console.log(result.status);
            if (result.status === 1) {

              this.alertMessage('info', 'Thông báo', 'Cập nhật thành công!');

              this.resetButton();
              this.searchData();

            } else {
                this.alertMessage('error', 'Cảnh báo', 'Cập nhật thất bại!');
            }
          });
        }
      });
    }
  }

  //  delete place
  deleteData(item) {
    this.confirmationService.confirm({
      message: 'Đồng chí có muốn XÓA phiên bản này?',
      header: 'Xóa phiên bản',
      icon: 'pi pi-question-circle',
      acceptLabel: 'Xóa',
      rejectLabel: 'Hủy bỏ',
      accept: () => {
        const condition = {
          versionType: item.versionType ? this.versionType.value.trim() : '',
          status: 3,
        };
        this.settingService.deleteVersionControl(condition).subscribe(result => {
          console.log(result.status);
          if (result.status === 1) {

            this.alertMessage('info', 'Thông báo', 'Xóa thành công!');

            this.resetButton();
            this.searchData();

          } else {
              this.alertMessage('error', 'Cảnh báo', 'Xóa thất bại!');
          }
        });
      }
    });
  }

  //  fill data to input
  doEdit(event) {
    const item = event.data;
    // this.disabledCode = true;
    // this.disabledSave = false;
    // this.versionType = item.versionType;
    this.version = item.version;
    this.versionDesc = item.versionDesc;
    this.versionLink = item.versionLink;
  }

  //  reset data
  resetButton() {
    this.versionType = this.suggest[0];
    this.version = null;
    this.versionDesc = null;
    this.versionLink = null;
    this.canActive = true;
    this.disabledCode = false;
    this.disabledSave = true;
  }

  //  check condition
  checkCondition(): boolean {
    let isOk = true;

    if (!this.versionType || !this.versionType.value) {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Kiểu thiết bị không được để trống');
      this.inputType.focus();
    } else if (!this.version) {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Số hiệu phiên bản không được để trống');
      this.inputVersion.domHandler.findSingle(this.inputVersion.el.nativeElement, 'input').focus();
    } else if (!this.versionLink) {
      isOk = false;
      this.alertMessage('error', 'Cảnh báo', 'Link tải không được để trống');
      this.inputLink.domHandler.findSingle(this.inputLink.el.nativeElement, 'input').focus();
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
}
