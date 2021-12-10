package com.viettel.vtnet360.vt05.vt050004.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050004InfoToSearchIssuesStationeryItems extends BaseEntity {

	/** ISSUES_STATIONERY.EMPLOYEE_USERNAME */
	private String employeeUserName;

	/** userName of this aacount logged on (HCDV) */
	private String managerUserName;

	/** ISSUES_STATIONERY.REQUEST_DATE */
	private Date requestDate;

	/** page number to limit records response */
	private int pageNumber;

	/** page size to limit records response */
	private int pageSize;

	/** status condition to search issues stationery items */
	private int status;

	/** role of user logged on is admin or not */
	private boolean roleAdmin;
	
	private int placeId;
	
	public VT050004InfoToSearchIssuesStationeryItems() {

	}

	

	



	public VT050004InfoToSearchIssuesStationeryItems(String employeeUserName, String managerUserName, Date requestDate,
      int pageNumber, int pageSize, int status, boolean roleAdmin, int placeId) {
    super();
    this.employeeUserName = employeeUserName;
    this.managerUserName = managerUserName;
    this.requestDate = requestDate;
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.status = status;
    this.roleAdmin = roleAdmin;
    this.placeId = placeId;
  }







 


	public int getPlaceId() {
    return placeId;
  }







  public void setPlaceId(int placeId) {
    this.placeId = placeId;
  }







  public String getEmployeeUserName() {
		return employeeUserName;
	}

	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
	}

	public String getManagerUserName() {
		return managerUserName;
	}

	public void setManagerUserName(String managerUserName) {
		this.managerUserName = managerUserName;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
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
}
