package com.viettel.vtnet360.vt02.vt020006.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK 17/09/2018
 *
 */
public class VT020006Lunch extends BaseEntity {

	/** LUNCH_CALENDAR.EMPLOYEE_USER_NAME */
	private String userName;

	/** LUNCH_CALENDAR.LUNCH_DATE */
	private Date lunchDate;

	/** LUNCH_CALENDAR.QUANTITY */
	private int quantity;

	/** LUNCH_CALENDAR.HAS_BOOKING */
	private int hasBooking;

	/** LUNCH_CALENDAR.KITCHEN_ID */
	private String kitchenID;

	/** LUNCH_CALENDAR.IS_PERIODIC */
	private int isPeriodic;
	
	/** LUNCH_CALENDAR.UNIT_ID */
	private int unitId;

	/** list LUNCH_CALENDAR.LUNCH_DATE use for insert periodic */
	private List<Date> listDateLunch;

	
	public VT020006Lunch() { }

	public VT020006Lunch(String userName, Date lunchDate, int quantity, int hasBooking, String kitchenID,
			int isPeriodic, List<Date> listDateLunch) {
		super();
		this.userName = userName;
		this.lunchDate = lunchDate;
		this.quantity = quantity;
		this.hasBooking = hasBooking;
		this.kitchenID = kitchenID;
		this.isPeriodic = isPeriodic;
		this.listDateLunch = listDateLunch;
	}
	
	public VT020006Lunch(String userName, Date lunchDate, int quantity, int hasBooking, String kitchenID, int unitId,
                        int isPeriodic, List<Date> listDateLunch) {
    this.userName = userName;
    this.lunchDate = lunchDate;
    this.quantity = quantity;
    this.hasBooking = hasBooking;
    this.kitchenID = kitchenID;
    this.isPeriodic = isPeriodic;
    this.listDateLunch = listDateLunch;
    this.unitId = unitId;
  }
	

	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }

	public Date getLunchDate() { return lunchDate; }
	public void setLunchDate(Date lunchDate) { this.lunchDate = lunchDate; }

	public int getQuantity() { return quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; }

	public int getHasBooking() { return hasBooking; }
	public void setHasBooking(int hasBooking) { this.hasBooking = hasBooking; }

	public String getKitchenID() { return kitchenID; }
	public void setKitchenID(String kitchenID) { this.kitchenID = kitchenID; }

	public int getIsPeriodic() { return isPeriodic; }
	public void setIsPeriodic(int isPeriodic) { this.isPeriodic = isPeriodic; }
	
	public int getUnitId() { return unitId; }
  public void setUnitId(int unitId) { this.unitId = unitId; }

  public List<Date> getListDateLunch() { return listDateLunch; }
	public void setListDateLunch(List<Date> listDateLunch) { this.listDateLunch = listDateLunch; }
}
