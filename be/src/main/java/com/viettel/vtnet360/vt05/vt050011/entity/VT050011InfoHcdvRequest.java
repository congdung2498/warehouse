package com.viettel.vtnet360.vt05.vt050011.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT050011InfoHcdvRequest {

	/** ISSUES_STATIONERY_APPROVED.HCDV_USERNAME */
	private String hcdvUserName;
	
	/** ISSUES_STATIONERY_APPROVED.HCDV_MESSAGE */
	private String hcdvMessage;
	
	/** ISSUES_STATIONERY_APPROVED.CREATE_DATE */
	private Date dateRequest;
	
	public VT050011InfoHcdvRequest() {
	
	}

	public VT050011InfoHcdvRequest(String hcdvUserName, String hcdvMessage, Date dateRequest) {
		super();
		this.hcdvUserName = hcdvUserName;
		this.hcdvMessage = hcdvMessage;
		this.dateRequest = dateRequest;
	}

	public String getHcdvUserName() {
		return hcdvUserName;
	}

	public void setHcdvUserName(String hcdvUserName) {
		this.hcdvUserName = hcdvUserName;
	}

	public String getHcdvMessage() {
		return hcdvMessage;
	}

	public void setHcdvMessage(String hcdvMessage) {
		this.hcdvMessage = hcdvMessage;
	}

	public Date getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
	}
}
