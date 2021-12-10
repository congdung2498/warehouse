"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var http_1 = require("@angular/common/http");
var map_1 = require("rxjs/operators/map");
var containts_1 = require("../shared/containts");
var httpOptions = {
    headers: new http_1.HttpHeaders({ "Content-Type": "application/json" })
};
var StationeryService = /** @class */ (function () {
    function StationeryService(http) {
        this.http = http;
    }
    // getSectors(): Observable<any> {
    //   return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030013/03', httpOptions);
    // }
    // DANH MUC VAN PHONG PHAM
    StationeryService.prototype.getItemsByStationeryIds = function (stationeryIds) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationery/get-items-by-stationerys';
        return this.http.post(url, { stationeryIds: stationeryIds }, httpOptions);
    };
    StationeryService.prototype.getStationeryItemImage = function (stationeryItemId) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/get-stationery-item-image';
        return this.http.post(url, stationeryItemId, { responseType: 'blob' });
    };
    StationeryService.prototype.getStationery = function () {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/01', httpOptions);
    };
    StationeryService.prototype.getCalcultionUnit = function () {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/02', httpOptions);
    };
    StationeryService.prototype.findCalcultionUnit = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/03', object, httpOptions);
    };
    StationeryService.prototype.searchStationery = function (object) {
        if (object.stationeryName != null) {
            object.stationeryName = object.stationeryName.trim();
        }
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/04', object, httpOptions);
    };
    StationeryService.prototype.searchCountStationery = function (object) {
        if (object.stationeryName != null) {
            object.stationeryName = object.stationeryName.trim();
        }
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/searchCountStationery', object, httpOptions);
    };
    StationeryService.prototype.insertStationery = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/05', object, httpOptions);
    };
    StationeryService.prototype.updateStationery = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/06', object, httpOptions);
    };
    StationeryService.prototype.deleteStationery = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/07', object, httpOptions);
    };
    StationeryService.prototype.deleteSelectStationery = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/deleteSelectStationery', object, httpOptions);
    };
    StationeryService.prototype.getLimit = function () {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/08', httpOptions);
    };
    StationeryService.prototype.updateLimit = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/09', object, httpOptions);
    };
    // DANH MUC NGUOI TIEP NHAN
    StationeryService.prototype.getReceiver = function () {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/01', httpOptions);
    };
    StationeryService.prototype.getRole = function () {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/02', httpOptions);
    };
    StationeryService.prototype.findEmployees = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/03', object, httpOptions);
    };
    StationeryService.prototype.findUnit = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/04', object, httpOptions);
    };
    StationeryService.prototype.findPlaceByHCDV = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/find-place-by-hcdv', object, httpOptions);
    };
    StationeryService.prototype.findPlace = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/05', object, httpOptions);
    };
    StationeryService.prototype.searchReceiver = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/06', object, httpOptions);
    };
    StationeryService.prototype.insertReceiver = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/07', object, httpOptions);
    };
    StationeryService.prototype.updateReceiver = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/08', object, httpOptions);
    };
    StationeryService.prototype.deleteReceiver = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050002/09', object, httpOptions);
    };
    StationeryService.prototype.getEmployees = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/01', object, httpOptions);
    };
    StationeryService.prototype.getEmployeeTCT = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/getEmployeeTCT', object, httpOptions);
    };
    StationeryService.prototype.getHcdv = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/10', object, httpOptions);
    };
    StationeryService.prototype.getReportStationery = function () {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/02', httpOptions);
    };
    StationeryService.prototype.getFullfillStationery = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/03', object, httpOptions);
    };
    StationeryService.prototype.getLastUser = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/04', object, httpOptions);
    };
    // getSectors(object: any) {
    //   return this.http.post(Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020000/03', object, httpOptions);
    // }
    StationeryService.prototype.searchReportStationery = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/05', object, httpOptions);
    };
    // countReportStationery(object: ReportStationery : Observable<any>) {
    //   const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/08';
    //   return this.http.post(url, object, httpOptions);
    // }
    StationeryService.prototype.countReportStationery = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/09';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.exportExcel = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/06', object, { responseType: 'arraybuffer' });
    };
    StationeryService.prototype.exportExcelEmployee = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/exportExcelEmployee', object, { responseType: 'arraybuffer' });
    };
    StationeryService.prototype.exportExcelVPP = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/07', object, { responseType: 'arraybuffer' });
    };
    StationeryService.prototype.getLoginInfo = function () {
        var _this = this;
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050015/08', {}, httpOptions).pipe(map_1.map(function (res) {
            return {
                treeUnit: _this.dataToTree(res['data']['unit']),
                receiver: res['data']['receiver']
            };
        }));
    };
    StationeryService.prototype.getSectors = function (query) {
        var _this = this;
        if (query === null || query === undefined) {
            query = '';
        }
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020000/03', {
            'query': query
        }, httpOptions).pipe(map_1.map(function (res) {
            return _this.dataToTree(res['data']);
        }));
    };
    StationeryService.prototype.getSectorGreatFather = function (query) {
        var _this = this;
        if (query === null || query === undefined) {
            query = '';
        }
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt02/vt020000/04', {
            'query': query
        }, httpOptions).pipe(map_1.map(function (res) {
            return _this.dataToTree([res['data']]);
        }));
    };
    StationeryService.prototype.dataToTree = function (data) {
        var nodes = [];
        for (var _i = 0, data_1 = data; _i < data_1.length; _i++) {
            var cont = data_1[_i];
            nodes.push(this.dataToTreeNode(cont));
        }
        return nodes;
    };
    StationeryService.prototype.dataToTreeNode = function (cont) {
        return {
            label: cont.unitName,
            data: cont.unitId,
            leaf: cont.isLeaf === null ? true : false
        };
    };
    StationeryService.prototype.insertStationeryForm = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050003/02', object, httpOptions);
    };
    StationeryService.prototype.findData = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/01', object, httpOptions);
    };
    StationeryService.prototype.findDataRequest = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/request-model', object, httpOptions);
    };
    StationeryService.prototype.findToGiveOut = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/01', object, httpOptions);
    };
    StationeryService.prototype.searchPlaceIsRole = function (text) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/searchPlaceIsRole/' + text;
        return this.http.get(url, httpOptions);
    };
    StationeryService.prototype.searchFullNameInRole = function (text) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/searchFullNameInRole/' + text;
        return this.http.get(url, httpOptions);
    };
    StationeryService.prototype.searchUnitName = function (text) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/issuesService/searchUnitName/' + text;
        return this.http.get(url, httpOptions);
    };
    StationeryService.prototype.searchProcessUnit = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050004/01';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.getListManagerDirect = function (text) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt03/vt030003/searchQltt/' + text;
        return this.http.get(url, httpOptions);
    };
    StationeryService.prototype.approveIssuesStationeryItems = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050004/02';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.findInfoRequest = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050010/01', object, httpOptions);
    };
    StationeryService.prototype.findIssuesStationeryItems = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050004/01';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.countIssuesStationeryItems = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050004/03';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.findStationeryByName = function () {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050001/01';
        return this.http.post(url, httpOptions);
    };
    StationeryService.prototype.insertIssuesStationeryItems = function () {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050003/01';
        return this.http.post(url, httpOptions);
    };
    StationeryService.prototype.findAllPlace = function () {
        var obj = {
            placeName: null,
            pageSize: containts_1.Constants.MAX_RECORD_DISPLAY,
            pageNumber: 0
        };
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt00/vt000000/04', obj, httpOptions);
    };
    StationeryService.prototype.findStationeryQuota = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/02';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.countStationeryQuota = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/03';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.getLimitDateEnd = function () {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/04';
        return this.http.post(url, httpOptions);
    };
    StationeryService.prototype.updateLimitDate = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/05';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.insertQuota = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/06';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.updateQuota = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/07';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.deleteQuota = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050012/08';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.insertIssuesStationery = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/insertIssuesStationery';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.updateAllIssuesStationery = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/updateAllIssuesStationery', object, httpOptions);
    };
    StationeryService.prototype.searchStationeryByEmployee = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/searchStationeryByEmployee', object, httpOptions);
    };
    StationeryService.prototype.getDetailsIssuesStationeryItems = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getDetailsIssuesStationeryItems', object, httpOptions);
    };
    StationeryService.prototype.countStationeryByEmployee = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/countStationeryByEmployee', object, httpOptions);
    };
    StationeryService.prototype.checkLimitStationeryByEmployee = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/checkLimitStationeryByEmployee', object, httpOptions);
    };
    StationeryService.prototype.findSpentQuotaByUser = function (employeeUsername) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/find-spent-quota-by-user';
        return this.http.post(url, { username: employeeUsername }, httpOptions);
    };
    StationeryService.prototype.getIssuesStationeryItemsById = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getIssuesStationeryItemsById';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.updateCancelIssuesStationery = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/updateCancelIssuesStationery';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.importExcelStationeryReport = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationery/uploadStationeryEmployee';
        return this.http.post(url, object, { observe: 'response', responseType: 'blob' });
    };
    StationeryService.prototype.templateImportExcelStationeryReport = function (object) {
        var url = 
        // Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/downloadTemplate';
        containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/downloadTemplateImport';
        return this.http.post(url, object, { observe: 'response', responseType: 'blob' });
    };
    StationeryService.prototype.downloadTemplateImportStationery = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/downloadTemplateImportStationery';
        return this.http.post(url, object, { observe: 'response', responseType: 'blob' });
    };
    StationeryService.prototype.editIssuesStationery = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/editIssuesStationery';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.updateRefuseIssuesStationery = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/updateRefuseIssuesStationery';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.updateApproveIssuesStationery = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/updateApproveIssuesStationery';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.findDataToApprove = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050006/01';
        return this.http.post(url, object, httpOptions);
    };
    // xac nhan tiep nhan
    StationeryService.prototype.confirmReceiptIssuesStationery = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/confirmReceiptIssuesStationery';
        return this.http.post(url, object, httpOptions);
    };
    // hoan thanh thuc hien
    StationeryService.prototype.postponedImplementationIssuesItems = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/postponedImplementationIssuesItems';
        return this.http.post(url, object, httpOptions);
    };
    // khong thuc hien duoc
    StationeryService.prototype.notPossibleApprove = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/notPossibleApprove';
        return this.http.post(url, object, httpOptions);
    };
    // hoan thanh
    StationeryService.prototype.isCompleteIssusStationery = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/isCompleteIssusStationery';
        return this.http.post(url, object, httpOptions);
    };
    // xac nhan cap nhat
    StationeryService.prototype.confirmationPendingApprove = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/confirmationPendingApprove';
        return this.http.post(url, object, httpOptions);
    };
    // tu choi cap nhat
    StationeryService.prototype.refuseConfirmationPendingApprove = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/refuseConfirmationPendingApprove';
        return this.http.post(url, object, httpOptions);
    };
    // get items employee bu id
    StationeryService.prototype.findInfoRequestEmployee = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/findInfoRequestEmployee';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.searchPlaceEmployeeById = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/searchPlaceEmployeeById';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.findInfoRequestHcdv = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/findDataToRatting';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.countInfoRequestHcdvDetails = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/countInfoRequestHcdvDetails';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.requestByUserName = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/requestByUserName', object, httpOptions);
    };
    StationeryService.prototype.voteHcdv = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/voteHcdv';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.voteVptct = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/voteVptct';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.getQuotaLimit = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/getQuotaLimit', object, httpOptions);
    };
    StationeryService.prototype.requestByUnitId = function (object) {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/requestByUnitId', object, httpOptions);
    };
    StationeryService.prototype.findSpendingLimit = function () {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/findSpendingLimit', httpOptions);
    };
    StationeryService.prototype.findSpendingLimitString = function () {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/findSpendingLimitString', httpOptions);
    };
    StationeryService.prototype.updateIssuesStationeryItems = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt05/vt050004/02';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.getListManager = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/vt00/vt000000/02';
        return this.http.post(url, object, httpOptions);
    };
    StationeryService.prototype.findSpendingLimitQuota = function () {
        return this.http.post(containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/findSpendingLimitQuota', httpOptions);
    };
    StationeryService.prototype.importService = function (object) {
        var url = containts_1.Constants.HOME_URL + 'com/viettel/vtnet360/stationeryReport/uploadStationeryManagement';
        return this.http.post(url, object, { observe: 'response', responseType: 'blob' });
    };
    StationeryService = __decorate([
        core_1.Injectable()
    ], StationeryService);
    return StationeryService;
}());
exports.StationeryService = StationeryService;
