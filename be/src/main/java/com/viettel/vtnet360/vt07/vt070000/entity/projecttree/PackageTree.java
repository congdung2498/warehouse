package com.viettel.vtnet360.vt07.vt070000.entity.projecttree;

import java.util.List;

public class PackageTree {
	private long packageId;
	private List<ContractTree> contracts;

	public long getPackageId() {
		return packageId;
	}

	public void setPackageId(long packageId) {
		this.packageId = packageId;
	}

	public List<ContractTree> getContracts() {
		return contracts;
	}

	public void setContracts(List<ContractTree> contracts) {
		this.contracts = contracts;
	}

}
