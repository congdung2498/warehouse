package com.viettel.vtnet360.vt04.vt040004.dao;

import java.util.List;
import java.util.Map;

import com.viettel.vtnet360.vt04.vt040004.entity.VT040004EntityDataForApprove;

/**
 * Class interface VT040004DAO
 * 
 * @author KienHT 4/10/2018
 * 
 */
public interface VT040004DAO {

	/**
	 * User request issuesedService For Approve
	 * 
	 * @param VT040004EntityRq
	 * @return List<VT040004EntityDataForApprove>
	 */
	public List<VT040004EntityDataForApprove> issuesedServiceForApprove(Map<String, Object> data);

}
