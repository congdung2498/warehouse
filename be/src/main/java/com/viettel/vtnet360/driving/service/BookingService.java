package com.viettel.vtnet360.driving.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.driving.dto.AddingTimeNotiInfo;
import com.viettel.vtnet360.driving.dto.CarBookingAlert;
import com.viettel.vtnet360.driving.dto.CarBookingModel;
import com.viettel.vtnet360.driving.request.dao.BookingDAO;
import com.viettel.vtnet360.driving.request.dao.DrivingDAO;
import com.viettel.vtnet360.kitchen.menu.dao.LunchDAO;
import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;
import com.viettel.vtnet360.vt03.Common.DateUtil;
import com.viettel.vtnet360.vt03.vt030000.dao.VT030000DAO;
import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityDriveSquad;
import com.viettel.vtnet360.vt03.vt030002.dao.VT030002DAO;
import com.viettel.vtnet360.vt03.vt030010.dao.VT030010DAO;

@Service
public class BookingService {
  
  @Autowired
  private DrivingDAO drivingDAO;
  
  @Autowired
  private BookingDAO bookingDao;
  
  @Autowired
  private VT030000DAO vt030000dao;
  
  @Autowired
  private VT030002DAO vt030002dao;
  
  @Autowired
  private VT030010DAO vt030010dao;
  
  @Autowired
  private VT000000DAO vt000000DAO;
  
  @Autowired
  private Notification notification;

  @Autowired
  private Sms sms;
  
  @Autowired
  private Properties notifyMessage;
  
  @Autowired
  private Properties smsMessage;
  
  
  public void checkCarbookingToAlert() {
    int minute = bookingDao.getCarBookingMinute();
    int limit = bookingDao.getCarBookingCount();
    List<String> carBookingIds = bookingDao.getCarbookingToAlert(new Date());
    List<CarBookingAlert> carBookingAlerted = bookingDao.getCarbookingAlerted();
    
    for(CarBookingAlert alertCarBooking : carBookingAlerted) {
      if(carBookingIds.contains(alertCarBooking.getBookingId())) {
        if(alertCarBooking.getMinutes() < minute) {
          alertCarBooking.setMinutes(alertCarBooking.getMinutes() + 1);
          bookingDao.updateBookingAlert(alertCarBooking);
        } else if(alertCarBooking.getMinutes() == minute) {
          alertCarBooking.setCount(alertCarBooking.getCount() + 1);
          AddingTimeNotiInfo notiInfo = bookingDao.getCarbookingById(alertCarBooking.getBookingId());
          if(alertCarBooking.getCount() == limit) {
            bookingDao.deleteCarBookingAlert(alertCarBooking.getBookingId());
            cancelCarBooking(alertCarBooking.getBookingId(), notiInfo, minute, limit );
          } else {
            sendSmsAlert(notiInfo, limit, alertCarBooking.getCount() * minute, limit * minute);
            alertCarBooking.setMinutes(1);
            bookingDao.updateBookingAlert(alertCarBooking);
          }
        }
        carBookingIds.remove(alertCarBooking.getBookingId());
      } else {
        bookingDao.deleteCarBookingAlert(alertCarBooking.getBookingId());
      }
    }
    
    if(carBookingIds.size() > 0) {
      for(String bookingId : carBookingIds) {
        bookingDao.createBookingAlert(bookingId);
      }
    }
  }
  
  @Transactional(readOnly = true)
  public CarBookingModel getCarBookingModel() {
    CarBookingModel model = new CarBookingModel();
    model.setSquads(vt030000dao.findAllSquad(new VT030000EntityDriveSquad()));
    model.setCarTypes(vt030002dao.getCarType());
    model.setSeats(vt030002dao.getCarSeat2());
    model.setJourneyTypes(vt030010dao.getJourney());
    model.setPlaces(drivingDAO.searchPlaceStartAll());
    return model;
  }
  
  @Transactional
  public void storgeNotiAndSms(VT000000EntitySmsVsNotify userInfo, String message, String title, AdditionalInfoBase addInfo) throws Exception {
    notification.storageNotification(userInfo.getToUserName(), null, addInfo.toString(),
        message, title, Constant.TYPE_NOTIFY_MODUL03, userInfo.getToUserName(),
        0, Constant.MANAGER_APPROVED);
    sms.sendSms(userInfo.getToUserId(), message, Constant.STATUS_NEW_SMS, userInfo.getPhone(), title);
  }
  
  @Transactional
  public void sendNotiAndSms(VT000000EntitySmsVsNotify userInfo, String message, String title, AdditionalInfoBase addInfo) {
    notification.sendNotification(userInfo.getToUserName(), addInfo.toString(),
        message, title, Constant.TYPE_NOTIFY_MODUL03, userInfo.getToUserName(),
        Constant.MANAGER_APPROVED);
    sms.sendSms(userInfo.getToUserId(), message, Constant.STATUS_NEW_SMS, userInfo.getPhone(), title);
  }
  
  public void sendNotiAndSms(VT000000EntitySmsVsNotify userInfo, String message, String notiTitle, String smsType, AdditionalInfoBase addInfo) {
    notification.sendNotification(userInfo.getToUserName(), addInfo.toString(),
        message, notiTitle, Constant.TYPE_NOTIFY_MODUL03, userInfo.getToUserName(),
        Constant.MANAGER_APPROVED);
    sms.sendSms(userInfo.getToUserId(), message, Constant.STATUS_NEW_SMS, userInfo.getPhone(), smsType);
  }
  
  private void cancelCarBooking(String bookingId, AddingTimeNotiInfo notiInfo, int minutes, int limit) {
    bookingDao.updateCancelCarBooking(bookingId);
    bookingDao.updateCancelStatusCar(bookingId);
    bookingDao.updateCancelStatusDriveCar(bookingId);
    bookingDao.updateCancelStatusDriver(bookingId);
    Thread notiThread = new Thread() {
      public void run() {
        cancelSendAlert(notiInfo, minutes, limit);
      }
    };
    notiThread.start();
  }
  
  private void sendSmsAlert(AddingTimeNotiInfo notiInfo, int limit, int minutes, int minuteTotal) {
    String title = "SMS BOOK CAR REMIND";
    String message = MessageFormat.format(smsMessage.getProperty("S113"),
        new DateUtil(notiInfo.getDateStart()).toString(DateUtil.FORMAT_DATE),
        new DateUtil(notiInfo.getDateEnd()).toString(DateUtil.FORMAT_DATE),
        minutes, minuteTotal);
    
    VT000000EntitySmsVsNotify inforNotifySmsEmp = vt000000DAO.findNotifySmsByUserName(notiInfo.getEmpUsername());
    inforNotifySmsEmp.setToUserName(notiInfo.getEmpUsername());
    
    sms.sendSms(inforNotifySmsEmp.getToUserId(), message, Constant.STATUS_NEW_SMS,
        inforNotifySmsEmp.getPhone(), title);
    
    if(notiInfo.getStatus() == 4 && notiInfo.getDriverUsername() != null) {
      VT000000EntitySmsVsNotify driver = vt000000DAO.findNotifySmsByUserName(notiInfo.getDriverUsername());
      driver.setToUserName(notiInfo.getDriverUsername());
      
      sms.sendSms(driver.getToUserId(), message, Constant.STATUS_NEW_SMS,
          driver.getPhone(), title);
    }
  }
  
  private void cancelSendAlert(AddingTimeNotiInfo notiInfo, int minutes, int limit) {
    String smsTitle = "SMS BOOK CAR CANCEL";
    String notiTitle = notifyMessage.getProperty("TITLE_NOTIFY_MODUL03");
    
    String message = MessageFormat.format(
        notifyMessage.getProperty("N111"), notiInfo.getEmpName(), 
        new DateUtil(notiInfo.getDateStart()).toString(DateUtil.FORMAT_DATE),
        new DateUtil(notiInfo.getDateEnd()).toString(DateUtil.FORMAT_DATE),
        minutes * limit, limit);
    
    VT000000EntitySmsVsNotify inforNotifySmsEmp = vt000000DAO.findNotifySmsByUserName(notiInfo.getEmpUsername());
    inforNotifySmsEmp.setToUserName(notiInfo.getEmpUsername());
    
    AdditionalInfoBase addInfo = new AdditionalInfoBase();
    addInfo.setId(notiInfo.getBookingId());
    addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
    sendNotiAndSms(inforNotifySmsEmp, message, notiTitle, smsTitle, addInfo);


    if(notiInfo.getFlagQltt() == 1 && notiInfo.getQlttUsername() != null) {
      VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getQlttUsername());
      approver.setToUserName(notiInfo.getQlttUsername());
      addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
      sendNotiAndSms(approver, message, notiTitle, smsTitle, addInfo);
    }

    if(notiInfo.getFlagLddv() == 1 && notiInfo.getQldvUsername() != null) {
      VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getQldvUsername());
      approver.setToUserName(notiInfo.getQldvUsername());
      addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
      sendNotiAndSms(approver, message, notiTitle, smsTitle, addInfo);
    }
    
    if(notiInfo.getFlagCvp() == 1 && notiInfo.getCvpUsername() != null && !notiInfo.getCvpUsername().equals(notiInfo.getCvpUsername())) {
      VT000000EntitySmsVsNotify approver = vt000000DAO.findNotifySmsByUserName(notiInfo.getCvpUsername());
      approver.setToUserName(notiInfo.getCvpUsername());
      addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER);
      sendNotiAndSms(approver, message, notiTitle, smsTitle, addInfo);
    }
    
    if(notiInfo.getDriverUsername() != null) {
      VT000000EntitySmsVsNotify driver = vt000000DAO.findNotifySmsByUserName(notiInfo.getDriverUsername());
      driver.setToUserName(notiInfo.getDriverUsername());
      addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYEE_DOIXE);
      sendNotiAndSms(driver, message, notiTitle, smsTitle, addInfo);
    }
    
    if(notiInfo.getSquadLeadUsername() != null) {
      VT000000EntitySmsVsNotify carLeader = vt000000DAO.findNotifySmsByUserName(notiInfo.getSquadLeadUsername());
      carLeader.setToUserName(notiInfo.getSquadLeadUsername());
      addInfo.setRoleReceiver(Constant.PMQT_ROLE_MANAGER_DOIXE);
      sendNotiAndSms(carLeader, message, notiTitle, smsTitle, addInfo);
    }
  }
}
