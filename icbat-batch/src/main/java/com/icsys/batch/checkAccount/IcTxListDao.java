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

/** 
 * @author   
 * @version 创建时间：2011-5-13 下午05:24:55 
 * 类说明 ：交易流水表
 */

public class IcTxListDao {

	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;

	private static Logger LOG = Logger.getLogger(IcTxListDao.class);

	/**
	 * 新增交易流水
	 * @param icTxList
	 * @return
	 * 20111013 增加卡序号
	 */
	public int addIcTxList(IcTxList icTxList,String table){
		int result = 0;
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql = "INSERT INTO "+table+"(txl_SERIAL,txl_trdate,txl_plserial,txl_hserial,txl_trbranch," +
				"ic_cardno,CARD_INDEX,txl_samount,txl_trflag,txl_recflag,txl_tramount,txl_FEE,currency_code," +
				"txl_acctno,lending_sign,txl_cardno1,txl_acctno1,txl_accamount1,txl_accflag,txl_htaccno,txl_trstatus,txl_correcord) " +
				"VALUES (#serial#,#tranDate#,#platformSerial#,#hostSerial#,#tranBranch#,#cardNo#,#cardIndex#," +
				"#startAmount#,#tranFlag#,#reconciliationLogo#,#tranAmount#,#fee#,#currType#," +
				"#acctNo#,#acctCd#,#cardNo1#,#acctNo1#,#acctAmount1#,#acctFlag#,#hostTranAcctNo#," +
				"#tranStatus#,#coreReversalRecord#) ";
		if(LOG.isDebugEnabled())
			LOG.debug("插入交易流水表的记录:" + icTxList.toString());
		result = template.update(sql, icTxList);
		LOG.warn("IC卡交易流水表[" + icTxList.getSerial() + "]结果" + (result == 1 ? "成功" : "失败"));
		return result;
	}
	
	/**
	 * 更新交易流水
	 * @param icTxList
	 * @return
	 * 20111013 增加卡序号
	 */
	public int updateIcTxListWithSerial(IcTxList icTxList) throws Exception{
		String table = SystemParamValue.getNotCurrentSerialTableName();
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql="UPDATE "+table+" set txl_plserial=#serial#,txl_trdate=#tranDate#,txl_serial= #platformSerial#," +
				"txl_hserial= #hostSerial#,txl_trbranch= #tranBranch#,ic_cardNO= #cardNo#,CARD_INDEX=#cardIndex#," +
				"txl_samount= #startAmount#,txl_trflag= #tranFlag#," +
				"txl_recflag= #reconciliationLogo#,txl_tramount= #tranAmount#,txl_FEE= #fee#," +
				"currency_code= #currType#,txl_acctno= #acctNo#,lending_sign= #acctCd#,txl_cardno1= #cardNo1#," +
				"txl_actno1= #acctNo1#,txl_accamount1= #acctAmount1#,txl_accflag= #acctFlag#," +
				"txl_htaccno= #hostTranAcctNo#,txl_trstatus= #tranStatus#,txl_correcord=#coreReversalRecord# WHERE txl_plSERIAL=#serial# ";
		
		if(LOG.isDebugEnabled())
			LOG.debug("更新交易流水表记录:" + sql);
		int result = template.update(sql, icTxList);
		return result;
	}
	
	
	/**
	 * 通过IC卡平台流水号获取交易流水
	 * @param platformSerial IC卡平台流水号
	 * @return 交易流水记录集
	 */
	public List<IcTxList> getIcTxListListByPlatformSerial(String platformSerial,String tranDate) throws Exception{
		String table = SystemParamValue.getNotCurrentSerialTableName();
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = "SELECT txl_serial as serial,txl_tramount as tran_amount,txl_trdate as tran_date,txl_hserial as host_serial," +
				"txl_trbranch as tran_branch,ic_cardno as card_no,card_index,txl_samount as start_amount,txl_trflag as tran_flag," +
				"txl_recflag as reconciliation_logo,txl_tramount as tran_amount,txl_fee as fee,currency_code as curr_type," +
				"txl_acctno as acctno,lending_sign as acct_cd,txl_cardno1 as card_no1,txl_acctno1 as acct_no1,txl_accamount1 as acct_amount1," +
				"txl_accflag as acct_flag,txl_htaccno as host_tran_acct_no,txl_trstatus as tran_status,txl_correcord as core_reversal_record FROM "+table
		+" WHERE txl_serial = #platformSerial# and txl_trdate=#TRAN_DATE#" +
		" union SELECT txl_serial as serial,txl_tramount as tran_amount,txl_trdate as tran_date,txl_hserial as host_serial," +
		"txl_trbranch as tran_branch,ic_cardno as card_no,card_index,txl_samount as start_amount,txl_trflag as tran_flag," +
		"txl_recflag as reconciliation_logo,txl_tramount as tran_amount,txl_fee as fee,currency_code as curr_type," +
		"txl_acctno as acctno,lending_sign as acct_cd,txl_cardno1 as card_no1,txl_acctno1 as acct_no1,txl_accamount1 as acct_amount1," +
		"txl_accflag as acct_flag,txl_htaccno as host_tran_acct_no,txl_trstatus as tran_status,txl_correcord as core_reversal_record " +
		" FROM gr_bus_txlisthis WHERE txl_serial = #platformSerial# and txl_trdate=#TRAN_DATE#";
		IcTxList icTxList = new IcTxList();
		icTxList.setPlatformSerial(platformSerial);
		icTxList.setTranDate(tranDate);
		List<IcTxList> result = template.query(sql, icTxList, IcTxList.class);
		return result;
	}

	/*
	 * 更新对账标志，对账使用
	 */
	public int updateRecFlag(IcTxList icTxList) throws Exception{
		String table = SystemParamValue.getNotCurrentSerialTableName();
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql="UPDATE " + table + " set txl_recflag= #reconciliationLogo# WHERE txl_plserial=#serial# ";
		int result = template.update(sql, icTxList);
		if(result == 0 ){
			sql = "update gr_bus_txlisthis set txl_recflag= #reconciliationLogo# WHERE txl_plserial=#serial#";
			result = template.update(sql, icTxList);
		}
		return result;
	}	
	
	/*
	 * 根据渠道编号及日期删除流水表记录
	 */
	public void deleteTxList(boolean isCups,String channel,String trandate) throws Exception{
		String table = SystemParamValue.getNotCurrentSerialTableName();
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("channel", channel);
		para.put("trandate", trandate);
		para.put("flag", "1");
		para.put("rec", "99");
		String sql = "delete from " + table + " where txl_serial in(select txl_serial from " + table + " a inner join " +
				"gr_bus_cheacctxlist b on a.txl_plserial = b.platform_serial where channel_no=#channel#";
		if(isCups){
			sql += " and (channel_date=#trandate# or substr(channel_date,5,4)=#trandate#))";
			template.update(sql, para);
			table = SystemParamValue.getCurrentSerialTableName();
			sql = "delete from " + table + " where txl_serial in(select txl_serial from " + table + " a inner join " +
			"gr_bus_cheacctxlist b on a.txl_plserial = b.platform_serial where channel_no=#channel# and " +
			"(channel_date=#trandate# or substr(channel_date,5,4)=#trandate#))";
		}else{
			sql += " and tran_date=#trandate# union all select txl_serial from " + table + " where txl_trdate=#trandate# and substr(txl_trflag,1,1)=#flag# and txl_recflag=#rec#)";
		}
		template.update(sql, para);
	}
	
	/**
	 * 入历史表
	 */
	public void insertHistory(boolean isCups,String channel,String trandate) throws Exception{
		String table = SystemParamValue.getNotCurrentSerialTableName();
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("channel", channel);
		para.put("trandate", trandate);
		para.put("flag", "1");
		para.put("rec", "99");
		String sql = "insert into gr_bus_txlisthis(select a.txl_serial,a.txl_plserial,a.txl_hserial,a.txl_trbranch,a.ic_cardno,a.card_index," +
				"a.txl_samount,a.txl_trflag,b.reconciliation_logo,a.txl_tramount,a.txl_fee,a.currency_code,a.txl_acctno,a.lending_sign," +
				"a.txl_cardno1,a.txl_acctno1,a.txl_accamount1,a.txl_accflag,a.txl_htaccno,a.txl_trstatus,a.txl_correcord,a.txl_trdate from " + table + 
				" a inner join gr_bus_cheacctxlist b on a.txl_plserial=b.platform_serial where channel_no=#channel#";
		if(isCups){
			sql += " and (channel_date=#trandate# or substr(channel_date,5,4)=#trandate#) union all ";
			table = SystemParamValue.getCurrentSerialTableName();
			sql += "select a.txl_serial,a.txl_plserial,a.txl_hserial,a.txl_trbranch,a.ic_cardno,a.card_index," +
			"a.txl_samount,a.txl_trflag,b.reconciliation_logo,a.txl_tramount,a.txl_fee,a.currency_code,a.txl_acctno,a.lending_sign," +
			"a.txl_cardno1,a.txl_acctno1,a.txl_accamount1,a.txl_accflag,a.txl_htaccno,a.txl_trstatus,a.txl_correcord,a.txl_trdate from " + table + 
			" a inner join gr_bus_cheacctxlist b on a.txl_plserial=b.platform_serial where channel_no=#channel# and " +
			"(channel_date=#trandate# or substr(channel_date,5,4)=#trandate#))";
		}else{
			sql += " and tran_date=#trandate# union all select * from " + table + " where txl_trdate=#trandate# and substr(txl_trflag,1,1)=#flag# and txl_recflag=#rec#)";
		}
		template.update(sql, para);
	}
}
