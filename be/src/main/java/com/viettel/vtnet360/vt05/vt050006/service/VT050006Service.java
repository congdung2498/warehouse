package com.viettel.vtnet360.vt05.vt050006.service;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050006.entity.VT050006RequestParam;

/**
 * @author DuyNK
 * 09/10/2018
 */
public interface VT050006Service {

	/**
	 * find data of 1 request to approve
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findDataToApprove(VT050006RequestParam param);
	
	public ResponseEntityBase findDataToApproveProcess(VT050006RequestParam param);
	
	public ResponseEntityBase findInfoRequestDetailProcessDetails(VT050006RequestParam param);
}
