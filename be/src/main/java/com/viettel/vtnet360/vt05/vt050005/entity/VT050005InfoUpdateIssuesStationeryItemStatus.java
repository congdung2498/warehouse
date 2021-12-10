package com.viettel.vtnet360.vt05.vt050005.entity;

import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050005InfoUpdateIssuesStationeryItemStatus extends BaseEntity {

	/** ISSUES_STATIONERY_ITEMS.STATUS */
	private int status;

	/** list ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_APPROVED_ID */
	private List<String> listIssuesStationeryApproveID;

	/** ISSUES_STATIONERY_ITEMS.STATUS now */
	private int statusNow;

	public VT050005InfoUpdateIssuesStationeryItemStatus() {

	}

	public VT050005InfoUpdateIssuesStationeryItemStatus(int status, List<String> listIssuesStationeryApproveID,
			int statusNow) {
		super();
		this.status = status;
		this.listIssuesStationeryApproveID = listIssuesStationeryApproveID;
		this.statusNow = statusNow;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<String> getListIssuesStationeryApproveID() {
		return listIssuesStationeryApproveID;
	}

	public void setListIssuesStationeryApproveID(List<String> listIssuesStationeryApproveID) {
		this.listIssuesStationeryApproveID = listIssuesStationeryApproveID;
	}

	public int getStatusNow() {
		return statusNow;
	}

	public void setStatusNow(int statusNow) {
		this.statusNow = statusNow;
	}
}
