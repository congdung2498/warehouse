export class ReportStationery {
    constructor(
        public ldUsername? : string,
        public rpStationeryId?: string,
        public unitId?: number,
        public placeId? : string,
        public unitName?: string,
        public userName?: string,
        public fullName?: string,
        public employeePhone?: string,
        public status?: number,
        public note?: string,
        public ratting?: number,
        public feedback?: string,
        public lastUser?: string,
        public listUnitId?: string[],
        public listStatus?: number[],
        public startDate?: Date,
        public endDate?: Date,
        public placeName?: string,
        public threeLevelUnit? : string,
        public message? : string,
        public acceptUserName? : string,
        public acceptFullName? : string,
        public  pageNumber? : number,
        public  pageSize? : number,
        public totalMoney? : number,
        public requestDate? : Date,
        public totalFulFill? : number,
        public totalRequest? : number,
        public rating?:number,
        public unitPrice? : number,
        public stationeryName? : string,
        public stationeryId? : string,
        public issuesStationeryId? : string,
        public issuesStationeryItemId? : string,
        public ratingToUser? : number,
        public feedbackToHcdv? : string,
        public feedbackToVptct? : string,
        public ratingToVptct? : number,
        public isApprove?: boolean,
        public securityUsername?: string,
        public securityPassword?: string
    ) { }
}
export class InfoToSearchIssuesStationeryItems {
    constructor(
        public employeeUserName: string,
        public managerUserName : String,
        public requestDate: Date,
        public pageNumber: number,
        public pageSize: number,
        public status: number,
        public roleAdmin: boolean,
        public securityUsername?: string,
        public securityPassword?: string
    ) { }
}
export class DataResponse {
    constructor(
        public  requestID : string,
    public  placeName : string,
    public  unitName : string,
    public  fullName : string,
    public  userName : string,
    public  dateRequest : Date,
    public  stationeryName:string,
    public  quantity : number,
    public  price : number,
    public  totalMoney : number,
    public  status : number
    ) { }
}
export class StationeryParam {
    constructor(
        public  stationeryId : string,
        public  quantity : number,
    ) { }
}
export class StationeryInfo {
    constructor(
        public  stationeryId : string,
        public  stationeryName : string,
        public  calculationUnit : string,
        public  unitPrice : number,
    ) { }
}
export class IssuesStationeryItemsToInsert {
    constructor(
        public  listStationery : string[],
        public  issuesStationeryItemID : string,
        public  issuesStationeryID : string,
        public  stationeryID : string,
        public  issuesStationeryApprovedID : string,
        public  issuesStationeryRegistryID : string,
        public  status : number,
        public  totalRequest : number,
        public  totalFulFill : number,
    ) { }
}
export class  StationeryReportRequest {
    constructor(
      public stationeryId : string,
      public quantity : number,
      public calUnit : string,
      public unitPrice:number,
      public stationeryName : string,
      public issuesStationeryItemId: string,
      public issuesStationeryApproveId?: string,
      public isDisable?: boolean,
      public isApproved?: boolean
    ) { }
}
export class RequestParam {
    constructor(
        public  placeID : number,
        public  listStationery : StationeryReportRequest[],
        public note : String
    ) { }
}

export class PlaceStationery {
    constructor(
        public lstPlaceId : string,
        public  placeId : number,
        public  placeName : string
    ) { }
}
export class  StationeryRequest {
    constructor(
        public  stationeryId : string,
        public  quantity : number,
    ) { }
}

export class StationeryEmployee {
    public employeeId: string;
    public employeeUsername: string;
    public totalMoneyRequest: number;
    public issuesStationeryItemId: string;
    public issuesStationeryId: string;
    public stationeryId: string;
    public totalRequest: number;
    public unitPrice: number;
    public status: number;
    public statusUnit: number;
    public statusItem: number;
    public requestDate: Date;
    public fullName: string;
    public startPlace: string;
    public placeId: number;
    public unitName: string;
    public calUnit: string;
    public calUnitId: string;
    public email: string;
    public phoneNumber: string;
    public stationeryName: string;
    public note: string;
    public startPlaceId: number;

    public isEdit: boolean;
    public isCancel: boolean;
    public isApprove: boolean;
    public approvedItem: number;
    constructor() { }
}

export class  SearchDataStationery {
    public issuesStationeryApprovedId : string;
    constructor(
    ) { }
}

export class  IssuesStationeryItems {
    public  issuesStationeryItemId : string ;
    public  issuesStationeryId : string ;
    public  stationeryId : string;
    public  totalRequest : number
    public  totalFulfill : number;
    public  unitPrice : number;
    public  dateFulfill : Date;
    public  issuesStationeryApprovedId : string ;
    public  status :number;
    public  reasonReject : string;
    constructor(
    ) { }
}

export class  SearchDataById {
    public  issuesStationeryApprovedId : string ;
    constructor(
    ) { }
}

export class  EditIssuesStationey {
    public  placeID : string ;
    public  listStationery : StationeryReportRequest[];
    public note : String;
    public issuesStationeryIdOld : string;
    constructor(
    ) { }
}

export class  SearchDataBookId {
    public  searchDTO : String ;
    public  searchPlaceName : String ;
    constructor(
    ) { }
}
export class  UpdateIssuesStationery {
    public issuesStationeryIdOld? :string;
    public  ldReasonReject? : string ;
    public  userName? : string ;
    public  issuesStationeryApprovedId? : string ;
    public  issuesStationeryItemId? : string ;
    public  issueStationeryId? : string ;
    constructor(
    ) { }
}

export class  DataResponseUnit {
    public fullName? : string;
    public totalRequestHcdv? : number;
    public dateRequest? : Date;
    public message? : string;
    public status? : number;
    public totalRequest? : number;
    public totalFulfill? : number;
    public sumTotalMoney? : number;
    public  feedBackToHcdv?: string;
    public feedBackToVptct?: string;
    public  ratingToUser?: number;
    public ratingToVptct?: number;
    public unitName? : string;
    public phoneNumber? : string;
    public totalFulfillHcdv? : number;
    public sumTotalMoneyHcdv? : number;
    public hcdvReasonReject? : string;
    public ldReasonReject? : string;
    public  vptctReasonReject? : string;
    public vptctReason? : string;
    public vptctPostponeToTime? : Date;
    public listData? : DataResponseDetails[];
    constructor(
    ) { }
}
export class  DataResponseRatting {
    public fullName? : string;
    public vptctReason?: string;
    public fullNameHcdv? : string;
    public fullNameTct? : string;
    public dateRequest? : Date;
    public message? : string;
    public status? : number;
    public feedBackToVptct? : string;
    public feedBackToHcdv? : string;
    public ratingToVptct? : number;
    public  ratingToUser?: number;
    public totalRequest?: number;
    public  totalFulfill?: number;
    public sumTotalMoney?: number;
    public totalRequestHcdv? : number;
    public sumTotalMoneyHcdv? : number;
    public hcdvReasonReject? : string;
    public ldReasonReject? : string;
    public vptctReasonReject? : string;
    public vptctPostponeToTime? : Date;
    public  unitName? : string;
    public phoneNumber? : string;
    public listData? : ItemModel[];
    constructor(
    ) { }
}
export class  ItemModel {
    public quantity? : number;
    public stationeryId : string;
    public totalRequest? : Date;
    public fullName? : string;
    public totalMoney? : number;
    public totalFullfill? : number;
    public totalMoneyFullfill? : number;
    public stationeryName? : number;
    public  unitPrice?: string;
    constructor(
    ) { }
}
export class DataResponseDetails  {
    public  requestID? : string; // ISSUES_STATIONERY_ITEM_ID
    public  placeName? : string;
    public  unitName? : string;
    public  fullName? : string;
    public  userName? : string;
    public  dateRequest? : Date;
    public  stationeryName? : string;
    public  quantity? : number;
    public  price?: number;
    public  totalMoney?: number;
    public stationeryId? : string;
    public phoneNumber? : string;
    public totalFulfill? : number;
    public  issuesStationeryApprovedId? : string;
    public  issuesStationeryId?  : string;
    constructor(
    ) { }
}
export class RequestParamUnit  {
    public  requestID? : string ;
    public username?: string;
    public password?: string

    constructor(
    ) { }
}
export class RequestParamUnit2  {
    public  requestID? : string ;
    public  pageNumber : number;
    public pageSize : number;
    constructor(
    ) { }
}
export class ProcessIssuesStationery  {
    public issuesStationeryIdOld? : string;
    public  ldReasonReject? : string;
    public  userName?: string;
    public  issuesStationeryApprovedId?: string;
    public  listIssuesStationeryItemId?: ItemModel[];
    public  listIssueStationeryId?: string[];
    public  vptctReason? : string;
    public  vptctPostponeToTime?: Date;
    public  vptctReasonReject? : string;
    public  hcdvReasonReject?: string;
    public  totalFulfill? : number;
    constructor(
    ) { }
}

export class InfoEmployee {
    constructor(
        public placeId : number,
        public  fullName : string,
        public  unitName : string,
        public  phoneNumber : string,
        public  email : string,
        public  status : number,
       public listData : DetailsEmployeeInfo[]
    ) { }
}
export class DetailsEmployeeInfo  {
    public  issuesStationeryItemId : string;
    public  stationeryId: string;
    public  totalRequest : number;
    public  unitPrice: number;
    public  status: number;
    public  note: string;
    public  statusUnit: string;
    public  totalFulfill: string;
    public  requestDate: Date;
    public  fullName: string;
    public  email: string;
    public  startPlace: string;
    public  calUnit: string;
    public  calUnitId: string;
    public  stationeryName: string;
    constructor(
    ) { }
}
export class PlaceEmployee{
    constructor(
        public placeId : number,
        public  placeName : string,
        public  lstPlaceId : string,
        public listData : DetailsEmployeeInfo[]
    ) { }
}
export class PlaceEmployeeParam{
    constructor(
        public placeId? : number,
    ) { }
}

export class CanceIssuesStationey{
    public issuesStationeryId? : string;
    public listIssueStationeryItemId? :StationeryReportRequest[];
    public userName? : string;
    constructor(

    ) { }
}
export class VoteHCDV  {
    public  ratingToHcdv? : number;
    public  feedbackToHcdv? : string;
    public  issuesStationeryApprovedId? : string;
    public  ratingToVptct? : number;
    public  feedbackToVptct? : string;
    constructor(
    ) { }
}
export class DataResponseQuota  {
    public  id : string ;
    public  unitName : string ;
    public  unitId : number;
    public  quota  : number;
    public  quantity  : number;
    public  total : number;
    public  path: string ;
    constructor(
    ) { }
}
export class ParamToInsertEmployee  {
    public  message? : string ;
    public  approveUserName? : string ;
    public  listRequestID? : string[];
    public  employeeUserName?  : string;
    constructor(
    ) { }
}
export class CompleteStationey  {
    public  issuesStationeryApprovedId? : string ;
    public  quantity? : number ;
    public  stationeryId?  : string;
    constructor(
    ) { }
}

export class StationeryItem {
    public stationeryId: string;
    public stationeryName: string;
    public unit: string;
    public price: number;
}