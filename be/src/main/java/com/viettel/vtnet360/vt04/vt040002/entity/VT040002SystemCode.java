package com.viettel.vtnet360.vt04.vt040002.entity;

/**
 * class entity system code to get stationery type and unit calculation VT040002
 * 
 * @author ThangBT 18/10/2018
 *
 */
public class VT040002SystemCode {

	/** value of code */
	private String codeValue;

	/** name of code */
	private String codeName;

	public VT040002SystemCode() {
		super();
	}

	public VT040002SystemCode(String codeValue, String codeName) {
		super();
		this.codeValue = codeValue;
		this.codeName = codeName;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
}
