package com.viettel.vtnet360.vt07.vt070000.entity;

import java.io.Serializable;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class SlotDetail extends BaseEntity implements Serializable {
	private long slotId;
	private long rackId;
	private int height;
	private String qrCode;
	private int status;//0: Con trong, 1: Da dat thung
	private boolean delFlag;//false: Chua xoa, true: danh dau la da bi xoa
	private boolean changed;
	private int updatedNum;
	private String error;
	
	public SlotDetail() {
	}
	
	public SlotDetail(long rackId, int height, String qrCode, int status, boolean delFlag) {
		super();
		this.rackId = rackId;
		this.height = height;
		this.qrCode = qrCode;
		this.status = status;
		this.delFlag = delFlag;
	}

	public long getSlotId() {
		return slotId;
	}
	public void setSlotId(long slotId) {
		this.slotId = slotId;
	}
	public long getRackId() {
		return rackId;
	}
	public void setRackId(long rackId) {
		this.rackId = rackId;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
}
