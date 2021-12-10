export class Employee {
  constructor(
    public employeeId?: string,
    public userName?: string,
    public employeeCode?: string,
    public fullName?: string,
    public employeePhone?: string,
    public employeeEmail?: string,
    public unitId?: number,
    public unitName?: string,
    public title?: string, //chuc danh
    public levelEmployee?: string,
    public role?: string, // muc duyet
    public placeId?: number, // dia diem lam viec
    public comment?: string,
    public status?: number,
    public securityUsername?: string,
    public securityPassword?: string
  ) {}
}
