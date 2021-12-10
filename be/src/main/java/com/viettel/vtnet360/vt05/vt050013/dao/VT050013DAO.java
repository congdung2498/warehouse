package com.viettel.vtnet360.vt05.vt050013.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viettel.vtnet360.vt05.vt050013.entity.VT050013DataDetail;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013DataGetAll;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013DataGetById;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013InfoToCancelRequest;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013InfoToCheckConditionRating;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013InfoToCheckConditionRatingVPTCT;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013InfoToEditRequest;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013InfoToFindDataDetail;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013ParamToRating;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013ParamToRatingVPTCT;

/**
 * @author DuyNK
 * 
 */
@Repository
public interface VT050013DAO {

	/**
	 * find data detail of 1 request
	 * 
	 * @param info
	 * @return List<VT050013DataDetail>
	 */
	public List<VT050013DataDetail> findData(VT050013InfoToFindDataDetail info);

	/**
	 * check condition before rating
	 * 
	 * @param info
	 * @return Integer
	 */
	public int checkConditionRating(VT050013InfoToCheckConditionRating info);

	/**
	 * rating
	 * 
	 * @param param
	 * @return Integer
	 */
	public int rating(VT050013ParamToRating param);

	/**
	 * check condition before cancel request and edit request
	 * 
	 * @param info
	 * @return Integer
	 */
	public int checkConditionCancelRequest(VT050013InfoToCancelRequest info);

	/**
	 * change status in ISSUES_STATIONERY when cancel request
	 * 
	 * @param info
	 */
	public void cancelIssueStationery(VT050013InfoToCancelRequest info);

	/**
	 * change status in ISSUES_STATIONERY_ITEMS when cancel request
	 * 
	 * @param info
	 */
	public void cancelIssueStationeryItem(VT050013InfoToCancelRequest info);

	/**
	 * delete all record of this request in ISSUES_STATIONERY_ITEMS
	 * 
	 * @param issuesStationeryID
	 */
	public void deleteIssueStationeryItem(String issuesStationeryID);

	/**
	 * update info of this request in ISSUES_STATIONERY
	 * 
	 * @param info
	 */
	public void updateIssuesStationery(VT050013InfoToEditRequest info);

	/**
	 * find total money in this request
	 * 
	 * @param requestID
	 * @return Double
	 */
	public double findTotalMoneyUseInThisRequest(String requestID);
	
	/**
	 * delete item (just status create in request)
	 * 
	 * @param info
	 */
	public void deleteIssueStationeryItemJustCreate(VT050013InfoToEditRequest info);
	
	/**
	 * check condition before delete item of request(get total of item can delete of list item get from param)
	 * @param info
	 * @return Integer
	 */
	public int checkConditionBeforeDeleteItem(VT050013InfoToEditRequest info);
	
	// start rating VPTCT
	public int checkConditionRatingVPTCT(VT050013InfoToCheckConditionRatingVPTCT info);
	
	public int ratingVPTCT(VT050013ParamToRatingVPTCT param);
	// end rating VPTCT
	
	// start rating HCDV
	public int checkConditionRatingHCDV(VT050013InfoToCheckConditionRatingVPTCT info);
	
	public int ratingHCDV(VT050013ParamToRatingVPTCT param);
	// end ratting HCDV
	
	// get details BY Id
	 public List<VT050013DataGetById> findDataVPTCT(String issuesStationeryApprovedId);
	 
	 public VT050013DataGetAll findAllDataVPTCT(String issuesStationeryApprovedId);
}
