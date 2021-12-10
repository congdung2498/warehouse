package com.viettel.vtnet360.vt00.vt000000.entity;

import com.viettel.vtnet360.common.security.BaseEntity;

/** 
 * @author DuyNK 08/09/2018
 */
public class VT000000RequestParam<T> extends BaseEntity {

	/** action insert(1), update(2), delete(3) */
	private int action;
	
	/** date get from client */
	private T data;
	
	public VT000000RequestParam() {
	
	}

	public VT000000RequestParam(int action, T data) {
		super();
		this.action = action;
		this.data = data;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
