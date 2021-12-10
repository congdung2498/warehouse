package com.viettel.vtnet360.vt05.vt050003.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003IssuesStationeryItemsToInsertEmployee;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003RequestParam;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003RequestParamEmployee;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003RequestParamForm;

/**
 * @author DuyNK 04/10/2018
 */
public interface VT050003Service {

	/**
	 * insert to ISSUES_STATIONERY and ISSUES_STATIONERY_ITEMS
	 * 
	 * @param requestParam
	 * @param userName
	 * @param roleList
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertIssuesStationery(VT050003RequestParam requestParam, String userName, Collection<GrantedAuthority> roleList);

	/**
	 * send sms and notify for HCDV
	 * 
	 * @param userName
	 * @param placeID
	 * @param idRecord
	 * @param statusRecord
	 * @throws Exception
	 */
	public void sendSmsAndNotify(String userName, int placeID, String idRecord, int statusRecord) throws Exception;
	
	/**
	 * insert to ISSUES_STATIONERY and ISSUES_STATIONERY_ITEMS
	 * 
	 * @param requestParam
	 * @param userName
	 * @param roleList
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase insertIssuesStationeryForm(VT050003RequestParamForm requestParam, String userName, Collection<GrantedAuthority> roleList);

	
	public ResponseEntityBase insertIssuesStationeryItemsEmployee(VT050003RequestParamEmployee requestParam, String userName, Collection<GrantedAuthority> roleList);

	/**
	 * send sms and notify for HCDV
	 * 
	 * @param userName
	 * @param placeID
	 * @param idRecord
	 * @param statusRecord
	 * @throws Exception
	 */
	
	
	
	
	
	
	
	
	
}
