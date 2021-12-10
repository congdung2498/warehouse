package com.viettel.vtnet360.vt00.vt000000.entity;

/**
 * Class Entity VT000000EntityRequestParamGetSystemCode
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT000000EntityRqSystemCode {

	/** m_system_code.master_class **/
	private String masterClass;

	/** m_system_code.code_value **/
	private String codeName;

	public VT000000EntityRqSystemCode() {
		super();
	}

	public VT000000EntityRqSystemCode(String masterClass, String codeName) {
		super();
		this.masterClass = masterClass;
		this.setCodeName(codeName);
	}

	public String getMasterClass() {
		return masterClass;
	}

	public void setMasterClass(String masterClass) {
		this.masterClass = masterClass;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

}
