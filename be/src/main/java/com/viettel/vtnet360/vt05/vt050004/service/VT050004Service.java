package com.viettel.vtnet360.vt05.vt050004.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004InfoToSearchIssuesStationeryItems;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004ParamToInsert;

/**
 * @author DuyNK 09/10/2018
 */
public interface VT050004Service {

	/**
	 * find list issues stationery items to send manager approve
	 * 
	 * @param info
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findIssuesStationeryItems(VT050004InfoToSearchIssuesStationeryItems info,
			Collection<GrantedAuthority> roleList);

	/**
	 * HCDV send request approve to manager
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase approveIssuesStationeryItems(VT050004ParamToInsert param, Collection<GrantedAuthority> roleList);

	/**
	 * send sms and notify for LDDV
	 * 
	 * @param approveUserName
	 * @param userName
	 * @param idRecord
	 * @param statusRecord
	 * @param message
	 * @throws Exception
	 */
	public void sendSmsAndNotify(String approveUserName, String userName, String idRecord, int statusRecord, String message)
			throws Exception;
	
	/**
	 * check spending limit money of unit (unit of hcdv logged on config in STATIONERY_STAFF) before send request to LDDV
	 * 
	 * @param param
	 * @return Boolean
	 */
	public boolean checkSpendingLimitOfUnit(VT050004ParamToInsert param);
	
	/**
	 * check spending limit money of unit (unit of hcdv logged on config in STATIONERY_STAFF) before send request to LDDV
	 * 
	 * @param param
	 * @return Boolean
	 */
	
	public ResponseEntityBase countIssuesStationeryItems(VT050004InfoToSearchIssuesStationeryItems info,
			Collection<GrantedAuthority> roleList);
	
}
