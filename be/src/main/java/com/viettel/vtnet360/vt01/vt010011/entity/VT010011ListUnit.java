package com.viettel.vtnet360.vt01.vt010011.entity;

/**
 * Class entity list of units VT010011
 * 
 * @author ThangBT 07/09/2018
 *
 */
public class VT010011ListUnit {

	/** role of login user */
	private String loginRole;

	/** column common */
	private String result;

	/** name of unit */
	private String unitName;

	/** id of unit */
	private String unitId;

	public VT010011ListUnit() {
		super();
	}

	public VT010011ListUnit(String result, String unitName, String unitId) {
		super();
		this.result = result;
		this.unitName = unitName;
		this.unitId = unitId;
	}

	public String getLoginRole() {
		return loginRole;
	}

	public void setLoginRole(String loginRole) {
		this.loginRole = loginRole;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}
