package com.viettel.vtnet360.stationery.entity;

public class StationeryNameAllResponseTemplate {

  private String stationeryId;
	private String stationeryName;
	private String calculationUnit;
	private Double unitPrice;
	
	
	public String getStationeryId() {
    return stationeryId;
  }
  public void setStationeryId(String stationeryId) {
    this.stationeryId = stationeryId;
  }
  public String getStationeryName() {
		return stationeryName;
	}
	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}
	public String getCalculationUnit() {
		return calculationUnit;
	}
	public void setCalculationUnit(String calculationUnit) {
		this.calculationUnit = calculationUnit;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public StationeryNameAllResponseTemplate(String stationeryName, String calculationUnit, Double unitPrice) {
		super();
		this.stationeryName = stationeryName;
		this.calculationUnit = calculationUnit;
		this.unitPrice = unitPrice;
	}
	public StationeryNameAllResponseTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
