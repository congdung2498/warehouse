package com.viettel.vtnet360.vt07.vt070000.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt07.vt070000.entity.DocumentDetails;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxDetailsRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxSearchRequest;
import com.viettel.vtnet360.vt07.vt070000.entity.TinBoxSearchResult;
import com.viettel.vtnet360.vt07.vt070000.entity.WarehouseDetail;
import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;

public interface TinBoxSearchService {
	public List<WarehouseDetail> getAllActiveWarehouse(Collection<GrantedAuthority> userRoles);
	public List<TinBoxSearchResult> getAllTinBoxInWarehouse(int warehouseId, Collection<GrantedAuthority> userRoles);
	public List<TinBoxSearchResult> searchTinBox(int warehouseId, String keyword, boolean byTinBox, boolean byFolder, boolean byProject, boolean byPackage, boolean byContract, boolean byConstruction, Collection<GrantedAuthority> userRoles);
	public List<DocumentDetails> getDocumentsInBox(long tinBoxId, Collection<GrantedAuthority> userRoles);
	public List<TinBoxDetail> searchTinBox(TinBoxSearchRequest req, Collection<GrantedAuthority> userRoles);
	public List<DocumentDetails> getDocumentsInBoxV2(TinBoxDetailsRequest tinBoxRq, Collection<GrantedAuthority> userRoles);
}
