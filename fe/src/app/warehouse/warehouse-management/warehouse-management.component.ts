import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { AutoComplete } from 'primeng/primeng';
import { MessageService, ConfirmationService } from 'primeng/api';
import { Table } from 'primeng/table';
import { Title } from '@angular/platform-browser';
import { Router, NavigationEnd } from '@angular/router';
import { WarehouseService } from '../warehouse.service';
import { ConditionSearch } from '../Entity/ConditionSearch';
import { Constants } from '../../shared/containts';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-warehouse-management',
  templateUrl: './warehouse-management.component.html',
  styleUrls: ['./warehouse-management.component.css']
})
export class WarehouseManagementComponent implements OnInit {


  type: string;
  typesOfWarehouse: any[];
  @ViewChild("myTable")
  myTable: Table;

  @ViewChild('inputName') private inputName: ElementRef;
  @ViewChild('inputType') private inputType: AutoComplete;
  @ViewChild('inputAcreage') private inputAcreage: ElementRef;
  @ViewChild('inputAddress') private inputAddress: ElementRef;
  @ViewChild('inputRow') private inputRow: ElementRef;
  @ViewChild('inputColumn') private inputColumn: ElementRef;
  @ViewChild('inputHeight') private inputHeight: ElementRef;

  navigationSubscription;

  status: number;
  isEdit = false;
  isShowMap = false;

  warehouse_id: number;
  warehouse_name: string;
  address: string;
  row_num: number;
  column_num: number;
  height_num: number;
  acreage: number;
  totalRecords: number;

  loading: boolean;

  pageNumber: number = 0;

  pageSize: number = 10;

  conditionSearch: ConditionSearch;

  occupiedSlot: number = 0;
  totalTinBox: number = 0;

  listWarehouse: any[];

  // tslint:disable-next-line:max-line-length
  onlyChar: RegExp = /^[0-9a-zA-ZÝÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÝÌỈĨỊÓÒỎÕỌÔờỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĝ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếờểễệíìỉĩịóòờõờôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;

  // tslint:disable-next-line:max-line-length
  // notChar: RegExp = /[^0-9a-zA-ZÝÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÝÌỈĨỊÓÒỎÕỌÔờỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĝ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếờểễệíìỉĩịóòờõờôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;
  notChar: RegExp = /[%&_*\\'<>]/g;
  cols = [
    { field: "", header: "STT", width: "5%" },
    { field: "", header: "Thao tác", width: "10%" },
    { field: "warehouseName", header: "Tên kho", width: "25%" },
    { field: "acreage", header: "Diện tích(m2)", width: "15%" },
    { field: "row_num", header: "Số hàng", width: "5%" },
    { field: "col_num", header: "Số cột", width: "5%" },
    { field: "height_num", header: "Chiều cao", width: "5%" },
    { field: "type", header: "Loại", width: "15%" },
    { field: "status", header: "Trạng Thái", width: "15%" }
  ];

  listCol: any[];

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private warehouseService: WarehouseService,
    private router: Router,
    private title: Title
  ) {
    this.navigationSubscription = this.router.events.subscribe(event => {
      if (!(event instanceof NavigationEnd)) {
        return;
      }
      // Do what you need to do here, for instance :
      this.status = 1;
      this.type = "Kho lưu";
      this.handleSearch();      
      title.setTitle("Thông tin Kho");
    });
    this.loading = false;
    this.conditionSearch = new ConditionSearch();
  }

  ngOnInit() {
  }

  // // ******************************** START CODE MOI *********************************** //

  addType(event) {
    this.typesOfWarehouse = [];
    this.typesOfWarehouse.push('Tất cả');
    this.typesOfWarehouse.push('Kho tạm');
    this.typesOfWarehouse.push('Kho lưu');
  }

  changeValue() {
    if (this.notChar.test(this.inputName.nativeElement.value)) {
      this.inputName.nativeElement.value = this.inputName.nativeElement.value.replace(this.notChar, '');
    }
    if (this.notChar.test(this.inputAddress.nativeElement.value)) {
      this.inputAddress.nativeElement.value = this.inputAddress.nativeElement.value.replace(this.notChar, '');
    }
  }

  handleSearch() {
    if (this.warehouse_name != null && this.address != undefined) {
      this.warehouse_name = this.warehouse_name.trim();
      for (let index = 0; index < this.warehouse_name.length; index++) {
        this.warehouse_name = this.warehouse_name.replace(this.notChar, "");
      }
    }
    if (this.address != null && this.address != undefined) {
      for (let index = 0; index < this.address.length; index++) {
        this.address = this.address.replace(this.notChar, "");
      }
    }
    this.isEdit = false;
    this.pageNumber = 0;
    this.warehouse_id = null;
    if (this.myTable !== undefined) {
      this.myTable.reset();
      let value1 =
        this.warehouse_name === undefined ||
          this.warehouse_name === null ||
          this.warehouse_name === ""
          ? null
          : this.warehouse_name;
      let value2 =
        this.address === undefined ||
          this.address === null ||
          this.address === ""
          ? null
          : this.address;
      let value3 =
        this.row_num === undefined || this.row_num === null
          ? null
          : this.row_num;
      let value4 =
        this.column_num === undefined || this.column_num === null
          ? null
          : this.column_num;
      let value5 =
        this.height_num === undefined || this.height_num === null
          ? null
          : this.height_num;
      let value6 =
        this.status === undefined || this.status === null ? null : this.status;
      let value7 =
        this.acreage === undefined || this.acreage === null ? null : this.acreage;
      let value8 =
        this.type !== undefined || this.type !== null ?
          this.type == 'Kho tạm' ? 0 : this.type == 'Kho lưu' ? 1 : null : null;
      this.conditionSearch.warehouse_name = value1;
      this.conditionSearch.address = value2;
      this.conditionSearch.row_num = value3;
      this.conditionSearch.column_num = value4;
      this.conditionSearch.height_num = value5;
      this.conditionSearch.status = value6;
      this.conditionSearch.acreage = value7;
      this.conditionSearch.type = value8;
      this.conditionSearch.pageNumber = this.pageNumber;
      this.conditionSearch.pageSize = this.pageSize;
      this.findSearchingResult();

    }
  }

  findSearchingResult() {
    let apiResponseStatus: number;
    this.warehouseService
      .getListWarehouse(this.conditionSearch)
      .subscribe(data => {
        this.listWarehouse = data.data;
        if (this.listWarehouse.length == 0) {
          this.totalRecords = 0;
        } else {
          this.totalRecords = this.listWarehouse[0].totalRecords;
        }
      });
  }

  handleAdd() {
    // console.log(this.acreage + " " + this.column_num + " " + this.row_num + " " + this.height_num);
    // this.acreage = this.acreage + "";
    // this.column_num = this.column_num + "";
    // this.row_num = this.row_num + "";
    // this.height_num = this.height_num + "";
    if (this.warehouse_name != null && this.address != undefined) {
      for (let index = 0; index < this.warehouse_name.length; index++) {
        this.warehouse_name = this.warehouse_name.replace(this.notChar, "");
      }
    }
    if (this.address != null && this.address != undefined) {
      for (let index = 0; index < this.address.length; index++) {
        this.address = this.address.replace(this.notChar, "");
      }
    }
    this.conditionSearch = new ConditionSearch();
    this.conditionSearch.pageNumber = this.pageNumber;
    this.conditionSearch.pageSize = this.pageSize;
    let typeOfWarehouse = this.type !== undefined || this.type !== null ? this.type == 'Kho tạm' ? 0 : this.type == 'Kho lưu' ? 1 : null : null;
    this.type = this.type !== undefined || this.type != null ? this.type == 'Tất cả' ? null : this.type : null;

    if (this.warehouse_name != null || this.warehouse_name != undefined) {
      this.warehouse_name = this.warehouse_name.replace(/^\s+|\s+$|\s+(?=\s)/g, "");
    }
    if (this.address != null || this.address != undefined) {
      this.address = this.address.replace(/^\s+|\s+$|\s+(?=\s)/g, "");
    }
    let check = this.checkDataBeforeAdd();

    if (check) {
      this.confirmationService.confirm({
        message: "Đồng chí có muốn lưu bản ghi này không?",
        rejectVisible: true,
        header: "Xác nhận Lưu",
        icon: "pi pi-info-circle",
        acceptLabel: 'Đồng ý',
        rejectLabel: 'Hủy bỏ',
        accept: () => {
          this.processInsertUpdate(typeOfWarehouse);
        }
      });
    }
  }

  processInsertUpdate(typeOfWarehouse) {

    let apiResponseStatus: number;

    this.warehouseService
      .insertUpdate(
        this.warehouse_id,
        this.warehouse_name,
        typeOfWarehouse,
        this.status,
        this.acreage,
        this.address,
        this.row_num,
        this.column_num,
        this.height_num
      )
      .subscribe(x => {
        apiResponseStatus = x["status"];
        if (apiResponseStatus == Constants.RESPONSE_SUCESS) {
          this.showMessage("success", "Thêm mới thành công", "");
          //this.findSearchingResult();
          this.resetForm(this.inputRow, this.inputColumn, this.inputHeight, this.inputAcreage);
        } else if (apiResponseStatus == Constants.UPDATE_SUCESS) {
          this.showMessage("success", "Cập nhật thành công", "");
          this.isEdit = false;
          this.resetForm(this.inputRow, this.inputColumn, this.inputHeight, this.inputAcreage);
        } else if (apiResponseStatus == Constants.DELETE_UPDATE_ERROR) {
          this.showMessage("error", "Cập nhật không thành công, kho đã bị xóa", "");
        } else if (apiResponseStatus == Constants.STATUS_DUPLICATE_WAREHOUSE) {
          this.showMessage("error", "Kho đã tồn tại. Vui lòng thử lại", "");
        } else if (apiResponseStatus == Constants.RESPONSE_ERROR) {
          this.showMessage("error", "Có lỗi không xác định, vui lòng thử lại sau", "");
        } else if (apiResponseStatus == Constants.SLOT_BEING_USED) {
          this.showMessage("error", "Có vị trí đã được lưu trữ tài liệu. Cập nhật không thành công.", "");
        } else {
          this.showMessage("error", "Có lỗi không xác định, vui lòng thử lại sau", "");
        }
      });

  }

  limitValue(input, min, max) {
    if (input.value < min || input.value == null) input.value = min;
    if (input.value > max) input.value = max;
  }

  setArc(input) {
    this.acreage = input.value;
  }

  setRow(input) {
    this.row_num = input.value;
  }

  setCol(input) {
    this.column_num = input.value;
  }

  setHeight(input) {
    this.height_num = input.value;
  }

  preventSpecialChar(input) {
    if (this.notChar.test(input.value)) {
      input.value = input.value.replace(this.notChar, '');
    }
  }

  /* Validate data before add */
  checkDataBeforeAdd() {
    let check = true;
    if (!this.validate(this.warehouse_name, "Tên kho không được trống", true)) {
      check = false;
      this.inputName.nativeElement.focus();
      return check;
    }
    if (!this.validate(this.type, "Bạn phải chọn loại Kho tạm hoặc Kho lưu trữ", true)) {
      check = false;
      this.inputType.domHandler.findSingle(this.inputType.el.nativeElement, 'input').focus();
      return check;
    }
    if (!this.validateNumberNullOrUndefined(this.acreage, "Diện tích không được trống", 'Diện tích phải lớn hơn 0.',
      'Diện tích không được lớn hơn 1000000000', true)) {
      check = false;
      this.inputAcreage.nativeElement.focus();
      return check;
    }
    if (!this.validate(this.address, "Địa chỉ không được trống", true)) {
      check = false;
      this.inputAddress.nativeElement.focus();
      return check;
    }
    if (!this.validateNumberNullOrUndefined(this.row_num, "Số hàng không được trống", 'Số hàng phải lớn hơn 0.',
      'Số hàng không được lớn hơn 100', true)) {
      check = false;
      this.inputRow.nativeElement.focus();
      return check;
    }
    if (!this.validateNumberNullOrUndefined(this.column_num, "Số cột không được trống", 'Số cột phải lớn hơn 0.',
      'Số cột không được lớn hơn 100', true)) {
      check = false;
      this.inputColumn.nativeElement.focus();
      return check;
    }
    if (!this.validateNumberNullOrUndefined(this.height_num, "Chiều cao không được trống", 'Chiều cao phải lớn hơn 0.',
      'Chiều cao không được lớn hơn 5', true)) {
      check = false;
      this.inputHeight.nativeElement.focus();
      return check;
    }

    return check;
  }

  /* Validate data */
  validate(name: string, mess: string, isShow: boolean) {
    if (name === undefined || name === null || name.trim().length == 0) {
      if (isShow) {
        this.messageService.add({
          severity: "error",
          summary: "Cảnh báo lỗi:",
          detail: mess
        });

      }
      return false;
    }
    return true;
  }

  validateNumberNullOrUndefined(name: number, messEmpty: string, mess: string, messTooBig: string, isShow: boolean) {
    if (name === undefined || name === null) {
      if (isShow) {
        this.messageService.add({
          severity: "error",
          summary: "Cảnh báo lỗi:",
          detail: messEmpty
        });

      }
      return false;
    } else if (name <= 0) {
      if (isShow) {
        this.messageService.add({
          severity: "error",
          summary: "Cảnh báo lỗi:",
          detail: mess
        });
      }
      return false;
    } else if (name > 1000000000) {
      if (isShow) {
        this.messageService.add({
          severity: "error",
          summary: "Cảnh báo lỗi:",
          detail: messTooBig
        })
      }
      return false;
    }
    return true;
  }

  doEdit(data) {
    this.isEdit = true;
    this.warehouse_id = data.warehouseId;
    this.warehouse_name = data.name;
    this.address = data.address;
    this.type = data.type == 0 ? 'Kho tạm' : data.type == 1 ? 'Kho lưu' : null;
    this.status = data.status;
    this.acreage = data.acreage;
    this.row_num = data.rowNum;
    this.height_num = data.heightNum;
    this.column_num = data.columnNum;
    let countRow = 0;
    let countOccupiedSlot = 0;
    this.warehouseService
      .loadListRackSlot(this.warehouse_id)
      .subscribe(data => {
        try {
          this.listCol = data.data;
          this.listCol.forEach(element => {
            element.forEach(row => {
              if (row.type === 0 || row.type === 2) {
                countRow++;
                countOccupiedSlot += +(row.numberPerSlot.slice(0, 1));
              }
            });
          });
          this.occupiedSlot = countOccupiedSlot;
          this.totalTinBox = countRow * this.height_num;          
        } catch (error) {
          console.log('warehouse loadListRackSlot' + error);
        }
      });
  }

  doDelete(data) {

    var apiResponseStatus: number;

    this.confirmationService.confirm({
      message: 'Đồng khí có muốn xóa kho?',
      rejectVisible: true,
      header: "Xác nhận xóa",
      icon: "pi pi-info-circle",
      acceptLabel: 'Đồng ý',
      rejectLabel: 'Hủy bỏ',
      accept: () => {
        this.warehouseService.deleteWarehouse(data.warehouseId).subscribe((x) => {

          apiResponseStatus = x["status"];
          if (apiResponseStatus == Constants.DELETE_WAREHOUSE_SUCCESS) {
            this.showMessage("success", "Đã xóa kho", "");

            var index = this.listWarehouse.findIndex(
              x => x.warehouseId == data.warehouseId
            );
            this.listWarehouse.splice(index, 1);
            this.totalRecords -= 1;
            this.resetForm(this.inputRow, this.inputColumn, this.inputHeight, this.inputAcreage);
          } else if (apiResponseStatus == 0) {
            this.showMessage(
              "error",
              "Có lỗi không xác định, vui lòng thử lại sau!",
              ""
            );
          } else if (apiResponseStatus == Constants.DELETE_UPDATE_ERROR) {
            this.confirmationService.confirm({
              message: "Xóa không thành công. Kho đã bị xóa bởi một người khác",
              header: "Thông báo",
              rejectVisible: false,
              icon: "pi pi-info-circle",
              accept: () => {
                var index = this.listWarehouse.findIndex(
                  x => x.warehouseId == data.warehouseId
                );
                this.listWarehouse.splice(index, 1);
                this.totalRecords -= 1;
              }
            });
            this.resetForm(this.inputRow, this.inputColumn, this.inputHeight, this.inputAcreage);
          } else if (apiResponseStatus == Constants.SLOT_BEING_USED) {
            this.showMessage("error", "Kho đã có tài liệu. Đồng chí không thể xóa.", "");
          }
        });
      }

    });
  }



  showMessage(severity: string, summary: string, detail: string) {
    this.messageService.add({
      severity: severity,
      summary: summary,
      detail: detail
    });
  }

  paginate(event) {
    this.loading = true;
    let pagenumber = event.first;
    this.conditionSearch.pageNumber = pagenumber;
    this.pageNumber = pagenumber;
    this.findSearchingResult();
    this.loading = false;
    // setTimeout(() => {
    //   let pagenumber = event.first;
    //   this.conditionSearch.pageNumber = pagenumber;
    //   this.pageNumber = pagenumber;
    //   this.findSearchingResult();
    //   this.loading = false;
    // }, 1000);

  }

  resetForm(inputRow, inputColumn, inputHeight, inputAcreage) {
    if (this.myTable !== undefined) {
      this.myTable.reset();
    }
    this.warehouse_id = null;
    this.warehouse_name = null;
    this.address = null;
    this.type = null;
    this.status = null;
    this.acreage = null;
    this.row_num = null;
    this.height_num = null;
    this.column_num = null;
    inputRow.value = null;
    inputColumn.value = null;
    inputHeight.value = null;
    inputAcreage.value = null;    
    this.status = 1;
    this.type = "Kho lưu";
    this.handleSearch();
  }

  showWarehouseMap() {
    this.isShowMap = true;
  }

  // // ******************************** END CODE MOI *********************************** //


  ngOnDestroy() {
    // avoid memory leaks here by cleaning up after ourselves. If we
    // don't then we will continue to run our initialiseInvites()
    // method on every navigationEnd event.
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }
}
