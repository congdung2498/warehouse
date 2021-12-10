package com.viettel.vtnet360.stationery.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.vtnet360.stationery.entity.CanceIssuesStationey;
import com.viettel.vtnet360.stationery.entity.DataResponseRatting;
import com.viettel.vtnet360.stationery.entity.DetailsEmployeeInfo;
import com.viettel.vtnet360.stationery.entity.InfoEmployee;
import com.viettel.vtnet360.stationery.entity.InfoToUpdateStorageHcdvRequest;
import com.viettel.vtnet360.stationery.entity.IssuesStationeryItemsToInsert;
import com.viettel.vtnet360.stationery.entity.MSystemCodeEntity;
import com.viettel.vtnet360.stationery.entity.PlaceEmployee;
import com.viettel.vtnet360.stationery.entity.PlaceEmployeeParam;
import com.viettel.vtnet360.stationery.entity.ProcessIssuesStationery;
import com.viettel.vtnet360.stationery.entity.RequestParam;
import com.viettel.vtnet360.stationery.entity.RequestParamEdit;
import com.viettel.vtnet360.stationery.entity.ResultConfigName;
import com.viettel.vtnet360.stationery.entity.SearchData;
import com.viettel.vtnet360.stationery.entity.SearchRequestParamPagging;
import com.viettel.vtnet360.stationery.entity.StationeryEmployee;
import com.viettel.vtnet360.stationery.entity.StationeryEmployeeParam;
import com.viettel.vtnet360.stationery.entity.StationeryParam;
import com.viettel.vtnet360.stationery.entity.UpdateAllList;
import com.viettel.vtnet360.stationery.entity.UpdateIssuesStationery;
import com.viettel.vtnet360.stationery.entity.VoteHCDV;
import com.viettel.vtnet360.stationery.request.dao.StationeryDAO;
import com.viettel.vtnet360.stationery.request.dao.StationeryReportDAO;
import com.viettel.vtnet360.vt00.common.AdditionalInfoBase;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntitySmsVsNotify;
import com.viettel.vtnet360.vt00.vt000000.service.VT000000Service;
import com.viettel.vtnet360.vt03.Common.DateUtil;
import com.viettel.vtnet360.vt05.vt050000.dao.VT050000DAO;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000AdditionalInfoToSendNotify;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoToFindSpendingLimitUnit;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000InfoTofindMoneyUsed;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000IssuesStationery;
import com.viettel.vtnet360.vt05.vt050000.entity.VT050000StationeryInfo;
import com.viettel.vtnet360.vt05.vt050000.service.VT050000Service;
import com.viettel.vtnet360.vt05.vt050002.dao.VT050002DAO;
import com.viettel.vtnet360.vt05.vt050002.entity.Place;
import com.viettel.vtnet360.vt05.vt050003.dao.VT050003DAO;
import com.viettel.vtnet360.vt05.vt050003.entity.IssuesStationeryItems;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003InfoToCheckPlaceExist;
import com.viettel.vtnet360.vt05.vt050003.entity.VT050003InfoToFindHcdv;
import com.viettel.vtnet360.vt05.vt050005.dao.VT050005DAO;
import com.viettel.vtnet360.vt05.vt050006.entity.VT050006RequestParam;
import com.viettel.vtnet360.vt05.vt050011.dao.VT050011DAO;
import com.viettel.vtnet360.vt05.vt050012.dao.VT050012DAO;
import com.viettel.vtnet360.vt05.vt050013.entity.VT050013DataGetAll;
import com.viettel.vtnet360.vt05.vt050015.entity.ReportStationery;
import com.viettel.vtnet360.vt05.vt050018.dao.VT050018DAO;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018InfoOfEmployeeRequest;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018InfoToCheckConditionBeforeUpdateIssuesApprove;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018InfoToUpdateIssuesStaioneryApprove;
import com.viettel.vtnet360.vt05.vt050018.entity.VT050018InfoToUpdateIssuesStationeryItem;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class StationeryReportServiceImpl implements StationeryReportService {

	private final ArrayList<String> listStatus = new ArrayList<>();
	private final ArrayList<String> listStatusUnit = new ArrayList<>();
	private final ArrayList<String> listApproveStatus = new ArrayList<>();
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT050000Service vt050000Service;

	@Autowired
	private StationeryReportDAO stationeryReportDAO;

	@Autowired
	private Properties linkTemplateExcel;

	@Autowired
	private VT050005DAO vt050005DAO;
	
	@Autowired
	private VT050003DAO vt050003DAO;

	@Autowired
	private VT050012DAO vt050012DAO;

	@Autowired
	private VT050002DAO vt050002DAO;

	@Autowired
	private VT050000DAO vt050000DAO;

	@Autowired
	private Properties notifyMessage;

	@Autowired
	private Properties smsMessage;

	@Autowired
	private Notification notification;

	@Autowired
	private StationeryDAO stationeryDao;

	@Autowired
	private StationeryService stationeryService;

	@Autowired
	private VT000000Service vt000000Service;

	@Autowired
	private VT000000DAO vt000000DAO;

	@Autowired
	private VT050011DAO vt050011DAO;

	@Autowired
	private VT050018DAO vt050018DAO;
	
	@Autowired
	private StationeryDAO stationeryDAO;
	
	@Override
	public ResponseEntityBase getSpendingLimit(String userName) {
	  ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, 0);
	  double moneyUsed = 0.0;
	  double spendingLimit = 0.0;
	  try {
	    String roles = stationeryReportDAO.getRoleByUser(userName);
	    // find total money used not finish
	    List<Integer> listStatusNotFinish = new ArrayList<>();
	    listStatusNotFinish.add(Constant.STATIONERY_MANAGER_REJECT);
	    listStatusNotFinish.add(Constant.STATIONERY_REJECT);
	    listStatusNotFinish.add(Constant.STATIONERY_FINISH);
	    listStatusNotFinish.add(Constant.STATIONERY_CANCEL);

	    // check role if HCVP => find by unit that HCDV was configed
	    int unitId = stationeryReportDAO.getUnitIdByUser(userName);

	    if (roles.contains(Constant.PMQT_ROLE_STAFF_HC_DV)) {
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
	    	if(Math.round(spendingLimit) == 0) {
	    		resp.setStatus(Constant.UNIT_NOT_EXIST_QUOTA);
	    		return resp;
	    	}
	    	StationeryStaff stationeryStaff = stationeryDAO.getStationeryStaffByUser(userName);
        List<Integer> placeIds = stationeryDAO.getPlaceIdByHCDV(userName);
        if(placeIds.size() > 0) {
          stationeryStaff.setPlaceIds(placeIds);
          moneyUsed = stationeryDAO.getHCDVLimit(stationeryStaff);
        }
        
        if(stationeryStaff != null) {
          /*String[] usernames = stationeryDAO.getHCDVByUnitId(stationeryStaff.getUnitId());
          if(usernames != null && usernames.length > 0) {
            double createItemsTotal = stationeryDao.getHCDVRequest(usernames);
            moneyUsed = moneyUsed + createItemsTotal;
          }*/
        } else {
          List<Integer> listStatusFinish = new ArrayList<>();
          listStatusFinish.add(Constant.STATIONERY_FINISH);
          VT050000InfoTofindMoneyUsed infoTofindMoneyUsedFinish = new VT050000InfoTofindMoneyUsed(userName,
              listStatusFinish);
          moneyUsed = vt050000DAO.findMoneyUseInThisMonthFinish(infoTofindMoneyUsedFinish);
        }
	    } else {
	      // find spending limit of user not HCDV
	      spendingLimit = (double) stationeryService.getLimitSpendingNV(userName).getData();
	      if (Math.round(spendingLimit) == 0) {
	        resp.setStatus(Constant.UNIT_NOT_EXIST_QUOTA);
	        return resp;
	      }

	      List<Integer> listStatusFinish = new ArrayList<>();
	      listStatusFinish.add(Constant.STATIONERY_FINISH);
	      VT050000InfoTofindMoneyUsed infoTofindMoneyUsedFinish = new VT050000InfoTofindMoneyUsed(userName,
	          listStatusFinish);
	      moneyUsed = vt050000DAO.findMoneyUseInThisMonthFinish(infoTofindMoneyUsedFinish);
	    }
	  } catch (Exception e) {
	    logger.error(e.getMessage(), e);
	  }
	  // get remaining spending limit
	  resp.setData(spendingLimit - moneyUsed);
	  return resp;
	}

	@Override
	public ResponseEntityBase findMSystemCodeByCodeName(MSystemCodeEntity mSystemCodeEntity) throws Exception {

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			List<MSystemCodeEntity> SystemCodeEntity = stationeryReportDAO.findMSystemCodeByCodeName(mSystemCodeEntity);
			if (SystemCodeEntity != null) {
				reb.setData(SystemCodeEntity);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			}
			logger.info("**************** End get list places - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list places - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	public ResponseEntityBase searchStationeryByEmployee(ReportStationery rpStationery, String loginUsername,
			Collection<GrantedAuthority> listRole) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<StationeryEmployee> listReportStationery = null;
		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))
					&& !listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					&& !listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))) {
				Place condition = new Place();
				condition.setUsername(loginUsername);
				StationeryStaff stationeryStaff = stationeryDAO.getStationeryStaffByUser(loginUsername);
        
        if(stationeryStaff != null) {
          if (rpStationery.getPlaceId() == null) {
            List<Place> places = vt050002DAO.findPlaceByHCDV(condition);
            String[] placeIds = new String[places.size()];
            for (int i = 0; i < places.size(); i++) {
              Place place = places.get(i);
              placeIds[i] = place.getPlaceId();
            }
            rpStationery.setPlaceIds(placeIds);
          }
        } else {
          rpStationery.setUserName(loginUsername);
        }
			}
			listReportStationery = stationeryReportDAO.searchStationeryByEmployee(rpStationery);
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getDetailsIssuesStationeryItems(SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<IssuesStationeryItems> listReportStationery = null;
		try {
			listReportStationery = stationeryReportDAO.getDetailsIssuesStationeryItems(searchData);
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateApproveIssuesStationery(UpdateIssuesStationery updateIssuesStationery) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			int currentStatus = stationeryService.validStationeryReport(updateIssuesStationery.getIssuesStationeryApprovedId());
		      if(currentStatus == 1 || currentStatus == 2) {
		        resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
		        return resp;
		      }
			  Date date = new Date();
		      Calendar cal = Calendar.getInstance();
		      cal.setTime(date);
		      int day = cal.get(Calendar.DAY_OF_MONTH);
		      String limitDate = vt050012DAO.getLimitDateEnd();
		      int foo;
		      foo = Integer.parseInt(limitDate);
		      if (day > foo) {
		        resp.setStatus(Constant.DAY_IS_LIMIT);
		        System.out.println("Constant.DAY_IS_LIMIT");
		        return resp;
		      }
			
			stationeryReportDAO.updateLDApproved(updateIssuesStationery);
			stationeryReportDAO.updateIssuesStationeryItems(updateIssuesStationery);
			resp.setData(null);
			resp.setStatus(Constant.SUCCESS);
			sendNotiApproved(updateIssuesStationery.getIssuesStationeryApprovedId(),
					updateIssuesStationery.getUserName());
			Thread sendNotiAndSmsThread = new Thread() {
				@Override
				public void run() {
					try {
						vt000000Service.sendNotifyEachMinute();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			};
			sendNotiAndSmsThread.start();
//			try {
//				// send sms and notify to HCDV
//				sendSmsAndNotifyToHCDV(true, updateIssuesStationery.getUserName(), updateIssuesStationery.getL);
//				// send sms and notify to VPTCT
//				sendSmsAndNotifyToVPTCT(param.getApproveUserName(), param.getListRequestID());
//			} catch (Exception e) {
//				logger.error(e.getMessage(), e);
//				resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
//			}
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateRefuseIssuesStationery(UpdateIssuesStationery stationery) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			  int currentStatus = stationeryService.validStationeryReport(stationery.getIssuesStationeryApprovedId());
		      if(currentStatus == 1 || currentStatus == 2) {
		        resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
		        return resp;
		      }
		  ObjectMapper mapper = new ObjectMapper();
		  System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(stationery));
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			String limitDate = vt050012DAO.getLimitDateEnd();
			int foo;
			foo = Integer.parseInt(limitDate);
			if (day <= foo) {
				stationeryReportDAO.updateRefuseIssuesStationeryItems(stationery);
				stationeryReportDAO.updateRefuseLD(stationery);
				List<String> listIssuesStationeryID = vt050005DAO.findIssuesStationeryID(stationery.getIssuesStationeryApprovedId());
				for (int i = 0; i < listIssuesStationeryID.size(); i++) {
					String issuesStationeryID = listIssuesStationeryID.get(i);
					vt050000Service.changeStatusIssuesStationery(issuesStationeryID, stationery.getUserName());
				}
				sendNotiApprovedReject(stationery.getIssuesStationeryApprovedId(), stationery.getUserName(),
						stationery.getLdReasonReject());
			} else {
				resp.setStatus(Constant.WAIT_MANAGER_APPROVE_STILL);
			}

		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase countStationeryByEmployee(ReportStationery rpStationery, String loginUsername,
			Collection<GrantedAuthority> listRole) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))
					&& !listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					&& !listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))) {
				Place condition = new Place();
				condition.setUsername(loginUsername);
				
				StationeryStaff stationeryStaff = stationeryDAO.getStationeryStaffByUser(loginUsername);
				
				if(stationeryStaff != null) {
	        if (rpStationery.getPlaceId() == null) {
	          List<Place> places = vt050002DAO.findPlaceByHCDV(condition);
	          String[] placeIds = new String[places.size()];
	          for (int i = 0; i < places.size(); i++) {
	            Place place = places.get(i);
	            placeIds[i] = place.getPlaceId();
	          }
	          rpStationery.setPlaceIds(placeIds);
	        }
				} else {
				  rpStationery.setUserName(loginUsername);
				}
			}
			int count = stationeryReportDAO.countStationeryByEmployee(rpStationery);
			resp.setData(count);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getIssuesStationeryItemsById(SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<StationeryEmployee> listReportStationery = null;
		try {
			listReportStationery = stationeryReportDAO.getIssuesStationeryItemsById(searchData);
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase updateCancelIssuesStationery(CanceIssuesStationey canceIssuesStationey) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
		  int currentStatus = stationeryService.validStationery(canceIssuesStationey.getIssuesStationeryId());
		  if(currentStatus != 1) {
		    resp.setStatus(Constant.RESPONSE_STATUS_NO_ROLE);
		    return resp;
		  }
			stationeryReportDAO.cancelIssuesStationeryItems(canceIssuesStationey);
			stationeryReportDAO.cancelIssuesStationery(canceIssuesStationey);
			resp.setData(null);
			resp.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase editIssuesStationery(RequestParamEdit requestParam, String userName,
			Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  int currentStatus = stationeryService.validStationery(requestParam.getIssuesStationeryIdOld());
      if(currentStatus != 1) {
        resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
        return resp;
      }
			// check validate input
			if (requestParam.getListStationery() == null || requestParam.getListStationery().isEmpty()
					|| requestParam.getPlaceID() <= 0) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			// check spending limit
			// get total price request
			List<StationeryParam> listStationery = requestParam.getListStationery();
			List<String> listStationeryID = new ArrayList<>();
			for (int i = 0; i < listStationery.size(); i++) {
				listStationeryID.add(listStationery.get(i).getStationeryId());
			}
			List<VT050000StationeryInfo> listPrice = vt050003DAO.findStationeryPrice(listStationeryID);

			double totalPrice = 0;
			for (int i = 0; i < listStationery.size(); i++) {
				for (int j = 0; j < listPrice.size(); j++) {
					if (listStationery.get(i).getStationeryId().equals(listPrice.get(j).getStationeryID())) {
						totalPrice += listStationery.get(i).getQuantity() * listPrice.get(j).getUnitPrice();
					}
				}
			}
			// get remaining spending limit
			if (!roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))) {
			  // get remaining spending limit
			  double remainingSpendingLimit = vt050000Service.caculRemainingSpendingLimit(userName, roleList);
			  double savedTotal = stationeryDAO.calTotalByItems(requestParam);
			  remainingSpendingLimit = remainingSpendingLimit + savedTotal;
			  
			  System.out.println("remainingSpendingLimit " + remainingSpendingLimit + " totalPrice " + totalPrice);
			  // if total price request > Remaining spending limit => no insert
			  if (totalPrice > remainingSpendingLimit) {
			    resp.setStatus(Constant.STATIONERY_RESPONSE_SPENDING_LIMIT_EXCEED);
			    return resp;
			  }
			} else {
			  double remainingSpendingLimit = vt050000Service.caculRemainingSpendingLimit(userName, roleList);
			  double savedTotal = stationeryDAO.calTotalByItems(requestParam);
        remainingSpendingLimit = remainingSpendingLimit + savedTotal;
			  double moneyUsed = 0.0;
			  StationeryStaff stationeryStaff = stationeryDAO.getStationeryStaffByUser(userName);
			  if(stationeryStaff != null) {
			    String[] usernames = stationeryDAO.getHCDVByUnitId(stationeryStaff.getUnitId());
			    if(usernames != null && usernames.length > 0) {
			      double createItemsTotal = stationeryDao.getHCDVRequest(usernames);
			      moneyUsed = moneyUsed + createItemsTotal;
			    }
			  } else {
			    List<Integer> listStatusFinish = new ArrayList<>();
			    listStatusFinish.add(Constant.STATIONERY_FINISH);
			    VT050000InfoTofindMoneyUsed infoTofindMoneyUsedFinish = new VT050000InfoTofindMoneyUsed(userName,
			        listStatusFinish);
			    moneyUsed = vt050000DAO.findMoneyUseInThisMonthFinish(infoTofindMoneyUsedFinish);
			  }
			  remainingSpendingLimit = remainingSpendingLimit - moneyUsed;

			  // if total price request > Remaining spending limit => no insert
			  if (totalPrice > remainingSpendingLimit) {
			    resp.setStatus(Constant.STATIONERY_RESPONSE_SPENDING_LIMIT_EXCEED);
			    return resp;
			  }
			}

			// check validate placeID
			VT050003InfoToCheckPlaceExist info = new VT050003InfoToCheckPlaceExist(requestParam.getPlaceID(),
					Constant.STATUS_ACTIVE);
			int checkPlaceID = vt050003DAO.checkPlaceExist(info);
			if (checkPlaceID != Constant.SUCCESS) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			
			int countItemApprove = stationeryDao.getItemApproved(requestParam.getIssuesStationeryIdOld());
			if(countItemApprove > 0) {
			  requestParam.setUserName(userName);
	      stationeryReportDAO.approvedeleteIssuesStationeryItems(requestParam);
	      
	      for(StationeryParam item : requestParam.getListStationery()) {
	        item.setUserName(userName);
	        stationeryReportDAO.saveIssuesStationeryItem(item);
	      }
			} else {
			  stationeryReportDAO.deleteIssuesStationeryItems(requestParam);
			  IssuesStationeryItemsToInsert issuesStationeryItemsToInsert = new IssuesStationeryItemsToInsert();
	      issuesStationeryItemsToInsert.setIssuesStationeryID(requestParam.getIssuesStationeryIdOld());
	      issuesStationeryItemsToInsert.setStatus(Constant.STATIONERY_CREATE);
	      issuesStationeryItemsToInsert.setCreateUser(userName);
	      issuesStationeryItemsToInsert.setListStationery(requestParam.getListStationery());
	      stationeryReportDAO.insertIssuesStationeryItems(issuesStationeryItemsToInsert);
			}
			stationeryReportDAO.updateIssueStationery(requestParam);
			// set response to client
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	// xac nhan tiep nhan
	@Override
	public ResponseEntityBase confirmReceiptIssuesStationery(ProcessIssuesStationery approvingInfo) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			 int currentStatus = stationeryService.validStationeryReport(approvingInfo.getIssuesStationeryIdOld());
		      if(currentStatus == 3) {
		        resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
		        return resp;
		      }
			stationeryReportDAO.confirmReceiptIssuesApprove(approvingInfo);
			stationeryReportDAO.confirmReceiptIssuesItems(approvingInfo);
			Thread sendNotiAndSmsThread = new Thread() {
				@Override
				public void run() {
					try {
						List<ApprovingInfo> approvingInfos = stationeryDao
								.getApprovingInfo(approvingInfo.getIssuesStationeryApprovedId());
						ApprovingInfo firtsInfo = approvingInfos.get(0);

						String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
						String smsTitle = smsMessage.getProperty("TYPE_SMS_MODUL05");

						AdditionalInfoBase addInfo = new AdditionalInfoBase();
						addInfo.setId(approvingInfo.getIssuesStationeryApprovedId());
						addInfo.setRoleReceiver(Constant.PMQT_ROLE_STAFF_HC_DV);

						if (firtsInfo.getHcdvUsername() != null) {
							VT000000EntitySmsVsNotify userInfo = vt000000DAO.findNotifySmsByUserName(approvingInfo.getUserName());
							userInfo.setToUserName(approvingInfo.getUserName());
							String hcdvMessageNotify = MessageFormat.format(notifyMessage.getProperty("N45"),
									userInfo.getCreateUser(), approvingInfo.getIssuesStationeryApprovedId());
							VT000000EntitySmsVsNotify hcdvInfo = vt000000DAO.findNotifySmsByUserName(firtsInfo.getHcdvUsername());
							hcdvInfo.setToUserName(firtsInfo.getHcdvUsername());
							stationeryService.sendNotiAndSms(hcdvInfo, hcdvMessageNotify, title, smsTitle, addInfo,
									Constant.TYPE_NOTIFY_MODUL05);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			};
			sendNotiAndSmsThread.start();
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	// hoan thuc hien
	@Override
	public ResponseEntityBase postponedImplementationIssuesItems(ProcessIssuesStationery approvingInfo) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			int currentStatus = stationeryService.validStationeryReport(approvingInfo.getIssuesStationeryIdOld());
		      if(currentStatus == 5 || currentStatus == 6  ) {
		        resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
		        return resp;
		      }
			stationeryReportDAO.postponedImplementationApprove(approvingInfo);
			stationeryReportDAO.postponedImplementationIssuesItems(approvingInfo);
			stationeryReportDAO.postponedImplementationIssuesItemsHistory(approvingInfo);

			Thread sendNotiAndSmsThread = new Thread() {
				@Override
				public void run() {
					try {
						List<ApprovingInfo> approvingInfos = stationeryDao
								.getApprovingInfo(approvingInfo.getIssuesStationeryApprovedId());
						ApprovingInfo firtsInfo = approvingInfos.get(0);

						String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
						String smsTitle = smsMessage.getProperty("TYPE_SMS_MODUL05");

						AdditionalInfoBase addInfo = new AdditionalInfoBase();
						addInfo.setId(approvingInfo.getIssuesStationeryApprovedId());
						addInfo.setRoleReceiver(Constant.PMQT_ROLE_STAFF_HC_DV);

						if (firtsInfo.getHcdvUsername() != null) {
							String hcdvMessageNotify = MessageFormat.format(notifyMessage.getProperty("N42"),
									approvingInfo.getIssuesStationeryApprovedId(),
									new DateUtil(approvingInfo.getVptctPostponeToTime()).toString(DateUtil.FORMAT_DATE), approvingInfo.getVptctReason());
							VT000000EntitySmsVsNotify hcdvInfo = vt000000DAO
									.findNotifySmsByUserName(firtsInfo.getHcdvUsername());
							hcdvInfo.setToUserName(firtsInfo.getHcdvUsername());
							stationeryService.sendNotiAndSms(hcdvInfo, hcdvMessageNotify, title, smsTitle, addInfo,
									Constant.TYPE_NOTIFY_MODUL05);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			};
			sendNotiAndSmsThread.start();

		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	// khong thuc hien duoc
	@Override
	public ResponseEntityBase notPossibleApprove(ProcessIssuesStationery approvingInfo) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			int currentStatus = stationeryService.validStationeryReport(approvingInfo.getIssuesStationeryIdOld());
		      if(currentStatus == 5 || currentStatus == 6 || currentStatus == 4) {
		        resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
		        return resp;
		      }
			stationeryReportDAO.notPossibleApprove(approvingInfo);
			stationeryReportDAO.notPossibleIssuesStationeryItems(approvingInfo);
			List<String> listIssuesStationeryID = vt050011DAO.findListIssuesStationeryID(approvingInfo.getIssuesStationeryApprovedId());
			for (int i = 0; i < listIssuesStationeryID.size(); i++) {
				String issuesStationeryID = listIssuesStationeryID.get(i);
				vt050000Service.changeStatusIssuesStationery(issuesStationeryID, approvingInfo.getUserName());
			}
			Thread sendNotiAndSmsThread = new Thread() {
				@Override
				public void run() {
					try {
						List<ApprovingInfo> approvingInfos = stationeryDao
								.getApprovingInfo(approvingInfo.getIssuesStationeryApprovedId());
						ApprovingInfo firtsInfo = approvingInfos.get(0);

						String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
						String smsTitle = smsMessage.getProperty("TYPE_SMS_MODUL05");

						AdditionalInfoBase addInfo = new AdditionalInfoBase();
						addInfo.setId(approvingInfo.getIssuesStationeryApprovedId());
						addInfo.setRoleReceiver(Constant.PMQT_ROLE_STAFF_HC_DV);

						if (firtsInfo.getHcdvUsername() != null) {
							String hcdvMessageNotify = MessageFormat.format(notifyMessage.getProperty("N44"),
									approvingInfo.getIssuesStationeryApprovedId(), approvingInfo.getVptctReasonReject());
							VT000000EntitySmsVsNotify hcdvInfo = vt000000DAO
									.findNotifySmsByUserName(firtsInfo.getHcdvUsername());
							hcdvInfo.setToUserName(firtsInfo.getHcdvUsername());
							stationeryService.sendNotiAndSms(hcdvInfo, hcdvMessageNotify, title, smsTitle, addInfo, 52);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			};
			sendNotiAndSmsThread.start();
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	// hoan thanh
	@Override
	public ResponseEntityBase isCompleteIssusStationery(ProcessIssuesStationery approvingInfo) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {	
			int currentStatus = stationeryService.validStationeryReport(approvingInfo.getIssuesStationeryIdOld());
		      if(currentStatus == 5 || currentStatus == 6 ) {
		        resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
		        return resp;
		      }
//			VT050013DataGetAll dataResponseRequest = new VT050013DataGetAll();
//			dataResponseRequest = stationeryService.getApprovedInfoById(approvingInfo.getIssuesStationeryApprovedId());
//			for(int i =0 ; i< dataResponseRequest.getListRequest().size() ; i++){
//				for(int j =0 ; j<approvingInfo.getListIssuesStationeryItemId().size() ; j++){
//					if(approvingInfo.getListIssuesStationeryItemId().get(j).getQuantity() > dataResponseRequest.getListRequest().get(i).getQuantity()){
//							   resp.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
//						return resp;
//					}
//				}
//				
//			}
			approvingInfo.getListIssuesStationeryItemId();
			stationeryReportDAO.isCompleteIssusStationeryApprove(approvingInfo);
			stationeryReportDAO.isCompleteIssusStationeryIssuesStationeryItem(approvingInfo);
			InfoToUpdateStorageHcdvRequest info = new InfoToUpdateStorageHcdvRequest();
			info.setIssuesStationeryApproveID(approvingInfo.getIssuesStationeryApprovedId());
			info.setListStationery(approvingInfo.getListIssuesStationeryItemId());
			info.setVptctUserName(approvingInfo.getUserName());
			stationeryReportDAO.isCompleteStorageHcvpRequest(info);
//			for (int z = 0; z < approvingInfo.getListIssuesStationeryItemId().size(); z++) {
//				VT050004DataResponse dataResponse = approvingInfo.getListIssuesStationeryItemId().get(z);
//				System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataResponse));
//				dataResponse.setUserName(approvingInfo.getUserName());
//				dataResponse.setIssuesStationeryApprovedId(approvingInfo.getIssuesStationeryApprovedId());
//				stationeryReportDAO.isCompleteStorageHcvpRequest(dataResponse);
//			}

			Thread sendNotiAndSmsThread = new Thread() {
				@Override
				public void run() {
					try {
						List<ApprovingInfo> approvingInfos = stationeryDao
								.getApprovingInfo(approvingInfo.getIssuesStationeryApprovedId());
						ApprovingInfo firtsInfo = approvingInfos.get(0);

						String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
						String smsTitle = smsMessage.getProperty("TYPE_SMS_MODUL05");

						AdditionalInfoBase addInfo = new AdditionalInfoBase();
						addInfo.setId(approvingInfo.getIssuesStationeryApprovedId());
						addInfo.setRoleReceiver(Constant.PMQT_ROLE_STAFF_HC_DV);

						if (firtsInfo.getHcdvUsername() != null) {
							String hcdvMessageNotify = MessageFormat.format(notifyMessage.getProperty("N43"),
									approvingInfo.getIssuesStationeryApprovedId());
							VT000000EntitySmsVsNotify hcdvInfo = vt000000DAO
									.findNotifySmsByUserName(firtsInfo.getHcdvUsername());
							hcdvInfo.setToUserName(firtsInfo.getHcdvUsername());
							stationeryService.sendNotiAndSms(hcdvInfo, hcdvMessageNotify, title, smsTitle, addInfo, 51);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			};
			sendNotiAndSmsThread.start();
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	// xac nhan cap phat
	@Override
	public ResponseEntityBase confirmationPendingApprove(ProcessIssuesStationery processIssuesStationery) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
				
			int currentStatus = stationeryService.validStationeryReport(processIssuesStationery.getIssuesStationeryApprovedId());
		      if(currentStatus == 7 || currentStatus == 8) {
		        resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
		        return resp;
		      }
			
			VT050018InfoToCheckConditionBeforeUpdateIssuesApprove infoCheckCondition = new VT050018InfoToCheckConditionBeforeUpdateIssuesApprove();
			infoCheckCondition.setIssuesStationeryApproveID(processIssuesStationery.getIssuesStationeryApprovedId());
			infoCheckCondition.setStatus(Constant.STATIONERY_APPROVED_EXECUTING);
			int checkCondition = vt050018DAO.checkConditionBeforeUpdateIssuesApprove(infoCheckCondition);
			if (checkCondition == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.ERROR_REQUEST_INVALID);
				return resp;
			}
			
			VT050018InfoToUpdateIssuesStaioneryApprove infoToUpdateIssuesStationeryApprove = new VT050018InfoToUpdateIssuesStaioneryApprove();
			infoToUpdateIssuesStationeryApprove.setStatus(Constant.STATIONERY_APPROVED_FINISH);
			infoToUpdateIssuesStationeryApprove.setHcdvUserName(processIssuesStationery.getUserName());
			infoToUpdateIssuesStationeryApprove.setRequestID(processIssuesStationery.getIssuesStationeryApprovedId());
			infoToUpdateIssuesStationeryApprove.setStatusNow(Constant.STATIONERY_APPROVED_EXECUTING);
			vt050018DAO.updateIssuesStationeryApprove(infoToUpdateIssuesStationeryApprove);
				
			List<VT050018InfoOfEmployeeRequest> listInfo = vt050018DAO.findTotalRequestOfEmployee(processIssuesStationery.getIssuesStationeryApprovedId());
			VT050018InfoToUpdateIssuesStationeryItem infoToUpdateIssuesStationeryItem = new VT050018InfoToUpdateIssuesStationeryItem();
			infoToUpdateIssuesStationeryItem.setHcdvUserName(processIssuesStationery.getUserName());
			infoToUpdateIssuesStationeryItem.setListInfo(listInfo);
			infoToUpdateIssuesStationeryItem.setStatus(Constant.STATIONERY_FINISH);
			infoToUpdateIssuesStationeryItem.setStatusNow(Constant.STATIONERY_EXECUTING);
			vt050018DAO.updateIssuesStationeryItems(infoToUpdateIssuesStationeryItem);

			List<String> listIssuesStationeryID = vt050011DAO
					.findListIssuesStationeryID(processIssuesStationery.getIssuesStationeryApprovedId());
			for (int i = 0; i < listIssuesStationeryID.size(); i++) {
				String issuesStationeryID = listIssuesStationeryID.get(i);
				vt050000Service.changeStatusIssuesStationery(issuesStationeryID, processIssuesStationery.getUserName());
			}
			resp.setData(null);
			resp.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	// tu choi cap phat
	@Override
	public ResponseEntityBase refuseConfirmationPendingApprove(ProcessIssuesStationery processIssuesStationery) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			int currentStatus = stationeryService.validStationeryReport(processIssuesStationery.getIssuesStationeryApprovedId());
		      if(currentStatus == 7 || currentStatus == 8) {
		        resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
		        return resp;
		      }
			
			VT050018InfoToCheckConditionBeforeUpdateIssuesApprove infoCheckCondition = new VT050018InfoToCheckConditionBeforeUpdateIssuesApprove();
			infoCheckCondition.setIssuesStationeryApproveID(processIssuesStationery.getIssuesStationeryApprovedId());
			infoCheckCondition.setStatus(Constant.STATIONERY_APPROVED_EXECUTING);
			int checkCondition = vt050018DAO.checkConditionBeforeUpdateIssuesApprove(infoCheckCondition);
			if (checkCondition == Constant.NONE_EXIST_RECORD) {
				resp.setStatus(Constant.ERROR_REQUEST_INVALID);
				return resp;
			}
			VT050018InfoToUpdateIssuesStaioneryApprove infoToUpdateIssuesStationeryApprove = new VT050018InfoToUpdateIssuesStaioneryApprove();
			infoToUpdateIssuesStationeryApprove.setStatus(Constant.STATIONERY_APPROVED_COMPLETE);
			infoToUpdateIssuesStationeryApprove.setHcdvUserName(processIssuesStationery.getUserName());
			infoToUpdateIssuesStationeryApprove.setHcdvReasonReject(processIssuesStationery.getHcdvReasonReject());
			infoToUpdateIssuesStationeryApprove.setRequestID(processIssuesStationery.getIssuesStationeryApprovedId());
			vt050018DAO.refuseIssuesStationeryApprove(infoToUpdateIssuesStationeryApprove);
			
			List<VT050018InfoOfEmployeeRequest> listInfo = vt050018DAO.findTotalRequestOfEmployee(processIssuesStationery.getIssuesStationeryApprovedId());
			
			// update ISSUES_STATIONERY_ITEMS
						VT050018InfoToUpdateIssuesStationeryItem infoToUpdateIssuesStationeryItem = new VT050018InfoToUpdateIssuesStationeryItem();
						infoToUpdateIssuesStationeryItem.setHcdvUserName(processIssuesStationery.getUserName());
						infoToUpdateIssuesStationeryItem.setListInfo(listInfo);
						infoToUpdateIssuesStationeryItem.setStatus(Constant.STATIONERY_REFUSE);
						vt050018DAO.refuseIssuesStationeryItems(infoToUpdateIssuesStationeryItem);
						// update status in ISSUES_STATIONERY
			List<String> listIssuesStationeryID = vt050011DAO.findListIssuesStationeryID(processIssuesStationery.getIssuesStationeryApprovedId());
			for (int i = 0; i < listIssuesStationeryID.size(); i++) {
				String issuesStationeryID = listIssuesStationeryID.get(i);
				vt050000Service.changeStatusIssuesStationery(issuesStationeryID, processIssuesStationery.getUserName());
			}
			resp.setData(null);
			resp.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findDataToRatting(SearchRequestParamPagging param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		DataResponseRatting dataResponse = new DataResponseRatting();
		try {
			VT050013DataGetAll dataResponseRequest = new VT050013DataGetAll();
			// get info of HCDV request
			dataResponseRequest = stationeryService.getApprovedInfoById(param.getRequestID());
			dataResponse.setFullNameHcdv(dataResponseRequest.getHcdvUserName());
			dataResponse.setFullNameTct(dataResponseRequest.getTptctUserName());
			dataResponse.setDateRequest(dataResponseRequest.getCreateDate());
			dataResponse.setMessage(dataResponseRequest.getHcdvMessage());
			dataResponse.setStatus(dataResponseRequest.getStatus());
			dataResponse.setHcdvReasonReject(dataResponseRequest.getHcdvReasonReject());
			dataResponse.setTotalFulfill(dataResponseRequest.getCountTotalFullfill());
			dataResponse.setTotalRequest(dataResponseRequest.getCountTotalRequest());
			dataResponse.setSumTotalMoney(dataResponseRequest.getCountTotalMoneyRequest());
			dataResponse.setSumTotalMoneyHcdv(dataResponseRequest.getCountTotalMoneyFullfill());
			dataResponse.setFeedBackToHcdv(dataResponseRequest.getFeedbackToHcdv());
			dataResponse.setFeedBackToVptct(dataResponseRequest.getFeedbackToVptct());
			dataResponse.setRatingToUser(dataResponseRequest.getRatingToUser());
			dataResponse.setRatingToVptct(dataResponseRequest.getRatingToVptct());
			dataResponse.setVptctReason(dataResponseRequest.getVptctReason());
			dataResponse.setVptctPostponeToTime(dataResponseRequest.getVptctPostponeToTime());
			dataResponse.setVptctReasonReject(dataResponseRequest.getVptctReasonReject());
			dataResponse.setUnitName(dataResponseRequest.getUnitName());
			dataResponse.setPhoneNumber(dataResponseRequest.getPhoneNumber());
			dataResponse.setLdReasonReject(dataResponseRequest.getLdReasonReject());
//			dataResponse = stationeryReportDAO.findInfoRequestHcdv(param.getRequestID());
			// get detail of request
			dataResponse.setListData(dataResponseRequest.getListRequest());
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findDataToRattingDetails(SearchRequestParamPagging param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		DataResponseRatting dataResponse = new DataResponseRatting();
		try {
			VT050013DataGetAll dataResponseRequest = new VT050013DataGetAll();
			// get info of HCDV request
			dataResponseRequest = stationeryService.getApprovedInfoByIdDetails(param.getRequestID());
			dataResponse.setFullNameHcdv(dataResponseRequest.getHcdvUserName());
			dataResponse.setFullNameTct(dataResponseRequest.getTptctUserName());
			dataResponse.setDateRequest(dataResponseRequest.getCreateDate());
			dataResponse.setMessage(dataResponseRequest.getHcdvMessage());
			dataResponse.setStatus(dataResponseRequest.getStatus());
			dataResponse.setHcdvReasonReject(dataResponseRequest.getHcdvReasonReject());
			dataResponse.setTotalFulfill(dataResponseRequest.getCountTotalFullfill());
			dataResponse.setTotalRequest(dataResponseRequest.getCountTotalRequest());
			dataResponse.setSumTotalMoney(dataResponseRequest.getCountTotalMoneyRequest());
			dataResponse.setSumTotalMoneyHcdv(dataResponseRequest.getCountTotalMoneyFullfill());
			dataResponse.setFeedBackToHcdv(dataResponseRequest.getFeedbackToHcdv());
			dataResponse.setFeedBackToVptct(dataResponseRequest.getFeedbackToVptct());
			dataResponse.setRatingToUser(dataResponseRequest.getRatingToUser());
			dataResponse.setRatingToVptct(dataResponseRequest.getRatingToVptct());
			dataResponse.setVptctReason(dataResponseRequest.getVptctReason());
			dataResponse.setVptctPostponeToTime(dataResponseRequest.getVptctPostponeToTime());
			dataResponse.setVptctReasonReject(dataResponseRequest.getVptctReasonReject());
			dataResponse.setUnitName(dataResponseRequest.getUnitName());
			dataResponse.setPhoneNumber(dataResponseRequest.getPhoneNumber());
			dataResponse.setLdReasonReject(dataResponseRequest.getLdReasonReject());
//			dataResponse = stationeryReportDAO.findInfoRequestHcdv(param.getRequestID());
			// get detail of request
			dataResponse.setListData(dataResponseRequest.getListRequest());
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}
	
	
	@Override
	public ResponseEntityBase findDataToStationeryEmployee(StationeryEmployeeParam param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		InfoEmployee dataResponse = null;
		try {
			// get info of HCDV request
			dataResponse = stationeryReportDAO.findInfoRequestEmployee(param.getRequestID());
			// get detail of request
			List<DetailsEmployeeInfo> listData = stationeryReportDAO.findDetailsRequestEmployee(param.getRequestID());
			dataResponse.setListData(listData);
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;

	}

	@Override
	public ResponseEntityBase searchPlaceEmployeeById(PlaceEmployeeParam param) {
		// TODO Auto-generated method stub

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		PlaceEmployee dataResponse = null;
		try {
			// get info of HCDV request
			dataResponse = stationeryReportDAO.searchPlaceEmployeeById(param.getPlaceId());
			// get detail of request
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase voteHcdv(VoteHCDV voteHCDV) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			Integer currentStatus = stationeryService.checkVoteHCDVValidStationeryReport(voteHCDV.getIssuesStationeryApprovedId());
		      if(  currentStatus != 0) {
		        resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
		        return resp;
		      }
			// get info of HCDV request
			int dataResponse = stationeryReportDAO.voteHcdv(voteHCDV);
			// get detail of request
			resp.setData(dataResponse);
			resp.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase voteVptct(VoteHCDV voteHCDV) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			Integer currentStatus = stationeryService.checkVoteVPTCTValidStationeryReport(voteHCDV.getIssuesStationeryApprovedId());
		      if(currentStatus != 0) {
		        resp.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);
		        return resp;
		      }
			// get info of HCDV request
			int dataResponse = stationeryReportDAO.voteVptct(voteHCDV);
			// get detail of request
			resp.setData(dataResponse);
			resp.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase countInfoRequestHcdvDetails(VT050006RequestParam param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// get info of HCDV request
			int dataResponse = stationeryReportDAO.countInfoRequestHcdvDetails(param.getRequestID());
			// get detail of request
			resp.setData(dataResponse);
			resp.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase requestByUserName(StationeryParam param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			double dataResponse = stationeryReportDAO.requestByUserName(param.getStationeryId());
			resp.setData(dataResponse);
			resp.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	// @Override
	// public ResponseEntityBase getQuotaLimit(StationeryParam param) {
	// ResponseEntityBase resp = new
	// ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
	// try {
	// VT050012DataResponseQuota dataResponse =
	// stationeryReportDAO.getQuotaLimit(param.getQuantity());
	// resp.setData(dataResponse);
	// resp.setStatus(Constant.SUCCESS);
	// } catch (Exception e) {
	// logger.error(e.getMessage(), e);
	// throw (e);
	// }
	// return resp;
	// }

	@Override
	public ResponseEntityBase requestByUnitId(StationeryParam param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			double dataResponse = stationeryReportDAO.requestByUnitId(param.getQuantity());
			resp.setData(dataResponse);
			resp.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findSpendingLimit(int unitId) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			double dataResponse = stationeryReportDAO.findSpendingLimit(unitId);
			resp.setData(dataResponse);
			resp.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public File createExcel(ReportStationery condition, String username) throws Exception {
		File file = null;
		List<StationeryEmployee> reportStationery = new ArrayList<>();

		String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_PATH_EMPLOYEE");
		try {
			reportStationery = stationeryReportDAO.searchStationeryByEmployee(condition);
			condition.setUserName(username);
			file = writeExcelEmpoyee(reportStationery, excelFilePath, condition);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return file;
	}

	public File writeExcelEmpoyee(List<StationeryEmployee> reportStationery, String excelFilePath,
			ReportStationery condition) throws IOException {

		File outFile = null;
		File file = null;

		Date date = new Date();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		String fileName = condition.getUserName() + "_" + cal.get(Calendar.YEAR) + "_" + (cal.get(Calendar.MONTH) + 1)
				+ "_" + cal.get(Calendar.DAY_OF_MONTH) + "_" + cal.get(Calendar.HOUR_OF_DAY) + "h"
				+ cal.get(Calendar.MINUTE) + "m" + cal.get(Calendar.SECOND) + "s" + cal.get(Calendar.MILLISECOND)
				+ "ms.xlsx";
		try {
			Resource resource = new ClassPathResource(excelFilePath);
			file = resource.getFile();
			String filePath = file.getAbsolutePath();
			outFile = new File(filePath.split("templateExcel")[0] + "saveExcel\\VT05\\" + fileName);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		try (FileInputStream inputStream = new FileInputStream(file)) {
			try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(inputStream), 100)) {
				SXSSFSheet sheet = workbook.getSheetAt(0);

				int rowCount = 8;
				CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
				CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
				cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
				cellStyle.setFillPattern(FillPatternType.NO_FILL);
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

				// style for date
				CellStyle combined = sheet.getWorkbook().createCellStyle();
				combined.cloneStyleFrom(cellStyle);
				combined.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));

				// style for note and feedback
				CellStyle combinedNote = sheet.getWorkbook().createCellStyle();
				combinedNote.cloneStyleFrom(cellStyle);
				combinedNote.setWrapText(true);
				int rowNumber = 1;
				// fill data
				for (StationeryEmployee item : reportStationery) {
					writeBookEmployee(item, sheet, ++rowCount, cellStyle, combined, combinedNote, rowNumber++);

				}

				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		}

		return outFile;
	}

	private void writeBookEmployee(StationeryEmployee reportStationery, Sheet sheet, int rowNum, CellStyle cellStyle,
			CellStyle combined, CellStyle combinedNote, int rowNumber) {
		getListStatus();
		getListStatusUnit();
		Row row = sheet.createRow(rowNum);

		// No.
		Cell cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(rowNumber);

		// vị trí
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getStartPlace());

		// đơn vị
		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getUnitName());

		// mã Yêu câuf
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getIssuesStationeryId());

		// ngày đăng kí
		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getRequestDate());

		// ma yc trinh ki
		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getIssuesStationeryItemId());

		// ten nhan vien
		cell = row.createCell(7);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getFullName());

		// so dien thoai
		cell = row.createCell(8);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getPhoneNumber());

		// yeu cau
		cell = row.createCell(10);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getTotalRequest());
		// dap ung
		cell = row.createCell(11);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getTotalFulfill());

		// status VPP
		cell = row.createCell(12);
		cell.setCellStyle(cellStyle);
		String status = listStatus.get(reportStationery.getStatusItem());
		cell.setCellValue(status);
		// status DV

		cell = row.createCell(13);
		cell.setCellStyle(cellStyle);
		String status2 = listStatusUnit.get(reportStationery.getStatus());
		cell.setCellValue(status2);

		// ten VPP
		cell = row.createCell(14);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getStationeryName());

		// don vi tinh
		cell = row.createCell(15);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getUnitPrice());

		// ngay thuc hien
		cell = row.createCell(16);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getUpdateDate());

		// nguoi thuc hien
		cell = row.createCell(17);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getUpdateUser());

		// ghi chu
		cell = row.createCell(18);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getNote());

	}

	public void getListStatus() {
		listStatus.add("Khởi tạo");
		listStatus.add("Chờ LĐ phê duyệt");
		listStatus.add("Lãnh đạo phê duyệt");
		listStatus.add("Lãnh đạo từ chối");
		listStatus.add("VPTCT tiếp nhận");
		listStatus.add("VPTCT hoãn thục hiện");
		listStatus.add("VPTCT Không thực hiện được");
		listStatus.add("VPTCT cấp phát cho HCDV");
		listStatus.add("HCDV xác nhận nhận VPP");
		listStatus.add("Hủy");
	}

	public void getListStatusUnit() {
	  listStatusUnit.add("Khởi tạo");
		listStatusUnit.add("Đang xử lý");
		listStatusUnit.add("Hoàn thành một phần");
		listStatusUnit.add("Hoàn thành");
		listStatusUnit.add("Từ chối");
		listStatusUnit.add("Hủy yêu cầu");
		listStatusUnit.add("Từ chối xác nhận");
		listStatusUnit.add("Không thực hiện được");
	}
	
	public void getListApproveStatus() {
	  listApproveStatus.add("Chờ LĐ phê duyệt");
	  listApproveStatus.add("Lãnh đạo phê duyệt");
	  listApproveStatus.add("Lãnh đạo từ chối");
	  listApproveStatus.add("VPTCT tiếp nhận");
	  listApproveStatus.add("VPTCT Hoãn thực hiện"); 
	  listApproveStatus.add("VPTCT Không thực hiện được"); 
	  listApproveStatus.add("VPTCT cấp phát cho HCĐV");
	  listApproveStatus.add("HCĐV xác nhận VPP");
	  listApproveStatus.add("Từ chối xác nhận");
	}

	@Override
	public ResponseEntityBase updateApproveIssuesStationeryAllList(UpdateAllList updateIssuesStationery) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			stationeryReportDAO.updateLDApprovedList(updateIssuesStationery);
			stationeryReportDAO.updateIssuesStationeryItemsList(updateIssuesStationery);

			for (String approvedId : updateIssuesStationery.getUpdateAllIssuesStationery()) {
				sendNotiApproved(approvedId, updateIssuesStationery.getUserName());
			}

			Thread sendNotiAndSmsThread = new Thread() {
				@Override
				public void run() {
					try {
						vt000000Service.sendNotifyEachMinute();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			};
			sendNotiAndSmsThread.start();
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	@Override
	public File createExcelEmployee(ReportStationery rpStationery, String loginUserName, Collection<GrantedAuthority> listRole) throws Exception {
	  File file = null;
	  List<StationeryEmployee> reportStationery = new ArrayList<>();

	  String excelFilePath = linkTemplateExcel.getProperty("EXCEL_FILE_EXPORT_EMPLOYEE");
	  try {
	    if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))
	        && !listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
	        && !listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))) {
	      Place condition = new Place();
	      condition.setUsername(loginUserName);
	      List<Place> places = vt050002DAO.findPlaceByHCDV(condition);
	      if (places.size() > 0) {
	        if (rpStationery.getPlaceId() == null) {
	          String[] placeIds = new String[places.size()];
	          for (int i = 0; i < places.size(); i++) {
	            Place place = places.get(i);
	            placeIds[i] = place.getPlaceId();
	          }
	          rpStationery.setPlaceIds(placeIds);
	        }
	      }
	    }
	    reportStationery = stationeryReportDAO.searchStationeryByEmployee(rpStationery);
	    rpStationery.setUserName(loginUserName);
	    file = writeReportExcelEmpoyee(reportStationery, excelFilePath, rpStationery);
	  } catch (Exception e) {
	    logger.error(e.getMessage(), e);
	  }

	  return file;
	}

	public File writeReportExcelEmpoyee(List<StationeryEmployee> reportStationery, String excelFilePath,
			ReportStationery condition) throws Exception {

		File outFile = null;
		File file = null;

		Date date = new Date();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		String fileName = condition.getUserName() + "_" + cal.get(Calendar.YEAR) + "_" + (cal.get(Calendar.MONTH) + 1)
				+ "_" + cal.get(Calendar.DAY_OF_MONTH) + "_" + cal.get(Calendar.HOUR_OF_DAY) + "h"
				+ cal.get(Calendar.MINUTE) + "m" + cal.get(Calendar.SECOND) + "s" + cal.get(Calendar.MILLISECOND)
				+ "ms.xlsx";
		try {
			Resource resource = new ClassPathResource(excelFilePath);
			file = resource.getFile();
			String filePath = file.getAbsolutePath();
			outFile = new File(filePath.split("templateExcel")[0] + "saveExcel\\VT05\\" + fileName);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		try (FileInputStream inputStream = new FileInputStream(file)) {
			try (SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(inputStream), 100)) {
				SXSSFSheet sheet = workbook.getSheetAt(0);

				int rowCount = 3;
				CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
				CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
				cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
				cellStyle.setFillPattern(FillPatternType.NO_FILL);
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

				// style for date
				CellStyle combined = sheet.getWorkbook().createCellStyle();
				combined.cloneStyleFrom(cellStyle);
				combined.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));

				// style for note and feedback
				CellStyle combinedNote = sheet.getWorkbook().createCellStyle();
				combinedNote.cloneStyleFrom(cellStyle);
				combinedNote.setWrapText(true);
				int rowNumber = 1;
				// fill data
				for (StationeryEmployee item : reportStationery) {
					writeBookReportEmployee(item, sheet, ++rowCount, cellStyle, combined, combinedNote, rowNumber++);

				}

				try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
					workbook.write(outputStream);
				}
			}
		}

		return outFile;
	}

	private void writeBookReportEmployee(StationeryEmployee reportStationery, Sheet sheet, int rowNum,
			CellStyle cellStyle, CellStyle combined, CellStyle combinedNote, int rowNumber) throws Exception {
		getListStatus();
		getListStatusUnit();
		getListApproveStatus();
		Row row = sheet.createRow(rowNum);

		// No.
		Cell cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(rowNumber);

		// vị trí
		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getStartPlace());

		// đơn vị
		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getUnitName());

		// mã Yêu câuf
		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getIssuesStationeryId());

		// ngày đăng kí
		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new DateUtil(reportStationery.getRequestDate()).toString(DateUtil.FORMAT_DATE));

		// ma yc trinh ki
		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		if(reportStationery.getIssuesStationeryApproveId()!= null){
			cell.setCellValue(reportStationery.getIssuesStationeryApproveId());
		}else{
			cell.setCellValue("");
		}
		
		// ngày đăng kí
		cell = row.createCell(6);
		cell.setCellStyle(cellStyle);
		if(reportStationery.getApproveCreateDate() != null){
		cell.setCellValue(new DateUtil(reportStationery.getApproveCreateDate()).toString(DateUtil.FORMAT_DATE));
		}else{
			cell.setCellValue("");
		}
		// ten nhan vien
		cell = row.createCell(7);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getFullName());

		// so dien thoai
		cell = row.createCell(8);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getPhoneNumber());

		// so dien thoai
		cell = row.createCell(9);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getHcdvFullname());

		// yeu cau
		cell = row.createCell(10);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getTotalRequest());
		// status VPP
		cell = row.createCell(11);
		cell.setCellStyle(cellStyle);
		String status = listStatusUnit.get(reportStationery.getStatus());
		cell.setCellValue(status);
		// status DV

		cell = row.createCell(12);
		cell.setCellStyle(cellStyle);
		if(reportStationery.getStatusUnit() != null){
		String status2 = listApproveStatus.get(Integer.parseInt(reportStationery.getStatusUnit()));
		cell.setCellValue(status2);
		}
		// ten VPP
		cell = row.createCell(13);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getStationeryName());

		// don vi tinh
		cell = row.createCell(14);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getCalUnit());

		// ngay thuc hien
		cell = row.createCell(15);
		cell.setCellStyle(cellStyle);
		if(reportStationery.getApproveCreateDate() != null) {
		  cell.setCellValue(new DateUtil(reportStationery.getApproveCreateDate()).toString(DateUtil.FORMAT_DATE));
		}

		// nguoi thuc hien
		cell = row.createCell(16);
		cell.setCellStyle(cellStyle);
		if(reportStationery.getApproveStatus() == 4 || reportStationery.getApproveStatus() == 6 || 
		      reportStationery.getApproveStatus() == 7 || reportStationery.getApproveStatus() == 5 || 
		      reportStationery.getApproveStatus() == 3 || reportStationery.getApproveStatus() == 8) {
		  cell.setCellValue(reportStationery.getVptctFullName());
		}

		// ghi chu
		cell = row.createCell(17);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(reportStationery.getNote());

	}

	@Override
	public ResponseEntityBase getMasterClass() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<ResultConfigName> listReportStationery = null;
		try {
			listReportStationery = stationeryReportDAO.getMasterClass();
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getMasterClassWeb() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<ResultConfigName> listReportStationery = null;
		try {
			listReportStationery = stationeryReportDAO.getMasterClassWeb();
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getMasterClassByCode(ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<ResultConfigName> listReportStationery = null;
		try {
			listReportStationery = stationeryReportDAO.getMasterClassByCode(resultConfigName);
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase editTypeParam(ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// get info of HCDV request
		  int checkExist = stationeryReportDAO.checkExistMasterClass(resultConfigName.getMasterCode());
		  if(checkExist > 0) {
		    int dataResponse = stationeryReportDAO.editTypeParam(resultConfigName);
		    resp.setData(dataResponse);
		  } else {
		    stationeryReportDAO.insertMasterClass(resultConfigName);
		  }
			// get detail of request
			resp.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getParamWebConfigByCode(ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<ResultConfigName> listReportStationery = null;
		try {
			listReportStationery = stationeryReportDAO.getParamWebConfigByCode(resultConfigName);
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase insertMasterCodeWeb(ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			int i = stationeryReportDAO.checkExitsInParamweb(resultConfigName);
			if(i == Constant.NONE_EXIST_RECORD){
			// get info of HCDV request
			stationeryReportDAO.insertMasterCodeWeb(resultConfigName);
			resp.setStatus(Constant.SUCCESS);
			} else {
				resp.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
			}
			// get detail of request
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase editWebParam(ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			int i = stationeryReportDAO.checkExitsInParamweb(resultConfigName);
			if(i == Constant.NONE_EXIST_RECORD){
			// get info of HCDV request
			stationeryReportDAO.editWebParam(resultConfigName);
			// get detail of request
			resp.setStatus(Constant.SUCCESS);
			
		}else {
			resp.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);
		}
			} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getCodeValueByMasterClass(ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<ResultConfigName> listReportStationery = null;
		try {
			listReportStationery = stationeryReportDAO.getCodeValueByMasterClass(resultConfigName);
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findSpendingLimitString(String unitId) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			double dataResponse = stationeryReportDAO.findSpendingLimitString(unitId);
			resp.setData(dataResponse);
			resp.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;

	}

	@Override
	@Transactional
	public double findSpendingLimitQuota(String username, Collection<GrantedAuthority> listRole) {
		double spendingLimit = 0;
		try {
			int unitId = stationeryReportDAO.getUnitIdByUser(username);
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HCVP_VPP))) {
				// find quota by unitd id have config
				double dataResponse = stationeryReportDAO.requestQuotaByUnitId(unitId);
				double requestByUnit = stationeryReportDAO.requestByUserName(username);
				spendingLimit = dataResponse - requestByUnit;
			} else {
				double dataResponse = stationeryReportDAO.findSpendingLimit(unitId);
				double requestByUnit = stationeryReportDAO.requestByUserName(username);
				spendingLimit = dataResponse - requestByUnit;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return spendingLimit;
	}

	@Override
	public ResponseEntityBase getParamProcessConfigByCode(ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<ResultConfigName> listReportStationery = null;
		try {
			listReportStationery = stationeryReportDAO.getParamProcessConfigByCode(resultConfigName);
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getCountParamProcessConfigByCode(ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<ResultConfigName> listReportStationery = null;
		try {
			listReportStationery = stationeryReportDAO.getCountParamProcessConfigByCode(resultConfigName);
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getCountMasterClassByCode(ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<ResultConfigName> listReportStationery = null;
		try {
			listReportStationery = stationeryReportDAO.getCountMasterClassByCode(resultConfigName);
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getCountParamWebConfigByCode(ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<ResultConfigName> listReportStationery = null;
		try {
			listReportStationery = stationeryReportDAO.getCountParamWebConfigByCode(resultConfigName);
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase editProcessParam(ResultConfigName resultConfigName) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		try {
			// get info of HCDV request
			int dataResponse = stationeryReportDAO.editProcessParam(resultConfigName);
			// get detail of request
			resp.setData(dataResponse);
			resp.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase getAllProcessConfig() {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		List<ResultConfigName> listReportStationery = null;
		try {
			listReportStationery = stationeryReportDAO.getAllProcessConfig();
			resp.setData(listReportStationery);
		} catch (Exception e) {
			resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase insertIssuesStationery(RequestParam requestParam, String userName,
			Collection<GrantedAuthority> roleList) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
			// check validate input
			if (requestParam.getListStationery() == null || requestParam.getListStationery().isEmpty()
					|| requestParam.getPlaceID() <= 0) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}

			// check spending limit
			// get total price request
			List<StationeryParam> listStationery = requestParam.getListStationery();
			List<String> listStationeryID = new ArrayList<>();
			for (int i = 0; i < listStationery.size(); i++) {
				listStationeryID.add(listStationery.get(i).getStationeryId());
			}

			double totalPrice = 0;
			for (int i = 0; i < listStationery.size(); i++) {
				totalPrice += listStationery.get(i).getQuantity() * listStationery.get(i).getUnitPrice();
			}
			
			if (!roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV))) {
			// get remaining spending limit
			      
	      double remainingSpendingLimit = vt050000Service.caculRemainingSpendingLimit(userName, roleList);
	      if (  Math.round(remainingSpendingLimit) == 0) {
		        resp.setStatus(Constant.UNIT_NOT_EXIST_QUOTA);
		        return resp;
		      }
	      StationeryStaff stationeryStaff = stationeryDAO.getStationeryStaffByUser(userName);
	      if(stationeryStaff != null) {
	        String[] usernames = stationeryDAO.getHCDVByUnitId(stationeryStaff.getUnitId());
	        if(usernames != null && usernames.length > 0) {
	          double createItemsTotal = stationeryDao.getHCDVRequest(usernames);
	          remainingSpendingLimit = remainingSpendingLimit + createItemsTotal;
	        }
	      }
	      
	      // if total price request > Remaining spending limit => no insert
	      if (totalPrice > remainingSpendingLimit) {
	        resp.setStatus(Constant.STATIONERY_RESPONSE_SPENDING_LIMIT_EXCEED);
	        return resp;
	      }
      } else {
        double remainingSpendingLimit = vt050000Service.caculRemainingSpendingLimit(userName, roleList);
        if (Math.round(remainingSpendingLimit) == 0) {
	        resp.setStatus(Constant.UNIT_NOT_EXIST_QUOTA);
	        return resp;
	      }
        double moneyUsed = 0.0;
        StationeryStaff stationeryStaff = stationeryDAO.getStationeryStaffByUser(userName);
        if(stationeryStaff != null) {
          String[] usernames = stationeryDAO.getHCDVByUnitId(stationeryStaff.getUnitId());
          if(usernames != null && usernames.length > 0) {
            double createItemsTotal = stationeryDao.getHCDVRequest(usernames);
            moneyUsed = moneyUsed + createItemsTotal;
            System.out.println("moneyUsed " + moneyUsed + " createItemsTotal " + createItemsTotal);
          }
        } else {
          List<Integer> listStatusFinish = new ArrayList<>();
          listStatusFinish.add(Constant.STATIONERY_FINISH);
          VT050000InfoTofindMoneyUsed infoTofindMoneyUsedFinish = new VT050000InfoTofindMoneyUsed(userName,
              listStatusFinish);
          moneyUsed = vt050000DAO.findMoneyUseInThisMonthFinish(infoTofindMoneyUsedFinish);
        }
        remainingSpendingLimit = remainingSpendingLimit - moneyUsed;
        
        // if total price request > Remaining spending limit => no insert
        if (totalPrice > remainingSpendingLimit) {
          resp.setStatus(Constant.STATIONERY_RESPONSE_SPENDING_LIMIT_EXCEED);
          return resp;
        }
      }

			// check validate placeID
			VT050003InfoToCheckPlaceExist info = new VT050003InfoToCheckPlaceExist(requestParam.getPlaceID(),
					Constant.STATUS_ACTIVE);
			int checkPlaceID = vt050003DAO.checkPlaceExist(info);
			if (checkPlaceID != Constant.SUCCESS) {
				resp.setStatus(Constant.RESPONSE_STATUS_ERROR);
				return resp;
			}
			// insert to ISSUES_STATIONERY
			VT050000IssuesStationery issuesStationery = new VT050000IssuesStationery(null, userName,
					requestParam.getPlaceID(), requestParam.getNote(), null, Constant.ISSUES_STATIONERY_PROCESSING, 0,
					null);
			issuesStationery.setCreateUser(userName);
			vt050003DAO.insertIssuesStationery(issuesStationery);
			// get ISSUE_STATIONERY_ID in last ISSUES_STATIONERY of this user to
			// insert to
			// ISSUES_STATIONERY_ITEMS
			String issuesStationeryID = vt050003DAO.findIssuesStationeryID(userName);
			// insert to ISSUES_STATIONERY_ITEMS
			IssuesStationeryItemsToInsert issuesStationeryItemsToInsert = new IssuesStationeryItemsToInsert();
			issuesStationeryItemsToInsert.setIssuesStationeryID(issuesStationeryID);
			issuesStationeryItemsToInsert.setStatus(Constant.STATIONERY_CREATE);
			issuesStationeryItemsToInsert.setCreateUser(userName);
			issuesStationeryItemsToInsert.setListStationery(requestParam.getListStationery());
			stationeryReportDAO.insertIssuesStationeryItems(issuesStationeryItemsToInsert);
			// set response to client

			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			// send sms and notify to HCDV
			try {
				sendSmsAndNotify(userName, requestParam.getPlaceID(), issuesStationeryID, Constant.STATIONERY_CREATE);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

	public void sendSmsAndNotify(String userName, int placeID, String idRecord, int statusRecord) throws Exception {
		Thread notiThread = new Thread() {
			public void run() {
				try {
					VT050003InfoToFindHcdv info = new VT050003InfoToFindHcdv();
					info.setUserName(userName);
					info.setPlaceID(placeID);
					info.setJobCode(Constant.STATIONERY_HCDV_CODE);
					List<String> listHcdvUserName = vt050003DAO.findHcdvUserName(info);

					// find full name of employee request VPP
					String fullName = vt050000DAO.findFullNameByUserName(userName);
					for (String hcdvUserName : listHcdvUserName) {
						// send notify
						String toUserName = hcdvUserName;
						VT050000AdditionalInfoToSendNotify addInfo = new VT050000AdditionalInfoToSendNotify(idRecord,
								Constant.PMQT_ROLE_STAFF_HC_DV, Constant.PMQT_ROLE_EMPLOYYEE);
						String additionalInformation = addInfo.toString();
						String message = notifyMessage.getProperty("N35_1");
						message = MessageFormat.format(message, fullName);
						String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
						int type = Constant.TYPE_NOTIFY_MODUL05;
						String createUser = userName;
						int status = statusRecord;
						notification.sendNotification(toUserName, additionalInformation, message, title, type,
								createUser, status);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************THREAD NOTI ERROR********:");
				}
			}

		};
		notiThread.start();
	}

	private void sendNotiApproved(String approvedId, String loginUsername) throws Exception {
		// TODO N40
		List<ApprovingInfo> approvingInfos = stationeryDao.getApprovingInfoVPP(approvedId);
		if(approvingInfos == null || approvingInfos.size() < 1) return; 
		
		ApprovingInfo firtsInfo = approvingInfos.get(0);
		// send to HCVP_VPP
		String messageNotify = MessageFormat.format(notifyMessage.getProperty("N40"), firtsInfo.getApprovedId());
		String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
		String smsTitle = smsMessage.getProperty("TYPE_SMS_MODUL05");

		AdditionalInfoBase addInfo = new AdditionalInfoBase();
		addInfo.setId(approvedId);
		addInfo.setRoleReceiver(Constant.PMQT_ROLE_STAFF_HCVP_VPP);

		for (ApprovingInfo info : approvingInfos) {
			if (info.getVptctUsername() != null) {
				VT000000EntitySmsVsNotify userInfo = vt000000DAO.findNotifySmsByUserName(info.getVptctUsername());
				userInfo.setToUserName(info.getVptctUsername());
				stationeryService.storgeNotiAndSms(userInfo, messageNotify, title, smsTitle, addInfo);
			}
		}

		// send to HCDV
		if (firtsInfo.getHcdvUsername() != null) {
			VT000000EntitySmsVsNotify loginUserInfo = vt000000DAO.findNotifySmsByUserName(loginUsername);
			String hcdvMessageNotify = MessageFormat.format(notifyMessage.getProperty("N36"),
					loginUserInfo.getCreateUser(), firtsInfo.getHcdvMessage());
			VT000000EntitySmsVsNotify hcdvInfo = vt000000DAO.findNotifySmsByUserName(firtsInfo.getHcdvUsername());
			hcdvInfo.setToUserName(firtsInfo.getHcdvUsername());
			addInfo.setRoleReceiver(Constant.PMQT_ROLE_STAFF_HC_DV);
			
			stationeryService.storgeNotiAndSms(hcdvInfo, hcdvMessageNotify, title, smsTitle, addInfo);
		}
	}

	private void sendNotiApprovedReject(String approvedId, String loginUsername, String message) throws Exception {
		// TODO N40
		Thread sendNotiAndSmsThread = new Thread() {
			@Override
			public void run() {
				try {
					List<ApprovingInfo> approvingInfos = stationeryDao.getApprovingInfo(approvedId);
					ApprovingInfo firtsInfo = approvingInfos.get(0);

					String title = notifyMessage.getProperty("TITLE_NOTIFY_MODUL05");
					String smsTitle = smsMessage.getProperty("TYPE_SMS_MODUL05");

					AdditionalInfoBase addInfo = new AdditionalInfoBase();
					addInfo.setId(approvedId);
					addInfo.setRoleReceiver(Constant.PMQT_ROLE_STAFF_HC_DV);

					if (firtsInfo.getHcdvUsername() != null) {
						VT000000EntitySmsVsNotify userInfo = vt000000DAO.findNotifySmsByUserName(loginUsername);
						String hcdvMessageNotify = MessageFormat.format(notifyMessage.getProperty("N38"),
								userInfo.getCreateUser(), message);
						VT000000EntitySmsVsNotify hcdvInfo = vt000000DAO
								.findNotifySmsByUserName(firtsInfo.getHcdvUsername());
						hcdvInfo.setToUserName(firtsInfo.getHcdvUsername());
						stationeryService.sendNotiAndSms(hcdvInfo, hcdvMessageNotify, title, smsTitle, addInfo, 52);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		};
		sendNotiAndSmsThread.start();
	}

	@Override
	public ResponseEntityBase findDataToProcess(SearchRequestParamPagging param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		DataResponseRatting dataResponse = new DataResponseRatting();
		try {
			VT050013DataGetAll dataResponseRequest = new VT050013DataGetAll();
			// get info of HCDV request
			dataResponseRequest = stationeryService.getApprovedInfoProcessById(param.getRequestID());
			dataResponse.setFullNameHcdv(dataResponseRequest.getHcdvUserName());
			dataResponse.setFullNameTct(dataResponseRequest.getTptctUserName());
			dataResponse.setDateRequest(dataResponseRequest.getCreateDate());
			dataResponse.setMessage(dataResponseRequest.getHcdvMessage());
			dataResponse.setStatus(dataResponseRequest.getStatus());
			dataResponse.setHcdvReasonReject(dataResponseRequest.getHcdvReasonReject());
			dataResponse.setTotalFulfill(dataResponseRequest.getCountTotalFullfill());
			dataResponse.setTotalRequest(dataResponseRequest.getCountTotalRequest());
			dataResponse.setSumTotalMoney(dataResponseRequest.getCountTotalMoneyRequest());
			dataResponse.setSumTotalMoneyHcdv(dataResponseRequest.getCountTotalMoneyFullfill());
			dataResponse.setFeedBackToHcdv(dataResponseRequest.getFeedbackToHcdv());
			dataResponse.setFeedBackToVptct(dataResponseRequest.getFeedbackToVptct());
			dataResponse.setRatingToUser(dataResponseRequest.getRatingToUser());
			dataResponse.setRatingToVptct(dataResponseRequest.getRatingToVptct());
			dataResponse.setVptctReason(dataResponseRequest.getVptctReason());
			dataResponse.setVptctPostponeToTime(dataResponseRequest.getVptctPostponeToTime());
			dataResponse.setVptctReasonReject(dataResponseRequest.getVptctReasonReject());
//			dataResponse = stationeryReportDAO.findInfoRequestHcdv(param.getRequestID());
			// get detail of request
			dataResponse.setListData(dataResponseRequest.getListRequest());
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase findDataProcessToRatting(SearchRequestParamPagging param) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		DataResponseRatting dataResponse = new DataResponseRatting();
		try {
			VT050013DataGetAll dataResponseRequest = new VT050013DataGetAll();
			// get info of HCDV request
			dataResponseRequest = stationeryService.getApprovedProcessInfoById(param.getRequestID());
			dataResponse.setFullNameHcdv(dataResponseRequest.getHcdvUserName());
			dataResponse.setFullNameTct(dataResponseRequest.getTptctUserName());
			dataResponse.setDateRequest(dataResponseRequest.getCreateDate());
			dataResponse.setMessage(dataResponseRequest.getHcdvMessage());
			dataResponse.setStatus(dataResponseRequest.getStatus());
			dataResponse.setHcdvReasonReject(dataResponseRequest.getHcdvReasonReject());
			dataResponse.setTotalFulfill(dataResponseRequest.getCountTotalFullfill());
			dataResponse.setTotalRequest(dataResponseRequest.getCountTotalRequest());
			dataResponse.setSumTotalMoney(dataResponseRequest.getCountTotalMoneyRequest());
			dataResponse.setSumTotalMoneyHcdv(dataResponseRequest.getCountTotalMoneyFullfill());
			dataResponse.setFeedBackToHcdv(dataResponseRequest.getFeedbackToHcdv());
			dataResponse.setFeedBackToVptct(dataResponseRequest.getFeedbackToVptct());
			dataResponse.setRatingToUser(dataResponseRequest.getRatingToUser());
			dataResponse.setRatingToVptct(dataResponseRequest.getRatingToVptct());
			dataResponse.setVptctReason(dataResponseRequest.getVptctReason());
			dataResponse.setVptctPostponeToTime(dataResponseRequest.getVptctPostponeToTime());
			dataResponse.setVptctReasonReject(dataResponseRequest.getVptctReasonReject());
			dataResponse.setUnitName(dataResponseRequest.getUnitName());
			dataResponse.setPhoneNumber(dataResponseRequest.getPhoneNumber());
			dataResponse.setLdReasonReject(dataResponseRequest.getLdReasonReject());
//			dataResponse = stationeryReportDAO.findInfoRequestHcdv(param.getRequestID());
			// get detail of request
			dataResponse.setListData(dataResponseRequest.getListRequest());
			resp.setData(dataResponse);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return resp;
	}

	@Override
	public ResponseEntityBase checkHcdvInStaff(String userName) {
		 ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
		StationeryStaff stationeryStaff = stationeryDAO.getStationeryStaffByUser(userName);
		resp.setData(stationeryStaff);
		return resp;
	}

	@Override
	public ResponseEntityBase checkVPCTCTInStaff(String userName) {
		 ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
			StationeryStaff stationeryStaff = stationeryDAO.getStationeryStaffVPTCTByUser(userName);
			resp.setData(stationeryStaff);
			return resp;
	}
}
