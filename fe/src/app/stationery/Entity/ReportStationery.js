"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var ReportStationery = /** @class */ (function () {
    function ReportStationery(rpStationeryId, unitId, placeId, unitName, userName, fullName, employeePhone, status, note, ratting, feedback, lastUser, listUnitId, listStatus, startDate, endDate, placeName, threeLevelUnit, message, acceptUserName, acceptFullName, pageNumber, pageSize, totalMoney, requestDate, totalFulFill, totalRequest, rating, unitPrice, stationeryName, stationeryId, issuesStationeryId, issuesStationeryItemId, ratingToUser, feedbackToHcdv, feedbackToVptct, ratingToVptct) {
        this.rpStationeryId = rpStationeryId;
        this.unitId = unitId;
        this.placeId = placeId;
        this.unitName = unitName;
        this.userName = userName;
        this.fullName = fullName;
        this.employeePhone = employeePhone;
        this.status = status;
        this.note = note;
        this.ratting = ratting;
        this.feedback = feedback;
        this.lastUser = lastUser;
        this.listUnitId = listUnitId;
        this.listStatus = listStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.placeName = placeName;
        this.threeLevelUnit = threeLevelUnit;
        this.message = message;
        this.acceptUserName = acceptUserName;
        this.acceptFullName = acceptFullName;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalMoney = totalMoney;
        this.requestDate = requestDate;
        this.totalFulFill = totalFulFill;
        this.totalRequest = totalRequest;
        this.rating = rating;
        this.unitPrice = unitPrice;
        this.stationeryName = stationeryName;
        this.stationeryId = stationeryId;
        this.issuesStationeryId = issuesStationeryId;
        this.issuesStationeryItemId = issuesStationeryItemId;
        this.ratingToUser = ratingToUser;
        this.feedbackToHcdv = feedbackToHcdv;
        this.feedbackToVptct = feedbackToVptct;
        this.ratingToVptct = ratingToVptct;
    }
    return ReportStationery;
}());
exports.ReportStationery = ReportStationery;
var InfoToSearchIssuesStationeryItems = /** @class */ (function () {
    function InfoToSearchIssuesStationeryItems(employeeUserName, managerUserName, requestDate, pageNumber, pageSize, status, roleAdmin) {
        this.employeeUserName = employeeUserName;
        this.managerUserName = managerUserName;
        this.requestDate = requestDate;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.status = status;
        this.roleAdmin = roleAdmin;
    }
    return InfoToSearchIssuesStationeryItems;
}());
exports.InfoToSearchIssuesStationeryItems = InfoToSearchIssuesStationeryItems;
var DataResponse = /** @class */ (function () {
    function DataResponse(requestID, placeName, unitName, fullName, userName, dateRequest, stationeryName, quantity, price, totalMoney, status) {
        this.requestID = requestID;
        this.placeName = placeName;
        this.unitName = unitName;
        this.fullName = fullName;
        this.userName = userName;
        this.dateRequest = dateRequest;
        this.stationeryName = stationeryName;
        this.quantity = quantity;
        this.price = price;
        this.totalMoney = totalMoney;
        this.status = status;
    }
    return DataResponse;
}());
exports.DataResponse = DataResponse;
var StationeryParam = /** @class */ (function () {
    function StationeryParam(stationeryId, quantity) {
        this.stationeryId = stationeryId;
        this.quantity = quantity;
    }
    return StationeryParam;
}());
exports.StationeryParam = StationeryParam;
var StationeryInfo = /** @class */ (function () {
    function StationeryInfo(stationeryId, stationeryName, calculationUnit, unitPrice) {
        this.stationeryId = stationeryId;
        this.stationeryName = stationeryName;
        this.calculationUnit = calculationUnit;
        this.unitPrice = unitPrice;
    }
    return StationeryInfo;
}());
exports.StationeryInfo = StationeryInfo;
var IssuesStationeryItemsToInsert = /** @class */ (function () {
    function IssuesStationeryItemsToInsert(listStationery, issuesStationeryItemID, issuesStationeryID, stationeryID, issuesStationeryApprovedID, issuesStationeryRegistryID, status, totalRequest, totalFulFill) {
        this.listStationery = listStationery;
        this.issuesStationeryItemID = issuesStationeryItemID;
        this.issuesStationeryID = issuesStationeryID;
        this.stationeryID = stationeryID;
        this.issuesStationeryApprovedID = issuesStationeryApprovedID;
        this.issuesStationeryRegistryID = issuesStationeryRegistryID;
        this.status = status;
        this.totalRequest = totalRequest;
        this.totalFulFill = totalFulFill;
    }
    return IssuesStationeryItemsToInsert;
}());
exports.IssuesStationeryItemsToInsert = IssuesStationeryItemsToInsert;
var StationeryReportRequest = /** @class */ (function () {
    function StationeryReportRequest(stationeryId, quantity, calUnit, unitPrice, stationeryName) {
        this.stationeryId = stationeryId;
        this.quantity = quantity;
        this.calUnit = calUnit;
        this.unitPrice = unitPrice;
        this.stationeryName = stationeryName;
    }
    return StationeryReportRequest;
}());
exports.StationeryReportRequest = StationeryReportRequest;
var RequestParam = /** @class */ (function () {
    function RequestParam(placeID, listStationery, note) {
        this.placeID = placeID;
        this.listStationery = listStationery;
        this.note = note;
    }
    return RequestParam;
}());
exports.RequestParam = RequestParam;
var PlaceStationery = /** @class */ (function () {
    function PlaceStationery(lstPlaceId, placeId, placeName) {
        this.lstPlaceId = lstPlaceId;
        this.placeId = placeId;
        this.placeName = placeName;
    }
    return PlaceStationery;
}());
exports.PlaceStationery = PlaceStationery;
var StationeryRequest = /** @class */ (function () {
    function StationeryRequest(stationeryId, quantity) {
        this.stationeryId = stationeryId;
        this.quantity = quantity;
    }
    return StationeryRequest;
}());
exports.StationeryRequest = StationeryRequest;
var StationeryEmployee = /** @class */ (function () {
    function StationeryEmployee() {
    }
    return StationeryEmployee;
}());
exports.StationeryEmployee = StationeryEmployee;
var SearchDataStationery = /** @class */ (function () {
    function SearchDataStationery() {
    }
    return SearchDataStationery;
}());
exports.SearchDataStationery = SearchDataStationery;
var IssuesStationeryItems = /** @class */ (function () {
    function IssuesStationeryItems() {
    }
    return IssuesStationeryItems;
}());
exports.IssuesStationeryItems = IssuesStationeryItems;
var SearchDataById = /** @class */ (function () {
    function SearchDataById() {
    }
    return SearchDataById;
}());
exports.SearchDataById = SearchDataById;
var EditIssuesStationey = /** @class */ (function () {
    function EditIssuesStationey() {
    }
    return EditIssuesStationey;
}());
exports.EditIssuesStationey = EditIssuesStationey;
var SearchDataBookId = /** @class */ (function () {
    function SearchDataBookId() {
    }
    return SearchDataBookId;
}());
exports.SearchDataBookId = SearchDataBookId;
var UpdateIssuesStationery = /** @class */ (function () {
    function UpdateIssuesStationery() {
    }
    return UpdateIssuesStationery;
}());
exports.UpdateIssuesStationery = UpdateIssuesStationery;
var DataResponseUnit = /** @class */ (function () {
    function DataResponseUnit() {
    }
    return DataResponseUnit;
}());
exports.DataResponseUnit = DataResponseUnit;
var DataResponseDetails = /** @class */ (function () {
    function DataResponseDetails() {
    }
    return DataResponseDetails;
}());
exports.DataResponseDetails = DataResponseDetails;
var RequestParamUnit = /** @class */ (function () {
    function RequestParamUnit() {
    }
    return RequestParamUnit;
}());
exports.RequestParamUnit = RequestParamUnit;
var RequestParamUnit2 = /** @class */ (function () {
    function RequestParamUnit2() {
    }
    return RequestParamUnit2;
}());
exports.RequestParamUnit2 = RequestParamUnit2;
var ProcessIssuesStationery = /** @class */ (function () {
    function ProcessIssuesStationery() {
    }
    return ProcessIssuesStationery;
}());
exports.ProcessIssuesStationery = ProcessIssuesStationery;
var InfoEmployee = /** @class */ (function () {
    function InfoEmployee(placeId, fullName, unitName, phoneNumber, email, status, listData) {
        this.placeId = placeId;
        this.fullName = fullName;
        this.unitName = unitName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = status;
        this.listData = listData;
    }
    return InfoEmployee;
}());
exports.InfoEmployee = InfoEmployee;
var DetailsEmployeeInfo = /** @class */ (function () {
    function DetailsEmployeeInfo() {
    }
    return DetailsEmployeeInfo;
}());
exports.DetailsEmployeeInfo = DetailsEmployeeInfo;
var PlaceEmployee = /** @class */ (function () {
    function PlaceEmployee(placeId, placeName, lstPlaceId, listData) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.lstPlaceId = lstPlaceId;
        this.listData = listData;
    }
    return PlaceEmployee;
}());
exports.PlaceEmployee = PlaceEmployee;
var PlaceEmployeeParam = /** @class */ (function () {
    function PlaceEmployeeParam(placeId) {
        this.placeId = placeId;
    }
    return PlaceEmployeeParam;
}());
exports.PlaceEmployeeParam = PlaceEmployeeParam;
var CanceIssuesStationey = /** @class */ (function () {
    function CanceIssuesStationey() {
    }
    return CanceIssuesStationey;
}());
exports.CanceIssuesStationey = CanceIssuesStationey;
var VoteHCDV = /** @class */ (function () {
    function VoteHCDV() {
    }
    return VoteHCDV;
}());
exports.VoteHCDV = VoteHCDV;
var DataResponseQuota = /** @class */ (function () {
    function DataResponseQuota() {
    }
    return DataResponseQuota;
}());
exports.DataResponseQuota = DataResponseQuota;
var ParamToInsertEmployee = /** @class */ (function () {
    function ParamToInsertEmployee() {
    }
    return ParamToInsertEmployee;
}());
exports.ParamToInsertEmployee = ParamToInsertEmployee;
var CompleteStationey = /** @class */ (function () {
    function CompleteStationey() {
    }
    return CompleteStationey;
}());
exports.CompleteStationey = CompleteStationey;
