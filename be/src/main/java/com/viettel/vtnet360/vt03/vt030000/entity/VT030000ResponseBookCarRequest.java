package com.viettel.vtnet360.vt03.vt030000.entity;

import java.util.Date;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030000ResponseBookCarRequest extends VT030000EntityBookCar {
	/** CAR_BOOKING.CAR_BOOKING_ID */
	private String bookCarId;
	
	/** CAR_BOOKING.TYPE */
	private String typeId;
	
	/** CAR_BOOKING.SEAT */
	private String seatId;
	
	/** CAR_BOOKING.ROUTE_TYPE */
	private String routeId;
	
	/** CAR_BOOKING.START_PLACE */
	private int startPlaceId;
	
	/** CAR_BOOKING.TARGET_PLACE */
	private int targetPlaceId;
	
	/** QLDV_UNIT.UNIT_ID */
	private int unitId;
	
	/** CAR_BOOKING.REASON_REFUSE */
	private String reasonRefuse;
	
	/** QLDV_UNIT.UNIT_NAME */
	private String unitName;
	
	/** CAR_BOOKING.DRIVE_USER */
	private String fullName;
	
	/** QLDV_EMPLOYEE.PHONE_NUMBER */
	private String emplPhone;
	
	/** QLDV_EMPLOYEE.RATTING */
	private int ratting;
	
	/** QLDV_EMPLOYEE.FEEDBACK */
	private String feedback;
	
	/** carType + carSeat + licensePlate */
	private String matchingCar;
	
	/** QLDV_EMPLOYEE.DRIVE_USER */
	private String driverName;
	
	/** QLDV_EMPLOYEE.CAR_ID */
	private String carId;
	
	
	private Integer driverRating ;
	
	private String driverFeedback ;
	/** QLDV_EMPLOYEE.REASON_ASSIGNER */
	private String reasonAssigner;
	
	private String driverSquadId;
	
	/** CAR_BOOKING.DRIVE_USER */
	private String driverUserName;
	
	public VT030000ResponseBookCarRequest() {
		super();
	}




	public VT030000ResponseBookCarRequest(String bookCarId, String typeId, String seatId, String routeId,
			int startPlaceId, int targetPlaceId, int unitId, String reasonRefuse, String unitName, String fullName,
			String emplPhone, int ratting, String feedback, String matchingCar, String driverName, String carId,
			Integer driverRating, String driverFeedback, String reasonAssigner, String driverSquadId,
			String driverUserName) {
		super();
		this.bookCarId = bookCarId;
		this.typeId = typeId;
		this.seatId = seatId;
		this.routeId = routeId;
		this.startPlaceId = startPlaceId;
		this.targetPlaceId = targetPlaceId;
		this.unitId = unitId;
		this.reasonRefuse = reasonRefuse;
		this.unitName = unitName;
		this.fullName = fullName;
		this.emplPhone = emplPhone;
		this.ratting = ratting;
		this.feedback = feedback;
		this.matchingCar = matchingCar;
		this.driverName = driverName;
		this.carId = carId;
		this.driverRating = driverRating;
		this.driverFeedback = driverFeedback;
		this.reasonAssigner = reasonAssigner;
		this.driverSquadId = driverSquadId;
		this.driverUserName = driverUserName;
	}




	public VT030000ResponseBookCarRequest(String bookCarId, String reasonRefuse,
			String unitName, String fullName, String empPhone) {
		super();
		this.bookCarId = bookCarId;
		this.reasonRefuse = reasonRefuse;
		this.unitName = unitName;
		this.fullName = fullName;
		this.emplPhone = empPhone;
	}

	

	public String getBookCarId() {
		return bookCarId;
	}
	public void setBookCarId(String bookCarId) {
		this.bookCarId = bookCarId;
	}
	public String getReasonRefuse() {
		return reasonRefuse;
	}
	public void setReasonRefuse(String reasonRefuse) {
		this.reasonRefuse = reasonRefuse;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmplPhone() {
		return emplPhone;
	}
	public void setEmplPhone(String empPhone) {
		this.emplPhone = empPhone;
	}

	public int getRatting() {
		return ratting;
	}

	public void setRatting(int ratting) {
		this.ratting = ratting;
	}

	public String getFeedback() {
		return feedback;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public int getStartPlaceId() {
		return startPlaceId;
	}

	public void setStartPlaceId(int startPlaceId) {
		this.startPlaceId = startPlaceId;
	}

	public int getTargetPlaceId() {
		return targetPlaceId;
	}

	public void setTargetPlaceId(int targetPlaceId) {
		this.targetPlaceId = targetPlaceId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getMatchingCar() {
		return matchingCar;
	}

	public void setMatchingCar(String matchingCar) {
		this.matchingCar = matchingCar;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getReasonAssigner() {
		return reasonAssigner;
	}

	public void setReasonAssigner(String reasonAssigner) {
		this.reasonAssigner = reasonAssigner;
	}

	

	public Integer getDriverRating() {
		return driverRating;
	}











	public void setDriverRating(Integer driverRating) {
		this.driverRating = driverRating;
	}


	public String getDriverFeedback() {
		return driverFeedback;
	}

	public void setDriverFeedback(String driverFeedback) {
		this.driverFeedback = driverFeedback;
	}


	public String getDriverSquadId() {
		return driverSquadId;
	}


	public void setDriverSquadId(String driverSquadId) {
		this.driverSquadId = driverSquadId;
	}




	public String getDriverUserName() {
		return driverUserName;
	}




	public void setDriverUserName(String driverUserName) {
		this.driverUserName = driverUserName;
	}
	
}
