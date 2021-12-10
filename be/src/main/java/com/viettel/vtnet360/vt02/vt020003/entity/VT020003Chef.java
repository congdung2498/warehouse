package com.viettel.vtnet360.vt02.vt020003.entity;

/** 
 * @author DuyNK 09/08/2018
 * 
 */
public class VT020003Chef {

	private String chefUserName;
	
	private String chefName;
	
	private String chefPhone;
	
	private String isEmployee;
	
	public VT020003Chef() {

	}

	public VT020003Chef(String chefUserName, String chefName, String chefPhone) {
		super();
		this.chefUserName = chefUserName;
		this.chefName = chefName;
		this.chefPhone = chefPhone;
	}

	public String getChefUserName() {
		return chefUserName;
	}

	public void setChefUserName(String chefUserName) {
		this.chefUserName = chefUserName;
	}

	public String getChefName() {
		return chefName;
	}

	public void setChefName(String chefName) {
		this.chefName = chefName;
	}

	public String getChefPhone() {
		return chefPhone;
	}

	public void setChefPhone(String chefPhone) {
		this.chefPhone = chefPhone;
	}

	public String getIsEmployee() {
		return isEmployee;
	}

	public void setIsEmployee(String isEmployee) {
		this.isEmployee = isEmployee;
	}

}
