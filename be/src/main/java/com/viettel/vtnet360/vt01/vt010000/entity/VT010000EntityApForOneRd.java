package com.viettel.vtnet360.vt01.vt010000.entity;

import java.util.Date;

/**
 * Class Entity VT010000EntityApForOneRd
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010000EntityApForOneRd {

	/** inOutRegisterId **/
	private String inOutRegisterId;

	/** status Record approve **/
	private int statusRecord;

	/** status approve client sended **/
	private int statusApprove;

	/** LOG_TO_ROLLBACK_STATUS **/
	private int logToRollBackStatus;

	/** LOG_TO_ROLLBACK_START_TIME_BY_PLAN **/
	private Date logToRollBackStartTimeByPlan;

	/** LOG_TO_ROLLBACK_END_TIME_BY_PLAN **/
	private Date logToRollBackEndTimeByPlan;

	/** reason abandon Of Approver **/
	private String reasonOfApprover;

	/** Name of guard allow out **/
	private String guardOutUserName;

	/** Name of guard allow in **/
	private String guardInUserName;

	/** reason abandon Of Guard **/
	private String reasonOfGuard;

	/** nowTime **/
	private Date timeNow;

	/** UserNow **/
	private String userNow;
	
	private int isMoreTimeGetInApprove;
	
	private int isLate;

	public VT010000EntityApForOneRd() {
		super();
	}

	public VT010000EntityApForOneRd(String inOutRegisterId, int statusRecord, int statusApprove,
			int logToRollBackStatus, Date logToRollBackStartTimeByPlan, Date logToRollBackEndTimeByPlan,
			String reasonOfApprover, String guardOutUserName, String guardInUserName, String reasonOfGuard,
			Date timeNow, String userNow) {
		super();
		this.inOutRegisterId = inOutRegisterId;
		this.statusRecord = statusRecord;
		this.statusApprove = statusApprove;
		this.logToRollBackStatus = logToRollBackStatus;
		this.logToRollBackStartTimeByPlan = logToRollBackStartTimeByPlan;
		this.logToRollBackEndTimeByPlan = logToRollBackEndTimeByPlan;
		this.reasonOfApprover = reasonOfApprover;
		this.guardOutUserName = guardOutUserName;
		this.guardInUserName = guardInUserName;
		this.reasonOfGuard = reasonOfGuard;
		this.timeNow = timeNow;
		this.userNow = userNow;
	}

	public String getInOutRegisterId() {
		return inOutRegisterId;
	}

	public void setInOutRegisterId(String inOutRegisterId) {
		this.inOutRegisterId = inOutRegisterId;
	}

	public int getStatusRecord() {
		return statusRecord;
	}

	public void setStatusRecord(int statusRecord) {
		this.statusRecord = statusRecord;
	}

	public int getStatusApprove() {
		return statusApprove;
	}

	public void setStatusApprove(int statusApprove) {
		this.statusApprove = statusApprove;
	}

	public int getLogToRollBackStatus() {
		return logToRollBackStatus;
	}

	public void setLogToRollBackStatus(int logToRollBackStatus) {
		this.logToRollBackStatus = logToRollBackStatus;
	}

	public Date getLogToRollBackStartTimeByPlan() {
		return logToRollBackStartTimeByPlan;
	}

	public void setLogToRollBackStartTimeByPlan(Date logToRollBackStartTimeByPlan) {
		this.logToRollBackStartTimeByPlan = logToRollBackStartTimeByPlan;
	}

	public Date getLogToRollBackEndTimeByPlan() {
		return logToRollBackEndTimeByPlan;
	}

	public void setLogToRollBackEndTimeByPlan(Date logToRollBackEndTimeByPlan) {
		this.logToRollBackEndTimeByPlan = logToRollBackEndTimeByPlan;
	}

	public String getReasonOfApprover() {
		return reasonOfApprover;
	}

	public void setReasonOfApprover(String reasonOfApprover) {
		this.reasonOfApprover = reasonOfApprover;
	}

	public String getGuardOutUserName() {
		return guardOutUserName;
	}

	public void setGuardOutUserName(String guardOutUserName) {
		this.guardOutUserName = guardOutUserName;
	}

	public String getGuardInUserName() {
		return guardInUserName;
	}

	public void setGuardInUserName(String guardInUserName) {
		this.guardInUserName = guardInUserName;
	}

	public String getReasonOfGuard() {
		return reasonOfGuard;
	}

	public void setReasonOfGuard(String reasonOfGuard) {
		this.reasonOfGuard = reasonOfGuard;
	}

	public Date getTimeNow() {
		return timeNow;
	}

	public void setTimeNow(Date timeNow) {
		this.timeNow = timeNow;
	}

	public String getUserNow() {
		return userNow;
	}

	public void setUserNow(String userNow) {
		this.userNow = userNow;
	}

  public int getIsMoreTimeGetInApprove() {
    return isMoreTimeGetInApprove;
  }

  public void setIsMoreTimeGetInApprove(int isMoreTimeGetInApprove) {
    this.isMoreTimeGetInApprove = isMoreTimeGetInApprove;
  }
}
