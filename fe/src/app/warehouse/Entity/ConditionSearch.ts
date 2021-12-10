import { SecurityAccount } from "../../shared/SecurityAccount";

export class ConditionSearch extends SecurityAccount {
    public warehouse_id: number;
    public warehouse_name: string;
    public type: number;
    public status: number;
    public acreage: number;
    public address: string;
    public row_num: number;
    public column_num: number;
    public height_num: number;
    public pageSize: number = 10;
    public pageNumber: number = 1;
}