
import {SelectItem} from "primeng/api";

export class DriverStatus {

    public static DRIVER_STATUS_LABELS: string[] = ['Lái xe mới', '', '', '', 'Đã xếp xe', 'Bắt đầu đi', 'Đến nơi', 'Về vị trí'];

    public static CAR_STATUS_LABELS: string[] = ['Xe mới', '', '', '', 'Đã xếp xe', 'Bắt đầu đi', 'Đến nơi', 'Về vị trí'];


    public static DRIVER_STATUS: SelectItem[] = [
        { label: 'Lái xe mới', value: '0' },
        { label: 'Đã xếp xe', value: '4' },
        { label: 'Bắt đầu đi', value: '5' },
        { label: 'Đến nơi', value: '6' },
        { label: 'Hoàn thành', value: '7' }
    ];

    public static DRIVER_FREE_STATUS: SelectItem[] = [
        { label: 'Lái xe rỗi', value: '0' },
        { label: 'Đã xếp xe', value: '4' },
        { label: 'Đang khởi hành', value: '5' }
    ];


    public static CAR_STATUS: SelectItem[] = [
        { label: 'Xe mới', value: '0' },
        { label: 'Đã xếp xe', value: '4' },
        { label: 'Bắt đầu đi', value: '5' },
        { label: 'Đến nơi', value: '6' },
        { label: 'Hoàn thành', value: '7' }
    ];

    public static CAR_FREE_STATUS: SelectItem[] = [
        { label: 'Xe rỗi', value: '0' },
        { label: 'Đã xếp xe', value: '4' },
        { label: 'Đang khởi hành', value: '5' }
    ];
}
