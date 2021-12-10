
export class RoleManagement {

  //module 1
  public static IN_OUT_MANAGER: string[] = ['IN_OUT_MANAGEMENT'];
  public static CHECKING: string[] = ['PMQT_NV', 'PMQT_QL', 'PMQT_Canhve', 'PMQT_CVP'];
  public static CARD_ID: string[] = ['PMQT_HCVP'];

  //module 2
  public static KITCHEN_MANAGEMENT: string[] = ['LUNCH_MANAGEMENT'];
  public static REPORT_BY_KITCHEN: string[]  = ['REPORT_BY_UNIT', 'PMQT_HCVP', 'PMQT_HCDV', 'PMQT_CVP', 'PMQT_QL'];
  public static REPORT_BY_EMPLOYEE: string[] = ['REPORT_BY_EMPLOYEE', 'PMQT_NV', 'PMQT_QL', 'PMQT_HCVP', 'PMQT_HCDV', 'PMQT_CVP'];
  public static KITCHEN_CONFIG: string[]     = ['KITCHEN_SETTING', 'PMQT_Bep_truong'];
  public static DISH_CONFIG: string[]        = ['PMQT_Bep_truong'];
  public static MENU_CONFIG: string[]        = ['PMQT_Bep_truong', 'PMQT_NV'];
  public static DAY_OFF_CONFIG: string[]     = ['PMQT_Bep_truong', 'PMQT_HCVP'];
  public static SHORTENING_CONFIG: string[]  = ['PMQT_Bep_truong'];

  //module 3
  public static CARBOOKING_MANAGEMENT: string[] = ['CAR_BOOKING_MANAGEMENT'];
  public static ADD_CATEGORY_DRIVER: string[]   = ['PMQT_QL_Doi_xe', 'DRIVE_CAR_SETTING'];
  public static ADD_TEAM_CAR: string[]          = ['DRIVES_SQUAD', 'PMQT_QL_Doi_xe'];
  public static ADD_CATEGORY_CAR: string[]      = ['CAR_SETTING', 'PMQT_QL_Doi_xe'];
  public static REPORT_BOOK_CAR: string[]       = ['CAR_BOOKING_REPORT', 'PMQT_QL_Doi_xe', 'PMQT_NV', 'PMQT_QL', 'PMQT_HCVP', 'PMQT_HCDV', 'PMQT_CVP', 'PMQT_Doi_xe'];
  public static CARFREETIMES: string[]          = ['DRIVE_CAR_SETTING', 'PMQT_QL_Doi_xe', 'PMQT_CVP'];

  //module 4
  public static SERVICE_MANAGEMENT: string[]    = ['SERVICE_MANAGEMENT'];
  public static SERVICE_MENU: string[]          = ['SERVICES', 'PMQT_HCVP'];
  public static STATIONERY_MENU: string[]       = ['STATIONERY', 'PMQT_HCVP'];
  public static SERVICE_STATISTICS: string[]    = ['ISSUES_SERVICE_REPORT', 'PMQT_NV', 'PMQT_QL', 'PMQT_HCVP', 'PMQT_HCDV', 'PMQT_CVP'];
  public static SIGN_VOFFICE: string[]          = ['PMQT_QL', 'PMQT_HCVP', 'PMQT_HC_DV', 'PMQT_CVP'];

  //module 5
  public static STATIONERY_MANAGEMENT: string[] = ['STATIONERY_MANAGEMENT'];
  public static STATIONERY_ITEMS: string[]      = ['STATIONERY_ITEMS', 'PMQT_NV', 'PMQT_QL', 'PMQT_HCVP_VPP', 'PMQT_HC_DV'];
  public static STATIONERY_STAFF: string[]      = ['STATIONERY_STAFF', 'PMQT_HCVP_VPP'];
  public static ISSUES_STATIONERY_REPORT: string[] = ['ISSUES_STATIONERY_REPORT', 'PMQT_NV', 'PMQT_QL', 'PMQT_HCVP_VPP', 'PMQT_HC_DV'];
  public static STATIONERY_LIMIT: string[]      = ['PMQT_HCVP_VPP'];

  //warehouse 
  public static UPLOAD_PROJECT: string[]                = ['WAREHOUSE_VT', 'WAREHOUSE_DA'];
  public static UPLOAD_OFFICIAL_DISPATCH: string[]      = ['WAREHOUSE_VT'];
  public static UPLOAD_VOUCHER: string[]                = ['WAREHOUSE_TC'];

  //setting
  public static CHANGE_PASSWORD: string[] = ['PMQT_Canhve'];
  public static SETTING: string[]         = [];
  public static PLACE_SETTING: string[]   = [];
  public static VERSION_SETTING: string[] = [];

}

