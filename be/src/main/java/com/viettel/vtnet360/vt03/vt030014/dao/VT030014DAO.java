package com.viettel.vtnet360.vt03.vt030014.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.driving.entity.ListTrip;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListEmployee;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListTrip;

/**
 * interface dao get data from database VT030014
 * 
 * @author ThangBT 07/09/2018
 *
 */

@Repository
@Transactional
public interface VT030014DAO {

	/**
	 * get list of employee
	 * 
	 * @param emp
	 * @return List<VT010010ListEmployee>
	 */
	public List<VT030014ListEmployee> findListEmployee(VT030014ListEmployee empInfo);

	/**
	 * get list of employee's phone number
	 * 
	 * @param emp
	 * @return List<VT010010ListEmployee>
	 */
	public List<VT030014ListEmployee> findListEmployeePhone(VT030014ListEmployee empInfo);

	/**
	 * get list driver
	 * 
	 * @param driverInfo
	 * @return List<VT030014ListDriver>
	 */
	public List<VT030014ListEmployee> findListDriver(VT030014ListEmployee driverInfo);

	/**
	 * get list car booking
	 * 
	 * @param condition
	 * @return List<VT030014ListTrip>
	 */
	public List<VT030014ListCondition> findListTrip(VT030014ListCondition condition);

	/**
	 * count total record
	 * 
	 * @param condition
	 * @return numOfRows in VT030014ListCondition
	 */
	public VT030014ListCondition countTotalRecord(VT030014ListCondition condition);
	
	
	
}
