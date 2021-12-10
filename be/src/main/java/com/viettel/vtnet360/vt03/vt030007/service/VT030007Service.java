package com.viettel.vtnet360.vt03.vt030007.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030007.entity.VT030007ResquestFindBookCar;


public interface VT030007Service {
	
	/**
	 * Get list bookcar's request
	 * 
	 * @param VT030007ResquestFindBookCar obj
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase findBookCarRequest(VT030007ResquestFindBookCar obj, Collection<GrantedAuthority> roleList) throws Exception;
	
	public ResponseEntityBase findBookCarRequestOrder(VT030007ResquestFindBookCar obj, Collection<GrantedAuthority> roleList) throws Exception;
	
	public ResponseEntityBase findBookCarList(VT030007ResquestFindBookCar obj);
	
	public ResponseEntityBase findBookCarListApprove(VT030007ResquestFindBookCar obj, Collection<GrantedAuthority> roleList);
}
