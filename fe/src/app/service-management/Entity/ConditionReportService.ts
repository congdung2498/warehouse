export class Condition {
    public placeId: string;
    public placeName: string;
    public listUnitId: string[];
    public listStatus: string[];
    public serviceId: string;
    public serviceName: string;
    public receiverUserName: string;
    public receiverName: string;
    public startDate: Date;
    public endDate: Date;
    public isStationary: boolean;
    public startRow: number;
    public rowSize: number;
    public manager: number;
    public listStatusDetail: string[];
    public listStatusSynthetic: string[];
    constructor() { }
}
