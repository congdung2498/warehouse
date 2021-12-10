"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Employee = /** @class */ (function () {
    function Employee(employeeId, fullName, userName, employeePhone, role, place, unit) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.userName = userName;
        this.employeePhone = employeePhone;
        this.role = role;
        this.place = place;
        this.unit = unit;
    }
    return Employee;
}());
exports.Employee = Employee;
