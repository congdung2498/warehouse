import {SecurityAccount} from "../../shared/SecurityAccount";
export class Place extends SecurityAccount {
    public placeCode: string;
    public placeName: string;
    public placeDescription: string;
    public status: number;
    public startRow: number;
    public rowSize: number;
}
export class ResultConfigName {

    constructor(
        public masterClass?: string,
    public masterCode?: string,
    public masterName?: string,
    public name?: string,
    public nameConfig?: string,
    public noteConfig?: string,
    public pageNumber? : number,
    public pageSize? : number,
    public status? : string,
    public updateDate? : string,
    public updateUser? : string,
    public value?: string,
    public valueConfig? : string,
    public masterClassConfig? : string,
    ) { }
}

export class MasterClass {
    public masterCode: string;
    public masterName: string;
    constructor() { }
}
