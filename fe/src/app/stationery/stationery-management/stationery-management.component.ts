import {Component, OnInit, ViewChild, ElementRef, Renderer2, Pipe, PipeTransform} from '@angular/core';
import {StationeryService} from '../stationery.service';
import {Stationery} from '../Entity/Stationery';
import {CallUnit} from '../Entity/CalUnit';
import {ConfirmationService, MessageService} from 'primeng/components/common/api';
import {LimitSpend} from '../Entity/LimitSpend';
import {Table} from 'primeng/table';
import {Meta, Title} from '@angular/platform-browser';
import {Constants} from '../../shared/containts';
import {AutoComplete} from 'primeng/autocomplete';
import {saveAs} from "file-saver";
import {DishConfig} from "../../kitchen-management/Entity/DishConfig";
import {TokenStorage} from "../../shared/token.storage";
import {UserInfo} from "../../shared/UserInfo";
import {AppComponent} from "../../app.component";
import {FileUpload} from "primeng/primeng";
import {Inject, Injectable} from '@angular/core';
import {DOCUMENT} from '@angular/platform-browser';
import {unescape} from 'lodash';

@Pipe({
    name: 'unescape'
})

@Component({
    selector: 'app-stationery-management',
    templateUrl: './stationery-management.component.html',
    styleUrls: ['./stationery-management.component.css']
})
@Injectable()
export class StationeryManagementComponent implements OnInit {
    // tslint:disable-next-line:max-line-length
    onlyChar: RegExp = /^[0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]+$/;

    // tslint:disable-next-line:max-line-length
    notChar: RegExp = /[^0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ()_\-\.]/g;

    @ViewChild("myTable") myTable: Table;
    @ViewChild('inputPrice') private inputPrice: ElementRef;
    @ViewChild('inputLimit') private inputLimit: ElementRef;
    @ViewChild('inputStationeryName') private inputStationeryName: ElementRef;
    @ViewChild('inputUnitCalculate') private inputUnitCalculate: AutoComplete;

    navigationSubscription;
    ElementRef
    stationery: Stationery;
    searchStationery: Stationery;
    calUnit: any;
    listCountStationery: Stationery[];
    selectedDish: Stationery;
    listStationery: Stationery[];
    textImage: string;
    showDlgImage = false;
    listCalculationUnit: CallUnit[];
    imageToShow: any;
    showDialogToInsert = false;
    listFilterCalUnit: any[];
    file: Blob;
    stationeryName: string;
    unitPrice: any;
    notiImportFile: any;
    edit: number;
    isEdit: boolean;
    display: boolean = false;
    limit: LimitSpend;
    limitValue: any;

    stationeryId: string;
    total_record: number;
    pageNumber: number;
    selectedItem: any[] = [];
    pageSize: number;

    loading: boolean;
    showImportForm: boolean = false
    uploadedFiles: any[] = [];

    isAdmin: boolean;
    is_HCVP_VPP: boolean;
    is_QL: boolean;
    is_PMQT_HC_DV: boolean;
    is_NV: boolean;

    imageData: any;
    isShowImageDialog: boolean;
    image: any;
    isShowHideButtonResult: boolean;
    imgName: boolean;
    imgSize: number;
    imgType: string;


    constructor(private stationeryService: StationeryService, private title: Title, private confirmationService: ConfirmationService,
                private messageService: MessageService, private renderer: Renderer2, private tokenService: TokenStorage, private app: AppComponent, @Inject(DOCUMENT) private document: any) {

    }

    ngOnInit() {
        this.stationery = new Stationery(null, null, 0, null, null, null, null, 0);
        this.searchStationery = new Stationery(null, null, 0, null, null, null, null, 0);
        this.stationeryName = null;
        this.edit = 0;
        this.limit = new LimitSpend(null, null);
        this.total_record = 0;
        this.unitPrice = "";
        //this.selectedDish = new Stationery();
        this.title.setTitle('Danh mục văn phòng phẩm - PMQTVP');
        this.getCalcultionUnit();
        this.getLimit();
        this.checkRole();
    }

    onShowImageDialog() {
        this.isShowImageDialog = true;
    }

    onHideImageDialog() {
        this.imageData = null;
        if (document.getElementsByClassName('ui-messages')[0] != null) {
            document.getElementsByClassName('ui-messages')[0].remove();
        }
        if (this.image) {
            const reader = new FileReader();
            reader.readAsDataURL(this.image);
            let component = this;
            reader.onload = function () {
                component.imageData = reader.result;
            };
            // this.image = null;
        }
    }

    getStationeryImage(stationeryItemId: string) {
        let component = this;
        this.stationeryService.getStationeryItemImage(stationeryItemId).subscribe(data => {
            if (data.size > 0) {
                let reader = new FileReader();
                reader.readAsDataURL(data);
                reader.onloadend = function () {
                    component.imageData = reader.result;
                    let blob = component.dataURItoBlob(reader.result);
                    let ext = component.imgType.split("/");
                    component.image = new File([blob], "img_" + stationeryItemId + "." + ext[1], {type: component.imgType});
                    component.imgName =component.image.name;
                    component.imgSize=component.image.size / 1000;
                }
            }
        });

    }

    onSelectImage(event) {
        this.imageData = null;
        this.image = event.files[0];
        this.isShowImageDialog = false;

        //start - truongdv - fix bug 6/8/2019
        let str = this.image.name;

        let ext = str.substring(str.lastIndexOf('.'), str.length);
        if (ext != '.gif' && ext != '.jpe' && ext != '.png' && ext != '.jpg') {
            this.app.showWarn('File không đúng định  dạng.');
            this.image = null;
            this.imageData = null;
            if (document.getElementsByClassName('ui-messages')[0] != null) {
                document.getElementsByClassName('ui-messages')[0].remove();
            }
        }
        if (this.image != null) {
            this.imgName = this.image.name;
            this.imgSize = this.image.size / 1000;
        }
        //end
    }

    // truongdv - add function remove img
    onRemoveImage(event) {
        this.image = null;
        this.imageData = null;
    }

    //
    checkRole() {
        let userInfo: UserInfo = this.tokenService.getUserInfo();
        if (userInfo.role.includes('PMQT_ADMIN')) {
            this.isAdmin = true;
        } else if (userInfo.role.includes('PMQT_HCVP_VPP')) {
            this.is_HCVP_VPP = true;
        } else if (userInfo.role.includes('PMQT_HC_DV')) {
            this.is_PMQT_HC_DV = true;
        } else if (userInfo.role.includes('PMQT_QL')) {
            this.is_QL = true;
        } else if (userInfo.role.includes('PMQT_NV')) {
            this.is_NV = true;
        }
    }

    changePrice2() {
        if (!this.inputPrice) return;
        //  remove all character is not a number and convert price to number
        if (/[^0-9]/g.test(this.inputPrice.nativeElement.value)) {
            this.inputPrice.nativeElement.value = +this.inputPrice.nativeElement.value.toString().replace(/[^0-9]/g, '');
        }
        //  remove all first 0 if number digit > 1
        if (!/^(?!(0))*[1-9][0-9]{0,}$/g.test(this.inputPrice.nativeElement.value.toString())) {
            this.inputPrice.nativeElement.value = this.inputPrice.nativeElement.value !== '' ?
                +this.inputPrice.nativeElement.value.toString().replace(/^0*/g, '') : '';
        }
        if (this.inputPrice.nativeElement.value.length >= 11) {
            this.renderer.setAttribute(this.inputPrice.nativeElement, 'maxlength', '11');
        } else {
            this.renderer.setAttribute(this.inputPrice.nativeElement, 'maxlength', '14');
        }
        if (+this.inputPrice.nativeElement.value > 9000000) {
            this.inputPrice.nativeElement.value = '9000000';
        }
        this.inputPrice.nativeElement.value = this.inputPrice.nativeElement.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        this.unitPrice = this.inputPrice.nativeElement.value;
        this.renderer.removeAttribute(this.inputPrice.nativeElement, 'maxlength');
        return this.inputPrice.nativeElement.value;
    }

    changePrice(basePrice: string) {
        if (!this.inputPrice) return;
        if (basePrice) {
            this.inputPrice.nativeElement.value = +basePrice;
        }

        // remove all character is not a number
        if (/[^0-9]/g.test(this.inputPrice.nativeElement.value)) {
            this.inputPrice.nativeElement.value = this.inputPrice.nativeElement.value.toString().replace(/[^0-9]/g, '');
        }

        if (/^[0]+$/g.test(this.inputPrice.nativeElement.value)) {
            this.inputPrice.nativeElement.value = '0';
        }

        if (/[.]+.0[0]+$/g.test(this.inputPrice.nativeElement.value)) {
            this.inputPrice.nativeElement.value = this.inputPrice.nativeElement.value.toString().replace(/[.]+.0[0]+$/g, '');
        }

        // remove dot of double number if duplicate
        if (this.inputPrice.nativeElement.value.replace(/[^.]/g, '').length > 1) {
            this.inputPrice.nativeElement.value =
                this.removeDuplicateRegexMatchesExceptFirst(this.inputPrice.nativeElement.value.toString(), /[.]/g);
        }

        //  remove all first 0 if number digit > 1
        if (!/^(?!(0))*[1-9][0-9]{0,}$/g.test(this.inputPrice.nativeElement.value)) {
            this.inputPrice.nativeElement.value = this.inputPrice.nativeElement.value !== '0' ?
                this.inputPrice.nativeElement.value.toString().replace(/^0*{0.}/g, '') : '0';
        }
        if (this.inputPrice.nativeElement.value.length >= 12) {
            this.renderer.setAttribute(this.inputPrice.nativeElement, 'maxlength', '12');
        } else {
            this.renderer.setAttribute(this.inputPrice.nativeElement, 'maxlength', '13');
        }
        if (+this.inputPrice.nativeElement.value > 9000000) {
            this.inputPrice.nativeElement.value = '9000000';
        }
        if (+this.inputPrice.nativeElement.value < 1) {
            this.inputPrice.nativeElement.value = '';
        }

        // convert to currency format
        if ((this.inputPrice.nativeElement.value.length > 1
            && this.inputPrice.nativeElement.value.charAt(this.inputPrice.nativeElement.value.length - 1) !== '.') &&
            (this.inputPrice.nativeElement.value
                .substring(this.inputPrice.nativeElement.value.length - 2, this.inputPrice.nativeElement.value.length) !== '.0')) {
            this.inputPrice.nativeElement.value =
                parseFloat(Math.floor(this.inputPrice.nativeElement.value * 100) / 100 + '')
                    .toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});
        } else {
            this.inputPrice.nativeElement.value = this.inputPrice.nativeElement.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        }

        // set to zero in case blank
        if (!this.inputPrice.nativeElement.value || this.inputPrice.nativeElement.value === 'NaN' ||
            this.inputPrice.nativeElement.value.length === 0) {
            this.inputPrice.nativeElement.value = '';
        }

        this.unitPrice = this.inputPrice.nativeElement.value;
        this.renderer.removeAttribute(this.inputPrice.nativeElement, 'maxlength');
        return this.inputPrice.nativeElement.value;
    }

    removeDuplicateRegexMatchesExceptFirst(string, regex) {
        let count = 0;
        const replaceWith = '';
        return string.replace(regex, function (match) {
            count++;
            if (count === 1) {
                return match;
            } else {
                return replaceWith;
            }
        });
    }

    removeBlank() {
        if (this.stationeryName != null) {
            this.stationeryName = this.stationeryName.trim()
        }
    }

    isSelect() {
        if (this.calUnit != null && this.calUnit != undefined) {
            if (this.calUnit.calUnit == null || this.calUnit.calUnit == undefined) {
                this.calUnit = null
            }
        }
    }

    changeLimit(basePrice: string) {
        if (basePrice) {
            this.inputLimit.nativeElement.value = +basePrice;
        }

        // remove all character is not a number
        if (/[.]+.0[0]+$/g.test(this.inputLimit.nativeElement.value)) {
            this.inputLimit.nativeElement.value = this.inputLimit.nativeElement.value.toString().replace(/[.]+.0[0]+$/g, '');
        }

        if (/^[0]+$/g.test(this.inputLimit.nativeElement.value)) {
            this.inputLimit.nativeElement.value = '0';
        }

        if (/^0.0[0]+$/g.test(this.inputLimit.nativeElement.value)) {
            this.inputLimit.nativeElement.value = '0.0';
        }

        // remove dot of double number if duplicate
        if (this.inputLimit.nativeElement.value.replace(/[^.]/g, '').length > 1) {
            this.inputLimit.nativeElement.value =
                this.removeDuplicateRegexMatchesExceptFirst(this.inputLimit.nativeElement.value.toString(), /[.]/g);
        }

        //  remove all first 0 if number digit > 1
        if (!/^(?!(0))*[1-9][0-9]{0,}$/g.test(this.inputLimit.nativeElement.value)) {
            this.inputLimit.nativeElement.value = this.inputLimit.nativeElement.value !== '0' ?
                this.inputLimit.nativeElement.value.toString().replace(/^0*{0.}/g, '') : '0';
        }
        if (this.inputLimit.nativeElement.value.length >= 12) {
            this.renderer.setAttribute(this.inputLimit.nativeElement, 'maxlength', '12');
        } else {
            this.renderer.setAttribute(this.inputLimit.nativeElement, 'maxlength', '13');
        }
        if (+this.inputLimit.nativeElement.value > 9000000) {
            this.inputLimit.nativeElement.value = '9000000';
        }

        // convert to currency format
        if ((this.inputLimit.nativeElement.value.length > 1
            && this.inputLimit.nativeElement.value.charAt(this.inputLimit.nativeElement.value.length - 1) !== '.') &&
            (this.inputLimit.nativeElement.value
                .substring(this.inputLimit.nativeElement.value.length - 2, this.inputLimit.nativeElement.value.length) !== '.0')) {
            this.inputLimit.nativeElement.value =
                parseFloat(Math.floor(this.inputLimit.nativeElement.value * 100) / 100 + '')
                    .toLocaleString('us-US', {minimumFractionDigits: 0, maximumFractionDigits: 2});
        } else {
            this.inputLimit.nativeElement.value = this.inputLimit.nativeElement.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        }

        // set to zero in case blank
        if (!this.inputLimit.nativeElement.value || this.inputLimit.nativeElement.value === 'NaN' ||
            this.inputLimit.nativeElement.value.length === 0) {
            this.inputLimit.nativeElement.value = '';
        }
        this.limit.limitValue = this.inputLimit.nativeElement.value;
        this.renderer.removeAttribute(this.inputLimit.nativeElement, 'maxlength');
        return this.inputLimit.nativeElement.value;
    }

    getStationery() {
        this.stationeryService.getStationery().subscribe(data => {
            this.listStationery = data.data;
            this.total_record = this.listStationery.length;
            if (this.listStationery.length == 0) {
                this.app.errorValidateDate("Không tồn tại bản ghi nào.")
            }
        })
    }

    getCalcultionUnit() {
        this.stationeryService.getCalcultionUnit().subscribe(data => {
            this.listCalculationUnit = data.data;
        })
    }

    checkEmpty(input: string) {
        let i = 0;
        let output: string;
        output = '';
        for (i = 0; i < input.length; i++) {
            if (input.charAt(i) != '') output += input.charAt(i)
        }
        return output;
    }

    filterCalUnit(event): any {
        let temp: any;

        if (this.calUnit == null) {
            temp = new CallUnit(null, null);
        } else {
            if (this.checkEmpty(this.calUnit) == '') temp = new CallUnit(null, null);
            else temp = new CallUnit(null, this.calUnit.trim());
        }
        this.stationeryService.findCalcultionUnit(temp).subscribe(data => {
            this.listFilterCalUnit = data.data;
        })
    }

    handleSearch() {
        this.settingSearch();
        if (this.myTable != undefined)
            this.myTable.reset();
    }

    getListStationery() {
        this.stationeryService.searchCountStationery(this.stationery).subscribe(data => {
            this.listCountStationery = data.data;
            this.total_record = this.listCountStationery.length;
            this.stationeryService.searchStationery(this.stationery).subscribe(data => {
                this.listStationery = data.data;
            });

        });
    }

    settingSearch() {
        if (this.calUnit != null && this.calUnit.calUnit != undefined) {
            this.stationery.calUnit = this.calUnit.calUnit;
        } else {
            this.stationery.calUnit = null;
        }
        this.stationery.pageSize = this.pageSize;
        this.stationery.pageNumber = this.pageNumber;
        this.stationery.stationeryName = this.stationeryName;
        this.stationery.unitPrice = this.unitPrice.replace(/[^0-9.]/g, '');
    }

    getNameObject(suggest: string): any {
        if (suggest == null) return "";
        let split = suggest.split(" - ");
        return split[0];
    }

    onLazyLoad(event) {
        this.selectedItem=[];
        this.stationery.pageNumber = event.first;
        this.pageNumber = event.first;
        this.stationery.pageSize = event.rows;
        this.pageSize = event.rows;
        this.getListStationery();
    }

    validate(stationeryName: string, unitPrice: string, calUnit: CallUnit) {
        if (stationeryName == null || stationeryName.trim().length == 0) {
            this.app.showWarn('Tên văn phòng phẩm không được để trống.');
            this.inputStationeryName.nativeElement.focus();
            return false;
        }
        if (stationeryName.trim().length > 50) {
            this.app.showWarn('Tên VPP không vượt quá 50 kí tự');
            this.inputStationeryName.nativeElement.focus();
            return false;
        }
        if (unitPrice == null || unitPrice === undefined || unitPrice === '') {
            this.app.showWarn('Đơn giá không được để trống.');
            this.inputPrice.nativeElement.focus();
            return false;
        }
        if (!(this.calUnit != null && this.calUnit.calUnit != undefined)) {
            this.app.showWarn('Đơn vị tính không được để trống.');

            this.inputUnitCalculate.domHandler.findSingle(this.inputUnitCalculate.el.nativeElement, 'input').focus();
            return false;
        }
        else {
            return true;
        }
    }

    settingStationery() {
        this.stationery.stationeryName = this.stationeryName.trim();
        this.stationery.unitPrice = this.unitPrice.toString().replace(/[^0-9.]/g, '');
        this.stationery.calUnit = this.calUnit.calUnit;
        if (this.calUnit != null && this.calUnit.calUnit != undefined) {
            let indexCalUnit = this.listCalculationUnit.find(x => x.calUnit.localeCompare(this.calUnit.calUnit) == 0);
            this.stationery.calUnitId = indexCalUnit.calUnitId;
        }
    }

    doEdit(rowData: Stationery) {
        this.stationeryId = rowData.stationeryId;
        this.edit = 1;
        this.isEdit = true;
        this.stationery.stationeryId = rowData.stationeryId;
        this.stationeryName = rowData.stationeryName;
        this.unitPrice = rowData.unitPrice;
        this.calUnit = new CallUnit(null, rowData.calUnit);
        this.unitPrice = this.changePrice(rowData.unitPrice + '');
        this.getStationeryImage(this.stationeryId);
    }

    handleAdd() {
        if (this.validate(this.stationeryName, this.unitPrice, this.calUnit)) {
            this.confirmationService.confirm({
                message: "Đồng chí có muốn Lưu bản ghi này không?",
                header: "Xác nhận Lưu",
                icon: "pi pi-info-circle",
                acceptLabel: 'Đồng ý',
                rejectLabel: 'Hủy bỏ',
                accept: () => {
                    this.settingStationery();
                    if (Number(this.unitPrice) < 1) {
                        this.app.showWarn('Đơn giá được nhập phải là số nguyên dương!');
                        this.inputPrice.nativeElement.focus();
                        return;
                    }
                    if (this.edit == 0) {
                        this.insertData();
                    } else {
                        this.settingStationery();

                        this.stationery.securityUsername = this.stationeryService.getAccount().securityUsername;
                        this.stationery.securityPassword= this.stationeryService.getAccount().securityPassword;
                        let object = {image: this.imageData, stationery: this.stationery};
                        let param = JSON.stringify({image: this.imageData, stationery: this.stationery});

                        this.stationeryService.updateStationery(param).subscribe(x => {
                            if (x["status"] == 1) {
                                this.app.showSuccess('Đã cập nhật thông tin văn phòng phẩm.');
                                //this.stationery=null;
                                this.resetButton();
                            } else if (x["status"] == 0) {
                                this.app.errorValidateDate('Có lỗi không xác định');
                            } else if (x["status"] == 2) {
                                this.app.errorValidateDate('Cập nhật không thành công. Văn phòng phẩm này đã tồn tại!');
                            } else if (x["status"] == Constants.DELETE_UPDATE_ERROR) {
                                this.app.errorValidateDate('Cập nhật không thành công. Văn phòng phẩm này đã bị xóa bởi một người khác.');
                            } else if (x["status"] == 5) {
                                this.app.errorValidateDate('Cập nhật không thành công. Văn phòng phẩm này đã tồn tại!');
                            }
                        this.edit=0;

                        });
                    }

                },
                reject: () => {
                /*    this.edit = 0;
                    this.resetButton();
                    this.isEdit = false;*/
                }
            });
        }
    }

    insertData() {
        this.stationery.securityUsername = this.stationeryService.getAccount().securityUsername;
        this.stationery.securityPassword = this.stationeryService.getAccount().securityPassword;
        let object = {image: this.imageData, stationery: this.stationery};
        let param = JSON.stringify({image: this.imageData, stationery: this.stationery});

        this.stationeryService.insertStationery(object).subscribe(resp => {
            if (resp["status"] == 0) {
                this.app.errorValidateDate('Trùng với văn phòng phẩm đã tồn tại');
            } else if (resp["status"] == 1) {
                this.app.showSuccess('Đã thêm văn phòng phẩm mới.');
                this.resetButton();
            }

        });
    }

    doDelete(rowData) {
        this.confirmationService.confirm({
            message: "Đồng chí có muốn xóa bản ghi này không?",
            header: "Xác nhận xoá",
            icon: "pi pi-info-circle",
            acceptLabel: 'Đồng ý',
            rejectLabel: 'Hủy bỏ',
            accept: () => {
                this.stationery.stationeryId = rowData.stationeryId;
                this.stationeryService.deleteStationery(this.stationery).subscribe(x => {
                    if (x["status"] == 0) {
                        this.app.errorValidateDate('Có lỗi không xác định');
                    } else if (x["status"] == 1) {
                        this.app.showSuccess('Đã xóa văn phòng phẩm.');
                    } else if (x["status"] == 4) {
                        this.app.errorValidateDate('Văn phòng phẩm đang được yêu cầu, không xóa được..');
                    } else if (x["status"] == Constants.DELETE_UPDATE_ERROR) {
                        this.app.errorValidateDate('Xóa không thành công. Văn phòng phẩm này đã bị xóa bởi một người khác.');
                    }
                    this.getStationery();
                    this.monitorReset();
                });
            },
            reject: () => {
            }
        });

    }

    monitorReset() {
        this.image = null;
        this.imageData = null;
        this.stationeryName = "";
        this.unitPrice = "";
        this.stationery.stationeryId = null;
        this.calUnit = null;
        this.isEdit = false;
        this.edit = 0;

        if (this.myTable !== undefined) {
            this.myTable.reset();
        }
    }

    resetButton() {
        this.image = null;
        this.imageData = null;
        this.calUnit = null;
        this.stationeryName = "";
        this.unitPrice = "";
        this.isEdit = false;
        this.handleSearch();
    }

    showDialog() {
        this.getLimit();
        this.display = true;
    }

    close() {
        this.display = false;
        this.getLimit();
    }

    getLimit() {
        this.stationeryService.getLimit().subscribe(data => {
            this.limit = data.data;
            this.limit.limitValue = this.changeLimit(this.limit.limitValue + '');
            this.limitValue = this.limit.limitValue;
        })
    }

    save() {
        this.limit.limitValue = Number(this.limit.limitValue.toString().replace(/[^0-9.]/g, ''));
        this.stationeryService.updateLimit(this.limit).subscribe(data => {
            if (data.status == 1) {
                this.app.showSuccess('Đã cập nhật định mức cho nhân viên.');
            }
        });
        this.display = false;
    }

    onChangeStationeryName() {
        if (this.notChar.test(this.inputStationeryName.nativeElement.value)) {
            this.inputStationeryName.nativeElement.value = this.inputStationeryName.nativeElement.value.replace(this.notChar, '');
        }
    }

    closeImportDialog() {
        this.showImportForm = false;
    }

    openImportExcel() {
        this.showImportForm = true;
    }

    uploadExcel(event: any) {
        this.notiImportFile = null;
        this.isShowHideButtonResult = false;
        var formData = new FormData();
        var file = event.files[0];
        formData.append('file', file);

        this.stationeryService.importService(formData).subscribe((res: Response) => {
            console.log(res);
            var mess = res.headers.get('messageCode');
            if(mess ==='fileEmpty'){
                this.notiImportFile = 'File import không có dữ liệu. Vui lòng kiểm tra lại.';
                this.app.errorValidateDate('File import không có dữ liệu. Vui lòng kiểm tra lại.');
            }else if (mess === 'failFormat') {
                this.notiImportFile = 'Format file import không chính xác. Vui lòng kiểm tra lại.';
                this.app.errorValidateDate('Format file import không chính xác. Vui lòng kiểm tra lại.');
            } else if (mess === 'fail') {
                this.notiImportFile = 'Import không thành công. Tải về file kết quả để kiểm tra lại.';
                this.file = new Blob([res.body], {type: 'application/xlsx'});
               // this.handleSearch();
                this.isShowHideButtonResult = true;
                this.app.errorValidateDate('Import không thành công. Tải về file kết quả để kiểm tra lại.');
            } else if (mess === 'success') {
                this.notiImportFile = 'Đã thêm văn phòng phẩm mới.';
                this.file = new Blob([res.body], {type: 'application/xlsx'});
               // this.handleSearch();
                this.isShowHideButtonResult = true;
                this.app.showSuccess('Đã thêm văn phòng phẩm mới.');
            } else {
                // this.alertMessage('error', 'Cảnh báo', 'Import thất bại');
                this.notiImportFile = 'Import thất bại';
                this.isShowHideButtonResult = true;
              //  this.app.errorValidateDate('Import thất bại.');
            }
            this.resetButton();
            this.loading = false;
        });
    }

    alertMessage(severity: string, summary: string, detail: string) {
        this.messageService.add({
            severity: severity,
            summary: summary,
            detail: detail
        });
    }

    onDeleteItem() {
        if (this.selectedItem.length == 0) {
            this.app.showWarn('Đ/c phải chọn ít nhất 1 bản ghi');
        } else {
            this.confirmationService.confirm({
                message: "Đồng chí có muốn xóa văn phòng phẩm này không",
                header: "Xóa văn phòng phẩm",
                icon: "pi pi-info-circle",
                acceptLabel: 'Có',
                rejectLabel: 'Không',
                accept: () => {
                    this.stationeryService.deleteSelectStationery(this.selectedItem).subscribe(x => {
                        if (x["status"] == 0) {
                            this.app.errorValidateDate('Có lỗi không xác định');
                        } else if (x["status"] == 1) {
                            this.app.showSuccess('Đã xóa văn phòng phẩm.');
                        } else if (x["status"] == 4) {
                            this.app.errorValidateDate('Văn phòng phẩm đang được yêu cầu, không xóa được.');
                        } else if (x["status"] == Constants.DELETE_UPDATE_ERROR) {
                            this.app.errorValidateDate('Xóa không thành công. Văn phòng phẩm này đã bị xóa bởi một người khác.');
                        }
                        this.getStationery();
                        this.monitorReset();
                    });
                },
                reject: () => {
                }
            });
        }
    }

    exportTemplateExcel() {
        this.stationeryService.downloadTemplateImportStationery({}).subscribe(
            (res: Response) => {
                var fileName = 'Import_stationery_template.xlsx';
                const file: Blob = new Blob([res.body], {type: 'application/xlsx'});
                saveAs(file, fileName);
            }
        );
    }

    exportImportResult() {
        var fileName = 'Import_stationery_manangement_template_result.xlsx';
        saveAs(this.file, fileName);
    }

    clearExcel() {
        this.notiImportFile = null;
        this.isShowHideButtonResult = false;
        this.showImportForm= false;
    }

    clearFileExcel() {
        this.notiImportFile = null;
        this.isShowHideButtonResult = false;
    }

    // truongdv

    dataURItoBlob(dataURI: string) {
        let byteString;

        if (dataURI.split(',')[0].indexOf('base64') >= 0)
            byteString = atob(dataURI.split(',')[1]);
        else
            byteString = unescape(dataURI.split(',')[1]);

        let mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
        if (mimeString != null) {
            this.imgType = mimeString;
        }
        let ia = new Uint8Array(byteString.length);
        for (var i = 0; i < byteString.length; i++) {
            ia[i] = byteString.charCodeAt(i);
        }
        return new Blob([ia], {type: mimeString});
    }

    clearImportExcel(){
        this.uploadedFiles = [];
    }
    //

    openDlgImage(type: number, stationery: Stationery) {
        this.getStationeryImage(stationery.stationeryId);
        this.showDlgImage = true;
    }

    closeDlgImage() {
        this.imageData = null;
        this.showDlgImage = false;
    }
}
