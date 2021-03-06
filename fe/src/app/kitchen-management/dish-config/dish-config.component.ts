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
        this.title.setTitle('C???u h??nh m??n ??n _  PMQTVP');
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
                summary: 'Th??ng b??o',
                detail: 'T??n m??n ??n kh??ng ???????c ????? tr???ng'
            });
            return false;
        } else if (!this.selectedKitchen || !this.selectedKitchen.kitchenID) {
            this.messageService.add({
                severity: 'error',
                summary: 'Th??ng b??o',
                detail: 'B???p kh??ng ???????c ????? tr???ng'
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
            message: "?????ng ch?? c?? mu???n th??m m??n ??n n??y kh??ng",
            header: "Th??m m??n ??n",
            icon: "pi pi-info-circle",
            acceptLabel: 'C??',
            rejectLabel: 'Kh??ng',
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
                            summary: 'Th??ng b??o',
                            detail: 'Th??m m???i th??nh c??ng'
                        });
                    } else if (data["status"] == 2) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Th??ng b??o',
                            detail: 'M??n ??n n??y ???? t???n t???i'
                        });
                    } else if (data["status"] == 3) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Th??ng b??o',
                            detail: '??/c kh??ng c?? quy???n ??? b???p n??y'
                        });
                    } else if (data["status"] == 4) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Th??ng b??o',
                            detail: 'B???p hi???n t???i ???? kh??ng ho???t ?????ng'
                        });
                    } else if (data["status"] == 16) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Th??ng b??o',
                            detail: 'Ch??? cho nh???p 2 k?? t??? ?????c bi???t ; v?? ,'
                        });
                    } else {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Th??ng b??o',
                            detail: '???? c?? l???i x???y ra'
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
            message: "?????ng ch?? c?? mu???n c??p nh???t l???i m??n ??n n??y kh??ng",
            header: "C??p nh???t m??n ??n",
            icon: "pi pi-info-circle",
            acceptLabel: 'C??',
            rejectLabel: 'Kh??ng',
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
                            summary: 'Th??ng b??o',
                            detail: 'C??p nh???t th??nh c??ng'
                        });
                    } else if (dish["status"] == 2) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Th??ng b??o',
                            detail: 'M??n ??n n??y ???? t???n t???i'
                        });
                    } else if (dish["status"] == 3) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Th??ng b??o',
                            detail: '??/c kh??ng c?? quy???n ??? b???p n??y'
                        });
                    } else if (dish["status"] == 0) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Th??ng b??o',
                            detail: '???? c?? l???i x???y ra'
                        });
                    } else if (dish["status"] == 4) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Th??ng b??o',
                            detail: 'B???p hi???n t???i ???? kh??ng ho???t ?????ng'
                        });
                    } else if (dish["status"] == 16) {
                        this.messageService.add({
                            severity: 'error',
                            summary: 'Th??ng b??o',
                            detail: 'Ch??? cho nh???p 2 k?? t??? ?????c bi???t ; v?? ,'
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
                summary: 'Th??ng b??o',
                detail: 'Ch??a ch???n b???ng ghi n??o'
            });
        } else {
            this.confirmationService.confirm({
                message: "?????ng ch?? c?? mu???n x??a m??n ??n n??y kh??ng",
                header: "X??a m??n ??n",
                icon: "pi pi-info-circle",
                acceptLabel: 'C??',
                rejectLabel: 'Kh??ng',
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
                                summary: 'Th??ng b??o',
                                detail: 'X??a m??n ??n th??nh c??ng'
                            });
                        } else if (data["status"] == 3) {
                            this.messageService.add({
                                severity: 'error',
                                summary: 'Th??ng b??o',
                                detail: '??/c kh??ng c?? quy???n x??a m??n thu???c b???p kh??ng c?? th???m quy???n'
                            });
                        } else {
                            this.messageService.add({
                                severity: 'error',
                                summary: 'Th??ng b??o',
                                detail: '???? c?? l???i x???y ra'
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
                if(!this.imageData) this.textImage = 'C?? l???i khi hi???n th??? ???nh';
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
