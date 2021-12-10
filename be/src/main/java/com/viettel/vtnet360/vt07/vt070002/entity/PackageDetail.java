package com.viettel.vtnet360.vt07.vt070002.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class PackageDetail extends BaseEntity implements Serializable {
	private long packageId;
	private List<PackageDocDetail> docs = new ArrayList<>();
	private String name;
	private String status;
	private String erpId;
	private String description;
	private String code;
	private List<ContractDetail> contracts;
	
	private long projectId;

	public long getPackageId() {
		return packageId;
	}

	public void setPackageId(long packageId) {
		this.packageId = packageId;
	}

	public List<PackageDocDetail> getDocs() {
		return docs;
	}

	public void setDocs(List<PackageDocDetail> docs) {
		this.docs = docs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErpId() {
		return erpId;
	}

	public void setErpId(String erpId) {
		this.erpId = erpId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public List<ContractDetail> getContracts() {
		return contracts;
	}

	public void setContracts(List<ContractDetail> contracts) {
		this.contracts = contracts;
	}
	

}
