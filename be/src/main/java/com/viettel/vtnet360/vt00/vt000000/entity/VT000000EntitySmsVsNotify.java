package com.viettel.vtnet360.vt00.vt000000.entity;

import java.util.Date;

/**
 * Class Entity VT000000EntitySmsVsNotify
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT000000EntitySmsVsNotify {

	/** toUserName */
	private String toUserName;

	/** FullNameOfToUserName */
	private String FullNameOfToUserName;

	/** additionalInformation */
	private String additionalInformation;

	/** createUser */
	private String createUser;

	/** toUserId */
	private int toUserId;

	/** phone */
	private String phone;

	/** UnitName */
	private String UnitName;

	/** startTimeByPlan */
	private Date startTimeByPlan;

	/** endTimeByPlan */
	private Date endTimeByPlan;

	/** oldStartTimeByPlan */
	private Date oldStartTimeByPlan;

	/** oldEndTimeByPlan */
	private Date oldEndTimeByPlan;

	/** approverUserName */
	private String approverUserName;

	/** approverUserNameFullName */
	private String approverUserNameFullName;

	/** status */
	private int status;

	/** oldStatus */
	private int oldStatus;

	public VT000000EntitySmsVsNotify() {
		super();
	}

	public VT000000EntitySmsVsNotify(String toUserName, String fullNameOfToUserName, String additionalInformation,
			String createUser, int toUserId, String phone, String unitName, Date startTimeByPlan, Date endTimeByPlan,
			Date oldStartTimeByPlan, Date oldEndTimeByPlan, String approverUserName, String approverUserNameFullName,
			int status, int oldStatus) {
		super();
		this.toUserName = toUserName;
		FullNameOfToUserName = fullNameOfToUserName;
		this.additionalInformation = additionalInformation;
		this.createUser = createUser;
		this.toUserId = toUserId;
		this.phone = phone;
		UnitName = unitName;
		this.startTimeByPlan = startTimeByPlan;
		this.endTimeByPlan = endTimeByPlan;
		this.oldStartTimeByPlan = oldStartTimeByPlan;
		this.oldEndTimeByPlan = oldEndTimeByPlan;
		this.approverUserName = approverUserName;
		this.approverUserNameFullName = approverUserNameFullName;
		this.status = status;
		this.oldStatus = oldStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(int oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getApproverUserNameFullName() {
		return approverUserNameFullName;
	}

	public void setApproverUserNameFullName(String approverUserNameFullName) {
		this.approverUserNameFullName = approverUserNameFullName;
	}

	public String getFullNameOfToUserName() {
		return FullNameOfToUserName;
	}

	public void setFullNameOfToUserName(String fullNameOfToUserName) {
		FullNameOfToUserName = fullNameOfToUserName;
	}

	public String getApproverUserName() {
		return approverUserName;
	}

	public void setApproverUserName(String approverUserName) {
		this.approverUserName = approverUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public int getToUserId() {
		return toUserId;
	}

	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUnitName() {
		return UnitName;
	}

	public void setUnitName(String unitName) {
		UnitName = unitName;
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

}
