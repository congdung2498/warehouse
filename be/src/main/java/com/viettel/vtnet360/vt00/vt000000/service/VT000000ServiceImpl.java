package com.viettel.vtnet360.vt00.vt000000.service;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityDataFindEmployee;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityDataSystemCode;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityListUnit;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityPlace;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRpFindEmp;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRpSystemCode;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRqFindEmp;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityRqSystemCode;
import com.viettel.vtnet360.vt00.common.Constant;
import com.viettel.vtnet360.vt00.common.Notification;
import com.viettel.vtnet360.vt00.common.ResponseEntityBase;
import com.viettel.vtnet360.vt00.vt000000.dao.VT000000DAO;

/**
 * Class @Service for common all sceen
 * 
 * @author KienHT 10/08/2018
 * 
 */
@Service
public class VT000000ServiceImpl implements VT000000Service {

	@Autowired
	VT000000DAO vt000000Dao;

	@Autowired
	DataSource dataSource;
	
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * find list SystemCode
	 * 
	 * @param VT000000EntityRequestParamGetSystemCode
	 * @return VT000000EntityReponseGetListSystemCode
	 */
	@Override
	@Transactional
	public VT000000EntityRpSystemCode findListSystemCode(VT000000EntityRqSystemCode requestParam) throws Exception {
		VT000000EntityRpSystemCode reponse = new VT000000EntityRpSystemCode();
		reponse.setStatus(Constant.RESPONSE_STATUS_ERROR);
		reponse.setData(null);
		try {
			List<VT000000EntityDataSystemCode> data = vt000000Dao.findListSystemCode(requestParam);
			reponse.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			reponse.setData(data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return reponse;
	}

	/**
	 * find List Employee
	 * 
	 * @param VT000000EntityRequestParamFindEmployee
	 * @return VT000000EntityReponseFindEmployee
	 */
	@Override
	@Transactional
	public VT000000EntityRpFindEmp findListEmployee(VT000000EntityRqFindEmp requestParam) throws Exception {
		VT000000EntityRpFindEmp reponse = new VT000000EntityRpFindEmp();
		reponse.setStatus(Constant.RESPONSE_STATUS_ERROR);
		reponse.setData(null);
		try {

			requestParam.setFromIndex(requestParam.getPageNumber() * requestParam.getPageSize());
			requestParam.setGetSize(requestParam.getPageSize());

			List<VT000000EntityDataFindEmployee> data = vt000000Dao.findListEmployee(requestParam);
			reponse.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			reponse.setData(data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return reponse;
	}

	/**
	 * Find Place
	 * 
	 * @param VT000000EntityPlace
	 * @return ResponseEntityBase
	 * @throws Exception
	 * @author CuongHD
	 */
	@Override
	@Transactional
	public ResponseEntityBase findPlace(VT000000EntityPlace obj) throws Exception {
		logger.info("********************* START EXECUTE APIVT000000_04 ***********************");
		ResponseEntityBase response = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT000000EntityPlace> data = new ArrayList<>();
		try {
			obj.setFromIndex(obj.getPageNumber() * obj.getPageSize());
			obj.setGetSize(obj.getPageSize());
			
			data = vt000000Dao.findPlace(obj);
			response = new ResponseEntityBase(Constant.RESPONSE_STATUS_SUCCESS, data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		logger.info("********************* START EXECUTE APIVT000000_04 ***********************");
		return response;
	}

	/**
	 * Find List Unit
	 * 
	 * @param VT000000EntityPlace
	 * @return ResponseEntityBase
	 * @throws Exception
	 * @author CuongHD
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntityBase findListUnit(VT000000EntityListUnit unitInfo) throws Exception {

		logger.info("**************** Start get list units ****************");

		ResponseEntityBase reb = new ResponseEntityBase(Constant.RESPONSE_STATUS_ERROR, null);
		List<VT000000EntityListUnit> units = new ArrayList<>();

		try {
			unitInfo.setPageNumber(unitInfo.getPageNumber() * unitInfo.getPageSize());
			units = vt000000Dao.findListUnit(unitInfo);
			reb.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
			reb.setData(units);

			logger.info("**************** End get list units - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End get list units - Fail ****************");

			logger.error(e.getMessage(), e);
		}

		return reb;
	}

	@Override
	public void invalidateDeviceToken(String username) throws Exception {
		logger.info("**************** Start invalidateDeviceToken ****************");

		try {
			vt000000Dao.invalidateDeviceToken(username);
			logger.info("**************** End invalidateDeviceToken - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End invalidateDeviceToken - Fail ****************");

			logger.error(e.getMessage(), e);
		}

	}

	@Override
	public void invalidateToken(String username, String client) throws Exception {
		logger.info("**************** Start invalidateToken units ****************");
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("username", username);
			param.put("client", client);
			vt000000Dao.invalidateToken(param);
			logger.info("**************** End invalidateToken - OK ****************");
		} catch (Exception e) {
			logger.info("**************** End invalidateToken - Fail ****************");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean changePassword(String oPassword, String nPassword) throws Exception {
		logger.info("**************** Start changeGuardPassword units ****************");
		try {
			if(StringUtils.isEmpty(nPassword) || nPassword.trim().length() < 6) {
				logger.info("**************** End changeGuardPassword - Fail ****************");
				return false;
			}
			if(vt000000Dao.getPasswordOfGuard().equals(oPassword)) {
				vt000000Dao.setPasswordOfGuard(nPassword.trim());
				logger.info("**************** End changeGuardPassword - OK ****************");
				return true;
			} else {
				logger.info("**************** End changeGuardPassword - Fail ****************");
				return false;
			}
		} catch (Exception e) {
			logger.info("**************** End changeGuardPassword - Fail ****************");
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	
	private void addDeviceID(String username, String deviceID, String deviceType) {
		Connection con = null;
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
			logger.info("********************** Invoked Old DeviceID"
					+ " of User: " + username + " **********************");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
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
	public boolean updateDeviceToken(String device_token, String device_type, String username, String client) throws Exception {
		deleteOldSession(device_token, username, client);
		// Add deviceID for notification push
		if (device_token != null && !device_token.isEmpty() && device_type != null && !device_type.isEmpty()) {
			addDeviceID(username, device_token, device_type);

			// re-try send notify in queue
			findSendSmsNotify(username, device_token, device_type);
			return true;
		}
		return false;
	}
	
	public void deleteOldSession(String deviceToken, String username, String client) {
		/**
		 * Delete old session base on Username and client
		 */
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = dataSource.getConnection();
			preparedStatement = con.prepareStatement(
					"DELETE d FROM NOTI_REG as d WHERE (d.USERNAME = ?)");
			preparedStatement.setString(1, username);
			preparedStatement.executeUpdate();
			logger.info("********************** Invoked Old DeviceID of User: " + username + " **********************");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
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
		/**
		 * Delete Device Token
		 */
		try {
			con = dataSource.getConnection();
			preparedStatement = con.prepareStatement("DELETE FROM NOTI_REG WHERE DEVICE_ID = ?");
			preparedStatement.setString(1, deviceToken);
			preparedStatement.executeUpdate();
			logger.info(
					"********************** Invoked Old DeviceID of User: " + username + " **********************");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
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

	private String getClientId() {
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		final String authorizationHeaderValue = request.getHeader("Authorization");
		final String base64AuthorizationHeader = Optional.ofNullable(authorizationHeaderValue)
				.map(headerValue -> headerValue.substring("Basic ".length())).orElse("");

		if (!StringUtils.isEmpty(base64AuthorizationHeader)) {
			String decodedAuthorizationHeader = new String(Base64.getDecoder().decode(base64AuthorizationHeader),
					Charset.forName("UTF-8"));
			return decodedAuthorizationHeader.split(":")[0];
		}

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
								logger.error("**************** TEST DEVICE: "+ toUserName + "*************************");
								if (Constant.ANDROID_DEVICE.equals(deviceType)) {
								String bodyRequest = Notification.getAndroidMessage(findTokenDevice,
										(String) noti.get("addInfo"), (String) noti.get("message"),
										(String) noti.get("title"), (int) noti.get("type"),
										(int) noti.get("statusRecord"), toUserName);

								Notification.sendPost(bodyRequest);

								int statusSend = Constant.FLAG_SEND_SMS_NOTIFY_SUCCESS;
								updateStorageNotification(findTokenDevice, (String) noti.get("notiLogId"), statusSend);
								
								} else if(Constant.APPLE_DEVICE.equals(deviceType)) {
										String bodyRequest = Notification.getAppleMessage(findTokenDevice,
												(String) noti.get("addInfo"), (String) noti.get("message"),
												(String) noti.get("title"), (int) noti.get("type"),
												(int) noti.get("statusRecord"), toUserName);

										Notification.sendPost(bodyRequest);

										int statusSend = Constant.FLAG_SEND_SMS_NOTIFY_SUCCESS;
										updateStorageNotification(findTokenDevice, (String) noti.get("notiLogId"), statusSend);
								}
							} catch (Exception e) {

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
	
	private List<Map<String, Object>> findNotifyWaitSend(String username) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Map<String, Object>> output = new ArrayList<Map<String, Object>>();
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
				Map<String, Object> temp = new HashMap<String, Object>();
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
			logger.error(e.getMessage(),e);
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
		return null;
	}
	
	private void updateStorageNotification(String deviceID, String notiLogId, int status) {
		Connection con = null;
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
			preparedStatement.setString(3, notiLogId);
			preparedStatement.executeUpdate();
			
			logger.info("**********************  UPDATEE : " + deviceID + " *********"+notiLogId+"*************"+status);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
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
	@Transactional
	public void sendNotifyEachMinute() throws Exception {
		
		try {
			
			List<Map<String, Object>> listNoti = vt000000Dao.findNotifyEachMinute();
			
			for (Map<String, Object> noti : listNoti) {
				try {
					logger.info("**************** SEND NOTI DEVICE: "+ (String)noti.get("deviceType") + "*************************");
					
					if (Constant.ANDROID_DEVICE.equals((String)noti.get("deviceType"))) {
						
					String bodyRequest = Notification.getAndroidMessage((String)noti.get("deviceId"),
							(String) noti.get("addInfo"), (String) noti.get("message"),
							(String) noti.get("title"), (int) noti.get("type"),
							(int) noti.get("statusRecord"), (String) noti.get("toUserName"));

					logger.info("********SEND NOTIFY TO USERNAME:"+ (String) noti.get("toUserName"));
					Notification.sendPost(bodyRequest);

					int statusSend = Constant.FLAG_SEND_SMS_NOTIFY_SUCCESS;
					
					updateStorageNotification((String)noti.get("deviceId"), (String) noti.get("notiLogId"), statusSend);
					
					} else if(Constant.APPLE_DEVICE.equals((String)noti.get("deviceType"))) {
						
							String bodyRequest = Notification.getAppleMessage((String)noti.get("deviceId"),
									(String) noti.get("addInfo"), (String) noti.get("message"),
									(String) noti.get("title"), (int) noti.get("type"),
									(int) noti.get("statusRecord"), (String) noti.get("toUserName"));

						
							logger.info("********SEND NOTIFY TO USERNAME:"+ (String) noti.get("toUserName"));
							Notification.sendPost(bodyRequest);

							int statusSend = Constant.FLAG_SEND_SMS_NOTIFY_SUCCESS;
							updateStorageNotification((String)noti.get("deviceType"), (String) noti.get("notiLogId"), statusSend);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					logger.info("****************************EROOR SEND NOTIFYCATION NOTI_LOG_ID:"
							+ (String) noti.get("notiLogId") + "**********************");

					
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("****************************send Notify Each Minute ERROR********:");
		}
		
	}

	@Override
	public Map<String, String> getAppVersion() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
