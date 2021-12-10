package com.viettel.vtnet360.vt01.vt010000.entity;

/**
 * Class Entity VT010000EntityRpMgAp
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010000EntityRpMgAp {

	/** status for reponese **/
	private int status;

	/** list data **/
	private String data;
	
	/** Duplicate time employees **/
	private String names;
	

	public VT010000EntityRpMgAp() {
		super();
	}

	public VT010000EntityRpMgAp(int status, String data) {
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

  public String getNames() {
    return names;
  }

  public void setNames(String names) {
    this.names = names;
  }
}
