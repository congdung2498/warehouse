package com.viettel.vtnet360.vt05.vt050000.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.stationery.request.dao.StationeryDAO;
import com.viettel.vtnet360.stationery.request.dao.StationeryReportDAO;
import com.viettel.vtnet360.stationery.service.StationeryService;
import com.viettel.vtnet360.stationery.service.StationeryStaff;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt05.vt050000.dao.VT050000DAO;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoOfEmployee;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoToFindListEmployeeOfUnit;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoToFindSpendingLimitUnit;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoToFindStationery;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoTofindMoneyUsed;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationery;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000RemainingSpendingLimit;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000StationeryInfo;

/**
 * @author DuyNK 04/10/2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT050000ServiceImpl implements VT050000Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050000DAO vt050000DAO;
	
	@Autowired
	private StationeryReportDAO stationeryReportDAO;
	
	@Autowired
  private StationeryDAO stationeryDAO;
	
	@Autowired
	private StationeryService stationeryService;


	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findStationeryByName(VT050000InfoToFindStationery info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT050000StationeryInfo> listStationery = null;
		try {
			info.setPageNumber(info.getPageNumber() * info.getPageSize());
			info.setStatus(Constant.STATUS_ACTIVE);
			info.setMasterClass(Constant.M_SYSTEM_CODE_CALCULATION);
			listStationery = vt050000DAO.findStationeryByName(info);
			resp.setData(listStationery);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findRemainingSpendingLimit(String userName, Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			double remainingSpendingLimit = 0;
			// get remaining spending limit of user loged on
			remainingSpendingLimit = caculRemainingSpendingLimit(userName, roleList);
			VT050000RemainingSpendingLimit response = new VT050000RemainingSpendingLimit(remainingSpendingLimit);
			// data send to client
			resp.setData(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
  @Transactional(readOnly = true)
  public double caculRemainingSpendingLimit(String userName, Collection<GrantedAuthority> roleList) {
    double moneyUsed = 0;
    double spendingLimit = 0;
    try {
      // find total money used not finish
      List<Integer> listStatusNotFinish = new ArrayList<>();
      listStatusNotFinish.add(Constant.STATIONERY_MANAGER_REJECT);
      listStatusNotFinish.add(Constant.STATIONERY_REJECT);
      listStatusNotFinish.add(Constant.STATIONERY_FINISH);
      listStatusNotFinish.add(Constant.STATIONERY_CANCEL);
      
      // check role if HCVP => find by unit that HCDV was configed
      int unitId =  stationeryReportDAO.getUnitIdByUser(userName);
      if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))) {
        int checkHcdvConfigInStaff = vt050000DAO.checkHcdvInStaffConfig(userName);
        if (checkHcdvConfigInStaff != Constant.NONE_EXIST_RECORD) {
          VT050000InfoToFindSpendingLimitUnit info = new VT050000InfoToFindSpendingLimitUnit();
          info.setJobCode(Constant.STATIONERY_HCDV_CODE);
          info.setmClass(Constant.STATIONERY_SPENDING_LIMIT_CLASS);
          info.setmCode(Constant.STATIONERY_SPENDING_LIMIT_CODE);
          info.setUnitId(unitId);
          info.setUserName(userName);
          spendingLimit = (double) stationeryService.getLimitSpendingHCDV(userName).getData();
        } else {
    		spendingLimit = (double) stationeryService.getLimitSpendingNV(userName).getData();
    	}
        if(Math.round(spendingLimit) == 0  ) return 0;
        
        StationeryStaff stationeryStaff = stationeryDAO.getStationeryStaffByUser(userName);
        List<Integer> placeIds = stationeryDAO.getPlaceIdByHCDV(userName);
        if(placeIds.size() > 0) {
          stationeryStaff.setPlaceIds(placeIds);
          moneyUsed = stationeryDAO.getHCDVLimit(stationeryStaff);
        }
      } else {
        // find spending limit of user not HCDV
        spendingLimit = (double) stationeryService.getLimitSpendingHCDV(userName).getData();
        if( Math.round(spendingLimit) == 0) return 0;

        List<Integer> listStatusFinish = new ArrayList<>();
        listStatusFinish.add(Constant.STATIONERY_FINISH);
        VT050000InfoTofindMoneyUsed infoTofindMoneyUsedFinish = new VT050000InfoTofindMoneyUsed(userName, listStatusFinish);
        moneyUsed = vt050000DAO.findMoneyUseInThisMonthFinish(infoTofindMoneyUsedFinish);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    // get remaining spending limit
    return spendingLimit - moneyUsed;
  }

	@Override
	@Transactional
	public void changeStatusIssuesStationery(String issuesStationeryID, String userName) {
		try {
			// list status reject
			List<Integer> listReject = new ArrayList<>();
			listReject.add(Constant.STATIONERY_MANAGER_REJECT);
			listReject.add(Constant.STATIONERY_REJECT);
			// list status processing
			List<Integer> listProcessing = new ArrayList<>();
			listProcessing.add(Constant.STATIONERY_CREATE);
			listProcessing.add(Constant.STATIONERY_WAIT_MANAGER_APPROVE);
			listProcessing.add(Constant.STATIONERY_MANAGER_APPROVE);
			listProcessing.add(Constant.STATIONERY_EXECUTING);

			// find all status of 1 request of user
			VT050000IssuesStationery param = new VT050000IssuesStationery();
			param.setUpdateUser(userName);
			param.setIssuesStationeryID(issuesStationeryID);
			List<Integer> listStatus = vt050000DAO.findIssuesStationeryStatus(issuesStationeryID);
			// status complete a part
			if (listStatus.contains(Constant.STATIONERY_FINISH)
					&& (listStatus.contains(Constant.STATIONERY_MANAGER_REJECT)
							|| listStatus.contains(Constant.STATIONERY_REJECT) ||  listStatus.contains(Constant.STATIONERY_REFUSE) )
					&& (!listStatus.contains(Constant.STATIONERY_CREATE)
							&& !listStatus.contains(Constant.STATIONERY_WAIT_MANAGER_APPROVE)
							&& !listStatus.contains(Constant.STATIONERY_MANAGER_APPROVE)
							&& !listStatus.contains(Constant.STATIONERY_EXECUTING)
							&& !listStatus.contains(Constant.STATIONERY_PAUSE)
							&& !listStatus.contains(Constant.STATIONERY_CANCEL)
							&& !listStatus.contains(Constant.STATIONERY_RECEIVE_REQUEST))) {
				param.setStatus(Constant.ISSUES_STATIONERY_COMPLETE_SECTION);
				vt050000DAO.updateIssuesStaioneryStatus(param);
				return;
			}
			// status complete
			if (listStatus.contains(Constant.STATIONERY_FINISH)
					&& (!listStatus.contains(Constant.STATIONERY_MANAGER_REJECT)
							&& !listStatus.contains(Constant.STATIONERY_REJECT)
							&& !listStatus.contains(Constant.STATIONERY_CREATE)
							&& !listStatus.contains(Constant.STATIONERY_WAIT_MANAGER_APPROVE)
							&& !listStatus.contains(Constant.STATIONERY_MANAGER_APPROVE)
							&& !listStatus.contains(Constant.STATIONERY_EXECUTING)
							&& !listStatus.contains(Constant.STATIONERY_PAUSE)
							&& !listStatus.contains(Constant.STATIONERY_CANCEL)
							&& !listStatus.contains(Constant.STATIONERY_RECEIVE_REQUEST))) {
				param.setStatus(Constant.ISSUES_STATIONERY_COMPLETE);
				vt050000DAO.updateIssuesStaioneryStatus(param);
				return;
			}
			// status reject
			if ((listStatus.contains(Constant.STATIONERY_MANAGER_REJECT))
					&& (!listStatus.contains(Constant.STATIONERY_FINISH)
							&& !listStatus.contains(Constant.STATIONERY_CREATE)
							&& !listStatus.contains(Constant.STATIONERY_WAIT_MANAGER_APPROVE)
							&& !listStatus.contains(Constant.STATIONERY_MANAGER_APPROVE)
							&& !listStatus.contains(Constant.STATIONERY_EXECUTING)
							&& !listStatus.contains(Constant.STATIONERY_PAUSE)
							&& !listStatus.contains(Constant.STATIONERY_CANCEL)
							&& !listStatus.contains(Constant.STATIONERY_RECEIVE_REQUEST))
					|| ((listStatus.contains(Constant.STATIONERY_MANAGER_REJECT))
							&& (listStatus.contains(Constant.STATIONERY_CHIEF_OF_STAFF_REJECT))
							&& (!listStatus.contains(Constant.STATIONERY_STAFF_REJECT_APPROVE))
							) || (!(listStatus.contains(Constant.STATIONERY_CHIEF_OF_STAFF_REJECT)) 
									&& (listStatus.contains(Constant.STATIONERY_MANAGER_REJECT)) 
									&& (listStatus.contains(Constant.STATIONERY_STAFF_REJECT_APPROVE)))
					|| (!(listStatus.contains(Constant.STATIONERY_MANAGER_REJECT)) 
							&& (listStatus.contains(Constant.STATIONERY_CHIEF_OF_STAFF_REJECT)) 
							&& (listStatus.contains(Constant.STATIONERY_STAFF_REJECT_APPROVE)))
					||((listStatus.contains(Constant.STATIONERY_MANAGER_REJECT)) 
							&& (listStatus.contains(Constant.STATIONERY_CHIEF_OF_STAFF_REJECT)) 
							&& (listStatus.contains(Constant.STATIONERY_STAFF_REJECT_APPROVE)))
					) {
				param.setStatus(Constant.ISSUES_STATIONERY_REJECT);
				vt050000DAO.updateIssuesStaioneryStatus(param);
				return;
			}
			// status cancel
			if (listStatus.contains(Constant.STATIONERY_CANCEL) 
			    && (!listStatus.contains(Constant.STATIONERY_FINISH)
					&& !listStatus.contains(Constant.STATIONERY_CREATE)
					&& !listStatus.contains(Constant.STATIONERY_WAIT_MANAGER_APPROVE)
					&& !listStatus.contains(Constant.STATIONERY_MANAGER_APPROVE)
					&& !listStatus.contains(Constant.STATIONERY_EXECUTING)
					&& !listStatus.contains(Constant.STATIONERY_PAUSE)
					&& !listStatus.contains(Constant.STATIONERY_MANAGER_REJECT)
					&& !listStatus.contains(Constant.STATIONERY_REJECT)
					&& !listStatus.contains(Constant.STATIONERY_RECEIVE_REQUEST))) {
				param.setStatus(Constant.ISSUES_STATIONERY_CANCEL);
				vt050000DAO.updateIssuesStaioneryStatus(param);
				return;
			}
			if (listStatus.contains(Constant.STATIONERY_REJECT) 
					&& (!listStatus.contains(Constant.STATIONERY_CREATE)
					&& !listStatus.contains(Constant.STATIONERY_WAIT_MANAGER_APPROVE)
					&& !listStatus.contains(Constant.STATIONERY_MANAGER_APPROVE)
					&& !listStatus.contains(Constant.STATIONERY_MANAGER_REJECT)
					&& !listStatus.contains(Constant.STATIONERY_RECEIVE_REQUEST)
					&& !listStatus.contains(Constant.STATIONERY_PAUSE)
					&& !listStatus.contains(Constant.STATIONERY_EXECUTING)
					&& !listStatus.contains(Constant.STATIONERY_FINISH)
					&& !listStatus.contains(Constant.STATIONERY_CANCEL)
					&& !listStatus.contains(Constant.STATIONERY_REFUSE))) {
				param.setStatus(Constant.ISSUES_STATIONERY_NOT_ACEEPT);
				vt050000DAO.updateIssuesStaioneryStatus(param);
				return;
			}
			
			if (listStatus.contains(Constant.STATIONERY_REFUSE) 
					&& (!listStatus.contains(Constant.STATIONERY_CREATE)
							&& !listStatus.contains(Constant.STATIONERY_WAIT_MANAGER_APPROVE)
							&& !listStatus.contains(Constant.STATIONERY_MANAGER_APPROVE)
							&& !listStatus.contains(Constant.STATIONERY_MANAGER_REJECT)
							&& !listStatus.contains(Constant.STATIONERY_RECEIVE_REQUEST)
							&& !listStatus.contains(Constant.STATIONERY_PAUSE)
							&& !listStatus.contains(Constant.STATIONERY_EXECUTING)
							&& !listStatus.contains(Constant.STATIONERY_FINISH)
							&& !listStatus.contains(Constant.STATIONERY_CANCEL)
							&& !listStatus.contains(Constant.STATIONERY_REJECT))) {
				param.setStatus(Constant.ISSUES_STATIONERY_REFUSE);
				vt050000DAO.updateIssuesStaioneryStatus(param);
				return;
			}
			
			// status reject
      if ((listStatus.contains(Constant.STATIONERY_MANAGER_REJECT) 
            || listStatus.contains(Constant.STATIONERY_CHIEF_OF_STAFF_REJECT) 
            || listStatus.contains(Constant.STATIONERY_STAFF_REJECT_APPROVE))
          && (!listStatus.contains(Constant.STATIONERY_FINISH)
              && !listStatus.contains(Constant.STATIONERY_CREATE)
              && !listStatus.contains(Constant.STATIONERY_WAIT_MANAGER_APPROVE)
              && !listStatus.contains(Constant.STATIONERY_MANAGER_APPROVE)
              && !listStatus.contains(Constant.STATIONERY_PAUSE)
              && !listStatus.contains(Constant.STATIONERY_CANCEL))) {
        param.setStatus(Constant.ISSUES_STATIONERY_REJECT);
        vt050000DAO.updateIssuesStaioneryStatus(param);
        return;
      }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListEmployeeOfUnit(VT050000InfoToFindListEmployeeOfUnit info, Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<VT050000InfoOfEmployee> listEmployee = null;
		try {
			//check user logged on is hcdv or admin
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV)) && !roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))) {
				info.setRoleAdmin(false);
			} else {
				info.setRoleAdmin(true);
			}
			info.setPageNumber(info.getPageNumber() * info.getPageSize());
			listEmployee = vt050000DAO.findListEmployeeOfUnit(info);
			resp.setData(listEmployee);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public void changeStatusIssuesStationeryPending(String issuesStationeryID, String userName) {
		try {
			// list status reject
			List<Integer> listReject = new ArrayList<>();
			listReject.add(Constant.STATIONERY_MANAGER_REJECT);
			listReject.add(Constant.STATIONERY_REJECT);
			// list status processing
			List<Integer> listProcessing = new ArrayList<>();
			listProcessing.add(Constant.STATIONERY_CREATE);
			listProcessing.add(Constant.STATIONERY_WAIT_MANAGER_APPROVE);
			listProcessing.add(Constant.STATIONERY_MANAGER_APPROVE);
			listProcessing.add(Constant.STATIONERY_EXECUTING);

			// find all status of 1 request of user
			VT050000IssuesStationery param = new VT050000IssuesStationery();
			param.setUpdateUser(userName);
			param.setIssuesStationeryID(issuesStationeryID);
			List<Integer> listStatus = vt050000DAO.findIssuesStationeryStatus(issuesStationeryID);
			if (listStatus.contains(Constant.STATIONERY_APPROVED_WAIT_MANAGER_APPROVE)
					|| (listStatus.contains(Constant.STATIONERY_WAIT_MANAGER_APPROVE)
							|| listStatus.contains(Constant.STATIONERY_MANAGER_APPROVE) || 
							listStatus.contains(Constant.STATIONERY_RECEIVE_REQUEST) 
						|| listStatus.contains(Constant.STATIONERY_PAUSE) 
						|| listStatus.contains(Constant.STATIONERY_EXECUTING) 
							)
					) {
				param.setStatus(Constant.ISSUES_STATIONERY_PROCESSING);
				vt050000DAO.updateIssuesStaioneryStatus(param);
			}
			//compltete
			if (listStatus.contains(Constant.STATIONERY_FINISH)) {
				param.setStatus(Constant.ISSUES_STATIONERY_COMPLETE);
				vt050000DAO.updateIssuesStaioneryStatus(param);
			}
			// status reject
			if ((listStatus.contains(Constant.STATIONERY_FINISH))
					&& (listStatus.contains(Constant.STATIONERY_MANAGER_REJECT)
							|| listStatus.contains(Constant.STATIONERY_REJECT) || listStatus.contains(Constant.STATIONERY_REFUSE))
							) {
				param.setStatus(Constant.ISSUES_STATIONERY_COMPLETE_SECTION);
				vt050000DAO.updateIssuesStaioneryStatus(param);
			}
			// status cancel
			if (listStatus.contains(Constant.STATIONERY_MANAGER_REJECT)) {
				param.setStatus(Constant.STATIONERY_EXECUTING);
				vt050000DAO.updateIssuesStaioneryStatus(param);
			}
			// status - 10
			if (listStatus.contains(Constant.STATIONERY_REJECT)) {
				param.setStatus(Constant.STATIONERY_EXECUTING);
				vt050000DAO.updateIssuesStaioneryStatus(param);
			}
			// status - 10
			if (listStatus.contains(Constant.STATIONERY_REFUSE)) {
				param.setStatus(Constant.STATIONERY_REJECT);
				vt050000DAO.updateIssuesStaioneryStatus(param);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		
	}
}
