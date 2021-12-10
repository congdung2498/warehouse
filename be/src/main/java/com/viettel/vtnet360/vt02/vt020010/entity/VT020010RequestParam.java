package com.viettel.vtnet360.vt02.vt020010.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK 14/09/2018
 * 
 */
public class VT020010RequestParam extends BaseEntity {

	/** LUNCH_CALENDAR.LUNCH_DATE */
	private Date lunchDate;
	
	/** KITCHEN_SETTING.CHEF_ACCOUNT use for find kitchenID */
	private String userName;

	/** LUNCH_CALENDAR.HAS_BOOKING */
	private int hasBooking;

	/** LUNCH_CALENDAR.QUANTITY */
	private int quantity;

	/** LUNCH_CALENDAR.KITCHEN_ID */
	private String kitchenID;
	
	private Date fromDate;
	
	private Date toDate;
	
	private int unitId;
	
	
	public VT020010RequestParam() { }

	public VT020010RequestParam(Date lunchDate, String userName, int hasBooking, int quantity,
			String kitchenID) {
		super();
		this.lunchDate = lunchDate;
		this.userName = userName;
		this.hasBooking = hasBooking;
		this.quantity = quantity;
		this.kitchenID = kitchenID;
	}


	public Date getLunchDate() { return lunchDate; }
	public void setLunchDate(Date lunchDate) { this.lunchDate = lunchDate; }

	public String getUserName() { return userName; }
	public void setUserName(String userName) {this.userName = userName; }

	public int getHasBooking() { return hasBooking;}
	public void setHasBooking(int hasBooking) { this.hasBooking = hasBooking; }

	public int getQuantity() { return quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; }

	public String getKitchenID() { return kitchenID; }
	public void setKitchenID(String kitchenID) { this.kitchenID = kitchenID; }

  public Date getFromDate() { return fromDate; }
  public void setFromDate(Date fromDate) { this.fromDate = fromDate; }

  public Date getToDate() { return toDate; }
  public void setToDate(Date toDate) { this.toDate = toDate; }

  public int getUnitId() { return unitId; }
  public void setUnitId(int unitId) { this.unitId = unitId; }
}
