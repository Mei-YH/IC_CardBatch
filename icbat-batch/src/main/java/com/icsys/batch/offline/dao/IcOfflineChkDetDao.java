package com.icsys.batch.offline.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.icsys.batch.offline.bean.ClearDetailBean;
import com.icsys.batch.util.Constants;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class IcOfflineChkDetDao {
	private static final Logger LOG = Logger.getLogger(IcOfflineChkDetDao.class);
	private NamedUpdateTemplate dm = TemplateManager.getNamedUpdateTemplate(SystemConstans.DB_SOURCE_NAME);
	private NamedQueryTemplate dq = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
	/**
	 * 根据清算日期，分页查询交易明细
	 * @param clearDate清算日期
	 * @param batchNo 批次号
	 * @param pageLength每页条数
	 * @param pageNo页码
	 * @return 交易明细
	 */
	public List<ClearDetailBean> getClearingDetailBean(String clearDate,String batchNo,String tranCode,int pageNo,int pageLength) {
		
		// 59个字段，包含数据库表中的所有字段，这些字段在类中都有定义
		String fields = " ocd_cldate as clear_Date,ocd_batchno as batch_No,ocd_batchsn as batch_SN,ocd_trcode as TRAN_CODE,ocd_acct_no as ACCT_NO, " +
				"ocd_tramount as TRAN_AMOUNT,ocd_key7 as KEY7,ocd_key11 as KEY11,ocd_key_38 as KEY_38, ocd_autdate as AUTHORIZE_DATE,ocd_key_37 as KEY_37," +
				"ocd_key32 as KEY32,ocd_key33 as KEY33,ocd_key18 as KEY18,ocd_key41 as KEY41,ocd_key42 as KEY42,ocd_key43 as KEY43, ocd_orgonf as orginalInf," +
				"ocd_sdflag as SINGLE_DOUBLE_FLAG,ocd_cupsserial as CUPS_SERIAL, ocd_recbranch as RECEIVE_BRANCH,ocd_issbranch as ISSUER_BRANCH,ocd_chano as CHANNEL_NO," +
				"ocd_fee as FEE,ocd_cbflag as CROSS_BORDER_FLAG,TAG9F26,ocd_key22 as KEY22,ocd_key23 as KEY23,ocd_key6022 as KEY6022,ocd_key6023 as KEY6023," +
				"ocd_tag9f33 as TAG9F33, TAG95, TAG9F37, TAG9F1E, TAG9F10, TAG9F36, TAG82, TAG9A, TAG9F1A,ocd_trpcode as TRAN_RESPONSE_CODE, TAG9C,TAG9F02," +
				" TAG5F2A,ocd_ctcresults as CHECK_TC_RESULTS,ocd_key14 as KEY14,TAG9F27, TAG9F03, TAG9F34, TAG9F35, TAG84, TAG8F09, TAG9F41," +
				"ocd_amount as AMOUNT,ocd_status as status,ocd_lflag as LOSE_FLAG,ocd_renumber as RETURN_NUMBER,ocd_sramount as SUM_RETURN_AMOUNT," +
				"ocd_accdate as ACCTING_DATE,ocd_plaserial as PLATFORM_SERIAL,ocd_accbranch as ACCTING_BRANCH,ocd_filesrc as FILE_SRC," +
				"ocd_orsys as ORIGSYSNO,ocd_otrtime as ORIG_TRAN_TIME,ocd_prono as PRODUCT_NO,ocd_ocaccout as ORG_CORE_ACCOUNT,ocd_billorg as BILL_ORG ";
		//查询sql语句
		String sql = "select " + fields + " from gr_bus_offchkdet where ocd_cldate=#clearDate#  and ocd_trcode = #tranCode# " +
		             "and ocd_batchno=#batchNo# order by ocd_batchno, ocd_batchsn";
		// 设置查询条件
		ClearDetailBean bean = new ClearDetailBean();
		bean.setClearDate(clearDate);
		bean.setBatchNo(batchNo);
		bean.setTranCode(tranCode);
		// 查询
		List<ClearDetailBean> result = new ArrayList<ClearDetailBean>();
		result = dq.query(sql, bean, ClearDetailBean.class,pageNo,pageLength);
		return result;
	}
	
	/**
	 * 根据清算日期查询总记录条数
	 * @param clearDate 清算日期
	 * @param batchNo 批次号
	 * @param tranCode 交易码
	 * @return 总记录条数
	 */
	public int getTotalCount(String clearDate,String batchNo,String tranCode) {
		String sql = "select count(*) count from gr_bus_offchkdet where ocd_cldate=#clearDate# and ocd_batchno=#batchNo# and ocd_trcode=#tranCode#"; /**/
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clearDate", clearDate);
		params.put("batchNo", batchNo);
		params.put("tranCode", tranCode);
		Map<String,Object> result = dq.queryRow(sql, params);
		return Integer.parseInt(result.get("count").toString());
	}
	
	/**
	 * 查询处理成功的条数或者处理失败的条数
	 * @param clearDate清算日期
	 * @param batchNo 批次号
	 * @param flag 成功失败标识
	 * @return 记录条数
	 */
	public int getCount(String clearDate,String batchNo,String flag) {
		String sql= "select count(*) count from ic_offline_chk_det where clear_date=#clearDate# and batch_no=#batchNo#";
		//String sql= "select count(*) count from ic_offline_chk_det where clear_date=#clearDate# and batch_no=#batchNo# and status like '___"+flag+"'";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clearDate", clearDate);
		params.put("batchNo", batchNo);
		Map<String,Object> result = dq.queryRow(sql, params);
		int count = Integer.parseInt(result.get("count").toString());
		return count;
	}
	
	/**
	 * 更新交易明细
	 * @param detail 新的交易明细
	 * @return 更新的条数
	 * @throws Exception
	 */
	public int updateDetail(ClearDetailBean detail) throws Exception{
		int count = 0;
		String sql = "update gr_bus_offchkdet set ocd_amount=#amount# ,ocd_lflag=#loseFlag# , ocd_status =#status# ,ocd_prono=#productNo# ,ocd_ocaccout=#orgCoreAccount#" +
				",ocd_accbranch=#acctingBranch#,ocd_accdate=#acctingDate#,ocd_plaserial=#platformSerial# ,ocd_ctcresults=#checkTcResults#,ocd_sramount=#SUM_RETURN_AMOUNT#" +
				",ocd_renumber=#returnNumber# where ocd_cldate=#clearDate# and ocd_batchno=#batchNo# and ocd_batchsn=#batchSN#" ;
		count = dm.update(sql, detail);
		return count;
	}

	
	/**
	 * 根据清算日期，批次号，及交易类型，将成功与失败的脱机交易明细分类
	 * @param clearDate 清算日期
	 * @param batchNo 批次号
	 * @param tranCode 交易码
	 * @param pageNo 页码
	 * @param pageLength 每页长度
	 * @param flag 成功失败标识
	 * @return
	 */
	public List<ClearDetailBean> getOfflineBillSysFilesInfo(String clearDate,String batchNo,int pageNo,int pageLength,String flag){
		//String querySql  = "select * from ic_offline_chk_det where clear_date=#clearDate# and batch_No=#batchNo# and status like '___"+flag+"'";
		String querySql  = "select * from ic_offline_chk_det where clear_date=#clearDate# and batch_No=#batchNo#";
		ClearDetailBean params = new ClearDetailBean();
		params.setClearDate(clearDate);
		params.setBatchNo(batchNo);
		List<ClearDetailBean> records = dq.query(querySql, params,ClearDetailBean.class, pageNo, pageLength);
		return records;
	}
	
	/**
	 * 根据清算日期，批次号，交易码及文件来源，获得脱机交易的汇总数据
	 * @param clearDate 清算日期
	 * @param batchNo 批次号
	 * @param fileSrc 文件来源（1：本行，2：银联）
	 * @return 
	 */
	public List<Map<String,Object>> getOfflineSummary(String clearDate,String batchNo,String fileSrc){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("clearDate", clearDate);
		params.put("batchNo", batchNo);
		params.put("tranCode1", Constants.OFFLINE_CONSUMPTION);//脱机消费交易码
		params.put("tranCode2", Constants.OFFLINE_GOODSREJECT);//脱机退货交易码
		params.put("fileSrc", fileSrc);
		String offlineSql = "select ORG_CORE_ACCOUNT,sum(amount) SUM from (select t1.ocd_ocaccout as ORG_CORE_ACCOUNT, sum(ocd_tramount) as amount from gr_bus_offchkdet t1 " +
				"where t1.ocd_trcode =#tranCode1# and t1.ocd_status='0111' and t1.ocd_cldate=#clearDate# and t1.ocd_batchno=#batchNo# " +
				"and t1.ocd_filesrc=#fileSrc# and t1.ocd_ocaccout is not null  group by t1.ocd_ocaccout "+
			" union select t2.ocd_ocaccout as ORG_CORE_ACCOUNT,-1*sum(ocd_tramount) as amount from gr_bus_offchkdet t2 " +
			"where t2.ocd_trcode =#tranCode2# and t2.ocd_status='0000' and  t2.ocd_cldate=#clearDate# and t2.ocd_batchno=#batchNo# " +
			"and t2.ocd_filesrc=#fileSrc# and  t2.ocd_ocaccout is not null  group by t2.ocd_ocaccout) group by ORG_CORE_ACCOUNT";

		List<Map<String,Object>> offlineSum =dq.query(offlineSql, params);
		return offlineSum;
	}
	
	
	/**
	 * 将转账失败的脱机交易明细，复制到差错表中
	 * @param clearDate 清算日期
	 * @param batchNo 批次号
	 */
	public void copyErr(String clearDate,String batchNo) throws  Exception{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("clearDate", clearDate);
		params.put("batchNo", batchNo);
		params.put("status1", "0000");
		params.put("status2", "0111");
		params.put("status3", "0001");
		params.put("code1", "300");
		params.put("code2", "301");
		String copySql = "insert into gr_bus_offchkdeterr (select * from gr_bus_offchkdet where ocd_cldate=#clearDate# and ocd_batchno=#batchNo#" +
				" and ocd_status<>#status1# and ((ocd_status <> #status2# and ocd_trcode=#code1#) or (ocd_status<>#status3# and ocd_trcode=#code2#)) )";
		dm.update(copySql, params);
	}
	
	/**
	 * 数据转移任务：将Constants.BAK_DAYS 天前的数据 从IC_OFFLINE_CHK_DET转移至IC_OFFLINE_CHK_DET_H中
	 * @throws Exception
	 */
	public void moveToHistory(String cleardate,String batchNo) throws Exception{
		String bakSql = "insert into gr_bus_hychkdet(select * from gr_bus_offchkdet where ocd_cldate=#cleardate# and ocd_batchno=#batchNo#)";
		String delSql = "delete from gr_bus_offchkdet where ocd_cldate=#cleardate# and ocd_batchno=#batchNo#";

		Map<String,Object> temp = new HashMap<String, Object>();
		temp.put("cleardate", cleardate);
		temp.put("batchNo", batchNo);
		dm.update(bakSql, temp);
		dm.update(delSql, temp);
	}
	
	/**
	 * 删除一个批次的数据
	 * @param clearDate 清算日期
	 * @param batchNo 批次号
	 * @return 删除的条数
	 */
	public void deleteDetails(String clearDate,String batchNo){
		Map<String,Object> temp = new HashMap<String, Object>();
		temp.put("clearDate", clearDate);
		temp.put("batchNo", batchNo);
		String deleteSql = "delete from gr_bus_offchkdeterr  where off_cdate=#clearDate# and off_batno=#batchNo#";
		dm.update(deleteSql, temp);
	}
	
	/**
	 * 获取原交易的信息(脱机退货)
	 * @param detail 退货交易的明细
	 * @return 原始交易信息
	 */
	public ClearDetailBean getOrginalInfo(ClearDetailBean detail) {
		String orginalInfo=detail.getOrginalInf();
		String orginalTime=orginalInfo.substring(3, 13);
		String orginalSysCode=orginalInfo.substring(13, 19);
		String fields = " ocd_cldate as clear_Date,ocd_batchno as batch_No,ocd_batchsn as batch_SN,ocd_trcode as TRAN_CODE, "
			+ "ocd_acct_no as ACCT_NO, ocd_tramount as TRAN_AMOUNT,ocd_key7 as KEY7,ocd_key11 as KEY11,ocd_key_38 as KEY_38, ocd_autdate as AUTHORIZE_DATE,ocd_key_37 as KEY_37, "
			+ "ocd_key32 as KEY32,ocd_key33 as KEY33,ocd_key18 as KEY18,ocd_key41 as KEY41,ocd_key42 as KEY42,ocd_key43 as KEY43,ocd_sdflag as SINGLE_DOUBLE_FLAG,ocd_cupsserial as CUPS_SERIAL, "
			+ "ocd_recbranch as RECEIVE_BRANCH,ocd_issbranch as ISSUER_BRANCH,ocd_chano as CHANNEL_NO,ocd_fee as FEE,ocd_cbflag as CROSS_BORDER_FLAG, "
			+ "TAG9F26,ocd_key22 as KEY22,ocd_key23 as KEY23,ocd_key6022 as KEY6022,ocd_key6023 as KEY6023,ocd_tag9f33 as TAG9F33, TAG95, TAG9F37, TAG9F1E, "
			+ "TAG9F10, TAG9F36, TAG82, TAG9A, TAG9F1A,ocd_trpcode as TRAN_RESPONSE_CODE, TAG9C, TAG9F02, TAG5F2A,ocd_ctcresults as CHECK_TC_RESULTS,ocd_key14 as KEY14,TAG9F27, TAG9F03, TAG9F34, "
			+ "TAG9F35, TAG84, TAG8F09, TAG9F41,ocd_amount as AMOUNT,ocd_status as status,ocd_lflag as LOSE_FLAG,ocd_renumber as RETURN_NUMBER, "
			+ "ocd_sramount as SUM_RETURN_AMOUNT,ocd_accdate as ACCTING_DATE,ocd_plaserial as PLATFORM_SERIAL,ocd_accbranch as ACCTING_BRANCH,ocd_filesrc as FILE_SRC,ocd_orsys as ORIGSYSNO," +
					"ocd_otrtime as ORIG_TRAN_TIME,ocd_prono as PRODUCT_NO,ocd_ocaccout as ORG_CORE_ACCOUNT,ocd_billorg as BILL_ORG ";
		//查询sql语句
		String sql = "select "+fields+" from gr_bus_offchkdet where ocd_key33='"+detail.getKey33()+"' and ocd_key32='"+
		detail.getKey32()+"' and ocd_key7='"+orginalTime+"' and ocd_key11='"+orginalSysCode+"'";
//		sql = sql + " union select "+fields+" from gr_bus_offchkdet_h where ocd_key33='"+sendBranchFlag+"' and ocd_key32='"+proxyBranchFlag+"' and ocd_key7='"+orginalTime+"' and ocd_key11='"+orginalSysCode+"'";
//		if(LOG.isDebugEnabled()){
//			LOG.debug(sql);
//		}
		ClearDetailBean orinalDetail=dq.queryRow(sql, null, ClearDetailBean.class);
		return orinalDetail;
	}
	
	public int insertClearDetail(ClearDetailBean detail) {
		String sql = "insert into gr_bus_offchkdet (ocd_cldate,ocd_batchno,ocd_batchsn,ocd_filesrc,ocd_billorg,ocd_status,ocd_trcode,ocd_bitmap,ocd_acct_no," +
				"ocd_tramount,ocd_txcur,ocd_key7,ocd_key11,ocd_key_38,ocd_autdate,ocd_key_37,ocd_key32,ocd_key33,ocd_key18,ocd_key41,ocd_key42,ocd_key43," +
				"ocd_orgonf,ocd_otrtime,ocd_orsys,ocd_recode,ocd_sdflag,ocd_cupsserial,ocd_recbranch,ocd_issbranch,ocd_cupsno,ocd_chano,ocd_feat," +
				"ocd_cpusres,ocd_poss,ocd_fee,ocd_cbflag,ocd_reserved0,tag9f26,ocd_key22,ocd_key23,ocd_key6022,ocd_key6023,ocd_tag9f33,tag95,tag9f37," +
				"tag9f1e,tag9f10,tag9f36,tag82,tag9a,tag9f1a,ocd_trpcode,tag9c,tag9f02,tag5f2a,ocd_ctcresults,ocd_key14,tag9f27,tag9f03,tag9f34,tag9f35," +
				"tag84,tag8f09,tag9f41,ocd_reserve2) values (#clearDate#,#batchNo#,#batchSn#,#fileSrc#,#billOrg#,#status#,#tranCode#,#bitMap#,#acctNo#,#tranAmount#," +
				"#txCurr#,#key7#,#key11#,#key38#,#authorizeDate#,#key37#,#key32#,#key33#,#key18#,#key41#,#key42#,#key43#,#orginalInf#,#origTranTime#,#origSysNo#," +
				"#reasonCode#,#singleDoubleFlag#,#cupsSerial#,#receiveBranch#,#issuerBranch#,#cupsNotify#,#channelNo#,#feature#,#cpusReserved#,#posSvr#," +
				"#fee#,#crossBorderFlag#,#reserved0#,#tag9f26#,#key22#,#key23#,#key6022#,#key6023#,#tag9f33#,#tag95#,#tag9f37#,#tag9f1e#,#tag9f10#,#tag9f36#," +
				"#tag82#,#tag9a#,#tag9f1a#,#tranResponseCode#,#tag9c#,#tag9f02#,#tag5f2a#,#checkTcResults#,#key14#,#tag9f27#,#tag9f03#,#tag9f34#,#tag9f35#,#tag84#," +
				"#tag8f09#,#tag9f41#,#reserved2#)";
		int count = dm.update(sql, detail);
		return count;
	}
	
	/***
	*	删除重复数据
	* @return 原始交易信息
	*/
	public int delClearDetail(){
		String sql = "delete from IC_OFFLINE_CHK_DET  where (TRAN_CODE,ACCT_NO,TAG9A,TRAN_AMOUNT,KEY11,TAG9F26,TAG9F36) " +
				"in (select TRAN_CODE,ACCT_NO,TAG9A,TRAN_AMOUNT,KEY11,TAG9F26,TAG9F36 from IC_OFFLINE_CHK_DET " +
				"group by TRAN_CODE,ACCT_NO,TAG9A,TRAN_AMOUNT,KEY11,TAG9F26,TAG9F36 having count(*)>1 ) and STATUS='0000'";
		LOG.debug("SQL:"+sql);
		return dm.update(sql, null);
	}
	
	public List<ClearDetailBean> getResultDetailBean(String clearDate,String billOrg) {
		
		// 59个字段，包含数据库表中的所有字段，这些字段在类中都有定义
		String fields = " ocd_cldate as clear_Date,ocd_batchno as batch_No,ocd_batchsn as batch_SN,ocd_trcode as TRAN_CODE,ocd_acct_no as ACCT_NO, " +
				"ocd_tramount as TRAN_AMOUNT,ocd_key7 as KEY7,ocd_key11 as KEY11,ocd_key_38 as KEY_38, ocd_autdate as AUTHORIZE_DATE,ocd_key_37 as KEY_37," +
				"ocd_key32 as KEY32,ocd_key33 as KEY33,ocd_key18 as KEY18,ocd_key41 as KEY41,ocd_key42 as KEY42,ocd_key43 as KEY43, ocd_orgonf as orginalInf," +
				"ocd_sdflag as SINGLE_DOUBLE_FLAG,ocd_cupsserial as CUPS_SERIAL, ocd_recbranch as RECEIVE_BRANCH,ocd_issbranch as ISSUER_BRANCH,ocd_chano as CHANNEL_NO," +
				"ocd_fee as FEE,ocd_cbflag as CROSS_BORDER_FLAG,TAG9F26,ocd_key22 as KEY22,ocd_key23 as KEY23,ocd_key6022 as KEY6022,ocd_key6023 as KEY6023," +
				"ocd_tag9f33 as TAG9F33, TAG95, TAG9F37, TAG9F1E, TAG9F10, TAG9F36, TAG82, TAG9A, TAG9F1A,ocd_trpcode as TRAN_RESPONSE_CODE, TAG9C,TAG9F02," +
				" TAG5F2A,ocd_ctcresults as CHECK_TC_RESULTS,ocd_key14 as KEY14,TAG9F27, TAG9F03, TAG9F34, TAG9F35, TAG84, TAG8F09, TAG9F41," +
				"ocd_amount as AMOUNT,ocd_status as status,ocd_lflag as LOSE_FLAG,ocd_renumber as RETURN_NUMBER,ocd_sramount as SUM_RETURN_AMOUNT," +
				"ocd_accdate as ACCTING_DATE,ocd_plaserial as PLATFORM_SERIAL,ocd_accbranch as ACCTING_BRANCH,ocd_filesrc as FILE_SRC," +
				"ocd_orsys as ORIGSYSNO,ocd_otrtime as ORIG_TRAN_TIME,ocd_prono as PRODUCT_NO,ocd_ocaccout as ORG_CORE_ACCOUNT,ocd_billorg as BILL_ORG ";
		//查询sql语句
		String sql = "select " + fields + " from gr_bus_offchkdet where ocd_cldate=#clearDate#  and ocd_billorg=#billOrg# and ocd_status<>#status#";
		// 设置查询条件
		ClearDetailBean bean = new ClearDetailBean();
		bean.setClearDate(clearDate);
		bean.setBillOrg(billOrg);
		bean.setStatus("0000");
		// 查询
		List<ClearDetailBean> result = new ArrayList<ClearDetailBean>();
		result = dq.query(sql, bean, ClearDetailBean.class);
		return result;
	}
}