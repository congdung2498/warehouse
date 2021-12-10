package com.viettel.vtnet360.vt02.vt020003.entity;

public class VT020003CookerParam {
	String query;
	String role;
	private String kitchenId;
	
	public VT020003CookerParam(String query, String role) {
		super();
		this.query = query;
		this.role = role;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getKitchenId() {
		return kitchenId;
	}
	public void setKitchenId(String kitchenId) {
		this.kitchenId = kitchenId;
	}
	
	
}
