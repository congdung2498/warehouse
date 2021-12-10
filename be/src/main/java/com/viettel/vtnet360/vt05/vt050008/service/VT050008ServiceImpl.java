//package com.viettel.vtnet360.vt05.vt050008.service;
//
//import java.text.MessageFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
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
//import com.viettel.vtnet360.vt05.vt050000.dao.VT050000DAO;
//import com.viettel.vtnet360.vt05.vt050000.entity.VT050000AdditionalInfoToSendNotify;
//import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoHcdvRequest;
//import com.viettel.vtnet360.vt05.vt050000.service.VT050000Service;
//import com.viettel.vtnet360.vt05.vt050008.dao.VT050008DAO;
//import com.viettel.vtnet360.vt05.vt050008.entity.VT050008DataResponse;
//import com.viettel.vtnet360.vt05.vt050008.entity.VT050008RequestParamToApprove;
//import com.viettel.vtnet360.vt05.vt050008.entity.VT050008RequestParamToSearch;
//
///**
// * @author DuyNK 04/10/2018
// */
//@Service
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//public class VT050008ServiceImpl implements VT050008Service {
//
//	private final Logger logger = Logger.getLogger(this.getClass());
//
//	@Autowired
//	private VT050000DAO vt050000DAO;
//
//	@Autowired
//	private VT050008DAO vt050008DAO;
//
//	@Autowired
//	private VT050000Service vt050000Service;
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
//	public ResponseEntityBase findDataToApprove(VT050008RequestParamToSearch param,
//			Collection<GrantedAuthority> roleList) {
//		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
//		List<VT050008DataResponse> dataResponse = null;
//		try {
//			// validate input
//			if (StringUtils.isBlank(param.getVptctUserName())) {
//				param.setVptctUserName(null);
//			}
//			param.setPageNumber(param.getPageNumber() * param.getPageSize());
//			// set info to find spending limit of unit
//			param.setmClass(Constant.STATIONERY_SPENDING_LIMIT_CLASS);
//			param.setmCode(Constant.STATIONERY_SPENDING_LIMIT_CODE);
//			// search record status = 3 => to approve
//			param.setStatus(Constant.STATIONERY_APPROVED_WAIT_CHIEF_OF_STAFF_APPROVE);
//			// if user is admin => search all
//			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
//				param.setRoleAdmin(true);
//			}
//			// find in db
//			dataResponse = vt050008DAO.findIssuesStationeryApprove(param);
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
//	public ResponseEntityBase approveOrReject(VT050008RequestParamToApprove param) {
//		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
//		try {
//			// validate input
//			if (param == null || param.getListRequestID().isEmpty()) {
//				resp.setStatus(Constant.ERROR);
//				return resp;
//			}
//			// approve request
//			if (param.getAction() == Constant.STATIONERY_ACTION_APPROVE) {
//				// update status & info to ISSUES_STATIONERY_APPROVED
//				param.setStatus(Constant.STATIONERY_APPROVED_CHIEF_OF_STAFF_APPROVE);
//				param.setStatusNow(Constant.STATIONERY_APPROVED_WAIT_CHIEF_OF_STAFF_APPROVE);
//				param.setReasonReject(null);
//				vt050008DAO.updateIssuesStationeryApprove(param);
//				// update status & info to ISSUES_STATIONERY_ITEMS
//				param.setStatus(Constant.STATIONERY_CHIEF_OF_STAFF_APPROVE);
//				param.setStatusNow(Constant.STATIONERY_WAIT_CHIEF_OF_STAFF_APPROVE);
//				vt050008DAO.updateIssuesStationeryItems(param);
//				// set data response client
//				resp.setStatus(Constant.SUCCESS);
//				try {
//					// send sms and notify to employee
//					sendSmsAndNotifyToHCDV(true, param.getCvpUserName(), param.getListRequestID());
//					// send sms and notify to VPTCT
//					sendSmsAndNotifyToVPTCT(param.getCvpUserName(), param.getListRequestID());
//				} catch (Exception e) {
//					logger.error(e.getMessage(), e);
//					resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
//				}
//			} else if (param.getAction() == Constant.STATIONERY_ACTION_REJECT) { // reject request
//				// update status & info to ISSUES_STATIONERY_APPROVED
//				param.setStatus(Constant.STATIONERY_APPROVED_CHIEF_OF_STAFF_REJECT);
//				param.setStatusNow(Constant.STATIONERY_APPROVED_WAIT_CHIEF_OF_STAFF_APPROVE);
//				vt050008DAO.updateIssuesStationeryApprove(param);
//				// update status & info to ISSUES_STATIONERY_ITEMS
//				param.setStatus(Constant.STATIONERY_CHIEF_OF_STAFF_REJECT);
//				param.setStatusNow(Constant.STATIONERY_WAIT_CHIEF_OF_STAFF_APPROVE);
//				vt050008DAO.updateIssuesStationeryItems(param);
//				// update status in ISSUES_STATIONERY
//				List<String> listIssuesStationeryID = vt050008DAO.findListIssuesStationeryID(param.getListRequestID());
//				for (int i = 0; i < listIssuesStationeryID.size(); i++) {
//					String issuesStationeryID = listIssuesStationeryID.get(i);
//					vt050000Service.changeStatusIssuesStationery(issuesStationeryID, param.getCvpUserName());
//				}
//				// set data response client
//				resp.setStatus(Constant.SUCCESS);
//				try {
//					// send sms and notify to employee
//					sendSmsAndNotifyToHCDV(false, param.getCvpUserName(), param.getListRequestID());
//				} catch (Exception e) {
//					logger.error(e.getMessage(), e);
//					resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
//				}
//			} else {
//				resp.setStatus(Constant.ERROR);
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw (e);
//		}
//		return resp;
//	}
//
//	@Override
//	public void sendSmsAndNotifyToHCDV(Boolean approveStatus, String cvpUserName,
//			List<String> listIssuesStationeryApproveID) throws Exception {
//		Thread notiThread = new Thread() {
//			public void run() {
//				try {
//					// find list hcdv request
//					List<VT050000InfoHcdvRequest> listInfo = new ArrayList<>();
//					listInfo = vt050000DAO.findInfoHcdvRequest(listIssuesStationeryApproveID);
//					for (int i = 0; i < listIssuesStationeryApproveID.size(); i++) {
//						VT050000InfoHcdvRequest info = listInfo.get(i);
//						// send notify
//						String toUserName = info.getHcdvUserName();
//						VT050000AdditionalInfoToSendNotify addInfo = new VT050000AdditionalInfoToSendNotify(
//								listIssuesStationeryApproveID.get(i), Constant.PMQT_ROLE_STAFF_HC_DV,
//								Constant.PMQT_ROLE_MANAGER_CVP);
//						String additionalInformation = addInfo.toString();
//						String message = "";
//						String content = "";
//						int status = 0;
//						if (approveStatus) {
//							message = notifyMessage.getProperty("N39");
//							content = notifyMessage.getProperty("S39");
//							status = Constant.STATIONERY_APPROVED_MANAGER_APPROVE;
//						} else {
//							message = notifyMessage.getProperty("N41");
//							content = notifyMessage.getProperty("S41");
//							status = Constant.STATIONERY_APPROVED_MANAGER_REJECT;
//						}
//						SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//						String dateRequest = df.format(info.getDateRequest());
//						message = MessageFormat.format(message, dateRequest, info.getHcdvMessage());
//						String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
//						int type = Constant.TYPE_NOTIFY_MODUL05;
//						String createUser = cvpUserName;
//						notification.sendNotification(toUserName, additionalInformation, message, title, type,
//								createUser, status);
//						// send sms
//						content = MessageFormat.format(message, dateRequest, info.getHcdvMessage());
//						int statusSms = Constant.STATUS_NEW_SMS;
//						String smsType = smsMessage.getProperty("TYPE_SMS_MODUL05");
//						sms.sendSms(toUserName, content, statusSms, smsType);
//					}
//				} catch (Exception e) {
//					logger.error(e.getMessage(), e);
//					logger.info("****************************THREAD NOTI ERROR********:");
//				}
//			}
//		};
//		notiThread.start();
//	}
//
//	@Override
//	public void sendSmsAndNotifyToVPTCT(String cvpUserName, List<String> listIssuesStationeryApproveID)
//			throws Exception {
//		Thread notiThread = new Thread() {
//			public void run() {
//				try {
//					// send notify
//					List<String> listVptctUserName = vt050008DAO.findVptctUserName(listIssuesStationeryApproveID);
//					for (int i = 0; i < listVptctUserName.size(); i++) {
//						String toUserName = listVptctUserName.get(i);
//						VT050000AdditionalInfoToSendNotify addInfo = new VT050000AdditionalInfoToSendNotify(null,
//								Constant.PMQT_ROLE_STAFF_HCVP_VPP, Constant.PMQT_ROLE_MANAGER_CVP);
//						String additionalInformation = addInfo.toString();
//						String message = notifyMessage.getProperty("N40");
//						String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
//						int type = Constant.TYPE_NOTIFY_MODUL05;
//						String createUser = cvpUserName;
//						int status = Constant.STATIONERY_MANAGER_APPROVE;
//						notification.sendNotification(toUserName, additionalInformation, message, title, type,
//								createUser, status);
//						// send sms
//						String content = smsMessage.getProperty("S40");
//						int statusSms = Constant.STATUS_NEW_SMS;
//						String smsType = smsMessage.getProperty("TYPE_SMS_MODUL05");
//						sms.sendSms(toUserName, content, statusSms, smsType);
//					}
//				} catch (Exception e) {
//					logger.error(e.getMessage(), e);
//					logger.info("****************************THREAD NOTI ERROR********:");
//				}
//			}
//		};
//		notiThread.start();
//	}
//}