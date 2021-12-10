package com.viettel.vtnet360.vt05.vt050017.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050017RequestParam extends BaseEntity {

	/**
	 * ISSUES_STATIONERY_APPROVED.CREATE_DATE date that hcdv send request to manager
	 * approve
	 */
	private Date dateRequest;

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private int status;

	/** page number to limit records response */
	private int pageNumber;

	/** page size to limit records response */
	private int pageSize;

	/** ISSUES_STATIONERY_APPROVED.HCDV_USERNAME get when user hcdv logged on */
	private String hcdvUserName;

	/** role of user logged on is admin or not */
	private boolean roleAdmin;

	public VT050017RequestParam() {

	}

	public VT050017RequestParam(Date dateRequest, int status, int pageNumber, int pageSize, String hcdvUserName,
			boolean roleAdmin) {
		super();
		this.dateRequest = dateRequest;
		this.status = status;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.hcdvUserName = hcdvUserName;
		this.roleAdmin = roleAdmin;
	}

	public Date getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getHcdvUserName() {
		return hcdvUserName;
	}

	public void setHcdvUserName(String hcdvUserName) {
		this.hcdvUserName = hcdvUserName;
	}

	public boolean isRoleAdmin() {
		return roleAdmin;
	}

	public void setRoleAdmin(boolean roleAdmin) {
		this.roleAdmin = roleAdmin;
	}
}
