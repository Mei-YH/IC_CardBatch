package com.icsys.batch.checkAccount;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.icsys.batch.util.SystemConstans;
import com.icsys.batch.util.SystemParamValue;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;
import com.icsys.platform.util.SystemProperties;

public class IcCheckAccountTxListDao {


	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;

	private static Logger LOG = Logger.getLogger(IcCheckAccountTxListDao.class);
	
	/**
	 * 批量新增对账表记录
	 * @param icTxList
	 * @return
	 */
	public void insertCheckAcctList(boolean isCups,String channel,String trandate) throws Exception{
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String tableName = SystemParamValue.getNotCurrentSerialTableName();
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("channel", channel);
		para.put("trandate", trandate);
		String sql = "INSERT INTO gr_bus_cheacctxlist(SELECT A.*,B.log_trfuction,B.log_REMARK, B.log_wrcstate,B.log_comresults,"+
		" B.channel_no,B.log_chdate,B.log_chserial,B.log_hdate,B.log_opteller,B.log_terno,B.log_cheteller,B.log_autteller,B.log_trtime " +
		" FROM " + tableName + " A ,gr_bus_trloglist B where A.txl_plserial = B.log_plserial and A.txl_trdate=B.log_trdate " +
				"and B.channel_no=#channel#";
		if(isCups){
			sql += " and (B.log_chdate=#trandate# or substr(B.log_chdate,5,4)=#trandate#) union all ";
			tableName = SystemParamValue.getCurrentSerialTableName();
			sql += "SELECT A.*,B.log_trfuction,B.log_REMARK, B.log_wrcstate,B.log_comresults,"+
			" B.channel_no,B.log_chdate,B.log_chserial,B.log_hdate,B.log_opteller,B.log_terno,B.log_cheteller,B.log_autteller,B.log_trtime " +
			" FROM " + tableName + " A ,gr_bus_trloglist B where A.txl_plserial = B.log_plserial and A.txl_trdate=B.log_trdate " +
					"and B.channel_no=#channel# and (B.log_chdate=#trandate# or substr(B.log_chdate,5,4)=#trandate#))";
		}else{
			sql += " and A.txl_trdate=#trandate#)";
		}
		template.update(sql, para);
	}
	
	
//	public int addRecord(IcCheckAccountTxList accountResult){
//		int result = 0;
//		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
//		String sql = "INSERT INTO IC_CHECK_ACCOUNT_TX_LIST(SERIAL,TRAN_DATE,PLATFORM_SERIAL,HOST_SERIAL,TRAN_BRANCH," +
//				"CARD_NO,START_AMOUNT,TRAN_FLAG,RECONCILIATION_LOGO,TRAN_AMOUNT,FEE,CURR_TYPE," +
//				"ACCT_NO,ACCT_CD,CARD_NO1,ACCT_NO1,ACCT_AMOUNT1,ACCT_FLAG,HOST_TRAN_ACCT_NO,TRAN_STATUS,CORE_REVERSAL_RECORD," +
//				"TRAN_FANCTION,REMARK,WRITE_CARD_STATE,COMMAND_RESULTS,CHANNEL_NO,CHANNEL_DATE,CHANNEL_SERIAL,HOST_DATE," +
//				" OPERATOR,TERMINAL_NO,CHECK_TELLER,AUTHORIZE_TELLER) " +
//				"VALUES (#SERIAL#,#TRAN_DATE#,#PLATFORM_SERIAL#,#HOST_SERIAL#,#TRAN_BRANCH#,#CARD_NO#," +
//				"#START_AMOUNT#,#TRAN_FLAG#,#RECONCILIATION_LOGO#,#TRAN_AMOUNT#,#FEE#,#CURR_TYPE#," +
//				"#ACCT_NO#,#ACCT_CD#,#CARD_NO1#,#ACCT_NO1#,#ACCT_AMOUNT1#,#ACCT_FLAG#,#HOST_TRAN_ACCT_NO#," +
//				"#TRAN_STATUS#,#CORE_REVERSAL_RECORD#," +
//				"#TRAN_FANCTION#,#REMARK#,#WRITE_CARD_STATE#,#COMMAND_RESULTS#,#CHANNEL_NO#,#CHANNEL_DATE#,#CHANNEL_SERIAL#,#HOST_DATE#" +
//				"#OPERATOR#,#TERMINAL_NO#,#CHECK_TELLER#,#AUTHORIZE_TELLER#) ";
//		result = template.update(sql, accountResult);
//		return result;
//	}

	/**
	 * 更新交易流水
	 * @param icTxList
	 * @return
	 */
	public int updateRecordWithSerial(IcCheckAccountTxList accountResult){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql="UPDATE IC_CHECK_ACCOUNT_TX_LIST set SERIAL=#serial#,TRAN_DATE=#tranDate#,PLATFORM_SERIAL= #platformSerial#," +
				"HOST_SERIAL= #hostSerial#,TRAN_BRANCH= #tranBranch#,CARD_NO= #cardNo#," +
				"START_AMOUNT= #startAmount#,TRAN_FLAG= #tranFlag#," +
				"RECONCILIATION_LOGO= #reconciliationLogo#,TRAN_AMOUNT= #tranAmount#,FEE= #fee#," +
				"CURR_TYPE= #currType#,ACCT_NO= #acctNo#,ACCT_CD= #acctCd#,CARD_NO1= #cardNo1#," +
				"ACCT_NO1= #acctNo1#,ACCT_AMOUNT1= #acctAmount1#,ACCT_FLAG= #acctFlag#," +
				"HOST_TRAN_ACCT_NO= #hostTranAcctNo#,TRAN_STATUS= #tranStatus#,CORE_REVERSAL_RECORD=#CORE_REVERSAL_RECORD#, " +
				" TRAN_FANCTION=#tranFanction#, REMARK=#remark#, WRITE_CARD_STATE=#writeCardState#, COMMAND_RESULTS=#commandResults#," +
				" CHANNEL_NO=#channelNo#, CHANNEL_DATE=#channelDate#, CHANNEL_SERIAL=#channelSerial#, HOST_DATE=#hostDate# ," +
				" OPERATOR=#operator#, TERMINAL_NO=#terminalNo#, CHECK_TELLER=#checkTeller#, AUTHORIZE_TELLER=#authorizeTeller#, " +
				" TRAN_TIME=#tranTime# WHERE SERIAL=#serial# ";
		if(LOG.isDebugEnabled())
			LOG.debug("更新交易流水表记录:" + sql);
		int result = template.update(sql, accountResult);
		return result;
	}
	/**
	 * 获取流水记录
	 * @param serial
	 * @return
	 */
	public IcCheckAccountTxList getRecord(String serial){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from  IC_CHECK_ACCOUNT_TX_LIST where serial =#serial# ";
		IcCheckAccountTxList accountResult = new IcCheckAccountTxList();
		accountResult.setIcSerial(serial);
		return template.queryRow(sql, accountResult, IcCheckAccountTxList.class);
	}
	
	/**
	 * 获取流水记录
	 * @return
	 */
	public List<IcCheckAccountTxList> getRecordsByChannel(boolean isCups,String channel,String tranDate){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from  gr_bus_cheacctxlist where reconciliation_logo='11' and channel_no=#channelNo#";
		if(isCups){
			sql += " and channel_date=#tranDate#";
		}else{
			sql += " and tran_date=#tranDate#";
		}
		IcCheckAccountTxList para = new IcCheckAccountTxList();
		para.setChannelNo(channel);
		para.setTranDate(tranDate);
		return template.query(sql, para, IcCheckAccountTxList.class);
	}
	
	/**
	
	/**
	 * 更新锁定流水
	 * @param serial
	 * @return
	 */
	public IcCheckAccountTxList getRecordWithLock(String serial){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String dbName = SystemProperties.get("database", "Oracle");
		String endSql = "";
		if ("DB2".equals(dbName) || "db2".equals(dbName) || "Db2".equals(dbName)) {
			endSql = " with RS";
		}
		String sql = " select * from  IC_CHECK_ACCOUNT_TX_LIST where serial =#serial# for update " + endSql;
		IcCheckAccountTxList accountResult = new IcCheckAccountTxList();
		accountResult.setIcSerial(serial);
		return template.queryRow(sql, accountResult, IcCheckAccountTxList.class);
	}
	
	
	public List<IcCheckAccountTxList> getUPBalanceRecords(String hostDate){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = " select * from  IC_CHECK_ACCOUNT_TX_LIST where HOST_DATE=#hostDate#  " +
				" AND (WRITE_CARD_STATE ='1' " +
				"  OR WRITE_CARD_STATE ='2' )" +
				" AND RECONCILIATION_LOGO = '11' AND  (CHANNEL_NO = '620' OR CHANNEL_NO = '831' OR CHANNEL_NO = '832')" ;
		IcCheckAccountTxList accountResult = new IcCheckAccountTxList();
		accountResult.setHostDate(hostDate);
		return template.query(sql, accountResult, IcCheckAccountTxList.class);
	}
	
	public List<IcCheckAccountTxList> getUPUnBalanceRecords(String hostDate,String tranDate){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = " select * from  IC_CHECK_ACCOUNT_TX_LIST where ((HOST_DATE=#hostDate#  " +
				" AND (((WRITE_CARD_STATE ='0' OR WRITE_CARD_STATE ='3' ) AND RECONCILIATION_LOGO = '11') OR RECONCILIATION_LOGO != '11')) " +
				"  OR(TRAN_DATE=#tranDate# AND HOST_DATE IS NULL))" +
				" AND  (CHANNEL_NO = '620' OR CHANNEL_NO = '831' OR CHANNEL_NO = '832')" ;
		IcCheckAccountTxList accountResult = new IcCheckAccountTxList();
		accountResult.setHostDate(hostDate);
		accountResult.setTranDate(tranDate);
		return template.query(sql, accountResult, IcCheckAccountTxList.class);
	}
	
	public List<Map<String, Object>> getFrontBranchInfo(String hostDate,String tranDate){
		//银联交易不会有余额调整
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = " select TRAN_BRANCH, COUNT(*) as num from  IC_CHECK_ACCOUNT_TX_LIST where HOST_DATE=#HOST_DATE#  " +
//		" AND (WRITE_CARD_STATE ='1' " +
//		"  OR WRITE_CARD_STATE ='2' )" +
//		" AND RECONCILIATION_LOGO = '11' AND  (CHANNEL_NO = '620' OR CHANNEL_NO = '831' OR CHANNEL_NO = '832')" +
//		" GROUP BY TRAN_BRANCH " ;
		String sql = " select TRAN_BRANCH, COUNT(*) as num from  IC_CHECK_ACCOUNT_TX_LIST " +
				"where ( HOST_DATE=#hostDate# AND (CHANNEL_NO = '620' OR CHANNEL_NO = '831' OR CHANNEL_NO = '832'))" +
				" OR (TRAN_DATE =#hostDate# AND (CHANNEL_NO = '620' OR CHANNEL_NO = '831' OR CHANNEL_NO = '832') AND HOST_DATE IS NULL) " +
				" GROUP BY TRAN_BRANCH " ;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("HOST_DATE", hostDate);
		params.put("TRAN_DATE", tranDate);
		return template.query(sql, params);
	}
	
	/**
	 * 更新平台多核心少的记录
	 * @param checkDate
	 * @param channel
	 * @throws Exception
	 */
	public void updateExtraRecordsOnPlatForm(boolean isCups,String checkDate,String channel){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql = " UPDATE gr_bus_cheacctxlist set reconciliation_logo='10' WHERE reconciliation_logo='00' " +
				"AND tran_status='0' and channel_no=#channel# ";
		if(isCups){
			sql += "and channel_date=#checkdate#";
		}else{
			sql += "and tran_date=#checkdate#";
		}
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("checkdate", checkDate);
		para.put("channel", channel);
		template.update(sql, para);
	}
	
//	public void updateExtraRecordsOnPlatFormByHostDate(String hostDate){
//		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
//		String sql = " UPDATE IC_CHECK_ACCOUNT_TX_LIST set RECONCILIATION_LOGO='10' WHERE HOST_DATE=#hostDate# " +
//				" AND RECONCILIATION_LOGO='00' AND TRAN_STATUS='0'";
//		IcCheckAccountTxList accountResult = new IcCheckAccountTxList();
//		accountResult.setHostDate(hostDate);
//		template.update(sql, accountResult);
//	}

//	public void moveDayBeforeYesterdayToHistory(String beforeYesterday){
//		NamedUpdateTemplate template =  TemplateManager.getNamedUpdateTemplate(DS_NAME);
//		String copySql = "INSERT INTO IC_TX_LIST_H (select SERIAL,PLATFORM_SERIAL, HOST_SERIAL ,TRAN_BRANCH , CARD_NO, START_AMOUNT, TRAN_FLAG ,RECONCILIATION_LOGO, TRAN_AMOUNT," +
//				"FEE, CURR_TYPE, ACCT_NO, ACCT_CD, CARD_NO1, ACCT_NO1, ACCT_AMOUNT1," +
//				" ACCT_FLAG, HOST_TRAN_ACCT_NO, TRAN_STATUS, CORE_REVERSAL_RECORD, TRAN_DATE  from IC_CHECK_ACCOUNT_TX_LIST " +
//				" WHERE TRAN_DATE=#tranDate#)";
//		//TODO delete 效率太慢，没有别的途径？？？
//		String deleteSql = "DELETE FROM IC_CHECK_ACCOUNT_TX_LIST WHERE TRAN_DATE=#tranDate#";
//		IcCheckAccountTxList accountResult = new IcCheckAccountTxList();
//		accountResult.setTranDate(beforeYesterday);
//		template.update(copySql, accountResult);
//		template.update(deleteSql, accountResult);
//		
//	}
	
	/**
	 * 根据日期按机构、交易功能获取汇总数据
	 * @param serial
	 * @return
	 */
//	public List<IcCheckAccountTxList> getRecord_date(String tranDate){
//		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = "select count(*) counts, sum(tran_amount) tran_amounts, tran_branch, tran_fanction " +
//		             "from IC_CHECK_ACCOUNT_TX_LIST where tran_date=#tranDate# " +
//		             "and tran_status = '0' and write_card_state in ('1','2') " +
//		             "group by tran_branch,tran_fanction order by tran_branch,tran_fanction";
//		IcCheckAccountTxList accountResult = new IcCheckAccountTxList();
//		accountResult.setTranDate(tranDate);
//		return template.query(sql, accountResult, IcCheckAccountTxList.class);
//	}
	
	/**
	 * 根据平台流水号获取明细
	 */
	public List<IcCheckAccountTxList> getRecordByPlatformSerial(String serial){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from gr_bus_cheacctxlist where platform_serial=#platformSerial#";
		IcCheckAccountTxList txlist = new IcCheckAccountTxList();
		txlist.setPlatformSerial(serial);
		return template.query(sql, txlist, IcCheckAccountTxList.class);
	}

	/*
	 * 更新对账标志，对账使用
	 */
	public int updateRecFlag(IcCheckAccountTxList list){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql="UPDATE gr_bus_cheacctxlist set reconciliation_logo=#reconciliationLogo#,tran_amount=#tranAmount#," +
				"fee=#fee# ";
		if(null!=list.getHostSerial()&&!"".equals(list.getHostSerial().trim())){
			sql += ",host_serial=#hostSerial#";
		}
		if(null!=list.getHostDate()&&!"".equals(list.getHostDate().trim())){
			sql += ",host_date=#hostDate#";
		}
		sql += " WHERE platform_serial=#platformSerial# ";
		return template.update(sql, list);
	}
	
	/**
	 * 如果IC卡平台流水对账状态为00－未对账,并且交易状态为:非成功,则更新其对账状态为11-平账
	 */
	public void updateOnPlatFormCloseOut(boolean isCups,String channel,String checkDate){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql="UPDATE gr_bus_cheacctxlist set reconciliation_logo='11' WHERE tran_status<>'0' and reconciliation_logo='00' " +
				"and channel_no=#channel# ";
		if(isCups){
			sql += "and channel_date=#checkdate#";
		}else{
			sql += "and tran_date=#checkdate#";
		}
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("channel", channel);
		para.put("checkdate", checkDate);
		template.update(sql, para);		
	}

	/**
	 * 删除当前渠道对账日期前一天的对账表数据
	 */
	public void deleteCheckLastDate(boolean isCups,String channel,String lastDate){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql = "delete from gr_bus_cheacctxlist where channel_no=#channel# " ;
		if(isCups){
			sql += "and (channel_date=#lastdate# or substr(channel_date,5,4)=#lastdate#)";
		}else{
			sql += "and tran_date=#lastdate#";
		}
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("channel", channel);
		para.put("lastdate", lastDate);
		template.update(sql, para);
	}
}
