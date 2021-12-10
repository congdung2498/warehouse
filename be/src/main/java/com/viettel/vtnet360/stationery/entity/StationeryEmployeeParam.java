package com.viettel.vtnet360.stationery.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class StationeryEmployeeParam extends BaseEntity {
	
	private String requestID;

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public StationeryEmployeeParam(String requestID) {
		super();
		this.requestID = requestID;
	}

	public StationeryEmployeeParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
