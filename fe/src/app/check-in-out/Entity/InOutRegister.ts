import {SecurityAccount} from "../../shared/SecurityAccount";
export class InOutRegister extends SecurityAccount {
    /** id's going out */
    inOutRegisterId: string;
    userName: string;
    /** Registration status of employee */
    status : number;
    /** Day which employees go out as planned */
    startTimeByPlan : any;
    /** Day which employees come back as planned */
    endTimeByPlan : any;
    /** Day which employees go out actually */
    startDateReal : any;
    /** Day which employees come back actually */
    endDateReal : any;
    /** Destination */
    destination : string;
    /** Name of user who approved this record */
    approverUserName : string;
    /** in out short reason go out */
    reasonRegistion : string;
    /** in out resonDetail */
    reasonDetail : string;
    //danh sach employee di cung
    inOutEmployeeList: string;
}