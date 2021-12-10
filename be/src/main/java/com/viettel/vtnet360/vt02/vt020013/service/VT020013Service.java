package com.viettel.vtnet360.vt02.vt020013.service;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt02.vt020013.entity.ReportByUnit;
import com.viettel.vtnet360.vt02.vt020013.entity.ReportByUnitParam;
import com.viettel.vtnet360.vt02.vt020013.entity.VT020013KitchenReport;
import com.viettel.vtnet360.vt02.vt020013.entity.VT020013RequestParam;

public interface VT020013Service {

	/**
	 * Return list of VT020013KitchenReport
	 * 
	 * @author VinhNVQ 9/8/2018
	 * @param authority
	 * @param userName
	 * 
	 * @param String
	 *            kitchenList
	 * 
	 * @return List<VT020013KitchenReport>
	 */
	public List<VT020013KitchenReport> kitchenList(VT020013RequestParam param, String userName,
			Collection<GrantedAuthority> authority) throws Exception;
	
	 public List<ReportByUnit> kitchenListForMobile(ReportByUnitParam param, Collection<GrantedAuthority> authority);

	/**
	 * Return list of VT020013KitchenReport
	 * 
	 * @author VinhNVQ 9/8/2018
	 * @param authority
	 * @param userName
	 * 
	 * @param String
	 *            kitchenList
	 * 
	 * @return List<VT020013KitchenReport>
	 */
	public File kitchenReport(VT020013RequestParam param, String userName, Collection<GrantedAuthority> authority)
			throws Exception;

	public List<VT020013KitchenReport> getReportResponse(VT020013RequestParam param) throws Exception;

}
