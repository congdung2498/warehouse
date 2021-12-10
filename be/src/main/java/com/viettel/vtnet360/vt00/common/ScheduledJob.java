package com.viettel.vtnet360.vt00.common;

import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.viettel.vtnet360.checking.service.CheckingService;
import com.viettel.vtnet360.kitchen.service.LunchService;
import com.viettel.vtnet360.vt00.vt000000.service.VT000000Service;
import com.viettel.vtnet360.vt01.vt010000.service.VT010000Service;
import com.viettel.vtnet360.vt02.vt020006.service.VT020006Service;
import com.viettel.vtnet360.vt03.vt030000.service.VT030000Service;

@Component
public class ScheduledJob {
	private final Logger logger = Logger.getLogger(this.getClass());
	// Example patterns:
	// "0 0 * * * *" = the top of every hour of every day.
	// "*/10 * * * * *" = every ten seconds.
	// "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
	// "0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day.
	// "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
	// "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
	// "0 0 0 25 12 ?" = every Christmas Day at midnight

	@Autowired
	private VT020006Service vt020006Service;

	@Autowired
	private VT010000Service vt010000Service;

	@Autowired
	VT030000Service VT030000Service;

	@Autowired
	VT000000Service vt000000Service;

	@Autowired
	private LunchService lunchService;
	
	@Autowired
	private CheckingService checkingService;

	@Autowired
	Properties smsMessage;
	
	
	// run each 16:30 in every day to sent sms head chef
	@Scheduled(cron = "0 30 16 * * *")
	public void sendSmsToChef16h30() {
	  System.out.println("sendSmsToChef16h30");
		try {
			vt020006Service.sendSmsToChef16h30();
			vt020006Service.saveDailyMeals();
		} catch (Exception e) {

		}
	}
	
	@Scheduled(cron = "0 30 12 * * *")
  public void sendLunchVoteMessage13h() {
	  System.out.println(this.getClass().getName() + " sendLunchVoteMessage12h30 ");
    try {
      lunchService.createLunchVoteMessage();
    } catch (Exception e) {
    }
  }
	
	//TODO: keep doing
	@Scheduled(cron = "0 0 6 * * *")
  public void updateUnitLunch() {
    System.out.println(this.getClass().getName() + " updateUnitLunch ");
    try {
      lunchService.updateUnitLunch();
    } catch (Exception e) {
    }
  }
	
	// run each 8:42 in every day to sent sms head chef
	@Scheduled(cron = "0 42 8 * * *")
	public void sendSmsToChef8h40() {
		try {
			vt020006Service.sendSmsToChef8h40();
		} catch (Exception e) {
		}
	}

	// run each 5th day in previous month to sent sms total meal info
	@Scheduled(cron = "0 0 0 4 1/1 *")
//	@Scheduled(cron = "0 30 11 1/1 * *")
//	@Scheduled(cron = "0 0/30 * 1/1 * *")
	public void sendInfoMealEachMonth() {
		try {
			vt020006Service.sendSmsMealInfEachMonthToEmployee();
		} catch (Exception e) {
		}
	}

	@Scheduled(cron="*/60 * * * * *")
	public void createRemindCheckIn() {
	  try {
	    checkingService.createRemindCheckIn();
	  } catch (Exception e) {
		  logger.error(e.getMessage(), e);
	  }
	}

	 /**
	 * Scheduled for send Notify Each Minute to User not yet recive
	 *
	 * @author KienHT
	 */
	@Scheduled(cron="*/60 * * * * *")
	public void sendNotifyEachMinute() {
	  try {
	    vt000000Service.sendNotifyEachMinute();
	  } catch (Exception e) {
	  }
	}
}
