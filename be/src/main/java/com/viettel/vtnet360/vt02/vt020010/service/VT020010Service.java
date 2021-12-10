package com.viettel.vtnet360.vt02.vt020010.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010RequestParam;

/**
 * Class service for screen VT020010 : report lunch date in this kitchen(of chef
 * logged on)
 * 
 * @author DuyNK 14/09/2018
 */
public interface VT020010Service {

	/**
	 * get report
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase getReport(VT020010RequestParam param, Collection<GrantedAuthority> roleList);
	
	public ResponseEntityBase getReportForMobile(VT020010RequestParam param, Collection<GrantedAuthority> roleList);
	
	public ResponseEntityBase reportByUnit(VT020010RequestParam param);
}
