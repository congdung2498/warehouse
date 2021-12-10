package com.viettel.vtnet360.vt03.vt030005.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;
import com.viettel.vtnet360.vt03.Common.DateUtil;
import com.viettel.vtnet360.vt03.vt030000.dao.VT030000DAO;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityBookCarResult;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityPlace;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000NotifySmsInfo;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000ResponseBookCarRequest;
import com.viettel.vtnet360.vt03.vt030005.dao.VT030005DAO;

/**
 * class service implements for Interface VT030005Service
 * 
 * @author CuongHD 11/09/2018
 *
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030005ServiceImpl implements VT030005Service {
	@Autowired
	private VT030005DAO vt030005dao;

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	Notification notification;

	@Autowired
	Sms sms;

	@Autowired
	VT000000DAO vt000000DAO;

	@Autowired
	VT030000DAO vt030000dao;

	@Autowired
	Properties notifyMessage;

	@Autowired
	Properties smsMessage;

	/**
	 * Get place start and place target
	 * 
	 * @return VT030005ResponsePlace
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findPlaceStart(VT030000EntityPlace obj) throws Exception {
		logger.info("********************* START EXECUTE APIVT030005_01 ***********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT030000EntityPlace> data = new ArrayList<>();
		try {
			data = vt030005dao.findPlaceStart(obj);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			response.setData(null);
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		logger.info("********************* END EXECUTE APIVT030005_01 ***********************");
		return response;
	}

	/**
	 * Insert new reqquest book car
	 * 
	 * @return ResponseEntity
	 */
	@Override
	@Transactional
	public ResponseEntityBase insertRequest(VT030000ResponseBookCarRequest obj) throws Exception {
		logger.info("********************* START EXECUTE APIVT030005_03 ***********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			String startLong = String.valueOf(obj.getDateStart().getTime());
			startLong = startLong.substring(0, startLong.length() - 3) + "000";
			obj.setDateStart(new Date(Long.parseLong(startLong)));
			
			String endLong = String.valueOf(obj.getDateEnd().getTime());
			endLong = endLong.substring(0, endLong.length() - 3) + "000";
			
			int checkDuplicateTime = vt030005dao.limitRequestBookCar(obj);
			if (checkDuplicateTime <= 0) {
				long currentTime = Calendar.getInstance().getTimeInMillis();
				 if (currentTime >= obj.getDateStart().getTime() ||
				 currentTime >= obj.getDateEnd().getTime()) {
				 return response;
				 }
				vt030005dao.insertRequest(obj);
				Thread notiThread = new Thread() {
					public void run() {
						try {
							// tim kiem thong tin chuyen xe
							VT030000NotifySmsInfo info = new VT030000NotifySmsInfo(Constant.MASTER_CLASS_TYPE_CAR,
									Constant.MASTER_CLASS_SEAT_CAR, Constant.MASTER_CLASS_ROUTE_CAR,
									obj.getBookCarId());
							VT030000NotifySmsInfo infoCarRoute = vt030000dao.findInfoNotifySms(info);

							VT000000EntitySmsVsNotify inforNotifySmsManager = null;
							VT000000EntitySmsVsNotify inforNotifySmsEmp = vt000000DAO
									.findNotifySmsByUserName(obj.getUserName());
							inforNotifySmsEmp.setToUserName(obj.getUserName());
							if (obj.getApproverDir() == null || obj.getApproverDir().trim().length() == 0) {
								if (obj.getApproverLead() != null) {
									inforNotifySmsManager = vt000000DAO.findNotifySmsByUserName(obj.getApproverLead());
									inforNotifySmsManager.setToUserName(obj.getApproverLead());
								} else if (obj.getApproverPre() != null) {
									inforNotifySmsManager = vt000000DAO.findNotifySmsByUserName(obj.getApproverPre());
									inforNotifySmsManager.setToUserName(obj.getApproverPre());
								}
							} else {
								inforNotifySmsManager = vt000000DAO.findNotifySmsByUserName(obj.getApproverDir());
								inforNotifySmsManager.setToUserName(obj.getApproverDir());
							}
							if (inforNotifySmsManager != null) {
								// send notify to approver
								String messageNotify = MessageFormat.format(notifyMessage.getProperty("N12"),
										inforNotifySmsEmp.getCreateUser(),
										new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE)
												.toUpperCase(),
										new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE)
												.toUpperCase());
								String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");

								AdditionalInfoBase addInfo = new AdditionalInfoBase();
								addInfo.setId(obj.getBookCarId());
								addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
								notification.sendNotification(inforNotifySmsManager.getToUserName(), addInfo.toString(),
										messageNotify, title, Constant.TYPE_NOTIFY_MODUL03, obj.getUserName(),
										Constant.WAIT_MANAGER_APPROVE);
								// send SMS to approver
								String messageSms = MessageFormat.format(smsMessage.getProperty("S12"),
										inforNotifySmsEmp.getCreateUser(),
										new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE)
												.toUpperCase(),
										new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE)
												.toUpperCase());
								String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_REGISTER");
								sms.sendSms(inforNotifySmsManager.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
										inforNotifySmsManager.getPhone(), typeSms);
							}

						} catch (Exception e) {
							logger.error("Not found device token");
						}
					}
				};
				notiThread.start();
				response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

			} else {
				response.setData(null);
				response.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
			}
		} catch (Exception e) {
			response.setData(null);
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		logger.info("********************* START EXECUTE APIVT030005_03 ***********************");
		return response;
	}

	@Override
	@Transactional
	public ResponseEntityBase insertRequestResult(VT030000EntityBookCarResult obj) throws Exception {
		logger.info("********************* START EXECUTE APIVT030005_03 ***********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			int checkDuplicateTime = vt030005dao.limitRequestBookCarResult(obj);
			if (checkDuplicateTime <= 0) {
				long currentTime = Calendar.getInstance().getTimeInMillis();
				if (currentTime <= obj.getDateStart().getTime() || currentTime <= obj.getDateEnd().getTime()) {
					return response;
				}
				
				int status = vt030005dao.insertRequestResult(obj);
				if (status == 0) {
					return response;
				}
				Thread notiThread = new Thread() {
					public void run() {
						try {
							// tim kiem thong tin chuyen xe
							VT030000NotifySmsInfo info = new VT030000NotifySmsInfo(Constant.MASTER_CLASS_TYPE_CAR,
									Constant.MASTER_CLASS_SEAT_CAR, Constant.MASTER_CLASS_ROUTE_CAR,
									obj.getBookCarId());
							VT030000NotifySmsInfo infoCarRoute = vt030000dao.findInfoNotifySms(info);

							VT000000EntitySmsVsNotify inforNotifySmsManager = null;
							VT000000EntitySmsVsNotify inforNotifySmsEmp = vt000000DAO
									.findNotifySmsByUserName(obj.getUserName());
							inforNotifySmsEmp.setToUserName(obj.getUserName());
							if (obj.getApproverDir() == null || obj.getApproverDir().trim().length() == 0) {
								if (obj.getApproverLead() != null) {
									inforNotifySmsManager = vt000000DAO.findNotifySmsByUserName(obj.getApproverLead());
									inforNotifySmsManager.setToUserName(obj.getApproverLead());
								} else if (obj.getApproverPre() != null) {
									inforNotifySmsManager = vt000000DAO.findNotifySmsByUserName(obj.getApproverPre());
									inforNotifySmsManager.setToUserName(obj.getApproverPre());
								}
							} else {
								inforNotifySmsManager = vt000000DAO.findNotifySmsByUserName(obj.getApproverDir());
								inforNotifySmsManager.setToUserName(obj.getApproverDir());
							}
							if (inforNotifySmsManager != null) {
								// send notify to approver
								String messageNotify = MessageFormat.format(notifyMessage.getProperty("N12"),
										inforNotifySmsEmp.getCreateUser(),
										new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE)
												.toUpperCase(),
										new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE)
												.toUpperCase());
								String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");

								AdditionalInfoBase addInfo = new AdditionalInfoBase();
								addInfo.setId(obj.getBookCarId());
								addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
								notification.sendNotification(inforNotifySmsManager.getToUserName(), addInfo.toString(),
										messageNotify, title, Constant.TYPE_NOTIFY_MODUL03, obj.getUserName(),
										Constant.WAIT_MANAGER_APPROVE);
								// send SMS to approver
								String messageSms = MessageFormat.format(smsMessage.getProperty("S12"),
										inforNotifySmsEmp.getCreateUser(),
										new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE)
												.toUpperCase(),
										new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE)
												.toUpperCase());
								String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_REGISTER");
								sms.sendSms(inforNotifySmsManager.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
										inforNotifySmsManager.getPhone(), typeSms);
							}

						} catch (Exception e) {
							logger.error("Not found device token");
						}
					}
				};
				notiThread.start();
				response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

			} else {
				response.setData(null);
				response.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
			}
		} catch (Exception e) {
			response.setData(null);
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		logger.info("********************* START EXECUTE APIVT030005_03 ***********************");
		return response;
	}

}
