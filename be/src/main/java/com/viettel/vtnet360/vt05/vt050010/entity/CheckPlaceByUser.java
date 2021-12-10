package com.viettel.vtnet360.vt05.vt050010.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class CheckPlaceByUser extends BaseEntity {

  private boolean isAdmin;
  private boolean isHCVPP;
  private String userName;
  private String placeName;
  private int placeId;
  private int pageSize;
  private int pageNumber;
  
  
  public String getPlaceName() {
    return placeName;
  }
  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }
  public int getPlaceId() {
    return placeId;
  }
  public void setPlaceId(int placeId) {
    this.placeId = placeId;
  }
  public int getPageSize() {
    return pageSize;
  }
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
  public int getPageNumber() {
    return pageNumber;
  }
  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }
  public boolean isAdmin() {
    return isAdmin;
  }
  public void setAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }
  public boolean isHCVPP() {
    return isHCVPP;
  }
  public void setHCVPP(boolean isHCVPP) {
    this.isHCVPP = isHCVPP;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
 
  public CheckPlaceByUser(boolean isAdmin, boolean isHCVPP, String userName, String placeName, int placeId,
      int pageSize, int pageNumber) {
    super();
    this.isAdmin = isAdmin;
    this.isHCVPP = isHCVPP;
    this.userName = userName;
    this.placeName = placeName;
    this.placeId = placeId;
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
  }
  public CheckPlaceByUser() {
    super();
    // TODO Auto-generated constructor stub
  }
  
  
}
