package com.viettel.vtnet360.checking.service;

import java.security.Principal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.vtnet360.checking.resgister.dao.CheckingDAO;
import com.viettel.vtnet360.checking.service.entity.Checking;
import com.viettel.vtnet360.checking.service.entity.SystemCode;
import com.viettel.vtnet360.driving.service.BookingService;
import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt00.common.TokenAdditionInfo;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;
import com.viettel.vtnet360.vt01.vt010000.dao.VT010000DAO;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityAdditionalInfo;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityApForOneRd;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRpMgAp;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqGetData;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqMgAp;
import com.viettel.vtnet360.vt01.vt010001.dao.VT010001DAO;
import com.viettel.vtnet360.vt01.vt010001.entity.VT010001EntityRpRegister;
import com.viettel.vtnet360.vt01.vt010002.dao.VT010002DAO;
import com.viettel.vtnet360.vt01.vt010002.entity.VT010002EntityRpUpdate;
import com.viettel.vtnet360.vt01.vt010011.entity.VT010011ListCondition;
import com.viettel.vtnet360.vt02.vt020000.dao.VT020000DAO;
import com.viettel.vtnet360.vt02.vt020000.entity.VT020000Unit;
import com.viettel.vtnet360.vt03.vt030000.entity.Employee;

@Service
public class CheckingService {
  private final Logger logger = Logger.getLogger(this.getClass());

  @Autowired
  private BookingService bookingService;
  
  @Autowired
  private CheckingDAO checkingDao;

  @Autowired
  private VT010000DAO vt010000Dao;

  @Autowired
  private VT010002DAO vt010002Dao;
  
  @Autowired
  private VT010001DAO vt010001Dao;

  @Autowired
  private VT020000DAO vt020000Dao;

  @Autowired
  private VT000000DAO vt000000DAO;

  @Autowired
  private Properties smsMessage;

  @Autowired
  private Sms sms;

  @Autowired
  private Notification notification;

  @Autowired
  private Properties notifyMessage;

  @Autowired
  private AuthorizationServerTokenServices tokenServices;

  @Transactional(readOnly = true)
  public CheckingModel findListInOut(VT010011ListCondition condition, Principal principal,
      OAuth2Authentication authentication) throws Exception {
    CheckingModel model = new CheckingModel();
    try {
      VT010000EntityRqGetData requestParam = new VT010000EntityRqGetData();
      requestParam.setPageSize(condition.getRowSize());
      requestParam.setStartTimeByPlan(condition.getStartDate());
      requestParam.setEndTimeByPlan(condition.getEndDate());
      requestParam.setSearchForUserName(condition.getPersonInfo());
      requestParam.setApprover(condition.getApprover());
      if(condition.getListUnit() != null && condition.getListUnit().length > 0) {
        requestParam.setEmployeeUnitId(Integer.parseInt(condition.getListUnit()[0]));
      }
      
      //status
      if(condition.getListStatus() != null && condition.getListStatus().length > 0) {
        int[] status = new int[condition.getListStatus().length];
        for(int i = 0; i < status.length; i++) {
          status[i] = Integer.parseInt(condition.getListStatus()[i]);
        }
        requestParam.setStatus(status);
      }
      
      // take value from token
      OAuth2Authentication oauth = (OAuth2Authentication) principal;
      String userName = (String) oauth.getPrincipal();
      requestParam.setUserName(userName);
      
      Collection<GrantedAuthority> roleList = oauth.getAuthorities();

      // set date time
      Date timeNow = new Date(System.currentTimeMillis());
      requestParam.setTimeNow(timeNow);

      Map<String, Object> additionalInfo = tokenServices.getAccessToken(authentication).getAdditionalInformation();
      TokenAdditionInfo tokenInfo = (TokenAdditionInfo) additionalInfo.get("userInfor");

      // build param used Map object contain param
      Map<String, Object> dataPram = new HashMap<String, Object>();
      dataPram.put("myUnitId", tokenInfo.getUnitId());
      dataPram.put("userName", userName);
      dataPram.put("fromIndex", condition.getStartRow());
      dataPram.put("getSize", requestParam.getPageSize());

      // set role
      dataPram.put(Constant.PMQT_ROLE_ADMIN, null);
      dataPram.put(Constant.PMQT_ROLE_MANAGER, null);
      dataPram.put(Constant.PMQT_ROLE_EMPLOYYEE, null);
      dataPram.put(Constant.PMQT_ROLE_GUARD, null);
      dataPram.put(Constant.PMQT_ROLE_MANAGER_CVP, null);

      // check role user
      // if user had role PMQT_ROLE_ADMIN
      if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
        dataPram.put(Constant.PMQT_ROLE_ADMIN, Constant.PMQT_ROLE_ADMIN);
        requestParam.setProcessByRole(Constant.PMQT_ROLE_ADMIN);
      } else if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))|| 
          roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP))) {
        dataPram.put(Constant.PMQT_ROLE_MANAGER, Constant.PMQT_ROLE_MANAGER);
        requestParam.setProcessByRole(Constant.PMQT_ROLE_MANAGER);

        // if user had role PMQT_ROLE_EMPLOYYEE
      } else if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_EMPLOYYEE))) {
        dataPram.put(Constant.PMQT_ROLE_EMPLOYYEE, Constant.PMQT_ROLE_EMPLOYYEE);
        requestParam.setProcessByRole(Constant.PMQT_ROLE_EMPLOYYEE);
        // if user had role PMQT_ROLE_STAFF
      } else if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_GUARD))) {
        requestParam.setProcessByRole(Constant.PMQT_ROLE_GUARD);
        dataPram.put(Constant.PMQT_ROLE_GUARD, Constant.PMQT_ROLE_GUARD);
        
        VT010011ListCondition config = getSecurityConfig(userName);
        requestParam.setFixed(config.isFixed());
        requestParam.setSecurity(config.isSecurity());
        if(requestParam.getEmployeeUnitId() <= 0 && config != null && config.getListUnit() != null && config.getListUnit().length > 0) {
          requestParam.setEmployeeUnitId(Integer.parseInt(config.getListUnit()[0]));
        }
        
        // if user had role PMQT_CVP
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
      List<Checking> data = checkingDao.findListInOut(dataPram);
      int totalRecord = checkingDao.countChecking(dataPram);
      model.setCheckings(data);
      model.setTotalRecords(totalRecord);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw e;
    }
    return model;
  }
  
  @Transactional(readOnly = true)
  public List<Employee> employeeAutocomplete(String search) {
    logger.info("**************** Start employee auto complete ****************");
    try {
      logger.info("**************** End employee auto complete - OK ****************");
      return checkingDao.employeeAutocomplete(search);
    } catch (Exception e) {
      logger.info("**************** get checking model - Fail ****************");
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  @Transactional(readOnly = true)
  public List<Employee> managerEmployeeAutocomplete(String search) {
    logger.info("**************** Start employee auto complete ****************");
    try {
      logger.info("**************** End employee auto complete - OK ****************");
      return checkingDao.managerEmployeeAutocomplete(search);
    } catch (Exception e) {
      logger.info("**************** autocomplete employee - Fail ****************");
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  @Transactional(readOnly = true)
  public List<Employee> getWithEmployees(String[] userNames) {
    logger.info("**************** Start get with employee ****************");
    try {
      logger.info("**************** End get with  - OK ****************");
      if(userNames == null) return null;
      return checkingDao.getWithEmployees(userNames);
    } catch (Exception e) {
      logger.info("**************** get with employee - Fail ****************");
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public VT010011ListCondition getSecurityConfig(String username) {
    VT010011ListCondition config = new VT010011ListCondition();
    config.setSecurity(true);
    String path = checkingDao.getPathByUsername(username);
    if(path.contains(Constant.KV1_UNIT_ID)) {
      config.setFixed(true);
      config.setFixedUnitId(Constant.KV1_UNIT_ID);
      config.setListUnit(new String[] {Constant.KV1_UNIT_ID});
    } else if(path.contains(Constant.KV2_UNIT_ID)) {
      config.setFixed(true);
      config.setFixedUnitId(Constant.KV2_UNIT_ID);
      config.setListUnit(new String[] {Constant.KV2_UNIT_ID});
    } else if(path.contains(Constant.KV3_UNIT_ID)) {
      config.setFixed(true);
      config.setFixedUnitId(Constant.KV3_UNIT_ID);
      config.setListUnit(new String[] {Constant.KV3_UNIT_ID});
    }

    return config;
  }
  
  @Transactional(readOnly = true)
  public CheckingModel getCheckingModel(CheckingSearching searchingModel, String username, Collection<GrantedAuthority> listRole) {
    logger.info("**************** Start get checking model ****************");
    try {
      VT010011ListCondition config = null;
      if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_GUARD))
          && !listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
          && !listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))) {
        config = getSecurityConfig(username);
        searchingModel.getConfig().setFixed(config.isFixed());
        searchingModel.getConfig().setSecurity(config.isSecurity());
        if(searchingModel.getConfig().getListUnit() == null || searchingModel.getConfig().getListUnit().length == 0) {
          searchingModel.getConfig().setListUnit(config.getListUnit());
        }
      }
      
      List<VT020000Unit> units = null;
      if(searchingModel.isLoadUnit()) {
        if(searchingModel.getUnitId() == null) {
          if(config != null && config.isFixed() == true)units = checkingDao.getUnitByUnitId(config.getFixedUnitId());
          else units = vt020000Dao.getLocationListByID(searchingModel.getUnitId());
        } else {
          if(config != null && config.isFixed() == true)units = checkingDao.getUnitByUnitId(config.getFixedUnitId());
          else units = checkingDao.getUnitByUnitId(searchingModel.getUnitId());
        }
      }
      
      List<Checking> checkings = checkingDao.getCheckings(searchingModel.getConfig());
      int totalRecords = checkingDao.countCheckings(searchingModel.getConfig());
      logger.info("**************** End get checking model - OK ****************");
      return new CheckingModel(units, checkings, totalRecords);
    } catch (Exception e) {
      logger.info("**************** get checking model - Fail ****************");
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  @Transactional(readOnly = true)
  public SelectingModel getSelectingModel() {
    List<SystemCode> places = checkingDao.getSystemCode("S005");
    List<SystemCode> reasons = checkingDao.getSystemCode("S004");
    return new SelectingModel(places, reasons);
  }

  @Transactional
  public VT010001EntityRpRegister createChecking(Checking requestParam, Principal principal) {
    logger.info("**************** Start insert checking ****************");
    VT010001EntityRpRegister reponse = new VT010001EntityRpRegister();
    try {
      OAuth2Authentication oauth = (OAuth2Authentication) principal;
      String userName = (String) oauth.getPrincipal();

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

      HashMap<String, Object> data = new HashMap<String, Object>();
      data.put("returnedId", null);
      data.put("userName", userName);
      data.put("requestParam", requestParam);
      data.put("dateNow", new Date(System.currentTimeMillis()));
      
      List<Map<String, Object>> valid = vt010001Dao.isValid(data);
      if (valid.size() >= 1) {
        reponse.setStatus(Constant.RESPONSE_EXIST_INOUT_REGISTER_NOT_VALID);
      } else {
        reponse.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        checkingDao.createChecking(data);
        
        Thread notiThread = new Thread() {
          public void run() {
            createSMSChecking(requestParam, data, userName);
          }
        };
        notiThread.start();
      }
      logger.info("**************** End insert checking - OK ****************");
    } catch (Exception e) {
      logger.info("**************** Insert checking - Fail ****************");
      logger.error(e.getMessage(), e);
    }
    return reponse;
  }
  
  @Transactional
  public VT010002EntityRpUpdate updateMoreTimeChecking(Checking checking, Principal principal) {
    logger.info("**************** Start update more time checking ****************");
    OAuth2Authentication oauth = (OAuth2Authentication) principal;
    String userName = (String) oauth.getPrincipal();

    VT010002EntityRpUpdate reponese = new VT010002EntityRpUpdate();
    reponese.setStatus(Constant.RESPONSE_STATUS_ERROR);
    reponese.setData(null);
    try {
      VT000000EntitySmsVsNotify inforRecord = vt000000DAO.findNotifySmsByIdInOut(checking.getInOutRegisterId());
      int status = inforRecord.getStatus();

      Date timeNow = new Date(System.currentTimeMillis());

      HashMap<String, Object> data = new HashMap<String, Object>();
      data.put("timeNow", timeNow);
      data.put("userName", userName);
      data.put("status", status);
      data.put("requestParam", checking);
      
      List<Map<String, Object>> valid = vt010002Dao.isValid(data);

      if (valid.size() >= 1) {
        reponese.setStatus(Constant.RESPONSE_EXIST_INOUT_REGISTER_NOT_VALID);
      } else {
        checkingDao.updateMoreTime(data);
        reponese.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        
        Thread notiThread = new Thread() {
          public void run() {
            String titleForModul01 = notifyMessage.getProperty("TITLE_NOTIFY_MODUL01");
            createUpdateSMSChecking(checking, userName, 1, titleForModul01);
          }
        };
        notiThread.start();
      }
      logger.info("**************** End update more time checking - OK ****************");
    } catch(Exception ex) {
      logger.info("**************** Update checking more time - Fail ****************");
      logger.error(ex.getMessage(), ex);
    }

    return reponese;
  }

  @Transactional
  public VT010002EntityRpUpdate updateChecking(Checking requestParam, Principal principal) {
    logger.info("**************** Start update checking ****************");
    OAuth2Authentication oauth = (OAuth2Authentication) principal;
    String userName = (String) oauth.getPrincipal();
    String titleForModul01 = notifyMessage.getProperty("TITLE_NOTIFY_MODUL01");

    VT010002EntityRpUpdate reponese = new VT010002EntityRpUpdate();
    reponese.setStatus(Constant.RESPONSE_STATUS_ERROR);
    reponese.setData(null);
    try {
      VT000000EntitySmsVsNotify inforRecord = vt000000DAO.findNotifySmsByIdInOut(requestParam.getInOutRegisterId());
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

        checkingDao.updateChecking(data);
        reponese.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        
        Thread notiThread = new Thread() {
          public void run() {
            createUpdateSMSChecking(requestParam, userName, status, titleForModul01);
          }
        };
        notiThread.start();
        logger.info("**************** End update checking - OK ****************");
      }
    } catch(Exception ex) {
      logger.info("**************** Update checking - Fail ****************");
      logger.error(ex.getMessage(), ex);
    }

    return reponese;
  }

  @Transactional
  public VT010000EntityRpMgAp approveByManager(VT010000EntityRqMgAp requestParam, Principal principal) throws Exception {
    OAuth2Authentication oauth = (OAuth2Authentication) principal;
    String userName = (String) oauth.getPrincipal();
    String empUserName = "";
    int statusRecord = -1;
    
    // get info from authentication
    Map<String, Object> additionalInfo = tokenServices.getAccessToken(oauth).getAdditionalInformation();
    TokenAdditionInfo tokenInfo = (TokenAdditionInfo) additionalInfo.get("userInfor");
    String myFullName = tokenInfo.getFullName();

    String titleForModul01 = notifyMessage.getProperty("TITLE_NOTIFY_MODUL01");
    String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL01_APPROVE");
    VT010000EntityRpMgAp reponese = new VT010000EntityRpMgAp();
    reponese.setStatus(Constant.RESPONSE_STATUS_ERROR);
    reponese.setData(null);
    
    String names = null;

    try {
      VT010000EntityAdditionalInfo addInfomation = new VT010000EntityAdditionalInfo();
      Date timeNow = new Date(System.currentTimeMillis());

      // check each record one by one
      VT010000EntityApForOneRd record = new VT010000EntityApForOneRd();
      record.setTimeNow(timeNow);
      record.setUserNow(userName);
      record.setReasonOfApprover(requestParam.getReasonOfApprover());
      record.setGuardInUserName(requestParam.getGuardInUserName());
      record.setGuardOutUserName(requestParam.getGuardOutUserName());
      record.setReasonOfGuard(requestParam.getReasonOfGuard());
      record.setIsMoreTimeGetInApprove(requestParam.getIsMoreTimeGetInApprove());
      
      for (String checkingId : requestParam.getInOutRegisterId()) {
        Checking checking = checkingDao.getCheckingById(checkingId);
        record.setInOutRegisterId(checkingId);
        record.setStatusRecord(checking.getStatus());
        
        VT000000EntitySmsVsNotify inforRecord = vt000000DAO.findNotifySmsByIdInOut(checkingId);
        if(checking.getIsLate() == 1) {
          requestParam.setStatus(1);
          record.setStatusApprove(1);
          if(checking.getStatus() == 5) record.setIsMoreTimeGetInApprove(1);
          else record.setIsMoreTimeGetInApprove(0);
          
          if(checking.getStatus() == 0) saveNotiAndSms(userName, checking, inforRecord);
          
          vt010000Dao.inOutManagerApprove(record);

          if(record.getStatusRecord() == 0) {
            if(checking.getInOutEmployeeList() != null && checking.getInOutEmployeeList().length() > 0) {
              String[] withUserNames = checking.getInOutEmployeeList().split(",");
              if(withUserNames.length > 0) {
                for(String withUserName: withUserNames) {
                  Checking newChecking = checking;
                  newChecking.setEmployeeUserName(withUserName);
                  Employee employee = checkingDao.getEmployeeByUsername(withUserName);
                  newChecking.setEmpName(employee.getFullName());
                  newChecking.setInOutEmployeeList(null);

                  int valid = createWithLateChecking(newChecking, userName);
                  if(valid == 0) {
                    if(names == null) names = employee.getFullName();
                    else names = names + "," + employee.getFullName();
                  }
                }
              }
            }
          }
        } else {
          if (timeNow.compareTo(inforRecord.getEndTimeByPlan()) > 0) {
            if(names == null) names = inforRecord.getFullNameOfToUserName();
            else names = names + "," + inforRecord.getFullNameOfToUserName();
          } else {
            if(checking.getStatus() == 0) {
              record.setIsMoreTimeGetInApprove(0);
              record.setStatusApprove(1);
              vt010000Dao.inOutManagerApprove(record);
              
              saveNotiAndSms(userName, checking, inforRecord); 
            } else if(checking.getStatus() == 5){
              record.setIsMoreTimeGetInApprove(1);
              if(checking.getLogToRollbackStatus() == 3) {
                record.setStatusApprove(3);
                vt010000Dao.inOutGruadApprove(record);
              } else {
                record.setStatusApprove(1);
                vt010000Dao.inOutManagerApprove(record);
              }
            }

            if(checking.getStatus() == Constant.IN_OUT_REGISTER_WATING_APPROVED) {
              Checking existedChecking = checkingDao.getCheckingById(checking.getInOutRegisterId());
              if(existedChecking.getInOutEmployeeList() != null && existedChecking.getInOutEmployeeList().length() > 0) {
                String[] withUserNames = existedChecking.getInOutEmployeeList().split(",");
                if(withUserNames.length > 0) {
                  for(String withUserName: withUserNames) {
                    Checking newChecking= existedChecking;
                    newChecking.setEmployeeUserName(withUserName);
                    Employee employee = checkingDao.getEmployeeByUsername(withUserName);
                    newChecking.setEmpName(employee.getFullName());
                    newChecking.setInOutEmployeeList(null);

                    int valid = createWithChecking(newChecking, userName);
                    if(valid == 0) {
                      if(names == null) names = employee.getFullName();
                      else names = names + "," + employee.getFullName();
                    }
                  }
                }
              }
            }
          }
        }
        
        Thread notiThread = new Thread() {
          public void run() {
            try {
              int isLate = 0;
              if(record.getStatusApprove() == 3 && record.getStatusRecord() == 0) isLate = 1;
              ObjectMapper mapper = new ObjectMapper();
              System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestParam));
              trySmsGuardrApprove(checkingId, requestParam, 1, inforRecord,
                  addInfomation, titleForModul01, userName, typeSms, myFullName, isLate);
            } catch (Exception e) {
              logger.error(e.getMessage(), e);
              logger.info("****************************THREAD NOTI ERROR********:");
            }
          }
        };
        notiThread.start();
      }
      reponese.setStatus(Constant.SUCCESS);
    } catch (Exception e) {
      logger.error("USER NAME APROOVE: " + userName + " " + "EMP USER NAME APROOVE: " + empUserName + " "
          + "Status Record: " + statusRecord + " " + "Flag Aproved: " + requestParam.getStatus());
      logger.error(e.getMessage(), e);
      throw e;
    }    
    reponese.setNames(names);
    return reponese;
  }
  
  @Transactional
  public VT010000EntityRpMgAp updateManagerApprove(VT010000EntityRqMgAp requestParam, Principal principal) throws Exception {
    OAuth2Authentication oauth = (OAuth2Authentication) principal;
    String userName = (String) oauth.getPrincipal();
    Boolean isAdmin = false;
    String empUserName = "";
    int statusRecord = -1;
    
    // get info from authentication
    Map<String, Object> additionalInfo = tokenServices.getAccessToken(oauth).getAdditionalInformation();
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
    
    String names = null;

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
      record.setIsMoreTimeGetInApprove(requestParam.getIsMoreTimeGetInApprove());
      
      // Case getStatus = MANAGER APPROVED
      if (requestParam.getStatus() == Constant.MANAGER_APPROVED || requestParam.getStatus() == Constant.MANAGER_ABANDON) {
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
              System.out.println("RESPONSE_INOUT_RIGISTER_CHANGED_APPROVER");
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
              if(statusInOutRegisterId == Constant.IN_OUT_REGISTER_WATING_APPROVED) {
                Checking existedChecking = checkingDao.getCheckingById(inOutRegisterId);
                if(existedChecking.getInOutEmployeeList() != null && existedChecking.getInOutEmployeeList().length() > 0) {
                  String[] withUserNames = existedChecking.getInOutEmployeeList().split(",");
                  if(withUserNames.length > 0) {
                    for(String withUserName: withUserNames) {
                      Checking checking = existedChecking;
                      checking.setEmployeeUserName(withUserName);
                      Employee employee = checkingDao.getEmployeeByUsername(withUserName);
                      checking.setEmpName(employee.getFullName());
                      checking.setInOutEmployeeList(null);
                      
                      int valid = 0;
                      if(checking.getIsLate() == 0) {
                        valid = createWithChecking(checking, userName);
                      } else {
                        valid = createWithLateChecking(checking, userName);
                      }
                      if(valid == 0) {
                        if(names == null) names = employee.getFullName();
                        else names = names + "," + employee.getFullName();
                      }
                    }
                  }
                }
              }
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
            System.out.println("Constant.RESPONSE_INOUT_OUT_DATE");
            reponese.setStatus(Constant.RESPONSE_INOUT_OUT_DATE);
            return reponese;
          } else if (checkInvalidGruardChangeStatus(statusInOutRegisterId, requestParam)) {
            System.out.println("Constant.RESPONSE_INOUT_RIGISTER_CHANGED_STATUS");
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
                int isLate = 0;
                if(record.getStatusApprove() == 3 && record.getStatusRecord() == 0) isLate = 1;
                trySmsGuardrApprove(inOutRegisterId, requestParam, statusInOutRegisterId, inforRecord,
                    addInfomation, titleForModul01, userName, typeSms, myFullName, isLate);
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
    reponese.setNames(names);
    return reponese;
  }
  
  private int createWithLateChecking(Checking checking, String createUser) {
    Calendar start = Calendar.getInstance();
    start.setTime(checking.getStartTimeByPlan());
    start.set(Calendar.SECOND, 0);
    start.set(Calendar.MILLISECOND, 0);
    Calendar end = Calendar.getInstance();
    end.setTime(checking.getEndTimeByPlan());
    end.set(Calendar.SECOND, 0);
    end.set(Calendar.MILLISECOND, 0);
    checking.setStartTimeByPlan(start.getTime());
    checking.setEndTimeByPlan(end.getTime());
    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("returnedId", null);
    data.put("userName", checking.getEmployeeUserName());
    data.put("requestParam", checking);
    data.put("dateNow", new Date(System.currentTimeMillis()));
    
    List<Map<String, Object>> valid = vt010001Dao.isValid(data);
    
    if(valid.size() >= 1) return 0;
    
    checkingDao.createWithLateChecking(data);
    String employeeUsername = checking.getEmployeeUserName();
    Thread notiThread = new Thread() {
      public void run() {
        DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Employee approver = checkingDao.getEmployeeByUsername(createUser);
        String message = MessageFormat.format(smsMessage.getProperty("S04"), approver.getFullName(),
            dateformat.format(checking.getStartTimeByPlan()),
            dateformat.format(checking.getEndTimeByPlan()));

        VT010000EntityAdditionalInfo addInfomation = new VT010000EntityAdditionalInfo();
        addInfomation.setId(data.get("returnedId").toString());
        addInfomation.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
        addInfomation.setStatus(3);

        String titleForModul01 = notifyMessage.getProperty("TITLE_NOTIFY_MODUL01");
        String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL01_APPROVE");

        notification.sendNotification(employeeUsername, addInfomation.toString(),
            message, titleForModul01, Constant.TYPE_NOTIFY_MODUL01, employeeUsername, 1);

        Employee employee = checkingDao.getEmployeeByUsername(employeeUsername);
        sms.sendSms(employee.getEmployeeId(), message, Constant.STATUS_NEW_SMS, employee.getEmployeePhone(), typeSms);
      }
    };
    notiThread.start();

    return 1;
  }

  private int createWithChecking(Checking checking, String createUser) {
    Calendar start = Calendar.getInstance();
    start.setTime(checking.getStartTimeByPlan());
    start.set(Calendar.SECOND, 0);
    start.set(Calendar.MILLISECOND, 0);
    Calendar end = Calendar.getInstance();
    end.setTime(checking.getEndTimeByPlan());
    end.set(Calendar.SECOND, 0);
    end.set(Calendar.MILLISECOND, 0);
    checking.setStartTimeByPlan(start.getTime());
    checking.setEndTimeByPlan(end.getTime());
    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("returnedId", null);
    data.put("userName", checking.getEmployeeUserName());
    data.put("requestParam", checking);
    data.put("dateNow", new Date(System.currentTimeMillis()));
    
    List<Map<String, Object>> valid = vt010001Dao.isValid(data);
    
    if(valid.size() >= 1) return 0;
    
    checkingDao.createWithChecking(data);
    String employeeUsername = checking.getEmployeeUserName();
    Thread notiThread = new Thread() {
      public void run() {
        DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Employee approver = checkingDao.getEmployeeByUsername(createUser);
        String message = MessageFormat.format(smsMessage.getProperty("S04"), approver.getFullName(),
            dateformat.format(checking.getStartTimeByPlan()),
            dateformat.format(checking.getEndTimeByPlan()));

        VT010000EntityAdditionalInfo addInfomation = new VT010000EntityAdditionalInfo();
        addInfomation.setId(data.get("returnedId").toString());
        addInfomation.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
        addInfomation.setStatus(1);

        String titleForModul01 = notifyMessage.getProperty("TITLE_NOTIFY_MODUL01");
        String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL01_APPROVE");

        notification.sendNotification(employeeUsername, addInfomation.toString(),
            message, titleForModul01, Constant.TYPE_NOTIFY_MODUL01, employeeUsername, 1);

        Employee employee = checkingDao.getEmployeeByUsername(employeeUsername);
        sms.sendSms(employee.getEmployeeId(), message, Constant.STATUS_NEW_SMS, employee.getEmployeePhone(), typeSms);
      }
    };
    notiThread.start();
    
    return 1;
  }
  
  private void storgeNotiAndSms(VT000000EntitySmsVsNotify userInfo, String message, String title, AdditionalInfoBase addInfo) throws Exception {
    notification.storageNotification(userInfo.getToUserName(), null, addInfo.toString(),
        message, title, Constant.TYPE_NOTIFY_MODUL01, userInfo.getToUserName(),
        0, Constant.MANAGER_APPROVED);
    sms.sendSms(userInfo.getToUserId(), message, Constant.STATUS_NEW_SMS, userInfo.getPhone(), title);
  }
  
  private void saveNotiAndSms(String loginUsername, Checking checking, VT000000EntitySmsVsNotify userInfo) throws Exception {
    DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    Employee approver = checkingDao.getEmployeeByUsername(loginUsername);
    String message = MessageFormat.format(smsMessage.getProperty("S04"), approver.getFullName(),
        dateformat.format(checking.getStartTimeByPlan()),
        dateformat.format(checking.getEndTimeByPlan()));

    VT010000EntityAdditionalInfo addInfomationMore = new VT010000EntityAdditionalInfo();
    addInfomationMore.setRoleReceiver(Constant.PMQT_ROLE_EMPLOYYEE);
    addInfomationMore.setStatus(1);

    String titleForModul01More = notifyMessage.getProperty("TITLE_NOTIFY_MODUL01");
    storgeNotiAndSms(userInfo, message, titleForModul01More, addInfomationMore);
  }
  
  private boolean checkOutDate(int status, Checking requestParam, VT000000EntitySmsVsNotify inforRecord, Date timeNow) {
    if ((status == Constant.IN_OUT_REGISTER_WATING_APPROVED) && (timeNow.compareTo(inforRecord.getEndTimeByPlan()) > 0)) {
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

  private boolean checkStatusInvalid(int status, Checking requestParam, VT000000EntitySmsVsNotify inforRecord, Date timeNow) {
    // if inOut Register have status invalid
    if (status == Constant.IN_OUT_REGISTER_REFUSE || status == Constant.IN_OUT_GO_IN
        || status == Constant.IN_OUT_REGISTER_REJECT_OUT || status == Constant.IN_OUT_REGISTER_REJECT_IN
        || status == Constant.OUT_DATE_IN_FOR_IN_OUT || status == Constant.OUT_DATE_IN_OUT) {
      return true;
    }

    // if inOut Register not is waiting approved then can't update
    if (status != Constant.IN_OUT_REGISTER_WATING_APPROVED
        && (requestParam.getApproverUserName() != null || requestParam.getDestination() != null
        || requestParam.getReasonDetail() != null || requestParam.getReasonRegistion() != null)) {
      return true;
    }

    return false;
  }

  private void createUpdateSMSChecking(Checking requestParam, String userName, int status, String titleForModul01) {
    // find information
    // for toUserID , Phone , UnitName
    VT000000EntitySmsVsNotify emp = vt000000DAO.findNotifySmsByUserName(userName);

    VT000000EntitySmsVsNotify manager = vt000000DAO.findNotifySmsByUserName(requestParam.getApproverUserName());

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
      
      Map<String, Object> infoRecord = vt000000DAO.findInfoInOutRecord(requestParam.getInOutRegisterId());
      
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
      sms.sendSms(manager.getToUserId(), messageSms, Constant.STATUS_NEW_SMS, manager.getPhone(), typeSms);
    }
  }

  private void createSMSChecking(Checking requestParam, HashMap<String, Object> data, String userName) {
    VT000000EntitySmsVsNotify inforNotifySmsEmp = vt000000DAO.findNotifySmsByUserName(userName);
    inforNotifySmsEmp.setToUserName(requestParam.getApproverUserName());

    VT000000EntitySmsVsNotify inforNotifySmsManager = vt000000DAO.findNotifySmsByUserName(requestParam.getApproverUserName());

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
        messageNotify, title, Constant.TYPE_NOTIFY_MODUL01, userName, Constant.WAIT_MANAGER_APPROVE);

    // build message and send sms
    String messageSms = MessageFormat.format(smsMessage.getProperty("S01"),
        inforNotifySmsEmp.getCreateUser(),
        dateformat.format(requestParam.getStartTimeByPlan()),
        dateformat.format(requestParam.getEndTimeByPlan()));
    String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL01_REGISTER");
    sms.sendSms(inforNotifySmsManager.getToUserId(), messageSms, Constant.STATUS_NEW_SMS, inforNotifySmsManager.getPhone(), typeSms);
  }
  
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
  
  private void trySmsGuardrApprove(String inOutRegisterId, VT010000EntityRqMgAp requestParam,
      int statusInOutRegisterId, VT000000EntitySmsVsNotify inforRecord,
      VT010000EntityAdditionalInfo addInfomation, String titleForModul01, String userName, String typeSms,
      String myFullName, int isLate) {

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
        if(isLate == 0) {
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
        } else if(isLate == 1) {
          message = MessageFormat.format(smsMessage.getProperty("S04"), myFullName,
              dateformat.format(inforRecord.getStartTimeByPlan()),
              dateformat.format(inforRecord.getEndTimeByPlan()));
          
          messageSms = MessageFormat.format(smsMessage.getProperty("S04"),myFullName,
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
  
  @Transactional
  public void createRemindCheckIn() {
    try {
      int minutes = checkingDao.getConfigMinute();
      String content = MessageFormat.format(smsMessage.getProperty("S01_1"), minutes);
      
      List<String> checkingIds = checkingDao.getListRemindCheckings(minutes);
      for(String checkingId : checkingIds) {
        VT000000EntitySmsVsNotify inforRecord = vt000000DAO.findNotifySmsByIdInOut(checkingId);
        sms.sendSms(inforRecord.getToUserId(), content, Constant.STATUS_NEW_SMS, inforRecord.getPhone(), "REMIND CHECKING");
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    
    try {
      bookingService.checkCarbookingToAlert();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
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
          && (requestParam.getStatus() == Constant.GRUARD_ABANDON_OUT)
          && timeNow.compareTo(inforRecord.getOldEndTimeByPlan()) > 0) {

        return true;
      }

    } catch (Exception e) {

      logger.error(e.getMessage(), e);
    }
    return false;

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

    } else if (requestParam.getStatus() == Constant.GRUARD_APPROVE_OUT && statusInOutRegisterId == 0) {
      return false;
    } else if(requestParam.getStatus() == 4 && statusInOutRegisterId == 1) {
      return false;
    }
    
    return true;
  }
  
  @Transactional(readOnly = true)
  public Checking getCheckingByBarcode(String barcode){
    return checkingDao.getCheckingByBarcode(barcode);
  }
}
