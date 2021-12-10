package com.viettel.vtnet360.vt07.vt070002.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class ContractDetail extends BaseEntity implements Serializable {
	private long contractId;
	private List<ConstructionDetail> constructions = new ArrayList<>();
	private String name;
	private long projectId;
	private String erpId;
	private List<ContractDocDetail> docs = new ArrayList<>();
	private String code;
	private String description;
	private String status;
	public long getContractId() {
		return contractId;
	}
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	public List<ConstructionDetail> getConstructions() {
		return constructions;
	}
	public void setConstructions(List<ConstructionDetail> constructions) {
		this.constructions = constructions;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public String getErpId() {
		return erpId;
	}
	public void setErpId(String erpId) {
		this.erpId = erpId;
	}
	public List<ContractDocDetail> getDocs() {
		return docs;
	}
	public void setDocs(List<ContractDocDetail> docs) {
		this.docs = docs;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	
}
