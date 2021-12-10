import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {TokenStorage} from "../../../../../shared/token.storage";
import {AppComponent} from "../../../../../app.component";
import {Router} from "@angular/router";
import {StationeryService} from "../../../../stationery.service";
import {RequestStationerySearch} from "../../../../Entity/RequestStationeryFrom";

@Component({
  selector: 'app-edit-list-request',
  templateUrl: './edit-list.component.html',
  styleUrls: ['./edit-list.component.css']
})
export class EditListRequestComponent implements OnInit {

  unitId : String ;
  email : String ;
  phoneNumber : String;
  fullName : String;

  listSelectedReceiver: any[];
  formSearch: FormGroup;
  placeStartList = new Array();

  constructor(private formBuilder: FormBuilder,
  private   tokenService: TokenStorage,
  private app: AppComponent,
  private router: Router,
  private  stationeryService : StationeryService
      ) { }

  ngOnInit() {
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

  saveCopy(){}

}
