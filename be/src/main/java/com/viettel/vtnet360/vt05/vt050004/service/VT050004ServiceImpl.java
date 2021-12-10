package com.viettel.vtnet360.vt05.vt050004.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.vtnet360.issuesService.entity.IssuesStationeryApprovedStatus;
import com.viettel.vtnet360.stationery.request.dao.StationeryDAO;
import com.viettel.vtnet360.stationery.request.dao.StationeryReportDAO;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.Sms;
import com.viettel.vtnet360.vt05.vt050000.dao.VT050000DAO;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000AdditionalInfoToSendNotify;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoToFindSpendingLimitUnit;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationeryApproved;
import com.viettel.vtnet360.vt05.vt050000.service.VT050000Service;
import com.viettel.vtnet360.vt05.vt050004.dao.VT050004DAO;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004DataResponse;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004InfoToCheckNumberOfValidRecordsRequest;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004InfoToFindTotalMoneyUsedOfUnit;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004InfoToInsertStorageHcdvRequest;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004InfoToSearchIssuesStationeryItems;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004InfoToUpdateIssuesStationeryItems;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004ParamToInsert;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004Stationery;
import com.viettel.vtnet360.vt05.vt050004.entity.VT50004CheckListRequestID;
import com.viettel.vtnet360.vt05.vt050012.dao.VT050012DAO;

/**
 * @author DuyNK 04/10/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050004ServiceImpl implements VT050004Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private StationeryDAO stationerDAO;
	
	@Autowired
	private VT050004DAO vt050004DAO;

	@Autowired
	private VT050012DAO vt050012DAO;

	@Autowired
	private VT050000DAO vt050000DAO;
	
	@Autowired
  private VT050000Service vt050000Service;

	@Autowired
	private StationeryReportDAO stationeryReportDAO;

	@Autowired
	Notification notification;

	@Autowired
	Sms sms;

	@Autowired
	Properties notifyMessage;

	@Autowired
	Properties smsMessage;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findIssuesStationeryItems(VT050004InfoToSearchIssuesStationeryItems info,
			Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT050004DataResponse> dataResponse = null;
		try {
			// validate input
			if (StringUtils.isBlank(info.getEmployeeUserName())) {
				info.setEmployeeUserName(null);
			}
			info.setPageNumber(info.getPageNumber() * info.getPageSize());
			// search record status = 0 => send to manager approve
			info.setStatus(Constant.STATIONERY_CREATE);
			// if user is admin => search all
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				info.setRoleAdmin(true);
			}
			// find in db
			dataResponse = vt050004DAO.findIssuesStationeryItems(info);
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
  @Transactional
  public ResponseEntityBase approveIssuesStationeryItems(VT050004ParamToInsert param, Collection<GrantedAuthority> roleList) {
	  ResponseEntityBase resp = new ResponseEntityBase(Constant.ERROR, null);
    try {
      
      int updatedItems = stationerDAO.checkValidStationeryItems(param);
      if(updatedItems > 0) {
        resp.setStatus(Constant.RESPONSE_STATUS_NO_ROLE);
        return resp;
      }
      
      // validate input
      if (param == null || param.getListRequestID().isEmpty()) {
        resp.setStatus(Constant.ERROR);
        return resp;
      }
      // check ngay hien tai < ngay gioi han trinh ki
      Date date = new Date();
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      int day = cal.get(Calendar.DAY_OF_MONTH);
      String limitDate = vt050012DAO.getLimitDateEnd();
      int foo;
      foo = Integer.parseInt(limitDate);
      if (day > foo) {
        resp.setStatus(Constant.DAY_IS_LIMIT);
        System.out.println("Constant.DAY_IS_LIMIT");
        return resp;
      }

      List<IssuesStationeryApprovedStatus> dataApprovedStatus = new ArrayList<>();

      dataApprovedStatus = stationeryReportDAO.getStatusApprovedByUserName(param.getEmployeeUserName());
      if (dataApprovedStatus.size() > 0) {
        for (IssuesStationeryApprovedStatus issues : dataApprovedStatus) {
          if (issues.getStatus() == 6) {
            resp.setStatus(Constant.CONFIRM_REQUEST);
            resp.setData(issues.getApproveStationeryId());
            return resp;
          }
        }
      }
      List<VT50004CheckListRequestID> checkListRequestIDs = new ArrayList<>();
      checkListRequestIDs = vt050004DAO.getListRequestID(param.getListRequestID());
      ObjectMapper mapper = new ObjectMapper();
      System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(checkListRequestIDs));
      List<Integer> checkDuplicateUnitd = new ArrayList<>();

      for (int j = 0; j < checkListRequestIDs.size(); j++) {
        if (!checkDuplicateUnitd.contains(checkListRequestIDs.get(j).getUnitId())) {
          checkDuplicateUnitd.add(checkListRequestIDs.get(j).getUnitId());
        }
        if (checkDuplicateUnitd.size() > 1) {
          resp.setStatus(Constant.UNIT_DUPLICATEKEY);
          System.out.println("Constant.UNIT_DUPLICATEKEY");
          return resp;
        }
      }

      List<Integer> checkDuplicate = new ArrayList<>();
      for (int j = 0; j < checkListRequestIDs.size(); j++) {
        if (!checkDuplicate.contains(checkListRequestIDs.get(j).getIssueLocation())) {
          checkDuplicate.add(checkListRequestIDs.get(j).getIssueLocation());
        }
      }

      if (checkDuplicate.size() > 1) {
        resp.setStatus(Constant.LOCATION_DUPLICATEKEY);
        System.out.println("Constant.LOCATION_DUPLICATEKEY");
        return resp;
      }

      if (!roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
        double limit = vt050000Service.caculRemainingSpendingLimit(param.getEmployeeUserName(), roleList); 
        double totalMoneyOfRequeset = vt050004DAO.findTotalMoneyOfHcdvRequest(param.getListRequestID());
        double toUsed = limit - totalMoneyOfRequeset;
        System.out.println("toUsed " + toUsed + " limit " + limit + " totalMoneyOfRequeset " + totalMoneyOfRequeset);
        if (toUsed < 0) {
          resp.setStatus(Constant.ERROR_EXCEED_SPENDING_LIMIT_MONEY_UNIT);
          System.out.println("Constant.ERROR_EXCEED_SPENDING_LIMIT_MONEY_UNIT");
          return resp;
        }
      }
      // check spending limit money of unit if Hcdv logged on

      // Check the number of valid records ( if there some record invalid
      // )
      VT050004InfoToCheckNumberOfValidRecordsRequest infoCheck = new VT050004InfoToCheckNumberOfValidRecordsRequest();
      infoCheck.setStatus(Constant.STATIONERY_CREATE);
      infoCheck.setListRequestID(param.getListRequestID());
      int checkValid = vt050004DAO.checkNumberOfValidRecords(infoCheck);
      if (checkValid != param.getListRequestID().size()) {
        System.out.println("Constant.ERROR_REQUEST_INVALID");
        resp.setStatus(Constant.ERROR_REQUEST_INVALID);
        return resp;
      }

      System.out.println("Start inserting");
      // insert to ISSUES_STATIONERY_APPROVED
      VT050000IssuesStationeryApproved issuesStationeryApproved = new VT050000IssuesStationeryApproved();
      issuesStationeryApproved.setHcdvUserName(param.getEmployeeUserName());
      issuesStationeryApproved.setMessage(param.getMessage());
      issuesStationeryApproved.setApproveUserName(param.getApproveUserName());
      issuesStationeryApproved.setStatus(Constant.STATIONERY_APPROVED_WAIT_MANAGER_APPROVE);
      issuesStationeryApproved.setCreateUser(param.getEmployeeUserName());
      vt050004DAO.insertIssuesStationeryApprove(issuesStationeryApproved);
      // get ISSUES_STATIONERY_APPROVED_ID just insert
      String issuesStationeryApprovedID = vt050004DAO.findIssuesStationeryApproveID(param.getEmployeeUserName());
      // update to ISSUES_STATIONERY_ITEMS
      VT050004InfoToUpdateIssuesStationeryItems info = new VT050004InfoToUpdateIssuesStationeryItems(
          param.getListRequestID());
      info.setIssuesStationeryApprovedID(issuesStationeryApprovedID);
      info.setStatus(Constant.STATIONERY_WAIT_MANAGER_APPROVE);
      info.setUpdateUser(param.getEmployeeUserName());
      vt050004DAO.updateIssuesStationeryItems(info);

      // find stationerry of this request group by stationeryID
      List<VT050004Stationery> listStationery = vt050004DAO
          .findStationeryByIssuesStationeryApproveID(issuesStationeryApprovedID);
      VT050004InfoToInsertStorageHcdvRequest infoToInsertStorageHcdvRequest = new VT050004InfoToInsertStorageHcdvRequest();
      infoToInsertStorageHcdvRequest.setHcdvUserName(param.getEmployeeUserName());
      infoToInsertStorageHcdvRequest.setIssuesStationeryApproveID(issuesStationeryApprovedID);
      infoToInsertStorageHcdvRequest.setListStationery(listStationery);
      // insert info to STORAGE_HCDV_REQUEST
      vt050004DAO.insertToStorageHcdvRequest(infoToInsertStorageHcdvRequest);
      resp.setStatus(Constant.SUCCESS);

      System.out.println("END inserting");

      // send sms and notify to Manager(LDDV)
      try {
        sendSmsAndNotify(param.getApproveUserName(), param.getEmployeeUserName(), issuesStationeryApprovedID,
            Constant.STATIONERY_APPROVED_WAIT_MANAGER_APPROVE, param.getMessage());
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      resp.setStatus(Constant.ERROR);
    }
    return resp;
  }

	@Override
	public void sendSmsAndNotify(String approveUserName, String userName, String idRecord, int statusRecord,
			String hcdvmessage) throws Exception {
		Thread notiThread = new Thread() {
			public void run() {
				try {
					// find full name of hcdv request
					String fullName = vt050000DAO.findFullNameByUserName(userName);
					// send notify
					String toUserName = approveUserName;
					VT050000AdditionalInfoToSendNotify addInfo = new VT050000AdditionalInfoToSendNotify(idRecord,
							Constant.PMQT_ROLE_MANAGER, Constant.PMQT_ROLE_STAFF_HC_DV);
					String additionalInformation = addInfo.toString();
					String message = notifyMessage.getProperty("N35");
					message = MessageFormat.format(message, fullName, hcdvmessage.toUpperCase());
					String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
					int type = Constant.TYPE_NOTIFY_MODUL05;
					String createUser = userName;
					int status = statusRecord;
					notification.sendNotification(toUserName, additionalInformation, message, title, type, createUser,
							status);
					// send sms
					int statusSms = Constant.STATUS_NEW_SMS;
					String smsType = smsMessage.getProperty("TYPE_SMS_MODUL05");
					sms.sendSms(toUserName, message, statusSms, smsType);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************THREAD NOTI ERROR********:");
				}
			}
		};
		notiThread.start();
	}

	@Override
	public boolean checkSpendingLimitOfUnit(VT050004ParamToInsert param) {
		// find unit of Hcdv logged on in STATIONERY_STAFF

		int unitID = vt050004DAO.findUnitIDOfHcdvLoginInStationeryStaff(param.getApproveUserName());

		// find all Hcdv was configed in STATIONERY_STAFF that has same unit of
		// Hcdv
		// logged on
		List<String> listHcdvUserName = vt050004DAO.findAllHcdvInStationeryStaffByUnitID(unitID);

		// find total money of unit
		VT050000InfoToFindSpendingLimitUnit infoToFindSpendingLimitUnit = new VT050000InfoToFindSpendingLimitUnit();
		infoToFindSpendingLimitUnit.setJobCode(Constant.STATIONERY_HCDV_CODE);
		infoToFindSpendingLimitUnit.setmClass(Constant.STATIONERY_SPENDING_LIMIT_CLASS);
		infoToFindSpendingLimitUnit.setmCode(Constant.STATIONERY_SPENDING_LIMIT_CODE);
		infoToFindSpendingLimitUnit.setUserName(param.getEmployeeUserName());
		infoToFindSpendingLimitUnit.setUnitId(unitID);
		double totalMoneyOfUnit = vt050000DAO.findSpendingLimitUnit(infoToFindSpendingLimitUnit);

		// calculate total money in all request not finish (except reject) in
		// this unit
		// of hcdv logged on in this month
		VT050004InfoToFindTotalMoneyUsedOfUnit infoToFindNotFinish = new VT050004InfoToFindTotalMoneyUsedOfUnit();
		infoToFindNotFinish.setListHcdvUserName(listHcdvUserName);
		List<Integer> listStatusNotFinish = new ArrayList<>();
		listStatusNotFinish.add(Constant.STATIONERY_APPROVED_WAIT_MANAGER_APPROVE);
		listStatusNotFinish.add(Constant.STATIONERY_APPROVED_MANAGER_APPROVE);
		listStatusNotFinish.add(Constant.STATIONERY_APPROVED_RECEIVE_REQUEST);
		listStatusNotFinish.add(Constant.STATIONERY_APPROVED_PAUSE);
		infoToFindNotFinish.setListStatus(listStatusNotFinish);
		double totalUseNotFinished = vt050004DAO.findTotalMoneyUsedNotFinishOfUnitInThisMonth(infoToFindNotFinish);

		// calculate total money in all request finish (VPTCT execute && HCDV
		// confirm
		// receive) in this unit of hcdv logged on in this month
		VT050004InfoToFindTotalMoneyUsedOfUnit infoToFindFinish = new VT050004InfoToFindTotalMoneyUsedOfUnit();
		infoToFindFinish.setListHcdvUserName(listHcdvUserName);
		List<Integer> listStatusFinish = new ArrayList<>();
		listStatusFinish.add(Constant.STATIONERY_APPROVED_EXECUTING);
		listStatusFinish.add(Constant.STATIONERY_APPROVED_FINISH);
		infoToFindFinish.setListStatus(listStatusFinish);
		double totalUseFinished = vt050004DAO.findTotalMoneyUsedNotFinishOfUnitInThisMonth(infoToFindFinish);

		// calculate total money of this request
		double totalMoneyOfRequeset = vt050004DAO.findTotalMoneyOfHcdvRequest(param.getListRequestID());

		// if total money of this request <= (total money of unit) - (total
		// money
		// used(not finish & finish) in this month) => return true
		if (totalMoneyOfRequeset <= totalMoneyOfUnit - totalUseNotFinished - totalUseFinished) {
			return true;
		}
		return false;
	}

	@Override
	public ResponseEntityBase countIssuesStationeryItems(VT050004InfoToSearchIssuesStationeryItems info,
			Collection<GrantedAuthority> roleList) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT050004DataResponse> dataResponse = null;
		try {
			// validate input
			if (StringUtils.isBlank(info.getEmployeeUserName())) {
				info.setEmployeeUserName(null);
			}
			info.setPageNumber(info.getPageNumber() * info.getPageSize());
			// search record status = 0 => send to manager approve
			info.setStatus(Constant.STATIONERY_CREATE);
			// if user is admin => search all
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				info.setRoleAdmin(true);
			}
			// find in db
			dataResponse = vt050004DAO.countIssuesStationeryItems(info);
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

}