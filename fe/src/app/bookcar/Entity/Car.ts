import { Employee } from "../../Entity/Employee";

export class Car{
    constructor(
        public car_id : String,
        public squad_id : String,
        public squad_name : String,
        public license_plate: String,
        public type : String,
        public seat: number,
        public status : number,
        public process_status : number
    ){}
}

export class Seat{
    constructor(
        public seat: number
    ){}
}

export class Type{
    constructor(
        public type : String
    ){}
}