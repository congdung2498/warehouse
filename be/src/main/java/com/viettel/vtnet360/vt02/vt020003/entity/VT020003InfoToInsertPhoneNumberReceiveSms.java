package com.viettel.vtnet360.vt02.vt020003.entity;

import java.util.List;

/**
 * @author DuyNK
 *
 */
public class VT020003InfoToInsertPhoneNumberReceiveSms {

	/** KITCHEN_PHONE_GET_SMS.KITCHEN_ID */
	private String kitchenID;
	
	/** list KITCHEN_PHONE_GET_SMS.PHONE_NUMBER */
	private List<String> listPhoneNumber;
	
	/** KITCHEN_PHONE_GET_SMS.CREATE_USER */
	private String userName;
	
	public VT020003InfoToInsertPhoneNumberReceiveSms() {
	
	}

	public VT020003InfoToInsertPhoneNumberReceiveSms(String kitchenID, List<String> listPhoneNumber, String userName) {
		super();
		this.kitchenID = kitchenID;
		this.listPhoneNumber = listPhoneNumber;
		this.userName = userName;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}

	public List<String> getListPhoneNumber() {
		return listPhoneNumber;
	}

	public void setListPhoneNumber(List<String> listPhoneNumber) {
		this.listPhoneNumber = listPhoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
