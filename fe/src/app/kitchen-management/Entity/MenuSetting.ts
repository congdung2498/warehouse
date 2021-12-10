
import {SecurityAccount} from "../../shared/SecurityAccount";
export class MenuSetting {

  public menuId : string;
  public chefID?: string;
  public dateOfMenu?: Date;
  public kitchenID?: string;
  public listDish?: MenuItem[];

  constructor() { }
}

export class MenuItem {
  public dishID?: string;
  public dishName?: string;
  public image?: string;

  constructor() {}
}

export class MenuSettingToInsert extends SecurityAccount{
  public dateOfMenuOld: string;
  public dateOfMenu: Date;
  public listDish: MenuItem[];
  public chefID: string;
  public kitchenID: string;
  public copyFlag: number;
}

export class MenuSettingToUpdate {
  public menuId : string ;
  public dateOfMenuOld: string;
  public dateOfMenu: string;
  public listDish: MenuItem[];
  public chefID: string;
  public kitchenID: string;
  public copyFlag: number;

  constructor() {}
}