package com.icsys.batch.util;

public interface BatchConstants {

	/**
	 * 电子现金应用AID
	 */
	String CASH_AID = "A000000333010106";
	
	String CD_AIC = "";
	
	/**
	 * 编码方式
	 */
	String CHARSET = "GBK";
	
	/**
	 * 任务执行成功标识
	 */
	String TASK_EXECUTE_SUCCESS_FLAG = "OK";
	
	/**
	 * 银行代码索引
	 */
	String BANK_CODE_INDEX = "100001";
	
	/**
	 * 批处理批次号索引
	 */
	String BATCH_NO_INDEX = "200002";
	
	/**
	 * 平台日期索引
	 */
	String PALTFORM_TRAN_DATE_INDEX = "000003";
	/**
	 * 核心日期索引
	 */
	String HOST_DATE_INDEX = "300001";
	
	/**
	 * CVV加密密钥操作名
	 */
	String CVV_KEY_OPRATION_NAME = "0009";
	
	/**
	 * 工作密钥操作名
	 */
	String WORK_KEY_OPRATION_NAME = "0007";
	
	/**
	 * 前置机在TFT客户端配置文件中，配置的主机编号
	 */
	String FRONT_HOST_NO = "0";
	
	/**
	 * 核心主机在TFT客户端配置文件中，配置的主机编号
	 */
	String CORE_HOST_NO = "1";
	
	/**
	 * 批处理处理日期
	 */
	String TRAN_DATE = "tran-date";
	
	/**
	 * 批处理批次号
	 */
	String BATCH_NO = "batch-no";
	

	/**
	 * TFT(TFTServer|TFTClient)根目录
	 */
	String TFT_ROOT_DIR = "tft-root-dir";
	
	/**
	 * 建账标志
	 */
	String MAKE_ACCT = "1";
	
	/**
	 * 发卡参数用途：0-无意义，1-签发请求参数，2-卡模板数据，3-卡数据
	 */
	String CARD_PARAM_USE_REQ = "1";
	String CARD_PARAM_USE_TEMPLATE = "2";
	String CARD_PARAM_USE_DATA = "3";
	
	/**
	 * 文件处理标志：0-未处理
	 */
	String FILE_DEAL_STATUS_UNDO = "0";
	/**
	 * 文件处理标志：1-正在处理
	 */
	String FILE_DEAL_STATUS_DOING = "1";
	/**
	 * 文件处理标志：2-处理成功
	 */
	String FILE_DEAL_STATUS_SUCCESS = "2";
	/**
	 * 文件处理标志：3-处理失败
	 */
	String FILE_DEAL_STATUS_FAILURE = "3";
	
	
	
	/**
	 * 写卡脚本状态：0-未生成脚本  
	 */
	String CARD_SCRIPT_STATUS_NO = "0";
	
	/**
	 * 写卡脚本状态：  1-写卡脚本发出  
	 */
	String CARD_SCRIPT_STATUS_SENDED = "1";
	
	/**
	 * 写卡脚本状态：  2-写卡成功   
	 */
	String CARD_SCRIPT_STATUS_SUCCESS = "2";
	
	/**
	 * 写卡脚本状态：  3-写卡失败
	 */
	String CARD_SCRIPT_STATUS_FAILURE = "3";
	
	
	/**
	 * 借贷标识：1-借；2-贷
	 */
	
	String ACCT_DC_DEBIT = "1";
	String ACCT_DC_CREDIT = "2";
	
	/**
	 * 处理标识：0-未处理
	 */
	String DEAL_FLAG_UNDO = "0";
	
	/**
	 * 处理标识：1-处理成功
	 */
	String DEAL_FLAG_SUCCESS = "1";
	
	/**
	 * 处理标识：2-处理失败
	 */
	String DEAL_FLAG_FAILURE = "2";
	
	/**
	 * 处理策略：0-冲IC卡平台
	 */
	String DEAL_TACTIC_0 = "0";
	
	
	/**
	 * 处理策略：1-冲核心
	 */
	String DEAL_TACTIC_1 = "1";
	
	/**
	 * 处理策略：2-冲平台，冲核心
	 */
	String DEAL_TACTIC_2 = "2";
	
	
	//TODO 
	/**
	 * 处理状态：0-冲平台成功
	 */
	String DEAL_STATUS_0 = "0";
	
	/**
	 * 处理状态：1-冲平台失败 
	 */
	String DEAL_STATUS_1 = "1";
	
	/**
	 * 处理状态：2-冲核心成功
	 */
	String DEAL_STATUS_2 = "2";
	
	/**
	 * 处理状态：3-冲核心失败
	 */
	String DEAL_STATUS_3 = "3";
	
	/**
	 * 处理状态：4-冲核心未知
	 */
	String DEAL_STATUS_4 = "4";
	
	/**
	 * 处理状态：5-平台成功，核心成功
	 */
	String DEAL_STATUS_5 = "5";
	
	/**
	 * 处理状态：6-平台成功，核心失败
	 */
	String DEAL_STATUS_6 = "6";
	
	/**
	 * 处理状态：7-平台成功，核心未知
	 */
	String DEAL_STATUS_7 = "7";
	
	/**
	 * 处理状态：8-平台失败，核心失败
	 */
	String DEAL_STATUS_8 = "8";
	
	/**
	 * 核心通知文件参数
	 */
	String CORE_NOTICE_PATH = "core_notice_path";
	
	/**
	 * 本地通知文件参数
	 */
	String LOCAL_NOTICE_PATH = "local_notic_path";
}