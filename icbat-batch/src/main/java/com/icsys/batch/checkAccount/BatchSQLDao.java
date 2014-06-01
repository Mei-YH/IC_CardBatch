package com.icsys.batch.checkAccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icsys.batch.util.SystemConstans;
import com.icsys.batch.util.SystemParamValue;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class BatchSQLDao {
	
	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;

	public void moveTxListRecordsToHistory() throws Exception{
		NamedUpdateTemplate template =  TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String tableName = SystemParamValue.getNotCurrentSerialTableName();
		String copySql = "INSERT INTO gr_bus_txlisthis (select * from "+tableName+")";
		template.update(copySql, null);
		truncateTxList(template, tableName,"");
	}
	
	public void moveTranLogRecordsToHistory(String tranDate,boolean isCups) throws Exception{
		NamedUpdateTemplate template =  TemplateManager.getNamedUpdateTemplate(DS_NAME);
//		String tableName = getNotCurrentSerialTable();
		String where = "";
		if(isCups){
			where = " where (log_chdate='" + tranDate + "' or substr(log_chdate,5,4)='" + tranDate + "') and request_id='0075'";
		}else{
			where = " where log_trdate='" + tranDate + "' and request_id<>'0075'";
		}
		String copySql = "INSERT INTO gr_bus_trloglisthis (select * from gr_bus_trloglist " + where + ")";
		template.update(copySql, null);
		truncateTxList(template, "gr_bus_trloglist",where);
	}
	
	public void copyUnBalanceRecordsToError() throws Exception{
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String tableName = SystemParamValue.getNotCurrentSerialTableName();
		String sql = "INSERT INTO gr_bus_cheaccerrlist(SELECT A.*,B.log_trfuction,B.log_REMARK, B.log_wrcstate,B.log_comresults,"+
		" B.log_chno,B.log_chdate,B.log_chserial,B.log_hdate,B.log_opteller,B.log_terno,B.log_cheteller,B.log_autteller, " +
		" '0','','','','0' FROM "+ tableName +" A ,gr_bus_trloglist B where A.txl_plserial = B.log_plserial and A.txl_trdate=B.log_trdate and A.txl_recflag!='11' and A.txl_recflag!='99')" ;
		IcCheckAccountTxList accountResult = new IcCheckAccountTxList();
		template.update(sql, accountResult);
//		truncateTxList(template,tableName);
	}
	
	/**
	 * 获取某个渠道的成功流水
	 * 
	 * @param channelNo
	 *            渠道编号
	 * @param channelDate
	 *            渠道日期
	 * @return
	 */
	public List<Map<String, Object>> getChannelBalanceRecords(String channelNo,
			String channelDate) {

		// 需要查询的字段;交易流水表的简写为：TX;交易日志表的简写为：TRAN
		String queryField = "TRAN.CHANNEL_DATE,TRAN.CHANNEL_SERIAL,TX.TRAN_DATE,TX.PLATFORM_SERIAL,TX.TRAN_AMOUNT,TRAN.CORRESPOND_CARD_NO,TRAN.CARD_NO,TRAN.CHANNEL_NO,TRAN.TRAN_CODE ";
		String whereStr = "  WHERE TRAN.CHANNEL_NO IN ("
				+ channelNo
				+ ") AND TRAN.CHANNEL_DATE = #channelDate# AND TX.TRAN_STATUS = '0' "
				+ " AND TRAN.PLATFORM_SERIAL = TX.PLATFORM_SERIAL ";
		String listA_sql = "SELECT " + queryField
				+ " FROM IC_TRAN_LIST TRAN,IC_TX_LIST_A TX " + whereStr;
		String listB_sql = "SELECT " + queryField
				+ " FROM IC_TRAN_LIST TRAN,IC_TX_LIST_B TX " + whereStr;
		String listHistory_sql = "SELECT " + queryField
				+ " FROM IC_TRAN_LIST_H TRAN,IC_TX_LIST_H TX " + whereStr;

		String sql = listA_sql + " UNION " + listB_sql + " UNION "
				+ listHistory_sql;

		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		Map<String, Object> inputParams = new HashMap<String, Object>();
		// inputParams.put("channelNo", channelNo);
		inputParams.put("channelDate", channelDate);
		// System.out.println("map is:"+inputParams);
		return template.query(sql, inputParams);

	}
		
	private void truncateTxList(NamedUpdateTemplate template,String tableName,String where ) throws Exception {
		
		template.update("delete from " + tableName + where, null);
	}
}
