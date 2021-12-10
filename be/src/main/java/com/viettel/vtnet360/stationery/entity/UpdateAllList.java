package com.viettel.vtnet360.stationery.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

public class UpdateAllList extends BaseEntity {

	private String userName;
	private List<String> updateAllIssuesStationery ;

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getUpdateAllIssuesStationery() {
		return updateAllIssuesStationery;
	}

	public void setUpdateAllIssuesStationery(List<String> updateAllIssuesStationery) {
		this.updateAllIssuesStationery = updateAllIssuesStationery;
	}
	public UpdateAllList(String userName, List<String> updateAllIssuesStationery) {
		super();
		this.userName = userName;
		this.updateAllIssuesStationery = updateAllIssuesStationery;
	}

	public UpdateAllList() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
