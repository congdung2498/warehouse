package com.viettel.vtnet360.stationery.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.stationery.service.ItemModel;
import com.viettel.vtnet360.vt05.vt050004.entity.VT050004DataResponse;

public class DataResponseRatting {
	/** QLDV_EMPLOYEE.FULL_NAME of ISSUES_STATIONERY_APPROVED.HCDV_USERNAME */
	private String fullNameHcdv;
	
	private String fullNameTct;
	/** ISSUES_STATIONERY_APPROVED.CREATE_DATE */
	private Date dateRequest;

	/** ISSUES_STATIONERY_APPROVED.MESSAGE */
	private String message;

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private int status;

	/** list ISSUES_STATIONERY_ITEMS */
	private List<ItemModel> listData;

	private String feedBackToVptct;

	private String feedBackToHcdv;

	private int ratingToVptct;

	private int ratingToUser;

	private int totalRequest;

	private int totalFulfill;

	private double sumTotalMoney;
	
	private int totalRequestHcdv ;
	
	private int totalFulfillHcdv;
	
	private double sumTotalMoneyHcdv;

	private String hcdvReasonReject ;
	
	private String ldReasonReject;
	
	private String vptctReasonReject;
	
	private Date vptctPostponeToTime;
	
	private String vptctReason;
	
	private String unitName;
	
	private String phoneNumber;
	
	
	

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

  public Date getVptctPostponeToTime() {
		return vptctPostponeToTime;
	}

	public void setVptctPostponeToTime(Date vptctPostponeToTime) {
		this.vptctPostponeToTime = vptctPostponeToTime;
	}

	public String getVptctReason() {
		return vptctReason;
	}

	public void setVptctReason(String vptctReason) {
		this.vptctReason = vptctReason;
	}

	public String getVptctReasonReject() {
		return vptctReasonReject;
	}

	public void setVptctReasonReject(String vptctReasonReject) {
		this.vptctReasonReject = vptctReasonReject;
	}

	public String getLdReasonReject() {
		return ldReasonReject;
	}

	public void setLdReasonReject(String ldReasonReject) {
		this.ldReasonReject = ldReasonReject;
	}

	public String getHcdvReasonReject() {
		return hcdvReasonReject;
	}

	public void setHcdvReasonReject(String hcdvReasonReject) {
		this.hcdvReasonReject = hcdvReasonReject;
	}


	public String getFullNameHcdv() {
		return fullNameHcdv;
	}

	public void setFullNameHcdv(String fullNameHcdv) {
		this.fullNameHcdv = fullNameHcdv;
	}

	public String getFullNameTct() {
		return fullNameTct;
	}

	public void setFullNameTct(String fullNameTct) {
		this.fullNameTct = fullNameTct;
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

	
	public List<ItemModel> getListData() {
		return listData;
	}

	public void setListData(List<ItemModel> listData) {
		this.listData = listData;
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

	public int getTotalRequestHcdv() {
		return totalRequestHcdv;
	}

	public void setTotalRequestHcdv(int totalRequestHcdv) {
		this.totalRequestHcdv = totalRequestHcdv;
	}

	public int getTotalFulfillHcdv() {
		return totalFulfillHcdv;
	}

	public void setTotalFulfillHcdv(int totalFulfillHcdv) {
		this.totalFulfillHcdv = totalFulfillHcdv;
	}

	public double getSumTotalMoneyHcdv() {
		return sumTotalMoneyHcdv;
	}

	public void setSumTotalMoneyHcdv(double sumTotalMoneyHcdv) {
		this.sumTotalMoneyHcdv = sumTotalMoneyHcdv;
	}

	
	
	public DataResponseRatting() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
