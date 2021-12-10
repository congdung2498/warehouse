package vt70001.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070001.controller.VT070001Controller;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeDetail;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodePrefix;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeRange;
import com.viettel.vtnet360.vt07.vt070001.service.VT070001Service;

import misc.MockPrincipal;

public class VT070001ControllerTest {
	private static final String ENCRYPTED_USERNAME = "a37d7e12b490d7000dcc6fa0fd7066c2";
	private static final String ENCRYPTED_PASSWORD = "c9a0172b171314c3eb811606a8d1528b";
	private String defaultUsername = "username";
	@InjectMocks
	private VT070001Controller mockController;	
	
	@Spy
	private Properties configProperty;
	
	@Spy
	VT070001Service vt070001Service;
	
	
    private OAuth2Authentication principal;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.doReturn(ENCRYPTED_USERNAME).when(configProperty).getProperty("SECURITY_USERNAME");
    	Mockito.doReturn(ENCRYPTED_PASSWORD).when(configProperty).getProperty("SECURITY_PASSWORD");
    	MockPrincipal mockUser = new MockPrincipal();
    	principal = new OAuth2Authentication(null, mockUser);
	}
	
	@Test
	public void findPlaceAuthenFail() throws Exception{
		VT070001EntityBarcodePrefix mockRequestParam = new VT070001EntityBarcodePrefix();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);		
		response = mockController.findPlace(mockRequestParam);		
		assertEquals(Constant.RESPONSE_STATUS_ERROR, response.getStatus());
	}
	
	@Test
	public void findPlaceAuthenSuccess() throws Exception{
		VT070001EntityBarcodePrefix mockRequestParam = new VT070001EntityBarcodePrefix();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070001Service).findTypeBarcode(mockRequestParam);
		
		response = mockController.findPlace(mockRequestParam);		
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void findPlaceAuthenSuccessHasException() throws Exception{
		VT070001EntityBarcodePrefix mockRequestParam = new VT070001EntityBarcodePrefix();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doThrow(new RuntimeException()).when(vt070001Service).findTypeBarcode(mockRequestParam);
		
		response = mockController.findPlace(mockRequestParam);		
		assertEquals(Constant.RESPONSE_STATUS_ERROR, response.getStatus());
	}
	
	@Test
	public void insertBarcodeRangeAuthenFail() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = new VT070001EntityBarcodeRange();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);
		response = mockController.insertBarcodeRange(mockRequestParam, principal);		
		assertEquals(Constant.RESPONSE_STATUS_ERROR, response.getStatus());
	}
	
	@Test
	public void insertBarcodeRangeHasException() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = null;
		ResponseEntityBase response = mockController.insertBarcodeRange(mockRequestParam, principal);		
		assertEquals(Constant.RESPONSE_STATUS_ERROR, response.getStatus());
	}
	
	@Test
	public void insertBarcodeRangeAuthenSuccess() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = new VT070001EntityBarcodeRange();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		mockRequestParam.setCreateUser(userName);
		Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070001Service).insertBarcodeRange(mockRequestParam);
		
		response = mockController.insertBarcodeRange(mockRequestParam, principal);
		
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void findDriveSquadsAuthenFail() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = new VT070001EntityBarcodeRange();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);;
		response = mockController.findDriveSquads(mockRequestParam);		
		assertEquals(null, response);
	}
	
	@Test
	public void findDriveSquadsHasException() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = null;
		ResponseEntityBase response = mockController.findDriveSquads(mockRequestParam);		
		assertEquals(null, response);
	}
	
	@Test
	public void findDriveSquadsAuthenSuccess() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = new VT070001EntityBarcodeRange();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070001Service).findBarcodeRange(mockRequestParam);
		response = mockController.findDriveSquads(mockRequestParam);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void findBarcodeDetailAuthenFail() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = new VT070001EntityBarcodeRange();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);
		response = mockController.findBarcodeDetail(mockRequestParam);
		assertEquals(null, response);
	}
	
	@Test
	public void findBarcodeDetailHasException() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = null;
		ResponseEntityBase response = mockController.findBarcodeDetail(mockRequestParam);
		assertEquals(null, response);
	}
	
	@Test
	public void findBarcodeDetailAuthenSuccess() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = new VT070001EntityBarcodeRange();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070001Service).findBarcodeDetail(mockRequestParam);
		response = mockController.findBarcodeDetail(mockRequestParam);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void changeBarcodeRangeDetailAuthenFail() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = new VT070001EntityBarcodeRange();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);
		response = mockController.changeBarcodeRangeDetail(mockRequestParam);
		assertEquals(null, response);
	}
	
	@Test
	public void changeBarcodeRangeDetailHasException() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = null;
		ResponseEntityBase response = mockController.changeBarcodeRangeDetail(mockRequestParam);
		assertEquals(null, response);
	}
	
	@Test
	public void changeBarcodeRangeDetailAuthenSuccess() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = new VT070001EntityBarcodeRange();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070001Service).changeBarcodeRangeDetail(mockRequestParam);
		response = mockController.changeBarcodeRangeDetail(mockRequestParam);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void checkChangeBarcodeRangeAuthenFail() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = new VT070001EntityBarcodeRange();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);
		response = mockController.checkChangeBarcodeRange(mockRequestParam);
		assertEquals(null, response);
	}
	
	@Test
	public void checkChangeBarcodeRangeHasException() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = null;
		ResponseEntityBase response = mockController.checkChangeBarcodeRange(mockRequestParam);
		assertEquals(null, response);
	}
	
	@Test
	public void checkChangeBarcodeRangeAuthenSuccess() throws Exception{
		VT070001EntityBarcodeRange mockRequestParam = new VT070001EntityBarcodeRange();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070001Service).checkChangeBarcodeRange(mockRequestParam);
		response = mockController.checkChangeBarcodeRange(mockRequestParam);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
	
	@Test
	public void checkBarcodeAvailableAuthenFail() throws Exception{
		VT070001EntityBarcodeDetail mockRequestParam = new VT070001EntityBarcodeDetail();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);
		response = mockController.checkBarcodeAvailable(mockRequestParam);
		assertEquals(null, response);
	}
	
	@Test
	public void checkBarcodeAvailableHasException() throws Exception{
		VT070001EntityBarcodeDetail mockRequestParam = null;
		ResponseEntityBase response = mockController.checkBarcodeAvailable(mockRequestParam);
		assertEquals(null, response);
	}
	
	@Test
	public void checkBarcodeAvailableAuthenSuccess() throws Exception{
		VT070001EntityBarcodeDetail mockRequestParam = new VT070001EntityBarcodeDetail();
		ResponseEntityBase response = new ResponseEntityBase(-999, null);
		mockRequestParam.setSecurityUsername("username");
    	mockRequestParam.setSecurityPassword("password");
    	Mockito.doReturn(new ResponseEntityBase(Constant.SUCCESS, null)).when(vt070001Service).checkBarcodeAvailable(mockRequestParam);
		response = mockController.checkBarcodeAvailable(mockRequestParam);
		assertEquals(Constant.SUCCESS, response.getStatus());
	}
}
