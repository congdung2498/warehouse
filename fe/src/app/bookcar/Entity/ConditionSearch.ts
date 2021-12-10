import {SecurityAccount} from "../../shared/SecurityAccount";
export class Condition extends SecurityAccount{
    public unitIdManager: string;
    public squadId?: string;
    public squadName?: string;
    public userNameDriver: string;
    public driverInfo: string;
    public carType: string;
    public userNameBooker: string;
    public empInfo: string;
    public selectedCarStatus: number[];
    public startDate: Date;
    public endDate: Date;
    public dateStart: Date;
    public dateEnd: Date;
    public numOfRows: number;
    public startRow: number;
    public rowSize: number;
    public driveSquadId?: string;
    public driveSquadName?: string;
}
export class ConditionById {
    public carBookingId : string;
    public driveSquadId?: string;
    public unitIdManager: string;
    public squadId: string;
    public driveSquadName: string;
    public userNameDriver: string;
    public driverInfo: string;
    public carType: string;
    public userNameBooker: string;
    public empInfo: string;
    public selectedCarStatus: number[];
    public startDate: Date;
    public endDate: Date;
    public dateStart: Date;
    public dateEnd: Date;
    public numOfRows: number;
    public startRow: number;
    public rowSize: number;
    constructor() { }
}