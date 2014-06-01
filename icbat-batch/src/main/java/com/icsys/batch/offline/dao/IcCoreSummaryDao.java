package com.icsys.batch.offline.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.icsys.batch.offline.bean.SummaryBean;
import com.icsys.batch.util.SystemConstans;
import com.icsys.batch.util.SystemParamValue;
import com.icsys.batch.util.Utils;
import com.icsys.platform.dao.TXHelper;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class IcCoreSummaryDao {
	private static final Logger LOG = Logger.getLogger(IcCoreSummaryDao.class);
	private NamedUpdateTemplate dm = TemplateManager.getNamedUpdateTemplate(SystemConstans.DB_SOURCE_NAME);
	private NamedQueryTemplate dq = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);

	/**
	 * 初始化一个汇总类,赋一些默认值
	 * @param bean
	 * @return
	 * @throws Exception 
	 */
	public SummaryBean initSummaryBean(SummaryBean bean){
		bean.setOutAccType("9");//转出账户类型为9-机构集中户
		bean.setOutAccAttr("0001");//转出帐户性质 0001-普通活期0002-普通定期 0003-整存整取0004-储寿保
		bean.setOutCurrency("01"); //转出货币代号 01-人民币
		bean.setOutFCFlag("0");//转出钞汇标志 0-钞户 1-汇户
		bean.setOutOsAcc("");//转出对方帐户
		bean.setInAccType("9");//转入帐户类型
		bean.setInAccAttr("0001");//转入帐户性质 
		bean.setInCurrency("01");//转入货币代号
		bean.setInFCFlag("0");//转入钞汇标志
		bean.setInOsAcc("");//转入对方帐户
		bean.setFee("0.00");//手续费金额
		bean.setRemark("");//备注信息
		bean.setProFlag("0");//处理标识（1为已提交，0为未提交）
		bean.setCoreSerial("");
		bean.setCoreStatus("");
		bean.setErrorMsg("");
		bean.setErrorReason("");
		bean.setCoreDate("");
		return bean;
	}
	
	public SummaryBean querySummaryByPK(String acctDate,String batchNo,String batchSn){
		String sql = "select scf_id as batch_no,scf_outatype as OUTACC_TYPE,scf_outacc as OUT_ACC,scf_outaattr as OUT_ACC_ATTR,scf_outcurrency as OUT_CURRENCY," +
				"scf_outfflag as OUT_FC_FLAG,scf_outaname as OUT_ACC_NAME,scf_outosacc as OUT_OS_ACC,scf_inatype as IN_ACC_TYPE,scf_inacc as IN_ACC," +
				"scf_in_acc_attr as IN_ACC_ATTR,scf_incurcode as IN_CURRENCY,scf_ifflag as IN_FC_FLAG,scf_ianame as IN_ACC_NAME,scf_inosacc as IN_OS_ACC," +
				"scf_tramt as TRAN_AMT,scf_fee as FEE,scf_remark as REMARK,scf_proflag as PRO_FLAG,scf_cldate as CLEAR_DATE,scf_cdate as CORE_DATE,scf_accdate as ACCT_DATE," +
				"sscf_cserial as CORE_SERIAL,scf_accbranch as ACCT_BRANCH,scf_cstatus as CORE_STATUS,scf_errreason as ERROR_REASON,scf_errmsg as ERROR_MSG "+
				"from gr_bus_scfile where scf_id=#BATCH_NO# and scf_accdate=#ACCT_DATE#";
		SummaryBean params = new SummaryBean();
		params.setAcctDate(acctDate);
		params.setBatchNo(batchNo+"|"+batchSn);
		SummaryBean summary = dq.queryRow(sql, params, SummaryBean.class);
		return summary;
	}
	
	public void saveOrUpdateSummary(SummaryBean bean) throws Exception{
		SummaryBean temp = querySummaryByPK(bean.getAcctDate(),bean.getBatchNo(),bean.getBatchSn());
		//将batch_no与batch_sn组合在一起变成（batch_sn|batch_sn）成为一个主键scf_id
		bean.setBatchNo(bean.getBatchNo()+"|"+bean.getBatchSn());
		String sql = "";
		if(temp!=null){
			//如果该条记录已经存在，则更新
			sql = "update gr_bus_scfile set scf_outatype=#OUTACC_TYPE#,scf_outacc=#OUT_ACC#,scf_outaattr=#OUT_ACC_ATTR#,scf_outcurrency=#OUT_CURRENCY#," +
				"scf_outfflag=#OUT_FC_FLAG#,scf_outaname=#OUT_ACC_NAME#,scf_outosacc=#OUT_OS_ACC#,scf_inatype=#IN_ACC_TYPE#,scf_inacc=#IN_ACC#," +
				"scf_in_acc_attr=#IN_ACC_ATTR#,scf_incurcode=#IN_CURRENCY#,scf_ifflag=#IN_FC_FLAG#,scf_ianame=#IN_ACC_NAME#,scf_inosacc=#IN_OS_ACC#," +
				"scf_tramt=#TRAN_AMT#,scf_fee=#FEE#,scf_remark=#REMARK#,scf_proflag=#PRO_FLAG#,scf_cldate=#CLEAR_DATE#,scf_cdate=#CORE_DATE#," +
				"sscf_cserial=#CORE_SERIAL#,scf_accbranch=#ACCT_BRANCH#,scf_cstatus=#CORE_STATUS#,scf_errreason=#ERROR_REASON#,scf_errmsg=#ERROR_MSG# " +
				"where scf_id=#BATCH_NO# and scf_accdate=#ACCT_DATE#";
		}else{
			//如果该条记录不存在，则插入
			sql = "insert into gr_bus_scfile(" +
					"scf_id,scf_outatype,scf_outacc,scf_outaattr,scf_outcurrency,scf_outfflag,scf_outaname" +
					",scf_outosacc,scf_inatype,scf_inacc,scf_in_acc_attr,scf_incurcode,scf_ifflag,scf_ianame,scf_inosacc,scf_tramt,scf_fee,scf_remark,scf_proflag,scf_accdate,scf_cldate,scf_cdate,scf_cserial,scf_accbranch,scf_cstatus,scf_errreason,scf_errmsg)" +
					" values(#BATCH_NO#,#OUTACC_TYPE#,#OUT_ACC#,#OUT_ACC_ATTR#,#OUT_CURRENCY#,#OUT_FC_FLAG#,#OUT_ACC_NAME#,#OUT_OS_ACC#," +
					"#IN_ACC_TYPE#,#IN_ACC#,#IN_ACC_ATTR#,#IN_CURRENCY#,#IN_FC_FLAG#,#IN_ACC_NAME#,#IN_OS_ACC#,#TRAN_AMT#,#FEE#,#REMARK#,#PRO_FLAG#,#ACCT_DATE#,#CLEAR_DATE#,#CORE_DATE#,#CORE_SERIAL#,#ACCT_BRANCH#,#CORE_STATUS#,#ERROR_REASON#,#ERROR_MSG#)";
		}
		dm.update(sql, bean);
	}
	 
	/**
	 * 根据清算日期日期，查询当日所以汇总信息
	 * @param clearDate 清算日期
	 * @return 汇总信息
	 */
//	public List<SummaryBean> getSummaryInfo(String clearDate) {
//		//int count = getCount(clearDate);
//		String sql = "select * from ic_core_summary t where t.clear_date=#clearDate#";
//		SummaryBean bean =  new SummaryBean();
//		bean.setClearDate(clearDate);
//		List<SummaryBean> result  = dq.query(sql,bean, SummaryBean.class);
//		return result;
//	}

	/**
	 * 根据清算日期获得当前日期汇总信息总条数
	 * @param clearDate 清算日期
	 * @return 记录总条数
	 */
	public int getCount(String clearDate){
		String countSql = "select count(*) COUNT from ic_core_summary t where t.clear_date=#clearDate#";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("clearDate", clearDate);
		Map<String,Object> result = dq.queryRow(countSql, params) ;
		int count = Integer.parseInt(result.get("COUNT").toString());
		return count;
	}
	/**
	 * 获取所有机构的汇总信息
	 * @param acctDate
	 * @return
	 */
	public List<Map<String, Object>> getAllBranchSummaryInfo(String acctDate){
		String sql = "SELECT ACCT_BRANCH,COUNT(*) AS TOTAL_NUM,SUM(TRAN_AMT) AS TOTAL_AMOUNT " +
				" FROM IC_CORE_SUMMARY " +
				" WHERE ACCT_DATE = #ACCT_DATE# " +
				" GROUP BY ACCT_BRANCH ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ACCT_DATE", acctDate);
		Map<String,Integer> paramsType = new HashMap<String, Integer>();
		paramsType.put("ACCT_DATE", Types.VARCHAR);
		return dq.query(sql, params, paramsType);
	}
	
	/**
	 * 获取所有核心转账记录，并对转出账户，转入账户相同的记录进行汇总
	 * @param acctDate 账务日期
	 * @param ID 当天的批次号
	 * @return
	 */
	public List<SummaryBean> getSummaryRecords(String acctDate,String batchNo){
		String sql = "  select BATCH_NO,BATCH_SN,ACCT_BRANCH,OUTACC_TYPE,OUT_ACC,OUT_ACC_ATTR,OUT_CURRENCY,OUT_FC_FLAG,OUT_ACC_NAME,OUT_OS_ACC, " +
				" IN_ACC_TYPE,IN_ACC,IN_ACC_ATTR,IN_CURRENCY,IN_FC_FLAG,IN_ACC_NAME,IN_OS_ACC,REMARK," +
				" sum(TRAN_AMT) as TRAN_AMT,sum(FEE) as FEE " +
				" FROM IC_CORE_SUMMARY " +
				" WHERE ACCT_DATE = #ACCT_DATE# and BATCH_NO=#BATCH_NO#" +
				" GROUP BY BATCH_NO,BATCH_SN,ACCT_BRANCH,OUTACC_TYPE,OUT_ACC,OUT_ACC_ATTR,OUT_CURRENCY,OUT_FC_FLAG,OUT_ACC_NAME,OUT_OS_ACC, " +
				" IN_ACC_TYPE,IN_ACC,IN_ACC_ATTR,IN_CURRENCY,IN_FC_FLAG,IN_ACC_NAME,IN_OS_ACC,REMARK";
		SummaryBean bean = new SummaryBean();
		bean.setAcctDate(acctDate);
		bean.setBatchNo(batchNo);
		return dq.query(sql, bean,SummaryBean.class);
	}
	
	public int updateCoreSuccessResult(String acctDate ,String coreDate,String coreSerial,String outAcc,String inAcc ){
		String sql = " UPDATE IC_CORE_SUMMARY SET CORE_STATUS = '1' ,CORE_DATE=#CORE_DATE#,CORE_SERIAL=#CORE_SERIAL# " +
				" WHERE OUT_ACC=#OUT_ACC# AND IN_ACC=#IN_ACC# AND ACCT_DATE = #ACCT_DATE# ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ACCT_DATE", acctDate);
		params.put("CORE_DATE", coreDate);
		params.put("CORE_SERIAL", coreSerial);
		params.put("OUT_ACC", outAcc);
		params.put("IN_ACC", inAcc);
		
		return dm.update(sql, params);
	}
	
	public int updateCoreFailureResult(String acctDate ,String coreDate,String coreSerial,String errReason,String errMsg ,String outAcc,String inAcc){
		String sql = " UPDATE IC_CORE_SUMMARY SET CORE_STATUS = '2' ,CORE_DATE=#CORE_DATE#,CORE_SERIAL=#CORE_SERIAL# ," +
				" ERROR_REASON=#ERROR_REASON#,ERROR_MSG=#ERROR_MSG# " +
				" WHERE OUT_ACC=#OUT_ACC# AND IN_ACC=#IN_ACC# AND ACCT_DATE = #ACCT_DATE# ";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ACCT_DATE", acctDate);
		params.put("CORE_DATE", coreDate);
		params.put("CORE_SERIAL", coreSerial);
		params.put("OUT_ACC", outAcc);
		params.put("IN_ACC", inAcc);
		params.put("ERROR_REASON", errReason);
		params.put("ERROR_MSG", errMsg);
		
		return dm.update(sql, params);
	}
	
	/**
	 * 根据交易日期获取该日期内的所有的批次号(不重复)
	 * @param tranDate
	 * @return
	 */
	public List<SummaryBean> getBatchNos(String tranDate) {
		String sql="select DISTINCT BATCH_NO from ic_core_summary where clear_date='"+tranDate+"'";
		return dq.query(sql, null,SummaryBean.class);
	}
	
	/**
	 * 根据交易日期获取该日期,该批次所有的交易金额汇总
	 * @param tranDate
	 * @return
	 */
	public String getTMT(String tranDate,String batchNo) {
		String sql="select sum(TRAN_AMT) as TRAN_AMT from ic_core_summary where clear_date='"+tranDate+"' and batch_no='"+batchNo+"'";
		return dq.queryRow(sql, null).get("TRAN_AMT").toString();
	}
}