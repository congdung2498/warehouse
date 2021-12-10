package com.viettel.vtnet360.vt03.vt030013.entity;

import java.util.List;

public class FreeCar {
  private List<CarDriverManage> listFreeCar;

  private int total;

  public List<CarDriverManage> getListFreeCar() {
    return listFreeCar;
  }

  public void setListFreeCar(List<CarDriverManage> listFreeCar) {
    this.listFreeCar = listFreeCar;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

}
