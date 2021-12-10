package com.viettel.vtnet360.vt07.vt070000.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt07.vt070000.dao.VT070000DAO;
import com.viettel.vtnet360.vt07.vt070000.entity.ImportedDataRow;
import com.viettel.vtnet360.vt07.vt070000.entity.search.DocumentDetailSearch;
import com.viettel.vtnet360.vt07.vt070002.dao.VT070002DAO;
import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;

@Service
@Transactional(rollbackFor = Exception.class)
public class ImportDataServiceImpl implements ImportDataService {

	private final String FULL_IMPORT_TYPE = "full";
	private final String SIMPLE_IMPORT_TYPE = "simple";

	private final String PROJECT_DOC = "PROJECT_DOC";
	private final String PACKAGE_DOC = "PACKAGE_DOC";
	private final String CONTRACT_DOC = "CONTRACT_DOC";
	private final String CONSTRUCTION_DOC = "CONSTRUCTION_DOC";

	private final int UNKNOWN_ERROR = 0; // cannot insert due to sql or other error

	private final int PROJECT_DOCUMENT_INSERTED = 1;
	private final int PACKAGE_DOCUMENT_INSERTED = 2;
	private final int CONTRACT_DOCUMENT_INSERTED = 3;
	private final int CONSTRUCTION_DOCUMENT_INSERTED = 4;

	private final int DUPLICATE_PROJECT_DOC = -1;
	private final int DUPLICATE_PACKAGE_DOC = -2;
	private final int DUPLICATE_CONTRACT_DOC = -3;
	private final int DUPLICATE_CONSTRUCTION_DOC = -4;

	private final int EMPTY_PACKAGE_WHILE_HAS_RELATE_DOC = -5;
	private final int EMPTY_CONTRACT_WHILE_HAS_RELATE_DOC = -6;
	private final int EMPTY_CONSTRUCTION_WHILE_HAS_RELATE_DOC = -7;
	private final int TOO_LONG_PROJECT_DOC = -8;
	private final int TOO_LONG_PACKAGE_NAME = -9;
	private final int TOO_LONG_PACKAGE_DOC = -10;
	private final int TOO_LONG_CONTRACT_NAME = -11;
	private final int TOO_LONG_CONTRACT_DOC = -12;
	private final int TOO_LONG_CONSTRUCTION_NAME = -13;
	private final int TOO_LONG_CONSTRUCTION_DOC = -14;

	// critial validate errors make all documents in a row not inserted
	private final int TIN_BOX_NOT_FOUND = -101;
	private final int FOLDER_NOT_FOUND_IN_TIN_BOX = -102;
	private final int THIS_USER_NOT_CREATE_THIS_BOX = -103;
	private final int EMPTY_TIN_BOX = -104;
	private final int EMPTY_CREATE_USER = -105;
	private final int EMPTY_FOLDER = -106;
	private final int EMPTY_PROJECT = -107;
	private final int TOO_LONG_PROJECT_NAME = -108;

	private final int INVALID_LEVEL_BAOMAT_PROJECT_DOC = -109;
	private final int INVALID_LEVEL_BAOMAT_PACKAGE_DOC = -110;
	private final int INVALID_LEVEL_BAOMAT_CONTRACT_DOC = -111;
	private final int INVALID_LEVEL_BAOMAT_CONSTRUCTION_DOC = -112;

	private final int EMPTY_LEVEL_BAOMAT_PROJECT_DOC = -113;
	private final int EMPTY_LEVEL_BAOMAT_PACKAGE_DOC = -114;
	private final int EMPTY_LEVEL_BAOMAT_CONTRACT_DOC = -115;
	private final int EMPTY_LEVEL_BAOMAT_CONSTRUCTION_DOC = -116;
	private final int EMPTY_FOLDER_NAME = -117;
	private final int DUPLICATE_FOLDER_CODE = -118;
	private final int TOO_LONG_FOLDER_NAME = -119;
	private final int TOO_LONG_FOLDER_CODE = -120;
	private final int NOT_EXIST_FOLDER_IN_BOX = -121;

	private final String[] LEVEL_BAOMAT_VALUES = { "M1", "M2", "M3", "M4" };

	private final int FOLDER_PROJECT_DOC = 1; // loai buoc ho so du an+
	private final int FOLDER_OFFICIAL_DISPATCH_TRAVEL_DOC = 2; // loai buoc ho so cong van di
	private final int FOLDER_INCOMING_OFFICIAL_DOC = 3; // loai buoc ho so cong van den
	private final int FOLDER_VOUCHER_DOC = 4; // loai buoc ho so chung tu thanh toan
	private final int PAYMENT_SUMMARY_DOC = 5; // bang tong hop thanh toan
	private final int FOLDER_VOUCHER_NOTE_DOC = 6; // loai buoc ho so chung tu ghi so
	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	VT070000DAO vt070000dao;

	@Autowired
	VT070002DAO vt070002dao;

	@Autowired
	Properties linkTemplateExcel;

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

	public Workbook bindSearchDocumentData(List<DocumentDetailSearch> dataRows, Workbook template,
			Collection<GrantedAuthority> userRoles) {
		int startRow = 1;

		Sheet worksheet = template.getSheetAt(0);

		for (int i = 0; i < dataRows.size(); i++) {
			DocumentDetailSearch dataRow = dataRows.get(i);
			Row row = worksheet.createRow(startRow++);
			int startCol = 0;

			Cell stt = row.createCell(startCol++);
			stt.setCellValue(i + 1);

			Cell scanStatus = row.createCell(startCol++);
			scanStatus.setCellValue(dataRow.getPath());

			Cell docName = row.createCell(startCol++);
			docName.setCellValue(dataRow.getDocName());

			Cell contruction = row.createCell(startCol++);
			contruction.setCellValue(dataRow.getConstractionName());

			Cell contract = row.createCell(startCol++);
			contract.setCellValue(dataRow.getContracName());

			Cell packageName = row.createCell(startCol++);
			packageName.setCellValue(dataRow.getPackageName());

			Cell project = row.createCell(startCol++);
			project.setCellValue(dataRow.getProjectName());

			Cell level = row.createCell(startCol++);
			level.setCellValue(dataRow.getLevelBaoMat());

		}

		return template;
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

				Cell createUser = row.createCell(startCol++);
				createUser.setCellValue(dataRow.getCreateUser());

				Cell folderQr = row.createCell(startCol++);
				folderQr.setCellValue(dataRow.getFolderQrCode());

				Cell folderName = row.createCell(startCol++);
				folderName.setCellValue(dataRow.getFolderName());
			}

			Cell projectName = row.createCell(startCol++);
			projectName.setCellValue(dataRow.getProjectName());

			Cell projectDoc = row.createCell(startCol++);
			projectDoc.setCellValue(dataRow.getProjectDoc());

			Cell levelBaoMatProjectDoc = row.createCell(startCol++);
			levelBaoMatProjectDoc.setCellValue(dataRow.getLevelBaoMatProject());

			Cell packageName = row.createCell(startCol++);
			packageName.setCellValue(dataRow.getPackageName());

			Cell packageDoc = row.createCell(startCol++);
			packageDoc.setCellValue(dataRow.getPackageDoc());

			Cell levelBaoMatPackageDoc = row.createCell(startCol++);
			levelBaoMatPackageDoc.setCellValue(dataRow.getLevelBaoMatPackage());

			Cell contractName = row.createCell(startCol++);
			contractName.setCellValue(dataRow.getContractName());

			Cell contractDoc = row.createCell(startCol++);
			contractDoc.setCellValue(dataRow.getContractDoc());

			Cell levelBaoMatContractDoc = row.createCell(startCol++);
			levelBaoMatContractDoc.setCellValue(dataRow.getLevelBaoMatContract());

			Cell constructionName = row.createCell(startCol++);
			constructionName.setCellValue(dataRow.getConstructionName());

			Cell constructionDoc = row.createCell(startCol++);
			constructionDoc.setCellValue(dataRow.getConstructionDoc());

			Cell levelBaoMatConstructionDoc = row.createCell(startCol++);
			levelBaoMatConstructionDoc.setCellValue(dataRow.getLevelBaoMatConstruction());

			Cell rowReport = row.createCell(startCol);
			rowReport.setCellValue(dataRow.getRowReport());
		}

		return template;
	}

	private int getUnit(Collection<GrantedAuthority> userRoles) {
		for (GrantedAuthority temp : userRoles) {
			// if user had role VIEW ALL TINBOX
			if (Constant.PMQT_ROLE_WAREHOUSE_GROUP.equalsIgnoreCase(temp.getAuthority())) {
				return 1;
			}
		}
		return 0;
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
				row = validateDataRow(row, FULL_IMPORT_TYPE, i, unit);
				if (!row.isCriticalError()) {
					row = importDataRow(row, FULL_IMPORT_TYPE, i, unit);
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
		int importResult = 0;
		int unit = getUnit(userRoles);

		try {
			for (int i = 0; i < dataRows.size(); i++) {
				ImportedDataRow row = dataRows.get(i);
				row = trimTextData(row);
				row.setUnit(unit);
				row = validateDataRow(row, SIMPLE_IMPORT_TYPE, i, unit);
				if (!row.isCriticalError()) {
					row = importDataRow(row, SIMPLE_IMPORT_TYPE, i, unit);
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

	private ImportedDataRow importDataRow(ImportedDataRow row, String importType, int rowIndex, int unit) {

		long folderId = row.getFolderId();
		long projectId = row.getProjectId();
		String createUser = row.getCreateUser();

		if (isOkToInsertProjectDoc(row)) {
			row = insertProjectDoc(row, importType, unit);
		}

		if (isOkToInsertPackageDoc(row)) {
			row = insertPackageDoc(row, rowIndex, importType, unit);
		}

		if (isOkToInsertContractDoc(row)) {
			row = insertContractDoc(row, rowIndex, importType, unit);
		}

		if (isOkToInsertConstructionDoc(row)) {
			row = insertConstructionDoc(row, rowIndex, importType, unit);
		}

		if (row.getRowAffected() > 0 && importType == FULL_IMPORT_TYPE) {
			if (unit == 1) {
				vt070000dao.bindProjectToFolderGroup(folderId, projectId, createUser);
			} else {
				vt070000dao.bindProjectToFolder(folderId, projectId, createUser);
			}
		}

		return row;
	}

	private ImportedDataRow insertProjectDoc(ImportedDataRow row, String importType, int unit) {
		long projectId = row.getProjectId();
		long folderId = row.getFolderId();
		String projectDoc = row.getProjectDoc();
		String levelBaoMatProject = row.getLevelBaoMatProject();

		long prjDocExistedId = unit == 1 ? vt070000dao.checkExistPrjDocGroup(projectId, projectDoc)
				: vt070000dao.checkExistPrjDoc(projectId, projectDoc);

		boolean isInsertDoc = false;

		if (prjDocExistedId > 0) {

			if (importType == SIMPLE_IMPORT_TYPE) {
				row.insertErrorCode(DUPLICATE_PROJECT_DOC);
				return row;
			}

			long folderIdOfExistedDoc = unit == 1 ? vt070000dao.getFolderIdOfProjectDocGroup(prjDocExistedId)
					: vt070000dao.getFolderIdOfProjectDoc(prjDocExistedId);

			if (folderIdOfExistedDoc > 0) {
				if (folderIdOfExistedDoc == row.getFolderId()) {
					row.insertErrorCode(DUPLICATE_PROJECT_DOC);
				} else {
					isInsertDoc = true;
				}
			} else {
				int bindResult = unit == 1 ? vt070000dao.bindPrjDocToFolderGroup(prjDocExistedId, folderId)
						: vt070000dao.bindPrjDocToFolder(prjDocExistedId, folderId);
				row = raiseDocAffected(row, bindResult, PROJECT_DOC);
			}
		} else {
			isInsertDoc = true;
		}
		if (isInsertDoc) {
			int insertPrjDocResult = unit == 1 ? vt070000dao.insertProjectDocGroup(projectDoc, folderId, projectId)
					: vt070000dao.insertProjectDoc(projectDoc, folderId, projectId, levelBaoMatProject);
			row = raiseDocAffected(row, insertPrjDocResult, PROJECT_DOC);
		}
		return row;
	}

	private ImportedDataRow insertPackageDoc(ImportedDataRow row, int rowIndex, String importType, int unit) {
		long projectId = row.getProjectId();
		long folderId = row.getFolderId();
		String packageName = row.getPackageName();
		String packageDoc = row.getPackageDoc();
		String levelBaoMatPackage = row.getLevelBaoMatPackage();

		long packageId = unit == 1 ? vt070000dao.getValidPackageIdGroup(packageName, projectId)
				: vt070000dao.getValidPackageId(packageName, projectId);

		if (packageId < 0) {
			int createPkgResult = unit == 1 ? vt070000dao.createPackageGroup(packageName, projectId, rowIndex)
					: vt070000dao.createPackage(packageName, projectId, rowIndex);

			if (createPkgResult > 0) {
				packageId = unit == 1 ? vt070000dao.getValidPackageIdGroup(packageName, projectId)
						: vt070000dao.getValidPackageId(packageName, projectId);
			} else {
				row.insertErrorCode(UNKNOWN_ERROR);
				return row;
			}
		}

		long pkgDocExistId = unit == 1 ? vt070000dao.checkExistPkgDocGroup(packageId, packageDoc)
				: vt070000dao.checkExistPkgDoc(packageId, packageDoc);
		boolean isInsertDoc = false;
		if (pkgDocExistId > 0) {

			if (importType == SIMPLE_IMPORT_TYPE) {
				row.insertErrorCode(DUPLICATE_PACKAGE_DOC);
				return row;
			}

			long folderIdOfExistedDoc = unit == 1 ? vt070000dao.getFolderIdOfPkgDocGroup(pkgDocExistId)
					: vt070000dao.getFolderIdOfPkgDoc(pkgDocExistId);

			if (folderIdOfExistedDoc > 0) {
				if (folderIdOfExistedDoc == row.getFolderId()) {
					row.insertErrorCode(DUPLICATE_PACKAGE_DOC);
				} else {
					isInsertDoc = true;
				}
			} else {
				int bindResult = unit == 1 ? vt070000dao.bindPkgDocToFolderGroup(pkgDocExistId, folderId)
						: vt070000dao.bindPkgDocToFolder(pkgDocExistId, folderId);
				row = raiseDocAffected(row, bindResult, PACKAGE_DOC);
			}
		} else {
			isInsertDoc = true;
		}
		if (isInsertDoc) {
			int insertPkgDocResult = unit == 1 ? vt070000dao.insertPackageDocGroup(packageDoc, folderId, packageId)
					: vt070000dao.insertPackageDoc(packageDoc, folderId, packageId, levelBaoMatPackage);
			row = raiseDocAffected(row, insertPkgDocResult, PACKAGE_DOC);
		}

		return row;
	}

	private ImportedDataRow insertContractDoc(ImportedDataRow row, int rowIndex, String importType, int org) {
		long projectId = row.getProjectId();
		long folderId = row.getFolderId();
		String contractName = row.getContractName();
		String contractDoc = row.getContractDoc();
		String levelBaoMatContract = row.getLevelBaoMatContract();
		int unit = row.getUnit();
		// get packageId by packageName and projectId
		String packageName = row.getPackageName();
		long packageId = vt070000dao.getValidPackageId(packageName, projectId);

		long contractId = org == 1 ? vt070000dao.getValidContractIdGroup(contractName, packageId)
				: vt070000dao.getValidContractId(contractName, packageId);

		if (contractId < 0) {

			int createCtrResult = org == 1
					? vt070000dao.createContractGroup(contractName, projectId, unit, rowIndex + 1)
					: vt070000dao.createContract(contractName, projectId, unit, rowIndex + 1, packageId);

			if (createCtrResult > 0) {
				contractId = org == 1 ? vt070000dao.getValidContractIdGroup(contractName, packageId)
						: vt070000dao.getValidContractId(contractName, packageId);
			}
		}

		long ctDocExistedId = org == 1 ? vt070000dao.checkExistCtDocGroup(contractId, contractDoc)
				: vt070000dao.checkExistCtDoc(contractId, contractDoc);

		boolean isInsertDoc = false;

		if (ctDocExistedId > 0) {

			if (importType == SIMPLE_IMPORT_TYPE) {
				row.insertErrorCode(DUPLICATE_CONTRACT_DOC);
				return row;
			}

			long folderIdOfExistedDoc = org == 1 ? vt070000dao.getFolderIdOfCtDocGroup(ctDocExistedId)
					: vt070000dao.getFolderIdOfCtDoc(ctDocExistedId);

			if (folderIdOfExistedDoc > 0) {
				if (folderIdOfExistedDoc == row.getFolderId()) {
					row.insertErrorCode(DUPLICATE_CONTRACT_DOC);
				} else {
					isInsertDoc = true;
				}
			} else {
				int bindResult = org == 1 ? vt070000dao.bindCtDocToFolderGroup(ctDocExistedId, folderId)
						: vt070000dao.bindCtDocToFolder(ctDocExistedId, folderId);
				row = raiseDocAffected(row, bindResult, CONTRACT_DOC);
			}
		} else {
			isInsertDoc = true;
		}
		if (isInsertDoc) {
			int insertCtDocResult = org == 1 ? vt070000dao.insertContractDocGroup(contractDoc, folderId, contractId)
					: vt070000dao.insertContractDoc(contractDoc, folderId, contractId, levelBaoMatContract);
			row = raiseDocAffected(row, insertCtDocResult, CONTRACT_DOC);
		}
		return row;
	}

	private ImportedDataRow insertConstructionDoc(ImportedDataRow row, int rowIndex, String importType, int org) {
		long projectId = row.getProjectId();
		long folderId = row.getFolderId();
		String contractName = row.getContractName();
		String constructionName = row.getConstructionName();
		String constructionDoc = row.getConstructionDoc();
		String levelBaoMatConstruction = row.getLevelBaoMatConstruction();

		int unit = row.getUnit();

		// get packageId by packageName and projectId
		String packageName = row.getPackageName();
		long packageId = vt070000dao.getValidPackageId(packageName, projectId);

		long contractId = org == 1 ? vt070000dao.getValidContractIdGroup(contractName, packageId)
				: vt070000dao.getValidContractId(contractName, packageId);

		if (contractId < 0) {

			int createCtrResult = org == 1
					? vt070000dao.createContractGroup(contractName, projectId, unit, rowIndex + 1)
					: vt070000dao.createContract(contractName, projectId, unit, rowIndex + 1, packageId);

			if (createCtrResult > 0) {
				contractId = org == 1 ? vt070000dao.getValidContractIdGroup(contractName, packageId)
						: vt070000dao.getValidContractId(contractName, packageId);
			} else {
				row.insertErrorCode(UNKNOWN_ERROR);
				return row;
			}
		}

		long constructionId = org == 1 ? vt070000dao.getValidConstructionIdGroup(constructionName, contractId)
				: vt070000dao.getValidConstructionId(constructionName, contractId);

		if (constructionId < 0) {

			int createConsResult = org == 1 ? vt070000dao.createConstructionGroup(constructionName, contractId)
					: vt070000dao.createConstruction(constructionName, contractId);

			if (createConsResult > 0) {
				constructionId = org == 1 ? vt070000dao.getValidConstructionIdGroup(constructionName, contractId)
						: vt070000dao.getValidConstructionId(constructionName, contractId);
			} else {
				row.insertErrorCode(UNKNOWN_ERROR);
				return row;
			}
		}

		long consDocExistedId = org == 1 ? vt070000dao.checkExistConsDocGroup(constructionId, constructionDoc)
				: vt070000dao.checkExistConsDoc(constructionId, constructionDoc);
		boolean isInsertDoc = false;
		if (consDocExistedId > 0) {

			if (importType == SIMPLE_IMPORT_TYPE) {
				row.insertErrorCode(DUPLICATE_CONSTRUCTION_DOC);
				return row;
			}

			long folderIdOfExistedDoc = org == 1 ? vt070000dao.getFolderIdOfConsDocGroup(consDocExistedId)
					: vt070000dao.getFolderIdOfConsDoc(consDocExistedId);

			if (folderIdOfExistedDoc > 0) {
				if (folderIdOfExistedDoc == row.getFolderId()) {
					row.insertErrorCode(DUPLICATE_CONSTRUCTION_DOC);
				} else {
					isInsertDoc = true;
				}
			} else {
				int bindResult = org == 1 ? vt070000dao.bindConsDocToFolderGroup(consDocExistedId, folderId)
						: vt070000dao.bindConsDocToFolder(consDocExistedId, folderId);
				row = raiseDocAffected(row, bindResult, CONSTRUCTION_DOC);
			}

		} else {
			isInsertDoc = true;
		}
		if (isInsertDoc) {
			int insertConsDocResult = org == 1
					? vt070000dao.insertConstructionDocGroup(constructionDoc, folderId, constructionId)
					: vt070000dao.insertConstructionDoc(constructionDoc, folderId, constructionId,
							levelBaoMatConstruction);
			row = raiseDocAffected(row, insertConsDocResult, CONSTRUCTION_DOC);
		}
		return row;
	}

	private ImportedDataRow raiseDocAffected(ImportedDataRow row, int affectedRow, String typeOfDoc) {

		int insertedCode = 0;

		switch (typeOfDoc) {
		case PROJECT_DOC:
			insertedCode = PROJECT_DOCUMENT_INSERTED;
			break;
		case PACKAGE_DOC:
			insertedCode = PACKAGE_DOCUMENT_INSERTED;
			break;
		case CONTRACT_DOC:
			insertedCode = CONTRACT_DOCUMENT_INSERTED;
			break;
		case CONSTRUCTION_DOC:
			insertedCode = CONSTRUCTION_DOCUMENT_INSERTED;
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
		String tinBoxQrCode = row.getTinBoxQrCode().trim();
		String createUser = row.getCreateUser().trim();
		String folderQrCode = row.getFolderQrCode().trim();
		String projectName = row.getProjectName().trim();
		String projectDoc = row.getProjectDoc().trim();
		String packageName = row.getPackageName().trim();
		String packageDoc = row.getPackageDoc().trim();
		String contractName = row.getContractName().trim();
		String contractDoc = row.getContractDoc().trim();
		String constructionName = row.getConstructionName().trim();
		String constructionDoc = row.getConstructionDoc().trim();
		row.setTinBoxQrCode(tinBoxQrCode);
		row.setCreateUser(createUser);
		row.setFolderQrCode(folderQrCode);
		row.setProjectName(projectName);
		row.setProjectDoc(projectDoc);
		row.setPackageName(packageName);
		row.setPackageDoc(packageDoc);
		row.setContractName(contractName);
		row.setContractDoc(contractDoc);
		row.setConstructionName(constructionName);
		row.setConstructionDoc(constructionDoc);
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

	private boolean isOkToInsertProjectDoc(ImportedDataRow row) {

		if (row.isCriticalError())
			return false;
		if (StringUtils.isEmpty(row.getProjectDoc()))
			return false;
		if (hasError(TOO_LONG_PROJECT_DOC, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_LEVEL_BAOMAT_PROJECT_DOC, row.getErrorCodes()))
			return false;
		if (hasError(INVALID_LEVEL_BAOMAT_PROJECT_DOC, row.getErrorCodes()))
			return false;

		return true;
	}

	private boolean isOkToInsertPackageDoc(ImportedDataRow row) {

		if (row.isCriticalError())
			return false;
		if (StringUtils.isEmpty(row.getPackageDoc()))
			return false;
		if (hasError(TOO_LONG_PACKAGE_DOC, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_PACKAGE_WHILE_HAS_RELATE_DOC, row.getErrorCodes()))
			return false;
		if (hasError(TOO_LONG_PACKAGE_NAME, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_LEVEL_BAOMAT_PACKAGE_DOC, row.getErrorCodes()))
			return false;
		if (hasError(INVALID_LEVEL_BAOMAT_PACKAGE_DOC, row.getErrorCodes()))
			return false;

		return true;
	}

	public boolean isOkToInsertContractDoc(ImportedDataRow row) {

		if (row.isCriticalError())
			return false;
		if (StringUtils.isEmpty(row.getContractDoc()))
			return false;
		if (hasError(TOO_LONG_CONTRACT_DOC, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_CONTRACT_WHILE_HAS_RELATE_DOC, row.getErrorCodes()))
			return false;
		if (hasError(TOO_LONG_CONTRACT_NAME, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_LEVEL_BAOMAT_CONTRACT_DOC, row.getErrorCodes()))
			return false;
		if (hasError(INVALID_LEVEL_BAOMAT_CONTRACT_DOC, row.getErrorCodes()))
			return false;

		return true;
	}

	public boolean isOkToInsertConstructionDoc(ImportedDataRow row) {

		if (row.isCriticalError())
			return false;
		if (StringUtils.isEmpty(row.getConstructionDoc()))
			return false;
		if (hasError(TOO_LONG_CONSTRUCTION_DOC, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_CONSTRUCTION_WHILE_HAS_RELATE_DOC, row.getErrorCodes()))
			return false;
		if (hasError(TOO_LONG_CONSTRUCTION_NAME, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_CONTRACT_WHILE_HAS_RELATE_DOC, row.getErrorCodes()))
			return false;
		if (hasError(TOO_LONG_CONTRACT_NAME, row.getErrorCodes()))
			return false;
		if (hasError(EMPTY_LEVEL_BAOMAT_CONSTRUCTION_DOC, row.getErrorCodes()))
			return false;
		if (hasError(INVALID_LEVEL_BAOMAT_CONSTRUCTION_DOC, row.getErrorCodes()))
			return false;

		return true;
	}

	private ImportedDataRow validateDataRow(ImportedDataRow row, String importType, int rowIndex, int unit) {

		if (importType == FULL_IMPORT_TYPE) {
			row = validateTinBox(row, unit);
			row = validateCreateUser(row, unit);
			row = validateFolder(row, unit);

		}

		row = validateProject(row, rowIndex, unit);
		row = validateProjectDoc(row);
		row = validatePackage(row);
		row = validatePackageDoc(row);
		row = validateContract(row);
		row = validateContractDoc(row);
		row = validateConstruction(row);
		row = validateConstructionDoc(row);

		row = validateLevelBaoMatProject(row);
		row = validateLevelBaoMatPackage(row);
		row = validateLevelBaoMatContract(row);
		row = validateLevelBaoMatConstruction(row);

		return row;
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
			if (folders != null && folders.size() > 0) {
				FolderDetail folder = folders.get(0);
				if (folder.getType() != FOLDER_PROJECT_DOC) {
					row.insertErrorCode(DUPLICATE_FOLDER_CODE);
					row.setCriticalError(true);
				} else if (folder.getTinBoxId() <= 0 || folder.getTinBoxId() != row.getTinBoxId()) {
					row.insertErrorCode(NOT_EXIST_FOLDER_IN_BOX);
					row.setCriticalError(true);
				} else {
					folderId = folder.getFolderId();
				}
			}
			if (folderId < 0 && row.getTinBoxId() > 0 && !row.isCriticalError()) {
				if (StringUtils.isEmpty(folderName)) {
					row.insertErrorCode(EMPTY_FOLDER_NAME);
					row.setCriticalError(true);
				} else {
					FolderDetail folder = new FolderDetail();
					folder.setTinBoxId(tinBoxId);
					folder.setName(folderName);
					folder.setQrCode(folderQrCode);
					folder.setCreateUser(row.createUser);
					folder.setUnit(unit);
					folder.setType(FOLDER_PROJECT_DOC);
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

	private ImportedDataRow validateProject(ImportedDataRow row, int rowIndex, int org) {
		String projectName = row.getProjectName();
		int unit = row.getUnit();

		if (projectName != null && projectName.length() > 0) {

			if (projectName.length() > 255) {
				row.insertErrorCode(TOO_LONG_PROJECT_NAME);
				row.setCriticalError(true);
			} else {

				long projectId = org == 1 ? vt070000dao.getProjectIdGroup(projectName)
						: vt070000dao.getProjectId(projectName);

				if (projectId < 0) {
					int createPrjResult = org == 1 ? vt070000dao.createProjectGroup(projectName, unit, rowIndex + 1)
							: vt070000dao.createProject(projectName, unit, rowIndex + 1);
					if (createPrjResult > 0) {
						projectId = org == 1 ? vt070000dao.getProjectIdGroup(projectName)
								: vt070000dao.getProjectId(projectName);
						row.setProjectId(projectId);
					}
				} else {
					row.setProjectId(projectId);
				}
			}
		} else {
			row.insertErrorCode(EMPTY_PROJECT);
			row.setCriticalError(true);
		}
		return row;
	}

	private ImportedDataRow validateProjectDoc(ImportedDataRow row) {
		String projectDoc = row.getProjectDoc();

		if (projectDoc.length() > 255) {
			row.insertErrorCode(TOO_LONG_PROJECT_DOC);
			row.setCriticalError(true);
		}

		return row;
	}

	private ImportedDataRow validatePackage(ImportedDataRow row) {
		String packageName = row.getPackageName();
		String packageDoc = row.getPackageDoc();

		if (StringUtils.isEmpty(packageName)) {
			if (!StringUtils.isEmpty(packageDoc) || !StringUtils.isEmpty(row.getContractName())) {
				row.insertErrorCode(EMPTY_PACKAGE_WHILE_HAS_RELATE_DOC);
				row.setCriticalError(true);
			}
		}

		if (packageName.length() > 255) {
			row.insertErrorCode(TOO_LONG_PACKAGE_NAME);
			row.setCriticalError(true);
		}

		return row;
	}

	private ImportedDataRow validatePackageDoc(ImportedDataRow row) {
		String packageDoc = row.getPackageDoc();

		if (packageDoc.length() > 255) {
			row.insertErrorCode(TOO_LONG_PACKAGE_DOC);
			row.setCriticalError(true);
		}

		return row;
	}

	public ImportedDataRow validateContract(ImportedDataRow row) {
		String contractName = row.getContractName();
		String contractDoc = row.getContractDoc();
		String constructionDoc = row.getConstructionDoc();

		if (StringUtils.isEmpty(contractName)) {
			if (!StringUtils.isEmpty(contractDoc) || !StringUtils.isEmpty(row.getConstructionName())) {
				row.insertErrorCode(EMPTY_CONTRACT_WHILE_HAS_RELATE_DOC);
				row.setCriticalError(true);
			}
		}

		if (contractName.length() > 255) {
			row.insertErrorCode(TOO_LONG_CONTRACT_NAME);
			row.setCriticalError(true);
		}

		return row;

	}

	private ImportedDataRow validateContractDoc(ImportedDataRow row) {
		String contractDoc = row.getContractDoc();

		if (contractDoc.length() > 255) {
			row.insertErrorCode(TOO_LONG_CONTRACT_DOC);
			row.setCriticalError(true);
		}

		return row;
	}

	public ImportedDataRow validateConstruction(ImportedDataRow row) {
		String constructionName = row.getConstructionName();
		String constructionDoc = row.getConstructionDoc();

		if (constructionDoc.length() > 0 && constructionName.length() == 0) {
			row.insertErrorCode(EMPTY_CONSTRUCTION_WHILE_HAS_RELATE_DOC);
			row.setCriticalError(true);
		}

		if (constructionName.length() > 255) {
			row.insertErrorCode(TOO_LONG_CONSTRUCTION_NAME);
			row.setCriticalError(true);
		}

		return row;

	}

	private ImportedDataRow validateConstructionDoc(ImportedDataRow row) {
		String constructionDoc = row.getConstructionDoc();

		if (constructionDoc.length() > 255) {
			row.insertErrorCode(TOO_LONG_CONSTRUCTION_DOC);
			row.setCriticalError(true);
		}

		return row;
	}

	private ImportedDataRow validateLevelBaoMatConstruction(ImportedDataRow row) {
		String levelBaoMatConstruction = row.getLevelBaoMatConstruction();
		String constructionDoc = row.getConstructionDoc();
		if (!StringUtils.isEmpty(constructionDoc) && StringUtils.isEmpty(levelBaoMatConstruction)) {
			row.insertErrorCode(EMPTY_LEVEL_BAOMAT_CONSTRUCTION_DOC);
			row.setCriticalError(true);
		}
		if (checkLevelBaoMat(levelBaoMatConstruction)) {
			row.insertErrorCode(INVALID_LEVEL_BAOMAT_CONSTRUCTION_DOC);
			row.setCriticalError(true);
		}
		return row;
	}

	private ImportedDataRow validateLevelBaoMatProject(ImportedDataRow row) {
		String levelBaoMatProject = row.getLevelBaoMatProject();
		String projectDoc = row.getProjectDoc();
		if (!StringUtils.isEmpty(projectDoc) && StringUtils.isEmpty(levelBaoMatProject)) {
			row.insertErrorCode(EMPTY_LEVEL_BAOMAT_PROJECT_DOC);
			row.setCriticalError(true);
		}
		if (checkLevelBaoMat(levelBaoMatProject)) {
			row.insertErrorCode(INVALID_LEVEL_BAOMAT_PROJECT_DOC);
			row.setCriticalError(true);
		}
		return row;
	}

	private ImportedDataRow validateLevelBaoMatContract(ImportedDataRow row) {
		String levelBaoMatContract = row.getLevelBaoMatContract();
		String contractDoc = row.getContractDoc();
		if (!StringUtils.isEmpty(contractDoc) && StringUtils.isEmpty(levelBaoMatContract)) {
			row.insertErrorCode(EMPTY_LEVEL_BAOMAT_CONTRACT_DOC);
			row.setCriticalError(true);
		}
		if (checkLevelBaoMat(levelBaoMatContract)) {
			row.insertErrorCode(INVALID_LEVEL_BAOMAT_CONTRACT_DOC);
			row.setCriticalError(true);
		}
		return row;
	}

	private ImportedDataRow validateLevelBaoMatPackage(ImportedDataRow row) {
		String levelBaoMatPackage = row.getLevelBaoMatPackage();
		String packageDoc = row.getPackageDoc();
		if (!StringUtils.isEmpty(packageDoc) && StringUtils.isEmpty(levelBaoMatPackage)) {
			row.insertErrorCode(EMPTY_LEVEL_BAOMAT_PACKAGE_DOC);
			row.setCriticalError(true);
		}
		if (checkLevelBaoMat(levelBaoMatPackage)) {
			row.insertErrorCode(INVALID_LEVEL_BAOMAT_PACKAGE_DOC);
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
}
