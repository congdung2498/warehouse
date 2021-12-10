package com.viettel.vtnet360.vt07.vt070003.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

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
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070000.dao.VT070000DAO;
import com.viettel.vtnet360.vt07.vt070002.dao.VT070002DAO;
import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070003.dao.VT070003DAO;
import com.viettel.vtnet360.vt07.vt070003.entity.FileUploadInfo;
import com.viettel.vtnet360.vt07.vt070003.entity.ImportedDataRow;

@Service
@Transactional(rollbackFor = Exception.class)
public class VT070003ImportDataServiceImpl implements VT070003ImportDataService {
	// region [Declaration & Constructors]
	private final String FULL_IMPORT_TYPE = "full";
	private final String SIMPLE_IMPORT_TYPE = "simple";

	private final String OFFICIAL_DISPATCH_TRAVELS_DOC = "OFFICIAL_DISPATCH_TRAVELS_DOC";
	private final String INCOMING_OFFICIAL_DISPATCH_DOC = "INCOMING_OFFICIAL_DISPATCH_DOC";

	private final int UNKNOWN_ERROR = 0; // cannot insert due to sql or other error

	private final int OFFICIAL_DISPATCH_TRAVELS_DOC_INSERTED = 2;
	private final int INCOMING_OFFICIAL_DISPATCH_DOC_INSERTED = 3;

	private final int DUPLICATE_OFFICIAL_DISPATCH = -1;
	private final int DUPLICATE_OFFICIAL_DISPATCH_TRAVELS_DOC = -2;
	private final int DUPLICATE_INCOMING_OFFICIAL_DISPATCH_DOC = -3;

	private final int EMPTY_OFFICIAL_DISPATCH = -5;
	private final int EMPTY_OFFICIAL_DISPATCH_TRAVELS_DOC = -6;
	private final int EMPTY_INCOMING_OFFICIAL_DISPATCH_DOC = -7;

	private final int TOO_LONG_OFFICIAL_DISPATCH = -8;
	private final int TOO_LONG_OFFICIAL_DISPATCH_TRAVELS_DOC = -9;
	private final int TOO_LONG_INCOMING_OFFICIAL_DISPATCH_DOC = -10;

	private final int INVALID_LEVEL_BAOMAT_OFFICIAL_DISPATCH_TRAVELS_DOC = -11;
	private final int INVALID_LEVEL_BAOMAT_INCOMING_OFFICIAL_DISPATCH_DOC = -12;

	private final int EMPTY_LEVEL_BAOMAT_OFFICIAL_DISPATCH_TRAVELS_DOC = -13;
	private final int EMPTY_LEVEL_BAOMAT_INCOMING_OFFICIAL_DISPATCH_DOC = -14;

	private final String[] LEVEL_BAOMAT_VALUES = { "M1", "M2", "M3", "M4" };

	private final int OFFICIAL_DISPATCH_TRAVELS_DOC_TYPE = 0;
	private final int INCOMING_OFFICIAL_DISPATCH_DOC_TYPE = 1;

	private final String OFFICIAL_DISPATCH_TRAVELS_DOC_TYPE_VALUE = "Công văn đến";
	private final String INCOMING_OFFICIAL_DISPATCH_DOC_TYPE_VALUE = "Công văn đi";

	private final int INVALID_TYPE = -15;
	private final int EMPTY_TYPE = -16;
	
	private final int FOLDER_OFFICIAL_DISPATCH = 3; // loai buoc ho so chung tu

	private final int TIN_BOX_NOT_FOUND = -17;
	private final int FOLDER_NOT_FOUND_IN_TIN_BOX = -18;
	private final int THIS_USER_NOT_CREATE_THIS_BOX = -19;
	private final int EMPTY_TIN_BOX = -20;
	private final int EMPTY_CREATE_USER = -21;
	private final int EMPTY_FOLDER = -22;
	private final int EMPTY_FOLDER_NAME = -23;
	private final int DUPLICATE_FOLDER_CODE = -118;
	private final int TOO_LONG_FOLDER_NAME = -119;
	private final int TOO_LONG_FOLDER_CODE = -120;
	private final int NOT_EXIST_FOLDER_IN_BOX = -121;

	private final int FOLDER_OFFICIAL_DISPATCH_TRAVEL_DOC = 2; // loai buoc ho so cong van di
	private final int FOLDER_INCOMING_OFFICIAL_DOC = 3; // loai buoc ho so cong van den

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	VT070003DAO vt070003dao;
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


			Cell type = row.createCell(startCol++);
			type.setCellValue(convertTypeValue(dataRow.getType()));

			Cell officialDispatchDocName = row.createCell(startCol++);
			officialDispatchDocName.setCellValue(dataRow.getOfficialDispatchDocName());

			Cell officialDispatchName = row.createCell(startCol++);
			officialDispatchName.setCellValue(dataRow.getOfficialDispatchName());

			Cell levelBaomatOfficialDispatchDoc = row.createCell(startCol++);
			levelBaomatOfficialDispatchDoc.setCellValue(dataRow.getLevelBaomatOfficialDispatchDoc());

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

		if (isOkToInsertOfficialDispatch(row)) {
			insertOfficialDispatch(row, importType, unit);
		}

		String officialDispatchName = row.getOfficialDispatchName();
		long officialDispatchId = vt070003dao.getOfficialDispatchIdByName(officialDispatchName);

		if (officialDispatchId <= 0) {
			return row;
		}

		if (isOkToInsertIncomingOfficialDispatchDoc(row)) {
			insertOfficialDispatchTravelsDoc(row, importType, officialDispatchId, unit);
		}

		if (isOkToInsertOfficialDispatchTravelsDoc(row)) {
			insertIncomingOfficialDispatchDoc(row, importType, officialDispatchId, unit);
		}

		return row;
	}

	private void insertOfficialDispatch(ImportedDataRow row, String importType, int unit) {
		String officialDispatchName = row.getOfficialDispatchName();
		int type = row.getType();

		long officialDispatchExistedId = unit == 1 ? vt070003dao.getOfficialDispatchIdByNameGroup(officialDispatchName)
				: vt070003dao.getOfficialDispatchIdByName(officialDispatchName);

		if (officialDispatchExistedId < 0) {
			long folderId = 0;
			if (importType == FULL_IMPORT_TYPE) {
				folderId = row.getFolderId();
			}
			if (unit == 1) {
				vt070003dao.createOfficialDispatchGroup(officialDispatchName, type, folderId);
			} else {
				vt070003dao.createOfficialDispatch(officialDispatchName, type, folderId);
			}

		}
	}

	private void insertOfficialDispatchTravelsDoc(ImportedDataRow row, String importType, long officialDispatchId,
			int unit) {
		String officialDispatchDocName = row.getOfficialDispatchDocName();
		int type = row.getType();
		String levelBaoMat = row.getLevelBaomatOfficialDispatchDoc();

		long officialDispatchDocId = unit == 1
				? vt070003dao.getOfficialDispatchTravelsDocIdByDispatchIdAndNameGroup(officialDispatchId,
						officialDispatchDocName)
				: vt070003dao.getOfficialDispatchTravelsDocIdByDispatchIdAndName(officialDispatchId,
						officialDispatchDocName);
		if (officialDispatchDocId > 0) {
			row.insertErrorCode(DUPLICATE_OFFICIAL_DISPATCH_TRAVELS_DOC);
			row.setCriticalError(true);
			return;
		}
		long folderId = 0;
		if (importType == FULL_IMPORT_TYPE) {
			folderId = row.getFolderId();
		}
		long insertPrjDocResult = unit == 1
				? vt070003dao.createOfficialDispatchTravelsDocGroup(officialDispatchId, officialDispatchDocName, type,
						levelBaoMat, folderId)
				: vt070003dao.createOfficialDispatchTravelsDoc(officialDispatchId, officialDispatchDocName, type,
						levelBaoMat, folderId);
		row = raiseDocAffected(row, insertPrjDocResult, OFFICIAL_DISPATCH_TRAVELS_DOC);
	}

	private void insertIncomingOfficialDispatchDoc(ImportedDataRow row, String importType, long officialDispatchId,
			int unit) {
		String incomingOfficialDispatchDocName = row.getOfficialDispatchDocName();
		int type = row.getType();
		String levelBaoMat = row.getLevelBaomatOfficialDispatchDoc();
		long incomingOfficialDispatchDocId = unit == 1
				? vt070003dao.getIncomingOfficialDispatchDocIdByDispatchIdAndNameGroup(officialDispatchId,
						incomingOfficialDispatchDocName)
				: vt070003dao.getIncomingOfficialDispatchDocIdByDispatchIdAndName(officialDispatchId,
						incomingOfficialDispatchDocName);
		if (incomingOfficialDispatchDocId > 0) {
			row.insertErrorCode(DUPLICATE_INCOMING_OFFICIAL_DISPATCH_DOC);
			row.setCriticalError(true);
			return;
		}
		long folderId = 0;
		if (importType == FULL_IMPORT_TYPE) {
			folderId = row.getFolderId();
		}
		long insertPrjDocResult = unit == 1
				? vt070003dao.createIncomingOfficialDispatchDocGroup(officialDispatchId,
						incomingOfficialDispatchDocName, type, levelBaoMat, folderId)
				: vt070003dao.createIncomingOfficialDispatchDoc(officialDispatchId, incomingOfficialDispatchDocName,
						type, levelBaoMat, folderId);
		row = raiseDocAffected(row, insertPrjDocResult, INCOMING_OFFICIAL_DISPATCH_DOC);
	}

	private ImportedDataRow raiseDocAffected(ImportedDataRow row, long affectedRow, String typeOfDoc) {

		int insertedCode = 0;

		switch (typeOfDoc) {
		case OFFICIAL_DISPATCH_TRAVELS_DOC:
			insertedCode = OFFICIAL_DISPATCH_TRAVELS_DOC_INSERTED;
			break;
		case INCOMING_OFFICIAL_DISPATCH_DOC:
			insertedCode = INCOMING_OFFICIAL_DISPATCH_DOC_INSERTED;
			break;
		}

		if (affectedRow > 0) {
			row.insertErrorCode(insertedCode);
			row.setRowAffected(row.getRowAffected() + 1);
		} else {
			row.insertErrorCode(UNKNOWN_ERROR);
		}

		return row;
	}

	private ImportedDataRow trimTextData(ImportedDataRow row) {
		String officialDispatchName = row.getOfficialDispatchName().trim();
		String officialDispatchDocName = row.getOfficialDispatchDocName().trim();
		String levelBaomatOfficialDispatchDoc = row.getLevelBaomatOfficialDispatchDoc().trim();

		row.setOfficialDispatchName(officialDispatchName);
		row.setOfficialDispatchDocName(officialDispatchDocName);
		row.setLevelBaomatOfficialDispatchDoc(levelBaomatOfficialDispatchDoc);

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

	private boolean isOkToInsertOfficialDispatch(ImportedDataRow row) {
		if (row.isCriticalError())
			return false;
		if (StringUtils.isEmpty(row.getOfficialDispatchName()))
			return false;
		if (hasError(EMPTY_OFFICIAL_DISPATCH, row.getErrorCodes()))
			return false;
		if (hasError(TOO_LONG_OFFICIAL_DISPATCH, row.getErrorCodes()))
			return false;
		if (hasError(DUPLICATE_OFFICIAL_DISPATCH, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_TYPE, row.getErrorCodes()))
			return false;
		if (hasError(INVALID_TYPE, row.getErrorCodes()))
			return false;
		return true;
	}

	private boolean isOkToInsertOfficialDispatchTravelsDoc(ImportedDataRow row) {
		if (row.getType() != OFFICIAL_DISPATCH_TRAVELS_DOC_TYPE)
			return false;
		if (row.isCriticalError())
			return false;
		if (StringUtils.isEmpty(row.getOfficialDispatchName()) || StringUtils.isEmpty(row.getOfficialDispatchDocName()))
			return false;
		if (hasError(EMPTY_OFFICIAL_DISPATCH_TRAVELS_DOC, row.getErrorCodes()))
			return false;
		if (hasError(TOO_LONG_OFFICIAL_DISPATCH_TRAVELS_DOC, row.getErrorCodes()))
			return false;
		if (hasError(DUPLICATE_OFFICIAL_DISPATCH_TRAVELS_DOC, row.getErrorCodes()))
			return false;
		if (hasError(INVALID_LEVEL_BAOMAT_OFFICIAL_DISPATCH_TRAVELS_DOC, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_LEVEL_BAOMAT_OFFICIAL_DISPATCH_TRAVELS_DOC, row.getErrorCodes()))
			return false;
		return true;
	}

	private boolean isOkToInsertIncomingOfficialDispatchDoc(ImportedDataRow row) {
		if (row.getType() != INCOMING_OFFICIAL_DISPATCH_DOC_TYPE)
			return false;
		if (row.isCriticalError())
			return false;
		if (StringUtils.isEmpty(row.getOfficialDispatchName()) || StringUtils.isEmpty(row.getOfficialDispatchDocName()))
			return false;
		if (hasError(EMPTY_INCOMING_OFFICIAL_DISPATCH_DOC, row.getErrorCodes()))
			return false;
		if (hasError(TOO_LONG_INCOMING_OFFICIAL_DISPATCH_DOC, row.getErrorCodes()))
			return false;
		if (hasError(DUPLICATE_INCOMING_OFFICIAL_DISPATCH_DOC, row.getErrorCodes()))
			return false;
		if (hasError(INVALID_LEVEL_BAOMAT_INCOMING_OFFICIAL_DISPATCH_DOC, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_LEVEL_BAOMAT_INCOMING_OFFICIAL_DISPATCH_DOC, row.getErrorCodes()))
			return false;
		return true;
	}

	private String convertTypeValue(int type) {
		if (type == OFFICIAL_DISPATCH_TRAVELS_DOC_TYPE) {
			return OFFICIAL_DISPATCH_TRAVELS_DOC_TYPE_VALUE;
		}
		if (type == INCOMING_OFFICIAL_DISPATCH_DOC_TYPE) {
			return INCOMING_OFFICIAL_DISPATCH_DOC_TYPE_VALUE;
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
		validateOfficialDispatch(row);
		validateOfficialDispatchDoc(row);
		validateType(row);
		validateLevelBaoMat(row);
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

		int folderType = FOLDER_OFFICIAL_DISPATCH;

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

	private void validateOfficialDispatch(ImportedDataRow row) {
		String officialDispatchName = row.getOfficialDispatchName();
		if (officialDispatchName.length() == 0) {
			row.insertErrorCode(EMPTY_OFFICIAL_DISPATCH);
			row.setCriticalError(true);
		} else if (officialDispatchName.length() > 255) {
			row.insertErrorCode(TOO_LONG_OFFICIAL_DISPATCH);
			row.setCriticalError(true);
		}

	}

	private void validateOfficialDispatchDoc(ImportedDataRow row) {

		int type = row.getType();

		if (type == OFFICIAL_DISPATCH_TRAVELS_DOC_TYPE) {
			validateOfficialDispatchTravelsDoc(row);
		} else {
			validateIncomingOfficialDispatchDoc(row);
		}
	}

	private void validateOfficialDispatchTravelsDoc(ImportedDataRow row) {

		String officialDispatchName = row.getOfficialDispatchName();
		if (officialDispatchName.length() == 0) {
			row.insertErrorCode(EMPTY_OFFICIAL_DISPATCH_TRAVELS_DOC);
			row.setCriticalError(true);
		} else if (officialDispatchName.length() > 255) {
			row.insertErrorCode(TOO_LONG_OFFICIAL_DISPATCH_TRAVELS_DOC);
			row.setCriticalError(true);
		}
	}

	private void validateIncomingOfficialDispatchDoc(ImportedDataRow row) {

		String officialDispatchName = row.getOfficialDispatchName();
		if (officialDispatchName.length() == 0) {
			row.insertErrorCode(EMPTY_INCOMING_OFFICIAL_DISPATCH_DOC);
			row.setCriticalError(true);
		} else if (officialDispatchName.length() > 255) {
			row.insertErrorCode(TOO_LONG_INCOMING_OFFICIAL_DISPATCH_DOC);
			row.setCriticalError(true);
		}
	}

	private ImportedDataRow validateType(ImportedDataRow row) {
		if (row.getType() == null) {
			row.insertErrorCode(EMPTY_TYPE);
			row.setCriticalError(true);
			return row;
		}
		int type = row.getType();

		if (type == 0 || type == 1) {
			return row;
		}
		row.insertErrorCode(INVALID_TYPE);
		row.setCriticalError(true);
		return row;
	}

	private ImportedDataRow validateLevelBaoMat(ImportedDataRow row) {
		int type = row.getType();
		if (type == INCOMING_OFFICIAL_DISPATCH_DOC_TYPE) {
			validateLevelBaoMatIncomingOfficialDispatchDoc(row);
		} else {
			validateLevelBaoMatOfficialDispatchTravelsDoc(row);
		}
		return row;
	}

	private ImportedDataRow validateLevelBaoMatIncomingOfficialDispatchDoc(ImportedDataRow row) {
		String levelBaoMat = row.getLevelBaomatOfficialDispatchDoc();

		if (StringUtils.isEmpty(levelBaoMat)) {
			row.insertErrorCode(EMPTY_LEVEL_BAOMAT_INCOMING_OFFICIAL_DISPATCH_DOC);
			row.setCriticalError(true);
		}
		if (checkLevelBaoMat(levelBaoMat)) {
			row.insertErrorCode(INVALID_LEVEL_BAOMAT_INCOMING_OFFICIAL_DISPATCH_DOC);
			row.setCriticalError(true);
		}
		return row;
	}

	private ImportedDataRow validateLevelBaoMatOfficialDispatchTravelsDoc(ImportedDataRow row) {
		String levelBaoMat = row.getLevelBaomatOfficialDispatchDoc();

		if (StringUtils.isEmpty(levelBaoMat)) {
			row.insertErrorCode(EMPTY_LEVEL_BAOMAT_OFFICIAL_DISPATCH_TRAVELS_DOC);
			row.setCriticalError(true);
		}
		if (checkLevelBaoMat(levelBaoMat)) {
			row.insertErrorCode(INVALID_LEVEL_BAOMAT_OFFICIAL_DISPATCH_TRAVELS_DOC);
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
	
	 /**
	  * Upload file 
	  */
	@Override
	public ResponseEntityBase uploadFile(MultipartFile file, OAuth2Authentication oauth,
			Collection<GrantedAuthority> userRoles, long documentId,long documentType) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			FileUploadInfo fileInfo = new FileUploadInfo();
			// luu file lên hệ thống
			String path = saveFile(file, documentId, documentType);
			if (!path.isEmpty()) {
				fileInfo.setDocumentId(documentId);
				fileInfo.setDocumentType(documentType);
				fileInfo.setPath(path);
				// update table
				vt070003dao.updateFileUpload(fileInfo);
			}
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, fileInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
	/**
	  * View file 
	  */
	@Override
	public FileUploadInfo viewFileUpload(OAuth2Authentication oauth,
			Collection<GrantedAuthority> userRoles, long documentId,long documentType) {
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		byte[] array  = null;
		FileUploadInfo fileInfo = new FileUploadInfo();
		try {
			
			
			fileInfo.setDocumentId(documentId);
			fileInfo.setDocumentType(documentType);
			// update table
			String path = vt070003dao.getFileUploadByIdAndType(fileInfo);
			if (path != null && !path.isEmpty()) {
				 array = Files.readAllBytes(Paths.get(path));
				 fileInfo.setFile(array);
				 fileInfo.setPath(path);
				 fileInfo.setFileType(FilenameUtils.getExtension(path));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return fileInfo;
	}
	
	/**
	 * Save file to disk
	 * @param fileupload
	 * @param dish
	 * @return path
	 */
	private String saveFile(MultipartFile fileupload, Long documentId, Long type) {
		  if(fileupload == null) return null;
		  
			String OrigifileName = fileupload.getOriginalFilename();
			String extension = FilenameUtils.getExtension(OrigifileName);
			String newName = documentId + "." + extension;
			FileOutputStream out = null;
			String directory = new File("").getAbsoluteFile().getParent() + File.separator + Constant.DISH_DOCUMENT_PATH + File.separator + type;
			File directoryFile = new File(directory);
			if(!directoryFile.exists()) {
			  directoryFile.mkdirs();
			}
			
			String path = directory + File.separator + newName;
			
			File imageToSave = new File(path);
			if (imageToSave.exists()) {
			  imageToSave.delete();
			}
			try {
				imageToSave.createNewFile();
				out = new FileOutputStream(imageToSave);
				out.write(fileupload.getBytes());
				out.flush();
				
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			} finally {
				if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
					// TODO Auto-generated catch block
					}
				}
			}
			
			return path;
		}
	
	private File getFileUploadByPath(String path) {
		File file = null;
		try {
			Resource resource = new ClassPathResource(path);
			file = resource.getFile();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return file;
	}
}
