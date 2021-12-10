package com.viettel.vtnet360.vt07.vt070002.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class ConstructionDetail extends BaseEntity implements Serializable {
	private String code;
	private long contractId;
	private long folderId;
	private String name;
	private List<ConstructionDocDetail> docs = new ArrayList<>();
	private String description;
	private long projectId;
	private String status;
	private long constructionId;
	private String erpId;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getContractId() {
		return contractId;
	}
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	public long getFolderId() {
		return folderId;
	}
	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ConstructionDocDetail> getDocs() {
		return docs;
	}
	public void setDocs(List<ConstructionDocDetail> docs) {
		this.docs = docs;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getConstructionId() {
		return constructionId;
	}
	public void setConstructionId(long constructionId) {
		this.constructionId = constructionId;
	}
	public String getErpId() {
		return erpId;
	}
	public void setErpId(String erpId) {
		this.erpId = erpId;
	}
}
