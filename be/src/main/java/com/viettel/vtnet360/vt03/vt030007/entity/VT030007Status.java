package com.viettel.vtnet360.vt03.vt030007.entity;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030007Status {
	/** CAR_BOOKING.STATUS  */
	private int approveStatus;
	
	/** CAR_BOOKING.STATUS */
	private int refuseStatus;

	/** CAR_BOOKING.STATUS */
	private int cancelStatus;

	/** CAR_BOOKING.STATUS */
	private int completeStatus;
	
	public VT030007Status(int approveStatus, int refuseStatus, int cancelStatus, int completeStatus) {
		super();
		this.approveStatus = approveStatus;
		this.refuseStatus = refuseStatus;
		this.cancelStatus = cancelStatus;
		this.completeStatus = completeStatus;
	}

	public VT030007Status() {
		super();
	}

	public int getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(int approveStatus) {
		this.approveStatus = approveStatus;
	}

	public int getRefuseStatus() {
		return refuseStatus;
	}

	public void setRefuseStatus(int refuseStatus) {
		this.refuseStatus = refuseStatus;
	}

	public int getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(int cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public int getCompleteStatus() {
		return completeStatus;
	}

	public void setCompleteStatus(int completeStatus) {
		this.completeStatus = completeStatus;
	}

	
}
