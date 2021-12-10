package com.viettel.vtnet360.vt03.vt030010.entity;

public class BookingCarByEmployees {
	private String unit;
	private String fullName;
	private String phoneNumber;
	private String type;
	private String seat;
	private String timeFrom;
	private String timeTo;
	private int numberEmployee;
	private int status;

	public BookingCarByEmployees() {
	}

	public BookingCarByEmployees(String unit, String fullName, String phoneNumber, String type, String seat,
			String timeFrom, String timeTo, int numberEmployee, int status) {
		super();
		this.unit = unit;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.type = type;
		this.seat = seat;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.numberEmployee = numberEmployee;
		this.status = status;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public int getNumberEmployee() {
		return numberEmployee;
	}

	public void setNumberEmployee(int numberEmployee) {
		this.numberEmployee = numberEmployee;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
