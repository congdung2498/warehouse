import { Component, OnInit, ViewChild } from '@angular/core';
import { DocumentBoxSearchService } from '../document-box-search.service';
import { Warehouse } from '../Entity/Warehouse';
import { MessageService } from 'primeng/api';
import { Title } from '@angular/platform-browser';
import { Constants } from '../../shared/containts';
import { DocumentCol, DocumentDetailCol } from './document-col';
import { AppComponent } from '../../app.component';
import { DocumentBoxSearchDetailDialogComponent } from '../document-box-search-detail-dialog/document-box-search-detail-dialog.component';
import { TokenStorage } from '../../shared/token.storage';
import { finalize } from 'rxjs/operators';
@Component({
  selector: 'app-document-box-search',
  templateUrl: './document-box-search.component.html',
  styleUrls: ['./document-box-search.component.css']
})
export class DocumentBoxSearchComponent implements OnInit {

  public selectedWarehouse: Warehouse;
  public keyword: any = '';
  public searchedKeyword = '';
  public warehouseList: Warehouse[] = [];
  public suggestedWarehouse: Warehouse[] = [];
  public searchResult: any;
  public tinBoxDetails: any;

  byTinBox = false;
  byFolder = false;
  byConstruction = false;
  byContract = false;
  byPackage = false;
  byProject = false;

  selectedDoc: any = {};
  totalSearchRecord = 0;
  totalDocumentRecord = 0;
  searchPageNumber = 0;
  documentPageNumber = 0;
  pageSize = 10;
  resultShowFlag: Boolean = false;

  isShowModal = false;
  isSearched = false;
  loading = false;

  searchResultTable: any;

  searchTypeList = [
  ];

  searchType = Constants.SEARCH_DOC_TYPE.BOX;

  _currentType = Constants.SEARCH_DOC_TYPE.BOX;

  cols = DocumentCol.BOX_COL;

  modal_cols = [];

  @ViewChild(DocumentBoxSearchDetailDialogComponent) treeDetail: DocumentBoxSearchDetailDialogComponent;
  @ViewChild('documentsTable') documentsTable;

  constructor(
    private service: DocumentBoxSearchService, private title: Title, private app: AppComponent,
    private messageService: MessageService,
    private tokenService: TokenStorage
  ) {
    title.setTitle('Tra Cứu Tài Liệu');
  }

  ngOnInit() {
    this.checkRole();
  }

  //   Văn thư là nhìn thấy tất cả - search tất cả
  // QLDA là không nhìn thấy công văn đi, công văn đến, chứng từ ( bỏ cả trong search)
  // Tài chính không nhìn thấy công văn đi, công văn đến ( bỏ cả trong search)
  checkRole() {
    if (this.tokenService.checkRole('PMQT_ADMIN') || this.tokenService.checkRole('WAREHOUSE_VT')) {
      this.searchTypeList = [
        Constants.SEARCH_DOC_TYPE.BOX,
        Constants.SEARCH_DOC_TYPE.FOLDER,
        Constants.SEARCH_DOC_TYPE.PROJECT,
        Constants.SEARCH_DOC_TYPE.PACKAGE,
        Constants.SEARCH_DOC_TYPE.CONTRACT,
        Constants.SEARCH_DOC_TYPE.CONSTRUCTION,
        Constants.SEARCH_DOC_TYPE.OFFICIAL_DISPATCH_TRAVEL,
        Constants.SEARCH_DOC_TYPE.INCOMING_OFFICIAL_DISPATCH,
        Constants.SEARCH_DOC_TYPE.VOUCHER,
        Constants.SEARCH_DOC_TYPE.VOUCHER_NOTE,
        Constants.SEARCH_DOC_TYPE.PAYMENT_SUMMARY
      ];
    } else if (this.tokenService.checkRole('WAREHOUSE_TC')) {
      this.searchTypeList = [
        Constants.SEARCH_DOC_TYPE.BOX,
        Constants.SEARCH_DOC_TYPE.FOLDER,
        Constants.SEARCH_DOC_TYPE.VOUCHER,
        Constants.SEARCH_DOC_TYPE.VOUCHER_NOTE,
        Constants.SEARCH_DOC_TYPE.PAYMENT_SUMMARY
      ];
    } else if (this.tokenService.checkRole('WAREHOUSE_DA')) {
      this.searchTypeList = [
        Constants.SEARCH_DOC_TYPE.BOX,
        Constants.SEARCH_DOC_TYPE.FOLDER,
        Constants.SEARCH_DOC_TYPE.PROJECT,
        Constants.SEARCH_DOC_TYPE.PACKAGE,
        Constants.SEARCH_DOC_TYPE.CONTRACT,
        Constants.SEARCH_DOC_TYPE.CONSTRUCTION,
      ];
    }
  }

  loadWarehouse(event) {
    this.service
      .getAllActiveWarehouse()
      .subscribe(resp => {
        this.warehouseList = resp.data;
        // console.log(this.warehouseList);
        if (typeof event.query !== 'undefined' && event.query !== null && event.query !== '') {
          const tempList = [];
          for (let i = 0; i < this.warehouseList.length; i++) {
            const warehouse = this.warehouseList[i];
            if (warehouse.name.toLowerCase().includes(event.query.toLowerCase().trim())) {
              tempList.push(warehouse);
            }
          }
          this.suggestedWarehouse = tempList;
        } else {
          this.suggestedWarehouse = this.warehouseList;
        }

      });
  }

  refresh() {
    this.keyword = '';
    this.selectedWarehouse = null;
    this.resultShowFlag = false;
    // this.searchType = Constants.SEARCH_DOC_TYPE.BOX;
    this.totalSearchRecord = 0;
    this.searchResult = [];
    if (this.searchResultTable) { this.searchResultTable.first = 0; }
  }

  paginateSearch(event) {
    this.searchPageNumber = event.first;
  }

  paginateDocument(event) {
    this.documentPageNumber = event.first;
  }

  doSearch(searchResultTable) {
    this.searchResultTable = searchResultTable;
    this.updateColumn();
    this.resultShowFlag = false;
    let warehouseId = null;
    this.searchResult = [];
    searchResultTable.first = 0;
    this.searchPageNumber = 0;
    this.loading = true;

    if (this.keyword.trim() === '') {
      return;
    }

    this._currentType = { ... this.searchType };

    if (this.selectedWarehouse != null) {
      warehouseId = this.selectedWarehouse.warehouseId;
    } else {
      warehouseId = -1;
    }

    const searchCondition = {
      warehouseId: warehouseId,
      keyword: this.keyword.trim(),
      type: this.searchType.code
    };


    this.service
      .searchDocumentBox(searchCondition)
      .pipe(
        finalize(() => {
          this.loading = false;
        })
      )
      .subscribe(resp => {
        if (resp.data) {
          this.searchResult = resp.data;
            this.searchResult.map(data => {
              if (data.column && data.row && data.height) {
                data.description = data.row + '/' + data.column + '/' + data.height;
              }
            });
          if (this._currentType.code === Constants.SEARCH_DOC_TYPE.FOLDER.code) {
            this.searchResult.map(folder => {
              if (folder.folderType) {
                this.mapFolderType(folder);
              }
            });
          }
          this.totalSearchRecord = this.searchResult.length;
          this.resultShowFlag = true;
        }
        this.searchedKeyword = this.keyword.trim();
      });
  }

  updateColumn(): void {

    switch (this.searchType) {
      case Constants.SEARCH_DOC_TYPE.BOX:
        this.cols = DocumentCol.BOX_COL;
        if (this.tokenService.checkRole('PMQT_ADMIN') || this.tokenService.checkRole('WAREHOUSE_VT')) {
          this.modal_cols = DocumentDetailCol.BOX_COL;
        } else if (this.tokenService.checkRole('WAREHOUSE_TC')) {
          this.modal_cols = DocumentDetailCol.BOX_COL_TC;
        } else if (this.tokenService.checkRole('WAREHOUSE_DA')) {
          this.modal_cols = DocumentDetailCol.BOX_COL_DA;
        }
        break;
      case Constants.SEARCH_DOC_TYPE.FOLDER:
        this.cols = DocumentCol.FOLDER_COL;
        if (this.tokenService.checkRole('PMQT_ADMIN') || this.tokenService.checkRole('WAREHOUSE_VT')) {
          this.modal_cols = DocumentDetailCol.BOX_COL;
        } else if (this.tokenService.checkRole('WAREHOUSE_TC')) {
          this.modal_cols = DocumentDetailCol.BOX_COL_TC;
        } else if (this.tokenService.checkRole('WAREHOUSE_DA')) {
          this.modal_cols = DocumentDetailCol.BOX_COL_DA;
        }
        break;
      case Constants.SEARCH_DOC_TYPE.PROJECT:
        this.cols = DocumentCol.PROJECT_COL;
        break;
      case Constants.SEARCH_DOC_TYPE.PACKAGE:
        this.cols = DocumentCol.PACKAGE_COL;
        break;
      case Constants.SEARCH_DOC_TYPE.CONTRACT:
        this.cols = DocumentCol.CONTRACT_COL;
        break;
      case Constants.SEARCH_DOC_TYPE.CONSTRUCTION:
        this.cols = DocumentCol.CONSTRUCTION_COL;
        break;
      case Constants.SEARCH_DOC_TYPE.OFFICIAL_DISPATCH_TRAVEL:
        this.cols = DocumentCol.OFFICIAL_DISPATCH_TRAVEL_COL;
        this.modal_cols = DocumentDetailCol.OFFICIAL_DISPATCH_TRAVEL_COL;
        break;
      case Constants.SEARCH_DOC_TYPE.INCOMING_OFFICIAL_DISPATCH:
        this.cols = DocumentCol.INCOMING_OFFICIAL_DISPATCH_COL;
        this.modal_cols = DocumentDetailCol.INCOMING_OFFICIAL_DISPATCH_COL;
        break;
      case Constants.SEARCH_DOC_TYPE.VOUCHER:
        this.cols = DocumentCol.VOUCHER_COL;
        this.modal_cols = DocumentDetailCol.VOUCHER_COL;
        break;
      case Constants.SEARCH_DOC_TYPE.VOUCHER_NOTE:
        this.cols = DocumentCol.VOUCHER_NOTE_COL;
        this.modal_cols = DocumentDetailCol.VOUCHER_NOTE_COL;
        break;
      case Constants.SEARCH_DOC_TYPE.PAYMENT_SUMMARY:
        this.cols = DocumentCol.PAYMENT_SUMMARY_COL;
        this.modal_cols = DocumentDetailCol.PAYMENT_SUMMARY_COL;
        break;
    }
  }

  mapFolderType(folder) {
    switch (folder.folderType) {
      case 1:
        folder.folderTypeText = 'Dự án';
        break;
      case 2:
        folder.folderTypeText = 'Chứng từ';
        break;
      case 3:
        folder.folderTypeText = 'Công văn';
        break;
    }
  }

  sync(id: number, isSync: boolean) {
    if (id) {
      this.app.confirmMessage(null, () => {// on accepted
        const param = {
          id,
          searchType: this._currentType.code,
          isSynchrony: isSync ? 1 : 0
        };
        this.service.synchrony(param)
          .subscribe(resp => {
            if (resp.status) {
              if (param.isSynchrony) {
                this.messageService.add({
                  severity: 'success',
                  summary: 'Thành công',
                  detail: 'Đồng bộ thành công'
                });
              } else {
                this.messageService.add({
                  severity: 'success',
                  summary: 'Thành công',
                  detail: 'Hủy đồng bộ thành công'
                });
              }
              this.doSearch(this.searchResultTable);
            }
          });
      }, () => {
      });
    }
  }

  showTinBoxDetails(selectedDoc: any, documentsTable: any) {
    if (this._currentType.sync) {
      const paramDialog = {
        ...selectedDoc,
        id: selectedDoc[this._currentType.idField],
        type: this._currentType
      };
      this.treeDetail.onShowDialog(paramDialog);
    } else {
      this.totalDocumentRecord = 0;
      this.documentPageNumber = 0;
      documentsTable.first = 0;
      this.selectedDoc = selectedDoc;
      const id = this.selectedDoc[this._currentType.idField];
      if (id) {
        this.service.getTinBoxContent({ id, searchType: this._currentType.code })
          .subscribe(resp => {
            if (resp.data) {
              const detailList = resp.data;
              // if (this._currentType.code === Constants.SEARCH_DOC_TYPE.FOLDER.code) {
              //   detailList = detailList.filter(detail => detail.folderId === this.selectedDoc.folderId);
              // }
              detailList.map(folder => {
                if (folder.folderType) {
                  this.mapFolderType(folder);
                }
              });
              this.tinBoxDetails = detailList;
              this.totalDocumentRecord = this.tinBoxDetails.length;
              const body = this.documentsTable.containerViewChild.nativeElement.getElementsByClassName('ui-table-scrollable-header-box')[0];
              body.style.marginLeft = '0';
              this.isShowModal = true;
            }

          });
      }
    }
  }

  calculateColspan(cols): number {
    if (screen.width < 500) {
      return cols - 1;
    }
    return cols;
  }

}

