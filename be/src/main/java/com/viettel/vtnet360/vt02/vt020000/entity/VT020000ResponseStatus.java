package com.viettel.vtnet360.vt02.vt020000.entity;

/** 
 * @author DuyNK 09/08/2018
 */

public class VT020000ResponseStatus {

	/** status response to client */
	private int status;
	
	public VT020000ResponseStatus() {
		
	}

	public VT020000ResponseStatus(int status) {
		super();
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
