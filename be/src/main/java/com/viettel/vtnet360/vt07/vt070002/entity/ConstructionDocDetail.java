package com.viettel.vtnet360.vt07.vt070002.entity;

import java.io.Serializable;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class ConstructionDocDetail extends BaseEntity implements Serializable {
	private long constructionDocId;
	private String error;
	private long constructionId;
	private String name;
	private long folderId;
	private int action; //4: add to folder, 5: remove from folder
	private int type;
	public long getConstructionDocId() {
		return constructionDocId;
	}
	public void setConstructionDocId(long constructionDocId) {
		this.constructionDocId = constructionDocId;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public long getConstructionId() {
		return constructionId;
	}
	public void setConstructionId(long constructionId) {
		this.constructionId = constructionId;
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
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	
}
