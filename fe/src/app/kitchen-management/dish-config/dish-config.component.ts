import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {KitchenManagementService} from '../kitchen-management.service';
import {Dish, DishConfig} from '../Entity/DishConfig';
import {Kitchen} from '../Entity/Kitchen';
import {AutoComplete, Dropdown} from 'primeng/primeng';
import {Table} from 'primeng/table';
import {isNullOrUndefined} from 'util';
import { FileUploadModule, FileUpload } from 'primeng/components/fileupload/fileupload';
import {ConfirmationService, MessageService} from 'primeng/api';
import { Constants } from '../../shared/containts';
import {Title} from "@angular/platform-browser";
import {TokenStorage} from "../../shared/token.storage";
@Component({
    selector: 'app-dish-config',
    templateUrl: './dish-config.component.html',
    styleUrls: ['./dish-config.component.css']
})
export class DishConfigComponent implements OnInit {
    @ViewChild('myTable') private myTable: Table;
    @ViewChild('inputRole') private inputRole: Dropdown;
    @ViewChild('fileUpload') private fileUpload: FileUpload;
    @ViewChild('searchDish') searchDish: AutoComplete;

    listDish: DishConfig[];
    searchingConfig: { pageNumber: number, pageSize: number, status: number, dishName: string, loadKitchen: boolean, kitchenID: string };
    kitchens: Kitchen[];
    selectedKitchen: Kitchen;
    selectedDish: DishConfig;
    totalRecords = 0;
    deleteDishIds: string[];
    canActive: boolean;
    listActivity: string[];
    isActive: boolean;
    totalRecord: number;
    startRow: number;
    rowSize: number;
    showDlgImage = false;
    image: any;
    showDialogToInsert = false;
    imageToShow: any;
    imageData: any;

    dishNames: string[];
    selectedDishName: string;
    isCheckAll: boolean;

    isSave: boolean;
    isCreate: boolean;
    textImage: string;

    constructor(private kitchenManagementService: KitchenManagementService,
                private messageService: MessageService, private title: Title,
                private confirmationService: ConfirmationService, private tokenStorage: TokenStorage) {
        this.searchingConfig = { pageNumber: 0, pageSize: 10, status: 1, dishName: null, loadKitchen: true, kitchenID: null };
        this.deleteDishIds = [];
        this.isActive = true;
    }

    ngOnInit() {
        this.getListDish();
        this.listActivity = [];
        this.selectedKitchen = new Kitchen();
        this.startRow = 0;
        this.rowSize = 1;
        this.totalRecord = 0;
        this.selectedDish = new DishConfig();
        this.title.setTitle('Cấu hình món ăn _  PMQTVP');
        this.isCreate = true;
    }

    onSearchShortName(event) {
        this.kitchenManagementService.dishAutocomplete(event.query).subscribe(response => {
            this.dishNames = response.data;
        });
    }

    validateKitchen(): boolean {
        let dishName:  string = this.searchDish.inputEL.nativeElement.value;
        if(!dishName || dishName.length === 0) {
            this.searchDish.domHandler.findSingle(this.searchDish.el.nativeElement, 'input').focus();
            this.messageService.add({
                severity: 'error',
                summary: 'Thông báo',
                detail: 'Tên món ăn không được để trống'
            });
            return false;
        } else if (!this.selectedKitchen || !this.selectedKitchen.kitchenID) {
            this.messageService.add({
                severity: 'error',
                summary: 'Thông báo',
                detail: 'Bếp không được để trống'
            });
            return false;
        }
        return true;
    }

    onInsertDishConfig() {
        if (this.validateKitchen() === false) {
            return;
        }

        this.confirmationService.confirm({
            message: "Đồng chí có muốn thêm món ăn này không",
            header: "Thêm món ăn",
            icon: "pi pi-info-circle",
            acceptLabel: 'Có',
            rejectLabel: 'Không',
            accept: () => {
                let dishName:  string = this.searchDish.inputEL.nativeElement.value;
                const dish: DishConfig = {
                    dishID: null, dishName: dishName, kitchenID: this.selectedKitchen.kitchenID,
                    kitchenName: null, status: this.isActive ?  1 : 0  , deleteFG: 0, listDishID: null, image: null,
                    securityUsername: this.tokenStorage.getAccount().securityUsername,
                    securityPassword: this.tokenStorage.getAccount().securityPassword
                };

                var param = JSON.stringify({action: 1, data: dish});
                var formData = new FormData();
                formData.append('strParam', param);
                formData.append('image', this.image);

                this.kitchenManagementService.updateDishConfig(formData).subscribe(data => {
                    if (data["status"] == 1) {
                        this.refreshForm();
                        this.onSearchListDish();
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Thông báo',
                            detail: 'Thêm mới thành công'
                        });
                    } else if (data["status"] == 2) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Thông báo',
                            detail: 'Món ăn này đã tồn tại'
                        });
                    } else if (data["status"] == 3) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Thông báo',
                            detail: 'Đ/c không có quyền ở bếp này'
                        });
                    } else if (data["status"] == 4) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Thông báo',
                            detail: 'Bếp hiện tại đã không hoạt động'
                        });
                    } else if (data["status"] == 16) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Thông báo',
                            detail: 'Chỉ cho nhập 2 ký tự đặc biệt ; và ,'
                        });
                    } else {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Thông báo',
                            detail: 'đã có lỗi xảy ra'
                        });
                    }
                });
            },
            reject: () => {
            }
        });

    }

    onUpdateDishConfig() {
        if (this.validateKitchen() === false) {
            return;
        }

        this.confirmationService.confirm({
            message: "Đồng chí có muốn câp nhật lại món ăn này không",
            header: "Câp nhật món ăn",
            icon: "pi pi-info-circle",
            acceptLabel: 'Có',
            rejectLabel: 'Không',
            accept: () => {
                let dishName:  string = this.searchDish.inputEL.nativeElement.value;
                let dishConfig: DishConfig = new DishConfig();

                dishConfig.dishName = dishName;
                dishConfig.kitchenID = this.selectedKitchen.kitchenID;
                dishConfig.kitchenName = this.selectedKitchen.kitchenName;
                dishConfig.status = this.isActive ?  1 : 0 ;
                dishConfig.dishID = this.selectedDish.dishID;
                dishConfig.securityUsername = this.tokenStorage.getAccount().securityUsername;
                dishConfig.securityPassword = this.tokenStorage.getAccount().securityPassword;


                var param = JSON.stringify({action: 2, data: dishConfig});
                var formData = new FormData();
                formData.append('strParam', param);
                formData.append('image', this.image);

                this.kitchenManagementService.updateDishConfig(formData).subscribe(dish => {
                    if (dish["status"] == 1) {
                        this.refreshForm();
                        this.onSearchListDish();
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Thông báo',
                            detail: 'Câp nhật thành công'
                        });
                    } else if (dish["status"] == 2) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Thông báo',
                            detail: 'Món ăn này đã tồn tại'
                        });
                    } else if (dish["status"] == 3) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Thông báo',
                            detail: 'Đ/c không có quyền ở bếp này'
                        });
                    } else if (dish["status"] == 0) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Thông báo',
                            detail: 'Đã có lỗi xảy ra'
                        });
                    } else if (dish["status"] == 4) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Thông báo',
                            detail: 'Bếp hiện tại đã không hoạt động'
                        });
                    } else if (dish["status"] == 16) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Thông báo',
                            detail: 'Chỉ cho nhập 2 ký tự đặc biệt ; và ,'
                        });
                    }
                });
            },
            reject: () => {
            }
        });
    }

    onDeleteDish() {
        if (this.listActivity.length == 0) {
            this.messageService.add({
                severity: 'error',
                summary: 'Thông báo',
                detail: 'Chưa chọn bảng ghi nào'
            });
        } else {
            this.confirmationService.confirm({
                message: "Đồng chí có muốn xóa món ăn này không",
                header: "Xóa món ăn",
                icon: "pi pi-info-circle",
                acceptLabel: 'Có',
                rejectLabel: 'Không',
                accept: () => {
                    const dish: DishConfig = {
                        dishID: null, dishName: null, kitchenID: null,
                        kitchenName: null, status: 1, deleteFG: 0, listDishID: this.listActivity, image: null,
						securityUsername: this.tokenStorage.getAccount().securityUsername,
						securityPassword: this.tokenStorage.getAccount().securityPassword
                    };

                    var param = JSON.stringify({action: 3, data: dish});
                    var formData = new FormData();
                    formData.append('strParam', param);
                    formData.append('image', this.image);

                    this.kitchenManagementService.updateDishConfig(formData).subscribe(data => {
                        if (data["status"] == 1) {
                            this.refreshForm();
                            this.onSearchListDish();
                            this.messageService.add({
                                severity: 'success',
                                summary: 'Thông báo',
                                detail: 'Xóa món ăn thành công'
                            });
                        } else if (data["status"] == 3) {
                            this.messageService.add({
                                severity: 'error',
                                summary: 'Thông báo',
                                detail: 'Đ/c không có quyền xóa món thuộc bếp không có thẩm quyền'
                            });
                        } else {
                            this.messageService.add({
                                severity: 'error',
                                summary: 'Thông báo',
                                detail: 'đã có lỗi xảy ra'
                            });
                        }
                    });
                },
                reject: () => {
                }
            });
        }
    }

    selectRow(dish: DishConfig) {
        this.isCreate = false;
        this.isSave = true;
        this.selectedDish = dish;
        if (this.fileUpload != null) {
            this.fileUpload.clear();
        }
        const kitchen: Kitchen = this.getKitchenById(this.selectedDish.kitchenID);
        this.selectedKitchen = kitchen;
        this.isActive = !!+ this.selectedDish.status  ;
        this.selectedDishName = dish.dishName;
        this.getDishImage(dish.dishID);
    }

    getDishImage(dishId: any) {
        this.kitchenManagementService.getDishImage(dishId).subscribe(data => {
            this.imageData = null;
            this.createImageFromBlob(data);
        });
    }

    createImageFromBlob(image: any) {
        let reader = new FileReader();
        reader.addEventListener('load', () => {
            this.imageToShow = reader.result;
            if (this.imageToShow) {
                this.imageData = this.imageToShow.toString().split(',')[1];
                if(!this.imageData) this.textImage = 'Có lỗi khi hiển thị ảnh';
            }
        }, false);

        if (image) {
            reader.readAsDataURL(image);
        }
    }

    selectAll() {
        if (this.listDish) {
            for (let i = 0; i < this.listDish.length; i++) {
                const dish: DishConfig = this.listDish[i];
                this.deleteDishIds.push(dish.dishID);
            }
        }
    }

    onSearchListDish() {
        this.isSave = false;
        this.isCreate = true;
        this.myTable.reset();
        let dishName:  string = this.searchDish.inputEL.nativeElement.value;
        this.searchingConfig.dishName = dishName;
        this.searchingConfig.loadKitchen = false;
        this.searchingConfig.status = this.isActive ?  1 : 0 ;
        this.searchingConfig.pageNumber = 0 ;
        this.searchingConfig.pageSize = 10 ;
        this.startRow = 0;
        this.rowSize = 1;
        if (this.selectedKitchen) {
            this.searchingConfig.kitchenID = this.selectedKitchen.kitchenID;
        }
        this.getListDish();
    }

    getListDish() {
        let component = this;
        let callback = () : void => {
            component.isCheckAll = false;
            component.listActivity = [];
            component.kitchenManagementService.getListDish(component.searchingConfig).subscribe(dish => {
                component.listDish = dish.data.dish;
                if (dish.data.kitchens) {
                    component.kitchens = dish.data.kitchens;
                }
                component.totalRecords = dish.data.totalDish;
                component.rowSize = dish.data.dish.length;
            });
        }
        this.tokenStorage.getSecurityAccount(callback);
    }

    paginate(event) {
        this.isCheckAll = false;
        this.searchingConfig.pageNumber = event.first;
        this.startRow = event.first;
        this.getListDish();
    }

    getKitchenById(kitchenId: string) {
        let selectKitchen: Kitchen = null;
        if (this.kitchens) {
            this.kitchens.forEach(function (kitchen: Kitchen) {
                if (kitchen.kitchenID === kitchenId) {
                    selectKitchen = kitchen;
                }
            });
        }
        return selectKitchen;
    }

    refreshForm() {
        this.searchDish.inputEL.nativeElement.value = null;
        this.selectedDishName = null;
        this.isCreate = true;
        this.isSave = false;
    }

    checkAll(isChecked: boolean) {
        if (!this.listDish || this.listDish.length == 0) {
            return;
        }

        this.listActivity = [];
        if (isChecked) {
            for (let i = 0; i < this.listDish.length; i++) {
                const booking = this.listDish[i];
                this.listActivity.push(booking.dishID);
            }
        }
    }

    checkItem(isChecked: boolean) {
        if(isChecked) {
            if(this.listActivity.length === this.listDish.length) this.isCheckAll = true;
        } else {
            this.isCheckAll = false;
        }
    }

    openDlgImage(type: number, dish: DishConfig) {
        if (type === 1) {
            this.showDialogToInsert = true;
        } else {
            this.showDialogToInsert = false;
        }
        if (dish != null) {
            this.imageData = null;
            this.selectedDish = dish;
            this.textImage = 'loading...';
            this.getDishImage(dish.dishID);
        } else {
            this.image = null;
            this.imageData = null;
        }
        this.showDlgImage = true;
    }

    closeDlgImage() {
        this.showDlgImage = false;
        this.textImage = null;
    }

    uploadImage(event: any) {
        this.image = event.files[0];
        this.showDlgImage = false;
    }
}
