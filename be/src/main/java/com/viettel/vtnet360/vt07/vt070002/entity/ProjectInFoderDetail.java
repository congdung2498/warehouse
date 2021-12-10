package com.viettel.vtnet360.vt07.vt070002.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class ProjectInFoderDetail extends BaseEntity {

	private long projectInFolderId;
	private long folderId;
	private long projectId;
	private int delFlag;
	
	public long getProjectInFolderId() {
		return projectInFolderId;
	}
	public void setProjectInFolderId(long projectInFolderId) {
		this.projectInFolderId = projectInFolderId;
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
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
}
