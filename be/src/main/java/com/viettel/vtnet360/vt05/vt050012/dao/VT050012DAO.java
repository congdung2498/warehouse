package com.viettel.vtnet360.vt05.vt050012.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt05.vt050012.entity.QuotaInsert;
import com.viettel.vtnet360.vt05.vt050012.entity.UpdateLimitDateDTO;
import com.viettel.vtnet360.vt05.vt050012.entity.VT050012DataResponse;
import com.viettel.vtnet360.vt05.vt050012.entity.VT050012DataResponseQuota;
import com.viettel.vtnet360.vt05.vt050012.entity.VT050012RequestParam;
import com.viettel.vtnet360.vt05.vt050012.entity.VT050012RequestParamQuota;

/**
 * @author DuyNK
 * 
 */
@Repository
public interface VT050012DAO {

	/**
	 * find info request of this user logged on
	 * 
	 * @param param
	 * @return List<VT050012DataResponse>
	 */
	public List<VT050012DataResponse> findData(VT050012RequestParam param);
	
	public int countRequest(VT050012RequestParam param);
	
	public List<VT050012DataResponseQuota> findStationeryQuota(VT050012RequestParamQuota param);
	
	public int countStationeryQuota(VT050012RequestParamQuota param);
	
	public String getLimitDateEnd();
	
	public int updateLimitDate(UpdateLimitDateDTO codeName);
	
	public int insertQuota(QuotaInsert quotaInsert);
	
	public int updateQuota(VT050012DataResponseQuota param);
	
	public int deleteQuota(VT050012DataResponseQuota param);
	
	public int isQuotaExist(Integer unitId);
}
