package com.viettel.vtnet360.vt05.vt050005.entity;

import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050005InfoToUpdateIssuesStationeryApproveStatus extends BaseEntity {

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private int status;

	/** ISSUES_STATIONERY_APPROVED.REASON_REJECT */
	private String reasonReject;

	/** list ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private List<String> listIssuesStationeryApproveID;

	/** ISSUES_STATIONERY_APPROVED.STATUS now */
	private int statusNow;

	public VT050005InfoToUpdateIssuesStationeryApproveStatus() {

	}

	public VT050005InfoToUpdateIssuesStationeryApproveStatus(int status, String reasonReject,
			List<String> listIssuesStationeryApproveID, int statusNow) {
		super();
		this.status = status;
		this.reasonReject = reasonReject;
		this.listIssuesStationeryApproveID = listIssuesStationeryApproveID;
		this.statusNow = statusNow;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReasonReject() {
		return reasonReject;
	}

	public void setReasonReject(String reasonReject) {
		this.reasonReject = reasonReject;
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