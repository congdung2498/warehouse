package com.viettel.vtnet360.vt03.vt030013.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * 
 * @author cuonghd
 *
 */

public class Driver extends BaseEntity {
	/** DRIVER.EMPLOYEE_USER_NAME */
	private String userName;
	/** QLDV_EMPLOYEE.FULL_NAME*/
	private String fullName;
	/** QLDV_EMPLOYEE.TITLE*/
	private String title;
	/**DRIVES_SQUAD.DRIVE_SQUAD_ID*/
	private String squadId;
	/**Searching String*/
	private String pattern;
	
	private int pageSize;
	private int pageNumber;
	private String role;
	
	public Driver() {
		super();
		
	}
	
	public Driver(String squadId, String pattern, int pageSize, int pageNumber) {
		super();
		this.squadId = squadId;
		this.pattern = pattern;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSquadId() {
		return squadId;
	}
	public void setSquadId(String squadId) {
		this.squadId = squadId;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
