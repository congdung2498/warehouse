package com.viettel.vtnet360.vt02.vt020000.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Entity for request parameter
 * 
 * @author VinhNVQ 9/9/2018
 *
 */
public class VT020000RequestParam extends BaseEntity {
	private String       query;
	private String       kitChen;
	private String       month;
	private List<String> unit;
	
	
	public String getQuery() { return query; }
	public void setQuery(String query) { this.query = query; }

	public String getKitChen() { return kitChen; }
	public void setKitChen(String kitChen) { this.kitChen = kitChen; }

	public String getMonth() { return month; }
	public void setMonth(String month) { this.month = month; }

	public List<String> getUnit() { return unit; }
	public void setUnit(List<String> unit) { this.unit = unit; }
}
