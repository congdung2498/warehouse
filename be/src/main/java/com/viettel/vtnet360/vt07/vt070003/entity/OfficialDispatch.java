package com.viettel.vtnet360.vt07.vt070003.entity;

public class OfficialDispatch {
	private int officialDispatchId;
	private String name;
	private int folderId;
	private int status;
	private int type;

	public int getOfficialDispatchId() {
		return officialDispatchId;
	}

	public void setOfficialDispatchId(int officialDispatchId) {
		this.officialDispatchId = officialDispatchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
