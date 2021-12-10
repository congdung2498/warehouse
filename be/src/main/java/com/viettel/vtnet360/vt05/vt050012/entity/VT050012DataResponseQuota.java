package com.viettel.vtnet360.vt05.vt050012.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class VT050012DataResponseQuota extends BaseEntity {
	
	private String   id ;
	private String   unitName ;
	private Long     unitId ;
	private Double  quota ;
	private Integer  quantity ;
	private Double   total;
	private String   path;
	private String   limitDate;

	
	public String getPath() { return path; }
	public void setPath(String path) { this.path = path; }
	
	public Long getUnitId() { return unitId; }
	public void setUnitId(Long unitId) { this.unitId = unitId; }

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	
	public Double getTotal() { return total; }
	public void setTotal(Double total) { this.total = total; }
	
	public String getUnitName() { return unitName; }
	public void setUnitName(String unitName) { this.unitName = unitName; }
	
	
	public Double getQuota() {
		return quota;
	}
	public void setQuota(Double quota) {
		this.quota = quota;
	}
	public Integer getQuantity() { return quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }
	
	public String getLimitDate() { return limitDate; }
  public void setLimitDate(String limitDate) { this.limitDate = limitDate; }
}
