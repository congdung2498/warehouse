package com.viettel.vtnet360.vt07.vt070002.entity;

import java.io.Serializable;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class PackageDocDetail extends BaseEntity implements Serializable {
	private long packageDocId;
	private int action; //4 add to folder, 5: remove from folder
	private long packageId;
	private String error;
	private String name;
	private long folderId;
	private int type;
	public long getPackageDocId() {
		return packageDocId;
	}
	public void setPackageDocId(long packageDocId) {
		this.packageDocId = packageDocId;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public long getPackageId() {
		return packageId;
	}
	public void setPackageId(long packageId) {
		this.packageId = packageId;
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
	public long getFolderId() {
		return folderId;
	}
	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}


}
