package com.viettel.vtnet360.vt02.vt020000.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Kitchen;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000MegaUnit;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;

/**
 * VT020000Service Interface define function that VT020000 service will solve
 * 
 * @author VinhNVQ 9/8/2018
 *
 */
public interface VT020000Service {
	
	/**
	 * Return list of VT020000Kitchen have name like searchText from database
	 * 
	 * @author VinhNVQ 9/8/2018
	 * @param userName 
	 * @param authority 
	 * 
	 * @param String searchText
	 * 
	 * @return List<VT020000Kitchen>
	 */
	public List<VT020000Kitchen> kitchenList(String searchText, String userName, Collection<GrantedAuthority> authority) throws Exception;
	
	/**
	 * Return list of unit as VT020000MegaUnit in tree like structure from database
	 * 
	 * @author VinhNVQ 9/8/2018
	 * 
	 * @param None
	 * 
	 * @return List<VT020000MegaUnit>
	 */
	public List<VT020000MegaUnit> locationList(String unitID) throws Exception;
	
	/**
	 * Return list of unit as VT020000MegaUnit in tree like structure from database
	 * 
	 * @author VinhNVQ 9/8/2018
	 * @param authority 
	 * @param userName 
	 * @param string 
	 * 
	 * @param None
	 * 
	 * @return List<VT020000MegaUnit>
	 */
	public List<VT020000Unit> locationListByID(String userName, Collection<GrantedAuthority> authority, String string) throws Exception;
	
	/**
	 * list unit necessary ( parent is VTNET, if UNIT_NAME not like 'khoi%' => get it, if UNIT_NAME like 'khoi%' get child)
	 * 
	 * @param unitID
	 * @return VT020000Unit
	 */
	public VT020000Unit getUnitParent(String unitID);
}
