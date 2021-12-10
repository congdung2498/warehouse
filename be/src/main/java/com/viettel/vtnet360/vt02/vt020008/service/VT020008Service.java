package com.viettel.vtnet360.vt02.vt020008.service;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020008.entity.VT020008ParamRequest;

/**
 * Class service for screen VT020008 : rating lunch
 * 
 * @author DuyNK 19/09/2018
 */
public interface VT020008Service {

	/**
	 * update rating and comment to day = now
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase updateRating(VT020008ParamRequest param);

}
