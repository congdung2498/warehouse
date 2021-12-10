package com.viettel.vtnet360.vt05.vt050004.entity;

public class VT50004CheckListRequestID {

	private int unitId;
	
	private int issueLocation;

	

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getIssueLocation() {
		return issueLocation;
	}

	public void setIssueLocation(int issueLocation) {
		this.issueLocation = issueLocation;
	}


	public VT50004CheckListRequestID(int unitId, int issueLocation) {
		super();
		this.unitId = unitId;
		this.issueLocation = issueLocation;
	}

	public VT50004CheckListRequestID() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
