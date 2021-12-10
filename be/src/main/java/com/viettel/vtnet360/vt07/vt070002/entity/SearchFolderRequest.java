package com.viettel.vtnet360.vt07.vt070002.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class SearchFolderRequest extends BaseEntity {
	private String qrCode;
	private int pageNumber = 1;
	private int pageSize = 10;

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
