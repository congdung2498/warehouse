package com.viettel.vtnet360.vt03.vt030006.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030006UpdateBookCar extends BaseEntity {

	/** CAR_BOOKING.STATUS */
	private int status;
	
	/** CAR_BOOKING.CAR_BOOKING_ID */
	private String bookCarId;
	
	/** list book car id */
	private String[] listId;
	
	/** CAR_BOOKING.REASON_REFUSE */
	private String reasonRefuse;
	
	/** CAR_BOOKING.REASON_REFUSE */
  private String reasonAssigner;
	
	/** CAR_BOOKING.(APPROVER_QLTT OR APPROVER_LDDV OR APPROVER_CVP) */
	private String userName;
	
	/** CAR_BOOKING.APPOVER_QLTT */
	private String qltt;
	
	/** CAR_BOOKING.FLAG_QLTT */
	private int flagQltt;
	
	/** CAR_BOOKING.APPOVER_LDDV */
	private String lddv;
	
	/** CAR_BOOKING.FLAG_LDDV */
	private int flagLddv;
	
	/** CAR_BOOKING.APPOVER_CVP */
	private String cvp;
	
	private int flasCvp;
	
	/** QLDV_EMPLOYEE.ROLE */
	private String role;
	
	/** CAR_BOOKING.EMPLOYEE_USER_NAME */
	private String userRequest;
	
	public VT030006UpdateBookCar() {
	}

	public VT030006UpdateBookCar(int status, String bookCarId, String[] listId, String reasonRefuse, String userName,
			String qltt, int flagQltt, String lddv, int flagLddv, String cvp) {
		super();
		this.status = status;
		this.bookCarId = bookCarId;
		this.listId = listId;
		this.reasonRefuse = reasonRefuse;
		this.userName = userName;
		this.qltt = qltt;
		this.flagQltt = flagQltt;
		this.lddv = lddv;
		this.flagLddv = flagLddv;
		this.cvp = cvp;
	}

	public String[] getListId() {
		return listId;
	}

	public void setListId(String[] listId) {
		this.listId = listId;
	}

	public String getBookCarId() {
		return bookCarId;
	}

	public void setBookCarId(String bookCarId) {
		this.bookCarId = bookCarId;
	}

	public String getReasonRefuse() {
		return reasonRefuse;
	}

	public void setReasonRefuse(String reasonRefuse) {
		this.reasonRefuse = reasonRefuse;
	}
	
	public String getReasonAssigner() {
    return reasonAssigner;
  }

  public void setReasonAssigner(String reasonAssigner) {
    this.reasonAssigner = reasonAssigner;
  }

  public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getQltt() {
		return qltt;
	}

	public void setQltt(String qltt) {
		this.qltt = qltt;
	}

	public int getFlagQltt() {
		return flagQltt;
	}

	public void setFlagQltt(int flagQltt) {
		this.flagQltt = flagQltt;
	}

	public String getLddv() {
		return lddv;
	}

	public void setLddv(String lddv) {
		this.lddv = lddv;
	}

	public int getFlagLddv() {
		return flagLddv;
	}

	public void setFlagLddv(int flagLddv) {
		this.flagLddv = flagLddv;
	}

	public String getCvp() {
		return cvp;
	}

	public void setCvp(String cvp) {
		this.cvp = cvp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserRequest() {
		return userRequest;
	}

	public void setUserRequest(String userRequest) {
		this.userRequest = userRequest;
	}

  public int getFlasCvp() {
    return flasCvp;
  }

  public void setFlasCvp(int flasCvp) {
    this.flasCvp = flasCvp;
  }
}
