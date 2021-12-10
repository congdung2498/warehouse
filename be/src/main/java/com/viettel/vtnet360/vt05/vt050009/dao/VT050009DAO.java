package com.viettel.vtnet360.vt05.vt050009.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt05.vt050004.entity.VT050004DataResponse;
import com.viettel.vtnet360.vt05.vt050009.entity.VT050009DataResponse;

/**
 * @author DuyNK 04/10/2018
 */
@Repository
public interface VT050009DAO {

	/**
	 * find info 1 request to cvp approve
	 * 
	 * @param issuesStationeryApproveID
	 * @return VT050006DataResponse
	 */
	public VT050009DataResponse findInfoRequest(String issuesStationeryApproveID);

	/**
	 * find info detail of 1 request to cvp approve
	 * 
	 * @param issuesStationeryApproveID
	 * @return List<VT050004DataResponse>
	 */
	public List<VT050004DataResponse> findInfoRequestDetail(String issuesStationeryApproveID);
}
