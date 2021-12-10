package com.viettel.vtnet360.vt05.vt050007.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT050007RequestParamToFindData {

	/**
	 * use for search record waiting chief of staff approve by place
	 * (STATIONERY_STAFF.ISSUE_LOCATION) of ISSUES_STATIONERY_APPROVED.HCDV_USERNAME
	 */
	private int placeID;

	/**
	 * use for search record waiting chief of staff approve by unit
	 * (STATIONERY_STAFF.UNIT_ID) of ISSUES_STATIONERY_APPROVED.HCDV_USERNAME
	 */
	private int unitID;

	/** ISSUES_STATIONERY_APPROVED.LD_APPROVED_DATE */
	private Date dateApprove;

	/** ISSUES_STATIONERY_APPROVED.HCDV_MESSAGE */
	private String message;

	/** use for limit number of record */
	private int pageNumber;

	/** use for limit number of record */
	private int pageSize;

	public VT050007RequestParamToFindData() {
	
	}
	
	public VT050007RequestParamToFindData(int placeID, int unitID, Date dateApprove, String message, int pageNumber,
			int pageSize) {
		super();
		this.placeID = placeID;
		this.unitID = unitID;
		this.dateApprove = dateApprove;
		this.message = message;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	public int getUnitID() {
		return unitID;
	}

	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}

	public Date getDateApprove() {
		return dateApprove;
	}

	public void setDateApprove(Date dateApprove) {
		this.dateApprove = dateApprove;
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
}
