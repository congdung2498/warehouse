package com.viettel.vtnet360.vt03.vt030000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * class entity of drive squad
 * 
 * 
 *
 */
public class VT030000EntityDriveSquad extends BaseEntity {

	/** DRIVES_SQUAD.DRIVE_SQUAD_ID */
	private String squadId;

	/** DRIVES_SQUAD.SQUAD_NAME */
	private String squadName;

	/** DRIVES_SQUAD.PLACE_ID */
	private String placeId;

	/** QLDV_PLACE.QLDV_PLACE */
	private String placeName;

	/** DRIVES_SQUAD.EMPLOYEE_USER_NAME */
	private String userName;

	/** DRIVES_SQUAD.CREATE_USER */
	private String userCreate;

	/** QLDV_EMPLOYEE.FULL_NAME */
	private String fullName;

	/** DRIVES_SQUAD.STATUS */
	private String status;

	/** QLDV_UNIT.UNIT_NAME */
	private String unitName;

	/** QLDV_EMPLOYEE.PHONE_NUMBER */
	private String emplPhone;

	/** squadName + unitName */
	private String displayOption;
	
	/** pageNumber in one page */
	private int pageNumber;
	
	private String loginRole;
	
	/** number of records in a page */
	private int pageSize;
	
	/** total all records */
	private int totalRecords;

	public VT030000EntityDriveSquad() {
	}

	public VT030000EntityDriveSquad(String squadId, String squadName, String placeId, String placeName, String username,
			String fullName, String status, int pageNumber, int pageSize) {
		super();
		this.squadId = squadId;
		this.squadName = squadName;
		this.placeId = placeId;
		this.placeName = placeName;
		this.userName = username;
		this.fullName = fullName;
		this.status = status;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
	}

	public String getSquadId() {
		return squadId;
	}

	public void setSquadId(String squadId) {
		this.squadId = squadId;
	}

	public String getSquadName() {
		return squadName;
	}

	public void setSquadName(String squadName) {
		this.squadName = squadName;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getEmplPhone() {
		return emplPhone;
	}

	public void setEmplPhone(String emplPhone) {
		this.emplPhone = emplPhone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDisplayOption() {
		return displayOption;
	}

	public void setDisplayOption(String displayOption) {
		this.displayOption = displayOption;
	}

	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public String getLoginRole() {
    return loginRole;
  }

  public void setLoginRole(String loginRole) {
    this.loginRole = loginRole;
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

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
}
