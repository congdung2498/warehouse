import {SecurityAccount} from "../../shared/SecurityAccount";

export class SearchingCondition extends  SecurityAccount{
  public squadName: string;
  public placeId: string;
  public userName: string;
  public status: string;
  public pageSize;
  public pageNumber;
}
