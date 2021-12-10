package com.viettel.vtnet360.vt02.vt020000.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt02.vt020000.dao.VT020000DAO;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Kitchen;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000MegaUnit;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;
import com.viettel.vtnet360.vt02.vt020010.dao.VT020010DAO;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010InfoToFindUnit;
import com.viettel.vtnet360.vt02.vt020010.entity.VT020010Unit;

/**
 * Class implement from VT020000Service with business rule
 * 
 * @author VinhNVQ 9/8/2018
 *
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT020000ServiceImpl implements VT020000Service {

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/** Data Access Object */
	@Autowired
	VT020000DAO vt020000Dao;

	@Autowired
	VT020010DAO vt020010DAO;

	/**
	 * Return list of VT020000Kitchen have name like searchText from database
	 * 
	 * @author VinhNVQ 9/8/2018
	 * 
	 * @param String searchText
	 * 
	 * @return List<VT020000Kitchen>
	 */
	@Override
	@Transactional(readOnly = true)
	public List<VT020000Kitchen> kitchenList(String searchText, String userName, Collection<GrantedAuthority> authority)
			throws Exception {

		/** Initialization List of Unit object for contain result */
		List<VT020000Kitchen> kitchenList = null;

		if (authority.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))) {
			try {
				kitchenList = vt020000Dao.getKitchenList(searchText);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw e;
			}
		} else if (authority.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_CHEF))) {
			try {
				kitchenList = vt020000Dao.getKitchen(userName);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw e;
			}
		} else {
			try {
				kitchenList = vt020000Dao.getKitchenList(searchText);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw e;
			}
		}
		return kitchenList;
	}

	/**
	 * Return list of unit as VT020000MegaUnit in tree like structure from database
	 * 
	 * @author VinhNVQ 9/8/2018
	 * 
	 * @param None
	 * 
	 * @return List<VT020000MegaUnit>
	 */
	@Override
	@Transactional(readOnly = true)
	public List<VT020000MegaUnit> locationList(String unitID) throws Exception {
		/** Initialization List of Unit object for contain result */
		List<VT020000MegaUnit> DX = new ArrayList<>();

		/** Initialization Map of unit for buildup tree like unit structure */
		Map<Long, VT020000MegaUnit> hm = new HashMap<>();

		/** Initialization variable for contain list unit from database */
		List<VT020000Unit> unitList;
		String temp = unitID;
		if (StringUtils.isNotBlank(unitID)) {
			unitID = "%" + unitID + "%";
		}
		try {
			/** Get list unit from database */
			unitList = vt020000Dao.getLocationList(unitID);

			for (VT020000Unit unit : unitList) {
				// Initialization variable of unit
				VT020000MegaUnit mmdChild;
				if (hm.containsKey(unit.getUnitId())) {
					// If there is unit then get that unit
					mmdChild = hm.get(unit.getUnitId());
				} else {
					// Create new unit if it not exist in hash map
					mmdChild = new VT020000MegaUnit();
					hm.put(unit.getUnitId(), mmdChild);
				}
				// Set unit in map to be same as unit
				mmdChild.setId(unit.getUnitId());
				mmdChild.setParentId(unit.getParentId());
				mmdChild.setName(unit.getUnitName());

				// Initialization variable of unit Parent
				VT020000MegaUnit mmdParent;

				if (hm.containsKey(unit.getParentId())) {
					// If there is parent in hash map then get that parent
					mmdParent = hm.get(unit.getParentId());
				} else {
					// Create new parent of unit if it not exist in hash map
					mmdParent = new VT020000MegaUnit();
					hm.put(unit.getParentId(), mmdParent);
					// set ID of parent
					mmdParent.setId(unit.getParentId());
					// Abstract parent will have id = -1
					mmdParent.setParentId((long) -1);
				}
				// Link unit to it parent
				mmdParent.addChildrenItem(mmdChild);
			}

			// Get the root where unit is child of Abstract unit (not exist)
			for (VT020000MegaUnit mmd : hm.values()) {
				if (unitID != null && String.valueOf(mmd.getId()).equals(temp)) {
					DX.add(mmd);
				} else {
					if (mmd.getParentId() == null) {
						DX.add(mmd);
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return DX;
	}

	@Override
	public List<VT020000Unit> locationListByID(String userName, Collection<GrantedAuthority> authority, String query)
			throws Exception {
		try {
			/** Get list unit from database */
			return vt020000Dao.getLocationListByID(query);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public VT020000Unit getUnitParent(String unitID) {
		// list unitName
		List<String> listUnitName = new ArrayList<>();
		listUnitName.add(Constant.UNIT_NAME_KHOI);
		// get all unit that is child of VTNET and UNIT_NAME not like 'khoi%'
		List<VT020010Unit> listUnitNotLikeKhoi = vt020010DAO
				.findAllUnitNotLikeKhoi(new VT020010InfoToFindUnit(Constant.UNIT_PARENT_ID, listUnitName));
		// get all unit that is child of VTNET and UNIT_NAME like 'khoi%' *(list 01)
		List<Integer> listUnitLikeKhoi = vt020010DAO
				.findAllUnitLikeKhoi(new VT020010InfoToFindUnit(Constant.UNIT_PARENT_ID, listUnitName));
		// get all unit that is child of *(list 01)
		
		
		List<VT020010Unit> listUnitChildOfKhoi = vt020010DAO.findAllUnitChildOfKhoiNoParent(listUnitLikeKhoi);

		// list unit necessary ( parent is VTNET, if UNIT_NAME not like 'khoi%' =>
		// get it, if UNIT_NAME like 'khoi%' get child)
		List<VT020010Unit> listUnit = new ArrayList<>();
		for (int i = 0; i < listUnitNotLikeKhoi.size(); i++) {
			listUnit.add(listUnitNotLikeKhoi.get(i));
		}
		for (int i = 0; i < listUnitChildOfKhoi.size(); i++) {
			listUnit.add(listUnitChildOfKhoi.get(i));
		}

		// unitPath of unitID(param)
		String unitPath = vt020000Dao.findUnitPathByUnitID(unitID);

		for (int i = 0; i < listUnit.size(); i++) {
			if (unitPath.contains(String.valueOf(listUnit.get(i).getUnitID()))) {
				return vt020000Dao.findUnitInfoByUnitID(String.valueOf(listUnit.get(i).getUnitID()));
			}
		}
		return null;
	}
}
