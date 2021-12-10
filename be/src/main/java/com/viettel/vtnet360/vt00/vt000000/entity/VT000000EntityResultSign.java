package com.viettel.vtnet360.vt00.vt000000.entity;

import java.util.List;

public class VT000000EntityResultSign {
	private Long result;
    private String message;
    private String controlId;
    private List<String> listEmail;
    /** status for reponese **/
	private int status;
	
	public Long getResult() {
		return result;
	}
	public void setResult(Long result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getControlId() {
		return controlId;
	}
	public void setControlId(String controlId) {
		this.controlId = controlId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public List<String> getListEmail() {
		return listEmail;
	}
	public void setListEmail(List<String> listEmail) {
		this.listEmail = listEmail;
	}
	public VT000000EntityResultSign(Long result, String message, String controlId, int status) {
		super();
		this.result = result;
		this.message = message;
		this.controlId = controlId;
		this.status = status;
	}
	
	public VT000000EntityResultSign(Long result, String message, String controlId) {
		super();
		this.result = result;
		this.message = message;
		this.controlId = controlId;
	}
	
	public VT000000EntityResultSign(Long result, String controlId) {
		super();
		this.result = result;
		this.controlId = controlId;
	}
	public VT000000EntityResultSign(Long result, List<String> listEmail) {
		super();
		this.result = result;
		this.listEmail = listEmail;
	}
	public VT000000EntityResultSign(Long result) {
		super();
		this.result = result;
	}
	public VT000000EntityResultSign() {
		super();
	}
	
    
}
