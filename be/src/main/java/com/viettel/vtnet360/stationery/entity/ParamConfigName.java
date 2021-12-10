package com.viettel.vtnet360.stationery.entity;

import java.util.List;

public class ParamConfigName {
	
	private String masterClass;
	
	private String value;
	
	private String name;
	
	private String masterCode;
	
	private String masterName;
	
	private List<Integer> listStatus;

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

	public List<Integer> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<Integer> listStatus) {
		this.listStatus = listStatus;
	}

	public ParamConfigName(String masterClass, String value, String name, String masterCode, String masterName,
			List<Integer> listStatus) {
		super();
		this.masterClass = masterClass;
		this.value = value;
		this.name = name;
		this.masterCode = masterCode;
		this.masterName = masterName;
		this.listStatus = listStatus;
	}

	public ParamConfigName() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
