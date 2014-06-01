package com.icsys.batch.business.acctsys;

/**
 * @author kitty
 * 
 */
public interface ErrorDef {
	/**
	 * 日切未完成
	 */
	public static final String DATE_SWITCH_NOT_COMPLETED = "00001";
	/**
	 * 日切日期不一致
	 */
	public static final String DATE_SWITCH_DATE_ERROR = "00002";
	/**
	 * 账务平衡检查异常
	 */
	public static final String CHECK_ACCOUNT_BALANCE_ERROR = "00003";
	/**
	 * 分录平衡检查异常
	 */
	public static final String CHECK_DETAIL_BALANCE_ERROR = "00004";
	/**
	 * 分录不平
	 */
	public static final String CHECK_DETAIL_NOT_BALANCE = "00005";
	/**
	 * 账务数据未找到
	 */
	public static final String ACCT_RECORD_NOT_FOUND = "00006";
	/**
	 * 科目数据未找到
	 */
	public static final String ACCT_SUBJECT_NOT_FOUND = "00018";
	/**
	 * 总账数据未找到
	 */
	public static final String ACCT_GENGL_NOT_FOUND = "00017";
	/**
	 * 生成总账异常
	 */
	public static final String CREATE_GENERAL_ERROR = "00007";
	/**
	 * 生成账务明细异常
	 */
	public static final String CREATE_ACCTING_DETAIL_ERROR = "00008";
	/**
	 * 时序不平
	 */
	public static final String CHECK_SEQUENCE_NOT_BALANCE = "00009";
	/**
	 * 科目日结异常
	 */
	public static final String DAILY_CLOSING__ERROR = "00010";

	/**
	 * 获取总账不平记录失败
	 */
	public static final String GET_UNBALANCED_GENERAL_ERROR = "00011";
	/**
	 * 获取总分平衡失败
	 */
	public static final String CHECK_UNBALANCED_ACCOUNT_ERROR = "00012";
	/**
	 * 账务系统参数未配置
	 */
	public static final String ACCT_SYS_PARAM_NOT_FOUND = "00016";
	/**
	 * 操作数据库失败
	 */
	public static final String OPERATE_DATABASE_ERROR = "00014";
	/**
	 * 自动挂账失败
	 */
	public static final String RUNUP_ACCOUNT_ERROR = "00015";
	
	/**
	 * 上一日扎帐未完成
	 */
	public static final String ACCOUNT_CHECK_ERROR = "00019";
}