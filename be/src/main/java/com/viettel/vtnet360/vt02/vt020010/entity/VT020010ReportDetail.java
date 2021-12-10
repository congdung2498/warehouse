package com.viettel.vtnet360.vt02.vt020010.entity;

import java.util.Date;

/**
 * @author DuyNK 14/09/2018
 * 
 */
public class VT020010ReportDetail {

	/** QLDV_UNIT.LUNCH_DATE */
	private Date lunchDate;

	/** QLDV_UNIT.PATH */
	private String unitPath;

	/** total lunchDate in a day */
	private int total;
	
	private int unitId;

	public VT020010ReportDetail() {

	}

	public VT020010ReportDetail(Date lunchDate, String unitPath, int total) {
		super();
		this.lunchDate = lunchDate;
		this.unitPath = unitPath;
		this.total = total;
	}

	public Date getLunchDate() {
		return lunchDate;
	}

	public void setLunchDate(Date lunchDate) {
		this.lunchDate = lunchDate;
	}

	public String getUnitPath() {
		return unitPath;
	}

	public void setUnitPath(String unitPath) {
		this.unitPath = unitPath;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

  public int getUnitId() {
    return unitId;
  }

  public void setUnitId(int unitId) {
    this.unitId = unitId;
  }
}
