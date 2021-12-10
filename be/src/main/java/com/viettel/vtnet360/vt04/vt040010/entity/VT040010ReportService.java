package com.viettel.vtnet360.vt04.vt040010.entity;

import java.util.Date;

/**
 * class entity get list of registration service VT040010
 * 
 * @author ThangBT 19/09/2018
 *
 */
public class VT040010ReportService {

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

	public VT040010ReportService() {
		super();
	}

	public VT040010ReportService(String issueServiceId, String serviceName, double timePlan, double timeReal,
			Date startDatePlan, Date endDatePlan, Date startDateReal, Date endDateReal, String unitName,
			String detailUnit, String empName, String empPhone, int fullFillTime, long timeRealBysecond,
			String receiName, String executiveName, int status, String note, int rating, String feedback,
			Date createDate) {
		super();
		this.issueServiceId = issueServiceId;
		this.serviceName = serviceName;
		this.timePlan = timePlan;
		this.timeReal = timeReal;
		this.startDatePlan = startDatePlan;
		this.endDatePlan = endDatePlan;
		this.startDateReal = startDateReal;
		this.endDateReal = endDateReal;
		this.unitName = unitName;
		this.detailUnit = detailUnit;
		this.empName = empName;
		this.empPhone = empPhone;
		this.fullFillTime = fullFillTime;
		this.timeRealBysecond = timeRealBysecond;
		this.receiName = receiName;
		this.executiveName = executiveName;
		this.status = status;
		this.note = note;
		this.rating = rating;
		this.feedback = feedback;
		this.createDate = createDate;
	}

	public long getTimeRealBysecond() {
		return timeRealBysecond;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setTimeRealBysecond(long timeRealBysecond) {
		this.timeRealBysecond = timeRealBysecond;
	}

	public int getFullFillTime() {
		return fullFillTime;
	}

	public void setFullFillTime(int fullFillTime) {
		this.fullFillTime = fullFillTime;
	}

	public String getExecutiveName() {
		return executiveName;
	}

	public void setExecutiveName(String executiveName) {
		this.executiveName = executiveName;
	}

	public String getIssueServiceId() {
		return issueServiceId;
	}

	public void setIssueServiceId(String issueServiceId) {
		this.issueServiceId = issueServiceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public String getReceiName() {
		return receiName;
	}

	public void setReceiName(String receiName) {
		this.receiName = receiName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
}
