package com.viettel.vtnet360.vt02.vt020002.entity;

import java.util.List;

import com.viettel.vtnet360.vt00.common.BaseEntity;

/**
 * @author DuyNK 08/09/2018
 */
public class VT020002Dish extends BaseEntity {

	/** DISH_SETTING.DISH_ID */
	private String dishID;

	/** DISH_SETTING.DISH_NAME */
	private String dishName;

	/** DISH_SETTING.KITCHEN_ID */
	private String kitchenID;

	/** KITCHEN_SETTING.KITCHEN_NAME */
	private String kitchenName;

	/** DISH_SETTING.STATUS */
	private int status;

	/** DISH_SETTING.DELETE_FG */
	private int deleteFG;
	
	private String image;

	/** use for delete */
	private List<String> listDishID;
	
	private int isImage;

	
	public VT020002Dish() { }

	public VT020002Dish(String dishID, String dishName, String kitchenID, String kitchenName, int status, int deleteFG,
			List<String> listDishID) {
		super();
		this.dishID = dishID;
		this.dishName = dishName;
		this.kitchenID = kitchenID;
		this.kitchenName = kitchenName;
		this.status = status;
		this.deleteFG = deleteFG;
		this.listDishID = listDishID;
	}
	

	public String getDishID() { return dishID; }
	public void setDishID(String dishID) { this.dishID = dishID; }

	public String getDishName() { return dishName; }
	public void setDishName(String dishName) { this.dishName = dishName; }

	public String getKitchenID() { return kitchenID; }
	public void setKitchenID(String kitchenID) { this.kitchenID = kitchenID; }

	public String getKitchenName() { return kitchenName; }
	public void setKitchenName(String kitchenName) { this.kitchenName = kitchenName; }

	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }

	public int getDeleteFG() { return deleteFG; }
	public void setDeleteFG(int deleteFG) { this.deleteFG = deleteFG; }

	public List<String> getListDishID() { return listDishID; }
	public void setListDishID(List<String> listDishID) { this.listDishID = listDishID; }

	public String getImage() { return image; }
	public void setImage(String image) { this.image = image; }

  public int getIsImage() { return isImage; }
  public void setIsImage(int isImage) { this.isImage = isImage; }
}
