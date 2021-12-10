export class CarDetails {
    public carBookingId: string;
    public empName : string;
    public empPhone : string;
    public driverName : string;
    public carType : string;
    public   licensePlate : string;
    public  teamCarName : string;
    public  unitName : string;
    public  detailUnit : string;
    public  startDate : string;
    public  endDate : string;
    public  timeReadyStart : string;
    public  timeReadyEnd : string;
    public   numOfPassenger : number    ;
    public  listOfPassenger : string;
    public  statusTrips : number;
    public route : string;
    public routeType : string;
    public  seat : string;
    public  rating : number;
    public  reasonRefuse : string;
    public  feedback    : string;
    public  appoverQltt : string;
    public  appoverLddv : string;
    public  appoverCvp : string;
    public  flagQltt : number;
    public  flagLddv : number;
    public  flagCvp : number;
    public  createUser : string;
    public  updateUser : String;

    constructor() { }
}

export class Journey {
    public journeyId: string;
    public journey : string;

    constructor() { }
}

export class Seat {
    public seatId: string;
    public seat : string;

    constructor() { }
}

export class Type {
    public typeId: string;
    public type : string;

    constructor() { }
}

export class SearchData {
    public squadId: string;
    public searchDTO : string;

    constructor() { }
}

export class CancelRequest {
    public bookCarId: string;
    public userName : string;

    constructor() { }
}

export class DriveCarInfo {
    public  bookCarId? : string;
    public  squadId : string;
    public  userName : string;
    public  userAssigner : string;
    public  carId : string;
    public  processStatus : number;
    public  userRequest : string;
    public  reasonRefuse  :string;

    constructor() { }
}

export class UpdateBookCar {
    public  status? : number;
    public  bookCarId? : string;
    public  listId? : string[];
    public  reasonRefuse? :string;
    public  userName? : string;
    public  qltt? : string;
    public  flagQltt? : number;
    public  lddv? : string;
    public  flagLddv? : number;
    public  cvp? : string;
    public  role? : string;
    public  userRequest?: string;

    constructor() { }
}

export class EntityBookCar {
    public  bookCarId? : string;
    public  userName? : string;
    public  reason? : string;
    public  dateStart? : Date;
    public  dateEnd? : Date;
    public  type? : string;
    public  seat? : string;
    public  route? : string;
    public  startPlace? : number;
    public  targetPlace? : string;
    public  routeWay? : string;
    public  totalPartner?: number;
    public  listPartner? : string;
    public  approverDir? : String;
    public  qlttName? : string;
    public  statusDir?: number;
    public  approverLead? : string;
    public  lddvName? : string;
    public  statusLead?: number;
    public  approverPre? : string;
    public  cvpName? : string;
    public  statusPre?: number;
    public  status?: number;
    public  squadId? : string;
    public  driverSquadName? : string;

    constructor() { }
}

export class ResponseCarRoute {
    public  bookCarId: string;
    public  userName: string;
    public  fullName: string;
    public  fullNameRequester: string;
    public  emplPhone: string;
    public  carId: string;
    public  type: string;
    public  seat: string;
    public  route: string;
    public  licensePlate: string;
    public  dateStart: Date;
    public  dateEnd: Date;
    public  totalPartner: number;
    public  startPlace: string;
    public  targetPlace: string;
    public  routeWay: string;
    public  status: number;
    public  listStatus : number[];
    public  processByRole: string;
    public  pageNumber: number;
    public  pageSize: number;
    public  detailUnit : string;
    public  phoneNumber : string;
    public  fullNameUser: string;
    public  squadName: string;

    constructor() { }
}

export class UpdateProcessCarRoute {
    public  userName : string;
    public  userDriver : string;
    public  bookCarId : string;
    public  status : number;
    public  timeReadyStart : Date;
    public  timeAtTarget : Date;
    public  timeReadyFinish : Date;
    public  carId : string;

    constructor() { }
}

export class RequestMatchCar {
    public  bookCarId? : string;
    public  userRequest?: string;
    public  userAssigner?: string;
    public  userName?: string;
    public  squadId?: string;
    public  carId?: string;
    public  fullName?: string;
    public  searchBy?: string;
    public  role?: string;
    public  pageNumber?:number;
    public  pageSize?:number;

    constructor() { }
}

export class ResponseCars {
    public  bookCarId? : string;
    public  carId?: string;
    public  type?: string;
    public  seat?: string;
    public  licensePlate?: string;
    public  userName?: string;
    public  squadId?: string;
    public  userAssigner?: string;
    public  pageNumber?: number;
    public  pageSize?: number;

    constructor() { }
}

export class EntityRqFindEmp {
    public  pattern? : string;
    public  role? : String[];
    public  pageSize? : number;
    public  pageNumber?: number;
    public  getSize?: number;
    public  fromIndex? : number;

    constructor() { }
}