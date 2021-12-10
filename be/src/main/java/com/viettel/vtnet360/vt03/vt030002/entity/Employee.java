package com.viettel.vtnet360.vt03.vt030002.entity;

public class Employee {
	private int employee_id;
	private String username;
	private String employee_code;
	private String full_name;
	private String employee_phone;
	private String employee_email;
	private int unit_id;
	private String title;
	private String level_employee;
	private String role;
	private int place_id;
	private String comment;
	private int status;
	
	public Employee() {
		
		// TODO Auto-generated constructor stub
	}

	public Employee(int employee_id, String username, String employee_code, String full_name, String employee_phone,
			String employee_email, int unit_id, String title, String level_employee, String role, int place_id,
			String comment, int status) {
		super();
		this.employee_id = employee_id;
		this.username = username;
		this.employee_code = employee_code;
		this.full_name = full_name;
		this.employee_phone = employee_phone;
		this.employee_email = employee_email;
		this.unit_id = unit_id;
		this.title = title;
		this.level_employee = level_employee;
		this.role = role;
		this.place_id = place_id;
		this.comment = comment;
		this.status = status;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmployee_code() {
		return employee_code;
	}

	public void setEmployee_code(String employee_code) {
		this.employee_code = employee_code;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getEmployee_phone() {
		return employee_phone;
	}

	public void setEmployee_phone(String employee_phone) {
		this.employee_phone = employee_phone;
	}

	public String getEmployee_email() {
		return employee_email;
	}

	public void setEmployee_email(String employee_email) {
		this.employee_email = employee_email;
	}

	public int getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(int unit_id) {
		this.unit_id = unit_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLevel_employee() {
		return level_employee;
	}

	public void setLevel_employee(String level_employee) {
		this.level_employee = level_employee;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getPlace_id() {
		return place_id;
	}

	public void setPlace_id(int place_id) {
		this.place_id = place_id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
