package com.viettel.vtnet360.driving.dto;

public class CarBookingAlert {
  
  private String  bookingId;
  private int     count;
  private int     minutes;
  
  
  public String getBookingId() { return bookingId; }
  public void setBookingId(String bookingId) { this.bookingId = bookingId; }
  
  public int getCount() { return count; }
  public void setCount(int count) { this.count = count; }
  
  public int getMinutes() { return minutes; }
  public void setMinutes(int minutes) { this.minutes = minutes; }
}
