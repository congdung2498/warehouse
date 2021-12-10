package com.viettel.vtnet360.vt05.vt050005.entity;

/**
 * @author DuyNK
 *
 */
public class VT050005PlaceIDOfIssuesStationeryApprove {

	/** placeID of Request (1 request = 1 record ISSUES_STATIONERY_APPROVED) */
	private int placeID;
	
	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private String issuesStationeryApprovedID;
	
	public VT050005PlaceIDOfIssuesStationeryApprove() {
	
	}

	public VT050005PlaceIDOfIssuesStationeryApprove(int placeID, String issuesStationeryApprovedID) {
		super();
		this.placeID = placeID;
		this.issuesStationeryApprovedID = issuesStationeryApprovedID;
	}

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	public String getIssuesStationeryApprovedID() {
		return issuesStationeryApprovedID;
	}

	public void setIssuesStationeryApprovedID(String issuesStationeryApprovedID) {
		this.issuesStationeryApprovedID = issuesStationeryApprovedID;
	}
}
