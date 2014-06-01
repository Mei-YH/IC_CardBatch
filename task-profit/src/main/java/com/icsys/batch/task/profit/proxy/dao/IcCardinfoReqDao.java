package com.icsys.batch.task.profit.proxy.dao;

import com.icsys.batch.task.profit.proxy.bean.IcCardinfoReq;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class IcCardinfoReqDao {
	public static final String DS_NAME = "IC_DATASOURCE";
	/**
	 * @param icNo
	 * @param icIndex
	 * @return
	 */
	public IcCardinfoReq showCardInfo(String icNo,String icIndex) {
		// TODO Auto-generated method stub
		NamedQueryTemplate template = TemplateManager
		.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from IC_CARDINFO_REQ where IC_NO=#icNo# and IC_INDEX=#icIndex#";
		IcCardinfoReq al = new IcCardinfoReq();
		al.setIcNo(icNo);
		al.setIcIndex(icIndex);
		return template.queryRow(sql, al, IcCardinfoReq.class);
	}
}
