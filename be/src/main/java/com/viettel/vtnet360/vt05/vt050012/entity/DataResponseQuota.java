package com.viettel.vtnet360.vt05.vt050012.entity;

public class DataResponseQuota {
	private String id;
	private String unitName ;
	private Integer quota ;
	private Integer quantity ;
	private Double total;
	private String limitDate;
	private String path;
	private Long unitId ;
	
	
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLimitDate() {
		return limitDate;
	}
	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Integer getQuota() {
		return quota;
	}
	public void setQuota(Integer quota) {
		this.quota = quota;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
		
		public DataResponseQuota(String id, String unitName, Integer quota, Integer quantity, Double total,
			String limitDate, String path, Long unitId) {
		super();
		this.id = id;
		this.unitName = unitName;
		this.quota = quota;
		this.quantity = quantity;
		this.total = total;
		this.limitDate = limitDate;
		this.path = path;
		this.unitId = unitId;
	}
		public DataResponseQuota() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
