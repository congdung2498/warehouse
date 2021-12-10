package com.viettel.vtnet360.vt02.vt020000.entity;

/** 
 * @author DuyNK 09/08/2018
 */

public class VT020000ResponseStatusData {

	/** status response to client */
	private int code;
	
	/** message response to client */
	private String message;
	
	public VT020000ResponseStatusData() {
		
	}

	public VT020000ResponseStatusData(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
