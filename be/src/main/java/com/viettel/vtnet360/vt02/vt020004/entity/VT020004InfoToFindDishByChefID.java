package com.viettel.vtnet360.vt02.vt020004.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * @author DuyNK 12/09/2018
 * 
 */
public class VT020004InfoToFindDishByChefID extends BaseEntity {

  /** QLDV_EMPLOYEE.USER_NAME */
  private String chefID;

  /** DISH_SETTING.STATUS */
  private int status;

  /** DISH_SETTING.DELETE_FG */
  private int deleteFG;

  /** use for search dish by dish name */
  private String dishName;

  /** MENU_SETTING.KITCHEN_ID */
  private String kitchenID;

  public VT020004InfoToFindDishByChefID() { }

  public VT020004InfoToFindDishByChefID(String chefID, int status, int deleteFG, String dishName, String kitchenID) {
    super();
    this.chefID = chefID;
    this.status = status;
    this.deleteFG = deleteFG;
    this.dishName = dishName;
    this.kitchenID = kitchenID;
  }


  public String getChefID() { return chefID; }
  public void setChefID(String chefID) { this.chefID = chefID; }

  public int getStatus() { return status; }
  public void setStatus(int status) { this.status = status; }

  public int getDeleteFG() { return deleteFG; }
  public void setDeleteFG(int deleteFG) { this.deleteFG = deleteFG; }

  public String getDishName() { return dishName; }
  public void setDishName(String dishName) { this.dishName = dishName; }

  public String getKitchenID() { return kitchenID; }
  public void setKitchenID(String kitchenID) { this.kitchenID = kitchenID; }
}
