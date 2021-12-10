package com.viettel.vtnet360.vt05.vt050013.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050013InfoToCancelRequest extends BaseEntity {

	/** ISSUES_STATIONERY_ITEMS.STATUS */
	private int status;

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ID */
	private String issueStationeryID;

	public VT050013InfoToCancelRequest() {

	}

	public VT050013InfoToCancelRequest(int status, String issueStationeryID) {
		super();
		this.status = status;
		this.issueStationeryID = issueStationeryID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIssueStationeryID() {
		return issueStationeryID;
	}

	public void setIssueStationeryID(String issueStationeryID) {
		this.issueStationeryID = issueStationeryID;
	}
}
