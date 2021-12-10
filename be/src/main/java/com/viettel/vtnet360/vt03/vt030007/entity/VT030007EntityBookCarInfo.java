package com.viettel.vtnet360.vt03.vt030007.entity;

import java.util.Date;

import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityBookCar;
/**
 * 
 * @author CuongHD
 *
 */
public class VT030007EntityBookCarInfo extends VT030000EntityBookCar  {
	
	/** QLDV_EMPLOYEE.UNIT_ID*/
	private int unitId;
	
	/** QLDV_UNIT.UNIT_NAME*/
	private String unitName;
	
	/** QLDV_EMPLOYEE.FULL_NAME*/
	private String fullName;
	
	/** QLDV_EMPLOYEE.PHONE_NUMBER*/
	private String emplPhone;
	
	/** CAR_BOOKING.TIME_START */
	private Date timeReadyStart;

	/** CAR_BOOKING.TIME_START */
	private Date timeReadyFinsh;
	
	/** CAR_BOOKING.RATTING */
	private int ratting;
	
	/** CAR_BOOKING.LICENSE_PLATE */
	private String licensePlate;
	
	public VT030007EntityBookCarInfo() {
	}

	

	public VT030007EntityBookCarInfo(int unitId, String unitName, String fullName, String emplPhone,
			Date timeReadyStart, Date timeReadyFinsh, int ratting) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
		this.fullName = fullName;
		this.emplPhone = emplPhone;
		this.timeReadyStart = timeReadyStart;
		this.timeReadyFinsh = timeReadyFinsh;
		this.ratting = ratting;
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

	public void setEmplPhone(String emplPhone) {
		this.emplPhone = emplPhone;
	}

	public Date getTimeReadyStart() {
		return timeReadyStart;
	}

	public void setTimeReadyStart(Date timeReadyStart) {
		this.timeReadyStart = timeReadyStart;
	}

	public Date getTimeReadyFinsh() {
		return timeReadyFinsh;
	}

	public void setTimeReadyFinsh(Date timeReadyFinsh) {
		this.timeReadyFinsh = timeReadyFinsh;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getRatting() {
		return ratting;
	}

	public void setRatting(int ratting) {
		this.ratting = ratting;
	}
	
	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
}
