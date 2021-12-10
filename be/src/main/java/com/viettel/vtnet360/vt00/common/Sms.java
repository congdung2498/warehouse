package com.viettel.vtnet360.vt00.common;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettel.vtnet360.checking.resgister.dao.CheckingDAO;
import com.viettel.vtnet360.vt00.common.dao.VTCommonDAO;

@Service
public class Sms {

  // Mang cac ky tu goc co dau
  private static char[] SOURCE_CHARACTERS = { 'À', 'Á', 'Â', 'Ã', 'È', 'É',
      'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
      'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
      'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
      'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
      'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
      'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
      'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
      'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
      'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
      'ữ', 'Ự', 'ự', };

  // Mang cac ky tu thay the khong dau
  private static char[] DESTINATION_CHARACTERS = { 'A', 'A', 'A', 'A', 'E',
      'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
      'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
      'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
      'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
      'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
      'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
      'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
      'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
      'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
      'U', 'u', 'U', 'u', };

  @Autowired
  private  VTCommonDAO vtCommonDAO;

  @Autowired
  private CheckingDAO  checkingDAO;

  /** Logger */
  private final Logger logger = Logger.getLogger(this.getClass());


  public void createForChecking(int toUserId ,String content,int status,String phone,String smsType, String checkingId, Date inTime) {
    Date sendTime = null;
    Date insertTime = new Date(System.currentTimeMillis());

    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("toUserId", toUserId);
    data.put("content", "[VTNet360] " + deAccent(content));
    data.put("status", status);
    data.put("insertTime", insertTime);
    data.put("phone", phone);
    data.put("smsType", smsType);
    data.put("sendTime", sendTime);
    data.put("checkingId", checkingId);
    data.put("inTime", inTime);

    checkingDAO.toSmsForChecking(data);
  }

  public void updateForChecking(String checkingId, Date inTime) {
    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("status", 0);
    data.put("checkingId", checkingId);
    data.put("inTime", inTime);

    checkingDAO.toUpdateSmsForChecking(data);
  }

  public void sendSms(int toUserId ,String content,int status,String phone,String smsType) {
    try {
      //them vao day
      storageSms(toUserId, content, status, phone, smsType);
    }catch (Exception e) {
      logger.info("****************************CAN'T SEND SMS ********:");
    }
  }


  public void sendSms(String toUserName ,String content,int status,String smsType) throws Exception{

    try {
      //them vao day
      Map<String, Object> data = vtCommonDAO.findInfo(toUserName);
      storageSms((int)data.get("empId"), content, status, (String)data.get("empPhone"), smsType);
    }catch (Exception e) {
      throw e;
    }
  }

  @Transactional
  private  boolean storageSms(int toUserId ,String content,int status,String phone, String smsType) throws Exception {
    Date sendTime = null;
    Date insertTime = new Date(System.currentTimeMillis());
    try {
      HashMap<String, Object> data = new HashMap<String, Object>();
      data.put("toUserId", toUserId);
      data.put("content", "[VTNet360] " + deAccent(content));
      data.put("status", status);
      data.put("insertTime", insertTime);
      data.put("phone", phone);
      data.put("smsType", smsType);
      data.put("sendTime", sendTime);

      vtCommonDAO.storageSms(data);
    } catch (Exception e) {
      throw e;
    }

    return true;

  }

  /**
   * @author VinhNVQ
   * 
   * Convert UTF-8 string to ASCII string
   * @param str
   * @return String
   */
  public static String deAccent(String str) {
    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return removeAccent(pattern.matcher(nfdNormalizedString).replaceAll(""));
  }

  /**
   * Bo dau 1 ky tu
   * 
   * @param ch
   * @return
   */
  public static char removeAccent(char ch) {
    int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
    if (index >= 0) {
      ch = DESTINATION_CHARACTERS[index];
    }
    return ch;
  }

  /**
   * Bo dau 1 chuoi
   * 
   * @param s
   * @return
   */
  public static String removeAccent(String s) {
    StringBuilder sb = new StringBuilder(s);
    for (int i = 0; i < sb.length(); i++) {
      sb.setCharAt(i, removeAccent(sb.charAt(i)));
    }
    return sb.toString();
  }
}
