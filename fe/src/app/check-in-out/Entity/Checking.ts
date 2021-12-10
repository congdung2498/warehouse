import { SecurityAccount } from '../../shared/SecurityAccount';

export class Checking extends SecurityAccount {

    public inOutRegisterId?: string;

    public empName?: string;
    public empPhone?: string;
    public empCode?: string;
    public empEmail?: string;
    public empTitle?: string;
    public unitName?: string;
    public detailUnit?: string;

    public employeeUserName?: string;
    public destination?: string;
    public reasonRegistion?: string;
    public reasonDetail?: string;
    public startTimeByPlan?: Date;
    public endTimeByPlan?: Date;
    public startTimeByActual?: Date;
    public endTimeByActual?: Date;
    public approverUserName?: string;
    public approverFullName?: string;
    public logToRollbackStatus?: number;
    public logToRollbackStartTimeByPlan?: Date;
    public logToRollbackEndTimeByPlan?: Date;
    public guardOutUsername?: string;
    public guardInUsername?: string;
    public reasonOfApprover?: string;
    public reasonOfGuard?: string;
    public inOutEmployeeList?: string;
    public isLate?: number;
    public status?: number;

    public isApprove?: boolean;
    public isEdit?: boolean;
    public isMoreTime?: boolean;
    public isCopy?: boolean;
    public isSecuriry?: boolean;
}

export class SystemCode {
    public masterClass?: string;
    public codeValue?: string;
    public codeName?: string;

    constructor() {}
}

export class UpdatingChecking extends SecurityAccount {
    public inOutRegisterId?: string[];
    public reasonOfApprover?: string;
    public status?: number;
    public guardOutUserName?: string;
    public guardInUserName?: string;
    public reasonOfGuard?: string;
    public logToRollBackStatus?: number;
    public isMoreTimeGetInApprove?: number = 0;
}
