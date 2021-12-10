package vt70000.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.lang.reflect.Field;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070000.dao.VT070000DAO;
import com.viettel.vtnet360.vt07.vt070000.entity.RackDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.RequestParam;
import com.viettel.vtnet360.vt07.vt070000.entity.SlotDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDiagramParam;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseRackSlot;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseRequestParam;
import com.viettel.vtnet360.vt07.vt070000.service.VT070000ServiceImpl;
import com.viettel.vtnet360.vt07.vt070001.dao.VT070001DAO;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeDetail;

import misc.BeanUtilities;

public class VT070000ServiceTest {
	private static final String ENCRYPTED_USERNAME = "a37d7e12b490d7000dcc6fa0fd7066c2";
	private static final String ENCRYPTED_PASSWORD = "c9a0172b171314c3eb811606a8d1528b";
	Date createDate;
	Date updateDate;
	WarehouseDetail defaultWarehouse = new WarehouseDetail();
	List<WarehouseDetail> defaultWarehouseList = new ArrayList<WarehouseDetail>();
	RackDetail defaultRack = new RackDetail();
	List<RackDetail> defaultRackList = new ArrayList<RackDetail>();
	SlotDetail defaultSlot = new SlotDetail();
	List<SlotDetail> defaultSlotList = new ArrayList<SlotDetail>();	
	VT070001EntityBarcodeDetail defaultBarcode = new VT070001EntityBarcodeDetail();
	WarehouseRequestParam defaultWarehouseReqParam = new WarehouseRequestParam();
	
	@InjectMocks
	private VT070000ServiceImpl mockService;
	
	@Spy
	VT070000DAO vt070000dao;
	@Spy
	VT070001DAO vt070001dao;
	@Spy
	Properties linkTemplateExcel;
	@Spy
	Properties headerTitle;
	@Spy
	Properties warehouseMessage;
	
	@Mock
    private OAuth2Authentication principal;
	
	@Mock
	private Collection<GrantedAuthority> roleList;
	
	@Before
	public void setup() {		
		MockitoAnnotations.initMocks(this);
		
		createDate = new Date();
		updateDate = new Date();
		
		defaultSlot.setSlotId(1984);
		defaultSlot.setRackId(9999);
		defaultSlot.setHeight(5);
		defaultSlot.setQrCode("df slot qr");
		defaultSlot.setDelFlag(false);
		defaultSlot.setChanged(false);
		defaultSlot.setUpdatedNum(13);
		defaultSlot.setStatus(1);
		defaultSlot.setError("df slot error");
		defaultSlotList.add(defaultSlot);
		
		defaultRack.setRackId(9999);
		defaultRack.setRow(8888);
		defaultRack.setWarehouseId(7777);
		defaultRack.setWarehouseName("Default warehouse");
		defaultRack.setColumn(6666);
		defaultRack.setType(1);
		defaultRack.setDelFlag(false);
		defaultRack.setChanged(false);
		defaultRack.setUpdatedNum(0);
		defaultRack.setError("df rack error");
		defaultRack.setPrintTime(5555);
		defaultRack.setSlots(defaultSlotList);
		defaultRackList.add(defaultRack);
		
		defaultWarehouse.setAcreage(1000);
		defaultWarehouse.setAddress("A test address");
		defaultWarehouse.setChanged(false);
		defaultWarehouse.setColumnNum(99);		
		defaultWarehouse.setCreateDate(createDate);
		defaultWarehouse.setUpdateDate(updateDate);
		defaultWarehouse.setCreateUser("createUser");
		defaultWarehouse.setDelFlag(false);
		defaultWarehouse.setError("error");
		defaultWarehouse.setHeightNum(5);
		defaultWarehouse.setName("Default warehouse");
		defaultWarehouse.setPageNumber(69);
		defaultWarehouse.setPageSize(10);
		defaultWarehouse.setRacks(defaultRackList);
		defaultWarehouse.setRowNum(55);
		defaultWarehouse.setStatus(1);
		defaultWarehouse.setTotalFolder(7);
		defaultWarehouse.setTotalRecords(15);
		defaultWarehouse.setTotalTinBox(12);
		defaultWarehouse.setType(1);
		defaultWarehouse.setUpdatedNum(8);
		defaultWarehouse.setWarehouseId(7777);
		defaultWarehouseList.add(defaultWarehouse);
		
		defaultWarehouseReqParam.setAcreage("999999");
		defaultWarehouseReqParam.setAddress("Ha Noi");
		defaultWarehouseReqParam.setColumnNum("69");
		defaultWarehouseReqParam.setCreatedDate(createDate.toString());
		defaultWarehouseReqParam.setCreateUser("User");
		defaultWarehouseReqParam.setDelFlag(false);
		defaultWarehouseReqParam.setHeightNum("4");
		defaultWarehouseReqParam.setName("A warehouse name");
		defaultWarehouseReqParam.setPageNumber(69);
		defaultWarehouseReqParam.setPageSize(20);
		defaultWarehouseReqParam.setRowNum("68");
		defaultWarehouseReqParam.setSecurityPassword(ENCRYPTED_PASSWORD);
		defaultWarehouseReqParam.setSecurityUsername(ENCRYPTED_USERNAME);
		defaultWarehouseReqParam.setStatus("1");
		defaultWarehouseReqParam.setTotalRecords(96);
		defaultWarehouseReqParam.setType("2");
		defaultWarehouseReqParam.setUpdateDate(updateDate);
		defaultWarehouseReqParam.setUpdateUser("Another user");
		defaultWarehouseReqParam.setWarehouseId("7777");
	}
	
	
	
	@Test
	public void getListWarehouseDetailTestNoWarehouse() throws Exception{
		RequestParam mockParam = new RequestParam();
		mockParam.setPageNo(1);
//		Object expectedResult = new Object() {
//			@SuppressWarnings("unused")
//			public List<WarehouseDetail> warehouses = new ArrayList<>();
//			@SuppressWarnings("unused")
//			public int total = 1;
//		};
		Mockito.doReturn(0).when(vt070000dao).getNumberOfWarehouse(mockParam);
		Mockito.doReturn(null).when(vt070000dao).getListWarehouse(mockParam);
		Object result = mockService.getListWarehouseDetail(mockParam);
//		Class<?> clazz = result.getClass();
//		Field field = clazz.getField("total");
//		field.setAccessible(true);
//		int resultTotalValue = (int) field.get(result);
		int resultTotalValue = (int) BeanUtilities.getObjectFieldValue(result, "total");
		List<WarehouseDetail> resultWarehouses = (List<WarehouseDetail>) BeanUtilities.getObjectFieldValue(result, "warehouses");
		
		assertEquals(0, resultTotalValue);
		assertEquals(new ArrayList<>(), resultWarehouses);
	}
	
	@Test
	public void getListWarehouseDetailTestZeroUserLevel() throws Exception{
		defaultWarehouse.setRacks(null);
		RequestParam mockParam = new RequestParam();
		mockParam.setPageNo(1);
		mockParam.setLevel(0);
		Mockito.doReturn(1).when(vt070000dao).getNumberOfWarehouse(mockParam);
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListWarehouse(mockParam);
		Object result = mockService.getListWarehouseDetail(mockParam);
		List<WarehouseDetail> resultWarehouses = (List<WarehouseDetail>) BeanUtilities.getObjectFieldValue(result, "warehouses");
		assertEquals(null, resultWarehouses.get(0).getRacks());
	}
	
	@Test
	public void getListWarehouseDetailTestLowUserLevelNullRack() throws Exception{
		defaultWarehouse.setRacks(null);
		RequestParam mockParam = new RequestParam();
		mockParam.setPageNo(1);
		mockParam.setLevel(1);
		Mockito.doReturn(1).when(vt070000dao).getNumberOfWarehouse(mockParam);
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListWarehouse(mockParam);
		Mockito.doReturn(null).when(vt070000dao).getListRackDetailByWarehouseId(7777);
		Object result = mockService.getListWarehouseDetail(mockParam);
		List<WarehouseDetail> resultWarehouses = (List<WarehouseDetail>) BeanUtilities.getObjectFieldValue(result, "warehouses");
		assertEquals(new ArrayList<>(), resultWarehouses.get(0).getRacks());
	}
	
	@Test
	public void getListWarehouseDetailTestNullSlot() throws Exception{
		defaultWarehouse.setRacks(null);
		RequestParam mockParam = new RequestParam();
		mockParam.setPageNo(1);
		mockParam.setLevel(2);
		Mockito.doReturn(1).when(vt070000dao).getNumberOfWarehouse(mockParam);
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListWarehouse(mockParam);
		Mockito.doReturn(defaultRackList).when(vt070000dao).getListRackDetailByWarehouseId(7777);
		Mockito.doReturn(null).when(vt070000dao).getListSlotDetailByRackId(9999);
		Object result = mockService.getListWarehouseDetail(mockParam);
		List<WarehouseDetail> resultWarehouses = (List<WarehouseDetail>) BeanUtilities.getObjectFieldValue(result, "warehouses");
		assertEquals(new ArrayList<>(), resultWarehouses.get(0).getRacks().get(0).getSlots());
	}
	
	@Test
	public void getListWarehouseDetailTestHasWarehouse() throws Exception{
		defaultWarehouse.setRacks(null);
		defaultRack.setSlots(null);
		RequestParam mockParam = new RequestParam();
		mockParam.setPageNo(1);
		mockParam.setLevel(2);
		Mockito.doReturn(1).when(vt070000dao).getNumberOfWarehouse(mockParam);
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListWarehouse(mockParam);
		Mockito.doReturn(defaultRackList).when(vt070000dao).getListRackDetailByWarehouseId(7777);
		Mockito.doReturn(defaultSlotList).when(vt070000dao).getListSlotDetailByRackId(9999);
		Object result = mockService.getListWarehouseDetail(mockParam);
		int resultTotalValue = (int) BeanUtilities.getObjectFieldValue(result, "total");
		List<WarehouseDetail> resultWarehouseList = (List<WarehouseDetail>) BeanUtilities.getObjectFieldValue(result, "warehouses");
		WarehouseDetail resultWarehouse = resultWarehouseList.get(0);
		RackDetail resultRack = resultWarehouse.getRacks().get(0);
		SlotDetail resultSlot = resultRack.getSlots().get(0);
		assertEquals(1, resultTotalValue);
		assertEquals(1000, (int) resultWarehouse.getAcreage());
		assertEquals("A test address", resultWarehouse.getAddress());
		assertEquals(false, resultWarehouse.isChanged());
		assertEquals(99, resultWarehouse.getColumnNum());
		assertEquals(createDate, resultWarehouse.getCreateDate());
		assertEquals("createUser", resultWarehouse.getCreateUser());
		assertEquals(false, resultWarehouse.isDelFlag());
		assertEquals("error", resultWarehouse.getError());
		assertEquals(5, resultWarehouse.getHeightNum());
		assertEquals("Default warehouse", resultWarehouse.getName());
		assertEquals(69, resultWarehouse.getPageNumber());
		assertEquals(10, resultWarehouse.getPageSize());
		assertEquals(55, resultWarehouse.getRowNum());
		assertEquals(1, resultWarehouse.getStatus());
		assertEquals(7, resultWarehouse.getTotalFolder());
		assertEquals(15, resultWarehouse.getTotalRecords());
		assertEquals(12, resultWarehouse.getTotalTinBox());
		assertEquals(1, resultWarehouse.getType());
		assertEquals(8, resultWarehouse.getUpdatedNum());
		assertEquals(5555, resultRack.getPrintTime());
		assertEquals("Default warehouse", resultRack.getWarehouseName());
		assertEquals(8888, resultRack.getRow());
		assertEquals(6666, resultRack.getColumn());
		assertEquals(1, resultRack.getType());
		assertEquals(7777, resultRack.getWarehouseId());
		assertEquals(false, resultRack.isChanged());
		assertEquals(false, resultRack.isDelFlag());
		assertEquals(0, resultRack.getUpdatedNum());
		assertEquals("df rack error", resultRack.getError());
		assertEquals(1984, resultSlot.getSlotId());
		assertEquals(9999, resultSlot.getRackId());
		assertEquals(5, resultSlot.getHeight());
		assertEquals("df slot qr", resultSlot.getQrCode());
		assertEquals(false, resultSlot.isChanged());
		assertEquals(false, resultSlot.isDelFlag());
		assertEquals(1, resultSlot.getStatus());
		assertEquals(13, resultSlot.getUpdatedNum());
		assertEquals("df slot error", resultSlot.getError());
	}
	
	@Test
	public void updateWarehouseDetailTestNullRack() {
		defaultWarehouse.setRacks(null);
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		assertEquals(null, result.getRacks());
	}
	
	@Test
	public void updateWarehouseDetailTestCannotChangeRackType() {
		defaultRack.setChanged(true);
		defaultSlot.setChanged(true);
		Mockito.doReturn(defaultSlotList).when(vt070000dao).getListSlotDetailByRackId(9999);
		Mockito.doReturn(4567).when(vt070000dao).updateSlotDetail(Matchers.any(SlotDetail.class));
		Mockito.doReturn(5678).when(vt070000dao).updateRackDetail(Matchers.any(RackDetail.class));
		Mockito.doReturn(defaultSlot).when(vt070000dao).getSlotById(1984);
		Mockito.doReturn("A mock message").when(warehouseMessage).getProperty("0700009");
		Mockito.doReturn(6789).when(vt070001dao).updateStatusBarcodeDetail(Matchers.any(VT070001EntityBarcodeDetail.class));
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		RackDetail resultRack = result.getRacks().get(0);
		SlotDetail resultSlot = resultRack.getSlots().get(0);
		assertEquals(true, resultRack.getError().startsWith("A mock message"));
	}
	
	@Test
	public void updateWarehouseDetailTestRackAlreadyChanged() {
		defaultRack.setChanged(false);
		defaultSlot.setChanged(true);
		Mockito.doReturn(defaultSlotList).when(vt070000dao).getListSlotDetailByRackId(9999);
		Mockito.doReturn(4567).when(vt070000dao).updateSlotDetail(Matchers.any(SlotDetail.class));
		Mockito.doReturn(5678).when(vt070000dao).updateRackDetail(Matchers.any(RackDetail.class));
		Mockito.doReturn(defaultSlot).when(vt070000dao).getSlotById(1984);
		Mockito.doReturn(6789).when(vt070001dao).updateStatusBarcodeDetail(Matchers.any(VT070001EntityBarcodeDetail.class));
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		RackDetail resultRack = result.getRacks().get(0);
		SlotDetail resultSlot = resultRack.getSlots().get(0);
		assertEquals("df rack error", resultRack.getError());
	}
	
	@Test
	public void updateWarehouseDetailTestNullSlotFromDAO() {
		defaultRack.setChanged(true);
		defaultSlot.setChanged(true);
		Mockito.doReturn(null).when(vt070000dao).getListSlotDetailByRackId(9999);
		Mockito.doReturn(4567).when(vt070000dao).updateSlotDetail(Matchers.any(SlotDetail.class));
		Mockito.doReturn(5678).when(vt070000dao).updateRackDetail(Matchers.any(RackDetail.class));
		Mockito.doReturn(defaultSlot).when(vt070000dao).getSlotById(1984);
		Mockito.doReturn(6789).when(vt070001dao).updateStatusBarcodeDetail(Matchers.any(VT070001EntityBarcodeDetail.class));
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		RackDetail resultRack = result.getRacks().get(0);
		assertEquals("df rack error", resultRack.getError());
	}
	
	@Test
	public void updateWarehouseDetailTestRackType2() {
		defaultRack.setChanged(true);
		defaultSlot.setChanged(true);
		defaultRack.setType(2);
		Mockito.doReturn(defaultSlotList).when(vt070000dao).getListSlotDetailByRackId(9999);
		Mockito.doReturn(4567).when(vt070000dao).updateSlotDetail(Matchers.any(SlotDetail.class));
		Mockito.doReturn(5678).when(vt070000dao).updateRackDetail(Matchers.any(RackDetail.class));
		Mockito.doReturn(defaultSlot).when(vt070000dao).getSlotById(1984);
		Mockito.doReturn(6789).when(vt070001dao).updateStatusBarcodeDetail(Matchers.any(VT070001EntityBarcodeDetail.class));
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		RackDetail resultRack = result.getRacks().get(0);
		assertEquals("df rack error", resultRack.getError());
	}
	
	@Test
	public void updateWarehouseDetailTestRackType0() {
		defaultRack.setChanged(true);
		defaultSlot.setChanged(true);
		defaultRack.setType(0);
		Mockito.doReturn(defaultSlotList).when(vt070000dao).getListSlotDetailByRackId(9999);
		Mockito.doReturn(4567).when(vt070000dao).updateSlotDetail(Matchers.any(SlotDetail.class));
		Mockito.doReturn(5678).when(vt070000dao).updateRackDetail(Matchers.any(RackDetail.class));
		Mockito.doReturn(defaultSlot).when(vt070000dao).getSlotById(1984);
		Mockito.doReturn(6789).when(vt070001dao).updateStatusBarcodeDetail(Matchers.any(VT070001EntityBarcodeDetail.class));
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		RackDetail resultRack = result.getRacks().get(0);
		assertEquals("df rack error", resultRack.getError());
	}
	
	@Test
	public void updateWarehouseDetailTestNoBusySlot() {
		defaultRack.setChanged(true);
		defaultSlot.setChanged(true);
		defaultSlot.setStatus(0);
		Mockito.doReturn(defaultSlotList).when(vt070000dao).getListSlotDetailByRackId(9999);
		Mockito.doReturn(4567).when(vt070000dao).updateSlotDetail(Matchers.any(SlotDetail.class));
		Mockito.doReturn(5678).when(vt070000dao).updateRackDetail(Matchers.any(RackDetail.class));
		Mockito.doReturn(defaultSlot).when(vt070000dao).getSlotById(1984);
		Mockito.doReturn(6789).when(vt070001dao).updateStatusBarcodeDetail(Matchers.any(VT070001EntityBarcodeDetail.class));
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		RackDetail resultRack = result.getRacks().get(0);
		assertEquals("df rack error", resultRack.getError());
		assertEquals(5678, resultRack.getUpdatedNum());
	}
	
	@Test
	public void updateWarehouseDetailTestNullSlot() {
		defaultRack.setChanged(false);
		defaultSlot.setChanged(true);
		defaultRack.setSlots(null);
		Mockito.doReturn(defaultSlotList).when(vt070000dao).getListSlotDetailByRackId(9999);
		Mockito.doReturn(4567).when(vt070000dao).updateSlotDetail(Matchers.any(SlotDetail.class));
		Mockito.doReturn(5678).when(vt070000dao).updateRackDetail(Matchers.any(RackDetail.class));
		Mockito.doReturn(defaultSlot).when(vt070000dao).getSlotById(1984);
		Mockito.doReturn(6789).when(vt070001dao).updateStatusBarcodeDetail(Matchers.any(VT070001EntityBarcodeDetail.class));
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		RackDetail resultRack = result.getRacks().get(0);
		assertEquals(null, resultRack.getSlots());
	}
	
	@Test
	public void updateWarehouseDetailTestSlotAlreadyChanged() {
		defaultRack.setChanged(false);
		defaultSlot.setChanged(false);
		Mockito.doReturn(defaultSlotList).when(vt070000dao).getListSlotDetailByRackId(9999);
		Mockito.doReturn(4567).when(vt070000dao).updateSlotDetail(Matchers.any(SlotDetail.class));
		Mockito.doReturn(5678).when(vt070000dao).updateRackDetail(Matchers.any(RackDetail.class));
		Mockito.doReturn(defaultSlot).when(vt070000dao).getSlotById(1984);
		Mockito.doReturn(6789).when(vt070001dao).updateStatusBarcodeDetail(Matchers.any(VT070001EntityBarcodeDetail.class));
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		RackDetail resultRack = result.getRacks().get(0);
		assertEquals(defaultSlot, resultRack.getSlots().get(0));
	}
	
	@Test
	public void updateWarehouseDetailTestChangeSlotNullBarcode() {
		defaultRack.setChanged(false);
		defaultSlot.setChanged(true);
		Mockito.doReturn(defaultSlot).when(vt070000dao).getSlotById(1984);
		Mockito.doReturn(null).when(vt070001dao).checkBarcodeIsAvail(Matchers.any());
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		SlotDetail resultSlot = result.getRacks().get(0).getSlots().get(0);
		assertEquals("A nother user", resultSlot.getUpdateUser());
	}
	
	@Test
	public void updateWarehouseDetailTestChangeSlotNullSlot() {
		defaultRack.setChanged(false);
		defaultSlot.setChanged(true);
		Mockito.doReturn(null).when(vt070000dao).getSlotById(1984);
		Mockito.doReturn(null).when(vt070001dao).checkBarcodeIsAvail(Matchers.any());
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		SlotDetail resultSlot = result.getRacks().get(0).getSlots().get(0);
		assertEquals("A nother user", resultSlot.getUpdateUser());
	}
	
	@Test
	public void updateWarehouseDetailTestChangeSlotNullQR() {
		defaultRack.setChanged(false);
		defaultSlot.setChanged(true);
		defaultSlot.setQrCode(null);
		Mockito.doReturn(defaultSlot).when(vt070000dao).getSlotById(1984);
		Mockito.doReturn(null).when(vt070001dao).checkBarcodeIsAvail(Matchers.any());
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		SlotDetail resultSlot = result.getRacks().get(0).getSlots().get(0);
		assertEquals("A nother user", resultSlot.getUpdateUser());
	}
	
	@Test
	public void updateWarehouseDetailTestChangeSlotQRExisted() {
		defaultRack.setChanged(false);
		defaultSlot.setChanged(true);
		defaultSlot.setQrCode("abcd");
		Mockito.doReturn(defaultSlot).when(vt070000dao).getSlotById(Matchers.anyInt());
		Mockito.doReturn(null).when(vt070001dao).checkBarcodeIsAvail(Matchers.any());
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		SlotDetail resultSlot = result.getRacks().get(0).getSlots().get(0);
		assertEquals("A nother user", resultSlot.getUpdateUser());
	}
	
	@Test
	public void updateWarehouseDetailTestChangeSlotAvailQR() {
		List<VT070001EntityBarcodeDetail> barcodeList = new ArrayList<VT070001EntityBarcodeDetail>();
		barcodeList.add(defaultBarcode);
		defaultRack.setChanged(false);
		defaultSlot.setChanged(true);
		defaultSlot.setQrCode("abcd");
		Mockito.doReturn(defaultSlot).when(vt070000dao).getSlotById(Matchers.anyInt());
		Mockito.doReturn(barcodeList).when(vt070001dao).checkBarcodeIsAvail(Matchers.any());
		Mockito.doReturn(1234).when(vt070000dao).updateSlotDetail(Matchers.any());
		WarehouseDetail result = (WarehouseDetail) mockService.updateWarehouseDetail(defaultWarehouse, "A nother user");
		SlotDetail resultSlot = result.getRacks().get(0).getSlots().get(0);
		assertEquals(1234, resultSlot.getUpdatedNum());
	}
	
	@Test
	public void getLocationByQrCodeTestNullWarehouseFromQR() {
		RequestParam mockParam = new RequestParam();
		mockParam.setQrCode("abcd");
		mockParam.setPageNo(1);
		Mockito.doReturn(null).when(vt070000dao).getListWarehouseDetailByQrCode(Matchers.anyString());
		Mockito.doReturn(null).when(vt070000dao).getListWarehouse(mockParam);
		Object result = mockService.getLocationByQrCode(mockParam);
		List<WarehouseDetail> resultWarehouses = (List<WarehouseDetail>) BeanUtilities.getObjectFieldValue(result, "warehouses");
		assertEquals(null, resultWarehouses);
	}
	
	@Test
	public void getLocationByQrCodeTest() {
		RequestParam mockParam = new RequestParam();
		mockParam.setQrCode("abcd");
		mockParam.setPageNo(1);
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListWarehouseDetailByQrCode(Matchers.anyString());
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListWarehouse(Matchers.any(RequestParam.class));
		Mockito.doReturn(defaultRackList).when(vt070000dao).getListRackDetailByQrCode(Matchers.anyString());
		Mockito.doReturn(defaultSlotList).when(vt070000dao).getListSlotDetailByQrCode(Matchers.anyString());
		Mockito.doReturn(defaultRackList).when(vt070000dao).getListRackDetailByWarehouseId(Matchers.anyLong());
		Mockito.doReturn(defaultSlotList).when(vt070000dao).getListSlotDetailByRackId(Matchers.anyLong());		
		Object result = mockService.getLocationByQrCode(mockParam);
		List<WarehouseDetail> resultWarehouses = (List<WarehouseDetail>) BeanUtilities.getObjectFieldValue(result, "warehouses");
		assertEquals(null, resultWarehouses);
	}
	
	@Test
	public void getWarehouseDetailTestHasException() {
		WarehouseRequestParam mockParam = new WarehouseRequestParam();
		mockParam.setPageSize(10);
		
		Mockito.doReturn(7).when(vt070000dao).getTotalRecord(mockParam);
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListWareHouseDetail(mockParam);
		
		ResponseEntityBase result = mockService.getWarehouseDetail(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void getWarehouseDetailTestHasException2() {
		WarehouseRequestParam mockParam = new WarehouseRequestParam();
		mockParam.setPageSize(10);
		mockParam.setPageNumber(10);
		Mockito.doReturn(7).when(vt070000dao).getTotalRecord(mockParam);
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListWareHouseDetail(mockParam);
		
		ResponseEntityBase result = mockService.getWarehouseDetail(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void getWarehouseDetailTestHasException3() {
		WarehouseRequestParam mockParam = new WarehouseRequestParam();
		mockParam.setPageSize(0);
		
		Mockito.doReturn(7).when(vt070000dao).getTotalRecord(mockParam);
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListWareHouseDetail(mockParam);
		
		ResponseEntityBase result = mockService.getWarehouseDetail(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void getWarehouseDetailTest() {
		WarehouseRequestParam mockParam = new WarehouseRequestParam();
		mockParam.setPageSize(10);
		
		Mockito.doReturn(7).when(vt070000dao).getTotalRecord(Matchers.any(WarehouseRequestParam.class));
		Mockito.doReturn(new ArrayList<>()).when(vt070000dao).getListWareHouseDetail(Matchers.any(WarehouseRequestParam.class));
		
		ResponseEntityBase result = mockService.getWarehouseDetail(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void getListMainWarehouseDetail() {
		RequestParam mockParam = new RequestParam();
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListMainWarehouseDetail(mockParam);
		Object result = mockService.getListMainWarehouseDetail(mockParam);
		List<WarehouseDetail> resultWhList = (List<WarehouseDetail>) BeanUtilities.getObjectFieldValue(result, "warehouses");
		assertEquals(defaultWarehouseList, resultWhList);
	}
	
	@Test
	public void getListMainWarehouseDetailNullWHL() {
		RequestParam mockParam = new RequestParam();
		Mockito.doReturn(null).when(vt070000dao).getListMainWarehouseDetail(mockParam);
		Object result = mockService.getListMainWarehouseDetail(mockParam);
		List<WarehouseDetail> resultWhList = (List<WarehouseDetail>) BeanUtilities.getObjectFieldValue(result, "warehouses");
		assertEquals(new ArrayList<>(), resultWhList);
	}
	
	@Test
	public void getListMainWarehouseDetailLv1RackNull() {
		RequestParam mockParam = new RequestParam();
		mockParam.setLevel(1);
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListMainWarehouseDetail(mockParam);		
		Mockito.doReturn(null).when(vt070000dao).getListRackDetailByWarehouseId(Matchers.anyLong());
		Object result = mockService.getListMainWarehouseDetail(mockParam);
		List<WarehouseDetail> resultWhList = (List<WarehouseDetail>) BeanUtilities.getObjectFieldValue(result, "warehouses");
		assertEquals(new ArrayList<RackDetail>(), resultWhList.get(0).getRacks());
	}
	
	@Test
	public void getListMainWarehouseDetailLv1RackNotNull() {
		RequestParam mockParam = new RequestParam();
		mockParam.setLevel(1);
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListMainWarehouseDetail(mockParam);
		Mockito.doReturn(defaultRackList).when(vt070000dao).getListRackDetailByWarehouseId(Matchers.anyLong());
		Object result = mockService.getListMainWarehouseDetail(mockParam);
		List<WarehouseDetail> resultWhList = (List<WarehouseDetail>) BeanUtilities.getObjectFieldValue(result, "warehouses");
		assertEquals(defaultRackList, resultWhList.get(0).getRacks());
	}
	
	@Test
	public void getListMainWarehouseDetailLv2RackNotNullSlotNull() {
		RequestParam mockParam = new RequestParam();
		mockParam.setLevel(2);
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListMainWarehouseDetail(mockParam);
		Mockito.doReturn(defaultRackList).when(vt070000dao).getListRackDetailByWarehouseId(Matchers.anyLong());
		Mockito.doReturn(null).when(vt070000dao).getListSlotDetailByRackId(Matchers.anyLong());
		Object result = mockService.getListMainWarehouseDetail(mockParam);
		List<WarehouseDetail> resultWhList = (List<WarehouseDetail>) BeanUtilities.getObjectFieldValue(result, "warehouses");
		assertEquals(new ArrayList<>(), resultWhList.get(0).getRacks().get(0).getSlots());
	}
	
	@Test
	public void getListMainWarehouseDetailLv2RackNotNullSlotNotNull() {
		RequestParam mockParam = new RequestParam();
		mockParam.setLevel(2);
		Mockito.doReturn(defaultWarehouseList).when(vt070000dao).getListMainWarehouseDetail(mockParam);
		Mockito.doReturn(defaultRackList).when(vt070000dao).getListRackDetailByWarehouseId(Matchers.anyLong());
		Mockito.doReturn(defaultSlotList).when(vt070000dao).getListSlotDetailByRackId(Matchers.anyLong());
		Object result = mockService.getListMainWarehouseDetail(mockParam);
		List<WarehouseDetail> resultWhList = (List<WarehouseDetail>) BeanUtilities.getObjectFieldValue(result, "warehouses");
		assertEquals(defaultSlotList, resultWhList.get(0).getRacks().get(0).getSlots());
	}
	
	@Test
	public void updatePrintTimeRackTestInsertSuccess() {
		RackDetail rack = new RackDetail();
		rack.setWarehouseName("2,3");
		Mockito.doReturn(200).when(vt070000dao).updatePrintTimeRack(Matchers.anyList());
		ResponseEntityBase result = mockService.updatePrintTimeRack(rack);
		assertEquals(Constant.REQUEST_ACTION_INSERT, result.getStatus());
	}
	
	@Test
	public void updatePrintTimeRackTestInsertError() {
		RackDetail rack = new RackDetail();
		rack.setWarehouseName("2,3");
		Mockito.doReturn(0).when(vt070000dao).updatePrintTimeRack(Matchers.anyList());
		ResponseEntityBase result = mockService.updatePrintTimeRack(rack);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void updatePrintTimeRackTestInsertException() {
		RackDetail rack = new RackDetail();
		rack.setWarehouseName("abcd,1234");
		Mockito.doReturn(200).when(vt070000dao).updatePrintTimeRack(Matchers.anyList());
		ResponseEntityBase result = mockService.updatePrintTimeRack(rack);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void findRackTest() {
		RackDetail mockParam = new RackDetail();
		Mockito.doReturn(defaultRackList).when(vt070000dao).findRack(mockParam);
		ResponseEntityBase result = mockService.findRack(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void findRackTestHasException() {
		RackDetail mockParam = new RackDetail();
		Mockito.doThrow(new RuntimeException()).when(vt070000dao).findRack(mockParam);
		ResponseEntityBase result = mockService.findRack(mockParam);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test (expected = NullPointerException.class)
	public void writeExcelTestHasException() throws Exception{
		WarehouseRackSlot mockWarehouseRackSlot = new WarehouseRackSlot();
		mockWarehouseRackSlot.setCreateUser("create user");
		mockWarehouseRackSlot.setSlotId("1984");		
		List<WarehouseRackSlot> mockParam1 = new ArrayList<WarehouseRackSlot>();
		mockParam1.add(mockWarehouseRackSlot);
		File result = mockService.writeExcel(mockParam1, "path");
	}
	
	@Test
	public void testWriteExcelObject() {
		WarehouseRackSlot mockWarehouseRackSlot = new WarehouseRackSlot();
		mockWarehouseRackSlot.setCreateUser("create user");
		mockWarehouseRackSlot.setSlotId("1984");
		mockWarehouseRackSlot.setColumn("3");
		mockWarehouseRackSlot.setPageNumber(8);
		mockWarehouseRackSlot.setPageSize(10);
		mockWarehouseRackSlot.setPosition("3/3/3");
		mockWarehouseRackSlot.setQrCode("R100998");
		mockWarehouseRackSlot.setRow("3");
		mockWarehouseRackSlot.setStatus("active");
		mockWarehouseRackSlot.setTotalActive(10);
		mockWarehouseRackSlot.setWarehouseName("mock warehouse");
		assertEquals("create user", mockWarehouseRackSlot.getCreateUser());
		assertEquals("1984", mockWarehouseRackSlot.getSlotId());
		assertEquals("3", mockWarehouseRackSlot.getColumn());
		assertEquals(8, mockWarehouseRackSlot.getPageNumber());
		assertEquals(10, mockWarehouseRackSlot.getPageSize());
		assertEquals("3/3/3", mockWarehouseRackSlot.getPosition());
		assertEquals("R100998", mockWarehouseRackSlot.getQrCode());
		assertEquals("3", mockWarehouseRackSlot.getRow());
		assertEquals("active", mockWarehouseRackSlot.getStatus());
		assertEquals(10, mockWarehouseRackSlot.getTotalActive());
		assertEquals("mock warehouse", mockWarehouseRackSlot.getWarehouseName());
	}
	
	@Test ()
	public void createExcelOutputExcelTestRole1() {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		WarehouseRackSlot mockWarehouseRackSlot = new WarehouseRackSlot();
		List<WarehouseRackSlot> mockList= new ArrayList<WarehouseRackSlot>();
		mockList.add(mockWarehouseRackSlot);
		WarehouseRequestParam mockParam = new WarehouseRequestParam();
		mockParam.setWarehouseId("7777");
		mockParam.setCreateUser("a user");
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		Mockito.doReturn(mockList).when(vt070000dao).getFullListSlotUseInWarehouse(mockParam);
		Mockito.doReturn(3333).when(vt070000dao).countSlotActive("7777");
		File result = mockService.createExcelOutputExcel(mockParam,roleList);
		assertEquals(null, result);
	}
	
	@Test
	public void createExcelOutputExcelTestRole2EmptyWRS() {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		WarehouseRackSlot mockWarehouseRackSlot = new WarehouseRackSlot();
		List<WarehouseRackSlot> mockList= new ArrayList<WarehouseRackSlot>();
		//mockList.add(mockWarehouseRackSlot);
		WarehouseRequestParam mockParam = new WarehouseRequestParam();
		mockParam.setWarehouseId("7777");
		mockParam.setCreateUser("a user");
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_REPORT));
		Mockito.doReturn(mockList).when(vt070000dao).getFullListSlotUseInWarehouse(mockParam);
		Mockito.doReturn(3333).when(vt070000dao).countSlotActive("7777");
		File result = mockService.createExcelOutputExcel(mockParam,roleList);
		assertEquals(null, result);
	}
	
	@Test
	public void createExcelOutputExcelTestRole2NullWRS() {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		WarehouseRackSlot mockWarehouseRackSlot = new WarehouseRackSlot();
		List<WarehouseRackSlot> mockList= new ArrayList<WarehouseRackSlot>();
		//mockList.add(mockWarehouseRackSlot);
		WarehouseRequestParam mockParam = new WarehouseRequestParam();
		mockParam.setWarehouseId("7777");
		mockParam.setCreateUser("a user");
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_REPORT));
		Mockito.doReturn(null).when(vt070000dao).getFullListSlotUseInWarehouse(mockParam);
		Mockito.doReturn(3333).when(vt070000dao).countSlotActive("7777");
		File result = mockService.createExcelOutputExcel(mockParam,roleList);
		assertEquals(null, result);
	}
	
	@Test
	public void createExcelOutputExcelTestNoRole() {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		WarehouseRackSlot mockWarehouseRackSlot = new WarehouseRackSlot();
		List<WarehouseRackSlot> mockList= new ArrayList<WarehouseRackSlot>();
		//mockList.add(mockWarehouseRackSlot);
		WarehouseRequestParam mockParam = new WarehouseRequestParam();
		mockParam.setWarehouseId("7777");
		mockParam.setCreateUser("a user");
		Mockito.doReturn(null).when(vt070000dao).getFullListSlotUseInWarehouse(mockParam);
		Mockito.doReturn(3333).when(vt070000dao).countSlotActive("7777");
		File result = mockService.createExcelOutputExcel(mockParam,roleList);
		assertEquals(null, result);
	}
	
	@Test
	public void getAllListWarehouseDetailTest() {
		WarehouseRequestParam mockParam = new WarehouseRequestParam();
		Mockito.doReturn(null).when(vt070000dao).getAllListWarehouseDetail(mockParam);
		ResponseEntityBase result = mockService.getAllListWarehouseDetail(mockParam);
		
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void getAllListWarehouseDetailTestException() {
		WarehouseRequestParam mockParam = new WarehouseRequestParam();
		Mockito.doThrow(new RuntimeException()).when(vt070000dao).getAllListWarehouseDetail(mockParam);
		ResponseEntityBase result = mockService.getAllListWarehouseDetail(mockParam);		
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void getListSlotOfWarehouseTestNullData() {
		WarehouseRequestParam mockParam = new WarehouseRequestParam();		
		ResponseEntityBase result = mockService.getListSlotOfWarehouse(mockParam, roleList);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void getListSlotOfWarehouseTest() {
		WarehouseRackSlot record = new WarehouseRackSlot();
		List<WarehouseRackSlot> recordList = new ArrayList<WarehouseRackSlot>();
		recordList.add(record);
		WarehouseRequestParam mockParam = new WarehouseRequestParam();		
		Mockito.doReturn(recordList).when(vt070000dao).getListSlotUseInWarehouse(mockParam);
		ResponseEntityBase result = mockService.getListSlotOfWarehouse(mockParam, roleList);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void getListSlotOfWarehouseTestPage0() {
		WarehouseRackSlot record = new WarehouseRackSlot();
		List<WarehouseRackSlot> recordList = new ArrayList<WarehouseRackSlot>();
		recordList.add(record);
		WarehouseRequestParam mockParam = new WarehouseRequestParam();
		mockParam.setPageNumber(0);
		mockParam.setPageSize(10);
		Mockito.doReturn(recordList).when(vt070000dao).getListSlotUseInWarehouse(mockParam);
		ResponseEntityBase result = mockService.getListSlotOfWarehouse(mockParam, roleList);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void getListSlotOfWarehouseTestPage0Size0() {
		WarehouseRackSlot record = new WarehouseRackSlot();
		List<WarehouseRackSlot> recordList = new ArrayList<WarehouseRackSlot>();
		recordList.add(record);
		WarehouseRequestParam mockParam = new WarehouseRequestParam();
		mockParam.setPageNumber(0);
		mockParam.setPageSize(0);
		Mockito.doReturn(recordList).when(vt070000dao).getListSlotUseInWarehouse(mockParam);
		ResponseEntityBase result = mockService.getListSlotOfWarehouse(mockParam, roleList);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void getListSlotOfWarehouseTestPage5SizeNegative() {
		WarehouseRackSlot record = new WarehouseRackSlot();
		List<WarehouseRackSlot> recordList = new ArrayList<WarehouseRackSlot>();
		recordList.add(record);
		WarehouseRequestParam mockParam = new WarehouseRequestParam();
		mockParam.setPageNumber(0);
		mockParam.setPageSize(-10);
		Mockito.doReturn(recordList).when(vt070000dao).getListSlotUseInWarehouse(mockParam);
		ResponseEntityBase result = mockService.getListSlotOfWarehouse(mockParam, roleList);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, result.getStatus());
	}
	
	@Test
	public void getDiagramObjectTest() {
		WarehouseDiagramParam object = new WarehouseDiagramParam();
		object.setColumn(69);
		object.setRow(69);
		object.setType(69);
		object.setNumberPerSlot("69");
		assertEquals(69, object.getColumn());
		assertEquals(69, object.getRow());
		assertEquals(69, object.getType());
		assertEquals("69", object.getNumberPerSlot());
	}
	
	@Test
	public void getDiagramTestEmptyWarehouseId(){
		ResponseEntityBase result = mockService.getDiagram("", roleList);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void getDiagramTestNullWarehouseId(){
		ResponseEntityBase result = mockService.getDiagram(null, roleList);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void getDiagramTestEmptyRoleList(){
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();	
		ResponseEntityBase result = mockService.getDiagram("7777", roleList);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void getDiagramTestNotEmptyRoleList(){
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();	
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP));
		ResponseEntityBase result = mockService.getDiagram("7777", roleList);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void getDiagramTestHasException(){
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();		
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		Mockito.doThrow(new RuntimeException()).when(vt070000dao).getWarehouseDetailById("7777");
		ResponseEntityBase result = mockService.getDiagram("7777", roleList);		
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void getDiagramTestColNotEqual(){
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();		
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		Mockito.doReturn(defaultWarehouseReqParam).when(vt070000dao).getWarehouseDetailById("7777");
		Mockito.doReturn(defaultRackList).when(vt070000dao).getListRackDetailByWarehouseId(7777);
		ResponseEntityBase result = mockService.getDiagram("7777", roleList);		
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void getDiagramTestColEqual(){
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();		
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		defaultWarehouseReqParam.setColumnNum("1");
		Mockito.doReturn(defaultWarehouseReqParam).when(vt070000dao).getWarehouseDetailById("7777");
		Mockito.doReturn(defaultRackList).when(vt070000dao).getListRackDetailByWarehouseId(7777);
		Mockito.doReturn(1).when(vt070000dao).countSlotUse(Matchers.anyLong());
		ResponseEntityBase result = mockService.getDiagram("7777", roleList);		
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void deleteWarehouseTestEmtpyRoleList() {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		ResponseEntityBase result = mockService.deleteWarehouse(defaultWarehouseReqParam,roleList);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void deleteWarehouseTestWrongRole() {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP));
		ResponseEntityBase result = mockService.deleteWarehouse(defaultWarehouseReqParam,roleList);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void deleteWarehouseTestHasException() {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		Mockito.doThrow(new RuntimeException()).when(vt070000dao).isDelete("7777");
		ResponseEntityBase result = mockService.deleteWarehouse(defaultWarehouseReqParam,roleList);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void deleteWarehouseTestNullId() {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		defaultWarehouseReqParam.setWarehouseId(null);
		ResponseEntityBase result = mockService.deleteWarehouse(defaultWarehouseReqParam,roleList);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void deleteWarehouseTestAlreadyDelete() {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		Mockito.doReturn(1).when(vt070000dao).isDelete("7777");
		ResponseEntityBase result = mockService.deleteWarehouse(defaultWarehouseReqParam,roleList);
		assertEquals(Constant.ERROR_UPDATE, result.getStatus());
	}
	
	@Test
	public void deleteWarehouseTestNotYetDeletedSlotUseNotZero() {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		Mockito.doReturn(0).when(vt070000dao).isDelete("7777");
		Mockito.doReturn(defaultWarehouseReqParam).when(vt070000dao).getWarehouseDetailById("7777");
		ResponseEntityBase result = mockService.deleteWarehouse(defaultWarehouseReqParam,roleList);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void deleteWarehouseTestChkSlotHasException() {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		Mockito.doReturn(0).when(vt070000dao).isDelete("7777");
		Mockito.doReturn(defaultWarehouseReqParam).when(vt070000dao).getWarehouseDetailById("7777");
		Mockito.doThrow(new RuntimeException()).when(vt070000dao).getListSlotWithRowColumn(Matchers.anyLong(), Matchers.anyInt(), Matchers.anyInt(), Matchers.anyInt(), Matchers.anyInt());
		ResponseEntityBase result = mockService.deleteWarehouse(defaultWarehouseReqParam,roleList);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, result.getStatus());
	}
	
	@Test
	public void deleteWarehouseTestNotYetDeletedSlotUseEqualZero() {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		Mockito.doReturn(0).when(vt070000dao).isDelete("7777");
		Mockito.doReturn(defaultWarehouseReqParam).when(vt070000dao).getWarehouseDetailById("7777");
		Mockito.doReturn(defaultSlotList).when(vt070000dao).getListSlotWithRowColumn(Matchers.anyLong(), Matchers.anyInt(), Matchers.anyInt(), Matchers.anyInt(), Matchers.anyInt());
		ResponseEntityBase result = mockService.deleteWarehouse(defaultWarehouseReqParam,roleList);
		assertEquals(5, result.getStatus());
	}
	
	@Test
	public void deleteWarehouseTestDeleted() {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		Collection<GrantedAuthority> roleList = oauth.getAuthorities();
		roleList.add(new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE));
		Mockito.doReturn(0).when(vt070000dao).isDelete("7777");
		Mockito.doReturn(defaultWarehouseReqParam).when(vt070000dao).getWarehouseDetailById("7777");
		Mockito.doReturn(null).when(vt070000dao).getListSlotWithRowColumn(Matchers.anyLong(), Matchers.anyInt(), Matchers.anyInt(), Matchers.anyInt(), Matchers.anyInt());
		Mockito.doReturn(1).when(vt070000dao).deleteWarehouse(defaultWarehouseReqParam);
		ResponseEntityBase result = mockService.deleteWarehouse(defaultWarehouseReqParam,roleList);
		assertEquals(Constant.REQUEST_ACTION_DELETE, result.getStatus());
	}
	
	@Test
	public void addSlotFromOldToNewTestNoAdd() {
		int result = mockService.addSlotFromOldToNew(2, 5, 5, "User");
		assertEquals(0, result);
	}
	
	@Test
	public void addSlotFromOldToNewTestAdded() {
		Mockito.doReturn(1).when(vt070000dao).insertSlot(Matchers.any(SlotDetail.class));
		int result = mockService.addSlotFromOldToNew(2, 3, 5, "User");		
		assertEquals(1, result);
	}
	
	@Test
	public void addRemainSlotTestNoAdd() {
		int result = mockService.addRemainSlot(2, 5, 5, "User");
		assertEquals(0, result);
	}
	
	@Test
	public void addRemainSlotTestAdded() {
		Mockito.doReturn(1).when(vt070000dao).insertSlot(Matchers.any(SlotDetail.class));
		int result = mockService.addRemainSlot(2, 3, 5, "User");		
		assertEquals(1, result);
	}
	
	@Test
	public void addSlotTestNoAdd() {
		int result = mockService.addSlot(2, 0, "User");
		assertEquals(0, result);
	}
	
	@Test
	public void addSlotTestAdded() {
		Mockito.doReturn(1).when(vt070000dao).insertSlot(Matchers.any(SlotDetail.class));
		int result = mockService.addSlot(2, 1, "User");		
		assertEquals(1, result);
	}
	
	@Test
	public void addRackTestNoAdd() {
		int result = mockService.addRack(2, 0, 0, 0, "User");
		assertEquals(0, result);
	}
	
	@Test
	public void addRackTestAddedHasRowEffected() {
		Mockito.doReturn(6).when(vt070000dao).insertRack(Matchers.any(RackDetail.class));
		int result = mockService.addRack(2, 1, 1, 1, "User");
		assertEquals(6, result);
	}
	
	@Test
	public void addRackTestAddedNoRowEffected() {
		Mockito.doReturn(0).when(vt070000dao).insertRack(Matchers.any(RackDetail.class));
		int result = mockService.addRack(2, 1, 1, 1, "User");
		assertEquals(0, result);
	}
	
	@Test
	public void deleteSlotHeightWithRowColumnTestNoDel() {
		int result = mockService.deleteSlotHeightWithRowColumn(1, 2, 2, "User");
		assertEquals(0, result);
	}
	
	@Test
	public void deleteSlotHeightWithRowColumnTestDeled() {
		Mockito.doReturn(8).when(vt070000dao).deleteSlotHeightWithRackId(Matchers.anyLong(), Matchers.anyInt());
		int result = mockService.deleteSlotHeightWithRowColumn(1, 5, 2, "User");
		assertEquals(8, result);
	}
	
	@Test
	public void deleteSlotHeightWithRowColumnTestHasExcdeption() {
		Mockito.doThrow(new RuntimeException()).when(vt070000dao).deleteSlotHeightWithRackId(Matchers.anyLong(), Matchers.anyInt());
		int result = mockService.deleteSlotHeightWithRowColumn(1, 5, 2, "User");
		assertEquals(0, result);
	}
	
	@Test
	public void deleteRackWithRowColumnTestNoDel() {
		int result = mockService.deleteRackWithRowColumn(7777, 0, 0, 0, 0, "User");
		assertEquals(0, result);
	}
	
	@Test
	public void deleteRackWithRowColumnTestHasException() {
		Mockito.doThrow(new RuntimeException()).when(vt070000dao).deleteRackWithRowColumn(Matchers.anyLong(), Matchers.anyInt(), Matchers.anyInt());
		int result = mockService.deleteRackWithRowColumn(7777, 3, 2, 4, 3, "User");
		assertEquals(0, result);
	}
	
	@Test
	public void deleteRackWithRowColumnTestDeleted() {
		Mockito.doReturn(22).when(vt070000dao).deleteRackWithRowColumn(Matchers.anyLong(), Matchers.anyInt(), Matchers.anyInt());
		int result = mockService.deleteRackWithRowColumn(7777, 3, 2, 4, 3, "User");
		assertEquals(22, result);
	}
	
	@Test
	public void addRackWithColumnDeleteRowTestNoAdd() {
		int result = mockService.addRackWithColumnDeleteRow(7777, 0, 0, 0, 0, 0, "User");
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithColumnDeleteRowTestHasException() {
		Mockito.doThrow(new RuntimeException()).when(vt070000dao).insertRack(Matchers.any(RackDetail.class));
		int result = mockService.addRackWithColumnDeleteRow(7777, 2, 2, 2, 5, 5, "User");
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithColumnDeleteRowTestNoRowEff() {
		Mockito.doReturn(0).when(vt070000dao).insertRack(Matchers.any(RackDetail.class));
		int result = mockService.addRackWithColumnDeleteRow(7777, 2, 2, 2, 5, 5, "User");
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithColumnDeleteRowTest1RowEff() {
		Mockito.doReturn(55).when(vt070000dao).insertRack(Matchers.any(RackDetail.class));
		int result = mockService.addRackWithColumnDeleteRow(7777, 2, 2, 2, 5, 5, "User");
		assertEquals(55, result);
	}
	
	@Test
	public void addRackWithRowDeleteColumnTestNoDel() {
		int result = mockService.addRackWithRowDeleteColumn(7777, 0, 0, 0, 0, 0, "user");
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithRowDeleteColumnTestHasException() {
		Mockito.doThrow(new RuntimeException()).when(vt070000dao).insertRack(Matchers.any(RackDetail.class));
		int result = mockService.addRackWithRowDeleteColumn(7777, 2, 4, 2, 5, 5, "user");
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithRowDeleteColumnTestNoRowEff() {
		Mockito.doReturn(0).when(vt070000dao).insertRack(Matchers.any(RackDetail.class));
		int result = mockService.addRackWithRowDeleteColumn(7777, 2, 4, 2, 5, 5, "User");
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithRowDeleteColumnTest141RowEff() {
		Mockito.doReturn(141).when(vt070000dao).insertRack(Matchers.any(RackDetail.class));
		int result = mockService.addRackWithRowDeleteColumn(7777, 2, 4, 2, 5, 5, "User");
		assertEquals(141, result);
	}
	
	@Test
	public void addRackWithRowColumnTestNoAdd() {
		int result = mockService.addRackWithRowColumn(7777, 0, 0, 0, 0, 0, 0, "user");
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithRowColumnTestHasException() {
		Mockito.doThrow(new RuntimeException()).when(vt070000dao).insertRack(Matchers.any(RackDetail.class));
		int result = mockService.addRackWithRowColumn(7777, 2, 2, 2, 4, 5, 5, "user");
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithRowColumnTestNoRowEff() {
		Mockito.doReturn(0).when(vt070000dao).insertRack(Matchers.any(RackDetail.class));
		int result = mockService.addRackWithRowColumn(7777, 2, 4, 2, 4, 5, 5, "user");
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithRowColumnTest13RowEff() {
		Mockito.doReturn(13).when(vt070000dao).insertRack(Matchers.any(RackDetail.class));
		int result = mockService.addRackWithRowColumn(7777, 2, 4, 2, 4, 5, 5, "user");
		assertEquals(13, result);
	}
	
	@Test
	public void addRackWithConditionTestNoAdd() throws Exception{
		int result = mockService.addRackWithCondition(defaultWarehouseReqParam, defaultWarehouseReqParam);
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithConditionTestAddHigherHeigtAndRowNumNullRack() throws Exception{
		WarehouseRequestParam newWarehouseRequestParam = new WarehouseRequestParam();;
		newWarehouseRequestParam.setHeightNum("9");
		newWarehouseRequestParam.setRowNum("99");
		newWarehouseRequestParam.setColumnNum("99");
		newWarehouseRequestParam.setWarehouseId("7777");
		Mockito.doReturn(null).when(vt070000dao).getRackWithRowColumnOfWarehouse(Matchers.anyString(), Matchers.anyInt(), Matchers.anyInt());
		int result = mockService.addRackWithCondition(newWarehouseRequestParam, defaultWarehouseReqParam);
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithConditionTestAddHeigtAndRowNum() throws Exception{
		WarehouseRequestParam newWarehouseRequestParam = new WarehouseRequestParam();;
		newWarehouseRequestParam.setHeightNum("9");
		newWarehouseRequestParam.setRowNum("99");
		newWarehouseRequestParam.setColumnNum("99");
		newWarehouseRequestParam.setWarehouseId("7777");
		Mockito.doReturn(defaultRack).when(vt070000dao).getRackWithRowColumnOfWarehouse(Matchers.anyString(), Matchers.anyInt(), Matchers.anyInt());
		int result = mockService.addRackWithCondition(newWarehouseRequestParam, defaultWarehouseReqParam);
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithConditionTestAddLowerHeigtAndRowNumNullRack() throws Exception{
		WarehouseRequestParam newWarehouseRequestParam = new WarehouseRequestParam();;
		newWarehouseRequestParam.setHeightNum("3");
		newWarehouseRequestParam.setRowNum("33");
		newWarehouseRequestParam.setColumnNum("99");
		newWarehouseRequestParam.setWarehouseId("7777");
		Mockito.doReturn(null).when(vt070000dao).getRackWithRowColumnOfWarehouse(Matchers.anyString(), Matchers.anyInt(), Matchers.anyInt());
		int result = mockService.addRackWithCondition(newWarehouseRequestParam, defaultWarehouseReqParam);
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithConditionTestAddLowerHeigtAndRowNum() throws Exception{
		WarehouseRequestParam newWarehouseRequestParam = new WarehouseRequestParam();;
		newWarehouseRequestParam.setHeightNum("3");
		newWarehouseRequestParam.setRowNum("33");
		newWarehouseRequestParam.setColumnNum("99");
		newWarehouseRequestParam.setWarehouseId("7777");
		Mockito.doReturn(defaultRack).when(vt070000dao).getRackWithRowColumnOfWarehouse(Matchers.anyString(), Matchers.anyInt(), Matchers.anyInt());
		int result = mockService.addRackWithCondition(newWarehouseRequestParam, defaultWarehouseReqParam);
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithConditionTestAddLowerHeigtHigerRowAndLowerColumn() throws Exception{
		WarehouseRequestParam newWarehouseRequestParam = new WarehouseRequestParam();;
		newWarehouseRequestParam.setHeightNum("3");
		newWarehouseRequestParam.setRowNum("99");
		newWarehouseRequestParam.setColumnNum("33");
		newWarehouseRequestParam.setWarehouseId("7777");
		Mockito.doReturn(defaultRack).when(vt070000dao).getRackWithRowColumnOfWarehouse(Matchers.anyString(), Matchers.anyInt(), Matchers.anyInt());
		int result = mockService.addRackWithCondition(newWarehouseRequestParam, defaultWarehouseReqParam);
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithConditionTestAddLowerHeigtHigerRowAndEqualColumn() throws Exception{
		WarehouseRequestParam newWarehouseRequestParam = new WarehouseRequestParam();;
		newWarehouseRequestParam.setHeightNum("3");
		newWarehouseRequestParam.setRowNum("99");
		newWarehouseRequestParam.setColumnNum("69");
		newWarehouseRequestParam.setWarehouseId("7777");
		Mockito.doReturn(defaultRack).when(vt070000dao).getRackWithRowColumnOfWarehouse(Matchers.anyString(), Matchers.anyInt(), Matchers.anyInt());
		int result = mockService.addRackWithCondition(newWarehouseRequestParam, defaultWarehouseReqParam);
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithConditionTestAddLowerHeigtRowAndColumn() throws Exception{
		WarehouseRequestParam newWarehouseRequestParam = new WarehouseRequestParam();;
		newWarehouseRequestParam.setHeightNum("3");
		newWarehouseRequestParam.setRowNum("33");
		newWarehouseRequestParam.setColumnNum("33");
		newWarehouseRequestParam.setWarehouseId("7777");
		Mockito.doReturn(defaultRack).when(vt070000dao).getRackWithRowColumnOfWarehouse(Matchers.anyString(), Matchers.anyInt(), Matchers.anyInt());
		int result = mockService.addRackWithCondition(newWarehouseRequestParam, defaultWarehouseReqParam);
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithConditionTestAddLowerHeigtEqualRowLowerColumn() throws Exception{
		WarehouseRequestParam newWarehouseRequestParam = new WarehouseRequestParam();;
		newWarehouseRequestParam.setHeightNum("3");
		newWarehouseRequestParam.setRowNum("68");
		newWarehouseRequestParam.setColumnNum("33");
		newWarehouseRequestParam.setWarehouseId("7777");
		Mockito.doReturn(defaultRack).when(vt070000dao).getRackWithRowColumnOfWarehouse(Matchers.anyString(), Matchers.anyInt(), Matchers.anyInt());
		int result = mockService.addRackWithCondition(newWarehouseRequestParam, defaultWarehouseReqParam);
		assertEquals(0, result);
	}
	
	@Test
	public void addRackWithConditionTestAddLowerHeigtEqualRowHigherColumn() throws Exception{
		WarehouseRequestParam newWarehouseRequestParam = new WarehouseRequestParam();;
		newWarehouseRequestParam.setHeightNum("3");
		newWarehouseRequestParam.setRowNum("68");
		newWarehouseRequestParam.setColumnNum("99");
		newWarehouseRequestParam.setWarehouseId("7777");
		Mockito.doReturn(defaultRack).when(vt070000dao).getRackWithRowColumnOfWarehouse(Matchers.anyString(), Matchers.anyInt(), Matchers.anyInt());
		int result = mockService.addRackWithCondition(newWarehouseRequestParam, defaultWarehouseReqParam);
		assertEquals(0, result);
	}
}

