package com.viettel.vtnet360.vt04.vt040004.entity;

import java.util.Date;

public class VT040004EntityDataForApprove {

	/** ISSUES_SERVICE_ID.ISSUES_SERVICE_ID */
	private String issuedServiceId;

	/** QLDV_UNIT.UNIT_NAME */
	private String unitName;

	/** QLDV_EMPLOYEE.FULL_NAME */
	private String fullName;

	/** SERVICES.SERVICE_NAME */
	private String nameService;

	/** ISSUES_SERVICE.CREATE_DATE */
	private Date createDate;

	/** ISSUES_SERVICE.STATUS */
	private int status;

	/** ISSUES_SERVICE.NOTE */
	private String note;

	public VT040004EntityDataForApprove() {
		super();
	}

	public VT040004EntityDataForApprove(String issuedServiceId, String unitName, String fullName, String nameService,
			Date createDate, int status, String note) {
		super();
		this.issuedServiceId = issuedServiceId;
		this.unitName = unitName;
		this.fullName = fullName;
		this.nameService = nameService;
		this.createDate = createDate;
		this.status = status;
		this.note = note;
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

}
