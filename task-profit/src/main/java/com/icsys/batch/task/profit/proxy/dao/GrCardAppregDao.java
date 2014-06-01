package com.icsys.batch.task.profit.proxy.dao;

import com.icsys.batch.task.profit.proxy.bean.GrCardAppreg;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class GrCardAppregDao {
	public static final String DS_NAME = "IC_DATASOURCE";

	public GrCardAppreg showCardApp(String cagAcctid, String appId) {
		// TODO Auto-generated method stub
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from GR_CARD_APPREG where CAG_ACCTID=#cagAccrid# and APP_ID=#appId#";
		GrCardAppreg al = new GrCardAppreg();
		al.setCagAccrid(cagAcctid);
		al.setAppId(appId);
		return template.queryRow(sql, al, GrCardAppreg.class);
	}

}
