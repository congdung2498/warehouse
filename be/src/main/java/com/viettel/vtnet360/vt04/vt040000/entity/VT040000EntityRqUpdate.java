package com.viettel.vtnet360.vt04.vt040000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class VT040000EntityRqUpdate
 * 
 * @author KienHT 27/09/2018
 * 
 */
public class VT040000EntityRqUpdate extends BaseEntity {

	/** ISSUES_SERVICE.ISSUES_SERVICE_ID */
	private String issuedServiceId;

	/**  7 ISSUED SERVICE CANCEL , 8 ISSUED SERVICE STATUS RECEIVE, 9 ISSUED SERVICE STATUS REJECT 10 ratting*/
	private int flag;

	/** user Reason cancel */
	private String userReason;

	/** ISSUES_SERVICE.RATTING */
	private int ratting;

	/** ISSUES_SERVICE.FEEDBACK */
	private String feedBack;
	
	/** ISSUES_SERVICE.RATING_TO_USER */
	private int rattingToUser;
	
	/** ISSUES_SERVICE.FEEDBACK_TO_USER */
	private String feedBackToUser;

	public VT040000EntityRqUpdate() {
		super();
	}

	public VT040000EntityRqUpdate(String issuedServiceId, int flag, String userReason, int ratting, String feedBack) {
		super();
		this.issuedServiceId = issuedServiceId;
		this.flag = flag;
		this.userReason = userReason;
		this.ratting = ratting;
		this.feedBack = feedBack;
	}

	public String getIssuedServiceId() {
		return issuedServiceId;
	}

	public void setIssuedServiceId(String issuedServiceId) {
		this.issuedServiceId = issuedServiceId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getUserReason() {
		return userReason;
	}

	public void setUserReason(String userReason) {
		this.userReason = userReason;
	}

	public int getRatting() {
		return ratting;
	}

	public void setRatting(int ratting) {
		this.ratting = ratting;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

  public int getRattingToUser() {
    return rattingToUser;
  }

  public void setRattingToUser(int rattingToUser) {
    this.rattingToUser = rattingToUser;
  }

  public String getFeedBackToUser() {
    return feedBackToUser;
  }

  public void setFeedBackToUser(String feedBackToUser) {
    this.feedBackToUser = feedBackToUser;
  }

	
}
