package com.icsys.batch.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: mocca wang
 * Date: 2007-3-30
 * Time: 16:34:45
 * 
 */
public class DateUtil {

    private static SimpleDateFormat fullFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS");

    private static SimpleDateFormat dateForamt1 = new SimpleDateFormat(
            "yyyy-MM-dd");

    private static SimpleDateFormat dateForamt2 = new SimpleDateFormat(
            "yyyy/MM/dd");

    private static SimpleDateFormat dateForamt3 = new SimpleDateFormat(
            "yyyyMMdd");

    public static String getFullText(Date date) {
        return fullFormat.format(date);
    }

    public static String getDateStrWithDASH(Date date) {
        return dateForamt1.format(date);
    }

    public static String getDateStrWithSPLASH(Date date) {
        return dateForamt2.format(date);
    }

    public static String getDateStri(Date date) {
        return dateForamt3.format(date);
    }

    public static Date getDateFromShortStr(String date) {
        try {
            return dateForamt3.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("parse date str err");
        }
    }
    /*format String to Date*/
    public static Date formatDate(String pattern, long date) {
        SimpleDateFormat f = new SimpleDateFormat(pattern);
        try {
			return f.parse(f.format(new Date(date)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			 throw new RuntimeException("parse date str err");
		}
    }

    /*format Date to String*/
    public static String formatDate(String pattern, Date date) {
        SimpleDateFormat f = new SimpleDateFormat(pattern);
        return f.format(date);
    }
    
    /*format Date to String*/
    public static String formatDate(String pattern, String date) {
        SimpleDateFormat f = new SimpleDateFormat(pattern);
        try {
			return f.format(f.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			 throw new RuntimeException("parse date str err");
		}
    }


    /**
     * 
     *
     * @param date1 [String]
     * @param date2 [String] notice: ca1<ca2
     * @return int
     * @author: wuhailong
     */
    public static int getdaysbetween(String date1, String date2) {
        int iYear = Integer.parseInt(date1.substring(0, 4));
        int iMonth = Integer.parseInt(date1.substring(4, 6)) - 1;
        int iDay = Integer.parseInt(date1.substring(6, 8));
        GregorianCalendar ca1 = new GregorianCalendar(iYear, iMonth, iDay);

        iYear = Integer.parseInt(date2.substring(0, 4));
        iMonth = Integer.parseInt(date2.substring(4, 6)) - 1;
        iDay = Integer.parseInt(date2.substring(6, 8));
        GregorianCalendar ca2 = new GregorianCalendar(iYear, iMonth, iDay);

        int year1 = ca1.get(Calendar.YEAR);
        int year2 = ca2.get(Calendar.YEAR);

        int dayofYear1 = ca1.get(Calendar.DAY_OF_YEAR);
        int dayofYear2 = ca2.get(Calendar.DAY_OF_YEAR);

//        int days = 0;
        int ip = 0;
        for (int i = year1; i < year2; i++) {
            if (isLeapyear(i)) {
                ip = ip + 366;
                // ip =i;
            } else {
                ip = ip + 365;
                // ip=i;
            }
        }
        int temp = ip + (dayofYear2 - dayofYear1 + 1);
        return temp;
    }

    /**
     * 
     *
     * @param year [int] 
     * @return boolean 
     * @author : wuhailong
     */
    public static boolean isLeapyear(int year) {
        boolean isproyear = false;
        if ((year % 400 == 0) | (year % 100 != 0 && year % 4 == 0)) {
            isproyear = true;
        } else {
            isproyear = false;
        }
        return isproyear;
    }


    public static Date getAfterNDay(Date date, int n) throws Exception {

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(GregorianCalendar.DATE,n);
        return gc.getTime();
//        long nDay = date.getTime() + (long) (n) * 86400000;
//        String nDayString = new java.text.SimpleDateFormat("yyyy/MM/dd").format(new Date(nDay));
//        return GetSqlDateFromStr(nDayString);
    }


//    public static java.sql.Date GetSqlDateFromStr(String strDt) {
//        strDt = strDt.replace(' ', '-');
//        strDt = strDt.replace('.', '-');
//        strDt = strDt.replace('/', '-');
//        return java.sql.Date.valueOf(strDt);
//    }

    public static String getDateString(String date) {
        return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    }


    public static void main(String args[]) {
//        System.out.println(getFullText(new Date()));
//
//        System.out.println(getDateStrWithDASH(new Date()));
//
//        System.out.println(getDateStrWithSPLASH(new Date()));
//
//        System.out.println(getDateFromShortStr("20031021"));
        try {
			System.out.println(getDateStri(getAfterNDay(getDateFromShortStr("20140321"),-31)));
			System.out.println(getdaysbetween("20130208","20140309"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
