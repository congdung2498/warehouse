package com.viettel.vtnet360.kitchen.dto;

import com.viettel.vtnet360.common.security.BaseEntity;

public class AbbreviationsPOJO extends BaseEntity {
	
	private String[] listUnit;
	private String kitchenId ;
	private String shortName;
	private int startRow;
	private int rowSize;
	private String note ;
	
	 
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public String[] getListUnit() {
		return listUnit;
	}
	public void setListUnit(String[] listUnit) {
		this.listUnit = listUnit;
	}
	public String getKitchenId() {
		return kitchenId;
	}
	public void setKitchenId(String kitchenId) {
		this.kitchenId = kitchenId;
	}
	
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public AbbreviationsPOJO(String[] listUnit, String kitchenId, String shortName, int startRow, int rowSize,
			String note) {
		super();
		this.listUnit = listUnit;
		this.kitchenId = kitchenId;
		this.shortName = shortName;
		this.startRow = startRow;
		this.rowSize = rowSize;
		this.note = note;
	}
	public AbbreviationsPOJO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
