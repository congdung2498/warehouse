package com.viettel.vtnet360.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

public class CalculateDate {

  public static Date calculateFinishTime(int hours, List<Date> offDays) {
    Date today = new Date();

    if(hours == 8) {
      today = calEightHours(offDays);
    } else if(hours < 8) {
      today = calLessEightHours(hours, offDays);
    } else {
      today = calThanEightHours(hours, offDays);
    }
    return today;
  }

  @SuppressWarnings("deprecation")
  public static Date calculateStartTime(List<Date> offDays) {
    Date today = setWorkRangeDate();

    for(Date date : offDays) {
      if(DateUtils.isSameDay(today, date)) {
        today.setTime(today.getTime() + 24 * 60 * 60 * 1000);
        today.setHours(8);
        today.setMinutes(0);
        today.setSeconds(0);
      }
    }
    return today;
  }

  @SuppressWarnings("deprecation")
  static Date calThanEightHours(int hours, List<Date> offDays) {
    Date currentDate = new Date();
    Date finishDate = setWorkRangeDate();
    
    
    long notUsedTime = 0;
    boolean isOffCurrentDay = true;
    if(!isDayOff(finishDate, offDays)) {
      notUsedTime = getMoreTimeThanEight(finishDate, hours);
      isOffCurrentDay = false;
    }
    
    if(isOffCurrentDay) {
      int days = hours / 8;
      int plusHours = hours % 8;

      finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);
      while(days > 0 || isDayOff(finishDate, offDays)) {
        if(isDayOff(finishDate, offDays)) {
        } else {
          days = days - 1;
        }
        finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);
      }
      
      if(plusHours == 0) {
        finishDate.setTime(finishDate.getTime() - 24 * 60 * 60 * 1000);
        finishDate.setHours(17);
        finishDate.setMinutes(30);
        finishDate.setSeconds(0);
      } else {
        finishDate.setHours(8 + plusHours);
        finishDate.setMinutes(0);
        finishDate.setSeconds(0);
        
        if((finishDate.getHours() == 12 && finishDate.getMinutes() > 0) || finishDate.getHours() > 12) {
          finishDate.setTime(finishDate.getTime() + 90 * 60 * 1000);
        }
      }
    } else {
      if(notUsedTime > 0) {
        finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);
        finishDate.setHours(8);
        finishDate.setMinutes(0);
        finishDate.setSeconds(0);
      }
      
      int days = (int) notUsedTime / (int) (8 * 60 * 60 * 1000);
      int plusHours = (int) notUsedTime % (int) (8 * 60 * 60 * 1000);
      
      boolean isOffDay = false;
      if(days == 0) {
        for(Date date : offDays) {
          if(DateUtils.isSameDay(finishDate, date)) {
            finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);
            finishDate.setHours(8);
            finishDate.setMinutes(0);
            finishDate.setSeconds(0);
          }
        }
      }
      
      while(days > 0 || (isDayOff(finishDate, offDays))) {
        finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);
        if(isDayOff(finishDate, offDays)) {
          isOffDay = true;
        } else {
          days = days - 1;
        }
      }
      
      if(isOffDay) {
        finishDate.setHours(8);
        finishDate.setMinutes(0);
        finishDate.setSeconds(0);
      }
      
      finishDate.setTime(finishDate.getTime() + plusHours);
      
      if((finishDate.getHours() == 12 && finishDate.getMinutes() > 0) || finishDate.getHours() > 12) {
        finishDate.setTime(finishDate.getTime() + 90 * 60 * 1000);
      }
      
      if((finishDate.getHours() == 17 && finishDate.getMinutes() > 30) || (finishDate.getHours() > 17 || 
            (isOffCurrentDay && finishDate.getDay() != currentDate.getDay()))) {
        finishDate.setTime(finishDate.getTime() + 14 * 60 * 60 * 1000 + 30 * 60 * 1000);
      }
      
      if(finishDate.getHours() == 8 && finishDate.getMinutes() == 0) {
        finishDate.setTime(finishDate.getTime() - 24 * 60 * 60 * 1000);
        finishDate.setHours(17);
        finishDate.setMinutes(30);
        finishDate.setSeconds(0);
      }
    }
    return finishDate;
  }

  @SuppressWarnings("deprecation")
  static Date calEightHours(List<Date> offDays) {
    Date currentDate = new Date();
    Date finishDate = setWorkRangeDate();
    
    long notUsedTime = 0;
    boolean isOffCurrentDay = true;
    if(!isDayOff(finishDate, offDays)) {
      notUsedTime = getMoreTime(finishDate, 8);
      isOffCurrentDay = false;
    }
    if(notUsedTime < 0) return finishDate;
    
    if(isOffCurrentDay) {
      finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);
      
      for(Date date : offDays) {
        if(DateUtils.isSameDay(finishDate, date)) {
          finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);
          finishDate.setHours(8);
          finishDate.setMinutes(0);
          finishDate.setSeconds(0);
        }
      }
      
      finishDate.setHours(8 + 8);
      finishDate.setMinutes(0);
      finishDate.setSeconds(0);
      
      if((finishDate.getHours() == 12 && finishDate.getMinutes() > 0) || finishDate.getHours() > 12) {
        finishDate.setTime(finishDate.getTime() + 90 * 60 * 1000);
      }
    } else {
      if(notUsedTime > 0) {
        finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);
        finishDate.setHours(8);
        finishDate.setMinutes(0);
        finishDate.setSeconds(0);
      }
      boolean isOffDay = false;
      for(Date date : offDays) {
        if(DateUtils.isSameDay(finishDate, date)) {
          finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);
          isOffDay = true;
        }
      }
      
      if(isOffDay) {
        finishDate.setHours(8);
        finishDate.setMinutes(0);
        finishDate.setSeconds(0);
      }
      
      finishDate.setTime(finishDate.getTime() + notUsedTime);
      if(finishDate.getHours() > 12 || ( finishDate.getMinutes() > 0 && finishDate.getHours() == 12)) {
        finishDate.setTime(finishDate.getTime() + 90 * 60 * 1000);
      }
      
      if((finishDate.getHours() == 17 && finishDate.getMinutes() > 30) || (finishDate.getHours() > 17 || 
          (isOffCurrentDay && finishDate.getDay() != currentDate.getDay()))) {
        finishDate.setTime(finishDate.getTime() + 14 * 60 * 60 * 1000 + 30 * 60 * 1000);
      }
    }
    return finishDate;
  }

  @SuppressWarnings("deprecation")
  static Date calLessEightHours(int hours, List<Date> offDays) {
    Date currentDate = new Date();
    Date finishDate = setWorkRangeDate();

    long notUsedTime = 0;
    boolean isOffCurrentDay = true;
    if(!isDayOff(finishDate, offDays)) {
      notUsedTime = getMoreTime(finishDate, hours);
      isOffCurrentDay = false;
    }
    if(notUsedTime < 0) return finishDate;

    if(isOffCurrentDay) {
      finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);

      for(Date date : offDays) {
        if(DateUtils.isSameDay(finishDate, date)) {
          finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);
          finishDate.setHours(8);
          finishDate.setMinutes(0);
          finishDate.setSeconds(0);
        }
      }

      finishDate.setHours(8 + hours);
      finishDate.setMinutes(0);
      finishDate.setSeconds(0);

      if((finishDate.getHours() == 12 && finishDate.getMinutes() > 0) || finishDate.getHours() > 12) {
        finishDate.setTime(finishDate.getTime() + 90 * 60 * 1000);
      }
    } else {
      if(notUsedTime > 0) {
        finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);
        finishDate.setHours(8);
        finishDate.setMinutes(0);
        finishDate.setSeconds(0);
      }
      boolean isOffDay = false;
      for(Date date : offDays) {
        if(DateUtils.isSameDay(finishDate, date)) {
          finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);
          isOffDay = true;
        }
      }

      if(isOffDay) {
        finishDate.setHours(8);
        finishDate.setMinutes(0);
        finishDate.setSeconds(0);
      }

      finishDate.setTime(finishDate.getTime() + notUsedTime);

      if((finishDate.getHours() == 12 && finishDate.getMinutes() > 0) || finishDate.getHours() > 12) {
        finishDate.setTime(finishDate.getTime() + 90 * 60 * 1000);
      }

      if((finishDate.getHours() == 17 && finishDate.getMinutes() > 30) || (finishDate.getHours() > 17 || 
          (isOffCurrentDay && finishDate.getDay() != currentDate.getDay()))) {
        finishDate.setTime(finishDate.getTime() + 14 * 60 * 60 * 1000 + 30 * 60 * 1000);
      }
    }
    return finishDate;
  }

  @SuppressWarnings("deprecation")
  static long getMoreTime(Date date, int hours) {
	  Date currentDate = new Date();
	  long time = -1;
	  long dateTime = date.getTime();
	  boolean isMorning = false;

	  if(date.getHours() < 12 && date.getHours() >= 8) {
		  isMorning = true;
	  }

	  date.setTime(date.getTime() + hours * 60 * 60 * 1000);
	  if(isMorning && ((date.getHours() == 12 && date.getMinutes() > 0) || (date.getHours() > 12))) {
		  date.setTime(date.getTime() + 90 * 60 * 1000);
	  }

	  if(date.getHours() > 17 || (date.getHours() == 17 && date.getMinutes() > 30)) {
		  date.setHours(17);
		  date.setMinutes(30);
		  date.setSeconds(0);

		  time = hours * 60 * 60 * 1000 - (date.getTime() - dateTime);
		  if(isMorning) time = time + 90 * 60 * 1000;
	  }

	  if(date.getDay() != currentDate.getDay() && date.getHours() < 8) {
		  date.setTime(date.getTime() - 24 * 60 * 60 * 1000);
		  time = hours * 60 * 60 * 1000 - (date.getTime() - dateTime);
	  }
	  return time;
  }

  @SuppressWarnings("deprecation")
  static long getMoreTimeThanEight(Date date, int hours) {
    long dateTime = date.getTime();

    boolean isMorning = false;
    if(date.getHours() < 12) {
      isMorning = true;
    }

    date.setHours(17);
    date.setMinutes(30);
    date.setSeconds(0);

    long time = hours * 60 * 60 * 1000 - (date.getTime() - dateTime);
    if(isMorning) time = time + 90 * 60 * 1000;

    return time;
  }

  @SuppressWarnings("deprecation")
  static Date setWorkRangeDate() {
    Date finishDate = new Date();
    if(finishDate.getHours() < 8) {
      finishDate.setHours(8);
      finishDate.setMinutes(0);
      finishDate.setSeconds(0);
    } else if((finishDate.getHours() >= 12 && finishDate.getMinutes() >= 0) && (finishDate.getHours() <= 13 && finishDate.getMinutes() < 30)) {
      finishDate.setHours(13);
      finishDate.setMinutes(30);
      finishDate.setSeconds(0);
    } else if(finishDate.getHours() > 17 || (finishDate.getHours() == 17 && finishDate.getMinutes() >= 30)) {
      finishDate.setTime(finishDate.getTime() + 24 * 60 * 60 * 1000);
      finishDate.setHours(8);
      finishDate.setMinutes(0);
      finishDate.setSeconds(0);
    }
    return finishDate;
  }

  public static boolean isDayOff(Date today, List<Date> offDays) {
    for(Date date : offDays) {
      if(DateUtils.isSameDay(today, date)) return true;
    }
    return false;
  }
  
  public static long getTotalHour(Date start,Date finish ,List<Date> offDays) {
	  long totalHour = 0l;
	  if(start.getTime()>= finish.getTime()){
		  return totalHour;
	  }
	  Calendar calendarFinishDay = Calendar.getInstance();
	  Calendar calendarStartDay = Calendar.getInstance();
	  Calendar calendarFinishLuch = Calendar.getInstance();
	  Calendar calendarStartLuch = Calendar.getInstance();
	  Calendar calendarStart = Calendar.getInstance();
	  calendarStart.setTime(start);
	  calendarStart=checkStartDay(start);
	  Calendar calendarFinish = Calendar.getInstance();
	  calendarFinish.setTime(finish);
	  calendarFinish=checkFinishDay(finish);
	  
	  if(checkIsAnotherDay(start,finish)){
		  if(isDayOff(finish, offDays)){
			  return totalHour;
		  }
		  calendarStartLuch.setTime(finish);
		  calendarStartLuch.set(Calendar.MILLISECOND, 0);
		  calendarStartLuch.set(Calendar.SECOND, 0);
		  calendarStartLuch.set(Calendar.MINUTE, 0);
		  calendarStartLuch.set(Calendar.HOUR_OF_DAY, 12);
		  
		  calendarFinishLuch.setTime(finish);
		  calendarFinishLuch.set(Calendar.MILLISECOND, 0);
		  calendarFinishLuch.set(Calendar.SECOND, 0);
		  calendarFinishLuch.set(Calendar.MINUTE, 30);
		  calendarFinishLuch.set(Calendar.HOUR_OF_DAY, 13);
		  
		  if(calendarFinish.getTimeInMillis()>calendarFinishLuch.getTimeInMillis()&&calendarStart.getTimeInMillis()< calendarStartLuch.getTimeInMillis()){
			  totalHour += ((calendarFinish.getTimeInMillis()-calendarStart.getTimeInMillis()- (1.5* 60 * 60 * 1000)));
		  }else if (calendarFinish.getTimeInMillis()<calendarFinishLuch.getTimeInMillis()&&calendarFinish.getTimeInMillis()>calendarStartLuch.getTimeInMillis()
				  &&calendarStart.getTimeInMillis()<calendarStartLuch.getTimeInMillis()) {
			  totalHour += ((calendarStartLuch.getTimeInMillis()-calendarStart.getTimeInMillis()));
		}else if (calendarStart.getTimeInMillis()<calendarFinishLuch.getTimeInMillis()&&calendarStart.getTimeInMillis()>calendarStartLuch.getTimeInMillis()
				  &&calendarFinish.getTimeInMillis()>calendarFinishLuch.getTimeInMillis()) {
			  totalHour += ((calendarFinish.getTimeInMillis()-calendarFinishLuch.getTimeInMillis()));
		}else if (calendarStart.getTimeInMillis()<calendarFinishLuch.getTimeInMillis()&&calendarStart.getTimeInMillis()>calendarStartLuch.getTimeInMillis()
				  &&calendarFinish.getTimeInMillis()<calendarFinishLuch.getTimeInMillis()&&calendarFinish.getTimeInMillis()>calendarStartLuch.getTimeInMillis()) {
			 return totalHour;
		}else{
			totalHour += ((calendarFinish.getTimeInMillis()-calendarStart.getTimeInMillis()));
		}
		  return totalHour;
	  }
	  
	  calendarFinishDay.setTime(start);
	  calendarFinishDay.set(Calendar.MILLISECOND, 0);
	  calendarFinishDay.set(Calendar.SECOND, 0);
	  calendarFinishDay.set(Calendar.MINUTE, 30);
	  calendarFinishDay.set(Calendar.HOUR_OF_DAY, 17);
		if(calendarStart.getTimeInMillis()< calendarFinishDay.getTimeInMillis()){
			if(!isDayOff(start, offDays)){
				  calendarStartLuch.setTime(start);
				  calendarStartLuch.set(Calendar.MILLISECOND, 0);
				  calendarStartLuch.set(Calendar.SECOND, 0);
				  calendarStartLuch.set(Calendar.MINUTE, 0);
				  calendarStartLuch.set(Calendar.HOUR_OF_DAY, 12);
				  
				  calendarFinishLuch.setTime(start);
				  calendarFinishLuch.set(Calendar.MILLISECOND, 0);
				  calendarFinishLuch.set(Calendar.SECOND, 0);
				  calendarFinishLuch.set(Calendar.MINUTE, 30);
				  calendarFinishLuch.set(Calendar.HOUR_OF_DAY, 13);
				  
				  if(calendarStart.getTimeInMillis()< calendarStartLuch.getTimeInMillis()){
					  totalHour += ((calendarFinishDay.getTimeInMillis()-calendarStart.getTimeInMillis()- (1.5* 60 * 60 * 1000)));
				  }else if(calendarStart.getTimeInMillis()> calendarStartLuch.getTimeInMillis() &&calendarStart.getTimeInMillis()< calendarFinishLuch.getTimeInMillis() ){
					  totalHour += ((calendarFinishDay.getTimeInMillis()-calendarFinishLuch.getTimeInMillis()));
				}else{
					totalHour += ((calendarFinishDay.getTimeInMillis()-calendarStart.getTimeInMillis()));
				}
				 
			 }		
		}
		 
		calendarStartDay.setTime(finish);
		calendarStartDay.set(Calendar.MILLISECOND, 0);
		calendarStartDay.set(Calendar.SECOND, 0);
		calendarStartDay.set(Calendar.MINUTE, 0);
		calendarStartDay.set(Calendar.HOUR_OF_DAY, 8);
	if(calendarFinish.getTimeInMillis()> calendarStartDay.getTimeInMillis()){
	 if(!isDayOff(finish, offDays)){
		 calendarStartLuch.setTime(finish);
		 calendarStartLuch.set(Calendar.MILLISECOND, 0);
		  calendarStartLuch.set(Calendar.SECOND, 0);
		  calendarStartLuch.set(Calendar.MINUTE, 0);
		  calendarStartLuch.set(Calendar.HOUR_OF_DAY, 12);
		  
		  calendarFinishLuch.setTime(finish);
		  calendarFinishLuch.set(Calendar.MILLISECOND, 0);
		  calendarFinishLuch.set(Calendar.SECOND, 0);
		  calendarFinishLuch.set(Calendar.MINUTE, 30);
		  calendarFinishLuch.set(Calendar.HOUR_OF_DAY, 13);
		  
		  if(calendarFinish.getTimeInMillis()> calendarFinishLuch.getTimeInMillis()){
			  totalHour += (double)((calendarFinish.getTimeInMillis()-calendarStartDay.getTimeInMillis()- (1.5* 60 * 60 * 1000)));
		  }else if(calendarFinish.getTimeInMillis()<= calendarFinishLuch.getTimeInMillis()&&calendarFinish.getTimeInMillis()>= calendarStartLuch.getTimeInMillis()){
			  totalHour += (double)((calendarStartLuch.getTimeInMillis()-calendarStartDay.getTimeInMillis()));
			  
		  }else{
			totalHour += (double)((calendarFinish.getTimeInMillis()-calendarStartDay.getTimeInMillis()));
		}
		 
	 }
	}
	  
	 calendarStart.set(Calendar.MILLISECOND, 0);
	 calendarStart.set(Calendar.SECOND, 0);
	 calendarStart.set(Calendar.MINUTE, 0);
	 calendarStart.set(Calendar.HOUR_OF_DAY, 0);

	 calendarFinish.set(Calendar.MILLISECOND, 0);
	 calendarFinish.set(Calendar.SECOND, 0);
	 calendarFinish.set(Calendar.MINUTE, 0);
	 calendarFinish.set(Calendar.HOUR_OF_DAY, 0);
	 
	  for(long i = calendarStart.getTimeInMillis()+(24*60*60*1000) ;i< calendarFinish.getTimeInMillis();i=i+(24*60*60*1000) ){
		  if(!isDayOff(new Date(i), offDays)){
			  totalHour +=8*60*60*1000;
		  }
	  }
	  return totalHour;
	}
  static boolean checkIsAnotherDay(Date start, Date finish){
	  if(DateUtils.isSameDay(start, finish)){
		  return true;
	  }
	  return false;
  }
  static Calendar checkStartDay(Date start){
	  Calendar calendarStartDay = Calendar.getInstance();
	  Calendar calendarFinishDay = Calendar.getInstance();
	  Calendar calendarStart = Calendar.getInstance();
	  calendarStart.setTime(start);
	  calendarStartDay.setTime(start);
		calendarStartDay.set(Calendar.MILLISECOND, 0);
		calendarStartDay.set(Calendar.SECOND, 0);
		calendarStartDay.set(Calendar.MINUTE, 0);
		calendarStartDay.set(Calendar.HOUR_OF_DAY, 8);
		
		  calendarFinishDay.setTime(start);
		  calendarFinishDay.set(Calendar.MILLISECOND, 0);
		  calendarFinishDay.set(Calendar.SECOND, 0);
		  calendarFinishDay.set(Calendar.MINUTE, 30);
		  calendarFinishDay.set(Calendar.HOUR_OF_DAY, 17);
		if(calendarStart.getTimeInMillis() < calendarStartDay.getTimeInMillis()){
			return calendarStartDay;
		}else if (calendarStart.getTimeInMillis() > calendarFinishDay.getTimeInMillis()) {
			return calendarFinishDay;
		}
		return calendarStart;
  }

  static Calendar checkFinishDay(Date finish){
	  Calendar calendarStartDay = Calendar.getInstance();
	  Calendar calendarFinishDay = Calendar.getInstance();
	  Calendar calendarFinish = Calendar.getInstance();
	  calendarFinish.setTime(finish);
	  calendarStartDay.setTime(finish);
		calendarStartDay.set(Calendar.MILLISECOND, 0);
		calendarStartDay.set(Calendar.SECOND, 0);
		calendarStartDay.set(Calendar.MINUTE, 0);
		calendarStartDay.set(Calendar.HOUR_OF_DAY, 8);
		
	  calendarFinishDay.setTime(finish);
	  calendarFinishDay.set(Calendar.MILLISECOND, 0);
	  calendarFinishDay.set(Calendar.SECOND, 0);
	  calendarFinishDay.set(Calendar.MINUTE, 30);
	  calendarFinishDay.set(Calendar.HOUR_OF_DAY, 17);
	  if(calendarFinishDay.getTimeInMillis() < calendarFinish.getTimeInMillis()){
		  return calendarFinishDay;
	  }else if (calendarFinish.getTimeInMillis() < calendarStartDay.getTimeInMillis()) {
			return calendarStartDay;
		}
	  return calendarFinish;
  }
}
