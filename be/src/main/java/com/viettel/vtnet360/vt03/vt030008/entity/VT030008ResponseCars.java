package com.viettel.vtnet360.vt03.vt030008.entity;

import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntitySystemCode;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030008ResponseCars extends VT030000EntitySystemCode {
	private String bookCarId;
	/** CARS.CAR_ID */
	private String carId;
	
	/** M_SYSTEM_CODE.CODE_NAME WITH MASTER_CLASS = S001 */
	private String type;
	
	/** M_SYSTEM_CODE.CODE_NAME WITH MASTER_CLASS = S002 */
	private String seat;
	
	private String licensePlate;
	
	/** DRIVE_CAR.EMPLOYEE_USER_NAME */
	private String userName;
	
	/** CARS.DRIVE_SQUAD_ID */
	private String driveSquadId;
	
	/** username got from Token */
	private String userAssigner;
	
	private int pageNumber;
	
	private int pageSize;
	
	public VT030008ResponseCars() {
		super();
	}
	
	public VT030008ResponseCars(String bookCarId, String carId, String type, String seat, int pageNumber, int pageSize) {
		super();
		this.carId = carId;
		this.type = type;
		this.seat = seat;
		this.setBookCarId(bookCarId);
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
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

  public String getBookCarId() {
    return bookCarId;
  }

  public void setBookCarId(String bookCarId) {
    this.bookCarId = bookCarId;
  }

}
