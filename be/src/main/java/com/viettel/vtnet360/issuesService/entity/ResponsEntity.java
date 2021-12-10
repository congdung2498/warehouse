package com.viettel.vtnet360.issuesService.entity;

public class ResponsEntity {
	private int status;
	private Object data;
	
	public ResponsEntity(int status, Object data) {
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
