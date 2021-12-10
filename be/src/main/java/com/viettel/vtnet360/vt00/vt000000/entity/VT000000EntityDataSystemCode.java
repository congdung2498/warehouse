package com.viettel.vtnet360.vt00.vt000000.entity;

/**
 * Class Entity VT000000EntityDataSystemCode
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT000000EntityDataSystemCode {

	/** m_system_code.code_value **/
	private String codeValue;

	/** m_system_code.code_name **/
	private String codeName;

	public VT000000EntityDataSystemCode() {
		super();
	}

	public VT000000EntityDataSystemCode(String codeValue, String codeName) {
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
