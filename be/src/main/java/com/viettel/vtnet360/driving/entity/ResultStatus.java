package com.viettel.vtnet360.driving.entity;

public class ResultStatus {
	
	private Integer status;
	private String label;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
	public ResultStatus(Integer status, String label) {
		super();
		this.status = status;
		this.label = label;
	}

	public ResultStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
