package com.viettel.vtnet360.vt02.vt020000.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Kitchen;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;

/**
 * VT020000DAO interface for data base connect
 * 
 * @author VinhNVQ 9/8/2018
 *
 */
@Transactional
public interface VT020000DAO {

	/**
	 * Return list location from database
	 * 
	 * @return List<VT020000Unit>
	 */
	public List<VT020000Unit> getLocationList(String unitID);

	/**
	 * Return list kitchen list from database
	 * 
	 * @return List<VT020000Kitchen>
	 */
	public List<VT020000Kitchen> getKitchenList(String searchText);
	
	/**
	 * Return list kitchen of this user from database
	 * 
	 * @return List<VT020000Kitchen>
	 */
	public List<VT020000Kitchen> getKitchen(String userName);
	
	/**
	 * Return list kitchen of this user from database by ID of parent
	 * 
	 * @return List<VT020000Kitchen>
	 */
	public List<VT020000Unit> getLocationListByID(String query);
	
	
	public String findUnitPathByUnitID(String unitID);

	public VT020000Unit findUnitInfoByUnitID(String unitID);
	
}
