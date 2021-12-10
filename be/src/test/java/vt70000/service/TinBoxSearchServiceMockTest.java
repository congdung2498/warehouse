package vt70000.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt07.vt070000.dao.VT070000DAO;
import com.viettel.vtnet360.vt07.vt070000.entity.DocumentDetails;
import com.viettel.vtnet360.vt07.vt070000.entity.RackDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.SlotDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxSearchResult;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070000.service.TinBoxSearchService;
import com.viettel.vtnet360.vt07.vt070000.service.TinBoxSearchServiceImpl;
import com.viettel.vtnet360.vt07.vt070002.entity.FolderDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;

public class TinBoxSearchServiceMockTest {
	
	private static final String TEST_WAREHOUSE_NAME = "Test Warehouse";
	
	@InjectMocks
	private TinBoxSearchServiceImpl mockService;
	
	@Spy
	VT070000DAO vt070000dao;
	
	@Mock
	List<WarehouseDetail> mockWarehouseDetailList;
	List<DocumentDetails> mockDocumentDetailList;
	FolderDetail folder;
	
	WarehouseDetail testWarehouse = new WarehouseDetail();
	List<WarehouseDetail> testWarehouseList = new ArrayList<WarehouseDetail>();
	DocumentDetails testDocument = new DocumentDetails();
	List<DocumentDetails> testDocumentList = new ArrayList<DocumentDetails>();
	FolderDetail testFolder = new FolderDetail();
	List<FolderDetail> testFolderList = new ArrayList<FolderDetail>();
	TinBoxSearchResult testTinBoxSearchResult = new TinBoxSearchResult();
	List<TinBoxSearchResult> testTBSearchResultList = new ArrayList<TinBoxSearchResult>();
	TinBoxDetail testTinBox = new TinBoxDetail();
	List<TinBoxDetail> testTinBoxList = new ArrayList<TinBoxDetail>();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		testWarehouse.setName(TEST_WAREHOUSE_NAME);
		testDocument.setConstructionName("constructionName");
		testDocument.setContractName("contractName");
		testDocument.setDocumentId(1);
		testDocument.setDocumentName("documentName");
		testDocument.setFolderId(2);
		testDocument.setFolderName("folderName");
		testDocument.setFolderQrCode("folderQrCode");
		testDocument.setPackageName("packageName");
		testDocument.setProjectName("projectName");
		testDocument.setType("type");
		testWarehouseList.add(testWarehouse);
		testDocumentList.add(testDocument);
		testFolderList.add(testFolder);
		testTBSearchResultList.add(testTinBoxSearchResult);
		testTinBox.setWarehouseId(1);
		testTinBoxList.add(testTinBox);
	}
	
	//TinBoxSearchServiceImpl.getDocumentsInBox
	@Test
	public void getAllActiveWarehouseMockTest() {
		Mockito.doReturn(new ArrayList<WarehouseDetail>()).when(vt070000dao).getAllActiveWarehouse();
		List<WarehouseDetail> result = mockService.getAllActiveWarehouse();
		assertEquals(new ArrayList<WarehouseDetail>(), result);
	}
	
	@Test
	public void getDocumentsInBoxMockTest() {
		Mockito.doReturn(testFolderList).when(vt070000dao).getFolderInTinBox(1);
		Mockito.doReturn(testDocumentList).when(vt070000dao).getAllDocumentInFolderV2(testFolder);
		List<DocumentDetails> result = mockService.getDocumentsInBox(1);
		assertEquals(testDocumentList, result);
	}
	
	@Test
	public void getDocumentsInBoxMockTest_TypeProject() {
		long testProjectId = 1;
		DocumentDetails testDocument = new DocumentDetails();
		List<DocumentDetails> testDocumentList = new ArrayList<DocumentDetails>();
		testDocument.setType("project");
		testDocument.setDocumentId(1);
		testDocumentList.add(testDocument);
		Mockito.doReturn(testFolderList).when(vt070000dao).getFolderInTinBox(1);
		Mockito.doReturn(testDocumentList).when(vt070000dao).getAllDocumentInFolderV2(testFolder);
		Mockito.doReturn(testProjectId).when(vt070000dao).getProjectIdByDoc(1);
		Mockito.doReturn("Test project name").when(vt070000dao).getProjectName(1);
		List<DocumentDetails> result = mockService.getDocumentsInBox(1);
		assertEquals("Test project name", result.get(0).getProjectName());
	}
	
	@Test
	public void getDocumentsInBoxMockTest_TypePackage() {
		long testPackageId = 1;
		DocumentDetails testDocument = new DocumentDetails();
		List<DocumentDetails> testDocumentList = new ArrayList<DocumentDetails>();
		testDocument.setType("package");
		testDocument.setDocumentId(1);
		testDocumentList.add(testDocument);
		Mockito.doReturn(testFolderList).when(vt070000dao).getFolderInTinBox(1);
		Mockito.doReturn(testDocumentList).when(vt070000dao).getAllDocumentInFolderV2(testFolder);
		Mockito.doReturn(testPackageId).when(vt070000dao).getPackageIdByDoc(1);
		Mockito.doReturn("Test project name").when(vt070000dao).getProjectNameByPackageDoc(1);
		Mockito.doReturn("Test package name").when(vt070000dao).getPackageName(1);
		List<DocumentDetails> result = mockService.getDocumentsInBox(1);
		assertEquals("Test package name", result.get(0).getPackageName());
	}
	
	@Test
	public void getDocumentsInBoxMockTest_TypeContract() {
		long testContractId = 1;
		DocumentDetails testDocument = new DocumentDetails();
		List<DocumentDetails> testDocumentList = new ArrayList<DocumentDetails>();
		testDocument.setType("contract");
		testDocument.setDocumentId(1);
		testDocumentList.add(testDocument);
		Mockito.doReturn(testFolderList).when(vt070000dao).getFolderInTinBox(1);
		Mockito.doReturn(testDocumentList).when(vt070000dao).getAllDocumentInFolderV2(testFolder);
		Mockito.doReturn(testContractId).when(vt070000dao).getContractIdByDoc(1);
		Mockito.doReturn("Test project name").when(vt070000dao).getProjectNameByContractDocV3(1);
		Mockito.doReturn("Test contract name").when(vt070000dao).getContractName(1);
		List<DocumentDetails> result = mockService.getDocumentsInBox(1);
		assertEquals("Test contract name", result.get(0).getContractName());
	}
	
	@Test
	public void getDocumentsInBoxMockTest_TypeConstruction() {
		long testConstructionId = 1;
		DocumentDetails testDocument = new DocumentDetails();
		List<DocumentDetails> testDocumentList = new ArrayList<DocumentDetails>();
		testDocument.setType("construction");
		testDocument.setDocumentId(1);
		testDocumentList.add(testDocument);
		Mockito.doReturn(testFolderList).when(vt070000dao).getFolderInTinBox(1);
		Mockito.doReturn(testDocumentList).when(vt070000dao).getAllDocumentInFolderV2(testFolder);
		Mockito.doReturn(testConstructionId).when(vt070000dao).getConstructionIdByDoc(1);
		Mockito.doReturn("Test project name").when(vt070000dao).getProjectNameByConstructionDocV3(1);
		Mockito.doReturn("Test construction name").when(vt070000dao).getConstructionName(1);
		Mockito.doReturn("Test contract name").when(vt070000dao).getContractNameByConstructionId(1);
		List<DocumentDetails> result = mockService.getDocumentsInBox(1);
		assertEquals("Test contract name", result.get(0).getContractName());
		assertEquals("Test construction name", result.get(0).getConstructionName());
	}
	
	@Test
	public void getAllTinBoxInWarehouseTest() {
		Mockito.doReturn(testTinBoxList).when(vt070000dao).getAllTinBox(1);
		Mockito.doReturn(TEST_WAREHOUSE_NAME).when(vt070000dao).getWarehouseNameById(1);
		List<TinBoxSearchResult> result = mockService.getAllTinBoxInWarehouse(1);
		assertEquals(TEST_WAREHOUSE_NAME, result.get(0).getWarehouseName());
	}
	
	@Test
	public void getAllTinBoxInWarehouseTest_WarehouseNameNull() {
		Mockito.doReturn(testTinBoxList).when(vt070000dao).getAllTinBox(1);
		Mockito.doReturn(null).when(vt070000dao).getWarehouseNameById(1);
		List<TinBoxSearchResult> result = mockService.getAllTinBoxInWarehouse(1);
		assertEquals(new ArrayList<TinBoxSearchResult>(), result);
	}
	
	@Test
	public void getAllTinBoxInWarehouseTest_WarehouseIDSmallerThan1() {
		Mockito.doReturn(testTinBoxList).when(vt070000dao).getAllTinBox(0);
		Mockito.doReturn(TEST_WAREHOUSE_NAME).when(vt070000dao).getWarehouseNameById(1);
		List<TinBoxSearchResult> result = mockService.getAllTinBoxInWarehouse(0);
		assertEquals(TEST_WAREHOUSE_NAME, result.get(0).getWarehouseName());
	}
	
	@Test
	public void getAllTinBoxInWarehouseTest_WarehouseIDSmallerThan1_CannotGetWarehouseName() {
		Mockito.doReturn(testTinBoxList).when(vt070000dao).getAllTinBox(0);
		Mockito.doReturn(null).when(vt070000dao).getWarehouseNameById(1);
		List<TinBoxSearchResult> result = mockService.getAllTinBoxInWarehouse(0);
		assertEquals(new ArrayList<TinBoxSearchResult>(), result);
	}
	
	@Test
	public void searchTinBoxTest_EmptyKeyword() {
		List<TinBoxSearchResult> result = mockService.searchTinBox(0, "", false, false, false, false, false, false);
		assertEquals(new ArrayList<TinBoxSearchResult>(), result);
	}
	
	@Test
	public void searchTinBoxTest_NegativeWarehouseId() {
		List<TinBoxSearchResult> result = mockService.searchTinBox(-1, "abc", false, false, false, false, false, false);
		assertEquals(new ArrayList<TinBoxSearchResult>(), result);
	}
	
	@Test
	public void searchTinBoxTest_ByTinBox() {
		List<Long> searchResult = new ArrayList<Long>();
		long testTinBoxId = 1;
		long testTinBoxId2 = 1;
		searchResult.add(testTinBoxId);
		searchResult.add(testTinBoxId2);
		List<Long> searchResultRemoveDup = new ArrayList<Long>();
		searchResultRemoveDup.add(testTinBoxId);
		long testSlotId = 111;
		SlotDetail testSlot = new SlotDetail();
		testSlot.setSlotId(111);
		testSlot.setHeight(5);
		testSlot.setQrCode("Slot QR Code");
		long testRackId = 222;
		RackDetail testRack = new RackDetail();
		testRack.setRow(33);
		testRack.setColumn(44);
		TinBoxDetail testTinBox = new TinBoxDetail();
		testTinBox.setTinBoxId(testTinBoxId);
		testTinBox.setSlotId(111);
		List<TinBoxDetail> testTinBoxList = new ArrayList<TinBoxDetail>();
		testTinBoxList.add(testTinBox);
		TinBoxSearchResult lastRecord = new TinBoxSearchResult();
		List<TinBoxSearchResult> testLastRecordList = new ArrayList<TinBoxSearchResult>();
		testLastRecordList.add(lastRecord);
		String testLocation = "33/44/5";
		
		Mockito.doReturn(searchResult).when(vt070000dao).searchTinBoxByName("%abc%");
		Mockito.doReturn(testTinBoxList).when(vt070000dao).getTinBox(searchResultRemoveDup, 1);
		Mockito.doReturn("Warehouse Name").when(vt070000dao).getWarehouseNameById(1);
		Mockito.doReturn(testSlot).when(vt070000dao).getSlotById(testSlotId);
		Mockito.doReturn(testRackId).when(vt070000dao).getRackIdBySlotId(testSlotId);
		Mockito.doReturn(testRack).when(vt070000dao).getRackById(testRackId);
		
		List<TinBoxSearchResult> result = mockService.searchTinBox(1, "abc", true, false, false, false, false, false);
		
		assertEquals(testTinBoxId, result.get(0).getTinBox().getTinBoxId());
		assertEquals("Slot QR Code", result.get(0).getSlotQrCode());
		assertEquals(testLocation, result.get(0).getTinBoxLocation());
		assertEquals("Warehouse Name", result.get(0).getWarehouseName());
	}
}
