package com.viettel.vtnet360.vt05.vt050012.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class QuotaInsert extends BaseEntity {
	private Integer  unitId;
	private Double   quota;
	private Integer  quantity ;
	
	
	public Integer getUnitId() { return unitId; }
	public void setUnitId(Integer unitId) { this.unitId = unitId; }
	
	public Double getQuota() { return quota; }
	public void setQuota(Double quota) { this.quota = quota; }
	
	public Integer getQuantity() { return quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
