/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.vtnet360.vt00.vt000000.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
//import com.sun.xml.ws.client.BindingProviderProperties;
import com.viettel.security.PassTranformer;
//import com.viettel.tktu.client.form.v2Suggestion.ResultActionForm;
//import com.viettel.tktu.client.form.v2Suggestion.SubmitVOfficeForm;
//import com.viettel.tktu.client.form.v2Suggestion.VofficeUserSign;
//import com.viettel.tktu.common.util.LanguageBundleUtils;
import com.viettel.voffice.ws_autosign.service.FileAttachTranfer;
import com.viettel.voffice.ws_autosign.service.FileAttachTranferList;
import com.viettel.voffice.ws_autosign.service.KttsVofficeCommInpuParam;
import com.viettel.voffice.ws_autosign.service.Vo2AutoSignSystemImpl;
import com.viettel.voffice.ws_autosign.service.Vo2AutoSignSystemImplService;
import com.viettel.voffice.ws_autosign.service.Vof2EntityUser;
import com.viettel.vtnet360.common.CASign.PassWordUtil;
import com.viettel.vtnet360.common.CASign.SecurityPassword;
import com.viettel.vtnet360.signVoffice.entity.SignVofficeEntity;
import com.viettel.vtnet360.signVoffice.request.dao.SignVofficeDao;
import com.viettel.vtnet360.vt00.common.LanguageBundleUtils;
import com.viettel.vtnet360.vt00.common.utils.ResourceBundleUtils;
import com.viettel.vtnet360.vt00.vt000000.entity.SignVofficeBO;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000EntityResultSign;
import com.viettel.vtnet360.vt00.vt000000.entity.VT000000SubmitVOfficeInput;
import com.viettel.vtnet360.vt00.vt000000.entity.VofficeUserSign;


/**
 *
 * @author vipc
 */
public class VOfficeAutoSignWebservice {

  @Autowired
  Properties messages;
  

  private final String OK = "OK";
  private final String NOK = "NOK";

  private final Logger logger = Logger.getLogger(VOfficeAutoSignWebservice.class);
  private static VOfficeAutoSignWebservice autoSignUtil;
  private URL url = null;
  private URL baseUrl;
  private Vo2AutoSignSystemImplService service;
  private static Vo2AutoSignSystemImpl port;

  public static VOfficeAutoSignWebservice getInstance(VT000000SubmitVOfficeInput form) throws Exception {
    if (autoSignUtil == null) {
      autoSignUtil = new VOfficeAutoSignWebservice();


    }
    if(form.getIsSign()!=1){
      FileAttachTranferList  attachTranferList =  port.getAllFile("PMQTVP", form.getTransCode(), true);
      /*        SignatureImageObjList  signatureImageObjList =  port.getListSignImage("PMQTVP", "PMQTVP-1564045379587");
                    signatureImageObjList.getLstSignatureImageObj().get(0).getAttachBytes();  */
      // attachTranferList.get
      byte[] t=attachTranferList.getLstFileAttachTranfer().get(0).getAttachBytes();
      OutputStream out = null;
      try {  
        out = new FileOutputStream(convertUrl()+"/documentVO/"+form.getSignVofficeId()+".out.out.pdf");
        out.write(t);
      } catch (Exception e) {  
        System.err.println("Error: " + e.getMessage());  
      } finally {
        if(out != null){
          out.close();
        }
      }
    }
    return autoSignUtil;
  }

  public VOfficeAutoSignWebservice() throws Exception {
    String wsUrl = ResourceBundleUtils.getResource("ws.autoSign.url");
    String targetNamespace = ResourceBundleUtils.getResource("ws.autoSign.targetNamespace");
    String name = ResourceBundleUtils.getResource("ws.autoSign.name");

    baseUrl = Vo2AutoSignSystemImplService.class.getResource(".");
    url = new URL(baseUrl, wsUrl);
    service = new Vo2AutoSignSystemImplService(url, new QName(targetNamespace, name));
    port = service.getVo2AutoSignSystemImplPort();

  }

  public List<Vof2EntityUser> getListVof2UserByMail(List<String> emails) {
    if (emails == null || emails.isEmpty()) {
      return null;
    } else {
      List<String> mailSends = new ArrayList<String>();
      for (String email : emails) {
        if (email != null) {
          if (email.trim().endsWith("@viettel.com.vn")) {
            mailSends.add(email.trim());
          } else if (!email.trim().contains("@")) {
            mailSends.add(email.trim() + "@viettel.com.vn");
          }
        }
      }
      return port.getListVof2UserByMail(mailSends);
    }
  }

  public List<VofficeUserSign> getListUserByMailInVOffice(String emails) {
    if (emails == null || "".equals(emails.trim())) {
      return new ArrayList<VofficeUserSign>();
    } else {
      List<String> listEmail = Arrays.asList(emails.trim().split(";"));
      List<Vof2EntityUser> listUser = getListVof2UserByMail(listEmail);
      if (listUser != null && !listUser.isEmpty()) {
        List<VofficeUserSign> rs = new ArrayList<VofficeUserSign>();
        VofficeUserSign temp;
        for (Vof2EntityUser item : listUser) {
          temp = new VofficeUserSign();
          temp.setAdOrgId(item.getAdOrgId());
          temp.setAdOrgName(item.getAdOrgName());
          temp.setEmail(item.getStrEmail());
          temp.setEmployeeCode(item.getStrCardNumber());
          temp.setFullName(item.getFullName());
          temp.setJobTile(item.getJobTile());
          temp.setUserId(item.getUserId());
          rs.add(temp);
        }
        return rs;
      } else {
        return null;
      }
    }
  }

  public Long vo2RegDigitalDocByEmail(KttsVofficeCommInpuParam input) {
    return port.vo2RegDigitalDocByEmail(input);
  }

  public boolean getFileSign(VT000000SubmitVOfficeInput formSubmit) {
    try {
      VOfficeAutoSignWebservice.getInstance(formSubmit);
    } catch (Exception e) {
      logger.error(e.getMessage(),e);
      // TODO Auto-generated catch block
    }
    return true;
  }

  public VT000000EntityResultSign submitVOfficeSuggestionInfo(VT000000SubmitVOfficeInput formSubmit, List<VofficeUserSign> listUser, List<FileAttachTranfer> lstTranfer) {
    Long result = -1L;
    try {
      KttsVofficeCommInpuParam vofficeParam = new KttsVofficeCommInpuParam();
      String appCode = ResourceBundleUtils.getValueByKey("ws.autoSign.appCode");
      String appPass = ResourceBundleUtils.getValueByKey("ws.autoSign.appPass");
      String sender = ResourceBundleUtils.getValueByKey("ws.autoSign.sender");
      String key = ResourceBundleUtils.getValueByKey("ws.autoSign.key");
      vofficeParam.setAppCode(appCode);
      vofficeParam.setAppPass(appPass);
      vofficeParam.setSender(sender);
      vofficeParam.setMoneyUnitID(1L);
      vofficeParam.setAreaId(formSubmit.getAreaOfDocumentId());
      vofficeParam.setRegisterNumber(formSubmit.getRegisterNumber());// ma hieu van ban
      vofficeParam.setHinhthucVanban(formSubmit.getFormOfDocumentId());

      vofficeParam.setAccountName(formSubmit.getUsername());
      PassTranformer.setInputKey(key);//set key
//      String accountPassEnc = PassWordUtil.getInstance().encrypt(formSubmit.getPassword());//ma hoa password
      String accountPassEnc = SecurityPassword.encryptPassword(formSubmit.getPassword());//ma hoa password
      System.out.println("kientn: plain " + formSubmit.getPassword() + "\\\\ encryp: " + accountPassEnc);
      vofficeParam.setAccountPass(accountPassEnc);

      String transCode = sender + "-" + new Date().getTime();
      vofficeParam.setTransCode(transCode);
      vofficeParam.setDocTitle(formSubmit.getDocTitle());
      List<Vof2EntityUser> listUserSign = new ArrayList<Vof2EntityUser>();
      Vof2EntityUser temp;
      List<String> listEmail = new ArrayList<String>();
      for (VofficeUserSign item : listUser) {
        temp = new Vof2EntityUser();
        temp.setUserId(item.getUserId());
        temp.setAdOrgId(item.getAdOrgId());
        temp.setSignImageIndex(item.getSignImageIndex());
        temp.setIsPublicText(1L);
        listUserSign.add(temp);
        listEmail.add(item.getEmail());
      }
      vofficeParam.getLstEmail().addAll(listEmail);
      vofficeParam.getLstUserVof2().addAll(listUserSign);
      vofficeParam.setAutoPromulgateText(1L);

      System.out.println("---lstTranfer--- " + lstTranfer.size());
      vofficeParam.getLstFileAttach().addAll(lstTranfer);

      vofficeParam.setDocTitle(formSubmit.getFileName());
      vofficeParam.setRegisterNumber("BC");
      result = vo2RegDigitalDocByEmail(vofficeParam);

      if (1L == result) {
        return new VT000000EntityResultSign(result, null, transCode);
      } else {
        return new VT000000EntityResultSign(result, "Trinh Ki sai", null);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }	
    return new VT000000EntityResultSign(result);
  }

  public String getMessageFromResult(Long result) {
    if (result != null) {
      if (result.equals(1L)) {
        return "OK";
      } else {
        String keyMessage = "message.voffice.result." + result.toString();
        return ResourceBundleUtils.getValueByKey(keyMessage);

      }
    } else {
      return "NOK";
    }
  }

  public VT000000EntityResultSign onSubmitVOffice(Principal principal, VT000000SubmitVOfficeInput form, List<SignVofficeEntity> details) {
    System.out.println("host vo: " + this.url.getHost().toString() + this.url.getPort());
    logger.info("HOST VO: " + this.url.getHost().toString() + this.url.getPort());
    long startTime = Calendar.getInstance().getTimeInMillis();
    Long resultId = -1L;
    try {
      ObjectMapper mapper = new ObjectMapper();
      String formOfDocument = ResourceBundleUtils.getValueByKey("ws.autoSign.suggestion.formOfDocumentId");
      String areaOfDocument = ResourceBundleUtils.getValueByKey("ws.autoSign.suggestion.areaOfDocumentId");
      try {
        form.setFormOfDocumentId(formOfDocument != null ? Long.valueOf(formOfDocument) : null);
      } catch (NumberFormatException e) {logger.error(e);}
      try {
        form.setAreaOfDocumentId(areaOfDocument != null ? Long.valueOf(areaOfDocument) : null);
      } catch (NumberFormatException e) {logger.error(e);}
      String register = ResourceBundleUtils.getValueByKey("ws.autoSign.suggestion.register");
      if (form.getRegisterNumber() == null || "".equals(form.getRegisterNumber().trim())) {
        form.setRegisterNumber(register);
      }
      List<String> listInputEmail = Arrays.asList(form.getEmails().split(";"));
      List<String> listIsPublicText = Arrays.asList(form.getIsPublicTexts().split(";"));
      List<String> listIndex = Arrays.asList(form.getSignImageIndexs().split(";"));
      List<Long> listUserId = new ArrayList<Long>();
      List<VofficeUserSign> listUserSignFinal = new ArrayList<VofficeUserSign>();
      //  String jobTitle;

      List<VofficeUserSign> listUserSignA = VOfficeAutoSignWebservice.getInstance(form).getListUserByMailInVOffice(form.getEmails()); 
      logger.info("VofficeUserSign: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(listUserSignA));

      Map<String, List<VofficeUserSign>> mapEmailUser = new HashMap<String, List<VofficeUserSign>>();
      List<String> listMail = new ArrayList<String>(Arrays.asList(form.getEmails().split(";")));
      if(listUserSignA != null){
        for (VofficeUserSign item : listUserSignA) {
          List<VofficeUserSign> listUserSignB = mapEmailUser.get(item.getEmail());
          if (listUserSignB == null) {
            listUserSignB = new ArrayList<VofficeUserSign>();
            mapEmailUser.put(item.getEmail(), listUserSignB);
          }
          listUserSignB.add(item);
        }
        logger.info("VOfficeAutoSignWebservice.getListUserByMailInVOffice is success! ");
      } else {
        logger.info("VOfficeAutoSignWebservice.getListUserByMailInVOffice return null ");
        List<String> listEmail= new ArrayList<>();
        return new VT000000EntityResultSign(null, listEmail);
      }

      for (String email : listInputEmail) {
        List<VofficeUserSign> listUserSign = mapEmailUser.get(email);
        if(listUserSign != null) {
          for (VofficeUserSign item : listUserSign) {
            if (listUserId.contains(item.getUserId())) {
              continue;
            }
            int a = listInputEmail.indexOf(item.getEmail());
            if (a > -1) {
              listUserId.add(item.getUserId());
              item.setSignImageIndex(2L);
              item.setIsPublicText(false);
              if(a!=0 || listInputEmail.size()==1){
                String displaySignImage = listIndex.get(0);
                if ("None".equalsIgnoreCase(displaySignImage)) {
                  item.setSignImageIndex(null);
                } else {
                  Long index = Long.valueOf(displaySignImage);
                  item.setSignImageIndex(index.equals(0L) ? null : index);
                }

                String isPublicText = listIsPublicText.get(0);
                if ("YES".equalsIgnoreCase(isPublicText)) {
                  item.setIsPublicText(true);
                } else if ("NO".equalsIgnoreCase(isPublicText)) {
                  item.setIsPublicText(false);
                } else {
                  item.setIsPublicText(false);
                }
              }
            }
            listUserSignFinal.add(item);
          }
        }
      }
      if(listUserSignFinal == null || listUserSignFinal.size() != listMail.size() ) {
        List<String> listEmail= new ArrayList<>();
        return new VT000000EntityResultSign(null, listEmail);
      }
      
      logger.info("Tranfer to PDF ******************* START *************************************");
      List<FileAttachTranfer> lstTranfer = new ArrayList<FileAttachTranfer>();

      FileAttachTranfer fileSign = new FileAttachTranfer();
      fileSign.setFileName(form.getFileName()+".pdf");
      fileSign.setFileSign(1L);//file ký chính

      File fileMain = new File(convertUrl()+"/documentVO/"+form.getSignVofficeId()+".out.pdf");
      byte[] data = Files.readAllBytes(fileMain.toPath());
      fileSign.setAttachBytes(data);
      lstTranfer.add(fileSign);
      
      if(details.size() > 0) {
        for(SignVofficeEntity entity : details) {
          FileAttachTranfer detailFileSign = new FileAttachTranfer();
          detailFileSign.setFileName(entity.getContent() + ".pdf");
          detailFileSign.setFileSign(2L);

          File detailFileMain = new File(convertUrl()+"/documentVO/" + entity.getSignVofficeId() + ".out.pdf");
          byte[] detailData = Files.readAllBytes(detailFileMain.toPath());
          detailFileSign.setAttachBytes(detailData);
          lstTranfer.add(detailFileSign);
        }
      }
      
      logger.info("Tranfer to PDF ******************* END *************************************");

      List<Long> listSuggestId = new ArrayList<Long>();
      if (form.getListSuggestId() != null && !"".equals(form.getListSuggestId().trim())) {
        String[] listA = form.getListSuggestId().split(";");
        for (int i = 0; i < listA.length; i++) {
          try {
            String suggestIdTmp = listA[i];
            listSuggestId.add(Long.valueOf(suggestIdTmp));
          } catch (NumberFormatException e) {
          }
        }
      } else {
        listSuggestId.add(form.getSuggestId());
      }
      
      logger.info("SUBMIT TO VO  ******************* START *************************************");
      VT000000EntityResultSign resultSubmit = 
          VOfficeAutoSignWebservice.getInstance(form).submitVOfficeSuggestionInfo(form, listUserSignFinal, lstTranfer);
      resultId = resultSubmit.getResult();
      
      logger.info("SUBMIT TO VO  ******************* END *************************************");
      
      long endTime = Calendar.getInstance().getTimeInMillis();
      System.out.println("Time request TRINH KY = = =  " + (endTime - startTime) + "; url: " + this.url.getHost());
      logger.info("Time request TRINH KY = = =  " + (endTime - startTime) + "; url: " + this.url.getHost());
      System.out.println(("Time request TRINH KY = = =  " + (endTime - startTime) + "; url: " + this.url.getHost()));
      
      logger.info(new Gson().toJson(resultSubmit));
      System.out.println((new Gson().toJson(resultSubmit))); 
      
      if (resultSubmit != null) {
        if (1L == resultId) {
          saveInfoToSignVOffice("SUGGESTION"
              , "ws.autoSign.appCode"
              , resultSubmit.getControlId()
              , "KienPK"
              , form.getDocTitle()
              );
          return new VT000000EntityResultSign(resultId, LanguageBundleUtils.getStringV2Suggestion("message.submitVOffice.success"), resultSubmit.getControlId());
        } else if (1L != resultSubmit.getResult()) {
          return new VT000000EntityResultSign(resultId, LanguageBundleUtils.getStringV2Suggestion("message.submitVOffice.fail") 
              + " " + resultSubmit.getMessage());
        } else {
          return new VT000000EntityResultSign(resultId, resultSubmit.getMessage());
        }
      } 
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return new VT000000EntityResultSign(resultId, LanguageBundleUtils.getStringV2Suggestion("message.submitVOffice.fail"), null);
  }

  // Get List File

  public List<FileAttachTranfer> getListFileAttachInBySuggestion(List<Long> listSuggestion) {
    List<FileAttachTranfer> rs = new ArrayList<FileAttachTranfer>();
    try {
      String folder = "../../report_out";
      File file = new File(folder);
      file.mkdirs();
      int plIndex = 1;
      //<editor-fold defaultstate="collapsed" desc="file suggestion new site">
      String title =  "voffice.suggestion.suggest.newSite.title";
      //String title = LanguageBundleUtils.getStringV2Suggestion("voffice.suggestion.suggest.newSite.title");


      //</editor-fold>
      //<editor-fold defaultstate="collapsed" desc="file suggestion moveSite">
      //title = LanguageBundleUtils.getStringV2Suggestion("voffice.suggestion.suggest.moveSite.title");
      title =  "voffice.suggestion.suggest.moveSite.title";                      

      //logger.info("listEfficientBo size " + listEfficientBo.size());
      //</editor-fold>


    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }
    return rs;
  }

  public void saveInfoToSignVOffice(String type, String appCode, String transCode, String username, String title){
    //save SignVofficeBo
    SignVofficeBO sign = new SignVofficeBO();
    sign.setAppCode(appCode);
    sign.setTransCode(transCode);
    sign.setType(type);
    sign.setCreateTime(new Date());
    sign.setStatus("0");
    sign.setDocTitle(title);
    //        sign.setDocumentCode(documentCode);
    //TODO
    // call service save data 
    //session.saveOrUpdate(sign);
    /* SignVofficeLogBO signLog = new SignVofficeLogBO();
        signLog.setSignVofficeId(sign.getId());
        signLog.setUserCreate(username);
        signLog.setUpdateTime(new Date());
        signLog.setContent("Submit to VOffice with title: "+ title);
        signLog.setAction(1L);
        session.saveOrUpdate(signLog);*/
  }
  public static void main(String[] args) {
    PassTranformer.setInputKey("keyencryp");
    String accountPassEnc = PassTranformer.decrypt("3e4129750d97bcea284fe37d4e85be68");//ma hoa password
    System.out.println(accountPassEnc);
  }
  private static String convertUrl(){
    int lastRegex = System.getProperty("catalina.home").lastIndexOf(File.separator);
    String url=System.getProperty("catalina.home").substring(0, lastRegex);

    File theDir = new File(url+"/documentVO");
    if (!theDir.exists()) {
      theDir.getName();
      try{
        theDir.mkdir();
      } 
      catch(SecurityException se){
      }        

    }
    return url;
  }
}
