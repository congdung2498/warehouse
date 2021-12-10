package com.viettel.vtnet360.vt02.vt020004.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.InputValidate;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020004.dao.VT020004DAO;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004Dish;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindDishByChefID;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindDishInMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindKitchen;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToFindMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004InfoToInsertMenu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004Menu;
import com.viettel.vtnet360.vt02.vt020004.entity.VT020004MenuItem;

/**
 * Class serviceImpl for screen VT020004 : setting menu
 * 
 * @author DuyNK 12/09/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT020004ServiceImpl implements VT020004Service {
	private final Logger logger = Logger.getLogger(this.getClass());
	private InputValidate validate = new InputValidate();

	@Autowired
	private VT020004DAO vt020004DAO;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findLishMenu(VT020004InfoToFindMenu info, String useName,
			Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		// check role user logged on if Chef => get kitchenID, if admin => find by
		// kitchenID of param request from client
		if (StringUtils.isEmpty(info.getKitchenID())) {
		  resp.setData(null);
		  return resp;
		}
		if (info.getDateFrom() != null) {
			info.setDateFrom(validate.validateDate(info.getDateFrom()));
		}
		if (info.getDateTo() != null) {
			info.setDateTo(validate.validateDate(info.getDateTo()));
		}
		List<VT020004Menu> listMenu = new ArrayList<>();
		List<VT020004MenuItem> listMenuItem = null;
		try {
			info.setPageNumber((info.getPageNumber() - 1) * info.getPageSize());
			// get all record in menuSetting by chefID
			listMenuItem = vt020004DAO.findListMenu(info);
			if (listMenuItem.isEmpty()) {
				resp.setData(null);
				return resp;
			}
			// convert menuItem to Menu
			Map<Date, List<VT020004MenuItem>> groupByDateOfMenu = listMenuItem.stream()
					.sorted((o1, o2) -> o2.getDateOfMenu().compareTo(o1.getDateOfMenu())).collect(Collectors
							.groupingBy(VT020004MenuItem::getDateOfMenu, LinkedHashMap::new, Collectors.toList()));
			Set<Date> set = groupByDateOfMenu.keySet();
			for (Date key : set) {
				List<VT020004MenuItem> list = groupByDateOfMenu.get(key);
				List<VT020004Dish> listDish = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					listDish.add(new VT020004Dish(list.get(i).getDishID(), list.get(i).getDishName(),list.get(i).getImage()));
				}
				listMenu.add(new VT020004Menu(null, key, listDish, null, null));
			}
			resp.setData(listMenu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListDishByChefID(VT020004InfoToFindDishByChefID info,
			Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			List<VT020004Dish> list = null;
			info.setDeleteFG(Constant.DELETE_FG_ACTIVE);
			info.setStatus(Constant.STATUS_ACTIVE);
			list = vt020004DAO.findListDishByChefID(info);
			resp.setData(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase checkMenuExist(VT020004InfoToFindDishInMenu info, String userName,
			Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		// validate input
		if (info == null || info.getDateOfMenu() == null) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			return resp;
		}
		info.setDateOfMenu(validate.validateDate(info.getDateOfMenu()));
		try {
			int check = vt020004DAO.checkMenuExist(info);
			if (check > Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
				return resp;
			} else if (check == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				return resp;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional
	public ResponseEntityBase insertMenu(VT020004InfoToInsertMenu param, String userName, Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			VT020004Menu info = new VT020004Menu();
			info.setListDish(param.getListDish());
			info.setKitchenID(param.getKitchenID());
			info.setChefID(userName);
			// convert dayOfMenu to Date
			info.setDateOfMenu(validate.validateDate(param.getDateOfMenu()));
			// validate input
			if (param.getDateOfMenu() == null || param.getListDish().isEmpty()) {
				resp.setStatus(0);
				return resp;
			}
			
			// check kitchen exist
			VT020004InfoToFindKitchen infoToFindKitchen = new VT020004InfoToFindKitchen();
			infoToFindKitchen.setUserName(userName);
			infoToFindKitchen.setKitchenID(info.getKitchenID());
			infoToFindKitchen.setDeleteFG(Constant.DELETE_FG_ACTIVE);
			infoToFindKitchen.setStatus(Constant.STATUS_ACTIVE);
			int checkKitchen = vt020004DAO.checkKitchenExisted(infoToFindKitchen);
			if (checkKitchen == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			
			// get system date
			Calendar sysDate = Calendar.getInstance();
			//add 1 day to system date to compare condition date of menu old >= today + 2
			sysDate.add(Calendar.DAY_OF_MONTH, 0);
			// check copyFlag, if=1 && date of menu old >= today + 2 => delete old menu
			if(param.getCopyFlag() == 1) {
				VT020004InfoToFindDishInMenu infoToDeleteMenuOld = new VT020004InfoToFindDishInMenu(param.getDateOfMenuOld(),
						info.getChefID(), info.getKitchenID());
				// if date old < today + 2 => no delete menu old
				// else delete menu old
				if(validate.validateDate(param.getDateOfMenuOld()).compareTo(validate.validateDate(sysDate.getTime())) > 0) {
					// delete menu old
					vt020004DAO.deleteMenuByday(infoToDeleteMenuOld);
				}
			}
			
			// check menu date and system date
			// just change for today + 2 (if today + 2 => return error)
			if (validate.validateDate(param.getDateOfMenu()).compareTo(validate.validateDate(sysDate.getTime())) <= 0) {
				resp.setStatus(Constant.RESPONSE_STATUS_NO_ROLE);
				return resp;
			}
			
			// check date of menu exist in date or not
			VT020004InfoToFindDishInMenu infoFindDish = new VT020004InfoToFindDishInMenu(info.getDateOfMenu(),
					info.getChefID(), info.getKitchenID());
			int check = vt020004DAO.checkMenuExist(infoFindDish);
			if (check == Constant.NONE_EXIST_RECORD) { // not exist
				vt020004DAO.inserMenu(info);
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			} else if (check > Constant.NONE_EXIST_RECORD) { // exist
				// delete old records
				vt020004DAO.deleteMenuByday(infoFindDish);
				// insert data
				vt020004DAO.inserMenu(info);
				resp.setStatus(Constant.RESPONSE_EXIST_SERVICE);
			} else { // check exist error
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

  @Override
  public List<VT020004Dish> findListDishByKitchen(VT020004InfoToFindDishByChefID info) {
    return vt020004DAO.findListDishByKitchen(info);
  }
}