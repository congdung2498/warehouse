package com.viettel.vtnet360.vt00.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.net.HttpURLConnection;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.vt00.common.dao.VTCommonDAO;

@Service
public class Notification {

	/** Logger */
	private final static Logger logger = Logger.getLogger(Notification.class);

	@Autowired
	private VTCommonDAO vtCommonDAO;

	@Autowired
	Properties configProperty;

	@Transactional
	public void sendNotification(String toUserName, String additionalInformation, String message, String title,
			int type, String createUser, int statusRecord) {
		try {

			// storage first
			int statusSend = Constant.FLAG_SEND_SMS_NOTIFY_FALL;

			String returnedLogId = storageNotification(toUserName, null, additionalInformation, message, title, type,
					createUser, statusSend, statusRecord);
			
			logger.info("****************** sendNotification to toUserName: " + toUserName);
			
			Map<String, String> tokenList = vtCommonDAO.findTokenDevice(toUserName);
			
			// device ID
			String tokenDevice = (String) tokenList.get("DEVICE_ID");

			// device type
			String deviceType = (String) tokenList.get("DEVICE_TYPE");
			
			
			
			if(Constant.ANDROID_DEVICE.equals(deviceType)) {
				
				logger.info("**************** SEND NOTIFY ANDROID DEVICE *************************");
				
				if (tokenDevice == null) {

					throw new Exception("CAN'T FIND TOKENDEVICE");

				} else {

					String bodyRequest = getAndroidMessage(tokenDevice, additionalInformation, message, title, type, statusRecord,
							toUserName);
					sendPost(bodyRequest);

					// update storage after success
					statusSend = Constant.FLAG_SEND_SMS_NOTIFY_SUCCESS;
					updateStorageNotification(returnedLogId, tokenDevice, statusSend);
				}
			} else if(Constant.APPLE_DEVICE.equals(deviceType)) {
				
				logger.info("**************** SEND NOTIFY APPLE DEVICE *************************");
				if (tokenDevice == null) {

					throw new Exception("CAN'T FIND TOKENDEVICE");

				} else {

					String bodyRequest = getAppleMessage(tokenDevice, additionalInformation, message, title, type, statusRecord,
							toUserName);
					sendPost(bodyRequest);

					// update storage after success
					statusSend = Constant.FLAG_SEND_SMS_NOTIFY_SUCCESS;
					updateStorageNotification(returnedLogId, tokenDevice, statusSend);
				}
			} else {
				throw new Exception("CAN'T FIND TOKENDEVICE");
			}
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			logger.info("****************************CAN'T SEND NOTIFICATION************************");
		}

	}

	// HTTP POST request
	public static void sendPost(String bodyRequest) throws Exception {

		// String url = configProperty.getProperty("URL_SERVER_GOOGLE_NOTIFICATION");
		String url = Constant.URL_SERVER_GOOGLE_NOTIFICATION;

		logger.info("************SENDING:URL:" + url);
		logger.info("************SENDING WITH BODY:"+bodyRequest);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		
		con.setConnectTimeout(30000); //set time out 30s
		// add request header
		con.setRequestMethod("POST");
//		con.setRequestProperty("Content-Type", "text/plain");
		con.setRequestProperty("Content-Type", "application/json");

		con.setRequestProperty("Authorization", "key=" + Constant.SERVER_KEY_NOTIFICATION);

		// Send post request
		con.setDoOutput(true);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
		bw.write(bodyRequest);
		bw.flush();
		bw.close();

		int responseCode = con.getResponseCode();
		logger.info("\nSending 'POST' request to URL : " + url);
		logger.info("Post parameters : " + bodyRequest);
		logger.info("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;

		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		logger.info(response.toString());
	}

	public static String getAndroidMessage(String tokenDevice, String additionalInformation, String message, String title,
			int type, int status, String toUserName) throws JSONException {

		JSONArray arrayIdDevice = new JSONArray();
		arrayIdDevice.put(tokenDevice);

		JSONObject data = new JSONObject();
		data.put("additionalInformation", additionalInformation);
		data.put("message", message);
		data.put("title", title);
		data.put("type", type);
		data.put("status", status);
		data.put("toUserName", toUserName);

		JSONObject allComponent = new JSONObject();
		allComponent.put("registration_ids", arrayIdDevice);
		allComponent.put("data", data);

		return allComponent.toString();
	}
	
	public static String getAppleMessage(String tokenDevice, String additionalInformation, String message, String title,
			int type, int status, String toUserName) throws JSONException {
		
		JSONObject data = new JSONObject();
		JSONObject appMessageDic = new JSONObject();
		JSONObject notification = new JSONObject();
		
		notification.put("title", title);
        notification.put("body", message);
		
		data.put("additionalInformation", additionalInformation);
		data.put("type", type);
		data.put("status", status);
		data.put("toUserName", toUserName);

        appMessageDic.put("priority", "high");
        appMessageDic.put("notification", notification);
        appMessageDic.put("data", data);
        appMessageDic.put("to", tokenDevice);

        return appMessageDic.toString();
    }

	@Transactional
  public String storageNotification(String toUserName, String tokenDevice, String additionalInformation,
			String message, String title, int type, String createUser, int statusSend, int statusRecord)
			throws Exception {
		Date timeNow = new Date(System.currentTimeMillis());
		HashMap<String, Object> data = new HashMap<String, Object>();
		try {

			data.put("toUserName", toUserName);
			data.put("tokenDevice", tokenDevice);
			data.put("additionalInformation", additionalInformation);
			data.put("message", message);
			data.put("title", title);
			data.put("type", type);
			data.put("createUser", createUser);
			data.put("statusSend", statusSend);
			data.put("statusRecord", statusRecord);
			data.put("timeNow", timeNow);
			vtCommonDAO.storageNotification(data);
		} catch (Exception e) {
			throw e;
		}

		return (String) data.get("returnedLogId");
	}

	@Transactional
	public boolean updateStorageNotification(String notiLogid, String tokenDevice, int statusSend) throws Exception {

		try {

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("notiLogid", notiLogid);
			data.put("tokenDevice", tokenDevice);
			data.put("statusSend", statusSend);

			vtCommonDAO.updateStorageNotification(data);

		} catch (Exception e) {
			throw e;
		}

		return true;
	}

}
