package com.viettel.vtnet360.vt07.vt070000.entity;

public class WarehouseDiagramParam {
	private int type;
	private int row;
	private int column;
	private String numberPerSlot;
	
	public WarehouseDiagramParam() {
	}

	public WarehouseDiagramParam(int type, int row, int column, String numberPerSlot) {
		super();
		this.type = type;
		this.row = row;
		this.column = column;
		this.numberPerSlot = numberPerSlot;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNumberPerSlot() {
		return numberPerSlot;
	}

	public void setNumberPerSlot(String numberPerSlot) {
		this.numberPerSlot = numberPerSlot;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	@Override
	public String toString() {
		return "WarehouseDiagramParam [type=" + type + ", numberPerSlot=" + numberPerSlot + "]";
	}
	
}
