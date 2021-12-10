package com.viettel.vtnet360.vt01.vt010000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class Entity VT010000EntityRqMgAp
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010000EntityRqMgAp extends BaseEntity {

	/** IN_OUT_REGISTER.IN_OUT_REGISTER_ID **/
	private String[] inOutRegisterId;

	/** IN_OUT_REGISTER.REASON_OF_APPROVER **/
	private String reasonOfApprover;

	/**
	 * 1 - approve 2 - reject 3 - accept out 4 - accept in 6 - reject out 7 - reject
	 * in
	 * 
	 **/
	private int status;

	/** IN_OUT_REGISTER.GUARD_OUT_USER_NAME **/
	private String guardOutUserName;

	/** IN_OUT_REGISTER.GUARD_IN_USER_NAME **/
	private String guardInUserName;

	/** IN_OUT_REGISTER.REASON_OF_GUARD **/
	private String reasonOfGuard;

	/** LOG_TO_ROLLBACK_STATUS **/
	private int logToRollBackStatus;
	
	private int isMoreTimeGetInApprove;

	public VT010000EntityRqMgAp() {
		super();
	}

	public VT010000EntityRqMgAp(String[] inOutRegisterId, String reasonOfApprover, int status,
			String guardOutUserName, String guardInUserName, String reasonOfGuard, int logToRollBackStatus) {
		super();
		this.inOutRegisterId = inOutRegisterId;
		this.reasonOfApprover = reasonOfApprover;
		this.status = status;
		this.guardOutUserName = guardOutUserName;
		this.guardInUserName = guardInUserName;
		this.reasonOfGuard = reasonOfGuard;
		this.logToRollBackStatus = logToRollBackStatus;
	}

	public String[] getInOutRegisterId() {
		return inOutRegisterId;
	}

	public void setInOutRegisterId(String[] inOutRegisterId) {
		this.inOutRegisterId = inOutRegisterId;
	}

	public String getReasonOfApprover() {
		return reasonOfApprover;
	}

	public void setReasonOfApprover(String reasonOfApprover) {
		this.reasonOfApprover = reasonOfApprover;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getLogToRollBackStatus() {
		return logToRollBackStatus;
	}

	public void setLogToRollBackStatus(int logToRollBackStatus) {
		this.logToRollBackStatus = logToRollBackStatus;
	}
	
	public int getIsMoreTimeGetInApprove() {
    return isMoreTimeGetInApprove;
  }

  public void setIsMoreTimeGetInApprove(int isMoreTimeGetInApprove) {
    this.isMoreTimeGetInApprove = isMoreTimeGetInApprove;
  }

}
