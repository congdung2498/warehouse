package com.viettel.vtnet360.vt01.vt010011.entity;

import java.util.Date;

/**
 * Class entity list of registered employees goes out VT010011
 * 
 * @author ThangBT 07/09/2018
 *
 */
public class VT010011ListInOut {

	/** id's going out */
	private String inOutId;

	/** name of employee */
	private String empName;

	/** phone number of employee */
	private String empPhone;

	/** code of employee */
	private String empCode;

	/** email of employee */
	private String empEmail;

	/** name of unit */
	private String unitName;

	/** 3 levels of unit */
	private String detailUnit;

	/** Registration status of employee */
	private int status;

	/** Day which employees go out as planned */
	private Date startDate;

	/** Day which employees come back as planned */
	private Date endDate;

	/** Day which employees go out actually */
	private Date startDateReal;

	/** Day which employees come back actually */
	private Date endDateReal;
	
	/** Destination */
	private String destination;
	
	/** Name of user who approved this record */
	private String approverName;

	/** user Name of user who approved this record */
	private String approverUserName;

	/** in out short reason go out */
	private String reason;
	
	/** in out resonDetail */
	private String resonDetail;

	/** in out resonApprover */
	private String resonApprover;

	/** in out resonGuard */
	private String resonGuard;

	/** Danh sach nguoi di cung **/
	private String inOutEmployeeList;
	
	private int isLate;

	public VT010011ListInOut() {
		super();
	}

	public VT010011ListInOut(String inOutId, String empName, String empPhone, String empCode, String empEmail,
			String unitName, String detailUnit, int status, Date startDate, Date endDate, Date startDateReal,
			Date endDateReal, String destination, String approverName, String reason, String resonDetail,
			String resonApprover, String resonGuard, String approverUserName, String inOutEmployeeList) {
		super();
		this.inOutId = inOutId;
		this.empName = empName;
		this.empPhone = empPhone;
		this.empCode = empCode;
		this.empEmail = empEmail;
		this.unitName = unitName;
		this.detailUnit = detailUnit;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startDateReal = startDateReal;
		this.endDateReal = endDateReal;
		this.destination = destination;
		this.approverName = approverName;
		this.reason = reason;
		this.resonDetail = resonDetail;
		this.resonApprover = resonApprover;
		this.resonGuard = resonGuard;
		this.approverUserName = approverUserName;
		this.inOutEmployeeList = inOutEmployeeList;
	}

	
	public String getDestination() { return destination; }
	public void setDestination(String destination) { this.destination = destination; }

	public String getApproverName() { return approverName; }
	public void setApproverName(String approverName) { this.approverName = approverName; }

	public String getReason() { return reason; }
	public void setReason(String reason) { this.reason = reason; }

	public String getResonDetail() { return resonDetail; }
	public void setResonDetail(String resonDetail) { this.resonDetail = resonDetail; }

	public String getResonApprover() { return resonApprover; }
	public void setResonApprover(String resonApprover) { this.resonApprover = resonApprover; }

	public String getResonGuard() { return resonGuard; }
	public void setResonGuard(String resonGuard) { this.resonGuard = resonGuard; }

	public String getInOutId() { return inOutId; }
	public void setInOutId(String inOutId) { this.inOutId = inOutId; }

	public String getEmpName() { return empName; }
	public void setEmpName(String empName) { this.empName = empName; }

	public String getEmpPhone() { return empPhone; }
	public void setEmpPhone(String empPhone) { this.empPhone = empPhone; }

	public String getEmpCode() { return empCode; }
	public void setEmpCode(String empCode) { this.empCode = empCode; }

	public String getEmpEmail() { return empEmail; }
	public void setEmpEmail(String empEmail) { this.empEmail = empEmail; }

	public String getUnitName() { return unitName; }
	public void setUnitName(String unitName) { this.unitName = unitName; }

	public String getDetailUnit() { return detailUnit; }
	public void setDetailUnit(String detailUnit) { this.detailUnit = detailUnit; }

	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }

	public Date getStartDate() { return startDate; }
	public void setStartDate(Date startDate) { this.startDate = startDate; }

	public Date getEndDate() { return endDate; }
	public void setEndDate(Date endDate) { this.endDate = endDate; }

	public Date getStartDateReal() { return startDateReal; }
	public void setStartDateReal(Date startDateReal) { this.startDateReal = startDateReal; }

	public Date getEndDateReal() { return endDateReal; }
	public void setEndDateReal(Date endDateReal) { this.endDateReal = endDateReal; }

	public String getApproverUserName() { return approverUserName; }
	public void setApproverUserName(String approverUserName) { this.approverUserName = approverUserName; }

	public String getInOutEmployeeList() { return inOutEmployeeList; }
	public void setInOutEmployeeList(String inOutEmployeeList) { this.inOutEmployeeList = inOutEmployeeList; }

  public int getIsLate() { return isLate; }
  public void setIsLate(int isLate) { this.isLate = isLate; }
}
