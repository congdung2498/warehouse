package com.viettel.vtnet360.vt01.vt010011.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class entity condition to query in database VT010011
 * 
 * @author ThangBT 07/09/2018
 *
 */
public class VT010011ListCondition extends BaseEntity {

	/** user name of login user */
	private String loginUserName;

	/** role of login user */
	private String loginRole;

	/** Information related to employees */
	private String personInfo;

	/** user name employees */
	private String userName;

	/** Registration status of employee */
	private String[] listStatus;

	/** list of unit id */
	private String[] listUnit;

	/** day employees out */
	private Date startDate;

	/** day employees in */
	private Date endDate;

	/** select from which row */
	private int startRow;

	/** number of row to select */
	private int rowSize;

	/** in out resonDetail */
	private String resonDetail;

	/** in out resonApprover */
	private String resonApprover;

	/** in out resonGuard */
	private String resonGuard;
	
	private String approver;
	
	private boolean fixed;
	
	private boolean security;
	
	private String fixedUnitId;
	

	public VT010011ListCondition() {
		super();
	}

	public VT010011ListCondition(String loginUserName, String loginRole, String personInfo, String userName,
			String[] listStatus, String[] listUnit, Date startDate, Date endDate, int startRow, int rowSize,
			String resonDetail, String resonApprover, String resonGuard) {
		super();
		this.loginUserName = loginUserName;
		this.loginRole = loginRole;
		this.personInfo = personInfo;
		this.userName = userName;
		this.listStatus = listStatus;
		this.listUnit = listUnit;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startRow = startRow;
		this.rowSize = rowSize;
		this.resonDetail = resonDetail;
		this.resonApprover = resonApprover;
		this.resonGuard = resonGuard;
	}

	
	public String getResonDetail() { return resonDetail; }
	public void setResonDetail(String resonDetail) { this.resonDetail = resonDetail; }

	public String getResonApprover() { return resonApprover; }
	public void setResonApprover(String resonApprover) { this.resonApprover = resonApprover; }

	public String getResonGuard() { return resonGuard; }
	public void setResonGuard(String resonGuard) { this.resonGuard = resonGuard; }

	public String getLoginUserName() { return loginUserName; }
	public void setLoginUserName(String loginUserName) { this.loginUserName = loginUserName; }

	public String getLoginRole() { return loginRole; }
	public void setLoginRole(String loginRole) { this.loginRole = loginRole; }

	public String getPersonInfo() { return personInfo; }
	public void setPersonInfo(String personInfo) { this.personInfo = personInfo; }

	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }

	public String[] getListStatus() { return listStatus; }
	public void setListStatus(String[] listStatus) { this.listStatus = listStatus; }

	public String[] getListUnit() { return listUnit; }
	public void setListUnit(String[] listUnit) { this.listUnit = listUnit; }

	public Date getStartDate() { return startDate; }
	public void setStartDate(Date startDate) { this.startDate = startDate; }

	public Date getEndDate() { return endDate; }
	public void setEndDate(Date endDate) { this.endDate = endDate; }

	public int getStartRow() { return startRow; }
	public void setStartRow(int startRow) { this.startRow = startRow; }

	public int getRowSize() { return rowSize; }
	public void setRowSize(int rowSize) { this.rowSize = rowSize; }

  public String getApprover() { return approver; }
  public void setApprover(String approver) { this.approver = approver; }

  public boolean isFixed() { return fixed; }
  public void setFixed(boolean fixed) { this.fixed = fixed; }

  public boolean isSecurity() { return security; }
  public void setSecurity(boolean security) { this.security = security; }

  public String getFixedUnitId() { return fixedUnitId; }
  public void setFixedUnitId(String fixedUnitId) { this.fixedUnitId = fixedUnitId; }
}
