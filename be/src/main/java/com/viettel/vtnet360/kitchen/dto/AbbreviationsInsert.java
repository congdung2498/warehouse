package com.viettel.vtnet360.kitchen.dto;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

public class AbbreviationsInsert extends BaseEntity {

	private String kitchenId;
	private String note;
	private int unitId;
	private String shortName;
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;
	private String kitchenIdChange ;
	
	public String getKitchenIdChange() {
		return kitchenIdChange;
	}

	public void setKitchenIdChange(String kitchenIdChange) {
		this.kitchenIdChange = kitchenIdChange;
	}

	public String getKitchenId() {
		return kitchenId;
	}

	public void setKitchenId(String kitchenId) {
		this.kitchenId = kitchenId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	

	public AbbreviationsInsert(String kitchenId, String note, int unitId, String shortName, String createUser,
			Date createDate, String updateUser, Date updateDate, String kitchenIdChange) {
		super();
		this.kitchenId = kitchenId;
		this.note = note;
		this.unitId = unitId;
		this.shortName = shortName;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
		this.kitchenIdChange = kitchenIdChange;
	}

	public AbbreviationsInsert() {
		super();
		// TODO Auto-generated constructor stub
	}

}
