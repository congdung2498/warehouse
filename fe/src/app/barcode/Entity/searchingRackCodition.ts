import {SecurityAccount} from "../../shared/SecurityAccount";

export class SearchingRackCondition extends  SecurityAccount{
  public warehouseId: number;
  public type: number;
  public row: number;
  public column:number;
  // public pageSize;
  // public pageNumber;
}
