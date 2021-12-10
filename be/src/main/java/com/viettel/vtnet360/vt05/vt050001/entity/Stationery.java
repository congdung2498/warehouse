package com.viettel.vtnet360.vt05.vt050001.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

public class Stationery extends BaseEntity {
	private String stationeryId;
	private String stationeryName;
	private Double unitPrice;
	private String calUnit;
	private String calUnitId;
	private List<Integer> listStatus;
	private String userName;
	private String updateDate;
	private String createDate;
	private String updateUser;
	private int pageNumber;
	private int pageSize;
	private int image;
	
	
	public Stationery() { }


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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStationeryId() {
		return stationeryId;
	}

	public String getCalUnitId() {
		return calUnitId;
	}

	public void setCalUnitId(String calUnitId) {
		this.calUnitId = calUnitId;
	}

	public void setStationeryId(String stationeryId) {
		this.stationeryId = stationeryId;
	}

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public List<Integer> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<Integer> listStatus) {
		this.listStatus = listStatus;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getCalUnit() {
		return calUnit;
	}

	public void setCalUnit(String calUnit) {
		this.calUnit = calUnit;
	}


  public int getImage() {
    return image;
  }


  public void setImage(int image) {
    this.image = image;
  }

	
}
