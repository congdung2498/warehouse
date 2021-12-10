package com.viettel.vtnet360.stationery.service;

import java.util.List;

import com.viettel.vtnet360.vt05.vt050012.entity.VT050012DataResponse;

public class ResquestModel {
	
	private List<VT050012DataResponse> 	requests;
	private int 						totalRecords;
	
	public ResquestModel(List<VT050012DataResponse> requests, int totalRecords) {
		this.requests = requests;
		this.totalRecords = totalRecords;
	}
	
	
	public List<VT050012DataResponse> getRequests() { return requests; }
	public void setRequests(List<VT050012DataResponse> requests) { this.requests = requests; }
	
	public int getTotalRecords() { return totalRecords; }
	public void setTotalRecords(int totalRecords) { this.totalRecords = totalRecords; }
}
