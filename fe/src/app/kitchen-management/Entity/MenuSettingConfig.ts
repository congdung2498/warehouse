
import {SecurityAccount} from "../../shared/SecurityAccount";
export class MenuSettingConfig extends SecurityAccount{

  public chefID: string;
  public dateFrom: Date;
  public dateTo: Date;
  public pageNumber: number;
  public pageSize: number;
  public kitchenID: string;
}
