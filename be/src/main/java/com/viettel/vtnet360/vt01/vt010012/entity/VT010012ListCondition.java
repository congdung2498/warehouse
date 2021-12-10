package com.viettel.vtnet360.vt01.vt010012.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * class entity condition VT010012
 * 
 * @author ThangBT 10/11/2018
 *
 */
public class VT010012ListCondition  extends BaseEntity {

	/** Information related to employees */
	private String personInfo;

	/** list of unit id */
	private String[] listUnit;

	/** id of card */
	private String cardId;

	/** total record */
	private int numOfRows;

	/** select from which row */
	private int startRow;

	/** number of row to select */
	private int rowSize;

	public VT010012ListCondition() {
		super();
	}

	public VT010012ListCondition(String personInfo, String[] listUnit, String cardId, int numOfRows, int startRow,
			int rowSize) {
		super();
		this.personInfo = personInfo;
		this.listUnit = listUnit;
		this.cardId = cardId;
		this.numOfRows = numOfRows;
		this.startRow = startRow;
		this.rowSize = rowSize;
	}

	public String getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(String personInfo) {
		this.personInfo = personInfo;
	}

	public String[] getListUnit() {
		return listUnit;
	}

	public void setListUnit(String[] listUnit) {
		this.listUnit = listUnit;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public int getNumOfRows() {
		return numOfRows;
	}

	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
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
