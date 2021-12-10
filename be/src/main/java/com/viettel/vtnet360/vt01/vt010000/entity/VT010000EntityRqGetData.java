package com.viettel.vtnet360.vt01.vt010000.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class Entity VT010000EntityRqGetData
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010000EntityRqGetData extends BaseEntity {

	/** in_out_register.status **/
	private int[] status;

	/** in_out_register.start_time_by_plan **/
	private Date startTimeByPlan;

	/** in_out_register.end_time_by_plan **/
	private Date endTimeByPlan;

	/** qldv_unit.unit_id **/
	private int employeeUnitId;

	/** in_out_register.employee_id **/
	private int employeeId;

	/** Size Record 1 page **/
	private int pageSize;

	/** number page **/
	private int pageNumber;

	/** role Employee **/
	private String role;

	/** qldv_employee.USER_NAME **/
	private String userName;

	/** search For search For User Name **/
	private String searchForUserName;

	/** check UnitId by User (QL) **/
	private int checkUnitId;

	/** Hanler by pageSize and pageNumber **/
	private int fromIndex;

	/** Hanler by pageSize and pageNumber **/
	private int getSize;

	/** timeNow **/
	private Date timeNow;

	/** processByRole */
	private String processByRole;
	
	private String approver;
	
	private boolean fixed;
  
  private boolean security;
  
  private String fixedUnitId;

	public VT010000EntityRqGetData() {
		super();
	}

	public VT010000EntityRqGetData(int[] status, Date startTimeByPlan, Date endTimeByPlan, int employeeUnitId,
			int employeeId, int pageSize, int pageNumber, String role, String userName, String searchForUserName,
			int checkUnitId, int fromIndex, int getSize, Date timeNow, String processByRole) {
		super();
		this.status = status;
		this.startTimeByPlan = startTimeByPlan;
		this.endTimeByPlan = endTimeByPlan;
		this.employeeUnitId = employeeUnitId;
		this.employeeId = employeeId;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.role = role;
		this.userName = userName;
		this.searchForUserName = searchForUserName;
		this.checkUnitId = checkUnitId;
		this.fromIndex = fromIndex;
		this.getSize = getSize;
		this.timeNow = timeNow;
		this.processByRole = processByRole;
	}

	public String getProcessByRole() {
		return processByRole;
	}

	public void setProcessByRole(String processByRole) {
		this.processByRole = processByRole;
	}

	public int[] getStatus() {
		return status;
	}

	public void setStatus(int[] status) {
		this.status = status;
	}

	public Date getStartTimeByPlan() {
		return startTimeByPlan;
	}

	public void setStartTimeByPlan(Date startTimeByPlan) {
		this.startTimeByPlan = startTimeByPlan;
	}

	public Date getEndTimeByPlan() {
		return endTimeByPlan;
	}

	public void setEndTimeByPlan(Date endTimeByPlan) {
		this.endTimeByPlan = endTimeByPlan;
	}

	public int getEmployeeUnitId() {
		return employeeUnitId;
	}

	public void setEmployeeUnitId(int employeeUnitId) {
		this.employeeUnitId = employeeUnitId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSearchForUserName() {
		return searchForUserName;
	}

	public void setSearchForUserName(String searchForUserName) {
		this.searchForUserName = searchForUserName;
	}

	public int getCheckUnitId() {
		return checkUnitId;
	}

	public void setCheckUnitId(int checkUnitId) {
		this.checkUnitId = checkUnitId;
	}

	public int getFromIndex() {
		return fromIndex;
	}

	public void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}

	public int getGetSize() {
		return getSize;
	}

	public void setGetSize(int getSize) {
		this.getSize = getSize;
	}

	public Date getTimeNow() {
		return timeNow;
	}

	public void setTimeNow(Date timeNow) {
		this.timeNow = timeNow;
	}

  public String getApprover() {
    return approver;
  }

  public void setApprover(String approver) {
    this.approver = approver;
  }

  public boolean isFixed() {
    return fixed;
  }

  public void setFixed(boolean fixed) {
    this.fixed = fixed;
  }

  public boolean isSecurity() {
    return security;
  }

  public void setSecurity(boolean security) {
    this.security = security;
  }

  public String getFixedUnitId() {
    return fixedUnitId;
  }

  public void setFixedUnitId(String fixedUnitId) {
    this.fixedUnitId = fixedUnitId;
  }
}