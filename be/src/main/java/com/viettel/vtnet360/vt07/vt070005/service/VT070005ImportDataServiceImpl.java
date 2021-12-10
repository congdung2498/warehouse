package com.viettel.vtnet360.vt07.vt070005.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt07.vt070000.dao.VT070000DAO;
import com.viettel.vtnet360.vt07.vt070002.dao.VT070002DAO;
import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070005.dao.VT070005DAO;
import com.viettel.vtnet360.vt07.vt070005.entity.ImportedDataRow;
import com.viettel.vtnet360.vt07.vt070005.entity.PaymentSummaryDocVO;
import com.viettel.vtnet360.vt07.vt070005.entity.PaymentSummaryVO;
import com.viettel.vtnet360.vt07.vt070005.entity.VoucherDocVO;
import com.viettel.vtnet360.vt07.vt070005.entity.VoucherVO;

@Service
@Transactional(rollbackFor = Exception.class)
public class VT070005ImportDataServiceImpl implements VT070005ImportDataService {
	// region [Declaration & Constructors]
	private final String FULL_IMPORT_TYPE = "full";
	private final String SIMPLE_IMPORT_TYPE = "simple";

	private final int UNKNOWN_ERROR = 0; // cannot insert due to sql or other error

	private final int VOUCHER_DOC_INSERTED = 1;
	private final int PAYMENT_SUMMARY_DOC_INSERTED = 2;

	private final int DUPLICATE_VOUCHER = -1;
	private final int DUPLICATE_VOUCHER_DOC = -2;

	private final int DUPLICATE_PAYMENT_SUMMARY = -3;
	private final int DUPLICATE_PAYMENT_SUMMARY_DOC = -4;

	private final int EMPTY_VOUCHER = -5;
	private final int EMPTY_VOUCHER_DOC = -6;
	private final int EMPTY_PAYMENT_SUMMARY = -7;
	private final int EMPTY_PAYMENT_SUMMARY_DOC = -8;

	private final int TOO_LONG_VOUCHER = -9;
	private final int TOO_LONG_VOUCHER_DOC = -10;

	private final int TOO_LONG_PAYMENT_SUMMARY = -11;
	private final int TOO_LONG_PAYMENT_SUMMARY_DOC = -12;

	private final int INVALID_LEVEL_BAOMAT_VOUCHER_DOC = -13;
	private final int EMPTY_LEVEL_BAOMAT_VOUCHER_DOC = -14;

	private final int INVALID_LEVEL_BAOMAT_PAYMENT_SUMMARY_DOC = -15;
	private final int EMPTY_LEVEL_BAOMAT_PAYMENT_SUMMARY_DOC = -16;

	private final String[] LEVEL_BAOMAT_VALUES = { "M1", "M2", "M3", "M4" };

	private final int INVALID_TYPE = -17;
	private final int EMPTY_TYPE = -18;

	private final int TIN_BOX_NOT_FOUND = -19;
	private final int THIS_USER_NOT_CREATE_THIS_BOX = -20;
	private final int EMPTY_TIN_BOX = -21;
	private final int EMPTY_CREATE_USER = -22;
	private final int EMPTY_FOLDER = -23;
	private final int EMPTY_FOLDER_NAME = -24;
	private final int FOLDER_NOT_FOUND = -25;
	private final int DUPLICATE_FOLDER_CODE = -118;
	private final int TOO_LONG_FOLDER_NAME = -119;
	private final int TOO_LONG_FOLDER_CODE = -120;
	private final int NOT_EXIST_FOLDER_IN_BOX = -121;

	private final int FOLDER_VOUCHER = 2; // loai buoc ho so chung tu
	private final int FOLDER_PROJECT = 1; // loai buoc ho so dự án

	private final int VOUCHER_TYPE = 4; // chung tu
	private final int PAYMENT_SUMMARY_DOC_TYPE = 5; // tong hop thanh toan
	private final int VOUCHER_DOC_TYPE = 6; // so chung tu ghi so

	private final String VOUCHER_TYPE_VALUE = "Chứng từ";
	private final String PAYMENT_SUMMARY_DOC_TYPE_VALUE = "Bảng TH thanh toán";
	private final String VOUCHER_DOC_TYPE_VALUE = "Chứng từ ghi sổ";

	private final int[] TYPE_VALUES = { 4, 5, 6 };

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	VT070005DAO vt070005dao;
	@Autowired
	VT070000DAO vt070000dao;
	@Autowired
	VT070002DAO vt070002dao;
	@Autowired
	Properties linkTemplateExcel;

	// endregion

	// region Override
	@Override
	public Workbook getExcelTemplate(String propertyName, Collection<GrantedAuthority> userRoles) {
		Workbook wb = null;
		String excelFilePath = linkTemplateExcel.getProperty(propertyName);
		File file = null;
		try {
			Resource resource = new ClassPathResource(excelFilePath);
			file = resource.getFile();
			String extension = FilenameUtils.getExtension(file.getName());
			FileInputStream inputStream = new FileInputStream(file);

			if ("xls".equals(extension)) {
				wb = new HSSFWorkbook(inputStream);
			} else {
				wb = new XSSFWorkbook(inputStream);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return wb;

	}

	@Override
	public Workbook bindResultDataToTemplate(List<ImportedDataRow> dataRows, Workbook template, String importType,
			Collection<GrantedAuthority> userRoles) {
		int startRow = 1;

		Sheet worksheet = template.getSheetAt(0);

		for (int i = 0; i < dataRows.size(); i++) {
			ImportedDataRow dataRow = dataRows.get(i);
			Row row = worksheet.createRow(startRow++);
			int startCol = 0;

			Cell stt = row.createCell(startCol++);
			stt.setCellValue(i + 1);

			if (importType == FULL_IMPORT_TYPE) {
				Cell tinBoxQr = row.createCell(startCol++);
				tinBoxQr.setCellValue(dataRow.getTinBoxQrCode());

				Cell folderQr = row.createCell(startCol++);
				folderQr.setCellValue(dataRow.getFolderQrCode());

				Cell folderName = row.createCell(startCol++);
				folderName.setCellValue(dataRow.getFolderName());

				Cell createUser = row.createCell(startCol++);
				createUser.setCellValue(dataRow.getCreateUser());
			}

			if (importType == SIMPLE_IMPORT_TYPE) {
				Cell folderQrCode = row.createCell(startCol++);
				folderQrCode.setCellValue(dataRow.getFolderQrCode());
			}

			Cell type = row.createCell(startCol++);
			type.setCellValue(convertTypeValue(dataRow.getType()));

			Cell name = row.createCell(startCol++);
			name.setCellValue(dataRow.getName());

			Cell nameDoc = row.createCell(startCol++);
			nameDoc.setCellValue(dataRow.getDocName());

			Cell levelBaomatDoc = row.createCell(startCol++);
			levelBaomatDoc.setCellValue(dataRow.getLevelBaomatDoc());

			Cell rowReport = row.createCell(startCol);
			rowReport.setCellValue(dataRow.getRowReport());
		}

		return template;
	}

	@Override
	public Object importFullData(List<ImportedDataRow> dataRows, Collection<GrantedAuthority> userRoles) {
		int importResult = 0;
		int unit = getUnit(userRoles);
		try {
			for (int i = 0; i < dataRows.size(); i++) {
				ImportedDataRow row = dataRows.get(i);
				row = trimTextData(row);
				row.setUnit(unit);
				validateDataRow(row, FULL_IMPORT_TYPE, unit);
				if (!row.isCriticalError()) {
					row = importDataRow(row, FULL_IMPORT_TYPE, unit);
				}
				importResult += row.getRowAffected();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		int finalResult = importResult;

		return new Object() {
			@SuppressWarnings("unused")
			public int result = finalResult;
			@SuppressWarnings("unused")
			public List<ImportedDataRow> resultDetails = dataRows;
		};
	}

	@Override
	public Object importSimpleData(List<ImportedDataRow> dataRows, Collection<GrantedAuthority> userRoles) {
		int unit = getUnit(userRoles);

		int importResult = 0;
		try {
			for (int i = 0; i < dataRows.size(); i++) {
				ImportedDataRow row = dataRows.get(i);
				row = trimTextData(row);
				row.setUnit(unit);
				validateDataRow(row, SIMPLE_IMPORT_TYPE, unit);
				if (!row.isCriticalError()) {
					row = importDataRow(row, SIMPLE_IMPORT_TYPE, unit);
				}
				importResult += row.getRowAffected();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		int finalResult = importResult;

		return new Object() {
			@SuppressWarnings("unused")
			public int result = finalResult;
			@SuppressWarnings("unused")
			public List<ImportedDataRow> resultDetails = dataRows;
		};

	}

	// endregion

	// region private

	private int getUnit(Collection<GrantedAuthority> userRoles) {
		for (GrantedAuthority temp : userRoles) {
			// if user had role VIEW ALL TINBOX
			if (Constant.PMQT_ROLE_WAREHOUSE_GROUP.equalsIgnoreCase(temp.getAuthority())) {
				return 1;
			}
		}
		return 0;
	}

	private ImportedDataRow importDataRow(ImportedDataRow row, String importType, int unit) {
		if (row.getType() == 4 || row.getType() == 6) {
			importDataRowByVoucherType(row, importType, unit);
			if (row.getRowAffected() > 0) {
				if (unit == 1) {
					vt070005dao.bindVoucherToFolderGroup(row.getFolderId(), row.getVoucherId(), row.getCreateUser());
				} else {
					vt070005dao.bindVoucherToFolder(row.getFolderId(), row.getVoucherId(), row.getCreateUser());
				}
			}
		}

		if (row.getType() == 5) {
			importDataRowByPaymentSummaryType(row, importType, unit);
			if (row.getRowAffected() > 0) {
				if (unit == 1) {
					vt070005dao.bindPaymentSummaryToFolderGroup(row.getFolderId(), row.getPaymentSummaryId(),
							row.getCreateUser());
				} else {
					vt070005dao.bindPaymentSummaryToFolder(row.getFolderId(), row.getPaymentSummaryId(),
							row.getCreateUser());
				}
			}
		}

		return row;
	}

	private ImportedDataRow importDataRowByPaymentSummaryType(ImportedDataRow row, String importType, int unit) {
		if (isOkToInsertPaymentSummary(row)) {
			insertPaymentSummary(row, importType, unit);
		}

		if (row.getPaymentSummaryId() <= 0) {
			return row;
		}

		if (isOkToInsertPaymentSummaryDoc(row)) {
			insertPaymentSummaryDoc(row, importType, unit, row.getPaymentSummaryId());
		}

		return row;
	}

	private ImportedDataRow importDataRowByVoucherType(ImportedDataRow row, String importType, int unit) {
		if (isOkToInsertVoucher(row)) {
			insertVoucher(row, importType, unit);
		}

		if (row.getVoucherId() <= 0) {
			return row;
		}
		if (isOkToInsertVoucherDoc(row)) {
			insertVoucherDoc(row, importType, unit, row.getVoucherId());
		}

		return row;
	}

	private void insertPaymentSummary(ImportedDataRow row, String importType, int unit) {
		PaymentSummaryVO paymentSummaryVO = new PaymentSummaryVO();
		paymentSummaryVO.setFolderId(row.getFolderId());
		paymentSummaryVO.setName(row.getName());
		paymentSummaryVO.setStatus(1);

		long voucherIdExistedId = unit == 1 ? vt070005dao.getPaymentSummaryIdByNameGroup(paymentSummaryVO.getName())
				: vt070005dao.getPaymentSummaryIdByName(paymentSummaryVO.getName());

		if (voucherIdExistedId < 0) {
			if (importType == FULL_IMPORT_TYPE) {
				paymentSummaryVO.setFolderId(row.getFolderId());
			}
			if (unit == 1) {
				vt070005dao.createPaymentSummary(paymentSummaryVO);
			} else {
				vt070005dao.createPaymentSummary(paymentSummaryVO);
			}
			voucherIdExistedId = paymentSummaryVO.getPaymentSummaryId();
		}

		row.setPaymentSummaryId(voucherIdExistedId);
	}

	private void insertVoucher(ImportedDataRow row, String importType, int unit) {
		VoucherVO voucherVO = new VoucherVO();
		voucherVO.setFolderId(row.getFolderId());
		voucherVO.setName(row.getName());
		voucherVO.setStatus(1);
		voucherVO.setType(row.getType());

		long voucherIdExistedId = unit == 1 ? vt070005dao.getVoucherIdByNameGroup(voucherVO.getName())
				: vt070005dao.getVoucherIdByName(voucherVO.getName());

		if (voucherIdExistedId < 0) {
			if (importType == FULL_IMPORT_TYPE) {
				voucherVO.setFolderId(row.getFolderId());
			}
			if (unit == 1) {
				vt070005dao.createVoucherGroup(voucherVO);
			} else {
				vt070005dao.createVoucher(voucherVO);
			}
			voucherIdExistedId = voucherVO.getVoucherId();
		}
		row.setVoucherId(voucherIdExistedId);
	}

	private void insertPaymentSummaryDoc(ImportedDataRow row, String importType, int unit, long paymentSummaryId) {

		PaymentSummaryDocVO paymentSummaryDoc = new PaymentSummaryDocVO();
		paymentSummaryDoc.setFolderId(row.getFolderId());
		paymentSummaryDoc.setName(row.getDocName());
		paymentSummaryDoc.setType(row.getType());
		paymentSummaryDoc.setPaymentSummaryId(paymentSummaryId);
		paymentSummaryDoc.setLevelBaomat(row.getLevelBaomatDoc());

		long voucherDocId = unit == 1
				? vt070005dao.getPaymentSummaryDocIdByPaymentSummaryIdAndNameGroup(paymentSummaryId,
						paymentSummaryDoc.getName())
				: vt070005dao.getPaymentSummaryDocIdByPaymentSummaryIdAndName(paymentSummaryId,
						paymentSummaryDoc.getName());

		if (voucherDocId > 0) {
			row.insertErrorCode(DUPLICATE_PAYMENT_SUMMARY_DOC);
			row.setCriticalError(true);
			return;
		}
		if (importType == FULL_IMPORT_TYPE) {
			paymentSummaryDoc.setFolderId(row.getFolderId());
		}
		long insertPrjDocResult = unit == 1 ? vt070005dao.createPaymentSummaryDocGroup(paymentSummaryDoc)
				: vt070005dao.createPaymentSummaryDoc(paymentSummaryDoc);
		row = raiseDocAffected(row, insertPrjDocResult, PAYMENT_SUMMARY_DOC_INSERTED);
	}

	private void insertVoucherDoc(ImportedDataRow row, String importType, int unit, long voucherId) {

		VoucherDocVO voucherDocVO = new VoucherDocVO();
		voucherDocVO.setFolderId(row.getFolderId());
		voucherDocVO.setName(row.getDocName());
		voucherDocVO.setType(row.getType());
		voucherDocVO.setVoucherId(voucherId);
		voucherDocVO.setLevelBaomat(row.getLevelBaomatDoc());

		long voucherDocId = unit == 1
				? vt070005dao.getVoucherDocIdByVoucherIdAndNameGroup(voucherId, voucherDocVO.getName())
				: vt070005dao.getVoucherDocIdByVoucherIdAndName(voucherId, voucherDocVO.getName());

		if (voucherDocId > 0) {
			row.insertErrorCode(DUPLICATE_VOUCHER_DOC);
			row.setCriticalError(true);
			return;
		}
		if (importType == FULL_IMPORT_TYPE) {
			voucherDocVO.setFolderId(row.getFolderId());
		}
		long insertPrjDocResult = unit == 1 ? vt070005dao.creatVoucherDocGroup(voucherDocVO)
				: vt070005dao.creatVoucherDoc(voucherDocVO);
		row = raiseDocAffected(row, insertPrjDocResult, VOUCHER_DOC_INSERTED);
	}

	private ImportedDataRow raiseDocAffected(ImportedDataRow row, long affectedRow, int insertedCode) {
		if (affectedRow > 0) {
			row.insertErrorCode(insertedCode);
			row.setRowAffected(row.getRowAffected() + 1);
		} else {
			row.insertErrorCode(UNKNOWN_ERROR);
		}

		return row;
	}

	private ImportedDataRow trimTextData(ImportedDataRow row) {
		String name = row.getName().trim();
		String DocName = row.getDocName().trim();
		String levelBaomatDoc = row.getLevelBaomatDoc().trim();
		String createUser = row.getCreateUser() != null ? row.getCreateUser().trim() : null;
		String folderQrCode = row.getFolderQrCode() != null ? row.getFolderQrCode().trim() : null;
		String tinBoxQrCode = row.getTinBoxQrCode() != null ? row.getTinBoxQrCode().trim() : null;
		String foldeName = row.getFolderName() != null ? row.getFolderName().trim() : null;

		row.setDocName(DocName);
		row.setName(name);
		row.setLevelBaomatDoc(levelBaomatDoc);
		row.setCreateUser(createUser);
		row.setFolderName(foldeName);
		row.setFolderQrCode(folderQrCode);
		row.setTinBoxQrCode(tinBoxQrCode);
		return row;
	}

	private boolean hasError(int errorCheck, List<Integer> errorList) {
		boolean result = false;

		for (int error : errorList) {
			if (error == errorCheck) {
				result = true;
			}
		}
		return result;
	}

	private boolean isOkValidateFolderSimple(ImportedDataRow row) {
		if (hasError(EMPTY_FOLDER, row.getErrorCodes()))
			return false;
		if (hasError(FOLDER_NOT_FOUND, row.getErrorCodes()))
			return false;
		return true;
	}

	private boolean isOkToInsertPaymentSummary(ImportedDataRow row) {
		if (row.isCriticalError())
			return false;
		if (StringUtils.isEmpty(row.getName()))
			return false;
		if (hasError(EMPTY_PAYMENT_SUMMARY, row.getErrorCodes()))
			return false;
		if (hasError(TOO_LONG_PAYMENT_SUMMARY, row.getErrorCodes()))
			return false;
		if (hasError(DUPLICATE_PAYMENT_SUMMARY, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_TYPE, row.getErrorCodes()))
			return false;
		if (hasError(INVALID_TYPE, row.getErrorCodes()))
			return false;
		return true;
	}

	private boolean isOkToInsertVoucher(ImportedDataRow row) {
		if (row.isCriticalError())
			return false;
		if (StringUtils.isEmpty(row.getName()))
			return false;
		if (hasError(EMPTY_VOUCHER, row.getErrorCodes()))
			return false;
		if (hasError(TOO_LONG_VOUCHER, row.getErrorCodes()))
			return false;
		if (hasError(DUPLICATE_VOUCHER, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_TYPE, row.getErrorCodes()))
			return false;
		if (hasError(INVALID_TYPE, row.getErrorCodes()))
			return false;
		return true;
	}

	private boolean isOkToInsertPaymentSummaryDoc(ImportedDataRow row) {
		if (StringUtils.isEmpty(row.getDocName()))
			return false;
		if (hasError(EMPTY_PAYMENT_SUMMARY_DOC, row.getErrorCodes()))
			return false;
		if (hasError(TOO_LONG_PAYMENT_SUMMARY_DOC, row.getErrorCodes()))
			return false;
		if (hasError(DUPLICATE_PAYMENT_SUMMARY_DOC, row.getErrorCodes()))
			return false;
		if (hasError(INVALID_LEVEL_BAOMAT_PAYMENT_SUMMARY_DOC, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_LEVEL_BAOMAT_PAYMENT_SUMMARY_DOC, row.getErrorCodes()))
			return false;
		return true;
	}

	private boolean isOkToInsertVoucherDoc(ImportedDataRow row) {
		if (row.isCriticalError())
			return false;
		if (StringUtils.isEmpty(row.getDocName()))
			return false;
		if (hasError(EMPTY_VOUCHER_DOC, row.getErrorCodes()))
			return false;
		if (hasError(TOO_LONG_VOUCHER_DOC, row.getErrorCodes()))
			return false;
		if (hasError(DUPLICATE_VOUCHER_DOC, row.getErrorCodes()))
			return false;
		if (hasError(INVALID_LEVEL_BAOMAT_VOUCHER_DOC, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_LEVEL_BAOMAT_VOUCHER_DOC, row.getErrorCodes()))
			return false;
		return true;
	}

	private String convertTypeValue(int type) {
		if (type == VOUCHER_TYPE) {
			return VOUCHER_TYPE_VALUE;
		}
		if (type == PAYMENT_SUMMARY_DOC_TYPE) {
			return PAYMENT_SUMMARY_DOC_TYPE_VALUE;
		}
		if (type == VOUCHER_DOC_TYPE) {
			return VOUCHER_DOC_TYPE_VALUE;
		}
		return "";
	}
	// endregion

	// region private validate

	private void validateDataRow(ImportedDataRow row, String importType, int unit) {
		if (importType == FULL_IMPORT_TYPE) {
			row = validateTinBox(row, unit);
			row = validateCreateUser(row, unit);
			row = validateFolder(row, unit);
		}
		if (importType == SIMPLE_IMPORT_TYPE) {
			row = validateFolderSimple(row, unit);
			if (!isOkValidateFolderSimple(row)) {
				return;
			}
		}
		if (row.getType() == 4 || row.getType() == 6) {
			validateVoucher(row);
			validateVoucherDoc(row);
			validateLevelBaoMatVoucherDoc(row);
		}

		if (row.getType() == 5) {
			validatePaymentSummary(row);
			validatePaymentSummaryDoc(row);
			validateLevelPaymentSummaryDoc(row);
		}
		validateType(row);
	}

	private ImportedDataRow validateTinBox(ImportedDataRow row, int org) {
		String tinBoxQrCode = row.getTinBoxQrCode();
		int unit = row.getUnit();

		if (StringUtils.isEmpty(tinBoxQrCode)) {
			row.insertErrorCode(EMPTY_TIN_BOX);
			row.setCriticalError(true);
		} else {
			long tinBoxId = org == 1 ? vt070000dao.getTinBoxIdGroup(tinBoxQrCode, unit)
					: vt070000dao.getTinBoxId(tinBoxQrCode, unit);

			if (tinBoxId < 0) {
				row.insertErrorCode(TIN_BOX_NOT_FOUND);
				row.setCriticalError(true);
			} else {
				row.setTinBoxId(tinBoxId);
			}
		}

		return row;
	}

	private ImportedDataRow validateCreateUser(ImportedDataRow row, int unit) {
		String createUser = row.getCreateUser();

		if (StringUtils.isEmpty(createUser)) {
			row.insertErrorCode(EMPTY_CREATE_USER);
			row.setCriticalError(true);
		} else {
			if (row.getTinBoxId() > 0) {
				int checkCreateUser = unit == 1 ? vt070000dao.checkTinBoxCreateUserGroup(row.getTinBoxId(), createUser)
						: vt070000dao.checkTinBoxCreateUser(row.getTinBoxId(), createUser);

				if (checkCreateUser == 0) {
					row.insertErrorCode(THIS_USER_NOT_CREATE_THIS_BOX);
					row.setCriticalError(true);
				}
			}
		}
		return row;
	}

	private ImportedDataRow validateFolder(ImportedDataRow row, int org) {
		String tinBoxQrCode = row.getTinBoxQrCode();
		String folderQrCode = row.getFolderQrCode();
		long tinBoxId = row.getTinBoxId();
		String folderName = row.getFolderName();
		int unit = row.getUnit();

		int folderType = FOLDER_VOUCHER;

		if (StringUtils.isEmpty(folderQrCode)) {
			row.insertErrorCode(EMPTY_FOLDER);
			row.setCriticalError(true);
		} else if (folderQrCode.length() > 255) {
			row.insertErrorCode(TOO_LONG_FOLDER_CODE);
			row.setCriticalError(true);
		} else if (folderName.length() > 255) {
			row.insertErrorCode(TOO_LONG_FOLDER_NAME);
			row.setCriticalError(true);
		} else {
			List<FolderDetail> folders = org == 1 ? vt070000dao.getFolderByQRCodeGroup(folderQrCode)
					: vt070000dao.getFolderByQRCode(folderQrCode);
			long folderId = -1;
			if(folders != null && folders.size() > 0) {
				FolderDetail folder = folders.get(0);
				if(folder.getType() != folderType) {
					row.insertErrorCode(DUPLICATE_FOLDER_CODE);
					row.setCriticalError(true);
				} else if (folder.getTinBoxId() <= 0 || folder.getTinBoxId() != row.getTinBoxId()) {
					row.insertErrorCode(NOT_EXIST_FOLDER_IN_BOX);
					row.setCriticalError(true);
				} else {
					folderId = folder.getFolderId();
				}
			}
			if (folderId < 0 && row.getTinBoxId() > 0  && !row.isCriticalError()) {
				if (StringUtils.isEmpty(folderName)) {
					row.insertErrorCode(EMPTY_FOLDER_NAME);
					row.setCriticalError(true);
				} else {
					FolderDetail folder = new FolderDetail();
					folder.setTinBoxId(tinBoxId);
					folder.setName(folderName);
					folder.setQrCode(folderQrCode);
					folder.setCreateUser(row.getCreateUser());
					folder.setUnit(unit);
					folder.setType(folderType);
					if (org == 1) {
						vt070002dao.insertFolderGroup(folder);
						folderId = folder.getFolderId();
					} else {
						vt070002dao.insertFolder(folder);
						folderId = folder.getFolderId();
					}
					row.setFolderId(folderId);
				}
			} else {
				row.setFolderId(folderId);
			}
		}

		return row;
	}

	private ImportedDataRow validateFolderSimple(ImportedDataRow row, int org) {
		String folderQrCode = row.getFolderQrCode();
		int unit = row.getUnit();

		if (StringUtils.isEmpty(folderQrCode)) {
			row.insertErrorCode(EMPTY_FOLDER);
			row.setCriticalError(true);
		} else {
			List<FolderDetail> folders = org == 1 ? vt070000dao.getFolderByQRCodeGroup(folderQrCode)
					: vt070000dao.getFolderByQRCode(folderQrCode);
			long folderId = -1;
			if(folders != null && folders.size() > 0) {
				FolderDetail folder = folders.get(0);
				if(folder.getType() != FOLDER_PROJECT) {
					row.insertErrorCode(DUPLICATE_FOLDER_CODE);
					row.setCriticalError(true);
				} else {
					folderId = folder.getFolderId();
				}
			}
			
			if (folderId < 0 && !row.isCriticalError()) {
				row.insertErrorCode(FOLDER_NOT_FOUND);
				row.setCriticalError(true);
			} else {
				row.setFolderId(folderId);
			}
		}

		return row;
	}

	private void validatePaymentSummary(ImportedDataRow row) {
		String voucherName = row.getName();
		if (voucherName.length() == 0) {
			row.insertErrorCode(EMPTY_PAYMENT_SUMMARY);
			row.setCriticalError(true);
		} else if (voucherName.length() > 255) {
			row.insertErrorCode(TOO_LONG_PAYMENT_SUMMARY);
			row.setCriticalError(true);
		}

	}

	private void validateVoucher(ImportedDataRow row) {
		String voucherName = row.getName();
		if (voucherName.length() == 0) {
			row.insertErrorCode(EMPTY_VOUCHER);
			row.setCriticalError(true);
		} else if (voucherName.length() > 255) {
			row.insertErrorCode(TOO_LONG_VOUCHER);
			row.setCriticalError(true);
		}

	}

	private void validateVoucherDoc(ImportedDataRow row) {

		String voucherDocName = row.getDocName();
		if (voucherDocName.length() == 0) {
			row.insertErrorCode(EMPTY_VOUCHER_DOC);
			row.setCriticalError(true);
		} else if (voucherDocName.length() > 255) {
			row.insertErrorCode(TOO_LONG_VOUCHER_DOC);
			row.setCriticalError(true);
		}
	}

	private void validatePaymentSummaryDoc(ImportedDataRow row) {

		String voucherDocName = row.getDocName();
		if (voucherDocName.length() == 0) {
			row.insertErrorCode(EMPTY_PAYMENT_SUMMARY_DOC);
			row.setCriticalError(true);
		} else if (voucherDocName.length() > 255) {
			row.insertErrorCode(TOO_LONG_PAYMENT_SUMMARY_DOC);
			row.setCriticalError(true);
		}
	}

	private ImportedDataRow validateType(ImportedDataRow row) {
		if (row.getType() == null) {
			row.insertErrorCode(EMPTY_TYPE);
			return row;
		}
		int type = row.getType();

		boolean contains = IntStream.of(TYPE_VALUES).anyMatch(x -> x == type);

		if (!contains) {
			row.insertErrorCode(INVALID_TYPE);
			row.setCriticalError(true);
		}
		return row;
	}

	private ImportedDataRow validateLevelPaymentSummaryDoc(ImportedDataRow row) {
		String levelBaoMat = row.getLevelBaomatDoc();

		if (StringUtils.isEmpty(levelBaoMat)) {
			row.insertErrorCode(EMPTY_LEVEL_BAOMAT_PAYMENT_SUMMARY_DOC);
			row.setCriticalError(true);
		}
		if (checkLevelBaoMat(levelBaoMat)) {
			row.insertErrorCode(INVALID_LEVEL_BAOMAT_PAYMENT_SUMMARY_DOC);
			row.setCriticalError(true);
		}
		return row;
	}

	private ImportedDataRow validateLevelBaoMatVoucherDoc(ImportedDataRow row) {
		String levelBaoMat = row.getLevelBaomatDoc();

		if (StringUtils.isEmpty(levelBaoMat)) {
			row.insertErrorCode(EMPTY_LEVEL_BAOMAT_VOUCHER_DOC);
			row.setCriticalError(true);
		}
		if (checkLevelBaoMat(levelBaoMat)) {
			row.insertErrorCode(INVALID_LEVEL_BAOMAT_VOUCHER_DOC);
			row.setCriticalError(true);
		}
		return row;
	}

	private boolean checkLevelBaoMat(String levelBaoMat) {
		if (StringUtils.isEmpty(levelBaoMat)) {
			return false;
		}
		return !Arrays.stream(LEVEL_BAOMAT_VALUES).anyMatch(levelBaoMat::equals);
	}
	// endregion
}
