package com.viettel.vtnet360.stationery.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.stationery.service.ItemModel;

public class ProcessIssuesStationery extends BaseEntity {

	private String ldReasonReject;
	private String userName;
	private String issuesStationeryApprovedId;
	private List<ItemModel> listIssuesStationeryItemId;
	private List<String> listIssueStationeryId;
	private String vptctReason;
	private Date vptctPostponeToTime;
	private String vptctReasonReject;
	private String hcdvReasonReject;
	private String issuesStationeryIdOld;

	
	public String getIssuesStationeryIdOld() {
		return issuesStationeryIdOld;
	}

	public void setIssuesStationeryIdOld(String issuesStationeryIdOld) {
		this.issuesStationeryIdOld = issuesStationeryIdOld;
	}

	public String getHcdvReasonReject() {
		return hcdvReasonReject;
	}

	public void setHcdvReasonReject(String hcdvReasonReject) {
		this.hcdvReasonReject = hcdvReasonReject;
	}

	public String getLdReasonReject() {
		return ldReasonReject;
	}

	public void setLdReasonReject(String ldReasonReject) {
		this.ldReasonReject = ldReasonReject;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIssuesStationeryApprovedId() {
		return issuesStationeryApprovedId;
	}

	public void setIssuesStationeryApprovedId(String issuesStationeryApprovedId) {
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
	}



	public List<ItemModel> getListIssuesStationeryItemId() {
		return listIssuesStationeryItemId;
	}

	public void setListIssuesStationeryItemId(List<ItemModel> listIssuesStationeryItemId) {
		this.listIssuesStationeryItemId = listIssuesStationeryItemId;
	}

	public String getVptctReason() {
		return vptctReason;
	}

	public void setVptctReason(String vptctReason) {
		this.vptctReason = vptctReason;
	}

	public Date getVptctPostponeToTime() {
		return vptctPostponeToTime;
	}

	public void setVptctPostponeToTime(Date vptctPostponeToTime) {
		this.vptctPostponeToTime = vptctPostponeToTime;
	}

	public String getVptctReasonReject() {
		return vptctReasonReject;
	}

	public void setVptctReasonReject(String vptctReasonReject) {
		this.vptctReasonReject = vptctReasonReject;
	}

	public List<String> getListIssueStationeryId() {
		return listIssueStationeryId;
	}

	public void setListIssueStationeryId(List<String> listIssueStationeryId) {
		this.listIssueStationeryId = listIssueStationeryId;
	}
	
	public ProcessIssuesStationery(String ldReasonReject, String userName, String issuesStationeryApprovedId,
			List<ItemModel> listIssuesStationeryItemId, List<String> listIssueStationeryId, String vptctReason,
			Date vptctPostponeToTime, String vptctReasonReject, String hcdvReasonReject) {
		super();
		this.ldReasonReject = ldReasonReject;
		this.userName = userName;
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
		this.listIssuesStationeryItemId = listIssuesStationeryItemId;
		this.listIssueStationeryId = listIssueStationeryId;
		this.vptctReason = vptctReason;
		this.vptctPostponeToTime = vptctPostponeToTime;
		this.vptctReasonReject = vptctReasonReject;
		this.hcdvReasonReject = hcdvReasonReject;
	}

	public ProcessIssuesStationery() {
		super();
		// TODO Auto-generated constructor stub
	}
}
