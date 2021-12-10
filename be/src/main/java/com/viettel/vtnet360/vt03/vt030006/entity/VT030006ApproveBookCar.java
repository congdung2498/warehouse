package com.viettel.vtnet360.vt03.vt030006.entity;

import com.viettel.vtnet360.vt03.vt030000.entity.VT030000EntityBookCar;

/**
 * 
 * @author CuongHD
 *
 */
public class VT030006ApproveBookCar extends VT030000EntityBookCar {

	/** CAR_BOOKING.CAR_BOOKING_ID */
	private String bookCarId;
	
	/** CAR_BOOKING.REASON_REFUSE */
	private String reasonRefuse;
	
	public VT030006ApproveBookCar() {
		super();
	}

	public VT030006ApproveBookCar(String bookCarId, String reasonRefuse) {
		super();
		this.bookCarId = bookCarId;
		this.reasonRefuse = reasonRefuse;
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
	
}
