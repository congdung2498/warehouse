package com.viettel.vtnet360.vt05.vt050018.entity;

import java.util.Date;
import java.util.List;

/**
 * @author DuyNK
 *
 */
public class VT050018DataResponse {
	
	/** fullName of ISSUES_STATIONERY_APPROVED.HCDV_USERNAME */
	private String hcdvFullName;
	
	private String feedbackToVptct;
	
	private String feedbackToHcdv;
	
	private int    ratingToUser;
	
	private int    ratingToVptct;
	
	/** ISSUES_STATIONERY_APPROVED.CREATE_DATE */
	private Date dateRequest;

	/** ISSUES_STATIONERY_APPROVED.HCDV_MESSAGE */
	private String message;

	/** Total stationery of request */
	private int totalRequest;

	/** Total stationery of request that VPTCT give out */
	private int totalFulfill;
	
	/** total money of request */
	private double totalMoney;

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private int status;
	
	private double totalMoneyFullfill;
	
	private List<VT050018DataDetail> listData;
	
	public VT050018DataResponse() {

	}


	public VT050018DataResponse(String hcdvFullName, String feedbackToVptct, String feedbackToHcdv, int ratingToUser,
			int ratingToVptct, Date dateRequest, String message, int totalRequest, int totalFulfill, double totalMoney,
			int status, double totalMoneyFullfill, List<VT050018DataDetail> listData) {
		super();
		this.hcdvFullName = hcdvFullName;
		this.feedbackToVptct = feedbackToVptct;
		this.feedbackToHcdv = feedbackToHcdv;
		this.ratingToUser = ratingToUser;
		this.ratingToVptct = ratingToVptct;
		this.dateRequest = dateRequest;
		this.message = message;
		this.totalRequest = totalRequest;
		this.totalFulfill = totalFulfill;
		this.totalMoney = totalMoney;
		this.status = status;
		this.totalMoneyFullfill = totalMoneyFullfill;
		this.listData = listData;
	}



	public String getFeedbackToVptct() {
		return feedbackToVptct;
	}



	public void setFeedbackToVptct(String feedbackToVptct) {
		this.feedbackToVptct = feedbackToVptct;
	}



	public String getFeedbackToHcdv() {
		return feedbackToHcdv;
	}



	public void setFeedbackToHcdv(String feedbackToHcdv) {
		this.feedbackToHcdv = feedbackToHcdv;
	}



	public int getRatingToUser() {
		return ratingToUser;
	}



	public void setRatingToUser(int ratingToUser) {
		this.ratingToUser = ratingToUser;
	}



	public int getRatingToVptct() {
		return ratingToVptct;
	}



	public void setRatingToVptct(int ratingToVptct) {
		this.ratingToVptct = ratingToVptct;
	}



	public double getTotalMoneyFullfill() {
		return totalMoneyFullfill;
	}

	public void setTotalMoneyFullfill(double totalMoneyFullfill) {
		this.totalMoneyFullfill = totalMoneyFullfill;
	}

	public String getHcdvFullName() {
		return hcdvFullName;
	}

	public void setHcdvFullName(String hcdvFullName) {
		this.hcdvFullName = hcdvFullName;
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

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<VT050018DataDetail> getListData() {
		return listData;
	}

	public void setListData(List<VT050018DataDetail> listData) {
		this.listData = listData;
	}
}