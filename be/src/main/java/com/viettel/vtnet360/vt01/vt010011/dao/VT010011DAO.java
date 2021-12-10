package com.viettel.vtnet360.vt01.vt010011.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt01.vt010011.entity.VT010011ListCondition;
import com.viettel.vtnet360.vt01.vt010011.entity.VT010011ListEmployee;
import com.viettel.vtnet360.vt01.vt010011.entity.VT010011ListInOut;

/**
 * interface dao get data from database VT010011
 * 
 * @author ThangBT 07/09/2018
 *
 */

@Repository
public interface VT010011DAO {

	/**
	 * get list of employee
	 * 
	 * @param emp
	 * @return List<VT010010ListEmployee>
	 */
	public List<VT010011ListEmployee> findListEmployee(VT010011ListEmployee empInfo);

	/**
	 * get list employee registry to go out
	 * 
	 * @param vlc
	 * @return List<VT010010ListInOut>
	 */
	public List<VT010011ListInOut> findListInOut(VT010011ListCondition vlc);

	/**
	 * count total record
	 * 
	 * @param vlc
	 * @return
	 */
	public int countTotalRecord(VT010011ListCondition vlc);

}
