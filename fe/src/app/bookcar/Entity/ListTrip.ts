export class ListTrip {
    public carBookingId: string;
    public empName: string;
    public empPhone: string;
    public driverName: string;
    public carType: string;
    public licensePlate: string;
    public teamCarName: string;
    public unitName: string;
    public detailUnit: string;
    public startDate: Date;
    public endDate: Date;
    public numOfPassenger: number;
    public statusTrips: number;
    public rating: number;
    public feedback: string;
    public createUser : string;
    public updateUser : String;
    public isShowEdit?: boolean;
    public isCopy?: boolean;

    constructor() { }
}

export  class  ListCarBooking {
    public  appoverQltt : string;
    public  appoverLddv : string;
    public  appoverCvp: string;
    public  listCar: string[];
    public  isAQltt: boolean;
    public  isAQldv: boolean;
    public  isACvp: boolean;

    constructor() { }
}


export  class  ListTripAll {

    public reasonAssigner?: string;
    public appoverCvp?: String;
    public appoverLddv?: String;
    public appoverQltt?: String;
    public averageRating?: string;
    public carBookingId?: string;
    public carType?: string;
    public createUser?: string;
    public dateEnd?: Date;
    public dateStart?: Date;
    public detailUnit?: string;
    public driveSquadId?: string;
    public driverName?: string;
    public squadName?: string;
    public driverInfo?: string;
    public empInfo?: string;
    public empName?: string;
    public empPhone?: string;
    public feedback?: string;
    public flagCvp?: number;
    public flagLddv?: number;
    public flagQltt?: number;
    public fullName?: string;
    public isACvp?: boolean;
    public isAQldv?: boolean;
    public isAQltt?: boolean;
    public journey?: string;
    public licensePlate?: string;
    public listOfPassenger?: string;
    public loginRole?: string;
    public loginUserName?: string;
    public numOfPassenger?: number;
    public numOfRows?: number;
    public placeName?: string;
    public rating?: number;
    public reason?: string;
    public reasonRefuse?: string;
    public route?: string;
    public routeType?: string;
    public rowSize?: number;
    public seat?: string;
    public seatName?: string;
    public selectedCarStatus?: string;
    public startPlace?: number;
    public startRow?: number;
    public statusTrips?: number;
    public tagetPlace?: string;
    public timeReadyEnd?: Date;
    public timeReadyStart?: Date;
    public type?: string;
    public unitIdManager?: string;
    public updateUser?: String;
    public userNameBooker?: string;
    public userNameDriver?: string;

    public noVote?: boolean;
    public driverRating? : number;
    public driverFeedback? : string;
    public carId? : string;
    public typeId? : string;
    public seatId? : string;
    public placeId? : number;
    public userName? :string;
    public squadId? : string;

    public isShowEdit: boolean;
    public isCopy: boolean;
    public isUserVote: boolean;
    public isDriverVote: boolean;
    public isReject: boolean;
    public isCancel: boolean;
    public isAddMoreTime: boolean;
    public isSetup: boolean;
    public isJourney: boolean;
    public isApprove: boolean;

    public statusLabel: string;
    public isLate: boolean;

    constructor() { }
}

    export class Ratting {
        bookCarId : string ;
        numberOfStar : number ;
        note : string ;
        userName: string ;
        updateDate: string
    constructor() { }
}
