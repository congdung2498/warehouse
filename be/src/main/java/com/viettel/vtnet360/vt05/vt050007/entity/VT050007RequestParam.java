package com.viettel.vtnet360.vt05.vt050007.entity;

import java.util.List;

/**
 * @author DuyNK
 *
 */
public class VT050007RequestParam {

	/** ISSUES_STATIONERY_APPROVED.VPTCT_MESSAGE */
	private String message;
	
	/** ISSUES_STATIONERY_APPROVED.CVP_USERNAME */
	private String approveUserName;
	
	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private List<String> listRequestID;
	
	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID & ISSUES_STATIONERY_ITEMS.UPDATE_USER */
	private String vptctUserName;
	
	/** use for update status in ISSUES_STATIONERY_APPROVED & ISSUES_STATIONERY_ITEMS */
	private int status;
	
	public VT050007RequestParam() {
	
	}

	public VT050007RequestParam(String message, String approveUserName, List<String> listRequestID,
			String vptctUserName, int status) {
		super();
		this.message = message;
		this.approveUserName = approveUserName;
		this.listRequestID = listRequestID;
		this.vptctUserName = vptctUserName;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getApproveUserName() {
		return approveUserName;
	}

	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}

	public List<String> getListRequestID() {
		return listRequestID;
	}

	public void setListRequestID(List<String> listRequestID) {
		this.listRequestID = listRequestID;
	}

	public String getVptctUserName() {
		return vptctUserName;
	}

	public void setVptctUserName(String vptctUserName) {
		this.vptctUserName = vptctUserName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
