package com.viettel.vtnet360.vt05.vt050005.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt05.vt050000.dao.VT050000DAO;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000AdditionalInfoToSendNotify;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoHcdvRequest;
import com.viettel.vtnet360.vt05.vt050000.service.VT050000Service;
import com.viettel.vtnet360.vt05.vt050005.dao.VT050005DAO;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005DataResponse;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005InfoToUpdateIssuesStationeryApproveStatus;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005InfoUpdateIssuesStationeryItemStatus;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005PlaceIDOfIssuesStationeryApprove;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005RequestParamToApprove;
import com.viettel.vtnet360.vt05.vt050005.entity.VT050005RequestParamToSearch;
import com.viettel.vtnet360.vt05.vt050012.dao.VT050012DAO;

/**
 * @author DuyNK 04/10/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050005ServiceImpl implements VT050005Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050005DAO vt050005DAO;

	@Autowired
	private VT050000DAO vt050000DAO;

	@Autowired
	private VT050000Service vt050000Service;

	@Autowired
	private VT050012DAO vt050012DAO;
	
	@Autowired
	Notification notification;

	@Autowired
	Sms sms;

	@Autowired
	Properties notifyMessage;

	@Autowired
	Properties smsMessage;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findDataToApprove(VT050005RequestParamToSearch param,
			Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT050005DataResponse> dataResponse = null;
		try {
			// validate input
			if (StringUtils.isBlank(param.getEmployeeUserName())) {
				param.setEmployeeUserName(null);
			}
			param.setPageNumber(param.getPageNumber() * param.getPageSize());
			// search record status = 0 => to approve
			// if user is admin => search all
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				param.setRoleAdmin(true);
			}
			// find in db
			dataResponse = vt050005DAO.findIssuesStationeryApprove(param);
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional
	public ResponseEntityBase approveOrReject(VT050005RequestParamToApprove param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// validate input
			if (param == null || param.getListRequestID().isEmpty()) {
				resp.setStatus(Constant.ERROR);
				return resp;
			}
			// check stationery date end limit
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			String limitDate = vt050012DAO.getLimitDateEnd();
			int foo;
			foo = Integer.parseInt(limitDate);
			if (day > foo) {
				resp.setStatus(Constant.DAY_IS_LIMIT);
				return resp;
			}
			
			// check condition record before update(status now of request valid or not)
			VT050005InfoToUpdateIssuesStationeryApproveStatus infoCheckCondition = new VT050005InfoToUpdateIssuesStationeryApproveStatus();
			infoCheckCondition.setListIssuesStationeryApproveID(param.getListRequestID());
			infoCheckCondition.setStatus(Constant.STATIONERY_APPROVED_WAIT_MANAGER_APPROVE);
			int checkCondition = vt050005DAO.checkConditionBeforeUpdateIssuesApprove(infoCheckCondition);
			if (checkCondition != param.getListRequestID().size()) {
				resp.setStatus(Constant.ERROR_REQUEST_INVALID);
				return resp;
			}
			// approve request
			if (param.getAction() == Constant.STATIONERY_ACTION_APPROVE) {
				// update status in ISSUES_STATIONERY_APPROVED
				VT050005InfoToUpdateIssuesStationeryApproveStatus infoToUpdateIssuesStationeryApproveStatus = new VT050005InfoToUpdateIssuesStationeryApproveStatus();
				infoToUpdateIssuesStationeryApproveStatus.setStatus(Constant.STATIONERY_APPROVED_MANAGER_APPROVE);
				infoToUpdateIssuesStationeryApproveStatus.setReasonReject(null);
				infoToUpdateIssuesStationeryApproveStatus.setUpdateUser(param.getApproveUserName());
				infoToUpdateIssuesStationeryApproveStatus.setListIssuesStationeryApproveID(param.getListRequestID());
				infoToUpdateIssuesStationeryApproveStatus
						.setStatusNow(Constant.STATIONERY_APPROVED_WAIT_MANAGER_APPROVE);
				vt050005DAO.updateIssuesStationeryApproveStatus(infoToUpdateIssuesStationeryApproveStatus);
				// update status in ISSUES_STATIONERY_ITEMS
				VT050005InfoUpdateIssuesStationeryItemStatus infoUpdateIssuesStationeryItemStatus = new VT050005InfoUpdateIssuesStationeryItemStatus();
				infoUpdateIssuesStationeryItemStatus.setStatus(Constant.STATIONERY_MANAGER_APPROVE);
				infoUpdateIssuesStationeryItemStatus.setUpdateUser(param.getApproveUserName());
				infoUpdateIssuesStationeryItemStatus.setListIssuesStationeryApproveID(param.getListRequestID());
				infoUpdateIssuesStationeryItemStatus.setStatusNow(Constant.STATIONERY_WAIT_MANAGER_APPROVE);
				vt050005DAO.updateIssuesStationeryItemStatus(infoUpdateIssuesStationeryItemStatus);
				// set data response client
				resp.setStatus(Constant.SUCCESS);
				try {
					// send sms and notify to HCDV
					sendSmsAndNotifyToHCDV(true, param.getApproveUserName(), param.getListRequestID());
					// send sms and notify to VPTCT
					sendSmsAndNotifyToVPTCT(param.getApproveUserName(), param.getListRequestID());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				}
			} else if (param.getAction() == Constant.STATIONERY_ACTION_REJECT) { // reject request
				// update status in ISSUES_STATIONERY_APPROVED
				VT050005InfoToUpdateIssuesStationeryApproveStatus infoToUpdateIssuesStationeryApproveStatus = new VT050005InfoToUpdateIssuesStationeryApproveStatus();
				infoToUpdateIssuesStationeryApproveStatus.setStatus(Constant.STATIONERY_APPROVED_MANAGER_REJECT);
				infoToUpdateIssuesStationeryApproveStatus.setReasonReject(param.getReasonReject());
				infoToUpdateIssuesStationeryApproveStatus.setUpdateUser(param.getApproveUserName());
				infoToUpdateIssuesStationeryApproveStatus.setListIssuesStationeryApproveID(param.getListRequestID());
				infoToUpdateIssuesStationeryApproveStatus
						.setStatusNow(Constant.STATIONERY_APPROVED_WAIT_MANAGER_APPROVE);
				vt050005DAO.updateIssuesStationeryApproveStatus(infoToUpdateIssuesStationeryApproveStatus);
				// update status in ISSUES_STATIONERY_ITEMS
				VT050005InfoUpdateIssuesStationeryItemStatus infoUpdateIssuesStationeryItemStatus = new VT050005InfoUpdateIssuesStationeryItemStatus();
				infoUpdateIssuesStationeryItemStatus.setStatus(Constant.STATIONERY_MANAGER_REJECT);
				infoUpdateIssuesStationeryItemStatus.setUpdateUser(param.getApproveUserName());
				infoUpdateIssuesStationeryItemStatus.setListIssuesStationeryApproveID(param.getListRequestID());
				infoUpdateIssuesStationeryItemStatus.setStatusNow(Constant.STATIONERY_WAIT_MANAGER_APPROVE);
				vt050005DAO.updateIssuesStationeryItemStatus(infoUpdateIssuesStationeryItemStatus);
				// update status in ISSUES_STATIONERY
				List<String> listIssuesStationeryID = vt050005DAO
						.findIssuesStationeryID(param.getListRequestID().get(0));
				for (int i = 0; i < listIssuesStationeryID.size(); i++) {
					String issuesStationeryID = listIssuesStationeryID.get(i);
					vt050000Service.changeStatusIssuesStationery(issuesStationeryID, param.getApproveUserName());
				}
				// set data response client
				resp.setStatus(Constant.SUCCESS);
				try {
					// send sms and notify to HCDV
					sendSmsAndNotifyToHCDV(false, param.getApproveUserName(), param.getListRequestID());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				}
			} else {
				resp.setStatus(Constant.ERROR);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public void sendSmsAndNotifyToHCDV(Boolean approveStatus, String approveUserName,
			List<String> listIssuesStationeryApproveID) throws Exception {
		Thread notiThread = new Thread() {
			public void run() {
				try {
					// find fullname of LDDV approve
					String fullName = vt050000DAO.findFullNameByUserName(approveUserName);

					// find list hcdv request
					List<VT050000InfoHcdvRequest> listInfo = new ArrayList<>();
					listInfo = vt050000DAO.findInfoHcdvRequest(listIssuesStationeryApproveID);
					for (int i = 0; i < listIssuesStationeryApproveID.size(); i++) {
						VT050000InfoHcdvRequest info = listInfo.get(i);
						// send notify
						String toUserName = info.getHcdvUserName();
						VT050000AdditionalInfoToSendNotify addInfo = new VT050000AdditionalInfoToSendNotify(
								listIssuesStationeryApproveID.get(i), Constant.PMQT_ROLE_STAFF_HC_DV,
								Constant.PMQT_ROLE_MANAGER);
						String additionalInformation = addInfo.toString();
						String message = "";
						int status = 0;
						if (approveStatus) {
							message = notifyMessage.getProperty("N36");
							status = Constant.STATIONERY_APPROVED_MANAGER_APPROVE;
						} else {
							message = notifyMessage.getProperty("N38");
							status = Constant.STATIONERY_APPROVED_MANAGER_REJECT;
						}
						message = MessageFormat.format(message, fullName, info.getHcdvMessage().toUpperCase());
						String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
						int type = Constant.TYPE_NOTIFY_MODUL05;
						String createUser = approveUserName;
						notification.sendNotification(toUserName, additionalInformation, message, title, type,
								createUser, status);
						// send sms
						int statusSms = Constant.STATUS_NEW_SMS;
						String smsType = smsMessage.getProperty("TYPE_SMS_MODUL05");
						sms.sendSms(toUserName, message, statusSms, smsType);
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
	public void sendSmsAndNotifyToVPTCT(String approveUserName, List<String> listIssuesStationeryApproveID)
			throws Exception {
		Thread notiThread = new Thread() {
			public void run() {
				try {
					
					// find place ID for each request
					List<VT050005PlaceIDOfIssuesStationeryApprove> listRequest = vt050005DAO
							.findLocationOfRequestForEachRequest(listIssuesStationeryApproveID);

					if (listRequest != null && !listRequest.isEmpty()) {
						// for each request => find VPTCT in this place & send sms
						for (int i = 0; i < listRequest.size(); i++) {
							List<String> listVptctUserName = vt050005DAO.findVPTCT(listRequest.get(i).getPlaceID());
							// send sms & notify for each vptct
							if (listVptctUserName != null && !listVptctUserName.isEmpty()) {
								for (int j = 0; j < listVptctUserName.size(); j++) {
									// send notify
									VT050000AdditionalInfoToSendNotify addInfo = new VT050000AdditionalInfoToSendNotify(listRequest.get(i).getIssuesStationeryApprovedID(),
											Constant.PMQT_ROLE_STAFF_HCVP_VPP, Constant.PMQT_ROLE_MANAGER);
									String additionalInformation = addInfo.toString();
									String message = notifyMessage.getProperty("N40");
									message = MessageFormat.format(message,
											listRequest.get(i).getIssuesStationeryApprovedID().toUpperCase());
									String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
									int type = Constant.TYPE_NOTIFY_MODUL05;
									String createUser = approveUserName;
									int status = Constant.STATIONERY_MANAGER_APPROVE;
									String toUserName = listVptctUserName.get(j);
									notification.sendNotification(toUserName, additionalInformation, message, title,
											type, createUser, status);
									// send sms
									int statusSms = Constant.STATUS_NEW_SMS;
									String smsType = smsMessage.getProperty("TYPE_SMS_MODUL05");
									sms.sendSms(toUserName, message, statusSms, smsType);
								}
							}
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************THREAD NOTI ERROR********:");
				}
			}
		};
		notiThread.start();
	}
}