package com.viettel.vtnet360.vt04.vt040007.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class VT040007EntityRqFindSt extends BaseEntity {

	/** pattern */
	private String pattern;

	/** Size Record 1 page **/
	private int pageSize;

	private String masterCode;

	/** number page **/
	private int pageNumber;

	private int getSize;

	private int fromIndex;

	public VT040007EntityRqFindSt() {
		super();
	}

	public VT040007EntityRqFindSt(String pattern, int pageSize, String masterCode, int pageNumber, int getSize,
			int fromIndex) {
		super();
		this.pattern = pattern;
		this.pageSize = pageSize;
		this.masterCode = masterCode;
		this.pageNumber = pageNumber;
		this.getSize = getSize;
		this.fromIndex = fromIndex;
	}

	public String getMasterCode() {
		return masterCode;
	}

	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
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

	public int getGetSize() {
		return getSize;
	}

	public void setGetSize(int getSize) {
		this.getSize = getSize;
	}

	public int getFromIndex() {
		return fromIndex;
	}

	public void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
