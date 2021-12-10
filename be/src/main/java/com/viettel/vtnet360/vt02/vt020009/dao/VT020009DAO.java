package com.viettel.vtnet360.vt02.vt020009.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt02.vt020009.entity.VT020009InfoToFindKitchenID;
import com.viettel.vtnet360.vt02.vt020009.entity.VT020009InfoToFindReport;
import com.viettel.vtnet360.vt02.vt020009.entity.VT020009Report;

/**
 * Class DAO for screen VT020009 : report kitchen rating
 * 
 * @author DuyNK 13/09/2018
 */
@Repository
public interface VT020009DAO {

	/**
	 * get feedback of a kitchen in a day (table LUNCH_CALENDAR)
	 * 
	 * @param info
	 * @return List<VT020009Report>
	 */
	public List<VT020009Report> findReportItem(VT020009InfoToFindReport info);

	/**
	 * @param info
	 * @return String
	 */
	public String findKitchenID(VT020009InfoToFindKitchenID info);
}
