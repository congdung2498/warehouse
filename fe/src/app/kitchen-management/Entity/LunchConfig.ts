
import {SecurityAccount} from "../../shared/SecurityAccount";
export class LunchModel extends SecurityAccount {
    public lunchId?: string;
    public lunchDate?: Date;
    public quantity?: number;
    public price?: number;
    public hasBooking?: number;
    public period?: number;
    public rating?: number;
    public comment?: string;
    public kitchenId?: string;
    public kitchenName?: string;

    public isUpdatable?: boolean;
}

export class SearchingLunchModel extends SecurityAccount {
    public  kitchenId?: string;
    public listPeriodic?: string[];
    public isPeriodic?: number;
    public lunchDate?: Date;
    public hasBooking?: number;
    public quantity?: number;
    public userName?: string;
    public unitId: string;
    public startTime?: Date;
    public endTime?: Date;
    public months?: number[];
    public year?: number;
    public startRow: number;
    public rowSize: number;
    public searchQuantity: boolean;
}

export class KitchenModel {
    public kitchenId: string;
    public kitchenName: string;

    constructor() { }
}

export class ConfigLunch extends SecurityAccount {
    public  kitchenID?: string;
    public listPeriodic?: string[];
    public isPeriodic?: number;
    public lunchDate?: Date;
    public hasBooking?: number;
    public quantity?: number;
    public userName?: string;
    public unitId: string;

    public startTime?: Date;
    public endTime?: Date;

    public months?: number[];
    public year?: number;
}

export class SearchingLunch extends SecurityAccount {
    public month: number;
    public year: number;
    public userName: string;
    public quantity?: number;
    public hasBooking?: number;
    public isPeriodic: number;

    public startRow: number;
    public rowSize: number;
}

export class UpdatingAllLunch extends SecurityAccount{
    public lunchIds: string[];
    public hasBooking: number;
    public quantity: number;
    public loginUsername: string;
}
