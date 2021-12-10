package com.viettel.vtnet360.vt07.vt070000.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class RackDetail extends BaseEntity implements Serializable {
	private long rackId;
	private int row;
	private long warehouseId;
	private String warehouseName;
	private int column;
	private int type;//-1: Chua cau hinh, 1: Duong di, 0: Gan cua, 2: Vi tri, 3:Khong xac dinh
	private boolean delFlag;//false: Chua xoa, true: danh dau la da bi xoa
	private boolean changed;
	private List<SlotDetail> slots = new ArrayList<>();
	private int updatedNum;
	private String error;
	private int printTime;
	
	public int getPrintTime() {
		return printTime;
	}

	public void setPrintTime(int printTime) {
		this.printTime = printTime;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public RackDetail() {
		
	}
	
	public RackDetail(int row, long warehouseId, int column, int type, boolean delFlag) {
		super();
		this.row = row;
		this.warehouseId = warehouseId;
		this.column = column;
		this.type = type;
		this.delFlag = delFlag;
	}

	public long getRackId() {
		return rackId;
	}
	public void setRackId(long rackId) {
		this.rackId = rackId;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public boolean isDelFlag() {
		return delFlag;
	}
	public void setDelFlag(boolean delFlag) {
		this.delFlag = delFlag;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public List<SlotDetail> getSlots() {
		return slots;
	}

	public void setSlots(List<SlotDetail> slots) {
		this.slots = slots;
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

	public String getName() {
		return "H" + ("" + (1000 + this.row)).substring(1) + "/" + "C" + ("" + (1000 + this.column)).substring(1);
	}
	
}
