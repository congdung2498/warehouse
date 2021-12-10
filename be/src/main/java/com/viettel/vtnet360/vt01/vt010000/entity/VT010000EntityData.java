package com.viettel.vtnet360.vt01.vt010000.entity;

import java.util.Date;

/**
 * Class Entity VT010000EntityData
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010000EntityData {

	/** in_out_register.in_out_register_id **/
	private String inOutRegisterId;

	/** qldv_employee.full_name **/
	private String employeeFullName;

	/** in_out_register.start_time_by_plan **/
	private Date startTimeByPlan;

	/** in_out_register.status **/
	private int status;

	/** in_out_register.reason_detail **/
	private String reasonDetail;

	/** qldv_employee.full_name **/
	private String approverUserName;

	/** in_out_register.start_time_by_actual **/
	private Date endTimeByPlan;

	/** in_out_register.start_time_by_actual **/
	private Date startTimeByActual;

	/** in_out_register.end_time_by_actual **/
	private Date endTimeByActual;

	/** in_out_register.REASON_REGISTION **/
	private String reasonRegistion;
	
  private String    inOutEmployeeList;
  
  private int       isLate;

	public VT010000EntityData() {
		super();
	}

	public VT010000EntityData(String inOutRegisterId, String employeeFullName, Date startTimeByPlan, int status,
			String reasonDetail, String approverUserName, Date endTimeByPlan, Date startTimeByActual,
			Date endTimeByActual, String reasonRegistion) {
		super();
		this.inOutRegisterId = inOutRegisterId;
		this.employeeFullName = employeeFullName;
		this.startTimeByPlan = startTimeByPlan;
		this.status = status;
		this.reasonDetail = reasonDetail;
		this.approverUserName = approverUserName;
		this.endTimeByPlan = endTimeByPlan;
		this.startTimeByActual = startTimeByActual;
		this.endTimeByActual = endTimeByActual;
		this.reasonRegistion = reasonRegistion;
	}

	public String getInOutRegisterId() {
		return inOutRegisterId;
	}

	public void setInOutRegisterId(String inOutRegisterId) {
		this.inOutRegisterId = inOutRegisterId;
	}

	public String getEmployeeFullName() {
		return employeeFullName;
	}

	public void setEmployeeFullName(String employeeFullName) {
		this.employeeFullName = employeeFullName;
	}

	public Date getStartTimeByPlan() {
		return startTimeByPlan;
	}

	public void setStartTimeByPlan(Date startTimeByPlan) {
		this.startTimeByPlan = startTimeByPlan;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReasonDetail() {
		return reasonDetail;
	}

	public void setReasonDetail(String reasonDetail) {
		this.reasonDetail = reasonDetail;
	}

	public String getApproverUserName() {
		return approverUserName;
	}

	public void setApproverUserName(String approverUserName) {
		this.approverUserName = approverUserName;
	}

	public Date getEndTimeByPlan() {
		return endTimeByPlan;
	}

	public void setEndTimeByPlan(Date endTimeByPlan) {
		this.endTimeByPlan = endTimeByPlan;
	}

	public Date getStartTimeByActual() {
		return startTimeByActual;
	}

	public void setStartTimeByActual(Date startTimeByActual) {
		this.startTimeByActual = startTimeByActual;
	}

	public Date getEndTimeByActual() {
		return endTimeByActual;
	}

	public void setEndTimeByActual(Date endTimeByActual) {
		this.endTimeByActual = endTimeByActual;
	}

	public String getReasonRegistion() {
		return reasonRegistion;
	}

	public void setReasonRegistion(String reasonRegistion) {
		this.reasonRegistion = reasonRegistion;
	}

  public String getInOutEmployeeList() {
    return inOutEmployeeList;
  }

  public void setInOutEmployeeList(String inOutEmployeeList) {
    this.inOutEmployeeList = inOutEmployeeList;
  }

  public int getIsLate() {
    return isLate;
  }

  public void setIsLate(int isLate) {
    this.isLate = isLate;
  }
}
