package com.viettel.vtnet360.vt00.common;

import java.io.File;

public class Constant {

	private Constant() {
	}
	
	/** SALT for EnDecryptUtils */
	public static final String DEFAULT_SALT = "nocpro@@123";
	
	/** when error */
	public static final int RESPONSE_STATUS_ERROR = 0;

	/** when success */
	public static final int RESPONSE_STATUS_SUCCESS = 1;

	/** when record exist */
	public static final int RESPONSE_STATUS_RECORD_EXISTED = 2;

	/** when no role to updat, delete record */
	public static final int RESPONSE_STATUS_NO_ROLE = 3;

	/** when record is not existed */
	public static final int RESPONSE_STATUS_RECORD_NOT_EXISTED = 4;

	/** when err DuplicateKey Exception */
	public static final int RESPONSE_ERROR_DUPLICATEKEY = 5;

	/** when exist same service not yet complete */
	public static final int RESPONSE_EXIST_SERVICE = 6;

	/** when exist in out Register not Valid */
	public static final int RESPONSE_EXIST_INOUT_REGISTER_NOT_VALID = 7;

	/** when exist SERVICE not Valid */
	public static final int RESPONSE_EXIST_SERVICE_NOT_VALID = 8;
	
	/**  ISSUED_SERVICE_CANT_EXECUTE when status issuedService change */
	public static final int RESPONSE_ISSUED_SERVICE_CHANGED_STATUS = 9;
	
	/**  RESPONSE_INOUT_RIGISTER_CHANGED_STATUS */
	public static final int RESPONSE_INOUT_RIGISTER_CHANGED_STATUS = 10;

	/**  RESPONSE_INOUT_RIGISTER_CHANGED_APPROVER */
	public static final int RESPONSE_INOUT_RIGISTER_CHANGED_APPROVER = 11;

	/** RESPONSE_INOUT_OUT_DATE */
	public static final int RESPONSE_INOUT_OUT_DATE = 12;
	
	/**  RESPONSE_LIST_INOUT_CANT_APROVED */
	public static final int RESPONSE_LIST_INOUT_CANT_APROVED = 13;
	
	/**  RESPONSE RESPONSE SERVICE DELETED */
	public static final int RESPONSE_SERVICE_DELETED = 14;
	
	/**  RESPONSE  STATIONERY DELETED */
	public static final int RESPONSE_STATIONERY_DELETED = 15;
	
	/** when user have request not yet complete*/
	public static final int RESPONSE_SERVICE_NOT_COMPLETE = 16;
	
	public static final int DAY_IS_LIMIT = 17;
	
	public static final int CONFIRM_REQUEST = 18;
	
	public static final int LOCATION_DUPLICATEKEY = 19;
	
	public static final int UNIT_DUPLICATEKEY = 20;
	
	/** when user have request not yet complete*/
	public static final int RESPONSE_FILE_TYPE_NOT_MATCH = 99;
	
	public static final int STATUS_MEG = 11;
	
	/**  DELETED FLAG SERVICE */
	public static final int DELETED_FLAG = 1;
	
	/** action insert */
	public static final int REQUEST_ACTION_INSERT = 1;

	/** action update */
	public static final int REQUEST_ACTION_UPDATE = 2;

	/** action delete */
	public static final int REQUEST_ACTION_DELETE = 3;

	/** field status in tables in db */
	public static final int STATUS_ACTIVE = 1;

	/** field status in tables in db */
	public static final int STATUS_INACTIVE = 0;

	/** field DELETE_FG in tables in db */
	public static final int DELETE_FG_ACTIVE = 0;

	/** field DELETE_FG in tables in db */
	public static final int DELETE_FG_INACTIVE = 1;

	/** lunchDate quantity default */
	public static final int QUANTITY_DEFAULT = 1;

	/** lunchDate quantity none */
	public static final int QUANTITY_NONE = 0;

	/** lunchDate quantity limited */
	public static final int QUANTITY_LIMITED = 20;
	//
	public static final int UNIT_NOT_EXIST_QUOTA = 21;

	public static final int UNIT_NOT_EXIST_STAFF= 22;
	/** lunchDate hasbooking default */
	public static final int HAS_BOOKING_DEFAULT = 1;

	/** lunchDate hasbooking default */
	public static final int HAS_BOOKING_NONE = 0;

	/** lunchDate periodic default */
	public static final int PERIODIC_DEFAULT = 1;

	/** lunchDate periodic none */
	public static final int PERIODIC_NONE = 0;

	/** lunchDate rating min */
	public static final int RATING_MIN = 0;

	/** lunchDate rating max */
	public static final int RATING_MAX = 5;

	/** success */
	public static final int SUCCESS = 1;

	/** eror */
	public static final int ERROR = 0;

	/** VP doi du an thuoc TT PTPM */
	public static final String PMQT_ROLE_ADMIN = "PMQT_ADMIN";

	/** VP Nhan vien (NV) toan TCT */
	public static final String PMQT_ROLE_EMPLOYYEE = "PMQT_NV";

	/** Can bo quan ly/lanh dao toan TCT */
	public static final String PMQT_ROLE_MANAGER = "PMQT_QL";

	/** Chanh van phong */
	public static final String PMQT_ROLE_MANAGER_CVP = "PMQT_CVP";

	/** NV canh ve trong TCT */
	public static final String PMQT_ROLE_GUARD = "PMQT_Canhve";

	/** Bep truong trong va ngoai TCT */
	public static final String PMQT_ROLE_CHEF = "PMQT_Bep_truong";

	/** NV doi xe trong TCT */
	public static final String PMQT_ROLE_EMPLOYEE_DOIXE = "PMQT_Doi_xe";

	/** doi truong doi xe trong TCT */
	public static final String PMQT_ROLE_MANAGER_DOIXE = "PMQT_QL_Doi_xe";

	/** NV van phong cua VP */
	public static final String PMQT_ROLE_STAFF = "PMQT_HCVP";

	/** NV VP cua cac don vi trong TCT */
	public static final String PMQT_ROLE_STAFF_HC_DV = "PMQT_HC_DV";

	/** NV chuyen trach VPP cua VP */
	public static final String PMQT_ROLE_STAFF_HCVP_VPP = "PMQT_HCVP_VPP";
	
	public static final String PMQT_ROLE_WAREHOUSE = "WAREHOUSE";
	
	public static final String PMQT_ROLE_WAREHOUSE_REPORT = "WAREHOUSE_REPORT";
	
	public static final String PMQT_ROLE_TINBOX_VIEW_ALL = "TINBOX_VIEW_ALL";
	public static final String PMQT_ROLE_TINBOX_UPDATE = "TINBOX_UPDATE";
	public static final String PMQT_ROLE_TINBOX_UPDATE_SUPER = "TINBOX_UPDATE_SUPER";
	public static final String PMQT_ROLE_TINBOX_DELETE = "TINBOX_DELETE";
	public static final String PMQT_ROLE_FOLDER_MOVE = "FOLDER_MOVE";
	public static final String PMQT_ROLE_WAREHOUSE_GROUP = "WAREHOUSE_GROUP";
	
	public static final String PDF = "pdf";
	public static final String XLS = "xls";
	public static final String XLSX = "xlsx";
	public static final String SVG = "svg";
	public static final String DOC = "doc";
	public static final String DOCX = "docx";

	
	public static final String PMQT_UNIT_STAFF_HCVP_TCT = "240541";
	
	/** ISSUED SERVICE STATUS COMPLETE */
	public static final int ISSUED_SERVICE_STATUS_WAITING_APROVE = 0;
	
	/** ISSUED SERVICE STATUS COMPLETE */
	public static final int ISSUED_SERVICE_STATUS_APROVED = 1;
	
	/** ISSUED SERVICE STATUS COMPLETE */
	public static final int ISSUED_SERVICE_STATUS_REJECT_APROVED = 2;
	
	/** ISSUED SERVICE STATUS COMPLETE */
	public static final int ISSUED_SERVICE_STATUS_EXECUTING = 3;
	
	/** ISSUED SERVICE STATUS COMPLETE */
	public static final int ISSUED_SERVICE_STATUS_PENDING_EXECUTE = 4;
	
	/** ISSUED SERVICE STATUS COMPLETE */
	public static final int ISSUED_SERVICE_STATUS_CANT_EXECUTE = 5;
	
	/** ISSUED SERVICE STATUS COMPLETE */
	public static final int ISSUED_SERVICE_STATUS_COMPLETE = 6;
	
	/** ISSUED SERVICE STATUS COMPLETE */
	public static final int ISSUED_SERVICE_CANCEL = 7;
	
	/** ISSUED SERVICE STATUS RECEIVE */
	public static final int ISSUED_SERVICE_STATUS_RECEIVE = 8;
	
	/** ISSUED SERVICE STATUS REJECT */
	public static final int ISSUED_SERVICE_STATUS_REJECT = 9;

	/** MANAGER_APPROVE */
	public static final int WAIT_MANAGER_APPROVE_STILL = 10;
	
	/** MANAGER_APPROVE */
	public static final int WAIT_MANAGER_APPROVE = 0;

	/** MANAGER_APPROVE */
	public static final int MANAGER_APPROVED = 1;

	/** ANAGER_ABANDON */
	public static final int MANAGER_ABANDON = 2;

	/** GRUARD_APPROVE_OUT */
	public static final int GRUARD_APPROVE_OUT = 3;

	/** GRUARD_APPROVE_IN */
	public static final int GRUARD_APPROVE_IN = 4;

	/** EXTEND_AFTER_APPROVED */
	public static final int EXTEND_AFTER_APPROVED = 5;

	/** GRUARD_ABANDON_OUT */
	public static final int GRUARD_ABANDON_OUT = 6;

	/** GRUARD_ABANDON_IN */
	public static final int GRUARD_ABANDON_IN = 7;
	
	/** IN_OUT_REGISTER_WATING_APPROVED*/
	public static final int IN_OUT_REGISTER_WATING_APPROVED = 0;
	
	/** IN_OUT_REGISTER_APPROVED*/
	public static final int IN_OUT_REGISTER_APPROVED = 1;
	
	/** IN_OUT_REGISTER_REFUSE */
	public static final int IN_OUT_REGISTER_REFUSE = 2;
	
	/** IN_OUT_REGISTER_REFUSE */
	public static final int IN_OUT_GO_OUT = 3;
	
	/** IN_OUT_REGISTER_REFUSE */
	public static final int IN_OUT_GO_IN = 4;
	
	/** IN_OUT_REGISTER_WATING_APPROVED*/
	public static final int IN_OUT_REGISTER_WATING_EXTEND = 5;
	
	/** IN_OUT_REGISTER_WATING_APPROVED*/
	public static final int IN_OUT_REGISTER_REJECT_OUT = 6;
	
	/** IN_OUT_REGISTER_WATING_APPROVED*/
	public static final int IN_OUT_REGISTER_REJECT_IN = 7;

	/** OUT_DATE_IN_FOR_IN_OUT */
	public static final int OUT_DATE_IN_FOR_IN_OUT = 8;

	/** GRUARD_ABANDON_IN */
	public static final int OUT_DATE_IN_OUT = 9;

	/** SERVER_GOOGLE_NOTIFICATION */
	public static final String URL_SERVER_GOOGLE_NOTIFICATION = "https://fcm.googleapis.com/fcm/send";

	/** SERVER_LOCAL_NOTIFICATION */
	/* 171.255.192.198:8199 */
	/* http://localhost:8085/PMQTVP/com/viettel/forward/api/vt000000/00 */
	public static final String URL_LOCAL_NOTIFICATION = "http://localhost:8085/PMQTVP/com/viettel/forward/api/vt000000/00";

	/** SERVER_KEY_NOTIFICATION */
//	public static final String SERVER_KEY_NOTIFICATION = "AIzaSyCpvjic2Wj1OttxkXr62DZBCGUpYlGAahE";
	//AIzaSyCVgGqpQtHCcujk3orCKrqPOrGtFfbkueM
	//AIzaSyC95XoKKAmtnjYjQDyasFzHJV82h9SztN4
	public static final String SERVER_KEY_NOTIFICATION = "AAAAWtmvfQY:APA91bErkQCj-nfk5OivSgNhm1EQN8mBq1juKxwbPHZk1z_AoqFt78M1TSvrtT6tqhptwQLzKLWLTtDDClG1I2cR4vDEoU6QNC_pAdAkacdb4WrmP6h3TI0Iv2QhjCFabwKD16uqYGuY";

	/** TYPE_NOTIFY_MODUL01 */
	public static final int TYPE_NOTIFY_MODUL01 = 1;

	/** TYPE_NOTIFY_MODUL01 */
	public static final int STATUS_NEW_SMS = 0;

	/** TYPE_NOTIFY_MODUL02 */
	public static final int TYPE_NOTIFY_MODUL02 = 2;

	/** TYPE_NOTIFY_MODUL03 */
	public static final int TYPE_NOTIFY_MODUL03 = 3;

	/** TYPE_NOTIFY_MODUL04 */
	public static final int TYPE_NOTIFY_MODUL04 = 4;

	/** TYPE_NOTIFY_MODUL05 */
	public static final int TYPE_NOTIFY_MODUL05 = 5;

	/** TYPE FORMAT DATE */
	public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

	/** CAR START RUNNING **/
	public static final int STATUS_ROUTE_CAR_START = 5;

	/** CAR AT TARGET **/
	public static final int STATUS_ROUTE_CAR_TARGET = 6;

	/** CAR COME BACK **/
	public static final int STATUS_ROUTE_CAR_COME_BACK = 7;

	/** UPDATE CAR **/
	public static final int STATUS_DUPLICATE_CAR_SQUAD = 3;
	
	public static final int STATUS_DUPLICATE_MANAGER_CAR_SQUAD = 4;

	/** STATUS TO CHECK CAR IS RUNNING WHEN DELETE CAR SQUAD **/
	public static final int STATUS_IS_RUNING_CAR = 5;
	
	/** STATUS TO CHECK CAR IS RUNNING WHEN DELETE CAR SQUAD **/
  public static final int STATUS_IS_RUNING_CAR_TEMP = 4;
	
	public static final int STATUS_IS_RUNING_DRIVE = 5;

	/** MASTER_CLASS OF TYPE CAR **/
	public static final String MASTER_CLASS_TYPE_CAR = "S001";

	/** MASTER_CLASS OF SEAT CAR **/
	public static final String MASTER_CLASS_SEAT_CAR = "S002";

	/** MASTER_CLASS OF ROUTE CAR **/
	public static final String MASTER_CLASS_ROUTE_CAR = "S003";

	/** MANAGER_CAR_SQUAD APPROVE **/
	public static final int MANAGER_CAR_SQUAD_APPROVE = 4;

	public static final int DRIVER_NOT_EXISTED = 3;

	public static final int CAR_NOT_EXISTED = 6;

	public static final int DELETE_FLAG = 1;
	
	public static final int MANAGER_HAS_APPROVED = 10;
	
	public static final int MANAGER_CAR_SQUAD_APPROVED = 10;

	/** STATUS PROCESSING IN ISSUES_STATIONERY */
	public static final int ISSUES_STATIONERY_PROCESSING = 1;

	/** STATUS COMPLETE SECTION IN ISSUES_STATIONERY */
	public static final int ISSUES_STATIONERY_COMPLETE_SECTION = 2;

	/** STATUS PROCESSING IN ISSUES_STATIONERY */
	public static final int ISSUES_STATIONERY_COMPLETE = 3;

	/** STATUS PROCESSING IN ISSUES_STATIONERY */
	public static final int ISSUES_STATIONERY_REJECT = 4;

	/** STATUS PROCESSING IN ISSUES_STATIONERY */
	public static final int ISSUES_STATIONERY_CANCEL = 5;
	
	public static final int ISSUES_STATIONERY_NOT_ACEEPT = 7;
	
	public static final int ISSUES_STATIONERY_REFUSE = 6;

	/** STATUS CREATE NEW RECORD IN ISSUES_STATIONERY_ITEMS */
	public static final int STATIONERY_CREATE = 0;

	/** STATUS WAIT MANAGER APPROVE IN ISSUES_STATIONERY_ITEMS */
	public static final int STATIONERY_WAIT_MANAGER_APPROVE = 1;

	/** STATUS MANAGER APPROVE IN ISSUES_STATIONERY_ITEMS */
	public static final int STATIONERY_MANAGER_APPROVE = 2;

	/** STATUS MANAGER REJECT IN ISSUES_STATIONERY_ITEMS */
	public static final int STATIONERY_MANAGER_REJECT = 3;

	/** STATUS WAIT CHIEF OF STAFF APPROVE IN ISSUES_STATIONERY_ITEMS */
	public static final int STATIONERY_WAIT_CHIEF_OF_STAFF_APPROVE = 4;

	/** STATUS CHIEF OF STAFF APPROVE IN ISSUES_STATIONERY_ITEMS */
	public static final int STATIONERY_CHIEF_OF_STAFF_APPROVE = 5;

	/** STATUS CHIEF OF STAFF REJECT IN ISSUES_STATIONERY_ITEMS */
	public static final int STATIONERY_CHIEF_OF_STAFF_REJECT = 6;

	/**
	 * STATUS STATIONERY CONFIRM RECEIVED REQUEST FROM HCDV IN
	 * ISSUES_STATIONERY_ITEMS
	 */
	public static final int STATIONERY_RECEIVE_REQUEST = 4;

	/** STATUS STATIONERY PAUSE IN ISSUES_STATIONERY_ITEMS */
	public static final int STATIONERY_PAUSE = 5;

	/** STATUS STATIONERY REJECT IN ISSUES_STATIONERY_ITEMS */
	public static final int STATIONERY_REJECT = 6;

	/** STATUS STATIONERY EXECUTE IN ISSUES_STATIONERY_ITEMS */
	public static final int STATIONERY_EXECUTING = 7;

	/** STATUS STATIONERY FINISH IN ISSUES_STATIONERY_ITEMS */
	public static final int STATIONERY_FINISH = 8;

	/** STATUS STATIONERY CANCEL IN ISSUES_STATIONERY_ITEMS */
	public static final int STATIONERY_CANCEL = 9;

	public static final int STATIONERY_REFUSE = 10;
	
	/** STATUS WAIT MANAGER APPROVE IN ISSUES_STATIONERY_APPROVED */
	public static final int STATIONERY_APPROVED_WAIT_MANAGER_APPROVE = 0;

	/** STATUS MANAGER APPROVE IN ISSUES_STATIONERY_APPROVED */
	public static final int STATIONERY_APPROVED_MANAGER_APPROVE = 1;

	/** STATUS MANAGER REJECT IN ISSUES_STATIONERY_APPROVED */
	public static final int STATIONERY_APPROVED_MANAGER_REJECT = 2;

//	/** STATUS WAIT CHIEF OF STAFF APPROVE IN ISSUES_STATIONERY_APPROVED */
//	public static final int STATIONERY_APPROVED_WAIT_CHIEF_OF_STAFF_APPROVE = 3;
//
//	/** STATUS CHIEF OF STAFF APPROVE IN ISSUES_STATIONERY_APPROVED */
//	public static final int STATIONERY_APPROVED_CHIEF_OF_STAFF_APPROVE = 4;
//
//	/** STATUS CHIEF OF STAFF REJECT IN ISSUES_STATIONERY_APPROVED */
//	public static final int STATIONERY_APPROVED_CHIEF_OF_STAFF_REJECT = 5;

	/**
	 * STATUS STATIONERY CONFIRM RECEIVED REQUEST FROM HCDV IN
	 * ISSUES_STATIONERY_APPROVED
	 */
	public static final int STATIONERY_APPROVED_RECEIVE_REQUEST = 3;

	/** STATUS STATIONERY PAUSE IN ISSUES_STATIONERY_APPROVED */
	public static final int STATIONERY_APPROVED_PAUSE = 4;

	/** STATUS STATIONERY REJECT IN ISSUES_STATIONERY_APPROVED */
	public static final int STATIONERY_APPROVED_REJECT = 5;

	/** STATUS STATIONERY EXECUTE IN ISSUES_STATIONERY_APPROVED */
	public static final int STATIONERY_APPROVED_EXECUTING = 6;

	/** STATUS STATIONERY FINISH IN ISSUES_STATIONERY_APPROVED */
	public static final int STATIONERY_APPROVED_FINISH = 7;
	
	/** STATUS STATIONERY COMPLETE IN ISSUES_STATIONERY_APPROVED */
	public static final int STATIONERY_APPROVED_COMPLETE = 8;

	/** SPENDING LIMIT IN M_SYSTEM_CODE.MASTER_CLASS */
	public static final String STATIONERY_SPENDING_LIMIT_CLASS = "S007";

	/** SPENDING LIMIT IN M_SYSTEM_CODE.CODE_VALUE */
	public static final String STATIONERY_SPENDING_LIMIT_CODE = "01";

	/** TYPE HCDV IN M_SYSTEM_CODE.MASTER_CLASS */
	public static final String STATIONERY_HCDV_CLASS = "S006";

	/** CODE HCDV IN M_SYSTEM_CODE.CODE_VALUE */
	public static final String STATIONERY_HCDV_CODE = "01";

	/** TYPE VPTCT IN M_SYSTEM_CODE.MASTER_CLASS */
	public static final String STATIONERY_VPTCT_CLASS = "S006";

	/** CODE VPTCT IN M_SYSTEM_CODE.CODE_VALUE */
	public static final String STATIONERY_VPTCT_CODE = "02";

	/** RESPONSE SPENDING LIMIT EXCEED */
	public static final int STATIONERY_RESPONSE_SPENDING_LIMIT_EXCEED = 2;

	public static final int STATIONERY_RESPONSE_SPENDING_LIMIT = 3;
	
	/** RESPONSE SPENDING LIMIT EXCEED */
	public static final int STATIONERY_ACTION_REJECT = 0;

	/** RESPONSE SPENDING LIMIT EXCEED */
	public static final int STATIONERY_ACTION_APPROVE = 1;

	/** Couple card and employee was existed */
	public static final int RESPONSE_EXISTED_CARD_EMPLOYEE = 2;

	/** insufficient powers */
	public static final int RESPONSE_INSUFFICIENT_POWERS = 3;

	/** data too long */
	public static final int RESPONSE_DATA_TOO_LONG = 4;

	/** error validate input */
	public static final int RESPONSE_ERROR_VALIDATE = 4;

	/** error place not exist */
	public static final int RESPONSE_PLACE_NOT_EXIST = 5;

	/** STATUS COMPLETE SERVICE ON TIME *//*
	public static final String STATUS_COMPLETE_ON_TIME = "Đúng hạn";

	*//** STATUS COMPLETE SERVICE OUT OF DATE *//*
	public static final String STATUS_COMPLETE_OUT_OF_DATE = "Quá hạn";*/

	/** SERVICE IS REQUESTING */
	public static final int SERVICE_IS_REQUESTING = 3;

	// used for modul 4

	public static final int WAITING_APROVE_IS_SV = 0;

	public static final int APROVED_IS_SV = 1;

	public static final int ABANDON_IS_SV = 2;

	public static final int FLAG_EXECUTIVING_IS_SV = 3;

	/* FLAG_PENDING_EXECUTIVE */
	public static final int FLAG_PENDING_EXECUTIVE_IS_SV = 4;

	/* FLAG_CANT_EXECUTIVE */
	public static final int FLAG_CANT_EXECUTIVE_IS_SV = 5;

	/* FLAG_COMPLETE */
	public static final int FLAG_COMPLETE_IS_SV = 6;

	/* SEND SMS AND NOTIFY SUCCESS */
	public static final int FLAG_SEND_SMS_NOTIFY_SUCCESS = 1;

	/* SEND SMS AND NOTIFY FALL */
	public static final int FLAG_SEND_SMS_NOTIFY_FALL = 0;

	/** CHECK RESULT COUNT(*) IN QUERY SQL */
	public static final int NONE_EXIST_RECORD = 0;

	/* Device Type Android */
	public static final String ANDROID_DEVICE = "android";

	/* Device Type iOS */
	public static final String APPLE_DEVICE = "apple";

	/**
	 * limit change total lunchDate after 16h30 use for module 2 ( maximum change =
	 * 10%)
	 */
	//public static final double LIMITED_CHANGE_lUNCH_DATE = 0.1;

	/**
	 * error limit change total lunchDate after 16h30 use for module 2 ( maximum
	 * change = 10%)
	 */
	public static final int LIMITED_CHANGE_lUNCH_DATE_ERROR = 2;

	/** HOUR CLOSE ORDER LUNCHDATE AND SEND SMS TO CHEF use for module 2 */
	public static final int TIME_CLOSE_ORDER_LUNCH_DATE_HOUR = 16;

	/** MINUTE CLOSE ORDER LUNCHDATE AND SEND SMS TO CHEF use for module 2 */
	public static final int TIME_CLOSE_ORDER_LUNCH_DATE_MINUTE = 30;

	/** HOUR CHANGE ORDER LUNCHDATE AND SEND SMS TO CHEF use for module 2 */
	public static final int TIME_CHANGE_ORDER_LUNCH_DATE_HOUR = 8;

	/** MINUTE CHANGE ORDER LUNCHDATE AND SEND SMS TO CHEF use for module 2 */
	public static final int TIME_CHANGE_ORDER_LUNCH_DATE_MINUTE = 40;

	/** TIME TO RATING (13-15H) */
	public static final int TIME_LIMIT_RATING_FROM = 13;

	/** TIME TO RATING (13-15H) */
	public static final int TIME_LIMIT_RATING_TO = 15;

	/** CODE MASTER_CLASS FOR CALCULATION IN M_SYSTEM_CODE */
	public static final String M_SYSTEM_CODE_CALCULATION = "S009";

	/**
	 * use for get report follow 1 unit parent, with units start with "khoi" and
	 * "ban" => get unit level 3, other get unit level 2
	 */
	public static final int UNIT_PARENT_ID = 234841;

	/**
	 * use for get report follow 1 unit parent, with units start with "khoi" and
	 * "ban" => get unit level 3, other get unit level 2
	 */
	public static final String UNIT_NAME_KHOI = "khối%";

//	/**
//	 * use for get report follow 1 unit parent, with units start with "khoi" and
//	 * "ban" => get unit level 3, other get unit level 2
//	 */
//	public static final String UNIT_NAME_BAN = "ban%";
	
	/** error when pause execute give out VPP that appointmentTime < time system */
	public static final int ERROR_OVER_TIME_PAUSE_EXECUTE_VPP = 3;
	
	/** error when there are some record invalid or when record is processed before module 5 */
	public static final int ERROR_REQUEST_INVALID = 2;
	
	/** error when there are some record invalid ( is deleted before other user update or delete ) module 2( dish setting ) */
	public static final int ERROR_DISH_REQUEST_INVALID = 4;
	
	/** error when there are some record invalid ( is deleted before other user update or delete ) module 2( place setting ) */
	public static final int ERROR_PLACE_DELETED_BEFORE_UPDATE = 3;
	
	/** error when update record that deleted before */
	public static final int ERROR_REQUEST_INVALID_KITCHEN_SETTING = 9;
	
	public static final int ERROR_UPDATE = 44;

	/** error when NV VPTCT logged on screen execute statoinery but didn't config in STATIONERY_STAFF */
	public static final int ERROR_VPTCT_NO_CONFIG_IN_STATIONERY_STAFF = 14;
	
	/** error when HCDV send request to LDDV but total money of request > spending limit money of unit */
	public static final int ERROR_EXCEED_SPENDING_LIMIT_MONEY_UNIT = 15;
	
	public static final int ERROR_VALID_SPECIAL_CHAR = 16;
	
	/** STATUS CHIEF OF STAFF REJECT IN ISSUES_STATIONERY_ITEMS */
  public static final int STATIONERY_STAFF_REJECT_APPROVE = 10;
  
  
  public static final String KV1_UNIT_ID = "235141";
  public static final String KV2_UNIT_ID = "235222";
  public static final String KV3_UNIT_ID = "235283";
	
	
	public static final String DISH_IMAGE_PATH = "resources" + File.separator + "images" + File.separator + "dish";
	public static final String DISH_DOCUMENT_PATH = "resources" + File.separator + "documents";
	public static final String STATIONERY_IMAGE_PATH = "resources" + File.separator + "images" + File.separator + "stationery";
	
	/*
	 * Warehouse constants
	 */
	public static final int CREATE_ACTION = 1;
	public static final int EDIT_ACTION = 2;
	public static final int DEL_ACTION = 3;
	public static final int ADD_ACTION = 4;
	public static final int REMOVE_ACTION = 5;
	
	/*
	 * Document type
	 */
	public static final int PROJECT_PL = 1;
	public static final int PROJECT_HC = 2;
	public static final int PACKAGE_PL = 3;
	public static final int CONTRACT_QT = 4;
	public static final int CONSTRUCTION_QT = 5;
	public static final int CONSTRUCTION_TK = 6;
	
	//Status check Warehouse
	public static final int STATUS_DUPLICATE_WAREHOUSE = 1;
	
	public static final int CAR_BOOKING_TYPE_DEFAULT = 1;
}
