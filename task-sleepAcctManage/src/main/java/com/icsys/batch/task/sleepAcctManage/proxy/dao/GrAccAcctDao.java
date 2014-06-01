package com.icsys.batch.task.sleepAcctManage.proxy.dao;

import java.util.List;

import com.icsys.batch.task.sleepAcctManage.proxy.bean.GrAccAcct;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.TemplateManager;
/**
 * @author SDNX
 *
 */
public class GrAccAcctDao {
	
	public static final String DB_PARA = SystemConstans.DB_SOURCE_NAME;
	/**
	 * @param accNo
	 * @param appId
	 * @return
	 */
	public List<GrAccAcct> getSleepAccList() {
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DB_PARA);
		String sql = "select ic_no as acc_no,max(ic_pro) as mac,sum(acc_balance) as acc_balance,max(acc_ltdate) as acc_ltdate from (" +
		"select ic_no,ic_pro,acc_no,acc_balance,acc_ltdate from gr_acc_acct g inner join(" + 
		"select ic_no,ic_pro,cag_offacct,cag_onacct from gr_card_appreg a inner join ic_cardinfo_req b on a.cag_acctid=b.acct_no where ic_stat=#appId#) c" +
		" on g.acc_no=c.cag_offacct or g.acc_no=c.cag_onacct) group by ic_no";
		GrAccAcct grAccAcct = new GrAccAcct();
		grAccAcct.setAppId("22");
		return template.query(sql, grAccAcct,GrAccAcct.class);
	}
}
