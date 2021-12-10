package com.viettel.vtnet360.vt04.vt040000.entity;

import java.util.Date;

/**
 * Class VT040000EntityDetailIssuedSv
 * 
 * @author KienHT 27/09/2018
 * 
 */
public class VT040000EntityDetailIssuedSv {

	/** ISSUES_SERVICE.ISSUES_SERVICE_ID */
	private String issuedServiceId;

	/** ISSUES_SERVICE.ISSUES_USERNAME */
	private String issuesUserName;

	/** QLDV_UNIT.UNIT_NAME */
	private String unitName;
	
	/** QLDV_UNIT.UNIT_NAME */
	private String nameUnit;

	/** QLDV_EMPLOYEE.FULL_NAME */
	private String fullName;

	/** Phone Number emp */
	private String phoneNumber;

	/** email emp */
	private String email;

	/** name of Place */
	private String namePlace;
	
  /** ISSUES_SERVICE.SERVICE_ID */
  private String idService;
  
	/** SERVICES.SERVICE_NAME */
	private String nameService;

	/** SERVICES.FULL_FILL_TIME */
	private int fullFillTime;

	/** ISSUES_SERVICE.NOTE */
	private String note;

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

	/** statusRecord */
	private int status;

	/** statusRecord */
	private int rating;

	/** ISSUES_SERVICE.FEEDBACK feedback */
	private String feedback;
	
  /** ISSUES_SERVICE.RATING_TO_USER */
  private int rattingToUser;

  /** ISSUES_SERVICE.FEEDBACK_TO_USER */
  private String feedBackToUser;

	/** ISSUES_SERVICE.FULL_FILL_TIME totalHourPlan */
	private double totalHourPlan;

	/** ISSUES_SERVICE.REAL_TIME_TOTAL totalHourActual */
	private double totalHourActual;


	private double realTimeTotal;

	/** ISSUES_SERVICE.REASON_REFUSE */
	private String reasonRefuse;

	/** ISSUES_SERVICE.POSTPONE_REASON */
	private String postponeReason;

	/** ISSUES_SERVICE. REASON */
	private String reason;

	/** ISSUES_SERVICE.POSTPONE_TO_TIME */
	private Date postponeToTime;

	private String userReason;
	
	/** SERVICES.REQUIRE_SIGN */
	private int requireSign;
	
	/** EMPLOYEE.ROLE */
	private String empRole;
	
	/** SERVICES.TIME_START_PLAN */
	private Date timeStartPlan;
	
	/** SERVICES.TIME_FINISH_PLAN */
	private Date timeFinishPlan;

	/** SERVICES.TIME_RESUME */
	private Date timeResume;

	/** STATIONERY */
	private VT040000EntityDataSt[] statonery;

	public VT040000EntityDetailIssuedSv() {
		super();
	}

	public VT040000EntityDetailIssuedSv(String issuedServiceId, String unitName, String fullName, String phoneNumber,
			String email, String namePlace, String nameService, int fullFillTime, String note, String nameQLTT,
			String userNameQLTT, int flagQLTT, String nameLDDV, String userNameLDDV, int flagLDVV, String nameCVP,
			String userNameCVP, int flagCVP, int status, int rating, String feedback, double totalHourPlan,
			double totalHourActual, String reasonRefuse, String postponeReason, String reason, Date postponeToTime,
			String userReason, VT040000EntityDataSt[] statonery, int requireSign) {
		super();
		this.issuedServiceId = issuedServiceId;
		this.unitName = unitName;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.namePlace = namePlace;
		this.nameService = nameService;
		this.fullFillTime = fullFillTime;
		this.note = note;
		this.nameQLTT = nameQLTT;
		this.userNameQLTT = userNameQLTT;
		this.flagQLTT = flagQLTT;
		this.nameLDDV = nameLDDV;
		this.userNameLDDV = userNameLDDV;
		this.flagLDVV = flagLDVV;
		this.nameCVP = nameCVP;
		this.userNameCVP = userNameCVP;
		this.flagCVP = flagCVP;
		this.status = status;
		this.rating = rating;
		this.feedback = feedback;
		this.totalHourPlan = totalHourPlan;
		this.totalHourActual = totalHourActual;
		this.reasonRefuse = reasonRefuse;
		this.postponeReason = postponeReason;
		this.reason = reason;
		this.postponeToTime = postponeToTime;
		this.userReason = userReason;
		this.statonery = statonery;
		this.requireSign = requireSign;
	}

	public String getUserReason() {
		return userReason;
	}

	public void setUserReason(String userReason) {
		this.userReason = userReason;
	}

	public String getUserNameQLTT() {
		return userNameQLTT;
	}

	public void setUserNameQLTT(String userNameQLTT) {
		this.userNameQLTT = userNameQLTT;
	}

	public double getRealTimeTotal() {
		return realTimeTotal;
	}

	public void setRealTimeTotal(double realTimeTotal) {
		this.realTimeTotal = realTimeTotal;
	}

	public String getUserNameLDDV() {
		return userNameLDDV;
	}

	public void setUserNameLDDV(String userNameLDDV) {
		this.userNameLDDV = userNameLDDV;
	}

	public String getUserNameCVP() {
		return userNameCVP;
	}

	public void setUserNameCVP(String userNameCVP) {
		this.userNameCVP = userNameCVP;
	}

	public String getIssuedServiceId() {
		return issuedServiceId;
	}

	public void setIssuedServiceId(String issuedServiceId) {
		this.issuedServiceId = issuedServiceId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public String getNamePlace() {
		return namePlace;
	}

	public void setNamePlace(String namePlace) {
		this.namePlace = namePlace;
	}

	public String getNameService() {
		return nameService;
	}

	public void setNameService(String nameService) {
		this.nameService = nameService;
	}

	public String getIdService() {
    return idService;
  }

  public void setIdService(String idService) {
    this.idService = idService;
  }

  public int getFullFillTime() {
		return fullFillTime;
	}

	public void setFullFillTime(int fullFillTime) {
		this.fullFillTime = fullFillTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNameQLTT() {
		return nameQLTT;
	}

	public void setNameQLTT(String nameQLTT) {
		this.nameQLTT = nameQLTT;
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

	public int getFlagCVP() {
		return flagCVP;
	}

	public void setFlagCVP(int flagCVP) {
		this.flagCVP = flagCVP;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public double getTotalHourPlan() {
		return totalHourPlan;
	}

	public void setTotalHourPlan(double totalHourPlan) {
		this.totalHourPlan = totalHourPlan;
	}

	public double getTotalHourActual() {
		return totalHourActual;
	}

	public void setTotalHourActual(double totalHourActual) {
		this.totalHourActual = totalHourActual;
	}

	public String getReasonRefuse() {
		return reasonRefuse;
	}

	public void setReasonRefuse(String reasonRefuse) {
		this.reasonRefuse = reasonRefuse;
	}

	public String getPostponeReason() {
		return postponeReason;
	}

	public void setPostponeReason(String postponeReason) {
		this.postponeReason = postponeReason;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getPostponeToTime() {
		return postponeToTime;
	}

	public void setPostponeToTime(Date postponeToTime) {
		this.postponeToTime = postponeToTime;
	}

	public VT040000EntityDataSt[] getStatonery() {
		return statonery;
	}

	public void setStatonery(VT040000EntityDataSt[] statonery) {
		this.statonery = statonery;
	}

	public String getIssuesUserName() {
		return issuesUserName;
	}

	public void setIssuesUserName(String issuesUserName) {
		this.issuesUserName = issuesUserName;
	}

	public int getRattingToUser() {
		return rattingToUser;
	}

	public void setRattingToUser(int rattingToUser) {
		this.rattingToUser = rattingToUser;
	}

	public String getFeedBackToUser() {
		return feedBackToUser;
	}

	public void setFeedBackToUser(String feedBackToUser) {
		this.feedBackToUser = feedBackToUser;
	}

	public int getRequireSign() {
		return requireSign;
	}

	public void setRequireSign(int requireSign) {
		this.requireSign = requireSign;
	}

	public String getEmpRole() {
		return empRole;
	}

	public void setEmpRole(String empRole) {
		this.empRole = empRole;
	}

	public Date getTimeStartPlan() {
		return timeStartPlan;
	}

	public void setTimeStartPlan(Date timeStartPlan) {
		this.timeStartPlan = timeStartPlan;
	}

	public Date getTimeFinishPlan() {
		return timeFinishPlan;
	}

	public void setTimeFinishPlan(Date timeFinishPlan) {
		this.timeFinishPlan = timeFinishPlan;
	}

	public Date getTimeResume() {
		return timeResume;
	}

	public void setTimeResume(Date timeResume) {
		this.timeResume = timeResume;
	}

	public String getNameUnit() {
		return nameUnit;
	}

	public void setNameUnit(String nameUnit) {
		this.nameUnit = nameUnit;
	}

}
