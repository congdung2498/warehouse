
import {SelectItem} from "primeng/api";

export class StatusSignVoffice {
    public static SIGNVOFFICE_STATUS: SelectItem[] = [
        { label: 'Chưa tạo báo cáo', value: '0' },
        { label: 'Chưa trình ký', value: '1' },
        { label: 'Đang trình ký', value: '2' },
        { label: 'Đã phê duyệt', value: '3' },
        { label: 'Đã từ chối', value: '4' },
        { label: 'Đã hủy', value: '5' }
    ];

    public static SIGNVOFFICE_STATUS_REPORT: SelectItem[] = [
        { label: 'Chưa trình ký', value: '1' },
        { label: 'Đang trình ký', value: '2' },
        { label: 'Đã phê duyệt', value: '3' },
        { label: 'Đã từ chối', value: '4' },
        { label: 'Đã hủy', value: '5' }
    ];

    public static SIGNVOFFICE_TYPE: SelectItem[] = [
        { label: 'Báo cáo chi tiết', value: '1' },
        { label: 'Báo cáo tổng hợp', value: '2' }
    ];

    public static STATUS: string[] = [
            'Chưa tạo báo cáo',
            'Chưa trình ký',
            'Đang trình ký',
            'Đã phê duyệt',
            'Đã từ chối',
            'Đã hủy'
    ];
    public static TYPE: string[] = [
        '',
        'Báo cáo chi tiết',
        'Báo cáo tổng hợp'
    ];
    public static SIGNVOFFICE_ERROR = {
        '3': 'File trình kí không đúng định dạng',
        '4': 'Đã tồn tại mã giao dịch này',
        '5': 'Mã giao dịch null',
        '8': 'Danh sách file trình kí null',
        '9': 'Account không đúng',
        '11': 'Lỗi phía WebService',
        '12': 'Thiếu thông tin trình kí',
        '13': 'Account null',
        '14': 'Mã đơn vị null',
        '15': 'Danh sách người ký null',
        '16': 'Mã nhân viên ban hành null',
        '17': 'Lỗi không có thông tin tài khoản Voffice',
        '18': 'Lỗi không truyền tham số tài khoản đăng nhập',
        '20': 'Lỗi ATTT tên file đính kèm',
        '22': 'Lỗi file đính kèm không có dung lượng',
        '28': 'Lỗi không tài khoản Voffice có mail trong danh sách mail trình ký',
        '102': 'Lỗi đăng nhập tài khoản tập trung SSO',
        '103': 'Lỗi trình ký cho văn thư',
        '104': 'Lấy sai đơn vị ban hành',
        '105': 'Lỗi dữ liệu rỗng',
        '106': 'Lỗi file đính kèm không hợp lệ',
        '107': 'Lỗi mail trình ký không tồn tại trên hệ thống Voffice',
        '108': 'Lỗi tiêu đề văn bản quá dài',
        '109': 'Lỗi giải mã mật khẩu',
        '110': 'Lỗi thiếu thông tin đơn vị hoặc Id người ký.',
    } ;

}
export class StatusIssuesService {
    public static IssuesService_STATUS: SelectItem[] = [
        {label: 'Chờ phê duyệt', value: '0'},
        {label: 'Đã Phê duyệt', value: '1'},
        {label: 'Từ chối', value: '2'},
        {label: 'Đang thực hiện', value: '3'},
        {label: 'Hoãn thực hiện', value: '4'},
        {label: 'Không thực hiện được', value: '5'},
        {label: 'Hoàn thành', value: '6'},
        {label: 'Hủy đơn', value: '7'},
        {label: 'Tiếp nhận bàn giao', value: '8'},
        {label: 'Từ chối bàn giao', value: '9'}
    ];

    public static STATUS: string[] = ['Chờ phê duyệt',
        'Đã phê duyệt',
        'Từ chối',
        'Đang thực hiện',
        'Hoãn thực hiện',
        'Không thực hiện được',
        'Hoàn thành',
        'Hủy đơn',
        'Tiếp nhận bàn giao',
        'Từ chối bàn giao' ,
        'Đăng kí mới'];

}