import { TeamCar } from "./TeamCar";
export class CarDriver{
    constructor(
        public car_driverId: number,
        public teamName : string,
        public driver_name : string,
        public call : string,
        public status: number
    ){}
}