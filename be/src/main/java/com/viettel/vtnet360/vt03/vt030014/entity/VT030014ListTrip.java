package com.viettel.vtnet360.vt03.vt030014.entity;

import java.util.Date;

/**
 * Class entity get trip VT030014
 * 
 * @author ThangBT 12/09/2018
 *
 */
public class VT030014ListTrip {

	/** id of car booking */
	private String carBookingId;

	/** name of employee who books car */
	private String empName;

	/** phone number of employee */
	private String empPhone;

	/** name of driver */
	private String driverName;

	/** type of car */
	private String carType;

	/** license plate */
	private String licensePlate;

	/** name of team car */
	private String teamCarName;

	/** name of unit */
	private String unitName;

	/** 3 levels of unit */
	private String detailUnit;

	/** start date search */
	private Date startDate;

	/** end date search */
	private Date endDate;
	
	/** realTime start */
	private Date timeReadyStart;
	
	/** realTime end */
	private Date timeReadyEnd;

	/** number of passenger */
	private int numOfPassenger;

	/** status of trips */
	private int statusTrips;

	/** rate of trip */
	private int rating;

	private String reasonRefuse ;
	
	
	/** feedback of passenger */
	private String feedback;

	public VT030014ListTrip() {
		super();
	}

	public VT030014ListTrip(String carBookingId, String empName, String empPhone, String driverName, String carType,
			String licensePlate, String teamCarName, String unitName, String detailUnit, Date startDate, Date endDate,
			Date timeReadyStart, Date timeReadyEnd,
			int numOfPassenger, int statusTrips, int rating, String feedback,String reasonRefuse  ) {
		super();
		this.carBookingId = carBookingId;
		this.empName = empName;
		this.empPhone = empPhone;
		this.driverName = driverName;
		this.carType = carType;
		this.licensePlate = licensePlate;
		this.teamCarName = teamCarName;
		this.unitName = unitName;
		this.detailUnit = detailUnit;
		this.startDate = startDate;
		this.endDate = endDate;
		this.timeReadyStart = timeReadyStart;
		this.timeReadyEnd = timeReadyEnd;
		this.numOfPassenger = numOfPassenger;
		this.statusTrips = statusTrips;
		this.rating = rating;
		this.feedback = feedback;
		this.reasonRefuse = reasonRefuse ;
	}

	
	public String getReasonRefuse() {
		return reasonRefuse;
	}

	public void setReasonRefuse(String reasonRefuse) {
		this.reasonRefuse = reasonRefuse;
	}

	public String getCarBookingId() {
		return carBookingId;
	}

	public void setCarBookingId(String carBookingId) {
		this.carBookingId = carBookingId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getTeamCarName() {
		return teamCarName;
	}

	public void setTeamCarName(String teamCarName) {
		this.teamCarName = teamCarName;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getNumOfPassenger() {
		return numOfPassenger;
	}

	public void setNumOfPassenger(int numOfPassenger) {
		this.numOfPassenger = numOfPassenger;
	}

	public int getStatusTrips() {
		return statusTrips;
	}

	public void setStatusTrips(int statusTrips) {
		this.statusTrips = statusTrips;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Date getTimeReadyStart() {
		return timeReadyStart;
	}

	public void setTimeReadyStart(Date timeReadyStart) {
		this.timeReadyStart = timeReadyStart;
	}

	public Date getTimeReadyEnd() {
		return timeReadyEnd;
	}

	public void setTimeReadyEnd(Date timeReadyEnd) {
		this.timeReadyEnd = timeReadyEnd;
	}
}
