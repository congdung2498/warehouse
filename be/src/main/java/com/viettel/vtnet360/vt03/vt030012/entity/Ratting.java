package com.viettel.vtnet360.vt03.vt030012.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class Ratting extends BaseEntity {
	private String bookCarId;
	private int numberOfStar;
	private String note;
	private String userName;
	private String updateDate;

	public Ratting() {
		// TODO Auto-generated constructor stub
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Ratting(String bookCarId, int numberOfStar, String note, String userName, String updateDate) {
		super();
		this.bookCarId = bookCarId;
		this.numberOfStar = numberOfStar;
		this.note = note;
		this.userName = userName;
		this.updateDate = updateDate;
	}

	public String getBookCarId() {
		return bookCarId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setBookCarId(String bookCarId) {
		this.bookCarId = bookCarId;
	}

	public int getNumberOfStar() {
		return numberOfStar;
	}

	public void setNumberOfStar(int numberOfStar) {
		this.numberOfStar = numberOfStar;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
