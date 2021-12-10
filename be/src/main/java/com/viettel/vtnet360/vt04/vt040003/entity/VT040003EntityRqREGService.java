package com.viettel.vtnet360.vt04.vt040003.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class VT040003EntityRqREGService
 * 
 * @author KienHT 27/09/2018
 * 
 */
public class VT040003EntityRqREGService extends BaseEntity {

	/** ISSUES_SERVICE.ISSUESE_LOCATION */
	private int issueseLocation;

	/** ISSUES_SERVICE.SERVICE_ID */
	private String serviceId;

	/** ISSUES_SERVICE.NOTE */
	private String note;

	/** UserName ISSUES_SERVICE.APPOVER_QLTT */
	private String appoverQLTT;

	/** UserName ISSUES_SERVICE.APPOVER_LDDV */
	private String appoverLDDV;

	/** UserName ISSUES_SERVICE.APPOVER_CVP */
	private String appoverCVP;
	
	/** Require sign ISSUES_SERVICE.REQUIRE_SIGN */
	private int requireSign;
	
	private String serviceReceiveerUsername;

	public VT040003EntityRqREGService() {
		super();
	}

	public VT040003EntityRqREGService(int issueseLocation, String serviceId, String note, String appoverQLTT,
			String appoverLDDV, String appoverCVP, int requireSign) {
		super();
		this.issueseLocation = issueseLocation;
		this.serviceId = serviceId;
		this.note = note;
		this.appoverQLTT = appoverQLTT;
		this.appoverLDDV = appoverLDDV;
		this.appoverCVP = appoverCVP;
		this.requireSign = requireSign;
		
	}

	public int getIssueseLocation() {
		return issueseLocation;
	}

	public void setIssueseLocation(int issueseLocation) {
		this.issueseLocation = issueseLocation;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAppoverQLTT() {
		return appoverQLTT;
	}

	public void setAppoverQLTT(String appoverQLTT) {
		this.appoverQLTT = appoverQLTT;
	}

	public String getAppoverLDDV() {
		return appoverLDDV;
	}

	public void setAppoverLDDV(String appoverLDDV) {
		this.appoverLDDV = appoverLDDV;
	}

	public String getAppoverCVP() {
		return appoverCVP;
	}

	public void setAppoverCVP(String appoverCVP) {
		this.appoverCVP = appoverCVP;
	}

	public int getRequireSign() {
		return requireSign;
	}

	public void setRequireSign(int requireSign) {
		this.requireSign = requireSign;
	}

	public String getServiceReceiveerUsername() {
		return serviceReceiveerUsername;
	}

	public void setServiceReceiveerUsername(String serviceReceiveerUsername) {
		this.serviceReceiveerUsername = serviceReceiveerUsername;
	}	

}
