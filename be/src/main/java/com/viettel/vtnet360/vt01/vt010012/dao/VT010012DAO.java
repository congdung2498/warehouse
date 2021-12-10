package com.viettel.vtnet360.vt01.vt010012.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt01.vt010012.entity.VT010012ListAddCard;
import com.viettel.vtnet360.vt01.vt010012.entity.VT010012ListCondition;
import com.viettel.vtnet360.vt01.vt010012.entity.VT010012ListEmployee;

/**
 * interface dao call query in xml file VT010012
 * 
 * @author ThangBT 10/11/2018
 *
 */

@Repository
@Transactional
public interface VT010012DAO {

	/**
	 * get suggest info of employee by condition
	 * 
	 * @param empInfo
	 * @return result(fullName, code, userName, email, phone number) and userName of
	 *         employee
	 */
	public List<VT010012ListEmployee> findListEmployee(VT010012ListEmployee empInfo);

	/**
	 * get suggest of card id
	 * 
	 * @param cardCondition
	 * @return list card id
	 */
	public List<VT010012ListCondition> findListCard(VT010012ListCondition cardCondition);

	/**
	 * get list employee with card id
	 * 
	 * @param condition
	 * @return list employee
	 */
	public List<VT010012ListAddCard> findListAddCard(VT010012ListCondition condition);

	/**
	 * number of record list employee with condition
	 * 
	 * @param condition
	 * @return number of record list employee
	 */
	public int countTotalRecord(VT010012ListCondition condition);

	/**
	 * number of row if existing couple card and employee
	 * 
	 * @param condition
	 * @return number of row
	 */
	public int checkExistCardAndEmp(VT010012ListAddCard condition);

	/**
	 * update card for each employee
	 * 
	 * @param condition
	 * @return status update card employee
	 */
	public int updateCardEmp(VT010012ListAddCard condition);

}
