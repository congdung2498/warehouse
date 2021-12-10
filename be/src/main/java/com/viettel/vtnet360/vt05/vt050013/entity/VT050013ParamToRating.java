package com.viettel.vtnet360.vt05.vt050013.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050013ParamToRating extends BaseEntity {

	/** ISSUES_STATIONERY.ISSUE_STATIONERY_ID */
	private String requestID;

	/** ISSUES_STATIONERY.RATING */
	private int rating;

	/** ISSUES_STATIONERY.FEEDBACK */
	private String feedback;

	public VT050013ParamToRating() {

	}

	public VT050013ParamToRating(String requestID, int rating, String feedback) {
		super();
		this.requestID = requestID;
		this.rating = rating;
		this.feedback = feedback;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
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
}
