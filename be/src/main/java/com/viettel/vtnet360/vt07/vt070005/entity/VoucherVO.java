package com.viettel.vtnet360.vt07.vt070005.entity;

import java.util.List;

public class VoucherVO {
	private long voucherId;
	private String name;
	private long folderId;
	private int type;
	private int status;
	private List<VoucherDocVO> voucherDocs;

	public VoucherVO() {
		super();
	}

	public VoucherVO(String name, long folderId, int type, int status) {
		super();
		this.name = name;
		this.folderId = folderId;
		this.type = type;
		this.status = status;
	}

	public List<VoucherDocVO> getVoucherDocs() {
		return voucherDocs;
	}

	public void setVoucherDocs(List<VoucherDocVO> voucherDocs) {
		this.voucherDocs = voucherDocs;
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

}
