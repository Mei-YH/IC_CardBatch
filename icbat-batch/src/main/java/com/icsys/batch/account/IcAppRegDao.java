package com.icsys.batch.account;

import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

/** 
 * @author Runpu Hu  
 * @version 创建时间：2011-4-22 下午06:01:23 
 * 类说明 ：
 */

public class IcAppRegDao {
	
	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;

	private static Logger LOG = Logger.getLogger(IcAppRegDao.class);

	/**
	 * 新加应用绑定表
	 * 
	 * @param icAppReg
	 * @return
	 */
	public int addIcAppReg(IcAppReg icAppReg) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "insert into IC_APP_REG (ACCT_NO_ID,AID,APPLICATION_CARD_LOGO,APPLICATION_OFFLINE_SUB_ACCT,APPLICATION_ONLINE_SUB_ACCT,BUSINESS_BRANCH,AMOUNT_MAX,TRADE_AMOUNT_MAX,APPLICATION_STATE) values (#ACCT_NO_ID#,#AID#,#APPLICATION_CARD_LOGO#,#APPLICATION_OFFLINE_SUB_ACCT#,#APPLICATION_ONLINE_SUB_ACCT#,#BUSINESS_BRANCH#,#AMOUNT_MAX#,#TRADE_AMOUNT_MAX#,#APPLICATION_STATE#)";
		if (LOG.isDebugEnabled()) {
			LOG.debug("add 客户账户信息表记录:" + sql);
		}
		int result = template.update(sql, icAppReg);
		return result;
	}

	/**
	 * 根据主键更新表
	 * 
	 * @param icAppReg
	 * @return
	 */
	public int updateIcAppReg(IcAppReg icAppReg) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "update IC_APP_REG set " +
					 "APPLICATION_CARD_LOGO= #APPLICATION_CARD_LOGO#," +
					 "APPLICATION_OFFLINE_SUB_ACCT= #APPLICATION_OFFLINE_SUB_ACCT#," +
					 "APPLICATION_ONLINE_SUB_ACCT= #APPLICATION_ONLINE_SUB_ACCT#," +
					 "BUSINESS_BRANCH= #BUSINESS_BRANCH#," +
					 "AMOUNT_MAX=#AMOUNT_MAX#," +
					 "TRADE_AMOUNT_MAX=#TRADE_AMOUNT_MAX#," +
					 "APPLICATION_STATE= #APPLICATION_STATE# " +
					 "where " +
					 "ACCT_NO_ID=#ACCT_NO_ID# and AID=#AID#";
		if (LOG.isDebugEnabled()) {
			LOG.debug("update DB" + sql);
		}
		return template.update(sql, icAppReg);
	}
	
	/**
	 * 更新应用状态
	 * 
	 * @param icAppReg
	 * @return
	 */
	public int updateStatus(IcAppReg icAppReg) {
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql = "update IC_APP_REG set " +
				     "APPLICATION_STATE= #APPLICATION_STATE# " +
				     "where " +
				     "ACCT_NO_ID=#ACCT_NO_ID# and AID=#AID#";
		if (LOG.isDebugEnabled()) {
			LOG.debug("update DB" + sql);
		}
		return template.update(sql, icAppReg);
	}

	/**
	 * 根据账户客户标识获取记录
	 * 
	 * @param acctNoId 客户账户标识
	 * @param appNo 应用编号
	 * @return
	 */
	public IcAppReg getIcAppReg(String acctNoId,String appNo) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = "select cag_offacct as application_offline_sub_acct,cag_onacct as application_online_sub_acct,cag_branch as business_branch," +
				"cag_state as application_state,cag_tramax as amount_max,cag_onemax as trade_amount_max from gr_card_appreg where cag_acctid=#ACCT_NO_ID# and app_id=#AID#";
		IcAppReg reg = new IcAppReg();
		reg.setAcctNoId(acctNoId);
		reg.setAid(appNo);		
		IcAppReg result = template.queryRow(sql, reg, IcAppReg.class);
		return result;
	}
	
	
	/**
	 * 根据客户账户帐号IC卡绑定记录（可能多条）
	 * @param acctNoId
	 * @return
	 */
	public List<IcAppReg> getNatureAppRegList(String acctNoId,String appStatus){
		NamedQueryTemplate template = TemplateManager
		.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from IC_APP_REG where ACCT_NO_ID=#ACCT_NO_ID# AND APPLICATION_STATE=#APPLICATION_STATE#";
		IcAppReg reg = new IcAppReg();
		reg.setAcctNoId(acctNoId);
		reg.setApplicationState(appStatus);
	    List<IcAppReg> list = template.query(sql, reg,IcAppReg.class);
	    return list;
	}
	
	/**
	 * 卡号查询此卡绑定的所有应用
	 * @param acctNoId
	 * @param appStatus
	 * @return
	 */
	public List<IcAppReg> getIcAppRegList(String acctNoId){
		NamedQueryTemplate template = TemplateManager
		.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from IC_APP_REG where ACCT_NO_ID=#ACCT_NO_ID#";
		IcAppReg reg = new IcAppReg();
		reg.setAcctNoId(acctNoId);
	    List<IcAppReg> list = template.query(sql, reg,IcAppReg.class);
	    return list;
	}

}
