package com.viettel.vtnet360.vt01.vt010002.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityAdditionalInfo;
import com.viettel.vtnet360.vt01.vt010002.dao.VT010002DAO;
import com.viettel.vtnet360.vt01.vt010002.entity.VT010002EntityRpUpdate;
import com.viettel.vtnet360.vt01.vt010002.entity.VT010002EntityRqUpdate;

import java.security.Principal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Class VT010002ServiceImpl for VT010002
 * 
 * @author KienHT 11/08/2018
 * 
 */
@Service
public class VT010002ServiceImpl implements VT010002Service {

	@Autowired
	VT010002DAO vt010002Dao;

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

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * inOut Register Update
	 * 
	 * @param VT010002EntityRqUpdate
	 * @param Principal
	 * @return VT010002EntityRpUpdate
	 */
	@Override
	@Transactional
	public VT010002EntityRpUpdate inOutRegisterUpdate(VT010002EntityRqUpdate requestParam, Principal principal)
			throws Exception {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();
		String titleForModul01 = notifyMessage.getProperty("TITLE_NOTIFY_MODUL01");

		VT010002EntityRpUpdate reponese = new VT010002EntityRpUpdate();
		reponese.setStatus(Constant.RESPONSE_STATUS_ERROR);
		reponese.setData(null);
		try {

			VT000000EntitySmsVsNotify inforRecord = vt000000DAO
					.findNotifySmsByIdInOut(requestParam.getInOutRegisterId());
			int status = inforRecord.getStatus();

			Date timeNow = new Date(System.currentTimeMillis());

			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("timeNow", timeNow);
			data.put("userName", userName);
			data.put("status", status);
			data.put("requestParam", requestParam);

			List<Map<String, Object>> valid = vt010002Dao.isValid(data);

			// Case IN OUT when NOT valid
			if (valid.size() >= 1) {

				reponese.setStatus(Constant.RESPONSE_EXIST_INOUT_REGISTER_NOT_VALID);

				// Case Register IN OUT when valid
			} else {

				if (checkStatusInvalid(status, requestParam, inforRecord, timeNow)) {
					reponese.setStatus(Constant.RESPONSE_INOUT_RIGISTER_CHANGED_STATUS);
					return reponese;
				} else if (checkOutDate(status, requestParam, inforRecord, timeNow)) {
					reponese.setStatus(Constant.RESPONSE_INOUT_OUT_DATE);
					return reponese;
				}

				vt010002Dao.inOutRegisterUpdate(data);
				reponese.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

				Thread notiThread = new Thread() {
					public void run() {
						try {

							// find information
							// for toUserID , Phone , UnitName
							VT000000EntitySmsVsNotify emp = vt000000DAO.findNotifySmsByUserName(userName);

							VT000000EntitySmsVsNotify manager = vt000000DAO
									.findNotifySmsByUserName(requestParam.getApproverUserName());

							// build message and send notify
							if (status == Constant.WAIT_MANAGER_APPROVE || status == Constant.EXTEND_AFTER_APPROVED) {

								DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
								String messageNotify = MessageFormat.format(smsMessage.getProperty("S02"),
										emp.getCreateUser(), dateformat.format(requestParam.getStartTimeByPlan()),
										dateformat.format(requestParam.getEndTimeByPlan()));
								VT010000EntityAdditionalInfo addInfomation = new VT010000EntityAdditionalInfo();
								addInfomation.setId(requestParam.getInOutRegisterId());
								addInfomation.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
								addInfomation.setStatus(status);
								notification.sendNotification(requestParam.getApproverUserName(),
										addInfomation.toString(), messageNotify, titleForModul01,
										Constant.TYPE_NOTIFY_MODUL01, userName, status);

								String messageSms = MessageFormat.format(smsMessage.getProperty("S02"),
										emp.getCreateUser(), dateformat.format(requestParam.getStartTimeByPlan()),
										dateformat.format(requestParam.getEndTimeByPlan()));
								String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL01_UPDATE");
								sms.sendSms(manager.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
										manager.getPhone(), typeSms);

							} else if (status == Constant.MANAGER_APPROVED || status == Constant.GRUARD_APPROVE_OUT) {

								Map<String, Object> infoRecord = vt000000DAO
										.findInfoInOutRecord(requestParam.getInOutRegisterId());

								DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
								Date logBackEndTime = (Date) infoRecord.get("LOG_TO_ROLLBACK_END_TIME_BY_PLAN");
								String messageNotify = MessageFormat.format(smsMessage.getProperty("S03"),
										emp.getCreateUser(), dateformat.format(logBackEndTime),
										dateformat.format(requestParam.getEndTimeByPlan()));

								VT010000EntityAdditionalInfo addInfomation = new VT010000EntityAdditionalInfo();
								addInfomation.setId(requestParam.getInOutRegisterId());
								addInfomation.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
								addInfomation.setStatus(Constant.EXTEND_AFTER_APPROVED);
								notification.sendNotification(requestParam.getApproverUserName(),
										addInfomation.toString(), messageNotify, titleForModul01,
										Constant.TYPE_NOTIFY_MODUL01, userName, Constant.EXTEND_AFTER_APPROVED);

								String messageSms = MessageFormat.format(smsMessage.getProperty("S03"),
										emp.getCreateUser(), dateformat.format(logBackEndTime),
										dateformat.format(requestParam.getEndTimeByPlan()));

								String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL01_UPDATE");
								sms.sendSms(manager.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
										manager.getPhone(), typeSms);

							}

						} catch (Exception e) {
							logger.error(e.getMessage(), e);
							logger.info("****************************THREAD NOTI ERROR********:");
						}
					}
				};
				notiThread.start();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return reponese;
	}

	private boolean checkOutDate(int status, VT010002EntityRqUpdate requestParam, VT000000EntitySmsVsNotify inforRecord,
			Date timeNow) {

		if ((status == Constant.IN_OUT_REGISTER_WATING_APPROVED)
				&& (timeNow.compareTo(inforRecord.getEndTimeByPlan()) > 0)) {

			return true;
		}

		if ((status == Constant.IN_OUT_REGISTER_APPROVED) && (timeNow.compareTo(inforRecord.getEndTimeByPlan()) > 0)) {

			return true;
		}

		if ((status == Constant.IN_OUT_REGISTER_WATING_EXTEND
				&& inforRecord.getOldStatus() == Constant.IN_OUT_REGISTER_APPROVED)
				&& (timeNow.compareTo(inforRecord.getEndTimeByPlan()) > 0)) {

			return true;
		}

		return false;
	}

	private boolean checkStatusInvalid(int status, VT010002EntityRqUpdate requestParam,
			VT000000EntitySmsVsNotify inforRecord, Date timeNow) {

		// if inOut Register have satus invalid
		if (status == Constant.IN_OUT_REGISTER_REFUSE || status == Constant.IN_OUT_GO_IN
				|| status == Constant.IN_OUT_REGISTER_REJECT_OUT || status == Constant.IN_OUT_REGISTER_REJECT_IN
				|| status == Constant.OUT_DATE_IN_FOR_IN_OUT || status == Constant.OUT_DATE_IN_OUT) {
			return true;
		}

		// if inOut Register not is wating approved then can't upadte
		if (status != Constant.IN_OUT_REGISTER_WATING_APPROVED
				&& (requestParam.getApproverUserName() != null || requestParam.getDestination() != null
						|| requestParam.getReasonDetail() != null || requestParam.getReasonRegistion() != null)) {
			return true;

		}

		return false;
	}
}
