package com.viettel.vtnet360.vt01.vt010000.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityCard;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityApForOneRd;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityData;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityDataGetRecord;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqGetData;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqGetRecord;
import com.viettel.vtnet360.vt01.vt010000.entity.VT010000EntityRqUpdateRecord;

/**
 * Class interface VT010000DAO
 * 
 * @author KienHT 11/08/2018
 * 
 */
@Transactional
public interface VT010000DAO {

	/**
	 * find List InOut
	 * 
	 * @param requestParam VT010000EntityRqGetData
	 * @return List<VT010000EntityData>
	 */
	public List<VT010000EntityData> findListInOut(Map<String, Object> data);

	/**
	 * find In Out Get Record
	 * 
	 * @param requestParam VT010000EntityRqGetRecord
	 * @return VT010000EntityDataGetRecord
	 */
	public VT010000EntityDataGetRecord findInOutGetRecord(VT010000EntityRqGetRecord requestParam);

	/**
	 * inOut Manager Approve
	 * 
	 * @param requestParam VT010000EntityApForOneRd
	 */
	public void inOutManagerApprove(VT010000EntityApForOneRd Record);

	/**
	 * inOut GUARD Approve
	 * 
	 * @param requestParam VT010000EntityApForOneRd
	 */
	public void inOutGruadApprove(VT010000EntityApForOneRd Record);

	/**
	 * check Unit Id
	 * 
	 * @param requestParam VT010000EntityRqGetData
	 * @return int
	 */
	public int checkUnitId(VT010000EntityRqGetData requestParam);

	/**
	 * check Log To Roll Back Status by String inOutRegisterId
	 * 
	 * @param requestParam String
	 * @return int
	 */
	public int checkLogToRollBackStatus(String inOutRegisterId);

	/**
	 * checkSatusInOutRegisterId by String inOutRegisterId
	 * 
	 * @param requestParam String
	 * @return int
	 */
	public int checkSatusInOutRegisterId(String inOutRegisterId);

	/**
	 * checklogToRollBackStartTimeByPlan by String inOutRegisterId
	 * 
	 * @param requestParam String
	 * @return String
	 */
	public String findFullNameByUserName(String userName);

	/**
	 * findInformationEmployee
	 * 
	 * @param requestParam String
	 * @return VT010000EntityDataGetRecord
	 */
	public VT010000EntityDataGetRecord findInformation(String userName);

	/**
	 * update Record by inOutRegisterId
	 * 
	 * @param VT010000EntityRqUpdateRecord
	 */
	public void updateRecord(VT010000EntityRqUpdateRecord requestParam);

	/**
	 * update Record Out Date by Day
	 * 
	 * @param Date
	 */
	public void autoUpdateRecordOutDate(Date timeNow);
	
	/**
	 * update Record Out Date by Day Ver2
	 * 
	 * @param Date
	 */
	public void autoUpdateRecordOutDateVer2(Date timeNow);

	/**
	 * find OUT INOUT_RECORD ID by IDCARD
	 * 
	 * @param VT010000EntityCard
	 * @return VT010000EntityCard
	 */
	public VT010000EntityCard findOutIdCard(VT010000EntityCard requestParam);

	/**
	 * find IN INOUT_RECORD ID by IDCARD
	 * 
	 * @param VT010000EntityCard
	 * @return VT010000EntityCard
	 */
	public VT010000EntityCard finInIdCard(VT010000EntityCard requestParam);
	
	/**
	 * find CodeEmp By InoutRegister
	 * 
	 * @param String
	 * @return String
	 */
	public String findCodeEmpByInoutRegister(String inOutRegisterId);

}