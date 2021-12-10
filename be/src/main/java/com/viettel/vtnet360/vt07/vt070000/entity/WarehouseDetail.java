package com.viettel.vtnet360.vt07.vt070000.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class WarehouseDetail extends BaseEntity implements Serializable {
	private long warehouseId;
	private String name;
	private String address;
	private int type; // 0: Kho tam, 1: Kho luu
	private int status; // 0: Chua hoat dong, 1: hoat dong
	private double acreage;
	private boolean delFlag;// false: Chua xoa, true: danh dau la da bi xoa
	private int rowNum;
	private int columnNum;
	private int heightNum;
	/** pageNumber in one page */
	private int pageNumber;

	/** number of records in a page */
	private int pageSize;

	/** total all records */
	private int totalRecords;
	private boolean changed;
	private List<RackDetail> racks = new ArrayList<>();
	private int updatedNum;
	private String error;
	private int totalTinBox;
	private int totalFolder;

	public long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(long warehouseId) {
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getAcreage() {
		return acreage;
	}

	public void setAcreage(double acreage) {
		this.acreage = acreage;
	}

	public boolean isDelFlag() {
		return delFlag;
	}

	public void setDelFlag(boolean delFlag) {
		this.delFlag = delFlag;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}

	public int getHeightNum() {
		return heightNum;
	}

	public void setHeightNum(int heightNum) {
		this.heightNum = heightNum;
	}
	public boolean isChanged() {
		return changed;
	}
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	public List<RackDetail> getRacks() {
		return racks;
	}
	public void setRacks(List<RackDetail> racks) {
		this.racks = racks;
	}
	public int getUpdatedNum() {
		return updatedNum;
	}
	public void setUpdatedNum(int updatedNum) {
		this.updatedNum = updatedNum;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
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

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalTinBox() {
		return totalTinBox;
	}

	public void setTotalTinBox(int totalTinBox) {
		this.totalTinBox = totalTinBox;
	}

	public int getTotalFolder() {
		return totalFolder;
	}

	public void setTotalFolder(int totalFolder) {
		this.totalFolder = totalFolder;
	}

	@Override
	public String toString() {
		return "WarehouseDetail [warehouseId=" + warehouseId + ", name=" + name + ", address=" + address + ", type="
				+ type + ", status=" + status + ", acreage=" + acreage + ", delFlag=" + delFlag + ", rowNum=" + rowNum
				+ ", columnNum=" + columnNum + ", heightNum=" + heightNum + ", createdDate=" + createDate.toLocaleString()
				+ ", updatedDate=" + updateDate.toLocaleString() + ", pageNumber=" + pageNumber + ", pageSize=" + pageSize
				+ ", totalRecords=" + totalRecords + "]";
	}

}
