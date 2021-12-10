package com.viettel.vtnet360.signVoffice.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

public class SignVofficeSearch extends BaseEntity {
	private Long signVofficeId;
	private Date fromDate;
	private Date toDate;
	private String documentCode;
	private String signUserName;
	private int status;
	private int type;
	private int isManager;
	private String unitId;
	private int startRow;
	private int rowSize;

	public Long getSignVofficeId() {
		return signVofficeId;
	}

	public void setSignVofficeId(Long signVofficeId) {
		this.signVofficeId = signVofficeId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public String getSignUserName() {
		return signUserName;
	}

	public void setSignUserName(String signUserName) {
		this.signUserName = signUserName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsManager() {
		return isManager;
	}

	public void setIsManager(int isManager) {
		this.isManager = isManager;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

}
