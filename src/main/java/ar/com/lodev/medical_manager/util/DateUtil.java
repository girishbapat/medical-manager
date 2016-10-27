package ar.com.lodev.medical_manager.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class DateUtil {
	
	public static long getDifferenceInMinutes(Date dateInit, Date dateFinish){
        Calendar dateInitCal = Calendar.getInstance();
        dateInitCal.setTime(dateInit);

        Calendar dateFinishCal = Calendar.getInstance();
        dateFinishCal.setTime(dateFinish);

        long diff = dateFinishCal.getTimeInMillis() - dateInitCal.getTimeInMillis(); //result in millis
        long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        //long diffMinutes = diff / (60 * 1000) % 60; 
        return diffMinutes;
    }

}
