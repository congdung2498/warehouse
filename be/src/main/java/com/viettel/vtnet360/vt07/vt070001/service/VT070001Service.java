package com.viettel.vtnet360.vt07.vt070001.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeDetail;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodePrefix;
import com.viettel.vtnet360.vt07.vt070001.entity.VT070001EntityBarcodeRange;

public interface VT070001Service {

	
	public ResponseEntityBase findTypeBarcode(VT070001EntityBarcodePrefix obj, Collection<GrantedAuthority> userRoles);
	
	public ResponseEntityBase insertBarcodeRange(VT070001EntityBarcodeRange object, Collection<GrantedAuthority> userRoles);
	
	public ResponseEntityBase findBarcodeRange(VT070001EntityBarcodeRange object, Collection<GrantedAuthority> userRoles);
	
	public ResponseEntityBase findBarcodeDetail(VT070001EntityBarcodeRange object, Collection<GrantedAuthority> userRoles);
	
	public ResponseEntityBase changeBarcodeRangeDetail(VT070001EntityBarcodeRange object, Collection<GrantedAuthority> userRoles);
	
	public ResponseEntityBase checkChangeBarcodeRange(VT070001EntityBarcodeRange object, Collection<GrantedAuthority> userRoles);
	
	public ResponseEntityBase checkBarcodeAvailable(VT070001EntityBarcodeDetail object, Collection<GrantedAuthority> userRoles);
}
