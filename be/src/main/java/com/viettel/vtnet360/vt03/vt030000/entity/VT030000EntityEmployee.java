package com.viettel.vtnet360.vt03.vt030000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030000EntityEmployee extends BaseEntity {
	
	/** QLDV_EMPLOYEE.USER_NAME */
	private String userName;
	
	/** QLDV_EMPLOYEE.UNIT_ID*/
	private int unitId;
	
	/** QLDV_UNIT.UNIT_NAME*/
	private String unitName;

	/** QLDV_EMPLOYEE.FULL_NAME*/
	private String fullName;
	
	/** QLDV_EMPLOYEE.PHONE_NUMBER*/
	private String emplPhone;

	/** QLDV_EMPLOYEE.PLACE_ID*/
	private String placeId;
	
	/** QLDV_EMPLOYEE.ROLE*/
	private String role;
	
	/** fullName + unitName */
	private String displayOption;
	
	private int pageSize;
	
	private int pageNumber;
	
	
	public VT030000EntityEmployee() {
		super();
	}

	public VT030000EntityEmployee(String userName, int unitId, String fullName, String empPhone, String role, String placeId) {
		super();
		this.userName = userName;
		this.unitId = unitId;
		this.fullName = fullName;
		this.emplPhone = empPhone;
		this.role = role;
		this.placeId = placeId;
		
	}

	public VT030000EntityEmployee(String fullName) {
		super();
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmpPhone() {
		return emplPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.emplPhone = empPhone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getDisplayOption() {
		return displayOption;
	}

	public void setDisplayOption(String displayOption) {
		this.displayOption = displayOption;
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

}
