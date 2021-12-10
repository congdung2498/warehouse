package com.viettel.vtnet360.vt05.vt050004.entity;

import java.util.List;

import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationeryItems;

/**
 * @author DuyNK
 *
 */
public class VT050004InfoToUpdateIssuesStationeryItems extends VT050000IssuesStationeryItems {

	/** list ISSUES_STATIONERY_ITEMS.ISSUES_STATIONERY_ITEM_ID */
	private List<String> listIssuesStationeryItemID;

	public VT050004InfoToUpdateIssuesStationeryItems() {

	}

	public VT050004InfoToUpdateIssuesStationeryItems(List<String> listIssuesStationeryItemID) {
		super();
		this.listIssuesStationeryItemID = listIssuesStationeryItemID;
	}

	public List<String> getListIssuesStationeryItemID() {
		return listIssuesStationeryItemID;
	}

	public void setListIssuesStationeryItemID(List<String> listIssuesStationeryItemID) {
		this.listIssuesStationeryItemID = listIssuesStationeryItemID;
	}
}
