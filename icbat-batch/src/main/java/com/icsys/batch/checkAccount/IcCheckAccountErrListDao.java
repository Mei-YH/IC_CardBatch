package com.icsys.batch.checkAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class IcCheckAccountErrListDao {

	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;

//	private static Logger LOG = Logger.getLogger(IcCheckAccountErrListDao.class);
	
	/**
	 * 批量新增对账差错表记录(以平台账务日期为条件)
	 * @param tranDate  IC卡平台账务日期
	 * @return
	 */
//	public int batchInsert(String tranDate) throws Exception{
//		int result = 0;
//		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
//		//TODO  对账标志等于：00的是不是也属于异常？？？？
//		String sql = "INSERT INTO IC_CHECK_ACCOUNT_ERR_LIST(SELECT A.*,'0',null,null,null,'0' from IC_CHECK_ACCOUNT_TX_LIST A "+
//		" WHERE A.TRAN_DATE = #TRAN_DATE# AND (A.RECONCILIATION_LOGO != '11' AND A.RECONCILIATION_LOGO != '99' AND A.RECONCILIATION_LOGO != '00'"+
//		" OR (A.RECONCILIATION_LOGO = '11' AND (A.WRITE_CARD_STATE = '0' OR A.WRITE_CARD_STATE = '3'))) ) " ;
//		IcCheckAccountErrList errList = new IcCheckAccountErrList();
//		errList.setTranDate(tranDate);
//		result = template.update(sql, errList);
//		//TODO 添加对余额调整的操作
//		sql = "INSERT INTO IC_CHECK_ACCOUNT_ERR_LIST(SELECT A.*,'0',null,null,null,'1' from IC_CHECK_ACCOUNT_TX_LIST A ,IC_TRAN_LIST B"+
//		" WHERE A.PLATFORM_SERIAL = B.ORIGINAL_TRAN_SERIAL AND  B.ORIGINAL_RAN_DATE = #TRAN_DATE# AND B.TRAN_FANCTION = '31' AND B.WRITE_CARD_STATE = '2'" +
//		" AND B.TRAN_STATUS = '0' "+
//		"  AND A.RECONCILIATION_LOGO = '11' ) " ;
//		
//		result = template.update(sql, errList);
//		return result;
//	}
	
	/**
	 * 有两条表间复制语句，必须加事务
	 * 批量新增对账差错表记录(以核心账务日期为条件)
	 * 
	 * @param tranDate  核心账务日期
	 * @return
	 */
//	public int batchInsertWithHostDate(String hostDate) throws Exception{
//		int result = 0;
//		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
//		//TODO  对账标志等于：00的是不是也属于异常？？？？
//		String sql = "INSERT INTO IC_CHECK_ACCOUNT_ERR_LIST(SELECT A.*,'0',null,null,null,'0' from IC_CHECK_ACCOUNT_TX_LIST A "+
//		" WHERE A.HOST_DATE = #HOST_DATE# AND (A.RECONCILIATION_LOGO != '11' AND A.RECONCILIATION_LOGO != '99' AND A.RECONCILIATION_LOGO != '00'"+
//		" OR (A.RECONCILIATION_LOGO = '11' AND (A.WRITE_CARD_STATE = '0' OR A.WRITE_CARD_STATE = '3') AND (A.TRAN_FANCTION != '18' AND A.TRAN_FANCTION != '12'))) ) " ;
//		IcCheckAccountErrList errList = new IcCheckAccountErrList();
//		errList.setHostDate(hostDate);
//		result = template.update(sql, errList);
//		//对余额调整的操作(余额调整交易日期一定等于主机日期)
//		sql = "INSERT INTO IC_CHECK_ACCOUNT_ERR_LIST(SELECT A.*,'0',null,null,null,'1' from IC_CHECK_ACCOUNT_TX_LIST A ,IC_TRAN_LIST B"+
//		" WHERE A.PLATFORM_SERIAL = B.ORIGINAL_TRAN_SERIAL AND  B.TRAN_DATE = #TRAN_DATE# AND B.TRAN_FANCTION = '31' AND B.WRITE_CARD_STATE = '2'" +
//		" AND B.TRAN_STATUS = '0' "+
//		"  AND A.RECONCILIATION_LOGO = '11' AND A.TRAN_STATUS = '0' ) " ;
//		errList.setTranDate(hostDate);
//		result = template.update(sql, errList);
//		return result;
//	}
	
	
	
	public int addRecord(IcCheckAccountErrList errList){
		int result = 0;
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql = "INSERT INTO gr_bus_cheaccerrlist(che_serial,che_trdate,che_plserial,che_hserial,che_reclogo,che_tramount,che_remark) " +
				"VALUES (#cheSerial#,#cheTrdate#,#chePlserial#,#cheHserial#,#cheReclogo#,#cheTramount#,#cheRemark#) ";
		result = template.update(sql, errList);
		return result;
	}
	
	
//	/**
//	 * 更新交易流水
//	 * @param icTxList
//	 * @return
//	 */
//	public int updateRecordWithSerial(IcCheckAccountErrList accountResult){
//		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
//		String sql="UPDATE IC_CHECK_ACCOUNT_ERR_LIST set SERIAL=#SERIAL#,TRAN_DATE=#TRAN_DATE#,PLATFORM_SERIAL= #PLATFORM_SERIAL#," +
//				"HOST_SERIAL= #HOST_SERIAL#,TRAN_BRANCH= #TRAN_BRANCH#,CARD_NO= #CARD_NO#," +
//				"START_AMOUNT= #START_AMOUNT#,TRAN_FLAG= #TRAN_FLAG#," +
//				"RECONCILIATION_LOGO= #RECONCILIATION_LOGO#,TRAN_AMOUNT= #TRAN_AMOUNT#,FEE= #FEE#," +
//				"CURR_TYPE= #CURR_TYPE#,ACCT_NO= #ACCT_NO#,ACCT_CD= #ACCT_CD#,CARD_NO1= #CARD_NO1#," +
//				"ACCT_NO1= #ACCT_NO1#,ACCT_AMOUNT1= #ACCT_AMOUNT1#,ACCT_FLAG= #ACCT_FLAG#," +
//				"HOST_TRAN_ACCT_NO= #HOST_TRAN_ACCT_NO#,TRAN_STATUS= #TRAN_STATUS#,CORE_REVERSAL_RECORD=#CORE_REVERSAL_RECORD#, " +
//				" TRAN_FANCTION=#TRAN_FANCTION#, REMARK=#REMARK#, WRITE_CARD_STATE=#WRITE_CARD_STATE#, COMMAND_RESULTS=#COMMAND_RESULTS#," +
//				" CHANNEL_NO=#CHANNEL_NO#, CHANNEL_DATE=#CHANNEL_DATE#, CHANNEL_SERIAL=#CHANNEL_SERIAL#, HOST_DATE=#HOST_DATE# ," +
//				" OPERATOR=#OPERATOR#, TERMINAL_NO=#TERMINAL_NO#, CHECK_TELLER=#CHECK_TELLER#, AUTHORIZE_TELLER=#AUTHORIZE_TELLER#, " +
//				" DEAL_FLAG=#DEAL_FLAG#, DEAL_TACTIC=#DEAL_TACTIC#, DEAL_STATUS=#DEAL_STATUS#, DEAL_REASON=#DEAL_REASON#,BALANCE_TRANSFER_FLAG=#BALANCE_TRANSFER_FLAG# " +
//				" WHERE SERIAL=#SERIAL# ";
//		LOG.debug("更新交易流水表记录:" + sql);
//		int result = template.update(sql, accountResult);
//		return result;
//	}
	
//	public int updateBalanceTransferFlag(String tranDate,String platformSerial){
//		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
//		String sql="UPDATE IC_CHECK_ACCOUNT_ERR_LIST set BALANCE_TRANSFER_FLAG='1' " +
//				" WHERE TRAN_DATE=#TRAN_DATE# AND PLATFORM_SERIAL= #PLATFORM_SERIAL#";
//		IcCheckAccountErrList record  = new IcCheckAccountErrList();
//		record.setTranDate(tranDate);
//		record.setPlatformSerial(platformSerial);
//		return template.update(sql, record);
//	}
	public List<IcCheckAccountErrList> getRecordsByChannel(boolean isCups,String channel,String trandate,String cheTrbranch,String tranDate){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = "select a.*,'" + tranDate + "' as tran_date from  gr_bus_cheaccerrlist a where che_chno=#cheChno# " +
				"and substr(che_trbranch,1,3)=#cheTrbranch# ";
		if(isCups){
			sql += "and che_cdate=#cheCdate#";
		}else{
			sql += "and che_trdate=#cheTrdate#";
		}
		IcCheckAccountErrList errList = new IcCheckAccountErrList();
		errList.setCheChno(channel);
		errList.setCheTrbranch(cheTrbranch);
		errList.setCheTrdate(trandate);
		errList.setCheCdate(trandate);
		return template.query(sql, errList, IcCheckAccountErrList.class);
	}
	
//	public List<IcCheckAccountErrList> getUPUnBalanceRecords(String hostDate,String tranDate){
//		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = " select * from  IC_CHECK_ACCOUNT_ERR_LIST where ((HOST_DATE=#HOST_DATE#  " +
//				" AND (((WRITE_CARD_STATE ='0' OR WRITE_CARD_STATE ='3' ) AND RECONCILIATION_LOGO = '11') OR RECONCILIATION_LOGO != '11')) " +
//				"  OR(TRAN_DATE=#TRAN_DATE# AND HOST_DATE IS NULL))" +
//				" AND  (CHANNEL_NO = '620' OR CHANNEL_NO = '831' OR CHANNEL_NO = '832')" ;
//		IcCheckAccountErrList accountResult = new IcCheckAccountErrList();
//		accountResult.setHostDate(hostDate);
//		accountResult.setTranDate(tranDate);
//		return template.query(sql, accountResult, IcCheckAccountErrList.class);
//	}
	
	/**
	 * 获取流水记录
	 * @param serial
	 * @return
	 */
//	public IcCheckAccountErrList getRecord(String serial){
//		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = "select * from  IC_CHECK_ACCOUNT_ERR_LIST where serial =#serial# ";
//		IcCheckAccountErrList errList = new IcCheckAccountErrList();
//		errList.setSerial(serial);
//		return template.queryRow(sql, errList, IcCheckAccountErrList.class);
//	}
//	
	/**
	
	/**
	 * 更新锁定流水
	 * @param serial
	 * @return
	 */
//	public IcCheckAccountErrList getRecordWithLock(String serial){
//		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = " select * from  IC_CHECK_ACCOUNT_ERR_LIST where serial =#serial# for update with RS";
//		IcCheckAccountErrList errList = new IcCheckAccountErrList();
//		errList.setSerial(serial);
//		return template.queryRow(sql, errList, IcCheckAccountErrList.class);
//	}
//	
//	public List<IcCheckAccountErrList> getRecordsByTranDate(String tranDate){
//		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = " select * from  IC_CHECK_ACCOUNT_ERR_LIST where TRAN_DATE=#TRAN_DATE# ";
//		IcCheckAccountErrList errList = new IcCheckAccountErrList();
//		errList.setTranDate(tranDate);
//		return template.query(sql, errList, IcCheckAccountErrList.class);
//	}
	
	
	
	
	/**
	 * 取得本行对账错误记录的条数
	 * @author Liuzy
	 * @return
	 */
//	public int getErrorsNum(){
//		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = " select count(*) rowCount from  IC_CHECK_ACCOUNT_ERR_LIST where CHANNEL_NO != '620' and CHANNEL_NO != '831' and CHANNEL_NO != '832' ";
//		Count rowCount=new Count();
//		Count count=template.queryRow(sql, rowCount,Count.class);
//		return count.getRowCount();
//	}
	
	
	/**
	 * 按条件查询的本行对账错误记录条数
	 * @author Liuzy
	 * @param transDate
	 * @param platformSerial
	 * @return
	 */
//	public int getErrorsNum(String transDate,String platformSerial){
//		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = " select count(*) rowCount from  IC_CHECK_ACCOUNT_ERR_LIST  where TRAN_DATE='"+transDate+"' and PLATFORM_SERIAL like '%"+platformSerial+"%' and CHANNEL_NO != '620' and CHANNEL_NO != '831' and CHANNEL_NO != '832'";
//		Count rowCount=new Count();
//		Count count=template.queryRow(sql, rowCount,Count.class);
//		return count.getRowCount();
//	}
	
	
	/**
	 * 分页查询全部的本行对账出错信息
	 * @author Liuzy
	 * @param start
	 * @param end
	 * @return
	 */
//	public List<IcCheckAccountErrList> getAllErrors(String start,String end){
//		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = "select * from (select b.* ,ROWNUM rnum from (select * from IC_CHECK_ACCOUNT_ERR_LIST where   CHANNEL_NO != '620' and CHANNEL_NO != '831' and CHANNEL_NO != '832' order by serial asc) b where ROWNUM<="+end+") p where p.rnum>="+start;
//
//		IcCheckAccountErrList errList = new IcCheckAccountErrList();
//		return template.query(sql, errList, IcCheckAccountErrList.class);
//	}
	/**
	 * 分页按条件查询全部的本行对账出错信息
	 * @author Liuzy
	 * @param start
	 * @param end
	 * @return
	 */
//	public List<IcCheckAccountErrList> getAllErrors(String transDate,String platformSerial,String start,String end){
//		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = "select * from (select b.* ,ROWNUM rnum from (select * from IC_CHECK_ACCOUNT_ERR_LIST where TRAN_DATE='"+transDate+"' and PLATFORM_SERIAL like '%"+platformSerial+"%' and  CHANNEL_NO != '620' and CHANNEL_NO != '831' and CHANNEL_NO != '832'    order by serial asc) b where ROWNUM<="+end+") p where p.rnum>="+start;
//		IcCheckAccountErrList errList = new IcCheckAccountErrList();
//		return template.query(sql, errList, IcCheckAccountErrList.class);
//	}

	
	
	/**
	 * 取得银联对账错误记录的条数
	 * @author Liuzy
	 * @return
	 */
//	public int getUnionErrorsNum(){
//		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = " select count(*) rowCount from  IC_CHECK_ACCOUNT_ERR_LIST where CHANNEL_NO = '620' or CHANNEL_NO = '831' or CHANNEL_NO = '832' ";
//		Count rowCount=new Count();
//		Count count=template.queryRow(sql, rowCount,Count.class);
//		return count.getRowCount();
//	}
	
	
	/**
	 * 按条件查询的银联对账错误记录条数
	 * @author Liuzy
	 * @param transDate
	 * @param platformSerial
	 * @return
	 */
//	public int getUnionErrorsNum(String transDate,String platformSerial){
//		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = " select count(*) rowCount from  IC_CHECK_ACCOUNT_ERR_LIST  where TRAN_DATE='"+transDate+"' and PLATFORM_SERIAL like '%"+platformSerial+"%' and CHANNEL_NO = '620' or CHANNEL_NO = '831' or CHANNEL_NO = '832'";
//		Count rowCount=new Count();
//		Count count=template.queryRow(sql, rowCount,Count.class);
//		return count.getRowCount();
//	}
	
	
	/**
	 * 分页查询全部的银联对账出错信息
	 * @author Liuzy
	 * @param start
	 * @param end
	 * @return
	 */
//	public List<IcCheckAccountErrList> getAllUnionErrors(String start,String end){
//		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = "select * from (select b.* ,ROWNUM rnum from (select * from IC_CHECK_ACCOUNT_ERR_LIST where   CHANNEL_NO = '620' or CHANNEL_NO = '831' or CHANNEL_NO = '832' order by serial asc) b where ROWNUM<="+end+") p where p.rnum>="+start;
//
//		IcCheckAccountErrList errList = new IcCheckAccountErrList();
//		return template.query(sql, errList, IcCheckAccountErrList.class);
//	}
	/**
	 * 分页按条件查询全部的银联对账出错信息
	 * @author Liuzy
	 * @param start
	 * @param end
	 * @return
	 */
//	public List<IcCheckAccountErrList> getAllUnionErrors(String transDate,String platformSerial,String start,String end){
//		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
//		String sql = "select * from (select b.* ,ROWNUM rnum from (select * from IC_CHECK_ACCOUNT_ERR_LIST where TRAN_DATE='"+transDate+"' and PLATFORM_SERIAL like '%"+platformSerial+"%' and  CHANNEL_NO = '620' or CHANNEL_NO = '831' or CHANNEL_NO = '832'    order by serial asc) b where ROWNUM<="+end+") p where p.rnum>="+start;
//		IcCheckAccountErrList errList = new IcCheckAccountErrList();
//		return template.query(sql, errList, IcCheckAccountErrList.class);
//	}
	
	/**
	 * 差错
	 * 平账情况下：
	 * 写卡失败时调账与冲正状态（tran_status:5，3）不能入差错表
	 * 撤消状态不可能写卡失败（原流水）
	 * 成功状态写卡失败需要入差错
	 */
	public void copyUnBalanceRecordsToError(boolean isCups,String channel,String checkDate){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql = "INSERT INTO gr_bus_cheaccerrlist(SELECT A.*,'0','','','','0' FROM gr_bus_cheacctxlist A " +
				"where ((reconciliation_logo<>'11' and reconciliation_logo<>'99') " +
				"or (reconciliation_logo='11' and write_card_state='3' and tran_status not in('5','3'))) and channel_no=#channel# " ;
		if(isCups){
			sql += "and channel_date=#checkdate#";
		}else{
			sql += "and tran_date=#checkdate#";
		}
		sql +=")";
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("channel", channel);
		para.put("checkdate", checkDate);
		template.update(sql, para);
	}
	
	public List<String> queryCityCode(){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = "select '9'||CORE_AREA_NO as CORE_AREA_NO from GR_AREACODE_INFO";
		List<Map<String,Object>> lists = template.query(sql, null);
		List<String> res = new ArrayList<String>();
		for(Map<String,Object> temp:lists){
			res.add(String.valueOf(temp.get("CORE_AREA_NO")));
		}
		return res;
	}

}
