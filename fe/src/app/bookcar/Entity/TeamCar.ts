import { Employee } from "../../Entity/Employee";
import { Location } from "./Location";

export class TeamCar {
  constructor(
    public teamId: number,
    public teamName: string,
    public position: Location,    
    public leader: Employee,
    public status: number
  ) {}
}
