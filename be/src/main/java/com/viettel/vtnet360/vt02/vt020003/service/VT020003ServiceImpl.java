package com.viettel.vtnet360.vt02.vt020003.service;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.InputValidate;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020003.dao.VT020003DAO;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003Chef;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003Condition;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003CookerParam;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003InfoToCheckConditionBeforeUpdateKitchen;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003InfoToInsertPhoneNumberReceiveSms;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003InfoToUpdatePriceInLunch;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003KitchenInfo;
import com.viettel.vtnet360.vt02.vt020003.entity.VT020003RequestQuery;

/**
 * Class serviceImpl for screen VT020003 : setting kitchen
 * 
 * @author DuyNK 10/09/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT020003ServiceImpl implements VT020003Service {
	private final Logger logger = Logger.getLogger(this.getClass());
	private InputValidate validate = new InputValidate();
	
	@Autowired
	private VT020003DAO vt020003DAO;
	
	@Autowired
	private VT020003ExcelOutputService vt020003ExcelOutputService;
	/**
     * Select all kitchen place( from QLDV_PLACE) 
     * 
     * @param
     * @return ResponseEntityBase(status, List<VT020003KitchenPlace>)
     */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findKitchenPlace(VT020003RequestQuery query) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS,null);
		List<VT020003Condition> listKitchenPlace = null;
		try {
			listKitchenPlace = vt020003DAO.findListKitchenPlace(query);
			resp.setData(listKitchenPlace);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	/**
     * Select all list chef(QLDV_EMPLOYEE where `ROLE` = 'PMQT_Bep_truong')
     * 
     * @param
     * @return ResponseEntityBase(status, List<VT020003KitchenPlace>)
     */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListChef(VT020003RequestQuery query) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS,null);
		List<VT020003Chef> listChef = null;
		try {
			String role = "%" + Constant.PMQT_ROLE_CHEF + "%";
			String search = "%" + query.getQuery() + "%";
			VT020003CookerParam cookerParam = new VT020003CookerParam(search, role);
			listChef = vt020003DAO.findListChef(cookerParam);
			resp.setData(listChef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	/**
     * Select all kitchenInfo(KITCHEN_SETTING)
     * 
     * @param
     * @return ResponseEntityBase(status, List<VT020003KitchenInfo>)
     */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListKitchenInfo(VT020003Condition condition) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS,null);
		List<VT020003KitchenInfo> listKitchenInfo = null;
		try {
			listKitchenInfo = vt020003DAO.findListKitchenInfo(condition);
			//add list phone number of who is configed to receive Sms to data response
			if(listKitchenInfo != null && !listKitchenInfo.isEmpty()) {
				for (int i = 0; i < listKitchenInfo.size(); i++) {
					List<String> listPhoneNumber = vt020003DAO.findListPhoneNumberReceiveSms(listKitchenInfo.get(i).getKitchenID());
					listKitchenInfo.get(i).setListPhoneNumberReceiveSms(listPhoneNumber);
				}
			}
			resp.setData(listKitchenInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase countTotalRecord(VT020003Condition condition) {
		int totalRecord = -1;
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR,totalRecord);
		try {
			totalRecord = vt020003DAO.countTotalRecord(condition);
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			resp.setData(totalRecord);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public File exportListKitchenInfo(VT020003Condition condition) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS,null);
		List<VT020003KitchenInfo> listKitchenInfo = null;
		File file = null;
		try {
			condition.setStartRow(-1);
			listKitchenInfo = vt020003DAO.findListKitchenInfo(condition);
			resp.setData(listKitchenInfo);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			return file;
		}
		
		try {
			file = vt020003ExcelOutputService.createExcelOutputExcel(listKitchenInfo);
			resp.setData(listKitchenInfo);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			return file;
		}
		return file;
	}

	/**
     * Insert new kitchen to KITCHEN_SETTING
     * 
     * @param VT020003KitchenInfo
     * @return ResponseEntityBase
     */
	@Override
	@Transactional
	public ResponseEntityBase insertKitchen(VT020003KitchenInfo kitchenInfo, String userName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS,null);

		//check validate input kitchen name not null or ""
		if(kitchenInfo == null) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			return resp;
		}
		if(!validate.validateWord(kitchenInfo.getKitchenName(), 255)) {
			resp.setStatus(Constant.RESPONSE_ERROR_VALIDATE);
			return resp;
		}
//		if(StringUtils.isBlank(kitchenInfo.getChefUserName()) || StringUtils.isBlank(kitchenInfo.getChefName())) {
//			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
//			return resp;
//		}
		
		try {
			//check kitchenName existed or not
		  int checkKitchenName = vt020003DAO.checkKitchenNameExist(new VT020003KitchenInfo(null, kitchenInfo.getKitchenName(), kitchenInfo.getPlaceID(), 
		      null, null, kitchenInfo.getChefUserName(), null, null, 0, 0, Constant.DELETE_FG_ACTIVE, null));
		  if(checkKitchenName > 0) {
		    resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
		    return resp;
		  }
			
			//set createUser by tokken
			kitchenInfo.setCreateUser(userName);
			kitchenInfo.setDeleteFG(Constant.DELETE_FG_ACTIVE);		
			//insert to database
			int i = vt020003DAO.insertKitchen(kitchenInfo);
			if(i == Constant.SUCCESS) {
				String kitchenID = vt020003DAO.findKitchenIDJustAddNew(userName);
				if(kitchenInfo.getListPhoneNumberReceiveSms() != null && !kitchenInfo.getListPhoneNumberReceiveSms().isEmpty()){
					//insert to KITCHEN_PHONE_GET_SMS who configed phone number to receive sms
					VT020003InfoToInsertPhoneNumberReceiveSms info = new VT020003InfoToInsertPhoneNumberReceiveSms();
					info.setKitchenID(kitchenID);
					info.setListPhoneNumber(kitchenInfo.getListPhoneNumberReceiveSms());
					info.setUserName(userName);
					vt020003DAO.insertPhoneNumberReceiveSms(info);
				}
				
				//insert chef
				kitchenInfo.setKitchenID(kitchenID);
				vt020003DAO.insertChef(kitchenInfo);
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}else {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;		
	}

	/**
     * Update kitchen to KITCHEN_SETTING
     * 
     * @param VT020003KitchenInfo
     * @return ResponseEntityBase
     */
	@Override
	@Transactional
	public ResponseEntityBase updateKitchen(VT020003KitchenInfo kitchenInfo, String userName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS,null);
		
		//check validate input kitchen name not null or ""
		if(kitchenInfo == null) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			return resp;
		}
		if(!validate.validateWord(kitchenInfo.getKitchenName(), 255)) {
			resp.setStatus(Constant.RESPONSE_ERROR_VALIDATE);
			return resp;
		}
		if(kitchenInfo.getPrice() < 0) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			return resp;
		}
	
		//check condition before update kitchen ( check DELETE_FLAG)
    VT020003InfoToCheckConditionBeforeUpdateKitchen infoCheck = new VT020003InfoToCheckConditionBeforeUpdateKitchen();
    infoCheck.setKitchenID(kitchenInfo.getKitchenID());
    infoCheck.setDeleteFG(Constant.DELETE_FG_ACTIVE);
    int checkCondition = vt020003DAO.checkConditionBeforeUpdateKitchen(infoCheck);
    if(checkCondition == Constant.NONE_EXIST_RECORD) {
      resp.setStatus(Constant.ERROR_REQUEST_INVALID_KITCHEN_SETTING);
      return resp;
    }
		
		try {
			//check PLACE_ID existed or not
			int checkPlaceID = vt020003DAO.checPlaceIdExist(kitchenInfo.getPlaceID());
			if(checkPlaceID == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.RESPONSE_PLACE_NOT_EXIST);
				return resp;
			}
			
			//check kitchenName existed or not
      int checkKitchenName = vt020003DAO.checkKitchenNameExist(kitchenInfo);
      if(checkKitchenName > 0) {
        resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
        return resp;
      }
	
			//set updateUser by tokken
			kitchenInfo.setUpdateUser(userName);
		
			//update to database
			int i = vt020003DAO.updateKitchen(kitchenInfo);
			if(i == Constant.SUCCESS) {
				//delete chef
				vt020003DAO.deleteChef(kitchenInfo.getKitchenID());
				//insert chef
				vt020003DAO.insertChef(kitchenInfo);
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			} else {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			}
			//delete in KITCHEN_PHONE_GET_SMS who configed phone number to receive sms
			vt020003DAO.deletePhoneNumberReceiveSms(kitchenInfo.getKitchenID());
			if(kitchenInfo.getListPhoneNumberReceiveSms() != null && !kitchenInfo.getListPhoneNumberReceiveSms().isEmpty()){
				//update to KITCHEN_PHONE_GET_SMS who configed phone number to receive sms
				VT020003InfoToInsertPhoneNumberReceiveSms infoToInsertPhoneNumberReceiveSms = new VT020003InfoToInsertPhoneNumberReceiveSms();
				infoToInsertPhoneNumberReceiveSms.setKitchenID(kitchenInfo.getKitchenID());
				infoToInsertPhoneNumberReceiveSms.setListPhoneNumber(kitchenInfo.getListPhoneNumberReceiveSms());
				infoToInsertPhoneNumberReceiveSms.setUserName(userName);
				vt020003DAO.insertPhoneNumberReceiveSms(infoToInsertPhoneNumberReceiveSms);
			}
			//update price in LUNCH_CALENDAR. no update for tomorrow if > 16h30
			Calendar sysDate = Calendar.getInstance();
			if(sysDate.get(Calendar.HOUR_OF_DAY) > 16 || (sysDate.get(Calendar.HOUR_OF_DAY) == 16 && sysDate.get(Calendar.MINUTE) > 30)) {
				sysDate.add(Calendar.DAY_OF_MONTH, 1);
			}
			VT020003InfoToUpdatePriceInLunch info = new VT020003InfoToUpdatePriceInLunch(kitchenInfo.getPrice(), kitchenInfo.getKitchenID(), sysDate.getTime());
			vt020003DAO.updatePriceInLunchCalendar(info);
			if(kitchenInfo.getStatus() == Constant.STATUS_INACTIVE) {
				vt020003DAO.deleteLunchDate(kitchenInfo.getKitchenID());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;		
	}
	
	/**
     * delete kitchen in KITCHEN_SETTING by KITCHEN_ID
     * 
	 * @param kitchenID
	 * @param userName
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase deleteKitchen(String kitchenID, String userName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS,null);
		try {
			//check condition before update kitchen ( check DELETE_FLAG)
			VT020003InfoToCheckConditionBeforeUpdateKitchen infoCheck = new VT020003InfoToCheckConditionBeforeUpdateKitchen();
			infoCheck.setKitchenID(kitchenID);
			infoCheck.setDeleteFG(Constant.DELETE_FG_ACTIVE);
			int checkCondition = vt020003DAO.checkConditionBeforeUpdateKitchen(infoCheck);
			if(checkCondition == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.ERROR_REQUEST_INVALID_KITCHEN_SETTING);
				return resp;
			}
			//delete kitchen (delete logic)
			VT020003KitchenInfo kitchenInfo = new VT020003KitchenInfo();
			kitchenInfo.setKitchenID(kitchenID);
			kitchenInfo.setDeleteFG(Constant.DELETE_FG_INACTIVE);
			kitchenInfo.setUpdateUser(userName);
			int i = vt020003DAO.deleteKitchen(kitchenInfo);
			if(i == Constant.SUCCESS) {
				//delete in KITCHEN_PHONE_GET_SMS who configed phone number to receive sms
				vt020003DAO.deletePhoneNumberReceiveSms(kitchenID);
				//delete lunch date
				vt020003DAO.deleteLunchDate(kitchenID);
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			} else {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findListChef2(VT020003RequestQuery query) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS,null);
		List<VT020003Chef> listChef = null;
		try {
			String role = "%" + Constant.PMQT_ROLE_CHEF + "%";
			String search = null;
			if(query.getQuery() != null && query.getQuery().length() > 0) search = "%" + query.getQuery() + "%";
			VT020003CookerParam cookerParam = new VT020003CookerParam(search, role);
			listChef = vt020003DAO.findListChef2(cookerParam);
			resp.setData(listChef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		
		return resp;
	}
}
