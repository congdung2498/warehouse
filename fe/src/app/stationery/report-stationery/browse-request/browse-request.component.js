"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var BrowseRequestComponent = /** @class */ (function () {
    function BrowseRequestComponent(stationeryService, kitchenService, confirmationService, messageService, renderer, tokenStorage, title, app) {
        this.stationeryService = stationeryService;
        this.kitchenService = kitchenService;
        this.confirmationService = confirmationService;
        this.messageService = messageService;
        this.renderer = renderer;
        this.tokenStorage = tokenStorage;
        this.title = title;
        this.app = app;
        this.result = new core_1.EventEmitter();
        this.entityRqFindEmp = { pattern: '', fromIndex: 1, getSize: 20 };
        this.listSearchDetails = [];
        this.paramToInsertEmployee = {
            message: null,
            employeeUserName: null,
            listRequestID: [],
            approveUserName: null
        };
    }
    BrowseRequestComponent.prototype.ngOnInit = function () {
    };
    BrowseRequestComponent.prototype.onModelChange = function (listChooseEmployee, callback) {
        var _this = this;
        var total = 0;
        var stationeryIds = [];
        for (var i = 0; i < listChooseEmployee.length; i++) {
            stationeryIds.push(listChooseEmployee[i].issuesStationeryId);
        }
        console.log(stationeryIds);
        this.stationeryService.getItemsByStationeryIds(stationeryIds).subscribe(function (res) {
            if (res) {
                _this.listSearchDetails = res.data;
                for (var i = 0; i < _this.listSearchDetails.length; i++) {
                    total = total + _this.listSearchDetails[i].totalMoneyRequest;
                }
                _this.fullNameDetails = total;
                callback();
            }
        });
    };
    BrowseRequestComponent.prototype.doCancel = function (rowData) {
        if (this.listSearchDetails) {
            for (var i = 0; i < this.listSearchDetails.length; i++) {
                if (rowData.issuesStationeryItemId === this.listSearchDetails[i].issuesStationeryItemId) {
                    this.fullNameDetails = this.fullNameDetails - this.listSearchDetails[i].totalMoneyRequest;
                    this.listSearchDetails.splice(i, 1);
                    break;
                }
            }
        }
    };
    BrowseRequestComponent.prototype.handleUpdateRequest = function () {
        var _this = this;
        if (!this.qlttName || !this.qlttName.userName) {
            this.app.showWarn('??/c ch??a ch???n l??nh ?????o k?? duy???t');
            return;
        }
        if (!this.listSearchDetails || this.listSearchDetails.length < 1) {
            this.app.showWarn('Kh??ng c?? y??u c???u n??o ????? tr??nh k??');
            return;
        }
        if (!this.note || this.note.length < 1) {
            this.app.showWarn('N???i dung kh??ng ???????c ????? tr???ng');
            return;
        }
        this.paramToInsertEmployee.listRequestID = [];
        for (var i = 0; i < this.listSearchDetails.length; i++) {
            this.paramToInsertEmployee.listRequestID.push(this.listSearchDetails[i].issuesStationeryItemId);
        }
        this.paramToInsertEmployee.message = this.note;
        this.paramToInsertEmployee.approveUserName = this.qlttName.userName;
        this.stationeryService.updateIssuesStationeryItems(this.paramToInsertEmployee).subscribe(function (res) {
            if (res.status === 1) {
                _this.app.showSuccess('Tr??nh k?? th??nh c??ng');
                _this.paramToInsertEmployee.listRequestID = [];
                _this.result.emit(1);
            }
            else if (res.status === 17) {
                _this.app.showWarn('???? qu?? th???i h???n ????ng k?? v??n ph??ng ph???m c???a th??ng n??y');
            }
            else if (res.status === 18) {
                _this.app.showWarn('??/c c???n x??c nh???n y??u c???u (' + res.data + ') ???? ho??n th??nh tr?????c khi t???o y??u c???u m???i');
            }
            else if (res.status === 19) {
                _this.app.showWarn('Tr??nh k?? c?? nhi???u h??n m???t v??? tr??');
            }
            else if (res.status === 20) {
                _this.app.showWarn('Tr??nh k?? c?? nhi???u h??n m???t ????n v???');
            }
            else if (res.status === 15) {
                _this.app.showWarn('V?????t qu?? h???n m???c');
            }
            else {
                _this.app.showWarn('Tr??nh k?? th???t b???i');
            }
        });
    };
    BrowseRequestComponent.prototype.handleCancel = function () {
        this.paramToInsertEmployee.listRequestID = [];
        this.qlttName = null;
        this.note = null;
    };
    BrowseRequestComponent.prototype.getManager = function (ev) {
        var _this = this;
        this.entityRqFindEmp.pattern = ev.query;
        this.entityRqFindEmp.role = ['PMQT_QL'];
        this.stationeryService.getListManager(this.entityRqFindEmp).subscribe(function (res) {
            _this.qlttNameList = res.data;
        });
    };
    __decorate([
        core_1.Output()
    ], BrowseRequestComponent.prototype, "result", void 0);
    BrowseRequestComponent = __decorate([
        core_1.Component({
            selector: 'app-browse-request',
            templateUrl: './browse-request.component.html',
            styleUrls: ['./browse-request.component.css']
        })
    ], BrowseRequestComponent);
    return BrowseRequestComponent;
}());
exports.BrowseRequestComponent = BrowseRequestComponent;
