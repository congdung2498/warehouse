package com.viettel.vtnet360.vt02.vt020002.entity;

/** 
 * @author DuyNK 08/09/2018
 */
public class VT020002Kitchen {
	
	/** KITCHEN_SETTING.KITCHEN_ID */
  private String kitchenID;

  /** KITCHEN_SETTING.KITCHEN_NAME */
  private String kitchenName;

  /** KITCHEN_SETTING.CHEF_NAME */
  private String chefName;

  /** KITCHEN_SETTING.STATUS */
  private int status;

  private int    placeID;

  private String   placeName;

  private String chefUserName;

  private String chefPhone;

  private String    isEmployee;

  private String   note;

  private double price;

	
	public VT020002Kitchen() { }

	public VT020002Kitchen(String kitchenID, String kitchenName, String chefName, int status) {
		super();
		this.kitchenID = kitchenID;
		this.kitchenName = kitchenName;
		this.chefName = chefName;
		this.status = status;
	}

	public String getKitchenID() { return kitchenID; }
	public void setKitchenID(String kitchenID) { this.kitchenID = kitchenID; }

	public String getKitchenName() { return kitchenName; }
	public void setKitchenName(String kitchenName) { this.kitchenName = kitchenName; }

	public String getChefName() { return chefName; }
	public void setChefName(String chefName) { this.chefName = chefName; }

	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }

  public int getPlaceID() { return placeID; }
  public void setPlaceID(int placeID) { this.placeID = placeID; }

  public String getPlaceName() { return placeName; }
  public void setPlaceName(String placeName) { this.placeName = placeName; }

  public String getChefUserName() { return chefUserName; }
  public void setChefUserName(String chefUserName) { this.chefUserName = chefUserName; }

  public String getChefPhone() { return chefPhone; }
  public void setChefPhone(String chefPhone) { this.chefPhone = chefPhone; }

  public String getIsEmployee() { return isEmployee; }
  public void setIsEmployee(String isEmployee) { this.isEmployee = isEmployee; }

  public String getNote() { return note; }
  public void setNote(String note) { this.note = note; }

  public double getPrice() { return price; }
  public void setPrice(double price) { this.price = price; }
}
