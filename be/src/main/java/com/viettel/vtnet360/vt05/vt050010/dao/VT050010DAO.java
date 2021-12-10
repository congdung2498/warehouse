package com.viettel.vtnet360.vt05.vt050010.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt05.vt050010.entity.CheckPlaceByUser;
import com.viettel.vtnet360.vt05.vt050010.entity.VT050010DataPlaceResponse;
import com.viettel.vtnet360.vt05.vt050010.entity.VT050010DataResponse;
import com.viettel.vtnet360.vt05.vt050010.entity.VT050010InfoToFindRequestInfo;

/**
 * @author DuyNK 04/10/2018
 */
@Repository
public interface VT050010DAO {

	/**
	 * search request to execute giving out request
	 * 
	 * @param info
	 * @return List<VT050010DataResponse>
	 */
	public List<VT050010DataResponse> findInfoRequest(VT050010InfoToFindRequestInfo info);

	/**
	 * if user Logged on by role PMQT_HCVP_VPP => search by their place
	 * 
	 * @param userName
	 * @return Integer
	 */
	public int findPlaceIDOfVptctUserName(String userName);
	
	/**
	 * check user logged on configed in STATIONERY_STAFF or not
	 * 
	 * @param userName
	 * @return Integer
	 */
	public int checkVptctInStaffConfig(String userName);
	
	public int checkHcdvInStaffConfig(String userName);
	
	public List<VT050010DataPlaceResponse> findPlaceIDUserName(CheckPlaceByUser checkPlaceByUser);
	
	public List<VT050010DataResponse> findInfoRequestAdmin(VT050010InfoToFindRequestInfo info);
}
