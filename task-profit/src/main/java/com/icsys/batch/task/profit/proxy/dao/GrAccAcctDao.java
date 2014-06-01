package com.icsys.batch.task.profit.proxy.dao;

import com.icsys.batch.task.profit.proxy.bean.GrAccAcct;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class GrAccAcctDao {
	public static final String DS_NAME = "IC_DATASOURCE";
	public GrAccAcct showAccAcct(String accNo,String appId) {
		// TODO Auto-generated method stub
		NamedQueryTemplate template = TemplateManager
		.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from GR_ACC_ACCT where ACC_NO=#accNo# and APP_ID=#appId#";
		GrAccAcct al = new GrAccAcct();
		al.setAccNo(accNo);
		al.setAppId(appId);
		return template.queryRow(sql, al, GrAccAcct.class);
	}

}
