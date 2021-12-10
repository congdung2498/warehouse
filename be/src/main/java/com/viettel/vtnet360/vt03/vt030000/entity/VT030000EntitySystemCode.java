package com.viettel.vtnet360.vt03.vt030000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class VT030000EntitySystemCode extends BaseEntity {
	
	/** M_SYSTEM_CODE.MASTER_CLASS */
	private String masterClassType;
	
	/** M_SYSTEM_CODE.MASTER_CLASS */
	private String masterClassSeat;
	
	/** M_SYSTEM_CODE.MASTER_CLASS */
	private String masterClassRoute;
	
	public VT030000EntitySystemCode() {
		super();
	}
	public VT030000EntitySystemCode(String masterClassType, String masterClassSeat, String masterClassRoute) {
		super();
		this.masterClassType = masterClassType;
		this.masterClassSeat = masterClassSeat;
		this.masterClassRoute = masterClassRoute;
	}
	public String getMasterClassType() {
		return masterClassType;
	}
	public void setMasterClassType(String masterClassType) {
		this.masterClassType = masterClassType;
	}
	public String getMasterClassSeat() {
		return masterClassSeat;
	}
	public void setMasterClassSeat(String masterClassSeat) {
		this.masterClassSeat = masterClassSeat;
	}
	public String getMasterClassRoute() {
		return masterClassRoute;
	}
	public void setMasterClassRoute(String masterClassRoute) {
		this.masterClassRoute = masterClassRoute;
	}
	
	
}
