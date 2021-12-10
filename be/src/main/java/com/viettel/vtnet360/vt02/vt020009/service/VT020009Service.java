package com.viettel.vtnet360.vt02.vt020009.service;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020009.entity.VT020009InfoToFindReport;

/**
 * Class service for screen VT020009 : report lunch date in this kitchen(of chef logged on)
 * 
 * @author DuyNK 13/09/2018
 */
public interface VT020009Service {

	/**
     * Select list report in a month
     * 
	 * @param info
	 * @param userName
	 * @param roleList
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findReport(VT020009InfoToFindReport info, String userName, Collection<GrantedAuthority> roleList);
}
