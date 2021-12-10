
import {HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';



import {AbstractControl, FormArray, FormControl, FormGroup} from '@angular/forms';
import {SelectItem} from "primeng/api";

export class CommonUtils {

    public static CAR_BOOKING_STATUS : SelectItem[] = [
        { label: 'Chờ phê duyệt', value: '0' },
        { label: 'Đã duyệt', value: '1' },
        { label: 'Từ chối ', value: '2' },
        { label: 'Hủy đặt xe', value: '3' },
        { label: 'Đã xếp xe', value: '4' },
        { label: 'Bắt đầu đi', value: '5' },
        { label: 'Hoàn thành', value: '7' },
        { label: 'Từ chối xếp xe', value: '9' },
        { label: 'Chờ gia hạn', value: '10' }
    ];

    public static STATUS : SelectItem[] = [
        { label: 'Chờ phê duyệt', value: '0' },
        { label: 'Đã duyệt', value: '1' },
        { label: 'Từ chối duyệt', value: '2' },
        { label: 'Đã ra', value: '3' },
        { label: 'Đã vào', value: '4' },
        { label: 'Chờ gia hạn', value: '5' },
        { label: 'Từ chối ra', value: '6' },
        { label: 'Từ chối vào', value: '7' },
        { label: 'Vào quá hạn', value: '8' },
        { label: 'Không vào', value: '9' }
    ];

    public static STATUS_LABELS = ['Chờ phê duyệt', 'Đã duyệt', 'Từ chối duyệt', 'Đã ra', 'Đã vào', 'Chờ gia hạn',
    'Từ chối ra', 'Từ chối vào', 'Vào quá hạn', 'Không vào'];

    public static buildParams(obj: any): HttpParams {
        return Object.entries(obj || {})
            .reduce((params, [key, value]) => {
                if (value === null) {
                    return params.set(key, String(''));
                } else if (typeof value === typeof {}) {
                    return params.set(key, JSON.stringify(value));
                } else {
                    return params.set(key, String(value));
                }
            }, new HttpParams());
    }
    /**
     * validateForm
     * @param form: FormGroup
     */




    public static toTreeNode(res: any): any {
        for (const node of res) {
            if (!node.leaf) {
                delete node.icon;
                if (node.children && node.children.length > 0) {
                    node.children = CommonUtils.toTreeNode(node.children);
                }
            }
        }
        return res;
    }



    public static convertFormFile(dataPost: any): FormData {
        const filteredData = CommonUtils.convertData(dataPost);
        const formData = CommonUtils.objectToFormData(filteredData, '', []);
        // console.log(formData);
        return formData;
    }
    /**
     * objectToFormData
     */
    public static objectToFormData(obj, rootName, ignoreList): FormData {
        const formData = new FormData();
        function appendFormData(data, root) {
            if (!ignore(root)) {
                root = root || '';
                if (data instanceof File) {
                    if (data.type !== 'vhr_stored_file') {
                        formData.append(root, data);
                    }
                } else if (Array.isArray(data)) {
                    for (let i = 0; i < data.length; i++) {
                        appendFormData(data[i], root + '[' + i + ']');
                    }
                } else if (data && typeof data === 'object') {
                    for (const key in data) {
                        if (data.hasOwnProperty(key)) {
                            if (root === '') {
                                appendFormData(data[key], key);
                            } else {
                                appendFormData(data[key], root + '.' + key);
                            }
                        }
                    }
                } else {
                    if (data !== null && typeof data !== 'undefined') {
                        formData.append(root, data);
                    }
                }
            }
        }

        function ignore(root) {
            return Array.isArray(ignoreList) && ignoreList.some(function(x) { return x === root; });
        }

        appendFormData(obj, rootName);
        return formData;
    }
    /**
     * convertData
     */
    public static convertData(data: any): any {
        if (typeof data === typeof {}) {
            return CommonUtils.convertDataObject(data);
        } else if (typeof data === typeof []) {
            return CommonUtils.convertDataArray(data);
        } else if (typeof data === typeof true) {
            return CommonUtils.convertBoolean(data);
        }
        return data;
    }
    /**
     * convertDataObject
     * param data
     */
    public static convertDataObject(data: Object): Object {
        if (data) {
            for (const key in data) {
                if (data[key] instanceof File) {

                } else {
                    data[key] = CommonUtils.convertData(data[key]);
                }
            }
        }
        return data;
    }
    public static convertDataArray(data: Array<any>): Array<any> {
        if (data && data.length > 0) {
            for (const i in data) {
                data[i] = CommonUtils.convertData(data[i]);
            }
        }
        return data;
    }
    public static convertBoolean(value: Boolean): number {
        return value ? 1 : 0;
    }
    /**
     * tctGetFileSize
     * param files
     */
    public static tctGetFileSize(files) {
        try {
            let fileSize;
            fileSize = files.size;
            fileSize /= (1024 * 1024); // chuyen ve MB
            return fileSize.toFixed(2);
        } catch (ex) {
            console.error(ex.message);
        }
    }
    /**
     * createForm controls
     */
    public static createForm(formData: any, options: any, validate?: any): FormGroup {
        const formGroup = new FormGroup({});
        for (const property in options) {
            if (formData.hasOwnProperty(property)) {
                options[property][0] = formData[property];
            }
            formGroup.addControl(property, new FormControl(options[property][0], options[property][1]));
        }
        if (validate) {
            formGroup.setValidators(validate);
        }
        return formGroup;
    }

    /** parse string dd/MM/yyyy to date */
    public static stringToDate(value: any): Date | null {
        if ((typeof value === 'string') && (value.indexOf('/') > -1)) {
            const str = value.split('/');

            const year = Number(str[2]);
            const month = Number(str[1]) - 1;
            const date = Number(str[0]);

            return new Date(year, month, date);
        } else if ((typeof value === 'string') && value === '') {
            return new Date();
        }
        const timestamp = typeof value === 'number' ? value : Date.parse(value);
        return isNaN(timestamp) ? null : new Date(timestamp);
    }

    public static dateToString(value:any){
        if (value == null) {
            return '';
        }
        const date = new Date(value);
        return (date.getDate() < 10 ? '0'+date.getDate() : date.getDate()) +'/'
            + ((date.getMonth()+1) < 10 ? '0'+(date.getMonth()+1) : (date.getMonth()+1)) +'/'
            + date.getFullYear();

    }
}
