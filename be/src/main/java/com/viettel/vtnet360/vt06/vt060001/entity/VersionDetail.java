package com.viettel.vtnet360.vt06.vt060001.entity;

import java.util.Date;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class VersionDetail extends BaseEntity {
	private String versionID;
	private String version;
	private String versionLink;
	private String versionType;
	private String versionDesc;
	private Integer status;

	public VersionDetail() {
		super();
	}

	public String getVersionID() {
		return versionID;
	}
	public void setVersionID(String versionID) {
		this.versionID = versionID;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersionLink() {
		return versionLink;
	}
	public void setVersionLink(String versionLink) {
		this.versionLink = versionLink;
	}
	public String getVersionType() {
		return versionType;
	}
	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}
	public String getVersionDesc() {
		return versionDesc;
	}
	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
