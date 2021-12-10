import { Employee } from "../../Entity/Employee";

export class DrivePlace {
  constructor(
    public placeId: number,
    public placeName: string,
    public listEmp: Employee[]
  ) {}
}
