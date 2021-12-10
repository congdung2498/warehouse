import { Component, OnInit, OnDestroy } from '@angular/core';
import { FolderService } from '../folder.service';
import { Folder } from '../Entity/Folder';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'folder-search',
  templateUrl: './folder-search.component.html',
  styleUrls: ['./folder-search.component.css']
})
export class FolderSearchComponent implements OnInit, OnDestroy {

  keyword: string = "";
  searchedKeyword: string = "";
  isSearched: boolean = false;
  loading: boolean = false;

  foundFolder: Folder[] = [];

  totalFoundRecord: number = 0;
  pageNumber: number = 1;
  pageSize: number = 10;
  pagingState: string;
  TYPE_NEW = "new";
  TYPE_PAGING = "paging";

  notChar: RegExp = /[%&_*\\'<>]/g;

  cols = [
    { field: "", header: "STT", width: "5%" },    
    { field: "", header: "Mã thùng", width: "15%" },
    { field: "", header: "Tên thùng", width: "25%" },
    { field: "", header: "Mã hồ sơ", width: "15%" },
    { field: "", header: "Tên hồ sơ", width: "25%" },
    { field: "", header: "Thao Tác", width: "15%" },
  ];

  constructor(private folderService: FolderService, private messageService: MessageService, private router: Router, private title: Title,) {
    this.title.setTitle("Cập nhật hồ sơ");
  }

  ngOnInit() {
    
    let lastScreen = localStorage.getItem("lastScreen");
    if(lastScreen == "FOLDER_DETAILS_SCREEN"){
      localStorage.removeItem("lastScreen");
      this.loadStorage();
    }    
    this.pagingState = this.calculatePaging();      
  }

  calculatePaging(){
    if(this.totalFoundRecord == 0) return '0 - 0 / 0';
    else{
      let startNo = this.pageSize*(this.pageNumber - 1) + 1;
      let endNo = this.pageNumber * this.pageSize;
      if(endNo > this.totalFoundRecord) endNo = this.totalFoundRecord;
      return startNo + ' - ' + endNo + ' / ' + this.totalFoundRecord;
    }
  }

  searchBtnClick(searchResultTable){
    if(this.keyword == "") return;
    this.pageNumber = 1;
    this.searchedKeyword = this.keyword;
    this.searchFolder(searchResultTable, this.TYPE_NEW);    
  }

  paginateSearch(event, searchResultTable) {
    this.pageNumber = event.first/this.pageSize + 1;
    this.searchFolder(searchResultTable, this.TYPE_PAGING);
  }

  searchFolder(searchResultTable, type){
    if(this.searchedKeyword.trim() != ""){
      this.folderService.searchFolderByNameOrQrCode(this.searchedKeyword.trim(), this.pageNumber, this.pageSize)
      .subscribe(resp=>{
        //console.log(resp)

        if(resp.status && resp.status == 1) this.bindData(resp);
        else this.showError();
        this.isSearched = true;
        this.keyword = this.keyword.trim();
        if(type == this.TYPE_NEW) searchResultTable.first = 0;
      })
    }    
  }

  bindData(resp: any){
    if(resp.data){
      this.foundFolder = resp.data;
      if(resp.data.length > 0) this.totalFoundRecord = resp.data[0].totalFoundRecord;
      else this.totalFoundRecord = 0;
      this.pagingState = this.calculatePaging();
    }
    else{
      this.foundFolder = [];
    }
    this.saveToStorage();
  }

  loadStorage(){
    this.keyword = localStorage.getItem("keyword");
    this.searchedKeyword = localStorage.getItem("searchedKeyword");
    this.pageNumber = parseInt(localStorage.getItem("pageNumber"));
    this.foundFolder = JSON.parse(localStorage.getItem("foundFolder"));
    this.totalFoundRecord = parseInt(localStorage.getItem("totalFoundRecord"));
  }

  saveToStorage(){
    localStorage.setItem("keyword", this.keyword);
    localStorage.setItem("searchedKeyword", this.searchedKeyword);
    localStorage.setItem("pageNumber", this.pageNumber.toString());
    localStorage.setItem("foundFolder", JSON.stringify(this.foundFolder));
    localStorage.setItem("totalFoundRecord", this.totalFoundRecord.toString());
  }

  showError(){
    this.messageService.add({
      severity: "error",
      summary: "Cảnh báo lỗi:",
      detail: "Có lỗi không xác định. Vui lòng thử lại!"
    });
  }

  goFolderDetails(folder: any){    
    localStorage.setItem("selectedFolder", JSON.stringify(folder));
    this.router.navigate(['folder/folder-details']);
  }

  refresh(searchResultTable){
    this.keyword = "";
    this.searchedKeyword = "";
    this.isSearched = false;
    this.foundFolder = [];
    this.totalFoundRecord = 0;
    this.pageNumber = 1;
    searchResultTable.first = null;
  }

  preventSpecialChar(input) {
    if (this.notChar.test(input.value)) {
      input.value = input.value.replace(this.notChar, '');
    }
  }

  ngOnDestroy(){
    if (this.router.routerState.snapshot.url.indexOf('folder-details') == -1) {
        //console.log('Hello, I am going to clear local storage!');
        localStorage.clear();
    }
  }
   
}

