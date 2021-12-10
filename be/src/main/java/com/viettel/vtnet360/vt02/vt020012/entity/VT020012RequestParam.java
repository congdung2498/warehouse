package com.viettel.vtnet360.vt02.vt020012.entity;

import java.util.Date;
import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class for parse request data of client for VT020009 
 * 
 * @author VinhNVQ 9/8/2018
 *
 */
public class VT020012RequestParam extends BaseEntity {
	
	/** KitchenID */
	String kitchenID;
	
	/** Month for search */
	Date month;
	
	/** List unit to filter */
	List<String> listUnitForSearch;
	
	List<String> kitchenIds;


	public String getKitchenID() { return kitchenID; }
	public void setKitchenID(String kitchenID) { this.kitchenID = kitchenID; }

	public Date getMonth() { return month; }
	public void setMonth(Date month) { this.month = month; }

	public List<String> getListUnitForSearch() { return listUnitForSearch; }
	public void setListUnitForSearch(List<String> listUnitForSearch) { this.listUnitForSearch = listUnitForSearch; }

  public List<String> getKitchenIds() { return kitchenIds; }
  public void setKitchenIds(List<String> kitchenIds) { this.kitchenIds = kitchenIds; }
}
