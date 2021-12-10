package com.viettel.vtnet360.vt05.vt050011.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050011RequestParamToExecute extends BaseEntity {

	/** action execute pause, finish, reject */
	private int action;

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_APPROVED_ID */
	private String requestID;

	/** ISSUES_STATIONERY_ITEMS.REASON */
	private String reason;

	/** ISSUES_STATIONERY_ITEMS.POSTPONE_TO_TIME */
	private Date appointmentTime;

	/** ISSUES_STATIONERY_APPROVED.VPTCT_USERNAME */
	private String vptctUserName;

	/** use for save info data fulfill to STORAGE_HCDV_REQUEST */
	private List<VT050011Stationery> listStaioneryGiveOut;
	
	/**
	 * use for update status in ISSUES_STATIONERY_APPROVED & ISSUES_STATIONERY_ITEMS
	 */
	private int status;

	/** use for define field when update in db in query in xml file */
	private boolean actionReceivedRequest;
	
	/** use for define field when update in db in query in xml file */
	private boolean actionPause;

	/** use for define field when update in db in query in xml file */
	private boolean actionReject;

	/** use for define field when update in db in query in xml file */
	private boolean actionExecute;

	/** list status condition to update */
	private List<Integer> listStatus;
	
	public VT050011RequestParamToExecute() {

	}

	public VT050011RequestParamToExecute(int action, String requestID, String reason, Date appointmentTime,
			String vptctUserName, List<VT050011Stationery> listStaioneryGiveOut, int status,
			boolean actionReceivedRequest, boolean actionPause, boolean actionReject, boolean actionExecute,
			List<Integer> listStatus) {
		super();
		this.action = action;
		this.requestID = requestID;
		this.reason = reason;
		this.appointmentTime = appointmentTime;
		this.vptctUserName = vptctUserName;
		this.listStaioneryGiveOut = listStaioneryGiveOut;
		this.status = status;
		this.actionReceivedRequest = actionReceivedRequest;
		this.actionPause = actionPause;
		this.actionReject = actionReject;
		this.actionExecute = actionExecute;
		this.listStatus = listStatus;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getVptctUserName() {
		return vptctUserName;
	}

	public void setVptctUserName(String vptctUserName) {
		this.vptctUserName = vptctUserName;
	}

	public List<VT050011Stationery> getListStaioneryGiveOut() {
		return listStaioneryGiveOut;
	}

	public void setListStaioneryGiveOut(List<VT050011Stationery> listStaioneryGiveOut) {
		this.listStaioneryGiveOut = listStaioneryGiveOut;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isActionReceivedRequest() {
		return actionReceivedRequest;
	}

	public void setActionReceivedRequest(boolean actionReceivedRequest) {
		this.actionReceivedRequest = actionReceivedRequest;
	}

	public boolean isActionPause() {
		return actionPause;
	}

	public void setActionPause(boolean actionPause) {
		this.actionPause = actionPause;
	}

	public boolean isActionReject() {
		return actionReject;
	}

	public void setActionReject(boolean actionReject) {
		this.actionReject = actionReject;
	}

	public boolean isActionExecute() {
		return actionExecute;
	}

	public void setActionExecute(boolean actionExecute) {
		this.actionExecute = actionExecute;
	}

	public List<Integer> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<Integer> listStatus) {
		this.listStatus = listStatus;
	}
}
