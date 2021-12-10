package com.viettel.vtnet360.vt00.vt000000.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.viettel.vtnet360.common.CASign.SecurityPassword;
import com.viettel.vtnet360.signVoffice.entity.SignVofficeEntity;
import com.viettel.vtnet360.signVoffice.request.dao.SignVofficeDao;
import com.viettel.vtnet360.signVoffice.service.SignVofficeService;
import com.viettel.vtnet360.vt00.common.AvatarSync;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.common.TokenAdditionInfo;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityListUnit;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityPlace;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityResultSign;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRpFindEmp;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRpSystemCode;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRqFindEmp;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRqSystemCode;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000SubmitVOfficeInput;
import com.viettel.vtnet360.vt00.vt000000.service.VOfficeAutoSignWebservice;
import com.viettel.vtnet360.vt00.vt000000.service.VT000000Service;
import com.viettel.vtnet360.vt06.vt060001.entity.VersionDetail;
import com.viettel.vtnet360.vt06.vt060001.service.VT060001Service;

import viettel.passport.client.ObjectToken;
import viettel.passport.client.RoleToken;
import viettel.passport.client.ServiceTicketValidator;
import viettel.passport.client.UserToken;
/**
 * Class controller VT000000
 * 
 * @author KienHT 10/08/2018
 * 
 */
@RestController
@RequestMapping("/com/viettel/vtnet360/vt00")
public class VT000000Controller {

  private final Logger logger = Logger.getLogger(this.getClass());

  @Autowired
  private VT000000Service vt000000Service;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private AuthorizationServerTokenServices tokenService;

  @Autowired
  private VT060001Service vt060001Service;

  @Autowired
  DataSource dataSource;

  @Autowired
  private SignVofficeService signVofficeService;
  
  @Autowired
  private SignVofficeDao signVofficeDao;

  private static final String OK = "OK";
  private static final String NOK = "NOK";
  /**
   * GetListSystemCode
   * 
   * @param requestParam VT000000EntityRqSystemCode
   * @return ResponseEntity<VT000000EntityRpSystemCode>
   */
  @RequestMapping(value = "/vt000000/01", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<VT000000EntityRpSystemCode> api000000_01(
      @RequestBody VT000000EntityRqSystemCode requestParam) {

    // set reponse default
    VT000000EntityRpSystemCode reponse = new VT000000EntityRpSystemCode();
    reponse.setStatus(Constant.RESPONSE_STATUS_ERROR);
    reponse.setData(null);
    try {

      // set reponse
      reponse = vt000000Service.findListSystemCode(requestParam);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    return new ResponseEntity<VT000000EntityRpSystemCode>(reponse, HttpStatus.OK);
  }

  /**
   * find Employee by parttern String
   * 
   * @param requestParam VT000000EntityRqFindEmp
   * @return ResponseEntity<VT000000EntityRpFindEmp>
   */
  @RequestMapping(value = "/vt000000/02", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<VT000000EntityRpFindEmp> api000000_02(@RequestBody VT000000EntityRqFindEmp requestParam) {
    VT000000EntityRpFindEmp reponse = new VT000000EntityRpFindEmp();
    reponse.setStatus(Constant.RESPONSE_STATUS_ERROR);
    reponse.setData(null);
    try {
      reponse = vt000000Service.findListEmployee(requestParam);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    return new ResponseEntity<VT000000EntityRpFindEmp>(reponse, HttpStatus.OK);
  }

  /**
   * get system date(milisecond)
   * 
   * @return ResponseEntityBase
   */
  @RequestMapping(value = "/vt000000/00", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ResponseEntityBase> vt000000FindSystemDate() {
    logger.info("*********** vt000000_00 find system date start***********");
    ResponseEntityBase resp = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
    Date sysDate = new Date();
    resp.setData(sysDate);
    logger.info("*********** vt000000_00 find system date end***********");
    return new ResponseEntity<ResponseEntityBase>(resp, HttpStatus.OK);
  }

  /**
   * 
   * @param VT000000EntityPlace obj
   * @return ResponseEntityBase
   * @throws Exception
   * @author CuongHD
   */
  @RequestMapping(value = "/vt000000/04", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
  public ResponseEntityBase findPlace(@RequestBody VT000000EntityPlace obj) throws Exception {
    ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    try {
      response = vt000000Service.findPlace(obj);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return response;
  }

  /**
   * -- CREATED BY ThangBT -- get list of unit
   * 
   * @param unitInfo
   * @return List<vt010011ListUnit>
   * @throws Exception
   */
  @RequestMapping(value = "/vt000000/05", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntityBase findListUnit(@RequestBody VT000000EntityListUnit unitInfo) throws Exception {

    ResponseEntityBase reb = null;

    try {
      reb = vt000000Service.findListUnit(unitInfo);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    return reb;
  }

  @RequestMapping(value = "/vt000000/06", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntityBase logout(Principal principal) throws Exception {

    /** Initialization OAuth2Authentication object */
    OAuth2Authentication oauth = (OAuth2Authentication) principal;

    /** Get userName */
    String userName = (String) oauth.getPrincipal();

    /** Get client */
    String client = (String) oauth.getOAuth2Request().getClientId();
    /** Device ID of user is request authentication */
    //String deviceID = null;
    //deviceID = ((LinkedHashMap<String, String>) oauth.getDetails()).get("device_token");
    try {
      vt000000Service.invalidateDeviceToken(userName);
      vt000000Service.invalidateToken(userName, client);
      //deleteOldSession(userName,client,deviceID);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
    }

    return new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, null);
  }

  public void deleteOldSession(String username, String client, String deviceToken) {

    /* Declaration and Initialization Connection variable */
    Connection con = null;

    /* Declaration and Initialization PreparedStatement variable */
    PreparedStatement preparedStatement = null;

    try {
      con = dataSource.getConnection();
      preparedStatement = con.prepareStatement(
              "DELETE a,r,d FROM oauth_access_token as a LEFT OUTER JOIN oauth_refresh_token as r ON a.refresh_token = r.token_id "
                      + "LEFT OUTER JOIN NOTI_REG as d ON a.user_name = d.USERNAME WHERE (a.user_name = ? AND a.client_id = ?)");
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, client);
      preparedStatement.executeUpdate();
      logger.info("Invoked Old Session of User: " + username);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    } finally {
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          /* ignored */}
      }
      if (con != null) {
        try {
          con.close();
        } catch (SQLException e) {
          /* ignored */}
      }
    }

    /* Initialization Connection variable */
    con = null;

    /* Initialization PreparedStatement variable */
    preparedStatement = null;

    try {
      con = dataSource.getConnection();
      preparedStatement = con.prepareStatement("DELETE a,r,d FROM oauth_access_token as a LEFT OUTER JOIN oauth_refresh_token as r "
              + "ON a.refresh_token = r.token_id LEFT OUTER JOIN NOTI_REG as d ON a.user_name = d.USERNAME WHERE (d.DEVICE_ID = ?)");
      preparedStatement.setString(1, deviceToken);
      preparedStatement.executeUpdate();
      logger.info("Invoked Old User used this device by this DeviceID: " + deviceToken);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    } finally {
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          /* ignored */}
      }
      if (con != null) {
        try {
          con.close();
        } catch (SQLException e) {
          /* ignored */}
      }
    }
  }

  @RequestMapping(value = "/vt000000/07", method = RequestMethod.GET)
  public ResponseEntityBase findListUnit(@RequestParam Map<String, String> query) throws Exception {
    return new ResponseEntityBase(1, AvatarSync.GetAvataByEmployeeID(query.get("EmployeeCode")));
  }

  @RequestMapping(value = "/vt000000/08", method = RequestMethod.GET)
  public OAuth2AccessToken LoginByPassport(@RequestParam Map<String, String> allParam, ModelMap model,
      HttpServletRequest request) {
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
      stValidator.setServiceTicket(allParam.get("ticket"));
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
      ArrayList<GrantedAuthority> grantedAuthorityImpl = new ArrayList<>();

      /**
       * Convert RoleToken List into GrantedAuthority for Spring integration purpose
       */
      for (RoleToken roleToken : roleList) {
        grantedAuthorityImpl.add(new SimpleGrantedAuthority(roleToken.getRoleName()));
      }

      addFunctionToList(grantedAuthorityImpl, functionPermissionList);

      // Invalidate old session of user before login
      Collection<OAuth2AccessToken> auth2AccessTokens = tokenStore
          .findTokensByClientIdAndUserName("browser-client", ut.getUserName());
      for (OAuth2AccessToken oAuth2AccessToken : auth2AccessTokens) {
        tokenStore.removeAccessToken(oAuth2AccessToken);
      }

      HashedMap<String, String> authorizationParameters = new HashedMap<>();
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

      OAuth2Request auth2Request = new OAuth2Request(authorizationParameters, "browser-client",
          grantedAuthorityImpl, true, scopes, null, "", reponseType, null);

      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          ut.getUserName(), null, grantedAuthorityImpl);

      OAuth2Authentication auth2Authentication = new OAuth2Authentication(auth2Request, authenticationToken);
      auth2Authentication.setAuthenticated(true);

      OAuth2AccessToken accessToken = tokenService.createAccessToken(auth2Authentication);
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

  private void addFunctionToList(ArrayList<GrantedAuthority> grantedAuthorityImpl, ArrayList<ObjectToken> functionPermissionList) {
    for (ObjectToken functionPermission : functionPermissionList) {
      grantedAuthorityImpl.add(new SimpleGrantedAuthority(functionPermission.getObjectName()));
      addFunctionToList(grantedAuthorityImpl, functionPermission.getChildObjects());
    }
  }

  @RequestMapping(value = "/vt000000/09", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntityBase changeGuardPassword(@RequestBody Map<String, String> param) throws Exception {

    ResponseEntityBase reb = new ResponseEntityBase(0, null);

    try {
      if(vt000000Service.changePassword(param.get("oldPassword"), param.get("newPassword"))) {
        reb.setStatus(1);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return reb;
  }

  @RequestMapping(value = "/vt000000/10", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntityBase updateDeviceToken(@RequestBody Map<String, String> param, Principal principal) throws Exception {

    ResponseEntityBase reb = new ResponseEntityBase(0, null);

    logger.info("*********** vt000000_10 update device token START ***********");

    try {
      if(vt000000Service.updateDeviceToken(param.get("device_token"), param.get("device_type"), (String) (((OAuth2Authentication) principal).getPrincipal()), ((OAuth2Authentication) principal).getOAuth2Request().getClientId())) {
        reb.setStatus(1);
        logger.info("*********** vt000000_10 update device token END - OK ***********");
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      logger.info("*********** vt000000_10 update device token END - ERROR ***********");
    }
    return reb;
  }

  @RequestMapping(value = "/vt000000/11", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntityBase getAppLink(@RequestBody Map<String, String> param, Principal principal) throws Exception {

    ResponseEntityBase reb = new ResponseEntityBase(0, null);

    logger.info("*********** vt000000_11 getLinkAPP START ***********");

    try {
      Map<String, Object> output = new HashMap<>();
      VersionDetail versionDetail = vt060001Service.getVersionDetail(param.get("device_type"));
      output.put("version", versionDetail.getVersion());
      output.put("linkApp", versionDetail.getVersionLink());
      reb.setData(output);
      reb.setStatus(Constant.SUCCESS);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      logger.info("*********** vt000000_11 getLinkAPP END - ERROR ***********");
      reb.setStatus(0);
    }
    return reb;
  }

  @RequestMapping(value = "/vt000000/12", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntityBase updateUserVersionForMonitor(@RequestBody Map<String, String> param, Principal principal) throws Exception {

    /** Initialization OAuth2Authentication object */
    OAuth2Authentication oauth = (OAuth2Authentication) principal;

    /** Get userName */
    String userName = (String) oauth.getPrincipal();

    ResponseEntityBase reb = new ResponseEntityBase(0, null);

    logger.info("*********** vt000000_12 updateUserVersionForMonitor START ***********");

    try {
      vt060001Service.setUserVersionDetail(param.get("device_type"), param.get("version"), userName);
      reb.setStatus(1);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      logger.info("*********** vt000000_12 updateUserVersionForMonitor END - ERROR ***********");
      reb.setStatus(0);
    }
    logger.info("*********** vt000000_12 updateUserVersionForMonitor END ***********");
    return reb;
  }

  /**
   * GetListSystemCode
   * 
   * @param requestParam VT000000EntityRqSystemCode
   * @return ResponseEntity<VT000000EntityRpSystemCode>
   */
  @RequestMapping(value = "/vt000000/15", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<VT000000EntityResultSign> api000000_01( @RequestBody VT000000SubmitVOfficeInput requestParam,
      Principal principal) {

    VT000000EntityResultSign request = new VT000000EntityResultSign();
    /** Initialization OAuth2Authentication object */
    OAuth2Authentication oauth = (OAuth2Authentication) principal;
    //** Get userName *//*
    String userName = (String) oauth.getPrincipal();
    try {
      List<SignVofficeEntity> details = signVofficeDao.findIssueServiceBySignVofficeId(requestParam.getSignVofficeId());
      VT000000EntityResultSign result = new VOfficeAutoSignWebservice().onSubmitVOffice(principal, requestParam, details);
      if (result != null && result.getResult() != null && result.getResult()==1l) {
        SignVofficeEntity signVofficeEntity= new SignVofficeEntity();
        signVofficeEntity.setSignVofficeId(requestParam.getSignVofficeId());
        signVofficeEntity.setStatus(2);
        signVofficeEntity.setControlId(result.getControlId());
        signVofficeEntity.setUserNameVoffice(requestParam.getUsername());
        signVofficeEntity.setPasswordVoffice(SecurityPassword.encryptPassword(requestParam.getPassword()));
        signVofficeService.updateSignVoffice(signVofficeEntity);
        request= result;
        request.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        System.out.println("api000000_01: " + new Gson().toJson(signVofficeEntity));
        logger.info("signVofficeEntity: " + new Gson().toJson(signVofficeEntity));
      }
      request= result;
      ObjectMapper mapper = new ObjectMapper();
      logger.info("Param to VO: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));
      logger.info("Result from VO: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }

    return new ResponseEntity<VT000000EntityResultSign>(request, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/vt000000/16", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntityBase getUserInfo(@RequestBody Map<String, String> param, OAuth2Authentication authen2) throws Exception {

    /** Get userName */
    Map<String, Object> token = (Map<String, Object>) tokenService.getAccessToken(authen2).getAdditionalInformation();
    
    TokenAdditionInfo tokenInfo = (TokenAdditionInfo) token.get("userInfor");

    ResponseEntityBase reb = new ResponseEntityBase(0, tokenInfo);

    return reb;
  }
}