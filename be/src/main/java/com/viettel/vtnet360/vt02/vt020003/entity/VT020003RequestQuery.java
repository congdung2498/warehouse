package com.viettel.vtnet360.vt02.vt020003.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class VT020003RequestQuery extends BaseEntity {
	String query;
	private String kitchenId;
	
	public VT020003RequestQuery() {
		super();
	}

	public VT020003RequestQuery(String query) {
		super();
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getKitchenId() {
		return kitchenId;
	}

	public void setKitchenId(String kitchenId) {
		this.kitchenId = kitchenId;
	}
	
}
