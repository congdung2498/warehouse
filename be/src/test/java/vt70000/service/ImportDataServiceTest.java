package vt70000.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt07.vt070000.dao.VT070000DAO;
import com.viettel.vtnet360.vt07.vt070000.entity.ImportDataRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.ImportedDataRow;
import com.viettel.vtnet360.vt07.vt070000.service.ImportDataServiceImpl;
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;

import misc.BeanUtilities;
import misc.MockPrincipal;

public class ImportDataServiceTest {
	
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
	
	//critial validate errors make all documents in a row not inserted
	private final int TIN_BOX_NOT_FOUND = -101;
	private final int FOLDER_NOT_FOUND_IN_TIN_BOX = -102;
	private final int THIS_USER_NOT_CREATE_THIS_BOX = -103;
	private final int EMPTY_TIN_BOX = -104;
	private final int EMPTY_CREATE_USER = -105;
	private final int EMPTY_FOLDER = -106;
	private final int EMPTY_PROJECT = -107;
	private final int TOO_LONG_PROJECT_NAME = -108;
	
	private final String EXCEL_TEMPLATE_FOLDER_FULL_IMPORT_SAMPLE = "/var/templateExcel/File_mau_tai_du_lieu_ho_so.xlsx";

	private final String EXCEL_TEMPLATE_PROJECT_SIMPLE_IMPORT_SAMPLE = "/var/templateExcel/File_mau_tai_du_lieu_du_an.xlsx";

	private final String EXCEL_TEMPLATE_FOLDER_FULL_IMPORT_RESULT = "/var/templateExcel/Ket_qua_tai_len_du_lieu_ho_so.xlsx";

	private final String EXCEL_TEMPLATE_PROJECT_SIMPLE_IMPORT_RESULT = "/var/templateExcel/Ket_qua_tai_len_du_lieu_du_an.xlsx";
	
	Workbook simpleSample = null;
	Workbook simpleResult = null;
	Workbook fullSample = null;
	Workbook fullResult = null;
	
	Collection<GrantedAuthority> userRoles;
	private OAuth2Authentication principal;
	ImportDataRequest mockImportDataReq = new ImportDataRequest();
	
	ImportedDataRow uploadedRow = new ImportedDataRow();
	List<ImportedDataRow> dataRows = new ArrayList<ImportedDataRow>();

	@InjectMocks
	private ImportDataServiceImpl mockService;
	
	@Mock
    private OAuth2Authentication mockPrincipal;
	
	@Spy
	VT070000DAO vt070000dao;
	
	@Spy
	Properties linkTemplateExcel;
	
	@Before
	public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		simpleSample = getExcelFile(EXCEL_TEMPLATE_PROJECT_SIMPLE_IMPORT_SAMPLE);
		fullSample = getExcelFile(EXCEL_TEMPLATE_FOLDER_FULL_IMPORT_SAMPLE);
		simpleResult = getExcelFile(EXCEL_TEMPLATE_PROJECT_SIMPLE_IMPORT_RESULT);
		fullResult = getExcelFile(EXCEL_TEMPLATE_FOLDER_FULL_IMPORT_RESULT);
		
		uploadedRow.setConstructionDoc("cons doc");
		uploadedRow.setConstructionName("cons name");
		uploadedRow.setContractDoc("ct doc");
		uploadedRow.setContractName("ct name");
		uploadedRow.setCreateUser("cr user");
		uploadedRow.setCriticalError(false);
		uploadedRow.setErrorCodes(new ArrayList<Integer>());
		uploadedRow.setFolderId(69);
		uploadedRow.setFolderQrCode("folder qr");
		uploadedRow.setPackageDoc("pk doc");
		uploadedRow.setPackageName("pk name");
		uploadedRow.setProjectDoc("pj doc");
		uploadedRow.setProjectId(6969);
		uploadedRow.setProjectName("pj name");
		uploadedRow.setRowAffected(1);
		uploadedRow.setRowReport("row rp");
		uploadedRow.setTinBoxId(696969);
		uploadedRow.setTinBoxQrCode("tinbox qr");
		uploadedRow.setUnit(-1);
		
		dataRows.add(uploadedRow);
		
		mockImportDataReq.setDataRows(dataRows);
		
		MockPrincipal mockUser = new MockPrincipal();
    	principal = new OAuth2Authentication(null, mockUser);
    	OAuth2Authentication oauth = (OAuth2Authentication) principal;
    	userRoles = oauth.getAuthorities();
	}
	
	public Workbook getExcelFile(String excelFilePath) throws IOException{
		Workbook wb = null;
		File file = null;
		Resource resource = new ClassPathResource(excelFilePath);
		file = resource.getFile();
		FileInputStream inputStream = new FileInputStream(file);
		wb = new XSSFWorkbook(inputStream);
		return wb;
	}
	
	@Test
	public void getExcelTemplateTestIOExcepion(){
		Mockito.doReturn("asdasdasd").when(linkTemplateExcel).getProperty("");
		Workbook wb = mockService.getExcelTemplate("");
		assertEquals(null, wb);
	}
	
	@Test
	public void getExcelTemplateSucc() throws IOException{
		String propName = "EXCEL_TEMPLATE_FOLDER_FULL_IMPORT_SAMPLE";
		Mockito.doReturn(EXCEL_TEMPLATE_FOLDER_FULL_IMPORT_SAMPLE).when(linkTemplateExcel).getProperty(propName);
		Workbook result = mockService.getExcelTemplate(propName);
		assertEquals(fullSample.getSheetAt(0).getSheetName(), result.getSheetAt(0).getSheetName());
	}
	
	@Test
	public void bindDataToTemplateTest() {
		Workbook result = mockService.bindResultDataToTemplate(dataRows, fullResult, FULL_IMPORT_TYPE);
		Row row =  result.getSheetAt(0).getRow(1);
		assertEquals(1, (long) row.getCell(0).getNumericCellValue());
		assertEquals("tinbox qr", row.getCell(1).getStringCellValue());
		assertEquals("cr user", row.getCell(2).getStringCellValue());
		assertEquals("folder qr", row.getCell(3).getStringCellValue());
		assertEquals("pj name", row.getCell(4).getStringCellValue());
		assertEquals("pj doc", row.getCell(5).getStringCellValue());
		assertEquals("pk name", row.getCell(6).getStringCellValue());
		assertEquals("pk doc", row.getCell(7).getStringCellValue());
		assertEquals("ct name", row.getCell(8).getStringCellValue());
		assertEquals("ct doc", row.getCell(9).getStringCellValue());
		assertEquals("cons name", row.getCell(10).getStringCellValue());
		assertEquals("cons doc", row.getCell(11).getStringCellValue());
		assertEquals("row rp", row.getCell(12).getStringCellValue());
	}
	
	@Test
	public void importFullDataTestSetUnitZero() {
		Object result = mockService.importFullData(dataRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(0, resultData.get(0).getUnit());		
	}
	
	@Test
	public void importFullDataTestSetUnitOne() {
		OAuth2Authentication oauth = (OAuth2Authentication) mockPrincipal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		SimpleGrantedAuthority unit1 = new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_GROUP);
		roleList.add(unit1);
		Object result = mockService.importFullData(dataRows, roleList);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(1, resultData.get(0).getUnit());		
	}
	
	@Test
	public void importFullDataTestEmptyTinBoxQr() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setTinBoxQrCode("");
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).isCriticalError());
		assertEquals(true, resultData.get(0).getErrorCodes().contains(EMPTY_TIN_BOX));
	}
	
	@Test
	public void importFullDataTestNotFoundTinBox() {
		List<ImportedDataRow> testRows = dataRows;
		Mockito.doReturn((long) -1).when(vt070000dao).getTinBoxId("tinbox qr", 0);		
		Object result = mockService.importFullData(testRows, userRoles);		
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).isCriticalError());
		assertEquals(true, resultData.get(0).getErrorCodes().contains(TIN_BOX_NOT_FOUND));
	}
	
	@Test
	public void importFullDataTestEmptyUser() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setCreateUser("");
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).isCriticalError());
		assertEquals(true, resultData.get(0).getErrorCodes().contains(EMPTY_CREATE_USER));
	}
	
	@Test
	public void importFullDataTestUserNotCreateBox() {
		List<ImportedDataRow> testRows = dataRows;
		Mockito.doReturn((long) 96969696).when(vt070000dao).getTinBoxId("tinbox qr", 0);	
		Mockito.doReturn(0).when(vt070000dao).checkTinBoxCreateUser(96969696, testRows.get(0).getCreateUser());
		Object result = mockService.importFullData(testRows, userRoles);			
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).isCriticalError());
		assertEquals(true, resultData.get(0).getErrorCodes().contains(THIS_USER_NOT_CREATE_THIS_BOX));
	}
	
	@Test
	public void importFullDataTestEmptyFolder() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setFolderQrCode("");
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).isCriticalError());
		assertEquals(true, resultData.get(0).getErrorCodes().contains(EMPTY_FOLDER));
	}
	
	@Test
	public void importFullDataTestFolderNotInBox() {
		List<ImportedDataRow> testRows = dataRows;
		Mockito.doReturn((long) -1).when(vt070000dao).getFolderId("folder qr", "tinbox qr", 0);
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).isCriticalError());
		assertEquals(true, resultData.get(0).getErrorCodes().contains(FOLDER_NOT_FOUND_IN_TIN_BOX));
	}
	
	@Test
	public void importFullDataTestEmptyProject() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setProjectName("");
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).isCriticalError());
		assertEquals(true, resultData.get(0).getErrorCodes().contains(EMPTY_PROJECT));
	}
	
	@Test
	public void importFullDataTestTooLongProject() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setProjectName(BeanUtilities.randomString(256));
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).isCriticalError());
		assertEquals(true, resultData.get(0).getErrorCodes().contains(TOO_LONG_PROJECT_NAME));
	}
	
	@Test
	public void importFullDataTestCreateProject() {
		List<ImportedDataRow> testRows = dataRows;
		Mockito.doReturn((long)-1).when(vt070000dao).getProjectId("pj name");
		Mockito.doReturn(1).when(vt070000dao).createProject("pj name", 0, 1);
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		System.out.println(resultData.get(0).getErrorCodes());
		assertEquals(false, resultData.get(0).isCriticalError());
		assertEquals(-1, resultData.get(0).getProjectId());
	}
	
	@Test
	public void importFullDataTestTooLongProjectDoc() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setProjectDoc(BeanUtilities.randomString(256));
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).getErrorCodes().contains(TOO_LONG_PROJECT_DOC));
	}
	
	@Test
	public void importFullDataTestEmtpyPkgWhileHasPkgDoc() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setPackageName("");
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).getErrorCodes().contains(EMPTY_PACKAGE_WHILE_HAS_RELATE_DOC));
	}
	
	@Test
	public void importFullDataTestTooLongPackageDoc() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setPackageDoc(BeanUtilities.randomString(256));
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).getErrorCodes().contains(TOO_LONG_PACKAGE_DOC));
	}
	
	@Test
	public void importFullDataTestTooLongPackageName() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setPackageName(BeanUtilities.randomString(256));
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).getErrorCodes().contains(TOO_LONG_PACKAGE_NAME));
	}
	
	@Test
	public void importFullDataTestEmtpyContractWhileHasRelatedDoc() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setContractName("");
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).getErrorCodes().contains(EMPTY_CONTRACT_WHILE_HAS_RELATE_DOC));
	}
	
	@Test
	public void importFullDataTestTooLongContractName() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setContractName(BeanUtilities.randomString(256));
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).getErrorCodes().contains(TOO_LONG_CONTRACT_NAME));
	}
	
	@Test
	public void importFullDataTestTooLongContractDoc() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setContractDoc(BeanUtilities.randomString(256));
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).getErrorCodes().contains(TOO_LONG_CONTRACT_DOC));
	}
	
	@Test
	public void importFullDataTestEmtpyConstructionWhileHasRelatedDoc() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setConstructionName("");
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).getErrorCodes().contains(EMPTY_CONSTRUCTION_WHILE_HAS_RELATE_DOC));
	}
	
	@Test
	public void importFullDataTestTooLongConstructionName() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setConstructionName(BeanUtilities.randomString(256));
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).getErrorCodes().contains(TOO_LONG_CONSTRUCTION_NAME));
	}
	
	@Test
	public void importFullDataTestTooLongConstructionDoc() {
		List<ImportedDataRow> testRows = dataRows;
		testRows.get(0).setConstructionDoc(BeanUtilities.randomString(256));
		Object result = mockService.importFullData(testRows, userRoles);
		List<ImportedDataRow> resultData = (List<ImportedDataRow>) BeanUtilities.getObjectFieldValue(result, "resultDetails");
		assertEquals(true, resultData.get(0).getErrorCodes().contains(TOO_LONG_CONSTRUCTION_DOC));
	}
}
