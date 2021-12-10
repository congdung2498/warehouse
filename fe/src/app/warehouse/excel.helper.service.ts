import { Injectable } from '@angular/core';
import { of } from 'rxjs/observable/of';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TokenStorage } from '../shared/token.storage';
import { Constants } from '../shared/containts';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ExcelHelperService {

  notChar: RegExp = /[%&*\\'<>]/g; // remove the character '_' to allow it
  SIMPLE_DATA = 'SIMPLE_DATA';
  FULL_DATA = 'FULL_DATA';

  constructor(private http: HttpClient, private tokenService: TokenStorage) {
  }

  validateUploadDataRow(row: any, type: string) {
    const result = { valid: false, msg: '' };

    if (type === this.FULL_DATA) {

      if (row.tinBoxQrCode === '' || row.tinBoxQrCode.length > 255) {
        result.msg = 'Mã thùng không được để trống hoặc độ dài quá 255 ký tự.';
        return result;
      }

      if (this.notChar.test(row.tinBoxQrCode)) {
        result.msg = 'Mã thùng không được chứa các ký tự đặc biệt.';
        return result;
      }

      if (row.createUser === '' || row.createUser.length > 255) {
        result.msg = 'Tên người tạo không được để trống hoặc độ dài quá 255 ký tự.';
        return result;
      }

      if (this.notChar.test(row.createUser)) {
        result.msg = 'Tên người tạo không được chứa các ký tự đặc biệt.';
        return result;
      }

      if (row.folderQrCode === '' || row.folderQrCode.length > 255) {
        result.msg = 'Mã hồ sơ không được để trống hoặc độ dài quá 255 ký tự.';
        return result;
      }

      if (this.notChar.test(row.folderQrCode)) {
        result.msg = 'Mã hồ sơ không được chứa các ký tự đặc biệt.';
        return result;
      }

    }

    if (row.projectName === '' || row.projectName.length > 255) {
      result.msg = 'Tên dự án không được để trống hoặc độ dài quá 255 ký tự.';
      return result;
    }

    if (this.notChar.test(row.projectName)) {
      result.msg = 'Tên dự án không được chứa các ký tự đặc biệt.';
      return result;
    }

    if (row.projectDoc.length > 255) {
      result.msg = 'Tài liệu dự án độ dài quá 255 ký tự.';
      return result;
    }

    if (this.notChar.test(row.projectDoc)) {
      result.msg = 'Tài liệu dự án không được chứa các ký tự đặc biệt.';
      return result;
    }

    if (row.packageDoc !== '' && (row.packageName === '' || row.packageName.length > 255)) {
      result.msg = 'Tên gói thầu của tài liệu không được để trống hoặc độ dài quá 255 ký tự.';
      return result;
    }

    if (this.notChar.test(row.packageName)) {
      result.msg = 'Tên gói thầu của tài liệu không được chứa các ký tự đặc biệt.';
      return result;
    }

    if (row.packageDoc.length > 255) {
      result.msg = 'Tài liệu gói thầu độ dài quá 255 ký tự.';
      return result;
    }

    if (this.notChar.test(row.packageDoc)) {
      result.msg = 'Tài liệu gói thầu không được chứa các ký tự đặc biệt.';
      return result;
    }

    if ((row.contractDoc !== '' || row.constructionDoc !== '') && (row.contractName === '' || row.contractName.length > 255)) {
      result.msg = 'Tên hợp đồng của tài liệu không được để trống hoặc độ dài quá 255 ký tự.';
      return result;
    }

    if (this.notChar.test(row.contractName)) {
      result.msg = 'Tên hợp đồng của tài liệu không được chứa các ký tự đặc biệt.';
      return result;
    }

    if (row.contractDoc.length > 255) {
      result.msg = 'Tài liệu hợp đồng độ dài quá 255 ký tự.';
      return result;
    }

    if (this.notChar.test(row.contractDoc)) {
      result.msg = 'Tài liệu hợp đồng không được chứa các ký tự đặc biệt.';
      return result;
    }

    if (row.constructionDoc !== '' && (row.constructionName === '' || row.constructionName.length > 255)) {
      result.msg = 'Tên công trình của tài liệu không được để trống hoặc độ dài quá 255 ký tự.';
      return result;
    }

    if (this.notChar.test(row.constructionName)) {
      result.msg = 'Tên công trình của tài liệu không được chứa các ký tự đặc biệt.';
      return result;
    }

    if (row.constructionDoc.length > 255) {
      result.msg = 'Tài liệu công trình độ dài quá 255 ký tự.';
      return result;
    }

    if (this.notChar.test(row.constructionDoc)) {
      result.msg = 'Tài liệu công trình không được chứa các ký tự đặc biệt.';
      return result;
    }

    result.valid = true;
    return result;
  }

  labelFolderProjectError(error) {
    let statusLabel = '';

    switch (error) {
      // case 1:
      //   statusLabel = "Đã thêm tài liệu dự án.";
      //   break;
      // case 2:
      //   statusLabel = "Đã thêm tài liệu gói thầu.";
      //   break;
      // case 3:
      //   statusLabel = "Đã thêm tài liệu hợp đồng.";
      //   break;
      // case 4:
      //   statusLabel = "Đã thêm tài liệu công trình.";
      //   break;
      case 0:
        statusLabel = 'Lỗi không xác định. ';
        break;
      case -1:
        statusLabel = 'Tài liệu dự án: Trùng. ';
        break;
      case -2:
        statusLabel = 'Tài liệu gói thầu: Trùng. ';
        break;
      case -3:
        statusLabel = 'Tài liệu hợp đồng: Trùng. ';
        break;
      case -4:
        statusLabel = 'Tài liệu công trình: Trùng. ';
        break;
      case -5:
        statusLabel = 'Gói thầu: Không được để trống. ';
        break;
      case -6:
        statusLabel = 'Hợp đồng: Không được để trống. ';
        break;
      case -7:
        statusLabel = 'Công trình: Không được để trống. ';
        break;
      case -8:
        statusLabel = 'Tài liệu dự án: Không được quá 255 ký tự. ';
        break;
      case -9:
        statusLabel = 'Gói thầu: Không được quá 255 ký tự. ';
        break;
      case -10:
        statusLabel = 'Tài liệu gói thầu: Không được quá 255 ký tự. ';
        break;
      case -11:
        statusLabel = 'Hợp đồng: Không được quá 255 ký tự. ';
        break;
      case -12:
        statusLabel = 'Tài liệu hợp đồng: Không được quá 255 ký tự. ';
        break;
      case -13:
        statusLabel = 'Công trình: Không được quá 255 ký tự. ';
        break;
      case -14:
        statusLabel = 'Tài liệu công trình: Không được quá 255 ký tự. ';
        break;
      // critical validate error
      case -101:
        statusLabel = 'Mã thùng: Không tồn tại. ';
        break;
      case -102:
        statusLabel = 'Mã hồ sơ: Không thấy trong thùng. ';
        break;
      case -103:
        statusLabel = 'Người tạo thùng: Thùng không do người này tạo. ';
        break;
      case -104:
        statusLabel = 'Mã thùng: Không được để trống. ';
        break;
      case -105:
        statusLabel = 'Người tạo thùng: Không được để trống. ';
        break;
      case -106:
        statusLabel = 'Mã hồ sơ: Không được để trống. ';
        break;
      case -107:
        statusLabel = 'Dự án: Không được để trống. ';
        break;
      case -108:
        statusLabel = 'Dự án: Không được quá 255 ký tự. ';
        break;
      // level bao mat
      case -109:
        statusLabel = 'Mức chia sẻ tài liệu dự án: Không tồn tại trên hệ thống. ';
        break;
      case -110:
        statusLabel = 'Mức chia sẻ tài liệu gói thầu: Không tồn tại trên hệ thống. ';
        break;
      case -111:
        statusLabel = 'Mức chia sẻ tài liệu hợp đồng: Không tồn tại trên hệ thống. ';
        break;
      case -112:
        statusLabel = 'Mức chia sẻ tài liệu công trình: Không tồn tại trên hệ thống. ';
        break;
      case -113:
        statusLabel = 'Mức chia sẻ tài liệu dự án: Không được để trống. ';
        break;
      case -114:
        statusLabel = 'Mức chia sẻ tài liệu gói thầu: Không được để trống. ';
        break;
      case -115:
        statusLabel = 'Mức chia sẻ tài liệu hợp đồng: Không được để trống. ';
        break;
      case -116:
        statusLabel = 'Mức chia sẻ tài liệu công trình: Không được để trống. ';
        break;
      case -117:
        statusLabel = 'Tên hồ sơ: Không được để trống. ';
        break;
      case -118:
        statusLabel = 'Mã hồ sơ đã tồn tại trên hệ thống không thỏa mãn loại hồ sơ. ';
        break;
      case -119:
        statusLabel = 'Tên hồ sơ: Không được quá 255 ký tự. ';
        break;
      case -120:
        statusLabel = 'Mã hồ sơ: Không được quá 255 ký tự. ';
        break;
      case -121:
        statusLabel = 'Mã hồ sơ: Không tồn tại trong thùng. ';
        break;
    }

    return statusLabel;
  }

  labelFolderOfficialDispatchError(error) {
    let statusLabel = '';

    switch (error) {
      case 0:
        statusLabel = 'Lỗi không xác định. ';
        break;
      case -1:
        statusLabel = 'Tên công văn: Trùng. ';
        break;
      case -2:
        statusLabel = 'Tài liệu công văn: Trùng. ';
        break;
      case -3:
        statusLabel = 'Tài liệu công văn: Trùng. ';
        break;
      case -5:
        statusLabel = 'Tên công văn: Không được để trống. ';
        break;
      case -6:
        statusLabel = 'Tài liệu công văn: Không được để trống. ';
        break;
      case -7:
        statusLabel = 'Tài liệu công văn: Không được để trống. ';
        break;
      case -8:
        statusLabel = 'Tên công văn: Không được quá 255 ký tự. ';
        break;
      case -9:
        statusLabel = 'Tài liệu công văn: Không được quá 255 ký tự. ';
        break;
      case -10:
        statusLabel = 'Tài liệu công văn: Không được quá 255 ký tự. ';
        break;
      case -11:
        statusLabel = 'Mức chia sẻ tài liệu: Không tồn tại trên hệ thống. ';
        break;
      case -12:
        statusLabel = 'Mức chia sẻ tài liệu: Không tồn tại trên hệ thống. ';
        break;
      case -13:
        statusLabel = 'Mức chia sẻ tài liệu: Không được để trống. ';
        break;
      case -14:
        statusLabel = 'Mức chia sẻ tài liệu: Không được để trống. ';
        break;
      case -15:
        statusLabel = 'Loại công văn: Không xác định. ';
        break;
      case -16:
        statusLabel = 'Loại công văn: Không được để trống. ';
        break;
      case -17:
        statusLabel = 'Mã thùng: Không tồn tại trên hệ thống. ';
        break;
      case -19:
        statusLabel = 'Người tạo thùng: Thùng không do người này tạo. ';
        break;
      case -20:
        statusLabel = 'Mã thùng: Không được để trống. ';
        break;
      case -21:
        statusLabel = 'Người tạo thùng: Không được để trống. ';
        break;
      case -22:
        statusLabel = 'Mã hồ sơ: Không được để trống. ';
        break;
      case -23:
        statusLabel = 'Tên hồ sơ: Không được để trống. ';
        break;
      case -118:
        statusLabel = 'Mã hồ sơ đã tồn tại trên hệ thống không thỏa mãn loại hồ sơ. ';
        break;
      case -119:
        statusLabel = 'Tên hồ sơ: Không được quá 255 ký tự. ';
        break;
      case -120:
        statusLabel = 'Mã hồ sơ: Không được quá 255 ký tự. ';
        break;
      case -121:
        statusLabel = 'Mã hồ sơ: Không tồn tại trong thùng. ';
        break;
    }
    return statusLabel;
  }

  labelFolderVoucherError(error) {
    let statusLabel = '';

    switch (error) {
      case 0:
        statusLabel = 'Lỗi không xác định. ';
        break;
      case -1:
        statusLabel = 'Chứng từ: Trùng. ';
        break;
      case -2:
        statusLabel = 'Tài liệu chứng từ: Trùng. ';
        break;
      case -3:
        statusLabel = 'Chứng từ: Trùng. ';
        break;
      case -4:
        statusLabel = 'Tài liệu chứng từ: Trùng. ';
        break;
      case -5:
        statusLabel = 'Chứng từ: Không được để trống. ';
        break;
      case -6:
        statusLabel = 'Tài liệu chứng từ: Không được để trống. ';
        break;
      case -7:
        statusLabel = 'Chứng từ: Không được để trống. ';
        break;
      case -8:
        statusLabel = 'Tài liệu chứng từ: Không được để trống. ';
        break;
      case -9:
        statusLabel = 'Chứng từ: Không được quá 255 ký tự. ';
        break;
      case -10:
        statusLabel = 'Tài liệu chứng từ: Không được quá 255 ký tự. ';
        break;
      case -11:
        statusLabel = 'Chứng từ: Không được quá 255 ký tự. ';
        break;
      case -12:
        statusLabel = 'Tài liệu chứng từ: Không được quá 255 ký tự. ';
        break;
      case -13:
        statusLabel = 'Mức chia sẻ tài liệu: Không tồn tại trên hệ thống. ';
        break;
      case -14:
        statusLabel = 'Mức chia sẻ tài liệu: Không được để trống. ';
        break;
      case -15:
        statusLabel = 'Mức chia sẻ tài liệu: Không tồn tại trên hệ thống. ';
        break;
      case -16:
        statusLabel = 'Mức chia sẻ tài liệu: Không được để trống. ';
        break;
      case -17:
        statusLabel = 'Loại chứng từ: Không xác định. ';
        break;
      case -18:
        statusLabel = 'Loại chứng từ: Không được để trống. ';
        break;
      case -19:
        statusLabel = 'Mã thùng: Không tồn tại trên hệ thống. ';
        break;
      case -20:
        statusLabel = 'Người tạo thùng: Thùng không do người này tạo. ';
        break;
      case -21:
        statusLabel = 'Mã thùng: Không được để trống. ';
        break;
      case -22:
        statusLabel = 'Người tạo thùng: Không được để trống. ';
        break;
      case -23:
        statusLabel = 'Mã hồ sơ: Không được để trống. ';
        break;
      case -24:
        statusLabel = 'Tên hồ sơ: Không được để trống. ';
        break;
      case -25:
        statusLabel = 'Mã hồ sơ: Không tồn tại trên hệ thống. ';
        break;
      case -118:
        statusLabel = 'Mã hồ sơ đã tồn tại trên hệ thống không thỏa mãn loại hồ sơ. ';
        break;
      case -119:
        statusLabel = 'Tên hồ sơ: Không được quá 255 ký tự. ';
        break;
      case -120:
        statusLabel = 'Mã hồ sơ: Không được quá 255 ký tự. ';
        break;
      case -121:
        statusLabel = 'Mã hồ sơ: Không tồn tại trong thùng. ';
        break;
    }
    return statusLabel;
  }

  hasAnyError(errorCodes: any) {
    let hasError = false;
    if (errorCodes) {

      if (errorCodes.length === 0) { hasError = true; }

      errorCodes.forEach(
        (code) => {
          if (parseInt(code) <= 0) { hasError = true; }
        }
      );
    }

    return hasError;
  }

  generateEachRowReport(uploadResult, importType) {
    for (let i = 0; i < uploadResult.length; i++) {
      const row = uploadResult[i];
      const errorCodes = row.errorCodes;
      row.rowReport = '';
      if (this.hasAnyError(errorCodes)) {

        // if (errorCodes.length === 0) { row.rowReport = 'Lỗi không xác định.'; }

        errorCodes.forEach(
          (code) => {
            switch (importType) {
              case Constants.IMPORT_TYPE.PROJECT:
                row.rowReport += this.labelFolderProjectError(parseInt(code)) + '<br>';
                break;
              case Constants.IMPORT_TYPE.OFFICIAL_DISPATCH:
                row.rowReport += this.labelFolderOfficialDispatchError(parseInt(code)) + '<br>';
                break;
              case Constants.IMPORT_TYPE.VOUCHER:
                row.rowReport += this.labelFolderVoucherError(parseInt(code)) + '<br>';
                break;
            }
          }
        );
      } else {
        row.rowReport = 'Thành công.';
      }
    }

    return uploadResult;
  }

  countUploadedFolderProject(uploadResult) {
    let duplicateCount = 0;
    let errorCount = 0;
    let totalCount = 0;
    let insertedCount = 0;

    if (!uploadResult) {
      return {
        duplicateCount: duplicateCount,
        errorCount: errorCount,
        totalCount: totalCount,
        insertedCount: insertedCount
      };
    }

    for (let i = 0; i < uploadResult.length; i++) {
      const row = uploadResult[i];
      if (row.constructionDoc !== '') { totalCount += 1; }
      if (row.contractDoc !== '') { totalCount += 1; }
      if (row.projectDoc !== '') { totalCount += 1; }
      if (row.packageDoc !== '') { totalCount += 1; }
      insertedCount += row.rowAffected;
      const errorCodes = row.errorCodes;
      if (errorCodes.length > 0) {
        errorCodes.forEach(
          (code) => {
            const err = parseInt(code);
            if (err === -1 || err === -2 || err === -3 || err === -4) { duplicateCount += 1; }
          }
        );
      }
    }

    errorCount = totalCount - duplicateCount - insertedCount;

    return {
      duplicateCount: duplicateCount,
      errorCount: errorCount,
      totalCount: totalCount,
      insertedCount: insertedCount
    };
  }

  countStatus(uploadResult, type?) {
    let duplicateCount = 0;
    let errorCount = 0;
    let totalCount = 0;
    let insertedCount = 0;

    if (!uploadResult) {
      return {
        duplicateCount: duplicateCount,
        errorCount: errorCount,
        totalCount: totalCount,
        insertedCount: insertedCount
      };
    }

    for (let i = 0; i < uploadResult.length; i++) {
      const row = uploadResult[i];
      totalCount += 1;
      insertedCount += row.rowAffected;
      const errorCodes = row.errorCodes;
      if (errorCodes.length > 0) {
        errorCodes.forEach(
          (code) => {
            const err = parseInt(code);

            if (type && type === Constants.IMPORT_TYPE.VOUCHER) {
              if (err === -1 || err === -2 || err === -3 || err === -4) { duplicateCount += 1; }
            } else {
              if (err === -1 || err === -2 || err === -3) { duplicateCount += 1; }
            }
          }
        );
      }
    }

    errorCount = totalCount - duplicateCount - insertedCount;

    return {
      duplicateCount: duplicateCount,
      errorCount: errorCount,
      totalCount: totalCount,
      insertedCount: insertedCount
    };

  }

  changeJsonKeys(dataConvert) {
    return dataConvert.map(item => {
      return {
        tinBoxQrCode: this.toTrimmedString(item.Ma_Thung),
        createUser: this.toTrimmedString(item.Nguoi_Tao),
        folderQrCode: this.toTrimmedString(item.Ma_HS),
        folderName: this.toTrimmedString(item.Ten_HS),
        projectName: this.toTrimmedString(item.Du_An),
        projectDoc: this.toTrimmedString(item.Tai_lieu_du_an),
        packageName: this.toTrimmedString(item.Goi_thau),
        packageDoc: this.toTrimmedString(item.Tai_lieu_goi_thau),
        contractName: this.toTrimmedString(item.Hop_dong),
        contractDoc: this.toTrimmedString(item.Tai_lieu_hop_dong),
        constructionName: this.toTrimmedString(item.Cong_trinh),
        constructionDoc: this.toTrimmedString(item.Tai_lieu_cong_trinh),
        levelBaoMatProject: this.toTrimmedString(item.level_baomat_du_an),
        levelBaoMatPackage: this.toTrimmedString(item.level_baomat_goi_thau),
        levelBaoMatContract: this.toTrimmedString(item.level_baomat_hop_dong),
        levelBaoMatConstruction: this.toTrimmedString(item.level_baomat_cong_trinh),
      };
    });
  }

  toTrimmedString(value) {
    return ('' + value).trim();
  }

  trimImportNotNull(array) {
    if (array) {
      array.forEach(object => {
        Object.keys(object).map(k => {
          if (object[k] == null || object[k] === undefined) {
            object[k] = '';
          } else {
            object[k] = typeof object[k] === 'string' ? object[k].trim() : object[k];
          }
        });
      });
    }
  }

  removeAsterisk(data: any) {
    try {
      let jsonString = JSON.stringify(data);
      jsonString = jsonString.replace(/\*/g, ''); // remove all * in data
      return JSON.parse(jsonString);
    } catch (error) {
      console.log(error);
    }
  }

  formatJSONForXLSX(uploadResult, fileType) {

    const formatedJson = uploadResult.map(obj => {
      const reportTxtForExcel = obj.rowReport.split('<br>').join('\r\n');
      return {
        'Ma_Thung': obj.tinBoxQrCode,
        'Nguoi_Tao': obj.createUser,
        'Ma_HS': obj.folderQrCode,
        'Ten_HS': obj.folderName,
        'Du_An': obj.projectName,
        'Tai_lieu_du_an': obj.projectDoc,
        'Goi_thau': obj.packageName,
        'Tai_lieu_goi_thau': obj.packageDoc,
        'Hop_dong': obj.contractName,
        'Tai_lieu_hop_dong': obj.contractDoc,
        'Cong_trinh': obj.constructionName,
        'Tai_lieu_cong_trinh': obj.constructionDoc,
        'Ket_qua_tai_len': reportTxtForExcel
      };
    });

    formatedJson.forEach(row => {

      if (fileType === this.SIMPLE_DATA) {
        delete row['Ma_Thung'];
        delete row['Nguoi_Tao'];
        delete row['Ma_HS'];
        delete row['Ten_HS'];
      }

    });

    return formatedJson;
  }

  getSimpleUploadTemplate(): Observable<any> {
    const account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/99/3';
    return this.http.post(url, obj, { observe: 'response', responseType: 'blob' });
  }

  getFullUploadTemplate(): Observable<any> {
    const account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/99/4';
    return this.http.post(url, obj, { observe: 'response', responseType: 'blob' });
  }

  getSimpleResultExcel(data: any): Observable<any> {
    const account = this.tokenService.getAccount();

    data.forEach((row: any) => {
      row.rowReport = row.rowReport.split('<br>').join('\r\n');
    });

    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      data: data
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/99/5';
    return this.http.post(url, obj, { observe: 'response', responseType: 'blob' });
  }

  getFullResultExcel(data: any): Observable<any> {
    const account = this.tokenService.getAccount();

    data.forEach((row: any) => {
      row.rowReport = row.rowReport.split('<br>').join('\r\n');
    });

    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      data: data
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070000/99/6';
    return this.http.post(url, obj, { observe: 'response', responseType: 'blob' });
  }

  getOfficialDispatchResultExcel(data: any): Observable<any> {
    const account = this.tokenService.getAccount();

    data.forEach((row: any) => {
      row.rowReport = row.rowReport.split('<br>').join('\r\n');
    });

    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      data: data
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070003/03';
    return this.http.post(url, obj, { observe: 'response', responseType: 'blob' });
  }

  getOfficialDispatchTemplate(): Observable<any> {
    const account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070003/02';
    return this.http.post(url, obj, { observe: 'response', responseType: 'blob' });
  }

  getFolderOfficialDispatchTemplate(): Observable<any> {
    const account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070003/05';
    return this.http.post(url, obj, { observe: 'response', responseType: 'blob' });
  }

  getFolderOfficialDispatchResultExcel(data: any): Observable<any> {
    const account = this.tokenService.getAccount();

    data.forEach((row: any) => {
      row.rowReport = row.rowReport.split('<br>').join('\r\n');
    });

    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      data: data
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070003/06';
    return this.http.post(url, obj, { observe: 'response', responseType: 'blob' });
  }

  getFolderVoucherResultExcel(data: any): Observable<any> {
    const account = this.tokenService.getAccount();

    data.forEach((row: any) => {
      row.rowReport = row.rowReport.split('<br>').join('\r\n');
    });

    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      data: data
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070005/03';
    return this.http.post(url, obj, { observe: 'response', responseType: 'blob' });
  }

  getFolderRetailProjectResultExcel(data: any): Observable<any> {
    const account = this.tokenService.getAccount();

    data.forEach((row: any) => {
      row.rowReport = row.rowReport.split('<br>').join('\r\n');
    });

    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword,
      data: data
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070005/06';
    return this.http.post(url, obj, { observe: 'response', responseType: 'blob' });
  }

  getFolderVoucherTemplate(): Observable<any> {
    const account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070005/02';
    return this.http.post(url, obj, { observe: 'response', responseType: 'blob' });
  }

  getFolderRetailProjectTemplate(): Observable<any> {
    const account = this.tokenService.getAccount();
    const obj: any = {
      securityUsername: account.securityUsername,
      securityPassword: account.securityPassword
    };
    const url = Constants.HOME_URL + 'com/viettel/vtnet360/vt07/vt070005/05';
    return this.http.post(url, obj, { observe: 'response', responseType: 'blob' });
  }

}
