package com.viettel.vtnet360.vt02.vt020000.entity;

/**
 * Entity for response from API
 * @author VinhNVQ 9/9/2018
 *
 */
public class VT020000ResponseEntity {
	int status;
	Object data;
	
	public VT020000ResponseEntity(int status, Object data) {
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
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
}
