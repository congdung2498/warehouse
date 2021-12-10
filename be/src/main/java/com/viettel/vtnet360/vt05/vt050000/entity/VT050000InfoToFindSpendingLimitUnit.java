package com.viettel.vtnet360.vt05.vt050000.entity;

/**
 * @author DuyNK
 *
 */
public class VT050000InfoToFindSpendingLimitUnit {

	/** M_SYSTEM_CODE.MASTER_CLASS to calcul limit of 1 unit */
	private String mClass;

	/** M_SYSTEM_CODE.CODE_VALUE to calcul limit of 1 unit */
	private String mCode;
	
	/** userName of HCDV logged on */
	private String userName;
	
	/** STATIONERY_STAFF.JOB_CODE */
	private String jobCode;
	
	private int unitId;
	
	public VT050000InfoToFindSpendingLimitUnit() {
	
	}

	public VT050000InfoToFindSpendingLimitUnit(String mClass, String mCode, String userName, String jobCode, int unitId) {
    super();
    this.mClass = mClass;
    this.mCode = mCode;
    this.userName = userName;
    this.jobCode = jobCode;
    this.unitId = unitId;
  }

	
  public int getUnitId() {
    return unitId;
  }

  public void setUnitId(int unitId) {
    this.unitId = unitId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
}
