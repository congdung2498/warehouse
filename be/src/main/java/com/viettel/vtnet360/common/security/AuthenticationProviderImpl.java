package com.viettel.vtnet360.common.security;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;

import viettel.passport.client.RoleToken;
import viettel.passport.client.UserToken;
import viettel.passport.service.SSOServiceUtils;

/**
 * AuthenticationProviderImpl is implement from AuthenticationProvider in order
 * to integrate VSA login system in to Spring Security
 *
 * @author VinhNVQ 9/12/2018
 *
 */
@Component
@SuppressWarnings("unchecked")
public class AuthenticationProviderImpl implements AuthenticationProvider {

	/** Database */
	@Autowired
	DataSource dataSource;

	/** Logger */
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Custom authentication that will validate user of VeBinh
	 * and other user will go thought VSA/Passport integration
	 * @author VinhNVQ
	 * @since 20/9/2018
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {

		/* Name of authentication */
		String name = authentication.getName();

		/* User name of user is request authentication */
		String username = String.valueOf(authentication.getPrincipal());

		/* Password of user is request authentication */
		String password = String.valueOf(authentication.getCredentials());

		/* Device ID of user is request authentication */
		String deviceID = null;

		/* Device Type of user is request authentication */
		String deviceType = null;

		try {
			deviceID = ((LinkedHashMap<String, String>) authentication.getDetails()).get("device_token");
			deviceType = ((LinkedHashMap<String, String>) authentication.getDetails()).get("device_type");
		} catch (Exception e) {
			logger.info("No device ID proviced");
		}

		logger.info("Validating deviceID: " + deviceID);
		logger.info("Validating Username: " + username);

		if ("canhve".equals(username)) {

			/* Declaration and Initialization Connection variable */
			Connection con = null;

			/* Declaration and Initialization PreparedStatement variable */
			PreparedStatement preparedStatement = null;

			/* Declaration and Initialization ResultSet variable */
			ResultSet resultSet = null;

			/* Declaration and Initialization List Role of user */
			Collection<GrantedAuthority> listAuthority = null;

			try {
				listAuthority = getListAuthorityOfGuard(username);
				/*
				 * Find password of User from Database
				 * then compare is that user validated
				 */
				con = dataSource.getConnection();
				preparedStatement = con.prepareStatement("SELECT * FROM M_SYSTEM_CODE WHERE MASTER_CLASS = ? AND CODE_VALUE = 01");
				preparedStatement.setString(1, "S010");
				resultSet = preparedStatement.executeQuery();
				resultSet.next();

				if(!resultSet.getString("CODE_NAME").equals(password)) {
					throw new AuthenticationCredentialsNotFoundException("Password is wrong");
				}
				logger.info("Validated User: " + username);
			} catch (Exception e) {
				throw new AuthenticationCredentialsNotFoundException("User Not Found");
			} finally {
				if (resultSet != null) {
					try {
						resultSet.close();
					} catch (SQLException e) {
						/* ignored */}
				}
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

			/*
			 * Invalidate user before login
			 * Invalidate device ID and add new one
			 */
			deleteOldSession(username, getClientId(), deviceID);

			/* Add deviceID for notification push */
			if (deviceID != null && !deviceID.isEmpty() && deviceType != null && !deviceType.isEmpty()) {
				addDeviceID(username, deviceID, deviceType);

				/* re-try send notify in queue */
				//findSendSmsNotify(username, deviceID, deviceType);
			}
			return new UsernamePasswordAuthenticationToken(username, password, listAuthority);
		}

		/* For testing purpose, help login test account without VSA, remove it in Product build ----- START HERE ----- */
		// TODO: Remove in product Build
		if (username.matches(".+_test$")) {

			/* Declaration and Initialization Connection variable */
			Connection con = null;

			/* Declaration and Initialization PreparedStatement variable */
			PreparedStatement preparedStatement = null;

			/* Declaration and Initialization ResultSet variable */
			ResultSet resultSet = null;

			/* Declaration and Initialization List Role of user */
			Collection<GrantedAuthority> listAuthority = null;

			try {
				/*
				 * Find password of User from Database
				 * then compare is that user validated
				 */
				con = dataSource.getConnection();
				preparedStatement = con.prepareStatement("SELECT * FROM M_SYSTEM_CODE WHERE MASTER_CLASS = ? AND CODE_VALUE = 02");
				preparedStatement.setString(1, "S010");
				resultSet = preparedStatement.executeQuery();
				resultSet.next();

				if(!resultSet.getString("CODE_NAME").equals(password)) {
					throw new AuthenticationCredentialsNotFoundException("Password is wrong");
				}
				/* get list role from database */
				listAuthority = getListAuthorityOfGuard(username);
				logger.info("Validated User: " + username);
			} catch (Exception e) {
				throw new AuthenticationCredentialsNotFoundException("User Not Found");
			} finally {
				if (resultSet != null) {
					try {
						resultSet.close();
					} catch (SQLException e) {
						/* ignored */}
				}
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

			logger.info("********************** Validated User: " + username + " **********************");

			/* Invalidate user before login */
			/* Invalidate device ID and add new one */
			deleteOldSession(username, getClientId(), deviceID);

			/* Add deviceID for notification push */
			if (deviceID != null && !deviceID.isEmpty() && deviceType != null && !deviceType.isEmpty()) {
				addDeviceID(username, deviceID, deviceType);

				/* re-try send notify in queue */
				//findSendSmsNotify(username, deviceID, deviceType);
			}
			return new UsernamePasswordAuthenticationToken(username, password, listAuthority);
		}
		/* for testing purpose, remove it in Product build ----- END HERE ----- */

		/* VSA Integration */
		try {
			logger.info("start" );
			/* Declaration and Initialization VSAValidate */
			UserToken userToken = SSOServiceUtils.validate(username, password);
			/*VSAValidate vsaValidate = new VSAValidate();
		

			 VSA Set user name 
			vsaValidate.setUser(username);

			 VSA Set password 
			vsaValidate.setPassword(password);

			 VSA validate user name and password thought VSA 
			vsaValidate.validate();*/

			/* Work with userToken if you Authentication is success */
		//	if (vsaValidate.isAuthenticationSuccesful()) {

				logger.info("Validated User: " + name);

				/* Get UserToken from VSA */
				//UserToken ut = vsaValidate.getUserToken();

				/* Declaration and Initialization Role List */
				ArrayList<RoleToken> roleList = userToken.getRolesList();

				/* Declaration and Initialization List Role of user */
				ArrayList<GrantedAuthority> grantedAuthorityImpl = new ArrayList<>();
				
				/* Set user name */
				username = userToken.getUserName();
				//logger.info("s "+ new Gson().toJson(userToken));
				/* Convert RoleToken List into GrantedAuthority for Spring integration purpose */
				for (RoleToken roleToken : roleList) {
					grantedAuthorityImpl.add(new SimpleGrantedAuthority(roleToken.getRoleName()));
				}

				/* Invalidate user before login */
				/* Invalidate device ID and add new one */
				deleteOldSession(username, getClientId(), deviceID);

				/* Add deviceID for notification push */
				if (deviceID != null && !deviceID.isEmpty() && deviceType != null && !deviceType.isEmpty()) {
					addDeviceID(username, deviceID, deviceType);

					/* re-try send notify in queue */
					//findSendSmsNotify(username, deviceID, deviceType);
				}

				return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorityImpl);
		/*	} else {
				logger.info("Validating User: " + name + " is failed");
				throw new AuthenticationCredentialsNotFoundException("User Not Found");
			}*/
	
		} catch (Exception e) {
			logger.error("Validating User:" + name + " error cause VSA/Passport have unexpected behavior", e);
			throw new AuthenticationCredentialsNotFoundException("User Not Found");
		}
	}

	public void deleteAllSession(){
		/* Declaration and Initialization Connection variable */
		Connection con = null;
		
		/* Declaration and Initialization PreparedStatement variable */
		PreparedStatement preparedStatement = null;

		try {
			con = dataSource.getConnection();
			preparedStatement = con.prepareStatement(
					"DELETE a,r,d FROM oauth_access_token as a LEFT OUTER JOIN oauth_refresh_token as r ON a.refresh_token = r.token_id "
							+ "LEFT OUTER JOIN NOTI_REG as d ON a.user_name = d.USERNAME ");
			preparedStatement.executeUpdate();
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
	
	private Collection<GrantedAuthority> getListAuthorityOfGuard(String username) {
		
		/* Declaration and Initialization Connection variable */
		Connection con = null;
		
		/* Declaration and Initialization PreparedStatement variable */
		PreparedStatement preparedStatement = null;
		
		/* Declaration and Initialization ResultSet variable */
		ResultSet resultSet = null;
		
		/* Declaration and Initialization List Role of user */
		Collection<GrantedAuthority> listAuthority = null;

		try {
			/* Get User Information from Database */
			con = dataSource.getConnection();
			preparedStatement = con.prepareStatement("SELECT * FROM QLDV_EMPLOYEE WHERE USER_NAME = ? AND STATUS = 1");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			
			/* 
			 * Declaration and Initialization List Role of User
			 * and fill it with role from database
			 */
			listAuthority = new ArrayList<>();
			String[] roles = resultSet.getString("ROLE").split(",");
			ArrayList<RoleToken> rolesList = new ArrayList<>();
			for (String role : roles) {
				RoleToken roleToken = new RoleToken();
				roleToken.setRoleCode(role);
				rolesList.add(roleToken);
				listAuthority.add(new SimpleGrantedAuthority(role));
			}
		} catch (Exception e) {
			throw new AuthenticationCredentialsNotFoundException("User Not Found");
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					/* ignored */}
			}
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
		return listAuthority;
	}

	/**
	 * Add DeviceID or Device Token and Device Type of User to Database
	 *
	 * @author VinhNVQ
	 * @since 13-10-2018
	 * @param username is UserName
	 * @param deviceID is DeviceID or DeviceToken
	 * @param deviceType is Device Type
	 */
	private void addDeviceID(String username, String deviceID, String deviceType) {
		
		/* Declaration and Initialization Connection variable */
		Connection con = null;
		
		/* Declaration and Initialization PreparedStatement variable */
		PreparedStatement preparedStatement = null;

		try {
			con = dataSource.getConnection();
			preparedStatement = con.prepareStatement("INSERT INTO NOTI_REG "
					+ "(USERNAME, DEVICE_ID, DEVICE_TYPE, CREATE_USER, CREATE_DATE, UPDATE_USER, UPDATE_DATE) "
					+ "VALUES(?, ?, ?, ?, NOW(), ?, NOW())");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, deviceID);
			preparedStatement.setString(3, deviceType);
			preparedStatement.setString(4, username);
			preparedStatement.setString(5, username);
			preparedStatement.executeUpdate();
			logger.info("Add new DeviceID of User: " + username);
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

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	/**
	 *
	 * Delete Old session base on UserName and ClientID
	 *
	 * @author VinhNVQ
	 * @since 13-10-2018
	 * @param username
	 * @param client
	 * @param deviceToken
	 */
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

	/**
	 * Get client token from request
	 *
	 * @author VinhNVQ
	 * @since 13-10-2018
	 * @return String is client token, String is empty if client token not found
	 */
	private String getClientId() {

//		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//				.getRequest();
//
//		final String authorizationHeaderValue = request.getHeader("Authorization");
//
//		final String base64AuthorizationHeader = Optional.ofNullable(authorizationHeaderValue)
//				.map(headerValue -> headerValue.substring("Basic ".length())).orElse("");
//
//		if (!StringUtils.isEmpty(base64AuthorizationHeader)) {
//
//			String decodedAuthorizationHeader = new String(Base64.getDecoder().decode(base64AuthorizationHeader),
//					Charset.forName("UTF-8"));
//
//			return decodedAuthorizationHeader.split(":")[0];
//		}

		return "";
	}

	public void findSendSmsNotify(String toUserName, String findTokenDevice, String deviceType) {

		Thread notiThread = new Thread() {
			public void run() {
				try {

					Thread.sleep(10000);

					try {

						List<Map<String, Object>> listNoti = findNotifyWaitSend(toUserName);
						for (Map<String, Object> noti : listNoti) {
							try {
								if (Constant.ANDROID_DEVICE.equals(deviceType)) {


									logger.info("**************** DEVICE: "+ deviceType + "*************************");
									String bodyRequest = Notification.getAndroidMessage(findTokenDevice,
											(String) noti.get("addInfo"), (String) noti.get("message"),
											(String) noti.get("title"), (int) noti.get("type"),
											(int) noti.get("statusRecord"), toUserName);

									logger.info("********SEND NOTIFY TO USERNAME:"+ toUserName);
									Notification.sendPost(bodyRequest);

									int statusSend = Constant.FLAG_SEND_SMS_NOTIFY_SUCCESS;
									updateStorageNotification((String) noti.get("notiLogId"), findTokenDevice, statusSend);

								} else if(Constant.APPLE_DEVICE.equals(deviceType)) {

									logger.info("**************** DEVICE: "+ deviceType + "*************************");

									String bodyRequest = Notification.getAppleMessage(findTokenDevice,
											(String) noti.get("addInfo"), (String) noti.get("message"),
											(String) noti.get("title"), (int) noti.get("type"),
											(int) noti.get("statusRecord"), toUserName);

									logger.info("********SEND NOTIFY TO USERNAME:"+ toUserName);
									Notification.sendPost(bodyRequest);

									int statusSend = Constant.FLAG_SEND_SMS_NOTIFY_SUCCESS;
									updateStorageNotification((String) noti.get("notiLogId"), findTokenDevice, statusSend);
								}
							} catch (Exception e) {
								logger.error(e.getMessage(),e);
								logger.info("****************************EROOR SEND NOTIFYCATION NOTI_LOG_ID:"
										+ (String) noti.get("notiLogId") + "**********************");

							}
						}

					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						logger.info("****************************CAN't FIND LOG_NOTIFY OR DEVICE TOKEN********:");
					}

				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************THREAD ERROR********:");

				}
			}

		};

		notiThread.start();




	}

	/**
	 * This method search database for list of notification that didn't send earlier.
	 *
	 * @author VinhNVQ
	 * @since 13-10-2018
	 * @param username UserName of user that notification belong to.
	 * @return List<Map<String, Object>> this return List of Map that each Map contain information of one notify.
	 */
	private List<Map<String, Object>> findNotifyWaitSend(String username) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Map<String, Object>> output = new ArrayList<>();
		try {
			con = dataSource.getConnection();
			preparedStatement = con.prepareStatement(
					"SELECT\r\n" +
							"	NOTI_LOG_ID as notiLogId,\r\n" +
							"	USERNAME as userName,\r\n" +
							"	DEVICE_ID as deviceId,\r\n" +
							"	TITLE as title,\r\n" +
							"	MESSAGE as message,\r\n" +
							"	TYPE as type,\r\n" +
							"	ADDITIONAL_INFOMATION as addInfo,\r\n" +
							"	STATUS as status,\r\n" +
							"	CREATE_USER as createUser,\r\n" +
							"	CREATE_DATE as createDate,\r\n" +
							"	STATUS_RECORD as statusRecord\r\n" +
							"	FROM\r\n" +
							"	NOTI_LOG\r\n" +
							"	WHERE\r\n" +
							"	USERNAME = ? AND STATUS = 0");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Map<String, Object> temp = new HashMap<>();
				temp.put("notiLogId", resultSet.getString("notiLogId"));
				temp.put("userName", resultSet.getString("userName"));
				temp.put("deviceId", resultSet.getString("deviceId"));
				temp.put("title", resultSet.getString("title"));
				temp.put("message", resultSet.getString("message"));
				temp.put("type", resultSet.getInt("type"));
				temp.put("addInfo", resultSet.getString("addInfo"));
				temp.put("status", resultSet.getInt("status"));
				temp.put("createUser", resultSet.getString("createUser"));
				temp.put("createDate", resultSet.getDate("createDate"));
				temp.put("statusRecord", resultSet.getInt("statusRecord"));
				output.add(temp);
			}
			logger.info("********************** Retrive all old notify of User: " + username + " **********************");
			return output;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					/* ignored */}
			}
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
		return new ArrayList<>();
	}

	/**
	 * @author VinhNVQ
	 * @since 14/10/2018
	 * @param notifyInLogID is ID of notify record in NOTI_LOG table
	 * @param deviceID is DeviceID or Device Token that identify one mobile application installation that we will update for record we found
	 * @param status is status that we will update for record we found
	 */
	private void updateStorageNotification(String notifyInLogID, String deviceID, int status) {

		/* Declaration and Initialization Connection variable */
		Connection con = null;
		
		/* Declaration and Initialization PreparedStatement variable */
		PreparedStatement preparedStatement = null;

		try {
			con = dataSource.getConnection();
			preparedStatement = con.prepareStatement(
					"UPDATE\r\n" +
							"	NOTI_LOG \r\n" +
							"	SET\r\n" +
							"		DEVICE_ID = ?,\r\n" +
							"		STATUS = ?\r\n" +
							"	WHERE\r\n" +
							"		NOTI_LOG_ID = ?");
			preparedStatement.setString(1, deviceID);
			preparedStatement.setInt(2, status);
			preparedStatement.setString(3, notifyInLogID);
			preparedStatement.executeUpdate();
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
}
