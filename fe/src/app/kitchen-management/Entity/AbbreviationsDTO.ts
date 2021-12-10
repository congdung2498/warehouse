export class AbbreviationsDTO   {
    public    kitchenId : string;
    public   kitchenName : string;
    public   unitId : number;
    public   unitName : string;
    public  shortName : string;
    public   note : string;
    public  path : string;
    public createUser : String;

    public isShowEdit?: boolean;
    public isShowAdmin? : boolean;
    constructor() { }
}
