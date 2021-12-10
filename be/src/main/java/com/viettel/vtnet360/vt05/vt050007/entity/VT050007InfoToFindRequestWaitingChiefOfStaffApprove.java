package com.viettel.vtnet360.vt05.vt050007.entity;

/**
 * @author DuyNK
 *
 */
public class VT050007InfoToFindRequestWaitingChiefOfStaffApprove extends VT050007RequestParamToFindData {

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private int status;

	/** M_SYSTEM_CODE.MASTER_CLASS to calcul limit of 1 unit */
	private String mClass;

	/** M_SYSTEM_CODE.CODE_VALUE to calcul limit of 1 unit */
	private String mCode;

	public VT050007InfoToFindRequestWaitingChiefOfStaffApprove() {

	}

	public VT050007InfoToFindRequestWaitingChiefOfStaffApprove(int status, String mClass, String mCode) {
		super();
		this.status = status;
		this.mClass = mClass;
		this.mCode = mCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
