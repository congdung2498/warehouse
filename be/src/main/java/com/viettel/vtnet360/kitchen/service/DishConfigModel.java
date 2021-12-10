package com.viettel.vtnet360.kitchen.service;

import java.util.List;

import com.viettel.vtnet360.kitchen.dto.KitchenDTO;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002Dish;

public class DishConfigModel {
  
  private List<VT020002Dish>    dish;
  private List<KitchenDTO>      kitchens;
  private int                   totalDish;
  
  public DishConfigModel(List<VT020002Dish> dish, List<KitchenDTO> kitchens, int total) {
    this.dish = dish;
    this.kitchens = kitchens;
    this.totalDish = total;
  }

  
  public List<VT020002Dish> getDish() { return dish; }
  public void setDish(List<VT020002Dish> dish) { this.dish = dish; }

  public List<KitchenDTO> getKitchens() { return kitchens; }
  public void setKitchens(List<KitchenDTO> kitchens) { this.kitchens = kitchens; }

  public int getTotalDish() { return totalDish; }
  public void setTotalDish(int totalDish) { this.totalDish = totalDish; }
}
