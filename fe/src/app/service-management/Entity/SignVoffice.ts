export class SignVoffice {
    public signVofficeId: number;
    public documentCode: string;
    public content: string;
    public requestUserName: string;
    public signUserName: string;
    public signTime: Date;
    public status: number;
    public createTime: Date;
    public approveTime: Date;
    public type: number;
    public note: string;
    public file: string;

    constructor() { }
}
export class SignVofficeSerch {
    public fromDate: Date;
    public toDate: Date;
    public documentCode: string;
     public signUserName: string;
    public status: number;
    public type: number;
    public startRow: number;
    public rowSize: number;

    constructor() { }
}
