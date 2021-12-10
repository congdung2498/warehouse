package com.viettel.vtnet360.stationery.entity;

public class StationeryParamForm {
	/** STATIONERY_ITEMS.STATIONERY_ID */
	private String stationeryId;

	/** quantity of stationery request */
	private int quantity;
	private Double unitPrice ;
	public String getStationeryId() {
		return stationeryId;
	}
	public void setStationeryId(String stationeryId) {
		this.stationeryId = stationeryId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public StationeryParamForm(String stationeryId, int quantity, Double unitPrice) {
		super();
		this.stationeryId = stationeryId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}
	public StationeryParamForm() {
		super();
		// TODO Auto-generated constructor stub
	} 
	
	
}
