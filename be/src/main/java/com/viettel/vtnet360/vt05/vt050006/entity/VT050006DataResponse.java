package com.viettel.vtnet360.vt05.vt050006.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.vt05.vt050004.entity.VT050004DataResponse;

/**
 * @author DuyNK
 *
 */
public class VT050006DataResponse {

	private int dateLimitEnd;
	/** QLDV_EMPLOYEE.FULL_NAME of ISSUES_STATIONERY_APPROVED.HCDV_USERNAME */
	private String fullName;

	/** ISSUES_STATIONERY_APPROVED.CREATE_DATE */
	private Date dateRequest;

	/** ISSUES_STATIONERY_APPROVED.MESSAGE */
	private String message;

	private String unitName;

	private String phoneNumber;

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private int status;

	private String ldReasonReject;
	
	private String vptctReason;
	
	/** list ISSUES_STATIONERY_ITEMS */
	private List<VT050004DataResponse> listData;

	private String feedBackToVptct;

	private String feedBackToHcdv;

	private int ratingToVptct;

	private int ratingToUser;

	private int totalRequest;

	private int totalFulfill;

	private double sumTotalMoney;
	
	private double sumTotalMoneyHcdv;

	private String hcdvReasonReject;

	private Date vptctPostponeToTime;
	
	private String vptctReasonReject;
	
	public VT050006DataResponse() {

	}

	

	


	public VT050006DataResponse(int dateLimitEnd, String fullName, Date dateRequest, String message, String unitName,
			String phoneNumber, int status, String ldReasonReject, String vptctReason,
			List<VT050004DataResponse> listData, String feedBackToVptct, String feedBackToHcdv, int ratingToVptct,
			int ratingToUser, int totalRequest, int totalFulfill, double sumTotalMoney, double sumTotalMoneyHcdv,
			String hcdvReasonReject, Date vptctPostponeToTime, String vptctReasonReject) {
		super();
		this.dateLimitEnd = dateLimitEnd;
		this.fullName = fullName;
		this.dateRequest = dateRequest;
		this.message = message;
		this.unitName = unitName;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.ldReasonReject = ldReasonReject;
		this.vptctReason = vptctReason;
		this.listData = listData;
		this.feedBackToVptct = feedBackToVptct;
		this.feedBackToHcdv = feedBackToHcdv;
		this.ratingToVptct = ratingToVptct;
		this.ratingToUser = ratingToUser;
		this.totalRequest = totalRequest;
		this.totalFulfill = totalFulfill;
		this.sumTotalMoney = sumTotalMoney;
		this.sumTotalMoneyHcdv = sumTotalMoneyHcdv;
		this.hcdvReasonReject = hcdvReasonReject;
		this.vptctPostponeToTime = vptctPostponeToTime;
		this.vptctReasonReject = vptctReasonReject;
	}



	public String getVptctReasonReject() {
		return vptctReasonReject;
	}






	public void setVptctReasonReject(String vptctReasonReject) {
		this.vptctReasonReject = vptctReasonReject;
	}






	public Date getVptctPostponeToTime() {
		return vptctPostponeToTime;
	}



	public void setVptctPostponeToTime(Date vptctPostponeToTime) {
		this.vptctPostponeToTime = vptctPostponeToTime;
	}



	public double getSumTotalMoneyHcdv() {
		return sumTotalMoneyHcdv;
	}

	public void setSumTotalMoneyHcdv(double sumTotalMoneyHcdv) {
		this.sumTotalMoneyHcdv = sumTotalMoneyHcdv;
	}

	public String getLdReasonReject() {
		return ldReasonReject;
	}


	public void setLdReasonReject(String ldReasonReject) {
		this.ldReasonReject = ldReasonReject;
	}


	public String getVptctReason() {
		return vptctReason;
	}


	public void setVptctReason(String vptctReason) {
		this.vptctReason = vptctReason;
	}


	public String getHcdvReasonReject() {
		return hcdvReasonReject;
	}

	public void setHcdvReasonReject(String hcdvReasonReject) {
		this.hcdvReasonReject = hcdvReasonReject;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public int getDateLimitEnd() {
		return dateLimitEnd;
	}

	public void setDateLimitEnd(int dateLimitEnd) {
		this.dateLimitEnd = dateLimitEnd;
	}

	public int getTotalRequest() {
		return totalRequest;
	}

	public void setTotalRequest(int totalRequest) {
		this.totalRequest = totalRequest;
	}

	public int getTotalFulfill() {
		return totalFulfill;
	}

	public void setTotalFulfill(int totalFulfill) {
		this.totalFulfill = totalFulfill;
	}

	public double getSumTotalMoney() {
		return sumTotalMoney;
	}

	public void setSumTotalMoney(double sumTotalMoney) {
		this.sumTotalMoney = sumTotalMoney;
	}

	public String getFeedBackToVptct() {
		return feedBackToVptct;
	}

	public void setFeedBackToVptct(String feedBackToVptct) {
		this.feedBackToVptct = feedBackToVptct;
	}

	public String getFeedBackToHcdv() {
		return feedBackToHcdv;
	}

	public void setFeedBackToHcdv(String feedBackToHcdv) {
		this.feedBackToHcdv = feedBackToHcdv;
	}

	public int getRatingToVptct() {
		return ratingToVptct;
	}

	public void setRatingToVptct(int ratingToVptct) {
		this.ratingToVptct = ratingToVptct;
	}

	public int getRatingToUser() {
		return ratingToUser;
	}

	public void setRatingToUser(int ratingToUser) {
		this.ratingToUser = ratingToUser;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<VT050004DataResponse> getListData() {
		return listData;
	}

	public void setListData(List<VT050004DataResponse> listData) {
		this.listData = listData;
	}
}
