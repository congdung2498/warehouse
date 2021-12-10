package com.viettel.vtnet360.vt05.vt050013.entity;

import java.util.List;

/**
 * @author DuyNK
 *
 */
public class VT050013DataResponse {

	/** ISSUES_STATIONERY.ISSUE_LOCATION */
	private int placeID;

	/** QLDV_PLACE.PLACE_NAME */
	private String placeName;

	/** ISSUES_STATIONERY.NOTE */
	private String message;
	
	/** ISSUES_STATIONERY.STATUS */
	private int status;

	/** ISSUES_STATIONERY.RATING */
	private int rating;

	/** ISSUES_STATIONERY.FEEDBACK */
	private String feedback;

	/** list ISSUES_STATIONERY_ITEMS */
	private List<VT050013IssuesStationeryItemDetail> listItem;

	public VT050013DataResponse() {

	}

	public VT050013DataResponse(int placeID, String placeName, String message, int status, int rating, String feedback,
			List<VT050013IssuesStationeryItemDetail> listItem) {
		super();
		this.placeID = placeID;
		this.placeName = placeName;
		this.message = message;
		this.status = status;
		this.rating = rating;
		this.feedback = feedback;
		this.listItem = listItem;
	}

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public List<VT050013IssuesStationeryItemDetail> getListItem() {
		return listItem;
	}

	public void setListItem(List<VT050013IssuesStationeryItemDetail> listItem) {
		this.listItem = listItem;
	}
}