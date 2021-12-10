package com.viettel.vtnet360.kitchen.dto;

import java.util.Date;

public class MenuSetting {
  
  private String  menuId;
  private String  dishID;
  private String  dishName;
  private String  image;
  private Date    dateOfMenu;
  private String  chefId;
  private String  kitchenId;
  
  
  public String getMenuId() { return menuId; }
  public void setMenuId(String menuId) { this.menuId = menuId; }
  
  public String getDishID() { return dishID; }
  public void setDishID(String dishID) { this.dishID = dishID; }
  
  public String getDishName() { return dishName; }
  public void setDishName(String dishName) { this.dishName = dishName; }
  
  public String getImage() { return image; }
  public void setImage(String image) { this.image = image; }
  
  public Date getDateOfMenu() { return dateOfMenu; }
  public void setDateOfMenu(Date dateOfMenu) { this.dateOfMenu = dateOfMenu; }
  
  public String getChefId() { return chefId; }
  public void setChefId(String chefId) { this.chefId = chefId; }
  
  public String getKitchenId() { return kitchenId; }
  public void setKitchenId(String kitchenId) { this.kitchenId = kitchenId; }
}
