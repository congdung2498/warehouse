package com.viettel.vtnet360.vt05.vt050012.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class VT050012RequestParamQuota extends BaseEntity {

	private Double quota;
	
	private int quotaInt;
		
	private Integer quantity;
	
	private Integer dateLimit;

	private String[] listStatus;

	/** use for limit number of record */
	private int pageNumber;

	/** use for limit number of record */
	private int pageSize;
	
	
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

	public String[] getListStatus() {
		return listStatus;
	}

	public void setListStatus(String[] listStatus) {
		this.listStatus = listStatus;
	}

	public Double getQuota() {
		return quota;
	}

	public void setQuota(Double quota) {
		this.quota = quota;
	}

	public int getQuotaInt() {
		return quotaInt;
	}

	public void setQuotaInt(int quotaInt) {
		this.quotaInt = quotaInt;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getDateLimit() {
		return dateLimit;
	}

	public void setDateLimit(Integer dateLimit) {
		this.dateLimit = dateLimit;
	}
}
