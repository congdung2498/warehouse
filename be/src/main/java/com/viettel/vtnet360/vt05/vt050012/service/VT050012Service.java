package com.viettel.vtnet360.vt05.vt050012.service;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050012.entity.QuotaInsert;
import com.viettel.vtnet360.vt05.vt050012.entity.UpdateLimitDateDTO;
import com.viettel.vtnet360.vt05.vt050012.entity.VT050012DataResponseQuota;
import com.viettel.vtnet360.vt05.vt050012.entity.VT050012RequestParam;
import com.viettel.vtnet360.vt05.vt050012.entity.VT050012RequestParamQuota;

/**
 * @author DuyNK 09/10/2018
 */
public interface VT050012Service {

	/**
	 * find info request of this user logged on
	 * 
	 * @param param
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findData(VT050012RequestParam param);
	
	public ResponseEntityBase findStationeryQuota(VT050012RequestParamQuota param);
	
	public ResponseEntityBase countStationeryQuota(VT050012RequestParamQuota param);
	
	public ResponseEntityBase getLimitDateEnd();

	public ResponseEntityBase updateLimitDate(UpdateLimitDateDTO updateLimitDateDTO);
	
	public ResponseEntityBase insertQuota(QuotaInsert quotaInsert);

	public ResponseEntityBase updateQuota(VT050012DataResponseQuota param);
	
	public ResponseEntityBase deleteQuota(VT050012DataResponseQuota param);
}
