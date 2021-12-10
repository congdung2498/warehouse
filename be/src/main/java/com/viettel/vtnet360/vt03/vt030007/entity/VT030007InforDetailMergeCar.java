package com.viettel.vtnet360.vt03.vt030007.entity;

import java.util.Date;

public class VT030007InforDetailMergeCar {
	private Integer totalPassage;
	private Date startDate;
	private Date endDate;
	
	public VT030007InforDetailMergeCar(Integer totalPassage, Date startDate, Date endDate) {
		super();
		this.totalPassage = totalPassage;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public VT030007InforDetailMergeCar() {
		super();
	}

	public Integer getTotalPassage() {
		return totalPassage;
	}

	public void setTotalPassage(Integer totalPassage) {
		this.totalPassage = totalPassage;
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
	
	
}
