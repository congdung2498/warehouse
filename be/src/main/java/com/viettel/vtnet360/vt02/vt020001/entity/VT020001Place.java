package com.viettel.vtnet360.vt02.vt020001.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK 20/09/2018
 * 
 */
public class VT020001Place extends BaseEntity {

	/** QLDV_PLACE.PLACE_CODE */
	private String placeCode;

	/** QLDV_PLACE.PLACE_CODE */
	private String placeName;

	/** description of place */
	private String placeDescription;

	/** QLDV_PLACE.STATUS */
	private int status;

	/** select from which row */
	private int startRow;

	/** number of row to select */
	private int rowSize;

	public VT020001Place() {
	}

	public VT020001Place(String placeCode, String placeName, String placeDescription, int status, int startRow,
			int rowSize) {
		super();
		this.placeCode = placeCode;
		this.placeName = placeName;
		this.placeDescription = placeDescription;
		this.status = status;
		this.startRow = startRow;
		this.rowSize = rowSize;
	}

	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceDescription() {
		return placeDescription;
	}

	public void setPlaceDescription(String placeDescription) {
		this.placeDescription = placeDescription;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}
}
