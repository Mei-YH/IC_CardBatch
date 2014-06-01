package com.icsys.batch.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.icsys.batch.offline.bean.SpecialAccount;
import com.icsys.batch.offline.dao.IcAccNoRegDao;

public class Utils {

	/**
	 * 判断起止日期是否在一个指定范围之内
	 * 
	 * @param from
	 *            起始日期
	 * @param to
	 *            截止日期
	 * @param distance
	 *            指定范围，单位为天
	 * @return
	 */
	public static boolean isInsidePale(Date from, Date to, int distance) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(from);
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(to);
		if (fromCalendar.after(toCalendar))
			return false;
		fromCalendar.add(Calendar.DAY_OF_YEAR, distance);
		if (fromCalendar.compareTo(toCalendar) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	// 或运算 union("1001","0101")="1101"
	public static String union(String one, String another) {
		if (null == one || null == another) {
			return null;
		}
		if (one.length() != another.length()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		char c;
		for (int index = 0; index < one.length(); index++) {
			c = one.charAt(index);
			if (c != '0') {
				sb.append(c);
			} else {
				sb.append(another.charAt(index));
			}
		}
		return sb.toString();
	}
//
//	public static String getClearDate() {
////		return getCurrentDate("yyyyMMdd");
//		String clearDate="";
//		try {
//			clearDate= SystemParamValue.getSystemDate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return clearDate;
//	}

	/**
	 * 将数据data切割成指定长度（length），长割短补
	 * @param data
	 * @param length
	 * @return
	 */
	public static String template(String data, int length) {
		if(data != null){
			byte[] dataBytes = data.getBytes();
			if (dataBytes.length < length) {
				while (dataBytes.length < length) {
					data = data + ' ';
					dataBytes = data.getBytes();
				}
			} else if (dataBytes.length > length) {
				data = data.substring(0, length);
			}
		}
		return data;
	}
	
	/**
	 * 过滤数据，如果数据为null 或者 "" ,则将数据赋值为指定长度的空串
	 * @param data 要被过滤的数据
	 * @param length 指定的长度
	 * @return 过滤后的数据
	 */
	public static String filter(String data,int length){
		if(data==null||"null".equals(data)||"".equals(data)){
			data = "";
			for(int i=0;i<length;i++){
				data += ' ';
			}
		}
		if(data.length()<length){
			int left = length - data.length();
			for(int i=0;i<left;i++){
				data += ' ';
			}
		}
		return data;
	}
	
	public static String figureFilter(String data,int length){
		if(data==null||"null".equals(data)||"".equals(data)){
			data = "";
			for(int i=0;i<length;i++){
				data = '0'+data;
			}
			return data;
		}
		BigDecimal bigData  =  new BigDecimal(data.trim());
		bigData = bigData.movePointRight(2);
		data = bigData.toString();
		if(data.length()<length){
			int left = length - data.length();
			for(int i=0;i<left;i++){
				data = '0'+data;
			}
		}
		return data;
	}
	
	/**
	 * 获得挂账账户
	 * @return
	 */
	public static String getSuspendAcct(){
		IcAccNoRegDao dao = new IcAccNoRegDao();
		SpecialAccount susAccount = dao.querySpecialAccount("7051", "1001");
		return susAccount.getAcctNo();
	}
	
	/**
	 * 根据格式获取系统当前时间
	 * @param format 格式
	 * @return 当前时间
	 */
	public static String getCurrentDate(String format){
		//getCurrentDate("yyyy-MM-dd HH:mm:ss")
		SimpleDateFormat tempDate = new SimpleDateFormat(format);
		String currentTime = tempDate.format(new java.util.Date());
		return currentTime;
	}
	
	/**
	 * 根据间隔获得指定日期(当前日期为账务日期)
	 * @param distance -10  就是往前推10 天 同理 +10 就是往后推十天
	 * @return
	 */
//	public static String getSpecialDate(int distance){
//		 String financialDate=Utils.getClearDate();
//		 String date=financialDate.substring(0, 4)+"-"+financialDate.substring(4,6)+"-"+financialDate.substring(6, 8);//转换为yyyy-MM-dd的形式
//		 SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//		 // 当前时间
//	     Calendar cal = Calendar.getInstance();
//	     cal.setTime(java.sql.Date.valueOf(date));
//	     // 日期的DATE减去10  就是往前推10 天 同理 +10 就是往后推十天
//	     cal.add(Calendar.DATE, distance);
//	     String str = sf.format(cal.getTime());
//	     return str;
//	}
	
	/**
	 * 获得账务日期
	 * @param format
	 * @return
	 * @throws Exception
	 */
//	public static String getSystemDate(String format) throws Exception{
//		return new ParamManagerImpl().getParameter(SysParamConstant.SYSTEM_DATE).getParameterValue();
//	}
	
	
	/**
	 * 银联商户待清算总户
	 */
	public static String getMerChantAcct(){
		IcAccNoRegDao dao=new IcAccNoRegDao();
		SpecialAccount specialAccout=dao.querySpecialAccount("30310904", "000000");
		return specialAccout.getAcctNo();
	}
}
