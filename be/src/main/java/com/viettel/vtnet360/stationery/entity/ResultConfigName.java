package com.viettel.vtnet360.stationery.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class ResultConfigName extends BaseEntity {
	
	private String masterClass;
	
	private String value;
	
	private String name;
	
	private String masterCode;
	
	private String masterName;
	
	private String masterClassConfig;
	
	private String status;
	
	private String updateUser;
	
	private String updateDate;
	
	private String nameConfig;
	
	private String valueConfig;
	
	private String noteConfig;
	
	private int pageNumber;
	
	private int pageSize;
	
	
	
	
	public String getMasterClassConfig() {
		return masterClassConfig;
	}

	public void setMasterClassConfig(String masterClassConfig) {
		this.masterClassConfig = masterClassConfig;
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

	public String getNameConfig() {
		return nameConfig;
	}

	public void setNameConfig(String nameConfig) {
		this.nameConfig = nameConfig;
	}

	public String getValueConfig() {
		return valueConfig;
	}

	public void setValueConfig(String valueConfig) {
		this.valueConfig = valueConfig;
	}

	public String getNoteConfig() {
		return noteConfig;
	}

	public void setNoteConfig(String noteConfig) {
		this.noteConfig = noteConfig;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getMasterClass() {
		return masterClass;
	}

	public void setMasterClass(String masterClass) {
		this.masterClass = masterClass;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public ResultConfigName(String masterClass, String value, String name, String masterCode, String masterName,
			String status, String updateUser, String updateDate, String nameConfig, String valueConfig,
			String noteConfig, int pageNumber, int pageSize) {
		super();
		this.masterClass = masterClass;
		this.value = value;
		this.name = name;
		this.masterCode = masterCode;
		this.masterName = masterName;
		this.status = status;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
		this.nameConfig = nameConfig;
		this.valueConfig = valueConfig;
		this.noteConfig = noteConfig;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public ResultConfigName() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
