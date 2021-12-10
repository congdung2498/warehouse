package com.viettel.vtnet360.vt02.vt020012.entity;

import java.math.BigDecimal;

/**
 * Entity class for communication with DB and client
 * 
 * @author VinhNVQ 9/8/2018
 *
 */
public class VT020012KitchenReport {

	private Double priceEach;
	private BigDecimal totalMeal;
	private String unitName;
	private Long unitId;
	private String detailUnit;
	private Integer resultDay;
	private String kitchenId;
	private String kitchenName;
	
	public VT020012KitchenReport() {
		super();
	}

	public VT020012KitchenReport(Double priceEach, BigDecimal totalMeal, String unitName, Long unitId,
			String detailUnit, Integer resultDay, String kitchenId, String kitchenName) {
		super();
		this.priceEach = priceEach;
		this.totalMeal = totalMeal;
		this.unitName = unitName;
		this.unitId = unitId;
		this.detailUnit = detailUnit;
		this.resultDay = resultDay;
		this.kitchenId = kitchenId;
		this.kitchenName = kitchenName;
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

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getDetailUnit() {
		return detailUnit;
	}

	public void setDetailUnit(String detailUnit) {
		this.detailUnit = detailUnit;
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
}
