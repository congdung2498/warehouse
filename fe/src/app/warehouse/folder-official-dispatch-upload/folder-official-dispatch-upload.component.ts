import { Component, OnInit, ViewChild } from '@angular/core';
import * as cloneDeep from 'lodash/cloneDeep';

import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { Title } from '@angular/platform-browser';
// export excel

import * as XLSX from 'xlsx';
import { WarehouseService } from '../warehouse.service';
import { ExcelHelperService } from '../excel.helper.service';
import { saveAs } from 'file-saver';
import { Constants } from '../../shared/containts';
import { FolderOfficialDispatchImport } from '../Entity/FolderOfficialDispatchImport';

@Component({
  selector: 'app-folder-official-dispatch-upload',
  templateUrl: './folder-official-dispatch-upload.component.html',
  styleUrls: ['./folder-official-dispatch-upload.component.css']
})
export class FolderOfficialDispatchUploadComponent implements OnInit {
  @ViewChild('myTable') myTable: Table;
  pageNumber = 0;
  totalRecords = 0;
  pageSize = 10;
  listExcelImport: any[];
  listExcelInitial: any[];
  isFileLoaded = false;
  isFileUploaded = false;
  uploadResult: any;
  resultCols: any;
  isShowingResult = false;
  resultPageNo = 1;
  totalResultRecord = 0;
  isLoadingFile = false;
  totalDocumentsInExcel = 0;
  totalDocumentsInserted = 0;
  totalDuplicateDocuments = 0;
  totalDocumentsHasError = 0;
  previewTable: any;
  fileInputField: any;
  // hanle import file excel
  arrayBuffer: any;
  file: File;
  @ViewChild('resultTable') resultTable;

  cols = [
    { field: '', header: 'STT', width: '50px' },
    { field: 'tinBoxQrCode', header: 'Mã thùng/hộp', width: '150px' },
    { field: 'folderQrCode', header: 'Mã buộc HS công văn', width: '200px' },
    { field: 'folderName', header: 'Tên buộc HS công văn', width: '200px' },
    { field: 'createUser', header: 'Người tạo', width: '150px' },
    { field: 'type', header: 'Loại công văn', width: '150px' },
    { field: 'officialDispatchName', header: 'Tên công văn', width: '150px' },
    { field: 'officialDispatchDocName', header: 'Tài liệu công văn', width: '200px' },
    { field: 'levelBaomatOfficialDispatchDoc', header: 'Mức chia sẻ tài liệu công văn', width: '240px' },
  ];

  fullResultCols = [
    { field: '', header: 'STT', width: '50px' },
    { field: 'tinBoxQrCode', header: 'Mã thùng/hộp', width: '200px' },
    { field: 'folderQrCode', header: 'Mã buộc HS công văn', width: '200px' },
    { field: 'folderName', header: 'Tên buộc HS công văn', width: '350px' },
    { field: 'createUser', header: 'Người tạo', width: '180px' },
    { field: 'type', header: 'Loại công văn', width: '180px' },
    { field: 'officialDispatchName', header: 'Tên công văn', width: '350px' },
    { field: 'officialDispatchDocName', header: 'Tài liệu công văn', width: '350px' },
    { field: 'levelBaomatOfficialDispatchDoc', header: 'Mức chia sẻ tài liệu công văn', width: '240px' },
    { field: 'rowReport', header: 'Kết quả tải lên', width: '350px' },
  ];
  constructor(
    private messageService: MessageService,
    private title: Title,
    private warehouseService: WarehouseService,
    private excelHelper: ExcelHelperService
  ) {
  }

  ngOnInit() {
    this.title.setTitle('Tải tài liệu buộc hồ sơ công văn');
  }

  incomingfile(event) {
    this.file = event.target.files[0];
    this.isFileUploaded = false;
  }

  previewBtnClick() {
    if (!this.file) {
      return;
    }
    this.previewFile(
      () => {
        // callback nothing if preview only
      },
      () => {
        this.reset();
      });
  }

  previewFile(cbSuccess: any, cbFail: any) {
    this.isLoadingFile = true;
    this.isFileUploaded = false;
    this.isShowingResult = false;
    if (this.file) {
      const fileReader = new FileReader();
      fileReader.onload = e => {
        try {
          this.arrayBuffer = fileReader.result;
          const data = new Uint8Array(this.arrayBuffer);
          const arr = new Array();
          for (let i = 0; i !== data.length; ++i) {
            arr[i] = String.fromCharCode(data[i]);
          }
          const bstr = arr.join('');
          const workbook = XLSX.read(bstr, { type: 'binary' });
          const first_sheet_name = workbook.SheetNames[0];
          const worksheet = workbook.Sheets[first_sheet_name];
          let excelData: any = XLSX.utils.sheet_to_json(worksheet, { raw: true, defval: '' });
          excelData = this.excelHelper.removeAsterisk(excelData);
          const peelOffData = this.peelOffData(excelData);
          // validate file excel
          if (this.validateFileExcel(excelData, peelOffData)) {
            this.listExcelImport = peelOffData;
            this.listExcelInitial = this.listExcelImport
              ? Array.from(this.listExcelImport)
              : null;
            this.totalRecords = this.listExcelImport.length;
            this.isFileLoaded = true;
            this.isLoadingFile = false;
            if (this.previewTable) { this.previewTable.first = 0; }
            cbSuccess();
          } else {
            this.listExcelImport = [];
            this.isFileLoaded = false;
            this.isLoadingFile = false;
            cbFail();
          }
        } catch (error) {
          this.isFileLoaded = false;
          this.isLoadingFile = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Cảnh báo lỗi:',
            detail: 'Đã có lỗi trong việc đọc file!'
          });
          cbFail();
        }
      };
      fileReader.readAsArrayBuffer(this.file);
      fileReader.onerror = e => {
        this.isLoadingFile = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Cảnh báo lỗi:',
          detail: 'Đã có lỗi trong việc đọc file !'
        });
      };
    } else {
      cbFail();
    }
  }

  // tach du lieu cong van di/den
  peelOffData(excelDatas): FolderOfficialDispatchImport[] {
    const peelOffDatas: FolderOfficialDispatchImport[] = [];
    if (excelDatas && excelDatas.length > 0) {
      excelDatas.forEach(excel => {
        this.removeNumber(excel);
        if (excel.cong_van_di && excel.cong_van_di.trim()) {
          peelOffDatas.push({
            tinBoxQrCode: excel.Ma_Thung,
            folderQrCode: excel.Ma_HS,
            folderName: excel.Ten_HS,
            createUser: excel.Nguoi_Tao,
            type: Constants.OFFICIAL_DISPATCH_TYPE.TRAVELS_DOC,
            officialDispatchName: excel.cong_van_di.trim(),
            officialDispatchDocName: excel.tai_lieu_cong_van_di ? excel.tai_lieu_cong_van_di.trim() : null,
            levelBaomatOfficialDispatchDoc: excel.level_baomat_tai_lieu_cong_van_di ? excel.level_baomat_tai_lieu_cong_van_di.trim() : null
          });
        }

        if (excel.cong_van_den && excel.cong_van_den.trim()) {
          peelOffDatas.push({
            tinBoxQrCode: excel.Ma_Thung,
            folderQrCode: excel.Ma_HS,
            folderName: excel.Ten_HS,
            createUser: excel.Nguoi_Tao,
            type: Constants.OFFICIAL_DISPATCH_TYPE.INCOMING_DOC,
            officialDispatchName: excel.cong_van_den.trim(),
            officialDispatchDocName: excel.tai_lieu_cong_van_den ? excel.tai_lieu_cong_van_den.trim() : null,
            levelBaomatOfficialDispatchDoc: excel.level_baomat_tai_lieu_cong_van_den ? excel.level_baomat_tai_lieu_cong_van_den.trim() : null
          });
        }
      });
    }
    return peelOffDatas;
  }

  removeNumber(object) {
    Object.keys(object).map(k => {
      if (object[k] == null || object[k] === undefined) {
        object[k] = '';
      } else {
        object[k] = this.excelHelper.toTrimmedString(object[k]);
      }
    });
  }

  validateFileExcel(excelData, listData: any) {
    let result = false;
    if (listData.length === 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Cảnh báo lỗi:',
        detail: 'File không có dữ liệu !'
      });
      return result;
    }

    if (listData.length > 1000) {
      this.messageService.add({
        severity: 'error',
        summary: 'Cảnh báo lỗi:',
        detail: 'File vuợt quá 1000 dòng !'
      });
      return result;
    }

    if (this.validateDataType(excelData) > 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Cảnh báo lỗi:',
        detail: 'File không đúng định dạng!'
      });
      return result;
    }

    result = true;

    return result;
  }

  validateDataType(excelData: any) {
    const firstRowKeys = Object.keys(excelData[0]);
    if (firstRowKeys[0] !== 'STT') {
      return 1;
    }
    if (firstRowKeys[1] !== 'Ma_Thung') {
      return 1;
    }
    if (firstRowKeys[2] !== 'Nguoi_Tao') {
      return 1;
    }
    if (firstRowKeys[3] !== 'Ma_HS') {
      return 1;
    }
    if (firstRowKeys[4] !== 'Ten_HS') {
      return 1;
    }
    if (firstRowKeys[5] !== 'cong_van_di') {
      return 1;
    }
    if (firstRowKeys[6] !== 'tai_lieu_cong_van_di') {
      return 1;
    }
    if (firstRowKeys[7] !== 'level_baomat_tai_lieu_cong_van_di') {
      return 1;
    }
    return excelData.filter(ob => {
      if (
        Object.keys(ob).filter(keyObj => {
          return (
            keyObj !== 'STT' &&
            keyObj !== 'Ma_Thung' &&
            keyObj !== 'Nguoi_Tao' &&
            keyObj !== 'Ma_HS' &&
            keyObj !== 'Ten_HS' &&
            keyObj !== 'cong_van' &&
            keyObj !== 'cong_van_di' &&
            keyObj !== 'tai_lieu_cong_van_di' &&
            keyObj !== 'level_baomat_tai_lieu_cong_van_di' &&
            keyObj !== 'cong_van_den' &&
            keyObj !== 'tai_lieu_cong_van_den' &&
            keyObj !== 'level_baomat_tai_lieu_cong_van_den' &&
            !keyObj.startsWith('__EMPTY')
          );
        }).length <= 0) {
        return false;
      } else {
        return true;
      }
    }).length;
  }

  uploadBtnClick() {
    if (this.file == null || this.isFileUploaded === true) {
      return;
    }
    if (!this.listExcelImport || this.listExcelImport.length < 1 || this.isFileUploaded) {
      this.previewFile(
        () => {
          this.uploadToApi();
        },
        () => {
          this.reset();
        }
      );
    } else {
      this.uploadToApi();
    }
  }

  uploadToApi() {

    this.resetUploadResultStatus();
    const uploadData = cloneDeep(this.listExcelImport);
    this.excelHelper.trimImportNotNull(uploadData);
    // push to api
    this.warehouseService.importFolderOfficialDispatchData(uploadData).subscribe(res => {
      this.handleResponse(res);
    });
  }

  handleResponse(res: any) {
    if (res.status && res.status === 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Cảnh báo lỗi:',
        detail: 'Có lỗi không xác định. Vui lòng thử lại!'
      });
    } else {
      this.uploadResult = res.data.resultDetails;
      if (this.uploadResult) {
        this.totalResultRecord = this.uploadResult.length;
        this.uploadResult = this.excelHelper.generateEachRowReport(this.uploadResult, Constants.IMPORT_TYPE.OFFICIAL_DISPATCH);
      } else { this.totalResultRecord = 0; }

      this.totalDocumentsInserted = res.data.result;
      const everyUploadedStatus = this.excelHelper.countStatus(this.uploadResult, Constants.IMPORT_TYPE.OFFICIAL_DISPATCH);
      this.totalDuplicateDocuments = everyUploadedStatus.duplicateCount;
      this.totalDocumentsInExcel = everyUploadedStatus.totalCount;
      this.totalDocumentsHasError = everyUploadedStatus.errorCount;

      this.messageService.add({
        severity: 'info',
        summary: 'Tải lên thành công',
        detail: 'Số bản ghi đã được thêm: ' + this.totalDocumentsInserted + '/' + this.totalDocumentsInExcel
      });
      this.listExcelImport = [];
      this.totalRecords = 0;
      this.showUploadResult();
    }
    this.isFileUploaded = true;
  }

  showUploadResult() {
    this.resultCols = this.fullResultCols;
    this.isShowingResult = true;
    const body = this.resultTable.containerViewChild.nativeElement.getElementsByClassName('ui-table-scrollable-header-box')[0];
    body.style.marginLeft = '0';
  }

  hanldeFilterTable(listData: any) {
    return listData;
  }

  reset() {
    this.listExcelImport = null;
    if (this.fileInputField) { this.fileInputField.value = ''; }
    this.file = null;
    this.arrayBuffer = null;
    this.isFileLoaded = false;
    this.isFileUploaded = false;
    this.uploadResult = null;
    this.isShowingResult = false;
    this.totalRecords = 0;
    if (this.previewTable) { this.previewTable.first = 0; }
    this.resetUploadResultStatus();
  }

  resetUploadResultStatus() {
    this.totalDocumentsInserted = 0;
    this.totalDuplicateDocuments = 0;
    this.totalDocumentsInExcel = 0;
    this.totalDocumentsHasError = 0;
  }

  paginate(event) {
    this.pageNumber = event.first;
  }

  exportResultToExcel() {
    const data = cloneDeep(this.uploadResult);
    this.excelHelper.getFolderOfficialDispatchResultExcel(data).subscribe(
      (res) => {
        const fileName = 'Ket_qua_tai_len_' + ('0' + new Date().getDate()).slice(-2) + '_'
          + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
          + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + '.xlsx';
        const file: Blob = new Blob([res.body], { type: 'application/xlsx' });
        saveAs(file, fileName);
      }
    );
  }

  downloadFullSample() {
    this.excelHelper.getFolderOfficialDispatchTemplate().subscribe(
      (res) => {
        const fileName = 'File_mau_tai_du_lieu_buoc_hs_cong_van.xlsx';
        const file: Blob = new Blob([res.body], { type: 'application/xlsx' });
        saveAs(file, fileName);
      }
    );
  }

  paginateResult(event) {
    this.resultPageNo = event.first / this.pageSize + 1;
  }

  getResultPagingLabel() {
    let startRecord = 0;
    let endRecord = 0;
    if (this.totalResultRecord > 0) {
      startRecord = (this.resultPageNo - 1) * this.pageSize + 1;
      if (this.resultPageNo * this.pageSize > this.totalResultRecord) {
        endRecord = this.totalResultRecord;
      } else {
        endRecord = this.resultPageNo * this.pageSize;
      }
    }

    return startRecord + ' - ' + endRecord + ' / ' + this.totalResultRecord;
  }

  showResultPopup(resultTable) {
    if (!(this.uploadResult)) {
      return;
    }
    this.isShowingResult = true;
    if (resultTable) { resultTable.first = 0; }
    const body = this.resultTable.containerViewChild.nativeElement.getElementsByClassName('ui-table-scrollable-header-box')[0];
    body.style.marginLeft = '0';
  }

}
