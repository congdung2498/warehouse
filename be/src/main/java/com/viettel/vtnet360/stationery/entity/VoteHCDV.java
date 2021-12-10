package com.viettel.vtnet360.stationery.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class VoteHCDV extends BaseEntity {

	private int ratingToHcdv ;
	private String feedbackToHcdv;
	private String issuesStationeryApprovedId;
	private int ratingToVptct;
	private String feedbackToVptct;
	public int getRatingToVptct() {
		return ratingToVptct;
	}
	public void setRatingToVptct(int ratingToVptct) {
		this.ratingToVptct = ratingToVptct;
	}
	public String getFeedbackToVptct() {
		return feedbackToVptct;
	}
	public void setFeedbackToVptct(String feedbackToVptct) {
		this.feedbackToVptct = feedbackToVptct;
	}
	public int getRatingToHcdv() {
		return ratingToHcdv;
	}
	public void setRatingToHcdv(int ratingToHcdv) {
		this.ratingToHcdv = ratingToHcdv;
	}
	public String getFeedbackToHcdv() {
		return feedbackToHcdv;
	}
	public void setFeedbackToHcdv(String feedbackToHcdv) {
		this.feedbackToHcdv = feedbackToHcdv;
	}
	public String getIssuesStationeryApprovedId() {
		return issuesStationeryApprovedId;
	}
	public void setIssuesStationeryApprovedId(String issuesStationeryApprovedId) {
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
	}
	public VoteHCDV(int ratingToHcdv, String feedbackToHcdv, String issuesStationeryApprovedId, int ratingToVptct,
			String feedbackToVptct) {
		super();
		this.ratingToHcdv = ratingToHcdv;
		this.feedbackToHcdv = feedbackToHcdv;
		this.issuesStationeryApprovedId = issuesStationeryApprovedId;
		this.ratingToVptct = ratingToVptct;
		this.feedbackToVptct = feedbackToVptct;
		
	}
	public VoteHCDV() {
		super();
		// TODO Auto-generated constructor stub
	}
}
