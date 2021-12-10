package com.viettel.vtnet360.vt00.vt000000.entity;

/**
 * Class Entity VT000000EntityRequestParamFindEmployee
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT000000EntityRqFindEmp {

	/** pattern **/
	private String pattern;

	/** role **/
	private String[] role;

	private int pageSize;
	private int pageNumber;
	private int getSize;
	private int fromIndex;

	public VT000000EntityRqFindEmp() {
		super();
	}

	public VT000000EntityRqFindEmp(String pattern, String[] role, int pageSize, int pageNumber, int getSize,
			int fromIndex) {
		super();
		this.pattern = pattern;
		this.role = role;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.getSize = getSize;
		this.fromIndex = fromIndex;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String[] getRole() {
		return role;
	}

	public void setRole(String[] role) {
		this.role = role;
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

}
