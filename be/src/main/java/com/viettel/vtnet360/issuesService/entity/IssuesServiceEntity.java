package com.viettel.vtnet360.issuesService.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

public class IssuesServiceEntity extends BaseEntity {

	/** id of issue service */
	private String issueServiceId;

	/** name of service */
	private String serviceName;

	/** total time plan */
	private double timePlan;

	/** total time real */
	private double timeReal;

	/** start time plan */
	private Date startDatePlan;

	/** end time plan */
	private Date endDatePlan;

	/** start time real */
	private Date startDateReal;

	/** end time real */
	private Date endDateReal;

	/** name of unit */
	private String unitName;

	/** get 3 levels of unit */
	private String detailUnit;

	/** name of employee */
	private String empName;

	/** phone number of employee */
	private String empPhone;

	/** full Fill Time of service */
	private int fullFillTime;

	/** time Real By second */
	private long timeRealBysecond;

	/** name of last updated receiver */
	private String receiName;

	private String executiveName;

	/** status of registration service */
	private int status;

	/** note */
	private String note;

	/** rating */
	private int rating;

	/** feedback */
	private String feedback;

	private Date createDate;

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

	/** ISSUES_SERVICE.RATING_TO_USER */
	private int rattingToUser;

	/** ISSUES_SERVICE.FEEDBACK_TO_USER */
	private String feedBackToUser;

	/** ISSUES_SERVICE.TIME_RECEIVE */
	private Date timeReceive;
	
	private int detailSignVofficeId;
	
	private int syntheticSignVofficeId;

	private int statusSynthetic;

	private int statusDetail;

	private String issuesUsername;

	private Long issueseLocation;

	private String issueseLocationName;

	private String userNameRecei;

	private String issuesServiceId;
	
	private String reasonRefuse;
	
	private String reason;
	
	private String postponeReason;

	private Date postponeToTime;
	
	private String userReason;
	
	private String stationeryName;
	
	private String stationeryID ;
	
	private Date timeResume;

	private String issuesUserFullName;
	private String issuesUserPhoneNumber;
	private String issuesUserEmail;
	private String serviceId;
	private String appoverQltt;
	private Long flagQltt;
	private String appoverLddv;
	private Long flagLddv;
	private String appoverCvp;
	private Long flagCvp;
	private Date timeStartPlan = new Date();
	private Date timeFinishPlan;
	private Long unitId;
	private Long employeeId;
	private String employeeFullName;
	private String userNameUser;
	private Long flag;
	
	private Double realTimeTotal;
	

	public IssuesServiceEntity() {
		super();
	}

	public String getIssuesServiceId() {
		return issuesServiceId;
	}

	public void setIssuesServiceId(String issuesServiceId) {
		this.issuesServiceId = issuesServiceId;
	}

	public String getIssuesUsername() {
		return issuesUsername;
	}

	public void setIssuesUsername(String issuesUsername) {
		this.issuesUsername = issuesUsername;
	}

	public String getIssuesUserFullName() {
		return issuesUserFullName;
	}

	public void setIssuesUserFullName(String issuesUserFullName) {
		this.issuesUserFullName = issuesUserFullName;
	}

	public String getIssuesUserPhoneNumber() {
		return issuesUserPhoneNumber;
	}

	public int getStatusSynthetic() {
		return statusSynthetic;
	}

	public void setStatusSynthetic(int statusSynthetic) {
		this.statusSynthetic = statusSynthetic;
	}

	public int getStatusDetail() {
		return statusDetail;
	}

	public void setStatusDetail(int statusDetail) {
		this.statusDetail = statusDetail;
	}

	public void setIssuesUserPhoneNumber(String issuesUserPhoneNumber) {
		this.issuesUserPhoneNumber = issuesUserPhoneNumber;
	}

	public String getIssuesUserEmail() {
		return issuesUserEmail;
	}

	public void setIssuesUserEmail(String issuesUserEmail) {
		this.issuesUserEmail = issuesUserEmail;
	}

	public Long getIssueseLocation() {
		return issueseLocation;
	}

	public void setIssueseLocation(Long issueseLocation) {
		this.issueseLocation = issueseLocation;
	}

	public String getIssueseLocationName() {
		return issueseLocationName;
	}

	public void setIssueseLocationName(String issueseLocationName) {
		this.issueseLocationName = issueseLocationName;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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

	public String getAppoverQltt() {
		return appoverQltt;
	}

	public void setAppoverQltt(String appoverQltt) {
		this.appoverQltt = appoverQltt;
	}

	public Long getFlagQltt() {
		return flagQltt;
	}

	public void setFlagQltt(Long flagQltt) {
		this.flagQltt = flagQltt;
	}

	public String getAppoverLddv() {
		return appoverLddv;
	}

	public void setAppoverLddv(String appoverLddv) {
		this.appoverLddv = appoverLddv;
	}

	public Long getFlagLddv() {
		return flagLddv;
	}

	public void setFlagLddv(Long flagLddv) {
		this.flagLddv = flagLddv;
	}

	public String getAppoverCvp() {
		return appoverCvp;
	}

	public void setAppoverCvp(String appoverCvp) {
		this.appoverCvp = appoverCvp;
	}

	public Long getFlagCvp() {
		return flagCvp;
	}

	public void setFlagCvp(Long flagCvp) {
		this.flagCvp = flagCvp;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeFullName() {
		return employeeFullName;
	}

	public void setEmployeeFullName(String employeeFullName) {
		this.employeeFullName = employeeFullName;
	}

	public String getUserNameUser() {
		return userNameUser;
	}

	public void setUserNameUser(String userNameUser) {
		this.userNameUser = userNameUser;
	}

	public Long getFlag() {
		return flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	public Date getTimeResume() {
		return timeResume;
	}

	public void setTimeResume(Date timeResume) {
		this.timeResume = timeResume;
	}

	public Double getRealTimeTotal() {
		return realTimeTotal;
	}

	public void setRealTimeTotal(Double realTimeTotal) {
		this.realTimeTotal = realTimeTotal;
	}

	public String getIssueServiceId() {
		return issueServiceId;
	}

	public void setIssueServiceId(String issueServiceId) {
		this.issueServiceId = issueServiceId;
	}

	public double getTimePlan() {
		return timePlan;
	}

	public void setTimePlan(double timePlan) {
		this.timePlan = timePlan;
	}

	public double getTimeReal() {
		return timeReal;
	}

	public void setTimeReal(double timeReal) {
		this.timeReal = timeReal;
	}

	public Date getStartDatePlan() {
		return startDatePlan;
	}

	public void setStartDatePlan(Date startDatePlan) {
		this.startDatePlan = startDatePlan;
	}

	public Date getEndDatePlan() {
		return endDatePlan;
	}

	public void setEndDatePlan(Date endDatePlan) {
		this.endDatePlan = endDatePlan;
	}

	public Date getStartDateReal() {
		return startDateReal;
	}

	public void setStartDateReal(Date startDateReal) {
		this.startDateReal = startDateReal;
	}

	public Date getEndDateReal() {
		return endDateReal;
	}

	public void setEndDateReal(Date endDateReal) {
		this.endDateReal = endDateReal;
	}

	public String getDetailUnit() {
		return detailUnit;
	}

	public void setDetailUnit(String detailUnit) {
		this.detailUnit = detailUnit;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public long getTimeRealBysecond() {
		return timeRealBysecond;
	}

	public void setTimeRealBysecond(long timeRealBysecond) {
		this.timeRealBysecond = timeRealBysecond;
	}

	public String getReceiName() {
		return receiName;
	}

	public void setReceiName(String receiName) {
		this.receiName = receiName;
	}

	public String getExecutiveName() {
		return executiveName;
	}

	public void setExecutiveName(String executiveName) {
		this.executiveName = executiveName;
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

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
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

	public String getUserNameRecei() {
		return userNameRecei;
	}

	public void setUserNameRecei(String userNameRecei) {
		this.userNameRecei = userNameRecei;
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

	public Date getTimeReceive() {
		return timeReceive;
	}

	public void setTimeReceive(Date timeReceive) {
		this.timeReceive = timeReceive;
	}

	public int getDetailSignVofficeId() {
		return detailSignVofficeId;
	}

	public void setDetailSignVofficeId(int detailSignVofficeId) {
		this.detailSignVofficeId = detailSignVofficeId;
	}

	public int getSyntheticSignVofficeId() {
		return syntheticSignVofficeId;
	}

	public void setSyntheticSignVofficeId(int syntheticSignVofficeId) {
		this.syntheticSignVofficeId = syntheticSignVofficeId;
	}

	public String getReasonRefuse() {
		return reasonRefuse;
	}

	public void setReasonRefuse(String reasonRefuse) {
		this.reasonRefuse = reasonRefuse;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPostponeReason() {
		return postponeReason;
	}

	public void setPostponeReason(String postponeReason) {
		this.postponeReason = postponeReason;
	}

	public Date getPostponeToTime() {
		return postponeToTime;
	}

	public void setPostponeToTime(Date postponeToTime) {
		this.postponeToTime = postponeToTime;
	}

	public String getUserReason() {
		return userReason;
	}

	public void setUserReason(String userReason) {
		this.userReason = userReason;
	}

	public String getStationeryID() {
		return stationeryID;
	}

	public void setStationeryID(String stationeryID) {
		this.stationeryID = stationeryID;
	}

}
