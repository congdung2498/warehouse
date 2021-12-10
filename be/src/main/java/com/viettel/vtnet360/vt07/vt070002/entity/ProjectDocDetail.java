package com.viettel.vtnet360.vt07.vt070002.entity;

import java.io.Serializable;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class ProjectDocDetail extends BaseEntity implements Serializable {
	private long projectDocId;
	private long folderId;
	private long projectId;
	private int type;
	private String name;
	private int action; //4: add to folder, 5: remove from folder
	private String error;
	public long getProjectDocId() {
		return projectDocId;
	}
	public void setProjectDocId(long projectDocId) {
		this.projectDocId = projectDocId;
	}
	public long getFolderId() {
		return folderId;
	}
	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	
}
