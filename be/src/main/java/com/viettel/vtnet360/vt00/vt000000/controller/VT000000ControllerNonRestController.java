package com.viettel.vtnet360.vt00.vt000000.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityListUnit;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityPlace;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRpFindEmp;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRpSystemCode;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRqFindEmp;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRqSystemCode;
import com.viettel.vtnet360.vt00.vt000000.service.VT000000Service;

import viettel.passport.client.ObjectToken;
import viettel.passport.client.RoleToken;
import viettel.passport.client.ServiceTicketValidator;
import viettel.passport.client.UserToken;

@Controller
@RequestMapping("/com/viettel/vtnet360/vt00")
public class VT000000ControllerNonRestController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private VT000000Service vt000000Service;

	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private AuthorizationServerTokenServices tokenService;
	
//	@RequestMapping(value= "/vt000000/08", method = RequestMethod.GET)
	public OAuth2AccessToken LoginByPassport(@RequestParam Map<String, String> allParam, ModelMap model, HttpServletRequest request) {
		String passportLoginURL;
		String serviceURL;
		String domainCode;
		String passportValidateURL;
		String errorUrl;
		String[] allowedUrls;
		ResourceBundle rb;
		try {
			rb = ResourceBundle.getBundle("cas");
			passportLoginURL = rb.getString("loginUrl");
			serviceURL = rb.getString("service");
			domainCode = rb.getString("domainCode");
			passportValidateURL = rb.getString("validateUrl");
			errorUrl = rb.getString("errorUrl");
			allowedUrls = rb.getString("AllowUrl").split(",");
			
			/** Log ticket */
			logger.info(allParam);
			logger.info(model);
			
			ServiceTicketValidator stValidator = new ServiceTicketValidator();
			stValidator.setCasValidateUrl(passportValidateURL);
			stValidator.setService(allParam.get("ticket"));
			stValidator.setService(serviceURL);
			stValidator.setDomainCode(domainCode);
			stValidator.validate();
			
			logger.info("Validated User " + stValidator.getUserToken().getUserName() + " and logging in");
			
			
			/** Get UserToken from Passport */
			UserToken ut = stValidator.getUserToken();

			/** Init Role List */
			ArrayList<RoleToken> roleList = ut.getRolesList();
			
			/** Init Role List */
			ArrayList<ObjectToken> functionPermissionList = ut.getObjectTokens();

			/** Init List GrantedAuthority */
			ArrayList<GrantedAuthority> grantedAuthorityImpl = new ArrayList<GrantedAuthority>();

			/**
			 * Convert RoleToken List into GrantedAuthority for Spring integration purpose
			 */
			for (RoleToken roleToken : roleList) {
				grantedAuthorityImpl.add(new SimpleGrantedAuthority(roleToken.getRoleName()));
			}
			
			for (ObjectToken functionPermission : functionPermissionList) {
				grantedAuthorityImpl.add(new SimpleGrantedAuthority(functionPermission.getObjectUrl()));
			}
			
			// Invalidate old session of user before login
			Collection<OAuth2AccessToken> auth2AccessTokens = tokenStore.findTokensByClientIdAndUserName("browser-client", ut.getUserName());
			for (OAuth2AccessToken oAuth2AccessToken : auth2AccessTokens) {
				tokenStore.removeAccessToken(oAuth2AccessToken);
			}
			
			HashedMap<String, String> authorizationParameters = new HashedMap<String, String>();
			authorizationParameters.put("scope", "read,write,trust");
			authorizationParameters.put("username", ut.getUserName());
			authorizationParameters.put("client_id", "browser-client");
			authorizationParameters.put("grant", "password");
			
			Set<String> reponseType = new HashSet<>();
			reponseType.add("password");
			
			Set<String> scopes = new HashSet<>();
			scopes.add("read");
			scopes.add("write");
			scopes.add("trust");
			
			OAuth2Request auth2Request = new OAuth2Request(authorizationParameters, "browser-client", grantedAuthorityImpl, true, scopes, null, "", reponseType, null);
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(ut.getUserName(), null, grantedAuthorityImpl);
			
			OAuth2Authentication auth2Authentication = new OAuth2Authentication(auth2Request, authenticationToken);
			auth2Authentication.setAuthenticated(true);
			
			OAuth2AccessToken accessToken = tokenService.createAccessToken(auth2Authentication);
			
			request.setAttribute("access_token", accessToken);
			
			/** Return data to web */
			return accessToken;
		} catch (MissingResourceException e) {
			logger.error(e.getMessage(), e);
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}