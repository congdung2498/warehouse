package com.viettel.vtnet360.stationery.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

public class CanceIssuesStationey extends BaseEntity {

	private String issuesStationeryId ;
	
	private List<StationeryEmployee> listIssueStationeryItemId ;

	private String userName ;
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIssuesStationeryId() {
		return issuesStationeryId;
	}

	public void setIssuesStationeryId(String issuesStationeryId) {
		this.issuesStationeryId = issuesStationeryId;
	}

	public List<StationeryEmployee> getListIssueStationeryItemId() {
		return listIssueStationeryItemId;
	}

	public void setListIssueStationeryItemId(List<StationeryEmployee> listIssueStationeryItemId) {
		this.listIssueStationeryItemId = listIssueStationeryItemId;
	}

	
}
