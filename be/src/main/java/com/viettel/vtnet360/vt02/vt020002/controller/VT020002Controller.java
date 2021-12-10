package com.viettel.vtnet360.vt02.vt020002.controller;

import java.security.Principal;
import java.util.Base64;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.common.security.BaseEntity;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt02.vt020002.entity.DishForMobile;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002Dish;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002DishOffset;
import com.viettel.vtnet360.vt02.vt020002.entity.VT020002InfoToFindKitchen;
import com.viettel.vtnet360.vt02.vt020002.service.VT020002Service;

/**
 * Class controller for screen VT020002 : setting dish
 * 
 * @author DuyNK 08/09/2018
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt02/vt020002")
public class VT020002Controller extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT020002Service vt020002Service;
	
	@Autowired
  private Properties configProperty;

	/**
	 * Select all kitchen from KITCHEN_SETTING that active
	 * 
	 * @param info
	 * @return ResponseEntityBase(status, List<VT020002Kitchen>)
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020002FindListKitchen(@RequestBody VT020002InfoToFindKitchen info) {
		logger.info("*********** vt020002_01 get list kitchen start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt020002Service.findListKitchen(info);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020002_01 get list kitchen end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Select list dish from DISH_SETTING by KITCHEN_ID, DISH_NAME, limit record by
	 * pageNumber and page size
	 * 
	 * @param VT020002DishOffset
	 * @return ResponseEntityBase(status, List<VT020002Kitchen>)
	 */
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020002FindListDish(@RequestBody VT020002DishOffset dishOffset) {
		logger.info("*********** vt020002_02 get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, dishOffset.getSecurityUsername(), dishOffset.getSecurityPassword())) {
		    resp = vt020002Service.findListDish(dishOffset);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020002_02 get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Insert, delete, update Dish to DISH_SETTING
	 * 
	 * @param param
	 * @param principal
	 * @return ResponseEntityBase
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_Bep_truong')")
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020002SettingDish(@RequestPart(name="image", required=false) MultipartFile image,
			@RequestParam String strParam, Principal principal) {
		logger.info("*********** vt020002_03 setting dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		Gson gson = new Gson();
		VT020002DishWrapper param = gson.fromJson(strParam, VT020002DishWrapper.class);
		try {
			// get userName
			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			String userName = (String) oauth.getPrincipal();
			Collection<GrantedAuthority> roleList = oauth.getAuthorities();
			VT020002Dish dish = param.getData();
			
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dish));
			
			if(isValidated(configProperty, dish.getSecurityUsername(), dish.getSecurityPassword())) {
			  if (param.getAction() == Constant.REQUEST_ACTION_INSERT) {
	        logger.info("*********** vt020002_03 insert dish start***********");
	        resp = vt020002Service.insertDish(dish, userName, roleList, image);
	        logger.info("*********** vt020002_03 insert dish end***********");

	      } else if (param.getAction() == Constant.REQUEST_ACTION_UPDATE) {
	        logger.info("*********** vt020002_03 update dish start***********");
	        resp = vt020002Service.updateDish(dish, userName, roleList, image);
	        logger.info("*********** vt020002_03 update dish end***********");

	      } else if (param.getAction() == Constant.REQUEST_ACTION_DELETE) {
	        logger.info("*********** vt020002_03 delete dish start***********");
	        resp = vt020002Service.deleteDish(dish, userName, roleList);
	        logger.info("*********** vt020002_03 delete dish end***********");

	      } else {
	        resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
	      }
			}
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("data", resp.getData());

		logger.info("*********** vt020002_03 setting dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Select list dish from DISH_SETTING by KITCHEN_ID, DISH_NAME, limit record by
	 * pageNumber and page size
	 * 
	 * @param VT020002DishOffset
	 * @return ResponseEntityBase(status, List<VT020002Kitchen>)
	 */
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020002FindKitchenInfoOfThisChef(@RequestBody BaseEntity info, Principal principal) {
		logger.info("*********** vt020002_02 get list dish start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String userName = (String) oauth.getPrincipal();
	      resp = vt020002Service.findKitchenInfoOfThisChef(userName);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020002_02 get list dish end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}

	/**
	 * Select all kitchen from KITCHEN_SETTING
	 * 
	 * @param info
	 * @return ResponseEntityBase(status, List<VT020002Kitchen>)
	 */
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ResponseEntityBase> vt020002FindAllKitchen(@RequestBody VT020002InfoToFindKitchen info) {
		logger.info("*********** vt020002_05 get all kitchen start***********");
		ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		try {
		  if(isValidated(configProperty, info.getSecurityUsername(), info.getSecurityPassword())) {
		    resp = vt020002Service.findAllKitchen(info);
		  }
		} catch (Exception e) {
			resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		}
		logger.info("*********** vt020002_05 get all kitchen end***********");
		return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
	}
	
  @PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_Bep_truong')")
  @RequestMapping(value = "/07", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> vt020002SettingDish(@RequestParam String param, Principal principal) {
    logger.info("*********** vt020002_06 setting dish start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, param);
    
    Gson gson = new Gson();
    DishForMobile dish = gson.fromJson(param, DishForMobile.class);
    
    
    byte[] decodedBytes = null;
    if(dish.getImage() != null) {
      dish.getImage().replaceAll("\r\n", "");
      decodedBytes = Base64.getDecoder().decode(dish.getImage());
    }
    try {
    	ObjectMapper mapper = new ObjectMapper();
    	System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dish));
      if(isValidated(configProperty, dish.getSecurityUsername(), dish.getSecurityPassword())) {
        // get userName
        OAuth2Authentication oauth = (OAuth2Authentication) principal;
        String userName = (String) oauth.getPrincipal();
        Collection<GrantedAuthority> roleList = oauth.getAuthorities();
        if (dish.getAction() == Constant.REQUEST_ACTION_INSERT) {
          logger.info("*********** vt020002_03 insert dish start***********");
          resp = vt020002Service.insertDish(dish.getData(), userName, roleList, null);
          if(resp.getStatus() == 1) vt020002Service.saveImage(decodedBytes, (VT020002Dish)resp.getData());
          logger.info("*********** vt020002_03 insert dish end***********");
        } else if (dish.getAction() == Constant.REQUEST_ACTION_UPDATE) {
          logger.info("*********** vt020002_03 update dish start***********");
          resp = vt020002Service.updateDish(dish.getData(), userName, roleList, null);
          if(resp.getStatus() == 1) vt020002Service.saveImage(decodedBytes, dish.getData());
          logger.info("*********** vt020002_03 update dish end***********");
        } else if (dish.getAction() == Constant.REQUEST_ACTION_DELETE) {
          logger.info("*********** vt020002_03 delete dish start***********");
          resp = vt020002Service.deleteDish(dish.getData(), userName, roleList);
          logger.info("*********** vt020002_03 delete dish end***********");
        } else {
          resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
        }
      }
    } catch (Exception e) {
      resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }
    logger.info("*********** vt020002_06 setting dish end***********");
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

	private class VT020002DishWrapper {
		private Integer action;
		private VT020002Dish data;

		public Integer getAction() {
			return action;
		}

		@SuppressWarnings("unused")
		public void setAction(Integer action) {
			this.action = action;
		}

		public VT020002Dish getData() {
			return data;
		}

		@SuppressWarnings("unused")
		public void setData(VT020002Dish data) {
			this.data = data;
		}
	}
}
