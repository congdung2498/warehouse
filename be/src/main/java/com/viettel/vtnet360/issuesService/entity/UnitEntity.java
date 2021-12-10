package com.viettel.vtnet360.issuesService.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class UnitEntity extends BaseEntity {
	private Long unitId;
	private String unitName;
	private Long parentId;
	private String path;
	private Long order;
	private Long isLeaf;
	private Long orderNumber;
	private String query;
	
	public UnitEntity() {
		super();
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public Long getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Long isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

}
