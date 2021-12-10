package com.viettel.vtnet360.vt03.vt030000.service;

import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000NotifySmsInfo;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000ResponseBookCarRequest;

/**
 * interface service of VT030000
 * 
 * @author ThangBT 11/09/2018
 * @author CuongHD
 *
 */
public interface VT030000Service {

	/**
	 * find all driveSquad
	 * 
	 * @author ThangBT
	 * @return List<VT030000EntityDriveSquad>
	 * @throws Exception
	 */
	public ResponseEntityBase findAllSquad(VT030000EntityDriveSquad squadInfo) throws Exception;

	/**
	 * Find Bookcar's request base on it's id 
	 * 
	 * @author CuongHD
	 * @param String username
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase findBookCarRequest(VT030000ResponseBookCarRequest obj) throws Exception;

	/**
	 * find info od car route to send notify and send SMS 
	 * 
	 * @param String id
	 * @return VT030000NotifySmsInfo
	 * @throws Exception
	 */
	public VT030000NotifySmsInfo findInfoNotifySms(VT030000NotifySmsInfo obj) throws Exception;
	
	/**
	 * Update all Bookcar's request timeout
	 * 
	 * @return int 
	 * @throws Exception
	 */
	public int updateStatusRequestTimeOut() throws Exception;
	
	public ResponseEntityBase findAllSquadById(SearchData searchData)  throws Exception;
 }
