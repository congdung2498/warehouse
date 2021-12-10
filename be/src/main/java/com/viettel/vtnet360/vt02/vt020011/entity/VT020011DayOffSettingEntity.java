package com.viettel.vtnet360.vt02.vt020011.entity;

import java.util.Date;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK 09/08/2018
 * 
 */
public class VT020011DayOffSettingEntity extends BaseEntity {

	/** DAY_OFF_SETTING.DAY_OFF_ID */
	private String dayOffId;

	/** DAY_OFF_SETTING.DAY_OFF */
	private Date dayOff;

	/** DAY_OFF_SETTING.DESCRIPTION */
	private String description;

	/** DAY_OFF_SETTING.STATUS */
	private int status;

	/** select from which row */
	private int startRow;

	/** number of row to select */
	private int rowSize;

	/** list of years to select */
	private String[] listYears;

	public VT020011DayOffSettingEntity() {
		super();
	}

	public VT020011DayOffSettingEntity(String dayOffId, Date dayOff, String description, int status, int startRow,
			int rowSize, String[] listYears) {
		super();
		this.dayOffId = dayOffId;
		this.dayOff = dayOff;
		this.description = description;
		this.status = status;
		this.startRow = startRow;
		this.rowSize = rowSize;
		this.listYears = listYears;
	}

	public String getDayOffId() {
		return dayOffId;
	}

	public void setDayOffId(String dayOffId) {
		this.dayOffId = dayOffId;
	}

	public Date getDayOff() {
		return dayOff;
	}

	public void setDayOff(Date dayOff) {
		this.dayOff = dayOff;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public String[] getListYears() {
		return listYears;
	}

	public void setListYears(String[] listYears) {
		this.listYears = listYears;
	}
}
