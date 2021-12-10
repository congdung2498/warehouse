package com.viettel.vtnet360.vt05.vt050013.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class VT050013ParamToRatingVPTCT extends BaseEntity {


	/** ISSUES_STATIONERY.ISSUE_STATIONERY_ID */
	private String requestId;

	/** ISSUES_STATIONERY.RATING */
	private int rating;

	/** ISSUES_STATIONERY.FEEDBACK */
	private String feedback;

	

	

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
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



  public VT050013ParamToRatingVPTCT(String requestId, int rating, String feedback) {
    super();
    this.requestId = requestId;
    this.rating = rating;
    this.feedback = feedback;
  }

  public VT050013ParamToRatingVPTCT() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
