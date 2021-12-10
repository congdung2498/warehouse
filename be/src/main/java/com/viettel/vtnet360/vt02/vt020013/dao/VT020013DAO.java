package com.viettel.vtnet360.vt02.vt020013.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt02.vt020013.entity.ReportByUnit;
import com.viettel.vtnet360.vt02.vt020013.entity.ReportByUnitParam;
import com.viettel.vtnet360.vt02.vt020013.entity.VT020013KitchenReport;
import com.viettel.vtnet360.vt02.vt020013.entity.VT020013RequestParam;

/**
 * VT020009DAO interface for data base connect
 * 
 * @author VinhNVQ 9/10/2018
 *
 */
@Repository
public interface VT020013DAO {

	/**
	 * Return list report of kitchen sales from database
	 * 
	 * @return List<VT020009KitchenReport>
	 */
	public List<VT020013KitchenReport> getReportForKitchen(VT020013RequestParam rq);
	
	public List<ReportByUnit> getReportForKitchenForMobile(ReportByUnitParam rq);
	
	public List<ReportByUnit> getReportForKitchenForMobileNoUnit(ReportByUnitParam rq);

	public List<VT020013KitchenReport> getReportResponse(VT020013RequestParam rq);

}
