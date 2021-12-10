package com.viettel.vtnet360.vt03.vt030009.entity;

import java.util.Date;

import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntitySystemCode;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030009UpdateProcessCarRoute extends VT030000EntitySystemCode {
	// CAR_BOOKING.CAR_BOOKING_ID
	private String userName;
	
	// CAR_BOOKING.DRIVE_USER
	private String userDriver;
	
	// CAR_BOOKING.CAR_BOOKING_ID
	private String bookCarId;
	
	// CAR_BOOKING.STATUS
	private int status;
	
	// CAR_BOOKING.TIME_START
	private Date timeReadyStart;
	
	// CAR_BOOKING.TIME_AT_TAGER
	private Date timeAtTarget;
	
	// CAR_BOOKING.TIME_FINISH
	private Date timeReadyFinish;
	
	// CARS.CAR_ID
	private String carId;
	
	
	
	public VT030009UpdateProcessCarRoute() {
		super();
	}

	public VT030009UpdateProcessCarRoute(String userName, String bookCarId, int flag, Date timeReadyStart,
			Date timeAtTarget, Date timeReadyFinish, String carId, String userDriver) {
		super();
		this.setUserName(userName);
		this.bookCarId = bookCarId;
		this.setStatus(flag);
		this.timeReadyStart = timeReadyStart;
		this.timeAtTarget = timeAtTarget;
		this.timeReadyFinish = timeReadyFinish;
		this.carId = carId;
		this.userDriver = userDriver;
	}

	public Date getTimeReadyStart() {
		return timeReadyStart;
	}
	public void setTimeReadyStart(Date timeReadyStart) {
		this.timeReadyStart = timeReadyStart;
	}
	public Date getTimeAtTarget() {
		return timeAtTarget;
	}
	public void setTimeAtTarget(Date timeAtTarget) {
		this.timeAtTarget = timeAtTarget;
	}
	public Date getTimeReadyFinish() {
		return timeReadyFinish;
	}
	public void setTimeReadyFinish(Date timeReadyFinish) {
		this.timeReadyFinish = timeReadyFinish;
	}

	public String getBookCarId() {
		return bookCarId;
	}

	public void setBookCarId(String bookCarId) {
		this.bookCarId = bookCarId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserDriver() {
		return userDriver;
	}

	public void setUserDriver(String userDriver) {
		this.userDriver = userDriver;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}
	
	
}
