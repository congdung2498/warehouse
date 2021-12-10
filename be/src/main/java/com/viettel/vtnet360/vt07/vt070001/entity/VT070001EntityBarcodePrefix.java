package com.viettel.vtnet360.vt07.vt070001.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class VT070001EntityBarcodePrefix extends BaseEntity {
	private int barCodePrefixId;
	private String prefix;
	private String name;
	private String description;
	private int status;


	public int getBarCodePrefixId() {
		return barCodePrefixId;
	}


	public void setBarCodePrefixId(int barCodePrefixId) {
		this.barCodePrefixId = barCodePrefixId;
	}


	public String getPrefix() {
		return prefix;
	}


	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}

	
}
