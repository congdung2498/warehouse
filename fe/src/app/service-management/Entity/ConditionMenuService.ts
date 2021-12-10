import {SecurityAccount} from "../../shared/SecurityAccount";
export class Condition extends SecurityAccount{
    public placeId: string;
    public placeName: string;
    public serviceId: string;
    public serviceName: string;
    public listReceiverUserName: string[];
    public unitId: string;
    public unitName: string;
    public responseTime: string;
    public statusService: string;
    public requireSign: number;
    public startRow: number;
    public rowSize: number;

}
