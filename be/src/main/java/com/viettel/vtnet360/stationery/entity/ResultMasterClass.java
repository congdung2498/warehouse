package com.viettel.vtnet360.stationery.entity;

public class ResultMasterClass {

	private String masterClass;
	
	private String masterCode;
	
	private String masterName;

	public String getMasterClass() {
		return masterClass;
	}

	public void setMasterClass(String masterClass) {
		this.masterClass = masterClass;
	}

	public String getMasterCode() {
		return masterCode;
	}

	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public ResultMasterClass(String masterClass, String masterCode, String masterName) {
		super();
		this.masterClass = masterClass;
		this.masterCode = masterCode;
		this.masterName = masterName;
	}

	public ResultMasterClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
