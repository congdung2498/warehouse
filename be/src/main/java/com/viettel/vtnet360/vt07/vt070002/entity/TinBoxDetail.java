package com.viettel.vtnet360.vt07.vt070002.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class TinBoxDetail extends BaseEntity implements Serializable {
	private long tinBoxId;
	private String name;
	private String qrCode;
	private String description;
	private int status;
	private long warehouseId;
	private String warehouseName;
	private long slotId;
	private String slotQrCode;
	private int index;
	private String mngUser;
	private int action; //1: create, 2: edit, 3: delete
	private String error;
	private int delFlag;
	private List<FolderDetail> folders = new ArrayList<>();
	private int totalFolder;
	private String type;
	private int warehouseType;
	private List<String> roles = new ArrayList<String>();
	private int unit;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getTinBoxId() {
		return tinBoxId;
	}
	public void setTinBoxId(long tinBoxId) {
		this.tinBoxId = tinBoxId;
	}

	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
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
	public long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public long getSlotId() {
		return slotId;
	}
	public void setSlotId(long slotId) {
		this.slotId = slotId;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getMngUser() {
		return mngUser;
	}
	public void setMngUser(String mngUser) {
		this.mngUser = mngUser;
	}
	public List<FolderDetail> getFolders() {
		return folders;
	}
	public void setFolders(List<FolderDetail> folders) {
		this.folders = folders;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalFolder() {
		return totalFolder;
	}
	public void setTotalFolder(int totalFolder) {
		this.totalFolder = totalFolder;
	}
	public String getSlotQrCode() {
		return slotQrCode;
	}
	public void setSlotQrCode(String slotQrCode) {
		this.slotQrCode = slotQrCode;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public int getWarehouseType() {
		return warehouseType;
	}
	public void setWarehouseType(int warehouseType) {
		this.warehouseType = warehouseType;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	

}
