package com.viettel.vtnet360.vt06.vt060001.entity;

public class SearchCondition {
	private String version;
	private String versionLink;
	private String versionType;
	private String versionDesc;
	private Integer status;
	
	public SearchCondition() {
		super();
	}
	
	public SearchCondition(String version, String versionLink, String versionType, String versionDesc, Integer status) {
		super();
		this.version = version;
		this.versionLink = versionLink;
		this.versionType = versionType;
		this.versionDesc = versionDesc;
		this.status = status;
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
