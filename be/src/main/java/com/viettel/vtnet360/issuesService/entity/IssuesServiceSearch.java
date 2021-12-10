package com.viettel.vtnet360.issuesService.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

public class IssuesServiceSearch extends BaseEntity {
  
  /** user name of login user */
  private String loginUserName;

  /** role of login user */
  private String loginRole;

  /** id of place */
  private String placeId;

  /** name of place */
  private String placeName;

  /** list of unit id */
  private String[] listUnitId;

  /** list of status */
  private String[] listStatus;

  /** id of service */
  private String serviceId;

  /** name of service */
  private String serviceName;

  /** user name of receiver */
  private String receiverUserName;

  /** name of receiver */
  private String receiverName;

  /** start date search */
  private Date startDate;

  /** end date search */
  private Date endDate;

  /** select from which row */
  private int startRow;

  /** number of row to select */
  private int rowSize;

  /** flag if user is manager */
  private int manager;
  
  /** name of QLTT */
  private String nameQLTT;

  /** name of QLTT */
  private String userNameQLTT;

  /** flagQLTT */
  private int flagQLTT;

  /** name of LDDV */
  private String nameLDDV;

  /** name of LDDV */
  private String userNameLDDV;

  /** flagLDVV */
  private int flagLDVV;

  /** name of CVP */
  private String nameCVP;

  /** name of CVP */
  private String userNameCVP;

  /** flagCVP */
  private int flagCVP;
  
  /** QLDV_EMPLOYEE.FULL_NAME */
  private String fullName;

  /** Phone Number emp */
  private String phoneNumber;

  /** email emp */
  private String email;
  
  /** isStationary emp */
  private boolean isStationary;
  
  private String[] listStatusDetail;
  
  private String[] listStatusSynthetic;
  
  private Long unitId;
  
  private int isStatusDetail;

  private int isStatusSynthetic;

  private int[] status;
  private Long isAdmin;
  private Long statusRequestApproved;
  private String userNameUser;
  private Long issueseLocation;
  private String issuesUsername;
  private String fromDate;
  private String toDate;
  private Date fromDateConvert;
  private Date toDateConvert;
  private List<IssuesServiceEntity> listIdIssuesService;

  public IssuesServiceSearch() {
    super();
   
  }

  public Long getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(Long isAdmin) {
    this.isAdmin = isAdmin;
  }

  public Long getStatusRequestApproved() {
    return statusRequestApproved;
  }

  public void setStatusRequestApproved(Long statusRequestApproved) {
    this.statusRequestApproved = statusRequestApproved;
  }

  public String getUserNameUser() {
    return userNameUser;
  }

  public void setUserNameUser(String userNameUser) {
    this.userNameUser = userNameUser;
  }

  public Long getUnitId() {
    return unitId;
  }

  public void setUnitId(Long unitId) {
    this.unitId = unitId;
  }

  public Long getIssueseLocation() {
    return issueseLocation;
  }

  public void setIssueseLocation(Long issueseLocation) {
    this.issueseLocation = issueseLocation;
  }

  public String getIssuesUsername() {
    return issuesUsername;
  }

  public void setIssuesUsername(String issuesUsername) {
    this.issuesUsername = issuesUsername;
  }

  public String getFromDate() {
    return fromDate;
  }

  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  public String getToDate() {
    return toDate;
  }

  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  public Date getFromDateConvert() {
    return fromDateConvert;
  }

  public void setFromDateConvert(Date fromDateConvert) {
    this.fromDateConvert = fromDateConvert;
  }

  public Date getToDateConvert() {
    return toDateConvert;
  }

  public void setToDateConvert(Date toDateConvert) {
    this.toDateConvert = toDateConvert;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public List<IssuesServiceEntity> getListIdIssuesService() {
    return listIdIssuesService;
  }

  public void setListIdIssuesService(List<IssuesServiceEntity> listIdIssuesService) {
    this.listIdIssuesService = listIdIssuesService;
  }

  public String getLoginUserName() {
    return loginUserName;
  }

  public void setLoginUserName(String loginUserName) {
    this.loginUserName = loginUserName;
  }

  public String getLoginRole() {
    return loginRole;
  }

  public void setLoginRole(String loginRole) {
    this.loginRole = loginRole;
  }

  public String getPlaceId() {
    return placeId;
  }

  public void setPlaceId(String placeId) {
    this.placeId = placeId;
  }

  public String getPlaceName() {
    return placeName;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  public String[] getListUnitId() {
    return listUnitId;
  }

  public void setListUnitId(String[] listUnitId) {
    this.listUnitId = listUnitId;
  }

  public String[] getListStatus() {
    return listStatus;
  }

  public void setListStatus(String[] listStatus) {
    this.listStatus = listStatus;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getReceiverUserName() {
    return receiverUserName;
  }

  public void setReceiverUserName(String receiverUserName) {
    this.receiverUserName = receiverUserName;
  }

  public String getReceiverName() {
    return receiverName;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public int getStartRow() {
    return startRow;
  }

  public void setStartRow(int startRow) {
    this.startRow = startRow;
  }

  public int getRowSize() {
    return rowSize;
  }

  public void setRowSize(int rowSize) {
    this.rowSize = rowSize;
  }

  public int getManager() {
    return manager;
  }

  public void setManager(int manager) {
    this.manager = manager;
  }

  public int[] getStatus() {
    return status;
  }

  public void setStatus(int[] status) {
    this.status = status;
  }

  public String getNameQLTT() {
    return nameQLTT;
  }

  public void setNameQLTT(String nameQLTT) {
    this.nameQLTT = nameQLTT;
  }

  public String getUserNameQLTT() {
    return userNameQLTT;
  }

  public void setUserNameQLTT(String userNameQLTT) {
    this.userNameQLTT = userNameQLTT;
  }

  public int getFlagQLTT() {
    return flagQLTT;
  }

  public void setFlagQLTT(int flagQLTT) {
    this.flagQLTT = flagQLTT;
  }

  public String getNameLDDV() {
    return nameLDDV;
  }

  public void setNameLDDV(String nameLDDV) {
    this.nameLDDV = nameLDDV;
  }

  public String getUserNameLDDV() {
    return userNameLDDV;
  }

  public void setUserNameLDDV(String userNameLDDV) {
    this.userNameLDDV = userNameLDDV;
  }

  public int getFlagLDVV() {
    return flagLDVV;
  }

  public void setFlagLDVV(int flagLDVV) {
    this.flagLDVV = flagLDVV;
  }

  public String getNameCVP() {
    return nameCVP;
  }

  public void setNameCVP(String nameCVP) {
    this.nameCVP = nameCVP;
  }

  public String getUserNameCVP() {
    return userNameCVP;
  }

  public void setUserNameCVP(String userNameCVP) {
    this.userNameCVP = userNameCVP;
  }

  public int getFlagCVP() {
    return flagCVP;
  }

  public void setFlagCVP(int flagCVP) {
    this.flagCVP = flagCVP;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean getIsStationary() {
    return isStationary;
  }

  public void setIsStationary(boolean isStationary) {
    this.isStationary = isStationary;
  }

public String[] getListStatusDetail() {
	return listStatusDetail;
}

public void setListStatusDetail(String[] listStatusDetail) {
	this.listStatusDetail = listStatusDetail;
}

public String[] getListStatusSynthetic() {
	return listStatusSynthetic;
}

public void setListStatusSynthetic(String[] listStatusSynthetic) {
	this.listStatusSynthetic = listStatusSynthetic;
}

public void setStationary(boolean isStationary) {
	this.isStationary = isStationary;
}

public int getIsStatusDetail() {
	return isStatusDetail;
}

public void setIsStatusDetail(int isStatusDetail) {
	this.isStatusDetail = isStatusDetail;
}

public int getIsStatusSynthetic() {
	return isStatusSynthetic;
}

public void setIsStatusSynthetic(int isStatusSynthetic) {
	this.isStatusSynthetic = isStatusSynthetic;
}


 

}
