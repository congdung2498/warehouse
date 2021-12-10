package com.viettel.vtnet360.vt05.vt050008.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT050008RequestParamToSearch {

	/** ISSUES_STATIONERY_APPROVED.VPTCT_USERNAME */
	private String vptctUserName;

	/**
	 * ISSUES_STATIONERY_APPROVED.VPTCT_CREATE_DATE (Date that VPTCT send request to CVP)
	 */
	private Date requestDate;

	/** ISSUES_STATIONERY_APPROVED.VPTCT_MESSAGE */
	private String message;

	/** use for limit number of record response */
	private int pageNumber;

	/** use for limit number of record response */
	private int pageSize;

	/** ISSUES_STATIONERY_APPROVED.CVP_USERNAME */
	private String cvpUserName;

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private int status;

	/** role of user logged on is admin or not */
	private boolean roleAdmin;

	/** M_SYSTEM_CODE.MASTER_CLASS to calcul limit of 1 unit */
	private String mClass;

	/** M_SYSTEM_CODE.CODE_VALUE to calcul limit of 1 unit */
	private String mCode;

	public VT050008RequestParamToSearch() {

	}

	public VT050008RequestParamToSearch(String vptctUserName, Date requestDate, String message, int pageNumber,
			int pageSize, String cvpUserName, int status, boolean roleAdmin, String mClass, String mCode) {
		super();
		this.vptctUserName = vptctUserName;
		this.requestDate = requestDate;
		this.message = message;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.cvpUserName = cvpUserName;
		this.status = status;
		this.roleAdmin = roleAdmin;
		this.mClass = mClass;
		this.mCode = mCode;
	}

	public String getVptctUserName() {
		return vptctUserName;
	}

	public void setVptctUserName(String vptctUserName) {
		this.vptctUserName = vptctUserName;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getCvpUserName() {
		return cvpUserName;
	}

	public void setCvpUserName(String cvpUserName) {
		this.cvpUserName = cvpUserName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isRoleAdmin() {
		return roleAdmin;
	}

	public void setRoleAdmin(boolean roleAdmin) {
		this.roleAdmin = roleAdmin;
	}

	public String getmClass() {
		return mClass;
	}

	public void setmClass(String mClass) {
		this.mClass = mClass;
	}

	public String getmCode() {
		return mCode;
	}

	public void setmCode(String mCode) {
		this.mCode = mCode;
	}
}
