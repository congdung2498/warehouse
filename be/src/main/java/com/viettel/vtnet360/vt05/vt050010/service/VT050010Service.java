package com.viettel.vtnet360.vt05.vt050010.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050010.entity.CheckPlaceByUser;
import com.viettel.vtnet360.vt05.vt050010.entity.VT050010RequestParam;

/**
 * @author DuyNK 09/10/2018
 */
public interface VT050010Service {

	/**
	 * find data request
	 * 
	 * @param param
	 * @param userName
	 * @param roleList
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findData(VT050010RequestParam param, String userName,
			Collection<GrantedAuthority> roleList);
	
	public ResponseEntityBase findPlaceByUserName( CheckPlaceByUser checkPlaceByUser, Collection<GrantedAuthority> roleList);

}
