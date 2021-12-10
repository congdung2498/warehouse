package com.viettel.vtnet360.vt02.vt020010.entity;

import java.util.Date;

/**
 * @author DuyNK
 *
 */
public class VT020010DataResponse {

	/** QLDV_UNIT.LUNCH_DATE */
	private Date lunchDate;

	/** QLDV_UNIT.UNIT_NAME */
	private String unitName;

	/** total lunchDate in a day */
	private int total;
	
	private int unitId;
	
	public VT020010DataResponse() {
	
	}

	public VT020010DataResponse(Date lunchDate, String unitName, int total) {
		super();
		this.lunchDate = lunchDate;
		this.unitName = unitName;
		this.total = total;
	}

	public Date getLunchDate() {
		return lunchDate;
	}

	public void setLunchDate(Date lunchDate) {
		this.lunchDate = lunchDate;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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
