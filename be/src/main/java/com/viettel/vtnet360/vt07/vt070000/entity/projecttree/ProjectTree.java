package com.viettel.vtnet360.vt07.vt070000.entity.projecttree;

import java.util.List;

public class ProjectTree {
	private long projectId;
	private List<PackageTree> packages;

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public List<PackageTree> getPackages() {
		return packages;
	}

	public void setPackages(List<PackageTree> packages) {
		this.packages = packages;
	}


}
