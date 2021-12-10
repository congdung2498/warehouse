package com.viettel.vtnet360.vt01.vt010002.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class Entity VT010002EntityRqUpdate
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010002EntityRqUpdate extends BaseEntity {

	/** in_out_register.in_out_register_id **/
	private String inOutRegisterId;

	/** in_out_register.destination **/
	private String destination;

	/** in_out_register.employee_user_name **/
	private String employeeUserName;

	/** in_out_register.reason_registion **/
	private String reasonRegistion;

	/** in_out_register.reason_detail **/
	private String reasonDetail;

	/** in_out_register.start_time_by_plan **/
	private Date startTimeByPlan;

	/** in_out_register.end_time_by_plan **/
	private Date endTimeByPlan;

	/** in_out_register.start_time_by_plan **/
	private Date oldStartTimeByPlan;

	/** in_out_register.end_time_by_plan **/
	private Date oldEndTimeByPlan;

	/** in_out_register.approver_user_name **/
	private String approverUserName;

	/** in_out_register.IN_OUT_EMPLOYEE_LIST - them danh sach nguoi di cung **/
	private String inOutEmployeeList;

	public VT010002EntityRqUpdate() {
		super();
	}

	public VT010002EntityRqUpdate(String inOutRegisterId, String destination, String employeeUserName,
			String reasonRegistion, String reasonDetail, Date startTimeByPlan, Date endTimeByPlan,
			Date oldStartTimeByPlan, Date oldEndTimeByPlan, String approverUserName, String inOutEmployeeList) {
		super();
		this.inOutRegisterId = inOutRegisterId;
		this.destination = destination;
		this.employeeUserName = employeeUserName;
		this.reasonRegistion = reasonRegistion;
		this.reasonDetail = reasonDetail;
		this.startTimeByPlan = startTimeByPlan;
		this.endTimeByPlan = endTimeByPlan;
		this.oldStartTimeByPlan = oldStartTimeByPlan;
		this.oldEndTimeByPlan = oldEndTimeByPlan;
		this.approverUserName = approverUserName;
		//them danh sach nguoi di cung
		this.inOutEmployeeList = inOutEmployeeList;
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

	public Date getOldStartTimeByPlan() {
		return oldStartTimeByPlan;
	}

	public void setOldStartTimeByPlan(Date oldStartTimeByPlan) {
		this.oldStartTimeByPlan = oldStartTimeByPlan;
	}

	public Date getOldEndTimeByPlan() {
		return oldEndTimeByPlan;
	}

	public void setOldEndTimeByPlan(Date oldEndTimeByPlan) {
		this.oldEndTimeByPlan = oldEndTimeByPlan;
	}

	public String getApproverUserName() {
		return approverUserName;
	}

	public void setApproverUserName(String approverUserName) {
		this.approverUserName = approverUserName;
	}

	public String getInOutEmployeeList() {
		return inOutEmployeeList;
	}

	public void setInOutEmployeeList(String inOutEmployeeList) {
		this.inOutEmployeeList = inOutEmployeeList;
	}
}
