package com.viettel.vtnet360.kitchen.dto;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

public class LunchDTO extends BaseEntity {
  private String  lunchId;
  private Date    lunchDate;
  private int     quantity;
  private double  price;
  private int     hasBooking;
  private int     period;
  private int     rating;
  private String  comment;
  private String  kitchenId;
  private String  kitchenName;
  
  
  public String getLunchId() { return lunchId; }
  public void setLunchId(String lunchId) { this.lunchId = lunchId; }
  
  public Date getLunchDate() { return lunchDate; }
  public void setLunchDate(Date lunchDate) { this.lunchDate = lunchDate; }
  
  public int getQuantity() { return quantity; }
  public void setQuantity(int quantity) { this.quantity = quantity; }
  
  public double getPrice() { return price; }
  public void setPrice(double price) { this.price = price; }
  
  public int getHasBooking() { return hasBooking; }
  public void setHasBooking(int hasBooking) { this.hasBooking = hasBooking; }
  
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
