package com.viettel.vtnet360.vt05.vt050013.entity;

/**
 * @author DuyNK
 * 
 */
public class VT050013StationeryParam {

	/** STATIONERY_ITEMS.STATIONERY_ID */
	private String stationeryID;

	/** quantity of stationery request */
	private int quantity;

	public VT050013StationeryParam() {

	}

	public VT050013StationeryParam(String stationeryID, int quantity) {
		super();
		this.stationeryID = stationeryID;
		this.quantity = quantity;
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
}
