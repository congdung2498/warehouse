package com.viettel.vtnet360.vt04.vt040001.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt04.vt040001.dao.VT040001DAO;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001InfoToFindEmployee;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001ListEmployee;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001ListPlace;
import com.viettel.vtnet360.vt04.vt040001.entity.VT040001ListService;

/**
 * class to do business VT040001
 * 
 * @author ThangBT 04/10/2018
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VT040001ServiceImpl implements VT040001Service {

	private final Logger logger = Logger.getLogger(this.getClass());

	private static final String END_INSERT_SERVICE_FAIL = "**************** End processing insert menu service - Fail ****************";

	private static final String END_UPDATE_SERVICE_FAIL = "**************** End processing udpate service - Fail ****************";

	private static final String END_DELETE_SERVICE_FAIL = "**************** End processing delete service - Fail ****************";

	@Autowired
	private VT040001DAO vt040001dao;

	/**
	 * get list places when type character
	 * 
	 * @param placeInfo
	 * @return List<VT040001ListPlace>
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListPlace(VT040001ListPlace placeInfo) throws Exception {

		logger.info("**************** Start get list places ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListPlace> places = new ArrayList<>();

		try {
			if(placeInfo.getPlaceName()!=null) {
			String pattern = placeInfo.getPlaceName();
			placeInfo.setPlaceName(pattern.trim());
			}
			
			places = vt040001dao.findListPlace(placeInfo);
			reb.setData(places);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list places - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list places - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get list services when type character
	 * 
	 * @param serviceInfo
	 * @return List<VT040001ListService>
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListService(VT040001ListService serviceInfo) throws Exception {

		logger.info("**************** Start get list services ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListService> services = new ArrayList<>();

		try {
			if(serviceInfo.getServiceName()!=null) {
				String pattern = serviceInfo.getServiceName();
				serviceInfo.setServiceName(pattern.trim());
			}
		
			services = vt040001dao.findListService(serviceInfo);
			reb.setData(services);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list services - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list services - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get list units when type character, reused class VT040001ListPlace
	 * 
	 * @param unitInfo
	 * @return List<VT040001ListPlace>
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListUnit(VT040001ListPlace unitInfo) throws Exception {

		logger.info("**************** Start get list units ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListPlace> units = new ArrayList<>();

		try {
			if(unitInfo.getPlaceName()!=null) {
			String pattern = unitInfo.getPlaceName();
			unitInfo.setPlaceName(pattern.trim());
			}
			
			units = vt040001dao.findListUnit(unitInfo);
			reb.setData(units);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list units - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list units - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get list receivers when type character
	 * 
	 * @param empInfo
	 * @return List<VT040001ListEmployee>
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListReceiver(VT040001ListEmployee empInfo) throws Exception {

		logger.info("**************** Start get list receivers ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListEmployee> receivers = new ArrayList<>();

		try {
			if (empInfo.getReceiverName() != null) {
				String pattern = empInfo.getReceiverName();
				empInfo.setReceiverName(pattern.trim());
			}

			receivers = vt040001dao.findListReceiver(empInfo);
			reb.setData(receivers);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list receivers - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list receivers - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * get list receivers when type character
	 * 
	 * @param empInfo
	 * @return List<VT040001ListEmployee>
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListReceiverForSussgest(VT040001InfoToFindEmployee empInfo, Collection<GrantedAuthority> roleList) throws Exception {

		logger.info("**************** Start get list receivers ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListEmployee> receivers = new ArrayList<>();

		try {
			if (empInfo.getReceiverName() != null) {
				String pattern = empInfo.getReceiverName();
				empInfo.setReceiverName(pattern.trim());
			}
			
			// if user logged on is HCĐV or QL or CVP => get employee by unit of them
			if (roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF_HC_DV)) 
					|| roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER))
					|| roleList.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_MANAGER_CVP))) {
				empInfo.setRoleToGetUnit(true);
			} else {
				empInfo.setRoleToGetUnit(false);
			}
			
			receivers = vt040001dao.findListReceiverForSussgest(empInfo);
			reb.setData(receivers);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list receivers - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list receivers - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListReceiverOfService(VT040001ListEmployee empInfo) throws Exception {

		logger.info("**************** Start get list receivers of service ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListEmployee> listReceiverOfService = null;

		try {
			listReceiverOfService = vt040001dao.findListReceiverOfService(empInfo);
			reb.setData(listReceiverOfService);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list receivers of service - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list receivers of service - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListMenuService(VT040001ListService serviceInfo,
			Collection<GrantedAuthority> listRole) throws Exception {

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListService> listMenuService = new ArrayList<>();

		try {
			logger.info("**************** Start get list menu services ****************");

			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {
				if (serviceInfo.getServiceName() != null) {
					String pattern = serviceInfo.getServiceName();
					serviceInfo.setServiceName(pattern.trim());
				}

				listMenuService = vt040001dao.findListSearchMenuService(serviceInfo);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				reb.setData(listMenuService);

				logger.info("**************** End get list menu services - OK ****************");
			}
		} catch (Exception e) {
			logger.info("**************** End get list menu services - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase countTotalMenuService(VT040001ListService serviceInfo,
			Collection<GrantedAuthority> listRole) throws Exception {

		int countRecords = -1;
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, countRecords);

		try {
			logger.info("**************** Start count records list menu services ****************");

			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {
				
				if (serviceInfo.getServiceName() != null) {
					String pattern = serviceInfo.getServiceName();
					serviceInfo.setServiceName(pattern.trim());
				}
				
				countRecords = vt040001dao.countTotalMenuService(serviceInfo);
				reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
				reb.setData(countRecords);

				logger.info("**************** End count records list menu services - OK ****************");
			}
		} catch (Exception e) {
			logger.info("**************** End count records list menu services - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	@Transactional
	public ResponseEntityBase insertServiceAndReceiver(VT040001ListService serviceInfo,
			Collection<GrantedAuthority> listRole) throws Exception {

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {
				logger.info("**************** Start processing insert menu service ****************");
				logger.info("**************** Start check duplicate service name and place ****************");

				int checkSvNameAndPlace = vt040001dao.checkServiceNameAndPlace(serviceInfo);

				logger.info("**************** End check duplicate service name and place - OK ****************");

				if (checkSvNameAndPlace <= 0) {
					logger.info("**************** Start insert menu service ****************");

					int statusService = vt040001dao.insertService(serviceInfo);

					logger.info("**************** End insert menu service - OK ****************");

					if (statusService > 0) {
						serviceInfo.setServiceId(serviceInfo.getServiceId());

						logger.info("**************** Start insert receiver ****************");

						int statusReceiver = vt040001dao.insertReceiver(serviceInfo);

						logger.info("**************** End insert receiver OK ****************");

						if (statusReceiver > 0) {
							reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

							logger.info("**************** End processing insert menu service - OK ****************");
						} else {
							logger.info(VT040001ServiceImpl.END_INSERT_SERVICE_FAIL);
						}
					}
				} else {
					// if both service name and place id are duplicate
					reb.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);

					logger.info("**************** Duplicate service name and place ****************");
					logger.info(VT040001ServiceImpl.END_INSERT_SERVICE_FAIL);
				}
			}
		} catch (DuplicateKeyException dke) {
			// if only service id are duplicate
			reb.setStatus(Constant.RESPONSE_ERROR_DUPLICATEKEY);

			logger.info("**************** End insert menu service - Fail - Duplicate key ****************");
			logger.error(dke);
		} catch (Exception e) {
			logger.info(VT040001ServiceImpl.END_INSERT_SERVICE_FAIL);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * handle action update service and receiver
	 * 
	 * @param reb
	 * @param serviceInfo
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase handleUpdateService(ResponseEntityBase reb, VT040001ListService serviceInfo)
			throws Exception {
		boolean checkStatus = false;

		int statusDeleteFlag = vt040001dao.checkDeleteFlagService(serviceInfo);

		/* check service have yet deleted or not yet */
		if (statusDeleteFlag == Constant.DELETED_FLAG) {
			reb.setStatus(Constant.RESPONSE_SERVICE_DELETED);
			return reb;
		}

		logger.info("**************** Start update menu service ****************");

		int statusService = vt040001dao.updateService(serviceInfo);

		logger.info("**************** End update menu service - OK ****************");

		if (statusService > 0) {
			logger.info("**************** Start find created user and date ****************");

			VT040001ListService findCreatedUser = vt040001dao.findCreatedUser(serviceInfo);

			logger.info("**************** End find created user and date ****************");
			logger.info("**************** Start delete receiver ****************");

			int statusDeleteReceiver = vt040001dao.deleteServiceReceiver(serviceInfo);

			logger.info("**************** End delete receiver - OK ****************");

			if (statusDeleteReceiver >= 0) {
				serviceInfo.setPlaceName(findCreatedUser.getPlaceName());
				serviceInfo.setUnitName(findCreatedUser.getUnitName());

				logger.info("**************** Start update receiver by inserting ****************");

				int statusUpdateReceiver = vt040001dao.updateReceiverByInsert(serviceInfo);

				logger.info("**************** End update receiver by inserting - OK ****************");

				if (statusUpdateReceiver > 0) {
					checkStatus = true;
				}
			}
		}

		if (checkStatus) {
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End processing udpate service - OK ****************");
		} else {
			logger.info(VT040001ServiceImpl.END_UPDATE_SERVICE_FAIL);
		}

		return reb;
	}

	@Override
	@Transactional
	public ResponseEntityBase updateServiceAndReceiver(VT040001ListService serviceInfo,
			Collection<GrantedAuthority> listRole) throws Exception {

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {
				logger.info("**************** Start processing udpate service ****************");
				logger.info("**************** Start check contain service name and place ****************");

				int checkSvNameAndPlace = vt040001dao.checkServiceNameAndPlace(serviceInfo);

				logger.info("**************** End check contain service name and place - OK ****************");

				if (checkSvNameAndPlace <= 0) {
					if (serviceInfo.getStartRow() == 1) {
						logger.info("**************** Start check service is requesting or not ****************");

						checkSvNameAndPlace = vt040001dao.checkServiceRequesting(serviceInfo);

						logger.info("**************** End check service is requesting or not - OK ****************");

						if (checkSvNameAndPlace <= 0) {
							reb = handleUpdateService(reb, serviceInfo);
						} else {
							logger.info("**************** Service is requesting ****************");
							logger.info(VT040001ServiceImpl.END_UPDATE_SERVICE_FAIL);

							reb.setStatus(Constant.SERVICE_IS_REQUESTING);
						}
					} else {
						reb = handleUpdateService(reb, serviceInfo);
					}
				} else {
					// if contain couple service name and place id
					reb.setStatus(Constant.RESPONSE_STATUS_RECORD_EXISTED);

					logger.info("**************** Contain service name and place ****************");
					logger.info(VT040001ServiceImpl.END_UPDATE_SERVICE_FAIL);
				}
			}
		} catch (Exception e) {
			logger.info(VT040001ServiceImpl.END_UPDATE_SERVICE_FAIL);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * handle delete service and receiver
	 * 
	 * @param reb
	 * @param serviceInfo
	 * @return ResponseEntityBase
	 * @throws Exception
	 */
	public ResponseEntityBase handleDeleteService(ResponseEntityBase reb, VT040001ListService serviceInfo)
			throws Exception {

		logger.info("**************** Start delete service ****************");

		int statusDeleteService = vt040001dao.deleteService(serviceInfo);

		logger.info("**************** End delete service - OK ****************");

		if (statusDeleteService > 0) {
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End processing delete service - OK ****************");
		} else {
			logger.info(VT040001ServiceImpl.END_DELETE_SERVICE_FAIL);
		}

		return reb;
	}

	@Override
	@Transactional
	public ResponseEntityBase deleteServiceAndReceiver(VT040001ListService serviceInfo,
			Collection<GrantedAuthority> listRole) throws Exception {

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
			if (listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_ADMIN))
					|| listRole.contains(new SimpleGrantedAuthority(Constant.PMQT_ROLE_STAFF))) {
				logger.info("**************** Start processing delete service ****************");
				logger.info("**************** Start check contain service name and place ****************");

				int checkSvNameAndPlace = vt040001dao.checkServiceNameAndPlaceForDeleting(serviceInfo);

				logger.info("**************** End check contain service name and place - OK ****************");

				if (checkSvNameAndPlace > 0) {
					logger.info("**************** Start check service is requesting or not ****************");

					checkSvNameAndPlace = vt040001dao.checkServiceRequesting(serviceInfo);

					logger.info("**************** End check service is requesting or not - OK ****************");

					if (checkSvNameAndPlace <= 0) {
						reb = handleDeleteService(reb, serviceInfo);
					} else {
						logger.info("**************** Service is requesting ****************");
						logger.info(VT040001ServiceImpl.END_UPDATE_SERVICE_FAIL);

						reb.setStatus(Constant.SERVICE_IS_REQUESTING);
					}
				} else {
					// if couple service name and place id are not existed
					reb.setStatus(Constant.RESPONSE_STATUS_RECORD_NOT_EXISTED);

					logger.info("**************** Service name and place are not existed ****************");
					logger.info(VT040001ServiceImpl.END_DELETE_SERVICE_FAIL);
				}
			}
		} catch (Exception e) {
			logger.info(VT040001ServiceImpl.END_DELETE_SERVICE_FAIL);
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	public ResponseEntityBase getListPlace(VT040001ListPlace placeInfo) {
		logger.info("**************** Start get list places ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListPlace> places = new ArrayList<>();

		try {
			if(placeInfo.getPlaceName()!=null) {
			String pattern = placeInfo.getPlaceName();
			placeInfo.setPlaceName(pattern.trim());
			}
			
			places = vt040001dao.getListPlace(placeInfo);
			reb.setData(places);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list places - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list places - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	public ResponseEntityBase getListService(VT040001ListService serviceInfo) throws Exception {
		logger.info("**************** Start get list services ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListService> services = new ArrayList<>();

		try {
			if(serviceInfo.getServiceName()!=null) {
				String pattern = serviceInfo.getServiceName();
				serviceInfo.setServiceName(pattern.trim());
			}
		
			services = vt040001dao.getListService(serviceInfo);
			reb.setData(services);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list services - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list services - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	public ResponseEntityBase getListUnit(VT040001ListPlace unitInfo) throws Exception {
		logger.info("**************** Start get list units ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListPlace> units = new ArrayList<>();

		try {
			if(unitInfo.getPlaceName()!=null) {
			String pattern = unitInfo.getPlaceName();
			unitInfo.setPlaceName(pattern.trim());
			}
			
			units = vt040001dao.getListUnit(unitInfo);
			reb.setData(units);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list units - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list units - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	public ResponseEntityBase getListReceiver(VT040001ListEmployee empInfo) {
		logger.info("**************** Start get list receivers ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListEmployee> receivers = new ArrayList<>();

		try {
			if (empInfo.getReceiverName() != null) {
				String pattern = empInfo.getReceiverName();
				empInfo.setReceiverName(pattern.trim());
			}

			receivers = vt040001dao.getListReceiver(empInfo);
			reb.setData(receivers);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list receivers - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list receivers - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	public int checkServiceNameAndPlace(VT040001ListService serviceInfo) {
		return vt040001dao.checkServiceNameAndPlace(serviceInfo);
	}
	
	@Override
	public ResponseEntityBase getPlaceByName(VT040001ListPlace placeInfo) {
		logger.info("**************** Start get list places ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListPlace> places = new ArrayList<>();

		try {
			if(placeInfo.getPlaceName()!=null) {
			String pattern = placeInfo.getPlaceName();
			placeInfo.setPlaceName(pattern.trim());
			}
			
			places = vt040001dao.getPlaceByName(placeInfo);
			reb.setData(places);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list places - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list places - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}
	
	@Override
	public ResponseEntityBase getServiceByName(VT040001ListService serviceInfo) throws Exception {
		logger.info("**************** Start get list services ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListService> services = new ArrayList<>();

		try {
			if(serviceInfo.getServiceName()!=null) {
				String pattern = serviceInfo.getServiceName();
				serviceInfo.setServiceName(pattern.trim());
			}
		
			services = vt040001dao.getServiceByName(serviceInfo);
			reb.setData(services);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list services - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list services - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}
	
	@Override
	public ResponseEntityBase getUnitByName(VT040001ListPlace unitInfo) throws Exception {
		logger.info("**************** Start get list units ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListPlace> units = new ArrayList<>();

		try {
			if(unitInfo.getUnitId()!=null) {
			String pattern = unitInfo.getUnitId();
			unitInfo.setUnitId(pattern.trim());
			}
			
			units = vt040001dao.getUnitByName(unitInfo);
			reb.setData(units);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list units - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list units - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findReceiverByUserName(VT040001ListEmployee empInfo) throws Exception {

		logger.info("**************** Start get list receivers ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT040001ListEmployee> receivers = new ArrayList<>();

		try {
			if (empInfo.getReceiverName() != null) {
				String pattern = empInfo.getReceiverName();
				empInfo.setReceiverName(pattern.trim());
			}

			receivers = vt040001dao.findReceiverByUserName(empInfo);
			reb.setData(receivers);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

			logger.info("**************** End get list receivers - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list receivers - Fail ****************");
			logger.error(e.getMessage(), e);
		}

		return reb;
	}
}
