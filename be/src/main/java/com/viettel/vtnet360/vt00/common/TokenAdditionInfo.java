package com.viettel.vtnet360.vt00.common;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

public class TokenAdditionInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	String userName;
	String fullName;
	String phoneNumber;
	String email;
	String unitId;
	String title;
	String code;
	String unitName;
	ArrayList<String> role;
	String avatar;
	
	public TokenAdditionInfo(String userName, String fullName, String phoneNumber, String email, String unitId,
			String title, String code, String unitName, ArrayList<String> role, String avatar) {
		super();
		this.userName = userName;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.unitId = unitId;
		this.title = title;
		this.code = code;
		this.unitName = unitName;
		this.role = role;
		this.avatar = avatar;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public ArrayList<String> getRole() {
		return role;
	}

	public void setRole(ArrayList<String> role) {
		this.role = role;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
