package com.viettel.vtnet360.vt02.vt020006.entity;

import java.util.List;

import com.viettel.vtnet360.kitchen.dto.Dish;

/**
 * @author DuyNK 17/09/2018
 *
 */
public class VT020006LunchInfo {

	/** LUNCH_CALENDAR.KITCHEN_ID = KITCHEN_SETTING.KITCHEN_ID */
	private String kitchenID;

	/** KITCHEN_SETTING.KITCHEN_NAME */
	private String kitchenName;

	/** KITCHEN_SETTING.HAS_BOOKING */
	private int hasBooking;

	/** KITCHEN_SETTING.QUANTITY */
	private int quantity;

	/** KITCHEN_SETTING.RATTING */
	private int rating;

	/** KITCHEN_SETTING.COMMENT */
	private String comment;

	/** KITCHEN_SETTING.KITCHEN_ID */
	private List<Dish> listDishName;
	
	private String lunchId;

	public VT020006LunchInfo() {

	}

	public VT020006LunchInfo(String kitchenID, String kitchenName, int hasBooking, int quantity, int rating,
			String comment) {
		super();
		this.kitchenID = kitchenID;
		this.kitchenName = kitchenName;
		this.hasBooking = hasBooking;
		this.quantity = quantity;
		this.rating = rating;
		this.comment = comment;
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

	public int getHasBooking() {
		return hasBooking;
	}

	public void setHasBooking(int hasBooking) {
		this.hasBooking = hasBooking;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

  public List<Dish> getListDishName() {
    return listDishName;
  }

  public void setListDishName(List<Dish> listDishName) {
    this.listDishName = listDishName;
  }

  public String getLunchId() {
    return lunchId;
  }

  public void setLunchId(String lunchId) {
    this.lunchId = lunchId;
  }
}
