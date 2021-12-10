package com.viettel.vtnet360.vt03.vt030006.service;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import com.viettel.vtnet360.vt00.vt000000.service.VT000000Service;
import com.viettel.vtnet360.vt03.Common.DateUtil;
import com.viettel.vtnet360.vt03.vt030000.dao.VT030000DAO;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000NotifySmsInfo;
import com.viettel.vtnet360.vt03.vt030006.dao.VT030006DAO;
import com.viettel.vtnet360.vt03.vt030006.entity.VT030006UpdateBookCar;
import com.viettel.vtnet360.vt03.vt030008.dao.VT030008DAO;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT030006ServiceImpl implements VT030006Service {
  private final Logger logger = Logger.getLogger(this.getClass());

  @Autowired
  private VT030006DAO vt030006dao;

  @Autowired
  private BookingDAO bookingDao;

  @Autowired
  private BookingService bookingService;

  @Autowired
  private Notification notification;

  @Autowired
  private Sms sms;

  @Autowired
  private VT030008DAO vt030008dao;

  @Autowired
  private VT000000DAO vt000000DAO;
  
  @Autowired
  private VT000000Service vt000000Service;

  @Autowired
  private VT030000DAO vt030000dao;

  @Autowired
  private Properties notifyMessage;

  @Autowired
  private Properties smsMessage;

  /**
   * Approve Or Refuse resquest book car
   * 
   * @param VT030006UpdateBookCar
   *            obj
   * @return ResponseEntityBase
   * @throws Exception
   */
  @Override
  @Transactional
  public ResponseEntityBase updateRequest(VT030006UpdateBookCar obj, Collection<GrantedAuthority> roleList)
      throws Exception {
    logger.info("********************* START EXECUTE APIVT030006_01 ***********************");
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    VT030006UpdateBookCar objTmp = null;
    String[] listId = null;
    try {
      if (roleList.isEmpty()) {
        return response;
      }
      if (!roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
        if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))
            || roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP))) {
          obj.setRole(Constant.PMQT_ROLE_MANAGER);
        } else {
          obj.setRole(Constant.PMQT_ROLE_MANAGER_DOIXE);
        }
      } else {
        obj.setRole(Constant.PMQT_ROLE_ADMIN);
      }

      listId = obj.getListId();
      if (listId.length > 0) {
        for (String id : listId) {
          obj.setBookCarId(id);
          int status = vt030008dao.getStatus(obj.getBookCarId());
          /** if two admins refuse one bookcar's request in the same time */
          if(status == Constant.MANAGER_ABANDON) {
            return new ResponseEntityBase(Constant.MANAGER_CAR_SQUAD_APPROVED, null);
          }
          /** if two admins cancel request in the same time in matching car */
          if(status == obj.getStatus()) {
            return new ResponseEntityBase(Constant.MANAGER_CAR_SQUAD_APPROVED, null);
          }
          /** if requester and  manager car's squad cancel in the same time */
          if(status == 3 && obj.getStatus() == Constant.MANAGER_ABANDON) {
            return new ResponseEntityBase(Constant.MANAGER_CAR_SQUAD_APPROVED, null);
          }
          /** tim kiem tat ca quan ly co quyen xac nhan cho don yeu cau nay */
          objTmp = vt030006dao.findAllLevelManager(obj.getBookCarId());
          if(objTmp == null) {
            return response;
          }
          if(status == Constant.WAIT_MANAGER_APPROVE_STILL || 
              status == Constant.ISSUED_SERVICE_STATUS_WAITING_APROVE  ||
              status == Constant.ISSUED_SERVICE_STATUS_APROVED || 
              status == Constant.ISSUED_SERVICE_STATUS_PENDING_EXECUTE ) {
            if (objTmp != null) {
              obj.setQltt(objTmp.getQltt() == null ? null : objTmp.getQltt());
              obj.setFlagQltt(objTmp.getFlagQltt());
              obj.setLddv(objTmp.getLddv() == null ? null : objTmp.getLddv());
              obj.setFlagLddv(objTmp.getFlagLddv());
              obj.setCvp(objTmp.getCvp() == null ? null : objTmp.getCvp());
              obj.setFlasCvp(objTmp.getFlasCvp());
              obj.setReasonRefuse(obj.getReasonRefuse() == null ? null : obj.getReasonRefuse().trim());

              AddingTimeNotiInfo notiInfo = bookingDao.getCarbookingById(obj.getBookCarId());

              vt030006dao.updateRequest(obj);

              try {
                /** get info of current approver */
                VT000000EntitySmsVsNotify inforNotifySmsCurrentApprover = vt000000DAO.findNotifySmsByUserName(obj.getUserName());

                /** tim kiem nguoi yeu cau theo id */
                String userRequest = vt030006dao.findUserRequest(obj.getBookCarId());

                /** get info of requester */
                VT000000EntitySmsVsNotify inforNotifySmsEmp = vt000000DAO.findNotifySmsByUserName(userRequest);
                inforNotifySmsEmp.setToUserName(userRequest);

                /** tim kiem thong tin chuyen xe */
                VT030000NotifySmsInfo info = new VT030000NotifySmsInfo(
                    Constant.MASTER_CLASS_TYPE_CAR, Constant.MASTER_CLASS_SEAT_CAR,
                    Constant.MASTER_CLASS_ROUTE_CAR, obj.getBookCarId());

                VT030000NotifySmsInfo infoCarRoute = vt030000dao.findInfoNotifySms(info);

                if (!obj.getRole().equals(Constant.PMQT_ROLE_MANAGER_DOIXE)) {
                  // if manager approve
                  if (obj.getStatus() == Constant.MANAGER_APPROVED) {
                    // send notify to requester
                    String messageNotify = MessageFormat.format(
                        notifyMessage.getProperty("N14"), inforNotifySmsCurrentApprover.getCreateUser(), 
                        new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                        new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase());
                    String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");

                    AdditionalInfoBase addInfo = new AdditionalInfoBase();
                    addInfo.setId(obj.getBookCarId());
                    addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                    notification.storageNotification(inforNotifySmsEmp.getToUserName(), null,
                        addInfo.toString(), messageNotify, title,
                        Constant.TYPE_NOTIFY_MODUL03, obj.getUserName(),
                        0, Constant.MANAGER_APPROVED);
                    // send SMS to requester
                    String messageSms = MessageFormat.format(smsMessage.getProperty("S14"),inforNotifySmsCurrentApprover.getCreateUser(), 
                        new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                        new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase());
                    String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
                    sms.sendSms(inforNotifySmsEmp.getToUserId(), messageSms,
                        Constant.STATUS_NEW_SMS, inforNotifySmsEmp.getPhone(), typeSms);

                    // the final approval
                    if ((obj.getCvp() == null && obj.getUserName().toLowerCase().equals(obj.getLddv().toLowerCase())) || 
                        (obj.getCvp() != null && obj.getUserName().toLowerCase().equals(obj.getCvp().toLowerCase()))
                        || obj.getRole().equals(Constant.PMQT_ROLE_ADMIN)) {
                      if (isFinalApprove(obj)) {
                        /** Send SMS and Notify for QL_DOI_XE */
                        String userCarSquad = vt030006dao.findManagerCarSquad(obj.getBookCarId());
                        if (userCarSquad != null) {
                          VT000000EntitySmsVsNotify inforNotifySmsManagerCarSquad = vt000000DAO
                              .findNotifySmsByUserName(userCarSquad);
                          inforNotifySmsManagerCarSquad.setToUserName(userCarSquad);
                          /** send notify to Manager Car's squad */	
                          messageNotify = MessageFormat.format(notifyMessage.getProperty("N22"), 
                              notiInfo.getCarType(), notiInfo.getSeat(), 
                              new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE), 
                              new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE), 
                              inforNotifySmsEmp.getCreateUser(), inforNotifySmsEmp.getPhone(), 
                              notiInfo.getEmpUnitName()
                              );
                          title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");

                          addInfo = new AdditionalInfoBase();
                          addInfo.setId(obj.getBookCarId());
                          addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_DOIXE);
                          notification.storageNotification(
                              inforNotifySmsManagerCarSquad.getToUserName(), null,
                              addInfo.toString(), messageNotify, title,
                              Constant.TYPE_NOTIFY_MODUL03, obj.getUserName(),
                              0, Constant.MANAGER_APPROVED);
                          /** send SMS to Manager Car's squad */
                          messageSms = MessageFormat.format(smsMessage.getProperty("S22"), infoCarRoute.getType(), 
                              infoCarRoute.getSeat(), new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE), 
                              new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE), 
                              inforNotifySmsEmp.getCreateUser(), inforNotifySmsEmp.getPhone(), notiInfo.getEmpUnitName()
                              );
                          typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
                          sms.sendSms(inforNotifySmsManagerCarSquad.getToUserId(),
                              messageSms, Constant.STATUS_NEW_SMS,
                              inforNotifySmsManagerCarSquad.getPhone(), typeSms);
                        }
                      } else {
                        /** get username of next approver */
                        String nextApprover = vt030006dao.findNextApprover(obj.getBookCarId());

                        /** get info of next approver */
                        VT000000EntitySmsVsNotify inforNotifySmsNextApprover = vt000000DAO.findNotifySmsByUserName(nextApprover);
                        inforNotifySmsNextApprover.setToUserName(nextApprover);

                        /** send notify to next approver */
                        messageNotify = MessageFormat.format(
                            notifyMessage.getProperty("N15"), inforNotifySmsEmp.getCreateUser(), infoCarRoute.getDateStart(),
                            infoCarRoute.getDateEnd());
                        title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");

                        addInfo = new AdditionalInfoBase();
                        addInfo.setId(obj.getBookCarId());
                        addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                        notification.storageNotification(
                            inforNotifySmsNextApprover.getToUserName(), null,
                            addInfo.toString(), messageNotify, title,
                            Constant.TYPE_NOTIFY_MODUL03, obj.getUserName(),
                            0, Constant.MANAGER_APPROVED);
                        /** send SMS to next approver */
                        messageSms = MessageFormat.format(smsMessage.getProperty("S15"),inforNotifySmsEmp.getCreateUser(),  
                            new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                            new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase());
                        typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
                        sms.sendSms(inforNotifySmsNextApprover.getToUserId(), messageSms,
                            Constant.STATUS_NEW_SMS, inforNotifySmsNextApprover.getPhone(),
                            typeSms);
                      }

                    } else {
                      /** get username of next approver */
                      String nextApprover = vt030006dao.findNextApprover(obj.getBookCarId());

                      /** get info of next approver */
                      VT000000EntitySmsVsNotify inforNotifySmsNextApprover = vt000000DAO
                          .findNotifySmsByUserName(nextApprover);
                      inforNotifySmsNextApprover.setToUserName(nextApprover);

                      /** send notify to next approver */
                      messageNotify = MessageFormat.format(notifyMessage.getProperty("N15"),inforNotifySmsEmp.getCreateUser(),  
                          new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                          new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase());
                      title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");

                      addInfo = new AdditionalInfoBase();
                      addInfo.setId(obj.getBookCarId());
                      addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                      notification.storageNotification(
                          inforNotifySmsNextApprover.getToUserName(), null, addInfo.toString(),
                          messageNotify, title, Constant.TYPE_NOTIFY_MODUL03,
                          obj.getUserName(), 0, Constant.MANAGER_APPROVED);
                      /** send SMS to next approver */
                      messageSms = MessageFormat.format(smsMessage.getProperty("S15"),inforNotifySmsEmp.getCreateUser(),  
                          new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                          new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase());
                      typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
                      sms.sendSms(inforNotifySmsNextApprover.getToUserId(), messageSms,
                          Constant.STATUS_NEW_SMS, inforNotifySmsNextApprover.getPhone(), typeSms);
                    }
                  } else if (obj.getStatus() == Constant.MANAGER_ABANDON) {
                    /** send notify to requester */	
                    if(status == Constant.WAIT_MANAGER_APPROVE) {
                      String messageNotify = MessageFormat.format(
                          notifyMessage.getProperty("N13"), inforNotifySmsCurrentApprover.getCreateUser(), 
                          new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                          new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                          infoCarRoute.getReasonRefuse().toUpperCase());
                      String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");

                      AdditionalInfoBase addInfo = new AdditionalInfoBase();
                      addInfo.setId(obj.getBookCarId());
                      addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                      notification.storageNotification(inforNotifySmsEmp.getToUserName(), null,
                          addInfo.toString(), messageNotify, title,
                          Constant.TYPE_NOTIFY_MODUL03, obj.getUserName(),
                          0, Constant.MANAGER_APPROVED);
                      /*  send SMS to requester */
                      String messageSms = MessageFormat.format(smsMessage.getProperty("S13"), inforNotifySmsCurrentApprover.getCreateUser(),  
                          new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                          new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(), infoCarRoute.getReasonRefuse());
                      String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
                      sms.sendSms(inforNotifySmsEmp.getToUserId(), messageSms,
                          Constant.STATUS_NEW_SMS, inforNotifySmsEmp.getPhone(), typeSms);

                    } else if(status == Constant.MANAGER_APPROVED) { 
                      String messageNotify = MessageFormat.format(
                          notifyMessage.getProperty("N16"), notiInfo.getEmpName(), infoCarRoute.getType(),
                          infoCarRoute.getSeat(),  new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                          new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                          infoCarRoute.getReasonAssigner().toUpperCase().trim());
                      String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");

                      AdditionalInfoBase addInfo = new AdditionalInfoBase();
                      addInfo.setId(obj.getBookCarId());
                      addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                      notification.storageNotification(inforNotifySmsEmp.getToUserName(), null,
                          addInfo.toString(), messageNotify, title,
                          Constant.TYPE_NOTIFY_MODUL03, obj.getUserName(),
                          0, Constant.MANAGER_APPROVED);
                      /**  send SMS to requester */								
                      String messageSms = MessageFormat.format(smsMessage.getProperty("S16"),
                          infoCarRoute.getType().toUpperCase(), infoCarRoute.getSeat().toUpperCase(), 
                          new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                          new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                          infoCarRoute.getReasonAssigner().toUpperCase().trim());
                      String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
                      sms.sendSms(inforNotifySmsEmp.getToUserId(), messageSms,
                          Constant.STATUS_NEW_SMS, inforNotifySmsEmp.getPhone(), typeSms);

                      //TODO: rewrite send noti
                      if(notiInfo.getFlagQltt() == 1 && notiInfo.getQlttUsername() != null) {
                        VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getQlttUsername());
                        approver.setToUserName(notiInfo.getQlttUsername());
                        addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                        bookingService.storgeNotiAndSms(approver, messageSms, title, addInfo);
                      }

                      if(notiInfo.getFlagLddv() == 1 && notiInfo.getQldvUsername() != null) {
                        VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getQldvUsername());
                        approver.setToUserName(notiInfo.getQldvUsername());
                        addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                        bookingService.storgeNotiAndSms(approver, messageSms, title, addInfo);
                      }
                      
                      if(notiInfo.getFlagCvp() == 1 && notiInfo.getCvpUsername() != null) {
                        VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getCvpUsername());
                        approver.setToUserName(notiInfo.getCvpUsername());
                        addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_CVP);
                        bookingService.storgeNotiAndSms(approver, messageSms, title, addInfo);
                      }
                    } 

                    if(status == Constant.WAIT_MANAGER_APPROVE_STILL) {
                      AdditionalInfoBase addInfo = new AdditionalInfoBase();
                      addInfo.setId(obj.getBookCarId());
                      addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);

                      VT000000EntitySmsVsNotify rejectedBy = vt000000DAO.findNotifySmsByUserName(obj.getUserName());
                      rejectedBy.setToUserName(obj.getUserName());
                      
                      VT000000EntitySmsVsNotify user = vt000000DAO.findNotifySmsByUserName(notiInfo.getEmpUsername());
                      user.setToUserName(notiInfo.getEmpUsername());

                      String messageNotify = MessageFormat.format(
                          notifyMessage.getProperty("N104"), rejectedBy.getCreateUser(),
                          new DateUtil(notiInfo.getDateStart()).toString(DateUtil.FORMAT_DATE),
                          new DateUtil(notiInfo.getDateEnd()).toString(DateUtil.FORMAT_DATE));
                      String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
                      bookingService.storgeNotiAndSms(user, messageNotify, title, addInfo);
                    }
                  } else if(obj.getStatus() == 9) { 
                    String messageSms = MessageFormat.format(
                        notifyMessage.getProperty("N16"), notiInfo.getEmpName(), infoCarRoute.getType(),
                        infoCarRoute.getSeat(),  new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                        new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(), infoCarRoute.getReasonAssigner().toUpperCase().trim());
                    String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");

                    AdditionalInfoBase addInfo = new AdditionalInfoBase();
                    addInfo.setId(obj.getBookCarId());
                    addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                    
                    if(notiInfo.getEmpUsername() != null) {
                      VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getEmpUsername());
                      approver.setToUserName(notiInfo.getEmpUsername());
                      addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                      bookingService.storgeNotiAndSms(approver, messageSms, title, addInfo);
                    }
                    
//                    if(notiInfo.getSquadLeadUsername() != null) {
//                      VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getSquadLeadUsername());
//                      approver.setToUserName(notiInfo.getSquadLeadUsername());
//                      addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_DOIXE);
//                      bookingService.storgeNotiAndSms(approver, messageSms, title, addInfo);
//                    }
                    
                    if(notiInfo.getFlagQltt() == 1 && notiInfo.getQlttUsername() != null) {
                      VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getQlttUsername());
                      approver.setToUserName(notiInfo.getQlttUsername());
                      addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                      bookingService.storgeNotiAndSms(approver, messageSms, title, addInfo);
                    }

                    if(notiInfo.getFlagLddv() == 1 && notiInfo.getQldvUsername() != null) {
                      VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getQldvUsername());
                      approver.setToUserName(notiInfo.getQldvUsername());
                      addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                      bookingService.storgeNotiAndSms(approver, messageSms, title, addInfo);
                    }
                  }else if(obj.getStatus() == 3){
                    String messageSms = MessageFormat.format(
                        notifyMessage.getProperty("N16"), notiInfo.getEmpName(), infoCarRoute.getType(),
                        infoCarRoute.getSeat(),  new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                        new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(), infoCarRoute.getReasonAssigner().toUpperCase().trim());
                    String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");

                    AdditionalInfoBase addInfo = new AdditionalInfoBase();
                    addInfo.setId(obj.getBookCarId());
                    addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                    if(notiInfo.getSquadLeadUsername() != null) {
                    VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getSquadLeadUsername());
                    approver.setToUserName(notiInfo.getSquadLeadUsername());
                    addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_DOIXE);
                    bookingService.storgeNotiAndSms(approver, messageSms, title, addInfo);
                    }
                  }
                  
                  /**  Manager Car'squad approve */
                } else {
                  if (obj.getStatus() == Constant.MANAGER_ABANDON) {
                    /**  send notify to requester */
                    String messageNotify = MessageFormat.format(
                        notifyMessage.getProperty("N16"), notiInfo.getEmpName(), infoCarRoute.getType(),
                        infoCarRoute.getSeat(),  new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                        new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(), infoCarRoute.getReasonAssigner());
                    String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");

                    AdditionalInfoBase addInfo = new AdditionalInfoBase();
                    addInfo.setId(obj.getBookCarId());
                    addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                    notification.storageNotification(inforNotifySmsEmp.getToUserName(), null,
                        addInfo.toString(), messageNotify, title,
                        Constant.TYPE_NOTIFY_MODUL03, obj.getUserName(),
                        0, Constant.MANAGER_APPROVED);
                    /**  send SMS to requester */
                    String messageSms = MessageFormat.format(smsMessage.getProperty("S16"),
                        infoCarRoute.getType().toUpperCase(), infoCarRoute.getSeat().toUpperCase(), new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                        new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(), infoCarRoute.getReasonAssigner());
                    String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL03_APPROVE");
                    sms.sendSms(inforNotifySmsEmp.getToUserId(), messageSms,
                        Constant.STATUS_NEW_SMS, inforNotifySmsEmp.getPhone(), typeSms);

                  }
                  
                  if(obj.getStatus() == 9) { 
                    String messageSms = MessageFormat.format(
                        notifyMessage.getProperty("N16"), notiInfo.getEmpName(), infoCarRoute.getType(),
                        infoCarRoute.getSeat(),  new DateUtil(infoCarRoute.getDateStart()).toString(DateUtil.FORMAT_DATE).toUpperCase(), 
                        new DateUtil(infoCarRoute.getDateEnd()).toString(DateUtil.FORMAT_DATE).toUpperCase(), infoCarRoute.getReasonAssigner());
                    String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");

                    AdditionalInfoBase addInfo = new AdditionalInfoBase();
                    addInfo.setId(obj.getBookCarId());
                    addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                    
                    if(notiInfo.getEmpUsername() != null) {
                      VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getEmpUsername());
                      approver.setToUserName(notiInfo.getEmpUsername());
                      addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
                      bookingService.storgeNotiAndSms(approver, messageSms, title, addInfo);
                    }
                    
                    if(notiInfo.getFlagQltt() == 1 && notiInfo.getQlttUsername() != null) {
                      VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getQlttUsername());
                      approver.setToUserName(notiInfo.getQlttUsername());
                      addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                      bookingService.storgeNotiAndSms(approver, messageSms, title, addInfo);
                    }

                    if(notiInfo.getFlagLddv() == 1 && notiInfo.getQldvUsername() != null) {
                      VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getQldvUsername());
                      approver.setToUserName(notiInfo.getQldvUsername());
                      addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
                      bookingService.storgeNotiAndSms(approver, messageSms, title, addInfo);
                    }

                    /*if(notiInfo.getFlagCvp() == 1 && notiInfo.getCvpUsername() != null) {
                      VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getCvpUsername());
                      approver.setToUserName(notiInfo.getCvpUsername());
                      addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_CVP);
                      bookingService.storgeNotiAndSms(approver, messageSms, title, addInfo);
                    }*/
                  } 
                }
              } catch (Exception e) {
                logger.error(e.getMessage(), e);
              }
              response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
            }
          } else {
            return new ResponseEntityBase(Constant.MANAGER_HAS_APPROVED, null);
          }
        }
      }
    } catch (Exception e) {
      response.setData(null);
      response.setStatus(Constant.RESPONSE_STATUS_ERROR);
      logger.error(e.getMessage(), e);
    }
    
    Thread sendNotiAndSmsThread = new Thread() {
      @Override
      public void run() {
        try {
          vt000000Service.sendNotifyEachMinute();
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
        }
      }
    };
    sendNotiAndSmsThread.start();
    logger.info("********************* START EXECUTE APIVT030006_01 ***********************");
    return response;
  }
  
  private boolean isFinalApprove(VT030006UpdateBookCar obj) {
    if ((obj.getCvp() == null && obj.getFlagLddv() == 0) || (obj.getFlagLddv() == 1)) {
      return true;
    }
    
    if((obj.getFlagQltt() == 1 || obj.getQltt() == null) && obj.getCvp() != null && 
        obj.getUserName().toLowerCase().equals(obj.getCvp().toLowerCase()) &&
        obj.getCvp().equals(obj.getLddv())) {
      return true;
    }
    
    return false;
  }
}
