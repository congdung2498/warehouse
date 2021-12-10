package com.viettel.vtnet360.vt02.vt020002.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/** 
 * @author DuyNK 08/09/2018
 */
public class VT020002DishOffset extends BaseEntity {
	
	/** page number get from client use for number of record get from DB */
	private int pageNumber;
	
	/** page size get from client use for number of record get from DB */
	private int pageSize;
	
	/** DISH_SETTING.DISH_NAME */
	private String dishName;
	
	/** DISH_SETTING.KITCHEN_ID */
	private String kitchenID;
	
	/** DISH_SETTING.DELETE_FG */
	private int deleteFG;
	
	/** DISH_SETTING.STATUS */
	private int status;
	
	private boolean isLoadKitchen;
	
	public VT020002DishOffset() {
		
	}

	public VT020002DishOffset(int pageNumber, int pageSize, String dishName, String kitchenID, int deleteFG, int status) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.dishName = dishName;
		this.kitchenID = kitchenID;
		this.deleteFG = deleteFG;
		this.status = status;
	}

	public int getPageNumber() { return pageNumber; }
	public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }

	public int getPageSize() { return pageSize; }
	public void setPageSize(int pageSize) { this.pageSize = pageSize; }

	public String getDishName() { return dishName; }
	public void setDishName(String dishName) { this.dishName = dishName; }

	public String getKitchenID() { return kitchenID; }
	public void setKitchenID(String kitchenID) { this.kitchenID = kitchenID; }

	public int getDeleteFG() { return deleteFG; }
	public void setDeleteFG(int deleteFG) { this.deleteFG = deleteFG; }

	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }

  public boolean isLoadKitchen() { return isLoadKitchen; }
  public void setLoadKitchen(boolean isLoadKitchen) { this.isLoadKitchen = isLoadKitchen; }
}
