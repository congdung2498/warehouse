package com.viettel.vtnet360.vt05.vt050010.entity;

import java.util.Date;
import java.util.List;

/**
 * @author DuyNK
 *
 */
public class VT050010InfoToFindRequestInfo extends VT050010RequestParam {

	/** ISSUES_STATIONERY_APPROVED.STATUS */
	private List<Integer> listStatus;

	/** M_SYSTEM_CODE.MASTER_CLASS to calcul limit of 1 unit */
	private String mClass;

	/** M_SYSTEM_CODE.CODE_VALUE to calcul limit of 1 unit */
	private String mCode;

	/** use for limit search by ISSUES_STATIONERY.REQUEST_DATE */

	private int pageNumber;

	private int pageSize;

	private boolean isHcvp;

	private boolean isAdmin;

	private Date dateRequestInfo;

	private String[] placeIds;

	private String month;

	private String year;

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public VT050010InfoToFindRequestInfo() {

	}

	public Date getDateRequestInfo() {
		return dateRequestInfo;
	}

	public void setDateRequestInfo(Date dateRequestInfo) {
		this.dateRequestInfo = dateRequestInfo;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isHcvp() {
		return isHcvp;
	}

	public void setHcvp(boolean isHcvp) {
		this.isHcvp = isHcvp;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	public List<Integer> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<Integer> listStatus) {
		this.listStatus = listStatus;
	}

	public String getmClass() {
		return mClass;
	}

	public void setmClass(String mClass) {
		this.mClass = mClass;
	}

	public String getmCode() {
		return mCode;
	}

	public void setmCode(String mCode) {
		this.mCode = mCode;
	}

	public String[] getPlaceIds() {
		return placeIds;
	}

	public void setPlaceIds(String[] placeIds) {
		this.placeIds = placeIds;
	}
}
