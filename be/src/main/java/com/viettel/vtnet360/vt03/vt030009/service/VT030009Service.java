package com.viettel.vtnet360.vt03.vt030009.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009ResponseCarRoute;
import com.viettel.vtnet360.vt03.vt030009.entity.VT030009UpdateProcessCarRoute;

/**
 * inteface VT030009Service
 * 
 * @author CuongHD
 *
 */
public interface VT030009Service {

	/**
	 * Update process for car's route
	 * 
	 * @param VT030009UpdateProcessCarRoute obj
	 * @param  Collection<GrantedAuthority> listRole
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase updateCarRoute(VT030009UpdateProcessCarRoute obj, Collection<GrantedAuthority> listRole) throws Exception;
	
	/**
	 * Get information of route
	 * 
	 * @param VT030009UpdateProcessCarRoute obj
	 * @param  Collection<GrantedAuthority> listRole
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase findCarRoute(VT030009UpdateProcessCarRoute obj, Collection<GrantedAuthority> listRole) throws Exception;
	
	/**
	 * Tim kiem tat ca yeu cau da duoc xep xe, den vi tri, hoan thanh
	 * 
	 * @param VT030009ResponseCarRoute obj
	 * @param  Collection<GrantedAuthority> listRole
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase getListRequest(VT030009ResponseCarRoute obj, Collection<GrantedAuthority> listRole) throws Exception;
}
