package com.viettel.vtnet360.vt02.vt020006.entity;

import java.util.List;

/**
 * @author DuyNK 17/09/2018
 *
 */
public class VT020006LunchReport {

	/** list day of week that periodic */
	private List<Integer> periodic;

	/** list datelunch that quantity > 0, hasBooking = 1 */
	private List<VT020006LunchDate> listLunchDate;

	/** find kitchenID that periodic */
	private String kitchenID;

	/** find kitchenName that periodic */
	private String kitchenName;

	public VT020006LunchReport() {

	}

	public VT020006LunchReport(List<Integer> periodic, List<VT020006LunchDate> listLunchDate, String kitchenID,
			String kitchenName) {
		super();
		this.periodic = periodic;
		this.listLunchDate = listLunchDate;
		this.kitchenID = kitchenID;
		this.kitchenName = kitchenName;
	}

	public List<Integer> getPeriodic() {
		return periodic;
	}

	public void setPeriodic(List<Integer> periodic) {
		this.periodic = periodic;
	}

	public List<VT020006LunchDate> getListLunchDate() {
		return listLunchDate;
	}

	public void setListLunchDate(List<VT020006LunchDate> listLunchDate) {
		this.listLunchDate = listLunchDate;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}

	public String getKitchenName() {
		return kitchenName;
	}

	public void setKitchenName(String kitchenName) {
		this.kitchenName = kitchenName;
	}
}
