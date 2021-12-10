package com.viettel.vtnet360.vt05.vt050005.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050005RequestParamToSearch extends BaseEntity {

	/** ISSUES_STATIONERY_APPROVED.HCDV_USERNAME */
	private String employeeUserName;

	/** ISSUES_STATIONERY_APPROVED.CREATE_DATE */
	private Date requestDate;

	/** use for limit number of record response */
	private int pageNumber;

	/** use for limit number of record response */
	private int pageSize;

	/** ISSUES_STATIONERY_APPROVED.APPROVED_USERNAME */
	private String approveUserName;

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	
	private List<Integer> listStatus;

	/** role of user logged on is admin or not */
	private boolean roleAdmin;
	
	private boolean isRequest;
	
	private boolean  isRefuse ;
	
	private boolean  isApprove;
	
	public VT050005RequestParamToSearch() {

	}



	public VT050005RequestParamToSearch(String employeeUserName, Date requestDate, int pageNumber, int pageSize,
			String approveUserName, List<Integer> listStatus, boolean roleAdmin, boolean isRequest, boolean isRefuse,
			boolean isApprove) {
		super();
		this.employeeUserName = employeeUserName;
		this.requestDate = requestDate;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.approveUserName = approveUserName;
		this.listStatus = listStatus;
		this.roleAdmin = roleAdmin;
		this.isRequest = isRequest;
		this.isRefuse = isRefuse;
		this.isApprove = isApprove;
	}




	public boolean isRequest() {
		return isRequest;
	}



	public void setRequest(boolean isRequest) {
		this.isRequest = isRequest;
	}



	public boolean isRefuse() {
		return isRefuse;
	}



	public void setRefuse(boolean isRefuse) {
		this.isRefuse = isRefuse;
	}



	public boolean isApprove() {
		return isApprove;
	}



	public void setApprove(boolean isApprove) {
		this.isApprove = isApprove;
	}



	public List<Integer> getListStatus() {
		return listStatus;
	}


	public void setListStatus(List<Integer> listStatus) {
		this.listStatus = listStatus;
	}


	public String getEmployeeUserName() {
		return employeeUserName;
	}

	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
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

	public String getApproveUserName() {
		return approveUserName;
	}

	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}


	public boolean isRoleAdmin() {
		return roleAdmin;
	}

	public void setRoleAdmin(boolean roleAdmin) {
		this.roleAdmin = roleAdmin;
	}
}
