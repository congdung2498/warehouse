//package com.viettel.vtnet360.vt05.vt050007.service;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Properties;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.viettel.vtnet360.vt00.common.Constant;
//import com.viettel.vtnet360.vt00.common.Notification;
//import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
//import com.viettel.vtnet360.vt00.common.Sms;
//import com.viettel.vtnet360.vt05.vt050000.entity.VT050000AdditionalInfoToSendNotify;
//import com.viettel.vtnet360.vt05.vt050007.dao.VT050007DAO;
//import com.viettel.vtnet360.vt05.vt050007.entity.VT050007DataResponse;
//import com.viettel.vtnet360.vt05.vt050007.entity.VT050007InfoToFindRequestWaitingChiefOfStaffApprove;
//import com.viettel.vtnet360.vt05.vt050007.entity.VT050007RequestParam;
//import com.viettel.vtnet360.vt05.vt050007.entity.VT050007RequestParamToFindData;
//import com.viettel.vtnet360.vt05.vt050007.entity.VT050007RequestParamToReject;
//
///**
// * @author DuyNK 04/10/2018
// */
//@Service
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//public class VT050007ServiceImpl implements VT050007Service {
//
//	private final Logger logger = Logger.getLogger(this.getClass());
//
//	@Autowired
//	private VT050007DAO vt050007DAO;
//
//	@Autowired
//	Notification notification;
//
//	@Autowired
//	Sms sms;
//
//	@Autowired
//	Properties notifyMessage;
//
//	@Autowired
//	Properties smsMessage;
//
//	@Override
//	@Transactional(readOnly = true)
//	public ResponseEntityBase findDataToApprove(VT050007RequestParamToFindData param, String userName,
//			Collection<GrantedAuthority> roleList) {
//		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
//		List<VT050007DataResponse> dataResponse = null;
//		try {
//			// check role if HCTCT => find by their place
//			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))) {
//				int placeID = vt050007DAO.findPlaceIDOfVptctUserName(userName);
//				param.setPlaceID(placeID);
//			}
//			// set param to select in db
//			VT050007InfoToFindRequestWaitingChiefOfStaffApprove info = new VT050007InfoToFindRequestWaitingChiefOfStaffApprove();
//			info.setPlaceID(param.getPlaceID());
//			info.setUnitID(param.getUnitID());
//			info.setPageNumber(param.getPageNumber() * param.getPageSize());
//			info.setPageSize(param.getPageSize());
//			if (StringUtils.isBlank(param.getMessage())) {
//				info.setMessage(null);
//			} else {
//				info.setMessage(param.getMessage());
//			}
//			info.setDateApprove(param.getDateApprove());
//			info.setStatus(Constant.STATIONERY_APPROVED_MANAGER_APPROVE);
//			// set info to find spending limit of unit
//			info.setmClass(Constant.STATIONERY_SPENDING_LIMIT_CLASS);
//			info.setmCode(Constant.STATIONERY_SPENDING_LIMIT_CODE);
//			// get in db
//			dataResponse = vt050007DAO.findRequestWaitingChiefOfStaffApprove(info);
//			resp.setData(dataResponse);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw (e);
//		}
//		return resp;
//	}
//
//	@Override
//	@Transactional
//	public ResponseEntityBase approveIssuesStationeryItems(VT050007RequestParam param) {
//		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
//		try {
//			// update status & info to ISSUES_STATIONERY_APPROVED
//			param.setStatus(Constant.STATIONERY_APPROVED_WAIT_CHIEF_OF_STAFF_APPROVE);
//			vt050007DAO.updateIssuesStationeryApprove(param);
//			// update status & info to ISSUES_STATIONERY_ITEMS
//			param.setStatus(Constant.STATIONERY_WAIT_CHIEF_OF_STAFF_APPROVE);
//			vt050007DAO.updateIssuesStationeryItems(param);
//			// set data send client
//			resp.setStatus(Constant.SUCCESS);
//			try {
//				// send sms and notify
//				sendSmsAndNotifyToCVP(param.getApproveUserName(), param.getVptctUserName(),
//						Constant.STATIONERY_APPROVED_WAIT_CHIEF_OF_STAFF_APPROVE);
//			} catch (Exception e) {
//				logger.error(e.getMessage(), e);
//				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw (e);
//		}
//		return resp;
//	}
//
//	@Override
//	@Transactional
//	public ResponseEntityBase rejectIssuesStationeryItems(VT050007RequestParamToReject param) {
//		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
//		try {
//			// update status & info to ISSUES_STATIONERY_APPROVED
//			param.setStatus(Constant.STATIONERY_APPROVED_VPTCT_NOT_SEND);
//			vt050007DAO.rejectIssuesStationeryApprove(param);
//			// update status & info to ISSUES_STATIONERY_ITEMS
//			VT050007RequestParam paramToUpDateIssuesStationeryItem = new VT050007RequestParam();
//			paramToUpDateIssuesStationeryItem.setListRequestID(param.getListRequestID());
//			paramToUpDateIssuesStationeryItem.setVptctUserName(param.getVptctUserName());
//			paramToUpDateIssuesStationeryItem.setStatus(Constant.STATIONERY_VPTCT_NOT_SEND);
//			vt050007DAO.updateIssuesStationeryItems(paramToUpDateIssuesStationeryItem);
//			// set data send client
//			resp.setStatus(Constant.SUCCESS);
//			try {
//				// send sms and notify
////				sendSmsAndNotify(param.getApproveUserName(), param.getVptctUserName(),
////						Constant.STATIONERY_APPROVED_WAIT_CHIEF_OF_STAFF_APPROVE);
//			} catch (Exception e) {
//				logger.error(e.getMessage(), e);
//				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw (e);
//		}
//		return resp;
//	}
//
//	@Override
//	public void sendSmsAndNotifyToCVP(String approveUserName, String userName, int statusRecord) throws Exception {
//		Thread notiThread = new Thread() {
//			public void run() {
//				try {
//					// send notify
//					String toUserName = approveUserName;
//					VT050000AdditionalInfoToSendNotify addInfo = new VT050000AdditionalInfoToSendNotify(null,
//							Constant.PMQT_ROLE_MANAGER_CVP, Constant.PMQT_ROLE_STAFF_HCVP_VPP);
//					String additionalInformation = addInfo.toString();
//					String message = notifyMessage.getProperty("N35");
//					String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
//					int type = Constant.TYPE_NOTIFY_MODUL05;
//					String createUser = userName;
//					int status = statusRecord;
//					notification.sendNotification(toUserName, additionalInformation, message, title, type, createUser,
//							status);
//					// send sms
//					String content = smsMessage.getProperty("S35");
//					int statusSms = Constant.STATUS_NEW_SMS;
//					String smsType = smsMessage.getProperty("TYPE_SMS_MODUL05");
//					sms.sendSms(toUserName, content, statusSms, smsType);
//				} catch (Exception e) {
//					logger.error(e.getMessage(), e);
//					logger.info("****************************THREAD NOTI ERROR********:");
//				}
//			}
//		};
//		notiThread.start();
//	}
//}