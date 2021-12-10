package com.viettel.vtnet360.vt05.vt050017.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT050017DataResponse {

	/** ISSUES_STATIONERY_APPROVED.ISSUES_STATIONERY_APPROVED_ID */
	private String requestID;

	/** fullName of ISSUES_STATIONERY_APPROVED.HCDV_USERNAME */
	private String hcdvFullName;
	
	/** ISSUES_STATIONERY_APPROVED.CREATE_DATE */
	private Date dateRequest;

	/** ISSUES_STATIONERY_APPROVED.HCDV_MESSAGE */
	private String message;
	
	/** fullName of ISSUES_STATIONERY_APPROVED.VPTCT_USERNAME */
	private String vptctFullName;

	/** ISSUES_STATIONERY_APPROVED.VPTCT_EXECUTE_DATE */
	private Date dateExecute;

	/** Total stationery of request */
	private int quantity;

	/** total money of request */
	private double totalMoney;
	
	/** total money of fullfill */
	private double totalFullfill; 

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private int status;
	
	public VT050017DataResponse() {

	}

	
	public VT050017DataResponse(String requestID, String hcdvFullName, Date dateRequest, String message,
			String vptctFullName, Date dateExecute, int quantity, double totalMoney, double totalFullfill, int status) {
		super();
		this.requestID = requestID;
		this.hcdvFullName = hcdvFullName;
		this.dateRequest = dateRequest;
		this.message = message;
		this.vptctFullName = vptctFullName;
		this.dateExecute = dateExecute;
		this.quantity = quantity;
		this.totalMoney = totalMoney;
		this.totalFullfill = totalFullfill;
		this.status = status;
	}


	public double getTotalFullfill() {
		return totalFullfill;
	}


	public void setTotalFullfill(double totalFullfill) {
		this.totalFullfill = totalFullfill;
	}


	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getHcdvFullName() {
		return hcdvFullName;
	}

	public void setHcdvFullName(String hcdvFullName) {
		this.hcdvFullName = hcdvFullName;
	}

	public Date getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(Date dateRequest) {
		this.dateRequest = dateRequest;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVptctFullName() {
		return vptctFullName;
	}

	public void setVptctFullName(String vptctFullName) {
		this.vptctFullName = vptctFullName;
	}

	public Date getDateExecute() {
		return dateExecute;
	}

	public void setDateExecute(Date dateExecute) {
		this.dateExecute = dateExecute;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}