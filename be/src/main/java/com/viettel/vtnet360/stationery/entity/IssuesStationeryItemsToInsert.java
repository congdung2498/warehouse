package com.viettel.vtnet360.stationery.entity;

import java.util.List;

import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationeryItems;

public class IssuesStationeryItemsToInsert extends VT050000IssuesStationeryItems {

	private List<StationeryParam> listStationery;

	public List<StationeryParam> getListStationery() {
		return listStationery;
	}

	public void setListStationery(List<StationeryParam> listStationery) {
		this.listStationery = listStationery;
	}

	public IssuesStationeryItemsToInsert(List<StationeryParam> listStationery) {
		super();
		this.listStationery = listStationery;
	}

	public IssuesStationeryItemsToInsert() {
		super();
		// TODO Auto-generated constructor stub
	}

}
