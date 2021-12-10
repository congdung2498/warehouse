import {
  Component,
  OnInit,
  ViewChild,
} from '@angular/core';
import * as cloneDeep from 'lodash/cloneDeep';

import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { Table } from 'primeng/table';
import { Title } from '@angular/platform-browser';
import { SearchingTinboxCondition } from '../../barcode/Entity/searchingTinboxCodition';
// export excel

import * as XLSX from 'xlsx';
import { WarehouseService } from '../warehouse.service';
import { ExcelHelperService } from '../excel.helper.service';
import { saveAs } from 'file-saver';
import { Constants } from '../../shared/containts';

@Component({
  selector: 'app-folder-project-upload',
  templateUrl: './folder-project-upload.component.html',
  styleUrls: ['./folder-project-upload.component.css']
})
export class FolderProjectUploadComponent implements OnInit {
  @ViewChild('myTable')
  myTable: Table;
  loading: boolean;
  navigationSubscription;
  pageNumber = 0;
  totalRecords = 0;
  pageSize = 10;
  status = '1';
  conditionSearch: SearchingTinboxCondition;
  dateCreate: Date;
  name: string;
  mngTextSearch: string;
  typeTinbox: any;
  listTypeTinbox: any[];
  listExcelImport: any[];
  listExcelInitial: any[];
  isChecked = false;
  index: number;
  isShowUpdateDialog: boolean;
  isEdit = true;
  notChar: RegExp = /[%&_*\\'<>]/g;
  colsDto: any[];
  excelFileType = 0; // type 0: full data exel file - type 1: simple data exel file
  selectedFileType = 0;
  cols: any;
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
  @ViewChild('resultTable') resultTable;
  fullCols = [
    { field: '', header: 'STT', width: '50px' },
    { field: 'Ma_Thung', header: 'Mã thùng/hộp', width: '200px' },
    { field: 'Nguoi_Tao', header: 'Người tạo', width: '200px' },
    { field: 'Ma_HS', header: 'Mã HS', width: '200px' },
    { field: 'Ten_HS', header: 'Tên HS', width: '200px' },
    { field: 'Du_An', header: 'Dự án', width: '200px' },
    { field: 'Tai_lieu_du_an', header: 'Tài liệu dự án', width: '200px' },
    { field: 'level_baomat_du_an', header: 'Mức chia sẻ tài liệu dự án', width: '240px' },
    { field: 'Goi_thau', header: 'Gói thầu', width: '200px' },
    { field: 'Tai_lieu_goi_thau', header: 'Tài liệu gói thầu', width: '200px' },
    { field: 'level_baomat_goi_thau', header: 'Mức chia sẻ tài liệu gói thầu', width: '240px' },
    { field: 'Hop_dong', header: 'Hợp đồng', width: '200px' },
    { field: 'Tai_lieu_hop_dong', header: 'Tài liệu hợp đồng', width: '200px' },
    { field: 'level_baomat_hop_dong', header: 'Mức chia sẻ tài liệu hợp đồng', width: '240px' },
    { field: 'Cong_trinh', header: 'Công trình', width: '200px' },
    { field: 'Tai_lieu_cong_trinh', header: 'Tài liệu công trình', width: '200px' },
    { field: 'level_baomat_cong_trinh', header: 'Mức chia sẻ tài liệu công trình', width: '240px' }
  ];

  fullResultCols = [
    { field: '', header: 'STT', width: '50px' },
    { field: 'tinBoxQrCode', header: 'Mã thùng/hộp', width: '200px' },
    { field: 'createUser', header: 'Người tạo', width: '200px' },
    { field: 'folderQrCode', header: 'Mã HS', width: '200px' },
    { field: 'folderName', header: 'Tên HS', width: '350px' },
    { field: 'projectName', header: 'Dự án', width: '350px' },
    { field: 'projectDoc', header: 'Tài liệu dự án', width: '350px' },
    { field: 'levelBaoMatProject', header: 'Mức chia sẻ tài liệu dự án', width: '240px' },
    { field: 'packageName', header: 'Gói thầu', width: '350px' },
    { field: 'packageDoc', header: 'Tài liệu gói thầu', width: '350px' },
    { field: 'levelBaoMatPackage', header: 'Mức chia sẻ tài liệu gói thầu', width: '240px' },
    { field: 'contractName', header: 'Hợp đồng', width: '350px' },
    { field: 'contractDoc', header: 'Tài liệu hợp đồng', width: '350px' },
    { field: 'levelBaoMatContract', header: 'Mức chia sẻ tài liệu hợp đồng', width: '240px' },
    { field: 'constructionName', header: 'Công trình', width: '350px' },
    { field: 'constructionDoc', header: 'Tài liệu công trình', width: '350px' },
    { field: 'levelBaoMatConstruction', header: 'Mức chia sẻ tài liệu công trình', width: '240px' },
    { field: 'rowReport', header: 'Kết quả tải lên', width: '350px' },

  ];
  constructor(
    private messageService: MessageService,
    private router: Router,
    private title: Title,
    private warehouseService: WarehouseService,
    private excelHelper: ExcelHelperService
  ) {
    this.loading = false;
    this.conditionSearch = new SearchingTinboxCondition();
  }

  ngOnInit() {
    this.title.setTitle('Tải tài liệu vào buộc hồ sơ dự án');
    this.selectTableHeader();
  }
  // hanle import file excel
  arrayBuffer: any;
  file: File;
  incomingfile(event) {
    this.file = event.target.files[0];
    this.isFileUploaded = false;
  }

  selectTableHeader() {
    this.selectedFileType = this.excelFileType;
    if (this.excelFileType === 0) {
      this.cols = this.fullCols;
    }
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
    this.selectTableHeader();
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
          // validate file excel
          if (this.validateFileExcel(excelData)) {
            this.listExcelImport = excelData;
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

  validateFileExcel(listData: any) {
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

    if (this.validateDataType(listData) > 0) {
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

  validateDataType(listData: any) {
    const firstRowKeys = Object.keys(listData[0]);
    if (this.excelFileType === 0) {
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

      if (firstRowKeys[5] !== 'Du_An') {
        return 1;
      }

      if (firstRowKeys[6] !== 'Tai_lieu_du_an') {
        return 1;
      }

      if (firstRowKeys[7] !== 'level_baomat_du_an') {
        return 1;
      }
    }

    return listData.filter(ob => {
      if (
        Object.keys(ob).filter(keyObj => {
          return (
            keyObj !== 'STT' &&
            keyObj !== 'Ma_Thung' &&
            keyObj !== 'Nguoi_Tao' &&
            keyObj !== 'Ma_HS' &&
            keyObj !== 'Ten_HS' &&
            keyObj !== 'Du_An' &&
            keyObj !== 'Tai_lieu_du_an' &&
            keyObj !== 'Goi_thau' &&
            keyObj !== 'Tai_lieu_goi_thau' &&
            keyObj !== 'Hop_dong' &&
            keyObj !== 'Tai_lieu_hop_dong' &&
            keyObj !== 'Cong_trinh' &&
            keyObj !== 'Tai_lieu_cong_trinh' &&
            keyObj !== 'level_baomat_du_an' &&
            keyObj !== 'level_baomat_goi_thau' &&
            keyObj !== 'level_baomat_hop_dong' &&
            keyObj !== 'level_baomat_cong_trinh' &&
            !keyObj.startsWith('__EMPTY') &&
            this.excelFileType === 0
          );
        }).length <= 0 &&
        Object.keys(ob).filter(keyObj => {
          return (
            keyObj !== 'STT' &&
            keyObj !== 'Du_An' &&
            keyObj !== 'Tai_lieu_du_an' &&
            keyObj !== 'Goi_thau' &&
            keyObj !== 'Tai_lieu_goi_thau' &&
            keyObj !== 'Hop_dong' &&
            keyObj !== 'Tai_lieu_hop_dong' &&
            keyObj !== 'Cong_trinh' &&
            keyObj !== 'Tai_lieu_cong_trinh' &&
            keyObj !== '__EMPTY' &&
            this.excelFileType === 1
          );
        }).length <= 0
      ) {
        return false;
      } else {
        return true;
      }
    }).length;
  }

  prepareJsonData() {
    if (this.listExcelImport) {
      const excelData = {
        Ma_Thung: '',
        Nguoi_Tao: '',
        Ma_HS: '',
        Ten_HS: '',
        Du_An: '',
        Tai_lieu_du_an: '',
        level_baomat_du_an: '',
        Goi_thau: '',
        Tai_lieu_goi_thau: '',
        level_baomat_goi_thau: '',
        Hop_dong: '',
        Tai_lieu_hop_dong: '',
        level_baomat_hop_dong: '',
        Cong_trinh: '',
        Tai_lieu_cong_trinh: '',
        level_baomat_cong_trinh: ''
      };
      const dataConvert = [];
      for (let i = 0; i < this.listExcelImport.length; i++) {
        if (this.listExcelImport[i]) {
          dataConvert.push(
            Object.assign({ ...excelData }, this.listExcelImport[i])
          );
        }
      }

      const result = this.excelHelper.changeJsonKeys(dataConvert);
      return result;
    }
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
    const uploadData = this.prepareJsonData();

    // push to api
    this.warehouseService.importFolderProjectData(uploadData).subscribe(res => {
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
        this.uploadResult = this.excelHelper.generateEachRowReport(this.uploadResult, Constants.IMPORT_TYPE.PROJECT);
      } else { this.totalResultRecord = 0; }

      this.totalDocumentsInserted = res.data.result;
      const everyUploadedStatus = this.excelHelper.countUploadedFolderProject(this.uploadResult);
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
    this.excelFileType = 0;
    this.selectedFileType = null;
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
    this.excelHelper.getFullResultExcel(data).subscribe(
      (res) => {
        const fileName = 'Ket_qua_tai_len_' + ('0' + new Date().getDate()).slice(-2) + '_'
          + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
          + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + '.xlsx';
        const file: Blob = new Blob([res.body], { type: 'application/xlsx' });
        saveAs(file, fileName);
      }
    );
    // if(this.uploadResult && this.uploadResult.length > 0){
    //   let formatedJson = this.excelHelper.formatJSONForXLSX(this.uploadResult, 'FULL_DATA');
    //   const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(formatedJson);
    //   const wb: XLSX.WorkBook = XLSX.utils.book_new();
    //   let fileName = 'Ket_qua_tai_len_' + ('0' + new Date().getDate()).slice(-2) + '_'
    //   + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
    //   + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's' + '.xlsx';
    //   XLSX.utils.book_append_sheet(wb, ws, 'Kết quả chi tiết');

    //   XLSX.writeFile(wb, fileName);
    // }
  }

  downloadFullSample() {
    this.excelHelper.getFullUploadTemplate().subscribe(
      (res) => {
        const fileName = 'File_mau_tai_du_lieu_buoc_ho_so_du_an.xlsx';
        const file: Blob = new Blob([res.body], { type: 'application/xlsx' });
        saveAs(file, fileName);
      }
    );
    // let sampleJSON:any = [{
    //   "Ma_Thung*": "TH20202020",
    //   "Nguoi_Tao*": "nguyen_van_a",
    //   "Ma_HS*": "HS99992020",
    //   "Du_An*": "Dự án mẫu ABCD1234",
    //   Tai_lieu_du_an: "Tài liệu thuộc dự án cùng dòng",
    //   Goi_thau: "Gói thầu thuộc dự án cùng dòng",
    //   Tai_lieu_goi_thau: "Tài liệu thuộc gói thầu cùng dòng",
    //   Hop_dong: "Hợp đồng thuộc dự án cùng dòng",
    //   Tai_lieu_hop_dong: "Tài liệu thuộc hợp đồng cùng dòng",
    //   Cong_trinh: "Công trình thuộc hợp đồng cùng dòng",
    //   Tai_lieu_cong_trinh: "Tài liệu thuộc công trình cùng dòng",
    // }]
    // const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(sampleJSON);
    // const wb: XLSX.WorkBook = XLSX.utils.book_new();
    // let fileName = 'File_mau_tai_du_lieu_ho_so.xlsx';
    // XLSX.utils.book_append_sheet(wb, ws, 'Dữ liệu mẫu');

    // XLSX.writeFile(wb, fileName);
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

  ngOnDestroy() {
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }
}
