package com.viettel.vtnet360.kitchen.dto;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

public class SearchingLunchModel extends BaseEntity {
  
  private String          kitchenId;
  private String          userName;
  private List<Integer>   listPeriodic;
  private int             quantity;
  private Date            startTime;
  private Date            endTime;
  private int []          months;
  private int             year;
  private int             startRow;
  private int             rowSize;
  private boolean         searchQuantity;
  
  
  public String getKitchenId() { return kitchenId; }
  public void setKitchenId(String kitchenId) { this.kitchenId = kitchenId; }
  
  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }
  
  public List<Integer> getListPeriodic() { return listPeriodic; }
  public void setListPeriodic(List<Integer> listPeriodic) { this.listPeriodic = listPeriodic; }
  
  public int getQuantity() { return quantity; }
  public void setQuantity(int quantity) { this.quantity = quantity; }
  
  public Date getStartTime() { return startTime; }
  public void setStartTime(Date startTime) { this.startTime = startTime; }
  
  public Date getEndTime() { return endTime; }
  public void setEndTime(Date endTime) { this.endTime = endTime; }
  
  public int[] getMonths() { return months; }
  public void setMonths(int[] months) { this.months = months; }
  
  public int getYear() { return year; }
  public void setYear(int year) { this.year = year; }
  
  public int getStartRow() { return startRow; }
  public void setStartRow(int startRow) { this.startRow = startRow; }
  
  public int getRowSize() { return rowSize; }
  public void setRowSize(int rowSize) { this.rowSize = rowSize; }
  
  public boolean isSearchQuantity() { return searchQuantity; }
  public void setSearchQuantity(boolean searchQuantity) { this.searchQuantity = searchQuantity; }
}
