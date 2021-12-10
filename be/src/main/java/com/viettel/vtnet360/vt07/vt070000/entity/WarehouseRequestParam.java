package com.viettel.vtnet360.vt07.vt070000.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class WarehouseRequestParam extends BaseEntity {

	private String warehouseId;
	private String name;
	private String address;
	private String type; // 0: Kho tam, 1: Kho luu
	private String status; // 0: Chua hoat dong, 1: hoat dong
	private String acreage;
	private boolean delFlag;// false: Chua xoa, true: danh dau la da bi xoa
	private String rowNum;
	private String columnNum;
	private String heightNum;
	private String createdDate;
	private String updatedDate;
	/** pageNumber in one page */
	private int pageNumber;
	/** number of records in a page */
	private int pageSize;
	/** total all records */
	private int totalRecords;
	
	public WarehouseRequestParam() {
	}

	public WarehouseRequestParam(String status, boolean delFlag, int pageNumber, int pageSize, int totalRecords) {
		super();
		this.name = "";
		this.address = "";
		this.type = "";
		this.status = status;
		this.acreage = "";
		this.delFlag = delFlag;
		this.rowNum = "";
		this.columnNum = "";
		this.heightNum = "";
		this.createdDate = "";
		this.updatedDate = "";
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalRecords = totalRecords;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAcreage() {
		return acreage;
	}

	public void setAcreage(String acreage) {
		this.acreage = acreage;
	}

	public boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(boolean delFlag) {
		this.delFlag = delFlag;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(String columnNum) {
		this.columnNum = columnNum;
	}

	public String getHeightNum() {
		return heightNum;
	}

	public void setHeightNum(String heightNum) {
		this.heightNum = heightNum;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}
	
	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	@Override
	public String toString() {
		return "WarehouseRequestParam [warehouseId=" + warehouseId + ", name=" + name + ", address=" + address
				+ ", type=" + type + ", status=" + status + ", acreage=" + acreage + ", delFlag=" + delFlag
				+ ", rowNum=" + rowNum + ", columnNum=" + columnNum + ", heightNum=" + heightNum + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + ", pageNumber=" + pageNumber + ", pageSize=" + pageSize
				+ ", totalRecords=" + totalRecords + "]";
	}
	
}
