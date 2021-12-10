package com.viettel.vtnet360.vt07.vt070002.entity;

import java.io.Serializable;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class ContractDocDetail extends BaseEntity implements Serializable {
	private int type;
	private long contractDocId;
	private String name;
	private long contractId;
	private int action; //4: add to folder, 5: remove from folder
	private long folderId;
	private String error;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getContractDocId() {
		return contractDocId;
	}
	public void setContractDocId(long contractDocId) {
		this.contractDocId = contractDocId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getContractId() {
		return contractId;
	}
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public long getFolderId() {
		return folderId;
	}
	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

}
