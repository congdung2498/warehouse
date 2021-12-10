package com.viettel.vtnet360.vt01.vt010012.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt01.vt010012.entity.VT010012ListAddCard;
import com.viettel.vtnet360.vt01.vt010012.entity.VT010012ListCondition;
import com.viettel.vtnet360.vt01.vt010012.entity.VT010012ListEmployee;

/**
 * interface service VT010012
 * 
 * @author ThangBT 10/11/2018
 *
 */
public interface VT010012Service {

	/**
	 * find List Employee
	 * 
	 * @param empInfo VT010012ListEmployee
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListEmployee(VT010012ListEmployee empInfo) throws Exception;

	/**
	 * find List Card
	 * 
	 * @param cardCondition VT010012ListCondition
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListCard(VT010012ListCondition cardCondition) throws Exception;

	/**
	 * find List Add Card
	 * 
	 * @param condition VT010012ListCondition
	 * @param listRole Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase findListAddCard(VT010012ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception;
	
	/**
	 * count Total Record
	 * 
	 * @param condition VT010012ListCondition
	 * @param listRole Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase countTotalRecord(VT010012ListCondition condition, Collection<GrantedAuthority> listRole)
			throws Exception;

	/**
	 * update Card Emp
	 * 
	 * @param condition VT010012ListCondition
	 * @param listRole Collection<GrantedAuthority>
	 * @return ResponseEntityBase
	 */
	public ResponseEntityBase updateCardEmp(VT010012ListAddCard condition, Collection<GrantedAuthority> listRole)
			throws Exception;

}
