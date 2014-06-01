package com.icsys.batch.util;

public class Constants {
	
    /**
     * 账户-机构往来户账户
     */
    public static String BANK_ACCOUNT="";
    
    /**
     * 交易类型：脱机交易
     */
    public static String TRAN_METHOD_OFFLINE = "1";
    
    /**
     * 交易类型:联机交易
     */
    public static String TRAN_METHOD_ONLINE = "2";
    /**
     * 银联
     */
    public static final String CLEARING_UNIONPAY = "2";
    
    /**
     * 本行
     */
    public static final String CLEARING_SELF = "1";
    
    /**
     * 脱机消费
     */
    public static final String OFFLINE_CONSUMPTION = "300";
    
    /**
     * 脱机退货
     */
    public static final String OFFLINE_GOODSREJECT = "301";
    
    /**
     * 联机退货
     */
    public static final String ONLINE_GOODSREJECT = "20";
    
    /**
     * 报文类型
     */
    public static final String DOC_TYPE = "0220";
    
    /**
     * 服务条件代码
     */
    public static final String KEY25 = "00";
    
    /**
     * 成功标识
     */
    public static final String SUCCESS = "1";
    
    /**
     * 失败标识
     */
    public static final String FAILED = "2";
    
    /**
	 * 清算任务状态：1-正在执行
	 */
	public static final String STATUS_EXECUTING = "1";

	/**
	 * 清算任务状态：2-失败
	 */
	public static final String STATUS_FAILURE = "2";

	/**
	 * 清算任务状态：3-执行成功
	 */
	public static final String STATUS_SUCCESS = "3";
	
	//清算步骤
	/**
	 *  0-入库
	 */
	public static final String STAGE_PUTIN = "0";
	
	/**
	 * 1-单笔检查并入账
	 */
	public static final String STAGE_CHECKIN = "1";
		
	/**
	 * 2-按机构汇总
	 */
	public static final String STAGE_SUMMURIZE = "2";
	
	/**
	 * 3-生成结果文件
	 */
	public static final String STAGE_ASSEMBLE = "3";
	
	/**
	 * 本行目录标识(文件全名中含有该值的文件来自本行)
	 */
	public static final String LocalBank = "LocalBank";
	
	/**
	 * 银联目录标识(文件全名中含有该值的文件来自银联)
	 */
	public static final String Unionpay = "Unionpay";
	
    /**
     * 电子现金应用ID
     */
    public static final String ECUSH_APPID = "A000000333010106";
    
    /**
     * 人民币代码
     */
	public static final String RMB ="156";
	
	/**
	 * 在处理脱机/联机交易时，每页显示的长度
	 */
	public static final int PAGE_LENGTH=500;
	
	
	public static final int QUERY_PAGE_LENGTH=1000;
	
	/**
	 *复合卡 
	 */
	public static final String CARD_TYPE_MUTIP = "1";
	
	/**
	 * 单芯卡标识（纯电子现金卡）
	 */
	public static final String CARD_TYPE_ELEC = "0";
	
	/**
	 * 成功文件后缀
	 */
	public static final String SUCCESS_SUFFIX =".succ";
	
	/**
	 * 失败文件后缀
	 */
	public static final String FAILED_SUFFIX = ".fail";
	
	/**
	 * 成功处理文件标识后缀名
	 */
	public static final String FINISHED_FLAG=".ok";
	
	/**
	 * 字符集
	 */
	public static final String CHARSET = "GBK";
	
	/**
	 * 获取批次号
	 */
	public static final String BATCH_NO_PARAM="200002";
	
	/**
	 * 银联账户索引
	 */
	public static final String UNIONPAY_INDEX = "9010";
	
	/**
	 * 币种
	 */
	
	public static final String CURR_TYPE="156";
}