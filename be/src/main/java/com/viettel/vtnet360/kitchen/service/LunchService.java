package com.viettel.vtnet360.kitchen.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viettel.vtnet360.kitchen.dto.ActionLog;
import com.viettel.vtnet360.kitchen.dto.AdditionalVoteBase;
import com.viettel.vtnet360.kitchen.dto.GettingLunch;
import com.viettel.vtnet360.kitchen.dto.LunchDTO;
import com.viettel.vtnet360.kitchen.dto.LunchModel;
import com.viettel.vtnet360.kitchen.dto.LunchVoteMessage;
import com.viettel.vtnet360.kitchen.dto.MenuSetting;
import com.viettel.vtnet360.kitchen.dto.SearchingLunchModel;
import com.viettel.vtnet360.kitchen.dto.SearchingMenuByDate;
import com.viettel.vtnet360.kitchen.dto.UpdatingAllLunch;
import com.viettel.vtnet360.kitchen.menu.dao.LunchDAO;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.InputValidate;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt02.vt020006.dao.VT020006DAO;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoToCheckKitchenExist;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006InfoToInsertDailyMeals;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006Lunch;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006LunchInfo;
import com.viettel.vtnet360.vt02.vt020006.entity.VT020006ParamRequest;
import com.viettel.vtnet360.vt03.Common.DateUtil;

@Service
public class LunchService {
  private final Logger logger = Logger.getLogger(this.getClass());
  private InputValidate validate = new InputValidate();

  @Autowired
  private VT020006DAO   vt020006DAO;

  @Autowired
  private LunchDAO      lunchDao;
  
  @Autowired
  private Sms           sms;

  @Autowired
  private Notification  notification;
  
  
  @Transactional
  public void sendSmsToUserRemindLunch() throws Exception {
    List<LunchUser> lunchUsers = lunchDao.getRemindLunchUsers();
    String message = "Đ/c chưa đặt ăn tháng tiếp theo";
    String title = "NHẮC NHỞ ĐẶT ĂN TRƯA";
    for(LunchUser user : lunchUsers) {
      notification.storageNotification(user.getUsername(), null, null,
          message, title, Constant.TYPE_NOTIFY_MODUL02, user.getUsername(), 0, 0);
      sms.sendSms(user.getUserId(), message, Constant.STATUS_NEW_SMS, user.getPhone(), title);
    }
  }
  
  @Transactional
  public void updateUnitLunch() {
    logger.info("=============== updateUnitLunch START ================ ");
    lunchDao.updateUnitLunch();
    logger.info("=============== updateUnitLunch END ================== ");
  }
  
  @Transactional(readOnly = true)
  public LunchDTO getLunchByIdForMobile(GettingLunch lunch) {
    return lunchDao.getLunchByIdForMobile(lunch);
  }
  
  @Transactional
  public void createLunchVoteMessage() {
    List<LunchVoteMessage> messages = lunchDao.getListLunchVoteMessage();
    String smsMessage = "Moi D/c danh gia va cho y kien bua trua hom nay tren VTNet360 truoc 15h";
    String notiMessage = "Mời Đ/c đánh giá và cho ý kiến bữa trưa hôm nay trên VTNet360 trước 15h";
    String notiTitle = "ĐÁNH GIÁ BỮA ĂN TRƯA";
    if(messages != null && messages.size() > 0) {
      for(LunchVoteMessage message : messages) {
        sms.sendSms(message.getEmployeeId(), smsMessage, Constant.STATUS_NEW_SMS, message.getPhone(), "SMS VOTE LUNCH");
        
        AdditionalVoteBase addInfo = new AdditionalVoteBase(message.getLunchDate());
        addInfo.setId(message.getLunchId());
        addInfo.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
        notification.sendNotification(message.getUserName(), addInfo.toString(), notiMessage, notiTitle, Constant.TYPE_NOTIFY_MODUL02, "SYSTEM", 1);
      }
    }
    
    /*Calendar calendar = Calendar.getInstance();
    int lastDayOfMonth = calendar.getActualMaximum(Calendar.DATE);
    int today = calendar.get(Calendar.DAY_OF_MONTH);
    if(lastDayOfMonth == today) {
      Thread notiThread = new Thread() {
        public void run() {
          try {
            Thread.sleep(600000);
            sendSmsToUserRemindLunch();
          } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(e);
          }
        }
      };
      notiThread.start();
    }*/
  }
  
  @Transactional(readOnly = true)
  public String getParentUnitId(String unitId) {
    return lunchDao.getParentUnitId(unitId);
  }
  
  @Transactional(readOnly  = true)
  public String getExistedLunchMessage(VT020006ParamRequest param) {
    List<Date> listLunchDate = new ArrayList<>();
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(param.getStartTime());
    
    Calendar sysDate = Calendar.getInstance();
    validate.validateDate(calendar.getTime());
    validate.validateDate(sysDate.getTime());
    
    if(calendar.getTimeInMillis() > sysDate.getTimeInMillis() && calendar.getTimeInMillis() < sysDate.getTimeInMillis() + 1000 * 24 * 60 * 60 ) {
      if (sysDate.get(Calendar.HOUR_OF_DAY) < Constant.TIME_CHANGE_ORDER_LUNCH_DATE_HOUR
          || (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CHANGE_ORDER_LUNCH_DATE_HOUR
              && sysDate.get(Calendar.MINUTE) <= Constant.TIME_CHANGE_ORDER_LUNCH_DATE_MINUTE)) {
        calendar.add(Calendar.DAY_OF_MONTH, 2);
      } else {
        calendar.add(Calendar.DAY_OF_MONTH, 1);
      }
    }
    
    while (calendar.getTime().before(param.getEndTime())) {
      Date result = calendar.getTime();
      listLunchDate.add(result);
      calendar.add(Calendar.DATE, 1);
    }
    listLunchDate.add(param.getEndTime());
    
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String message = null;
    for(Date date : listLunchDate) {
      LunchDTO lunch = lunchDao.getLunchByUser(new VT020006ParamRequest(date, param.getUserName()));
      if(lunch != null && lunch.getQuantity() > 0) {
        if(message == null) message = dateFormat.format(date).toString() + " (" + lunch.getQuantity() + " suất)";
        else message = message + ", " + dateFormat.format(date).toString() + " (" + lunch.getQuantity() + " suất)";
      }
    }
    
    return message;
  }
  
  @Transactional(readOnly = true)
  public List<MenuSetting> getMenuSettingByDate(SearchingMenuByDate searching) {
    return lunchDao.getMenuSettingByDate(searching);
  }
  
  @Transactional(readOnly = true)
  public LunchModel getLunchModelByDay(SearchingLunchModel searchingConfig) {
    List<LunchDTO> lunches = lunchDao.getLunchByDay(searchingConfig);
    int totalRecords = lunchDao.getTotalLunchByDay(searchingConfig);
    return new LunchModel(lunches, totalRecords);
  }
  
  @Transactional(readOnly = true)
  public LunchModel getLunchModelByPeriod(SearchingLunchModel searchingConfig) {
    List<Integer> dayOfweeks = convertDayOfWeekToDB(searchingConfig.getListPeriodic());
    searchingConfig.setListPeriodic(dayOfweeks);
    List<LunchDTO> lunches = lunchDao.getLunchByPeriod(searchingConfig);
    int totalRecords = lunchDao.getTotalLunchByPeriod(searchingConfig);
    return new LunchModel(lunches, totalRecords);
  }

  @Transactional
  public ResponseEntityBase createLunchByPeriodic(VT020006ParamRequest param) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    String existUser = lunchDao.checkUserExist(param.getUserName());
    if(existUser == null || existUser.isEmpty()) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      return resp;
    }
    if(param.getMonths() == null || param.getMonths().length == 0) return resp;
    
    Date startTime = new Date();
    int checkKitchenID = vt020006DAO.checkKitchenExisted(new VT020006InfoToCheckKitchenExist(param.getKitchenID(), Constant.STATUS_ACTIVE));
    if (checkKitchenID == Constant.NONE_EXIST_RECORD) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      return resp;
    } 
    if (param == null || param.getListPeriodic().isEmpty()) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      return resp;
    }
    
    Calendar sysDate = Calendar.getInstance();
    Arrays.sort(param.getMonths());
    
    if((param.getMonths()[0] - 1) == sysDate.get(Calendar.MONTH) && param.getYear() == sysDate.get(Calendar.YEAR)) {
      if (sysDate.get(Calendar.HOUR_OF_DAY) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
          || (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
          && sysDate.get(Calendar.MINUTE) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_MINUTE)) {
        if(isCheckLimit(param)) sysDate.add(Calendar.DAY_OF_MONTH, 2);
        else sysDate.add(Calendar.DAY_OF_MONTH, 1);
      } else {
        sysDate.add(Calendar.DAY_OF_MONTH, 1);
      }
    } else {
      sysDate.set(Calendar.YEAR, param.getYear());
      sysDate.set(Calendar.MONTH, param.getMonths()[0] - 1);
      sysDate.set(Calendar.DAY_OF_MONTH, 1);
    }
      
    sysDate.setTime(validate.validateDate(sysDate.getTime()));
    
    //delete lunch by months and year
    param.setLunchDate(sysDate.getTime());
    lunchDao.deleteByMonth(param);
    
    List<Date> listLunchDate = new ArrayList<>();
    List<Date> listDayOff = vt020006DAO.findDayOff(Constant.STATUS_ACTIVE);
    for(int i = 0; i < param.getMonths().length; i++) {
      Calendar dayOfMonth = Calendar.getInstance();
      dayOfMonth.set(Calendar.YEAR, param.getYear());
      dayOfMonth.set(Calendar.MONTH, param.getMonths()[i] -1);
      dayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
      

      Calendar lastDayOfMonth = Calendar.getInstance();
      lastDayOfMonth.set(Calendar.YEAR, param.getYear());
      lastDayOfMonth.set(Calendar.MONTH, param.getMonths()[i]);
      lastDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
      lastDayOfMonth.add(Calendar.DATE, -1);

      for (int k = 1; k <= lastDayOfMonth.get(Calendar.DAY_OF_MONTH); k++) {
        dayOfMonth.set(Calendar.DAY_OF_MONTH, k);
        int dayOfWeek = dayOfMonth.get(Calendar.DAY_OF_WEEK);
        if (param.getListPeriodic().contains(dayOfWeek) && !listDayOff.contains(validate.validateDate(dayOfMonth.getTime()))) {
          if (validate.validateDate(dayOfMonth.getTime()).compareTo(validate.validateDate(sysDate.getTime())) >= 0) {
            listLunchDate.add(dayOfMonth.getTime());
          }
        }
      }
    }
    
    if(listLunchDate.size() > 0) {
      int unitId = lunchDao.getUnitIdByUsername(param.getUserName());
      
      VT020006Lunch lunch = new VT020006Lunch(param.getUserName(), null, Constant.QUANTITY_DEFAULT,
          Constant.HAS_BOOKING_DEFAULT, param.getKitchenID(), unitId, Constant.PERIODIC_DEFAULT, listLunchDate);
      vt020006DAO.insertLunchDate(lunch);
      
      String content = "Create Lunch By Periodic in year" + param.getYear() + ", in month " + Arrays.toString(param.getMonths()) + 
          ", on day of week " + param.getListPeriodic().toString();
      createActionLog("CREATE", startTime, new Date(), param.getUserName(), 1, content);
    }
    return resp;
  }
  
  boolean isCheckLimit(VT020006ParamRequest param) {
    int totalOld;
    int totalNow;
    int quantityOld;
    Calendar sysDate = Calendar.getInstance();
    sysDate.add(Calendar.DAY_OF_MONTH, 1);

    // find info of old order
    VT020006Lunch infoToFindOldOrder = new VT020006Lunch();
    infoToFindOldOrder.setKitchenID(param.getKitchenID());
    infoToFindOldOrder.setLunchDate(validate.validateDate(sysDate.getTime()));
    infoToFindOldOrder.setUserName(param.getUserName());
    VT020006LunchInfo lunchInfo = vt020006DAO.findLunchInfo(infoToFindOldOrder);
    // if kitchenID old != kitchenID new => oldQuantity = 0
    if (lunchInfo != null && lunchInfo.getKitchenID().equals(param.getKitchenID())) {
      quantityOld = lunchInfo.getQuantity();
    } else {
      quantityOld = 0;
    }
    // find total number of lunchDate in this kitchen save in 16h30
    totalOld = vt020006DAO.findTotalLunchDateOld(
        new VT020006InfoToInsertDailyMeals(param.getKitchenID(), 0, sysDate.getTime()));
    // find total number of lunchDate in this kitchen in now
    totalNow = vt020006DAO.findTotalLunchDateNow(
        new VT020006InfoToInsertDailyMeals(param.getKitchenID(), 0, sysDate.getTime()));
    if (!checkConditionChangeNumberOfLunchDate(totalOld, totalNow, quantityOld, 1)) {
      return false;
    }
    return true;
  }
  
  @Transactional
  public void updateVoting(LunchDTO lunch) {
    lunchDao.updateVoting(lunch);
  }
  
  @Transactional
  public void updateAllLunch(UpdatingAllLunch config) throws Exception {
    Date startTime = new Date();
    List<String> lunchIds = new ArrayList<>();
    for(String lunchId : config.getLunchIds()) {
      LunchDTO lunch = lunchDao.getLunchById(lunchId);
      Calendar sysDate = Calendar.getInstance();
      Calendar dateForLunch = Calendar.getInstance();
      dateForLunch.setTime(lunch.getLunchDate());
      
      if (validate.validateDate(dateForLunch.getTime()).compareTo(validate.validateDate(sysDate.getTime())) == 0) {
        if (sysDate.get(Calendar.HOUR_OF_DAY) < Constant.TIME_CHANGE_ORDER_LUNCH_DATE_HOUR
            || (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CHANGE_ORDER_LUNCH_DATE_HOUR
                && sysDate.get(Calendar.MINUTE) <= Constant.TIME_CHANGE_ORDER_LUNCH_DATE_MINUTE)) {
          int quantityOld = lunch.getQuantity();
          int quantityNow = config.getQuantity();
          int totalOld = vt020006DAO.findTotalLunchDateOld(
              new VT020006InfoToInsertDailyMeals(lunch.getKitchenId(), 0, lunch.getLunchDate()));
          int totalNow = vt020006DAO.findTotalLunchDateNow(
              new VT020006InfoToInsertDailyMeals(lunch.getKitchenId(), 0, lunch.getLunchDate()));
          
          if(config.getHasBooking() == 0) quantityNow = 0;
          if (checkConditionChangeNumberOfLunchDate(totalOld, totalNow, quantityOld, quantityNow)) {
            lunchIds.add(lunchId);
          }
        }
      } else {
        lunchIds.add(lunchId);
      }
    }
    
    String[] resultArray = new String[lunchIds.size()];
    resultArray = lunchIds.toArray(resultArray);
    config.setLunchIds(resultArray);
    if(config.getQuantity() <= 0) config.setHasBooking(0);
    ObjectMapper mapper = new ObjectMapper();
    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(config));
    if(config.getLunchIds().length > 0) {
      lunchDao.updateAllLunch(config);
    }
    String content = "Update Lunch by ids " + Arrays.toString(config.getLunchIds());
    createActionLog("UPDATE", startTime, new Date(), config.getLoginUsername(), 1, content);
  }
  
  @Transactional
  public void deleteLunch(UpdatingAllLunch config) throws JsonProcessingException {
    Date startTime = new Date();
    List<String> lunchIds = new ArrayList<>();
    for(String lunchId : config.getLunchIds()) {
      LunchDTO lunch = lunchDao.getLunchById(lunchId);
      Calendar sysDate = Calendar.getInstance();
      Calendar dateForLunch = Calendar.getInstance();
      dateForLunch.setTime(lunch.getLunchDate());
      
      if (validate.validateDate(dateForLunch.getTime()).compareTo(validate.validateDate(sysDate.getTime())) == 0) {
        if (sysDate.get(Calendar.HOUR_OF_DAY) < Constant.TIME_CHANGE_ORDER_LUNCH_DATE_HOUR
            || (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CHANGE_ORDER_LUNCH_DATE_HOUR
                && sysDate.get(Calendar.MINUTE) <= Constant.TIME_CHANGE_ORDER_LUNCH_DATE_MINUTE)) {
          int quantityOld = lunch.getQuantity();
          int quantityNow = config.getQuantity();
          int totalOld = vt020006DAO.findTotalLunchDateOld(
              new VT020006InfoToInsertDailyMeals(lunch.getKitchenId(), 0, lunch.getLunchDate()));
          int totalNow = vt020006DAO.findTotalLunchDateNow(
              new VT020006InfoToInsertDailyMeals(lunch.getKitchenId(), 0, lunch.getLunchDate()));
          
          if(config.getHasBooking() == 0) quantityNow = 0;
          if (checkConditionChangeNumberOfLunchDate(totalOld, totalNow, quantityOld, quantityNow)) {
            lunchIds.add(lunchId);
          }
        }
      } else {
        lunchIds.add(lunchId);
      }
    }
    
    String[] resultArray = new String[lunchIds.size()];
    resultArray = lunchIds.toArray(resultArray);
    config.setLunchIds(resultArray);
    
    if(config.getLunchIds().length > 0) {
      lunchDao.deleteAllLunch(config);
    }
    String content = "Delete Lunch by ids " + Arrays.toString(config.getLunchIds());
    createActionLog("DELETE", startTime, new Date(), config.getLoginUsername(), 1, content);
  }

  @Transactional
  public ResponseEntityBase deleteLunchByPeriodic(VT020006ParamRequest param) {
    Date startTime = new Date();
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    if(param.getMonths() == null || param.getMonths().length == 0) return resp;
    
    int checkKitchenID = vt020006DAO.checkKitchenExisted(new VT020006InfoToCheckKitchenExist(param.getKitchenID(), Constant.STATUS_ACTIVE));
    if (checkKitchenID == Constant.NONE_EXIST_RECORD) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      return resp;
    } 
    if (param == null || param.getListPeriodic().isEmpty()) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      return resp;
    }
    
    Calendar sysDate = Calendar.getInstance();
    Arrays.sort(param.getMonths());
    
    if((param.getMonths()[0] - 1) == sysDate.get(Calendar.MONTH) && param.getYear() == sysDate.get(Calendar.YEAR)) {
      if (sysDate.get(Calendar.HOUR_OF_DAY) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
          || (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
          && sysDate.get(Calendar.MINUTE) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_MINUTE)) {
        sysDate.add(Calendar.DAY_OF_MONTH, 2);
      } else {
        sysDate.add(Calendar.DAY_OF_MONTH, 1);
      }
    } else {
      sysDate.set(Calendar.YEAR, param.getYear());
      sysDate.set(Calendar.MONTH, param.getMonths()[0] - 1);
      sysDate.set(Calendar.DAY_OF_MONTH, 1);
    }
    
    List<Integer> dayOfweeks = convertDayOfWeekToDB(param.getListPeriodic());
    param.setListPeriodic(dayOfweeks);
    sysDate.setTime(validate.validateDate(sysDate.getTime()));
    param.setLunchDate(sysDate.getTime());
    lunchDao.deleteByPeriod(param);
    resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
    
    String content = "Delete Lunch By Periodic in month " + Arrays.toString(param.getMonths()) + ",on day of week " + param.getListPeriodic().toString();
    createActionLog("DELETE", startTime, new Date(), param.getUserName(), 1, content);
    return resp;
  }
  
  boolean compareDate(Calendar startDate) {
    Calendar today = Calendar.getInstance();
    if(startDate.get(Calendar.YEAR) < today.get(Calendar.YEAR)) return false;
    if(startDate.get(Calendar.MONTH) < today.get(Calendar.MONTH)) return false;
    if(startDate.get(Calendar.DAY_OF_MONTH) < today.get(Calendar.DAY_OF_MONTH)) return false;
    return true;
  }
  
  @Transactional
  public ResponseEntityBase createLunchByDay(VT020006ParamRequest param) throws JsonGenerationException, JsonMappingException, IOException {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    String existUser = lunchDao.checkUserExist(param.getUserName());
    if(existUser == null || existUser.isEmpty()) return resp;
    if(param.getQuantity() < 1) return resp;
    
    int checkKitchenID = vt020006DAO.checkKitchenExisted(new VT020006InfoToCheckKitchenExist(param.getKitchenID(), Constant.STATUS_ACTIVE));
    if (checkKitchenID == Constant.NONE_EXIST_RECORD) {
      resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
      return resp;
    }
    
    Date startTime = new Date();
    boolean isLimit = false;
    List<Date> listLunchDate = new ArrayList<>();
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(param.getStartTime());
    
    if(!compareDate(calendar)) calendar.setTime(new Date());
    Calendar sysDate = Calendar.getInstance();
    
    if (calculateNumberOfDaysBetween(sysDate.getTime(), calendar.getTime()) == 0) {
      Calendar dateForLunch = Calendar.getInstance();
      
      if((sysDate.get(Calendar.HOUR_OF_DAY) < Constant.TIME_CHANGE_ORDER_LUNCH_DATE_HOUR
          || (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CHANGE_ORDER_LUNCH_DATE_HOUR
              && sysDate.get(Calendar.MINUTE) <= Constant.TIME_CHANGE_ORDER_LUNCH_DATE_MINUTE)) || 
          (sysDate.get(Calendar.HOUR_OF_DAY) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
          || (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
          && sysDate.get(Calendar.MINUTE) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_MINUTE))) {
        
        if (sysDate.get(Calendar.HOUR_OF_DAY) < Constant.TIME_CHANGE_ORDER_LUNCH_DATE_HOUR
            || (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CHANGE_ORDER_LUNCH_DATE_HOUR
                && sysDate.get(Calendar.MINUTE) <= Constant.TIME_CHANGE_ORDER_LUNCH_DATE_MINUTE)) {
          dateForLunch.setTime(calendar.getTime());
        } else if (sysDate.get(Calendar.HOUR_OF_DAY) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
            || (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
            && sysDate.get(Calendar.MINUTE) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_MINUTE)) {
          calendar.add(Calendar.DAY_OF_MONTH, 1);
          dateForLunch.setTime(calendar.getTime());
        }
        
        int totalOld;
        int totalNow;
        int quantityOld;
        int quantityNow = param.getQuantity();

        // find info of old order
        VT020006Lunch infoToFindOldOrder = new VT020006Lunch();
        infoToFindOldOrder.setKitchenID(param.getKitchenID());
        infoToFindOldOrder.setLunchDate(validate.validateDate(calendar.getTime()));
        infoToFindOldOrder.setUserName(param.getUserName());
        VT020006LunchInfo lunchInfo = vt020006DAO.findLunchInfo(infoToFindOldOrder);
        // if kitchenID old != kitchenID new => oldQuantity = 0
        if (lunchInfo != null && lunchInfo.getKitchenID().equals(param.getKitchenID())) {
          quantityOld = lunchInfo.getQuantity();
        } else {
          quantityOld = 0;
        }
        // find total number of lunchDate in this kitchen save in 16h30
        totalOld = vt020006DAO.findTotalLunchDateOld(
            new VT020006InfoToInsertDailyMeals(param.getKitchenID(), 0, calendar.getTime()));
        // find total number of lunchDate in this kitchen in now
        totalNow = vt020006DAO.findTotalLunchDateNow(
            new VT020006InfoToInsertDailyMeals(param.getKitchenID(), 0, calendar.getTime()));
        if (!checkConditionChangeNumberOfLunchDate(totalOld, totalNow, quantityOld, quantityNow)) {
          calendar.add(Calendar.DAY_OF_MONTH, 1);
          isLimit = true;
        }
      } else {
        calendar.add(Calendar.DAY_OF_MONTH, 1);
      }
    } else  if (calculateNumberOfDaysBetween(sysDate.getTime(), calendar.getTime()) == 1) {
      Calendar dateForLunch = Calendar.getInstance();
      
      if (sysDate.get(Calendar.HOUR_OF_DAY) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
            || (sysDate.get(Calendar.HOUR_OF_DAY) == Constant.TIME_CLOSE_ORDER_LUNCH_DATE_HOUR
            && sysDate.get(Calendar.MINUTE) > Constant.TIME_CLOSE_ORDER_LUNCH_DATE_MINUTE)) {
          dateForLunch.setTime(calendar.getTime());
          int totalOld;
          int totalNow;
          int quantityOld;
          int quantityNow = param.getQuantity();

          // find info of old order
          VT020006Lunch infoToFindOldOrder = new VT020006Lunch();
          infoToFindOldOrder.setKitchenID(param.getKitchenID());
          infoToFindOldOrder.setLunchDate(validate.validateDate(calendar.getTime()));
          infoToFindOldOrder.setUserName(param.getUserName());
          VT020006LunchInfo lunchInfo = vt020006DAO.findLunchInfo(infoToFindOldOrder);
          // if kitchenID old != kitchenID new => oldQuantity = 0
          if (lunchInfo != null && lunchInfo.getKitchenID().equals(param.getKitchenID())) {
            quantityOld = lunchInfo.getQuantity();
          } else {
            quantityOld = 0;
          }
          // find total number of lunchDate in this kitchen save in 16h30
          totalOld = vt020006DAO.findTotalLunchDateOld(
              new VT020006InfoToInsertDailyMeals(param.getKitchenID(), 0, calendar.getTime()));
          // find total number of lunchDate in this kitchen in now
          totalNow = vt020006DAO.findTotalLunchDateNow(
              new VT020006InfoToInsertDailyMeals(param.getKitchenID(), 0, calendar.getTime()));
          if (!checkConditionChangeNumberOfLunchDate(totalOld, totalNow, quantityOld, quantityNow)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            isLimit = true;
          }
        }
    }
    
    if (validate.validateDate(calendar.getTime()).compareTo(validate.validateDate(param.getEndTime())) <= 0) {
      listLunchDate.add(param.getEndTime());
    }
    
    while (calendar.getTime().before(param.getEndTime())) {
      Date result = calendar.getTime();
      listLunchDate.add(result);
      calendar.add(Calendar.DATE, 1);
    }
    if(isLimit == true) {
      resp.setStatus(Constant.RESPONSE_EXIST_SERVICE_NOT_VALID);
      return resp;
    } else if(listLunchDate.size() == 0) {
      resp.setStatus(Constant.RESPONSE_STATUS_NO_ROLE);
      return resp;
    }
    
    try {
      List<Date> listDayOff = vt020006DAO.findDayOff(Constant.STATUS_ACTIVE);
      boolean isDayOff = false;
      int count = 0;
      
      for(Date lunchDate : listLunchDate) {
        if (listDayOff.contains(validate.validateDate(lunchDate))) {
          isDayOff = true;
          continue;
        }

        List<Date> dates = new ArrayList<>();
        dates.add(lunchDate);
        
        int unitId = lunchDao.getUnitIdByUsername(param.getUserName());
        
        VT020006Lunch lunch = new VT020006Lunch(param.getUserName(), lunchDate, param.getQuantity(),
            Constant.HAS_BOOKING_DEFAULT, param.getKitchenID(), unitId, 0, dates);
        
        lunch.setLunchDate(validate.validateDate(lunch.getLunchDate()));
        // validate input hasBooking = (0;1), 0 <= quantity <=20
        if (lunch.getHasBooking() != Constant.HAS_BOOKING_DEFAULT) {
          lunch.setHasBooking(Constant.HAS_BOOKING_NONE);
        }
        if (lunch.getQuantity() < Constant.QUANTITY_NONE
            || lunch.getQuantity() > Constant.QUANTITY_LIMITED) {
          lunch.setQuantity(0);
        }
        // check if date not existed in db => insert else update
        int checkLunch = vt020006DAO.checkLunchDate(lunch);
        if (checkLunch > 0) {
          lunch.setIsPeriodic(Constant.PERIODIC_NONE);
          vt020006DAO.updateLunch(lunch);
        } else {
          lunch.setIsPeriodic(Constant.PERIODIC_NONE);
          vt020006DAO.insertLunchDate(lunch);
        }
        count = count + 1;
      }
      if(isDayOff && count == 0) resp.setStatus(Constant.RESPONSE_STATUS_NO_ROLE);
      else resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      
      String content = "Create lunch by day from " + new DateUtil(param.getStartTime()).toString(DateUtil.FORMAT_DATE) + 
          "  to " + new DateUtil(param.getEndTime()).toString(DateUtil.FORMAT_DATE);
      createActionLog("CREATE", startTime, new Date(), param.getUserName(), 1, content);
    } catch (Exception e) {
      createActionLog("CREATE", startTime, new Date(), param.getUserName(), 0, e.getMessage());
      logger.error(e.getMessage(), e);
    }
    return resp;
  }
  
  @Transactional
  public void createActionLog(String actionMethod, Date startTime, Date endTime, String username, double result, String content) {
    ActionLog actionLog = new ActionLog();
    actionLog.setAppCode("PMQTVP");
    actionLog.setClassName(this.getClass().getSimpleName());
    actionLog.setActionMethod(actionMethod);
    actionLog.setStartTime(startTime);
    actionLog.setEndTime(endTime);
    actionLog.setResult(result);
    actionLog.setUsername(username);
    actionLog.setContent(content);
    lunchDao.createActionLog(actionLog);
  }

  public boolean checkConditionChangeNumberOfLunchDate(int totalOld, int totalNow, int quantityOld, int quantityNow) {
	  double limit = lunchDao.getLunchPercent() / 100;
	  if (Math.abs(totalNow - quantityOld + quantityNow - totalOld) <= totalOld * limit) {
		  return true;
	  }
	  return false;
  }
  
  public List<Integer> convertDayOfWeekToDB(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) == 2)
        list.set(i, 0);
      else if (list.get(i) == 3)
        list.set(i, 1);
      else if (list.get(i) == 4)
        list.set(i, 2);
      else if (list.get(i) == 5)
        list.set(i, 3);
      else if (list.get(i) == 6)
        list.set(i, 4);
      else if (list.get(i) == 7)
        list.set(i, 5);
    }
    return list;
  }
  
  private int calculateNumberOfDaysBetween(Date startDate, Date endDate) {
    if (startDate.after(endDate)) {
      return -1;
    }

    long startDateTime = startDate.getTime();
    long endDateTime = endDate.getTime();
    long milPerDay = 1000 * 60 * 60 * 24; 

    int numOfDays = (int) ((endDateTime - startDateTime) / milPerDay); // calculate vacation duration in days

    return (numOfDays); // add one day to include start date in interval
  }
}
