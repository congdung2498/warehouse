package com.viettel.vtnet360.vt04.vt040000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class VT040000EntityRqApprove
 * 
 * @author KienHT 27/09/2018
 * 
 */
public class VT040000EntityRqApprove extends BaseEntity {

	private String[] issuedServiceId;

	/** 1 - approve 2-reject */
	private int flagAppove;

	/** ISSUES_SERVICE.REASON_REFUSE */
	private String resonRefuse;

	public VT040000EntityRqApprove() {
		super();
	}

	public VT040000EntityRqApprove(String[] issuedServiceId, int flagAppove, String resonRefuse) {
		super();
		this.issuedServiceId = issuedServiceId;
		this.flagAppove = flagAppove;
		this.resonRefuse = resonRefuse;
	}

	public String[] getIssuedServiceId() {
		return issuedServiceId;
	}

	public void setIssuedServiceId(String[] issuedServiceId) {
		this.issuedServiceId = issuedServiceId;
	}

	public int getFlagAppove() {
		return flagAppove;
	}

	public void setFlagAppove(int flagAppove) {
		this.flagAppove = flagAppove;
	}

	public String getResonRefuse() {
		return resonRefuse;
	}

	public void setResonRefuse(String resonRefuse) {
		this.resonRefuse = resonRefuse;
	}

}
