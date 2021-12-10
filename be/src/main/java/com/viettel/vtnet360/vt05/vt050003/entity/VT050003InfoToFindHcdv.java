package com.viettel.vtnet360.vt05.vt050003.entity;

/**
 * @author DuyNK
 *
 */
public class VT050003InfoToFindHcdv {

	/** STATIONERY_STAFF.ISSUE_LOCATION */
	private int placeID;

	/** QLDV_EMPLOYEE.USER_NAME of employee request stationery to find unitID */
	private String userName;

	/** STATIONERY_STAFF.JOB_CODE */
	private String jobCode;

	public VT050003InfoToFindHcdv() {

	}

	public VT050003InfoToFindHcdv(int placeID, String userName, String jobCode) {
		super();
		this.placeID = placeID;
		this.userName = userName;
		this.jobCode = jobCode;
	}

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
}
