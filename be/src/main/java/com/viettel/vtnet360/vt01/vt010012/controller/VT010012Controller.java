package com.viettel.vtnet360.vt01.vt010012.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vtnet360.common.security.BaseController;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt01.vt010012.entity.VT010012ListAddCard;
import com.viettel.vtnet360.vt01.vt010012.entity.VT010012ListCondition;
import com.viettel.vtnet360.vt01.vt010012.entity.VT010012ListEmployee;
import com.viettel.vtnet360.vt01.vt010012.service.VT010012Service;

/**
 * Class VT010012Controller
 * 
 * @author THANGBT 09/08/2018
 * 
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt01/vt010012")
public class VT010012Controller extends BaseController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT010012Service vt010012Service;
	
	@Autowired
  private Properties configProperty;

	/**
	 * find List Employee
	 * 
	 * @param empInfo VT010012ListEmployee
	 * @return ResponseEntityBase
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/01", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListEmployee(@RequestBody VT010012ListEmployee empInfo) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		
		try {
		  if(isValidated(configProperty, empInfo.getSecurityUsername(), empInfo.getSecurityPassword())) {
		    reb = vt010012Service.findListEmployee(empInfo);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * find List Card
	 * 
	 * @param cardCondition VT010012ListCondition
	 * @return ResponseEntityBase
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/02", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListCard(@RequestBody VT010012ListCondition cardCondition) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
		  if(isValidated(configProperty, cardCondition.getSecurityUsername(), cardCondition.getSecurityPassword())) {
		    reb = vt010012Service.findListCard(cardCondition);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * find List Add Card
	 * 
	 * @param condition VT010012ListCondition
	 * @return ResponseEntityBase
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/03", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase findListAddCard(@RequestBody VT010012ListCondition condition, Principal principal) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = vt010012Service.findListAddCard(condition, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * count Total Record
	 * 
	 * @param condition VT010012ListCondition
	 * @return ResponseEntityBase
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase countTotalRecord(@RequestBody VT010012ListCondition condition, Principal principal) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = vt010012Service.countTotalRecord(condition, listRole);
		  }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	/**
	 * update Card Emp
	 * 
	 * @param condition VT010012ListCondition
	 * @return ResponseEntityBase
	 */
	@PreAuthorize("hasAuthority('PMQT_ADMIN') or hasAuthority('PMQT_HCVP')")
	@RequestMapping(value = "/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntityBase updateCardEmp(@RequestBody VT010012ListAddCard condition, Principal principal) throws Exception {
		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);

		try {
		  if(isValidated(configProperty, condition.getSecurityUsername(), condition.getSecurityPassword())) {
		    OAuth2Authentication oauth = (OAuth2Authentication) principal;
	      String loginUserName = (String) oauth.getPrincipal();
	      condition.setLoginUserName(loginUserName);
	      Collection<GrantedAuthority> listRole = oauth.getAuthorities();
	      reb = vt010012Service.updateCardEmp(condition, listRole);
		  }
		} catch (Exception e) {
			logger.error(reb);
		}

		return reb;
	}
}
