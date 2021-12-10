package com.viettel.vtnet360.common.security;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.viettel.vtnet360.vt00.common.AvatarSync;
import com.viettel.vtnet360.vt00.common.TokenAdditionInfo;

/** 
 * CustomTokenEnchancer is an implement from TokenEnchancer that 
 * add user detail data to token for Client/API usage 
 * 
 * @author VinhNVQ
 * @since 14/09/2018
 *
 */
public class CustomTokenEnhancer implements TokenEnhancer {

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/** Data source */
	@Autowired
	DataSource dataSource;

	
	/**
	 * enhance is an implement from TokenEnchancer that 
	 * add user detail data to token for Client/API usage 
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		/* Declaration and Initialization Connection variable */
		Connection con = null;
		
		/* Declaration and Initialization PreparedStatement variable */
		PreparedStatement preparedStatement = null;
		
		/* Declaration and Initialization ResultSet variable */
		ResultSet resultSet = null;
		
		/* Declaration and Initialization UserName variable */
		String userName = (String) authentication.getPrincipal();
		
		/* Declaration and Initialization additionalInfo variable */
		final Map<String, Object> additionalInfo = new HashMap<>();
		try {
			con = dataSource.getConnection();
			preparedStatement = con
					.prepareStatement("SELECT\r\n" + 
							"	QLDV_EMPLOYEE.FULL_NAME,\r\n" + 
							"	QLDV_EMPLOYEE.PHONE_NUMBER,\r\n" + 
							"	QLDV_EMPLOYEE.EMAIL,\r\n" + 
							"	QLDV_EMPLOYEE.UNIT_ID,\r\n" + 
							"	QLDV_EMPLOYEE.TITLE,\r\n" + 
							"	QLDV_EMPLOYEE.CODE,\r\n" + 
						  " QLDV_EMPLOYEE.USER_NAME," + 
							"	CASE \r\n" + 
							"		WHEN QLDV_UNIT.UNIT_ID = 234841 THEN IFNULL(QLDV_UNIT.UNIT_NAME, '')\r\n" + 
							"		WHEN b.UNIT_ID = 234841 THEN CONCAT(IFNULL(b.UNIT_NAME, ''), IFNULL(CONCAT(' / ', QLDV_UNIT.UNIT_NAME), ''))\r\n" + 
							"		WHEN c.UNIT_ID = 234841 THEN CONCAT(IFNULL(c.UNIT_NAME, ''), IFNULL(CONCAT(' / ', b.UNIT_NAME), ''), IFNULL(CONCAT(' / ', QLDV_UNIT.UNIT_NAME), ''))\r\n" + 
							"		ELSE CONCAT(IFNULL(d.UNIT_NAME, ''), IFNULL(CONCAT(' / ', c.UNIT_NAME), ''), IFNULL(CONCAT(' / ', b.UNIT_NAME), ''))\r\n" + 
							"	END AS UNIT_NAME" +
							"   FROM \r\n" + 
							"	QLDV_EMPLOYEE LEFT OUTER JOIN QLDV_UNIT ON\r\n" + 
							"					QLDV_UNIT.UNIT_ID = QLDV_EMPLOYEE.UNIT_ID\r\n" + 
							"				LEFT JOIN QLDV_UNIT b ON\r\n" + 
							"					QLDV_UNIT.PARENT_UNIT_ID = b.UNIT_ID\r\n" + 
							"				LEFT JOIN QLDV_UNIT c ON\r\n" + 
							"					b.PARENT_UNIT_ID = c.UNIT_ID\r\n" + 
							"				LEFT JOIN QLDV_UNIT d ON\r\n" + 
							"					c.PARENT_UNIT_ID = d.UNIT_ID" +
							" WHERE USER_NAME = ? or CODE = ? ");
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, userName);
			resultSet = preparedStatement.executeQuery();
			
			Collection<GrantedAuthority> roleList = authentication.getAuthorities();
      ArrayList<String> roleString = new ArrayList<>();
      MessageDigest md = MessageDigest.getInstance("MD5");
	  md.update(accessToken.getValue().getBytes());
      byte[] digest = md.digest();
      String myHash = DatatypeConverter
        .printHexBinary(digest).toUpperCase();
      for (GrantedAuthority role : roleList) {
    	  String roleEncode = Base64.encodeBase64String((myHash + role.getAuthority()).getBytes("UTF-8"));
          
          roleEncode = myHash.substring(6, 18) 
        		  + roleEncode.substring(0, 5)
        		  + myHash.substring(0, 12)
        		  + roleEncode.substring(5, roleEncode.length() - 2)
        		  + myHash.substring(17, 29) + roleEncode.substring(roleEncode.length() - 2);
          
          roleEncode = Base64.encodeBase64String((roleEncode.substring(0, roleEncode.length() - 3) 
        		  + myHash.substring(3, 5) 
        		  + roleEncode.substring(roleEncode.length() - 3)).getBytes());
          roleEncode = Base64.encodeBase64String((roleEncode.substring(0, roleEncode.length() - 3) 
        		  + myHash.substring(9, 14)
        		  + roleEncode.substring(roleEncode.length() - 3)).getBytes());
          roleEncode = Base64.encodeBase64String((roleEncode.substring(0, roleEncode.length() - 3)
        		  + myHash.substring(15, 27) 
        		  + roleEncode.substring(roleEncode.length() - 3)).getBytes());
          
          roleString.add(roleEncode);
      }
			while (resultSet.next()) {
			  TokenAdditionInfo userInfor = new TokenAdditionInfo(resultSet.getString("USER_NAME"), resultSet.getString("FULL_NAME"), resultSet.getString("PHONE_NUMBER"),
	          resultSet.getString("EMAIL"), resultSet.getLong("UNIT_ID") + "", resultSet.getString("TITLE"), resultSet.getString("CODE"),
	          resultSet.getString("UNIT_NAME"), roleString, AvatarSync.GetAvataByEmployeeID(resultSet.getString("CODE")));
	      additionalInfo.put("userInfor", userInfor);
      }
			
			DefaultOAuth2AccessToken accessToken2 = (DefaultOAuth2AccessToken) accessToken;
			accessToken2.setAdditionalInformation(additionalInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
		    if (resultSet != null) {
		        try {
		        	resultSet.close();
		        } catch (SQLException e) { logger.error(e.getMessage(), e);}
		    }
		    if (preparedStatement != null) {
		        try {
		        	preparedStatement.close();
		        } catch (SQLException e) { logger.error(e.getMessage(), e);}
		    }
		    if (con != null) {
		        try {
		        	con.close();
		        } catch (SQLException e) { logger.error(e.getMessage(), e);}
		    }
		}
		return accessToken;
	}

}