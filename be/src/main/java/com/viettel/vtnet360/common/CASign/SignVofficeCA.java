package com.viettel.vtnet360.common.CASign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.viettel.vtnet360.signVoffice.entity.SignVofficeEntity;
import com.viettel.vtnet360.vt00.common.utils.ResourceBundleUtils;

public class SignVofficeCA {

  public static void getFileSign(SignVofficeEntity signVofficeEntity) {
    String url = ResourceBundleUtils.getResource("ws.autoSign.url.getFile");
    String key = ResourceBundleUtils.getResource("ws.autoSign.key.getFile");
    cmd(url, new Gson().toJson(signVofficeEntity), key, "/u01/pmqt/documentVO/");
  }

  private static void cmd(String url, String data, String key, String out) {
    String command = "java -jar /u01/pmqt/getFileSign.jar"
        .concat(" ").concat(url).concat(" ").concat(data).concat(" ").concat(key).concat(" ").concat(out);
    System.out.println(command);
    
    Process proc = null;
    try {
      proc = Runtime.getRuntime().exec(command);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Read the output

    BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

    String line = "";
    try {
      while ((line = reader.readLine()) != null) {
        System.out.print(line + "\n");
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    try {
      proc.waitFor();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
