"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var Employee_1 = require("../Entity/Employee");
var Place_1 = require("../Entity/Place");
var ReportStationery_1 = require("../Entity/ReportStationery");
var common_1 = require("@angular/common");
var file_saver_1 = require("file-saver");
var animations_1 = require("@angular/animations");
var validate_1 = require("../../../common/validate");
var datetime_1 = require("../../../common/datetime");
var pixelWidth = require('string-pixel-width');
var ReportStationeryEmployeeComponent = /** @class */ (function () {
    function ReportStationeryEmployeeComponent(stationeryService, kitchenService, confirmationService, messageService, renderer, tokenStorage, title, app) {
        this.stationeryService = stationeryService;
        this.kitchenService = kitchenService;
        this.confirmationService = confirmationService;
        this.messageService = messageService;
        this.renderer = renderer;
        this.tokenStorage = tokenStorage;
        this.title = title;
        this.app = app;
        this.status = [
            { label: 'Đang xử lý', value: '0' },
            { label: 'Hoàn thành một phần', value: '1' },
            { label: 'Hoàn thành', value: '2' },
            { label: 'Từ chối', value: '3' },
            { label: 'Hủy yêu cầu', value: '4' }
        ];
        this.ListSelectedStatus = [];
        this.vn = datetime_1.DateTimeUtil.vn;
        this.listChooseEmployee = [];
        this.searchData = {
            issuesStationeryApprovedId: ''
        };
        this.uploadedFiles = [];
        this.dialogDetailsUnit = false;
        this.isDisplay = false;
        this.statusConstant = 'Đang xử lý';
        this.lstChefInfor = [];
        this.listChefInforById = [];
        this.canceIssuesStationey = {
            userName: null,
            issuesStationeryId: null,
            listIssueStationeryItemId: []
        };
        this.editIssuesStationey = {
            placeID: null,
            listStationery: [],
            issuesStationeryIdOld: '',
            note: ''
        };
        this.infoToSearchIssuesStationeryItems = {
            employeeUserName: '',
            managerUserName: '',
            requestDate: null,
            pageNumber: null,
            pageSize: null,
            status: null,
            roleAdmin: true
        };
        this.issuesStationeryItemsToInsert = {
            listStationery: [''],
            issuesStationeryItemID: '',
            issuesStationeryID: '',
            stationeryID: '',
            issuesStationeryApprovedID: '',
            issuesStationeryRegistryID: '',
            status: null,
            totalRequest: null,
            totalFulFill: null,
        };
        this.placeStationery = {
            placeId: null,
            placeName: '',
            lstPlaceId: ''
        };
        this.total_record_employee = 0;
        this.searchDataById = {
            issuesStationeryApprovedId: ''
        };
        this.display = false;
        this.requestParam = {
            placeID: null,
            listStationery: [],
            note: ''
        };
        this.stationeryRequest = {
            stationeryId: null,
            quantity: null
        };
        this.listSelectedUnitId = [];
        this.loadingTree = false;
        this.pageNumber = 0;
        this.pageSize = 10;
        this.selectedItem = [];
        this.isCancel = false;
        this.isView = false;
        this.rowSize = 0;
        this.startRow = 0;
    }
    ReportStationeryEmployeeComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.reportStationery = new ReportStationery_1.ReportStationery(null, 0, null, null, null, null, null, 0, null, 0, null, null, [], [], null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        this.uf = this.tokenStorage.getUserInfo();
        this.listUnitManager = [];
        this.fromDate = new Date();
        this.toDate = new Date();
        this.averageRatting = 0;
        this.len = 50;
        this.total_record = 0;
        this.maxLenStatus = 431;
        this.textShow = 'Chọn tất cả';
        this.title.setTitle('Thống kê yêu cầu văn phòng phẩm - PMQTVP');
        this.setRole();
        this.checkRole();
        this.setStatus();
        this.lstChefInfor = [];
        this.lstChefInfor.push(new ReportStationery_1.StationeryReportRequest(null, null, null, null, null));
        this.selectedStationeryItem = new ReportStationery_1.ReportStationery(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        this.stationeryService.findStationeryByName().subscribe(function (data) {
            _this.stationeryNameList = data.data;
        });
        this.empUnit = this.uf.unitName;
        this.empName = this.uf.fullName;
        this.empPhone = this.uf.phoneNumber;
        this.empEmail = this.uf.email;
    };
    ReportStationeryEmployeeComponent.prototype.setRole = function () {
        if (this.uf.role.includes('PMQT_ADMIN')) {
            this.isAdmin = true;
        }
        else if (this.uf.role.includes('PMQT_HCVP_VPP')) {
            this.isHCVP_VPP = true;
        }
        else if (this.uf.role.includes('PMQT_HC_DV')) {
            this.isHCDV = true;
        }
        else if (this.uf.role.includes('PMQT_QL')) {
            this.isQL = true;
        }
        else if (this.uf.role.includes('PMQT_NV')) {
            this.isNV = true;
            this.isDisableEmployeeFld = true;
            this.employee = { userName: this.uf.userName, fullName: this.uf.fullName };
        }
    };
    ReportStationeryEmployeeComponent.prototype.setStatus = function () {
        this.convertStatusUnit = ['Khởi tạo',
            'Chờ LĐ phê duyệt',
            'Lãnh đạo phê duyệt',
            'LD từ chối',
            'VPTCT tiếp nhận',
            'VPTCT Hoãn thục hiện',
            'VPTCT Không thực hiện được',
            'VPTCT cấp phát cho HCDV',
            'HCDV xác nhận nhận VPP (Hoàn thành)',
            'Hủy'];
        this.convertStatus = ['', 'Đang xử lý', 'Hoàn thành một phần', 'Hoàn thành', 'Từ chối', 'Hủy yêu cầu'];
    };
    ReportStationeryEmployeeComponent.prototype.onHeaderCheckboxToggle = function (event) {
        this.listChooseEmployee = [];
        if (event.checked) {
            if (!this.listStationeryReportEmployee || this.listStationeryReportEmployee.length === 0)
                return;
            for (var i = 0; i < this.listStationeryReportEmployee.length; i++) {
                if (this.listStationeryReportEmployee[i].statusUnit === 0) {
                    this.listChooseEmployee.push(this.listStationeryReportEmployee[i]);
                }
            }
        }
    };
    ReportStationeryEmployeeComponent.prototype.closeDetailsUnit = function () {
    };
    ReportStationeryEmployeeComponent.prototype.refuse = function () {
    };
    ReportStationeryEmployeeComponent.prototype.approveHandle = function () {
    };
    ReportStationeryEmployeeComponent.prototype.onBasicUpload = function () {
    };
    //  set min date when click 'Tu' field
    ReportStationeryEmployeeComponent.prototype.selectFromDate = function () {
        if (this.toDate && this.toDate < this.fromDate) {
            this.toDate = this.fromDate;
        }
    };
    //  set max date when click 'Den' field
    ReportStationeryEmployeeComponent.prototype.selectToDate = function () {
        if (this.fromDate && this.toDate && this.fromDate > this.toDate) {
            this.fromDate = this.toDate;
        }
    };
    //  when click icon 'x' of from date
    ReportStationeryEmployeeComponent.prototype.clearFromDate = function (event) {
        if (this.fromDate) {
            event.stopPropagation();
            this.fromDate = null;
        }
    };
    //  when click icon 'x' of to date
    ReportStationeryEmployeeComponent.prototype.clearToDate = function (event) {
        if (this.toDate) {
            event.stopPropagation();
            this.toDate = null;
        }
    };
    ReportStationeryEmployeeComponent.prototype.checkRole = function () {
        var _this = this;
        this.stationeryService.findSpendingLimitQuota().subscribe(function (y) {
            if (y && y.data)
                _this.limitQuota = y.data['spendingLimit'];
        });
        if (this.uf.role.includes('PMQT_ADMIN') || this.uf.role.includes('PMQT_HC_DV') || this.uf.role.includes('PMQT_HCVP_VPP')) {
            this.getSector(0);
        }
        else {
            this.getSector(2);
        }
    };
    ReportStationeryEmployeeComponent.prototype.getSector = function (type) {
        var _this = this;
        if (this.isQL) {
            this.kitchenService.getSectorGreatFather(this.uf.unitId.toString()).subscribe(function (data) {
                _this.sectors = data;
            });
        }
        else {
            if (type === 0) {
                this.kitchenService.getSectors(null).subscribe(function (data) {
                    _this.sectors = data;
                });
            }
            else if (type === 1) {
                this.kitchenService.getSectors(this.uf.unitId).subscribe(function (data) {
                    _this.sectors = data;
                });
            }
            else if (type === 2) {
                this.sectors = [{
                        label: this.uf.unitName,
                        data: this.uf.unitId,
                        selectable: false
                    }];
                this.selectedSector = [this.sectors[0]];
            }
        }
    };
    ReportStationeryEmployeeComponent.prototype.nodeExpand = function (event) {
        var _this = this;
        this.loadingTree = true;
        if (event.node && event.node.children !== undefined) {
            this.loadingTree = false;
            return;
        }
        if (event.node && event.node.children === undefined) {
            this.kitchenService
                .getSectors(event.node.data).subscribe(function (data) {
                event.node.children = data;
                _this.loadingTree = false;
            });
        }
    };
    ReportStationeryEmployeeComponent.prototype.getReportStationery = function () {
        var _this = this;
        this.stationeryService.getReportStationery().subscribe(function (data) {
            _this.listStationeryReport = data.data;
            data.data.forEach(function (element) {
                _this.averageRatting += element.ratting;
            });
            _this.averageRatting = Number((_this.averageRatting / _this.listStationeryReport.length).toFixed(2));
            _this.total_record = _this.listStationeryReport.length;
        });
    };
    ReportStationeryEmployeeComponent.prototype.filterPlace = function (event) {
        var _this = this;
        var temp;
        if (this.place == null) {
            temp = new Place_1.Place(null, null);
        }
        else {
            if (this.checkEmpty(this.place) == '') {
                temp = new Place_1.Place(null, null);
            }
            else {
                temp = new Place_1.Place(null, this.place.trim());
            }
        }
        if (this.isHCDV) {
            temp.username = this.uf.userName.toString();
            this.stationeryService.findPlaceByHCDV(temp).subscribe(function (data) {
                _this.listFilterPlace = data.data;
            });
        }
        else {
            this.stationeryService.findPlace(temp).subscribe(function (data) {
                _this.listFilterPlace = data.data;
            });
        }
    };
    ReportStationeryEmployeeComponent.prototype.filterEmployee = function (event) {
        var _this = this;
        var temp;
        if (this.employee == null) {
            temp = new Employee_1.Employee(null, null, null, null, null, null, null);
        }
        else {
            if (this.checkEmpty(this.employee) == '') {
                temp = new Employee_1.Employee(null, null, null, null, null, null, null);
            }
            else {
                temp = new Employee_1.Employee(null, this.employee.trim(), this.employee.trim(), null, null, null, null);
            }
        }
        this.stationeryService.getEmployees(temp).subscribe(function (data) {
            _this.listFilterEmployee = data.data;
        });
    };
    ReportStationeryEmployeeComponent.prototype.employeeSelect = function (event) {
        this.employee = new Employee_1.Employee(event.employeeId, this.getNameObject(event.fullName), event.userName, event.employeePhone, event.role, event.place, event.unit);
    };
    ReportStationeryEmployeeComponent.prototype.getNameObject = function (suggest) {
        if (suggest == null)
            return "";
        var split = suggest.split(" - ");
        return split[0];
    };
    ReportStationeryEmployeeComponent.prototype.checkEmpty = function (input) {
        var i = 0;
        var output;
        output = '';
        for (i = 0; i < input.length; i++) {
            if (input.charAt(i) != '')
                output += input.charAt(i);
        }
        return output;
    };
    ReportStationeryEmployeeComponent.prototype.focusOutPlace = function () {
        if (this.place != null && this.place != undefined) {
            if (this.place.placeId == null || this.place.placeId == undefined) {
                this.place = null;
            }
        }
    };
    ReportStationeryEmployeeComponent.prototype.focusOutEmployee = function () {
        if (this.employee != null && this.employee != undefined) {
            if (this.employee.employeeId == null || this.employee.employeeId == undefined) {
                this.employee = null;
            }
        }
    };
    ReportStationeryEmployeeComponent.prototype.showDialog = function (rowData) {
        var _this = this;
        this.reportStationery.rpStationeryId = rowData.rpStationeryId;
        this.stationeryService.getFullfillStationery(this.reportStationery).subscribe(function (y) {
            _this.listStationeryFullfill = y.data;
            var item = _this.listStationeryFullfill[0];
            _this.reportStationery.fullName = rowData.fullName;
            _this.reportStationery.status = rowData.status - 1;
            _this.reportStationery.lastUser = item.lastUser;
            //this.reportStationery.lastUser = item.lastUser;
            _this.stationeryService.getLastUser(_this.reportStationery).subscribe(function (data) {
                _this.reportStationery.lastUser = data.data;
            });
        });
        this.display = true;
    };
    ReportStationeryEmployeeComponent.prototype.close = function () {
        this.display = false;
    };
    ReportStationeryEmployeeComponent.prototype.selectRow = function (rowData) {
        var _this = this;
        this.unitEmployee = rowData.unitName;
        this.nameEmployee = rowData.fullName;
        this.phoneEmployee = rowData.phoneNumber;
        this.emailEmployee = rowData.email;
        this.placeEmployee = rowData.startPlace;
        this.stationeryRequestStr = rowData.stationeryName;
        this.stationeryQuantityStr = rowData.totalRequest;
        this.dialogEditRequestEmployee = true;
        this.isCancel = true;
        this.isView = true;
        this.massage = rowData.note;
        var temp = new Place_1.Place(rowData.startPlace, null);
        this.stationeryService.findPlace(temp).subscribe(function (data) {
            _this.listFilterPlace = data.data;
            _this.listFilterPlace.forEach(function (el) {
                if (el.placeId == rowData.startPlaceId) {
                    _this.placeStationery = el;
                }
            });
        });
        if (rowData.issuesStationeryItemId) {
            this.searchDataById.issuesStationeryApprovedId = rowData.issuesStationeryId;
            this.stationeryService.getIssuesStationeryItemsById(this.searchDataById).subscribe(function (res) {
                if (res) {
                    _this.stationeryNameListById = res.data;
                    _this.lstChefInfor = [];
                    _this.stationeryNameListById.forEach(function (el) {
                        _this.lstChefInfor.push(new ReportStationery_1.StationeryReportRequest(el.stationeryId, el.quantity, el.calUnit, el.unitPrice, el.stationeryName));
                    });
                }
            });
        }
        this.stationeryService.findSpentQuotaByUser(rowData.employeeUsername).subscribe(function (res) {
            if (res) {
                _this.quotaEmployee = res.data;
            }
        });
    };
    ReportStationeryEmployeeComponent.prototype.settingSearch = function () {
        var _this = this;
        if (this.employee != null && this.employee.userName != undefined) {
            this.reportStationery.userName = this.employee.userName;
        }
        else {
            this.reportStationery.userName = null;
        }
        if (this.place != null && this.place.placeName != undefined) {
            this.reportStationery.placeName = this.place.placeName;
            this.reportStationery.placeId = this.place.placeId;
        }
        else {
            if (this.place === '')
                this.place = null;
            this.reportStationery.placeName = this.place;
            this.reportStationery.placeId = this.place;
        }
        this.listSelectedUnitId = [];
        if (this.selectedSector == null || this.selectedSector.length === 0) {
            this.listSelectedUnitId = [this.sectors[0].data];
        }
        else
            this.selectedSector.forEach(function (element) {
                _this.listSelectedUnitId.push(element.data);
            });
        this.reportStationery.listUnitId = this.listSelectedUnitId;
        this.reportStationery.listStatus = this.ListSelectedStatus;
        this.reportStationery.startDate = this.fromDate;
        this.reportStationery.endDate = this.toDate;
    };
    ReportStationeryEmployeeComponent.prototype.handleSearchEmployee = function () {
        this.reportStationery.pageSize = 15;
        this.reportStationery.pageNumber = 0;
        this.settingSearch();
        this.getListReport();
    };
    ReportStationeryEmployeeComponent.prototype.onPaginate = function (event) {
        var _this = this;
        this.startRow = event.first + 1;
        this.reportStationery.pageNumber = event.first;
        this.rowSize = this.startRow + 14;
        if (this.rowSize > this.total_record_employee)
            this.rowSize = this.total_record_employee;
        this.stationeryService.searchStationeryByEmployee(this.reportStationery).subscribe(function (data) {
            _this.listStationeryReportEmployee = data.data;
        });
    };
    ReportStationeryEmployeeComponent.prototype.getListReport = function () {
        var _this = this;
        this.stationeryService.countStationeryByEmployee(this.reportStationery).subscribe(function (data) {
            _this.total_record_employee = data.data;
            _this.rowSize = 15;
            if (_this.rowSize > _this.total_record_employee)
                _this.rowSize = _this.total_record_employee;
            if (_this.total_record_employee > 0) {
                _this.startRow = 1;
                _this.stationeryService.searchStationeryByEmployee(_this.reportStationery).subscribe(function (data) {
                    _this.listStationeryReportEmployee = data.data;
                    if (_this.myEmployee)
                        _this.myEmployee.reset();
                });
            }
            else {
                _this.startRow = 0;
                _this.listStationeryReportEmployee = null;
                if (_this.myEmployee)
                    _this.myEmployee.reset();
            }
        });
    };
    ReportStationeryEmployeeComponent.prototype.exportExcelVPP = function () {
        this.settingSearch();
        var fileName = 'TKDUVPP_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
            + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
            + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
        if (this.reportStationery.listUnitId == null || this.reportStationery.listUnitId.length == 0) {
            this.reportStationery.listUnitId = this.sectors.map(function (sector) { return sector.data; });
        }
        this.stationeryService.exportExcelVPP(this.reportStationery).subscribe(function (next) {
            var file = new Blob([next], { type: 'application/xlsx' });
            file_saver_1.saveAs(file, fileName);
        });
    };
    ReportStationeryEmployeeComponent.prototype.exportExcel = function () {
        this.settingSearch();
        var fileName = 'TKYCVPP_' + this.uf.userName + '_' + ('0' + new Date().getDate()).slice(-2) + '_'
            + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
            + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + new Date().getMilliseconds() + 'ms.xlsx';
        this.stationeryService.exportExcelEmployee(this.reportStationery).subscribe(function (next) {
            var file = new Blob([next], { type: 'application/xlsx' });
            file_saver_1.saveAs(file, fileName);
        });
    };
    ReportStationeryEmployeeComponent.prototype.selectedAll = function (event) {
        event.stopPropagation();
        if (this.ListSelectedStatus != null && this.ListSelectedStatus.length === this.status.length) {
            this.ListSelectedStatus = [];
            this.textShow = 'Chọn tất cả';
        }
        else {
            this.ListSelectedStatus = [];
            for (var i = 0; i < this.status.length; i++) {
                this.ListSelectedStatus.push(this.status[i].value);
                this.textShow = 'Bỏ chọn tất cả';
            }
        }
        this.onChange();
    };
    ReportStationeryEmployeeComponent.prototype.onChange = function () {
        if (this.ListSelectedStatus != null && this.ListSelectedStatus.length === this.status.length) {
            this.textShow = 'Bỏ chọn tất cả';
        }
        else {
            this.textShow = 'Chọn tất cả';
        }
        if (this.ListSelectedStatus == null) {
            this.textShow = '';
        }
        var str = [];
        var child = this.myDrop.el.nativeElement.children[0].children[1];
        this.len = 50 + pixelWidth(',...', { size: 16 });
        if (this.ListSelectedStatus != null
            && this.ListSelectedStatus !== undefined
            && this.ListSelectedStatus.length > 0) {
            for (var i = 0; i < this.ListSelectedStatus.length; i++) {
                this.len += pixelWidth(this.status[this.ListSelectedStatus[i]].label, { size: 16 });
                if (this.len < this.maxLenStatus) {
                    if (i === 0) {
                        str.push(this.status[this.ListSelectedStatus[i]].label);
                    }
                    else {
                        str.push(' ' + this.status[this.ListSelectedStatus[i]].label);
                    }
                }
            }
            if (this.len > this.maxLenStatus) {
                child.children[0].innerText = str.toString() + ',...';
            }
            else {
                child.children[0].innerText = str.toString();
            }
        }
        else {
            child.children[0].innerText = '-- Chọn trạng thái --';
        }
    };
    ReportStationeryEmployeeComponent.prototype.onShow = function () {
        var child = this.myDrop.el.nativeElement.children[0].children[3];
        this.renderer.setStyle(child, 'max-width', 'inherit');
        this.renderer.setStyle(child, 'width', 'inherit');
        this.isDisplay = true;
    };
    ReportStationeryEmployeeComponent.prototype.onHide = function () {
        this.isDisplay = false;
    };
    ReportStationeryEmployeeComponent.prototype.checkAll = function (isChecked) {
        if (!this.listStationeryReport || this.listStationeryReport.length == 0) {
            return;
        }
        this.listActivity = [];
        if (isChecked) {
            for (var i = 0; i < this.listStationeryReport.length; i++) {
                var booking = this.listStationeryReport[i];
                this.listActivity.push(booking.rpStationeryId);
            }
        }
    };
    ReportStationeryEmployeeComponent.prototype.checkAllEmployee = function (isChecked) {
        if (!this.listStationeryReportEmployee || this.listStationeryReportEmployee.length == 0) {
            return;
        }
        this.listActivityEmployee = [];
        if (isChecked) {
            for (var i = 0; i < this.listStationeryReportEmployee.length; i++) {
                var booking = this.listStationeryReportEmployee[i];
                this.listActivityEmployee.push(booking.issuesStationeryItemId);
            }
        }
    };
    ReportStationeryEmployeeComponent.prototype.checkItemEmployee = function (isChecked) {
        if (isChecked) {
            if (this.listActivityEmployee.length === this.listActivityEmployee.length)
                this.isCheckAllEmployee = true;
        }
        else {
            this.isCheckAllEmployee = false;
        }
    };
    ReportStationeryEmployeeComponent.prototype.checkItem = function (isChecked) {
        if (isChecked) {
            if (this.listActivity.length === this.listStationeryReport.length)
                this.isCheckAll = true;
        }
        else {
            this.isCheckAll = false;
        }
    };
    ReportStationeryEmployeeComponent.prototype.settingInfoItem = function (event) {
        this.infoToSearchIssuesStationeryItems.status = event.status;
        this.infoToSearchIssuesStationeryItems.requestDate = event.requestDate;
    };
    ReportStationeryEmployeeComponent.prototype.addRequest = function () {
        var _this = this;
        var isValid = this.onValidate(this.placeAddFld);
        if (isValid) {
            this.requestParam.listStationery = this.lstChefInfor;
            this.requestParam.placeID = this.placeStationery.placeId;
            this.requestParam.note = this.note ? this.note : '';
            this.stationeryService.insertIssuesStationery(this.requestParam).subscribe(function (y) {
                if (y.status == 0) {
                    _this.app.showWarn('Có lỗi không xác định');
                }
                if (y.status == 1) {
                    _this.app.showSuccess('Thêm mới thành công');
                    _this.dialogAddStationery = false;
                    _this.handleSearchEmployee();
                }
                if (y.status == 2) {
                    _this.app.showWarn('Số tiền yêu cầu không được lớn hơn định mức còn lại');
                }
            });
        }
    };
    ReportStationeryEmployeeComponent.prototype.resetButton = function () {
        this.fromDate = new Date();
        this.toDate = new Date();
        this.ListSelectedStatus = [];
        this.onChange();
        this.selectedSector = [];
        this.place = null;
        this.employee = null;
        this.handleSearchEmployee();
    };
    ReportStationeryEmployeeComponent.prototype.indexTracker = function (index, value) {
        return index;
    };
    ReportStationeryEmployeeComponent.prototype.selectedAddStationery = function (i, event) {
        var count = 0;
        if (this.lstChefInfor[i] != null) {
            this.lstChefInfor.forEach(function (element) {
                if (element.stationeryId === event.value.stationeryId) {
                    count++;
                }
            });
            if (count > 1) {
                this.app.showWarn('Văn phòng phẩm không được chọn trùng');
                this.lstChefInfor.splice(i, 1);
            }
        }
    };
    ReportStationeryEmployeeComponent.prototype.addRequestStationery = function () {
        var _this = this;
        this.dialogAddStationery = true;
        this.stationeryService.findSpendingLimitQuota().subscribe(function (y) {
            if (y.data)
                _this.limitQuota = y.data['spendingLimit'];
        });
    };
    ReportStationeryEmployeeComponent.prototype.removeChef = function (index) {
        this.lstChefInfor.splice(index, 1);
    };
    ReportStationeryEmployeeComponent.prototype.addChef = function () {
        this.lstChefInfor.push(new ReportStationery_1.StationeryReportRequest(null, null, null, null, null));
    };
    ReportStationeryEmployeeComponent.prototype.acceptRequest = function (rowData) {
        var _this = this;
        if (rowData) {
            this.dialogConfirmRequest = true;
            this.infoToSearchIssuesStationeryItems.requestDate = rowData.requestDate;
            this.infoToSearchIssuesStationeryItems.managerUserName = this.uf.userName;
            this.infoToSearchIssuesStationeryItems.status = rowData.status;
            this.selectedStationeryItem = rowData;
            this.stationeryService.findIssuesStationeryItems(this.infoToSearchIssuesStationeryItems).subscribe(function (y) {
                _this.listStationeryFullUnit = y.data;
            });
        }
        else {
            if (this.selectedItem.length == 1) {
                this.dialogConfirmRequest = true;
                this.infoToSearchIssuesStationeryItems.requestDate = this.selectedItem[0].requestDate;
                this.infoToSearchIssuesStationeryItems.managerUserName = this.uf.userName;
                this.infoToSearchIssuesStationeryItems.status = this.selectedItem[0].status;
                this.selectedStationeryItem = this.selectedItem[0];
                this.stationeryService.findIssuesStationeryItems(this.infoToSearchIssuesStationeryItems).subscribe(function (y) {
                    _this.listStationeryFullUnit = y.data;
                });
            }
            else {
                // this.app.confirmMessage()
            }
        }
    };
    ReportStationeryEmployeeComponent.prototype.onValidate = function (place) {
        console.log("a");
        if (!this.placeStationery || !this.placeStationery.placeId) {
            validate_1.ValidateUtils.validateAutoComplete(place, 'Vị trí không được để trống', this.app);
            return false;
        }
        var count = 0;
        var isQuantity = 0;
        this.lstChefInfor.forEach(function (item) {
            if (item.stationeryId) {
                count++;
                if (!item.quantity) {
                    isQuantity++;
                }
            }
        });
        if (isQuantity != 0) {
            this.app.showWarn('Số lượng không được để trống');
            return false;
        }
        if (count == 0) {
            this.app.showWarn('Văn phòng phẩm không được để trống');
            return false;
        }
        if (this.note != null && this.note.length > 255) {
            this.app.showWarn('Ghi chú không được nhập quá 255 ký tự');
            return false;
        }
        return true;
    };
    ReportStationeryEmployeeComponent.prototype.handleRequest = function (item) {
        this.dialogDetailsRequest = true;
    };
    ReportStationeryEmployeeComponent.prototype.confirmRequest = function (rowData) {
        var _this = this;
        this.infoToSearchIssuesStationeryItems.requestDate = rowData.requestDate;
        this.infoToSearchIssuesStationeryItems.managerUserName = this.uf.userName;
        this.infoToSearchIssuesStationeryItems.status = rowData.status;
        this.selectedStationeryItem = rowData;
        this.stationeryService.findIssuesStationeryItems(this.infoToSearchIssuesStationeryItems).subscribe(function (y) {
            _this.listStationeryFullUnit = y.data;
        });
        this.dialogAcceptRequest = true;
    };
    ReportStationeryEmployeeComponent.prototype.doVoteHCDV = function (rowData) {
        var _this = this;
        this.infoToSearchIssuesStationeryItems.requestDate = rowData.requestDate;
        this.infoToSearchIssuesStationeryItems.managerUserName = this.uf.userName;
        this.infoToSearchIssuesStationeryItems.status = rowData.status;
        this.selectedStationeryItem = rowData;
        this.stationeryService.findIssuesStationeryItems(this.infoToSearchIssuesStationeryItems).subscribe(function (y) {
            _this.listStationeryFullUnit = y.data;
        });
        this.dialogVoteHCDVStationery = true;
    };
    ReportStationeryEmployeeComponent.prototype.doEdit = function (event) {
        var _this = this;
        this.dialogEditRequestEmployee = true;
        this.isCancel = false;
        this.unitEmployee = event.unitName;
        this.nameEmployee = event.fullName;
        this.phoneEmployee = event.phoneNumber;
        this.emailEmployee = event.email;
        this.placeEmployee = event.startPlace;
        this.massage = event.note;
        var temp = new Place_1.Place(event.startPlace, null);
        this.stationeryService.findPlace(temp).subscribe(function (data) {
            _this.listFilterPlace = data.data;
            _this.listFilterPlace.forEach(function (el) {
                if (el.placeId == event.placeId) {
                    _this.placeStationery = el;
                }
            });
        });
        if (event.issuesStationeryItemId) {
            this.searchDataById.issuesStationeryApprovedId = event.issuesStationeryId;
            this.stationeryService.getIssuesStationeryItemsById(this.searchDataById).subscribe(function (res) {
                if (res) {
                    _this.stationeryNameListById = res.data;
                    _this.lstChefInfor = [];
                    _this.stationeryNameListById.forEach(function (el) {
                        _this.lstChefInfor.push(new ReportStationery_1.StationeryReportRequest(el.stationeryId, el.quantity, el.calUnit, el.unitPrice, el.stationeryName));
                    });
                    _this.editIssuesStationey.issuesStationeryIdOld = event.issuesStationeryId;
                    _this.editIssuesStationey.placeID = event.placeId;
                }
            });
        }
    };
    ReportStationeryEmployeeComponent.prototype.editEmployee = function () {
        var _this = this;
        var isValid = this.onValidate(this.placeCopyFld);
        if (isValid) {
            this.editIssuesStationey.placeID = this.placeStationery.placeId ? this.placeStationery.placeId : this.editIssuesStationey.placeID;
            this.editIssuesStationey.note = this.massage ? this.massage : ' ';
            this.editIssuesStationey.listStationery = this.lstChefInfor;
            this.stationeryService.editIssuesStationery(this.editIssuesStationey).subscribe(function (y) {
                if (y.status == 1) {
                    _this.app.showSuccess('Đã cập nhật yêu cầu văn phòng phẩm');
                    _this.onClosedialog();
                    _this.handleSearchEmployee();
                }
            });
        }
    };
    ReportStationeryEmployeeComponent.prototype.doCancel = function (item) {
        var _this = this;
        this.isCancel = true;
        this.dialogEditRequestEmployee = true;
        this.unitEmployee = item.unitName;
        this.nameEmployee = item.fullName;
        this.phoneEmployee = item.phoneNumber;
        this.emailEmployee = item.email;
        this.placeEmployee = item.startPlace;
        this.massage = item.note;
        var temp = new Place_1.Place(item.startPlace, null);
        this.stationeryService.findPlace(temp).subscribe(function (data) {
            _this.listFilterPlace = data.data;
            _this.listFilterPlace.forEach(function (el) {
                if (el.placeId == item.placeId) {
                    _this.placeStationery = el;
                }
            });
        });
        if (item.issuesStationeryItemId) {
            this.searchDataById.issuesStationeryApprovedId = item.issuesStationeryId;
            this.stationeryService.getIssuesStationeryItemsById(this.searchDataById).subscribe(function (res) {
                if (res) {
                    _this.stationeryNameListById = res.data;
                    _this.lstChefInfor = [];
                    _this.stationeryNameListById.forEach(function (el) {
                        _this.lstChefInfor.push(new ReportStationery_1.StationeryReportRequest(el.stationeryId, el.quantity, el.calUnit, el.unitPrice, el.stationeryName));
                        _this.canceIssuesStationey.listIssueStationeryItemId = _this.lstChefInfor;
                    });
                }
            });
        }
        this.canceIssuesStationey.issuesStationeryId = item.issuesStationeryId;
    };
    ReportStationeryEmployeeComponent.prototype.cancelEmployee = function () {
        var _this = this;
        this.stationeryService.updateCancelIssuesStationery(this.canceIssuesStationey).subscribe(function (y) {
            if (y.status == 1) {
                _this.app.showSuccess('Đã hủy yêu cầu văn phòng phẩm');
                _this.onClosedialog();
                _this.handleSearchEmployee();
            }
        });
    };
    ReportStationeryEmployeeComponent.prototype.convertDate = function (date) {
        return new common_1.DatePipe("en-US").transform(date, 'dd-MM-yyyy');
    };
    ReportStationeryEmployeeComponent.prototype.importExcel = function () {
        this.dialogImport = true;
    };
    ReportStationeryEmployeeComponent.prototype.execute = function () {
        if (!this.listChooseEmployee || this.listChooseEmployee.length === 0) {
            this.app.showWarn('Đồng chí chưa chọn yêu cầu nào');
            return;
        }
        var component = this;
        var callback = function () {
            component.isDialogBrowseRequest = true;
        };
        this.browseRequestComponent.onModelChange(this.listChooseEmployee, callback);
    };
    ReportStationeryEmployeeComponent.prototype.onHideExcute = function () {
        this.browseRequestComponent.handleCancel();
        this.listChooseEmployee = null;
    };
    ReportStationeryEmployeeComponent.prototype.onCloseResultBrowseRequest = function (event) {
        if (event == 1) {
            this.isDialogBrowseRequest = false;
            this.handleSearchEmployee();
        }
    };
    ReportStationeryEmployeeComponent.prototype.openImportExcel = function () {
        this.showImportForm = true;
    };
    ReportStationeryEmployeeComponent.prototype.uploadExcel = function (event) {
        var _this = this;
        this.notiImportFile = null;
        var formData = new FormData();
        var file = event.files[0];
        formData.append('file', file);
        this.stationeryService.importExcelStationeryReport(formData).subscribe(function (res) {
            var mess = res.headers.get('messageCode');
            if (mess === 'failFormat') {
                _this.notiImportFile = 'Format file import không chính xác. Vui lòng kiểm tra lại.';
            }
            else if (mess === 'fail') {
                _this.notiImportFile = 'Import không thành công. Tải về file kết quả để kiểm tra lại.';
                _this.file = new Blob([res.body], { type: 'application/xlsx' });
            }
            else if (mess === 'success') {
                _this.notiImportFile = 'Import thành công.';
                _this.file = new Blob([res.body], { type: 'application/xlsx' });
                _this.handleSearchEmployee();
            }
            else if (mess === 'limited') {
                _this.notiImportFile = 'Vượt quá hạn mức.';
            }
            else if (mess === 'norow') {
                _this.notiImportFile = 'Không có bản ghi nào thỏa mãn điều kiện.';
            }
            else {
                _this.alertMessage('error', 'Cảnh báo', 'Import thất bại');
            }
        });
    };
    ReportStationeryEmployeeComponent.prototype.alertMessage = function (severity, summary, detail) {
        this.messageService.add({
            severity: severity,
            summary: summary,
            detail: detail
        });
    };
    ReportStationeryEmployeeComponent.prototype.clearExcel = function () {
        this.notiImportFile = null;
        this.showImportForm = false;
    };
    ReportStationeryEmployeeComponent.prototype.exportTemplateExcel = function () {
        this.stationeryService.templateImportExcelStationeryReport({}).subscribe(function (res) {
            var fileName = 'Import_stationery_template.xlsx';
            var file = new Blob([res.body], { type: 'application/xlsx' });
            file_saver_1.saveAs(file, fileName);
        });
    };
    ReportStationeryEmployeeComponent.prototype.exportImportResult = function () {
        var fileName = 'Import_stationery_result.xlsx';
        file_saver_1.saveAs(this.file, fileName);
    };
    ReportStationeryEmployeeComponent.prototype.onClosedialog = function () {
        this.dialogDetailsRequestEmployee = false;
        this.dialogAddStationery = false;
        this.dialogEditRequestEmployee = false;
        this.placeStationery = null;
        this.massage = null;
        this.listChooseEmployee = [];
        this.lstChefInfor = [];
        this.quotaEmployee = null;
        this.note = null;
        this.lstChefInfor.push(new ReportStationery_1.StationeryReportRequest(null, null, null, null, null));
    };
    __decorate([
        core_1.ViewChild('myTable')
    ], ReportStationeryEmployeeComponent.prototype, "myTable", void 0);
    __decorate([
        core_1.ViewChild('myEmployee')
    ], ReportStationeryEmployeeComponent.prototype, "myEmployee", void 0);
    __decorate([
        core_1.ViewChild('myDrop')
    ], ReportStationeryEmployeeComponent.prototype, "myDrop", void 0);
    __decorate([
        core_1.ViewChild('dtUnit')
    ], ReportStationeryEmployeeComponent.prototype, "dtUnit", void 0);
    __decorate([
        core_1.ViewChild('placeAddFld')
    ], ReportStationeryEmployeeComponent.prototype, "placeAddFld", void 0);
    __decorate([
        core_1.ViewChild('placeCopyFld')
    ], ReportStationeryEmployeeComponent.prototype, "placeCopyFld", void 0);
    __decorate([
        core_1.ViewChild('browseRequestComponent')
    ], ReportStationeryEmployeeComponent.prototype, "browseRequestComponent", void 0);
    ReportStationeryEmployeeComponent = __decorate([
        core_1.Component({
            selector: 'app-report-stationery-employee',
            templateUrl: './report-stationery-employee.component.html',
            styleUrls: ['./report-stationery-employee.component.css'],
            animations: [
                animations_1.trigger('animation', [
                    animations_1.state('true', animations_1.style({
                        opacity: 1,
                        transform: 'translateY(0px)'
                    })),
                    animations_1.state('false', animations_1.style({
                        opacity: 0,
                        display: 'none',
                        transform: 'translateY(10px)'
                    })),
                    animations_1.transition('0 => 1', animations_1.animate('190ms')),
                    animations_1.transition('1 => 0', animations_1.animate('190ms'))
                ])
            ]
        })
    ], ReportStationeryEmployeeComponent);
    return ReportStationeryEmployeeComponent;
}());
exports.ReportStationeryEmployeeComponent = ReportStationeryEmployeeComponent;
