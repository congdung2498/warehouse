package com.viettel.vtnet360.vt00.vt000000.entity;

/**
 * Class Entity VT000000EntityPlace
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT000000EntityPlace {

	/** PLACE.PLACE_ID */
	private int placeId;

	/** PLACE.PLACE_NAME */
	private String placeName;

	/** PLACE.STATUS */
	private int status;

	private int pageSize;
	private int pageNumber;

	private int getSize;
	private int fromIndex;

	public VT000000EntityPlace() {
		super();
	}

	public VT000000EntityPlace(int placeId, String placeName, int status, int pageSize, int pageNumber, int getSize,
			int fromIndex) {
		super();
		this.placeId = placeId;
		this.placeName = placeName;
		this.status = status;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.getSize = getSize;
		this.fromIndex = fromIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getGetSize() {
		return getSize;
	}

	public void setGetSize(int getSize) {
		this.getSize = getSize;
	}

	public int getFromIndex() {
		return fromIndex;
	}

	public void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
