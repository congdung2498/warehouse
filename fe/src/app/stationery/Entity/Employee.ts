export class Employee {
    constructor(
        public employeeId: string,
        public fullName: string,
        public userName: string,
        public employeePhone: string,
        public role: string,
        public place : string,
        public unit : string,
        public selectUserName?: string,
        public securityUsername?: string,
        public securityPassword?: string
    ) { }
}
