package com.viettel.vtnet360.vt00.vt000000.entity;

/**
 * Class Entity VT000000EntityDataFindEmployee
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT000000EntityDataFindEmployee {

	/** qldv_employee.user_name **/
	private String userName;

	/** qldv_employee.full_name **/
	private String fullName;

	/** qldv_employee.full_name **/
	private String fullNameEmployee;

	/** qldv_employee.title **/
	private String title;

	/** qldv_employee.level_employee **/
	private String levelEmployee;

	private String unitName;

	private String email;

	public VT000000EntityDataFindEmployee() {
		super();
	}

	public VT000000EntityDataFindEmployee(String userName, String fullName, String title, String levelEmployee) {
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

	public String getFullNameEmployee() {
		return fullNameEmployee;
	}

	public void setFullNameEmployee(String fullNameEmployee) {
		this.fullNameEmployee = fullNameEmployee;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
