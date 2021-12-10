package com.viettel.vtnet360.kitchen.dto;

public class KitchenDTO {

	private String kitchenID;
	private String kitchenName;
	private int    status;
	private String chefName;
	
	public KitchenDTO() { }
	
	public KitchenDTO(String kitchenID, String kitchenName) {
    this.kitchenID = kitchenID;
    this.kitchenName = kitchenName;
  }
	
	
	public String getKitchenID() { return kitchenID; }
	public void setKitchenID(String kitchenID) { this.kitchenID = kitchenID; }
	
	public String getKitchenName() { return kitchenName; }
	public void setKitchenName(String kitchenName) { this.kitchenName = kitchenName; }

  public int getStatus() { return status; }
  public void setStatus(int status) { this.status = status; }

  public String getChefName() { return chefName; }
  public void setChefName(String chefName) { this.chefName = chefName; }
}
