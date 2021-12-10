package com.viettel.vtnet360.vt02.vt020013.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class for parse request data of client for VT020009 
 * 
 * @author VinhNVQ 9/8/2018
 *
 */
public class VT020013RequestParam extends BaseEntity {
	
	/** KitchenID */
	private String kitchenID;
	
	/** Month for search */
	private Date month;
	
	/** List unit to filter */
	private List<String> listUnitForSearch;
	
	/** Username to filter */
	private String userName;
	
	/** Randomness Search */
	private String query;
	
	private List<String> kitchenIds;


	public String getKitchenID() { return kitchenID; }
	public void setKitchenID(String kitchenID) { this.kitchenID = kitchenID; }

	public Date getMonth() { return month; }
	public void setMonth(Date month) { this.month = month; }

	public List<String> getListUnitForSearch() { return listUnitForSearch; }
	public void setListUnitForSearch(List<String> listUnitForSearch) { this.listUnitForSearch = listUnitForSearch; }

	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }

	public String getQuery() { return query; }
	public void setQuery(String query) { this.query = query; }

  public List<String> getKitchenIds() { return kitchenIds; }
  public void setKitchenIds(List<String> kitchenIds) { this.kitchenIds = kitchenIds; }
}
