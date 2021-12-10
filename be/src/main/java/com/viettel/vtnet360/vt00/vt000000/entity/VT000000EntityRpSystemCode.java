package com.viettel.vtnet360.vt00.vt000000.entity;

import java.util.List;

/**
 * Class Entity VT000000EntityReponseGetListSystemCode
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT000000EntityRpSystemCode {

	/** status for reponese **/
	private int status;

	/** list data **/
	private List<VT000000EntityDataSystemCode> data;

	public VT000000EntityRpSystemCode() {
		super();
	}

	public VT000000EntityRpSystemCode(int status, List<VT000000EntityDataSystemCode> data) {
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

	public List<VT000000EntityDataSystemCode> getData() {
		return data;
	}

	public void setData(List<VT000000EntityDataSystemCode> data) {
		this.data = data;
	}

}
