package com.viettel.vtnet360.vt01.vt010000.service;

import java.security.Principal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.AvatarSync;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.common.TokenAdditionInfo;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;
import com.viettel.vtnet360.vt01.vt010000.dao.VT010000DAO;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityAdditionalInfo;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityApForOneRd;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityCard;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityData;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityDataGetRecord;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRpGetData;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRpGetRecord;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRpMgAp;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqGetData;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqGetRecord;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqMgAp;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqUpdateRecord;

/**
 * Class @Service for VT010005,VT010006,VT010007
 * 
 * @author KienHT 09/08/2018
 * 
 */
@Service
public class VT010000ServiceImpl implements VT010000Service {

	@Autowired
	VT010000DAO vt010000Dao;

	@Autowired
	Notification notification;

	@Autowired
	Sms sms;

	@Autowired
	VT000000DAO vt000000DAO;

	@Autowired
	Properties notifyMessage;

	@Autowired
	Properties smsMessage;

	@Autowired
	private AuthorizationServerTokenServices tokenServices;

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * find list entity common for 3 sceen VT010005,VT010006,VT010007
	 * 
	 * @param requestParam VT010000EntityRqGetData requestParam and Principal
	 * @return VT010000EntityRpGetData
	 */
	@Override
	@Transactional(readOnly = true)
	public VT010000EntityRpGetData findListInOut(VT010000EntityRqGetData requestParam, Principal principal,
			OAuth2Authentication authentication) throws Exception {
		VT010000EntityRpGetData reponese = new VT010000EntityRpGetData();
		reponese.setStatus(Constant.RESPONSE_STATUS_ERROR);
		reponese.setData(null);
		try {

			// take value from token
			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			String userName = (String) oauth.getPrincipal();
			requestParam.setUserName(userName);

			// set date time
			Date timeNow = new Date(System.currentTimeMillis());
			requestParam.setTimeNow(timeNow);

			Map<String, Object> additionalInfo = tokenServices.getAccessToken(authentication)
					.getAdditionalInformation();
			TokenAdditionInfo tokenInfo = (TokenAdditionInfo) additionalInfo.get("userInfor");

			// build param used Map object contain param
			Map<String, Object> dataPram = new HashMap<String, Object>();
			dataPram.put("myUnitId", tokenInfo.getUnitId());
			dataPram.put("userName", userName);
			dataPram.put("fromIndex", requestParam.getPageNumber() * requestParam.getPageSize());
			dataPram.put("getSize", requestParam.getPageSize());

			// set role
			dataPram.put(Constant.PMQT_ROLE_ADMIN, null);
			dataPram.put(Constant.PMQT_ROLE_MANAGER, null);
			dataPram.put(Constant.PMQT_ROLE_EMPLOYYEE, null);
			dataPram.put(Constant.PMQT_ROLE_GUARD, null);
			dataPram.put(Constant.PMQT_ROLE_MANAGER_CVP, null);

			// check role user
			Collection<GrantedAuthority> arrayRoleToken = (Collection<GrantedAuthority>) oauth.getAuthorities();
			for (GrantedAuthority temp : arrayRoleToken) {

				// check and push role into Map Object param if user had role
				logger.info("USER ROLE ALL:" + temp.getAuthority());

				// if user had role PMQT_ROLE_ADMIN
				if (Constant.PMQT_ROLE_ADMIN.equalsIgnoreCase(temp.getAuthority())) {
					dataPram.put(Constant.PMQT_ROLE_ADMIN, Constant.PMQT_ROLE_ADMIN);

					// if user had role PMQT_ROLE_MANAGER
				} else if (Constant.PMQT_ROLE_MANAGER.equalsIgnoreCase(temp.getAuthority())) {
					dataPram.put(Constant.PMQT_ROLE_MANAGER, Constant.PMQT_ROLE_MANAGER);

					// if user had role PMQT_ROLE_EMPLOYYEE
				} else if (Constant.PMQT_ROLE_EMPLOYYEE.equalsIgnoreCase(temp.getAuthority())) {
					dataPram.put(Constant.PMQT_ROLE_EMPLOYYEE, Constant.PMQT_ROLE_EMPLOYYEE);

					// if user had role PMQT_ROLE_STAFF
				} else if (Constant.PMQT_ROLE_GUARD.equalsIgnoreCase(temp.getAuthority())) {
					dataPram.put(Constant.PMQT_ROLE_GUARD, Constant.PMQT_ROLE_GUARD);

					// if user had role PMQT_CVP
				} else if (Constant.PMQT_ROLE_MANAGER_CVP.equalsIgnoreCase(temp.getAuthority())) {
					dataPram.put(Constant.PMQT_ROLE_MANAGER_CVP, Constant.PMQT_ROLE_MANAGER_CVP);
				}

			}

			// log info
			logger.info("USER PMQT_ROLE_ADMIN:" + dataPram.get(Constant.PMQT_ROLE_ADMIN));
			logger.info("USER PMQT_ROLE_MANAGER:" + dataPram.get(Constant.PMQT_ROLE_MANAGER));
			logger.info("USER PMQT_ROLE_EMPLOYYEE:" + dataPram.get(Constant.PMQT_ROLE_EMPLOYYEE));
			logger.info("USER PMQT_ROLE_GUARD:" + dataPram.get(Constant.PMQT_ROLE_GUARD));
			logger.info("USER PMQT_ROLE_MANAGER_CVP:" + dataPram.get(Constant.PMQT_ROLE_MANAGER_CVP));

			logger.info("USER ProcessByRole:" + requestParam.getProcessByRole());
			// case when have endTime and not have startTime
			if (requestParam.getStartTimeByPlan() == null && requestParam.getEndTimeByPlan() != null) {

				// StartTimeByPlan == null => search from 0 to EndTimeByPlan .
				Date date = new Date(0);
				requestParam.setStartTimeByPlan(date);
			}

			dataPram.put("requestParam", requestParam);
			// use object query to find list entity
			List<VT010000EntityData> data = vt010000Dao.findListInOut(dataPram);
			reponese.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			reponese.setData(data);
		} catch (Exception e) {

			// roll back if error
			logger.error(e.getMessage(), e);
			throw e;
		}
		return reponese;
	}

	/**
	 * find List InOut Get Record
	 * 
	 * @param requestParam VT010000EntityRqGetRecord and Principal
	 * @return VT010000EntityRpGetRecord
	 */
	@Override
	@Transactional(readOnly = true)
	public VT010000EntityRpGetRecord findListInOutGetRecord(VT010000EntityRqGetRecord requestParam, Principal principal)
			throws Exception {
		VT010000EntityRpGetRecord reponese = new VT010000EntityRpGetRecord();
		reponese.setStatus(Constant.RESPONSE_STATUS_ERROR);
		reponese.setData(null);
		try {

			String codeEmp = vt010000Dao.findCodeEmpByInoutRegister(requestParam.getInOutRegisterId());

			String imgBase64 = AvatarSync.GetAvataByEmployeeID(codeEmp);
			VT010000EntityDataGetRecord data = vt010000Dao.findInOutGetRecord(requestParam);
			data.setImgBase64(imgBase64);
			reponese.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			reponese.setData(data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return reponese;
	}

	/**
	 * inOut Manager Approve
	 * 
	 * @param requestParam VT010000EntityRqMgAp
	 * @return VT010000EntityRpMgAp
	 */
	@Override
	@Transactional
	public VT010000EntityRpMgAp inOutManagerApprove(VT010000EntityRqMgAp requestParam, Principal principal,
			OAuth2Authentication authentication) throws Exception {

		// hanler
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		Boolean isAdmin = false;
		String empUserName = "";
		int statusRecord = -1;

		// get info from authentication
		Map<String, Object> additionalInfo = tokenServices.getAccessToken(authentication).getAdditionalInformation();
		TokenAdditionInfo tokenInfo = (TokenAdditionInfo) additionalInfo.get("userInfor");
		String myFullName = tokenInfo.getFullName();

		// check role user admin
		Collection<GrantedAuthority> arrayRoleToken = (Collection<GrantedAuthority>) oauth.getAuthorities();
		for (GrantedAuthority temp : arrayRoleToken) {
			// if user had role PMQT_ROLE_ADMIN
			if (Constant.PMQT_ROLE_ADMIN.equalsIgnoreCase(temp.getAuthority()) || Constant.PMQT_ROLE_MANAGER_CVP.equalsIgnoreCase(temp.getAuthority())) {
				isAdmin = true;
				logger.info("APROVED BY ADMINNNNNNNN:");
				break;
			}
		}

		String titleForModul01 = notifyMessage.getProperty("TITLE_NOTIFY_MODUL01");
		String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL01_APPROVE");
		VT010000EntityRpMgAp reponese = new VT010000EntityRpMgAp();
		reponese.setStatus(Constant.RESPONSE_STATUS_ERROR);
		reponese.setData(null);

		int recordDontValid = 0;
		
		try {

			VT010000EntityAdditionalInfo addInfomation = new VT010000EntityAdditionalInfo();
			Date timeNow = new Date(System.currentTimeMillis());

			// check each record one by one
			VT010000EntityApForOneRd record = new VT010000EntityApForOneRd();
			record.setTimeNow(timeNow);
			record.setUserNow(userName);
			record.setStatusApprove(requestParam.getStatus());
			record.setReasonOfApprover(requestParam.getReasonOfApprover());
			record.setGuardInUserName(requestParam.getGuardInUserName());
			record.setGuardOutUserName(requestParam.getGuardOutUserName());
			record.setReasonOfGuard(requestParam.getReasonOfGuard());

			// Case getStatus = MANAGER APPROVED
			if (requestParam.getStatus() == Constant.MANAGER_APPROVED
					|| requestParam.getStatus() == Constant.MANAGER_ABANDON) {

				// hanler approve for each inOutRegisterId in array inOutRegisterId
				for (String inOutRegisterId : requestParam.getInOutRegisterId()) {

					// check each inOutRegisterId one by one and approve
					int statusInOutRegisterId = vt010000Dao.checkSatusInOutRegisterId(inOutRegisterId);
					statusRecord = statusInOutRegisterId;

					// find infor inOutRegisterId
					VT000000EntitySmsVsNotify inforRecord = vt000000DAO.findNotifySmsByIdInOut(inOutRegisterId);
					empUserName = inforRecord.getToUserName();
					if (!isAdmin && !userName.equals(inforRecord.getApproverUserName())) {

						logger.error("MODUL 1: approve once record change approver ");
						if (requestParam.getInOutRegisterId().length == 1) {

							reponese.setStatus(Constant.RESPONSE_INOUT_RIGISTER_CHANGED_APPROVER);
							return reponese;

						} else {

							recordDontValid++;
							continue;

						}

					} else

					// if in out register can approve then It's status is 0(Waiting approved ) or
					if (statusInOutRegisterId == Constant.IN_OUT_REGISTER_WATING_APPROVED
							|| statusInOutRegisterId == Constant.IN_OUT_REGISTER_WATING_EXTEND) {

						// check approved record out date
						if (timeNow.compareTo(inforRecord.getEndTimeByPlan()) > 0) {

							if (requestParam.getInOutRegisterId().length == 1) {

								logger.error("MODUL 1: RESPONSE INOUT OUT DATE ");
								reponese.setStatus(Constant.RESPONSE_INOUT_OUT_DATE);
								return reponese;

							} else {

								recordDontValid++;
								continue;

							}

						}

						// set and Appove
						record.setInOutRegisterId(inOutRegisterId);
						record.setStatusRecord(statusInOutRegisterId);
						
						vt010000Dao.inOutManagerApprove(record);

						Thread notiThread = new Thread() {
							public void run() {
								try {
									// try sms
									trySmsManagerApprove(inOutRegisterId, requestParam, statusInOutRegisterId,
											inforRecord, addInfomation, titleForModul01, userName, typeSms, myFullName);
								} catch (Exception e) {
									logger.error(e.getMessage(), e);
									logger.info("****************************THREAD NOTI ERROR********:");
								}
							}
						};

						notiThread.start();

					} else {
						// if status record isn't either wating aprroved or waiting extend then that
						// record can't approve
						logger.error("MODUL 1: RESPONSE INOUT RIGISTER CHANGED STATUS ");

						if (requestParam.getInOutRegisterId().length == 1) {

							reponese.setStatus(Constant.RESPONSE_INOUT_RIGISTER_CHANGED_STATUS);
							return reponese;

						} else {

							recordDontValid++;
							continue;

						}

					}

				}

				// Case getStatus = GRUARD APPROVED
			} else if (requestParam.getStatus() == Constant.GRUARD_APPROVE_IN
					|| requestParam.getStatus() == Constant.GRUARD_APPROVE_OUT
					|| requestParam.getStatus() == Constant.GRUARD_ABANDON_IN
					|| requestParam.getStatus() == Constant.GRUARD_ABANDON_OUT) {

				for (String inOutRegisterId : requestParam.getInOutRegisterId()) {

					// check each inOutRegisterId one by one
					int statusInOutRegisterId = vt010000Dao.checkSatusInOutRegisterId(inOutRegisterId);
					statusRecord = statusInOutRegisterId;
					VT000000EntitySmsVsNotify inforRecord = vt000000DAO.findNotifySmsByIdInOut(inOutRegisterId);
					empUserName = inforRecord.getToUserName();
					// check valid
					if (checkInvalidGruardOutDate(statusInOutRegisterId, timeNow, inforRecord, requestParam, inOutRegisterId)) {
						reponese.setStatus(Constant.RESPONSE_INOUT_OUT_DATE);
						return reponese;
					} else if (checkInvalidGruardChangeStatus(statusInOutRegisterId, requestParam)) {
						reponese.setStatus(Constant.RESPONSE_INOUT_RIGISTER_CHANGED_STATUS);
						return reponese;
					}

					// set and Appove
					record.setInOutRegisterId(inOutRegisterId);
					record.setStatusRecord(statusInOutRegisterId);
					
					vt010000Dao.inOutGruadApprove(record);

					Thread notiThread = new Thread() {
						public void run() {
							try {

								trySmsGuardrApprove(inOutRegisterId, requestParam, statusInOutRegisterId, inforRecord,
										addInfomation, titleForModul01, userName, typeSms, myFullName);

							} catch (Exception e) {
								logger.error(e.getMessage(), e);
								logger.info("****************************THREAD NOTI ERROR********:");

							}
						}

					};

					notiThread.start();

				}
			}

			if (requestParam.getInOutRegisterId().length == recordDontValid) {

				reponese.setStatus(Constant.RESPONSE_LIST_INOUT_CANT_APROVED);
				logger.info("MODUL 1: approve list don't valid ");

			} else {

				reponese.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			}

		} catch (Exception e) {
			logger.error("USER NAME APROOVE: " + userName + " " + "EMP USER NAME APROOVE: " + empUserName + " "
					+ "Status Record: " + statusRecord + " " + "Flag Aproved: " + requestParam.getStatus());
			logger.error(e.getMessage(), e);
			throw e;
		}
		return reponese;
	}

	private boolean checkInvalidGruardChangeStatus(int statusInOutRegisterId, VT010000EntityRqMgAp requestParam) {

		if ((requestParam.getStatus() == Constant.GRUARD_APPROVE_OUT)
				&& (statusInOutRegisterId == Constant.MANAGER_APPROVED
						|| statusInOutRegisterId == Constant.EXTEND_AFTER_APPROVED)) {
			return false;

		} else if ((requestParam.getStatus() == Constant.GRUARD_ABANDON_OUT)
				&& (statusInOutRegisterId == Constant.MANAGER_APPROVED
						|| statusInOutRegisterId == Constant.EXTEND_AFTER_APPROVED)) {
			return false;

		} else if ((requestParam.getStatus() == Constant.GRUARD_APPROVE_IN)
				&& (statusInOutRegisterId == Constant.GRUARD_APPROVE_OUT
						|| statusInOutRegisterId == Constant.EXTEND_AFTER_APPROVED)) {
			return false;

		} else if ((requestParam.getStatus() == Constant.GRUARD_ABANDON_IN)
				&& (statusInOutRegisterId == Constant.GRUARD_APPROVE_OUT
						|| statusInOutRegisterId == Constant.EXTEND_AFTER_APPROVED)) {
			return false;

		} else if(requestParam.getStatus() == 7 && statusInOutRegisterId == 1) {
      return false;
    }

		return true;
	}

	private Boolean checkInvalidGruardOutDate(int statusInOutRegisterId, Date timeNow,
			VT000000EntitySmsVsNotify inforRecord, VT010000EntityRqMgAp requestParam, String inOutRegisterId) {

		try {

			if (statusInOutRegisterId == Constant.MANAGER_APPROVED
					&& (requestParam.getStatus() == Constant.GRUARD_APPROVE_OUT
							|| requestParam.getStatus() == Constant.GRUARD_ABANDON_OUT)
					&& timeNow.compareTo(inforRecord.getEndTimeByPlan()) > 0) {

				return true;

			} else if (statusInOutRegisterId == Constant.EXTEND_AFTER_APPROVED
					&& (requestParam.getStatus() == Constant.GRUARD_APPROVE_OUT
							|| requestParam.getStatus() == Constant.GRUARD_ABANDON_OUT)
					&& timeNow.compareTo(inforRecord.getOldEndTimeByPlan()) > 0) {

				return true;
			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
		}
		return false;

	}

	/**
	 * try sms for Manager Approve
	 * 
	 * @param myFullName
	 * 
	 * @param String
	 * @param VT010000EntityRqMgAp
	 * @param                              int
	 * @param VT000000EntitySmsVsNotify
	 * @param VT010000EntityAdditionalInfo
	 * @param String
	 * @param String
	 * @param String
	 */
	private void trySmsManagerApprove(String inOutRegisterId, VT010000EntityRqMgAp requestParam,
			int statusInOutRegisterId, VT000000EntitySmsVsNotify inforRecord,
			VT010000EntityAdditionalInfo addInfomation, String titleForModul01, String userName, String typeSms,
			String myFullName) {

		try {

			// notify and Sms
			int statusAfterApprove = vt010000Dao.checkSatusInOutRegisterId(inOutRegisterId);

			// Case getStatus = MANAGER_APPROVED
			if (requestParam.getStatus() == Constant.MANAGER_APPROVED) {

				// Case statusInOutRegisterId = EXTEND_AFTER_APPROVED
				if (statusInOutRegisterId == Constant.EXTEND_AFTER_APPROVED) {
					DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					// build message and send notification
					String message = MessageFormat.format(smsMessage.getProperty("S06"), myFullName,
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));

					addInfomation.setId(inOutRegisterId);
					addInfomation.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
					addInfomation.setStatus(statusAfterApprove);
					notification.sendNotification(inforRecord.getToUserName(), addInfomation.toString(), message,
							titleForModul01, Constant.TYPE_NOTIFY_MODUL01, userName, statusAfterApprove);

					// build message and send Sms
					String messageSms = MessageFormat.format(smsMessage.getProperty("S06"), myFullName,
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));
					sms.sendSms(inforRecord.getToUserId(), messageSms, Constant.STATUS_NEW_SMS, inforRecord.getPhone(),
							typeSms);

					// rest appove InOutRegisterId watiing
				} else {

					// build message and send notification
					DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					String message = MessageFormat.format(smsMessage.getProperty("S04"), myFullName,
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));
					addInfomation.setId(inOutRegisterId);
					addInfomation.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
					addInfomation.setStatus(statusAfterApprove);
					notification.sendNotification(inforRecord.getToUserName(), addInfomation.toString(), //
							message, titleForModul01, Constant.TYPE_NOTIFY_MODUL01, userName, statusAfterApprove);

					// build message and send Sms
					String messageSms = MessageFormat.format(smsMessage.getProperty("S04"), myFullName,
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));
					sms.sendSms(inforRecord.getToUserId(), messageSms, Constant.STATUS_NEW_SMS, inforRecord.getPhone(),
							typeSms);
				}

				// Case manager getStatus = MANAGER_ABANDON
			} else if (requestParam.getStatus() == Constant.MANAGER_ABANDON) {

				// Case MANAGER ABANDON when InOutRegisterId getStatus = EXTEND_AFTER_APPROVED
				if (statusInOutRegisterId == Constant.EXTEND_AFTER_APPROVED) {

					DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					// build message and send notification
					String message = MessageFormat.format(smsMessage.getProperty("S07"), myFullName,
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));

					addInfomation.setId(inOutRegisterId);
					addInfomation.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
					addInfomation.setStatus(statusAfterApprove);
					notification.sendNotification(inforRecord.getToUserName(), addInfomation.toString(), message,
							titleForModul01, Constant.TYPE_NOTIFY_MODUL01, userName, statusAfterApprove);

					// build message and send Sms
					String messageSms = MessageFormat.format(smsMessage.getProperty("S07"), myFullName,
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));

					sms.sendSms(inforRecord.getToUserId(), messageSms, Constant.STATUS_NEW_SMS, inforRecord.getPhone(),
							typeSms);

					// Case MANAGER ABANDON when InOutRegisterId getStatus waiting approve
				} else {
					DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					// build message and send notification
					String message = MessageFormat.format(smsMessage.getProperty("S05"), myFullName,
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));

					addInfomation.setId(inOutRegisterId);
					addInfomation.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
					addInfomation.setStatus(statusAfterApprove);
					notification.sendNotification(inforRecord.getToUserName(), addInfomation.toString(), message,
							titleForModul01, Constant.TYPE_NOTIFY_MODUL01, userName, statusAfterApprove);

					// build message and send Sms
					String messageSms = MessageFormat.format(smsMessage.getProperty("S05"), myFullName,
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));

					sms.sendSms(inforRecord.getToUserId(), messageSms, Constant.STATUS_NEW_SMS, inforRecord.getPhone(),
							typeSms);
				}
			}

			// end try
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("************************ERROR SEND NOTYFYLY*******************");
		}
		// end notify

	}

	/**
	 * try sms for GUARD Approve
	 * 
	 * @param myFullName
	 * @param String
	 * @param VT010000EntityRqMgAp
	 * @param                              int
	 * @param VT000000EntitySmsVsNotify
	 * @param VT010000EntityAdditionalInfo
	 * @param String
	 * @param String
	 * @param String
	 */
	private void trySmsGuardrApprove(String inOutRegisterId, VT010000EntityRqMgAp requestParam,
			int statusInOutRegisterId, VT000000EntitySmsVsNotify inforRecord,
			VT010000EntityAdditionalInfo addInfomation, String titleForModul01, String userName, String typeSms,
			String myFullName) {

		try {

			// notify for each other record approved or reject
			int statusAfterApprove = vt010000Dao.checkSatusInOutRegisterId(inOutRegisterId);

			// Case getStatus = GRUARD APPROVED IN
			if (requestParam.getStatus() == Constant.GRUARD_APPROVE_IN) {
				String message = null;
				String messageSms = null;

				Date timeNow = new Date(System.currentTimeMillis());
				DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				// Case getStatus = GRUARD_APPROVE_IN and inOutRegisterId extend
				if (statusInOutRegisterId == Constant.EXTEND_AFTER_APPROVED) {

					// duyet cho vao khong hop le voi ban ghi dang gian han

					if (statusAfterApprove == Constant.OUT_DATE_IN_FOR_IN_OUT) {
						// build message
						message = MessageFormat.format(smsMessage.getProperty("S011"),
								dateformat.format(inforRecord.getOldStartTimeByPlan()),
								dateformat.format(inforRecord.getOldEndTimeByPlan()));
						messageSms = MessageFormat.format(smsMessage.getProperty("S011"),
								dateformat.format(inforRecord.getOldStartTimeByPlan()),
								dateformat.format(inforRecord.getOldEndTimeByPlan()));

					} else {
						// build message
						message = MessageFormat.format(smsMessage.getProperty("S010"),
								dateformat.format(inforRecord.getOldStartTimeByPlan()),
								dateformat.format(inforRecord.getOldEndTimeByPlan()));
						messageSms = MessageFormat.format(smsMessage.getProperty("S010"),
								dateformat.format(inforRecord.getOldStartTimeByPlan()),
								dateformat.format(inforRecord.getOldEndTimeByPlan()));
					}

				} else {

					if (statusAfterApprove == Constant.OUT_DATE_IN_FOR_IN_OUT) {

						// Case getStatus = GRUARD_APPROVE_IN
						message = MessageFormat.format(smsMessage.getProperty("S011"),
								dateformat.format(inforRecord.getStartTimeByPlan()),
								dateformat.format(inforRecord.getEndTimeByPlan()));
						messageSms = MessageFormat.format(smsMessage.getProperty("S011"),
								dateformat.format(inforRecord.getStartTimeByPlan()),
								dateformat.format(inforRecord.getEndTimeByPlan()));

					} else {
						// Case getStatus = GRUARD_APPROVE_IN
						message = MessageFormat.format(smsMessage.getProperty("S010"),
								dateformat.format(inforRecord.getStartTimeByPlan()),
								dateformat.format(inforRecord.getEndTimeByPlan()));
						messageSms = MessageFormat.format(smsMessage.getProperty("S010"),
								dateformat.format(inforRecord.getStartTimeByPlan()),
								dateformat.format(inforRecord.getEndTimeByPlan()));

					}

				}

				// send notification and send Sms Emloyee
				addInfomation.setId(inOutRegisterId);
				addInfomation.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
				addInfomation.setStatus(statusAfterApprove);
				notification.sendNotification(inforRecord.getToUserName(), addInfomation.toString(), message,
						titleForModul01, Constant.TYPE_NOTIFY_MODUL01, userName, statusAfterApprove);

				sms.sendSms(inforRecord.getToUserId(), messageSms, Constant.STATUS_NEW_SMS, inforRecord.getPhone(),
						typeSms);

				// send notification APPOVER
				addInfomation.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);

				String messageApprover = MessageFormat.format(smsMessage.getProperty("S010_1"),
						inforRecord.getFullNameOfToUserName(), dateformat.format(timeNow));

				notification.sendNotification(inforRecord.getApproverUserName(), addInfomation.toString(),
						messageApprover, titleForModul01, Constant.TYPE_NOTIFY_MODUL01, userName, statusAfterApprove);

				// Case getStatus = GRUARD_APPROVE_OUT
			} else if (requestParam.getStatus() == Constant.GRUARD_APPROVE_OUT) {
				String message = null;
				String messageSms = null;
				DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				// Case getStatus = GRUARD_APPROVE_OUT and inOutRegisterId extend
				if (statusInOutRegisterId == Constant.EXTEND_AFTER_APPROVED) {

					// build message
					message = MessageFormat.format(smsMessage.getProperty("S08"),
							dateformat.format(inforRecord.getOldStartTimeByPlan()),
							dateformat.format(inforRecord.getOldEndTimeByPlan()));
					messageSms = MessageFormat.format(smsMessage.getProperty("S08"),
							dateformat.format(inforRecord.getOldStartTimeByPlan()),
							dateformat.format(inforRecord.getOldEndTimeByPlan()));

					// Case getStatus = GRUARD_APPROVE_OUT and inOutRegisterId waiting
				} else {

					// build message
					message = MessageFormat.format(smsMessage.getProperty("S08"),
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));
					messageSms = MessageFormat.format(smsMessage.getProperty("S08"),
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));
				}

				// send notification
				addInfomation.setId(inOutRegisterId);
				addInfomation.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
				addInfomation.setStatus(statusAfterApprove);
				notification.sendNotification(inforRecord.getToUserName(), addInfomation.toString(), message,
						titleForModul01, Constant.TYPE_NOTIFY_MODUL01, userName, statusAfterApprove);

				// send Sms
				sms.sendSms(inforRecord.getToUserId(), messageSms, Constant.STATUS_NEW_SMS, inforRecord.getPhone(),
						typeSms);

				// send notification APPOVER
				addInfomation.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
				Date timeNow = new Date(System.currentTimeMillis());
				String messageApprover = MessageFormat.format(smsMessage.getProperty("S08_1"),
						inforRecord.getFullNameOfToUserName(), dateformat.format(timeNow));

				notification.sendNotification(inforRecord.getApproverUserName(), addInfomation.toString(),
						messageApprover, titleForModul01, Constant.TYPE_NOTIFY_MODUL01, userName, statusAfterApprove);

				// Case getStatus = GRUARD ABANDON IN
			} else if (requestParam.getStatus() == Constant.GRUARD_ABANDON_IN) {

				String message = null;
				String messageSms = null;

				// Case getStatus = GRUARD ABANDON IN and inOutRegisterId EXTEND AFTER APPROVED

				DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				if (statusInOutRegisterId == Constant.EXTEND_AFTER_APPROVED) {

					// build message
					message = MessageFormat.format(smsMessage.getProperty("S011_1"),
							dateformat.format(inforRecord.getOldStartTimeByPlan()),
							dateformat.format(inforRecord.getOldEndTimeByPlan()));
					messageSms = MessageFormat.format(smsMessage.getProperty("S011_1"),
							dateformat.format(inforRecord.getOldStartTimeByPlan()),
							dateformat.format(inforRecord.getOldEndTimeByPlan()));

					// Case getStatus = GRUARD ABANDON IN and inOutRegisterId EXTEND AFTER APPROVED
				} else {

					// build message
					message = MessageFormat.format(smsMessage.getProperty("S011_1"),
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));
					messageSms = MessageFormat.format(smsMessage.getProperty("S011_1"),
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));
				}

				// send notification
				addInfomation.setId(inOutRegisterId);
				addInfomation.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
				addInfomation.setStatus(statusAfterApprove);
				notification.sendNotification(inforRecord.getToUserName(), addInfomation.toString(), message,
						titleForModul01, Constant.TYPE_NOTIFY_MODUL01, userName, statusAfterApprove);

				// send Sms
				sms.sendSms(inforRecord.getToUserId(), messageSms, Constant.STATUS_NEW_SMS, inforRecord.getPhone(),
						typeSms);

				// send notification APPOVER
				addInfomation.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
				Date timeNow = new Date(System.currentTimeMillis());
				String messageApprover = MessageFormat.format(smsMessage.getProperty("S010_2"),
						inforRecord.getFullNameOfToUserName(), dateformat.format(timeNow));

				notification.sendNotification(inforRecord.getApproverUserName(), addInfomation.toString(),
						messageApprover, titleForModul01, Constant.TYPE_NOTIFY_MODUL01, userName, statusAfterApprove);

				// Case getStatus = GRUARD ABANDON OUT
			} else if (requestParam.getStatus() == Constant.GRUARD_ABANDON_OUT) {
				String message = null;
				String messageSms = null;

				// Case getStatus = GRUARD ABANDON OUT and inOutRegisterId is EXTEND
				// AFTERAPPROVED
				DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				if (statusInOutRegisterId == Constant.EXTEND_AFTER_APPROVED) {

					message = MessageFormat.format(smsMessage.getProperty("S09"),
							dateformat.format(inforRecord.getOldStartTimeByPlan()),
							dateformat.format(inforRecord.getOldEndTimeByPlan()));
					messageSms = MessageFormat.format(smsMessage.getProperty("S09"),
							dateformat.format(inforRecord.getOldStartTimeByPlan()),
							dateformat.format(inforRecord.getOldEndTimeByPlan()));

					// Case getStatus = GRUARD ABANDON OUT and inOutRegisterId is WAITTING
				} else {

					// build message
					message = MessageFormat.format(smsMessage.getProperty("S09"),
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));
					messageSms = MessageFormat.format(smsMessage.getProperty("S09"),
							dateformat.format(inforRecord.getStartTimeByPlan()),
							dateformat.format(inforRecord.getEndTimeByPlan()));
				}

				// send notification
				addInfomation.setId(inOutRegisterId);
				addInfomation.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
				addInfomation.setStatus(statusAfterApprove);
				notification.sendNotification(inforRecord.getToUserName(), inOutRegisterId, message, titleForModul01,
						Constant.TYPE_NOTIFY_MODUL01, userName, statusAfterApprove);

				// send Sms
				sms.sendSms(inforRecord.getToUserId(), messageSms, Constant.STATUS_NEW_SMS, inforRecord.getPhone(),
						typeSms);

				// send notification APPOVER
				addInfomation.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
				Date timeNow = new Date(System.currentTimeMillis());
				String messageApprover = MessageFormat.format(smsMessage.getProperty("S09_1"),
						inforRecord.getFullNameOfToUserName(), dateformat.format(timeNow));

				notification.sendNotification(inforRecord.getApproverUserName(), addInfomation.toString(),
						messageApprover, titleForModul01, Constant.TYPE_NOTIFY_MODUL01, userName, statusAfterApprove);

			}

			// end try notify and Sms
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("************************ERROR SEND NOTYFYLY*******************");
		}

	}

	/**
	 * update Record
	 * 
	 * @param requestParam VT010000EntityRqUpdateRecord
	 * @return ResponseEntityBase
	 */

	@Override
	@Transactional
	public ResponseEntityBase updateRecord(VT010000EntityRqUpdateRecord requestParam) throws Exception {
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			vt010000Dao.updateRecord(requestParam);
			reponse.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return reponse;
	}

	/**
	 * update Record Once/Day
	 * 
	 */
	@Override
	@Transactional
	public void autoUpdateRecordOutDate() throws Exception {
		try {
			Date timeNow = new Date(System.currentTimeMillis());
			vt010000Dao.autoUpdateRecordOutDate(timeNow);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	/**
	 * update Record Once/Day Ver2
	 * 
	 */
	@Override
	@Transactional
	public void autoUpdateRecordOutDateVer2() throws Exception {
		try {
			Date timeNow = new Date(System.currentTimeMillis());
			vt010000Dao.autoUpdateRecordOutDateVer2(timeNow);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	/**
	 * id Card
	 * 
	 * @param VT010000EntityCard
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional
	public ResponseEntityBase idCard(VT010000EntityCard requestParam) throws Exception {

		// set reponse
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {

			// find inOutRegisterId is out Side
			VT010000EntityCard data = vt010000Dao.findOutIdCard(requestParam);

			// find inOutRegisterId is In Side
			if (data == null) {
				data = vt010000Dao.finInIdCard(requestParam);
			}

			// set reponese
			reponse.setData(data);
			reponse.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return reponse;
	}

}
