package com.viettel.vtnet360.vt05.vt050013.entity;

import java.util.Date;

public class VT050013DataGetById {

  private Date createDate;
  private int totalRequest;
  private String fullName;
  private double totalMoney;
  private int totalFullfill;
  private double totalMoneyFullfill;
  private String stationeryName;
  private double unitPrice;



  public Date getCreateDate() {
    return createDate;
  }
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  public String getFullName() {
    return fullName;
  }
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  public int getTotalFullfill() {
    return totalFullfill;
  }
  public double getTotalMoneyFullfill() {
    return totalMoneyFullfill;
  }
  public void setTotalMoneyFullfill(double totalMoneyFullfill) {
    this.totalMoneyFullfill = totalMoneyFullfill;
  }
  public void setTotalFullfill(int totalFullfill) {
    this.totalFullfill = totalFullfill;
  }

  public int getTotalRequest() {
    return totalRequest;
  }
  public void setTotalRequest(int totalRequest) {
    this.totalRequest = totalRequest;
  }
  public double getTotalMoney() {
    return totalMoney;
  }
  public void setTotalMoney(double totalMoney) {
    this.totalMoney = totalMoney;
  }
  public String getStationeryName() {
    return stationeryName;
  }
  public void setStationeryName(String stationeryName) {
    this.stationeryName = stationeryName;
  }
  public double getUnitPrice() {
    return unitPrice;
  }
  public void setUnitPrice(double unitPrice) {
    this.unitPrice = unitPrice;
  }

  public VT050013DataGetById(Date createDate, int totalRequest, String fullName, double totalMoney, int totalFullfill,
      double totalMoneyFullfill, String stationeryName, double unitPrice) {
    super();
    this.createDate = createDate;
    this.totalRequest = totalRequest;
    this.fullName = fullName;
    this.totalMoney = totalMoney;
    this.totalFullfill = totalFullfill;
    this.totalMoneyFullfill = totalMoneyFullfill;
    this.stationeryName = stationeryName;
    this.unitPrice = unitPrice;
  }
  public VT050013DataGetById() {
    super();
    // TODO Auto-generated constructor stub
  }


}
