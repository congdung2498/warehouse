package com.viettel.vtnet360.vt07.vt070000.entity;

import com.viettel.vtnet360.vt07.vt070002.entity.TinBoxDetail;

public class TinBoxSearchResult {
	
	private String warehouseName;
	private TinBoxDetail tinBox;
	private String tinBoxLocation;
	private String slotQrCode;
	
	public TinBoxSearchResult() {}
	
	public TinBoxSearchResult(String warehouseName, TinBoxDetail tinBox) {
		super();
		this.warehouseName = warehouseName;
		this.tinBox = tinBox;
		this.tinBoxLocation = "";
		this.slotQrCode = "";
	}
	
	/**
	 * @return the slotQrCode
	 */
	public String getSlotQrCode() {
		return slotQrCode;
	}

	/**
	 * @param slotQrCode the slotQrCode to set
	 */
	public void setSlotQrCode(String slotQrCode) {
		this.slotQrCode = slotQrCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}
	
	/**
	 * @return the tinBoxLocation
	 */
	public String getTinBoxLocation() {
		return tinBoxLocation;
	}

	/**
	 * @param tinBoxLocation the tinBoxLocation to set
	 */
	public void setTinBoxLocation(String tinBoxLocation) {
		this.tinBoxLocation = tinBoxLocation;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public TinBoxDetail getTinBox() {
		return tinBox;
	}

	public void setTinBox(TinBoxDetail tinBox) {
		this.tinBox = tinBox;
	}
	
}
