package com.viettel.vtnet360.vt05.vt050004.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050004ParamToInsert extends BaseEntity {

	/** ISSUES_STATIONERY_APPROVED.MESSAGE */
	private String message;

	/** ISSUES_STATIONERY_APPROVED.APPROVED_USERNAME */
	private String approveUserName;

	/** list ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ITEM_ID */
	private List<String> listRequestID;

	/** ISSUES_STATIONERY_APPROVED.HCDV_USERNAME */
	private String employeeUserName;

	public VT050004ParamToInsert() {

	}

	public VT050004ParamToInsert(String message, String approveUserName, List<String> listRequestID,
			String employeeUserName) {
		super();
		this.message = message;
		this.approveUserName = approveUserName;
		this.listRequestID = listRequestID;
		this.employeeUserName = employeeUserName;
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

	public String getEmployeeUserName() {
		return employeeUserName;
	}

	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
	}
}
