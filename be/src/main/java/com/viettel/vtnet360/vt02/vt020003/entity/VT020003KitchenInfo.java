package com.viettel.vtnet360.vt02.vt020003.entity;

import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK 09/08/2018
 */
public class VT020003KitchenInfo extends BaseEntity {

	/** KITCHEN_SETTING.KITCHEN_ID */
	private String kitchenID;

	/** KITCHEN_SETTING.KITCHEN_NAME */
	private String kitchenName;

	/** KITCHEN_SETTING.PLACE_ID = QLDV_PLACE.PLACE_ID */
	private int placeID;

	/** QLDV_PLACE.PLACE_NAME */
	private String placeName;

	/** KITCHEN_SETTING.CHEF_NAME */
	private String chefName;

	/** KITCHEN_SETTING.CHEF_ACCOUNT */
	private String chefUserName;

	/** KITCHEN_SETTING.CHEF_PHONE */
	private String chefPhone;

	/** KITCHEN_SETTING.NOTE */
	private String note;

	/** KITCHEN_SETTING.PRICE */
	private double price;

	/** KITCHEN_SETTING.STATUS */
	private int status;

	/** KITCHEN_SETTING.DELETE_FLAG */
	private int deleteFG;

	/** list phone number setting to receive SMS in KITCHEN_PHONE_GET_SMS */
	private List<String> listPhoneNumberReceiveSms;
	
	private String isEmployee;
	
	private List<VT020003Chef> lstChef;
	
	public VT020003KitchenInfo() {
	}

	public VT020003KitchenInfo(String kitchenID, String kitchenName, int placeID, String placeName, String chefName,
			String chefUserName, String chefPhone, String note, double price, int status, int deleteFG,
			List<String> listPhoneNumberReceiveSms) {
		super();
		this.kitchenID = kitchenID;
		this.kitchenName = kitchenName;
		this.placeID = placeID;
		this.placeName = placeName;
		this.chefName = chefName;
		this.chefUserName = chefUserName;
		this.chefPhone = chefPhone;
		this.note = note;
		this.price = price;
		this.status = status;
		this.deleteFG = deleteFG;
		this.listPhoneNumberReceiveSms = listPhoneNumberReceiveSms;
	}

	public String getKitchenID() {
		return kitchenID;
	}

	public void setKitchenID(String kitchenID) {
		this.kitchenID = kitchenID;
	}

	public String getKitchenName() {
		return kitchenName;
	}

	public void setKitchenName(String kitchenName) {
		this.kitchenName = kitchenName;
	}

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getChefName() {
		return chefName;
	}

	public void setChefName(String chefName) {
		this.chefName = chefName;
	}

	public String getChefUserName() {
		return chefUserName;
	}

	public void setChefUserName(String chefUserName) {
		this.chefUserName = chefUserName;
	}

	public String getChefPhone() {
		return chefPhone;
	}

	public void setChefPhone(String chefPhone) {
		this.chefPhone = chefPhone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDeleteFG() {
		return deleteFG;
	}

	public void setDeleteFG(int deleteFG) {
		this.deleteFG = deleteFG;
	}

	public List<String> getListPhoneNumberReceiveSms() {
		return listPhoneNumberReceiveSms;
	}

	public void setListPhoneNumberReceiveSms(List<String> listPhoneNumberReceiveSms) {
		this.listPhoneNumberReceiveSms = listPhoneNumberReceiveSms;
	}

	public String getIsEmployee() {
		return isEmployee;
	}

	public void setIsEmployee(String isEmployee) {
		this.isEmployee = isEmployee;
	}

	public List<VT020003Chef> getLstChef() {
		return lstChef;
	}

	public void setLstChef(List<VT020003Chef> lstChef) {
		this.lstChef = lstChef;
	}
	
}
