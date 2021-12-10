package com.viettel.vtnet360.vt03.vt030009.entity;

import java.util.Date;

public class VT030009RequestGetUser {
	private String carId;
	private Date dateStart;
	public VT030009RequestGetUser(String carId, Date dateStart) {
		super();
		this.carId = carId;
		this.dateStart = dateStart;
	}
	public VT030009RequestGetUser() {
		super();
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	
	

}
