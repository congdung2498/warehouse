package com.viettel.vtnet360.vt01.vt010000.entity;

/**
 * Class Entity VT010000EntityRpGetRecord
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010000EntityRpGetRecord {

	/** status for reponese **/
	private int status;

	/** list data **/
	private VT010000EntityDataGetRecord data;

	public VT010000EntityRpGetRecord() {
		super();
	}

	public VT010000EntityRpGetRecord(int status, VT010000EntityDataGetRecord data) {
		super();
		this.status = status;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public VT010000EntityDataGetRecord getData() {
		return data;
	}

	public void setData(VT010000EntityDataGetRecord data) {
		this.data = data;
	}

}
