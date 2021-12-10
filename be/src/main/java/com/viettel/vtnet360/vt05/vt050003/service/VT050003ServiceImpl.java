package com.viettel.vtnet360.vt05.vt050003.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.stationery.request.dao.StationeryReportDAO;
import com.viettel.vtnet360.stationery.service.StationeryReportService;
import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;
import com.viettel.vtnet360.vt05.vt050000.dao.VT050000DAO;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000AdditionalInfoToSendNotify;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationery;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000StationeryInfo;
import com.viettel.vtnet360.vt05.vt050000.service.VT050000Service;
import com.viettel.vtnet360.vt05.vt050003.dao.VT050003DAO;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003InfoToCheckPlaceExist;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003InfoToFindHcdv;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003IssuesStationeryItemsToInsert;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003IssuesStationeryItemsToInsertEmployee;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003IssuesStationeryItemsToInsertForm;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003RequestParam;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003RequestParamEmployee;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003RequestParamForm;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003StationeryParam;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003StationeryParamForm;
import com.viettel.vtnet360.vt05.vt050012.entity.VT050012DataResponseQuota;

/**
 * @author DuyNK 04/10/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050003ServiceImpl implements VT050003Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050000Service vt050000Service;
	
	@Autowired
	private VT050003DAO vt050003DAO;

	@Autowired
	private VT050000DAO vt050000DAO;
	
	@Autowired
	VT000000DAO vt000000DAO;
	
	@Autowired
	private StationeryReportDAO stationeryReportDAO;
	
	@Autowired
	Notification notification;

	@Autowired
	Sms sms;

	@Autowired
	Properties notifyMessage;

	@Autowired
	Properties smsMessage;
	
	
	@Override
	@Transactional
	public ResponseEntityBase insertIssuesStationery(VT050003RequestParam requestParam, String userName,
			Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// check validate input
			if (requestParam.getListStationery() == null || requestParam.getListStationery().isEmpty()
					|| requestParam.getPlaceID() <= 0) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			String path = stationeryReportDAO.getUnitPathByUserName(userName);
			
			
			double spendingLimit = 0;

			String[] unitIds = path.split("/");

			Collections.reverse(Arrays.asList(unitIds));
			for(String unit : unitIds) {
				spendingLimit = vt050000DAO.findSpendingLimit(Integer.parseInt(unit));
				if(spendingLimit > 0) {
					break;
				}
			}
			
			VT050012DataResponseQuota responseQuota = new VT050012DataResponseQuota();
			// check spending limit
			// get total price request
			List<VT050003StationeryParam> listStationery = requestParam.getListStationery();
			List<String> listStationeryID = new ArrayList<>();
			for (int i = 0; i < listStationery.size(); i++) {
				listStationeryID.add(listStationery.get(i).getStationeryID());
			}
			List<VT050000StationeryInfo> listPrice = vt050003DAO.findStationeryPrice(listStationeryID);

			double totalPrice = 0;
			for (int i = 0; i < listStationery.size(); i++) {
				for (int j = 0; j < listPrice.size(); j++) {
					if (listStationery.get(i).getStationeryID().equals(listPrice.get(j).getStationeryID())) {
						totalPrice += listStationery.get(i).getQuantity() * listPrice.get(j).getUnitPrice();
					}
				}
			}
			if (!roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))) {
				// get remaining spending limit
				double remainingSpendingLimit = vt050000Service.caculRemainingSpendingLimit(userName, roleList);
//				double remainingSpendingLimit =	stationeryReportService.findSpendingLimitQuota(userName,roleList);
				// if total price request > Remaining spending limit => no insert
				if (totalPrice > remainingSpendingLimit) {
					resp.setStatus(Constant.STATIONERY_RESPONSE_SPENDING_LIMIT_EXCEED);
					return resp;
				}
			}

			// check validate placeID
			VT050003InfoToCheckPlaceExist info = new VT050003InfoToCheckPlaceExist(requestParam.getPlaceID(),
					Constant.STATUS_ACTIVE);
			int checkPlaceID = vt050003DAO.checkPlaceExist(info);
			if (checkPlaceID != Constant.SUCCESS) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			// insert to ISSUES_STATIONERY
			VT050000IssuesStationery issuesStationery = new VT050000IssuesStationery(null, userName,
					requestParam.getPlaceID(), requestParam.getNote(), null, Constant.ISSUES_STATIONERY_PROCESSING, 0,
					null);
			issuesStationery.setCreateUser(userName);
			vt050003DAO.insertIssuesStationery(issuesStationery);
			// get ISSUE_STATIONERY_ID in last ISSUES_STATIONERY of this user to insert to
			// ISSUES_STATIONERY_ITEMS
			String issuesStationeryID = vt050003DAO.findIssuesStationeryID(userName);
			// insert to ISSUES_STATIONERY_ITEMS
			VT050003IssuesStationeryItemsToInsert issuesStationeryItemsToInsert = new VT050003IssuesStationeryItemsToInsert();
			issuesStationeryItemsToInsert.setIssuesStationeryID(issuesStationeryID);
			issuesStationeryItemsToInsert.setStatus(Constant.STATIONERY_CREATE);
			issuesStationeryItemsToInsert.setCreateUser(userName);
			issuesStationeryItemsToInsert.setListStationery(requestParam.getListStationery());
			vt050003DAO.insertIssuesStationeryItems(issuesStationeryItemsToInsert);
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			// set response to client
			// send sms and notify to HCDV
			try {
				sendSmsAndNotify(userName, requestParam.getPlaceID(), issuesStationeryID, Constant.STATIONERY_CREATE);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public void sendSmsAndNotify(String userName, int placeID, String idRecord, int statusRecord) throws Exception {
		Thread notiThread = new Thread() {
			public void run() {
				try {
					VT050003InfoToFindHcdv info = new VT050003InfoToFindHcdv();
					info.setUserName(userName);
					info.setPlaceID(placeID);
					info.setJobCode(Constant.STATIONERY_HCDV_CODE);
					List<String> listHcdvUserName = vt050003DAO.findHcdvUserName(info);

					// find full name of employee request VPP
					String fullName = vt050000DAO.findFullNameByUserName(userName);
					for (String hcdvUserName : listHcdvUserName) {
						// send notify
						String toUserName = hcdvUserName;
						VT050000AdditionalInfoToSendNotify addInfo = new VT050000AdditionalInfoToSendNotify(idRecord,
								Constant.PMQT_ROLE_STAFF_HC_DV, Constant.PMQT_ROLE_EMPLOYYEE);
						String additionalInformation = addInfo.toString();
						String message = notifyMessage.getProperty("N35_1");
						message = MessageFormat.format(message, fullName);
						String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
						int type = Constant.TYPE_NOTIFY_MODUL05;
						String createUser = userName;
						int status = statusRecord;
						notification.sendNotification(toUserName, additionalInformation, message, title, type,
								createUser, status);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************THREAD NOTI ERROR********:");
				}
			}

		};
		notiThread.start();
	}

	@Override
	public ResponseEntityBase insertIssuesStationeryForm(VT050003RequestParamForm requestParam, String userName,
			Collection<GrantedAuthority> roleList) {
		

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// check validate input
			if (requestParam.getListStationery() == null || requestParam.getListStationery().isEmpty()
					|| requestParam.getPlaceID() <= 0) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}

			// check spending limit
			// get total price request
			List<VT050003StationeryParamForm> listStationery = requestParam.getListStationery();
			List<String> listStationeryID = new ArrayList<>();
			for (int i = 0; i < listStationery.size(); i++) {
				listStationeryID.add(listStationery.get(i).getStationeryID());
			}
			

			double totalPrice = 0;
			for (int i = 0; i < listStationery.size(); i++) {
				
					
						totalPrice += listStationery.get(i).getQuantity() * listStationery.get(i).getUnitPrice();
					
				
			}
			// get remaining spending limit
			double remainingSpendingLimit = vt050000Service.caculRemainingSpendingLimit(userName, roleList);

			// if total price request > Remaining spending limit => no insert
			if (totalPrice > remainingSpendingLimit) {
				resp.setStatus(Constant.STATIONERY_RESPONSE_SPENDING_LIMIT_EXCEED);
				return resp;
			}

			// check validate placeID
			VT050003InfoToCheckPlaceExist info = new VT050003InfoToCheckPlaceExist(requestParam.getPlaceID(),
					Constant.STATUS_ACTIVE);
			int checkPlaceID = vt050003DAO.checkPlaceExist(info);
			if (checkPlaceID != Constant.SUCCESS) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			// insert to ISSUES_STATIONERY
			VT050000IssuesStationery issuesStationery = new VT050000IssuesStationery(null, userName,
					requestParam.getPlaceID(), requestParam.getNote(), null, Constant.ISSUES_STATIONERY_PROCESSING, 0,
					null);
			issuesStationery.setCreateUser(userName);
			vt050003DAO.insertIssuesStationery(issuesStationery);
			// get ISSUE_STATIONERY_ID in last ISSUES_STATIONERY of this user to insert to
			// ISSUES_STATIONERY_ITEMS
			String issuesStationeryID = vt050003DAO.findIssuesStationeryID(userName);
			// insert to ISSUES_STATIONERY_ITEMS
			VT050003IssuesStationeryItemsToInsertForm issuesStationeryItemsToInsert = new VT050003IssuesStationeryItemsToInsertForm();
			issuesStationeryItemsToInsert.setIssuesStationeryID(issuesStationeryID);
			issuesStationeryItemsToInsert.setStatus(Constant.STATIONERY_CREATE);
			issuesStationeryItemsToInsert.setCreateUser(userName);
			issuesStationeryItemsToInsert.setListStationery(requestParam.getListStationery());
			vt050003DAO.insertIssuesStationeryItemsForm(issuesStationeryItemsToInsert);
			// set response to client
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			// send sms and notify to HCDV
			try {
				sendSmsAndNotify(userName, requestParam.getPlaceID(), issuesStationeryID, Constant.STATIONERY_CREATE);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	

	@Override
	public ResponseEntityBase insertIssuesStationeryItemsEmployee(VT050003RequestParamEmployee requestParam, String userName,
			Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// check validate input
			if (requestParam.getListStationery() == null || requestParam.getPlaceID() <= 0) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}

			double spendingLimit = 0;
			String path = stationeryReportDAO.getUnitPathByUserName(userName);

			String[] unitIds = path.split("/");

			Collections.reverse(Arrays.asList(unitIds));
			for(String unit : unitIds) {
				spendingLimit = vt050000DAO.findSpendingLimit(Integer.parseInt(unit));
				if(spendingLimit > 0) {
					break;
				}
			}
			// set response to client
			if(Math.round(spendingLimit) == 0  ) {
				resp.setStatus(Constant.UNIT_NOT_EXIST_QUOTA);
				return resp;
			} 
			// check spending limit
			// get total price request
			VT050003StationeryParam listStationery = requestParam.getListStationery();
			List<String> listStationeryID = new ArrayList<>();
			
			listStationeryID.add(listStationery.getStationeryID());
			
			// check validate placeID
			VT050003InfoToCheckPlaceExist info = new VT050003InfoToCheckPlaceExist(requestParam.getPlaceID(),
					Constant.STATUS_ACTIVE);
			int checkPlaceID = vt050003DAO.checkPlaceExist(info);
			if (checkPlaceID != Constant.SUCCESS) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			// insert to ISSUES_STATIONERY
			VT050000IssuesStationery issuesStationery = new VT050000IssuesStationery(null, userName,
					requestParam.getPlaceID(), requestParam.getNote(), null, Constant.ISSUES_STATIONERY_PROCESSING, 0,
					null);
			issuesStationery.setCreateUser(userName);
			vt050003DAO.insertIssuesStationery(issuesStationery);
			// get ISSUE_STATIONERY_ID in last ISSUES_STATIONERY of this user to insert to
			// ISSUES_STATIONERY_ITEMS
			String issuesStationeryID = vt050003DAO.findIssuesStationeryID(userName);
			// insert to ISSUES_STATIONERY_ITEMS
			VT050003IssuesStationeryItemsToInsertEmployee issuesStationeryItemsToInsert = new VT050003IssuesStationeryItemsToInsertEmployee();
			issuesStationeryItemsToInsert.setIssuesStationeryID(issuesStationeryID);
			issuesStationeryItemsToInsert.setStatus(Constant.STATIONERY_CREATE);
			issuesStationeryItemsToInsert.setCreateUser(userName);
			issuesStationeryItemsToInsert.setCalculationUnit(requestParam.getListStationery().getCalculationUnit());
			issuesStationeryItemsToInsert.setStationeryID(requestParam.getListStationery().getStationeryID());
			issuesStationeryItemsToInsert.setStationeryName(requestParam.getListStationery().getStationeryName());
			issuesStationeryItemsToInsert.setQuantity(requestParam.getListStationery().getQuantity());
			issuesStationeryItemsToInsert.setUnitPrice(requestParam.getListStationery().getUnitPrice());
			
			vt050003DAO.insertIssuesStationeryItemsEmployee(issuesStationeryItemsToInsert);
			
			try {
				sendSmsAndNotify(userName, requestParam.getPlaceID(), issuesStationeryID, Constant.STATIONERY_CREATE);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}
}