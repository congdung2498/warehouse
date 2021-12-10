package vt70002.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.viettel.vtnet360.common.security.AuthenticationProviderImpl;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070002.controller.VT070002Controller;
import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.RequestParam;
import com.viettel.vtnet360.vt07.vt070002.entity.SearchFolderRequest;
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;
import com.viettel.vtnet360.vt07.vt070002.service.FolderService;
import com.viettel.vtnet360.vt07.vt070002.service.VT070002Service;

import misc.MockPrincipal;

public class VT070002ControllerTest {
	private static final String ENCRYPTED_USERNAME = "a37d7e12b490d7000dcc6fa0fd7066c2";
	private static final String ENCRYPTED_PASSWORD = "c9a0172b171314c3eb811606a8d1528b";
	private String defaultUsername = "username";
	
	@InjectMocks
	private VT070002Controller mockController;
	
	@Spy
	AuthenticationProviderImpl authenticationProviderImpl;
	@Spy
	Properties linkTemplateExcel;
	@Spy
	Properties configProperty;
	
	@Mock
    private OAuth2Authentication principal;
	
	@Mock
	private Collection<GrantedAuthority> roleList;
	
	@Spy
	VT070002Service vt070002Service;
	
	@Spy
	FolderService folderService;
	
	@Before
	public void setup() {		
		MockitoAnnotations.initMocks(this);
		Mockito.doReturn(ENCRYPTED_USERNAME).when(configProperty).getProperty("SECURITY_USERNAME");
    	Mockito.doReturn(ENCRYPTED_PASSWORD).when(configProperty).getProperty("SECURITY_PASSWORD");
    	MockPrincipal mockUser = new MockPrincipal();
    	principal = new OAuth2Authentication(null, mockUser);
    	
    	OAuth2Authentication oauth = (OAuth2Authentication) principal;
		roleList = oauth.getAuthorities();
	}
	
	@Test
	public void getListTinBoxTestAuthenFail() {
		RequestParam mockRequestParam = new RequestParam();
		ResponseEntityBase response = mockController.getListTinBox(mockRequestParam, principal);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getListTinBoxTestAuthenSuccess() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(0,null)).when(vt070002Service).getListTinBox(mockRequestParam, defaultUsername, roleList);
		ResponseEntityBase response = mockController.getListTinBox(mockRequestParam, principal);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void getListTinBoxTestAuthenSuccessHasException() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doThrow(new RuntimeException()).when(vt070002Service).getListTinBox(mockRequestParam, defaultUsername, roleList);
		ResponseEntityBase response = mockController.getListTinBox(mockRequestParam, principal);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void updateTinBoxTestAuthenFail() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		ResponseEntityBase response = mockController.updateTinBox(mockRequestParam, principal);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void updateTinBoxTestAuthenSuccess() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		mockRequestParam.setCreateUser(userName);
		Mockito.doReturn(1).when(vt070002Service).updateTinBox(mockRequestParam, userName, roleList);
		ResponseEntityBase response = mockController.updateTinBox(mockRequestParam, principal);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void updateTinBoxTestAuthenSuccessHasException() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		mockRequestParam.setCreateUser(userName);
		Mockito.doThrow(new RuntimeException()).when(vt070002Service).updateTinBox(mockRequestParam, userName, roleList);
		ResponseEntityBase response = mockController.updateTinBox(mockRequestParam, principal);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void createTinBoxTestAuthenFail() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		ResponseEntityBase response = mockController.createTinBox(mockRequestParam, principal);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void createTinBoxTestAuthenSuccess() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		mockRequestParam.setCreateUser(userName);
		Mockito.doReturn(1).when(vt070002Service).createTinBox(mockRequestParam, userName, roleList);
		ResponseEntityBase response = mockController.createTinBox(mockRequestParam, principal);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void createTinBoxTestAuthenSuccessHasException() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		mockRequestParam.setCreateUser(userName);
		Mockito.doThrow(new RuntimeException()).when(vt070002Service).createTinBox(mockRequestParam, userName, roleList);
		ResponseEntityBase response = mockController.createTinBox(mockRequestParam, principal);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getAvailProjectsTestAuthenFail() {
		RequestParam mockRequestParam = new RequestParam();
		ResponseEntityBase response = mockController.getAvailProjects(mockRequestParam, principal);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getAvailProjectsTestAuthenSuccess() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(0,null)).when(vt070002Service).getAvailObjectInProject(mockRequestParam, roleList);
		ResponseEntityBase response = mockController.getAvailProjects(mockRequestParam, principal);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void getAvailProjectsTestAuthenSuccessHasException() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doThrow(new RuntimeException()).when(vt070002Service).getAvailObjectInProject(mockRequestParam, roleList);
		ResponseEntityBase response = mockController.getAvailProjects(mockRequestParam, principal);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getAvailPackagesInProjectTestAuthenFail() {
		RequestParam mockRequestParam = new RequestParam();
		ResponseEntityBase response = mockController.getAvailPackagesInProject(mockRequestParam, principal);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getAvailPackagesInProjectTestAuthenSuccess() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(0,null)).when(vt070002Service).getAvailObjectInProject(mockRequestParam, roleList);
		ResponseEntityBase response = mockController.getAvailPackagesInProject(mockRequestParam, principal);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void getAvailPackagesInProjectTestAuthenSuccessHasException() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doThrow(new RuntimeException()).when(vt070002Service).getAvailObjectInProject(mockRequestParam, roleList);
		ResponseEntityBase response = mockController.getAvailPackagesInProject(mockRequestParam, principal);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getAvailContractsTestAuthenFail() {
		RequestParam mockRequestParam = new RequestParam();
		ResponseEntityBase response = mockController.getAvailContracts(mockRequestParam, principal);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getAvailContractsTestAuthenSuccess() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(0,null)).when(vt070002Service).getAvailObjectInProject(mockRequestParam, roleList);
		ResponseEntityBase response = mockController.getAvailContracts(mockRequestParam, principal);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void getAvailContractsTestAuthenSuccessHasException() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doThrow(new RuntimeException()).when(vt070002Service).getAvailObjectInProject(mockRequestParam, roleList);
		ResponseEntityBase response = mockController.getAvailContracts(mockRequestParam, principal);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getAvailConstructionsInContractTestAuthenFail() {
		RequestParam mockRequestParam = new RequestParam();
		ResponseEntityBase response = mockController.getAvailConstructionsInContract(mockRequestParam, principal);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getAvailConstructionsInContractTestAuthenSuccess() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(0,null)).when(vt070002Service).getAvailObjectInProject(mockRequestParam, roleList);
		ResponseEntityBase response = mockController.getAvailConstructionsInContract(mockRequestParam, principal);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void getAvailConstructionsInContractTestAuthenSuccessHasException() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doThrow(new RuntimeException()).when(vt070002Service).getAvailObjectInProject(mockRequestParam, roleList);
		ResponseEntityBase response = mockController.getAvailConstructionsInContract(mockRequestParam, principal);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getProjectObjectInFolderTestAuthenFail() {
		RequestParam mockRequestParam = new RequestParam();
		ResponseEntityBase response = mockController.getProjectObjectInFolder(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getProjectObjectInFolderTestAuthenSuccess() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(0,null)).when(vt070002Service).getProjectObjectInFolder(mockRequestParam);
		ResponseEntityBase response = mockController.getProjectObjectInFolder(mockRequestParam);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void getProjectObjectInFolderTestAuthenSuccessHasException() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doThrow(new RuntimeException()).when(vt070002Service).getProjectObjectInFolder(mockRequestParam);
		ResponseEntityBase response = mockController.getProjectObjectInFolder(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void searchListTinBoxTestAuthenFail() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		ResponseEntityBase response = mockController.searchListTinBox(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void searchListTinBoxTestAuthenSuccess() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS,null)).when(vt070002Service).searchListTinBox(mockRequestParam);
		ResponseEntityBase response = mockController.searchListTinBox(mockRequestParam);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void searchListTinBoxTestAuthenSuccessHasException() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doThrow(new RuntimeException()).when(vt070002Service).searchListTinBox(mockRequestParam);
		ResponseEntityBase response = mockController.searchListTinBox(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void findTypeWarehouseDetailTestAuthenFail() {
		WarehouseDetail mockRequestParam = new WarehouseDetail();
		ResponseEntityBase response = mockController.findTypeWarehouseDetail(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void findTypeWarehouseDetailTestAuthenSuccess() {
		WarehouseDetail mockRequestParam = new WarehouseDetail();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS,null)).when(vt070002Service).findTypeWarehouseDetail(mockRequestParam);
		ResponseEntityBase response = mockController.findTypeWarehouseDetail(mockRequestParam);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void findTypeWarehouseDetailTestAuthenSuccessHasExeption() {
		WarehouseDetail mockRequestParam = new WarehouseDetail();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doThrow(new RuntimeException()).when(vt070002Service).findTypeWarehouseDetail(mockRequestParam);
		ResponseEntityBase response = mockController.findTypeWarehouseDetail(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void updateTinboxByInformationTestAuthenFail() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		ResponseEntityBase response = mockController.updateTinboxByInformation(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void updateTinboxByInformationTestAuthenSuccess() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS,null)).when(vt070002Service).updateTinboxByInformation(mockRequestParam);
		ResponseEntityBase response = mockController.updateTinboxByInformation(mockRequestParam);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void updateTinboxByInformationTestAuthenSuccessHasException() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doThrow(new RuntimeException()).when(vt070002Service).updateTinboxByInformation(mockRequestParam);
		ResponseEntityBase response = mockController.updateTinboxByInformation(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getProjectDocsByWarehouseTestAuthenFail() {
		RequestParam mockRequestParam = new RequestParam();
		ResponseEntityBase response = mockController.getProjectDocsByWarehouse(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getProjectDocsByWarehouseTestAuthenSuccess() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(0,null)).when(vt070002Service).getProjectDocOfWarehouse(mockRequestParam);
		ResponseEntityBase response = mockController.getProjectDocsByWarehouse(mockRequestParam);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void getProjectDocsByWarehouseTestAuthenSuccessHasException() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doThrow(new RuntimeException()).when(vt070002Service).getProjectDocOfWarehouse(mockRequestParam);
		ResponseEntityBase response = mockController.getProjectDocsByWarehouse(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getDocumentReportTestAuthenFail() {
		RequestParam mockRequestParam = new RequestParam();
		ResponseEntityBase response = mockController.getDocumentReport(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getDocumentReportTestAuthenSuccess() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	mockRequestParam.setWarehouseId("999");
    	mockRequestParam.setPageSize(888);
    	mockRequestParam.setPageNumber(777);
    	Mockito.doReturn(new ResponseEntityBase(0,null)).when(vt070002Service).getDocumentReport(999,888,777);
		ResponseEntityBase response = mockController.getDocumentReport(mockRequestParam);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void getDocumentReportTestAuthenSuccessHasException() {
		RequestParam mockRequestParam = new RequestParam();
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	mockRequestParam.setWarehouseId("999abcd");
    	mockRequestParam.setPageSize(888);
    	mockRequestParam.setPageNumber(777);
    	Mockito.doReturn(new ResponseEntityBase(0,null)).when(vt070002Service).getDocumentReport(999,888,777);
		ResponseEntityBase response = mockController.getDocumentReport(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getType1WarehouseTestAuthenFail() {
		WarehouseDetail mockRequest = new WarehouseDetail();
		ResponseEntityBase response = mockController.getType1Warehouses(mockRequest);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getType1WarehouseTestAuthenSuccess() {
		WarehouseDetail mockRequestParam = new WarehouseDetail();
		mockRequestParam.setSecurityUsername("username");
		mockRequestParam.setSecurityPassword("password");
		Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070002Service).getType1Warehouses(mockRequestParam);
		ResponseEntityBase response = mockController.getType1Warehouses(mockRequestParam);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void getType1WarehouseTestHasException() {
		WarehouseDetail mockRequestParam = new WarehouseDetail();
		mockRequestParam.setSecurityUsername("username");
		mockRequestParam.setSecurityPassword("password");
		Mockito.doThrow(new RuntimeException()).when(vt070002Service).getType1Warehouses(mockRequestParam);
		ResponseEntityBase response = mockController.getType1Warehouses(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getTinBoxInType1WarehouseTestAuthenFail() {
		TinBoxDetail mockRequest = new TinBoxDetail();
		ResponseEntityBase response = mockController.getTinBoxInType1Warehouse(mockRequest);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void getTinBoxInType1WarehouseTestAuthenSuccess() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		mockRequestParam.setSecurityUsername("username");
		mockRequestParam.setSecurityPassword("password");
		Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070002Service).getTinBoxInType1Warehouse(mockRequestParam);
		ResponseEntityBase response = mockController.getTinBoxInType1Warehouse(mockRequestParam);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void getTinBoxInType1WarehouseTestHasException() {
		TinBoxDetail mockRequestParam = new TinBoxDetail();
		mockRequestParam.setSecurityUsername("username");
		mockRequestParam.setSecurityPassword("password");
		Mockito.doThrow(new RuntimeException()).when(vt070002Service).getTinBoxInType1Warehouse(mockRequestParam);
		ResponseEntityBase response = mockController.getTinBoxInType1Warehouse(mockRequestParam);
		assertEquals(Constant.ERROR, response.getStatus());
	}
	
	@Test
	public void countTinBoxAndOccupiedSlotTestAuthenFail() {
		RequestParam req = new RequestParam();
		ResponseEntityBase res = mockController.countTinBoxAndOccupiedSlot(req);
		assertEquals(Constant.ERROR, res.getStatus());
	}
	
	@Test
	public void countTinBoxAndOccupiedSlotTestHasException() {
		RequestParam req = new RequestParam();
		req.setSecurityUsername("username");
		req.setSecurityPassword("password");
		ResponseEntityBase res = mockController.countTinBoxAndOccupiedSlot(req);
		assertEquals(Constant.ERROR, res.getStatus());
	}
	
	@Test
	public void countTinBoxAndOccupiedSlotTestAuthenSucc() {
		RequestParam req = new RequestParam();
		req.setSecurityUsername("username");
		req.setSecurityPassword("password");
		req.setWarehouseId("1234");
		Mockito.doReturn(new ResponseEntityBase(0,null)).when(vt070002Service).countTinBoxAndOccupiedSlot(1234);
		ResponseEntityBase res = mockController.countTinBoxAndOccupiedSlot(req);
		assertEquals(Constant.SUCCESS, res.getStatus());
	}
	
	@Test
	public void getFolderByQRCodeTestAuthenFail() {
		RequestParam req = new RequestParam();
		ResponseEntityBase res = mockController.getFolderByQRCode(req, principal);
		assertEquals(Constant.ERROR, res.getStatus());
	}
	
	@Test
	public void getFolderByQRCodeTestHasException() {
		RequestParam req = new RequestParam();
		req.setSecurityUsername("username");
		req.setSecurityPassword("password");
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		Mockito.doThrow(new RuntimeException()).when(vt070002Service).getFolderByQRCode(req, userName, roleList);
		ResponseEntityBase res = mockController.getFolderByQRCode(req, principal);
		assertEquals(Constant.ERROR, res.getStatus());
	}
	
	@Test
	public void getFolderByQRCodeTestAuthenSucc() {
		RequestParam req = new RequestParam();
		req.setSecurityUsername("username");
		req.setSecurityPassword("password");
		ResponseEntityBase res = mockController.getFolderByQRCode(req, principal);
		assertEquals(Constant.SUCCESS, res.getStatus());
	}
	
	@Test
	public void getProjectTreeInFolderTestAuthenFail() {
		RequestParam req = new RequestParam();
		ResponseEntityBase res = mockController.getProjectTreeInFolder(req);
		assertEquals(Constant.ERROR, res.getStatus());
	}
	
	@Test
	public void getProjectTreeInFolderTestHasException() {
		RequestParam req = new RequestParam();
		req.setSecurityUsername("username");
		req.setSecurityPassword("password");
		Mockito.doThrow(new RuntimeException()).when(vt070002Service).getProjectTreeInFolder(req);
		ResponseEntityBase res = mockController.getProjectTreeInFolder(req);
		assertEquals(Constant.ERROR, res.getStatus());
	}
	
	@Test
	public void getProjectTreeInFolderTestAuthenSucc() {
		RequestParam req = new RequestParam();
		req.setSecurityUsername("username");
		req.setSecurityPassword("password");
		ResponseEntityBase res = mockController.getProjectTreeInFolder(req);
		assertEquals(Constant.SUCCESS, res.getStatus());
	}
	
	@Test
	public void searchFolderByNameOrQrCodeTestAuthenFail() {
		SearchFolderRequest req = new SearchFolderRequest();
		ResponseEntityBase res = mockController.searchFolderByNameOrQrCode(req, principal);
		assertEquals(Constant.ERROR, res.getStatus());
	}
	
	@Test
	public void searchFolderByNameOrQrCodeTestHasError() {
		SearchFolderRequest req = new SearchFolderRequest();
		req.setSecurityUsername("username");
		req.setSecurityPassword("password");
		req.setQrCode("abcd1234");
		req.setPageNumber(1);
		req.setPageSize(10);
		Mockito.doThrow(new RuntimeException()).when(folderService).searchFolderByNameOrQrCode("abcd1234", 1, 10, roleList);
		ResponseEntityBase res = mockController.searchFolderByNameOrQrCode(req, principal);
		assertEquals(Constant.ERROR, res.getStatus());
	}
	
	@Test
	public void searchFolderByNameOrQrCodeTestAuthenSucc() {
		SearchFolderRequest req = new SearchFolderRequest();
		req.setSecurityUsername("username");
		req.setSecurityPassword("password");
		req.setQrCode("abcd1234");
		req.setPageNumber(1);
		req.setPageSize(10);
		Mockito.doReturn(null).when(folderService).searchFolderByNameOrQrCode("abcd1234", 1, 10, roleList);
		ResponseEntityBase res = mockController.searchFolderByNameOrQrCode(req, principal);
		assertEquals(Constant.SUCCESS, res.getStatus());
	}
	
	@Test
	public void getProjectsInFolderTestAuthenFail() {
		FolderDetail req = new FolderDetail();
		ResponseEntityBase res = mockController.getProjectsInFolder(req);
		assertEquals(Constant.ERROR, res.getStatus());
	}
	
	@Test
	public void getProjectsInFolderTestHasException() {
		FolderDetail req = new FolderDetail();
		req.setSecurityUsername("username");
		req.setSecurityPassword("password");
		req.setFolderId(1234);
		Mockito.doThrow(new RuntimeException()).when(folderService).getProjectsInFolder(1234);
		ResponseEntityBase res = mockController.getProjectsInFolder(req);
		assertEquals(Constant.ERROR, res.getStatus());
	}
	
	@Test
	public void getProjectsInFolderTestAuthenSucc() {
		FolderDetail req = new FolderDetail();
		req.setSecurityUsername("username");
		req.setSecurityPassword("password");
		ResponseEntityBase res = mockController.getProjectsInFolder(req);
		assertEquals(Constant.SUCCESS, res.getStatus());
	}
	
	@Test
	public void getProjectsNotInFolderTestAuthenFail() {
		FolderDetail req = new FolderDetail();
		ResponseEntityBase res = mockController.getProjectsNotInFolder(req, principal);
		assertEquals(Constant.ERROR, res.getStatus());
	}
	
	@Test
	public void getProjectsNotInFolderTestHasException() {
		FolderDetail req = new FolderDetail();
		req.setSecurityUsername("username");
		req.setSecurityPassword("password");
		req.setFolderId(1234);
		Mockito.doThrow(new RuntimeException()).when(folderService).getProjectsNotInFolder(1234, roleList);
		ResponseEntityBase res = mockController.getProjectsNotInFolder(req, principal);
		assertEquals(Constant.ERROR, res.getStatus());
	}
	
	@Test
	public void getProjectsNotInFolderTestAuthenSucc() {
		FolderDetail req = new FolderDetail();
		req.setSecurityUsername("username");
		req.setSecurityPassword("password");
		ResponseEntityBase res = mockController.getProjectsNotInFolder(req, principal);
		assertEquals(Constant.SUCCESS, res.getStatus());
	}
}
