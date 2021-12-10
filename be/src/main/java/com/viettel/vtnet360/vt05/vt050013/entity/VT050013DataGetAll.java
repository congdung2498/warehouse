package com.viettel.vtnet360.vt05.vt050013.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.stationery.service.ItemModel;

public class VT050013DataGetAll {

	private String userName;
	private String phoneNumber;
	private String hcdvUserName;
	private String tptctUserName;
	private Date   createDate;
	private Date vptctPostponeToTime;
	private int    status;
	private String hcdvMessage;
	private String feedbackToVptct;
	private String feedbackToHcdv;
	private int    ratingToUser;
	private int    ratingToVptct;
	private double countTotalMoneyRequest;
	private double countTotalMoneyFullfill;
	private int    countTotalRequest;
	private int    countTotalFullfill;
	private String hcdvReasonReject;
	private String vptctReason;
	private String vptctReasonReject;
	private List<ItemModel> listRequest;
	private String ldReasonReject;
	private String fullName;
	private Date   dateRequest;
	private String stationeryName;
	private int    quantity;
	private double price;
	private double totalMoney;
	private String unitName;
	
	
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

	public String getLdReasonReject() {
		return ldReasonReject;
	}

	public void setLdReasonReject(String ldReasonReject) {
		this.ldReasonReject = ldReasonReject;
	}

	public Date getVptctPostponeToTime() {
		return vptctPostponeToTime;
	}

	public void setVptctPostponeToTime(Date vptctPostponeToTime) {
		this.vptctPostponeToTime = vptctPostponeToTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHcdvReasonReject() {
		return hcdvReasonReject;
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

	public void setHcdvReasonReject(String hcdvReasonReject) {
		this.hcdvReasonReject = hcdvReasonReject;
	}

	public String getHcdvUserName() {
		return hcdvUserName;
	}

	public void setHcdvUserName(String hcdvUserName) {
		this.hcdvUserName = hcdvUserName;
	}

	public String getTptctUserName() {
		return tptctUserName;
	}

	public void setTptctUserName(String tptctUserName) {
		this.tptctUserName = tptctUserName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHcdvMessage() {
		return hcdvMessage;
	}

	public void setHcdvMessage(String hcdvMessage) {
		this.hcdvMessage = hcdvMessage;
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

	public double getCountTotalMoneyRequest() {
		return countTotalMoneyRequest;
	}

	public void setCountTotalMoneyRequest(double countTotalMoneyRequest) {
		this.countTotalMoneyRequest = countTotalMoneyRequest;
	}

	public double getCountTotalMoneyFullfill() {
		return countTotalMoneyFullfill;
	}

	public void setCountTotalMoneyFullfill(double countTotalMoneyFullfill) {
		this.countTotalMoneyFullfill = countTotalMoneyFullfill;
	}

	public int getCountTotalRequest() {
		return countTotalRequest;
	}

	public void setCountTotalRequest(int countTotalRequest) {
		this.countTotalRequest = countTotalRequest;
	}

	public int getCountTotalFullfill() {
		return countTotalFullfill;
	}

	public void setCountTotalFullfill(int countTotalFullfill) {
		this.countTotalFullfill = countTotalFullfill;
	}

  public List<ItemModel> getListRequest() {
    return listRequest;
  }

  public void setListRequest(List<ItemModel> listRequest) {
    this.listRequest = listRequest;
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

  public String getStationeryName() {
    return stationeryName;
  }

  public void setStationeryName(String stationeryName) {
    this.stationeryName = stationeryName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public double getTotalMoney() {
    return totalMoney;
  }

  public void setTotalMoney(double totalMoney) {
    this.totalMoney = totalMoney;
  }

@Override
public String toString() {
	return "VT050013DataGetAll [userName=" + userName + ", hcdvUserName=" + hcdvUserName + ", tptctUserName="
			+ tptctUserName + ", createDate=" + createDate + ", status=" + status + ", hcdvMessage=" + hcdvMessage
			+ ", feedbackToVptct=" + feedbackToVptct + ", feedbackToHcdv=" + feedbackToHcdv + ", ratingToUser="
			+ ratingToUser + ", ratingToVptct=" + ratingToVptct + ", countTotalMoneyRequest=" + countTotalMoneyRequest
			+ ", countTotalMoneyFullfill=" + countTotalMoneyFullfill + ", countTotalRequest=" + countTotalRequest
			+ ", countTotalFullfill=" + countTotalFullfill + ", hcdvReasonReject=" + hcdvReasonReject + ", vptctReason="
			+ vptctReason + ", vptctReasonReject=" + vptctReasonReject + ", listRequest=" + listRequest + ", fullName="
			+ fullName + ", dateRequest=" + dateRequest + ", stationeryName=" + stationeryName + ", quantity="
			+ quantity + ", price=" + price + ", totalMoney=" + totalMoney + "]";
}
  
  
}
