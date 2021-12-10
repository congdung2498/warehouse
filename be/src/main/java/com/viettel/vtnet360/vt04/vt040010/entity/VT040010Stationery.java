package com.viettel.vtnet360.vt04.vt040010.entity;

/**
 * class entity get list of stationery from issueServiceId VT040010
 * 
 * @author ThangBT 21/09/2018
 *
 */
public class VT040010Stationery {

	/** name of stationery */
	private String stationeryName;

	/** type of stationery */
	private String stationeryType;

	/** quantity using of stationery */
	private double stationeryQuan;

	/** unit calculation of stationery */
	private String stationeryUnitCal;

	public VT040010Stationery() {
		super();
	}

	public VT040010Stationery(String stationeryName, String stationeryType, double stationeryQuan,
			String stationeryUnitCal) {
		super();
		this.stationeryName = stationeryName;
		this.stationeryType = stationeryType;
		this.stationeryQuan = stationeryQuan;
		this.stationeryUnitCal = stationeryUnitCal;
	}

	public String getStationeryName() {
		return stationeryName;
	}

	public void setStationeryName(String stationeryName) {
		this.stationeryName = stationeryName;
	}

	public String getStationeryType() {
		return stationeryType;
	}

	public void setStationeryType(String stationeryType) {
		this.stationeryType = stationeryType;
	}

	public double getStationeryQuan() {
		return stationeryQuan;
	}

	public void setStationeryQuan(double stationeryQuan) {
		this.stationeryQuan = stationeryQuan;
	}

	public String getStationeryUnitCal() {
		return stationeryUnitCal;
	}

	public void setStationeryUnitCal(String stationeryUnitCal) {
		this.stationeryUnitCal = stationeryUnitCal;
	}
}
