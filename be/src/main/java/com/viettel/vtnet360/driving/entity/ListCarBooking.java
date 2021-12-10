package com.viettel.vtnet360.driving.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

public class ListCarBooking extends BaseEntity {
	private String appoverQltt;
	private String appoverLddv;
	private String appoverCvp;
	private String[] listCar;
	private Boolean isAQltt;
	private Boolean isAQldv;
	private Boolean isACvp;
	
	private String loginUsername;
	
	
  public Boolean getIsAQltt() {
		return isAQltt;
	}

	public void setIsAQltt(Boolean isAQltt) {
		this.isAQltt = isAQltt;
	}

	public Boolean getIsAQldv() {
		return isAQldv;
	}

	public void setIsAQldv(Boolean isAQldv) {
		this.isAQldv = isAQldv;
	}

	public Boolean getIsACvp() {
		return isACvp;
	}

	public void setIsACvp(Boolean isACvp) {
		this.isACvp = isACvp;
	}

	public String getAppoverQltt() {
		return appoverQltt;
	}

	public void setAppoverQltt(String appoverQltt) {
		this.appoverQltt = appoverQltt;
	}

	public String getAppoverLddv() {
		return appoverLddv;
	}

	public void setAppoverLddv(String appoverLddv) {
		this.appoverLddv = appoverLddv;
	}

	public String getAppoverCvp() {
		return appoverCvp;
	}

	public void setAppoverCvp(String appoverCvp) {
		this.appoverCvp = appoverCvp;
	}

	
	public String[] getListCar() {
		return listCar;
	}

	public void setListCar(String[] listCar) {
		this.listCar = listCar;
	}
	
	public String getLoginUsername() {
    return loginUsername;
  }

  public void setLoginUsername(String loginUsername) {
    this.loginUsername = loginUsername;
  }
}
