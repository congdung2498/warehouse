package com.viettel.vtnet360.vt05.vt050000.entity;

/**
 * @author DuyNK 04/10/2018
 */
public class VT050000InfoToFindSpendingLimit {

	/** M_SYSTEM_CODE.MASTER_CLASS */
	private String mClass;

	/** M_SYSTEM_CODE.CODE_VALUE */
	private String mCode;

	public VT050000InfoToFindSpendingLimit() {

	}

	public VT050000InfoToFindSpendingLimit(String mClass, String mCode) {
		super();
		this.mClass = mClass;
		this.mCode = mCode;
	}

	public String getmClass() {
		return mClass;
	}

	public void setmClass(String mClass) {
		this.mClass = mClass;
	}

	public String getmCode() {
		return mCode;
	}

	public void setmCode(String mCode) {
		this.mCode = mCode;
	}
}
