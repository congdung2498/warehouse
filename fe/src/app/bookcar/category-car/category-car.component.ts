import { Component, OnInit, ViewChild, ElementRef } from "@angular/core";
import { BookcarService } from "../bookcar.service";
import { Car } from "../Entity/Cars";
import { Type, Seat } from "../Entity/Cars";
import { DriveSquad } from "../Entity/DriveSquad";
import { ConfirmationService, MessageService } from "primeng/api";
import { Table } from "primeng/table";
import { Title } from "@angular/platform-browser";
import { Constants } from "../../shared/containts";
import { AutoComplete, Dropdown } from "primeng/primeng";
@Component({
  selector: "app-category-car",
  templateUrl: "./category-car.component.html",
  styleUrls: ["./category-car.component.css"]
})
export class CategoryCarComponent implements OnInit {
  @ViewChild("myTable")
  myTable: Table;
  @ViewChild('inputPlate') private inputPlate: ElementRef;
  @ViewChild('inputSquad') private inputSquad: AutoComplete;
  @ViewChild('inputType') private inputType: Dropdown;
  ListCar: Car[];
  ListCarType: Type[] = [];
  ListCarSeat: Seat[] = [];
  listteamcar: DriveSquad[];
  tempcar: DriveSquad;
  driveId: string;
  type: Type;
  seat: Seat;
  count: number;
  car: Car;
  plate: string;
  status: number;
  item: String;
  message: String;
  edit: number;
  add: number;
  listSuggest: any[];
  filteredCountriesSingle: any[];
  squad: any;
  squadSplit: any[];
  isEdit: boolean;

  pageNumber: number = 0;

  pageSize: number = 10;

  total_record: number;

  filteredCountriesSingleSquad: DriveSquad[];

  // tslint:disable-next-line:max-line-length
  onlyChar: RegExp = /^[0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;

  // tslint:disable-next-line:max-line-length
  notChar: RegExp = /[^0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;

  cols = [
    { field: "", header: "STT", width: "5%" },
    { field: "", header: "Thao Tác", width: "10%" },
    { field: "teamName", header: "Tên Đội Xe", width: "20%" },
    { field: "full_name", header: "Biển Kiểm Soát", width: "20%" },
    { field: "full_name", header: "Loại Xe", width: "15%" },
    { field: "full_name", header: "Số Chỗ", width: "10%" },
    { field: "status", header: "Trạng Thái", width: "20%" }
  ];

  constructor(
    private bookcarService: BookcarService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private title: Title
  ) {
    this.getListCar();
    this.getListCarSeat();
    this.getListCarType();
    this.getTeamCar();
    this.isEdit = false;
    //this.total_record = this.ListCar.length;
  }

  ngOnInit() {
    this.car = new Car("", "", "", "", "", "", 0, 7, "");
    //this.tempcar = new DriveSquad("", "", 0, "", "", "", 0);
    this.seat = new Seat("", "");
    this.type = new Type("", "");
    this.status = 1;
    this.count = 10;
    this.plate = "";
    this.edit = 0;
    this.total_record = 0;
    this.add = 0;
    this.listSuggest = [""];
    this.filteredCountriesSingleSquad = [];
    this.title.setTitle('Thêm mới danh mục xe - PMQTVP');
    //this.squad = new DriveSquad(null, null, 0, null, null, null, 0);
    this.isEdit = false;
  }

  setTeamCar() {
    // if (this.squad == null) {
    //   this.squad = new DriveSquad(null, null, 0, null, null, null, 0);
    // }
  }

  settingSearch() {
    if (this.squad != null && this.squad.squadName != undefined) {
      this.car.squadName = this.getNameObject(this.squad.squadName);
    } else
      this.car.squadName = this.squad;
    this.car.licensePlate = this.plate.trim();
    this.car.status = this.status;

    this.car.processStatus = 0;
    this.car.seat = this.seat.seatId;
    this.car.type = this.type.typeId;
  }

  settingCar() {

    // if (this.squad != null && this.squad.squadId != undefined) {
    //   this.car.squadId = this.getNameObject(this.squad.squadId);
    // } else
    //   this.car.squadName = this.squad;
    this.car.squadId = this.squad.squadId;
    this.car.licensePlate = this.plate.trim();
    this.car.status = this.status;
    this.car.processStatus = 0;
    this.car.seat = this.seat.seatId;
    this.car.type = this.type.typeId;
    console.log(this.car);
  }

  validate(squad: DriveSquad, type: string, plate: string, seat: string) {
    if (squad == null) {
      this.messageService.add({
        severity: "error",
        summary: "Thông báo",
        detail: "Đội xe không được để trống."
      });
      this.inputSquad.domHandler.findSingle(this.inputSquad.el.nativeElement, 'input').focus();
      return false;
    }
    if (type.length == 0) {
      this.messageService.add({
        severity: "error",
        summary: "Thông báo",
        detail: "Loại xe không được để trống."
      });
      
      return false;
    }
    if (plate.trim().length == 0) {
      this.messageService.add({
        severity: "error",
        summary: "Thông báo",
        detail: "Biển số xe không được để trống."
        
      });
      this.inputPlate.nativeElement.focus();
      return false;
    }
    if (seat.length == 0) {
      this.messageService.add({
        severity: "error",
        summary: "Thông báo",
        detail: "Chỗ ngồi không được để trống."
      });
      return false;
    } else {
      this.message = "";
      return true;
    }
  }

  isSelect() {
    if (this.squad != null && this.squad != undefined) {
      if (this.squad.squadId == null || this.squad.squadId == undefined) {
        this.squad = null
      }
    }
  }

  paginate(event) {
    this.pageNumber = event.first;
  }

  // search data
  handleSearch() {
    //alert(this.type.type=="");
    if (this.myTable !== undefined) {
      this.myTable.reset();
      this.settingSearch();
      console.log(this.car);
      this.bookcarService
        .searchListcar(this.car)
        .subscribe(y => {
          this.ListCar = y.data;
          this.pageNumber = 0;
          this.total_record = this.ListCar.length;
        });
    }
  }

  handleAdd() {
    if (
      this.validate(
        this.squad,
        this.type.type,
        this.plate,
        this.seat.seat
      )
    ) {
      this.confirmationService.confirm({
        message: "Đồng chí có muốn Lưu bản ghi này không?",
        header: "Xác nhận Lưu",
        icon: "pi pi-info-circle",
        accept: () => {
          this.add = 1;
          
            this.settingCar();
            if (this.edit == 0) {
              this.insertData();
            } else {
              //this.updateData();
  
              this.settingCar();
              this.bookcarService.updateListcar(this.car).subscribe(x => {
                console.log("STATUS = " + x.status);
                if (x["status"] == 1) {
                  this.messageService.add({
                    severity: "success",
                    summary: "Thông báo",
                    detail: "Đã cập nhật thông tin xe."
                  });
                  var index = this.ListCar.findIndex(
                    x => x.carId == this.driveId
                  );
                  this.ListCar[index].squadName = this.squad.squadName;
                  this.ListCar[index].licensePlate = this.plate;
                  this.ListCar[index].type = this.type.type;
                  this.ListCar[index].seat = this.seat.seat;
                  this.ListCar[index].status = this.status;
  
                } else if (x["status"] == 0) {
                  this.messageService.add({
                    severity: "error",
                    summary: "Thông báo",
                    detail: "Có lỗi không xác định"
                  });
                } else if (x["status"] == 4) {
                  this.messageService.add({
                    severity: "error",
                    summary: "Thông báo",
                    detail: "Xe hiện tại đang làm nhiệm vụ."
                  });
                } else if (x["status"] == Constants.DELETE_UPDATE_ERROR) {
                  this.messageService.add({
                    severity: "error",
                    summary: "Thông báo",
                    detail: "Cập nhật không thành công. Xe này đã bị xóa bởi một người khác."
                  });
                }
                else if (x["status"] == 5) {
                  this.messageService.add({
                    severity: "error",
                    summary: "Thông báo",
                    detail: "Xe này đã tồn tại."
                  });
                }
  
                this.getListCar();
                this.monitorReset();
              });
              // 
            }
          
  
        },
        reject: () => {
          this.monitorReset();
        }
      });
    }
    
  }

  doDelete(obj) {

    this.confirmationService.confirm({
      message: "Đồng chí có muốn xóa bản ghi này không?",
      header: "Xác nhận xoá",
      icon: "pi pi-info-circle",
      accept: () => {
        this.car.licensePlate = obj.licensePlate;
        this.car.carId = obj.carId;
        console.log(this.car);
        this.bookcarService.deleteCar(this.car).subscribe(x => {
          if (x["status"] == 0) {
            this.messageService.add({
              severity: "error",
              summary: "Thông báo",
              detail: "Có lỗi không xác định"
            });
          } else if (x["status"] == 1) {
            this.getListCar();
            this.messageService.add({
              severity: "success",
              summary: "Thông báo",
              detail: "Đã xóa xe."
            });
            var index = this.ListCar.findIndex(
              x => x.licensePlate == obj.licensePlate
            );
            this.ListCar.splice(index, 1);
          } else if (x["status"] == 4) {
            this.messageService.add({
              severity: "error",
              summary: "Thông báo",
              detail: "Xe hiện tại đang làm nhiệm vụ."
            });
          } else if (x["status"] == Constants.DELETE_UPDATE_ERROR) {
            this.getListCar();
            this.messageService.add({
              severity: "error",
              summary: "Thông báo",
              detail: "Xóa không thành công. Xe này đã bị xóa bởi một người khác."
            });
          }
          this.monitorReset();

        });
      },
      reject: () => {
      }
    });

  }


  monitorReset() {
    if (this.myTable !== undefined) {
      this.myTable.reset();
    }
    this.edit = 0;
    this.isEdit = false;
    this.seat = new Seat("", "");
    this.type = new Type("", "");
    this.status = 1;
    this.plate = "";
    this.squad = null;
    this.driveId = null;
    this.isEdit = false;
  }

  resetButton() {
    this.squad = null;
    this.seat = new Seat("", "");
    this.type = new Type("", "");
    this.plate = "";
    this.status = 1;
    this.isEdit = false;
    this.getListCar();
  }

  insertData() {
    this.bookcarService.insertListcar(this.car).subscribe(y => {
      if (y["status"] == 0) {
        this.messageService.add({
          severity: "error",
          summary: "Thông báo",
          detail: "Trùng với đội xe đã tồn tại"
        });
      } else if (y["status"] == 1) {
        this.messageService.add({
          severity: "success",
          summary: "Thông báo",
          detail: "Đã thêm xe mới."
        });
      }
      this.getListCar();
      this.monitorReset();

    });
  }

  getCarType() {
    this.bookcarService
      .getCarType()
      .subscribe(y => (this.getListCarType = y.data));
  }

  getCarSeat() {
    this.bookcarService
      .getCarSeat()
      .subscribe(y => (this.ListCarSeat = y.data));
  }

  checkEmpty(input: string) {
    let i = 0;
    let output: string;
    output = '';
    for (i = 0; i < input.length; i++) {
      if (input.charAt(i) != '') output += input.charAt(i)
    }
    return output;
  }


  filterCountrySingle(event): any {
    let temp: any;
    if (this.squad == null) {
      temp = new DriveSquad(null, null, 0, null, null, null, null, null);
    } else {
      if (this.checkEmpty(this.squad) == '') temp = new DriveSquad(null, null, 0, null, null, null, null, null);
      else temp = new DriveSquad(null, this.squad.trim(), 0, null, null, null, null, null);
    }
    console.log(this.squad)
    this.bookcarService.findSquad(temp).subscribe(y => {
      this.filteredCountriesSingleSquad = y.data;
    });
  }

  getListCar() {
    this.bookcarService.getListcar().subscribe(y => {
      this.ListCar = y.data;
      this.total_record = this.ListCar.length;
    });
  }

  getListCarSeat() {
    return this.bookcarService
      .getCarSeat()
      .subscribe(y => (this.ListCarSeat = y.data));
  }

  getListCarType() {
    this.bookcarService
      .getCarType()
      .subscribe(y => (this.ListCarType = y.data));
  }

  getTeamCar() {
    this.bookcarService.getTeamCar().subscribe(y => {
      this.listteamcar = y.data;
    });
  }

  //temp: any;
  doEdit(car: Car) {
    this.isEdit = true;
    this.edit = 1;
    this.car.carId = car.carId;
    console.log(this.car.carId);
    //let index = this.listteamcar.findIndex(x => x.squadName == car.squadName);
    let index_sq =
      this.listteamcar.findIndex(x => x.squadId == car.squadId)
      ;
    console.log(index_sq);
    this.squad = this.listteamcar[index_sq];

    let indexSeat = this.ListCarSeat.findIndex(x => x.seat == car.seat);
    this.seat = this.ListCarSeat[indexSeat];

    let indexType = this.ListCarType.findIndex(y => y.type == car.type);
    this.type = this.ListCarType[indexType];
    this.driveId = car.carId;
    this.plate = car.licensePlate;
    this.status = car.status;
    this.car.squadId = car.squadId;
    // this.listteamcar.push(this.temp);
  }

  focusOutCar() {
    if (this.squad.squadId == null) {
      this.squad.squadName = "";
      this.squad = new DriveSquad(null, null, 0, null, null, null, 0, null);
      this.squad.squadName = "";
    }
  }


  setSeat() {
    if (this.seat == null || this.seat == undefined) {
      this.seat = new Seat("", "");
    }
  }

  setType() {
    if (this.type == null || this.type == undefined) {
      this.type = new Type("", "");
    }
  }


  getNameObject(suggest: string): any {
    let split = suggest.split(" - ");
    return split[0];
  }

  confirmAdd() {
    this.confirmationService.confirm({
      message: "Đồng chí có chắc chắn lưu bản ghi này không?",
      header: "Confirmation",
      icon: "pi pi-exclamation-triangle",

      accept: () => {
        this.handleAdd();
      },
      reject: () => {
        this.isEdit = false;
        this.edit = 0;
      }
    });
  }

  // xóa kí tự đặc biệt khi paste vào "Mã đội xe"
  onChangePlate() {
    if (this.notChar.test(this.inputPlate.nativeElement.value)) {
      this.inputPlate.nativeElement.value = this.inputPlate.nativeElement.value.replace(this.notChar, '');
    }
  }
}
