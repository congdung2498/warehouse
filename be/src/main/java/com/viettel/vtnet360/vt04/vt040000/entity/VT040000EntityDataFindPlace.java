package com.viettel.vtnet360.vt04.vt040000.entity;

import java.util.Date;

/**
 * Class VT040000EntityDataFindPlace
 * 
 * @author KienHT 27/09/2018
 * 
 */
public class VT040000EntityDataFindPlace {

	/** QLDV_PLACE.PLACE_ID */
	private int placeId;

	/** QLDV_PLACE.PLACE_CODE */
	private String placeCode;

	/** QLDV_PLACE.PLACE_NAME */
	private String palceName;

	/** QLDV_PLACE.AREA */
	private String area;

	/** QLDV_PLACE.DESCRIPTION */
	private String description;

	/** QLDV_PLACE.STATUS */
	private int status;

	/** QLDV_PLACE.UPDATE_USER */
	private String updateUser;

	/** QLDV_PLACE.UPDATE_DATE */
	private Date updateDate;

	public VT040000EntityDataFindPlace() {
		super();
	}

	public VT040000EntityDataFindPlace(int placeId, String placeCode, String palceName, String area, String description,
			int status, String updateUser, Date updateDate) {
		super();
		this.placeId = placeId;
		this.placeCode = placeCode;
		this.palceName = palceName;
		this.area = area;
		this.description = description;
		this.status = status;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	public String getPalceName() {
		return palceName;
	}

	public void setPalceName(String palceName) {
		this.palceName = palceName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

}
