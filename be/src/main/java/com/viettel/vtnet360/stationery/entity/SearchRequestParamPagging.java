package com.viettel.vtnet360.stationery.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class SearchRequestParamPagging extends BaseEntity {
	private String requestID;
	
	private int pageSize;
	
	private int pageNumber;

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public SearchRequestParamPagging(String requestID, int pageSize, int pageNumber) {
		super();
		this.requestID = requestID;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
	}

	public SearchRequestParamPagging() {
		super();
		// TODO Auto-generated constructor stub
	}
}
