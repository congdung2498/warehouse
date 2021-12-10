package com.viettel.vtnet360.vt05.vt050009.entity;

/**
 * @author DuyNK
 *
 */
public class VT050009InfoToFindRequestWaitingChiefOfStaffApprove {

	/** ISSUES_STATIONERY_ITEMS.STATUS */
	private int issuesStationeryItemStatus;

	/** ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_REGISTRY_ID */
	private String issuesStationeryRegistryID;

	/** M_SYSTEM_CODE.MASTER_CLASS to calcul limit of 1 unit */
	private String mClass;

	/** M_SYSTEM_CODE.CODE_VALUE to calcul limit of 1 unit */
	private String mCode;

	public VT050009InfoToFindRequestWaitingChiefOfStaffApprove() {

	}

	public VT050009InfoToFindRequestWaitingChiefOfStaffApprove(int issuesStationeryItemStatus,
			String issuesStationeryRegistryID, String mClass, String mCode) {
		super();
		this.issuesStationeryItemStatus = issuesStationeryItemStatus;
		this.issuesStationeryRegistryID = issuesStationeryRegistryID;
		this.mClass = mClass;
		this.mCode = mCode;
	}

	public int getIssuesStationeryItemStatus() {
		return issuesStationeryItemStatus;
	}

	public void setIssuesStationeryItemStatus(int issuesStationeryItemStatus) {
		this.issuesStationeryItemStatus = issuesStationeryItemStatus;
	}

	public String getIssuesStationeryRegistryID() {
		return issuesStationeryRegistryID;
	}

	public void setIssuesStationeryRegistryID(String issuesStationeryRegistryID) {
		this.issuesStationeryRegistryID = issuesStationeryRegistryID;
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
