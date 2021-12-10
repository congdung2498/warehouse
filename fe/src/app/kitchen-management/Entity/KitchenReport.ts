export class KitchenInfo {
    constructor(
      public kitchenID: string,
      public kitchenName: string,
      public placeID: number,
      public placeName: string,
      public chefName: string,
      public chefUserName: string,
      public chefPhone: string,
      public note: string,
      public price: number,
      public status: boolean
    ) {}
  }
