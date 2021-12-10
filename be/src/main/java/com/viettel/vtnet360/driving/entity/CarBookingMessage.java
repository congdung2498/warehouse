package com.viettel.vtnet360.driving.entity;

import java.util.Date;

public class CarBookingMessage {
	private Long messageId;
	private String carBookingId;
	private String carBookingIdMessage;
	private String content;
	private Integer count;
	private String type;
	private Date sendTime;
	private Integer status;
	private String phone;
	private String user;
	private String note;
	private Date createTime;
	private String appoverQltt;
	private String appoverLddv;
	private String appoverCvp;
	private String employeeUserName;
	private String driveUser;
	private String driverSquadId;
	private Date start;

	public CarBookingMessage() {
		super();
	}

	public CarBookingMessage(String carBookingId, String content, String type, Date sendTime, String phone,
			String user) {
		super();
		this.carBookingId = carBookingId;
		this.content = content;
		this.type = type;
		this.sendTime = sendTime;
		this.phone = phone;
		this.user = user;
	}

	public CarBookingMessage(String carBookingId, String carBookingIdMessage, String appoverQltt, String appoverLddv, String appoverCvp,
			String employeeUserName, String driveUser, String driverSquadId, Date start) {
		super();
		this.carBookingIdMessage = carBookingIdMessage;
		this.carBookingId = carBookingId;
		this.appoverQltt = appoverQltt;
		this.appoverLddv = appoverLddv;
		this.appoverCvp = appoverCvp;
		this.employeeUserName = employeeUserName;
		this.driveUser = driveUser;
		this.driverSquadId = driverSquadId;
		this.start = start;
	}

	public String getCarBookingIdMessage() {
		return carBookingIdMessage;
	}

	public void setCarBookingIdMessage(String carBookingIdMessage) {
		this.carBookingIdMessage = carBookingIdMessage;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getCarBookingId() {
		return carBookingId;
	}

	public void setCarBookingId(String carBookingId) {
		this.carBookingId = carBookingId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getEmployeeUserName() {
		return employeeUserName;
	}

	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
	}

	public String getDriveUser() {
		return driveUser;
	}

	public void setDriveUser(String driveUser) {
		this.driveUser = driveUser;
	}

	public String getDriverSquadId() {
		return driverSquadId;
	}

	public void setDriverSquadId(String driverSquadId) {
		this.driverSquadId = driverSquadId;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

}
