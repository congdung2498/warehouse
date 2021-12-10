package com.viettel.vtnet360.vt05.vt050011.entity;

/**
 * @author DuyNK
 *
 */
public class VT050011Stationery {

	/** ISSUES_STATIONERY_ITEMS.STATIONERY_ID */
	private String stationeryID;

	/** STATIONERY_ITEMS.STATIONERY_NAME */
	private String stationeryName;

	/** ISSUES_STATIONERY_ITEMS.TOTAL_REQUEST */
	private int quantity;

	public VT050011Stationery() {

	}

	public VT050011Stationery(String stationeryID, String stationeryName, int quantity) {
		super();
		this.stationeryID = stationeryID;
		this.stationeryName = stationeryName;
		this.quantity = quantity;
	}

	public String getStationeryID() {
		return stationeryID;
	}

	public void setStationeryID(String stationeryID) {
		this.stationeryID = stationeryID;
	}

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
