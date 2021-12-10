package com.viettel.vtnet360.vt01.vt010001.service;

import java.security.Principal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityAdditionalInfo;
import com.viettel.vtnet360.vt01.vt010001.dao.VT010001DAO;
import com.viettel.vtnet360.vt01.vt010001.entity.VT010001EntityRpRegister;
import com.viettel.vtnet360.vt01.vt010001.entity.VT010001EntityRqRegister;
import com.viettel.vtnet360.vt01.vt010001.entity.VT010001SystemCode;
import com.viettel.vtnet360.vt03.vt030000.entity.Employee;

/**
 * Class VT010001ServiceImpl
 * 
 * @author KienHT 09/08/2018
 * 
 */
@Service
public class VT010001ServiceImpl implements VT010001Service {

	@Autowired
	VT010001DAO vt010001Dao;

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
	 * register in_out_record
	 * 
	 * @param VT010001EntityRequestParam
	 * @param Principal
	 * @return ResponseEntity<VT010001EntityRpRegister>
	 */
	@Override
	@Transactional
	public VT010001EntityRpRegister inOutregister(VT010001EntityRqRegister requestParam, Principal principal) throws Exception {
		// use token
		OAuth2Authentication oauth = (OAuth2Authentication) principal;
		String userName = (String) oauth.getPrincipal();

		// get time now
		Date dateNow = new Date(System.currentTimeMillis());

		// set reponse default
		VT010001EntityRpRegister reponse = new VT010001EntityRpRegister();
		reponse.setStatus(Constant.RESPONSE_STATUS_ERROR);
		reponse.setData(null);

		Calendar start = Calendar.getInstance();
		start.setTime(requestParam.getStartTimeByPlan());
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		Calendar end = Calendar.getInstance();
		end.setTime(requestParam.getEndTimeByPlan());
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);
		requestParam.setStartTimeByPlan(start.getTime());
		requestParam.setEndTimeByPlan(end.getTime());
		

		// create hashmap contain param need use
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("returnedId", null);
		data.put("userName", userName);
		data.put("requestParam", requestParam);
		data.put("dateNow", dateNow);
		try {

			List<Map<String, Object>> valid = vt010001Dao.isValid(data);

			// Case Register IN OUT when NOT valid
			if (valid.size() >= 1) {

				reponse.setStatus(Constant.RESPONSE_EXIST_INOUT_REGISTER_NOT_VALID);

				// Case Register IN OUT when valid
			} else {

				// register in out
				vt010001Dao.insertInOutRegister(data);
				// set reponse code
				reponse.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

				Thread notiThread = new Thread() {
					public void run() {
						try {

							// find information
							// for toUserID , Phone , UnitName
							VT000000EntitySmsVsNotify inforNotifySmsEmp = vt000000DAO.findNotifySmsByUserName(userName);
							inforNotifySmsEmp.setToUserName(requestParam.getApproverUserName());

							// find info manager
							VT000000EntitySmsVsNotify inforNotifySmsManager = vt000000DAO
									.findNotifySmsByUserName(requestParam.getApproverUserName());

							// build message and send notify
							DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
							String messageNotify = MessageFormat.format(smsMessage.getProperty("S01"),
									inforNotifySmsEmp.getCreateUser(),
									dateformat.format(requestParam.getStartTimeByPlan()),
									dateformat.format(requestParam.getEndTimeByPlan()));

							String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL01");
							VT010000EntityAdditionalInfo addInfomation = new VT010000EntityAdditionalInfo();
							addInfomation.setId((String) data.get("returnedId"));
							addInfomation.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
							addInfomation.setStatus(Constant.WAIT_MANAGER_APPROVE);
							notification.sendNotification(inforNotifySmsEmp.getToUserName(), addInfomation.toString(),
									messageNotify, title, Constant.TYPE_NOTIFY_MODUL01, userName,
									Constant.WAIT_MANAGER_APPROVE);

							// build message and send sms
							String messageSms = MessageFormat.format(smsMessage.getProperty("S01"),
									inforNotifySmsEmp.getCreateUser(),
									dateformat.format(requestParam.getStartTimeByPlan()),
									dateformat.format(requestParam.getEndTimeByPlan()));
							String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL01_REGISTER");
							sms.sendSms(inforNotifySmsManager.getToUserId(), messageSms, Constant.STATUS_NEW_SMS,
									inforNotifySmsManager.getPhone(), typeSms);

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
		return reponse;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findEmployee(Employee employee) throws Exception {
		logger.info("**************** Start get list of employee ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<Employee> listDS = new ArrayList<>();

		try {
			listDS = vt010001Dao.findEmployee(employee);
			reb.setData(listDS);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list of employee - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list of employee - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findEmployeeByUserName(Employee employee) throws Exception {
		logger.info("**************** Start get employee by user name ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		Employee listDS;

		try {
			listDS = vt010001Dao.findEmployeeByUserName(employee);
			reb.setData(listDS);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get employee by user name - OK ****************");
		} catch (Exception e) {
			logger.info("**************** get employee by user name - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	public ResponseEntityBase getSystemCodeByParams(VT010001SystemCode des) throws Exception {
		logger.info("**************** Start get list of getSystemCodeByParams ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT010001SystemCode> listDS = new ArrayList<>();

		try {
			listDS = vt010001Dao.getSystemCodeByParams(des);
			reb.setData(listDS);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list of getSystemCodeByParams - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list of getSystemCodeByParams - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}
}
