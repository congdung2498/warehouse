import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {CommonUtils} from "../../../../common/commonUtils";
import {RequestStationeryFrom, RequestStationerySearch} from "../../Entity/RequestStationeryFrom";
import {TokenStorage} from "../../../shared/token.storage";
import {AppComponent} from "../../../app.component";
import {StationeryService} from "../../stationery.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-request-office',
  templateUrl: './request-office.component.html',
  styleUrls: ['./request-office.component.css']
})
export class RequestOfficeComponent implements OnInit {

  listSelectedReceiver: any[];
  formSearch: FormGroup;
  placeStartList = new Array();

  requestOficeForm : RequestStationeryFrom = {

    placeID : null ,
    note : '' ,
    listStationery : []

  };
  unitId : String ;
  email : String ;
  phoneNumber : String;
  fullName : String;


  constructor(private formBuilder: FormBuilder,
              private   tokenService: TokenStorage,
              private app: AppComponent,
              private router: Router,
              private  stationeryService : StationeryService

  ) {

    this.listSelectedReceiver = [];
    let userInfo = this.tokenService.getUserInfo();
    this.unitId = userInfo.unitId;
    this.email = userInfo.email ;
    this.fullName = userInfo.fullName;
    this.phoneNumber = userInfo.phoneNumber;
    console.log(userInfo);
 }

  ngOnInit() {

    this.buildForm();
    // this.processSearch();

  }
  get f() {
    return this.formSearch.controls;
  }
  private buildForm(): void {
    this.formSearch = this.formBuilder.group({
      stationeryID : [''],
      quantity : [''] ,
      unitPrice : [''] ,
      note : [''] ,
      placeID : ['']
    });
  }

//  when add textbox receiver
  addTextbox() {
    let requestStationery :  RequestStationerySearch = { stationeryID : '',  quantity : 0 , unitPrice : 0};
    console.log(this.listSelectedReceiver) ;
    this.listSelectedReceiver.push(requestStationery);
  }


//  when remove textbox receiver
  removeTextbox(index: number) {
    console.log(this.listSelectedReceiver) ;
    this.listSelectedReceiver.splice(index, 1);

  }
  setList() {


  }

  searchPlaceIsRole(ev) {
    this.stationeryService.searchPlaceIsRole(ev.query)
      .subscribe(res => {
        this.placeStartList = res.data;
      });
  }

  selectPlace(item){
    this.formSearch.value.placeID = item.placeId;
  }
  settingParams() {
    this.requestOficeForm.placeID = this.formSearch.value.placeID;
    this.requestOficeForm.note = this.formSearch.value.note;
    this.requestOficeForm.listStationery = this.listSelectedReceiver;
   }
  save(){

    this.settingParams();
    this.app.confirmMessage(null, () => {// on accepted

      this.stationeryService.insertStationeryForm(this.requestOficeForm).subscribe(res => {
        this.router.navigate(['/stationery/requestStationery']);
        this.app.showSuccess('Gửi thành công')
      });

    }, () => {
      this.app.showWarn('Gửi thất bại');
    });



  }



}
