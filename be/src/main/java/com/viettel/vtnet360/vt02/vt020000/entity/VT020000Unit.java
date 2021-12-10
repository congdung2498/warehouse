package com.viettel.vtnet360.vt02.vt020000.entity;

/**
 * Entity for mapping data from database
 * @author VinhNVQ 9/9/2018
 *
 */
public class VT020000Unit {
	private Long   unitId;
	private String unitName;
	private Long   parentId;
	private String path;
	private Long   order;
	private Long   isLeaf;
	private Long orderNumber;
	
	public VT020000Unit(Long unitId, String unitName, Long parentId, String path, Long order, Long isLeaf) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
		this.parentId = parentId;
		this.path = path;
		this.order = order;
		this.isLeaf = isLeaf;
	}
	
	
	public VT020000Unit(Long unitId, String unitName, Long parentId, String path) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
		this.parentId = parentId;
		this.path = path;
	}
	
	public VT020000Unit(Long unitId, String unitName, Long parentId, String path, Long orderNumber) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
		this.parentId = parentId;
		this.path = path;
		this.orderNumber = orderNumber;
	}

	public Long getUnitId() { return unitId; }
	public void setUnitId(Long unitId) { this.unitId = unitId; }
	
	public String getUnitName() { return unitName; }
	public void setUnitName(String unitName) { this.unitName = unitName; }
	
	public Long getParentId() { return parentId; }
	public void setParentId(Long parentId) { this.parentId = parentId; }
	
	public String getPath() { return path; }
	public void setPath(String path) { this.path = path; }
	
	public Long getOrder() { return order; }
	public void setOrder(Long order) { this.order = order; }
	
	public Long getIsLeaf() { return isLeaf; }
	public void setIsLeaf(Long isLeaf) { this.isLeaf = isLeaf; }


	public Long getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	
}
