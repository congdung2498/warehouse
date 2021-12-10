package com.viettel.vtnet360.vt02.vt020010.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt02.vt020010.entity.SearchingUnit;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010DataResponse;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010InfoToFindUnit;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010ReportDetail;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010RequestParam;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010Unit;

/**
 * Class DAO for screen VT020010 report lunch date in this kitchen(of chef
 * logged on)
 * 
 * @author DuyNK 14/09/2018
 */
@Repository
public interface VT020010DAO {
  
  public String getUnitShortNameKhoi(VT020010Unit vt020010Unit);
  
  public List<VT020010DataResponse> reportByUnit(VT020010RequestParam param);
  
	/**
	 * find all report in a month
	 * 
	 * @param param
	 * @returnList<VT020010ReportDetail>
	 */
	public List<VT020010ReportDetail> findReportItem(VT020010RequestParam param);

	/**
	 * find all report in a day in a kitchen
	 * 
	 * @param param
	 * @returnList<VT020010ReportDetail>
	 */
	public List<VT020010ReportDetail> findReportItemByDate(VT020010RequestParam param);
	
	/**
	 * get all unit by unitId PARENT_UNIT_ID that UNIT_NAME not like 'khoi%'
	 * 
	 * @param info
	 * @return List<VT020010Unit>
	 */
	public List<VT020010Unit> findAllUnitNotLikeKhoi(VT020010InfoToFindUnit info);

	/**
	 * get all unit by unitId PARENT_UNIT_ID that UNIT_NAME like 'khoi%'
	 * 
	 * @param info
	 * @return List<Integer>
	 */
	public List<Integer> findAllUnitLikeKhoi(VT020010InfoToFindUnit info);

	/**
	 * get all unit by unitId PARENT_UNIT_ID that Unit Parent has UNIT_NAME like
	 * 'khoi%' with name = 2 level
	 * 
	 * @param listUnitID
	 * @return List<VT020010Unit>
	 */
	public List<VT020010Unit> findAllUnitChildOfKhoi(SearchingUnit searching);
	
	/**
	 * get all unit by unitId PARENT_UNIT_ID that Unit Parent has UNIT_NAME like
	 * 'khoi%' with name = 1 level
	 * 
	 * @param listUnitID
	 * @return List<VT020010Unit>
	 */
	public List<VT020010Unit> findAllUnitChildOfKhoiNoParent(List<Integer> listUnitID);
	
	/**
	 * 
	 * @param unitId
	 * @return
	 */
	public String getUnitShortName(VT020010Unit vt020010Unit);
	
	public String findReportItemByDateLog(VT020010RequestParam param);
}
