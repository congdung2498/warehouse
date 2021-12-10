package com.viettel.vtnet360.vt03.vt030011.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class VT030011CancelRequest extends BaseEntity {
	// CAR_BOOKING.CAR_BOOKING_ID
	private String bookCarId;
	
	// CAR_BOOKING.EMPLOYEE_USER_NAME
	private String userName;
	
	private String reason;

	
	public String getBookCarId() { return bookCarId; }
	public void setBookCarId(String bookCarId) { this.bookCarId = bookCarId; }
	
	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }
	
  public String getReason() { return reason; }
  public void setReason(String reason) { this.reason = reason; }
}
