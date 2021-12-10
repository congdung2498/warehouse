package com.viettel.vtnet360.vt07.vt070001.entity;

import java.sql.Date;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class VT070001EntityBarcodeDetail extends BaseEntity {
	private int barCodeDetailId;
	private int barCodeRangeId;
	private String code;
	private int status;
	private boolean printed;
	private String error;

	
	
	public VT070001EntityBarcodeDetail() {
		
	}



	public VT070001EntityBarcodeDetail(int barCodeRangeId, String code, int status, Date createDate,
			boolean printed, String createUser) {
		super();
		this.barCodeRangeId = barCodeRangeId;
		this.code = code;
		this.status = status;
		this.createDate = createDate;
		this.printed = printed;
		this.createUser = createUser;
	}



	public VT070001EntityBarcodeDetail(int barCodeRangeId, String code, int status, boolean printed,
			String createUser) {
		super();
		this.barCodeRangeId = barCodeRangeId;
		this.code = code;
		this.status = status;
		this.printed = printed;
		this.createUser = createUser;
	}



	public int getBarCodeDetailId() {
		return barCodeDetailId;
	}



	public void setBarCodeDetailId(int barCodeDetailId) {
		this.barCodeDetailId = barCodeDetailId;
	}



	public int getBarCodeRangeId() {
		return barCodeRangeId;
	}



	public void setBarCodeRangeId(int barCodeRangeId) {
		this.barCodeRangeId = barCodeRangeId;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public boolean isPrinted() {
		return printed;
	}



	public void setPrinted(boolean printed) {
		this.printed = printed;
	}



	public String getError() {
		return error;
	}



	public void setError(String error) {
		this.error = error;
	}






	
}
