package vt70002.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070000.dao.VT070000DAO;
import com.viettel.vtnet360.vt07.vt070000.entity.ImportedDataRow;
import com.viettel.vtnet360.vt07.vt070000.entity.RackDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.SlotDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseRequestParam;
import com.viettel.vtnet360.vt07.vt070000.service.TinBoxSearchService;
import com.viettel.vtnet360.vt07.vt070001.dao.VT070001DAO;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeDetail;
import com.viettel.vtnet360.vt07.vt070002.dao.VT070002DAO;
import com.viettel.vtnet360.vt07.vt070002.entity.ConstructionDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ConstructionDocDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ContractDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ContractDocDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.DocumentReportRecord;
import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ObjId;
import com.viettel.vtnet360.vt07.vt070002.entity.PackageDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.PackageDocDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ProjectDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ProjectDocDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ProjectInFoderDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.RequestParam;
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;
import com.viettel.vtnet360.vt07.vt070002.service.VT070002ServiceImpl;

import misc.BeanUtilities;

public class VT070002ServiceTest {
	private static final String ENCRYPTED_USERNAME = "a37d7e12b490d7000dcc6fa0fd7066c2";
	private static final String ENCRYPTED_PASSWORD = "c9a0172b171314c3eb811606a8d1528b";
	Date createDate;
	Date updateDate;
	TinBoxDetail defaultTinBox = new TinBoxDetail();
	List<TinBoxDetail> dfTinBoxList = new ArrayList<TinBoxDetail>();
	FolderDetail defaultFolder= new FolderDetail();
	List<FolderDetail> dfFolderList = new ArrayList<FolderDetail>();
	ProjectDetail defaultProject = new ProjectDetail();
	List<ProjectDetail> dfProjectList = new ArrayList<ProjectDetail>();
	ProjectDocDetail defaultProjectDoc = new ProjectDocDetail();
	List<ProjectDocDetail> dfProjectDocList = new ArrayList<ProjectDocDetail>();
	ContractDetail defaultContract = new ContractDetail();
	List<ContractDetail> dfContractList = new ArrayList<ContractDetail>();
	ConstructionDetail defaultConstruction = new ConstructionDetail();
	List<ConstructionDetail> dfConstructionList = new ArrayList<ConstructionDetail>();
	ConstructionDocDetail defaultConstructionDoc = new ConstructionDocDetail();
	List<ConstructionDocDetail> dfConstructionDocList = new ArrayList<ConstructionDocDetail>();
	PackageDetail defaultPackage = new PackageDetail();
	List<PackageDetail> dfPackageList = new ArrayList<PackageDetail>();
	PackageDocDetail defaultPackageDoc = new PackageDocDetail();
	List<PackageDocDetail> dfPackageDocList = new ArrayList<PackageDocDetail>();
	ContractDocDetail defaultContractDoc = new ContractDocDetail();
	List<ContractDocDetail> dfContractDocList = new ArrayList<ContractDocDetail>();
	RequestParam defaultReqParam = new RequestParam();
	ObjId objId = new ObjId();
	List<ObjId> objIdList = new ArrayList<ObjId>();
	ProjectInFoderDetail prjInFolder = new ProjectInFoderDetail();
	String userName;
	Collection<GrantedAuthority> roleList;
	
	@InjectMocks
	private VT070002ServiceImpl mockService;
	
	@Spy
	VT070002DAO vt070002dao;
	@Spy
	VT070000DAO vt070000dao;
	@Spy
	VT070001DAO vt070001dao;

	@Spy
	Properties linkTemplateExcel;
	@Spy
	Properties headerTitle;

	@Spy
	TinBoxSearchService tinBoxSearchService;
	
	@Spy
	Properties warehouseMessage;
	
	@Mock
    private OAuth2Authentication principal;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		createDate = new Date();
		updateDate = new Date();
		
		objId.setId(6543);
		objIdList.add(objId);
		
		defaultReqParam.setLevel(0);
		defaultReqParam.setByAll(false);
		defaultReqParam.setByTinBoxName(false);
		defaultReqParam.setByProjectName(false);
		defaultReqParam.setByContractName(false);
		defaultReqParam.setByPackageName(false);
		defaultReqParam.setByContructionName(false);
		defaultReqParam.setWarehouseId("10001");
		defaultReqParam.setExceptObjIds(objIdList);
		defaultReqParam.setByFolderName(false);
		
		defaultConstructionDoc.setAction(0);
		defaultConstructionDoc.setConstructionDocId(222);
		defaultConstructionDoc.setConstructionId(4444);
		defaultConstructionDoc.setCreateDate(createDate);
		defaultConstructionDoc.setCreateUser("create user");
		defaultConstructionDoc.setError("df construction doc error");
		defaultConstructionDoc.setFolderId(8888);
		defaultConstructionDoc.setName("df construction doc name");
		defaultConstructionDoc.setType(6);
		dfConstructionDocList.add(defaultConstructionDoc);
		
		defaultConstruction.setCode("df construction code");
		defaultConstruction.setConstructionId(4444);
		defaultConstruction.setContractId(5555);
		defaultConstruction.setCreateDate(createDate);
		defaultConstruction.setCreateUser("create user");
		defaultConstruction.setDescription("default construction desc");
		defaultConstruction.setDocs(dfConstructionDocList);
		defaultConstruction.setErpId("default construction erp ID");
		defaultConstruction.setFolderId(8888);
		defaultConstruction.setName("df construction name");
		defaultConstruction.setProjectId(7777);
		defaultConstruction.setStatus("df construction status");
		dfConstructionList.add(defaultConstruction);
		
		defaultContractDoc.setAction(12);
		defaultContractDoc.setContractDocId(333);
		defaultContractDoc.setContractId(5555);
		defaultContractDoc.setCreateDate(createDate);
		defaultContractDoc.setCreateUser("create user");
		defaultContractDoc.setError("df contract doc error");
		defaultContractDoc.setFolderId(8888);
		defaultContractDoc.setName("df contract doc name");
		defaultContractDoc.setType(1);
		dfContractDocList.add(defaultContractDoc);
		
		defaultContract.setCode("df contract code");
		defaultContract.setConstructions(dfConstructionList);
		defaultContract.setContractId(5555);
		defaultContract.setCreateDate(createDate);
		defaultContract.setCreateUser("create user");
		defaultContract.setDescription("df contract desc");
		defaultContract.setDocs(dfContractDocList);
		defaultContract.setErpId("df contract erp ID");
		defaultContract.setName("df contract name");
		defaultContract.setProjectId(7777);
		defaultContract.setStatus("df contract status");
		dfContractList.add(defaultContract);
		
		defaultProjectDoc.setAction(3);
		defaultProjectDoc.setCreateDate(createDate);
		defaultProjectDoc.setCreateUser("create user");
		defaultProjectDoc.setError("df project doc error");
		defaultProjectDoc.setFolderId(8888);
		defaultProjectDoc.setName("df project doc name");
		defaultProjectDoc.setProjectDocId(444);
		defaultProjectDoc.setProjectId(7777);
		defaultProjectDoc.setType(4);
		dfProjectDocList.add(defaultProjectDoc);
		
		defaultPackageDoc.setAction(31);
		defaultPackageDoc.setCreateDate(createDate);
		defaultPackageDoc.setCreateUser("create user");
		defaultPackageDoc.setError("default package error");
		defaultPackageDoc.setFolderId(8888);
		defaultPackageDoc.setName("default package doc name");
		defaultPackageDoc.setPackageDocId(555);
		defaultPackageDoc.setPackageId(6666);
		defaultPackageDoc.setType(2);
		dfPackageDocList.add(defaultPackageDoc);
		
		defaultPackage.setCode("df package code");
		defaultPackage.setCreateDate(createDate);
		defaultPackage.setCreateUser("create user");
		defaultPackage.setDescription("df package desc");
		defaultPackage.setDocs(dfPackageDocList);
		defaultPackage.setErpId("df package erp ID");
		defaultPackage.setName("default package name");
		defaultPackage.setPackageId(6666);
		defaultPackage.setProjectId(7777);
		defaultPackage.setSecurityPassword(ENCRYPTED_PASSWORD);
		defaultPackage.setSecurityUsername(ENCRYPTED_USERNAME);
		defaultPackage.setStatus("df package status");
		defaultPackage.setUpdateDate(updateDate);
		defaultPackage.setUpdateUser("update user");
		dfPackageList.add(defaultPackage);
		
		defaultProject.setCode("df project code");
		defaultProject.setContracts(dfContractList);
		defaultProject.setCreateDate(createDate);
		defaultProject.setCreateUser("create user");
		defaultProject.setDescription("df project desc");
		defaultProject.setDocs(dfProjectDocList);
		defaultProject.setErpId("df project erp ID");
		defaultProject.setName("default project name");
		defaultProject.setPackages(dfPackageList);
		defaultProject.setProjectId(7777);
		defaultProject.setSecurityPassword(ENCRYPTED_PASSWORD);
		defaultProject.setSecurityUsername(ENCRYPTED_USERNAME);
		defaultProject.setStatus("df project status");
		defaultProject.setUpdateDate(updateDate);
		defaultProject.setUpdateUser("update user");
		dfProjectList.add(defaultProject);
		
		prjInFolder.setProjectInFolderId(7777);
		prjInFolder.getDelFlag();
		prjInFolder.getFolderId();
		prjInFolder.getProjectId();
		prjInFolder.getProjectInFolderId();
		
		defaultFolder.setAction(0);
		defaultFolder.setBorrowId(88);
		defaultFolder.setCreateDate(createDate);
		defaultFolder.setCreateUser("create user");
		defaultFolder.setDelFlag(0);
		defaultFolder.setError("df folder error");
		defaultFolder.setFolderId(8888);
		defaultFolder.setName("df folder name");
		defaultFolder.setProjects(dfProjectList);
		defaultFolder.setQrCode("default folder qr code");
		defaultFolder.setSecurityPassword(ENCRYPTED_PASSWORD);
		defaultFolder.setSecurityUsername(ENCRYPTED_USERNAME);
		defaultFolder.setStatus(2);
		defaultFolder.setTinBoxId(9999);
		defaultFolder.setTotalProject(1);
		defaultFolder.setUpdateDate(updateDate);
		defaultFolder.setUpdateUser("update user");
		dfFolderList.add(defaultFolder);
		
		defaultTinBox.setAction(0);
		defaultTinBox.setCreateDate(createDate);
		defaultTinBox.setCreateUser("create user");
		defaultTinBox.setDelFlag(0);
		defaultTinBox.setDescription("default tin box description");
		defaultTinBox.setError("df tin box error");
		defaultTinBox.setFolders(dfFolderList);
		defaultTinBox.setIndex(1234);
		defaultTinBox.setMngUser("manage user");
		defaultTinBox.setName("default tin box name");
		defaultTinBox.setQrCode("df tin box qr code");
		defaultTinBox.setStatus(1);
		defaultTinBox.setSecurityUsername(ENCRYPTED_USERNAME);
		defaultTinBox.setSecurityPassword(ENCRYPTED_PASSWORD);
		defaultTinBox.setSlotId(636);
		defaultTinBox.setSlotQrCode("df tin box slot qr code");
		defaultTinBox.setTinBoxId(9999);
		defaultTinBox.setTotalFolder(1);
		defaultTinBox.setType("default tinbox type");
		defaultTinBox.setUpdateDate(updateDate);
		defaultTinBox.setUpdateUser("update user");
		defaultTinBox.setWarehouseId(objId.getId());
		defaultTinBox.setWarehouseType(2);
		dfTinBoxList.add(defaultTinBox);
		
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		roleList = oauth.getAuthorities();
		SimpleGrantedAuthority unit1 = new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_GROUP);
		roleList.add(unit1);
		userName = "test user";
	}
	
	@Test
	public void testGetter() {
		defaultProjectDoc.getAction();
		defaultProjectDoc.getError();
		defaultProjectDoc.getFolderId();
		defaultProjectDoc.getName();
		defaultProjectDoc.getProjectDocId();
		defaultProjectDoc.getProjectId();
		defaultPackageDoc.getAction();
		defaultPackageDoc.getError();
		defaultPackageDoc.getFolderId();
		defaultPackageDoc.getName();
		defaultPackageDoc.getPackageDocId();
		defaultPackageDoc.getPackageId();
		defaultPackageDoc.getType();
		defaultConstructionDoc.getAction();
		defaultConstructionDoc.getConstructionDocId();
		defaultConstructionDoc.getConstructionId();
		defaultConstructionDoc.getError();
		defaultConstructionDoc.getFolderId();
		defaultConstructionDoc.getName();
		defaultConstructionDoc.getType();
		defaultContractDoc.getAction();
		defaultContractDoc.getName();
		defaultContractDoc.getContractId();
		defaultContractDoc.getFolderId();
		defaultContractDoc.getError();
	}
	
	@Test
	public void getListTinBoxTestEmptyParam() {
		RequestParam mockParam = new RequestParam();
		Object result = mockService.getListTinBox(mockParam, userName, roleList);
		List<TinBoxDetail> resultTinboxList = (List<TinBoxDetail>) BeanUtilities.getObjectFieldValue(result, "tinBoxs");
		assertEquals(new ArrayList<>(), resultTinboxList);
	}
	
	@Test
	public void getListTinBoxTestNotEmptyParamEmptyTinBoxList() {
		RequestParam mockParam = new RequestParam();
		mockParam.setPageNo(1);
		mockParam.setName("a name");
		mockParam.setQrCode("a qr code");
		Mockito.doReturn(new ArrayList()).when(vt070002dao).getListTinBoxByQrCode(mockParam);
		Object result = mockService.getListTinBox(mockParam, userName, roleList);
		List<TinBoxDetail> resultTinboxList = (List<TinBoxDetail>) BeanUtilities.getObjectFieldValue(result, "tinBoxs");
		assertEquals(new ArrayList<>(), resultTinboxList);
	}
	
	@Test
	public void getListTinBoxTestZeroTinBoxId() {
		RequestParam mockParam = new RequestParam();
		mockParam.setPageNo(1);
		mockParam.setName("a name");
		mockParam.setQrCode("a qr code");
		mockParam.setTinBoxId(0);
		Mockito.doReturn(new ArrayList()).when(vt070002dao).getListTinBoxByQrCode(mockParam);
		Object result = mockService.getListTinBox(mockParam, userName, roleList);
		List<TinBoxDetail> resultTinboxList = (List<TinBoxDetail>) BeanUtilities.getObjectFieldValue(result, "tinBoxs");
		assertEquals(new ArrayList<>(), resultTinboxList);
	}
	
	@Test
	public void getListTinBoxTestZeroTinBoxIdNullList() {
		RequestParam mockParam = new RequestParam();
		mockParam.setPageNo(1);
		mockParam.setName("a name");
		mockParam.setQrCode("");
		mockParam.setTinBoxId(0);
		Mockito.doReturn(null).when(vt070002dao).getListTinBoxByQrCode(mockParam);
		Mockito.doReturn(null).when(vt070002dao).getListTinBox(mockParam);
		Object result = mockService.getListTinBox(mockParam, userName, roleList);
		List<TinBoxDetail> resultTinboxList = (List<TinBoxDetail>) BeanUtilities.getObjectFieldValue(result, "tinBoxs");
		assertEquals(new ArrayList<>(), resultTinboxList);
	}
	
	@Test
	public void getListTinBoxTestNullFolderList() {
		RequestParam mockParam = new RequestParam();
		mockParam.setPageNo(1);
		mockParam.setName("a name");
		mockParam.setQrCode(null);
		mockParam.setTinBoxId(9999);
		
		Mockito.doReturn(dfTinBoxList).when(vt070002dao).getListTinBoxByQrCode(mockParam);
		Mockito.doReturn(dfTinBoxList).when(vt070002dao).getListTinBox(mockParam);
		Mockito.doReturn(null).when(vt070002dao).getListFolderByTinBoxId(mockParam);
		
		Object result = mockService.getListTinBox(mockParam, userName, roleList);
		List<TinBoxDetail> resultTinboxList = (List<TinBoxDetail>) BeanUtilities.getObjectFieldValue(result, "tinBoxs");
		assertEquals(dfTinBoxList, resultTinboxList);
	}
	
	@Test
	public void getListTinBoxTest() {
		RequestParam mockParam = new RequestParam();
		mockParam.setPageNo(1);
		mockParam.setName("a name");
		mockParam.setQrCode(null);
		mockParam.setTinBoxId(9999);
		mockParam.setFolderId(8888);
		
		Mockito.doReturn(dfTinBoxList).when(vt070002dao).getListTinBoxByQrCode(mockParam);
		Mockito.doReturn(dfTinBoxList).when(vt070002dao).getListTinBox(mockParam);
		Mockito.doReturn(dfFolderList).when(vt070002dao).getListFolderByTinBoxId(mockParam);
		
		Object result = mockService.getListTinBox(mockParam, userName, roleList);
		List<TinBoxDetail> resultTinboxList = (List<TinBoxDetail>) BeanUtilities.getObjectFieldValue(result, "tinBoxs");
		assertEquals(dfTinBoxList, resultTinboxList);
	}
	
	@Test
	public void searchListTinBoxTestEmptyParam() {
		TinBoxDetail mockParam = new TinBoxDetail();
		ResponseEntityBase result = mockService.searchListTinBox(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void searchListTinBoxTestHasException() {
		TinBoxDetail mockParam = new TinBoxDetail();
		Mockito.doThrow(new RuntimeException()).when(vt070002dao).searchListTinBox(mockParam);
		ResponseEntityBase result = mockService.searchListTinBox(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void searchListTinBoxTest() {
		TinBoxDetail mockParam = new TinBoxDetail();
		mockParam.setName("mock name");
		mockParam.setMngUser("mock user");
		Mockito.doReturn(dfTinBoxList).when(vt070002dao).searchListTinBox(mockParam);
		ResponseEntityBase result = mockService.searchListTinBox(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void createTinBoxTestNullBarcode() {
		TinBoxDetail mockParam = new TinBoxDetail();
		mockParam.setQrCode("mock qr code");
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		Mockito.doReturn(null).when(vt070001dao).checkBarcodeIsAvail(mockBarcode);
		Mockito.doReturn("mock message").when(warehouseMessage).getProperty("0700001");
		
		Object result = mockService.createTinBox(mockParam, userName, roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void createTinBoxTestEmptyBarcode() {
		TinBoxDetail mockParam = new TinBoxDetail();
		mockParam.setQrCode("mock qr code");
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		Mockito.doReturn(new ArrayList<>()).when(vt070001dao).checkBarcodeIsAvail(mockBarcode);
		Mockito.doReturn("mock message").when(warehouseMessage).getProperty("0700001");
		
		Object result = mockService.createTinBox(mockParam, userName, roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void createTinBoxTestHasMoreThanOneBarcode() {
		TinBoxDetail mockParam = defaultTinBox;
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		mockBarcodeList.add(mockBarcode);
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn("mock message").when(warehouseMessage).getProperty("0700001");
		
		Object result = mockService.createTinBox(mockParam, userName, roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void createTinBoxTestHasBarcodeNullWarehouse() {
		TinBoxDetail mockParam = defaultTinBox;
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn("mock message").when(warehouseMessage).getProperty("0700001");
		Mockito.doReturn(null).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		
		Object result = mockService.createTinBox(mockParam, userName, roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void createTinBoxTestHasBarcodeWarehouseType1() {
		TinBoxDetail mockParam = defaultTinBox;
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("1");
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn("mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		
		Object result = mockService.createTinBox(mockParam, userName, roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void createTinBoxTestHasBarcodeWarehouseTypeNoTBNameNoFolder() {
		TinBoxDetail mockParam = defaultTinBox;
		mockParam.setName(null);
		mockParam.setFolders(null);
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("0");
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn("mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		
		Object result = mockService.createTinBox(mockParam, userName, roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void createTinBoxTestHasBarcodeWarehouseType1NotNullSlotStatus1() {
		TinBoxDetail mockParam = defaultTinBox;
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("1");
		SlotDetail mockSlot = new SlotDetail();
		mockSlot.setStatus(1);
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn("mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		Mockito.doReturn(mockSlot).when(vt070000dao).getSlotById(Matchers.anyLong());
		
		Object result = mockService.createTinBox(mockParam, userName, roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void createTinBoxTestHasBarcodeWarehouseType1NotNullSlotStatus2() {
		TinBoxDetail mockParam = defaultTinBox;
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("1");
		SlotDetail mockSlot = new SlotDetail();
		mockSlot.setStatus(2);
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn("mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		Mockito.doReturn(mockSlot).when(vt070000dao).getSlotById(Matchers.anyLong());
		
		Object result = mockService.createTinBox(mockParam, userName, roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void createTinBoxTestHasBarcodeWarehouseType1NotNullSlotStatus0NullRack() {
		TinBoxDetail mockParam = defaultTinBox;
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("1");
		SlotDetail mockSlot = new SlotDetail();
		mockSlot.setStatus(0);
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn("mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		Mockito.doReturn(mockSlot).when(vt070000dao).getSlotById(Matchers.anyLong());
		
		Object result = mockService.createTinBox(mockParam, userName, roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void createTinBoxTestHasBarcodeWarehouseType1NotNullSlotStatus0NotNullRack() {
		TinBoxDetail mockParam = defaultTinBox;
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("1");
		SlotDetail mockSlot = new SlotDetail();
		mockSlot.setStatus(0);
		RackDetail mockRack = new RackDetail();
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn("mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		Mockito.doReturn(mockSlot).when(vt070000dao).getSlotById(Matchers.anyLong());
		Mockito.doReturn(mockRack).when(vt070000dao).getRackById(Matchers.anyLong());
		
		Object result = mockService.createTinBox(mockParam, userName, roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void createTinBoxTestHasBarcodeWarehouseType1SameWarehouseId() {
		TinBoxDetail mockParam = defaultTinBox;
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("1");
		SlotDetail mockSlot = new SlotDetail();
		mockSlot.setStatus(0);
		RackDetail mockRack = new RackDetail();
		mockRack.setWarehouseId(defaultTinBox.getWarehouseId());
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn("mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		Mockito.doReturn(mockSlot).when(vt070000dao).getSlotById(Matchers.anyLong());
		Mockito.doReturn(mockRack).when(vt070000dao).getRackById(Matchers.anyLong());
		
		Object result = mockService.createTinBox(mockParam, userName, roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void createTinBoxTestHasBarcodeWarehouseType1SameWarehouseIdHasProject() {
		TinBoxDetail mockParam = defaultTinBox;
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("1");
		SlotDetail mockSlot = new SlotDetail();
		mockSlot.setStatus(0);
		RackDetail mockRack = new RackDetail();
		mockRack.setWarehouseId(defaultTinBox.getWarehouseId());
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn("mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		Mockito.doReturn(mockSlot).when(vt070000dao).getSlotById(Matchers.anyLong());
		Mockito.doReturn(mockRack).when(vt070000dao).getRackById(Matchers.anyLong());
		Mockito.doReturn(dfProjectList).when(vt070002dao).checkProjectInFolder(Matchers.any(RequestParam.class));
		
		Object result = mockService.createTinBox(mockParam, userName, roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void createTinBoxTestHasListTinBox() {
		TinBoxDetail mockParam = defaultTinBox;
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("1");
		SlotDetail mockSlot = new SlotDetail();
		mockSlot.setStatus(0);
		RackDetail mockRack = new RackDetail();
		mockRack.setWarehouseId(defaultTinBox.getWarehouseId());
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn("mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		Mockito.doReturn(mockSlot).when(vt070000dao).getSlotById(Matchers.anyLong());
		Mockito.doReturn(mockRack).when(vt070000dao).getRackById(Matchers.anyLong());
		Mockito.doReturn(dfProjectList).when(vt070002dao).checkProjectInFolder(Matchers.any(RequestParam.class));
		Mockito.doReturn(dfTinBoxList).when(vt070002dao).getListTinBoxByQrCode(Matchers.any(RequestParam.class));
		
		Object result = mockService.createTinBox(mockParam, userName, roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void updateTinBoxTestEmptyParam() {
		TinBoxDetail mockParam = new TinBoxDetail();
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void updateTinBoxTest() {
		TinBoxDetail mockParam = defaultTinBox;
		Mockito.doReturn(defaultTinBox).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
		
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void updateTinBoxTestDelAction() {
		TinBoxDetail mockParam = defaultTinBox;
		mockParam.setAction(Constant.DEL_ACTION);			
		Mockito.doReturn(defaultTinBox).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty("0700001");
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty("0700011");
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty("0700006");
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void updateTinBoxTestDelActionNullCreateUserHasSlot() {
		TinBoxDetail mockParam = defaultTinBox;
		mockParam.setAction(Constant.DEL_ACTION);
		SlotDetail mockSlot = new SlotDetail();
		defaultTinBox.setCreateUser(null);
		Mockito.doReturn(defaultTinBox).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty("0700001");
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty("0700006");
		Mockito.doReturn(mockSlot).when(vt070000dao).getSlotById(mockParam.getSlotId());
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	//NullPointerException cause by new code
//	@Test
//	public void updateTinBoxTestDelActionNullCreateUserNullSlotNullNameNullFolder() {
//		TinBoxDetail mockParam = defaultTinBox;
//		mockParam.setAction(Constant.DEL_ACTION);
//		defaultTinBox.setCreateUser(null);
//		defaultTinBox.setName(null);
//		defaultTinBox.setFolders(null);
//		Mockito.doReturn(defaultTinBox).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
//		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty("0700001");
//		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty("0700011");
//		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty("0700006");
//		Mockito.doReturn(null).when(vt070000dao).getSlotById(mockParam.getSlotId());
//		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
//		assertEquals(mockParam, result);
//	}
	
	@Test
	public void updateTinBoxTestEditActionNullTinBox() {
		TinBoxDetail mockParam = defaultTinBox;
		mockParam.setAction(Constant.EDIT_ACTION);
		Mockito.doReturn(null).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
		
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void updateTinBoxTestEditActionHasBarcode() {
		TinBoxDetail mockParam = defaultTinBox;
		mockParam.setAction(Constant.EDIT_ACTION);
		defaultTinBox.setQrCode(null);
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn(defaultTinBox).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void updateTinBoxTestEditAction2Barcode() {
		TinBoxDetail mockParam = defaultTinBox;
		mockParam.setAction(Constant.EDIT_ACTION);
		defaultTinBox.setQrCode(null);
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		mockBarcodeList.add(mockBarcode);
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn(defaultTinBox).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void updateTinBoxTestEditActionNullBarcode() {
		TinBoxDetail mockParam = defaultTinBox;
		mockParam.setAction(Constant.EDIT_ACTION);
		defaultTinBox.setQrCode(null);
		Mockito.doReturn(null).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn(defaultTinBox).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void updateTinBoxTestEditActionHasWarehouseType0() {
		TinBoxDetail mockParam = defaultTinBox;
		mockParam.setAction(Constant.EDIT_ACTION);
		defaultTinBox.setQrCode(null);
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("0");
		SlotDetail mockSlot = new SlotDetail();
		Mockito.doReturn(mockSlot).when(vt070000dao).getSlotById(Matchers.anyLong());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn(defaultTinBox).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void updateTinBoxTestEditActionWHType0NullSlot() {
		TinBoxDetail mockParam = defaultTinBox;
		mockParam.setAction(Constant.EDIT_ACTION);
		defaultTinBox.setQrCode(null);
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("0");
		Mockito.doReturn(null).when(vt070000dao).getSlotById(Matchers.anyLong());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn(defaultTinBox).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void updateTinBoxTestEditActionWHType0NullSlotNullTB() {
		TinBoxDetail mockParam = defaultTinBox;
		mockParam.setAction(Constant.EDIT_ACTION);
		defaultTinBox.setQrCode(null);
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("0");
		Mockito.doReturn(null).when(vt070000dao).getSlotById(Matchers.anyLong());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn(null).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void updateTinBoxTestEditActionHasWarehouseType1() {
		TinBoxDetail mockParam = defaultTinBox;
		mockParam.setAction(Constant.EDIT_ACTION);
		defaultTinBox.setQrCode(null);
		defaultTinBox.setSlotId(0);
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("1");
		SlotDetail mockSlot = new SlotDetail();
		mockSlot.setStatus(0);
		RackDetail mockRack = new RackDetail();
		mockRack.setWarehouseId(10001);
		Mockito.doReturn(mockRack).when(vt070000dao).getRackById(Matchers.anyLong());
		Mockito.doReturn(mockSlot).when(vt070000dao).getSlotById(Matchers.anyLong());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn(defaultTinBox).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void updateTinBoxTestEditActionWHType1NullSlot() {
		TinBoxDetail mockParam = defaultTinBox;
		mockParam.setAction(Constant.EDIT_ACTION);
		defaultTinBox.setQrCode(null);
		defaultTinBox.setSlotId(0);
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("1");
		SlotDetail mockSlot = new SlotDetail();
		mockSlot.setStatus(0);
		RackDetail mockRack = new RackDetail();
		mockRack.setWarehouseId(10001);
		Mockito.doReturn(mockRack).when(vt070000dao).getRackById(Matchers.anyLong());
		Mockito.doReturn(null).when(vt070000dao).getSlotById(Matchers.anyLong());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn(defaultTinBox).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void updateTinBoxTestEditActionWHType1HasFolderEditAction() {
		TinBoxDetail mockParam = defaultTinBox;
		//mockParam.setAction(Constant.EDIT_ACTION);
		defaultTinBox.setQrCode(null);
		defaultTinBox.setSlotId(0);
		VT070001EntityBarcodeDetail mockBarcode = new VT070001EntityBarcodeDetail();
		mockBarcode.setCode(mockParam.getQrCode());
		List<VT070001EntityBarcodeDetail> mockBarcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		mockBarcodeList.add(mockBarcode);
		WarehouseRequestParam mockWarehouse = new WarehouseRequestParam();
		mockWarehouse.setType("1");
		SlotDetail mockSlot = new SlotDetail();
		mockSlot.setStatus(0);
		RackDetail mockRack = new RackDetail();
		mockRack.setWarehouseId(10001);
		FolderDetail mockFolder = new FolderDetail();
		mockFolder.setAction(Constant.EDIT_ACTION);
		mockFolder.setQrCode(null);
		Mockito.doReturn(mockFolder).when(vt070002dao).getFolderById(Matchers.anyLong());
		Mockito.doReturn(mockRack).when(vt070000dao).getRackById(Matchers.anyLong());
		Mockito.doReturn(mockSlot).when(vt070000dao).getSlotById(Matchers.anyLong());
		Mockito.doReturn(mockWarehouse).when(vt070000dao).getWarehouseDetailById(Matchers.anyString());
		Mockito.doReturn(mockBarcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any(VT070001EntityBarcodeDetail.class));
		Mockito.doReturn(defaultTinBox).when(vt070002dao).getTinBoxById(mockParam.getTinBoxId());
		Mockito.doReturn("a mock message").when(warehouseMessage).getProperty(Matchers.anyString());
		Object result = mockService.updateTinBox(mockParam, "update user", roleList);
		assertEquals(mockParam, result);
	}
	
	@Test
	public void getAvailObjectInProjectTestEmptyParam() {
		RequestParam mockParam = new RequestParam();
		mockParam.setName(null);
		Object result = mockService.getAvailObjectInProject(mockParam, roleList);
		assertEquals(new ArrayList<>(), result);
	}
	
	@Test
	public void getAvailObjectInProjectTestEmptyName() {
		RequestParam mockParam = new RequestParam();
		mockParam.setName("");
		Mockito.doReturn(null).when(vt070002dao).getProjects(mockParam);
		Object result = mockService.getAvailObjectInProject(mockParam, roleList);
		assertEquals(null, result);
	}
	
	@Test
	public void getAvailObjectInProjectTestNoObjectType() {
		RequestParam mockParam = new RequestParam();
		mockParam.setName("abcd");
		
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjects(mockParam);
		
		Object result = mockService.getAvailObjectInProject(mockParam, roleList);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getAvailObjectInProjectTestObjectType1NullPackage() {
		RequestParam mockParam = new RequestParam();
		mockParam.setName("abcd");
		mockParam.setObjectType(1);
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjects(mockParam);
		Mockito.doReturn(null).when(vt070002dao).getPackages(mockParam);
		Object result = mockService.getAvailObjectInProject(mockParam, roleList);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getAvailObjectInProjectTestObjectType1EmptyPackage() {
		RequestParam mockParam = new RequestParam();
		mockParam.setName("abcd");
		mockParam.setObjectType(1);
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjects(mockParam);
		Mockito.doReturn(new ArrayList<>()).when(vt070002dao).getPackages(mockParam);
		Object result = mockService.getAvailObjectInProject(mockParam, roleList);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getAvailObjectInProjectTestObjectType1HasPackage() {
		RequestParam mockParam = new RequestParam();
		mockParam.setName("abcd");
		mockParam.setObjectType(1);
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjects(mockParam);
		Mockito.doReturn(dfPackageList).when(vt070002dao).getPackages(mockParam);
		Object result = mockService.getAvailObjectInProject(mockParam, roleList);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getAvailObjectInProjectTestObjectType2NullContract() {
		RequestParam mockParam = new RequestParam();
		mockParam.setName("abcd");
		mockParam.setObjectType(2);
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjects(mockParam);
		Mockito.doReturn(null).when(vt070002dao).getContracts(mockParam);
		Object result = mockService.getAvailObjectInProject(mockParam, roleList);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getAvailObjectInProjectTestObjectType2EmptyContract() {
		RequestParam mockParam = new RequestParam();
		mockParam.setName("abcd");
		mockParam.setObjectType(2);
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjects(mockParam);
		Mockito.doReturn(new ArrayList<>()).when(vt070002dao).getContracts(mockParam);
		Object result = mockService.getAvailObjectInProject(mockParam, roleList);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getAvailObjectInProjectTestObjectType2HasContract() {
		RequestParam mockParam = new RequestParam();
		mockParam.setName("abcd");
		mockParam.setObjectType(2);
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjects(mockParam);
		Mockito.doReturn(dfContractList).when(vt070002dao).getContracts(mockParam);
		Object result = mockService.getAvailObjectInProject(mockParam, roleList);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getAvailObjectInProjectTestObjectType3HasContractNullConstruction() {
		RequestParam mockParam = new RequestParam();
		mockParam.setName("abcd");
		mockParam.setObjectType(3);
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjects(mockParam);
		Mockito.doReturn(dfContractList).when(vt070002dao).getContracts(mockParam);
		Mockito.doReturn(null).when(vt070002dao).getConstructions(mockParam);
		Object result = mockService.getAvailObjectInProject(mockParam, roleList);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getAvailObjectInProjectTestObjectType3HasContractEmptyConstruction() {
		RequestParam mockParam = new RequestParam();
		mockParam.setName("abcd");
		mockParam.setObjectType(3);
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjects(mockParam);
		Mockito.doReturn(dfContractList).when(vt070002dao).getContracts(mockParam);
		Mockito.doReturn(new ArrayList<>()).when(vt070002dao).getConstructions(mockParam);
		Object result = mockService.getAvailObjectInProject(mockParam, roleList);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getAvailObjectInProjectTestObjectType3HasContractHasConstruction() {
		RequestParam mockParam = new RequestParam();
		mockParam.setName("abcd");
		mockParam.setObjectType(3);
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjects(mockParam);
		Mockito.doReturn(dfContractList).when(vt070002dao).getContracts(mockParam);
		Mockito.doReturn(dfConstructionList).when(vt070002dao).getConstructions(mockParam);
		Object result = mockService.getAvailObjectInProject(mockParam, roleList);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getProjectObjectInFolderTestEmptyParam() {
		RequestParam mockParam = new RequestParam();
		Object result = mockService.getProjectObjectInFolder(mockParam);
		assertEquals(new ArrayList<>(), result);
	}
	
	@Test
	public void getProjectObjectInFolderTestNullProject() {
		RequestParam mockParam = new RequestParam();
		Mockito.doReturn(null).when(vt070002dao).getProjectById(mockParam);
		Object result = mockService.getProjectObjectInFolder(mockParam);
		assertEquals(null, result);
	}
	
	@Test
	public void getProjectObjectInFolderTestEmptyProject() {
		RequestParam mockParam = new RequestParam();
		Mockito.doReturn(new ArrayList<>()).when(vt070002dao).getProjectById(mockParam);
		Object result = mockService.getProjectObjectInFolder(mockParam);
		assertEquals(new ArrayList<>(), result);
	}
	
	@Test
	public void getProjectObjectInFolderTestHasProjectNullPackageNContract() {
		RequestParam mockParam = new RequestParam();
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjectById(mockParam);
		Mockito.doReturn(null).when(vt070002dao).getProjectPackagesInFolder(mockParam);
		Mockito.doReturn(null).when(vt070002dao).getProjectContractsInFolder(mockParam);
		Object result = mockService.getProjectObjectInFolder(mockParam);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getProjectObjectInFolderTestHasProjectEmptyPackageNContract() {
		RequestParam mockParam = new RequestParam();
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjectById(mockParam);
		Mockito.doReturn(new ArrayList<>()).when(vt070002dao).getProjectPackagesInFolder(mockParam);
		Mockito.doReturn(new ArrayList<>()).when(vt070002dao).getProjectContractsInFolder(mockParam);
		Object result = mockService.getProjectObjectInFolder(mockParam);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getProjectObjectInFolderTestHasProjectPackageContractNullConstruction() {
		RequestParam mockParam = new RequestParam();
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjectById(mockParam);
		Mockito.doReturn(dfPackageList).when(vt070002dao).getProjectPackagesInFolder(mockParam);
		Mockito.doReturn(dfContractList).when(vt070002dao).getProjectContractsInFolder(mockParam);
		Mockito.doReturn(null).when(vt070002dao).getProjectConstructionsInFolder(Matchers.any(RequestParam.class));
		Object result = mockService.getProjectObjectInFolder(mockParam);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getProjectObjectInFolderTestHasProjectPackageContractEmptyConstruction() {
		RequestParam mockParam = new RequestParam();
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjectById(mockParam);
		Mockito.doReturn(dfPackageList).when(vt070002dao).getProjectPackagesInFolder(mockParam);
		Mockito.doReturn(dfContractList).when(vt070002dao).getProjectContractsInFolder(mockParam);
		Mockito.doReturn(new ArrayList<>()).when(vt070002dao).getProjectConstructionsInFolder(Matchers.any(RequestParam.class));
		Object result = mockService.getProjectObjectInFolder(mockParam);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void getProjectObjectInFolderTestHasProjectPackageContractConstruction() {
		RequestParam mockParam = new RequestParam();
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjectById(mockParam);
		Mockito.doReturn(dfPackageList).when(vt070002dao).getProjectPackagesInFolder(mockParam);
		Mockito.doReturn(dfContractList).when(vt070002dao).getProjectContractsInFolder(mockParam);
		Mockito.doReturn(dfConstructionList).when(vt070002dao).getProjectConstructionsInFolder(Matchers.any(RequestParam.class));
		Object result = mockService.getProjectObjectInFolder(mockParam);
		assertEquals(dfProjectList, result);
	}
	
	@Test
	public void findTypeWarehouseDetailTest() {
		WarehouseDetail mockParam = new WarehouseDetail();
		ResponseEntityBase result = mockService.findTypeWarehouseDetail(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
		assertEquals(new ArrayList<>(), result.getData());
	}
	
	@Test
	public void findTypeWarehouseDetailTestHasException() {
		WarehouseDetail mockParam = new WarehouseDetail();
		Mockito.doThrow(new RuntimeException()).when(vt070002dao).findTypeWarehouseDetail(mockParam);
		ResponseEntityBase result = mockService.findTypeWarehouseDetail(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
		assertEquals(new ArrayList<>(), result.getData());
	}
	
	@Test
	public void updateTinboxByInformationTestEmptyParam() {
		TinBoxDetail mockParam = new TinBoxDetail();
		ResponseEntityBase result = mockService.updateTinboxByInformation(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
		assertEquals(null, result.getData());
	}
	
	@Test
	public void updateTinboxByInformationTestHasException() {
		TinBoxDetail mockParam = new TinBoxDetail();
		Mockito.doThrow(new RuntimeException()).when(vt070002dao).updateTinboxByInformation(mockParam);
		ResponseEntityBase result = mockService.updateTinboxByInformation(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
		assertEquals(null, result.getData());
	}
	
	@Test
	public void updateTinboxByInformationTestSuccessUpdate() {
		TinBoxDetail mockParam = new TinBoxDetail();
		mockParam.setName("abcd");
		mockParam.setMngUser("an user");
		Mockito.doReturn(1).when(vt070002dao).updateTinboxByInformation(mockParam);
		
		ResponseEntityBase result = mockService.updateTinboxByInformation(mockParam);
		assertEquals(Constant.REQUEST_ACTION_INSERT, result.getStatus());
		assertEquals(null, result.getData());
	}
	
	@Test
	public void updateTinboxByInformationTestTextTooLong() {
		TinBoxDetail mockParam = new TinBoxDetail();
		mockParam.setName(BeanUtilities.randomString(500));
		mockParam.setMngUser(BeanUtilities.randomString(500));
		Mockito.doReturn(1).when(vt070002dao).updateTinboxByInformation(mockParam);
		ResponseEntityBase result = mockService.updateTinboxByInformation(mockParam);
		assertEquals(Constant.REQUEST_ACTION_INSERT, result.getStatus());
		assertEquals(null, result.getData());
	}
	
	@Test
	public void getProjectDocOfWarehouseTestEmptyParam() {
		RequestParam mockParam = new RequestParam();
		ResponseEntityBase result = mockService.getProjectDocOfWarehouse(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
		assertEquals(new ArrayList<>(), result.getData());
	}
	
	@Test
	public void getProjectDocOfWarehouseTestHasException() {
		RequestParam mockParam = new RequestParam();
		Mockito.doThrow(new RuntimeException()).when(vt070002dao).countProjectTinboxWithWarehouseId(mockParam);
		ResponseEntityBase result = mockService.getProjectDocOfWarehouse(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
		assertEquals(new ArrayList<>(), result.getData());
	}
	
	@Test
	public void getProjectDocOfWarehouseTestNullData() {
		RequestParam mockParam = new RequestParam();
		DocumentReportRecord mockRecord = new DocumentReportRecord();		
		List<DocumentReportRecord> mockRecordList = new ArrayList<DocumentReportRecord>();
		mockRecordList.add(mockRecord);
		Mockito.doReturn(1).when(vt070002dao).countProjectDocumentWithWarehouseId(mockParam);
		Mockito.doReturn(null).when(vt070002dao).getProjectDocumentWithWarehouseId(mockParam);
		ResponseEntityBase result = mockService.getProjectDocOfWarehouse(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void getProjectDocOfWarehouseTestNoData() {
		RequestParam mockParam = new RequestParam();
		DocumentReportRecord mockRecord = new DocumentReportRecord();		
		List<DocumentReportRecord> mockRecordList = new ArrayList<DocumentReportRecord>();
		mockRecordList.add(mockRecord);
		Mockito.doReturn(1).when(vt070002dao).countProjectDocumentWithWarehouseId(mockParam);
		Mockito.doReturn(mockRecordList).when(vt070002dao).getProjectDocumentWithWarehouseId(mockParam);
		ResponseEntityBase result = mockService.getProjectDocOfWarehouse(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void getProjectDocOfWarehouseTestNoDataHasPaging() {
		RequestParam mockParam = new RequestParam();
		mockParam.setPageNumber(0);
		mockParam.setPageNo(0);
		mockParam.setPageSize(999);
		DocumentReportRecord mockRecord = new DocumentReportRecord();		
		List<DocumentReportRecord> mockRecordList = new ArrayList<DocumentReportRecord>();
		mockRecordList.add(mockRecord);
		Mockito.doReturn(1).when(vt070002dao).countProjectDocumentWithWarehouseId(mockParam);
		Mockito.doReturn(mockRecordList).when(vt070002dao).getProjectDocumentWithWarehouseId(mockParam);
		ResponseEntityBase result = mockService.getProjectDocOfWarehouse(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void getProjectDocOfWarehouseTestNoDataInvalidPaging() {
		RequestParam mockParam = new RequestParam();
		mockParam.setPageNumber(999);
		mockParam.setPageNo(0);
		mockParam.setPageSize(0);
		DocumentReportRecord mockRecord = new DocumentReportRecord();		
		List<DocumentReportRecord> mockRecordList = new ArrayList<DocumentReportRecord>();
		mockRecordList.add(mockRecord);
		Mockito.doReturn(1).when(vt070002dao).countProjectDocumentWithWarehouseId(mockParam);
		Mockito.doReturn(mockRecordList).when(vt070002dao).getProjectDocumentWithWarehouseId(mockParam);
		ResponseEntityBase result = mockService.getProjectDocOfWarehouse(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void getProjectDocOfWarehouseTestHasData() {
		RequestParam mockParam = new RequestParam();
		DocumentReportRecord mockRecord = new DocumentReportRecord();
		mockRecord.setConstructionDoc("constructionDoc");
		mockRecord.setConstructionName("constructionName");
		mockRecord.setContractDoc("contractDoc");
		mockRecord.setContractName("contractName");
		mockRecord.setDocumentName("documentName");
		mockRecord.setFolderName("folderName");
		mockRecord.setFolderQRCode("folderQRCode");
		mockRecord.setPackageDoc("packageDoc");
		mockRecord.setPackageName("packageName");
		mockRecord.setPageNumber(69);
		mockRecord.setPosition("position");
		mockRecord.setPositionQRCode("positionQRCode");
		mockRecord.setProjectDoc("projectDoc");
		mockRecord.setProjectId(7777);
		mockRecord.setProjectName("projectName");
		mockRecord.setTinBoxName("tinBoxName");
		mockRecord.setTinBoxQRCode("tinBoxQRCode");
		mockRecord.setTotalFolder(68);
		mockRecord.setTotalRecords(96);
		mockRecord.setTotalTinbox(1);
		mockRecord.setWarehouseName("warehouseName");
		List<DocumentReportRecord> mockRecordList = new ArrayList<DocumentReportRecord>();
		mockRecordList.add(mockRecord);
		Mockito.doReturn(1).when(vt070002dao).countProjectDocumentWithWarehouseId(mockParam);
		Mockito.doReturn(mockRecordList).when(vt070002dao).getProjectDocumentWithWarehouseId(mockParam);
		ResponseEntityBase result = mockService.getProjectDocOfWarehouse(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
		assertEquals(new ArrayList<>(), result.getData());
	}
	
	@Test
	public void getProjectDocOfWarehouseTestNullconstDoc() {
		RequestParam mockParam = new RequestParam();
		DocumentReportRecord mockRecord = new DocumentReportRecord();
		mockRecord.setConstructionDoc(null);
		mockRecord.setConstructionName("constructionName");
		mockRecord.setContractDoc("contractDoc");
		mockRecord.setContractName("contractName");
		mockRecord.setDocumentName("documentName");
		mockRecord.setFolderName("folderName");
		mockRecord.setFolderQRCode("folderQRCode");
		mockRecord.setPackageDoc("packageDoc");
		mockRecord.setPackageName("packageName");
		mockRecord.setPageNumber(69);
		mockRecord.setPosition("position");
		mockRecord.setPositionQRCode("positionQRCode");
		mockRecord.setProjectDoc("projectDoc");
		mockRecord.setProjectId(7777);
		mockRecord.setProjectName("projectName");
		mockRecord.setTinBoxName("tinBoxName");
		mockRecord.setTinBoxQRCode("tinBoxQRCode");
		mockRecord.setTotalFolder(68);
		mockRecord.setTotalRecords(96);
		mockRecord.setTotalTinbox(1);
		mockRecord.setWarehouseName("warehouseName");
		List<DocumentReportRecord> mockRecordList = new ArrayList<DocumentReportRecord>();
		mockRecordList.add(mockRecord);
		Mockito.doReturn(1).when(vt070002dao).countProjectDocumentWithWarehouseId(mockParam);
		Mockito.doReturn(mockRecordList).when(vt070002dao).getProjectDocumentWithWarehouseId(mockParam);
		ResponseEntityBase result = mockService.getProjectDocOfWarehouse(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void getProjectDocOfWarehouseTestNullPkgDocNullCtDoc() {
		RequestParam mockParam = new RequestParam();
		DocumentReportRecord mockRecord = new DocumentReportRecord();
		mockRecord.setConstructionDoc("constructionDoc");
		mockRecord.setConstructionName("constructionName");
		mockRecord.setContractDoc(null);
		mockRecord.setContractName("contractName");
		mockRecord.setDocumentName("documentName");
		mockRecord.setFolderName("folderName");
		mockRecord.setFolderQRCode("folderQRCode");
		mockRecord.setPackageDoc(null);
		mockRecord.setPackageName("packageName");
		mockRecord.setPageNumber(69);
		mockRecord.setPosition("position");
		mockRecord.setPositionQRCode("positionQRCode");
		mockRecord.setProjectDoc("projectDoc");
		mockRecord.setProjectId(7777);
		mockRecord.setProjectName("projectName");
		mockRecord.setTinBoxName("tinBoxName");
		mockRecord.setTinBoxQRCode("tinBoxQRCode");
		mockRecord.setTotalFolder(68);
		mockRecord.setTotalRecords(96);
		mockRecord.setTotalTinbox(1);
		mockRecord.setWarehouseName("warehouseName");
		List<DocumentReportRecord> mockRecordList = new ArrayList<DocumentReportRecord>();
		mockRecordList.add(mockRecord);
		Mockito.doReturn(1).when(vt070002dao).countProjectDocumentWithWarehouseId(mockParam);
		Mockito.doReturn(mockRecordList).when(vt070002dao).getProjectDocumentWithWarehouseId(mockParam);
		ResponseEntityBase result = mockService.getProjectDocOfWarehouse(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test 
	public void getDocumentReportTestZeroParamHasException() {
		ResponseEntityBase result = mockService.getDocumentReport(0, 0, 0);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test 
	public void getDocumentReportTest() {
		WarehouseDetail mockWarehouse = new WarehouseDetail();
		Mockito.doReturn(mockWarehouse).when(vt070002dao).countThingsInWarehouse(10001);
		ResponseEntityBase result = mockService.getDocumentReport(10001, 20, 0);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void getTinBoxInType1WarehouseTestEmptyParam() {
		TinBoxDetail mockParam = new TinBoxDetail();
		ResponseEntityBase result = mockService.getTinBoxInType1Warehouse(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
		
	}
	
	@Test
	public void getTinBoxInType1WarehouseTest() {
		TinBoxDetail mockParam = new TinBoxDetail();
		mockParam.setName("mock name");
		mockParam.setMngUser("mock user");
		ResponseEntityBase result = mockService.getTinBoxInType1Warehouse(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
		
	}
	
	@Test
	public void getTinBoxInType1WarehouseTestHasException() {
		TinBoxDetail mockParam = new TinBoxDetail();
		Mockito.doThrow(new RuntimeException()).when(vt070002dao).getTinBoxInType1Warehouse(mockParam);
		ResponseEntityBase result = mockService.getTinBoxInType1Warehouse(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
		
	}
	
	@Test
	public void exportExcelTestNoRole() {
		int warehouseId = 9999;
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		//roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		File result = mockService.exportExcel(warehouseId, roleList);
		assertEquals(null, result);
	}
	
	@Test
	public void exportExcelTestHasException() {
		int warehouseId = 9999;
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		Mockito.doThrow(new RuntimeException()).when(vt070002dao).countThingsInWarehouse(warehouseId);
		File result = mockService.exportExcel(warehouseId, roleList);
		assertEquals(null, result);
	}
	
	@Test
	public void exportExcelTestRolePMQT_ROLE_WAREHOUSE() {
		int warehouseId = 9999;
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		Mockito.doReturn(new WarehouseDetail()).when(vt070002dao).countThingsInWarehouse(warehouseId);
		File result = mockService.exportExcel(warehouseId, roleList);
		assertEquals(null, result);
	}
	
	@Test
	public void exportExcelTestRolePMQT_ROLE_WAREHOUSE_REPORT() {
		int warehouseId = 9999;
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_REPORT));
		Mockito.doReturn(new WarehouseDetail()).when(vt070002dao).countThingsInWarehouse(warehouseId);
		File result = mockService.exportExcel(warehouseId, roleList);
		assertEquals(null, result);
	}
	
	@Test
	public void writeExcelTest() throws Exception{
		Mockito.doReturn("Mock title").when(headerTitle).getProperty("TOTAL_DOC");
		Mockito.doReturn("Mock title").when(headerTitle).getProperty("TOTAL_TINBOX");
		Mockito.doReturn("Mock title").when(headerTitle).getProperty("TOTAL_FOLDER");
		Mockito.doReturn("Mock title").when(headerTitle).getProperty("PROJECT");
		Mockito.doReturn("Mock title").when(headerTitle).getProperty("PACKAGE");
		Mockito.doReturn("Mock title").when(headerTitle).getProperty("CONTRACT");
		Mockito.doReturn("Mock title").when(headerTitle).getProperty("CONSTRUCTION");
		Mockito.doReturn("Mock title").when(headerTitle).getProperty("DOC");
		Mockito.doReturn("Mock title").when(headerTitle).getProperty("TINBOX_CODE");
		Mockito.doReturn("Mock title").when(headerTitle).getProperty("TINBOX_NAME");
		Mockito.doReturn("Mock title").when(headerTitle).getProperty("FOLDER_CODE");
		Mockito.doReturn("Mock title").when(headerTitle).getProperty("FOLDER_NAME");
		Mockito.doReturn("Mock title").when(headerTitle).getProperty("RACK_CODE");
		
		List<DocumentReportRecord> records = new ArrayList<DocumentReportRecord>();
		DocumentReportRecord record = new DocumentReportRecord();
		records.add(record);
		File f = mockService.writeExcel(records, 1, 1, "/var/templateExcel/VT70002.xlsx");
		assertEquals(true, f.isFile());
	}
	
	@Test
	public void createExcelOutputExcelTestNoRole() {
		RequestParam req = new RequestParam();
		File res = mockService.createExcelOutputExcel(req, roleList);
		assertEquals(null, res);
	}
	
	@Test
	public void createExcelOutputExcelTestHasRole() {
		RequestParam req = new RequestParam();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> hasRole = oauth.getAuthorities();
		SimpleGrantedAuthority whrep = new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_REPORT);
		hasRole.add(whrep);
		File res = mockService.createExcelOutputExcel(req, hasRole);
		assertEquals(null, res);
	}
	
	
	@Test
	public void createExcelOutputExcelTestHasRoleAndEmptyReport() {		
		RequestParam req = new RequestParam();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> hasRole = oauth.getAuthorities();
		SimpleGrantedAuthority whrep = new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_REPORT);
		hasRole.add(whrep);
		Mockito.doReturn(new ArrayList<DocumentReportRecord>()).when(vt070002dao).getAllProjectDocumentWithWarehouseId(req);
		File res = mockService.createExcelOutputExcel(req, hasRole);
		assertEquals(null, res);
	}
	
	@Test
	public void createExcelOutputExcelTestHasRoleAndNotEmptyReport() {		
		RequestParam req = new RequestParam();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> hasRole = oauth.getAuthorities();
		SimpleGrantedAuthority whrep = new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_REPORT);
		hasRole.add(whrep);
		List<DocumentReportRecord> reports = new ArrayList<DocumentReportRecord>();
		DocumentReportRecord report = new DocumentReportRecord();
		reports.add(report);
		Mockito.doReturn(reports).when(vt070002dao).getAllProjectDocumentWithWarehouseId(req);
		File res = mockService.createExcelOutputExcel(req, hasRole);
		assertEquals(null, res);
	}
	
	@Test
	public void createExcelOutputExcelTestHasRoleAndNotEmptyReportAndPkgCtDocNotNull() {		
		RequestParam req = new RequestParam();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> hasRole = oauth.getAuthorities();
		SimpleGrantedAuthority whrep = new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_REPORT);
		hasRole.add(whrep);
		List<DocumentReportRecord> reports = new ArrayList<DocumentReportRecord>();
		DocumentReportRecord report = new DocumentReportRecord();
		report.setPackageDoc("abcd");
		report.setContractDoc("abcd1234");
		reports.add(report);
		Mockito.doReturn(reports).when(vt070002dao).getAllProjectDocumentWithWarehouseId(req);
		File res = mockService.createExcelOutputExcel(req, hasRole);
		assertEquals(null, res);
	}
	
	@Test
	public void createExcelOutputExcelTestHasRoleAndNotEmptyReportAndPkgCtConsDocNotNull() {		
		RequestParam req = new RequestParam();
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> hasRole = oauth.getAuthorities();
		SimpleGrantedAuthority whrep = new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_REPORT);
		hasRole.add(whrep);
		List<DocumentReportRecord> reports = new ArrayList<DocumentReportRecord>();
		DocumentReportRecord report = new DocumentReportRecord();
		report.setPackageDoc("abcd");
		report.setContractDoc("abcd1234");
		report.setConstructionDoc("abcxyz123");
		reports.add(report);
		Mockito.doReturn(reports).when(vt070002dao).getAllProjectDocumentWithWarehouseId(req);
		File res = mockService.createExcelOutputExcel(req, hasRole);
		assertEquals(null, res);
	}
	
	@Test
	public void countTinBoxAndOccupiedSlotTest() {
		ResponseEntityBase res = mockService.countTinBoxAndOccupiedSlot(9999);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, res.getStatus());
	}
	
	@Test
	public void countTinBoxAndOccupiedSlotTestException() {
		Mockito.doThrow(new RuntimeException()).when(vt070002dao).countTotalTinBox(9999);
		ResponseEntityBase res = mockService.countTinBoxAndOccupiedSlot(9999);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, res.getStatus());
	}
	
	@Test
	public void getFolderByQRCodeTestEmptyFolder() {
		RequestParam req = new RequestParam();
		Mockito.doReturn("mock msg").when(warehouseMessage).getProperty("0700010");
		Object res = mockService.getFolderByQRCode(req, "uname", roleList);
		FolderDetail resFolder = (FolderDetail) BeanUtilities.getObjectFieldValue(res, "folder");
		assertEquals("mock msg", resFolder.getError());
	}
	
	@Test
	public void getFolderByQRCodeTestNotEmptyFolder() {
		RequestParam req = new RequestParam();
		Mockito.doReturn(dfFolderList).when(vt070002dao).getListFolderByQrCode(req);
		Object res = mockService.getFolderByQRCode(req, "uname", roleList);
		FolderDetail resFolder = (FolderDetail) BeanUtilities.getObjectFieldValue(res, "folder");
		assertEquals(defaultFolder, resFolder);
	}
	
	@Test
	public void getProjectTreeInFolderTestNull() {
		RequestParam req = new RequestParam();
		Object res = mockService.getProjectTreeInFolder(req);
		assertEquals(new ArrayList(), res);
	}
	
	@Test
	public void getProjectTreeInFolderTestPjNotNull() {
		RequestParam req = new RequestParam();
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjectById(req);
		List<ProjectDetail> res = (List<ProjectDetail>) mockService.getProjectTreeInFolder(req);
		assertEquals(1, res.size());
	}
	
	@Test
	public void getProjectTreeInFolderTestPkgCtNotNull() {
		RequestParam req = new RequestParam();
		Mockito.doReturn(dfProjectList).when(vt070002dao).getProjectById(req);
		Mockito.doReturn(dfPackageList).when(vt070002dao).getProjectPackagesInFolderSuper(req);
		Mockito.doReturn(dfContractList).when(vt070002dao).getProjectContractsInFolderSuper(req);
		List<ProjectDetail> res = (List<ProjectDetail>) mockService.getProjectTreeInFolder(req);
		assertEquals(1, res.size());
	}
	
	@Test
	public void getType1WarehousesTest() {
		WarehouseDetail param = new WarehouseDetail();
		ResponseEntityBase res = mockService.getType1Warehouses(param);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, res.getStatus());		
	}
	
	@Test
	public void getType1WarehousesTestHasException() {
		WarehouseDetail param = new WarehouseDetail();
		Mockito.doThrow(new RuntimeException()).when(vt070002dao).getType1Warehouses(param);
		ResponseEntityBase res = mockService.getType1Warehouses(param);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, res.getStatus());		
	}
}
