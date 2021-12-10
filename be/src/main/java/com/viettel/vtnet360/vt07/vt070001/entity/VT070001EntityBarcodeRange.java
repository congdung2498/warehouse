package com.viettel.vtnet360.vt07.vt070001.entity;

import com.viettel.vtnet360.vt00.common.BaseEntity;

public class VT070001EntityBarcodeRange extends BaseEntity {
	private int barCodeRangeId;
	private int barCodePrefixId;
	private String prefix;
	private int status;
	private boolean printed;
	private int quantity;
	private String description;
	/** pageNumber in one page */
	private int pageNumber;	
	/** number of records in a page */
	private int pageSize;	
	/** total all records */
	private int totalRecords;


	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public int getBarCodeRangeId() {
		return barCodeRangeId;
	}

	public void setBarCodeRangeId(int barCodeRangeId) {
		this.barCodeRangeId = barCodeRangeId;
	}

	public int getBarCodePrefixId() {
		return barCodePrefixId;
	}

	public void setBarCodePrefixId(int barCodePrefixId) {
		this.barCodePrefixId = barCodePrefixId;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isPrinted() {
		return printed;
	}

	public void setPrinted(boolean printed) {
		this.printed = printed;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

}
