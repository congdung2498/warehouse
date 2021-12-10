package com.viettel.vtnet360.vt05.vt050000.entity;

import java.util.List;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK
 *
 */
public class VT050000InfoToFindStationery extends BaseEntity {

	/** STATIONERY_ITEMS.STATIONERY_NAME */
	private String stationeryName;

	/** STATIONERY_ITEMS.STATUS */
	private int status;

	/** M_SYSTEM_CODE.MASTER_CLASS */
	private String masterClass;
	
	/** List stationery that used => not get in list */
	private List<String> listStationeryNotGet; 
	
	/** page number to limit records response */
	private int pageNumber;

	/** page size to limit records response */
	private int pageSize;
	
	public VT050000InfoToFindStationery() {
	
	}

	public VT050000InfoToFindStationery(String stationeryName, int status, String masterClass,
			List<String> listStationeryNotGet, int pageNumber, int pageSize) {
		super();
		this.stationeryName = stationeryName;
		this.status = status;
		this.masterClass = masterClass;
		this.listStationeryNotGet = listStationeryNotGet;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMasterClass() {
		return masterClass;
	}

	public void setMasterClass(String masterClass) {
		this.masterClass = masterClass;
	}

	public List<String> getListStationeryNotGet() {
		return listStationeryNotGet;
	}

	public void setListStationeryNotGet(List<String> listStationeryNotGet) {
		this.listStationeryNotGet = listStationeryNotGet;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
