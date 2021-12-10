package com.viettel.vtnet360.vt04.vt040004.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

public class VT040004EntityRq extends BaseEntity {

	/** QLDV_UNIT.UNIT_ID */
	private int unitId;

	/** ISSUES_SERVICE.ISSUES_USERNAME name Of Requester */
	private String userNameOfRequester;

	/** ISSUES_SERVICE.PHONE_NUMBER phone Of Requester */
	private String phoneOfRequester;

	/** start Time */
	private Date startTime;

	/** end Time */
	private Date endTime;

	/** SERVICES.SERVICE_ID id Service */
	private String idService;

	/** SERVICES.STATUS status */
	private int[] status;

	/** Size Record 1 page **/
	private int pageSize;

	/** number page **/
	private int pageNumber;

	public VT040004EntityRq() {
		super();
	}

	public VT040004EntityRq(int unitId, String userNameOfRequester, String phoneOfRequester, Date startTime,
			Date endTime, String idService, int[] status, int pageSize, int pageNumber) {
		super();
		this.unitId = unitId;
		this.userNameOfRequester = userNameOfRequester;
		this.phoneOfRequester = phoneOfRequester;
		this.startTime = startTime;
		this.endTime = endTime;
		this.idService = idService;
		this.status = status;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getUserNameOfRequester() {
		return userNameOfRequester;
	}

	public void setUserNameOfRequester(String userNameOfRequester) {
		this.userNameOfRequester = userNameOfRequester;
	}

	public String getPhoneOfRequester() {
		return phoneOfRequester;
	}

	public void setPhoneOfRequester(String phoneOfRequester) {
		this.phoneOfRequester = phoneOfRequester;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIdService() {
		return idService;
	}

	public void setIdService(String idService) {
		this.idService = idService;
	}

	public int[] getStatus() {
		return status;
	}

	public void setStatus(int[] status) {
		this.status = status;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
