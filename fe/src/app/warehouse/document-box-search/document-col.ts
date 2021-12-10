export class DocumentCol {

    public static BOX_COL = [
        { field: 'description', header: 'Vị trí', subHeader: '(Hàng, Cột, Cao)', width: '200px'},
        { field: 'slotQRCode', header: 'Mã vị trí', width: '200px', class: 'left-align' },
        { field: 'tinBoxQRcode', header: 'Mã thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'tinBoxName', header: 'Tên thùng/hộp', width: '200px', class: 'left-align' },
    ];

    public static FOLDER_COL = [
        { field: 'folderQRCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'tinBoxQRCode', header: 'Mã thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'tinBoxName', header: 'Tên thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'description', header: 'Vị trí', subHeader: '(Hàng, Cột, Cao)', width: '200px'},
        { field: 'slotQRCode', header: 'Mã vị trí', width: '200px', class: 'left-align' },
        { field: 'folderTypeText', header: 'Loại buộc hồ sơ', width: '200px', class: ''},
    ];

    public static PROJECT_COL = [
        { field: 'synchrony', header: 'Tình trạng', width: '150px', class: '' },
        { field: 'projectName', header: 'Dự án', width: '200px', class: 'left-align' },
        { field: 'folderQRCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'tinBoxQRCode', header: 'Mã thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'tinBoxName', header: 'Tên thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'description', header: 'Vị trí', subHeader: '(Hàng, Cột, Cao)', width: '200px'},
        { field: 'slotQRCode', header: 'Mã vị trí', width: '200px', class: 'left-align' },
    ];

    public static PACKAGE_COL = [
        { field: 'synchrony', header: 'Tình trạng', width: '150px', class: '' },
        { field: 'packageName', header: 'Gói thầu', width: '200px', class: 'left-align' },
        { field: 'projectName', header: 'Dự án', width: '200px', class: 'left-align' },
        { field: 'folderQRCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'tinBoxQRCode', header: 'Mã thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'tinBoxName', header: 'Tên thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'description', header: 'Vị trí', subHeader: '(Hàng, Cột, Cao)', width: '200px'},
        { field: 'slotQRCode', header: 'Mã vị trí', width: '200px', class: 'left-align' },
    ];

    public static CONTRACT_COL = [
        { field: 'synchrony', header: 'Tình trạng', width: '150px', class: '' },
        { field: 'contractName', header: 'Hợp đồng', width: '200px', class: 'left-align' },
        { field: 'packageName', header: 'Gói thầu', width: '200px', class: 'left-align' },
        { field: 'projectName', header: 'Dự án', width: '200px', class: 'left-align' },
        { field: 'folderQRCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'tinBoxQRCode', header: 'Mã thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'tinBoxName', header: 'Tên thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'description', header: 'Vị trí', subHeader: '(Hàng, Cột, Cao)', width: '200px'},
        { field: 'slotQRCode', header: 'Mã vị trí', width: '200px', class: 'left-align' },
    ];

    public static CONSTRUCTION_COL = [
        { field: 'synchrony', header: 'Tình trạng', width: '150px', class: '' },
        { field: 'constructionName', header: 'Công trình', width: '200px', class: 'left-align' },
        { field: 'contractName', header: 'Hợp đồng', width: '200px', class: 'left-align' },
        { field: 'packageName', header: 'Gói thầu', width: '200px', class: 'left-align' },
        { field: 'projectName', header: 'Dự án', width: '200px', class: 'left-align' },
        { field: 'folderQRCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderQRCode', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'tinBoxQRCode', header: 'Mã thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'tinBoxName', header: 'Tên thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'description', header: 'Vị trí', subHeader: '(Hàng, Cột, Cao)', width: '200px'},
        { field: 'slotQRCode', header: 'Mã vị trí', width: '200px', class: 'left-align' },
    ];

    public static OFFICIAL_DISPATCH_TRAVEL_COL = [
        { field: 'officialDispatchName', header: 'Công văn đi', width: '200px', class: 'left-align' },
        { field: 'folderQRCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'tinBoxQRCode', header: 'Mã thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'tinBoxName', header: 'Tên thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'description', header: 'Vị trí', subHeader: '(Hàng, Cột, Cao)', width: '200px'},
        { field: 'slotQRCode', header: 'Mã vị trí', width: '200px', class: 'left-align' },
    ];

    public static INCOMING_OFFICIAL_DISPATCH_COL = [
        { field: 'officialDispatchName', header: 'Công văn đến', width: '200px', class: 'left-align' },
        { field: 'folderQRCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'tinBoxQRCode', header: 'Mã thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'tinBoxName', header: 'Tên thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'description', header: 'Vị trí', subHeader: '(Hàng, Cột, Cao)', width: '200px'},
        { field: 'slotQRCode', header: 'Mã vị trí', width: '200px', class: 'left-align' },
    ];

    public static VOUCHER_COL = [
        { field: 'voucherName', header: 'Chứng từ', width: '200px', class: 'left-align' },
        { field: 'folderQRCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'tinBoxQRCode', header: 'Mã thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'tinBoxName', header: 'Tên thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'description', header: 'Vị trí', subHeader: '(Hàng, Cột, Cao)', width: '200px'},
        { field: 'slotQRCode', header: 'Mã vị trí', width: '200px', class: 'left-align' },
    ];

    public static VOUCHER_NOTE_COL = [
        { field: 'voucherName', header: 'Chứng từ ghi sổ', width: '200px', class: 'left-align' },
        { field: 'folderQRCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'tinBoxQRCode', header: 'Mã thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'tinBoxName', header: 'Tên thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'description', header: 'Vị trí', subHeader: '(Hàng, Cột, Cao)', width: '200px'},
        { field: 'slotQRCode', header: 'Mã vị trí', width: '200px', class: 'left-align' },
    ];

    public static PAYMENT_SUMMARY_COL = [
        { field: 'paymentSummaryName', header: 'Bảng TH thanh toán', width: '200px', class: 'left-align' },
        { field: 'folderQRCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'tinBoxQRCode', header: 'Mã thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'tinBoxName', header: 'Tên thùng/hộp', width: '200px', class: 'left-align' },
        { field: 'description', header: 'Vị trí', subHeader: '(Hàng, Cột, Cao)', width: '200px'},
        { field: 'slotQRCode', header: 'Mã vị trí', width: '200px', class: 'left-align' },
    ];
}


export class DocumentDetailCol {

    public static BOX_COL = [
        // { field: 'folderTypeText', header: 'Loại buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderQrCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'projectName', header: 'Dự án', width: '200px', class: 'left-align' },
        { field: 'packageName', header: 'Gói thầu', width: '200px', class: 'left-align' },
        { field: 'contractName', header: 'Hợp đồng', width: '200px', class: 'left-align' },
        { field: 'constructionName', header: 'Công trình', width: '200px', class: 'left-align' },
        { field: 'voucherName', header: 'Chứng từ', width: '200px', class: 'left-align' },
        { field: 'voucherBookName', header: 'Chứng từ ghi sổ', width: '200px', class: 'left-align' },
        { field: 'paymentSummaryName', header: 'Bảng TH thanh toán', width: '200px', class: 'left-align' },
        { field: 'officialDispatchTravelsName', header: 'Công văn đi', width: '200px', class: 'left-align' },
        { field: 'incomingOfficialDispatchName', header: 'Công văn đến', width: '200px', class: 'left-align' },
        { field: 'documentName', header: 'Tài liệu', width: '200px', class: 'left-align' },
    ];

    public static BOX_COL_DA = [
        { field: 'folderTypeText', header: 'Loại buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderQrCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'projectName', header: 'Dự án', width: '200px', class: 'left-align' },
        { field: 'packageName', header: 'Gói thầu', width: '200px', class: 'left-align' },
        { field: 'contractName', header: 'Hợp đồng', width: '200px', class: 'left-align' },
        { field: 'constructionName', header: 'Công trình', width: '200px', class: 'left-align' },
        { field: 'documentName', header: 'Tài liệu', width: '200px', class: 'left-align' },
    ];

    public static BOX_COL_TC = [
        { field: 'folderTypeText', header: 'Loại buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderQrCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'voucherName', header: 'Chứng từ', width: '200px', class: 'left-align' },
        { field: 'voucherBookName', header: 'Chứng từ ghi sổ', width: '200px', class: 'left-align' },
        { field: 'paymentSummaryName', header: 'Bảng TH thanh toán', width: '200px', class: 'left-align' },
        { field: 'documentName', header: 'Tài liệu', width: '200px', class: 'left-align' },
    ];

    public static OFFICIAL_DISPATCH_TRAVEL_COL = [
        { field: 'folderQrCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'officialDispatchTravelsName', header: 'Công văn đi', width: '200px', class: 'left-align' },
        { field: 'documentName', header: 'Tài liệu', width: '200px', class: 'left-align' },
    ];

    public static INCOMING_OFFICIAL_DISPATCH_COL = [
        { field: 'folderQrCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'incomingOfficialDispatchName', header: 'Công văn đến', width: '200px', class: 'left-align' },
        { field: 'documentName', header: 'Tài liệu', width: '200px', class: 'left-align' },
    ];

    public static VOUCHER_COL = [
        { field: 'folderQrCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'voucherName', header: 'Chứng từ', width: '200px', class: 'left-align' },
        { field: 'documentName', header: 'Tài liệu', width: '200px', class: 'left-align' },
    ];

    public static VOUCHER_NOTE_COL = [
        { field: 'folderQrCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'voucherBookName', header: 'Chứng từ ghi sổ', width: '200px', class: 'left-align' },
        { field: 'documentName', header: 'Tài liệu', width: '200px', class: 'left-align' },
    ];

    public static PAYMENT_SUMMARY_COL = [
        { field: 'folderQrCode', header: 'Mã buộc HS', width: '200px', class: 'left-align' },
        { field: 'folderName', header: 'Tên buộc HS', width: '200px', class: 'left-align' },
        { field: 'paymentSummaryName', header: 'Bảng TH thanh toán', width: '200px', class: 'left-align' },
        { field: 'documentName', header: 'Tài liệu', width: '200px', class: 'left-align' },
    ];
}
