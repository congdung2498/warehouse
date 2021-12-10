package com.viettel.vtnet360.vt02.vt020006.entity;

import java.util.Date;

/**
 * @author DuyNK 17/09/2018
 *
 */
public class VT020006LunchDate {

	private Date     lunchDate;
	private int      quantity;
	private int      hasBooking;
	private String   lunchId;
	private double   price;
	private int      period;
	private int      rating;
	private String   comment;
	private String   kitchenId;
	private String   kitchenName;
	
	
	public VT020006LunchDate() { }

	public VT020006LunchDate(Date lunchDate, int quantity) {
		super();
		this.lunchDate = lunchDate;
		this.quantity = quantity;
	}
	

	public Date getLunchDate() { return lunchDate; }
	public void setLunchDate(Date lunchDate) { this.lunchDate = lunchDate; }

	public int getQuantity() { return quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; }

  public int getHasBooking() { return hasBooking; }
  public void setHasBooking(int hasBooking) { this.hasBooking = hasBooking; }

  public String getLunchId() { return lunchId; }
  public void setLunchId(String lunchId) { this.lunchId = lunchId; }

  public double getPrice() { return price; }
  public void setPrice(double price) { this.price = price; }

  public int getPeriod() { return period; }
  public void setPeriod(int period) { this.period = period; }

  public int getRating() { return rating; }
  public void setRating(int rating) { this.rating = rating; }

  public String getComment() { return comment; }
  public void setComment(String comment) { this.comment = comment; }

  public String getKitchenId() { return kitchenId; }
  public void setKitchenId(String kitchenId) { this.kitchenId = kitchenId; }

  public String getKitchenName() { return kitchenName; }
  public void setKitchenName(String kitchenName) { this.kitchenName = kitchenName; }
}
