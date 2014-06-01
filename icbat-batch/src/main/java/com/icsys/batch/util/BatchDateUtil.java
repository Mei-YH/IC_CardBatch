package com.icsys.batch.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BatchDateUtil {
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 返回相对于当前日期(T)的上一日期(T-1)的字符串格式：yyyyMMdd
	 * @param currentDate  当前日期的字符串格式(yyyyMMdd)
	 * @return
	 * @throws Exception
	 */
	public static String getLastDateWithStringFormat(String currentDate) throws Exception{
		Date lastDate = getLastDate(format.parse(currentDate));
		return format.format(lastDate);
	}
	/**
	 * 返回相对于当前日期(T)的下一日期(T+1)的字符串格式：yyyyMMdd
	 * @param currentDate  当前日期的字符串格式(yyyyMMdd)
	 * @return
	 * @throws Exception
	 */
	public static String getAfterDateWithStringFormat(String currentDate) throws Exception{
		Date lastDate = getAfterDate(format.parse(currentDate));
		return format.format(lastDate);
	}
	
	/**
	 * 返回相对于当前日期(T)的上上一日期(T-2)的字符串格式：yyyyMMdd
	 * @param currentDate  当前日期的字符串格式(yyyyMMdd)
	 * @return
	 * @throws Exception
	 */
	public static String getDateBeforeYesterdayWithStringFormat(String currentDate) throws Exception{
		Date dateBeforeYesterday = getDateBeforeYesterday(format.parse(currentDate));
		return format.format(dateBeforeYesterday);
	}
	
	/**
	 * 返回相对于当前日期(T)的上一日期(T-1)
	 * @param currentDate 当前日期(T)
	 * @return
	 */
	public static Date getLastDate(Date currentDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return calendar.getTime();
	}
	
	/**
	 * 返回相对于当前日期(T)的上上一日期(T-2)
	 * @param currentDate 当前日期(T)
	 * @return
	 */
	public static Date getDateBeforeYesterday(Date currentDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_YEAR, -2);
		return calendar.getTime();
	}
	/**
	 * 返回相对于当前日期(T)的下一日期(T+1)
	 * @param currentDate 当前日期(T)
	 * @return
	 */
	public static Date getAfterDate(Date currentDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime();
	}
}
