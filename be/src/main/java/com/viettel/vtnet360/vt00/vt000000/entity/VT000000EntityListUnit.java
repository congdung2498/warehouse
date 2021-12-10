package com.viettel.vtnet360.vt00.vt000000.entity;

/**
 * Class entity list of units VT000000
 * 
 * @author ThangBT 07/09/2018
 *
 */
public class VT000000EntityListUnit {

	/** column common */
	private String result;

	/** name of unit */
	private String unitName;

	/** id of unit */
	private String unitId;

	/** start page */
	private int pageSize;

	/** number of record each page */
	private int pageNumber;

	public VT000000EntityListUnit() {
		super();
	}

	public VT000000EntityListUnit(String result, String unitName, String unitId, int pageSize, int pageNumber) {
		super();
		this.result = result;
		this.unitName = unitName;
		this.unitId = unitId;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}
