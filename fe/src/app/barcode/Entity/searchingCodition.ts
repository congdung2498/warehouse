import {SecurityAccount} from "../../shared/SecurityAccount";

export class SearchingCondition extends  SecurityAccount{
  public description: string;
  public prefix: string;
  public printed: boolean;
  public quantity: number;
  public pageSize;
  public pageNumber;
}
