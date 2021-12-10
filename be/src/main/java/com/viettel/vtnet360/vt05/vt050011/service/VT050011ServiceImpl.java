package com.viettel.vtnet360.vt05.vt050011.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.stationery.request.dao.StationeryDAO;
import com.viettel.vtnet360.stationery.service.ApprovingInfo;
import com.viettel.vtnet360.stationery.service.StationeryService;
import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;
import com.viettel.vtnet360.vt03.Common.DateUtil;
import com.viettel.vtnet360.vt05.vt050000.service.VT050000Service;
import com.viettel.vtnet360.vt05.vt050011.dao.VT050011DAO;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011DataResponse;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011InfoHcdvRequest;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011InfoRequest;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011InfoRequestDetail;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011InfoToInsertStationeryItemHistory;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011InfoToUpdateStorageHcdvRequest;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011RequestParamToExecute;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011RequestParamToSearch;
import com.viettel.vtnet360.vt05.vt050011.entity.VT050011Stationery;

/**
 * @author DuyNK
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050011ServiceImpl implements VT050011Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050011DAO vt050011DAO;

	@Autowired
	private VT050000Service vt050000Service;
	
	@Autowired
	private VT000000DAO             vt000000DAO;
	
	@Autowired
	private StationeryDAO      stationeryDao;
	
	@Autowired
	private StationeryService       stationeryService;

	@Autowired
	private Properties notifyMessage;

	@Autowired
	private Properties smsMessage;
	

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findData(VT050011RequestParamToSearch param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		VT050011DataResponse dataResponse = new VT050011DataResponse();
		try {
			List<VT050011InfoRequestDetail> requestDetail = null;
			VT050011InfoRequest requestData = new VT050011InfoRequest();
			requestDetail = vt050011DAO.findInfoRequest(param);
			requestData = vt050011DAO.findInfoRequestStationery(param);
			dataResponse.setFullName(requestData.getFullName());
			dataResponse.setPhoneNumber(requestData.getPhoneNumber());
			dataResponse.setReason(requestData.getReason());
			dataResponse.setAppointmentTime(requestData.getAppointmentTime());
			dataResponse.setStatus(requestData.getStatus());
			dataResponse.setUnitName(requestData.getUnitName());
			
			List<VT050011Stationery> listStationery = new ArrayList<>();
			if(requestDetail != null && requestDetail.size() > 0) {
				for (int i = 0; i < requestDetail.size(); i++) {
					listStationery.add(new VT050011Stationery(requestDetail.get(i).getStationeryID(),
							requestDetail.get(i).getStationeryName(), requestDetail.get(i).getQuantity()));
				}
			}
			dataResponse.setListStationery(listStationery);
			 
			resp.setData(dataResponse);
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e);
		}
		return resp;
	}

	@Override
	@Transactional
	public ResponseEntityBase executeGiveOut(VT050011RequestParamToExecute param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// list status condition before update of ISSUES_STATIONERY_APPROVED
			List<Integer> listStatusStationeryApprove = new ArrayList<>();
			listStatusStationeryApprove.add(Constant.STATIONERY_APPROVED_MANAGER_APPROVE);
			listStatusStationeryApprove.add(Constant.STATIONERY_APPROVED_RECEIVE_REQUEST);
			listStatusStationeryApprove.add(Constant.STATIONERY_APPROVED_PAUSE);
			param.setListStatus(listStatusStationeryApprove);
			
			VT050011InfoHcdvRequest infoHcvp = vt050011DAO.findInfoHcdvRequest(param.getRequestID());
			String hcvpUserName = infoHcvp.getHcdvUserName();
			
			//check condition record before update(status now of request valid or not)
			int checkCondition = vt050011DAO.checkConditionBeforeUpdateIssuesApprove(param);
			if(checkCondition == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.ERROR_REQUEST_INVALID);
				return resp;
			}
			if (param.getAction() == Constant.STATIONERY_APPROVED_RECEIVE_REQUEST) { // confirm received request from
																						// HCDV
				// update status & info to ISSUES_STATIONERY_APPROVED
				param.setActionReceivedRequest(true);
				param.setActionPause(false);
				param.setActionReject(false);
				param.setActionExecute(false);
				param.setStatus(Constant.STATIONERY_APPROVED_RECEIVE_REQUEST);
				int checkSuccess = vt050011DAO.updateIssuesStationeryApprove(param);
				if (checkSuccess == Constant.SUCCESS) {
					// update status & info to ISSUES_STATIONERY_ITEMS
					param.setStatus(Constant.STATIONERY_RECEIVE_REQUEST);
					vt050011DAO.updateIssuesStationeryItems(param);
					// set data to send client
					resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				} else {
					resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
					return resp;
				}

			} else if (param.getAction() == Constant.STATIONERY_APPROVED_PAUSE) { // execute pause
				Date now = new Date();
				if (param.getAppointmentTime().compareTo(now) <= 0) {
					resp.setStatus(Constant.ERROR_OVER_TIME_PAUSE_EXECUTE_VPP);
					return resp;
				}
				// update status & info to ISSUES_STATIONERY_APPROVED
				param.setActionReceivedRequest(false);
				param.setActionPause(true);
				param.setActionReject(false);
				param.setActionExecute(false);
				param.setStatus(Constant.STATIONERY_APPROVED_PAUSE);
				int checkSuccess = vt050011DAO.updateIssuesStationeryApprove(param);
				if (checkSuccess == Constant.SUCCESS) {
					// update status & info to ISSUES_STATIONERY_ITEMS
					param.setStatus(Constant.STATIONERY_PAUSE);
					vt050011DAO.updateIssuesStationeryItems(param);
					// insert to ISSUES_STATIONERY_ITEM_HISTORY
					VT050011InfoToInsertStationeryItemHistory info = new VT050011InfoToInsertStationeryItemHistory();
					info.setRequestID(param.getRequestID());
					info.setPostponeToTime(param.getAppointmentTime());
					info.setReason(param.getReason());
					info.setCreateUser(param.getVptctUserName());
					vt050011DAO.insertStationeryItemHistory(info);
					// set data to send client
					resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				} else {
					resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
					return resp;
				}

			} else if (param.getAction() == Constant.STATIONERY_APPROVED_REJECT) { // not execute(reject)
				// update status & info to ISSUES_STATIONERY_APPROVED
				param.setActionReceivedRequest(false);
				param.setActionPause(false);
				param.setActionReject(true);
				param.setActionExecute(false);
				param.setStatus(Constant.STATIONERY_APPROVED_REJECT);
				int checkSuccess = vt050011DAO.updateIssuesStationeryApprove(param);
				if (checkSuccess == Constant.SUCCESS) {
					// update status & info to ISSUES_STATIONERY_ITEMS
					param.setStatus(Constant.STATIONERY_REJECT);
					vt050011DAO.updateIssuesStationeryItems(param);
					// update status in ISSUES_STATIONERY
					List<String> listIssuesStationeryID = vt050011DAO.findListIssuesStationeryID(param.getRequestID());
					for (int i = 0; i < listIssuesStationeryID.size(); i++) {
						String issuesStationeryID = listIssuesStationeryID.get(i);
						vt050000Service.changeStatusIssuesStationery(issuesStationeryID, param.getVptctUserName());
					}
					// set data to send client
					resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
					// send sms and notify
				} else {
					resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
					return resp;
				}
			} else if (param.getAction() == Constant.STATIONERY_APPROVED_EXECUTING) { // finish(execute giving out to
																						// hcdv)
				// update status & info to ISSUES_STATIONERY_APPROVED
				param.setActionReceivedRequest(false);
				param.setActionPause(false);
				param.setActionReject(false);
				param.setActionExecute(true);
				param.setStatus(Constant.STATIONERY_APPROVED_EXECUTING);
				int checkSuccess = vt050011DAO.updateIssuesStationeryApprove(param);
				if (checkSuccess == Constant.SUCCESS) {
					// update status & info to ISSUES_STATIONERY_ITEMS
					param.setStatus(Constant.STATIONERY_EXECUTING);
					vt050011DAO.updateIssuesStationeryItems(param);
					// update total fulfill to STORAGE_HCDV_REQUEST
					VT050011InfoToUpdateStorageHcdvRequest info = new VT050011InfoToUpdateStorageHcdvRequest();
					info.setIssuesStationeryApproveID(param.getRequestID());
					info.setListStationery(param.getListStaioneryGiveOut());
					info.setVptctUserName(param.getVptctUserName());
					vt050011DAO.updateStorageHcdvRequest(info);
					resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
					// send sms and notify
				} else {
					resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
					return resp;
				}
			} else {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			}
			
			try {
				sendSmsAndNotifyToHCDV(param.getAction(), param);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw (e);
		}
		return resp;
	}

	public void sendSmsAndNotifyToHCDV(int statusRecord, VT050011RequestParamToExecute param) throws Exception {
		Thread notiThread = new Thread() {
			public void run() {
				try {
					List<ApprovingInfo> approvingInfos = stationeryDao.getApprovingInfo(param.getRequestID());
					ApprovingInfo firtsInfo = approvingInfos.get(0);

					String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
					String smsTitle = smsMessage.getProperty("TYPE_SMS_MODUL05");

					AdditionalInfoBase addInfo = new AdditionalInfoBase();
					addInfo.setId(param.getRequestID());
					addInfo.setRoleReceiver(Constant.PMQT_ROLE_STAFF_HC_DV);

					if(statusRecord == Constant.STATIONERY_APPROVED_RECEIVE_REQUEST) {
						if(firtsInfo.getHcdvUsername() != null) {
							VT000000EntitySmsVsNotify userInfo = vt000000DAO.findNotifySmsByUserName(param.getVptctUserName());
							String hcdvMessageNotify = MessageFormat.format(notifyMessage.getProperty("N45"), 
									userInfo.getCreateUser(), param.getRequestID());
							VT000000EntitySmsVsNotify hcdvInfo = vt000000DAO.findNotifySmsByUserName(firtsInfo.getHcdvUsername());
							hcdvInfo.setToUserName(firtsInfo.getHcdvUsername());
							stationeryService.sendNotiAndSms(hcdvInfo, hcdvMessageNotify, title, smsTitle, addInfo, Constant.TYPE_NOTIFY_MODUL05);
						}
					} else if(statusRecord == Constant.STATIONERY_APPROVED_PAUSE) {
						if(firtsInfo.getHcdvUsername() != null) {
							String hcdvMessageNotify = MessageFormat.format(notifyMessage.getProperty("N42"), 
									param.getRequestID(), new DateUtil(param.getAppointmentTime()).toString(DateUtil.FORMAT_DATE), param.getReason());
							VT000000EntitySmsVsNotify hcdvInfo = vt000000DAO.findNotifySmsByUserName(firtsInfo.getHcdvUsername());
							hcdvInfo.setToUserName(firtsInfo.getHcdvUsername());
							stationeryService.sendNotiAndSms(hcdvInfo, hcdvMessageNotify, title, smsTitle, addInfo, Constant.TYPE_NOTIFY_MODUL05);
						}
					} else if(statusRecord == Constant.STATIONERY_APPROVED_REJECT) {
						if(firtsInfo.getHcdvUsername() != null) {
							String hcdvMessageNotify = MessageFormat.format(notifyMessage.getProperty("N44"), 
									param.getRequestID(), param.getReason());
							VT000000EntitySmsVsNotify hcdvInfo = vt000000DAO.findNotifySmsByUserName(firtsInfo.getHcdvUsername());
							hcdvInfo.setToUserName(firtsInfo.getHcdvUsername());
							stationeryService.sendNotiAndSms(hcdvInfo, hcdvMessageNotify, title, smsTitle, addInfo, 52);
						}
					} else if(statusRecord == Constant.STATIONERY_APPROVED_EXECUTING) {
						if(firtsInfo.getHcdvUsername() != null) {
							String hcdvMessageNotify = MessageFormat.format(notifyMessage.getProperty("N43"), param.getRequestID());
							VT000000EntitySmsVsNotify hcdvInfo = vt000000DAO.findNotifySmsByUserName(firtsInfo.getHcdvUsername());
							hcdvInfo.setToUserName(firtsInfo.getHcdvUsername());
							stationeryService.sendNotiAndSms(hcdvInfo, hcdvMessageNotify, title, smsTitle, addInfo, 51);
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************THREAD NOTI ERROR********:");
				}
			}
		};
		notiThread.start();
	}
	
	
}