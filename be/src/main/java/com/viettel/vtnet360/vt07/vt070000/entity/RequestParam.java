package com.viettel.vtnet360.vt07.vt070000.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class RequestParam extends BaseEntity {
	private long warehouseId;
	private int level; //0: Get warehouse data, 1: get until rack data, 2: get until slot data
	private String warehouseName;
	private String qrCode;
	private int pageNo;
	
	public RequestParam(long warehouseId, int level) {
		this.warehouseId = warehouseId;
		this.level = level;
	}
	
	public RequestParam() {
		
	}

	public long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
}
