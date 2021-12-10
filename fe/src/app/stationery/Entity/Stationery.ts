export class Stationery {
    constructor(
        public stationeryId: string,
        public stationeryName: string,
        public unitPrice: number,
        public calUnit: string,
        public calUnitId: string,
        public pageSize : number,
        public pageNumber : number,
        public image: number,
        public securityUsername?: string,
        public securityPassword?: string
    ) { }
}

export class DataResponseQuota {
    public id : string;
    public unitName: string;
    public unitId: number;
    public quota: number;
    public quantity: number;
    public total: number;
    public limitDate : string;

    constructor( ) { }
}

export class DataResponseEdit {
    public  id : string ;
    public  unitName : string ;
    public  unitId  : number;
    public  quota : number ;
    public  quantity : number;
    public  total : number;
    public  path : string;

    constructor( ) { }
}
export class RequestParamQuota {
    public quota: number;
    public quantity : number;
    public dateLimit: number;
    public listStatus: string[];
    public pageNumber: number;
    public pageSize: number;
    constructor( ) { }
}
export class UpdateLimitDateDTO {
    public codeName: number;
    constructor( ) { }
}
export class InsertQuota {
    public unitId: number;
    public quota: number;
    public quantity: number;
    constructor( ) { }
}
export class UpdateAllList {
    public userName : string;
    public updateAllIssuesStationery: string[];
    constructor( ) { }
}

export class  StationeryStatus {
    public label: string;
    public status: number;

    constructor( ) { }
}


