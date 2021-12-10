package com.viettel.vtnet360.vt03.vt030008.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030008DriveCarInfo extends BaseEntity {
	/** CAR_BOOKING.CAR_BOOKING_ID */
	private String bookCarId;
	
	/** DRIVE_CAR.DRIVE_SQUAD_ID */
	private String driveSquadId;
	
	/** DRIVE_CAR.EMPLOYEE_USER_NAME */
	private String userName;
	
	/** CAR_BOOKING.USER_ASSIGNER */
	private String userAssigner;
	
	/** DRIVE_CAR.CAR_ID */
	private String carId;
	
	/**  DRIVE_CAR.PROCESS_STATUS */
	private int processStatus;
	
	/** CAR_BOOKING.EMPLOYEE_USER_NAME */
	private String userRequest;
	
	/** CAR_BOOKING.REASON_REFUSE */
	private String reasonRefuse;
	
	private List<String> listBookCarId;
	
	private Integer carBookingType;
	
	public VT030008DriveCarInfo() {
		super();	
	}
	

	public VT030008DriveCarInfo(String bookCarId, String driveSquadId, String userName, String carId,
			int processStatus, String userRequest, String reasonRefuse) {
		super();
		this.bookCarId = bookCarId;
		this.driveSquadId = driveSquadId;
		this.userName = userName;
		this.carId = carId;
		this.processStatus = processStatus;
		this.userRequest = userRequest;
		this.reasonRefuse = reasonRefuse;
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

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public int getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}


	public String getDriveSquadId() {
		return driveSquadId;
	}


	public void setDriveSquadId(String driveSquadId) {
		this.driveSquadId = driveSquadId;
	}


	public String getUserAssigner() {
		return userAssigner;
	}


	public void setUserAssigner(String userAssigner) {
		this.userAssigner = userAssigner;
	}


	public String getUserRequest() {
		return userRequest;
	}


	public void setUserRequest(String userRequest) {
		this.userRequest = userRequest;
	}


	public String getReasonRefuse() {
		return reasonRefuse;
	}


	public void setReasonRefuse(String reasonRefuse) {
		this.reasonRefuse = reasonRefuse;
	}
	
	public List<String> getListBookCarId() {
		return listBookCarId;
	}

	public void setListBookCarId(List<String> listBookCarId) {
		this.listBookCarId = listBookCarId;
	}


	public Integer getCarBookingType() {
		return carBookingType;
	}


	public void setCarBookingType(Integer carBookingType) {
		this.carBookingType = carBookingType;
	}
	
	
	
}
