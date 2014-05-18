/*HISTORY
* CATEGORY			 :- ALARM MANAGER
* DEVELOPER			 :- VIKALP PATEL
* AIM 				 :- WAKE UP SERVICES ON ALARM
* DESCRIPTION 		 :- SETTING ALARM SERVICE BASED ON SYNC FREQUENCY -  AUTOSYNC
* 
* S - START E- END C- COMMENTED U -EDITED A -ADDED
* --------------------------------------------------------------------------------------------------------------------
* INDEX		 DEVELOPER		 DATE		 FUNCTION			DESCRIPTION
* --------------------------------------------------------------------------------------------------------------------
* P3AG1     VIKALP PATEL  09/04/2014     AGGRESSIVE         APPLICATION PESTERING TO LOGIN
* P3B01     VIKALP PATEL  14/04/2014     BUG                STOPING ALARM
* P3B02     VIKALP PATEL  21/04/2014     BUG                AGRESSIVE START OF SERVICE - STATIC CLASS TO INSTANCEABLE CLASS
* --------------------------------------------------------------------------------------------------------------------
*/

package com.netdoers.com.service;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.netdoers.com.SmartConsultant;

public class ServiceAlarm extends WakefulBroadcastReceiver {
	public AlarmManager alarmMgr;
	public AlarmManager stopAlarmMgr;
	public PendingIntent alarmIntent;
	public PendingIntent stopAlarmIntent;
	public static int REQUEST_CODE = 388225;//ADDED P3B01
	Context context;
	 public void onReceive(Context context, Intent intent) {   
	        Intent service = new Intent(context, UploadData.class);
	        
	        startWakefulService(context, service);
	    }

	public  void startServiceAlarm(String times)
	{
		context = SmartConsultant.getApplication().getApplicationContext();
		alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);		
		Intent intent = new Intent(SmartConsultant.getApplication().getApplicationContext(), ServiceAlarm.class);
		alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//		alarmIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);//EDITED P3B01

		// Set the alarm to start at 9:00 p.m.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 22);
		calendar.set(Calendar.MINUTE, 45);
//		SA P2M01
		if(SmartConsultant.getPreferences().getUserLoginDTO().getSign_id()!=null) //ADDDED P3AG1
		{
			switch(Integer.parseInt(times))
			{
			case 0://midnight
				alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
				Log.i("Alarm", "Every midnight from 9pm");
//				toStopServiceAlarm(times);//ADDED P3B01
				break;
			case 1://every hour
				
				Calendar calendarHour = Calendar.getInstance();
				calendarHour.setTimeInMillis(System.currentTimeMillis());
				
				alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarHour.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, alarmIntent);
				Log.i("Alarm", "Every Hour");
				break;
			case 2://twice a day
				alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, alarmIntent);
				Log.i("Alarm", "Twice a day");
				break;
			case 3://once in a day
				alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
				Log.i("Alarm", "Once in a day");
				break;
			case 4://once in week
				alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 604800000, alarmIntent);
				Log.i("Alarm", "Once in a week");
				break;
			case 5://manually
				Log.i("Alarm", "Update Manually");
				toStopServiceAlarm(times);//ADDED P3B01
				break;
			}
		}
//		EA P2M01
	}
	
	public void toStopServiceAlarm(String times)
	{
		context = SmartConsultant.getApplication().getApplicationContext();
		stopAlarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);		
		Intent intentStop = new Intent(SmartConsultant.getApplication().getApplicationContext(), TestStopService.class);
//		stopAlarmIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intentStop, 0);
		stopAlarmIntent = PendingIntent.getBroadcast(context, 0, intentStop, 0);

		// Set the alarm to start at 11:45 p.m.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 45);//EDITED P3B01
		
		//SA P3B01
		switch(Integer.parseInt(times))
		{
		case 0://midnight
			stopAlarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, stopAlarmIntent);
			Log.i("Stop Alarm", "Every midnight from 9pm");
			try {
				if (stopAlarmMgr != null) {
					stopAlarmMgr.cancel(stopAlarmIntent);
				}
			} catch (Exception e) {
				Log.e("Stop Alarm", e.toString());
			}
			break;
		case 1://every hour
			stopAlarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, stopAlarmIntent);
			Log.i("Alarm", "Every Hour");
			break;
		case 2://twice a day
			stopAlarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, stopAlarmIntent);
			Log.i("Alarm", "Twice a day");
			break;
		case 3://once in a day
			stopAlarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, stopAlarmIntent);
			Log.i("Alarm", "Once in a day");
			break;
		case 4://once in week
			stopAlarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 604800000, stopAlarmIntent);
			Log.i("Alarm", "Once in a week");
			break;
		case 5://manually
			Log.i("Alarm", "Update Manually");
			try {
			if (alarmMgr != null) {
				alarmMgr.cancel(alarmIntent);
			}
			if (stopAlarmMgr != null) {
				stopAlarmMgr.cancel(stopAlarmIntent);
			}
		} catch (Exception e) {
			Log.e("Stop Alarm", e.toString());
		}
			break;
		}
		//EA P3B01
		Log.i("Service", "Stop Service!");
	}
	
	public class StopServiceAlarm {
		public void stopServiceAlarm() {
//			alarmMgr.cancel(alarmIntent);
			Log.i("Service", "Alarm cancelled service!");
		}
	}
}
