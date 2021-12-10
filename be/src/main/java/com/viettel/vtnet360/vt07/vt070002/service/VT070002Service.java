package com.viettel.vtnet360.vt07.vt070002.service;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.ProjectDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.RequestParam;
import com.viettel.vtnet360.vt07.vt070002.entity.SearchDocumentResponse;
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;

public interface VT070002Service {
	public boolean validateRole(RequestParam requestParam, Collection<GrantedAuthority> userRoles);
	public Object getListTinBox(RequestParam requestParam, String userName, Collection<GrantedAuthority> userRoles);
	public Object createTinBox(TinBoxDetail tinBoxDetail, String userName, Collection<GrantedAuthority> userRoles);
	public Object updateTinBox(TinBoxDetail tinBoxDetail, String userName, Collection<GrantedAuthority> userRoles);
	public Object getAvailObjectInProject(RequestParam requestParam, Collection<GrantedAuthority> userRoles);	
	public Object getProjectObjectInFolder(RequestParam requestParam, Collection<GrantedAuthority> userRoles);
	public ResponseEntityBase getProjectDocOfWarehouse(RequestParam requestParam, Collection<GrantedAuthority> userRoles);
	public ResponseEntityBase getDocumentReport(int warehouseId, int pageSize, int pageNumber, Collection<GrantedAuthority> userRoles);
	public File createExcelOutputExcel(RequestParam requestParam, Collection<GrantedAuthority> listRole);
	public File exportExcel(int warehouseId, Collection<GrantedAuthority> listRole);
	public ResponseEntityBase searchListTinBox(TinBoxDetail requestParam, Collection<GrantedAuthority> userRoles);
	public ResponseEntityBase findTypeWarehouseDetail(WarehouseDetail requestParam, Collection<GrantedAuthority> userRoles);
	public ResponseEntityBase updateTinboxByInformation(TinBoxDetail requestParam, Collection<GrantedAuthority> userRoles);
	public ResponseEntityBase countTinBoxAndOccupiedSlot(int warehouseId, Collection<GrantedAuthority> userRoles);
	
	public ResponseEntityBase getType1Warehouses(WarehouseDetail requestParam, Collection<GrantedAuthority> userRoles);
	public ResponseEntityBase getTinBoxInType1Warehouse(TinBoxDetail requestParam, Collection<GrantedAuthority> userRoles);
	
	public Object getFolderByQRCode(RequestParam requestParam, String userName, Collection<GrantedAuthority> userRoles);
	public Object getProjectTreeInFolder(RequestParam requestParam, Collection<GrantedAuthority> userRoles);
	public ResponseEntityBase searchDocument(String keyword, int pageSize, int pageNumber, Collection<GrantedAuthority> userRoles);
	
	public List<ProjectDetail> getProjectsInTinBox(long tinBoxId, Collection<GrantedAuthority> userRoles);
	public List<SearchDocumentResponse> searchDocumentsByKeyword(String keyword, Collection<GrantedAuthority> userRoles, int pageNumber, int pageSize);
}
