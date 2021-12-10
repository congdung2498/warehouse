package com.viettel.vtnet360.vt02.vt020004.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK 12/09/2018
 */
public class VT020004InfoToFindMenu extends BaseEntity {

	/** MENU_SETTING.CHEF_ID = QLDV_EMPLOYEE.USER_NAME */
	private String chefID;

	/** get dateFrom from client */
	private Date dateFrom;

	/** get dateTo from client */
	private Date dateTo;

	/** page number get from client use for number of record get from DB */
	private int pageNumber;

	/** page size get from client use for number of record get from DB */
	private int pageSize;

	/** MENU_SETTING.KITCHEN_ID */
	private String kitchenID;
	
	public VT020004InfoToFindMenu() {

	}

	public VT020004InfoToFindMenu(String chefID, Date dateFrom, Date dateTo, int pageNumber, int pageSize,
			String kitchenID) {
		super();
		this.chefID = chefID;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.kitchenID = kitchenID;
	}

	public String getChefID() {
		return chefID;
	}

	public void setChefID(String chefID) {
		this.chefID = chefID;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}
}
