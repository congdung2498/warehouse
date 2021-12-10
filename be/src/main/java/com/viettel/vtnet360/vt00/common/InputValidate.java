package com.viettel.vtnet360.vt00.common;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class InputValidate {

	/**
	 * check 1 string just include number, character(UTF8) and space
	 *  and check limit length of string
	 * 
	 * @param input
	 * @param length
	 * @return
	 */
	public boolean validateWord(String input, int length) {
		if (!StringUtils.isBlank(input)){
			if(input.length() > length) {
				return false;
			}
			else {
				int c = ' ';
	            StringBuilder sb = new StringBuilder(input);
	            for (int i = 0; i < sb.length(); i++){
	                int code = sb.codePointAt(i);
	                if (!(Character.isLetterOrDigit(code) || code == c)){
	                    return false;
	                }
	            }
	            return true;
			}
        }
        return false;
	}
	
	public boolean validateWordForDish(String input, int length) {
	  if (!StringUtils.isBlank(input)){
	    if(input.length() > length) {
	      return false;
	    }
	    else {
	      int c = ' ';
	      StringBuilder sb = new StringBuilder(input);
	      for (int i = 0; i < sb.length(); i++){
	        int code = sb.codePointAt(i);
	        if(code == ';' || code == ',') continue;
	        if (!(Character.isLetterOrDigit(code) || code == c)) return false;
	      }
	      return true;
	    }
	  }
	  return false;
  }
	
	/**
	 * set hour, minute, second, milisecond = 0  
	 * 
	 * @param date
	 * @return
	 */
	public Date validateDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);	
		return c.getTime();
	}
}