/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class DateUtil {
    
    private static final SimpleDateFormat week = new SimpleDateFormat("w");
    private static final SimpleDateFormat day = new SimpleDateFormat("yyyyD");
    private static final SimpleDateFormat time = new SimpleDateFormat("HHmmss");
    private static final SimpleDateFormat month = new SimpleDateFormat("MM");
    private static final SimpleDateFormat general = new SimpleDateFormat("yyyyDHHmmss");
    
    private static final Calendar calendar = Calendar.getInstance();
    
    public static int compareWeek(Date d1, Date d2){
        return week.format(d1).compareTo(week.format(d2));
    }
    
    public static int compareDay(Date d1, Date d2){
        return day.format(d1).compareTo(day.format(d2));
    }
    
    public static int compareTime(Date d1, Date d2){
        return time.format(d1).compareTo(time.format(d2));
    }
    
    public static int compare(Date d1, Date d2){
        return general.format(d1).compareTo(general.format(d2));
    }
    
    public static Date min(Date d1, Date d2){
        return d1.before(d2) ? d1 : d2;
    }
    
    public static Date max(Date d1, Date d2){
        return d1.before(d2) ? d2 : d1;
    }

    public static int compareMonth(Date d1, Date d2) {
        return month.format(d1).compareTo(month.format(d2));
    }
    
    public static int getDayOfWeek(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    
    public static Date add(Date date, int type, int amount){
        calendar.setTime(date);
        calendar.add(type, amount);
        return calendar.getTime();
    }

    public static int getHour(Date nuDate) {
        calendar.setTime(nuDate);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinuteOfDay(Date nuDate) {
        calendar.setTime(nuDate);
        return calendar.get(Calendar.HOUR_OF_DAY)*60+calendar.get(Calendar.MINUTE);
    }
    
    public static Date clone(Date date){
        return new Date(date.getTime());
    }
}
