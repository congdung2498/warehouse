package com.viettel.vtnet360.vt05.vt050013.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.stationery.service.StationeryService;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000StationeryInfo;
import com.viettel.vtnet360.vt05.vt050000.service.VT050000Service;
import com.viettel.vtnet360.vt05.vt050003.dao.VT050003DAO;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003IssuesStationeryItemsToInsert;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003StationeryParam;
import com.viettel.vtnet360.vt05.vt050013.dao.VT050013DAO;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013DataDetail;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013DataGetAll;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013DataGetById;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013DataResponse;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013InfoToCancelRequest;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013InfoToCheckConditionRating;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013InfoToEditRequest;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013InfoToFindDataDetail;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013IssuesStationeryItemDetail;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013ParamToRating;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013ParamToRatingVPTCT;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013RequestParam;

/**
 * @author DuyNK 04/10/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050013ServiceImpl implements VT050013Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050013DAO vt050013DAO;

	@Autowired
	private VT050003DAO vt050003DAO;

	@Autowired
	private VT050000Service vt050000Service;
	
	@Autowired
	private StationeryService  stationeryService ;


	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findData(VT050013RequestParam param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		VT050013DataResponse dataResponse = new VT050013DataResponse();
		try {
			// find data in db
			List<VT050013DataDetail> listDetail = null;
			VT050013InfoToFindDataDetail info = new VT050013InfoToFindDataDetail(param.getRequestID(),
					Constant.M_SYSTEM_CODE_CALCULATION);
			listDetail = vt050013DAO.findData(info);
			if (listDetail.isEmpty()) {
				resp.setStatus(Constant.SUCCESS);
				resp.setData(null);
				return resp;
			}
			dataResponse.setPlaceID(listDetail.get(0).getPlaceID());
			dataResponse.setPlaceName(listDetail.get(0).getPlaceName());
			dataResponse.setStatus(listDetail.get(0).getStatusRecord());
			dataResponse.setMessage(listDetail.get(0).getMessage());
			dataResponse.setRating(listDetail.get(0).getRating());
			dataResponse.setFeedback(listDetail.get(0).getFeedback());
			List<VT050013IssuesStationeryItemDetail> listItemDetail = new ArrayList<>();
			for (int i = 0; i < listDetail.size(); i++) {
				String stationeryID = listDetail.get(i).getStationeryID();
				String stationeryName = listDetail.get(i).getStationeryName();
				int quantity = listDetail.get(i).getQuantity();
				double unitPrice = listDetail.get(i).getUnitPrice();
				String calculationUnit = listDetail.get(i).getCalculationUnit();
				int totalFulFill = listDetail.get(i).getTotalFulFill();
				int status = listDetail.get(i).getStatus();
				String reasonReject = listDetail.get(i).getReasonReject();
				String reasonPause = listDetail.get(i).getReasonPause();
				listItemDetail.add(new VT050013IssuesStationeryItemDetail(stationeryID, stationeryName, quantity,
						totalFulFill, unitPrice, calculationUnit, status, reasonReject, reasonPause));
			}
			dataResponse.setListItem(listItemDetail);
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional
	public ResponseEntityBase rating(VT050013ParamToRating param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// check condition before rating
			VT050013InfoToCheckConditionRating info = new VT050013InfoToCheckConditionRating();
			info.setIssueStationeryID(param.getRequestID());
			List<Integer> listStatus = new ArrayList<>();
			listStatus.add(Constant.ISSUES_STATIONERY_COMPLETE_SECTION);
			listStatus.add(Constant.ISSUES_STATIONERY_COMPLETE);
			info.setListStatus(listStatus);
			int checkCondition = vt050013DAO.checkConditionRating(info);
			if (checkCondition == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			// update rating to db
			vt050013DAO.rating(param);
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional
	public ResponseEntityBase cancelRequest(VT050013RequestParam param, String userName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			VT050013InfoToCancelRequest info = new VT050013InfoToCancelRequest();
			info.setIssueStationeryID(param.getRequestID());
			info.setUpdateUser(userName);
			// check condition before cancel request
			info.setStatus(Constant.STATIONERY_CREATE);
			int checkConditionCancel = vt050013DAO.checkConditionCancelRequest(info);
			if (checkConditionCancel != Constant.NONE_EXIST_RECORD) { // there are some record in processing
				resp.setStatus(Constant.ERROR_REQUEST_INVALID);
				return resp;
			}
			// change status in ISSUES_STATIONERY
			info.setStatus(Constant.ISSUES_STATIONERY_CANCEL);
			vt050013DAO.cancelIssueStationery(info);
			// change status in ISSUES_STATIONERY_ITEMS
			info.setStatus(Constant.STATIONERY_CANCEL);
			vt050013DAO.cancelIssueStationeryItem(info);
			// set response status to send to client
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional
	public ResponseEntityBase editRequest(VT050013InfoToEditRequest param, Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// check validate input
			if (param.getListStationery() == null || param.getListStationery().isEmpty() || param.getPlaceID() <= 0) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}

			// if isDelete = true => api use for delete some item of request
			if(param.isDeleteFlag()) {
				// check condition before delete item (number of item request to delete = number of item can delete in system)
				// item can delete = item that has status = create
				int checkCondition = vt050013DAO.checkConditionBeforeDeleteItem(param);
				if(checkCondition != param.getListStationery().size()) {
					resp.setStatus(Constant.ERROR_REQUEST_INVALID);
					return resp;
				} else {
					// delete item
					vt050013DAO.deleteIssueStationeryItemJustCreate(param);
					
					// update status in ISSUES_STATIONERY
					vt050000Service.changeStatusIssuesStationery(param.getRequestID(), param.getUpdateUser());
					
					// set response status to send to client
					resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				}
			} else { // isDelete = false => api use for edit request
				VT050013InfoToCancelRequest info = new VT050013InfoToCancelRequest();
				info.setIssueStationeryID(param.getRequestID());
				info.setUpdateUser(param.getUpdateUser());
				// check condition before edit request
				info.setStatus(Constant.STATIONERY_CREATE);
				int checkConditionCancel = vt050013DAO.checkConditionCancelRequest(info);
				if (checkConditionCancel != Constant.NONE_EXIST_RECORD) { // there are some record in processing
					resp.setStatus(Constant.ERROR_REQUEST_INVALID);
					return resp;
				}

				// check spending limit
				List<VT050003StationeryParam> listStationery = param.getListStationery();
				List<String> listStationeryID = new ArrayList<>();
				for (int i = 0; i < listStationery.size(); i++) {
					listStationeryID.add(listStationery.get(i).getStationeryID());
				}
				List<VT050000StationeryInfo> listPrice = vt050003DAO.findStationeryPrice(listStationeryID);
				int totalPrice = 0;
				for (int i = 0; i < listStationery.size(); i++) {
					for (int j = 0; j < listPrice.size(); j++) {
						if (listStationery.get(i).getStationeryID().equals(listPrice.get(j).getStationeryID())) {
							totalPrice += listStationery.get(i).getQuantity() * listPrice.get(j).getUnitPrice();
						}
					}
				}
				// get remaining spending limit
				double remainingSpendingLimit = vt050000Service.caculRemainingSpendingLimit(param.getUpdateUser(), roleList);

				// find Total Money Use In This old Request
				double moneyInthisOldRequest = vt050013DAO.findTotalMoneyUseInThisRequest(param.getRequestID());
				// remaning spending limit (new) = old(full request) + Total Money Use In This
				// old Request
				remainingSpendingLimit += moneyInthisOldRequest;
				// if total price request > Remaining spending limit => no insert
				if (totalPrice > remainingSpendingLimit) {
					resp.setStatus(Constant.STATIONERY_RESPONSE_SPENDING_LIMIT_EXCEED);
					return resp;
				}

				// delete all record of this request in ISSUES_STATIONERY_ITEMS
				vt050013DAO.deleteIssueStationeryItem(param.getRequestID());
				// update info of this request in ISSUES_STATIONERY
				vt050013DAO.updateIssuesStationery(param);
				// insert new record in ISSUES_STATIONERY_ITEMS
				VT050003IssuesStationeryItemsToInsert issuesStationeryItemsToInsert = new VT050003IssuesStationeryItemsToInsert();
				issuesStationeryItemsToInsert.setIssuesStationeryID(param.getRequestID());
				issuesStationeryItemsToInsert.setStatus(Constant.STATIONERY_CREATE);
				issuesStationeryItemsToInsert.setCreateUser(param.getUpdateUser());
				issuesStationeryItemsToInsert.setListStationery(param.getListStationery());
				vt050003DAO.insertIssuesStationeryItems(issuesStationeryItemsToInsert);
				// set response status to send to client
				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase ratingVPTCT(VT050013ParamToRatingVPTCT param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// check condition before rating
//			VT050013InfoToCheckConditionRatingVPTCT info = new VT050013InfoToCheckConditionRatingVPTCT();
//			info.setIssuesStationeryApprovedId(param.getRequestId());
//			List<Integer> listStatus = new ArrayList<>();
//			listStatus.add(Constant.STATIONERY_PAUSE);
//			listStatus.add(Constant.STATIONERY_FINISH);
//			info.setListStatus(listStatus);
//			int checkCondition = vt050013DAO.checkConditionRatingVPTCT(info);
//			if (checkCondition > Constant.NONE_EXIST_RECORD) {
//				resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
//				return resp;
//			}
			// update rating to db
			vt050013DAO.ratingVPTCT(param);
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;	
	}

	@Override
	public ResponseEntityBase ratingHCDV(VT050013ParamToRatingVPTCT param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// check condition before rating
//			VT050013InfoToCheckConditionRatingVPTCT info = new VT050013InfoToCheckConditionRatingVPTCT();
//			 info.setIssuesStationeryApprovedId(param.getRequestId());
//			List<Integer> listStatus = new ArrayList<>();
//			listStatus.add(Constant.STATIONERY_FINISH);
//			info.setListStatus(listStatus);
//			int checkCondition = vt050013DAO.checkConditionRatingHCDV(info);
//			if (checkCondition > Constant.NONE_EXIST_RECORD) {
//				resp.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);
//				return resp;
//			}
			// update rating to db
			vt050013DAO.ratingHCDV(param);
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

  @Override
  public ResponseEntityBase findDataVPTCT(VT050013RequestParam param) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    try {
      VT050013DataGetAll dataResponse = new VT050013DataGetAll();
      dataResponse = stationeryService.getApprovedInfoById(param.getRequestID());
      dataResponse.setUserName(param.getUserName());
      resp.setData(dataResponse);
      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw (e);
    }
    return resp;
  }
}