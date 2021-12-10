package vt70001.service;

import static org.junit.Assert.assertEquals;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070001.dao.VT070001DAO;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeDetail;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodePrefix;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeRange;
import com.viettel.vtnet360.vt07.vt070001.service.VT070001ServiceImpl;

import misc.BeanUtilities;

public class VT070001ServiceTest {
	
	VT070001EntityBarcodePrefix defaultMockBarcodePrefix = new VT070001EntityBarcodePrefix();
	VT070001EntityBarcodeRange defaultMockBarcodeRange = new VT070001EntityBarcodeRange();
	VT070001EntityBarcodeDetail defaultBarcode = new VT070001EntityBarcodeDetail();
	List<VT070001EntityBarcodePrefix> defaultMockBarCodePrefixList = new ArrayList<VT070001EntityBarcodePrefix>();
	List<VT070001EntityBarcodeRange> defaultMockBarCodeRangeList = new ArrayList<VT070001EntityBarcodeRange>();
	List<VT070001EntityBarcodeDetail> defaultMockBarCodeList = new ArrayList<VT070001EntityBarcodeDetail>();
	Date createDate;
	Date updateDate;
	@InjectMocks
	private VT070001ServiceImpl mockService;
	
	@Spy
	VT070001DAO vt070001dao;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		defaultBarcode.setBarCodeDetailId(9999);
		defaultBarcode.setBarCodeRangeId(8888);
		defaultBarcode.setCode("A bar code");
		defaultBarcode.setStatus(7777);
		defaultBarcode.setPrinted(false);
		defaultBarcode.setError("No error");
		defaultMockBarCodeList.add(defaultBarcode);
		
		defaultMockBarcodePrefix.setBarCodePrefixId(999);
		defaultMockBarcodePrefix.setPrefix("Test prefix");
		defaultMockBarcodePrefix.setName("Test name");
		defaultMockBarcodePrefix.setDescription("Test desc");
		defaultMockBarcodePrefix.setStatus(888);
		defaultMockBarCodePrefixList.add(defaultMockBarcodePrefix);
		
		defaultMockBarcodeRange.setBarCodeRangeId(777);
		defaultMockBarcodeRange.setBarCodePrefixId(666);
		defaultMockBarcodeRange.setPrefix("prefix");
		defaultMockBarcodeRange.setStatus(555);
		defaultMockBarcodeRange.setPrinted(true);
		defaultMockBarcodeRange.setQuantity(1);
		defaultMockBarcodeRange.setDescription("description");
		defaultMockBarcodeRange.setPageNumber(333);
		defaultMockBarcodeRange.setPageSize(222);
		defaultMockBarcodeRange.setTotalRecords(111);
		defaultMockBarcodeRange.setCreateUser("Mock user");
		createDate = new Date();
		defaultMockBarcodeRange.setCreateDate(createDate);
		updateDate = new Date();
		defaultMockBarcodeRange.setUpdateDate(updateDate);
		
		defaultMockBarCodeRangeList.add(defaultMockBarcodeRange);
	}	
	
	@Test
	public void findTypeBarcodeTest() {
		Mockito.doReturn(defaultMockBarCodePrefixList).when(vt070001dao).findTypeBarcode(defaultMockBarcodePrefix);
		ResponseEntityBase response = mockService.findTypeBarcode(defaultMockBarcodePrefix);
		List<VT070001EntityBarcodePrefix> responseData = (List<VT070001EntityBarcodePrefix>) response.getData();
		VT070001EntityBarcodePrefix responseRecord = responseData.get(0);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, response.getStatus());
		assertEquals(999, responseRecord.getBarCodePrefixId());
		assertEquals("Test prefix", responseRecord.getPrefix());	
		assertEquals("Test name", responseRecord.getName());	
		assertEquals("Test desc", responseRecord.getDescription());
		assertEquals(888, responseRecord.getStatus());
	}
	
	@Test
	public void findTypeBarcodeTestHasException() {
		Mockito.doThrow(new RuntimeException()).when(vt070001dao).findTypeBarcode(defaultMockBarcodePrefix);
		ResponseEntityBase response = mockService.findTypeBarcode(defaultMockBarcodePrefix);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, response.getStatus());
	}
	
	@Test
	public void insertBarcodeRangeTest_InsertSuccess() {
		Mockito.doReturn(1).when(vt070001dao).insertBarcodeRange(defaultMockBarcodeRange);
		Mockito.doReturn(defaultMockBarcodeRange).when(vt070001dao).getBarcodeRangeLastItem(777);
		Mockito.doReturn(6969).when(vt070001dao).getBarcodeDetailSequence();
		Mockito.doReturn(1).when(vt070001dao).insertBarcodeDetail(Matchers.any(VT070001EntityBarcodeDetail.class));
		ResponseEntityBase response = mockService.insertBarcodeRange(defaultMockBarcodeRange);
		assertEquals(Constant.REQUEST_ACTION_INSERT, response.getStatus());
	}
	
	@Test
	public void insertBarcodeRangeTest_InsertFailed() {
		Mockito.doReturn(-1).when(vt070001dao).insertBarcodeRange(defaultMockBarcodeRange);
		Mockito.doReturn(defaultMockBarcodeRange).when(vt070001dao).getBarcodeRangeLastItem(777);
		Mockito.doReturn(6969).when(vt070001dao).getBarcodeDetailSequence();
		Mockito.doReturn(-1).when(vt070001dao).insertBarcodeDetail(Matchers.any(VT070001EntityBarcodeDetail.class));
		ResponseEntityBase response = mockService.insertBarcodeRange(defaultMockBarcodeRange);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, response.getStatus());
	}
	
	@Test
	public void insertBarcodeRangeTest_NullDesc() {
		defaultMockBarcodeRange.setDescription(null);
		Mockito.doReturn(1).when(vt070001dao).insertBarcodeRange(defaultMockBarcodeRange);
		Mockito.doReturn(defaultMockBarcodeRange).when(vt070001dao).getBarcodeRangeLastItem(777);
		Mockito.doReturn(6969).when(vt070001dao).getBarcodeDetailSequence();
		Mockito.doReturn(1).when(vt070001dao).insertBarcodeDetail(Matchers.any(VT070001EntityBarcodeDetail.class));
		ResponseEntityBase response = mockService.insertBarcodeRange(defaultMockBarcodeRange);
		assertEquals(Constant.REQUEST_ACTION_INSERT, response.getStatus());
	}
	
	@Test
	public void insertBarcodeRangeTest_HasException() {
		Mockito.doThrow(new RuntimeException()).when(vt070001dao).insertBarcodeRange(defaultMockBarcodeRange);
		ResponseEntityBase response = mockService.insertBarcodeRange(defaultMockBarcodeRange);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, response.getStatus());
	}
	
	@Test
	public void findBarcodeRangeTest() {
		Mockito.doReturn(123).when(vt070001dao).getTotalRecord(defaultMockBarcodeRange);
		Mockito.doReturn(defaultMockBarCodeRangeList).when(vt070001dao).findBarcodeRange(defaultMockBarcodeRange);
		ResponseEntityBase response = mockService.findBarcodeRange(defaultMockBarcodeRange);
		List<VT070001EntityBarcodeRange> responseData = (List<VT070001EntityBarcodeRange>) response.getData();
		VT070001EntityBarcodeRange responseRecord = responseData.get(0);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, response.getStatus());
		assertEquals(777, responseRecord.getBarCodeRangeId());
		assertEquals(666, responseRecord.getBarCodePrefixId());
		assertEquals("prefix", responseRecord.getPrefix());
		assertEquals(555, responseRecord.getStatus());
		assertEquals(true, responseRecord.isPrinted());
		assertEquals(1, responseRecord.getQuantity());
		assertEquals("description", responseRecord.getDescription());
		assertEquals(333, responseRecord.getPageNumber());
		assertEquals(222, responseRecord.getPageSize());
		assertEquals(123, responseRecord.getTotalRecords());
		assertEquals(createDate, responseRecord.getCreateDate());
		assertEquals(updateDate, responseRecord.getUpdateDate());
	}
	
	@Test
	public void findBarcodeRangeTestNullDesc() {
		defaultMockBarcodeRange.setDescription(null);
		Mockito.doReturn(123).when(vt070001dao).getTotalRecord(defaultMockBarcodeRange);
		Mockito.doReturn(defaultMockBarCodeRangeList).when(vt070001dao).findBarcodeRange(defaultMockBarcodeRange);
		ResponseEntityBase response = mockService.findBarcodeRange(defaultMockBarcodeRange);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, response.getStatus());
	}
	
	@Test
	public void findBarcodeRangeTestDescTooLong() {
		defaultMockBarcodeRange.setDescription(BeanUtilities.randomString(1000));
		Mockito.doReturn(123).when(vt070001dao).getTotalRecord(defaultMockBarcodeRange);
		Mockito.doReturn(defaultMockBarCodeRangeList).when(vt070001dao).findBarcodeRange(defaultMockBarcodeRange);
		ResponseEntityBase response = mockService.findBarcodeRange(defaultMockBarcodeRange);
		List<VT070001EntityBarcodeRange> responseData = (List<VT070001EntityBarcodeRange>) response.getData();
		VT070001EntityBarcodeRange responseRecord = responseData.get(0);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, response.getStatus());
		assertEquals(255, responseRecord.getDescription().length());
	}
	
	@Test
	public void findBarcodeRangeTestNoData() {
		defaultMockBarcodeRange.setDescription(null);
		Mockito.doReturn(123).when(vt070001dao).getTotalRecord(defaultMockBarcodeRange);
		Mockito.doReturn(new ArrayList<VT070001EntityBarcodeRange>()).when(vt070001dao).findBarcodeRange(defaultMockBarcodeRange);
		ResponseEntityBase response = mockService.findBarcodeRange(defaultMockBarcodeRange);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, response.getStatus());
	}
	
	@Test
	public void findBarcodeRangeTestChangeDataSize() {
		defaultMockBarcodeRange.setPageNumber(0);
		Mockito.doReturn(123).when(vt070001dao).getTotalRecord(defaultMockBarcodeRange);
		Mockito.doReturn(defaultMockBarCodeRangeList).when(vt070001dao).findBarcodeRange(defaultMockBarcodeRange);
		ResponseEntityBase response = mockService.findBarcodeRange(defaultMockBarcodeRange);
		List<VT070001EntityBarcodeRange> responseData = (List<VT070001EntityBarcodeRange>) response.getData();
		VT070001EntityBarcodeRange responseRecord = responseData.get(0);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, response.getStatus());
		assertEquals(1, responseRecord.getTotalRecords());
	}
	
	@Test
	public void findBarcodeRangeTestHasException() {
		Mockito.doThrow(new RuntimeException()).when(vt070001dao).getTotalRecord(defaultMockBarcodeRange);;
		ResponseEntityBase response = mockService.findBarcodeRange(defaultMockBarcodeRange);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, response.getStatus());
	}
	
	@Test
	public void findBarcodeDetailTest() {
		Mockito.doReturn(defaultMockBarCodeRangeList).when(vt070001dao).findBarcodeDetail(777);
		ResponseEntityBase response = mockService.findBarcodeDetail(defaultMockBarcodeRange);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, response.getStatus());
	}
	
	@Test
	public void findBarcodeDetailTestHasException() {
		Mockito.doThrow(new RuntimeException()).when(vt070001dao).findBarcodeDetail(777);
		ResponseEntityBase response = mockService.findBarcodeDetail(defaultMockBarcodeRange);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, response.getStatus());
	}
	
	@Test 
	public void changeBarcodeRangeDetailTestUpdateSucc() {
		Mockito.doReturn(1).when(vt070001dao).updateBarcodeRange(777);
		ResponseEntityBase response = mockService.changeBarcodeRangeDetail(defaultMockBarcodeRange);
		assertEquals(Constant.REQUEST_ACTION_INSERT, response.getStatus());
	}
	
	@Test 
	public void changeBarcodeRangeDetailTestUpdateFail() {
		Mockito.doReturn(0).when(vt070001dao).updateBarcodeRange(777);
		ResponseEntityBase response = mockService.changeBarcodeRangeDetail(defaultMockBarcodeRange);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, response.getStatus());
	}
	
	@Test 
	public void changeBarcodeRangeDetailTestHasException() {
		Mockito.doThrow(new RuntimeException()).when(vt070001dao).updateBarcodeRange(777);
		ResponseEntityBase response = mockService.changeBarcodeRangeDetail(defaultMockBarcodeRange);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, response.getStatus());
	}
	
	@Test 
	public void checkChangeBarcodeRangeTest_AlreadyPrinted() {
		VT070001EntityBarcodeRange result = new VT070001EntityBarcodeRange();
		result.setPrinted(true);
		Mockito.doReturn(result).when(vt070001dao).checkChangeBarcodeRange(777);
		ResponseEntityBase response = mockService.checkChangeBarcodeRange(defaultMockBarcodeRange);
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, response.getStatus());
	}
	
	@Test 
	public void checkChangeBarcodeRangeTest_NotPrinted() {
		VT070001EntityBarcodeRange result = new VT070001EntityBarcodeRange();
		result.setPrinted(false);
		Mockito.doReturn(result).when(vt070001dao).checkChangeBarcodeRange(777);
		ResponseEntityBase response = mockService.checkChangeBarcodeRange(defaultMockBarcodeRange);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, response.getStatus());
	}
	
	@Test 
	public void checkChangeBarcodeRangeTest_HasException() {
		Mockito.doThrow(new RuntimeException()).when(vt070001dao).checkChangeBarcodeRange(777);
		ResponseEntityBase response = mockService.checkChangeBarcodeRange(defaultMockBarcodeRange);
		assertEquals(Constant.RESPONSE_STATUS_ERROR, response.getStatus());
	}
	
	@Test
	public void checkBarcodeAvailableTest() {
		Mockito.doReturn(defaultMockBarCodeList).when(vt070001dao).checkBarcodeIsAvail(defaultBarcode);
		ResponseEntityBase response = mockService.checkBarcodeAvailable(defaultBarcode);
		VT070001EntityBarcodeDetail responseRecord = (VT070001EntityBarcodeDetail) response.getData();
		assertEquals(Constant.RESPONSE_STATUS_SUCCESS, response.getStatus());
		assertEquals(9999, responseRecord.getBarCodeDetailId());
		assertEquals(8888, responseRecord.getBarCodeRangeId());
		assertEquals("A bar code", responseRecord.getCode());
		assertEquals(7777, responseRecord.getStatus());
		assertEquals(false, responseRecord.isPrinted());
		assertEquals("No error", responseRecord.getError());
	}
	
	@Test
	public void checkBarcodeAvailableTest_NullBarcode() {
		Mockito.doReturn(null).when(vt070001dao).checkBarcodeIsAvail(defaultBarcode);
		ResponseEntityBase response = mockService.checkBarcodeAvailable(defaultBarcode);
		VT070001EntityBarcodeDetail responseRecord = (VT070001EntityBarcodeDetail) response.getData();
		assertEquals(null, response.getData());
	}
	
	@Test
	public void checkBarcodeAvailableTest_HasException() {
		Mockito.doThrow(new RuntimeException()).when(vt070001dao).checkBarcodeIsAvail(defaultBarcode);
		ResponseEntityBase response = mockService.checkBarcodeAvailable(defaultBarcode);
		assertEquals(null, response.getData());
	}
}
