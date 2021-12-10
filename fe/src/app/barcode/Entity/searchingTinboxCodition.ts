import {SecurityAccount} from "../../shared/SecurityAccount";

export class SearchingTinboxCondition extends  SecurityAccount{
  public warehouseId: number;
  public type: string;
  public mngUser: string;
  public name:string;
  public index: number;
  // public pageSize;
  // public pageNumber;
}
