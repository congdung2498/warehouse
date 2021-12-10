package vt70000.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.vtnet360.common.security.Account;
import com.viettel.vtnet360.vt00.common.BaseEntity;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070000.controller.VT070000Controller;
import com.viettel.vtnet360.vt07.vt070000.dao.VT070000DAO;
import com.viettel.vtnet360.vt07.vt070000.entity.DocumentDetails;
import com.viettel.vtnet360.vt07.vt070000.entity.ImportDataRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.RackDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.RequestParam;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxDetailsRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseRequestParam;
import com.viettel.vtnet360.vt07.vt070000.service.ImportDataService;
import com.viettel.vtnet360.vt07.vt070000.service.TinBoxSearchService;
import com.viettel.vtnet360.vt07.vt070000.service.TinBoxSearchServiceImpl;
import com.viettel.vtnet360.vt07.vt070000.service.VT070000Service;

import misc.MockPrincipal;

import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxSearchRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxSearchResult;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.List;

import static org.mockito.Mockito.verify;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "testContext.xml")
//@PropertySource("controllerTest.properties")
public class VT070000ControllerTest {	
	private static final String ENCRYPTED_USERNAME = "a37d7e12b490d7000dcc6fa0fd7066c2";
	private static final String ENCRYPTED_PASSWORD = "c9a0172b171314c3eb811606a8d1528b";
	private String defaultUsername = "username";
	@InjectMocks
	private VT070000Controller mockController;	
	
	@Spy
	private Properties configProperty;
	
	@Spy
	private VT070000Service vt070000Service;
	
	@Spy
	private TinBoxSearchService tinBoxSearchService;
	
	@Spy
	private ImportDataService importDataService;
	
	
    private OAuth2Authentication principal;
	
	@Mock
	private Collection<GrantedAuthority> roleList;
	
	@Mock
	WarehouseRequestParam warehouseDetail;
	 
	@Before
	public void setup() {
		//mockController = mock(VT070000Controller.class);	
//		mockController = new VT070000Controller();
//		System.out.println(mockProperties.getProperty("SECURITY_USERNAME"));		
		MockitoAnnotations.initMocks(this);
		Mockito.doReturn(ENCRYPTED_USERNAME).when(configProperty).getProperty("SECURITY_USERNAME");
    	Mockito.doReturn(ENCRYPTED_PASSWORD).when(configProperty).getProperty("SECURITY_PASSWORD");
//		mockController.setProperties(configProperty);
    	
    	MockPrincipal mockUser = new MockPrincipal();
    	principal = new OAuth2Authentication(null, mockUser);
    	//when(principal.getName()).thenReturn(defaultUsername);
	}
	       
    @Test                                                                                         
    public void getListWarehouseAuthenFailTest() {
    	RequestParam  mockRequestParam = new RequestParam();
    	ResponseEntityBase response = mockController.getListWarehouse(mockRequestParam);
    	
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test                                                                                         
    public void getListWarehouseAuthenHasException() {
    	RequestParam  mockRequestParam = null;
    	ResponseEntityBase response = mockController.getListWarehouse(mockRequestParam);    	
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test                                                                                         
    public void getListWarehouseAuthenSuccessTest() {
	
    	RequestParam  mockRequestParam = new RequestParam();    	
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");  	
    	
    	Mockito.doReturn(new Object()).when(vt070000Service).getListWarehouseDetail(mockRequestParam);
    	ResponseEntityBase response = mockController.getListWarehouse(mockRequestParam);
    	
    	assertEquals(Constant.SUCCESS, response.getStatus());
    }
    
    @Test
    public void getListMainWarehouseAuthenFailTest() {
    	RequestParam  mockRequestParam = new RequestParam();
    	ResponseEntityBase response = mockController.getListMainWarehouse(mockRequestParam);
    	
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void getListMainWarehouseHasException() {
    	RequestParam  mockRequestParam = null;
    	ResponseEntityBase response = mockController.getListMainWarehouse(mockRequestParam);    	
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void getListMainWarehouseAuthenSuccessTest() {
    	RequestParam  mockRequestParam = new RequestParam();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new Object()).when(vt070000Service).getListMainWarehouseDetail(mockRequestParam);
    	ResponseEntityBase response = mockController.getListMainWarehouse(mockRequestParam);
    	
    	assertEquals(Constant.SUCCESS, response.getStatus());
    }
    
    @Test
    public void updateWarehouseAuthenFailTest() {
    	WarehouseDetail mockRequestParam = new WarehouseDetail();
    	ResponseEntityBase response = mockController.updateWarehouse(mockRequestParam, principal);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void updateWarehouseHasException() {
    	WarehouseDetail mockRequestParam = null;
    	ResponseEntityBase response = mockController.updateWarehouse(mockRequestParam, principal);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void updateWarehouseAuthenSuccessTest() {
    	WarehouseDetail mockRequestParam = new WarehouseDetail();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new Object()).when(vt070000Service).updateWarehouseDetail(mockRequestParam, defaultUsername);
    	ResponseEntityBase response = mockController.updateWarehouse(mockRequestParam, principal);
    	
    	assertEquals(Constant.SUCCESS, response.getStatus());
    }
    
    @Test
    public void getLocationByQrCodeAuthenFailTest() {
    	RequestParam mockRequestParam = new RequestParam();
    	ResponseEntityBase response = mockController.getLocationByQrCode(mockRequestParam);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void getLocationByQrCodeHasException() {
    	RequestParam mockRequestParam = null;
    	ResponseEntityBase response = mockController.getLocationByQrCode(mockRequestParam);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void getLocationByQrCodeAuthenSuccessTest() {
    	RequestParam mockRequestParam = new RequestParam();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new Object()).when(vt070000Service).getLocationByQrCode(mockRequestParam);
    	ResponseEntityBase response = mockController.getLocationByQrCode(mockRequestParam);
    	assertEquals(Constant.SUCCESS, response.getStatus());
    }
    
    @Test
    public void getListWarehouseAuthenFail() {
    	WarehouseRequestParam mockRequestParam = new WarehouseRequestParam();
    	ResponseEntityBase response = mockController.getListWarehouse(mockRequestParam, principal);
    	assertEquals(null, response.getData());
    }
    
    @Test
    public void getListWarehouseHasException() {
    	WarehouseRequestParam mockRequestParam = null;
    	ResponseEntityBase response = mockController.getListWarehouse(mockRequestParam, principal);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void getListWarehouseAuthenSuccess() {
    	WarehouseRequestParam mockRequestParam = new WarehouseRequestParam();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070000Service).getWarehouseDetail(mockRequestParam);
    	ResponseEntityBase response = mockController.getListWarehouse(mockRequestParam, principal);
    	assertEquals(Constant.SUCCESS, response.getStatus());
    }
    
    @Test
    public void insertUpdateWarehouseAuthenFail() {
    	WarehouseRequestParam mockRequestParam = new WarehouseRequestParam();
    	ResponseEntityBase response = mockController.insertUpdateWarehouse(mockRequestParam, principal);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void insertUpdateWarehouseHasException() {
    	WarehouseRequestParam mockRequestParam = null;
    	ResponseEntityBase response = mockController.insertUpdateWarehouse(mockRequestParam, principal);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void insertUpdateWarehouseAuthenSuccessAndValidData() {
    	WarehouseRequestParam mockRequestParam = new WarehouseRequestParam();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	mockRequestParam.setColumnNum("1");
    	mockRequestParam.setRowNum("1");
    	mockRequestParam.setHeightNum("1");
    	OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		mockRequestParam.setCreateUser(userName);
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
    	String body = (new ObjectMapper()).valueToTree(mockRequestParam).toString();
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070000Service).insertUpdateWarehouse(mockRequestParam, roleList);
    	ResponseEntityBase response = mockController.insertUpdateWarehouse(mockRequestParam, principal);
    	assertEquals(Constant.SUCCESS, response.getStatus());
    }
    
    @Test
    public void insertUpdateWarehouseAuthenSuccessAndNegativeData() {
    	WarehouseRequestParam mockRequestParam = new WarehouseRequestParam();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	mockRequestParam.setColumnNum("-1");
    	mockRequestParam.setRowNum("-1");
    	mockRequestParam.setHeightNum("-1");
    	OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		mockRequestParam.setCreateUser(userName);
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
    	String body = (new ObjectMapper()).valueToTree(mockRequestParam).toString();
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070000Service).insertUpdateWarehouse(mockRequestParam, roleList);
    	ResponseEntityBase response = mockController.insertUpdateWarehouse(mockRequestParam, principal);
    	assertEquals(-1, response.getStatus());
    }
    
    @Test
    public void insertUpdateWarehouseAuthenSuccessAndTooBigData() {
    	WarehouseRequestParam mockRequestParam = new WarehouseRequestParam();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	mockRequestParam.setColumnNum("999");
    	mockRequestParam.setRowNum("999");
    	mockRequestParam.setHeightNum("999");
    	OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		mockRequestParam.setCreateUser(userName);
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
    	String body = (new ObjectMapper()).valueToTree(mockRequestParam).toString();
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070000Service).insertUpdateWarehouse(mockRequestParam, roleList);
    	ResponseEntityBase response = mockController.insertUpdateWarehouse(mockRequestParam, principal);
    	assertEquals(-1, response.getStatus());
    }
    
    @Test
    public void deleteWarehouseAuthenFail() {
    	WarehouseRequestParam mockRequestParam = new WarehouseRequestParam();
    	ResponseEntityBase response = mockController.deleteWarehouse(mockRequestParam, principal);
    	assertEquals(Constant.ERROR, response.getStatus());    	
    }
    
    @Test
    public void deleteWarehouseHasException() {
    	WarehouseRequestParam mockRequestParam = null;
    	ResponseEntityBase response = mockController.deleteWarehouse(mockRequestParam, principal);
    	assertEquals(Constant.ERROR, response.getStatus());    	
    }
    
    @Test
    public void deleteWarehouseAuthenSuccess() {
    	ResponseEntityBase testSuccessResp = new ResponseEntityBase(Constant.SUCCESS, null);
    	WarehouseRequestParam mockRequestParam = new WarehouseRequestParam();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		mockRequestParam.setCreateUser(userName);
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
    	Mockito.doReturn(testSuccessResp).when(vt070000Service).deleteWarehouse(mockRequestParam, roleList);
    	ResponseEntityBase response = mockController.deleteWarehouse(mockRequestParam, principal);
    	System.out.println(response);
    	assertEquals(Constant.SUCCESS, response.getStatus());
    	
    }
    
    @Test
    public void getListRackSlotAuthenFail() {
    	WarehouseRequestParam mockRequestParam = new WarehouseRequestParam();
    	ResponseEntityBase response = mockController.getListRackSlot(mockRequestParam, principal);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void getListRackSlotHasException() {
    	WarehouseRequestParam mockRequestParam = null;
    	ResponseEntityBase response = mockController.getListRackSlot(mockRequestParam, principal);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void getListRackSlotAuthenSuccess() {
    	WarehouseRequestParam mockRequestParam = new WarehouseRequestParam();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		mockRequestParam.setCreateUser(userName);
		mockRequestParam.setWarehouseId("1");
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070000Service).getDiagram("1", roleList);
    	ResponseEntityBase response = mockController.getListRackSlot(mockRequestParam, principal);
    	assertEquals(Constant.SUCCESS, response.getStatus());
    }
    
    @Test
    public void getListWarehouseForDropdownAuthenFail() {
    	BaseEntity mockRequestParam = new BaseEntity();
    	ResponseEntityBase response = mockController.getListWarehouseForDropdown(mockRequestParam, principal);
    	assertEquals(null, response.getData());
    }
    
    @Test
    public void getListWarehouseForDropdownHasException() {
    	BaseEntity mockRequestParam = null;
    	ResponseEntityBase response = mockController.getListWarehouseForDropdown(mockRequestParam, principal);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void getListWarehouseForDropdownAuthenSuccess() {
    	BaseEntity mockRequestParam = new BaseEntity();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070000Service).getAllListWarehouseDetail(Matchers.any(WarehouseRequestParam.class));
    	ResponseEntityBase response = mockController.getListWarehouseForDropdown(mockRequestParam, principal);
    	assertEquals(Constant.SUCCESS, response.getStatus());
    }
    
    @Test
    public void getListSlotOfWarehouseAuthenFail() {
    	WarehouseRequestParam mockRequestParam = new WarehouseRequestParam();
    	ResponseEntityBase response = mockController.getListSlotOfWarehouse(mockRequestParam, principal);
    	assertEquals(null, response.getData());
    }
    
    @Test
    public void getListSlotOfWarehouseHasException() {
    	WarehouseRequestParam mockRequestParam = null;
    	ResponseEntityBase response = mockController.getListSlotOfWarehouse(mockRequestParam, principal);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void getListSlotOfWarehouseAuthenSuccess() {
    	WarehouseRequestParam mockRequestParam = new WarehouseRequestParam();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		mockRequestParam.setCreateUser(userName);
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070000Service).getListSlotOfWarehouse(mockRequestParam, roleList);
    	ResponseEntityBase response = mockController.getListSlotOfWarehouse(mockRequestParam, principal);
    	assertEquals(Constant.SUCCESS, response.getStatus());
    }
    
    @Test
    public void getAllActiveWarehouseAuthenFail() {
    	BaseEntity mockRequestParam = new BaseEntity();
    	ResponseEntityBase response = mockController.getAllActiveWarehouse(mockRequestParam);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void getAllActiveWarehouseHasException() {
    	BaseEntity mockRequestParam = null;
    	ResponseEntityBase response = mockController.getAllActiveWarehouse(mockRequestParam);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void getAllActiveWarehouseAuthenSuccess() {
    	BaseEntity mockRequestParam = new BaseEntity();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ArrayList<WarehouseDetail>()).when(tinBoxSearchService).getAllActiveWarehouse();
    	ResponseEntityBase response = mockController.getAllActiveWarehouse(mockRequestParam);
    	assertEquals(Constant.SUCCESS, response.getStatus());
    }
    
    @Test
    public void searchDocumentInWarehouseAuthenFail() {
    	TinBoxSearchRequest mockRequestParam = new TinBoxSearchRequest();
    	ResponseEntityBase response = mockController.searchDocumentInWarehouse(mockRequestParam);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void searchDocumentInWarehouseHasException() {
    	TinBoxSearchRequest mockRequestParam = null;
    	ResponseEntityBase response = mockController.searchDocumentInWarehouse(mockRequestParam);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void searchDocumentInWarehouseAuthenSuccess() {
    	TinBoxSearchRequest mockRequestParam = new TinBoxSearchRequest();
    	mockRequestParam.setWarehouseId(1234);
    	mockRequestParam.setByTinBox(true);
    	mockRequestParam.setByProject(true);
    	mockRequestParam.setByFolder(true);
    	mockRequestParam.setByPackage(true);
    	mockRequestParam.setByContract(true);
    	mockRequestParam.setByConstruction(true);
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	mockRequestParam.setKeyword("abcd");
    	mockRequestParam.toString();
    	int warehouseId = mockRequestParam.getWarehouseId();
        String keyword = mockRequestParam.getKeyword();
    	boolean byTinBox = mockRequestParam.isByTinBox();
    	boolean byFolder = mockRequestParam.isByFolder();
    	boolean byProject = mockRequestParam.isByProject();
    	boolean byPackage = mockRequestParam.isByPackage();
    	boolean byContract = mockRequestParam.isByContract();
    	boolean byConstruction = mockRequestParam.isByConstruction();
    	Mockito.doReturn(new ArrayList<TinBoxSearchResult>()).when(tinBoxSearchService).searchTinBox( warehouseId, keyword, byTinBox, byFolder, byProject, byPackage, byContract, byConstruction);
    	ResponseEntityBase response = mockController.searchDocumentInWarehouse(mockRequestParam);
    	assertEquals(Constant.SUCCESS, response.getStatus());
    }
    
    @Test
    public void getTinBoxDetailsAuthenFail() {
    	TinBoxDetailsRequest mockRequestParam = new TinBoxDetailsRequest();
    	ResponseEntityBase response = mockController.getTinBoxDetails(mockRequestParam);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void getTinBoxDetailsHasException() {
    	TinBoxDetailsRequest mockRequestParam = null;
    	ResponseEntityBase response = mockController.getTinBoxDetails(mockRequestParam);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void getTinBoxDetailsAuthenSuccessAndValidRequest() {
    	TinBoxDetailsRequest mockRequestParam = new TinBoxDetailsRequest();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	mockRequestParam.setTinBoxId(2);
    	Mockito.doReturn(new ArrayList<DocumentDetails>()).when(tinBoxSearchService).getDocumentsInBox(mockRequestParam.getTinBoxId());
    	ResponseEntityBase response = mockController.getTinBoxDetails(mockRequestParam);
    	assertEquals(Constant.SUCCESS, response.getStatus());
    }
    
    @Test
    public void getTinBoxDetailsAuthenSuccessAndInvalidRequest() {
    	TinBoxDetailsRequest mockRequestParam = new TinBoxDetailsRequest();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	mockRequestParam.setTinBoxId(-9);
    	Mockito.doReturn(new ArrayList<DocumentDetails>()).when(tinBoxSearchService).getDocumentsInBox(mockRequestParam.getTinBoxId());
    	ResponseEntityBase response = mockController.getTinBoxDetails(mockRequestParam);
    	assertEquals(Constant.ERROR, response.getStatus());
    }
    
    @Test
    public void findSearchingRackResultAuthenFail() throws Exception {
    	RackDetail mockRequestParam = new RackDetail();
    	ResponseEntityBase response = mockController.findSearchingRackResult(mockRequestParam);
    	assertEquals(null, response);    	
    }
    
    @Test
    public void findSearchingRackResultHasException() throws Exception{
    	RackDetail mockRequestParam = null;
    	ResponseEntityBase response = mockController.findSearchingRackResult(mockRequestParam);
    	assertEquals(null, response);    	
    }
    
    @Test
    public void findSearchingRackResultAuthenSuccess() throws Exception {
    	RackDetail mockRequestParam = new RackDetail();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070000Service).findRack(mockRequestParam);
    	ResponseEntityBase response = mockController.findSearchingRackResult(mockRequestParam);
    	assertEquals(Constant.SUCCESS, response.getStatus());    	
    }
    
    @Test
    public void updatePrintTimeRackAuthenFail() throws Exception {
    	RackDetail mockRequestParam = new RackDetail();
    	ResponseEntityBase response = mockController.updatePrintTimeRack(mockRequestParam);
    	assertEquals(null, response); 
    }
    
    @Test
    public void updatePrintTimeRackHasException() throws Exception {
    	RackDetail mockRequestParam = null;
    	ResponseEntityBase response = mockController.updatePrintTimeRack(mockRequestParam);
    	assertEquals(null, response); 
    }
    
    @Test
    public void updatePrintTimeRackAuthenSuccess() throws Exception {
    	RackDetail mockRequestParam = new RackDetail();
    	mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070000Service).updatePrintTimeRack(mockRequestParam);
    	ResponseEntityBase response = mockController.updatePrintTimeRack(mockRequestParam);
    	assertEquals(Constant.SUCCESS, response.getStatus()); 
    }
    
    @Test 
    public void searchDocInWh2TestAuthenFail() throws Exception{
    	TinBoxSearchRequest mockReq = new TinBoxSearchRequest();
    	ResponseEntityBase response = mockController.searchDocumentInWarehouseV2(mockReq);
    	assertEquals(Constant.ERROR, response.getStatus());    	
    }
    
    @Test 
    public void searchDocInWh2TestHasException() throws Exception{
    	TinBoxSearchRequest mockReq = null;
    	ResponseEntityBase response = mockController.searchDocumentInWarehouseV2(mockReq);
    	assertEquals(Constant.ERROR, response.getStatus());    	
    }
    
    @Test 
    public void searchDocInWh2TestAuthenSucc() throws Exception{
    	TinBoxSearchRequest mockReq = new TinBoxSearchRequest();
    	mockReq.setSecurityUsername("username");
    	mockReq.setSecurityPassword("password");
    	ResponseEntityBase response = mockController.searchDocumentInWarehouseV2(mockReq);
    	assertEquals(Constant.SUCCESS, response.getStatus());    	
    }
    
    @Test 
    public void importDataTestAuthenFail() throws Exception{
    	ImportDataRequest mockReq = new ImportDataRequest();
    	ResponseEntityBase response = mockController.importData(mockReq, principal);
    	assertEquals(Constant.ERROR, response.getStatus());    	
    }
    
    @Test 
    public void importDataTestHasException() throws Exception{
    	ImportDataRequest mockReq = null;
    	ResponseEntityBase response = mockController.importData(mockReq, principal);
    	assertEquals(Constant.ERROR, response.getStatus());    	
    }
    
    @Test 
    public void importDataTestAutheSucc() throws Exception{
    	ImportDataRequest mockReq = new ImportDataRequest();
    	mockReq.setSecurityUsername("username");
    	mockReq.setSecurityPassword("password");
    	Mockito.doReturn(new Object()).when(importDataService).importFullData(mockReq.getData(), null);
    	ResponseEntityBase response = mockController.importData(mockReq, principal);
    	
    	assertEquals(Constant.SUCCESS, response.getStatus());    	
    }
    
    @Test 
    public void importDocsTestAuthenFail() throws Exception{
    	ImportDataRequest mockReq = new ImportDataRequest();
    	ResponseEntityBase response = mockController.importDocuments(mockReq, principal);
    	assertEquals(Constant.ERROR, response.getStatus());    	
    }
    
    @Test 
    public void importDocsTestHasException() throws Exception{
    	ImportDataRequest mockReq = null;
    	ResponseEntityBase response = mockController.importDocuments(mockReq, principal);
    	assertEquals(Constant.ERROR, response.getStatus());    	
    }
    
    @Test 
    public void importDocsTestAuthenSucc() throws Exception{
    	ImportDataRequest mockReq = new ImportDataRequest();
    	mockReq.setSecurityUsername("username");
    	mockReq.setSecurityPassword("password");
    	Mockito.doReturn(new Object()).when(importDataService).importSimpleData(mockReq.getData(), null);
    	ResponseEntityBase response = mockController.importDocuments(mockReq, principal);
    	assertEquals(Constant.SUCCESS, response.getStatus());    	
    }
    
    @Test 
    public void getProjectImportSampleTestAuthenFail() throws Exception{
    	ImportDataRequest mockReq = new ImportDataRequest();
    	HttpHeaders headers = new HttpHeaders();
    	ResponseEntity<byte[]> response = mockController.getProjectImportSample(mockReq, principal);
    	assertEquals(new ResponseEntity<>(new byte[0], headers, HttpStatus.OK), response); 
    }
    
    @Test 
    public void getProjectImportSampleHasException() throws Exception{
    	ImportDataRequest mockReq = null;
    	HttpHeaders headers = new HttpHeaders();
    	ResponseEntity<byte[]> response = mockController.getProjectImportSample(mockReq, principal);
    	assertEquals(new ResponseEntity<>(new byte[0], headers, HttpStatus.OK), response); 
    }
    
}
