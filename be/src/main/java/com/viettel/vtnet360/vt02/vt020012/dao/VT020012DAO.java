package com.viettel.vtnet360.vt02.vt020012.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt02.vt020012.entity.VT020012KitchenReport;
import com.viettel.vtnet360.vt02.vt020012.entity.VT020012RequestParam;


/**
 * VT020009DAO interface for data base connect
 * 
 * @author VinhNVQ 9/10/2018
 *
 */
@Repository
public interface VT020012DAO {

	/**
	 * Return list report of kitchen sales from database
	 * 
	 * @return List<VT020009KitchenReport>
	 */
	public List<VT020012KitchenReport> getReportForKitchen(VT020012RequestParam rq);

}
