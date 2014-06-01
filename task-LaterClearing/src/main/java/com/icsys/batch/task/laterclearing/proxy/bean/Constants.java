package com.icsys.batch.task.laterclearing.proxy.bean;
/**
 * 
 * @author  作者:lyq
 * 常量类,用来标注用到的changliang
 */
public class Constants {
	/**
	 * 数据源属性名
	 */
	public static final String ATTRIB_DATA_SOURCE = "IC_DATASOURCE";	
	/**
	 * IC卡收回状态
	 */
	public static final String ATTRIB_IC_IC_STATUS="4";
	/**
	 * 不可读收回
	 */
	public static final String STATUS_RECYCLE_UNREADABLE = "1";	
	/**
	 * 可读收回
	 */
	public static final String STATUS_RECYCLE_READABLE = "2";
	/**
	 * 换卡
	 */
	public static final String STATUS_RE_CARD="1";
	/**
	 * 销卡
	 */
	public static final String STATUS_BR_CARD="2";
	/**
	 * 补卡
	 */
	public static final String STATUS_CE_CARD="3";
}
