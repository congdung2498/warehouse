package com.viettel.vtnet360.vt07.vt070002.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class ProjectDetail extends BaseEntity implements Serializable {
	private long projectId;

	private String description;
	private String status;

	private String code;
	private List<ProjectDocDetail> docs = new ArrayList<>();
	private List<PackageDetail> packages = new ArrayList<>();
	private List<ContractDetail> contracts = new ArrayList<>();
	private String name;
	private String erpId;
	private int type;

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<ProjectDocDetail> getDocs() {
		return docs;
	}
	public void setDocs(List<ProjectDocDetail> docs) {
		this.docs = docs;
	}
	public List<PackageDetail> getPackages() {
		return packages;
	}
	public void setPackages(List<PackageDetail> packages) {
		this.packages = packages;
	}
	public List<ContractDetail> getContracts() {
		return contracts;
	}
	public void setContracts(List<ContractDetail> contracts) {
		this.contracts = contracts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getErpId() {
		return erpId;
	}
	public void setErpId(String erpId) {
		this.erpId = erpId;
	}
	

}
