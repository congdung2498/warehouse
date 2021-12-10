package com.viettel.vtnet360.vt07.vt070005.entity;

public class VoucherDocVO {
	private long id;
	private long voucherId;
	private String name;
	private long folderId;
	private int type;
	private int status;
	private String levelBaomat;

	public VoucherDocVO() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLevelBaomat() {
		return levelBaomat;
	}

	public void setLevelBaomat(String levelBaomat) {
		this.levelBaomat = levelBaomat;
	}

}
