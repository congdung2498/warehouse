package com.viettel.vtnet360.vt02.vt020003.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK 09/08/2018
 */
public class VT020003Condition extends BaseEntity {

	/** select from which row */
	private int startRow;

	/** number of row to select */
	private int rowSize;

	public VT020003Condition() {
	}

	public VT020003Condition(int startRow, int rowSize) {
		super();
		this.startRow = startRow;
		this.rowSize = rowSize;
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
