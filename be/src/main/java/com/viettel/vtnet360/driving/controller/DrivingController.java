package com.viettel.vtnet360.driving.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.checking.service.Condition;
import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.driving.dto.SearchCarBooking;
import com.viettel.vtnet360.driving.dto.SearchData;
import com.viettel.vtnet360.driving.entity.ListCarBooking;
import com.viettel.vtnet360.driving.entity.ListTrip;
import com.viettel.vtnet360.driving.service.DrivingService;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt03.vt030014.entity.VT030014ListCondition;

@RestController
@RequestMapping("/com/viettel/vtnet360/vt03/vt030003")
public class DrivingController extends BaseController {
	private final static Logger logger = Logger.getLogger(DrivingController.class);

	@Autowired
	private DrivingService drivingService;
	
	@Autowired
  private Properties configProperty;

	@RequestMapping(value = "/get-booking-by-id", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getCarBookingbyId(@RequestBody SearchCarBooking config) {
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      if(isValidated(configProperty, config.getSecurityUsername(), config.getSecurityPassword())) {
        resp.setData(drivingService.getCarbookingById(config));
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      }
    } catch (Exception e) {
      resp.setData("Cannot send the noti!");
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
      logger.error(e.getMessage(), e);
    }
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

	@RequestMapping(value = "/searchQldv", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchLddv(@RequestBody Condition searchDTO) {

		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchDTO.getSecurityUsername(), searchDTO.getSecurityPassword())) {
		    resp = drivingService.searchManagerUnit(searchDTO.getSearch().trim());
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchQlcvp", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchCvp(@RequestBody Condition searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchDTO.getSecurityUsername(), searchDTO.getSecurityPassword())) {
		    resp = drivingService.searchManagerChief(searchDTO.getSearch().trim());		    
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchPlaceStart", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchPlaceStart(@RequestBody SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
		    resp = drivingService.searchPlaceStart(searchData);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchUnit", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchUnit(@RequestBody Condition searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchDTO.getSecurityUsername(), searchDTO.getSecurityPassword())) {
		    resp = drivingService.searchUnit(Integer.parseInt(searchDTO.getSearch()));
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchPlaceName", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchPlaceName(@RequestBody SearchData searchData) {
		logger.info("*********** searchPlaceName get list searchAllCars start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
		    resp = drivingService.searchPlaceName(searchData);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** searchPlaceName get list searchAllCars end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchFullName", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchFullName(@RequestBody Condition searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchDTO.getSecurityUsername(), searchDTO.getSecurityPassword())) {
		    resp = drivingService.searchFullName(Integer.parseInt(searchDTO.getSearch()));
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchPhoneNumber", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchPhoneNumber(@RequestBody Condition searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchDTO.getSecurityUsername(), searchDTO.getSecurityPassword())) {
		    resp = drivingService.searchPhoneNumber(Integer.parseInt(searchDTO.getSearch()));
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/searchDriveName", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchDriveName(@RequestBody Condition searchDTO) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchDTO.getSecurityUsername(), searchDTO.getSecurityPassword())) {
		    resp = drivingService.searchDriveName(searchDTO.getSearch());
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchDriveNameIsFree", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchDriveNameAll(@RequestBody ListTrip listTrip) {
		logger.info("*********** searchDriveNameIsFree get list searchDriveNameIsFree start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    resp = drivingService.searchDriveNameIsFree(listTrip);
		  }
			resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** searchDriveNameIsFree get list searchDriveNameIsFree end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchCarsIsFree", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchCarsIsFree(@RequestBody ListTrip listTrip) {
		logger.info("*********** searchCarsIsFree get list searchCarsIsFree start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    resp = drivingService.searchCarsIsFree(listTrip);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** searchCarsIsFree get list searchCarsIsFree end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * @param condition
	 * @param principal
	 * @return List<VT030014ListTrip>
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') "
	    + "          or hasAuthority('PMQT_QL_Doi_xe') or hasAuthority('PMQT_Doi_xe') or hasAuthority('PMQT_HCDV')")
	@RequestMapping(value = "/findAllListTrip", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findAllListTrip(@RequestBody VT030014ListCondition condition, Principal principal) throws Exception {
		ResponseEntityBase reb = null;
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String loginUserName = (String) oauth.getPrincipal();
	      condition.setLoginUserName(loginUserName);
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = drivingService.findAllListTrip(condition, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return reb;
	}

	@RequestMapping(value = "/updateCarDetails", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateCarDetails(@RequestBody ListTrip listTrip, Principal principal) {
		logger.info("*********** updateCarDetails get list updateCarDetails start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String username = (String) oauth.getPrincipal();
	      listTrip.setUpdateUser(username);
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      resp = drivingService.updateCarDetails(listTrip, listRole, username);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** updateCarDetails get list updateCarDetails end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateRejectCarDetails", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateRejectCarDetails(@RequestBody ListTrip listTrip, Principal principal) {
		logger.info("*********** updateCarDetails get list updateCarDetails start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      listTrip.setUpdateUser((String) oauth.getPrincipal());
	      resp = drivingService.updateRejectCarDetails(listTrip);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** updateCarDetails get list updateCarDetails end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/insertCarDetails", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> insertListTrip(@RequestBody ListTrip listTrip, Principal principal) {
		logger.info("*********** insertCarDetails get list insertCarDetails start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      listTrip.setCreateUser((String) oauth.getPrincipal());
	      listTrip.setEmpName((String) oauth.getPrincipal());
	      resp = drivingService.insertCarDetails(listTrip);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** insertCarDetails get list insertCarDetails end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateNewCarDetails", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateNewCarDetails(@RequestBody ListTrip listTrip, Principal principal) {
		logger.info("*********** updateCarDetails get list updateCarDetails start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      listTrip.setUpdateUser((String) oauth.getPrincipal());
	      resp = drivingService.updateNewCarDetails(listTrip);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** updateCarDetails get list updateCarDetails end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCompleteCarDetails", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateCompleteCarDetails(@RequestBody ListTrip listTrip, Principal principal) {
		logger.info("*********** updateCompleteCarDetails get list updateCompleteCarDetails start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      listTrip.setUpdateUser((String) oauth.getPrincipal());
	      resp = drivingService.updateCompleteCarDetails(listTrip);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** updateCompleteCarDetails get list updateCompleteCarDetails end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateRenewCarDetails", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateRenewCarDetails(@RequestBody ListTrip listTrip, Principal principal) {
		logger.info("*********** updateRenewCarDetails get list updateRenewCarDetails start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      listTrip.setUpdateUser((String) oauth.getPrincipal());
	      resp = drivingService.updateRenewCarDetails(listTrip);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** updateRenewCarDetails get list updateRenewCarDetails end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCarJourney", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateCarJourney(@RequestBody ListTrip listTrip, Principal principal) {
		logger.info("*********** updateCarJourney get list updateCarJourney start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      listTrip.setUpdateUser((String) oauth.getPrincipal());
	      resp = drivingService.updateCarJourney(listTrip);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** updateCarJourney get list updateCarJourney end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCarRoute", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateCarRoute(@RequestBody ListTrip listTrip, Principal principal) {
		logger.info("*********** updateCarJourney get list updateCarJourney start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      listTrip.setUpdateUser((String) oauth.getPrincipal());
	      resp = drivingService.updateCarRoute(listTrip);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** updateCarJourney get list updateCarJourney end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchDriveSquadName", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchDriveSquadName(@RequestBody SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
		    resp = drivingService.searchDriveSquadName(searchData);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchLicensePlate", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchLicensePlate(@RequestBody SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
		    resp = drivingService.searchLicensePlate(searchData);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchSuggestionLicensePlate", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchSuggestionLicensePlate(@RequestBody SearchData searchData) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
		    resp = drivingService.searchSuggestionLicensePlate(searchData);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getPlaceByAll", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getPlaceById(@RequestBody BaseEntity info) {
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = drivingService.getPlaceByAll();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getQlttByUserName", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getQlttByUserName(@RequestBody SearchData searchData) {
		logger.info("*********** getQlttByUserName get list getQlttByUserName start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
		    resp = drivingService.getQlttByUserName(searchData);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}
		logger.info("*********** getCarSeatById get list searchAllCars END***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getQldvByUserName", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getQldvByUserName(@RequestBody SearchData searchData) {
		logger.info("*********** getQldvByUserName get list getQldvByUserName start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
		    resp = drivingService.getQldvByUserName(searchData);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}
		logger.info("*********** getQldvByUserName get list getQldvByUserName END***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getQlcvpByUserName", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> getQlcvpByUserName(@RequestBody SearchData searchData) {
		logger.info("*********** getQlcvpByUserName get list getQlcvpByUserName start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, searchData.getSecurityUsername(), searchData.getSecurityPassword())) {
		    resp = drivingService.getQlcvpByUserName(searchData);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
		}
		logger.info("*********** getQlcvpByUserName get list getQlcvpByUserName END***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCarRefuse", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateCarRefuse(@RequestBody ListTrip listTrip, Principal principal) {
		logger.info("*********** updateCarDetails get list updateCarDetails start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      listTrip.setUpdateUser((String) oauth.getPrincipal());
	      resp = drivingService.updateCarRefuse(listTrip);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** updateCarDetails get list updateCarDetails end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCarApprove", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateCarApprove(@RequestBody ListTrip listTrip, Principal principal) {
		logger.info("*********** updateCarApprove get list updateCarDetails start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      listTrip.setUpdateUser((String) oauth.getPrincipal());
	      resp = drivingService.updateCarApprove(listTrip);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** updateCarApprove get list updateCarDetails end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateListCarApprove", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateListCarApprove(@RequestBody ListCarBooking listCarBooking, Principal principal) {
		logger.info("*********** updateListCarApprove get list updateCarDetails start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		Collection<GrantedAuthority> roleList = null;
		String userName = null;
    try {
      if(isValidated(configProperty, listCarBooking.getSecurityUsername(), listCarBooking.getSecurityPassword())) {
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        userName = (String) oauth.getPrincipal();
        listCarBooking.setLoginUsername(userName);
        roleList = oauth.getAuthorities();
        resp = drivingService.updateListCarApprove(listCarBooking, roleList);
        resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
      }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** updateListCarApprove get list updateCarDetails end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_QL') or hasAuthority('PMQT_NV') or hasAuthority('PMQT_CVP') or hasAuthority('PMQT_QL_Doi_xe')")
	@RequestMapping(value = "/findAllListTripById", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findAllListTripById(@RequestBody VT030014ListCondition condition, Principal principal) throws Exception {
		ResponseEntityBase reb = null;
		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String loginUserName = (String) oauth.getPrincipal();
	      condition.setLoginUserName(loginUserName);
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = drivingService.findAllListTripById(condition, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return reb;
	}

	@RequestMapping(value = "/searchPlaceStartAll", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> searchPlaceStartAll(@RequestBody BaseEntity info) {
		logger.info("*********** searchPlaceStartAll get list searchPlaceStartAll start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = drivingService.searchPlaceStartAll();
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
			logger.error(e.getMessage(), e);
			throw (e);
		}
		logger.info("*********** searchPlaceStartAll get list searchPlaceStartAll end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateCarRefuseOrder", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateCarRefuseOrder(@RequestBody ListTrip listTrip, Principal principal) {
		logger.info("*********** updateCarRefuseOrder get list updateCarRefuseOrder start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      listTrip.setUpdateUser((String) oauth.getPrincipal());
	      resp = drivingService.updateCarRefuseOrder(listTrip);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** updateCarRefuseOrder get list updateCarRefuseOrder end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateCarOrder", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> updateCarOrder(@RequestBody ListTrip listTrip, Principal principal) {
		logger.info("*********** updateCarOrder get list updateCarOrder start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, listTrip.getSecurityUsername(), listTrip.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      listTrip.setUpdateUser((String) oauth.getPrincipal());
	      resp = drivingService.updateCarOrder(listTrip);
	      resp.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** updateCarOrder get list updateCarOrder end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
}
