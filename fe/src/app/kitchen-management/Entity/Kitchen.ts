//  ThangBT
export class Kitchen {
  public kitchenID: string;
  public kitchenName: string;
  public placeID: number;
  public placeName: string;
  public chefName: string;
  public note: string;
  public price: number;
  public chefUserName: string;
  public chefPhone: string;
  public isEmployee: string;
  public status: number;
  public listPhoneNumberReceiveSms : any;
  public check : number;
  constructor() { }
}

export class SelectingKitchen {
  public kitchenID: string;
  public kitchenName: string;
  public chefName?: string;
  public status?: number;

  constructor() { }
}

export class SelectingKitchenUnit {
  public unitId : string;
  public kitchenID: string;
  public shortName: string;

  constructor() { }
}

export class ReportKitchen {
  public kitchenID?: string;
  public kitchenName?: string;
}
