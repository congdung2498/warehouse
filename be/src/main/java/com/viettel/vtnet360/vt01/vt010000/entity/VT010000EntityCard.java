package com.viettel.vtnet360.vt01.vt010000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class Entity VT010000EntityCard
 * 
 * @author KienHT 11/08/2018
 * 
 */
public class VT010000EntityCard extends BaseEntity {

	/** inOutRegisterId **/
	private String inOutRegisterId;

	/** idCard **/
	private String idCard;

	/** status **/
	private int status;

	public VT010000EntityCard() {
		super();
	}

	public VT010000EntityCard(String inOutRegisterId, String idCard, int status) {
		super();
		this.inOutRegisterId = inOutRegisterId;
		this.idCard = idCard;
		this.status = status;
	}

	public String getInOutRegisterId() {
		return inOutRegisterId;
	}

	public void setInOutRegisterId(String inOutRegisterId) {
		this.inOutRegisterId = inOutRegisterId;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
