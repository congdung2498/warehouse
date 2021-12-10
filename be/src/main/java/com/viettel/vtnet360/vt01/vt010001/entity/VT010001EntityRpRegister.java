package com.viettel.vtnet360.vt01.vt010001.entity;

/**
 * Class Entity VT010001EntityResponseRegister
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010001EntityRpRegister {

	/** status for reponese **/
	private int status;

	/** data **/
	private String data;

	public VT010001EntityRpRegister() {
		super();
	}

	public VT010001EntityRpRegister(int status, String data) {
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
