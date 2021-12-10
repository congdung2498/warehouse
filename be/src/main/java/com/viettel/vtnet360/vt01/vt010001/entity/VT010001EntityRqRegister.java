package com.viettel.vtnet360.vt01.vt010001.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class Entity VT010001EntityRequestParamRegister
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010001EntityRqRegister extends BaseEntity {

	/** in_out_register.destination **/
	private String destination;

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

	/** id **/
	private String inOutRegisterId;

	/** danh sach nguoi di cung **/
	private String inOutEmployeeList;

	public VT010001EntityRqRegister() {
		super();
	}

	public VT010001EntityRqRegister(String inOutRegisterId, String destination, String reasonRegistion, String reasonDetail,
			Date startTimeByPlan, Date endTimeByPlan, String approverUserName, String inOutEmployeeList) {
		super();
		this.inOutRegisterId = inOutRegisterId;
		this.destination = destination;
		this.reasonRegistion = reasonRegistion;
		this.reasonDetail = reasonDetail;
		this.startTimeByPlan = startTimeByPlan;
		this.endTimeByPlan = endTimeByPlan;
		this.approverUserName = approverUserName;
		this.inOutEmployeeList = inOutEmployeeList;
	}

	public String getDestination() { return destination; }
	public void setDestination(String destination) { this.destination = destination; }

	public String getReasonRegistion() { return reasonRegistion; }
	public void setReasonRegistion(String reasonRegistion) { this.reasonRegistion = reasonRegistion; }

	public String getReasonDetail() { return reasonDetail; }
	public void setReasonDetail(String reasonDetail) { this.reasonDetail = reasonDetail; }

	public Date getStartTimeByPlan() { return startTimeByPlan; }
	public void setStartTimeByPlan(Date startTimeByPlan) { this.startTimeByPlan = startTimeByPlan; }

	public Date getEndTimeByPlan() { return endTimeByPlan; }
	public void setEndTimeByPlan(Date endTimeByPlan) { this.endTimeByPlan = endTimeByPlan; }

	public String getApproverUserName() { return approverUserName; }
	public void setApproverUserName(String approverUserName) { this.approverUserName = approverUserName; }
	
	public String getInOutEmployeeList() { return inOutEmployeeList; }
	public void setInOutEmployeeList(String inOutEmployeeList) { this.inOutEmployeeList = inOutEmployeeList; }

	public String getInOutRegisterId() { return inOutRegisterId; }
	public void setInOutRegisterId(String inOutRegisterId) { this.inOutRegisterId = inOutRegisterId; }
}
