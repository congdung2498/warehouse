package com.viettel.vtnet360.vt02.vt020013.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity class for communication with DB and client
 * 
 * @author VinhNVQ 9/8/2018
 *
 */
public class VT020013KitchenReport {
	private String code;
	private Integer id;
	private String fullName;
	private String detailUnit;
	private Double priceEach;
	private BigDecimal totalMeal;
	private String unitName;
	private Integer resultDay;
	private String kitchenId;
	private String kitchenName;
	private String comment;
	private Integer ratting;
	private Date lunchDate;
	private String phoneNumber;

	public VT020013KitchenReport() {
		super();
	}
	
	public VT020013KitchenReport(String code, Integer id, String fullName, String detailUnit, Double priceEach,
			BigDecimal totalMeal, String unitName, Integer resultDay, String kitchenId, String kitchenName) {
		super();
		this.code = code;
		this.id = id;
		this.fullName = fullName;
		this.detailUnit = detailUnit;
		this.priceEach = priceEach;
		this.totalMeal = totalMeal;
		this.unitName = unitName;
		this.resultDay = resultDay;
		this.kitchenId = kitchenId;
		this.kitchenName = kitchenName;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getDetailUnit() {
		return detailUnit;
	}


	public void setDetailUnit(String detailUnit) {
		this.detailUnit = detailUnit;
	}


	public Double getPriceEach() {
		return priceEach;
	}


	public void setPriceEach(Double priceEach) {
		this.priceEach = priceEach;
	}


	public BigDecimal getTotalMeal() {
		return totalMeal;
	}


	public void setTotalMeal(BigDecimal totalMeal) {
		this.totalMeal = totalMeal;
	}


	public String getUnitName() {
		return unitName;
	}


	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}


	public Integer getResultDay() {
		return resultDay;
	}


	public void setResultDay(Integer resultDay) {
		this.resultDay = resultDay;
	}


	public String getKitchenId() {
		return kitchenId;
	}


	public void setKitchenId(String kitchenId) {
		this.kitchenId = kitchenId;
	}


	public String getKitchenName() {
		return kitchenName;
	}


	public void setKitchenName(String kitchenName) {
		this.kitchenName = kitchenName;
	}
	
	public String getCodeandPrice() {
		return this.code + "|" + this.kitchenId;
	}
	
	public String getIDString() {
		return this.id + "";
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getRatting() {
		return ratting;
	}

	public void setRatting(Integer ratting) {
		this.ratting = ratting;
	}

	public Date getLunchDate() {
		return lunchDate;
	}

	public void setLunchDate(Date lunchDate) {
		this.lunchDate = lunchDate;
	}

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
