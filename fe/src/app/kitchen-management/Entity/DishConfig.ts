import {SecurityAccount} from "../../shared/SecurityAccount";
export class DishConfig extends  SecurityAccount{
    public dishID: string;
    public dishName: string;
    public kitchenID: string;
    public kitchenName : string;
    public status: number;
    public deleteFG : number;
    public image: string;
    public listDishID: string[];
    public isImage?: number;
}

export class Dish {
    public dishId: string;
    public dishName: string;

    constructor() {}
}