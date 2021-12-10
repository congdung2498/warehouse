package com.viettel.vtnet360.vt07.vt070000.entity.projecttree;

import java.util.List;

public class ContractTree {
	private long contractId;
	private List<ConstructionTree> constructions;

	public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	public List<ConstructionTree> getConstructions() {
		return constructions;
	}

	public void setConstructions(List<ConstructionTree> constructions) {
		this.constructions = constructions;
	}

}
