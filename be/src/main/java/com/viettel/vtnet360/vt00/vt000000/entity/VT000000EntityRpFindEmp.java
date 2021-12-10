package com.viettel.vtnet360.vt00.vt000000.entity;

import java.util.List;

/**
 * Class Entity VT000000EntityReponseFindEmployee
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT000000EntityRpFindEmp {

	/** status for reponese **/
	private int status;

	/** list data **/
	private List<VT000000EntityDataFindEmployee> data;

	public VT000000EntityRpFindEmp() {
		super();
	}

	public VT000000EntityRpFindEmp(int status, List<VT000000EntityDataFindEmployee> data) {
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

	public List<VT000000EntityDataFindEmployee> getData() {
		return data;
	}

	public void setData(List<VT000000EntityDataFindEmployee> data) {
		this.data = data;
	}

}
