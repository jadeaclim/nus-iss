package com.team1.iss.trial.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {
	
	public static long ONE_DAY_TOTAL_MILLION_SECONDS=1000*60*60*24;
	public static String TIME_ZONE="Asia/Singapore";
	
    /**
     * @return current timestamp
     */
    public static long getCurrentTimestamp(){
        return new Date().getTime();
    }

    /**
     * month start timestamp
     *
     * @param timeStamp
     * @return
     */
    public static Long getMonthStartTimestamp(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * month end timestamp
     * @param timeStamp
     * @return
     */
    public static Long getMonthEndTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * year start timestamp
     * @param timeStamp
     * @return
     */
    public static Long getYearStartTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.DATE, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * year end timestamp
     *
     * @param timeStamp
     * @return
     */
    public static Long getYearEndTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTimeInMillis(timeStamp);
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTimeInMillis();
    }

    /**
     * convert timestamp to yyyy-MM-dd HH:mm format
     * @param timestamp
     * @return
     */
    public static String convertTimestampToTimeFormat(long timestamp){
        Timestamp ts = new Timestamp(timestamp);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String tsStr = sdf.format(ts);
        return tsStr;
    }
    
    /**
     * convert timestamp to HH:mm format
     * @param timestamp
     * @return
     */
    public static String convertTimestampToHHmm(long timestamp){
        Timestamp ts = new Timestamp(timestamp);
        DateFormat sdf = new SimpleDateFormat("HH:mm");

        String tsStr = sdf.format(ts);
        return tsStr;
    }

    /**
     * convert timestamp to HH format
     * @param timestamp
     * @return
     */
    public static String convertTimestampToHH(long timestamp){
        Timestamp ts = new Timestamp(timestamp);
        DateFormat sdf = new SimpleDateFormat("HH");

        String tsStr = sdf.format(ts);
        return tsStr;
    }
    
    /**
     * convert timestamp to yyyy-MM-dd format
     * @param timestamp
     * @return
     */
    public static String convertTimestampToyyyMMdd(long timestamp){
        Timestamp ts = new Timestamp(timestamp);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String tsStr = sdf.format(ts);
        return tsStr;
    }

    /**
     * get the week day by timestamp
     * @param timeStamp
     * @return
     */
    public static String getWeek(long timeStamp) {
        String week = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                week += "Sunday";
                break;
            case 2:
                week += "Monday";
                break;
            case 3:
                week += "Tuesday";
                break;
            case 4:
                week += "Wednesday";
                break;
            case 5:
                week += "Thursday";
                break;
            case 6:
                week += "Friday";
                break;
            case 7:
                week += "Saturday";
                break;
        }
        return week;
    }
}
