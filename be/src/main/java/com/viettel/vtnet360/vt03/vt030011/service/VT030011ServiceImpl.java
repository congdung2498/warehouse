package com.viettel.vtnet360.vt03.vt030011.service;

import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.driving.dto.AddingTimeNotiInfo;
import com.viettel.vtnet360.driving.request.dao.BookingDAO;
import com.viettel.vtnet360.driving.service.BookingService;
import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;
import com.viettel.vtnet360.vt03.Common.DateUtil;
import com.viettel.vtnet360.vt03.vt030000.dao.VT030000DAO;
import com.viettel.vtnet360.vt03.vt030011.dao.VT030011DAO;
import com.viettel.vtnet360.vt03.vt030011.entity.VT030011CancelRequest;

/**
 * Class VT030011ServiceImpl
 *
 * @author CuongHD
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030011ServiceImpl implements VT030011Service {

	@Autowired
	private VT030011DAO vt030011dao;

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
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private BookingDAO bookingDao;

	/**
	 * Cancel a request by it's id
	 *
	 * @param VT030011CancelRequest obj
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	@Override
	@Transactional
	public ResponseEntityBase updateRequest(VT030011CancelRequest obj) throws Exception {
		logger.info("********************* START EXECUTE APIVT030011_01 ***********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			int status = vt030011dao.getStatusRequest(obj);
			if (status == Constant.WAIT_MANAGER_APPROVE_STILL || 
			    status == Constant.WAIT_MANAGER_APPROVE ||
			    status == Constant.MANAGER_APPROVED||
			    status == Constant.MANAGER_CAR_SQUAD_APPROVE) {
				// tim kiem thong tin chuyen xe

				// update status driver if manager has matched to driver
				vt030011dao.updateStatusDriveAfterCancel(obj);

				vt030011dao.updateStatusCarAfterCancel(obj);

				vt030011dao.updateStatusDriveCarAfterCancel(obj);

				AddingTimeNotiInfo notiInfo = bookingDao.getCarbookingById(obj.getBookCarId());
				vt030011dao.updateRequest(obj);
				response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);

				Thread notiThread = new Thread() {
					public void run() {
					  AdditionalInfoBase addInfo = new AdditionalInfoBase();
            addInfo.setId(obj.getBookCarId());
            
					  if(notiInfo.getStatus() == 1) {
					    if(obj.getUserName().equals(notiInfo.getEmpUsername())) {
					      String message = MessageFormat.format(notifyMessage.getProperty("N118"),
	                  notiInfo.getEmpName(), notiInfo.getCarType(), notiInfo.getSeat(),
	                  new DateUtil(notiInfo.getDateStart()).toString(DateUtil.FORMAT_DATE),
	                  new DateUtil(notiInfo.getDateEnd()).toString(DateUtil.FORMAT_DATE));
	              String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
	              
	              if(notiInfo.getFlagQltt() == 1) {
	                VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getQlttUsername());
	                notiUser.setToUserName(notiInfo.getQlttUsername());
	                addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
	                bookingService.sendNotiAndSms(notiUser, message, title, addInfo);
	              }
	              
	              if(notiInfo.getFlagLddv() == 1) {
	                VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getQldvUsername());
	                notiUser.setToUserName(notiInfo.getQldvUsername());
	                addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
	                bookingService.sendNotiAndSms(notiUser, message, title, addInfo);
	              }
	              
	              if(notiInfo.getSquadLeadUsername() != null) {
                  VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getSquadLeadUsername());
                  notiUser.setToUserName(notiInfo.getSquadLeadUsername());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_CVP);
                  bookingService.sendNotiAndSms(notiUser, message, title, addInfo);
                }
					    } else {
					      String message = MessageFormat.format(notifyMessage.getProperty("N115"),
                    notiInfo.getEmpName(), notiInfo.getCarType(), notiInfo.getSeat(),
                    new DateUtil(notiInfo.getDateStart()).toString(DateUtil.FORMAT_DATE),
                    new DateUtil(notiInfo.getDateEnd()).toString(DateUtil.FORMAT_DATE), obj.getReason());
                String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
                
                if(notiInfo.getEmpUsername() != null) {
                  VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getEmpUsername());
                  notiUser.setToUserName(notiInfo.getEmpUsername());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                  bookingService.sendNotiAndSms(notiUser, message, title, addInfo);
                }
                
                if(notiInfo.getFlagQltt() == 1) {
                  VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getQlttUsername());
                  notiUser.setToUserName(notiInfo.getQlttUsername());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                  bookingService.sendNotiAndSms(notiUser, message, title, addInfo);
                }
                
                if(notiInfo.getFlagLddv() == 1) {
                  VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getQldvUsername());
                  notiUser.setToUserName(notiInfo.getQldvUsername());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                  bookingService.sendNotiAndSms(notiUser, message, title, addInfo);
                }
                
                if(notiInfo.getSquadLeadUsername() != null && !obj.getUserName().equals(notiInfo.getSquadLeadUsername())) {
                  VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getSquadLeadUsername());
                  notiUser.setToUserName(notiInfo.getSquadLeadUsername());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_DOIXE);
                  bookingService.sendNotiAndSms(notiUser, message, title, addInfo);
                }
					    }
					  } 
					  
					  if(notiInfo.getStatus() == 4) {
					    if(obj.getUserName().equals(notiInfo.getEmpUsername())) {
					      String message = MessageFormat.format(notifyMessage.getProperty("N118"),
	                  notiInfo.getEmpName(), notiInfo.getCarType(), notiInfo.getSeat(),
	                  new DateUtil(notiInfo.getDateStart()).toString(DateUtil.FORMAT_DATE),
	                  new DateUtil(notiInfo.getDateEnd()).toString(DateUtil.FORMAT_DATE));
	              String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
	              
	              if(notiInfo.getFlagQltt() == 1) {
	                VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getQlttUsername());
	                notiUser.setToUserName(notiInfo.getQlttUsername());
	                addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
	                bookingService.sendNotiAndSms(notiUser, message, title, addInfo);
	              }
	              
	              if(notiInfo.getFlagLddv() == 1) {
	                VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getQldvUsername());
	                notiUser.setToUserName(notiInfo.getQldvUsername());
	                addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
	                bookingService.sendNotiAndSms(notiUser, message, title, addInfo);
	              }
	              
					      if(notiInfo.getSquadLeadUsername() != null) {
	                VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getSquadLeadUsername());
	                notiUser.setToUserName(notiInfo.getSquadLeadUsername());
	                addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_DOIXE);
	                bookingService.sendNotiAndSms(notiUser, message, title, addInfo);
	              }
					      
					      if(notiInfo.getDriverUsername() != null) {
					        String driverMessage = MessageFormat.format(notifyMessage.getProperty("N119"),
	                    notiInfo.getRouteType(), notiInfo.getStartPlace(), notiInfo.getTargetPlace(),
	                    notiInfo.getLicensePlate(),
	                    new DateUtil(notiInfo.getDateStart()).toString(DateUtil.FORMAT_DATE),
	                    new DateUtil(notiInfo.getDateEnd()).toString(DateUtil.FORMAT_DATE));
					        
					        VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getDriverUsername());
                  notiUser.setToUserName(notiInfo.getDriverUsername());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYEE_DOIXE);
                  bookingService.sendNotiAndSms(notiUser, driverMessage, title, addInfo);
					      }
					    } else {
					      String message = MessageFormat.format(notifyMessage.getProperty("N115"),
                    notiInfo.getEmpName(), notiInfo.getCarType(), notiInfo.getSeat(),
                    new DateUtil(notiInfo.getDateStart()).toString(DateUtil.FORMAT_DATE),
                    new DateUtil(notiInfo.getDateEnd()).toString(DateUtil.FORMAT_DATE), obj.getReason());
                String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
                
                if(notiInfo.getEmpUsername() != null) {
                  String userMessage = MessageFormat.format(notifyMessage.getProperty("N116"),
                      notiInfo.getRouteType(), notiInfo.getStartPlace(), notiInfo.getTargetPlace(),
                      notiInfo.getLicensePlate(),
                      new DateUtil(notiInfo.getDateStart()).toString(DateUtil.FORMAT_DATE),
                      new DateUtil(notiInfo.getDateEnd()).toString(DateUtil.FORMAT_DATE), obj.getReason());
                  
                  VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getEmpUsername());
                  notiUser.setToUserName(notiInfo.getEmpUsername());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                  bookingService.sendNotiAndSms(notiUser, userMessage, title, addInfo);
                }
                
                if(notiInfo.getFlagQltt() == 1) {
                  VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getQlttUsername());
                  notiUser.setToUserName(notiInfo.getQlttUsername());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                  bookingService.sendNotiAndSms(notiUser, message, title, addInfo);
                }
                
                if(notiInfo.getFlagLddv() == 1) {
                  VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getQldvUsername());
                  notiUser.setToUserName(notiInfo.getQldvUsername());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                  bookingService.sendNotiAndSms(notiUser, message, title, addInfo);
                }
                
                if(notiInfo.getSquadLeadUsername() != null && !obj.getUserName().equals(notiInfo.getSquadLeadUsername())) {
                  VT000000EntitySmsVsNotify notiUser = vt000000DAO.findNotifySmsByUserName(notiInfo.getSquadLeadUsername());
                  notiUser.setToUserName(notiInfo.getSquadLeadUsername());
                  addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_DOIXE);
                  bookingService.sendNotiAndSms(notiUser, message, title, addInfo);
                }
					    }
            }
					}
				};
				notiThread.start();
			} else {
				response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			}

		} catch (Exception e) {
			response.setData(null);
			response.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		logger.info("********************* END EXECUTE APIVT030011_01 ***********************");
		return response;
	}


}
