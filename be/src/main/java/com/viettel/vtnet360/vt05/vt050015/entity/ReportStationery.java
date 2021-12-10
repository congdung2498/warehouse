package com.viettel.vtnet360.vt05.vt050015.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

public class ReportStationery extends BaseEntity {
	private String rpStationeryId;
	private int unitId;
	private String unitName;
	private String placeId;
	private String placeName;
	private String userName;
	private String fullName;
	private String employeePhone;
	private int status;
	private String note;
	private int ratting;
	private String feedback;
	private String lastUser;
	private List<String> listUnitId;
	private List<Integer> listStatus;
	private Date startDate;
	private Date endDate;
	private String dateFrom;
	private String dateTo;
	private String threeLevelUnit;
	private String message;
	private int totalRequest;
	private int totalFulFill;
	private String acceptUserName;
	private String acceptFullName;
	private String email;
	/** page number get from client use for number of record get from DB */
	private int pageNumber;
	/** page size get from client use for number of record get from DB */
	private int pageSize;
	private Date requestDate;
	private Double totalMoney;
	private Double unitPrice;
	private String stationeryName;
	private String stationeryId;
	private String issuesStationeryId;
	private String issuesStationeryItemId;
	private String feedbackToHcdv;
	private int ratingToUser;
	private String feedbackToVptct;
	private int ratingToVptct;
	private boolean isHCDV;
	private boolean isVPTCT;
	private boolean isAdmin;
	private boolean isQL;
	private String ldUsername;
	private String[] placeIds;
	private String userNameVPTCT;
	
	
	public String getUserNameVPTCT() {
		return userNameVPTCT;
	}
	public void setUserNameVPTCT(String userNameVPTCT) {
		this.userNameVPTCT = userNameVPTCT;
	}
	public String getLdUsername() {
		return ldUsername;
	}
	public void setLdUsername(String ldUsername) {
		this.ldUsername = ldUsername;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public boolean isHCDV() {
		return isHCDV;
	}
	public void setHCDV(boolean isHCDV) {
		this.isHCDV = isHCDV;
	}
	public boolean isVPTCT() {
		return isVPTCT;
	}
	public void setVPTCT(boolean isVPTCT) {
		this.isVPTCT = isVPTCT;
	}
	public boolean isQL() {
		return isQL;
	  }
	  public void setQL(boolean isQL) {
		this.isQL = isQL;
	  }
	  
	public String getFeedbackToHcdv() { return feedbackToHcdv; }
	public void setFeedbackToHcdv(String feedbackToHcdv) { this.feedbackToHcdv = feedbackToHcdv; }

	public int getRatingToUser() { return ratingToUser; }
	public void setRatingToUser(int ratingToUser) { this.ratingToUser = ratingToUser; }

	public String getFeedbackToVptct() { return feedbackToVptct; }
	public void setFeedbackToVptct(String feedbackToVptct) { this.feedbackToVptct = feedbackToVptct; }

	public int getRatingToVptct() { return ratingToVptct; }
	public void setRatingToVptct(int ratingToVptct) { this.ratingToVptct = ratingToVptct; }

	public String getStationeryId() { return stationeryId; }
	public void setStationeryId(String stationeryId) { this.stationeryId = stationeryId; }

	public String getIssuesStationeryId() { return issuesStationeryId; }
	public void setIssuesStationeryId(String issuesStationeryId) { this.issuesStationeryId = issuesStationeryId; }

	public String getIssuesStationeryItemId() { return issuesStationeryItemId; }
	public void setIssuesStationeryItemId(String issuesStationeryItemId) { this.issuesStationeryItemId = issuesStationeryItemId; }

	public String getStationeryName() { return stationeryName; }
	public void setStationeryName(String stationeryName) { this.stationeryName = stationeryName; }

	public Double getUnitPrice() { return unitPrice; }
	public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }

	public Double getTotalMoney() { return totalMoney; }
	public void setTotalMoney(Double totalMoney) { this.totalMoney = totalMoney; }

	public Date getRequestDate() { return requestDate; }
	public void setRequestDate(Date requestDate) { this.requestDate = requestDate; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public int getPageNumber() { return pageNumber; }
	public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }

	public int getPageSize() { return pageSize; }
	public void setPageSize(int pageSize) { this.pageSize = pageSize; }

	public String getAcceptUserName() { return acceptUserName; }
	public void setAcceptUserName(String acceptUserName) { this.acceptUserName = acceptUserName; }

	public String getAcceptFullName() { return acceptFullName; }
	public void setAcceptFullName(String acceptFullName) { this.acceptFullName = acceptFullName; }

	public String getRpStationeryId() { return rpStationeryId; }
	public void setRpStationeryId(String rpStationeryId) { this.rpStationeryId = rpStationeryId; }

	public String getPlaceId() { return placeId; }
	public void setPlaceId(String placeId) { this.placeId = placeId; }

	public int getUnitId() { return unitId; }
	public void setUnitId(int unitId) { this.unitId = unitId; }

	public String getUnitName() { return unitName; }
	public void setUnitName(String unitName) { this.unitName = unitName; }

	public String getPlaceName() { return placeName; }
	public void setPlaceName(String placeName) { this.placeName = placeName; }

	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }

	public String getFullName() { return fullName; }
	public void setFullName(String fullName) { this.fullName = fullName; }

	public String getEmployeePhone() { return employeePhone; }
	public void setEmployeePhone(String employeePhone) { this.employeePhone = employeePhone; }

	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }

	public String getNote() { return note; }
	public void setNote(String note) { this.note = note; }

	public int getRatting() { return ratting; }
	public void setRatting(int ratting) { this.ratting = ratting; }

	public String getFeedback() { return feedback; }
	public void setFeedback(String feedback) { this.feedback = feedback; }

	public String getLastUser() { return lastUser; }
	public void setLastUser(String lastUser) { this.lastUser = lastUser; }

	public List<String> getListUnitId() { return listUnitId; }
	public void setListUnitId(List<String> listUnitId) { this.listUnitId = listUnitId; }

	public List<Integer> getListStatus() { return listStatus; }
	public void setListStatus(List<Integer> listStatus) { this.listStatus = listStatus; }

	public Date getStartDate() { return startDate; }
	public void setStartDate(Date startDate) { this.startDate = startDate; }

	public Date getEndDate() { return endDate; }
	public void setEndDate(Date endDate) { this.endDate = endDate; }

	public String getDateFrom() { return dateFrom; }
	public void setDateFrom(String dateFrom) { this.dateFrom = dateFrom; }

	public String getDateTo() { return dateTo; }
	public void setDateTo(String dateTo) { this.dateTo = dateTo; }

	public String getThreeLevelUnit() { return threeLevelUnit; }
	public void setThreeLevelUnit(String threeLevelUnit) { this.threeLevelUnit = threeLevelUnit; }

	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }

	public int getTotalRequest() { return totalRequest; }
	public void setTotalRequest(int totalRequest) { this.totalRequest = totalRequest; }

	public int getTotalFulFill() { return totalFulFill; }
	public void setTotalFulFill(int totalFulFill) { this.totalFulFill = totalFulFill; }

  public String[] getPlaceIds() { return placeIds; }
  public void setPlaceIds(String[] placeIds) { this.placeIds = placeIds; }
  
  
}
