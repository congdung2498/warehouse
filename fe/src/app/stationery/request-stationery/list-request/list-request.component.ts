import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {RequestSearch} from "../../Entity/RequestSearch";
import {StationeryService} from "../../stationery.service";
import {Calendar} from "primeng/primeng";
import {Table} from "primeng/table";
import {DatePipe} from "@angular/common";
import {AppComponent} from "../../../app.component";
import {DateTimeUtil} from "../../../../common/datetime";
import {Router} from "@angular/router";

@Component({
    selector: 'app-list-request',
    templateUrl: './list-request.component.html',
    styleUrls: ['./list-request.component.css']
})
export class ListRequestComponent implements OnInit {
    @ViewChild('dt')
    private dataTableComponent: Table;
    @ViewChild('transferDateFromF') transferDateFromF: Calendar;
    @ViewChild('transferDateToF') transferDateToF: Calendar;
    resultList: any = [];


    totalRecords: number;
    pageNumber : number;
    pageSize : number;
    dateFrom: Date;
    dateTo: Date;

    vn = DateTimeUtil.vn;

    status: string[];

    requestSearch: RequestSearch = {
        pageNumber : null,
        pageSize : null,
        userName: null,
        dateFrom: null,
        dateTo: null,
        listStatus: []
    };
    isProcess: number;
    isPart: number;
    isComplete: number;
    isRefuse: number;
    noRefuse: number;

    formSearch: FormGroup;

    get f() {
        return this.formSearch.controls;
    }

    private buildForm(): void {
        this.formSearch = this.formBuilder.group({
            isProcess: [''],
            isPart: [''],
            isComplete: [''],
            isRefuse: [''],
            noRefuse: ['']
        });
    }

    constructor(private formBuilder: FormBuilder,
                private stationeryService: StationeryService,
                private app: AppComponent ,
                private router: Router
                                        ) {

    }

    ngOnInit() {
        this.buildForm();
    }

    onShowDetail(item) {
        console.log(item);
        if(item.status === 1) {

            this.router.navigate(['/stationery/editListRequest/' + item.requestID]);

        } else if(item.status === 3) {

            this.router.navigate(['/stationery/rattingListRequest' , item.requestID]);

        }


    }

    search() {
        if (this.validateDate() === false) {
            return;
        }
        this.settingParams();
        this.dataTableComponent.reset();

    }

    validateDate(): boolean {

        if (this.dateFrom != null && this.dateFrom !== undefined) {
            this.requestSearch.dateFrom = new DatePipe('en-US').transform(this.dateFrom, 'yyyy-MM-dd');

        } else {
            this.requestSearch.dateFrom = null;
        }
        if (this.dateTo != null && this.dateTo !== undefined) {
            this.requestSearch.dateTo = new DatePipe('en-US').transform(this.dateTo, 'yyyy-MM-dd');
        } else {
            this.requestSearch.dateTo = null;
        }
        // tslint:disable-next-line:max-line-length
        if (this.requestSearch.dateFrom != null && this.requestSearch.dateTo != null && this.requestSearch.dateTo < this.requestSearch.dateFrom) {
            this.app.errorValidateDate('Ngày bắt đầu phải nhỏ hơn ngày kết thúc');
            this.transferDateFromF.inputfieldViewChild.nativeElement.focus();
            return false;
        }
        // tslint:disable-next-line:max-line-length
        if (this.requestSearch.dateFrom != null && this.requestSearch.dateFrom > new DatePipe('en-US').transform(Date.now(), 'yyyy-MM-dd')) {
            this.app.errorValidateDateNow('Ngày kết thúc phải lớn hơn ngày hiện tại');
            this.transferDateFromF.inputfieldViewChild.nativeElement.focus();
            return false;
        }
        // tslint:disable-next-line:max-line-length
        if (this.requestSearch.dateTo != null && this.requestSearch.dateTo > new DatePipe('en-US').transform(Date.now(), 'yyyy-MM-dd')) {
            this.transferDateToF.inputfieldViewChild.nativeElement.focus();
            this.app.errorValidateDateNow('Ngày bắt đầu phải lớn hơn ngày hiện tại');
            return false;
        }
        return true;
    }

    settingParams() {

        this.isProcess = this.formSearch.value.isProcess;
        this.isPart = this.formSearch.value.isPart;
        this.isComplete = this.formSearch.value.isComplete;
        this.isRefuse = this.formSearch.value.isRefuse;
        this.noRefuse = this.formSearch.value.noRefuse;

        //TODO[TUan ANh]:
        this.requestSearch.listStatus = this.status;

        /*this.requestSearch.listStatus.push(this.isProcess);
        this.requestSearch.listStatus.push(this.isPart);
        this.requestSearch.listStatus.push(this.isComplete);
        this.requestSearch.listStatus.push(this.isRefuse);
        this.requestSearch.listStatus.push(this.noRefuse);*/
        console.log(this.requestSearch.listStatus );
    }

    public onLazyLoad(event) {


        this.requestSearch.pageNumber = event.first;
        this.requestSearch.pageSize = event.rows;

        this.stationeryService.findDataRequest(this.requestSearch).subscribe(res => {
            this.resultList = res.data;
            this.totalRecords = res.data.totalRecords;

        });
    }


}
