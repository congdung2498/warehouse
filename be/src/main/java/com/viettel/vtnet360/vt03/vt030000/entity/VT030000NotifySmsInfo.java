package com.viettel.vtnet360.vt03.vt030000.entity;

import java.util.Date;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030000NotifySmsInfo extends VT030000EntitySystemCode {
	/** CAR_BOOKING.CAR_BOOKING_ID */
	private String bookCarId;
	
	/** CAR_BOOKING.TYPE */
	private String type;
	
	/** CAR_BOOKING.SEAT */
	private String seat;
	
	/** CAR_BOOKING.START */
	private Date dateStart;
	
	/** CAR_BOOKING.END */
	private Date dateEnd;
	
	/** CAR_BOOKING.ROUTE_TYPE */
	private String route;
	
	/** CAR_BOOKING.START_PLACE */
	private String startPlace;
	
	/** CAR_BOOKING.TARGET_PLACE */
	private String targetPlace;
	
	/** CAR_BOOKING.DRIVER_USER */
	private String driverUser;
	
	/** CAR_BOOKING.DRIVE_USER */
	private String fullName;
	
	/** CAR_BOOKING.LICENSE_PLATE */
	private String licensePlate;

	/** CAR_BOOKING.REASON_REFUSE */
	private String reasonRefuse;
	
	/** CAR_BOOKING.REASON_ASSIGNER */
	private String reasonAssigner;
	
	public VT030000NotifySmsInfo() {
		super();
	}

	public VT030000NotifySmsInfo(String masterClassType, String masterClassSeat, String masterClassRoute, String bookCarId) {
		super(masterClassType, masterClassSeat, masterClassRoute);
		this.bookCarId = bookCarId;
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

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getTargetPlace() {
		return targetPlace;
	}

	public void setTargetPlace(String targetPlace) {
		this.targetPlace = targetPlace;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getBookCarId() {
		return bookCarId;
	}

	public void setBookCarId(String bookCarId) {
		this.bookCarId = bookCarId;
	}

	public String getDriverUser() {
		return driverUser;
	}

	public void setDriverUser(String driverUser) {
		this.driverUser = driverUser;
	}

	public String getReasonRefuse() {
		return reasonRefuse;
	}

	public void setReasonRefuse(String reasonRefuse) {
		this.reasonRefuse = reasonRefuse;
	}

	public String getReasonAssigner() {
		return reasonAssigner;
	}

	public void setReasonAssigner(String reasonAssigner) {
		this.reasonAssigner = reasonAssigner;
	}
}
