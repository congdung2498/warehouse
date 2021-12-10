package com.viettel.vtnet360.vt01.vt010000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class Entity VT010000EntityRqUpdateRecord
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010000EntityRqUpdateRecord  extends BaseEntity {

	/** IN_OUT_REGISTER.IN_OUT_REGISTER_ID */
	private String inOutRegisterId;

	/** IN_OUT_REGISTER.STATUS */
	private int status;

	/** Have you rollback? */
	private Boolean rollBack;

	public VT010000EntityRqUpdateRecord() {
		super();
	}

	public VT010000EntityRqUpdateRecord(String inOutRegisterId, int status, Boolean rollBack) {
		super();
		this.inOutRegisterId = inOutRegisterId;
		this.status = status;
		this.rollBack = rollBack;
	}

	public String getInOutRegisterId() {
		return inOutRegisterId;
	}

	public void setInOutRegisterId(String inOutRegisterId) {
		this.inOutRegisterId = inOutRegisterId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Boolean getRollBack() {
		return rollBack;
	}

	public void setRollBack(Boolean rollBack) {
		this.rollBack = rollBack;
	}

}
