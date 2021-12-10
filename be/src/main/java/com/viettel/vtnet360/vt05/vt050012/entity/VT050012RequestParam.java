package com.viettel.vtnet360.vt05.vt050012.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */

public class VT050012RequestParam extends BaseEntity {
	/** ISSUES_STATIONERY.EMPLOYEE_USERNAME */
	private String userName;

	/** use for limit search by ISSUES_STATIONERY.REQUEST_DATE */
	private Date dateFrom;

	/** use for limit search by ISSUES_STATIONERY.REQUEST_DATE */
	private Date dateTo;

	/** list ISSUES_STATIONERY.STATUS */
	private List<Integer> listStatus;

	/** use for limit number of record */
	private int pageNumber;

	/** use for limit number of record */
	private int pageSize;
	
	private int placeId;

	public VT050012RequestParam() {

	}

	
	
	
	


	public VT050012RequestParam(String userName, Date dateFrom, Date dateTo, List<Integer> listStatus, int pageNumber,
      int pageSize, int placeId) {
    super();
    this.userName = userName;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.listStatus = listStatus;
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.placeId = placeId;
  }







  public int getPlaceId() {
    return placeId;
  }







  public void setPlaceId(int placeId) {
    this.placeId = placeId;
  }







  public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
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
