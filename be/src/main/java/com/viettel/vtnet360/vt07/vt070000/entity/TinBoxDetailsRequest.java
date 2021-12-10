package com.viettel.vtnet360.vt07.vt070000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class TinBoxDetailsRequest extends BaseEntity {

	private int id;
	private int searchType;
	private String role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
