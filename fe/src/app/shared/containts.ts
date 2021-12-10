import { environment } from '../../environments/environment';
import { RoleManagement } from "../header/Role";

export class Constants {
    //For local developing
    public static HOME_URL = 'http://localhost:8080/PMQTVP/';
    public static LOGOUT_URL = 'http://localhost:4200/login/vebinh';
    public static LOGIN_URL = 'http://localhost:4200/login/vebinh';

    // public static HOME_URL = environment.production ? 'https://10.255.216.33:9781/PMQTVP/' : 'https://10.255.216.33:9781/PMQTVP/';
    // public static LOGOUT_URL = 'https://10.255.216.33:9781/PMQTVP/login/vebinh';
    // public static LOGIN_URL = 'https://10.255.216.33:9781/PMQTVP/login/vebinh';

    // public static HOME_URL = environment.production ? 'https://vtnet360.arrowhitech.net/PMQTVP/' : 'https://vtnet360.arrowhitech.net/PMQTVP/';
    // public static LOGOUT_URL = 'https://vtnet360.arrowhitech.net/PMQTVP/login/vebinh';
    // public static LOGIN_URL = 'https://vtnet360.arrowhitech.net/PMQTVP/login/vebinh';

    // //For developing
    //public static HOME_URL =  environment.production ? 'http://3.17.99.127:8080/PMQTVP/' : 'http://3.17.99.127:8080/PMQTVP/';
    //public static LOGOUT_URL = 'http://3.17.99.127:8080/PMQTVP/login/vebinh';
    //public static LOGIN_URL = 'http://3.17.99.127:8080/PMQTVP/login/vebinh';

    /* public static HOME_URL =  environment.production ? 'http://10.61.2.211:8084/PMQTVP/' : 'http://10.61.2.211:8084/PMQTVP/';
     public static LOGOUT_URL = 'http://10.61.2.211:8084/PMQTVP/login/vebinh';
     public static LOGIN_URL = 'http://10.61.2.211:8084/PMQTVP/login/vebinh';
 */
    /*public static HOME_URL =  environment.production ? 'https://10.61.2.229:8080/PMQTVP/' : 'https://10.61.2.229:8080/PMQTVP/';
     public static LOGOUT_URL = 'https://10.61.2.229:8080/PMQTVP/login/vebinh';
     public static LOGIN_URL = 'https://10.61.2.229:8080/PMQTVP/login/vebinh';*/

    //FOR Testing
    /*public static HOME_URL =  environment.production ? 'http://10.61.2.196:9090/PMQTVP/' : 'http://10.61.2.196:9090/PMQTVP/';
    public static LOGOUT_URL = 'http://10.61.2.196:9090/PMQTVP/login/vebinh';
    public static LOGIN_URL = 'http://10.61.2.196:9090/PMQTVP/login/vebinh';*/

    //For Viettel, TODO: change htpps, sso
    //  public static HOME_URL =  environment.production ? 'https://10.60.135.43:8080/PMQTVP/' : 'https://10.60.135.43:8080/PMQTVP/';
    //  public static LOGOUT_URL = 'https://10.255.58.201:8002/sso/logout?' + 'appCode=PMQT&service=https://10.60.135.43:8080/PMQTVP';
    //  public static LOGIN_URL = 'https://10.255.58.201:8002/sso/login?' + 'appCode=PMQT&service=https%3A%2F%2F10.60.135.43%3A8080%2FPMQTVP%2Flogin';

    /*public static HOME_URL =  environment.production ? 'https://10.60.135.43:8086/PMQTVP/' : 'https://10.60.135.43:8086/PMQTVP/';
     public static LOGOUT_URL = 'https://10.255.58.201:8002/sso/logout?' + 'appCode=PMQT&service=https://10.60.135.43:8086/PMQTVP';
     public static LOGIN_URL = 'https://10.255.58.201:8002/sso/login?' + 'appCode=PMQT&service=https%3A%2F%2F10.60.135.43%3A8086%2FPMQTVP%2Flogin';*/


    public static TOKEN_KEY = 'AuthToken';
    public static REFRESH_TOKEN_KEY = 'RefreshToken';
    public static USER_INFO = 'UserInfo';

    //module 1
    //module 1
    public static 'check-in-out' = RoleManagement.IN_OUT_MANAGER;
    public static 'addCarId' = RoleManagement.CARD_ID;
    public static 'checking' = RoleManagement.CHECKING;

    //module 2
    public static 'kitchenManager' = RoleManagement.KITCHEN_MANAGEMENT;
    public static 'report-by-kitchen' = RoleManagement.REPORT_BY_KITCHEN;
    public static 'report-by-employee' = RoleManagement.REPORT_BY_EMPLOYEE;
    public static 'dish-config' = RoleManagement.DISH_CONFIG;
    public static 'menu-config' = RoleManagement.MENU_CONFIG;
    public static 'kitchen-config' = RoleManagement.KITCHEN_CONFIG;
    public static 'dayoff-config' = RoleManagement.DAY_OFF_CONFIG;
    public static 'abbreviations-config' = RoleManagement.SHORTENING_CONFIG;
    public static 'add-new-abbreviations' = RoleManagement.SHORTENING_CONFIG;

    //module 3
    public static 'book-car' = RoleManagement.CARBOOKING_MANAGEMENT;
    public static 'addTeamCar' = RoleManagement.ADD_TEAM_CAR;
    public static 'addCategoryCar' = RoleManagement.ADD_CATEGORY_CAR;
    public static 'addCategoryDriver' = RoleManagement.ADD_CATEGORY_DRIVER;
    public static 'reportBookCar' = RoleManagement.REPORT_BOOK_CAR;
    public static 'carFreeTimes' = RoleManagement.CARFREETIMES;

    // module 4
    public static 'service-management' = RoleManagement.SERVICE_MANAGEMENT;
    public static 'ServiceMenu' = RoleManagement.SERVICE_MENU;
    public static 'StationeryMenu' = RoleManagement.STATIONERY_MENU;
    public static 'ServiceStatistics' = RoleManagement.SERVICE_STATISTICS;
    public static 'SignVoffice' = RoleManagement.SIGN_VOFFICE;

    //module 5
    public static 'stationery' = RoleManagement.STATIONERY_MANAGEMENT;
    public static 'stationeryManagement' = RoleManagement.STATIONERY_ITEMS;
    public static 'receiver' = RoleManagement.STATIONERY_STAFF;
    public static 'reportStationery' = RoleManagement.ISSUES_STATIONERY_REPORT;
    public static 'addStationery' = RoleManagement.STATIONERY_LIMIT;

    //setting
    public static 'doimatkhau' = RoleManagement.CHANGE_PASSWORD;
    public static 'setting' = RoleManagement.SETTING;
    public static 'place-config' = RoleManagement.PLACE_SETTING;
    public static 'version-config' = RoleManagement.VERSION_SETTING;

    /** BOOKCAR-Add TeamCar */
    public static 'RESPONSE_SUCESS' = 1;
    public static 'RESPONSE_ERROR' = 0;
    public static 'UPDATE_SUCESS' = 2;
    public static 'DRIVE_SQUAD_EXIST' = 3;
    public static 'STATUS_DUPLICATE_MANAGER_CAR_SQUAD' = 4;
    public static 'DELETE_SQUAD_SUCESS' = 1;
    public static 'DELETE_UPDATE_ERROR' = 44;

    public static 'MAX_RECORD_DISPLAY' = 50;


    /** Warehouse File Management */
    public static 'warehouse' = 'WAREHOUSE';
    public static 'fileBucket' = 'FILE_BUCKET';
    public static 'warehouseManagement' = 'WAREHOUSE_MANAGEMENT';
    public static 'warehouseReport' = 'WAREHOUSE_REPORT';
    public static 'warehouseGroup' = 'WAREHOUSE_GROUP';

    /** Warehouse */
    public static 'STATUS_DUPLICATE_WAREHOUSE' = 1;
    public static 'DELETE_WAREHOUSE_SUCCESS' = 3;
    public static 'SLOT_BEING_USED' = 5;
    public static 'uploadProject' = RoleManagement.UPLOAD_PROJECT;
    public static 'uploadOfficialDispatch' = RoleManagement.UPLOAD_OFFICIAL_DISPATCH;
    public static 'uploadVoucher' = RoleManagement.UPLOAD_VOUCHER;

    public static 'OFFICIAL_DISPATCH_TYPE' = {
        'INCOMING_DOC': 0,
        'TRAVELS_DOC': 1
    };
    public static 'IMPORT_TYPE' = {
        'PROJECT': 1,
        'OFFICIAL_DISPATCH': 2,
        'INCOMING': 3,
        'VOUCHER': 4,
        'PAYMENT_SUMMARY': 5,
        'VOUCHER_NOTE': 6,
    };

    public static 'VOUCHER_TYPE' = {
        'DEFAULT': 1, // chung tu
        'NOTE': 2   // chung tu ghi so
    };

    public static 'SEARCH_DOC_TYPE' = {
        BOX: {
            code: 1,
            name: 'Thùng/hộp',
            sync: false,
            idField: 'tinBoxId'
        },
        FOLDER: {
            code: 2,
            name: 'Buộc hồ sơ',
            sync: false,
            idField: 'folderId'
        },
        PROJECT: {
            code: 3,
            name: 'Dự án',
            sync: true,
            idField: 'projectId'
        },
        PACKAGE: {
            code: 4,
            name: 'Gói thầu',
            sync: true,
            idField: 'packageId'
        },
        CONTRACT: {
            code: 5,
            name: 'Hợp đồng',
            sync: true,
            idField: 'contractId'
        },
        CONSTRUCTION: {
            code: 6,
            name: 'Công trình',
            sync: true,
            idField: 'constructionId'
        },
        OFFICIAL_DISPATCH_TRAVEL: {
            code: 7,
            name: 'Công văn đi',
            sync: false,
            idField: 'id'
        },
        INCOMING_OFFICIAL_DISPATCH: {
            code: 8,
            name: 'Công văn đến',
            sync: false,
            idField: 'id'
        },
        VOUCHER: {
            code: 9,
            name: 'Chứng từ',
            sync: false,
            idField: 'voucherId'
        },
        VOUCHER_NOTE: {
            code: 10,
            name: 'Chứng từ ghi sổ',
            sync: false,
            idField: 'voucherId'
        },
        PAYMENT_SUMMARY: {
            code: 11,
            name: 'Bảng TH thanh toán',
            sync: false,
            idField: 'paymentSummaryId'
        }
    };

    public static PROJECT_TREE_NODE_TYPE = {
        PROJECT: 1,
        PACKAGE: 2,
        CONTRACT: 3,
        CONSTRUCTION: 4
    };

    public static RESPONSE_FILE_TYPE_NOT_MATCH = 99;

    public static DOCUMENT_ACCEPT_TYPE = [
        'pdf', 'doc', 'docx', 'xls', 'xlsx', 'svg'
      ];
}
