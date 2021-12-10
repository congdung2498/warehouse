package com.viettel.vtnet360.vt03.vt030011.service;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030011.entity.VT030011CancelRequest;

/**
 * interface VT030011Service
 * 
 * @author CuongHD
 *
 */
public interface VT030011Service {
	/**
	 * Cancel a request by it's id
	 * 
	 * @param VT030011CancelRequest obj
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase updateRequest(VT030011CancelRequest obj) throws Exception;
}
