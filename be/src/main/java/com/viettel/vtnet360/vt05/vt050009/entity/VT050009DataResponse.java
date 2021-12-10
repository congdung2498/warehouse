package com.viettel.vtnet360.vt05.vt050009.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.vt05.vt050004.entity.VT050004DataResponse;

/**
 * @author DuyNK
 *
 */
public class VT050009DataResponse {

	/** QLDV_EMPLOYEE.FULL_NAME of ISSUES_STATIONERY_APPROVED.HCDV_USERNAME */
	private String fullName;

	/** ISSUES_STATIONERY_APPROVED.CREATE_DATE */
	private Date dateRequest;

	/** ISSUES_STATIONERY_APPROVED.MESSAGE */
	private String message;

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private String status;
	
	/** list ISSUES_STATIONERY_ITEMS */
	private List<VT050004DataResponse> listData;

	public VT050009DataResponse() {

	}

	public VT050009DataResponse(String fullName, Date dateRequest, String message, String status,
			List<VT050004DataResponse> listData) {
		super();
		this.fullName = fullName;
		this.dateRequest = dateRequest;
		this.message = message;
		this.status = status;
		this.listData = listData;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<VT050004DataResponse> getListData() {
		return listData;
	}

	public void setListData(List<VT050004DataResponse> listData) {
		this.listData = listData;
	}
}
