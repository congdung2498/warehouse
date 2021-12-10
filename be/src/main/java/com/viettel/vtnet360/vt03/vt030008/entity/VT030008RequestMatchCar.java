package com.viettel.vtnet360.vt03.vt030008.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030008RequestMatchCar extends BaseEntity {
	/** CAR_BOOKING.CAR_BOOKING_ID */
	private String bookCarId;
	
	/** CAR_BOOKING.EMPLOYEE_USER_NAME */
	private String userRequest;
	
	/** CAR_BOOKING.USER_ASSIGNER */
	private String userAssigner;
	
	/** DRIVE.EMPLOYEE_USER_NAME */
	private String userName;
	
	/** DRIVE_CAR.CAR_ID */
	private String carId;
	
	/** QLDV_EMPLOYEE.FULL_NAME */
	private String fullName;
	
	/** search driver by their's code, fullname, email or phone */
	private String searchBy;
	
	private String squadId;
	
	private String role;
	
	private int pageNumber;
	
	private int pageSize;

	
	public String getBookCarId() {
		return bookCarId;
	}
	public void setBookCarId(String bookCarId) {
		this.bookCarId = bookCarId;
	}
	public String getUserRequest() {
		return userRequest;
	}
	public void setUserRequest(String userRequest) {
		this.userRequest = userRequest;
	}
	public String getUserAssigner() {
		return userAssigner;
	}
	public void setUserAssigner(String userAssigner) {
		this.userAssigner = userAssigner;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	
	public String getSquadId() {
    return squadId;
  }
  public void setSquadId(String squadId) {
    this.squadId = squadId;
  }
  public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
