package com.viettel.vtnet360.vt04.vt040000.entity;

import java.util.Date;

import com.viettel.vtnet360.common.security.BaseEntity;

/**
 * Class VT040000EntityRqFind
 * 
 * @author KienHT 27/09/2018
 * 
 */
public class VT040000EntityRqFind extends BaseEntity {

	/** param for search Service */

	/** patterm sreach */
	private String pattern;

	/** serviceLocation sreach */
	private int serviceLocation;

	/** param For Find issued Service */

	/** UNIT_ID */
	private int unitId;

	/** name Of Requester */
	private String userNameOfRequester;

	/** phone Of Requester */
	private String phoneOfRequester;

	/** start Time */
	private Date startTime;

	/** end Time */
	private Date endTime;

	/** id Service */
	private String idService;

	/** status */
	private int[] status;

	/** processByRole */
	private String processByRole;

	/** Size Record 1 page **/
	private int pageSize;

	/** number page **/
	private int pageNumber;

	private int getSize;

	private int fromIndex;

	/** param for search Detail service **/

	/** ISSUES_SERVICE_ID */
	private String issuedServiceId;

	private String masterCode;

	public VT040000EntityRqFind() {
		super();
	}

	public VT040000EntityRqFind(String pattern, int serviceLocation, int unitId, String userNameOfRequester,
			String phoneOfRequester, Date startTime, Date endTime, String idService, int[] status, String processByRole,
			int pageSize, int pageNumber, int getSize, int fromIndex, String issuedServiceId, String masterCode) {
		super();
		this.pattern = pattern;
		this.serviceLocation = serviceLocation;
		this.unitId = unitId;
		this.userNameOfRequester = userNameOfRequester;
		this.phoneOfRequester = phoneOfRequester;
		this.startTime = startTime;
		this.endTime = endTime;
		this.idService = idService;
		this.status = status;
		this.processByRole = processByRole;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.getSize = getSize;
		this.fromIndex = fromIndex;
		this.issuedServiceId = issuedServiceId;
		this.masterCode = masterCode;
	}

	public String getMasterCode() {
		return masterCode;
	}

	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}

	public int getGetSize() {
		return getSize;
	}

	public void setGetSize(int getSize) {
		this.getSize = getSize;
	}

	public int getFromIndex() {
		return fromIndex;
	}

	public void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public int getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(int serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getUserNameOfRequester() {
		return userNameOfRequester;
	}

	public void setUserNameOfRequester(String userNameOfRequester) {
		this.userNameOfRequester = userNameOfRequester;
	}

	public String getPhoneOfRequester() {
		return phoneOfRequester;
	}

	public void setPhoneOfRequester(String phoneOfRequester) {
		this.phoneOfRequester = phoneOfRequester;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIdService() {
		return idService;
	}

	public void setIdService(String idService) {
		this.idService = idService;
	}

	public int[] getStatus() {
		return status;
	}

	public void setStatus(int[] status) {
		this.status = status;
	}

	public String getProcessByRole() {
		return processByRole;
	}

	public void setProcessByRole(String processByRole) {
		this.processByRole = processByRole;
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

	public String getIssuedServiceId() {
		return issuedServiceId;
	}

	public void setIssuedServiceId(String issuedServiceId) {
		this.issuedServiceId = issuedServiceId;
	}

}
