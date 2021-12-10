
export class RequestStationeryFrom {

    public placeID: number;

    public listStationery : RequestStationerySearch[];

    public note : string;

    constructor() {}
}

export class RequestStationerySearch {

    public stationeryID : String ;
    public quantity : number ;
    public unitPrice : number;

    constructor() {}
}



