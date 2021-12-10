package com.viettel.vtnet360.vt02.vt020012.service;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt02.vt020012.entity.VT020012KitchenReport;
import com.viettel.vtnet360.vt02.vt020012.entity.VT020012RequestParam;


public interface VT020012Service {
	
	/**
	 * Return list of VT020012KitchenReport
	 * 
	 * @author VinhNVQ 9/8/2018
	 * @param authority 
	 * @param userName 
	 * 
	 * @param String kitchenList
	 * 
	 * @return List<VT020012KitchenReport>
	 */
	public List<VT020012KitchenReport> kitchenList(VT020012RequestParam param, String userName, Collection<GrantedAuthority> authority) throws Exception;
	
	/**
	 * Return list of VT020012KitchenReport
	 * 
	 * @author VinhNVQ 9/8/2018
	 * @param authority 
	 * @param userName 
	 * 
	 * @param String kitchenList
	 * 
	 * @return List<VT020012KitchenReport>
	 */
	public File kitchenReport(VT020012RequestParam param, String userName, Collection<GrantedAuthority> authority) throws Exception;
}
