import {DriveCar} from "./DriveCar";
import {SecurityAccount} from "../../shared/SecurityAccount";
export class Drive extends SecurityAccount {

        public driveId : string;
        public squadId : string;
        public squadName : string;
        public status: number;
        public processStatus : number;
        public fullName : string;
        public userName : string;
        public employeePhone : string;
        public employeeId : string;
        public oldValue : string ;
        public selectedLicensePlate: DriveCar[];
        public startRow: number;
        public rowSize: number;
}