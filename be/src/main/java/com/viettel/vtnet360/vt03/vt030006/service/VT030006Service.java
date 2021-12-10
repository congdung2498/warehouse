package com.viettel.vtnet360.vt03.vt030006.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030006.entity.VT030006UpdateBookCar;


/**
 * 
 * @author CuongHD
 *
 */
public interface VT030006Service {
	/**
	 * do approve and do refuse
	 * 
	 * @param obj
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase updateRequest(VT030006UpdateBookCar obj, Collection<GrantedAuthority> roleList) throws Exception;
	
	
	
}
