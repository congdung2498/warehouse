package com.viettel.vtnet360.vt01.vt010000.entity;

import java.util.Date;

/**
 * Class Entity VT010000EntityDataGetRecord
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010000EntityDataGetRecord {

	/** in_out_register.IN_OUT_REGISTER_ID **/
	private String inOutRegisterId;

	/** in_out_register.destination **/
	private String destination;

	/** in_out_register.employee_user_name **/
	private String employeeUserName;

	/** QLDV_EMPLOYEE.CODE **/
	private String employeeCode;

	/** employeeFullName **/
	private String employeeFullName;

	/** QLDV_EMPLOYEE.PHONE_NUMBER **/
	private String employeePhone;

	/** QLDV_EMPLOYEE.TITLE **/
	private String employeeTitle;

	/** QLDV_EMPLOYEE.employeeUnitName **/
	private String employeeUnitName;

	/** QLDV_EMPLOYEE.employeeEmail **/
	private String employeeEmail;

	/** in_out_register.reason_registion **/
	private String reasonRegistion;

	/** in_out_register.reason_detail **/
	private String reasonDetail;

	/** in_out_register.start_time_by_plan **/
	private Date startTimeByPlan;

	/** in_out_register.end_time_by_plan **/
	private Date endTimeByPlan;

	/** in_out_register.approver_user_name **/
	private String approverUserName;

	/** approverFullName **/
	private String approverFullName;

	/** approverTitle **/
	private String approverTitle;

	/** in_out_register.status **/
	private int status;

	/** in_out_register.log_to_rollback_status **/
	private int rollbackStatus;

	/** in_out_register.log_to_rollback_start_time_by_plan **/
	private Date rollbackStartTime;

	/** in_out_register.log_to_rollback_end_time_by_plan **/
	private Date rollbackEndTime;

	/** in_out_register.reason_of_manager **/
	private String managerRejectReason;

	/** in_out_register.reason_of_guard **/
	private String guardRejectReason;

	/** IMG by emp in inout register **/
	private String imgBase64;
	
	private int       isLate;
	
	private String inOutEmployeeList;

	public VT010000EntityDataGetRecord() {
		super();
	}

	public VT010000EntityDataGetRecord(String inOutRegisterId, String destination, String employeeUserName,
			String employeeCode, String employeeFullName, String employeePhone, String employeeTitle,
			String employeeUnitName, String employeeEmail, String reasonRegistion, String reasonDetail,
			Date startTimeByPlan, Date endTimeByPlan, String approverUserName, String approverFullName,
			String approverTitle, int status, int rollbackStatus, Date rollbackStartTime, Date rollbackEndTime,
			String managerRejectReason, String guardRejectReason, String imgBase64, int isLate) {
		super();
		this.inOutRegisterId = inOutRegisterId;
		this.destination = destination;
		this.employeeUserName = employeeUserName;
		this.employeeCode = employeeCode;
		this.employeeFullName = employeeFullName;
		this.employeePhone = employeePhone;
		this.employeeTitle = employeeTitle;
		this.employeeUnitName = employeeUnitName;
		this.employeeEmail = employeeEmail;
		this.reasonRegistion = reasonRegistion;
		this.reasonDetail = reasonDetail;
		this.startTimeByPlan = startTimeByPlan;
		this.endTimeByPlan = endTimeByPlan;
		this.approverUserName = approverUserName;
		this.approverFullName = approverFullName;
		this.approverTitle = approverTitle;
		this.status = status;
		this.rollbackStatus = rollbackStatus;
		this.rollbackStartTime = rollbackStartTime;
		this.rollbackEndTime = rollbackEndTime;
		this.managerRejectReason = managerRejectReason;
		this.guardRejectReason = guardRejectReason;
		this.imgBase64 = imgBase64;
		this.isLate = isLate;
	}

	public String getImgBase64() {
		return imgBase64;
	}

	public void setImgBase64(String imgBase64) {
		this.imgBase64 = imgBase64;
	}

	public String getInOutRegisterId() {
		return inOutRegisterId;
	}

	public void setInOutRegisterId(String inOutRegisterId) {
		this.inOutRegisterId = inOutRegisterId;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getEmployeeUserName() {
		return employeeUserName;
	}

	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeFullName() {
		return employeeFullName;
	}

	public void setEmployeeFullName(String employeeFullName) {
		this.employeeFullName = employeeFullName;
	}

	public String getEmployeePhone() {
		return employeePhone;
	}

	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}

	public String getEmployeeTitle() {
		return employeeTitle;
	}

	public void setEmployeeTitle(String employeeTitle) {
		this.employeeTitle = employeeTitle;
	}

	public String getEmployeeUnitName() {
		return employeeUnitName;
	}

	public void setEmployeeUnitName(String employeeUnitName) {
		this.employeeUnitName = employeeUnitName;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getReasonRegistion() {
		return reasonRegistion;
	}

	public void setReasonRegistion(String reasonRegistion) {
		this.reasonRegistion = reasonRegistion;
	}

	public String getReasonDetail() {
		return reasonDetail;
	}

	public void setReasonDetail(String reasonDetail) {
		this.reasonDetail = reasonDetail;
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

	public String getApproverUserName() {
		return approverUserName;
	}

	public void setApproverUserName(String approverUserName) {
		this.approverUserName = approverUserName;
	}

	public String getApproverFullName() {
		return approverFullName;
	}

	public void setApproverFullName(String approverFullName) {
		this.approverFullName = approverFullName;
	}

	public String getApproverTitle() {
		return approverTitle;
	}

	public void setApproverTitle(String approverTitle) {
		this.approverTitle = approverTitle;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRollbackStatus() {
		return rollbackStatus;
	}

	public void setRollbackStatus(int rollbackStatus) {
		this.rollbackStatus = rollbackStatus;
	}

	public Date getRollbackStartTime() {
		return rollbackStartTime;
	}

	public void setRollbackStartTime(Date rollbackStartTime) {
		this.rollbackStartTime = rollbackStartTime;
	}

	public Date getRollbackEndTime() {
		return rollbackEndTime;
	}

	public void setRollbackEndTime(Date rollbackEndTime) {
		this.rollbackEndTime = rollbackEndTime;
	}

	public String getManagerRejectReason() {
		return managerRejectReason;
	}

	public void setManagerRejectReason(String managerRejectReason) {
		this.managerRejectReason = managerRejectReason;
	}

	public String getGuardRejectReason() {
		return guardRejectReason;
	}

	public void setGuardRejectReason(String guardRejectReason) {
		this.guardRejectReason = guardRejectReason;
	}

  public int getIsLate() {
    return isLate;
  }

  public void setIsLate(int isLate) {
    this.isLate = isLate;
  }
  
  public String getInOutEmployeeList() {
    return inOutEmployeeList;
  }

  public void setInOutEmployeeList(String inOutEmployeeList) {
    this.inOutEmployeeList = inOutEmployeeList;
  }
}
