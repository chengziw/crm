package com.amayadream.webchat.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * FileName: TimeUtils
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description:
 * History:
 */
public class TimeUtils {

    private static final String ISO8601_DATE_FORMAT = "yyyyMMddHHmmss";

    private static final SimpleDateFormat df = new SimpleDateFormat(
            ISO8601_DATE_FORMAT);

    private static final SimpleDateFormat df2 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat df3 = new SimpleDateFormat(
            "yyyyMMdd");

    private static final SimpleDateFormat df4 = new SimpleDateFormat(
            "yyyyMMdd");

    private static final String YYMMDDHHMMSS_N = "yyyyMMddHHmmss";
    private static final String YYMMDDHHMMSSSS_N = "yyyyMMddHHmmssSSS";
    private static final String YYMMDD_N = "yyyyMMdd";
    private static final String YYMM_N = "yyyyMM";
    private static final String YYMMDDHHMMSS_M = "yyyy-MM-dd HH:mm:ss";
    private static final String YYMMDD_M = "yyyy-MM-dd";
    private static final String HHMMSS_M = "HH:mm:ss";

    public static String getCurrent() {
        return df.format(new Date());
    }

    public static String get(Date date) {
        return df.format(date);
    }

    public static String getYyyymmddhhmmss(Date date) {
        return df2.format(date);
    }

    public static String getYyyymmdd(Date date) {
        return df3.format(date);
    }

    public static String getYyyymm(Date date) {
        return df4.format(date);
    }

    public static String getCurrentYyyymmddhhmmss() {
        return getYyyymmddhhmmss(new Date());
    }

    public static String getCurrentYyyymmdd() {
        return getYyyymmdd(new Date());
    }

    public static String getCurrentYyyymm() {
        return getYyyymm(new Date());
    }

    public static String getCurrentYYMMDDHHMMSS_N() {
        return getYYMMDDHHMMSS_N(new Date());
    }

    public static String getCurrentYYMMDDHHMMSSFF_N() {
        return getYYMMDDHHMMSSSS_N(new Date());
    }

    public static String getYYMMDD_N(Date date) {
        return get(date, YYMMDD_N);
    }

    public static String getYYMM_N(Date date) {
        return get(date, YYMM_N);
    }

    public static String getYYMMDDHHMMSS_N(Date date) {
        return get(date, YYMMDDHHMMSS_N);
    }

    public static String getYYMMDDHHMMSSSS_N(Date date) {
        return get(date, YYMMDDHHMMSSSS_N);
    }

    public static String getYYMMDDHHMMSS_M(Date date) {
        return get(date, YYMMDDHHMMSS_M);
    }

    public static String getYYMMDD_M(Date date) {
        return get(date, YYMMDD_M);
    }

    public static String get(Date date, String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
//		df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    public static String getHHMMSS_M(Date date) {
        return get(date, HHMMSS_M);
    }

    public static Date addDateBySecond(Date date, int d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, d);
        return cal.getTime();
    }

    public static Date addDateByMin(Date date, int d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, d);
        return cal.getTime();
    }

    public static Date addDateByMonth(Date date, int d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, d);
        return cal.getTime();
    }

    public static void main(String[] args) {
        String tt = getYYMMDDHHMMSS_N(new Date());
        System.out.println(tt);
        System.out.println(tt.length());
//		System.out.println(getYYMMDDHHMMSS_N(addDateBySecond(new Date(), (60 * 15))));
    }

    /**
     * @param addtime
     * @param now
     * @param day
     * @Title: isLatestDay
     * @Description: TODO 判断当前时间是否在n天某个时间内
     * @return: boolean
     */
    public static boolean isLatestDay(Date addtime, Date now, int day) {
        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(now);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, day);  //设置为n天前
        Date beforedays = calendar.getTime();   //得到n天前的时间
        if (beforedays.getTime() < addtime.getTime()) {
            return true;
        } else {
            return false;
        }
    }

}