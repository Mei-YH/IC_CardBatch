package com.icsys.batch.task.laterclearing.proxy.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.task.laterclearing.proxy.bean.CanReadBean;
import com.icsys.batch.task.laterclearing.proxy.bean.Constants;
import com.icsys.batch.task.laterclearing.proxy.bean.IcanReadBean;
import com.icsys.batch.util.SystemConstans;
import com.icsys.batch.util.SystemParamValue;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;
import com.icsys.platform.util.DateUtils;

/**
 * @author 作者:lyq E-mail:lyq_java@126.com 此类是得到所需条件的sql查询方法类
 */
public class LaterClearDao {
	
	private Logger LOG = Logger.getLogger(LaterClearDao.class);

	/**
	 * 
	 * @param
	 * @return 查询异常登记簿中已经回收的卡,可读,销卡
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public List<CanReadBean> queryCanRead(long startNo,long endNo) throws NumberFormatException,
			Exception {
		String tran_date=getTranDate();
		//String sql = "select j.offline_clear_sub_acct,j.AID,t.tran_branch,k.ISSUER_BRANCH,k.CARD_NO,k.PRODUCT_NO,t.tran_teller,t.tran_date from ic_abn_info t,ic_clear_acct j, IC_INFO_REG k where t.abnormal_state=" +Constants.STATUS_RECYCLE_READABLE+" and t.late_tran_state="+Constants.STATUS_BR_CARD+" and t.card_no=j.card_no and t.card_no=k.card_no and TRAN_DATE=" + tran_date +" and rownum>="+startNo+" and rownum<="+endNo;
		//String sql="select temp.* from (select j.offline_clear_sub_acct,j.AID,t.tran_branch,k.ISSUER_BRANCH,k.CARD_NO,k.PRODUCT_NO,t.tran_teller,t.tran_date,rownum from ic_abn_info t,ic_clear_acct j, IC_INFO_REG k where t.abnormal_state=" +Constants.STATUS_RECYCLE_READABLE+" and t.late_tran_state="+Constants.STATUS_BR_CARD+" and t.card_no=j.card_no and t.card_no=k.card_no and TRAN_DATE=" + tran_date+") temp where rownum>="+startNo+" and rownum<="+endNo;
		String sql="select temp.* from (select j.offline_clear_sub_acct,j.AID,t.CARD_INDEX,t.tran_branch,k.ISSUER_BRANCH,k.CARD_NO,k.PRODUCT_NO,t.tran_teller,t.tran_date,row_number() over() as rownum from ic_abn_info t,ic_clear_acct j, IC_INFO_REG k where t.abnormal_state=" +Constants.STATUS_RECYCLE_READABLE+" and t.late_tran_state="+Constants.STATUS_BR_CARD+" and t.card_no=j.card_no and t.card_no=k.card_no and t.CARD_INDEX=j.CARD_INDEX and TRAN_DATE=" + tran_date+") temp where rownum>="+startNo+" and rownum<="+endNo;
		NamedQueryTemplate dq = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
		List<CanReadBean> result = dq.query(sql, null, CanReadBean.class);
		return result;
	}

	/**
	 * 
	 * @param CARD_NO
	 * @return 查询异常登记簿中已经回收的卡不可读状态
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public List<IcanReadBean> queryIcanRead(String tran_date) throws NumberFormatException, Exception {
		//String sql="select j.offline_clear_sub_acct,j.aid,t.tran_branch,t.tran_teller,t.card_no from ic_abn_info t,ic_clear_acct j where t.abnormal_state="+Constants.STATUS_RECYCLE_UNREADABLE+" and t.card_no=j.card_no and TRAN_DATE=" + tran_date+" and rownum>="+startNo+" and rownum<="+endNo;
		//oracle sql
		//String sql="select temp.* from (select j.offline_clear_sub_acct,j.aid,t.tran_branch,t.tran_teller,t.card_no, rownum from ic_abn_info t,ic_clear_acct j where t.abnormal_state="+Constants.STATUS_RECYCLE_UNREADABLE+" and t.card_no=j.card_no and TRAN_DATE=" + tran_date+") temp where rownum>="+startNo+" and rownum<="+endNo;
		//DB2 
//		String sql="select j.cag_offacct as offline_clear_sub_acct,j.app_id as aid,t.ic_index as card_index,t.branch as tran_branch,t.teller_no as tran_teller,t.ic_no as card_no from ic_cardabn_req t,gr_card_clear j " +
//				" where t.abn_stat ='"+Constants.STATUS_RECYCLE_UNREADABLE+"' and t.ic_no=j.ic_cardno and t.ic_index=j.ic_index and tx_dt='" + tran_date+"'";
		
		String sql = "select ic_no,ic_index,old_off,old_on,t.app_id,branch,teller_no,new_ic_no,cag_offacct as new_off,cag_onacct as new_on from (" +
				"select n.ic_no,n.ic_index,cag_offacct as old_off,cag_onacct as old_on,app_id,branch,teller_no,new_ic_no,c.acct_no from (" +
				"select ic_no,ic_index,cag_offacct,cag_onacct,app_id,branch,teller_no,new_ic_no from (" +
					"select b.ic_no,b.ic_index,b.acct_no,a.branch,a.teller_no,a.new_ic_no from ic_cardabn_req a left join ic_cardinfo_req b " +
					"on a.ic_no=b.ic_no and a.ic_index=b.ic_index where tx_dt='" + tran_date + "' and abn_stat='" + Constants.STATUS_RECYCLE_UNREADABLE + "'" +
							" order by tx_dt,tx_tm) r left join gr_card_appreg g on r.acct_no=g.cag_acctid) n " +
				"left join ic_cardinfo_req c on n.new_ic_no=c.ic_no) t " +
				"left join gr_card_appreg g on t.acct_no=g.cag_acctid ";
		
		sql = "select * from(select ic_no,ic_index,old_off,old_on,branch,teller_no,tx_dt,tx_tm,new_ic_no,cag_offacct as new_off,cag_onacct as new_on from (" +
		      "select n.ic_no,n.ic_index,cag_offacct as old_off,cag_onacct as old_on,branch,teller_no,tx_dt,tx_tm,new_ic_no,c.acct_no from (" +
		      "select ic_no,ic_index,cag_offacct,cag_onacct,branch,teller_no,new_ic_no,tx_dt,tx_tm from (" +
		      "select b.ic_no,b.ic_index,b.acct_no,a.branch,a.teller_no,a.new_ic_no,a.tx_dt,a.tx_tm from (" +
		      "select * from ic_cardabn_req where tx_dt=#aid# and abn_stat=#abnState#" +
		      ") a left join ic_cardinfo_req b on a.ic_no=b.ic_no and a.ic_index=b.ic_index " +
		      ") r left join gr_card_appreg g on r.acct_no=g.cag_acctid " +
		      ") n left join ic_cardinfo_req c on n.new_ic_no=c.ic_no " + 
		      ") t left join gr_card_appreg g on t.acct_no=g.cag_acctid order by tx_dt,tx_tm) where old_off is not null or old_on is not null";

		NamedQueryTemplate dq = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
		IcanReadBean temp = new IcanReadBean();
		temp.setAbnState(Constants.STATUS_RECYCLE_UNREADABLE);
		temp.setAid(tran_date);
		return dq.query(sql, temp, IcanReadBean.class);
	}
	/**
	 * 查询异常登记簿中已经回收的卡,可读,销卡记录数
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public int queryRowCanRead() throws NumberFormatException, Exception {
		String tran_date=getTranDate();
		String sql="select count(*) count from ic_abn_info t,ic_clear_acct j where t.abnormal_state='" +Constants.STATUS_RECYCLE_READABLE+"' and t.late_tran_state='"+Constants.STATUS_BR_CARD+"' and t.card_no=j.card_no and TRAN_DATE='" + tran_date+"'";
		LOG.info(sql);
		NamedQueryTemplate dq = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
		int count=Integer.parseInt(dq.queryRow(sql, null).get("count").toString());
		LOG.info("大小:"+count);
		return count;
	}
	/**
	 * 查询异常登记簿中已经回收的卡不可读记录数
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public int queryRowIcanRead() throws NumberFormatException, Exception {
		String tran_date=getTranDate();
		String sql="select count(*) count from ic_abn_info t,ic_clear_acct j where t.abnormal_state ='"+Constants.STATUS_RECYCLE_UNREADABLE+"' and t.card_no=j.card_no and TRAN_DATE='" + tran_date+"'";
		NamedQueryTemplate dq = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);
		int count=Integer.parseInt(dq.queryRow(sql, null).get("count").toString());
		return count;
	}
	/**
	 * 通过账务日期与清算周期的比较,得到过了清算周期的那一天
	 * @return
	 * @throws Exception
	 */
	public String getTranDate() throws Exception {
		String cDate = SystemParamValue.getOffClearCycle();
		int clearDate = Integer.parseInt(cDate);//清算周期
		LOG.info("清算周期为:"+clearDate);
		String financialDate=SystemParamValue.getSystemDate();//取得系统的账务日期
		String date=financialDate.substring(0, 4)+"-"+financialDate.substring(4,6)+"-"+financialDate.substring(6, 8);//转换为yyyy-MM-dd的形式
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");//设定要格式化日期的规则
		Calendar c=Calendar.getInstance();
		c.setTime(Date.valueOf(date));
		LOG.info("账务日期为:"+sf.format(c.getTime()));
		c.add(Calendar.DATE, -clearDate);
		LOG.info("要处理的数据日期为:"+sf.format(c.getTime()));
		return sf.format(c.getTime());
	}
	
	/*
	 * 更新异常表状态
	 */
	public void updateAbnInfo(String cardNo,String cardIndex,String lateState,String tranDate,String sysdate,
			String lateSerial,String lateBranch) throws Exception{
		String lateTime = DateUtils.getCurrentTime();
		NamedUpdateTemplate up = TemplateManager.getNamedUpdateTemplate(SystemConstans.DB_SOURCE_NAME);
		String sql = "update ic_cardabn_req set end_stat="+lateState+",end_dt='"+sysdate+"',end_tm='"+lateTime+"',end_seqno='"+lateSerial+"'," +
				"end_branch_code='"+lateBranch+"' where ic_no='"+cardNo+"' and ic_index='"+cardIndex+"' and tx_dt='"+tranDate+"'";
		up.update(sql, null);
		
	}
}
