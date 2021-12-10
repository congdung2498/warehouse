package com.viettel.vtnet360.driving.entity;

public class UpdateStatus {

	private int flagQltt ;
	private int flagLddv;
	private int flagCvp ;
	private int status  ;
	public int getFlagQltt() {
		return flagQltt;
	}
	public void setFlagQltt(int flagQltt) {
		this.flagQltt = flagQltt;
	}
	public int getFlagLddv() {
		return flagLddv;
	}
	public void setFlagLddv(int flagLddv) {
		this.flagLddv = flagLddv;
	}
	public int getFlagCvp() {
		return flagCvp;
	}
	public void setFlagCvp(int flagCvp) {
		this.flagCvp = flagCvp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public UpdateStatus(int flagQltt, int flagLddv, int flagCvp, int status) {
		super();
		this.flagQltt = flagQltt;
		this.flagLddv = flagLddv;
		this.flagCvp = flagCvp;
		this.status = status;
	}
	public UpdateStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
