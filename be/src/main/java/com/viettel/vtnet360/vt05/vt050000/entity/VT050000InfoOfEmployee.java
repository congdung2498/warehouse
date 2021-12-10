package com.viettel.vtnet360.vt05.vt050000.entity;

public class VT050000InfoOfEmployee {
	
	/** QLDV_EMPLOYEE.USER_NAME **/
	private String userName;

	/** QLDV_EMPLOYEE.FULL_NAME **/
	private String fullName;

	/** QLDV_EMPLOYEE.TITLE **/
	private String title;

	/** QLDV_EMPLOYEE.LEVEL_EMPLOYEE **/
	private String levelEmployee;
	
	public VT050000InfoOfEmployee() {
	
	}

	public VT050000InfoOfEmployee(String userName, String fullName, String title, String levelEmployee) {
		super();
		this.userName = userName;
		this.fullName = fullName;
		this.title = title;
		this.levelEmployee = levelEmployee;
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

	public String getLevelEmployee() {
		return levelEmployee;
	}

	public void setLevelEmployee(String levelEmployee) {
		this.levelEmployee = levelEmployee;
	}
}
