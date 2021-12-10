package com.viettel.vtnet360.vt01.vt010000.entity;

import java.util.List;

/**
 * Class Entity VT010000EntityRpGetData
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010000EntityRpGetData {

	/** status for reponese **/
	private int status;

	/** list data **/
	private List<VT010000EntityData> data;

	public VT010000EntityRpGetData() {
		super();
	}

	public VT010000EntityRpGetData(int status, List<VT010000EntityData> data) {
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

	public List<VT010000EntityData> getData() {
		return data;
	}

	public void setData(List<VT010000EntityData> data) {
		this.data = data;
	}

}
