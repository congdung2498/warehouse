package com.viettel.vtnet360.vt03.vt030002.entity;

public class Place {
	private int place_id;
	private String place_code;
	private String place_name;
	private String area;    // Ma Dia ban
	private String description;
	private int status;
	public Place() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Place(int place_id, String place_code, String place_name, String area, String description, int status) {
		super();
		this.place_id = place_id;
		this.place_code = place_code;
		this.place_name = place_name;
		this.area = area;
		this.description = description;
		this.status = status;
	}
	public int getPlace_id() {
		return place_id;
	}
	public void setPlace_id(int place_id) {
		this.place_id = place_id;
	}
	public String getPlace_code() {
		return place_code;
	}
	public void setPlace_code(String place_code) {
		this.place_code = place_code;
	}
	public String getPlace_name() {
		return place_name;
	}
	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
