package com.viettel.vtnet360.vt05.vt050010.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050010RequestParam extends BaseEntity {

	/**
	 * use for search record waiting VPTCT execute giving out by place
	 * (STATIONERY_STAFF.ISSUE_LOCATION) of ISSUES_STATIONERY_APPROVED.HCDV_USERNAME
	 */
	private int placeID;

	/**
	 * use for search record waiting VPTCT execute giving out by unit
	 * (STATIONERY_STAFF.UNIT_ID) of ISSUES_STATIONERY_APPROVED.HCDV_USERNAME
	 */
	private int unitID;

	/** list status to search */
	private List<Integer> listStatus;

	/** use for limit number of record */
	private int pageNumber;

	/** use for limit number of record */
	private int pageSize;

	private Long 	timeRequest;
	
	private Date   dateRequest;
	
	
	
	public VT050010RequestParam() {

	}

	





public VT050010RequestParam(int placeID, int unitID, List<Integer> listStatus, int pageNumber, int pageSize,
			Long timeRequest, Date dateRequest) {
		super();
		this.placeID = placeID;
		this.unitID = unitID;
		this.listStatus = listStatus;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.timeRequest = timeRequest;
		this.dateRequest = dateRequest;
	}







public Long getTimeRequest() {
		return timeRequest;
	}







	public void setTimeRequest(Long timeRequest) {
		this.timeRequest = timeRequest;
	}







	public Date getDateRequest() {
		return dateRequest;
	}







	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
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

	public List<Integer> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<Integer> listStatus) {
		this.listStatus = listStatus;
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
