package com.viettel.vtnet360.vt01.vt010012.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * class entity list add card VT010012
 * 
 * @author ThangBT 10/11/2018
 *
 */
public class VT010012ListAddCard extends BaseEntity {

	/** user name of login user */
	private String loginUserName;

	/** role of login user */
	private String loginRole;

	/** name of employee */
	private String empName;

	/** code of employee */
	private String empCode;

	/** name of unit */
	private String unitName;

	/** 3 levels of unit */
	private String detailUnit;

	/** id of card */
	private String cardId;

	public VT010012ListAddCard() {
		super();
	}

	public VT010012ListAddCard(String empName, String empCode, String unitName, String detailUnit, String cardId) {
		super();
		this.empName = empName;
		this.empCode = empCode;
		this.unitName = unitName;
		this.detailUnit = detailUnit;
		this.cardId = cardId;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getLoginRole() {
		return loginRole;
	}

	public void setLoginRole(String loginRole) {
		this.loginRole = loginRole;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDetailUnit() {
		return detailUnit;
	}

	public void setDetailUnit(String detailUnit) {
		this.detailUnit = detailUnit;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
}
