import {Component, OnInit, ViewChild} from '@angular/core';
import {StationeryService} from "../../stationery.service";
import {Table} from "primeng/table";
import {ProcessUnit} from "../../Entity/ProcessUnit";

@Component({
  selector: 'app-process-request',
  templateUrl: './process-request.component.html',
  styleUrls: ['./process-request.component.css']
})
export class ProcessRequestComponent implements OnInit {
  @ViewChild(Table) dataTableComponent: Table;
  placeStartList = new Array();
  unitNameList = new Array();
  unitId : number;
  placeId : number;
  status: string[];
  resultList : any;
  total_record : number;
  processUnit : ProcessUnit ={

     placeID: null,

    unitID : null,

    listStatus : ['']  ,

    pageNumber: null,

    pageSize: null

    } ;

  constructor(private stationeryService : StationeryService,


  ) {
  }

  ngOnInit() {
  }



  searchUnitName(ev){

    this.stationeryService.searchUnitName(ev.query)
      .subscribe(res => {
        this.unitNameList = res.data;
        console.log(res);

      });
  }


  searchPlaceIsRole(ev) {
  this.stationeryService.searchPlaceIsRole(ev.query)
      .subscribe(res => {
        this.placeStartList = res.data;
        console.log(res);
      });
  }

  selectPlace(item){

    this.placeId = item.placeId;
    }

  selectUnit(item){

    this.unitId = item.unitId;
 }
  settingParams(){

    this.processUnit.placeID = this.placeId;
    this.processUnit.unitID = this.unitId;
    this.processUnit.listStatus = this.status;

    }

  searchData() {

    this.settingParams();
    this.dataTableComponent.reset();
    }


  public onLazyLoad(event) {

    this.processUnit.pageNumber = event.first;
    this.processUnit.pageSize = event.rows;
    this.stationeryService.findInfoRequest(this.processUnit).subscribe(res => {
      this.resultList = res.data;
      console.log( res);
      if(this.resultList){
      this.total_record = this.resultList.length;
      }
    });
  }





}
