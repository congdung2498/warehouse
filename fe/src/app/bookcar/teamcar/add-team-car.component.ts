import {Component, OnInit, OnDestroy, ViewChild, ElementRef, Renderer2} from "@angular/core";
import {BookcarService} from "../bookcar.service";
import {Employee} from "../../Entity/Employee";
import {
    MessageService,
    ConfirmationService
} from "primeng/api";
import {Router, NavigationEnd} from "@angular/router";
import {Table} from "primeng/table";
import {Title} from "@angular/platform-browser";
import {SearchingCondition} from "../Entity/searchingCodition";
import {Constants} from "../../shared/containts";
import {AutoComplete} from "primeng/primeng";


@Component({
    selector: "app-add-team-car",
    templateUrl: "./add-team-car.component.html",
    styleUrls: ["./add-team-car.component.css"]
})
export class AddTeamCarComponent implements OnInit, OnDestroy {
    @ViewChild("myTable")
    myTable: Table;

    @ViewChild('inputSquad') private inputSquad: ElementRef;
    @ViewChild('inputPlace') private inputPlace: AutoComplete;
    @ViewChild('inputEmployee') private inputEmployee: AutoComplete;

    navigationSubscription;

    driveSquad: any;

    isEdit = false;

    driveId: string = null;

    listDriver: Employee[] = [];

    totalRecords: number;

    loading: boolean;

    pageNumber: number = 0;

    pageSize: number = 10;

    conditionSearch: SearchingCondition;

    // tslint:disable-next-line:max-line-length
    onlyChar: RegExp = /^[0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;

    // tslint:disable-next-line:max-line-length
    notChar: RegExp = /[^0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;

    cols = [
        {field: "", header: "STT", width: "5%"},
        {field: "", header: "Thao tác", width: "10%"},
        {field: "position", header: "Vị Trí", width: "30%"},
        {field: "teamName", header: "Tên Đội Xe", width: "25%"},
        {field: "full_name", header: "Tên Đội Trưởng", width: "20%"},
        {field: "status", header: "Trạng Thái", width: "10%"}
    ];

    constructor(
        private bookcarService: BookcarService,
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private router: Router,
        private title: Title,
        private renderer: Renderer2
    ) {
        this.navigationSubscription = this.router.events.subscribe(event => {
            if (!(event instanceof NavigationEnd)) {
                return;
            }
            // Do what you need to do here, for instance :
            this.status = "1";
            this.handleSearch();
        });
        this.loading = false;
        this.conditionSearch = new SearchingCondition();
    }

    // ******************************** START CODE MOI *********************************** //

    listPlace: any[];
    place: any;

    onChangeSquad() {
        if (this.squadName.length > 255) {
            this.showMessage("error", "Tối đa 255 kí tự", "")
            this.squadName = this.squadName.slice(0, 255).trim()
        }

        if (this.notChar.test(this.inputSquad.nativeElement.value)) {
            this.inputSquad.nativeElement.value = this.inputSquad.nativeElement.value.replace(this.notChar, '');
        }
    }

    removeBlank() {
        if (this.squadName != null)
            this.squadName = this.squadName.trim()
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

    /* Find Place */
    filterPlace(event) {
        let status: number;
        let query: any;
        if (this.place == null) {
            query = null;
        } else {
            if (this.checkEmpty(this.place) == '') query = null;
            else query = this.place;
        }
        this.bookcarService.findPlace(query).subscribe(x => {
            this.listPlace = x["data"];
            status = x["status"];
            if (status == 1) {
                // if (this.listPlace.length == 0) {
                //   this.showMessage("info", "Thông báo: Không có dữ liệu", "");
                // }
            } else {
                this.showMessage("error", "Thông báo: Có lỗi xảy ra", "");
            }
        });
    }

    listManagerDriver: any[];
    manager: any;
    placeId: string = null;

    getDriver(event) {
        this.placeId = event.placeId;
    }

    filterDriver(event) {
        this.phone = "";
        this.unitName = "";
        let status: number;
        let value1 = this.place == undefined ? null : "" + this.place.placeId;


        let query: any;
        if (this.manager == null) {
            query = null;
        } else {
            if (this.checkEmpty(this.manager) == '') query = null;
            else query = this.manager;
        }

        this.bookcarService.findLeader(value1, query).subscribe(x => {
            this.listManagerDriver = x["data"];
            status = x["status"];
            if (status == 1) {
                if (this.listManagerDriver.length == 0) {
                    // this.showMessage("info", "Thông báo: Không có dữ liệu", "");
                } else {
                    this.listManagerDriver.forEach(x => {
                        x.displayOption = x.fullName + " - " + x.unitName;
                    });
                }

            } else {
                this.showMessage("error", "Thông báo: Có lỗi xảy ra", "");
            }
        });
    }

    phone: string;
    unitName: string;

    getNameObject(suggest: string): any {
        if (suggest == null) return "";
        let split = suggest.split(" - ");
        return split[0];
    }


    setPhoneAndUnitName(event) {
        console.log(event);
        this.manager = {
            "displayOption": event.fullName,
            "empPhone": event.empPhone,
            "fullName": event.fullName,
            "placeId": event.placeId,
            "role": event.role,
            "unitId": event.unitId,
            "unitName": event.unitName,
            "userName": event.userName
        };
        //this.manager= event;
        this.phone = event.empPhone;
        this.unitName = event.unitName;
    }

    listDriveSquad: any[];

    /* Searching function */
    findSearchingResult() {
        let status1: number;
        this.bookcarService
            .findSearchingResult(this.conditionSearch)
            .subscribe(x => {
                this.listDriveSquad = x["data"];
                if (this.listDriveSquad.length > 0) {
                    if (
                        (this.conditionSearch.squadName == null ||
                            this.conditionSearch.squadName == "") &&
                        (this.conditionSearch.userName == "" ||
                            this.conditionSearch.userName == null) &&
                        this.conditionSearch.placeId == null
                    ) {
                        this.totalRecords = this.listDriveSquad[0].totalRecords;
                    } else {
                        if (this.myTable !== undefined) {
                            this.myTable.reset();
                            this.totalRecords = this.listDriveSquad.length;
                        }
                    }
                }

                status1 = x["status"];

                if (status1 == 1) {
                    if (this.listDriveSquad.length == 0) {
                        this.pageNumber = 0;
                        this.totalRecords = 0;
                        //this.showMessage("info", "Không tìm thấy dữ liệu phù hợp", "");
                    } else {
                        this.listDriveSquad.forEach(x => {
                            x.displayOption = x.fullName;
                        });
                    }
                } else {
                    this.showMessage(
                        "error",
                        "Có lỗi không xác định, vui lòng thử lại sau!",
                        ""
                    );
                }
            });
    }

    squadName: string;
    status: string = "1";

    handleSearch() {
        this.pageNumber = 0;

        console.log("abc");
        if (this.myTable !== undefined) {
            this.myTable.reset();
            let value1 =
                this.squadName === undefined ||
                this.squadName === null ||
                this.squadName === ""
                    ? null
                    : this.squadName;
            let value2 =
                this.place === undefined || this.place === null
                    ? null
                    : "" + this.place.placeId;
            let value3 =
                this.manager === undefined || this.manager === null
                    ? null
                    : this.manager.userName;
            let value4 =
                this.status === undefined || this.status === null ? "1" : this.status;
            this.conditionSearch.squadName = value1;
            this.conditionSearch.placeId = value2;
            this.conditionSearch.userName = value3;
            this.conditionSearch.status = value4;
            this.conditionSearch.pageNumber = this.pageNumber;
            this.conditionSearch.pageSize = this.pageSize;
            this.findSearchingResult();
        }
    }

    doEdit(data) {
        this.isEdit = true;
        this.place = null;
        this.driveId = data.squadId;
        this.place = data;
        this.squadName = data.squadName;
        this.status = data.status;
        this.manager = data;
        this.phone = data.emplPhone;
        this.unitName = data.unitName;
    }

    doDelete(data) {
        var status1: number;
        this.confirmationService.confirm({
            message: "Đồng chí có muốn xóa đội xe?",
            rejectVisible: true,
            header: "Xác nhận xóa",
            icon: "pi pi-info-circle",
            acceptLabel: 'Đồng ý',
            rejectLabel: 'Hủy bỏ',
            accept: () => {
                this.bookcarService.deleteSquad(data.squadId).subscribe(x => {
                    status1 = x["status"];
                    if (status1 == Constants.DELETE_SQUAD_SUCESS) {
                        this.showMessage("success", "Đã xóa đội xe", "");

                        var index = this.listDriveSquad.findIndex(
                            x => x.squadId == data.squadId
                        );
                        this.listDriveSquad.splice(index, 1);
                        this.totalRecords -= 1;
                    } else if (status1 == 5) {
                        this.showMessage("error", "Có xe trong đội đang làm nhiệm vụ", "");
                    } else if (status1 == 0) {
                        this.showMessage(
                            "error",
                            "Có lỗi không xác định, vui lòng thử lại sau!",
                            ""
                        );
                    }  else if (status1 == Constants.DELETE_UPDATE_ERROR) {
                        this.confirmationService.confirm({
                            message: "Xóa không thành công. Đội xe đã bị xóa bởi một người khác",
                            header: "Thông báo",
                            rejectVisible: false,
                            icon: "pi pi-info-circle",
                            accept: () => {
                                var index = this.listDriveSquad.findIndex(
                                    x => x.squadId == data.squadId
                                );
                                this.listDriveSquad.splice(index, 1);
                                this.totalRecords = this.listDriveSquad.length;
                            }
                        });
                    }
                    this.resetForm();
                });
            }

        });
    }

    handleAdd() {
        this.conditionSearch = new SearchingCondition();
        this.conditionSearch.pageNumber = this.pageNumber;
        this.conditionSearch.pageSize = this.pageSize;
        this.conditionSearch.status = "1";
        let status1: number;
        let check = this.checkDataBeforeAdd();
        if (check) {
            this.confirmationService.confirm({
                message: "Đồng chí có muốn Lưu bản ghi này không?",
                rejectVisible: true,
                header: "Xác nhận Lưu",
                icon: "pi pi-info-circle",
                acceptLabel: 'Đồng ý',
                rejectLabel: 'Hủy bỏ',
                accept: () => {


                    this.bookcarService
                        .insertUpdate(
                            this.driveId,
                            this.squadName,
                            "" + this.place.placeId,
                            this.manager.userName,
                            this.status,
                            this.phone
                        )
                        .subscribe(x => {
                            status1 = x["status"];
                            if (status1 == Constants.RESPONSE_SUCESS) {
                                this.showMessage("success", "Đã thêm mới đội xe", "");
                                this.inputSquad.nativeElement.focus();
                                this.findSearchingResult();
                                this.resetForm();
                            } else if (status1 == Constants.UPDATE_SUCESS) {
                                this.showMessage("success", "Đã cập nhật thông tin đội xe", "");
                                this.inputSquad.nativeElement.focus();
                                var index = this.listDriveSquad.findIndex(
                                    squad => squad.squadId == this.driveId
                                );
                                this.listDriveSquad[index].squadName = this.squadName;
                                this.listDriveSquad[index].placeName = this.place.placeName;
                                this.listDriveSquad[index].placeId = this.place.placeId;
                                this.listDriveSquad[index].fullName = this.manager.fullName;
                                this.listDriveSquad[index].status = this.status;
                                this.listDriveSquad[index].unitName = this.unitName;
                                this.listDriveSquad[index].emplPhone = this.phone;
                                this.listDriveSquad[
                                    index
                                    ].displayOption = this.manager.displayOption;
                                this.listDriveSquad[index].userName = this.manager.userName;
                                this.isEdit = false;
                                this.resetForm();
                            } else if (status1 == Constants.DRIVE_SQUAD_EXIST) {
                                this.inputPlace.domHandler.findSingle(this.inputPlace.el.nativeElement, 'input').focus();
                                this.showMessage("error", "Vị trí đã tồn tại đội xe", "");

                            } else if (status1 == Constants.DELETE_UPDATE_ERROR) {
                                this.showMessage("error", "Cập nhập không thành công. Đội xe đã bị xoá bởi một người khác.", "");
                                this.inputSquad.nativeElement.focus();
                            } else if (status1 == Constants.STATUS_DUPLICATE_MANAGER_CAR_SQUAD) {
                                this.showMessage("error", "Đồng chí này đã được gán cho đội xe khác", "");
                                this.inputEmployee.domHandler.findSingle(this.inputEmployee.el.nativeElement, 'input').focus();
                            } else if (status1 == Constants.RESPONSE_ERROR) {
                                this.showMessage("error", "Có lỗi không xác định, vui lòng thử lại sau!", "");
                                this.manager = null;
                                this.phone = null;
                                this.unitName = null;
                            } else if (status1 == 10) {
                                this.showMessage(
                                    "error",
                                    "Có lỗi không xác định, vui lòng thử lại sau!",
                                    ""
                                );
                                this.manager = null;
                                this.phone = null;
                                this.unitName = null;
                            } else if (status1 == 5) {
                                this.showMessage(
                                    "error",
                                    "Có xe trong đội đang làm nhiệm vụ.",
                                    ""
                                );
                                this.inputSquad.nativeElement.focus();
                            }
                        });

                },
                reject: () => {

                    this.isEdit = false;
                    this.resetForm();
                }
            });
        }

    }

    /* Validate data before add */
    checkDataBeforeAdd() {
        let check = true;
        var value1 =
            this.manager === undefined || this.manager === null
                ? null
                : this.manager.fullName;
        var value2 =
            this.place === undefined || this.place === null
                ? null
                : this.place.placeName;
        if (!this.validate(this.squadName, "Tên đội xe không được để trống", true)) {
            check = false;
            this.inputSquad.nativeElement.focus();
            return check;
        }
        if (!this.validate(value2, "Vị trí không được để trống", true)) {
            check = false;

            this.inputPlace.domHandler.findSingle(this.inputPlace.el.nativeElement, 'input').focus();
            return check;
        }
        if (!this.validate(value1, "Tên Đội trưởng không được để trống", true)) {
            check = false;
            this.inputEmployee.domHandler.findSingle(this.inputEmployee.el.nativeElement, 'input').focus();
            return check;
        }
        if (!this.validate(this.status, "Trạng thái không được để trống", true)) {
            check = false;
            return check;
        }
        if (!this.validate(this.phone, "Số điện thoại không được để trống", true)) {
            check = false;

            return check;
        }
        if (!this.validate(this.unitName, "Đơn vị không được để trống", true)) {
            check = false;
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

    showMessage(severity: string, summary: string, detail: string) {
        this.messageService.add({
            severity: severity,
            summary: summary,
            detail: detail
        });
    }

    isSelected() {
        if (this.manager == "") {
            this.phone = null;
            this.unitName = null;
        }
        if (this.place != undefined && this.place != null) {
            if (this.place.placeId == null || this.place.placeId == undefined) {
                this.place = null;
            }
        }
        if (this.manager != undefined && this.manager != null) {
            if (this.manager.userName == null || this.manager.userName == undefined) {
                this.manager = null;
            }
        }
    }

    paginate(event) {
        this.loading = true;
        setTimeout(() => {
            let pagenumber = event.first;
            this.conditionSearch.pageNumber = pagenumber;
            this.pageNumber = pagenumber;
            this.findSearchingResult();
            this.loading = false;
        }, 1000);
    }

    resetForm() {
        if (this.myTable !== undefined) {
            this.myTable.reset();
        }
        this.driveId = null;
        this.place = null;
        this.manager = null;
        this.squadName = null;
        this.phone = null;
        this.unitName = null;
        this.handleSearch();
    }

    resetButton() {
        this.driveId = null;
        this.place = null;
        this.manager = null;
        this.squadName = null;
        this.phone = null;
        this.unitName = null;
        this.isEdit = false;
        this.handleSearch();
    }

    // ******************************** END CODE MOI *********************************** //

    ngOnInit() {
        this.handleSearch();
        this.title.setTitle("Thêm mới đội xe - VTNet");
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
