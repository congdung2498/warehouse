package com.viettel.vtnet360.vt04.vt040007.service;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.common.util.CalculateDate;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt02.vt020006.dao.VT020006DAO;
import com.viettel.vtnet360.vt04.vt040000.dao.VT040000DAO;
import com.viettel.vtnet360.vt04.vt040000.entity.VT040000EntityAdditionalInfo;
import com.viettel.vtnet360.vt04.vt040007.dao.VT040007DAO;
import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityDataSt;
import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityRqExecutive;
import com.viettel.vtnet360.vt04.vt040007.entity.VT040007EntityRqFindSt;

/**
 * Class VT040007ServiceImpl
 * 
 * @author KienHT 8/10/2018
 * 
 */
@Service
public class VT040007ServiceImpl implements VT040007Service {

	@Autowired
	VT040007DAO vt040007DAO;

	@Autowired
	Notification notification;

	@Autowired
	Sms sms;

	@Autowired
	VT040000DAO vt040000DAO;

	@Autowired
	Properties notifyMessage;

	@Autowired
	Properties smsMessage;
	
	@Autowired
	private VT020006DAO vt020006DAO;

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * User executiveService
	 * 
	 * @param VT040007EntityRqExecutive
	 * @param Principal
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	@Override
	@Transactional
	public ResponseEntityBase executiveService(VT040007EntityRqExecutive requestParam, Principal principal)
			throws Exception {

		Date dateNow = new Date(System.currentTimeMillis());
		// take value from token
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();

		// timeNow
		Date timeNow = new Date(System.currentTimeMillis());
		List<Date> offDays = vt020006DAO.findDayOff(Constant.STATUS_ACTIVE);
		// set reponse default
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		Map<String, Object> dataInfo = vt040000DAO.infoIssuedService(requestParam.getIssuedServiceId());
		Long realTimeTotal= 0l;
		if(dataInfo.get("realTimeTotal")!= null){
			BigDecimal realTimeTotalBig= (BigDecimal) dataInfo.get("realTimeTotal");
			realTimeTotal=realTimeTotalBig.longValue();
		}
		Date timeStartActual = null;
		if(dataInfo.get("timeStartActual") != null){
			timeStartActual = (Date)dataInfo.get("timeStartActual");
		}
		Date timeStartPlan = null;
		if(dataInfo.get("timeStartPlan") != null){
			timeStartPlan = (Date)dataInfo.get("timeStartPlan");
		}
		
		Date timeResume = null;
		if(dataInfo.get("timeResume") != null){
			timeResume = (Date)dataInfo.get("timeResume");
		}
		Date postponeToTime = null;
		if(dataInfo.get("postponeToTime") != null){
			postponeToTime = (Date)dataInfo.get("postponeToTime");
		}
		int statusIssuedService = (int) dataInfo.get("status");
		if(requestParam.getFlagExecutive()==3 && requestParam.getStatusStart()==1){
			requestParam.setRealTimeTotal(CalculateDate.getTotalHour(timeStartPlan, dateNow, offDays));
		}else if (requestParam.getFlagExecutive()==3&&(timeStartActual== null)) {
			requestParam.setStatusStart(1);
			requestParam.setRealTimeTotal(realTimeTotal);
		}else if (requestParam.getFlagExecutive()==4 ) {
			if(statusIssuedService == 1){
				requestParam.setRealTimeTotal(CalculateDate.getTotalHour(timeStartPlan, dateNow, offDays));
			}else {
				if(postponeToTime == null||timeResume.getTime()<= postponeToTime.getTime()){
					requestParam.setRealTimeTotal(realTimeTotal+CalculateDate.getTotalHour(timeResume, dateNow, offDays));
				}else {
					requestParam.setRealTimeTotal(realTimeTotal+CalculateDate.getTotalHour(postponeToTime, dateNow, offDays));
				}
			}

		} else if (requestParam.getFlagExecutive()==6 || requestParam.getFlagExecutive()==7 ) {
			if(postponeToTime == null||timeResume.getTime()<= postponeToTime.getTime()){
				requestParam.setRealTimeTotal(realTimeTotal+CalculateDate.getTotalHour(timeResume, dateNow, offDays));
			}else {
				requestParam.setRealTimeTotal(realTimeTotal+CalculateDate.getTotalHour(postponeToTime, dateNow, offDays));
			}
		}
		// create Map Object contain param
		Map<String, Object> data = new HashMap<String, Object>();
		
		if(requestParam.getStatusStart()==1){
			data.put("statusStart", 0);	
		}else{
			data.put("statusStart", 1);
		}
		
		data.put("userName", userName);
		data.put("timeNow", timeNow);
		data.put("requestParam", requestParam);
		data.put("fullFillTime", dataInfo.get("fullFillTime"));

		if (checkStatusBeforeInvalid(statusIssuedService, requestParam.getFlagExecutive())) {

			reponse.setStatus(Constant.RESPONSE_ISSUED_SERVICE_CHANGED_STATUS);
			return reponse;
		}

		// try executiveService
		try {

			// save post pone
			if (Constant.FLAG_PENDING_EXECUTIVE_IS_SV == requestParam.getFlagExecutive()) {
				int checkExisted = -1;
				checkExisted = vt040007DAO.checkExisted(requestParam.getIssuedServiceId());
				if(checkExisted == 0)
					vt040007DAO.savePostPoneHistory(data);
				else 
					vt040007DAO.updateHistory(data);
			}

			// after sql data contain more statusStart
			vt040007DAO.executiveService(data);

			// put issuedServiceId into addInfomation json
			VT040000EntityAdditionalInfo addInfomation = new VT040000EntityAdditionalInfo();
			addInfomation.setId(requestParam.getIssuedServiceId());

			// Case get requestParam flag executive = FLAG_COMPLETE_IS_SV
			if (Constant.FLAG_COMPLETE_IS_SV == requestParam.getFlagExecutive()
					&& requestParam.getStationery() != null) {

				// we fill each other stationery user used into database
				for (VT040007EntityDataSt temp : requestParam.getStationery()) {
					data.put("stationeryId", temp.getStationeryId());
					data.put("stationeryQuantity", temp.getStationeryQuantity());
					data.put("unitPrice", temp.getUnitPrice());
					vt040007DAO.fillStationery(data);
				}
			}

			int statusStart = (int) data.get("statusStart");

			trySmsNotify(requestParam, addInfomation, statusStart, userName);

			// set reponse
			reponse.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return reponse;

	}

	private Boolean checkStatusBeforeInvalid(int statusIssuedService, int flagExcutive) {

		if (statusIssuedService == Constant.ISSUED_SERVICE_STATUS_WAITING_APROVE
				|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_REJECT_APROVED
				|| statusIssuedService == Constant.ISSUED_SERVICE_CANCEL
				|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_RECEIVE
				|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_REJECT
				|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_CANT_EXECUTE
				|| statusIssuedService == Constant.ISSUED_SERVICE_STATUS_COMPLETE
				) {

			return true;
		}

		if (statusIssuedService == Constant.ISSUED_SERVICE_STATUS_EXECUTING
				&& flagExcutive == Constant.ISSUED_SERVICE_STATUS_EXECUTING) {
			return true;
		}

		if (statusIssuedService == Constant.ISSUED_SERVICE_STATUS_PENDING_EXECUTE
				&& flagExcutive == Constant.ISSUED_SERVICE_STATUS_PENDING_EXECUTE) {
			return true;
		}

		return false;

	}

	private void trySmsNotify(VT040007EntityRqExecutive requestParam, VT040000EntityAdditionalInfo addInfomation,
			int statusStart, String userName) {

		/// kiênnen

		Thread notiThread = new Thread() {
			public void run() {
				try {

					// NOTIFLY AND SMS HANDLER
					// find name service by issuedServiceId
					// String serviceName;
					Map<String, Object> dataSmsNoti = vt040000DAO.infoIssuedService(requestParam.getIssuedServiceId());
					// serviceName = (String) dataSmsNoti.get("serviceName");
					String serviceId = (String) dataSmsNoti.get("serviceId");

					// set default property
					String messageNotify = "";
					String title = "";
					String messageSms = "";
					String typeSms = "";
					String roleEmp = Constant.PMQT_ROLE_EMPLOYYEE;

					// Case get requestParam flag executive = FLAG_COMPLETE_IS_SV
					if (Constant.FLAG_COMPLETE_IS_SV == requestParam.getFlagExecutive()) {

						// notify complete Issued Service
						// build message notification
						messageNotify = MessageFormat.format(smsMessage.getProperty("S32"), serviceId.toUpperCase());
						title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

						// build message Sms
						messageSms = MessageFormat.format(smsMessage.getProperty("S32"), serviceId.toUpperCase());
						typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_EXECUTIVE");

						// Case get requestParam flag executive = FLAG_EXECUTIVING_IS_SV
					} else if (Constant.FLAG_EXECUTIVING_IS_SV == requestParam.getFlagExecutive()) {

						// Case Issued Service status is status executive
						if (statusStart == 0) {

							// notifly continue executive Issued Service
							// build message notification
							messageNotify = MessageFormat.format(smsMessage.getProperty("S28"),
									serviceId.toUpperCase());
							title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

							// build message Sms
							messageSms = MessageFormat.format(smsMessage.getProperty("S28"), serviceId.toUpperCase());
							typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_EXECUTIVE");

							// Case Issued Service continue is start executive
						} else {

							// notifly start executive Issued Service
							// build message notification
							messageNotify = MessageFormat.format(smsMessage.getProperty("S30"),
									serviceId.toUpperCase());
							title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

							// build message Sms
							messageSms = MessageFormat.format(smsMessage.getProperty("S30"), serviceId.toUpperCase());
							typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_EXECUTIVE");
						}

						// Case get requestParam flag executive = flag pending executive Issued Service
					} else if (Constant.FLAG_PENDING_EXECUTIVE_IS_SV == requestParam.getFlagExecutive()) {

						// notify pending executive Issued Service
						// build message notification
						DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
						messageNotify = MessageFormat.format(smsMessage.getProperty("S29"), serviceId.toUpperCase(),
								dateformat.format(requestParam.getDatePostpone()),
								requestParam.getResonPostponeExecutive().toUpperCase());

						title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

						// build message Sms
						messageSms = MessageFormat.format(smsMessage.getProperty("S29"), serviceId.toUpperCase(),
								dateformat.format(requestParam.getDatePostpone()),
								requestParam.getResonPostponeExecutive().toUpperCase());
						typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_EXECUTIVE");

						// Case get requestParam flag executive = flag cant excutive Issued Service
					} else if (Constant.FLAG_CANT_EXECUTIVE_IS_SV == requestParam.getFlagExecutive()) {

						// notify cant excutive Issued Service
						// build message notification
						messageNotify = MessageFormat.format(smsMessage.getProperty("S31"), serviceId.toUpperCase());
						title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");

						// build message Sms
						messageSms = MessageFormat.format(smsMessage.getProperty("S31"), serviceId.toUpperCase());
						typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_EXECUTIVE");
					}

					// send notification
					addInfomation.setRoleReceiver(roleEmp);
					notification.sendNotification((String) dataSmsNoti.get("empUserName"), addInfomation.toString(),
							messageNotify, title, Constant.TYPE_NOTIFY_MODUL04, userName,
							requestParam.getFlagExecutive());

					// send Sms
					sms.sendSms((int) dataSmsNoti.get("empId"), messageSms, Constant.STATUS_NEW_SMS,
							(String) dataSmsNoti.get("empPhone"), typeSms);

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************THREAD NOTI ERROR********:");

				}
			}

		};

		notiThread.start();

	}

	/**
	 * User find Stationery
	 * 
	 * @param VT040007EntityRqExecutive
	 * @param Principal
	 * @return ResponseEntityBase
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findStationery(VT040007EntityRqFindSt requestParam, Principal principal) {

		// set reponse default
		ResponseEntityBase reponse = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		// try find Stationery
		try {

			requestParam.setFromIndex(requestParam.getPageNumber() * requestParam.getPageSize());
			requestParam.setGetSize(requestParam.getPageSize());
			requestParam.setMasterCode(Constant.M_SYSTEM_CODE_CALCULATION);
			List<VT040007EntityDataSt> data = vt040007DAO.findStationery(requestParam);
			reponse.setData(data);
			reponse.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return reponse;
	}

}
