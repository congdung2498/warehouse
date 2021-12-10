package com.viettel.vtnet360.vt04.vt040000.entity;

import java.util.Date;

/**
 * Class VT040000EntityDataIssuedService
 * 
 * @author KienHT 27/09/2018
 * 
 */
public class VT040000EntityDataIssuedService {

	/** ISSUES_SERVICE.ISSUES_SERVICE_ID */
	private String issuedServiceId;

	/** ISSUES_SERVICE.ISSUES_USERNAME */
	private String empUserName;

	/** QLDV_UNIT.UNIT_NAME */
	private String unitName;

	/** QLDV_EMPLOYEE.FULL_NAME */
	private String fullName;

	/** SERVICES.SERVICE_NAME */
	private String nameService;

	/** ISSUES_SERVICE.CREATE_DATE */
	private Date createDate;

	/** ISSUES_SERVICE.RATTING */
	private int ratting;

	/** SERVICES.FULL_FILL_TIME */
	private double fullFillTime;

	/** ISSUES_SERVICE.REAL_TIME_TOTAL */
	private double totalHourActual;

	/** ISSUES_SERVICE.STATUS */
	private int status;

	/** ISSUES_SERVICE.NOTE */
	private String note;

	/** ISSUES_SERVICE.TIME_START_PLAN */
	private Date timeStartPlan;

	/** ISSUES_SERVICE.TIME_FINISH_PLAN */
	private Date timeFinishPlan;
	
	/** ISSUES_SERVICE.TIME_RECEIVE */
	private Date timeReceive;
	
	/*IS_SV.POSTPONE_TO_TIME*/
	private Date postponeToTime;
	
	/*IS_SV.TIME_RESUME*/
	private Date timeResume;

	public VT040000EntityDataIssuedService() {
		super();
	}

	public VT040000EntityDataIssuedService(String issuedServiceId, String empUserName, String unitName, String fullName,
			String nameService, Date createDate, int ratting, double fullFillTime, double totalHourActual, int status,
			String note, Date timeStartPlan, Date timeFinishPlan, Date timeReceive) {
		super();
		this.issuedServiceId = issuedServiceId;
		this.empUserName = empUserName;
		this.unitName = unitName;
		this.fullName = fullName;
		this.nameService = nameService;
		this.createDate = createDate;
		this.ratting = ratting;
		this.fullFillTime = fullFillTime;
		this.totalHourActual = totalHourActual;
		this.status = status;
		this.note = note;
		this.timeStartPlan = timeStartPlan;
		this.timeFinishPlan = timeFinishPlan;
		this.timeReceive = timeReceive;
	}

	public String getIssuedServiceId() {
		return issuedServiceId;
	}

	public void setIssuedServiceId(String issuedServiceId) {
		this.issuedServiceId = issuedServiceId;
	}

	public String getEmpUserName() {
		return empUserName;
	}

	public void setEmpUserName(String empUserName) {
		this.empUserName = empUserName;
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

	public String getNameService() {
		return nameService;
	}

	public void setNameService(String nameService) {
		this.nameService = nameService;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getRatting() {
		return ratting;
	}

	public void setRatting(int ratting) {
		this.ratting = ratting;
	}

	public double getFullFillTime() {
		return fullFillTime;
	}

	public void setFullFillTime(double fullFillTime) {
		this.fullFillTime = fullFillTime;
	}

	public double getTotalHourActual() {
		return totalHourActual;
	}

	public void setTotalHourActual(double totalHourActual) {
		this.totalHourActual = totalHourActual;
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

	public Date getTimeReceive() {
		return timeReceive;
	}

	public void setTimeReceive(Date timeReceive) {
		this.timeReceive = timeReceive;
	}

	public Date getPostponeToTime() {
		return postponeToTime;
	}

	public void setPostponeToTime(Date postponeToTime) {
		this.postponeToTime = postponeToTime;
	}

	public Date getTimeResume() {
		return timeResume;
	}

	public void setTimeResume(Date timeResume) {
		this.timeResume = timeResume;
	}
	
}
