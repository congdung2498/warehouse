package com.viettel.vtnet360.vt05.vt050000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class VT050000InfoToFindListEmployeeOfUnit extends BaseEntity {
	
	/** use for get employee of unit of hcdv and child */
	private String hcdvUserName;
	
	/** use for check user logged on is admin or hcdv */
	private boolean roleAdmin;
	
	/** input param to search employee */
	private String pattern;
	
	/** use for number of record response */
	private int pageNumber;
	
	/** use for number of record response */
	private int pageSize;
	
	public VT050000InfoToFindListEmployeeOfUnit() {
	
	}

	public VT050000InfoToFindListEmployeeOfUnit(String hcdvUserName, boolean roleAdmin, String pattern, int pageNumber,
			int pageSize) {
		super();
		this.hcdvUserName = hcdvUserName;
		this.roleAdmin = roleAdmin;
		this.pattern = pattern;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public String getHcdvUserName() {
		return hcdvUserName;
	}

	public void setHcdvUserName(String hcdvUserName) {
		this.hcdvUserName = hcdvUserName;
	}

	public boolean isRoleAdmin() {
		return roleAdmin;
	}

	public void setRoleAdmin(boolean roleAdmin) {
		this.roleAdmin = roleAdmin;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
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
