import { Component, OnInit, OnDestroy, ViewChild, Renderer2, ElementRef } from '@angular/core';
import { ReportserviceService } from '../reportservice.service';
import { ConfirmationService, MessageService, TreeNode } from 'primeng/api';
import { Table } from 'primeng/table';
import { Title } from '@angular/platform-browser';
import { Condition } from '../Entity/ConditionSearchCard';
import { AddCardItem } from '../Entity/ListAddCard';
import { Router, NavigationEnd } from '@angular/router';
import { ResponseAPI } from '../../service-management/Entity/Response';
import { KitchenManagementService } from '../../kitchen-management/kitchen-management.service';

@Component({
  selector: 'app-add-car-id',
  templateUrl: './add-card-id.component.html',
  styleUrls: ['./add-card-id.component.css']
})
export class AddCardIdComponent implements OnInit, OnDestroy {

  @ViewChild('myTable') private myTable: Table;
  @ViewChild('inputCard') private inputCard: ElementRef;

  navigationSubscription;
  condition: Condition;
  isClick: boolean;
  listParentUnit: string[];
  listSelectedUnitId: any[] = [];

  listSearchCard: AddCardItem[];
  itemCard: AddCardItem;
  filterEmployee: any[];
  sectors: TreeNode[];
  selectedSector: TreeNode[];
  filterCard: any[];
  empInfor: any;
  cardInfo: any;
  empCardId: string;
  totalRecord: number;
  startRow: number;
  rowSize: number;

  isEmpty: boolean;
  loading: boolean;
  loading2: boolean;
  loadingTree = false;
  displayCardEmployee: boolean;
  headerCardEmployee: string;

  // tslint:disable-next-line:max-line-length
  onlyCharEmail: RegExp = /^[0-9a-zA-Z@ÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;
  onlyNum: RegExp = /^[0-9]+$/;

  constructor(private _ReportserviceService: ReportserviceService, private _KitchenManagementService: KitchenManagementService,
    private messageService: MessageService, private confirmationService: ConfirmationService,
    private renderer: Renderer2, private title: Title, private router: Router) {
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
    this.loading2 = false;
    this.displayCardEmployee = false;
    this.startRow = -1;
    this.rowSize = 1;
    this.totalRecord = 0;
    this.title.setTitle('Thêm mã thẻ - PMQTVP');
    this.getSector();
  }

  //  get list unit
  getSector() {
    this._KitchenManagementService.getSectors(null).subscribe(data => {
      this.sectors = data;
      const parentId = [];
      this.sectors.forEach(value => {
        if (value.data) {
          parentId.push(value.data);
        }
      });
      this.listParentUnit = parentId;
      this.searchData();
    });
  }

  //  expand tree
  nodeExpand(event) {
    this.loadingTree = true;
    if (event.node && event.node.children !== undefined) {
      this.loadingTree = false;
      return;
    }
    if (event.node && event.node.children === undefined) {
      this._KitchenManagementService.getSectors(event.node.data).subscribe(
        data => {
          event.node.children = data;
          if (event.node !== null && event.node !== undefined && this.selectedSector !== null && this.selectedSector.includes(event.node)) {
            data.forEach(element => {
              if (element !== null && element !== undefined && element.data !== null && element.data !== undefined) {
                this.selectedSector.push(element);
              }
            });
          }
          this.loadingTree = false;
        });
    }
  }

  //  when editing card
  changeCard() {
    
    if(this.empCardId.length > 20){
      this.inputCard.nativeElement.value = this.empCardId.substring(0,20);
    }

    if (/[^0-9]/g.test(this.inputCard.nativeElement.value)) {
      this.inputCard.nativeElement.value = this.inputCard.nativeElement.value.replace(/[^0-9]/g, '');
    }
    this.empCardId = this.inputCard.nativeElement.value;
    //  set maxlength to format
    this.renderer.setAttribute(this.inputCard.nativeElement, 'maxlength', '20');
    //  remove maxlength to reset
    this.renderer.removeAttribute(this.inputCard.nativeElement, 'maxlength');
  }

  //  get data of node selected
  getSelectedNodeData(): any[] {
    this.listSelectedUnitId = [];
    if (this.selectedSector === null || this.selectedSector.length === 0) {
      return this.listParentUnit;
    } else if (this.selectedSector != null) {
      for (const file of this.selectedSector) {
        if (file.data) {
          this.listSelectedUnitId.push(file.data);
        }
      }
    }
    return this.listSelectedUnitId.length > 0 ? this.listSelectedUnitId : null;
  }

  //  when click 'x' of employee info
  clearEmp(event) {
    if (this.empInfor != null) {
      event.stopPropagation();
      this.empInfor = null;
      this.loading2 = false;
    }
  }

  //  when click 'x' of card field
  clearCard(event) {
    if (this.cardInfo != null) {
      event.stopPropagation();
      this.cardInfo = null;
      this.loading2 = false;
    }
  }

  //  show suggestions when type in employee field
  loadEmployee(event) {
    const object = {
      'result': event.query
    };
    this._ReportserviceService.getEmployee2(object).subscribe(item => {
      this.filterEmployee = item['data'];
    });
  }

  //  show suggestions when type in card field
  loadCard(event) {
    const object = {
      'cardId': event.query
    };
    const temp = [];
    this._ReportserviceService.getCardId(object).subscribe(item => {
      for (let i = 0; i < item['data'].length; i++) {
        temp.push(item['data'][i].cardId);
      }
      this.filterCard = temp;
    });
  }

  //  show card employee
  showInfoCardEmp(item: AddCardItem) {
    this.headerCardEmployee = 'Cập nhật thẻ';
    this.itemCard = item;
    this.empCardId = item.cardId;
    this.displayCardEmployee = true;
  }

  //  get condition and validate
  getConditionSearch() {
    let personinfo = null;
    let cardinfo = null;

    //  handle employee
    if (this.empInfor != null && this.empInfor.length > 0) {
      personinfo = this.empInfor;
    } else if (this.empInfor != null && this.empInfor['result'] !== undefined) {
      personinfo = this.empInfor['result'];
    }

    //  handle card
    if (this.cardInfo != null && this.cardInfo.length > 0) {
      cardinfo = this.cardInfo;
    }

    this.condition = null;
    this.condition = {
      personInfo: personinfo,
      listUnit: this.getSelectedNodeData(),
      cardId: cardinfo,
      numOfRows: -1,
      startRow: -1,
      rowSize: -1
    };
  }

  //  get list search card employee from database
  getListSearchCardEmp() {
    this.loading = true;
    if (this.myTable !== undefined) {
      this.myTable.reset();
    }

    this._ReportserviceService.getListAddCard(this.condition).subscribe(item => {
      if (item['status'] === 1) {
        this.listSearchCard = item['data'];
        this.startRow = 0;
        if (this.listSearchCard != null && this.listSearchCard.length >= 10) {
          this.rowSize = 10;
        } else {
          this.rowSize = this.listSearchCard.length;
        }

        if (this.listSearchCard === null || this.listSearchCard.length === 0) {
          this.isEmpty = true;

          // if (this.isClick) {
          //   this.alertMessage('info', 'Thông báo', 'Không tìm thấy bản ghi tương ứng');
          // }
        } else {
          this.isEmpty = false;
        }

        this.isClick = true;
        this.loading = false;
      } else if (item['status'] === 3) {
        this.isEmpty = true;
        this.alertMessage('error', 'Cảnh báo', 'Không đủ quyền thực hiện thao tác');
      }
    });
  }

  //  when click new page
  paginate(event) {
    this.loading = true;
    this.startRow = event.first;
    this.condition.startRow = event.first;
    this.condition.rowSize = 10;

    this._ReportserviceService.getListAddCard(this.condition).subscribe(item => {
      this.listSearchCard = item['data'];
      this.rowSize = this.listSearchCard.length;
      this.loading = false;
    });
  }

  //  when click search button
  searchData() {
    this.getConditionSearch();
    this.condition.startRow = 0;
    this.condition.rowSize = 10;
    this._ReportserviceService.countTotalRecord2(this.condition).subscribe(item => {
      this.totalRecord = item['data'];
      this.getListSearchCardEmp();
    });
  }

  //  update data
  updateData() {
    if (!this.empCardId || this.empCardId.length <= 20) {
      this.confirmationService.confirm({
        message: 'Đồng chí có muốn CẬP NHẬT mã thẻ này?',
        header: 'Cập nhật mã thẻ',
        icon: 'fa fa-warning',
        acceptLabel: 'Cập nhật',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          const object = {
            'cardId': this.empCardId.replace(/[^0-9]/g, ''),
            'empCode': this.itemCard.empCode
          };
          this._ReportserviceService.updateCardEmployee(object).subscribe(item => {
            if (item['status'] === 1) {
              this.resetButton();
              this.searchData();
              this.displayCardEmployee = false;
              this.alertMessage('success', 'Thông báo', 'Cập nhật mã thẻ thành công');
            } else if (item['status'] === 0) {
              this.alertMessage('error', 'Cảnh báo', 'Cập nhật mã thẻ thất bại');
            } else if (item['status'] === 2) {
              this.alertMessage('error', 'Cảnh báo', 'Mã thẻ đã tồn tại');
            } else if (item['status'] === 3) {
              this.alertMessage('error', 'Cảnh báo', 'Không đủ quyền thực hiện thao tác');
            } else {
              this.alertMessage('error', 'Cảnh báo', 'Có lỗi không xác định');
            }
          });
        }
      });
    } else {
      this.alertMessage('error', 'Cảnh báo', 'Dữ liệu quá dài');
    }
  }

  //  when click refresh button
  resetButton() {
    this.selectedSector = null;
    this.empInfor = null;
    this.cardInfo = null;
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
