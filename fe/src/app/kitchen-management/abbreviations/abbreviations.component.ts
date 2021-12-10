import {Component, OnDestroy, OnInit, Renderer2, ViewChild} from '@angular/core';
import {ConfirmationService, MessageService, TreeNode} from "primeng/api";
import {KitchenManagementService} from "../kitchen-management.service";
import {UserInfo} from "../../shared/UserInfo";
import {TokenStorage} from "../../shared/token.storage";
import {DateTimeUtil} from "../../../common/datetime";

import {CommonUtils} from "../../../common/commonUtils";

import {Title} from "@angular/platform-browser";
import {NavigationEnd, Router} from "@angular/router";
import {Abbreviations} from "../Entity/Abbreviations";
import {FormBuilder, FormGroup} from "@angular/forms";
import {AbbreviationsInsert} from "../Entity/AbbreviationsInsert";
import {ReportserviceService} from "../../check-in-out/reportservice.service";
import {AppComponent} from "../../app.component";
import {Condition} from "../../check-in-out/Entity/ConditionSearch";
import {Table} from "primeng/table";
import {UpdateAbbreviations} from "../Entity/UpdateAbbreviations";
import {SelectingKitchen, SelectingKitchenUnit} from "../Entity/Kitchen";
import {AutoComplete, Dropdown} from "primeng/primeng";
import {AbbreviationsDTO} from "../Entity/AbbreviationsDTO";

@Component({
    selector: 'app-abbreviations',
    templateUrl: './abbreviations.component.html',
    styleUrls: ['./abbreviations.component.css']
})
export class AbbreviationsComponent implements OnInit, OnDestroy {
    onlyCharEmail: RegExp = /^[0-9a-zA-ZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ]+$/;
    onlyNum: RegExp = /^[0-9]+$/;
    @ViewChild('dt')
    @ViewChild('kitchenFld') kitchenFld: Dropdown;
    @ViewChild('dt') dataTableComponent: Table;
    @ViewChild('shortNameFld') private shortNameFld: AutoComplete;

    navigationSubscription;
    condition: Condition;
    isClick: boolean;
    userInfo: UserInfo;
    len: number;
    maxLenStatus: number;
    listParentUnit: string[];
    listSelectedUnitId: any[] = [];
    formSearch: FormGroup;
    kitchenNameListUpdate : SelectingKitchenUnit[];
    fromDate: Date;
    toDate: Date;
    vn = DateTimeUtil.vn;
    status = CommonUtils.STATUS;
    placeShortNameList = new Array();
    total_record : number;
    unitId: number;
    kitchenNameList: SelectingKitchen[];
    unitShorteningList : SelectingKitchenUnit[];
    selectedKitchen: SelectingKitchen;
    filterEmployee: any[];
    sectors: TreeNode[];
    selectedStatus: any[] = [];
    selectedSector: TreeNode[];
    selectedSectorUpdate: TreeNode[];
    sectorUpdates: TreeNode[];
    selectedNode: TreeNode;
    tempNode: TreeNode;
    unitShorteningListNew : SelectingKitchenUnit[];
    //TODO:
    editSectors: TreeNode[];
    editSelectedSector: TreeNode;

    empInfor: any;
    totalRecord: number;
    isChecked: boolean;
    isCheck:boolean;
    imageToShow: any;
    isEmpty: boolean;
    loading: boolean;
    loading2: boolean;
    loadingTree = false;
    convertStatus: string[];
    isShow: boolean;
    isShowManager: boolean;
    isManager: boolean;
    isEmployee: boolean;
    isShowImg: boolean;
    isDisplay: boolean;
    textShow: string;
    shortName : SelectingKitchenUnit ;
    kitchenIdOld : string;
    kichenIdUpdate : string[];
    // AnhLT-IIST-start
    abbreviations: Abbreviations = {
        kitchenId: '',
        listUnit: [null],
        shortName: '',
        startRow : null,
        rowSize : null,
        note : ''
    };

    updateAbbreviations : UpdateAbbreviations ={
        kitchenIdChange : '' ,
        kitchenId : '',
        note: '',
        unitId: null,
        shortName: '',
        createUser: '',
        createDate: '',
        updateUser: '',
        updateDate: '',

    }

    isDialogDetails : boolean;
    resultList:   AbbreviationsDTO[];
    isDialogAddNew: boolean;
    isShowDropdown: boolean;
    isShowTextBox: boolean;
    kitchenId : string;
    kitchenName: any;
    note : string;
    shortNameText : string;
    startRow: number;
    rowSize: number;

    chefKitchens: SelectingKitchen[];
    isChef: boolean;
// AnhLT-IIST-end


// tslint:disable-next-line:max-line-length
    constructor(private formBuilder: FormBuilder , private _KitchenManagementService: KitchenManagementService,
                private confirmationService: ConfirmationService, private messageService: MessageService, private  app : AppComponent ,
                private tokenStorage: TokenStorage, private title: Title, private renderer: Renderer2, private router: Router) {

        this.userInfo = this.tokenStorage.getUserInfo();
        this.navigationSubscription = this.router.events.subscribe(event => {
            if (!(event instanceof NavigationEnd)) { return; }
            this.resetButton();
            if (this.isClick) {
                this.ngOnInit();
            }
        });

        let component = this;
        let callback = () : void => {
            component._KitchenManagementService.findKitchenNameAll().subscribe(res => {
                component.kitchenNameList = res.data;
            });

            component._KitchenManagementService.findUnitShortNameDropdownAll().subscribe(res => {
                component.unitShorteningList = res.data;
            });

            if(component.userInfo.role.includes('PMQT_Bep_truong')) {
                component.isChef = true;
                component._KitchenManagementService.findKitchenNameByUser(component.userInfo.userName).subscribe(res => {
                    component.chefKitchens = res.data;
                });
            }

            component.checkRole();
        }
        component.tokenStorage.getSecurityAccount(callback);
    }

    ngOnInit() {
        this.title.setTitle('Cấu hính tên viết tắt _ PMQTVP');
        this.isShowDropdown = true;
        this.isShowTextBox = false;
        this.buildForm();
        this.len = 50;
        this.isDisplay = false;
        this.textShow = 'Chọn tất cả';
        this.imageToShow = '';
        this.setStatus();
        this.maxLenStatus = 431;
        this.selectedSectorUpdate = [];
        this.total_record = 0;
        this.startRow = -1;
        this.rowSize = 1;
    }

    findNode(sectorUpdates: TreeNode, unitId: any) {
        if (sectorUpdates.data == unitId) {
            this.selectedNode = sectorUpdates;
        }
        for (let i = 0; i < sectorUpdates.children.length; i++) {
            this.findNode(sectorUpdates.children[i], unitId);
        }
    }

    findNode2(sectorUpdates: TreeNode, unitId: any) {
        if (sectorUpdates.data == unitId) {
            this.tempNode = sectorUpdates;
            sectorUpdates.expanded = true;
        }
        for (let i = 0; i < sectorUpdates.children.length; i++) {
            this.findNode2(sectorUpdates.children[i], unitId);
        }
    }

    setExpand(sectorUpdates: TreeNode, node: any) {
        if (node == null) {
            return;
        }
        this.findNode2(sectorUpdates, node);
        this.setExpand(sectorUpdates, this.tempNode.parent);
    }

//  check role user
    checkRole() {
        this.isShow = false;
        this.isShowManager = false;
        this.isShowImg = false;
        this.isEmployee = false;
        this.isManager = false;

        if (this.userInfo.role.includes('PMQT_Canhve')) {
            this.isShow = true;
            this.isShowManager = true;
            this.isShowImg = true;
            this.selectedStatus = ['1', '3'];

            this.getSector(0);
        } else if (this.userInfo.role.includes('PMQT_ADMIN') || this.userInfo.role.includes('PMQT_Bep_truong')) {
            this.isShow = true;
            this.isShowManager = true;
            this.getSector(0);
        } else if (this.userInfo.role.includes('PMQT_QL') || this.userInfo.role.includes('PMQT_CVP')) {
            this.isShow = true;
            this.isShowManager = true;
            this.isManager = true;
            this.getSector(1);
        } else {
            this.empInfor = {
                result: this.userInfo.fullName,
                userName: this.userInfo.userName
            };
            this.isEmployee = true;
            this.getSector(2);
        }
    }

    private   buildForm(): void {
        this.formSearch = this.formBuilder.group({
            kitchenId: [''],
            note: [''],
            unitId: [''],
            shortName : ['']

        });
    }

    settingParams() {
        let shorName = this.shortNameFld.inputEL.nativeElement.value;
        this.abbreviations.kitchenId = this.formSearch.value.kitchenId.kitchenID;
        this.abbreviations.shortName = shorName;
        this.abbreviations.listUnit = this.getSelectedNodeData();
        this.abbreviations.note = this.formSearch.value.note;
    }

    searchData() {
        this.settingParams();
        this.dataTableComponent.reset();
    }


    public onLazyLoad(event) {
        this.abbreviations.startRow = event.first;
        this.abbreviations.rowSize = event.rows;
        this.startRow = event.first;

        let component = this;
        let callback = () : void => {
            component._KitchenManagementService.countTotalMenuService(component.abbreviations).subscribe(res => {
                component.total_record = res.data;
            });

            component._KitchenManagementService.findAbbreviations(component.abbreviations).subscribe(res => {
                if(res.status == 1) {
                    component.resultList = res.data;
                    if (component.resultList) {
                        component.rowSize = component.resultList.length;
                        for (let i = 0; i < component.resultList.length; i++) {
                            const listAbbreviation = component.resultList[i];

                            if(component.isChef && component.chefKitchens && component.chefKitchens.length > 0) {
                                for(let j = 0; j < component.chefKitchens.length; j++) {
                                    if(listAbbreviation.kitchenId === component.chefKitchens[j].kitchenID) {
                                        listAbbreviation.isShowEdit = true;
                                        listAbbreviation.isShowAdmin = false;
                                    }
                                }
                            } else if(component.userInfo.role.includes('PMQT_ADMIN')){
                                listAbbreviation.isShowEdit = false;
                                listAbbreviation.isShowAdmin = true;
                            }
                        }
                    }
                }
                else {
                    component.app.showWarn('Gửi thất bại');
                }
            });
        }
        component.tokenStorage.getSecurityAccount(callback);
    }

//  get list unit
    getSector(type: number) {
        if (type === 0) {
            this._KitchenManagementService.getSectors(null).subscribe(data => {
                this.sectors = data;
                const parentId = [];
                this.sectors.forEach(value => {
                    if (value.data) {
                        parentId.push(value.data);
                    }
                });
                this.listParentUnit = parentId;
                this.searchData();
                // this.sectorUpdates = JSON.parse(JSON.stringify( this.sectors));
            });
        } else if (type === 1) {
            this._KitchenManagementService.getSectors(this.userInfo.unitId as string).subscribe(data => {
                this.sectors = [{
                    label: this.userInfo.unitName as string,
                    data: this.userInfo.unitId,
                    selectable: true,
                    children: data
                }];
                this.listParentUnit = [];
                this.listParentUnit.push(this.userInfo.unitId as string);
                this.searchData();
                // this.sectorUpdates = JSON.parse(JSON.stringify( this.sectors));
            });
        } else if (type === 2) {
            this.sectors = [{
                label: this.userInfo.unitName as string,
                data: this.userInfo.unitId,
                selectable: false
            }];
            this.selectedSector = [this.sectors[0]];
            this.searchData();
            // this.sectorUpdates = JSON.parse(JSON.stringify( this.sectors));
        }

    }

//  expand tree
    nodeExpand(event) {
        this.loadingTree = true;
        if (event.node && event.node.children !== undefined) {
            this.loadingTree = false;
            return;
        }
        if (event.node && event.node.children === undefined) {
            this._KitchenManagementService.getSectors(event.node.data).subscribe(data => {
                event.node.children = data;
                this.loadingTree = false;
            });
        }
    }

//  set up status
    setStatus() {
        this.convertStatus = ['Chờ phê duyệt', 'Đã duyệt', 'Từ chối duyệt', 'Đã ra', 'Đã vào', 'Chờ gia hạn',
            'Từ chối ra', 'Từ chối vào', 'Vào quá hạn', 'Không vào'];
    }

// lai xe
    getShortName(ev) {
        var text = ev.query.trim();
        this._KitchenManagementService.getShortName(text)
          .subscribe(res => {
              this.placeShortNameList = res.data;
          });
    }

    getSelectedNodeData(): any[] {
        this.listSelectedUnitId = [];
        if ((this.isManager || !this.isEmployee) && (this.selectedSector === null || this.selectedSector.length === 0)) {
            return this.listParentUnit;
        } else if (this.isEmployee) {
            this.listSelectedUnitId.push(this.userInfo.unitId);
        } else if (this.selectedSector != null) {
            for (const file of this.selectedSector) {
                if (file.data) {
                    this.listSelectedUnitId.push(file.data);
                }
            }
        }

        return this.listSelectedUnitId.length > 0 ? this.listSelectedUnitId : null;
    }

    setShorteningList() {
        this._KitchenManagementService.findUnitShortNameDropdownAll().subscribe(res => {
            this.unitShorteningList = res.data;
        });
    }
    setKitchenNameList() {
        this._KitchenManagementService.findAllKitchenName().subscribe(res => {


            this.kitchenNameList = res.data;
        });
    }

//  when click 'Lam moi' button
    resetButton() {
        this.selectedStatus = null;
        this.fromDate = new Date();
        this.toDate = new Date();
        this.selectedSector = null;
        this.empInfor = '';
    }

    onGetTreeToCheck(path: string) {
        let vtnetUnit: string = path.split("234841")[1];
        let unitIds = vtnetUnit.split("/");
        this._KitchenManagementService.getSectors(null).subscribe(data => {
            this.editSectors = data;

            this._KitchenManagementService.getSectors(unitIds[1]).subscribe(data => {
                this.editSectors[0].children = data;
                this.editSectors[0].leaf = true;
            });
        });

    }

    doDelete(event){
        const deleteAbbreviations: UpdateAbbreviations = {
            kitchenIdChange : null , kitchenId :event.kitchenId ,note : null ,
            unitId : event.unitId , shortName : '' , createUser : '' , createDate : '' ,
            updateUser : '' , updateDate : ''
        };
        this.confirmationService.confirm({
            message: 'Đ/c có muốn xóa tên viết tắt này không ?',
            header: 'Xóa tên viết tắt',
            icon: 'pi pi-exclamation-triangle',
            acceptLabel: 'Đồng ý',
            rejectLabel: 'Hủy bỏ',
            accept: () => {
                this.deleteHandle(deleteAbbreviations);
            }
        });

    }

    deleteHandle(deleteAbbreviations : UpdateAbbreviations){

        this._KitchenManagementService.deleteAbbreviations(deleteAbbreviations)
          .subscribe(res => {
              if(res.status ==1 ){
                  this.app.showSuccess('Xóa thành công');
              } else  {
                  this.app.showWarn('Xóa thất bại');
              }
              this.searchData();
          });
    }

    doEdit(event) {
        this.isCheck = false;
        this.shortNameText = '';
        this.changeTypeUpdate(false);

        this.isShowDropdown = true;
        if(this.userInfo.role.includes('PMQT_Bep_truong')) {
            this._KitchenManagementService.findKitchenNameByUser(this.userInfo.userName).subscribe(res => {
                this.kitchenNameListUpdate = res.data;
            });
        }else if(this.userInfo.role.includes('PMQT_ADMIN')){
            this._KitchenManagementService.findAllKitchenName().subscribe(res => {
                this.kitchenNameListUpdate = res.data;
            });
        }

        this._KitchenManagementService.findKitchenNameUpdateCheck(event.unitId).subscribe(res => {
            if(res) {
                this.unitShorteningList = res.data;
                for (let i = 0; i < this.unitShorteningList.length; i++) {
                    if (this.unitShorteningList[i].kitchenID === event.kitchenId ) {
                        this.shortName = this.unitShorteningList[i];
                        break;
                    }
                }
            }
        });

        this.onGetTreeToCheck(event.path);
        this.isDialogDetails = true;
        this.note = event.note;
        this.updateAbbreviations.unitId = event.unitId;
        this.selectedSectorUpdate = [];
        this.kitchenIdOld = event.kitchenId;

        this._KitchenManagementService.buildCompleteTree(null).subscribe(res => {
              this.sectorUpdates = res.data;
              this.findNode(this.sectorUpdates[0], event.unitId);
              this.setExpand(this.sectorUpdates[0], this.selectedNode.parent);
              this.selectedSectorUpdate = [this.selectedNode];
          });
        //   this.editAbbreviations(event);
        if(this.isChef) {
            for (let i = 0; i < this.chefKitchens.length; i++) {
                if (this.chefKitchens[i].kitchenID === event.kitchenId) {
                    this.selectedKitchen = this.chefKitchens[i];
                    break;
                }
            }
        } else {
            for (let i = 0; i < this.kitchenNameList.length; i++) {
                if (this.kitchenNameList[i].kitchenID === event.kitchenId) {
                    this.selectedKitchen = this.kitchenNameList[i];
                    break;
                }
            }
        }
    }

    editAbbreviations(abbreviations: Abbreviations) {
        for (let i = 0; i < this.kitchenNameList.length; i++) {
            this.kitchenName = this.kitchenNameList[i];
        }

        for (let i = 0; i < this.unitShorteningList.length; i++) {
            this.shortName = this.unitShorteningList[i];
        }
    }

    private dataToTreeNode(cont: any): TreeNode {
        return {
            label: cont.label,
            data: cont.data,
            children: cont.children,
            selectable: cont.selectable,
            leaf: cont.leaf === null ? true : false
        };
    }

    update(){
        if(this.isCheck){
            this.updateAbbreviations.shortName = this.shortNameText;
        } else {
            this.updateAbbreviations.shortName = this.shortName.shortName;
        }
        this.updateAbbreviations.kitchenId = this.kitchenIdOld;
        this.updateAbbreviations.kitchenIdChange = this.selectedKitchen.kitchenID ;
        if(this.updateAbbreviations.kitchenId == this.updateAbbreviations.kitchenIdChange){
            this.updateAbbreviations.kitchenIdChange = '';
        }
        this.updateAbbreviations.note = this.note;
        this.updateAbbreviations.updateUser = this.userInfo.userName;

        if(!this.updateAbbreviations.shortName || this.updateAbbreviations.shortName.length === 0) {
            this.app.showWarn('Tên viết tắt không được để trống');
            return;
        }

        this._KitchenManagementService.updateAbbreviations(this.updateAbbreviations).subscribe(res => {
            if(res.status === 1 ) {
                this.app.showSuccess('Cập nhật thành công');
                this.isDialogDetails = false;
                this.searchData();
            } else if(res.status === 5 ) {
                this.app.showWarn('Một đơn vị ứng với một bếp chỉ có một tên viết tắt');
            } else if(res.status === 2) {
                this.app.showWarn('Tên viết tắt này đã tồn tại');
            } else {
                this.app.showWarn('Gửi thất bại');
            }
        });
    }
    changeTypeUpdate(isChecked: boolean) {
        if (isChecked) {
            this.isShowTextBox = true;
            this.isShowDropdown = false;
        } else {
            this.isShowDropdown = true;
            this.isShowTextBox = false;
        }
    }

    changeType(isChecked: boolean) {

        if (isChecked) {
            this.isShowTextBox = true;
            this.isShowDropdown = false;
        } else {
            this.isShowDropdown = true;
            this.isShowTextBox = false;
        }
        this.formSearch.value.shortName = null;

    }

    add(){
        this.router.navigate(['/kitchenManager/add-new-abbreviations']);
    }

    ngOnDestroy() {
        // avoid memory leaks here by cleaning up after ourselves. If we
        // don't then we will continue to run our initialiseInvites()
        // method on every navigationEnd event.
        if (this.navigationSubscription) {
            this.navigationSubscription.unsubscribe();
        }
    }
}
