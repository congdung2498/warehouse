package vt70002.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt07.vt070000.dao.VT070000DAO;
import com.viettel.vtnet360.vt07.vt070000.service.TinBoxSearchService;
import com.viettel.vtnet360.vt07.vt070001.dao.VT070001DAO;
import com.viettel.vtnet360.vt07.vt070002.dao.VT070002DAO;
import com.viettel.vtnet360.vt07.vt070002.entity.ProjectDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.SearchFolderResponse;
import com.viettel.vtnet360.vt07.vt070002.service.FolderServiceImpl;
import com.viettel.vtnet360.vt07.vt070002.service.VT070002ServiceImpl;

public class FolderServiceTest {
	private static final String ENCRYPTED_USERNAME = "a37d7e12b490d7000dcc6fa0fd7066c2";
	private static final String ENCRYPTED_PASSWORD = "c9a0172b171314c3eb811606a8d1528b";
	Date createDate;
	Date updateDate;
	
	@InjectMocks
	private FolderServiceImpl mockService;
	
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
	
	String userName;
	Collection<GrantedAuthority> roleList;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		createDate = new Date();
		updateDate = new Date();
		
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		roleList = oauth.getAuthorities();
		SimpleGrantedAuthority unit1 = new SimpleGrantedAuthority(Constant.PMQT_ROLE_WAREHOUSE_GROUP);
		roleList.add(unit1);
		userName = "test user";
	}
	
	@Test
	public void searchFolderByNameOrQrCodeTestEmptyResultRole0() {
		roleList.clear();
		List<SearchFolderResponse> result = mockService.searchFolderByNameOrQrCode("abc", 1, 10, roleList);
		assertEquals(new ArrayList<>(), result);
	}
	
	@Test
	public void searchFolderByNameOrQrCodeTest() {
		List<SearchFolderResponse> mockRes = new ArrayList<SearchFolderResponse>();
		SearchFolderResponse mockRecord = new SearchFolderResponse();
		mockRecord.setFolderId(69);
		mockRecord.setFolderName("mock folder");
		mockRecord.setFolderQrCode("mock folder qr");
		mockRecord.setTinBoxId(6969);
		mockRecord.setTinBoxName("mock tinbox");
		mockRecord.setTinBoxQrCode("mock tinbox qr");
		mockRecord.setTotalFoundRecord(96);
		mockRes.add(mockRecord);
		Mockito.doReturn(1).when(vt070002dao).searchFolderByNameOrQrCodeTotalRecord("abc", 1);
		Mockito.doReturn(mockRes).when(vt070002dao).searchFolderByNameOrQrCode("abc", 0, 10, 1);
		List<SearchFolderResponse> result = mockService.searchFolderByNameOrQrCode("abc", 1, 10, roleList);
		assertEquals(1, result.size());
		assertEquals(69, result.get(0).getFolderId());
		assertEquals(6969, result.get(0).getTinBoxId());
		assertEquals("mock folder", result.get(0).getFolderName());
		assertEquals("mock folder qr", result.get(0).getFolderQrCode());
		assertEquals("mock tinbox", result.get(0).getTinBoxName());
		assertEquals("mock tinbox qr", result.get(0).getTinBoxQrCode());
	}
	
	@Test
	public void getProjectsInFolderTest() {
		List<ProjectDetail> result = mockService.getProjectsInFolder(69);
		assertEquals(0, result.size());
	}
	
	@Test
	public void getProjectsNotInFolderTest() {
		List<ProjectDetail> result = mockService.getProjectsNotInFolder(69, roleList);
		assertEquals(0, result.size());
	}
}
