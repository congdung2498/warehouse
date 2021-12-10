package com.viettel.vtnet360.vt05.vt050018.service;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018RequestParamToConfirm;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018RequestParamToSearch;

/**
 * @author DuyNK 09/10/2018
 */
public interface VT050018Service {

	/**
	 * Find Data Detail Of 1 Request Of HCDV
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findData(VT050018RequestParamToSearch param);

	/**
	 * hcdv confirm received stationery from VPTCT
	 * 
	 * @param param
	 * @param hcdvUserName
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase ConfirmReceivedStationery(VT050018RequestParamToConfirm param, String hcdvUserName);
	
	public ResponseEntityBase refuseReceivedStationery(VT050018RequestParamToConfirm param, String hcdvUserName);
}
