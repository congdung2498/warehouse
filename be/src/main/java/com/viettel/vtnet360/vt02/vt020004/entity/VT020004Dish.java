package com.viettel.vtnet360.vt02.vt020004.entity;

/**
 * @author DuyNK 12/09/2018
 * 
 */
public class VT020004Dish {

	/** MENU_SETTING.DISH_ID = DISH_SETTING.DISH_ID */
	private String   dishID;

	/** DISH_SETTING.DISH_NAME */
	private String   dishName;
	
	private String   image;
	
	private int      isImage;
	
	public VT020004Dish() { }
	
	public VT020004Dish(String dishID, String dishName, String image) {
		super();
		this.dishID = dishID;
		this.dishName = dishName;
		this.image = image;
	}

	public String getImage() { return image; }
	public void setImage(String image) { this.image = image; }

	public String getDishID() { return dishID; }
	public void setDishID(String dishID) { this.dishID = dishID; }

	public String getDishName() { return dishName; }
	public void setDishName(String dishName) { this.dishName = dishName; }
	
  public int getIsImage() { return isImage; }
  public void setIsImage(int isImage) { this.isImage = isImage; }
}
