package com.viettel.vtnet360.vt05.vt050011.service;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011RequestParamToExecute;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011RequestParamToSearch;

/**
 * @author DuyNK 09/10/2018
 */
public interface VT050011Service {

	/**
	 * find data of 1 request
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findData(VT050011RequestParamToSearch param);

	/**
	 * execute give out stationery request
	 * 
	 * @param param
	 * @param userName
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase executeGiveOut(VT050011RequestParamToExecute param);
}
