package com.viettel.vtnet360.signVoffice.entity;

import java.util.Date;
import java.util.List;

public class ReportSynthetic {
  private int  signVOfficeId;
  
	private Long syntheticSignVofficeId;

	private Long detailSignVofficeId;
	
	private String unitName;
	
	private String stationeryId;

	private String quantity;

	private List<Stationery> listStationery;
	
	private int type;
	
	private Date fromDate;
	
	private Date toDate;
	
	private String[] listUnitId;
	
	private int status ;

	private String transCode;
	
	
	public int getSignVOfficeId() {
    return signVOfficeId;
  }

  public void setSignVOfficeId(int signVOfficeId) {
    this.signVOfficeId = signVOfficeId;
  }

  public Long getSyntheticSignVofficeId() {
		return syntheticSignVofficeId;
	}

	public void setSyntheticSignVofficeId(Long syntheticSignVofficeId) {
		this.syntheticSignVofficeId = syntheticSignVofficeId;
	}

	public Long getDetailSignVofficeId() {
		return detailSignVofficeId;
	}

	public void setDetailSignVofficeId(Long detailSignVofficeId) {
		this.detailSignVofficeId = detailSignVofficeId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getStationeryId() {
		return stationeryId;
	}

	public void setStationeryId(String stationeryId) {
		this.stationeryId = stationeryId;
	}

	public List<Stationery> getListStationery() {
		return listStationery;
	}

	public void setListStationery(List<Stationery> listStationery) {
		this.listStationery = listStationery;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String[] getListUnitId() {
		return listUnitId;
	}

	public void setListUnitId(String[] listUnitId) {
		this.listUnitId = listUnitId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	

}
