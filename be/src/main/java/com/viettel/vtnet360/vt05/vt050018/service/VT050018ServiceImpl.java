package com.viettel.vtnet360.vt05.vt050018.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.stationery.service.StationeryService;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050000.service.VT050000Service;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013DataGetAll;
import com.viettel.vtnet360.vt05.vt050018.dao.VT050018DAO;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018DataDetail;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018DataResponse;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018InfoOfEmployeeRequest;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018InfoToCheckConditionBeforeUpdateIssuesApprove;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018InfoToUpdateIssuesStaioneryApprove;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018InfoToUpdateIssuesStationeryItem;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018RequestParamToConfirm;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018RequestParamToSearch;

/**
 * @author DuyNK
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050018ServiceImpl implements VT050018Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050018DAO vt050018DAO;

	@Autowired
	private VT050000Service vt050000Service;

	@Autowired
	private StationeryService stationeryService;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findData(VT050018RequestParamToSearch param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		VT050018DataResponse dataResponse = new VT050018DataResponse();;
		try {
			
			VT050013DataGetAll vt050013DataGetall = new VT050013DataGetAll();
			vt050013DataGetall = stationeryService.getApprovedInfoById(param.getRequestID());
			dataResponse.setFeedbackToHcdv(vt050013DataGetall.getFeedbackToHcdv());
			dataResponse.setFeedbackToVptct(vt050013DataGetall.getFeedbackToVptct());
			dataResponse.setRatingToUser(vt050013DataGetall.getRatingToUser());
			dataResponse.setRatingToVptct(vt050013DataGetall.getRatingToVptct());
			dataResponse.setTotalFulfill(vt050013DataGetall.getCountTotalFullfill());
			dataResponse.setTotalRequest(vt050013DataGetall.getCountTotalRequest());
			dataResponse.setTotalMoney(vt050013DataGetall.getCountTotalMoneyRequest());
			dataResponse.setTotalMoneyFullfill(vt050013DataGetall.getCountTotalMoneyFullfill());
			dataResponse.setMessage(vt050013DataGetall.getHcdvMessage());
			dataResponse.setDateRequest(vt050013DataGetall.getCreateDate());
			dataResponse.setStatus(vt050013DataGetall.getStatus());
			dataResponse.setHcdvFullName(vt050013DataGetall.getHcdvUserName());
			
			// find data of request(info of employee)
			List<VT050018DataDetail> listDataDetail = null;
			listDataDetail = vt050018DAO.findDataDetail(param);
			dataResponse.setListData(listDataDetail);
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase ConfirmReceivedStationery(VT050018RequestParamToConfirm param, String hcdvUserName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {

			// check condition record before update(status now of request valid
			// or not)
			VT050018InfoToCheckConditionBeforeUpdateIssuesApprove infoCheckCondition = new VT050018InfoToCheckConditionBeforeUpdateIssuesApprove();
			infoCheckCondition.setIssuesStationeryApproveID(param.getRequestID());
			infoCheckCondition.setStatus(Constant.STATIONERY_APPROVED_EXECUTING);
			int checkCondition = vt050018DAO.checkConditionBeforeUpdateIssuesApprove(infoCheckCondition);
			if (checkCondition == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.ERROR_REQUEST_INVALID);
				return resp;
			}
			// update ISSUES_STATIONERY_APPROVED
			VT050018InfoToUpdateIssuesStaioneryApprove infoToUpdateIssuesStationeryApprove = new VT050018InfoToUpdateIssuesStaioneryApprove();
			infoToUpdateIssuesStationeryApprove.setStatus(Constant.STATIONERY_APPROVED_FINISH);
			infoToUpdateIssuesStationeryApprove.setHcdvUserName(hcdvUserName);
			infoToUpdateIssuesStationeryApprove.setRequestID(param.getRequestID());
			infoToUpdateIssuesStationeryApprove.setStatusNow(Constant.STATIONERY_APPROVED_EXECUTING);
			vt050018DAO.updateIssuesStationeryApprove(infoToUpdateIssuesStationeryApprove);
			// find total stationery that employee request with each stationery
			List<VT050018InfoOfEmployeeRequest> listInfo = vt050018DAO.findTotalRequestOfEmployee(param.getRequestID());
			// update ISSUES_STATIONERY_ITEMS
			VT050018InfoToUpdateIssuesStationeryItem infoToUpdateIssuesStationeryItem = new VT050018InfoToUpdateIssuesStationeryItem();
			infoToUpdateIssuesStationeryItem.setHcdvUserName(hcdvUserName);
			infoToUpdateIssuesStationeryItem.setListInfo(listInfo);
			infoToUpdateIssuesStationeryItem.setStatus(Constant.STATIONERY_FINISH);
			infoToUpdateIssuesStationeryItem.setStatusNow(Constant.STATIONERY_EXECUTING);
			vt050018DAO.updateIssuesStationeryItems(infoToUpdateIssuesStationeryItem);
			// update status in ISSUES_STATIONERY
			List<String> listIssuesStationeryID = vt050018DAO.findIssuesStationeryID(param.getRequestID());
			for (int i = 0; i < listIssuesStationeryID.size(); i++) {
				String issuesStationeryID = listIssuesStationeryID.get(i);
				vt050000Service.changeStatusIssuesStationery(issuesStationeryID, hcdvUserName);
			}
			// set response status to send to client
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase refuseReceivedStationery(VT050018RequestParamToConfirm param, String hcdvUserName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {

			// check condition record before update(status now of request valid
			// or not)
			VT050018InfoToCheckConditionBeforeUpdateIssuesApprove infoCheckCondition = new VT050018InfoToCheckConditionBeforeUpdateIssuesApprove();
			infoCheckCondition.setIssuesStationeryApproveID(param.getRequestID());
			infoCheckCondition.setStatus(Constant.STATIONERY_APPROVED_EXECUTING);
			int checkCondition = vt050018DAO.checkConditionBeforeUpdateIssuesApprove(infoCheckCondition);
			if (checkCondition == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.ERROR_REQUEST_INVALID);
				return resp;
			}
			// update ISSUES_STATIONERY_APPROVED
			VT050018InfoToUpdateIssuesStaioneryApprove infoToUpdateIssuesStationeryApprove = new VT050018InfoToUpdateIssuesStaioneryApprove();
			infoToUpdateIssuesStationeryApprove.setStatus(Constant.STATIONERY_APPROVED_COMPLETE);
			infoToUpdateIssuesStationeryApprove.setHcdvUserName(hcdvUserName);
			infoToUpdateIssuesStationeryApprove.setHcdvReasonReject(param.getHcdvReasonReject());
			infoToUpdateIssuesStationeryApprove.setRequestID(param.getRequestID());
			vt050018DAO.refuseIssuesStationeryApprove(infoToUpdateIssuesStationeryApprove);
			// find total stationery that employee request with each stationery
			List<VT050018InfoOfEmployeeRequest> listInfo = vt050018DAO.findTotalRequestOfEmployee(param.getRequestID());
			// update ISSUES_STATIONERY_ITEMS
			VT050018InfoToUpdateIssuesStationeryItem infoToUpdateIssuesStationeryItem = new VT050018InfoToUpdateIssuesStationeryItem();
			infoToUpdateIssuesStationeryItem.setHcdvUserName(hcdvUserName);
			infoToUpdateIssuesStationeryItem.setListInfo(listInfo);
			infoToUpdateIssuesStationeryItem.setStatus(Constant.STATIONERY_REFUSE);
			vt050018DAO.refuseIssuesStationeryItems(infoToUpdateIssuesStationeryItem);
			// update status in ISSUES_STATIONERY
			List<String> listIssuesStationeryID = vt050018DAO.findIssuesStationeryID(param.getRequestID());
			for (int i = 0; i < listIssuesStationeryID.size(); i++) {
				String issuesStationeryID = listIssuesStationeryID.get(i);
				vt050000Service.changeStatusIssuesStationery(issuesStationeryID, hcdvUserName);
			}
			// set response status to send to client
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	// private void sendSmsAndNoti() {
	// Thread notiThread = new Thread() {
	// public void run() {
	// try {
	// int type = Constant.TYPE_NOTIFY_MODUL05;
	// VT040000EntityAdditionalInfo addInfomation = new
	// VT040000EntityAdditionalInfo();
	// addInfomation.setId(returnId);
	//
	// String messageNotify =
	// MessageFormat.format(smsMessage.getProperty("S23"),
	// ((String) data.get("empFullName")), ((String)
	// data.get("serviceId")).toUpperCase());
	// String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL04");
	// notification.sendNotification(userNameManager, addInfomation.toString(),
	// messageNotify, title,
	// Constant.TYPE_NOTIFY_MODUL04, userName, Constant.WAITING_APROVE_IS_SV);
	//
	// // build message and send Sms
	// String messageSms = MessageFormat.format(smsMessage.getProperty("S23"),
	// ((String) data.get("empFullName")), ((String)
	// data.get("serviceId")).toUpperCase());
	// String typeSms = smsMessage.getProperty("TYPE_SMS_MODUL04_APPROVE");
	// sms.sendSms(toUserid, messageSms, Constant.STATUS_NEW_SMS, Phone,
	// typeSms);
	//
	// } catch (Exception e) {
	// logger.error(e.getMessage(), e);
	// logger.info("****************************THREAD NOTI ERROR********:");
	//
	// }
	// }
	//
	// };
	//
	// notiThread.start();
	// }
}