import { Chef } from "../Entity/Chef";

export class KitchenPlace {
    constructor(
      public placeID: number,
      public placeName: string,
      public listChef: Chef[]
    ) {}
  }