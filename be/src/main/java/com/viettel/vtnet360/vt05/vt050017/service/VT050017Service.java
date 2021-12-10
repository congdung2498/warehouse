package com.viettel.vtnet360.vt05.vt050017.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050017.entity.VT050017RequestParam;

/**
 * @author DuyNK
 */
public interface VT050017Service {

	/**
	 * find list info request of hcdv
	 * 
	 * @param param
	 * @param roleList
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findData(VT050017RequestParam param, Collection<GrantedAuthority> roleList);

}
