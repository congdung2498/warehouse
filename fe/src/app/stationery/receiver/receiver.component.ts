import {Component, OnInit, ViewChild} from '@angular/core';
import {Receiver} from '../Entity/Receiver';
import {Unit} from '../Entity/Unit';
import {Place} from '../Entity/Place';
import {Role} from '../Entity/Role';
import {StationeryService} from '../stationery.service';
import {ConfirmationService, MessageService, TreeNode} from 'primeng/components/common/api';
import {Employee} from '../Entity/Employee';
import {DriveSquad} from '../../bookcar/Entity/DriveSquad';
import {NULL_INJECTOR} from '@angular/core/src/render3/component';
import {Title} from '@angular/platform-browser';
import {Constants} from '../../shared/containts';
import {Table} from 'primeng/table';
import {AutoComplete} from 'primeng/autocomplete';
import {Dropdown} from 'primeng/primeng';
import {takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";
import {KitchenManagementService} from "../../kitchen-management/kitchen-management.service";
import {UserInfo} from "../../shared/UserInfo";
import {TokenStorage} from "../../shared/token.storage";
import {AppComponent} from "../../app.component";


@Component({
    selector: 'app-receiver',
    templateUrl: './receiver.component.html',
    styleUrls: ['./receiver.component.css']
})
export class ReceiverComponent implements OnInit {
    @ViewChild("myTable")
    myTable: Table;
    @ViewChild('inputPlace') private inputPlace: AutoComplete;
    @ViewChild('inputUnit') private inputUnit: AutoComplete;
    @ViewChild('inputRole') private inputRole: Dropdown;
    @ViewChild('inputEmployee') private inputEmployee: AutoComplete;

    employee: any;
    receiver: Receiver;
    unit: any;
    showTree : boolean;
    unitEmployee: any;
    place: any;
    role: Role;
    editSectors: TreeNode[];
    selectedNode: TreeNode;
    tempNode: TreeNode;
// mark that this component destroyed
    private destroy$: Subject<boolean> = new Subject<boolean>();
    listUnitId: any[] = [];
    listSelectedUnitId: any[] = [];
    listParentUnit: string[];
    listFilterEmployee: any[];
    userInfo: UserInfo;
    listFilterUnit: any[];
    listFilterPlace: any[];
    listReceiver: Receiver[];
    listRole: any[];
    isTCT: boolean;
    edit: number;
    total_record: number;
    pageNumber: number = 0;
    pageSize: number = 10;
    isEdit: boolean;
    sectors: TreeNode[];
    loadingTree = false;
    selectedSector: TreeNode[];
    selectionMode:string = "checkbox";
    disableRole = false;
    cols = [
        {field: "", header: "STT", width: "10%"},
        {field: "", header: "Thao Tác", width: "10%"},
        {field: "teamName", header: "Vị Trí", width: "15%"},
        {field: "full_name", header: "Đơn Vị", width: "20%"},
        {field: "full_name", header: "Tên Nhân Viên", width: "15%"},
        {field: "full_name", header: "Số Điện Thoại", width: "15%"},
        {field: "full_name", header: "Công Việc", width: "15%"}
    ];

    lstPlace: any[];
    lstSgsPlace:any[];
    lstSelectedPlace: any[];


    constructor(private stationeryService: StationeryService, private title: Title, private confirmationService: ConfirmationService, private messageService: MessageService,
                private _KitchenManagementService: KitchenManagementService , private tokenStorage: TokenStorage ,private  app: AppComponent ) {
        this.getReceiver();
        this.getRole();
    }

    ngOnInit() {
        this.showTree = true;
        this.userInfo = this.tokenStorage.getUserInfo();
        this.isTCT = false;
        this.role = new Role("", null, "");
        this.receiver = new Receiver(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,null,null);
        this.edit = 0;
        this.total_record = 0;
        this.isEdit = false;
        this.title.setTitle('Danh mục người tiếp nhận - PMQTVP');
        this.lstPlace = [];
        this.lstSgsPlace = []

        this.stationeryService.findAllPlace().subscribe(x => {
            this.lstSgsPlace = x["data"];
        })
        this.lstPlace.push(null);
        this.lstSelectedPlace = [];
        this.stationeryService.getSectors(null).pipe(takeUntil(this.destroy$)).subscribe(data => {
            this.sectors = data;
        });

        if (this.userInfo.role.includes('PMQT_Canhve')) {
            this.getSector(0);
        } else if (this.userInfo.role.includes('PMQT_ADMIN')|| this.userInfo.role.includes('PMQT_Bep_truong')) {
            this.getSector(0);
        } else if (this.userInfo.role.includes('PMQT_QL') || this.userInfo.role.includes('PMQT_CVP')) {

            this.getSector(0);
        } else {
            this.getSector(0);
        }

    }

    getReceiver() {
        this.stationeryService.getReceiver().subscribe(data => {
            this.listReceiver = data.data;
            this.total_record = this.listReceiver.length;
        })
    }

    getRole() {
        this.stationeryService.getRole().subscribe(
            data => {
                this.listRole = data.data;
            }
        )
    }

    paginate(event) {
        this.pageNumber = event.first;
    }

    handleSearch() {
        if (this.myTable != undefined) this.myTable.reset();
        this.settingSearch();
        this.stationeryService.searchReceiver(this.receiver).subscribe(data => {
            this.listReceiver = data.data;
            this.total_record = this.listReceiver.length;
            this.pageNumber = 0;
        })
    }


    settingSearch() {
        this.receiver.unitId = this.getLastItem();
        this.receiver.listUnit = this.getSelectedNodeData();

        if (this.unit != null && this.unit.unitName != undefined) {
        } else {
            if (this.unit == '') this.unit = null;
            this.receiver.unit = this.unit;
        }

        if (this.employee != null && this.employee.fullName != undefined) {
            this.receiver.fullName = this.employee.fullName
        } else {
            if(this.employee!==null && this.employee!==undefined){
                this.receiver.fullName = this.employee.trim();
            }else
            this.receiver.fullName = this.employee;
        }

        this.receiver.roleName = this.role.roleName;
        this.receiver.roleId = this.role.roleId;

        this.receiver.lstPlaceId = [];
        for (let i = 0; i < this.lstSelectedPlace.length; i++) {
            if (this.lstSelectedPlace[i] != null) {
                this.receiver.lstPlaceId.push(this.lstSelectedPlace[i].placeId);
            }
        }
        if (this.receiver.lstPlaceId.length === 0) {
            this.receiver.lstPlaceId = null;
        }

    }

    employeeSelect(event) {
        this.employee = new Employee(event.employeeId, this.getNameObject(event.fullName), event.selectUserName, event.employeePhone, event.role, event.place, event.unit);
    }

    filterEmployee(event): any {
        this.unitEmployee = this.getSelectedNodeData();
        let temp: any;
        if (this.role.roleName == null || this.unitEmployee == null) {
            if (this.isTCT == false) {
                this.listFilterEmployee = [];
                return;
            }
        }
        if (this.unit == '') {
            this.listFilterEmployee = [];
            return;
        }

        if (this.employee == null) {
            temp = new Employee(null, null, null, null, this.role.jobCode, null, null);
        } else {
            if (this.checkEmpty(this.employee) == '') {
                temp = new Employee(null, null, null, null, this.role.jobCode, null, null);
            }
            else {
                temp = new Employee(null, this.employee.trim(), this.employee.trim(), null, this.role.jobCode, null, null);
            }
        }

        if (this.unitEmployee != null && this.unitEmployee.length != 0) {
            temp.listUnit = this.unitEmployee;
        }


        this.stationeryService.findEmployees(temp).subscribe(
            data => {
                this.listFilterEmployee = data.data;
                // if (data.status == 1) {
                //   this.listFilterEmployee.forEach(x => {
                //     x.fullName = x.fullName + " - " + x.employeePhone + " - " + x.unit;
                //   });
                // }
            }
        );
    }

    setRole() {
        this.employee = null;
        if (this.role == null || this.role == undefined) {
            this.employee = null;
            this.role = new Role(null, null, '');
            this.isTCT = false;
            this.showTree = true;
        } else {
            if (this.role.roleName == 'Nhân viên VP TCT') {
                this.selectionMode = "single";
                this.isTCT = true;
                this.unit = null;
                this.unitEmployee = null;
                //this.showTree = false;
            }
            else  {
                this.selectionMode = "checkbox";
                //this.showTree = true;
                this.isTCT = false;
              ///  this.getSector(0);
            }

        }
    }

    filterUnit(event) {
        let temp: any;
        this.employee = null;
        if (this.unit == null) {
            temp = new Unit(null, null, null);
        } else {
            if (this.checkEmpty(this.unit) == '') {
                temp = new Unit(null, null, null);
                this.unit = null;
            }
            else {
                temp = new Unit(null, this.unit.trim(), null);
            }
        }
        this.stationeryService.findUnit(temp).subscribe(
            data => {
                this.listFilterUnit = data.data;
                this.listFilterUnit.forEach(element => {
                    element.twoLevelUnit = element.twoLevelUnit;
                });
            }
        )
    }

    filterPlace(event, index: string) {
        let place = new Place(null, event.query);
        let lstPlaceId = [];
        for (let i = 0; i < this.lstSelectedPlace.length; i++) {
            if (this.lstSelectedPlace[i] != null) {
                lstPlaceId.push(this.lstSelectedPlace[i].placeId);
            }
        }
        place.lstPlaceId = lstPlaceId;

        this.stationeryService.findPlace(place).subscribe(
            data => {
                this.listFilterPlace = data.data;
            }
        )
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


    getNameObject(suggest: string): any {
        if (suggest == null) return "";
        let split = suggest.split(" - ");
        return split[0];
    }

    validate(place: Place, unit: any, role: string, employee: Employee) {
        if(this.role.roleName == 'Nhân viên HC đơn vị'){
            if(unit== null || unit.length == 0){
                this.app.showWarn("Đơn vị không được để trống")
                return false;
            }
            if(unit== null || unit.length > 1){
                this.app.showWarn("Chỉ được chọn 1 đơn vị")
                return false;
            }
        }

        if (this.lstSelectedPlace.length == 0 || this.lstSelectedPlace[0] == null) {
            this.app.showWarn("Vị trí không được để trống.")
            this.inputPlace.domHandler.findSingle(this.inputPlace.el.nativeElement, 'input').focus();
            return false;
        }

        // if (this.place == null) {
        //
        // } else if (this.place.placeName == undefined) {
        //   this.messageService.add({
        //     severity: "error",
        //     summary: "Thông báo",
        //     detail: "Vị trí không tồn tại."
        //   });
        //   this.inputPlace.domHandler.findSingle(this.inputPlace.el.nativeElement, 'input').focus();
        //   return false;
        // }

        // if (this.role.roleName != 'Nhân viên VP TCT') {
        //     if (this.unit == null) {
        //         this.messageService.add({
        //             severity: "error",
        //             summary: "Thông báo",
        //             detail: "Đơn vị không được để trống."
        //         });
        //         this.inputUnit.domHandler.findSingle(this.inputUnit.el.nativeElement, 'input').focus();
        //         return false;
        //     }
        // }

        if (role == null || role.trim().length == 0) {
            this.app.showWarn("Vai trò không được để trống.")
            this.inputRole.focus();
            return false;
        }
        if (!(this.employee != null && this.employee.fullName != undefined)) {
            this.app.showWarn("Tên nhân viên không được để trống.")
            this.inputEmployee.domHandler.findSingle(this.inputEmployee.el.nativeElement, 'input').focus();
            return false;
        }

        else {
            return true;
        }
    }


    settingReceiver() {
        this.receiver.employeeId = this.employee.employeeId;
        if (this.role.roleName == 'Nhân viên VP TCT') this.receiver.unitId = this.employee.unitId;
        else this.receiver.unitId = this.getSelectedNodeData()[0];
        this.receiver.userName = this.employee.userName;
        this.receiver.fullName = this.employee.fullName;
        this.receiver.employeePhone = this.employee.employeePhone;
        this.receiver.roleId = this.role.jobCode;

        this.receiver.lstPlaceId = [];
        for (let i = 0; i < this.lstSelectedPlace.length; i++) {
            if (this.lstSelectedPlace[i] != null) {
                this.receiver.lstPlaceId.push(this.lstSelectedPlace[i].placeId);
            }
        }
        if (this.receiver.lstPlaceId.length === 0) {
            this.receiver.lstPlaceId = null;
        }
    }


    handleAdd() {
        if ( this.validate( this.place, this.getSelectedNodeData(), this.role.roleName, this.employee ) ) {
            this.confirmationService.confirm({
                message: "Đồng chí có muốn Lưu bản ghi này không?",
                header: "Xác nhận Lưu",
                icon: "pi pi-info-circle",
                acceptLabel: 'Đồng ý',
                rejectLabel: 'Hủy bỏ',
                accept: () => {
                    this.settingReceiver();
                    if (this.edit === 0) {
                        this.insertData();
                    } else {
                        this.stationeryService.updateReceiver(this.receiver).subscribe(x => {
                            if (x["status"] === 1) {
                                this.app.showSuccess("Đã cập nhật thông tin người tiếp nhận.")
                                this.disableRole = false
                                this.resetButton();
                            } else if (x["status"] === 0) {
                                this.app.errorValidateDate("Có lỗi không xác định")
                            } else if (x["status"] === Constants.DELETE_UPDATE_ERROR) {
                                this.app.errorValidateDate("Cập nhật không thành công. Người tiếp nhận này đã bị xóa bởi một người khác.")
                            } else if (x["status"] === 5) {
                                this.app.errorValidateDate("Người tiếp nhận này đã tồn tại.")
                            }

                            // this.isEdit = false;
                        });
                    }
                },
                reject: () => {
                   // this.monitorReset();
                }
            });
        }
    }

    monitorReset() {
        if (this.myTable !== undefined) {
            this.myTable.reset();
        }
        this.selectionMode = "checkbox"
        this.place = null;
        this.unit = null;
        this.role = new Role("", "", "");
        this.employee = null;
        this.edit = 0;
        this.isEdit = false;
        this.isTCT = false;
        this.selectedSector = [];
        this.lstSelectedPlace = [];
        this.lstPlace = [];

    }

    resetButton() {
        this.disableRole = false;
        if (this.userInfo.role.includes('PMQT_Canhve')) {
            this.getSector(0);
        } else if (this.userInfo.role.includes('PMQT_ADMIN')|| this.userInfo.role.includes('PMQT_Bep_truong')) {
            this.getSector(0);
        } else if (this.userInfo.role.includes('PMQT_QL') || this.userInfo.role.includes('PMQT_CVP')) {

            this.getSector(0);
        } else {
            this.getSector(0);
        }
        this.selectionMode = "checkbox"
        this.place = null;
        this.unit = null;
        this.role = new Role("", null, "");
        this.employee = null;
        this.edit = 0;
        this.isEdit = false;
        this.isTCT = false;
        this.lstSelectedPlace = [];
        this.selectedSector = [];
        this.handleSearch();
        //this.getReceiver();
    }

    focusOutPlace() {
        if (this.place != null && this.place != undefined) {
            if (this.place.placeId == null || this.place.placeId == undefined) {
                this.place = null
            }
        }
    }

    focusOutUnit() {
        if (this.unit != null && this.unit != undefined) {
            if (this.unit.unitId == null || this.unit.unitId == undefined) {
                this.unit = null
            }
        }
    }

    doEdit(rowData: Receiver) {
        this.disableRole = true;
        this.edit = 1;
        this.isEdit = true;
        this.receiver.receiverId = rowData.receiverId;
        this.edit = 1;

        this.place = new Place(rowData.placeId, rowData.place);
        if (rowData.unit == null) this.unit = null;
        else this.unit = new Unit(Number(rowData.unitId), rowData.unit, rowData.unit);
        let index = this.listRole.find(x => x.jobCode == rowData.jobCode);
        this.role = index;
        if (this.role.roleName == 'Nhân viên VP TCT') {
            this.isTCT = true;
            this.selectionMode="single";
            this.unit = null;
            this.unitEmployee = null;
        }
        this.employee = new Employee(rowData.employeeId, rowData.fullName, rowData.savedUsername, rowData.employeePhone, rowData.roleName, rowData.place, rowData.unit);

        this.lstSelectedPlace = [];
        if (rowData.placeNames != null) {
            var names = rowData.placeNames.split(',');
            var ids = rowData.placeIds.split(',');
            this.lstSgsPlace.forEach(el=>{
                if(ids.includes(el.placeId +"",0)){
                    this.lstSelectedPlace.push(el)
                }
            })
        }
        this.onGetTreeToCheck(rowData.path);
        this._KitchenManagementService.buildCompleteTree(null).subscribe(res => {
            this.sectors = res.data;
            this.findNode(this.sectors[0], rowData.unitId);
            this.setExpand(this.sectors[0], this.selectedNode.parent);
            this.selectedSector = [this.selectedNode];
            this.selectedSector[0].selectable = true;
            this.selectionMode="checkbox";
        });
        //this.selectedSector = [];
        // this.sectors.
    }

    findNode(sectors: TreeNode, unitId: any) {
        if (sectors.data == unitId) {
            this.selectedNode = sectors;
            this.selectedNode.selectable = true;
        }
        for (let i = 0; i < sectors.children.length; i++) {
            sectors.children[i].selectable = true;
            this.findNode(sectors.children[i], unitId);
        }
    }
    findNode2(sectors: TreeNode, unitId: any) {
        if (sectors.data == unitId) {
            this.tempNode = sectors;
            sectors.expanded = true;
        }
        for (let i = 0; i < sectors.children.length; i++) {
            this.findNode2(sectors.children[i], unitId);
        }
    }
    setExpand(sectors: TreeNode, node: any) {
        if (node == null) {
            return;
        }
        this.findNode2(sectors, node);
        this.setExpand(sectors, this.tempNode.parent);
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

    insertData() {
        this.stationeryService.insertReceiver(this.receiver).subscribe(y => {
            if (y["status"] == 0) {
                this.app.errorValidateDate("Người tiếp nhận này đã tồn tại")
            } else if (y["status"] == 1) {
                this.app.showSuccess("Đã thêm người tiếp nhận mới.")
                this.resetButton();
            }

        });
    }

    doDelete(rowData) {
        this.confirmationService.confirm({
            message: "Đ/c có muốn xóa danh mục người tiếp nhận?",
            header: "Xác nhận xoá",
            icon: "pi pi-info-circle",
            acceptLabel: 'Đồng ý',
            rejectLabel: 'Hủy bỏ',
            accept: () => {
                this.receiver.receiverId = rowData.receiverId;
                this.stationeryService.deleteReceiver(this.receiver).subscribe(x => {
                    if (x["status"] == 0) {
                        this.app.errorValidateDate("Có lỗi không xác định")
                    } else if (x["status"] == 1) {
                        this.app.showSuccess("Đã xóa người tiếp nhận.")
                    } else if (x["status"] == 4) {
                        this.app.showWarn("Người tiếp nhận đang được yêu cầu, không xóa được.")
                    } else if (x["status"] == Constants.DELETE_UPDATE_ERROR) {
                        this.app.errorValidateDate("Xóa không thành công. Người tiếp nhận này đã bị xóa bởi một người khác.")
                    }
                    this.getReceiver();
                    this.monitorReset();
                });
            },
            reject: () => {

            }
        });

    }


    addPlace() {
        this.lstPlace.push(null);
        this.lstSelectedPlace.push(null);
    }

    removePlace(index: number) {
        this.lstPlace.splice(index, 1);
        this.lstSelectedPlace.splice(index, 1);
    }

    nodeExpand(event) {
        this.loadingTree = true;
        if (event.node && event.node.children !== undefined) {
            this.loadingTree = false;
            return;
        }
        if (event.node && event.node.children === undefined) {
            this.stationeryService
                .getSectors(event.node.data).subscribe(
                data => {
                    event.node.children = data;

                    this.loadingTree = false;
                });

        }
    }
    getSelectedNodeData(): any[] {
        this.listSelectedUnitId  = [];
         if (this.selectedSector != null) {
            for (const file of this.selectedSector) {
                this.listSelectedUnitId.push(file.data);
            }
        }
        return this.listSelectedUnitId.length > 0 ? this.listSelectedUnitId : null;



    }
    getSector(type: number) {
        this.listParentUnit = [];

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
                this.sectors[0].selectable = false;
            });

        } else if (type === 1) {
            this._KitchenManagementService.getSectors(this.userInfo.unitId as string).subscribe(data => {
                this.sectors = [{
                    label: this.userInfo.unitName as string,
                    data: this.userInfo.unitId,
                    selectable: true,
                    children: data
                }];
                this.listParentUnit.push(this.userInfo.unitId as string);
            });

        } else if (type === 2) {
            this.sectors = [{
                label: this.userInfo.unitName as string,
                data: this.userInfo.unitId,
                selectable: false
            }];
            this.selectedSector = [this.sectors[0]];
        }
    }
    getLastItem(): any {
        this.listUnitId = [];
        this.listUnitId = this.getSelectedNodeData();
        if(!this.listUnitId) return null;
        return this.listUnitId.slice(-1)[0];
    }
    changeOn(){
        if (this.role.roleName == 'Nhân viên HC đơn vị') {
            this.employee = null;
        }
    }

    changeUnOn(){
        if (this.role.roleName == 'Nhân viên HC đơn vị') {
            this.employee = null;
        }
    }
}
