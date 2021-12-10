import { Employee } from "../../Entity/Employee";
import { TeamCar } from "./TeamCar";
import {SecurityAccount} from "../../shared/SecurityAccount";

export class Car {
    constructor(
        public carId : string,
        public squadId : string,
        public squadName : string,
        public licensePlate: string,
        public type : string,
        public seat: string,
        public status : number,
        public processStatus : number,
        public displayOption : string,
        public securityUsername?: string,
        public securityPassword?: string
    ){}

    public toString() : any{
        return this.licensePlate +" / "+this.type+" / "+this.seat;
    }
}


export class Seat{
    constructor(
        public seatId : string,
        public seat: string
    ){}
}

export class Type{
    constructor(
        public typeId : string,
        public type : string
        
    ){}
}