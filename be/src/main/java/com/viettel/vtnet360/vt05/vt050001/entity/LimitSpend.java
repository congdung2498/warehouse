package com.viettel.vtnet360.vt05.vt050001.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class LimitSpend extends BaseEntity {
	private String limitId;
	private String limitValue;

	public LimitSpend() {
		// TODO Auto-generated constructor stub
	}

	public LimitSpend(String limitId, String limitValue) {
		super();
		this.limitId = limitId;
		this.limitValue = limitValue;
	}

	public String getLimitId() {
		return limitId;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public String getLimitValue() {
		return limitValue;
	}

	public void setLimitValue(String limitValue) {
		this.limitValue = limitValue;
	}

}
