package com.viettel.vtnet360.vt02.vt020006.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK 17/09/2018
 *
 */
public class VT020006ParamRequest extends BaseEntity {

	/** LUNCH_CALENDAR.KITCHEN_ID = KITCHEN_SETTING.KITCHEN_ID */
	private String     kitchenID;

	/** list day of week that periodic */
	private List<Integer> listPeriodic;

	/** KITCHEN_SETTING.LUNCH_DATE */
	private Date     lunchDate;

	/** KITCHEN_SETTING.HAS_BOOKING */
	private int      hasBooking;

	/** KITCHEN_SETTING.QUANTITY */
	private int      quantity;

	/** LUNCH_CALENDAR.EMPLOYEE_USER_NAME */
	private String  userName;
	
	/** LUNCH_CALENDAR.UNIT_ID */
  private int     unitId;
  
  /** if lunch by days **/
  private Date    startTime;
  
  /** if lunch by days **/
  private Date    endTime;
  
  private int []  months;
  
  private int     year;

  
	public VT020006ParamRequest() { }
	
	public VT020006ParamRequest(Date lunchDate, String userName) {
    this.lunchDate = lunchDate;
    this.userName = userName;
  }

	public VT020006ParamRequest(String kitchenID, List<Integer> listPeriodic, Date lunchDate, int hasBooking,
			int quantity, String userName) {
		super();
		this.kitchenID = kitchenID;
		this.listPeriodic = listPeriodic;
		this.lunchDate = lunchDate;
		this.hasBooking = hasBooking;
		this.quantity = quantity;
		this.userName = userName;
	}
	

	public String getKitchenID() { return kitchenID; }
	public void setKitchenID(String kitchenID) { this.kitchenID = kitchenID; }

	public List<Integer> getListPeriodic() { return listPeriodic; }
	public void setListPeriodic(List<Integer> listPeriodic) { this.listPeriodic = listPeriodic; }

	public Date getLunchDate() { return lunchDate; }
	public void setLunchDate(Date lunchDate) { this.lunchDate = lunchDate; }

	public int getHasBooking() { return hasBooking; }
	public void setHasBooking(int hasBooking) { this.hasBooking = hasBooking; }

	public int getQuantity() { return quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; }

	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }

  public int getUnitId() { return unitId; }
  public void setUnitId(int unitId) { this.unitId = unitId; }

  public Date getStartTime() { return startTime; }
  public void setStartTime(Date startTime) { this.startTime = startTime; }

  public Date getEndTime() { return endTime; }
  public void setEndTime(Date endTime) { this.endTime = endTime; }

  public int[] getMonths() { return months; }
  public void setMonths(int[] months) { this.months = months; }

  public int getYear() { return year; }
  public void setYear(int year) { this.year = year; }
}
