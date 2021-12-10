package com.viettel.vtnet360.vt05.vt050004.entity;

/**
 * @author DuyNK
 *
 */
public class VT050004Stationery {

	/** ISSUES_STATIONERY_ITEMS.STATIONERY_ID */
	private String stationeryID;

	/**
	 * quantity of stationery of 1 request that HCDV request to LDDV group by
	 * STATIONERY_ID
	 */
	private int quantity;

	private String issuesStationeryItemId;
	
	
	public String getIssuesStationeryItemId() {
		return issuesStationeryItemId;
	}

	public void setIssuesStationeryItemId(String issuesStationeryItemId) {
		this.issuesStationeryItemId = issuesStationeryItemId;
	}

	public VT050004Stationery() {

	}

	public String getStationeryID() {
		return stationeryID;
	}

	public void setStationeryID(String stationeryID) {
		this.stationeryID = stationeryID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public VT050004Stationery(String stationeryID, int quantity, String issuesStationeryItemId) {
		super();
		this.stationeryID = stationeryID;
		this.quantity = quantity;
		this.issuesStationeryItemId = issuesStationeryItemId;
	}
	
}
